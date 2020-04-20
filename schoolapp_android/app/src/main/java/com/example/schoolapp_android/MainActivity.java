package com.example.schoolapp_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    String username;    //TODO:接入用户名
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(),"欢迎回来,"+username,Toast.LENGTH_SHORT).show();    //每次进入主界面时显示(不要在子页面返回时显示)
        viewPager=findViewById(R.id.demo);
        final List<Fragment> fragments=new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CurriculumFragment());
        fragments.add(new LifeFragment());
        fragments.add(new MineFragment());
        final String[] strings={"首页","课表","生活","我的"};
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return strings[position];
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void home(View view){
        viewPager.setCurrentItem(0,false);
    }
    public void course(View view){
        viewPager.setCurrentItem(1,false);
    }
    public void life(View view){
        viewPager.setCurrentItem(2,false);
    }
    public void mine(View view){ viewPager.setCurrentItem(3,false);}
}
