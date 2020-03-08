package com.seventeenok.test.UI.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    //行高纪录
    List<Integer> lstHeights = new ArrayList<>();
    //每一行的视图
    List<List<View>> lstLineView = new ArrayList<>();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        lstHeights.clear();
        lstLineView.clear();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量父布局的size  和  mode
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //当前控件的宽高
        int measureWidth = 0;
        int measureHeight = 0;

        //当前行宽，行高，因为存在多行，下一行数据要放到下方，行高需要保存
        int iCurLineW = 0;
        int iCurLineH = 0;


        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            measureWidth = widthSize;
            measureHeight = heightSize;
        } else {
            //当前VIEW宽高
            int iChildWidth = 0;
            int iChildHeight = 0;
            //获取子VIEW数量用于迭代
            int childCount = getChildCount();
            //当前行存的View
            List<View> viewList = new ArrayList<>();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                //1.测量自己
                measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
                //2.获取xml资源
                MarginLayoutParams layoutParams = (MarginLayoutParams) childAt.getLayoutParams();
                //3.获得实际宽度和高度
                iChildWidth = childAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                iChildHeight = childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

                //4.是否需要换行
                if (iCurLineW + iChildWidth > widthSize) {
                    measureWidth = Math.max(measureWidth, iCurLineW);
                    measureHeight += iCurLineH;

                    lstHeights.add(iCurLineH);
                    lstLineView.add(viewList);

                    iCurLineW = iChildWidth;
                    iChildHeight = iChildHeight;
                    viewList=new ArrayList<>();
                    viewList.add(childAt);
                } else {
                    iCurLineW += iChildWidth;
                    iCurLineH = Math.max(iChildHeight, iCurLineH);
                    viewList.add(childAt);

                }
                //如果最后一个换行了
                if (i == childCount - 1) {
                    measureWidth = Math.max(measureWidth, iCurLineW);
                    measureHeight += iChildHeight;
                    lstLineView.add(viewList);
                    lstHeights.add(iCurLineH);
                }

            }

            setMeasuredDimension(measureWidth, measureHeight);

        }


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //1.取得所有视图信息
        //与之当前组件上下左右四个编剧
        int left,top,right,bottom;
        //当前顶部高度和左部高度
        int curTop = 0;
        int curLeft = 0;

        int lineCount = lstLineView.size();
        for (int i = 0; i < lineCount; i++) {
            List<View> viewList = lstLineView.get(i);
            int size = viewList.size();
            for (int j = 0; j < size; j++) {
                View childView = viewList.get(j);
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
                left=curLeft+layoutParams.leftMargin;
                top=curTop+layoutParams.topMargin;
                right = left + childView.getMeasuredWidth();
                bottom = top + childView.getMeasuredHeight();
                childView.layout(left,top,right,bottom);
                curLeft+=childView.getMeasuredWidth()+layoutParams.leftMargin + layoutParams.rightMargin;


            }
            curLeft = 0;
            curTop += lstHeights.get(i);
        }
        lstLineView.clear();
        lstHeights.clear();
    }

}
