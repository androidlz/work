package com.seventeenok.test.android;

import android.content.Intent;
import android.os.Bundle;

import com.seventeenok.test.R;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        MyThread thread=new MyThread();
        thread.start();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public  class MyThread extends Thread{
        @Override
        public void run() {
            try {
                sleep(1000);
                System.out.println("哈哈哈哈哈");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            super.run();
        }
    }


    public class TestThread implements  Runnable{

        @Override
        public void run() {

        }
    }


}
