package com.example.schoolapp_android.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.schoolapp_android.R;
import com.example.schoolapp_android.Son.LoopViewAdapter;
import com.example.schoolapp_android.Son.kebiao_activity;
import com.example.schoolapp_android.Son.news_pager;
import com.example.schoolapp_android.Son.pagerOnClickListener;
import com.example.schoolapp_android.extend.newsAdapter;

import java.util.ArrayList;
import java.util.List;
import htmlservice.check_news;
import javabean.JavaBean;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    //图片轮播所用对象
    private ViewPager viewPager;
    private int[] mImg;
    private int[] mImg_id;
    private String[] mDec;
    private ArrayList<ImageView> mImgList;
    private LinearLayout ll_dots_container;
    private TextView loop_dec;
    private  int previousSelectedPosition = 0;
    boolean isRunning = false;
    //图片轮播所用对象 结束.

    private List<JavaBean> newslist = new ArrayList<>();
    private View view;
    Context context;
    private String username;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home ,container, false);
        ImageView imageView=view.findViewById(R.id.kechengbiao);
        Bundle bundle =this.getArguments();
        username=bundle.getString("username");
        //课表页——————————————————
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), kebiao_activity.class);
                intent.putExtra("user",username);
                startActivity(intent);
            }
        });

        initLoopView();

        new thread_valiUser().execute();
        context=this.getContext();

        return view;
    }

    private void initLoopView(){    //初始化轮播图
        viewPager = (ViewPager) view.findViewById(R.id.loopviewpager);
        ll_dots_container = (LinearLayout) view.findViewById(R.id.ll_dots_loop);
        loop_dec = (TextView) view.findViewById(R.id.loop_dec);

        mImg = new int[]{
          //TODO:此处填充5张轮播用图片
                R.drawable.act1,
                R.drawable.act2,
                R.drawable.act3,
                R.drawable.act4,
                R.drawable.act5
        };
        mDec = new String[]{
          //TODO:此处填充面向5张轮播图片的标题
                "标题1",
                "标题2",
                "标题3",
                "标题4",
                "标题5"
        };
        mImg_id = new int[]{
                R.id.pager_img1,
                R.id.pager_img2,
                R.id.pager_img3,
                R.id.pager_img4,
                R.id.pager_img5
        };

        mImgList = new ArrayList<ImageView>();
        ImageView imageView;
        View dotView;
        LinearLayout.LayoutParams layoutParams;
        for(int i=0; i<mImg.length; i++){
            imageView = new ImageView(getContext());
            imageView.setBackgroundResource(mImg[i]);
            imageView.setId(mImg_id[i]);
            imageView.setOnClickListener(new pagerOnClickListener(getContext().getApplicationContext()));
            mImgList.add(imageView);
            dotView = new View(getContext());
            dotView.setBackgroundResource(R.drawable.dot);
            layoutParams = new LinearLayout.LayoutParams(10,10);
            if(i!=0){
                layoutParams.leftMargin=10;
            }
            dotView.setEnabled(false);
            ll_dots_container.addView(dotView,layoutParams);
        }
        ll_dots_container.getChildAt(0).setEnabled(true);
        loop_dec.setText(mDec[0]);
        previousSelectedPosition=0;
        viewPager.setAdapter(new LoopViewAdapter(mImgList));
        int m = (Integer.MAX_VALUE /2)%mImgList.size();
        int currentPosition = Integer.MAX_VALUE /2 -m;
        viewPager.setCurrentItem(currentPosition);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int i, float v, int i1){ }
            @Override
            public void onPageSelected(int i){
                int newPosition = i % mImgList.size();
                loop_dec.setText(mDec[newPosition]);
                ll_dots_container.getChildAt(previousSelectedPosition).setEnabled(false);
                ll_dots_container.getChildAt(newPosition).setEnabled(true);
                previousSelectedPosition = newPosition;
            }
            @Override
            public void onPageScrollStateChanged(int i){ }
        });
        new Thread(){
            public void run(){
                isRunning = true;
                while (isRunning){
                    try {
                        Thread.sleep(5000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }.start();
    }

    private class thread_valiUser extends AsyncTask<Void,String,ArrayList<JavaBean>> {
        @Override
        protected ArrayList<JavaBean> doInBackground(Void... params) {
//            try{ Thread.sleep(2000); }catch (Exception e){e.printStackTrace();}   //仅用于测试进度条动画显示情况
            check_news a=new check_news();

            return a.ccheck_news();
        }




        protected void onPostExecute(ArrayList<JavaBean> list){
            //任务完成
            newslist=list;
            if(list.get(0).id!=null){
            return;
            }
            RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
            //
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            //news recyclerview适配器
            newsAdapter adapter = new newsAdapter(newslist);
            //取消自带的滚动
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(adapter);//设置适配器

            //取消自带的滚动
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });

            //————单击事件————————
            adapter.setOnItemClickListener(new newsAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {

                    Intent newspager = new Intent(context, news_pager.class);

                    ArrayList<String> news=new ArrayList<>();
                    news.add(newslist.get(position).N_createBy);//0
                    news.add(newslist.get(position).N_name);//1
                    news.add(newslist.get(position).N_text);//2
                    news.add(newslist.get(position).N_classCName);//3
                    String time =newslist.get(position).N_Time.substring(0,10);
                    news.add(time);//4
                    news.add(newslist.get(position).N_like);//5
                    news.add(newslist.get(position).N_comment);//6
                    news.add(newslist.get(position).N_naviPic);
                    newspager.putExtra("list",news);
                    startActivity(newspager);
                }
            });

//        //长按事件————————————
//        adapter.setOnItemLongClickListener(new newsAdapter.OnItemLongClickListener() {
//            @Override
//            public void onClick(int position) {
//                System.out.println("long"+position);
//            }
//        });
        }
    }




    }

