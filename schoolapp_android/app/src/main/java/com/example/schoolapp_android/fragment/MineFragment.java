package com.example.schoolapp_android.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.schoolapp_android.MainActivity;
import com.example.schoolapp_android.R;
import com.example.schoolapp_android.SettingsActivity;

import htmlservice.check_kick;
import javabean.JavaBean;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {
    String user;
    String pwd;
    private ArrayList<JavaBean> userlist;
    View view;
    ImageView img;
    TextView kick;
    TextView sno;
    TextView school;
    Button btn_Settings;

    public MineFragment() {
        // Required empty public constructor
    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);;
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle bundle = this.getArguments();
        user = bundle.getString("username");
        pwd = bundle.getString("pwd");
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        school = view.findViewById(R.id.user_school);
        sno = view.findViewById(R.id.user_sno);
        kick = view.findViewById(R.id.user_kick);
        img = view.findViewById(R.id.user_avatar);
        new checkkick().execute();
        return view;
    }

    private class checkkick extends AsyncTask<Void, String, ArrayList<JavaBean>> {
        private String url = "http://xxschoolapp.oss-cn-beijing.aliyuncs.com/img/";

        @Override
        protected ArrayList<JavaBean> doInBackground(Void... voids) {
            check_kick a = new check_kick();

            return a.check_kick(user, pwd);
        }

        @Override
        protected void onPostExecute(ArrayList<JavaBean> strings) {
            super.onPostExecute(strings);
            userlist = strings;

            school.setText(userlist.get(0).U_school);
            kick.setText(userlist.get(0).U_kick);
            String snos = userlist.get(0).U_sno;
            if (snos != null) {
                sno.setText(snos);
            } else {
                sno.setText("学号未设定");
            }

            new load_image(img).execute(url + userlist.get(0).U_img);
        }
    }

    Drawable LoadImageFromWebOperations(String url) {
        InputStream is = null;
        Drawable d = null;
        try {
            is = (InputStream) new URL(url).getContent();
            d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    private class load_image extends AsyncTask<String, Void, Drawable> {
        ImageView img;

        load_image(ImageView img) {
            // list all the parameters like in normal class define
            this.img = img;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            Drawable drawable = LoadImageFromWebOperations(params[0]);
            return drawable;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            super.onPostExecute(result);

            img.setImageDrawable(result);
        }
    }
    private void init(){
        btn_Settings=(Button)getActivity().findViewById(R.id.btn_goSettings);
        btn_Settings.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent goSettings = new Intent(getActivity(), SettingsActivity.class);
                startActivity(goSettings);
            }
        });
    }
}
