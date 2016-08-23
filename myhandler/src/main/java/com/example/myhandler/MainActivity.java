package com.example.myhandler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {
    private EditText mEtName;
    private EditText mEtAge;
    private Button mBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();// 初始化
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyPostThread(String.valueOf(mEtName.getText()), Integer.parseInt(mEtAge.getText().toString())).start();
            }
        });
    }

    private void init() {
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtAge = (EditText) findViewById(R.id.et_age);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    class MyPostThread extends Thread {
        private String mName;
        private int mAge;
        private String mUrl = "http://192.168.82.2:8080/web/servlet/MyServlet";

        public MyPostThread(String name, int age) {
            this.mName = name;
            this.mAge = age;
        }

        @Override
        public void run() {
            super.run();
//            doGet();
            doPost();
        }

        private void doPost() {
            try {
                Properties properties = System.getProperties();
                properties.list(System.out);

                URL url = new URL(mUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setReadTimeout(5000);
                OutputStream outputStream = urlConnection.getOutputStream();
                String para = "name=" + mName + "&age=" + mAge;
                outputStream.write(para.getBytes());    // 参数转为字节传输
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                Log.i("MyHandler", sb.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void doGet() {
            try {
                mUrl = mUrl + "?name=" + URLEncoder.encode(mName, "utf-8") + "&age=" + mAge;
                URL url = new URL(mUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(5000);
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                Log.i("MyHandler", sb.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class MyImageThread extends Thread {
        private Handler mHandler;
        private ImageView mImageView;
        private String mUrl;

        public MyImageThread(ImageView imageView, Handler handler, String url) {
            this.mHandler = handler;
            this.mImageView = imageView;
            this.mUrl = url;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(mUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("GET");
                InputStream inputStream = httpURLConnection.getInputStream();
                File imageFile = null;
                // 保存到sd卡文件中
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    imageFile = new File(Environment.getExternalStorageDirectory(), String.valueOf(System.currentTimeMillis()));
                    FileOutputStream fos = new FileOutputStream(imageFile);
                    byte[] bytes = new byte[2 * 1024];
                    int line;
                    while ((line = inputStream.read(bytes)) != -1) {
                        fos.write(bytes, 0, line);    // 文件流保存到sd卡文件中
                    }
                    fos.close();
                }
                final Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(bitmap);
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class MyThread extends Thread {
        private Handler mHandler;
        private WebView mWebView;
        private String mUrl;

        public MyThread(WebView webView, Handler handler, String url) {
            this.mHandler = handler;
            this.mWebView = webView;
            this.mUrl = url;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(mUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("GET");
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                final String data = sb.toString();

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadDataWithBaseURL(mUrl, data, "text/html", "utf-8", null);
//                        mWebView.loadData(data,"text/html;charset=utf-8",null);
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
