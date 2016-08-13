package com.example.myjson;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.myjson.factory.HttpFactory;
import com.example.myjson.factory.JsonFactory;
import com.example.myjson.model.Result;
import com.example.myjson.model.School;
import com.example.myjson.model.Teacher;
import com.example.myjson.utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyJson";
    private Handler mHandler;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.schoolList);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String json = String.valueOf(msg.obj);
                Result result = JsonFactory.toBean(json,Result.class);
                TeacharAdaper teacharAdapter = new TeacharAdaper(MainActivity.this,result.getTeachers());
                mListView.setAdapter(teacharAdapter);
            }
        };
        new Thread(new HttpFactory(HttpFactory.MethodEnum.POST,mHandler,null,"http://192.168.1.3:8080/web/servlet/MyServlet", HttpFactory
                .TypeEnum.JSON)).start();
    }

    @NonNull
    private List<Teacher> getTeachers(String json) throws JSONException {
        List<Teacher> teachars = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("mTeachers");
        for (int i = 0;i<jsonArray.length();i++){
            Teacher teachar = new Teacher();
            JSONObject jsonTeachar = jsonArray.getJSONObject(i);
            String name = jsonTeachar.getString("mName");
            int age = jsonTeachar.getInt("mAge");
            String url = jsonTeachar.getString("mUrl");
            JSONArray jsonSchoolArrays = jsonTeachar.getJSONArray("mSchools");
            List<School> schools = new ArrayList<>();
            for (int a = 0;a<jsonSchoolArrays.length();a++) {
                School school = new School();
                JSONObject jsonSchool = jsonSchoolArrays.getJSONObject(a);
                String schoolName = jsonSchool.getString("mSchoolName");
                school.setSchoolName(schoolName);
                schools.add(school);
            }
            teachar.setName(name);
            teachar.setAge(age);
            teachar.setUrl(url);
            teachar.setSchools(schools);
            teachars.add(teachar);
        }
        return teachars;
    }

    @NonNull
    private Result getResult() {
        Result result = new Result();
        result.setState(1);
        List<Teacher> teacherList = new ArrayList<>();
        // 添加教师1
        Teacher teacher = new Teacher();
        teacher.setAge(20);
        teacher.setName("张三");
        teacher.setUrl("http://p3.so.qhmsg.com/bdr/326__/t013a84a077d5b75888.jpg");
        // 添加教师学校
        List<School> schoolList = new ArrayList<>();
        School school = new School();
        school.setSchoolName("哈佛");
        schoolList.add(school);
        teacher.setSchools(schoolList);
        teacherList.add(teacher);   // 加入到教师集合中

        // 添加教师2
        Teacher teacher2 = new Teacher();
        teacher2.setAge(30);
        teacher2.setName("李四");
        teacher2.setUrl("http://p0.so.qhmsg.com/bdr/326__/t01f4df0b15021a380a.jpg");
        // 添加教师学校
        List<School> schoolList2 = new ArrayList<>();
        School school2 = new School();
        school2.setSchoolName("师大");
        schoolList2.add(school2);
        teacher2.setSchools(schoolList2);
        teacherList.add(teacher2);  // 加入到教师集合中

        result.setTeachers(teacherList);
        return result;
    }
}
