package com.example.schoolapp_android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    String username;    //TODO:接入用户名
    private ImageButton btn_home,btn_course,btn_life,btn_me;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(),"欢迎回来,"+username,Toast.LENGTH_SHORT).show();    //每次进入主界面时显示(不要在子页面返回时显示)
        viewPager=findViewById(R.id.pager);
        final List<Fragment> fragments=new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CurriculumFragment());
        fragments.add(new LifeFragment());
        fragments.add(new MineFragment());
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetBottom();
                switch (viewPager.getCurrentItem()){
                    case 0:
                        btn_home.setImageResource(R.drawable.main);
                        break;
                    case 1:
                        btn_course.setImageResource(R.drawable.course);
                        break;
                    case 2:
                        btn_life.setImageResource(R.drawable.life);
                        break;
                    case 3:
                        btn_me.setImageResource(R.drawable.me);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btn_home=findViewById(R.id.bot_home);
        btn_course=findViewById(R.id.bot_course);
        btn_life=findViewById(R.id.bot_life);
        btn_me=findViewById(R.id.bot_me);
    }

    public void onClick(View view){ //整合底部按钮单击事件
        resetBottom();
        switch (view.getId()){
            case R.id.bot_home:
                btn_home.setImageResource(R.drawable.main);
                viewPager.setCurrentItem(0);
                break;
            case R.id.bot_course:
                btn_course.setImageResource(R.drawable.course);
                viewPager.setCurrentItem(1);
                break;
            case R.id.bot_life:
                btn_life.setImageResource(R.drawable.life);
                viewPager.setCurrentItem(2);
                break;
            case R.id.bot_me:
                btn_me.setImageResource(R.drawable.me);
                viewPager.setCurrentItem(3);
                break;

        }
    }
    private void resetBottom(){ //重设底部按钮颜色
        btn_home.setImageResource(R.drawable.main_grey);
        btn_course.setImageResource(R.drawable.course_grey);
        btn_life.setImageResource(R.drawable.life_grey);
        btn_me.setImageResource(R.drawable.me_grey);
    }
}
