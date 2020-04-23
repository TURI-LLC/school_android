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
import java.util.List;

import javabean.JavaBean;

public class StoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<JavaBean> newsList;
    private String url="http://xxschoolapp.oss-cn-beijing.aliyuncs.com/img/";


    //TODO: 创建recyclerview——————————定义接口————————————
    //定义内部类ViewHolder,并继承RecyclerView.ViewHolder。
    /**
     * viewHolder1为单图文模式
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView biaoti;
        TextView pingfen;
        TextView pinglun;
        TextView money;
        TextView weizhi;

        //传入的View参数通常是RecyclerView子项的最外层布局。
        public ViewHolder (View view)
        {
            super(view);

            image=(ImageView)view.findViewById(R.id.store_img);
            biaoti=(TextView)view.findViewById(R.id.store_name);
            pingfen=(TextView)view.findViewById(R.id.grade);
            pinglun =(TextView)view.findViewById(R.id.comment_count);
            money =(TextView)view.findViewById(R.id.price);
            weizhi=(TextView)view.findViewById(R.id.distance);
        }
    }





    //构造函数,用于把要展示的数据源传入,并赋予值给全局变量List。
    StoreAdapter(List <JavaBean> newslist){
        this.newsList = newslist;
    }

    @Override
    public int getItemViewType(int position){

        return  newsList.get(position).N_style;

    }

    @Override
    //用于创建ViewHolder实例,并把加载的布局传入到构造函数去,再把ViewHolder实例返回。
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //传递窗口的地方————————————
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item, parent, false);

          return new StoreAdapter.ViewHolder(view);
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
        String title = newsList.get(position).st_name;
        String img =newsList.get(position).st_img;;
        String price =newsList.get(position).st_price;
        String weizhi =newsList.get(position).st_address;
        String grade = newsList.get(position).st_grade;
        String pinglun=newsList.get(position).st_pinglun;


        if(holder instanceof StoreAdapter.ViewHolder){
           ((ViewHolder) holder).biaoti.setText(title);
           ((ViewHolder) holder).money.setText("￥"+price);
           ((ViewHolder) holder).pinglun.setText(pinglun+"条评论");
           ((ViewHolder) holder).pingfen.setText(grade+"分");
           ((ViewHolder) holder).weizhi.setText(weizhi);

            new StoreAdapter.load_image(((ViewHolder) holder).image).execute(url+img);
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

    private newsAdapter.OnItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(newsAdapter.OnItemClickListener listener) {

        this.listener = listener;
    }




    public interface OnItemLongClickListener {
        void onClick(int position);
    }

    private newsAdapter.OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(newsAdapter.OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

}
