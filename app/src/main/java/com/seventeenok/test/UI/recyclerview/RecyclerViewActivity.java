package com.seventeenok.test.UI.recyclerview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.seventeenok.test.R;

import java.util.ArrayList;
import java.util.Collections;

public class RecyclerViewActivity extends AppCompatActivity implements ItemMoveListener{
    
//    private RecyclerView mRecyclerView;
    private ArrayList<String> mMStrings;
    private MyRecyclerAdapter mMyRecyclerAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
//        mRecyclerView = findViewById(R.id.rv);
        mMStrings = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            mMStrings.add("当前是数据--------------->" + (char) i);
        }
//        for (int i = 'A'; i <= 'Z'; i++) {
//            mMStrings.add("当前是数据--------------->" + (char) i);
//
//        }
//        for (int i = 'A'; i <= 'Z'; i++) {
//            mMStrings.add("当前是数据--------------->" + (char) i);
//        }
//
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//        mRecyclerView.addItemDecoration(new CusRecyclerDecoration(this,CusRecyclerDecoration.VERTICAL_LIST,R.drawable.diver_love,10));
        mMyRecyclerAdapter = new MyRecyclerAdapter(this, mMStrings);
//        mMyRecyclerAdapter.setHasStableIds(true);

//        mRecyclerView.setAdapter(mMyRecyclerAdapter);
//        mHandler.sendEmptyMessageDelayed(1,200);
        MyItemCallBack callback=new MyItemCallBack(this);
//        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(callback);
//        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }


    private int i;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mMStrings.add("添加了------>"+(i++));
//                    mMyRecyclerAdapter.notifyDataSetChanged();
                    mHandler.sendEmptyMessageDelayed(1,200);
                    break;
                default:
                    break;
            }
        }
    };
    private boolean isLinear;
    
    public void go1(View view) {
        if (isLinear) {
//            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        } else {
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        }
        isLinear = !isLinear;
    }
    
    public void go2(View view) {
        
        
    }
    
    public void go3(View view) {
        
        
    }
    
    public void go4(View view) {
        
        
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mMStrings,fromPosition,toPosition);
//        mMyRecyclerAdapter.notifyItemMoved(fromPosition,toPosition);
        return true;
    }

    @Override
    public boolean onItemRemove(int positon) {

        return false;
    }


}
