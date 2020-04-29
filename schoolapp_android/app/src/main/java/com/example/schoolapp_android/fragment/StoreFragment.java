package com.example.schoolapp_android.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.schoolapp_android.R;
import com.example.schoolapp_android.Son.OrderActivity;
import com.example.schoolapp_android.extend.StoreAdapter;
import com.example.schoolapp_android.extend.newsAdapter;

import java.util.ArrayList;
import java.util.List;

import htmlservice.check_store;
import javabean.JavaBean;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {
    private List<JavaBean>  Storelist = new ArrayList<>();
    private View view;
    private String user;
    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle bundle =this.getArguments();
         user=bundle.getString("username");

            new thread_valiUser().execute();


        view = inflater.inflate(R.layout.fragment_store,container, false);
        return view;
    }
    private class thread_valiUser extends AsyncTask<Void,String, ArrayList<JavaBean>> {

        @Override
        protected ArrayList<JavaBean> doInBackground(Void... voids) {
            check_store a=new check_store();

            return a.check_store(user);

        }

        @Override
        protected void onPostExecute(ArrayList<JavaBean> list) {
            super.onPostExecute(list);
            Storelist=list;
            RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.store_list);
            //
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            //news recyclerview适配器
            StoreAdapter adapter = new StoreAdapter(Storelist);
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


        }
    }
}

