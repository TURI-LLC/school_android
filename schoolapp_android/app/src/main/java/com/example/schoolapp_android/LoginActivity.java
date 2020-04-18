package com.example.schoolapp_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private EditText txt_usr;
    private TextInputLayout til_usr;
    private TextInputLayout til_pwd;
    private EditText txt_pwd;
    private ProgressBar con_prg;
    private ImageButton btn_cls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txt_usr = (EditText)findViewById(R.id.txt_user);
        til_usr = (TextInputLayout)findViewById(R.id.til_pwd);
        til_pwd = (TextInputLayout)findViewById(R.id.til_pwd);
        txt_pwd = (EditText)findViewById(R.id.txt_pwd);
        con_prg = (ProgressBar)findViewById(R.id.connection_prg);
        txt_usr.addTextChangedListener(new TextWatcher(){   //设置监听器
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                btn_cls = (ImageButton)findViewById(R.id.btn_clr1);
                if(!txt_usr.getText().toString().isEmpty()){
                    btn_cls.setVisibility(View.VISIBLE);
                    til_pwd.setVisibility(View.VISIBLE);
                } else {
                    btn_cls.setVisibility((View.INVISIBLE));
                    til_pwd.setVisibility(View.GONE);
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
//        if(txt_usr.getText().toString().isEmpty()){
//            //TODO:如果用户没有输入,屏幕下方弹出警告
//        }else{
//            //TODO:如果有用户输入,开始查找用户
//            txt_usr.setEnabled(false);//通讯时禁用用户名输入框
//            btn_cls.setEnabled(false);//通讯时禁用清除按钮
//            if(true){  //TODO:补充判断条件(查找数据库中学号,手机号,邮箱字段)
//                til_pwd.setVisibility(View.VISIBLE);    //显示密码框
//            }else{
//                //TODO:如果未找到用户,询问是否要创建用户
//
//                txt_usr.setEnabled(true);   //开放用户名输入框
//            }
//        }
//        btn_cls.setEnabled(true); //通讯完成后启用清除按钮
//        con_prg.setVisibility(View.GONE);   //通讯完成后隐藏进度条
    }
    public void btnReg_onClick(View view){
        //跳转到注册页面
    }
}
