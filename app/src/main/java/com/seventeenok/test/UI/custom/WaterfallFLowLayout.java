package com.seventeenok.test.UI.custom;



import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * @author barry
 * @time 2018-6-13
 * @version V1.0
 */
public class WaterfallFLowLayout extends ViewGroup {

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


//思路，通过前面两节课我门知道了其实，绘制流程最终会调用到我门的OnMesure  和   onLayout,
//而不通的布局，他们自己的实现不一样，所以才有了我门使用的这些基本布局组件
//那么我们现在自己来开发一个瀑布式的流式布局

    public WaterfallFLowLayout(Context context) {
        super(context);
    }

    public WaterfallFLowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WaterfallFLowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        Log.i("barry", "onMeasure.......");

        //此处我门可以知道这里是我们的爸爸的SIZE
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        //当前空间宽高
        int measureWidth = 0;
        int measureHeight = 0;

        //当前行宽，行高，因为存在多行，下一行数据要放到下方，行高需要保存
        int iCurLineW = 0;
        int iCurLineH = 0;


        //1.确认自己当前空间的宽高，这里因为会有两次OnMeasure,进行二级测量优化，所以采用IF_ELSE结构
        //二级优化原理在源码具体Draw时，第一次不会直接进行performDraw的调用反而是在下面重新进行了一次scheduleTraversals
        //在ViewRootImpl源码2349-2372之中我门会看到  scheduleTraversals在我们的2363
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            measureWidth = widthSize;
            measureHeight = heightSize;
        } else {
            //当前VIEW宽高
            int iChildWidth = 0;
            int iChildHeight = 0;
            //获取子VIEW数量用于迭代
            int childCount = getChildCount();
            Log.i("barry", "childCount:" + childCount);
            //单行信息容器
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                //1.测量自己
                measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
                //2.获取XML资源
                MarginLayoutParams layoutParams = (MarginLayoutParams) childAt.getLayoutParams();


                //3.获得实际宽度和高度(MARGIN+WIDTH)
                iChildWidth = childAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                iChildHeight = childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

                Log.i("barry", "------widthSize-----:" + widthSize);

                //4.是否需要换行
                if (iCurLineW + iChildWidth > widthSize) {
                    //4.1.纪录当前行信息
                    //4.1.1.纪录当前行最大宽度，高度累加
                    measureWidth = Math.max(measureWidth, iCurLineH);
                    measureHeight += iCurLineH;
                    Log.i("barry", "------height---换行--:" + measureHeight);
                    //4.1.2.保存这一行数据，及行高

                    //4.2.纪录新的行信息
                    //4.2.1.赋予新行新的宽高
                    iCurLineW = iChildWidth;
                    iCurLineH = iChildHeight;



                } else {
                    //5.1.不换行情况
                    //5.1.1.记录某行内的消息行内宽度的叠加、高度比较
                    iCurLineW += iChildWidth;
                    iCurLineH = Math.max(iCurLineH, iChildHeight);



                }

                //6.如果正好是最后一行需要换行
                if (i == childCount - 1) {
                    //6.1.记录当前行的最大宽度，高度累加
                    measureWidth = Math.max(measureWidth, iCurLineW);
                    measureHeight += iCurLineH;
                }
            }
        }


        Log.i("barry", "width:" + measureWidth);
        Log.i("barry", "height:" + measureHeight);


        //确认保存自己的宽高
        setMeasuredDimension(widthSize, measureHeight);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i("barry", "onLayout.....");

        int startX = getPaddingLeft();
        int startY = getPaddingTop();
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        //每个子控件占据的宽度
        int childViewUseWidth = 0;
        int childViewUseLineHight = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {


            View childView = getChildAt(i);

            if (childView.getVisibility() == GONE) {

                continue;
            }
            //获取每个子控件的layoutParams
            MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();

            int childViewMeasuredWidth = childView.getMeasuredWidth();
            int childViewMeasuredHeight = childView.getMeasuredHeight();

            //startX 变化为0 就换行， 每个子控件在摆放之前，判断剩余控件是否足够，用startX + childViewMeasuredWidth是否大于整个控件的宽度
            //判断的时候考虑PaddingRight
            //考虑了子控件自己的margin值，每个子控件占据的宽度：childViewMeasuredWidth + leftMargin + rightMargin
            childViewUseWidth = childViewMeasuredWidth + layoutParams.leftMargin + layoutParams.rightMargin;
            if (startX + /*childViewMeasuredWidth*/childViewUseWidth > measuredWidth - getPaddingRight()) {

                startX = getPaddingLeft();

                //换行的时候，上一行使用的高度以一行的最高的为准
                startY += /*childViewMeasuredHeight*/childViewUseLineHight; //y左边累加，因为现在所有的子控件高度都一样
            }


            //摆放子控件
            int leftChildView = startX + layoutParams.leftMargin;//考虑自己的margin
            int topChildView = startY + layoutParams.topMargin;
            int rightChildView = leftChildView + childViewMeasuredWidth;
            int bottomChildView = topChildView + childViewMeasuredHeight;
            //子控件布局
            childView.layout(leftChildView, topChildView, rightChildView, bottomChildView);

            //子控件摆放之后累加startX的值, 考虑每个孩子占据的宽度要加上marginLeft , marginRingt
            startX += /*childViewMeasuredWidth*/childViewUseWidth;

            //计算每一行使用的高度
            childViewUseLineHight = Math.max(childViewUseLineHight, childViewMeasuredHeight + layoutParams.topMargin + layoutParams.bottomMargin);
        }
    }
}