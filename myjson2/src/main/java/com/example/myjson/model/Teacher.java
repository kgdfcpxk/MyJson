package com.example.myjson.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
public class Teacher {
    private String mUrl;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    private String mName;
    private int mAge;
    private List<School> mSchools;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public List<School> getSchools() {
        return mSchools;
    }

    public void setSchools(List<School> schools) {
        mSchools = schools;
    }
}
