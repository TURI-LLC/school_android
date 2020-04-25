package com.example.schoolapp_android.Son;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.schoolapp_android.R;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class news_pager extends AppCompatActivity {
    TextView classname;
    TextView createby;
    TextView text;
    TextView title;
    TextView date;
    TextView like;
    TextView comment;

    private String url="http://xxschoolapp.oss-cn-beijing.aliyuncs.com/img/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_pager);
        Intent intent =getIntent();

        ArrayList<String> news =new ArrayList<>();
        System.out.println("创建了：");
         news=intent.getStringArrayListExtra("list");
        System.out.println("录入了："+news.size());
        classname=findViewById(R.id.news_pager_class);
        createby=findViewById(R.id.news_pager_name);
        text=findViewById(R.id.news_pager_text);
        title=findViewById(R.id.news_pager_title);
        date=findViewById(R.id.news_pager_date);
        like=findViewById(R.id.news_pager_like);
        comment=findViewById(R.id.news_pager_comment);
        createby.setText(news.get(0));
        title.setText(news.get(1));
        text.setText(news.get(2));
        classname.setText(news.get(3));
        date.setText(news.get(4));
        String likestr=news.get(5);
        if(likestr==null){likestr="0";}
        else{
            String likes[]=likestr.split("[;]");
            likestr =String.valueOf(likes.length);
        }

        String commentstr=news.get(6);
        if(commentstr==null){commentstr="0";}
        else{
            String comments[]=commentstr.split("[;]");
            commentstr =String.valueOf(comments.length);
        }

        like.setText(likestr);
        comment.setText(likestr);
        String img =news.get(7);
        ArrayList<String> imgs=new ArrayList<>();
        if(img==null){img="0";}
        else{

            String im[]=img.split("[;]");
            for(int i=0;i<im.length;i++){
                imgs.add(im[i]);
            }
        }
        LinearLayout linearLayout=findViewById(R.id.ll_group);
        for (int i = 0; i < imgs.size(); i++) {


            ImageView  imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));  //设置图片宽高
            imageView.setId(i);


            new load_image(imageView,false,i,linearLayout).execute(url+imgs.get(i));

        }

    }

    public void back(View view){
        this.finish();
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

    class load_image extends AsyncTask<String, Void, Drawable> {

        ImageView img;
        boolean t=true;
        int index;
        LinearLayout llGroup;
        load_image(ImageView img,boolean t,int index, LinearLayout llGroup){
            this.t=t;
            this.img = img;
            this.index=index;
            this.llGroup=llGroup;
        }
        @Override
        protected Drawable doInBackground(String... params) {
            Drawable drawable = LoadImageFromWebOperations(params[0]);
            return drawable;
        }
        @Override
        protected void onPostExecute(Drawable result) {
            super.onPostExecute(result);
            img.setImageDrawable(result); //图片资源
            llGroup.addView(img,index);

        }

    }

}
