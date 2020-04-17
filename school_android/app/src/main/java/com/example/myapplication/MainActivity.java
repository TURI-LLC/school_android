package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Typeface;
import android.os.Bundle;

import htmlservice.HttpEntity;
import javabean.JavaBean;
import okhttp3.*;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.*;


import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import htmlservice.HtmlService;
import javabean.JavaBean;

public class MainActivity extends AppCompatActivity {
    private String jsonstr;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.TV_1);
        Typeface font1 = Typeface.createFromAsset(getAssets(), "font/xmPro.ttf");
        tv.setTypeface(font1);
        TextPaint tp = tv.getPaint();
        tp.setFakeBoldText(true);


    }

    public void ceshi2(View v) {


hh2();

    }

    public void hh() {
        HttpEntity httpentity = new HttpEntity(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("123", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                final String json = responseBody.string();
                Log.d("123", json);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(json);
                    }
                });
            }
        });
        httpentity.request();
    }

    public void hh2() {
            HtmlService.sendRequestWithOkhttp("http://123.56.48.182:5000/api/1?id=1", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    ResponseBody resbody= response.body();
                     final String json =resbody.string();
                    Log.d("123", json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson= new Gson();
                            JsonParser jsonParser =new JsonParser();
                            JsonArray jsonArray=jsonParser.parse(json).getAsJsonArray();
                            ArrayList<JavaBean> beans =new ArrayList<>();
                            for(JsonElement bean:jsonArray){
                                JavaBean bean1 =gson.fromJson(bean,JavaBean.class);
                                beans.add(bean1);
                            }
                            System.out.println(beans.size());
                            System.out.println(beans.get(0).name);


                        }
                    });
                }
            });
    }
}


