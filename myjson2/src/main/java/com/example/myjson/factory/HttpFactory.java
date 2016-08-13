package com.example.myjson.factory;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
public class HttpFactory implements Runnable {

    private static final String TAG = "MyJson";
    private String mUrl;
    private Handler mHandler;
    private byte[] mBytes;  // Post请求数据
    private MethodEnum mMethodEnum;
    private TypeEnum mTypeEnum;

    // 提交类型枚举
    public enum MethodEnum {
        GET, POST
    }
    // 提交类型枚举
    public enum TypeEnum {
        JSON, IMAGE
    }

    public HttpFactory(MethodEnum methodEnum, Handler handler, byte[] bytes, String url,TypeEnum typeEnum) {
        this.mMethodEnum = methodEnum;
        this.mHandler = handler;
        this.mBytes = bytes;
        this.mUrl = url;
        this.mTypeEnum = typeEnum;
    }

    @Override
    public void run() {
        if (MethodEnum.GET == mMethodEnum) {
            doGet();
        } else if (MethodEnum.POST == mMethodEnum) {
            doPost();
        }
    }

    /**
     * Get提交数据
     */
    private void doGet() {
        InputStream data = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(mUrl);
            conn = (HttpURLConnection) url.openConnection();
            String method = "GET";
            // 设置参数
            settingURLConnection(conn, method);
            // 获取http响应数据
            data = getInputStream(conn);
            // message消息发送到主线程中更新
            sendMessageFromResponse(data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            conn.disconnect();
        }
    }

    /**
     * Post提交数据
     */
    private void doPost() {
        InputStream data = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(mUrl);
            conn = (HttpURLConnection) url.openConnection();
            String method = "POST";
            // 设置参数
            settingURLConnection(conn, method);
            // 设置正文
            settingOutputStream(conn);
            // 获取http响应数据
            data = getInputStream(conn);
            // message消息发送到主线程中更新
            sendMessageFromResponse(data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            conn.disconnect();
        }
    }

    /**
     * 获取http响应数据
     *
     * @param conn
     * @return
     * @throws IOException
     */
    private InputStream getInputStream(HttpURLConnection conn) {
        InputStream inputStream = null;
        try {
            Log.i(TAG,String.valueOf(conn.getResponseCode()));
            // 响应代码 200表示成功
            if (conn.getResponseCode() == 200) {
                // 提交数据到服务器
                inputStream = conn.getInputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * 设置http正文
     *
     * @param conn
     * @throws IOException
     */
    private void settingOutputStream(HttpURLConnection conn) {
        OutputStream outputStream = null;
        try {
            outputStream = conn.getOutputStream();
            outputStream.write(mBytes);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置URLConnection参数
     *
     * @param conn
     * @param method
     * @throws ProtocolException
     */
    private void settingURLConnection(HttpURLConnection conn, String method) {
        conn.setReadTimeout(5000);
        // 设定传送的内容类型是可序列化的java对象
        // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
        conn.setRequestProperty("Content-type", "application/x-java-serialized-object");
        // Post请求，因为数据放在http正文中，所以要设置true，即上传数据
        conn.setDoOutput(true);
        try {
            conn.setRequestMethod(method);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送Http返回数据到Handler
     *
     * @param data
     */
    private void sendMessageFromResponse(InputStream data) {
        Message message = new Message();
        if(mTypeEnum == TypeEnum.IMAGE) {
            message.obj = BitmapFactory.decodeStream(data);
        } else if(mTypeEnum == TypeEnum.JSON) {
            StringBuilder sb = getStringByBufferedReader(data);
            message.obj = String.valueOf(sb);
        }
        mHandler.sendMessage(message);
    }

    @NonNull
    private StringBuilder getStringByBufferedReader(InputStream data) {
        BufferedReader br = new BufferedReader(new InputStreamReader(data));
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = br.readLine())!=null){
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }

}
