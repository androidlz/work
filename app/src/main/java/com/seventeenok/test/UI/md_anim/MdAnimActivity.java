package com.seventeenok.test.UI.md_anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.seventeenok.test.R;
import com.seventeenok.test.UI.MainTestActivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.transition.Explode;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MdAnimActivity extends AppCompatActivity {
    @BindView(R.id.imgv)
    Button imgv;
    @BindView(R.id.bt)
    Button bt;
    @BindView(R.id.first)
    LinearLayout first;
    @BindView(R.id.second)
    ImageView second;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md_anim);
        ButterKnife.bind(this);
        //滑动效果
        Slide slide = new Slide();
        //展开效果
        Explode explode = new Explode();
        //渐变隐藏效果
        Fade fade = new Fade();


        slide.setDuration(1000);
        getWindow().setEnterTransition(slide);
        getWindow().setExitTransition(slide);

    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.imgv:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Animator animator = ViewAnimationUtils.createCircularReveal(view, view.getWidth() / 2, view.getHeight() / 2, 0, (float) Math.hypot(view.getWidth() / 2, view.getHeight() / 2));
                    animator.setDuration(1000);
                    animator.setInterpolator(new AccelerateInterpolator());
                    animator.start();
                }
                break;
            case R.id.imgv1:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, 0, 0, (float) Math.hypot(view.getWidth(), view.getHeight()));
                    animator.setDuration(1000);
                    animator.setInterpolator(new AccelerateInterpolator());
                    animator.start();
                }
                break;
        }

    }

    public void startFirstAnimation(View view) {
        //翻转  透明  缩放
        //1  翻转
        ObjectAnimator first_rotaionX = ObjectAnimator.ofFloat(first, "rotationX", 0, 25f);
        first_rotaionX.setDuration(200);

        //2. 透明度
        ObjectAnimator first_alpha = ObjectAnimator.ofFloat(first, "alpha", 1.0f, 0.7f);
        first_alpha.setDuration(200);
        //3  缩放
        ObjectAnimator first_scaleX = ObjectAnimator.ofFloat(first, "scaleX", 1.0f, 0.8f);
        first_alpha.setDuration(300);
        ObjectAnimator first_scaleY = ObjectAnimator.ofFloat(first, "scaleY", 1.0f, 0.8f);
        first_alpha.setDuration(300);

//        first_rotaionX.addUpdateListener(listener);
        ObjectAnimator first_rotaionX_delay = ObjectAnimator.ofFloat(first, "rotationX", 25f, 0f);
        first_rotaionX.setDuration(200);
        first_rotaionX_delay.setStartDelay(300);

        ObjectAnimator animator_translationY = ObjectAnimator.ofFloat(first, "translationY", 0, (float) (-0.1 * first.getHeight()));
        animator_translationY.setDuration(200);
        animator_translationY.setStartDelay(300);


        ObjectAnimator second_translationY = ObjectAnimator.ofFloat(second, "translationY", second.getHeight(), 0f);
        second_translationY.setDuration(400);
        second_translationY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {
                second.setVisibility(View.VISIBLE);

            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(first_rotaionX, first_alpha, first_rotaionX, first_scaleX,
                first_scaleY, first_rotaionX_delay, animator_translationY, second_translationY);
        animatorSet.start();

    }

    public void startSecondAnimation(View view) {
        ObjectAnimator second_translationY = ObjectAnimator.ofFloat(second, "translationY", 0f, second.getHeight());
        second_translationY.setDuration(200);
        second_translationY.start();
        //恢复动画   --- 省略


        //Activity转场动画
//        startActivity(new Intent(this,MainTestActivity.class));
//        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

        //Activity转场动画   共享元素效果
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, second, "scence_name");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(new Intent(this, MainTestActivity.class), compat.toBundle());
        }
        //多个共享元素转场
//        ActivityOptionsCompat compat1 = ActivityOptionsCompat
//                .makeSceneTransitionAnimation(this, Pair.create((View)first, "first"), Pair.create((View)second, "second"));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            startActivity(new Intent(this,MainTestActivity.class),compat1.toBundle());
//        }

    }

}
