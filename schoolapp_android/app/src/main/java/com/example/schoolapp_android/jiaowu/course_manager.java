package com.example.schoolapp_android.jiaowu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.schoolapp_android.R;

public class course_manager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_manager);
    }

    public void onclick3(View v){
        LinearLayout li1=this.findViewById(R.id.course_endlayout2);
        LinearLayout li2=this.findViewById(R.id.course_toplayout);
        LinearLayout li3=this.findViewById(R.id.course_admin);
        li1.setVisibility(View.VISIBLE);
        li2.setVisibility(View.VISIBLE);
        li3.setVisibility(View.GONE);
    }
}
