package com.example.schoolapp_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class RegActivity extends AppCompatActivity {
    private EditText txt_name;
    private EditText txt_pwd;
    private EditText txt_email;
    private EditText txt_phone;
    private ImageButton btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        txt_name = (EditText)findViewById(R.id.txt_user);   //用户名
        txt_pwd = (EditText)findViewById(R.id.txt_pwd); //密码
        txt_email = (EditText)findViewById(R.id.txt_email); //电子邮件地址
        txt_phone = (EditText)findViewById(R.id.txt_phone); //电话号码
        btn_submit = (ImageButton)findViewById(R.id.btn_Submit);//提交按钮
        //TODO:从登录界面获取用户名
        btn_submit.setClickable(false); //副视图的xml不知为何属性设置无效,故在此再次声明属性.
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
    }

    public void btnSubmit_onClick(View view){
        dialog_cfm();
    }

    public void dialog_cfm(){
        boolean conditionA = !txt_email.getText().toString().isEmpty(), conditionB=!txt_phone.getText().toString().isEmpty();
        String context = "您的用户名: " + txt_name.getText().toString().trim();
        context+="\n您的密码: " + txt_name.getText().toString();
        if(conditionA){ context +="\n您的邮件地址: "+txt_email.getText().toString(); }
        if(conditionB){ context +="\n您的手机号码: "+txt_phone.getText().toString(); }
        context+="\n________________________________________";
        if(conditionA==conditionB){ context+="\n\n* 请注意:您没有提供任何额外信息,这会降低找回账号的成功率."; }
        context +="\n\n"+getString(R.string.need_choose);

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
    public void textWatchR(){   //文本框监视
        if(txt_pwd.getText().toString().isEmpty()||txt_name.getText().toString().trim().isEmpty()){ //如果用户和密码输入框其中任一没有内容,禁用提交按钮
            btn_submit.setClickable(false);
            btn_submit.setBackground(getDrawable(R.drawable.btn_next_gray));
        } else {    //如果有内容,启用提交按钮
            btn_submit.setClickable(true);
            btn_submit.setBackground(getDrawable(R.drawable.btn_next));
        }
    }
    private void start_reg(){   //注册
        ProgressDialog loading = new ProgressDialog(RegActivity.this);
        loading.setTitle("正在注册...");
        loading.setMessage("请稍等,\n正在与服务器通讯.");
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.show();

        //TODO:向服务器用户表添加一条记录(要将邮件字符串toLowerCase();)
        if(true){   //如果注册成功,跳转至主界面
            Intent go2main = new Intent(this,MainActivity.class);
            go2main.putExtra("username",txt_name.getText().toString());  //为主界面传送用户信息
            startActivity(go2main);
        }else{
            //TODO:向用户提供错误信息
        }

        loading.dismiss();
        //TODO:清空所有文本框,以备下一次注册.
    }
}
