package com.seventeenok.test.UI.scrollAnimator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/**
 *
 *  效果：滑动时在平时的布局上从某一方向划出一个View,和背景渐变
 *  通过设置{@link #setOnScrollAnimatorLinstener(OnScrollAnimatorLinstener)}监听滑动完成时进行界面的切换
 *  使用事项：
 *  若所需要方向不需要滑动，无需再xml定义tag为相应方向的view即可
 * {@link #setMode(Mode)} 设置滑动的类别，有垂直和水平两种
 * 以下view xml中{@see visibility}设置为GONE
 * {@link #mTopView} 在xml中设置tag为 top 一般位置设置为布局中心
 * {@link #mBottomView} 在xml中设置tag为 bottom 一般位置设置为布局中心
 * {@link #mLeftView}  在xml中设置tag为left 一般位置设置为布局中心
 * {@link #mRightView} 在xml中设置tag为right 一般位置设置为布局中心
 * {@link #mMaskView} 在xml中设置tag为mask 用于设置背景蒙版，一般设置其背景色，滑动时会用背景渐变效果
 *  一般布局顺序：主要通常布局-->mMaskView --> mXXView
 *
 * Created by liyaochi on 2016/8/23.
 */
public class ScrollAnimatorLayout extends FrameLayout implements Animator.AnimatorListener {

    /**
     * 垂直的滑动距离
     * 当滑动距离达到该值，动画达到目标位置
     */
    private final static int DEFAULT_VERTICAL_TOUCH_DIATANCE = 400;
    /**
     * 水平的滑动距离
     * 当滑动距离达到该值，动画达到目标位置
     */
    private final static int DEFAULT_HORIZONTAL_TOUCH_DIATANCE = 200;

    /**
     * 默认动画时间
     */
    private static final int DEFAULT_DURATION = 500;

    private final static int MSG_SCROOL_TO_TARGER_POSITON = 1001;
    private final static int MSG_SCROLL_TO_START_POSITON = 1002;

    /**
     * 垂直的触摸距离  用于计算滑动的比例
     */
    private int mVerticalMaxTouchDistance = DEFAULT_VERTICAL_TOUCH_DIATANCE;

    /**
     * 水平的最大触摸距离  用于计算滑动的比例
     */
    private int mHorizontalMaxTouchDistance = DEFAULT_HORIZONTAL_TOUCH_DIATANCE;

    private View mTopView;
    private View mBottomView;
    private View mLeftView;
    private View mRightView;
    private View mMaskView;

    /**
     * 当前滑动操作的view
     */
    private View mOperaView;
    /**
     * 当前滑动的方向
     */
    private ScrollDirection mCurScrollDirection = ScrollDirection.NONE;
    /**
     * 滑动的模式
     * @see Mode
     */
    private Mode mMode = Mode.VERTICAL;
    /**
     * 触摸时的起始坐标
     */
    private float mStartX;
    private float mStartY;
    /**
     * 触摸时的当前坐标
     */
    private float mCurX;
    private float mCurY;

    /**
     * view的最大移动高度
     * {@link #initSize()}
     */
    private float mScrolleHeight;
    /**
     * view的最大的移动宽度
     * {@link #initSize()}
     */
    private float mScrolleWidth;

    private int mTouchSlop;
    private Context mContext;

    private ObjectAnimator mTransInAnimator;
    private ObjectAnimator mTransOutAnimator;
    private ObjectAnimator mAlphaInAnimator;
    private ObjectAnimator mAlphaOutAnimator;
    private AnimatorSet mInAnimatorSet;
    private AnimatorSet mOutAnimatorSet;

    private int mDuration = DEFAULT_DURATION;

