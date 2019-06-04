package com.seventeenok.test.UI.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seventeenok.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyHolder> {
    Context mContext;
    private ArrayList<String> mStringArrayList;
    private List<Integer> heights;
    
    public MyRecyclerAdapter(Context context, ArrayList<String> stringArrayList) {
        mContext = context;
        mStringArrayList = stringArrayList;
        heights = new ArrayList<Integer>();
        for (int i = 0; i < mStringArrayList.size(); i++) {
            heights.add((int) Math.max(200, Math.random() * 550));
        }
    }
    
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final MyHolder myHolder = new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false));
//        myHolder.mTextView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                int postion= myHolder.getAdapterPosition();
//                Toast.makeText(mContext,postion, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
        return myHolder; 
    }
    
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.mTextView.getLayoutParams();
        params.height = heights.get(position);
        holder.mTextView.setBackgroundColor(Color.rgb(100, (int) (Math.random() * 255), (int) (Math.random() * 255)));
        holder.mTextView.setLayoutParams(params);
        holder.mTextView.setText(mStringArrayList.get(position));
        holder.mTextView.setOnLongClickListener(new MyOnLongClickLisener(position));
        holder.mTextView.setTag(position);
    }
    
    public class MyOnLongClickLisener implements View.OnLongClickListener {
        private int position;
        
        MyOnLongClickLisener(int position) {
            this.position = position;
        }
        
        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(mContext, mStringArrayList.get(position), Toast.LENGTH_SHORT).show();
            Log.e("aaa","长安了。。。。"+position);
            return false;
        }
    }
    
    @Override
    public int getItemCount() {
        return mStringArrayList.size();
    }
    
    class MyHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        
        MyHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv);
        }
    }
}
