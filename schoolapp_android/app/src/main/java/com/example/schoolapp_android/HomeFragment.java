package com.example.schoolapp_android;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import htmlservice.check_news;
import htmlservice.check_user;
import javabean.JavaBean;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private List<JavaBean> newslist = new ArrayList<>();
    private View view;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        view = inflater.inflate(R.layout.fragment_home ,container, false);
        new thread_valiUser().execute();


        return view;
    }
    private class thread_valiUser extends AsyncTask<Void,String,ArrayList<JavaBean>> {
        @Override
        protected ArrayList<JavaBean> doInBackground(Void... params) {
//            try{ Thread.sleep(2000); }catch (Exception e){e.printStackTrace();}   //测试进度条动画显示情况
            check_news a=new check_news();

            return a.ccheck_news();
        }

        protected void onPostExecute(ArrayList<JavaBean> list){
            //任务完成
            newslist=list;
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
                    System.out.println(position);
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

