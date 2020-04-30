package com.example.schoolapp_android.Son;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.schoolapp_android.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import htmlservice.check_kebiao;
import htmlservice.check_kecheng;
import javabean.JavaBean;

public class kebiao_activity extends AppCompatActivity {
    private final int WC = 50;
    private final int MP = ViewGroup.LayoutParams.WRAP_CONTENT;
    TableLayout tableLayout;
    private String user;
    private ArrayList<JavaBean> kebiao;
    private ArrayList<JavaBean> kecheng;
    private String zhouci;
    private String xueqi;
    private ArrayList<String> year=new ArrayList<>();
    private ArrayList<Integer> maxweek=new ArrayList<>();
    private Spinner yearSpinner;
    private Spinner weekSpinner;
    private ArrayAdapter<String> adapter = null;
    private int jie=12;//节次
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kebiao);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("课程表");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        shuabiao();
        new thread_valiUser().execute();


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


    private class thread_valiUser extends AsyncTask<Void,String, ArrayList<JavaBean>> {


        @Override
        protected ArrayList<JavaBean> doInBackground(Void... voids) {
            check_kebiao a=new check_kebiao();
            ArrayList<JavaBean> A=a.check_kebiao(user);

            return A;
        }

        @Override
        protected void onPostExecute(ArrayList<JavaBean> javaBeans) {
            super.onPostExecute(javaBeans);
            kebiao=javaBeans;
            for(int i=0;i<javaBeans.size();i++){
                year.add(javaBeans.get(i).CoA_Semester);
            }
            HashSet h=new HashSet(year);
            year.clear();
            year.addAll(h);
            yearSpinner=(Spinner)findViewById(R.id.snipper_Year);
            adapter = new ArrayAdapter<String>(kebiao_activity.this,android.R.layout.simple_spinner_item, year);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            yearSpinner.setAdapter(adapter);
            yearSpinner.setVisibility(View.VISIBLE);
            xueqi=year.get(0);
            new thread_valiUser2().execute();
        }
    }
    private class thread_valiUser2 extends AsyncTask<Void,String, ArrayList<JavaBean>> {


        @Override
        protected ArrayList<JavaBean> doInBackground(Void... voids) {
            check_kecheng a=new check_kecheng();

            return a.check_kecheng(user);
        }

        @Override
        protected void onPostExecute(ArrayList<JavaBean> javaBeans) {
            super.onPostExecute(javaBeans);
            kecheng=javaBeans;
            shuabiao();
            for(int i=0;i<javaBeans.size();i++){
                maxweek.add(javaBeans.get(i).Coi_Festivals);
            }
            int max = Collections.max(maxweek);
            ArrayList<String> number= new ArrayList<>();
            for(int i=1;i<=max;i++){
                number.add("第"+i+"周");
            }
            zhouci=number.get(0);
            weekSpinner=(Spinner)findViewById(R.id.snipper_zhou);
            adapter = new ArrayAdapter<String>(kebiao_activity.this,android.R.layout.simple_spinner_item, number);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            weekSpinner.setAdapter(adapter);
            weekSpinner.setVisibility(View.VISIBLE);
            weekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String str=(String)weekSpinner.getSelectedItem();
                    zhouci=str;
                    shuaxinkecheng();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String str=(String)yearSpinner.getSelectedItem();
                    xueqi=str;

                        shuaxinkecheng();


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            weekSpinner.setSelection(0,true);
            yearSpinner.setSelection(0,true);

            //over
            }

        }


        private void shuaxinkecheng(){
            int week=0;
            int start=0;
            int end=0;
            shuabiao();
            String address="";
            String classname="";
            String teacher="";
            Pattern p=Pattern.compile("[^0-9]");
            Matcher m = p.matcher(zhouci);
            zhouci =m.replaceAll("").trim();
            for(int n=0;n<kebiao.size();n++) {
                if(kebiao.get(n).CoA_Semester.equals(xueqi)&&kebiao.get(n).CoA_weekly.equals(zhouci)){
                    //获取老师
                    for(int i=0;i<kecheng.size();i++){

                        if(kecheng.get(i).Coi_id.equals(kebiao.get(n).Coi_id)){
                            teacher=kecheng.get(i).Coi_teacher;
                        }
                    }
                    //获取第几周的课
                    if(kebiao.get(n).CoA_week==null){
                        for(int i=0;i<kecheng.size();i++){

                            if(kecheng.get(i).Coi_id.equals(kebiao.get(n).Coi_id)){
                                week=Integer.parseInt(kecheng.get(i).Coi_week);
                            }
                        }
                    }else{
                        week=Integer.parseInt(kebiao.get(n).CoA_week);
                    }
                    //获取开始节次
                    if(kebiao.get(n).CoA_start==null){
                        for(int i=0;i<kecheng.size();i++){

                            if(kecheng.get(i).Coi_id.equals(kebiao.get(n).Coi_id)){
                                start=Integer.parseInt(kecheng.get(i).Coi_start);
                            }
                        }
                    }else{
                        start=Integer.parseInt(kebiao.get(n).CoA_start);
                    }
                    //获取结束节次
                    if(kebiao.get(n).CoA_end==null){
                        for(int i=0;i<kecheng.size();i++){
                            if(kecheng.get(i).Coi_id.equals(kebiao.get(n).Coi_id)){
                                end=Integer.parseInt(kecheng.get(i).Coi_end);
                            }
                        }
                    }else{
                        end=Integer.parseInt(kebiao.get(n).CoA_end);
                    }
                    //获取课程名
                    for(int i=0;i<kecheng.size();i++){
                        if(kecheng.get(i).Coi_id.equals(kebiao.get(n).Coi_id)){
                            classname=kecheng.get(i).Coi_name;
                        }
                    }
                    //规划课程名
                    final ArrayList<String> xinxi=new ArrayList<>();
                    xinxi.add(classname);
                    if(classname.length()>6){
                        classname=classname.substring(0,5)+"...";
                    }
                    //获取上课地点
                    if(kebiao.get(n).CoA_address==null){
                        for(int i=0;i<kecheng.size();i++){

                            if(kecheng.get(i).Coi_id.equals(kebiao.get(n).Coi_id)){
                                address=kecheng.get(i).Coi_address;
                            }
                        }
                    }else{
                        address=kebiao.get(n).CoA_address;
                    }
                    System.out.println(week+":"+start+":"+end);
                    boolean bol=true;



                          xinxi.add(teacher);
                          xinxi.add(String.valueOf(week));
                          xinxi.add(String.valueOf(start));
                          xinxi.add(String.valueOf(end));
                          xinxi.add(address);
                          xinxi.add(zhouci);


                    //第一个值为————行  第二个值为————列
//
                    for (int i = 0;i<7; i++) { //列
                        for (int j = 0;j<tableLayout.getChildCount(); j++) {//行
                            if (i==week&&j>=start&&j<=end){
                                final int c= j*8+i+1;
                                if(bol){
                                    TextView textView;
                                    TextView textView1;
                                    textView1=findViewById(c+8);
                                    textView=findViewById(c);
                                    if(address!="0"&&textView1.getText()==""){
                                        textView1.setText(address);
                                        textView1.setTextSize(11f);
                                        textView1.setTextColor(Color.rgb(255,255,255));
                                        TextPaint tp=textView1.getPaint();
                                        tp.setFakeBoldText(true);
                                    }
                                    textView.setText(classname);
                                    textView.setTextSize(11f);
                                    textView.setTextColor(Color.rgb(255,255,255));
                                    TextPaint tp=textView.getPaint();
                                    tp.setFakeBoldText(true);
                                    bol=false;
                                }

                                ((TableRow) tableLayout.getChildAt(j)).getChildAt(i).setBackgroundColor(Color.rgb(0, 150, 255));
                                ((TableRow) tableLayout.getChildAt(j)).getChildAt(i).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent =new Intent(kebiao_activity.this, coursexq_Activity.class);
                                        intent.putStringArrayListExtra("xinxi",xinxi);
                                        startActivity(intent);
                                    }
                                });
                            }


                        }//j

                    }//i
                }else{

                }

            }
        }

        private void shuabiao(){
            String[] weekly = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
            tableLayout = (TableLayout) findViewById(R.id.table1);
            tableLayout.removeAllViews();
            tableLayout.setStretchAllColumns(true);

            int ci=0;
            for (int i = 1; i <= jie; i++) {

                TableRow tableRow = new TableRow(kebiao_activity.this);
                for (int j = 1; j <= 8; j++) {
                    ci++;
                    TextView text = new TextView(getApplicationContext());
                    text.setGravity(Gravity.CENTER);
                    text.setHeight(150);
                    text.setWidth(50);
                    text.setId(ci);
//                    text.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            System.out.println(v.getId());
//                        }
//                    });


                    if (j == 1 && i != 1) {
                        text.setText(String.valueOf(i - 1));
                    } else if (j > 1 && i == 1 && j < 9) {
                        text.setText(weekly[j - 2]);
                    }
                    tableRow.addView(text);
                }
                //新建的TableRow添加到TableLayout
                tableLayout.addView(tableRow, new TableLayout.LayoutParams(WC, MP,1));
            }





        }
    }
