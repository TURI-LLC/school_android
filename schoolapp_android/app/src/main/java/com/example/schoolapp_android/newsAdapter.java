package com.example.schoolapp_android;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

import javabean.JavaBean;


public class newsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<JavaBean> newsList;
    private String url="http://xxschoolapp.oss-cn-beijing.aliyuncs.com/img/";


    //TODO: 创建recyclerview——————————定义接口————————————
    //定义内部类ViewHolder,并继承RecyclerView.ViewHolder。
    /**
     * viewHolder1为单图文模式
     */
    static class ViewHolder1 extends RecyclerView.ViewHolder{
        ImageView image;
        TextView biaoti;
        TextView date;
        TextView name;

    //传入的View参数通常是RecyclerView子项的最外层布局。
        public ViewHolder1 (View view)
        {
            super(view);

            image=(ImageView)view.findViewById(R.id.xytt_item1_Content2_img);
            biaoti=(TextView)view.findViewById(R.id.xytt_item1_Content2_title);
            date=(TextView)view.findViewById(R.id.xytt_item1_Content2_date);
            name =(TextView)view.findViewById(R.id.xytt_item1_Content2_name);
        }
    }
    /**
     * viewHolder2为多图文模式
     */
     static class ViewHolder2 extends RecyclerView.ViewHolder{
         ImageView  image1;
         ImageView image2;
         ImageView image3;
         TextView biaoti;
         TextView date;
         TextView name;
         public ViewHolder2(View view) {
             super(view);
             image1=(ImageView)view.findViewById(R.id.xytt_item2_Content1_img1);
             image2=(ImageView)view.findViewById(R.id.xytt_item2_Content1_img2);
             image3=(ImageView)view.findViewById(R.id.xytt_item2_Content1_img3);
             biaoti=(TextView)view.findViewById(R.id.xytt_item2_Content1_title);
             date=(TextView)view.findViewById(R.id.xytt_item2_Content1_date);
             name =(TextView)view.findViewById(R.id.xytt_item2_Content1_name);
         }
     }







    //构造函数,用于把要展示的数据源传入,并赋予值给全局变量List。
   newsAdapter (List <JavaBean> newslist){
        this.newsList = newslist;
        System.out.println(newslist);
    }

    @Override
    public int getItemViewType(int position){
        System.out.println("ps:"+position);

        return  newsList.get(position).N_style;

    }

    @Override
    //用于创建ViewHolder实例,并把加载的布局传入到构造函数去,再把ViewHolder实例返回。
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //传递窗口的地方————————————
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item2, parent, false);

        switch (viewType) {
            case 1:
                return new ViewHolder1(view);
            case 2:
                return new ViewHolder2(view2);
            default:   return new ViewHolder1(view);
        }

    }


    @Override
    //则是用于对子项的数据进行赋值,会在每个子项被滚动到屏幕内时执行
    //position得到当前index的List实例。
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        final int po=position;

        //单击监听
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(po);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null) {
                    longClickListener.onClick(po);
                }
                return true;
            }
        });

        //设置内容的地方————————————
        //holder.fruitName.setText(newsList.get(position).N_name);
//        holder.fruitImage.setImageResource(fruit.getImageId());
        String title = newsList.get(position).N_name;
        String time =newsList.get(position).N_createOn;;
        String by =newsList.get(position).N_Time;


        if(holder instanceof ViewHolder1){
            ((ViewHolder1) holder).biaoti.setText(title);
            ((ViewHolder1) holder).date.setText(time);
            ((ViewHolder1) holder).name.setText(by);
            String img = newsList.get(position).N_naviPic;
            img=url+img;

            new load_image(((ViewHolder1) holder).image).execute(img);
            return;
        }
        if(holder instanceof ViewHolder2){
            ((ViewHolder2) holder).biaoti.setText(title);
            ((ViewHolder2) holder).date.setText(time);
            ((ViewHolder2) holder).name.setText(by);
            String img = newsList.get(position).N_naviPic;
            String s[]=img.split("[;]");
            new load_image(((ViewHolder2) holder).image1).execute(url+s[0]);
            new load_image(((ViewHolder2) holder).image2).execute(url+s[1]);
            new load_image(((ViewHolder2) holder).image3).execute(url+s[2]);
            return;
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

    class load_image extends AsyncTask<String, Void, Drawable> {

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

    @Override
    //返回RecyclerView的子项数目
    public int getItemCount(){
        return newsList.size();
    }



    //TODO:创建单击监听接口与方法
    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }




    public interface OnItemLongClickListener {
        void onClick(int position);
    }

    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }





}
