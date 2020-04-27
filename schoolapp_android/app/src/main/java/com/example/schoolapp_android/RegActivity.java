package com.example.schoolapp_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import javabean.*;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import htmlservice.*;

public class RegActivity extends AppCompatActivity {
    private EditText txt_name;
    private EditText txt_pwd;
    private EditText txt_school;
    private ImageButton btn_submit;
    private Spinner spinner;
    ArrayList<String> list =new ArrayList<>();
    private Context context;
    private ArrayAdapter<String> arr_adapter;
    //ProgressDialog loading = new ProgressDialog(RegActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        txt_name = (EditText)findViewById(R.id.txt_user);   //用户名
        txt_pwd = (EditText)findViewById(R.id.txt_pwd); //密码
        txt_school = (EditText)findViewById(R.id.txt_school); //电子邮件地址
        btn_submit = (ImageButton)findViewById(R.id.btn_Submit);//提交按钮
        context = this;
        btn_submit.setClickable(false); //副视图的xml不知为何属性设置无效,故在此再次声明属性.
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        txt_name.addTextChangedListener(new TextWatcher(){   //设置用户文本框监听器
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){ textWatchR(); }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        txt_pwd.addTextChangedListener(new TextWatcher() { //设置密码框监听器
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { textWatchR(); }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        txt_school.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textWatchR();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    protected void onStart() {
        super.onStart();
        //下拉框
        Setspinner a=new Setspinner();
        a.execute();
    }
    public void btnSubmit_onClick(View view){
        String err_msg;
        switch (new field_check().field_check(list,txt_school.getText().toString(),txt_name.getText().toString(),txt_pwd.getText().toString())){
            case 0:
                go_dialog();
                break;
            case 1:
                err_msg = "学校不存在或不支持,请检查.";
                snackBar_err(view,err_msg);
                break;
            case 2:
                err_msg = "密码至少需要6个字符,请再复杂一点^_^";
                snackBar_err(view,err_msg);
                break;
            case 3:
                err_msg = "用户名不符合规范,请满足以字母开头,至少6位且不含除下划线以外的字符的要求.";
                snackBar_err(view,err_msg);
                break;
        }
    }
    private void go_dialog(){
        //输出确认注册信息至对话框
        String context ="您的学校地址: "+txt_school.getText().toString();
        context += "\n您的用户名: " + txt_name.getText().toString().trim();
        context+="\n您的密码: " + txt_pwd.getText().toString();
        context+="\n________________________________________";
//      if(conditionA&&conditionB){ context+="\n\n* 请注意:您没有提供任何额外信息,这会降低找回账号的成功率."; } :(
        context +="\n\n"+getString(R.string.need_choose);
        //调用确认对话框
        confirmDialog(context);
    }
    private void textWatchR(){   //文本框监视,由文本框监听器进行外部调用.
        boolean condition = txt_pwd.getText().toString().isEmpty()|txt_name.getText().toString().trim().isEmpty()||txt_school.getText().toString().isEmpty();
        if(condition){
            //如果学校,用户,密码输入框任意其一没有内容,禁用提交按钮
            btn_submit.setClickable(false);
            btn_submit.setBackground(getDrawable(R.drawable.btn_next_gray));
        } else {
            //如果有内容,启用提交按钮
            btn_submit.setClickable(true);
            btn_submit.setBackground(getDrawable(R.drawable.btn_next));
        }
    }
    private void start_reg(){   //注册
        register a=new register();
        a.execute();

    }
    private void confirmDialog(String context){     //确认对话框
        final AlertDialog.Builder confirmDialog = new AlertDialog.Builder(RegActivity.this);
        confirmDialog.setIcon(R.drawable.ic_info);
        confirmDialog.setTitle(getString(R.string.request_confirm));
        confirmDialog.setMessage(context);
        confirmDialog.setPositiveButton(getString(R.string.agree),new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which){
                start_reg();
            }
        });
        confirmDialog.setNegativeButton(getString(R.string.negative), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        confirmDialog.show();
    }
    private void snackBar_err(View view,String err_msg){   //错误消息栏
        Snackbar.make(view,err_msg,Snackbar.LENGTH_SHORT)
                .setAction("重置",new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        reset();
                    }
                })
                .show();
    }
    public void reset(){    //重置
//        txt_name.setText("");
//        txt_pwd.setText("");
//        txt_email.setText("");
//        txt_phone.setText("");
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private class Setspinner extends AsyncTask<Void,String,Boolean> {
        //下拉框异步更新
        @Override
        protected void onPreExecute(){
            //任务之前
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            //异步耗时任务
            check_school a=new check_school();
            list=a.checkschool();
            return true;
        }
        protected void onPostExecute(Boolean b){

            //任务完成
            spinner = (Spinner) findViewById(R.id.spinner);
            arr_adapter= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
            //设置样式
            arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //加载适配器
            spinner.setAdapter(arr_adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String str=(String)spinner.getSelectedItem();
                    TextInputEditText txtschool =(TextInputEditText) findViewById(R.id.txt_school);
                    txtschool.setText(str);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

    }
    private class register extends AsyncTask<Void,String,Boolean> {
        //下拉框异步更新
        String school;
        String id;
        String pwd;
        @Override
        protected void onPreExecute(){
            //任务之前
             school=txt_school.getText().toString();
            id =txt_name.getText().toString();
             pwd =txt_pwd.getText().toString();
//            loading.setTitle("正在注册...");
//            loading.setMessage("请稍等,\n我们正在与服务器通讯.");
//            loading.setIndeterminate(true);
//            loading.setCancelable(false);
//            loading.show();
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            //异步耗时任务

            register_user a=new register_user();

            if(a.register(id,pwd,school)){
             return true;
            }else{
                return false;
            }
        }
        protected void onPostExecute(Boolean b){

            //任务完成
//            loading.dismiss();
            if(b){
                Intent go2main = new Intent(context,MainActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLogin",true);
                editor.putString("username",txt_name.getText().toString());
                editor.commit();
                startActivity(go2main);
            }else{
                String err_msg="发生错误,请稍后再试.";//TODO:此处改为传值注册失败消息
                snackBar_err(findViewById(R.id.reg_view),err_msg);  //向用户提供错误信息
            }

        }

    }
}
