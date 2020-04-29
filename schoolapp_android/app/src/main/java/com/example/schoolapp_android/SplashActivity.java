package com.example.schoolapp_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("isLogin",false)){
            Intent go2main = new Intent(SplashActivity.this,MainActivity.class);
            go2main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(go2main);
        }else{
            Intent go2Login = new Intent(SplashActivity.this,LoginActivity.class);
            go2Login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(go2Login);
        }

        finish();
    }
}
