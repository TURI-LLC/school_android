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

import java.util.ArrayList;

import htmlservice.check_circle;
import javabean.JavaBean;


/**
 * A simple {@link Fragment} subclass.
 */
public class LifeFragment extends Fragment {
    private ArrayList<JavaBean> ciclelist;
    private View view;
    private String user;
    private String pwd;
    public LifeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle =this.getArguments();
        user=bundle.getString("username");
        pwd =bundle.getString("pwd");
        view = inflater.inflate(R.layout.fragment_life, container, false);
        new thread_valiUser().execute();
        return  view;
    }


    private class thread_valiUser extends AsyncTask<Void,String, ArrayList<JavaBean>> {

        @Override
        protected ArrayList<JavaBean> doInBackground(Void... voids) {
            check_circle a=new check_circle();

            return a.check_quanzi(user);

        }

        @Override
        protected void onPostExecute(ArrayList<JavaBean> list) {
            super.onPostExecute(list);
            ciclelist=list;
            RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.quanzi_list);
            //
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            //news recyclerview适配器
            circleAdapter adapter = new circleAdapter(ciclelist);
            //取消自带的滚动
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(adapter);//设置适配器

            //取消自带的滚动
//            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1){
//                @Override
//                public boolean canScrollVertically() {
//                    return false;
//                }
//            });



        }
    }


}
