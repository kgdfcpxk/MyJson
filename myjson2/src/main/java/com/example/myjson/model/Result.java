package com.example.myjson.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
public class Result {
    private int mState;
    private List<Teacher> mTeachers;

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        mState = state;
    }

    public List<Teacher> getTeachers() {
        return mTeachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        mTeachers = teachers;
    }
}
