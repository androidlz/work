package com.seventeenok.test;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.seventeenok.test.test.R;

import java.util.List;

public class Main2Activity extends AppCompatActivity {

    TextView tv;
    private AddPersonInter addPersonInter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=this.findViewById(R.id.tv);
        // 启动服务端的服务，并进行绑定
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.seventeenok.test", "com.seventeenok.test.MyService"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<Person> list = addPersonInter.addPerson(new Person("lizhi", 24));
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            addPersonInter = AddPersonInter.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            addPersonInter = null;
        }
    };
}