    private boolean canScroll = true;
    private OnScrollAnimatorLinstener mOnScrollAnimatorLinstener;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SCROOL_TO_TARGER_POSITON:
                    if (mOnScrollAnimatorLinstener != null)
                        mOnScrollAnimatorLinstener.onEnd(mCurScrollDirection);
                    break;
                case MSG_SCROLL_TO_START_POSITON:
                    if (mOnScrollAnimatorLinstener != null)
                        mOnScrollAnimatorLinstener.onCancel();
                    reset();
                    break;
            }
        }
    };

    public ScrollAnimatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    private void init() {
        mTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        this.setBackgroundColor(Color.TRANSPARENT);
        mMaskView.setAlpha(0f);
        mAlphaInAnimator = ObjectAnimator.ofFloat(mMaskView, "alpha", 0f, 1f);
        mAlphaOutAnimator = ObjectAnimator.ofFloat(mMaskView, "alpha", 1f, 0f);

        mTransInAnimator = new ObjectAnimator();
        mTransOutAnimator = new ObjectAnimator();

        mInAnimatorSet = new AnimatorSet();
        mInAnimatorSet.playTogether(mAlphaInAnimator, mTransInAnimator);
        mInAnimatorSet.setDuration(mDuration);
        mOutAnimatorSet = new AnimatorSet();
        mOutAnimatorSet.playTogether(mAlphaOutAnimator, mTransOutAnimator);
        mOutAnimatorSet.setDuration(mDuration);

        mTransInAnimator.addListener(this);
        mTransOutAnimator.addListener(this);

    }

    /**
     * 初始化view
     * {@link #mTopView} 在xml中设置tag为 top 
     * {@link #mBottomView} 在xml中设置tag为 bottom
     * {@link #mLeftView}  在xml中设置tag为left
     * {@link #mRightView} 在xml中设置tag为right
     */
    private void initView() {
        mTopView = findViewWithTag("top");
        mBottomView = findViewWithTag("bottom");
        mLeftView = findViewWithTag("left");
        mRightView = findViewWithTag("right");
        mMaskView = findViewWithTag("mask");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initSize();
    }

    /**
     * 根据各个View的位置、大小
     * 设置偏移到当前layout的上方、下方、左右的位置
     */
    private void initSize() {
        if (mBottomView != null) {
            mScrolleHeight = (this.getHeight() + mBottomView.getMeasuredHeight()) / 2f;
            mBottomView.setTranslationY(mScrolleHeight);
            mBottomView.setVisibility(View.VISIBLE);
        }
        if (mTopView != null) {
            mScrolleHeight = (this.getHeight() + mTopView.getMeasuredHeight()) / 2f;
            mTopView.setTranslationY(-mScrolleHeight);
            mTopView.setVisibility(View.VISIBLE);
        }
        if (mLeftView != null) {
            mScrolleWidth = (this.getWidth() + mLeftView.getMeasuredWidth()) / 2f;
            mLeftView.setTranslationX(-mScrolleWidth);
            mLeftView.setVisibility(VISIBLE);
        }
        if (mRightView != null) {
            mScrolleWidth = (this.getWidth() + mRightView.getMeasuredWidth()) / 2f;
            mRightView.setTranslationX(mScrolleWidth);
            mRightView.setVisibility(VISIBLE);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                mStartY = event.getY();
                mCurX = event.getX();
                mCurY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                mCurX = event.getX();
                mCurY = event.getY();
                if (canScroll) {
                    initDirection();
                    setCurAnimatorPos();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (canScroll) {
                    continueAninmator();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 根据当前滑动的距离与{@link #mVerticalMaxTouchDistance}的比值
     * 确认当前的动画播放位置
     */
    private void setCurAnimatorPos() {
        float fac;
        if (mMode == Mode.VERTICAL) {
            fac = (mStartY - mCurY) / mVerticalMaxTouchDistance;
        } else
            fac = (mStartX - mCurX) / mHorizontalMaxTouchDistance;

        if (mCurScrollDirection != ScrollDirection.NONE) {
            mTransInAnimator.setCurrentPlayTime((long) (mTransInAnimator.getDuration() * Math.abs(fac)));
            mAlphaInAnimator.setCurrentPlayTime((long) (mAlphaInAnimator.getDuration() * Math.abs(fac) * 2));
//            mInAnimatorSet.pause();
        }
    }

    /**
     * 根据滑动的程度来判读是回复到初始位置，或继续到目标位置
     * 滑动结束后继续动画到初始位置或目标位置
     */
    private void continueAninmator() {

        if (mOperaView == null)
            return;

        float fac = 0f;
        if (mMode == Mode.VERTICAL) {
            fac = Math.abs(mStartY - mCurY) / mVerticalMaxTouchDistance;
        } else {
            fac = Math.abs(mStartX - mCurX) / mHorizontalMaxTouchDistance;
        }
        if (fac < 0.5) {
            mOutAnimatorSet.start();
            mTransOutAnimator.setCurrentPlayTime((long) (mTransOutAnimator.getDuration() * (1 - fac)));
            mAlphaOutAnimator.setCurrentPlayTime((long) (mAlphaOutAnimator.getDuration() * (1 - fac)));
        } else {
            mInAnimatorSet.start();
            mTransInAnimator.setCurrentPlayTime((long) (mTransInAnimator.getDuration() * fac));
            mAlphaInAnimator.setCurrentPlayTime((long) (mAlphaInAnimator.getDuration() * fac * 2));
        }
    }

    /**
     * 根据触摸的方向设置滑动的方向
     * 并确定{@see mOperaView}
     */
    private void initDirection() {
        if (mCurScrollDirection == ScrollDirection.NONE) {

            if (mMode == Mode.VERTICAL) {
                if (mCurY - mStartY > mTouchSlop) {
                    mCurScrollDirection = ScrollDirection.DOWN;
                    mOperaView = mTopView;
                } else if (mStartY - mCurY > mTouchSlop) {
                    mCurScrollDirection = ScrollDirection.UP;
                    mOperaView = mBottomView;
                }
            }
            if (mMode == Mode.HORIZONTAL) {
                if (mCurX - mStartX > mTouchSlop) {
                    mCurScrollDirection = ScrollDirection.RIGHT;
                    mOperaView = mLeftView;
                } else if (mStartX - mCurX > mTouchSlop) {
                    mCurScrollDirection = ScrollDirection.LEFT;
                    mOperaView = mRightView;
                }
            }
            if (mCurScrollDirection != ScrollDirection.NONE) {
                initAnimator();
            }
        }
    }

    private void initAnimator() {
        if (mOperaView == null) {
            mCurScrollDirection = ScrollDirection.NONE;
            return;
        }
        mTransOutAnimator.setTarget(mOperaView);
        mTransInAnimator.setTarget(mOperaView);
        if (mMode == Mode.VERTICAL) {
            mTransOutAnimator.setPropertyName("translationY");
            mTransOutAnimator.setFloatValues(0, mOperaView.getTranslationY());
            // mTransOutAnimator.setEvaluator(mDistanceEvaluator);

            mTransInAnimator.setPropertyName("translationY");
            mTransInAnimator.setFloatValues(mOperaView.getTranslationY(), 0);
            // mTransInAnimator.setEvaluator(mDistanceEvaluator);
        } else {
            mTransOutAnimator.setPropertyName("translationX");
            mTransOutAnimator.setFloatValues(0, mOperaView.getTranslationX());

            mTransInAnimator.setPropertyName("translationX");
            mTransInAnimator.setFloatValues(mOperaView.getTranslationX(), 0);
        }

        if (mOnScrollAnimatorLinstener != null)
            mOnScrollAnimatorLinstener.onStart(mCurScrollDirection);
    }

    /**
     * 重置动画，回到初始状态
     */
    public void reset() {
        mMaskView.setAlpha(0);
        mCurScrollDirection = ScrollDirection.NONE;
        if (mMode == Mode.VERTICAL) {
            if (mBottomView != null) {
                mBottomView.setTranslationY(mScrolleHeight);
            }
            if (mTopView != null) {
                mTopView.setTranslationY(-mScrolleHeight);
            }
        } else {
            if (mLeftView != null)
                mLeftView.setTranslationX(-mScrolleWidth);

            if (mRightView != null)
                mLeftView.setTranslationX(mScrolleWidth);
        }
    }

    /**
     * 获取滑动的模式
     * @see #mMode
     * @see Mode
     * @return
     */
    public Mode getMode() {
        return mMode;
    }

    /**
     * 设置滑动的模式
     * @see #mMode
     * @see Mode
     * @return
     */
    public void setMode(Mode mMode) {
        this.mMode = mMode;
    }

    public void setOnScrollAnimatorLinstener(OnScrollAnimatorLinstener mListener) {
        this.mOnScrollAnimatorLinstener = mListener;
    }

    public boolean isCanScroll() {
        return canScroll;
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if (animation == mTransInAnimator)
            mHandler.sendEmptyMessage(MSG_SCROOL_TO_TARGER_POSITON);
        else if (animation == mTransOutAnimator)
            mHandler.sendEmptyMessage(MSG_SCROLL_TO_START_POSITON);
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    /**
     * 枚举类型
     * 滑动的方向
     */
    public enum ScrollDirection {
        NONE, LEFT, UP, RIGHT, DOWN
    }

    /**
     * 枚举类型
     * 滑动的模式
     */
    public enum Mode {
        VERTICAL, HORIZONTAL
    }

}
