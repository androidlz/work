package com.seventeenok.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.seventeenok.test.AddPersonInter;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    private static final String TAG = "MyService";

    private List<Person> persons = new ArrayList<>();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("aaaa", "binder绑定成功");
        return binder;
    }

    private final IBinder binder = new AddPersonInter.Stub() {
        @Override
        public List<Person> addPerson(Person person) throws RemoteException {
            synchronized (persons) {
                persons.add(person);
                Log.e("aaaaa", "服务端  name----" + person.getName() + "       age===" + person.getAge());
                return persons;
            }
        }
    };
}
