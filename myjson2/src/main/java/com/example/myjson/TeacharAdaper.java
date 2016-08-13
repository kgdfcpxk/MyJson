package com.example.myjson;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myjson.factory.HttpFactory;
import com.example.myjson.model.School;
import com.example.myjson.model.Teacher;

import java.util.List;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
public class TeacharAdaper extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<Teacher> mTeacherList;

    public TeacharAdaper(Context context, List<Teacher> teacherList) {
        mLayoutInflater = LayoutInflater.from(context);
        mTeacherList = teacherList;
    }

    @Override
    public int getCount() {
        return mTeacherList.size();
    }

    @Override
    public Object getItem(int i) {
        return mTeacherList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holper holper = null;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.schoolitem, null);
            holper = new Holper(view);
            view.setTag(holper);
        } else {
            holper = (Holper) view.getTag();
        }
        Teacher t = mTeacherList.get(i);
        holper.mName.setText(t.getName());
        holper.mAge.setText(String.valueOf(t.getAge()));
        for (School s : t.getSchools()) {
            holper.mSchool.setText(s.getSchoolName());
        }
        final Holper finalHolper = holper;
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                finalHolper.mIcon.setImageBitmap((Bitmap) msg.obj);
            }
        };
        new Thread(new HttpFactory(HttpFactory.MethodEnum.GET,handler,null,t.getUrl(), HttpFactory.TypeEnum.IMAGE)).start();
        return view;
    }

    class Holper {
        public ImageView mIcon;
        public TextView mName;
        public TextView mAge;
        public TextView mSchool;

        public Holper(View view) {
            mIcon = (ImageView) view.findViewById(R.id.iv_image);
            mName = (TextView) view.findViewById(R.id.tv_name);
            mAge = (TextView) view.findViewById(R.id.tv_age);
            mSchool = (TextView) view.findViewById(R.id.tv_school);
        }
    }
}
