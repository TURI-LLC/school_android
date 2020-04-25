package com.example.schoolapp_android.fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.schoolapp_android.R;
import com.example.schoolapp_android.extend.circleAdapter;

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
    private Button btn_Moment,btn_wanted,btn_PartJob;
    private int type=0;
    public LifeFragment() {}// Required empty public constructor

    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        init();
        listen();
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

            return a.check_quanzi(user,type);

        }

        @Override
        protected void onPostExecute(ArrayList<JavaBean> list) {
            super.onPostExecute(list);
            ciclelist=list;
            if(ciclelist.get(0).Ci_id!=null){
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
            }else {
                RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.quanzi_list);
                //
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                //news recyclerview适配器
                ArrayList<JavaBean> listnull=new ArrayList<>();
                circleAdapter adapter = new circleAdapter(listnull);
                //取消自带的滚动

                recyclerView.setHasFixedSize(true);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setAdapter(adapter);//设置适配器
            }


            //取消自带的滚动
//            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1){
//                @Override
//                public boolean canScrollVertically() {
//                    return false;
//                }
//            });



        }
    }

    private void init(){
        btn_Moment=(Button) getActivity().findViewById(R.id.btn_moment);
        btn_Moment.setTextSize(20);
        btn_Moment.setTextColor(Color.BLACK);   //xml中设置的属性失效,故在初始化时再设定.
        btn_wanted=(Button) getActivity().findViewById(R.id.btn_wanted);
        btn_PartJob=(Button) getActivity().findViewById(R.id.btn_partJob);
    }
    public void btnLife_onClick(View view){
        btn_Moment.setTextSize(17);
        btn_wanted.setTextSize(17);
        btn_PartJob.setTextSize(17);
        btn_Moment.setTextColor(Color.GRAY);
        btn_wanted.setTextColor(Color.GRAY);
        btn_PartJob.setTextColor(Color.GRAY);
    }
    private void listen(){
        btn_Moment.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                btnLife_onClick(view);
                btn_Moment.setTextSize(20);
                btn_Moment.setTextColor(Color.BLACK);
                type=0;
                new thread_valiUser().execute();
            }
        });
        btn_wanted.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                btnLife_onClick(view);
                btn_wanted.setTextSize(20);
                btn_wanted.setTextColor(Color.BLACK);
                type=2;
                new thread_valiUser().execute();
            }
        });
        btn_PartJob.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                btnLife_onClick(view);
                btn_PartJob.setTextSize(20);
                btn_PartJob.setTextColor(Color.BLACK);
                type=3;
                new thread_valiUser().execute();
            }
        });
    }
}
