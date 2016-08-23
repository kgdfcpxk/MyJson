package com.example.mygesturedatector;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MyGestureOverlay extends AppCompatActivity {

    private GestureOverlayView mGestureOverlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mygestureoverlay);
        mGestureOverlayView = (GestureOverlayView) findViewById(R.id.gestureOverlayView);
        // 从资源中将手势库加入到项目中
        final GestureLibrary gestureLibrary = GestureLibraries.fromRawResource(MyGestureOverlay.this, R.raw.gestures);
        gestureLibrary.load();
        mGestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
                ArrayList<Prediction> recognize = gestureLibrary.recognize(gesture);
                Prediction prediction = recognize.get(0);
                if (prediction.score >= 5.0) {
                    if (prediction.name.equals("exit")) {
                        Toast.makeText(MyGestureOverlay.this, "退出", Toast.LENGTH_SHORT).show();
                    } else if (prediction.name.equals("prev")) {
                        Toast.makeText(MyGestureOverlay.this, "向上", Toast.LENGTH_SHORT).show();
                    } else if (prediction.name.equals("next")) {
                        Toast.makeText(MyGestureOverlay.this, "向下", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MyGestureOverlay.this, "没找到手势", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
