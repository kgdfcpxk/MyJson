package com.example.administrator.mytwoproject;

import android.app.ActivityManager;
import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MySystemService extends AppCompatActivity {

    private Button mBtnNetwork;
    private Button mBtnWifi;
    private Button mBtnAudio;
    private Button mBtnPackage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater = (LayoutInflater) MySystemService.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_mysystemservice, null);
        setContentView(view);
        init();
        mBtnNetwork.setOnClickListener(new View.OnClickListener() {
            // 查看网络是否连接
            @Override
            public void onClick(View view) {
                if(isNetWork(MySystemService.this)){
                    Toast.makeText(MySystemService.this, "网络已连接", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MySystemService.this, "网络未连接", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBtnWifi.setOnClickListener(new View.OnClickListener() {
            // WIFI开关
            @Override
            public void onClick(View view) {
                WifiManager wifiManage = (WifiManager)MySystemService.this.getSystemService(WIFI_SERVICE);
                if(wifiManage.isWifiEnabled()){
                    Toast.makeText(MySystemService.this, "WIFI已关闭", Toast.LENGTH_SHORT).show();
                    wifiManage.setWifiEnabled(false);
                } else {
                    Toast.makeText(MySystemService.this, "WIFI已打开", Toast.LENGTH_SHORT).show();
                    wifiManage.setWifiEnabled(true);
                }
            }
        });
        mBtnAudio.setOnClickListener(new View.OnClickListener() {
            // 获取音量
            @Override
            public void onClick(View view) {
                AudioManager audioManager = (AudioManager)MySystemService.this.getSystemService(AUDIO_SERVICE);
                int streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
                int streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
                Toast.makeText(MySystemService.this, "当前音量="+streamVolume+",最大音量="+streamMaxVolume, Toast.LENGTH_SHORT).show();
            }
        });
        mBtnPackage.setOnClickListener(new View.OnClickListener() {
            // 获取包名
            @Override
            public void onClick(View view) {
                ActivityManager activityManager = (ActivityManager)MySystemService.this.getSystemService(ACTIVITY_SERVICE);
                String packageName = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
                Toast.makeText(MySystemService.this, "当前包名="+packageName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNetWork(Context context){
        if(context!=null) {
            ConnectivityManager systemService = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = systemService.getActiveNetworkInfo();
            if(activeNetworkInfo!=null){
                return activeNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    private void init() {
        mBtnNetwork = (Button) findViewById(R.id.btnNetwork);
        mBtnWifi = (Button) findViewById(R.id.btnWifi);
        mBtnAudio = (Button) findViewById(R.id.btnAudio);
        mBtnPackage = (Button) findViewById(R.id.btnPackage);
    }
}
