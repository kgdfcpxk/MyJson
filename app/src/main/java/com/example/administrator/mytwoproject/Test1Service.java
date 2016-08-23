package com.example.administrator.mytwoproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.administrator.mytwoproject.utils.L;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class Test1Service extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        L.i("Service--onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.i("Service--onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.i("Service--onDestroy()");
    }
}
