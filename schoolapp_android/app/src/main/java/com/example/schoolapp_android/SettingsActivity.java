package com.example.schoolapp_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {
    Button btn_Logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btn_Logout = findViewById(R.id.btn_logout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("设置");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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

    public void btnLogout_onClick(View view){
        final AlertDialog.Builder logoutDialog = new AlertDialog.Builder(SettingsActivity.this);
        logoutDialog.setIcon(R.drawable.ic_warning);
        logoutDialog.setTitle("警告");
        logoutDialog.setMessage("您确定要退出吗?\n您的账号信息仍会保留,但会被要求重新登录.");
        logoutDialog.setPositiveButton("登出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //反向登录工程
                Intent logout = new Intent(SettingsActivity.this,LoginActivity.class);
                logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLogin",false);
                editor.putString("username",null);
                editor.putString("pwd",null);
                editor.commit();
                startActivity(logout);
            }
        });
        logoutDialog.setNegativeButton("手滑了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        logoutDialog.show();
    }

    public void btnAbout_onClick(View view) {
        Intent go2About = new Intent(this,AboutActivity.class);
        startActivity(go2About);
    }
}
