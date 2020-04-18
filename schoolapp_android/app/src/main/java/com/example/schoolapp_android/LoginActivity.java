package com.example.schoolapp_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private EditText txt_usr;
    private TextInputLayout til_usr;
    private TextInputLayout til_pwd;
    private EditText txt_pwd;
    private ProgressBar con_prg;
    private ImageButton btn_cls;
    private ImageButton btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txt_usr = (EditText)findViewById(R.id.txt_user);    //用户名输入框
        btn_next = (ImageButton)findViewById(R.id.btn_next);    //下一步按钮
        con_prg = (ProgressBar)findViewById(R.id.connection_prg);   //进度条动画
        txt_pwd = (EditText)findViewById(R.id.txt_pwd); //密码输入框
        til_usr = (TextInputLayout)findViewById(R.id.til_pwd);  //用户名输入框架
        til_pwd = (TextInputLayout)findViewById(R.id.til_pwd);  //密码输入框架

        txt_usr.addTextChangedListener(new TextWatcher(){   //设置用户文本框监听器
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                btn_cls = (ImageButton)findViewById(R.id.btn_clr1);
                if(txt_usr.getText().toString().isEmpty()){ //如果用户输入框没有内容,禁用下一步按钮,隐藏清除按钮
                    btn_cls.setVisibility(View.INVISIBLE);
                    btn_next.setClickable(false);
                    btn_next.setBackground(getDrawable(R.drawable.btn_next_gray));
                } else {    //如果有内容,显示清除按钮,启用下一步按钮
                    btn_cls.setVisibility((View.VISIBLE));
                    btn_next.setClickable(true);
                    btn_next.setBackground(getDrawable(R.drawable.btn_next));
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        TextView lbl_Title = (TextView)findViewById(R.id.TitleText);
        TextView lbl_zen = (TextView)findViewById(R.id.lbl_zenText);
        Typeface oppoSans_b = Typeface.createFromAsset(getAssets(),"OPPOSans-B.ttf");
        lbl_Title.setTypeface(oppoSans_b);
        lbl_zen.setTypeface(oppoSans_b);


    }

    public void btnClr_onClick(View view){  //清除按钮
        txt_usr.setText("");
        til_pwd.setVisibility(View.GONE);
        txt_usr.setEnabled(true);

    }
    public void btnNext_onClick(View view) {    //下一步按钮

        con_prg.setVisibility(View.VISIBLE);
        txt_usr.setText(txt_usr.getText().toString().trim());   //去除空格
        if(til_pwd.getVisibility()==View.GONE){ //如果密码框还未显示,则先查找用户
            txt_pwd.setText("");    //清空上一次输入的密码
            if(!txt_usr.getText().toString().isEmpty()){
                //TODO:如果用户名输入框有内容,开始查找用户
                txt_usr.setEnabled(false);//通讯时禁用用户名输入框
                btn_cls.setEnabled(false);//通讯时禁用清除按钮
                if(true){  //TODO:查找数据库中用户名,手机号,邮箱字段
                    til_pwd.setVisibility(View.VISIBLE);    //显示密码框
                }else{
                    //TODO:提示未找到用户
                    txt_usr.setEnabled(true);   //开放用户名输入框
                }
            }
        }else{  //否则,验证密码
            if(true){   //TODO:验证密码
                //跳转到主界面
                Intent go2main = new Intent(this,MainActivity.class);
                go2main.putExtra("username",txt_usr.getText().toString());  //为主界面传送用户信息
                startActivity(go2main);
            }
        }
        btn_cls.setEnabled(true); //通讯完成后启用清除按钮
        con_prg.setVisibility(View.GONE);   //通讯完成后隐藏进度条
    }
    public void btnReg_onClick(View view){
        //跳转到注册页面
        Intent go2reg = new Intent(this,RegActivity.class);
        go2reg.putExtra("username",txt_usr.getText().toString());   //自动为用户填充注册UI的用户名(从登录UI
        startActivity(go2reg);
    }
}
