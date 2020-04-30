package com.example.schoolapp_android.extend;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class LoopViewAdapter extends PagerAdapter {
    private ArrayList<ImageView> imageViewList;
    public LoopViewAdapter(ArrayList<ImageView> mImgList){
        imageViewList = mImgList;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        int newPosition = position % imageViewList.size();
        ImageView img = imageViewList.get(newPosition);
        container.addView(img);
        return img;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        container.removeView((View)object);
    }
    @Override
    public int getCount(){
        return Integer.MAX_VALUE;
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o){
        return  view == o;
    }

}
