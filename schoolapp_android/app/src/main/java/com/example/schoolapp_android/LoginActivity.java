package com.example.schoolapp_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import htmlservice.*;
public class LoginActivity extends AppCompatActivity {
    private EditText txt_usr;
    private TextInputLayout til_usr;
    private TextInputLayout til_pwd;
    private EditText txt_pwd;
    private ProgressBar con_prg,con_prg2;
    private ImageButton btn_cls;
    private ImageButton btn_next;
    private View view;
    private Context ct;
    String user,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txt_usr = (EditText)findViewById(R.id.txt_user);    //用户名输入框
        btn_next = (ImageButton)findViewById(R.id.btn_next);    //下一步按钮
        btn_next.setClickable(false); //副视图的xml不知为何属性设置无效,故在此再次声明属性.
        con_prg = (ProgressBar)findViewById(R.id.connection_prg);   //进度条动画
        con_prg2 = (ProgressBar)findViewById(R.id.connection_prg2);
        txt_pwd = (EditText)findViewById(R.id.txt_pwd); //密码输入框
        til_usr = (TextInputLayout)findViewById(R.id.til_pwd);  //用户名输入框架
        til_pwd = (TextInputLayout)findViewById(R.id.til_pwd);  //密码输入框架
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        txt_usr.addTextChangedListener(new TextWatcher(){   //设置用户文本框监听器
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                btn_cls = (ImageButton)findViewById(R.id.btn_clr1);
                if(txt_usr.getText().toString().isEmpty()){ //如果用户输入框没有内容,禁用下一步按钮,隐藏清除按钮
                    btn_cls.setVisibility(View.INVISIBLE);
                    btn_next.setClickable(false);
                    btn_next.setBackground(getDrawable(R.drawable.btn_next_gray));
                    til_pwd.setVisibility(View.GONE);
                } else {    //如果有内容,显示清除按钮,启用下一步按钮
                    btn_cls.setVisibility((View.VISIBLE));
                    btn_next.setClickable(true);
                    btn_next.setBackground(getDrawable(R.drawable.btn_next));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        txt_usr.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                try {
                    btnNext_onClick(findViewById(R.id.login_view));  //当按下软键盘的完成按钮时,调用下一步按钮单击事件
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

        TextView lbl_Title = (TextView)findViewById(R.id.TitleText);
        TextView lbl_zen = (TextView)findViewById(R.id.lbl_zenText);
        Typeface oppoSans_b = Typeface.createFromAsset(getAssets(),"OPPOSans-B.ttf");
        lbl_Title.setTypeface(oppoSans_b);
        lbl_zen.setTypeface(oppoSans_b);


    }

    public void btnClr_onClick(View view){  //清除按钮单击事件
        txt_usr.setText("");
        txt_usr.setEnabled(true);
        txt_pwd.setEnabled(true);

    }
    public void btnNext_onClick(View view) throws InterruptedException {    //下一步按钮单击事件
        long startTime=System.currentTimeMillis();  //用于测算通讯时间
        btn_next.setClickable(false); btn_next.setBackground(getDrawable(R.drawable.btn_next_gray));    //暂时禁用登录按钮
        txt_usr.setText(txt_usr.getText().toString().trim());   //去除空格
        user = txt_usr.getText().toString();
        con_prg.setVisibility(View.VISIBLE);
        this.view=view;//传入view
        ct = LoginActivity.this;//传入本体
        if(til_pwd.getVisibility()==View.GONE){ //如果密码框还未显示,则先查找用户
            txt_pwd.setText("");    //清空上一次输入的密码

            if(!txt_usr.getText().toString().isEmpty()) {
                //如果用户名输入框有内容,开始通讯
                txt_usr.setEnabled(false);  //通讯时禁用用户名输入框
                btn_cls.setEnabled(true);  //通讯时禁用清除按钮

                //调用子线程查询用户,避免UI线程等待
                new thread_valiUser().execute();
            }
        }else{  //否则,验证密码
            txt_pwd.setEnabled(false);
            con_prg2.setVisibility(View.VISIBLE);
            pwd = txt_pwd.getText().toString();
            //调用子线程验证密码
            new thread_valiPwd().execute();
        }
        //通讯完成后重新开放控件
        long endTime=System.currentTimeMillis(); System.out.println(endTime-startTime);  //测算通讯耗时
        btn_cls.setEnabled(true);
        btn_next.setClickable(true);
        btn_next.setBackground(getDrawable(R.drawable.btn_next));
    }
    public void btnReg_onClick(View view){  //注册按钮单击事件
        //跳转到注册页面
        Intent go2reg = new Intent(this,RegActivity.class);
        go2reg.putExtra("username",txt_usr.getText().toString());   //自动为用户填充注册UI的用户名(从登录UI
        startActivity(go2reg);
    }

    private class thread_valiUser extends AsyncTask<Void,String,Boolean>{
        @Override
        protected Boolean doInBackground(Void... params) {
//            try{ Thread.sleep(2000); }catch (Exception e){e.printStackTrace();}   //测试进度条动画显示情况
            check_user check_user = new check_user();
                if (check_user.check_user(user)) {  //查找数据库中用户名,手机号,邮箱字段是否成功的结果
                    return true;
                }else{
                    return false;   //非ui线程
                }
        }

        protected void onPostExecute(Boolean b){
            //任务完成
            if(b){
                    til_pwd.setVisibility(View.VISIBLE);
            }else{
                    Snackbar.make(view,"我们找不到这个用户的信息,请检查一下拼写.\n此外,如果您是新用户,请单击\"注册\"按钮.",Snackbar.LENGTH_LONG)
                            .setAction("注册",new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    btnReg_onClick(v);
                                }
                            })
                            .show();
                txt_usr.setEnabled(true);   //开放用户名输入框
            }
            con_prg.setVisibility(View.GONE);
        }
    }

    private class thread_valiPwd extends AsyncTask<Void,String,Boolean>{
        @Override
        protected Boolean doInBackground(Void... params) {
            //异步耗时任务
            login_class login=new login_class();

                if (login.loginjosn(user,pwd)) {  //判断帐号密码
                    return true;
                }else{
                    return false;   //非ui线程
                }
        }

        protected void onPostExecute(Boolean b){
            //任务完成
            if(b){
                SharedPreferences sharedPreferences = getSharedPreferences("settings",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Intent go2main = new Intent(ct,MainActivity.class);
                editor.putBoolean("isLogin",true);
                editor.putString("username",user);
                editor.putString("pwd",pwd);
                editor.commit();
                startActivity(go2main);
                finish();
            }else{
                Snackbar.make(view,"帐号或者密码错误.",Snackbar.LENGTH_LONG).show();
                txt_usr.setEnabled(true);   //开放用户名输入框
                txt_pwd.setEnabled(true);
            }
            con_prg.setVisibility(View.GONE);
            con_prg2.setVisibility(View.GONE);
        }
    }
}