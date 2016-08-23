package com.example.administrator.mytwoproject;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent mIntent;
    private Intent mIntent2;
    private Test2Service mTest2Service;
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mTest2Service = ((Test2Service.MyBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartService:
                mIntent = new Intent(MainActivity.this,Test1Service.class);
                startService(mIntent);
                break;
            case R.id.btnStopService:
                stopService(mIntent);
                break;
            case R.id.btnBindService:
                mIntent2 = new Intent(MainActivity.this,Test2Service.class);
                bindService(mIntent2,mConn, Service.BIND_AUTO_CREATE);
                break;
            case R.id.btnPayMusic:
                mTest2Service.playMusic();
                break;
            case R.id.btnHaltMusic:
                mTest2Service.pauseMusic();
                break;
            case R.id.btnStopMusic:
                mTest2Service.stopMusic();
                break;
            case R.id.btnUnBindService:
                unbindService(mConn);
                break;
        }
    }
}
