package com.example.administrator.mytwoproject;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.administrator.mytwoproject.utils.L;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class Test2Service extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        L.i("Test2Service-onCreate()");
    }

    @Override
    public boolean onUnbind(Intent intent){
        L.i("Test2Service-onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.i("Test2Service-onDestroy()");
    }

    public class MyBinder extends Binder{
        public Test2Service getService() {
            return Test2Service.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        L.i("Test2Service-onBind()");
        return new MyBinder();
    }
    public void playMusic(){
        L.i("Test2Service-播放音乐");
    }
    public void pauseMusic(){
        L.i("Test2Service-暂停音乐");
    }
    public void stopMusic(){
        L.i("Test2Service-关闭音乐");
    }
}
