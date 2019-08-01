package com.seventeenok.test;


import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

public class HandlerTest {
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    public void test(){
        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


}
