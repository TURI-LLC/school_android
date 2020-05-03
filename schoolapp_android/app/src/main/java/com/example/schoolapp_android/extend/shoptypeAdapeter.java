package com.example.schoolapp_android.extend;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp_android.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javabean.JavaBean;

public class shoptypeAdapeter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> list;
    private String url = "http://xxschoolapp.oss-cn-beijing.aliyuncs.com/img/";

    //定义内部类ViewHolder,并继承RecyclerView.ViewHolder。

    /**
     * viewHolder1为单图文模式
     */
    static class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView type1;


        //传入的View参数通常是RecyclerView子项的最外层布局。
        public ViewHolder1(View view) {
            super(view);
            type1=view.findViewById(R.id.shoptype);

        }
    }


    //构造函数,用于把要展示的数据源传入,并赋予值给全局变量List。
    public shoptypeAdapeter(List<String> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {

        return 1;

    }

    @Override
    //用于创建ViewHolder实例,并把加载的布局传入到构造函数去,再把ViewHolder实例返回。
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //传递窗口的地方————————————
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopitem_type1, parent, false);

        switch (viewType) {
            case 1:
                return new ViewHolder1(view);

            default:
                return new ViewHolder1(view);
        }

    }


    @Override
    //则是用于对子项的数据进行赋值,会在每个子项被滚动到屏幕内时执行
    //position得到当前index的List实例。
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int po = position;

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
        String typetext = list.get(position);
        if(holder instanceof ViewHolder1){
            ((ViewHolder1) holder).type1.setText(typetext);
        }
        return;


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
    public int getItemCount() {
        return list.size();
    }


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