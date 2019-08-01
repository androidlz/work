package com.seventeenok.test.UI.scrollAnimator;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.seventeenok.test.UI.recyclerview.MyRecyclerAdapter;
import com.seventeenok.test.R;

import java.util.ArrayList;


public class ScrollAnimatorActivity extends AppCompatActivity implements FabScrollListener.HideScorllListener {

    private Toolbar toolbar;
    private MyScrollView myScrollView;
    private RecyclerView recyclerview;
    private ImageButton fab;
    private ArrayList<String> mMStrings;
    private MyRecyclerAdapter mMyRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        recyclerview = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.fab);
//        myScrollView = findViewById(R.id.scroll);
//        myScrollView.setOnScrollListener(this);
        setSupportActionBar(toolbar);
        setTitle("新闻标题");
//        recyclerview.addOnScrollListener(new FabScrollListener(this));
        mMStrings = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            mMStrings.add("当前是数据--------------->" + (char) i);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(ScrollAnimatorActivity.this.findViewById(R.id.root), "哈哈哈哈", Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(ScrollAnimatorActivity.this, "确定", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mMyRecyclerAdapter = new MyRecyclerAdapter(this, mMStrings);
//        mRecyclerView.addItemDecoration(new CusRecyclerDecoration(this,CusRecyclerDecoration.VERTICAL_LIST,R.drawable.diver_love,10));
        recyclerview.setAdapter(mMyRecyclerAdapter);
    }

//    @Override
//    public void onChangeAlpha(float alpha) {
//        toolbar.setAlpha(alpha);
//    }

    //获取状态栏高度
    public int getStatusBarHeight(Context context) {
        //android.R.dimen.status_bar_height
        try {
            Class<?> aClass = Class.forName("com.android.internal.R$dimen");
            Object o = aClass.newInstance();
            String status_bar_height = aClass.getField("status_bar_height").get(o).toString();
            //单位是dp
            int height = Integer.parseInt(status_bar_height);
            //dp--sp
            return context.getResources().getDimensionPixelSize(height);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean reverse = false;

    public void rotate(View view) {
        float toDegree = reverse ? -180f : 180f;
        ObjectAnimator animator = ObjectAnimator
                .ofFloat(view, "rotation", 0.0f, toDegree)
                .setDuration(400);
        animator.start();
        reverse = !reverse;
    }

    @Override
    public void onHide() {
        //---属性动画
        toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new DecelerateInterpolator(3));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fab.getLayoutParams();
        fab.animate().translationY(fab.getHeight() + layoutParams.bottomMargin).setInterpolator(new AccelerateInterpolator(3));
    }

    @Override
    public void onShow() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fab.getLayoutParams();
        fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
    }
}

