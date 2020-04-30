package com.example.schoolapp_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.schoolapp_android.extend.fitActivityAdapter;
import com.example.schoolapp_android.extend.newsAdapter;

import java.util.ArrayList;
import java.util.Collections;

import htmlservice.check_activity;
import htmlservice.check_hous_2credit;
import javabean.JavaBean;

public class secondClassActivity extends AppCompatActivity {
    private String username;
    private ArrayList<JavaBean> second = new ArrayList<>();
    private ArrayList<JavaBean> hous = new ArrayList<>();
    private ArrayList<JavaBean> activityall = new ArrayList<>();
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_class);
        Intent intent=this.getIntent();
        username=intent.getStringExtra("user");
        context=this;
        new check_second_hous().execute();

    }

    private class check_second_hous  extends AsyncTask<Void,String,ArrayList<JavaBean>>{

        @Override
        protected ArrayList<JavaBean> doInBackground(Void... voids) {
            check_hous_2credit a=new check_hous_2credit();
            second=a.check_2credit(username);
            hous=a.check_hous(username);
            check_activity b=new check_activity() ;
            activityall=b.check_activityall(username);
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<JavaBean> javaBeans) {
            //设置学分小时等
            TextView secondtext =findViewById(R.id.second);
            TextView houstext =findViewById(R.id.hous);
            TextView credittext = findViewById(R.id.credit);
            TextView threetext = findViewById(R.id.three);
            //初始化
            Double secondcont=0.00;
            Double houscout=0.00;
            Double threecout =0.00;
            Double creditcout=0.00;

            for(int i =0;i<second.size();i++){
                secondcont+=Double.parseDouble(second.get(i).xf_credit);


            }
            for(int i =0;i<hous.size();i++){
               houscout+=Double.parseDouble(hous.get(i).ho_hours);
            }

            secondtext.setText(String.valueOf(secondcont));
            houstext.setText(String.valueOf(houscout));
            credittext.setText(String.valueOf(creditcout));
            threetext.setText(String.valueOf(threecout));
            //设置结束,如果活动内容不为空则执行动态创建
            if(activityall.get(0).id!=null){
                return;
            }
            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.fitactivity_recy);
            //
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            //news recyclerview适配器
            fitActivityAdapter adapter = new fitActivityAdapter(activityall);
            //取消自带的滚动
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(adapter);//设置适配器


        }
    }

}
