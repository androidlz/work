package com.seventeenok.test.UI.anim;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.seventeenok.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimActivity extends Activity {


    @BindView(R.id.imgv)
    ImageView imgv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        ButterKnife.bind(this);
    }

    public void click(View view) {
        //实现抛物线的效果  x  匀速    y加速  y=1/2 gt*t
//        ValueAnimator animator = new ValueAnimator();
//        animator.setDuration(5000);
//        animator.setObjectValues(new PointF(0, 0));
//        //估值器
//        animator.setEvaluator(new TypeEvaluator<PointF>() {
//            @Override
//            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
//                PointF pointF = new PointF();
//                pointF.x = 100f * fraction * 5;
//                pointF.y = (0.5f * 100f * fraction * fraction * 25);
//                return pointF;
//            }
//        });
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                PointF pointF = (PointF) animation.getAnimatedValue();
//                imgv.setX(pointF.x);
//                imgv.setY(pointF.y);
//            }
//        });
//        animator.start();

        //设置加速度
        ObjectAnimator animator = ObjectAnimator.ofFloat(imgv, "translationX", 0f, 700f);
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateInterpolator(5));
        animator.start();
    }
}
