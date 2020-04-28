package com.example.schoolapp_android.Son;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.schoolapp_android.R;

public class pagerOnClickListener implements View.OnClickListener {
    Context mContext;
    public pagerOnClickListener(Context mContext){
        this.mContext=mContext;
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.pager_img1:
                //TODO:设定单击图片后重定向
                Toast.makeText(mContext, "图片1被点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pager_img2:
                Toast.makeText(mContext, "图片2被点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pager_img3:
                Toast.makeText(mContext, "图片3被点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pager_img4:
                Toast.makeText(mContext, "图片4被点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pager_img5:
                Toast.makeText(mContext, "图片5被点击", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
