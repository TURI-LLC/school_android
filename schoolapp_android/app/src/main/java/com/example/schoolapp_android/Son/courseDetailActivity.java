package com.example.schoolapp_android.Son;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.schoolapp_android.R;

import java.util.ArrayList;

public class courseDetailActivity extends AppCompatActivity {
    private TextView teacher;
    private TextView classname;
    private TextView address;
    private TextView week;
    private TextView jie;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("课程详情");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent =getIntent();
        ArrayList<String> s=new ArrayList<>();
        s= intent.getStringArrayListExtra("xinxi");
        System.out.println("你得到了"+s.size());
        teacher=findViewById(R.id.course_teacher);
        classname=findViewById(R.id.course_classname);
        address=findViewById(R.id.course_class);
        week=findViewById(R.id.course_week);
        jie=findViewById(R.id.course_jie);//

        teacher.setText(s.get(0));
        classname.setText(s.get(1));
        week.setText("第"+s.get(2)+"周");
        jie.setText(s.get(3)+"—"+s.get(4)+"节");
        address.setText(s.get(5));



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
