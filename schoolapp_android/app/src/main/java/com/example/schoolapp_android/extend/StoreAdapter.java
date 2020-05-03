package com.example.schoolapp_android.extend;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp_android.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javabean.JavaBean;

public class StoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<JavaBean> List;
    private String url="http://xxschoolapp.oss-cn-beijing.aliyuncs.com/img/";
    private onRecyclerItemClickerListener mListener;
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
    /**
     * 增加点击监听
     */
    public void setItemListener(onRecyclerItemClickerListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 点击监听回调接口
     */
    public interface onRecyclerItemClickerListener {
        void onRecyclerItemClick(View view, Object data, int position);
    }

    //构造函数,用于把要展示的数据源传入,并赋予值给全局变量List。
    public StoreAdapter(List<JavaBean> list){
        this.List = list;
    }
    /**
     * 点击
     */
    private View.OnClickListener getOnClickListener(final int po){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null && v != null) {
                    mListener.onRecyclerItemClick(v, List.get(po), po);
                }
            }
        };
    }

    @Override
    //用于创建ViewHolder实例,并把加载的布局传入到构造函数去,再把ViewHolder实例返回。
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //传递窗口的地方————————————
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item, parent, false);
        final RecyclerView.ViewHolder holder = new ViewHolder(view);

          return holder;
        }




    @Override
    //则是用于对子项的数据进行赋值,会在每个子项被滚动到屏幕内时执行
    //position得到当前index的List实例。
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){

        //设置内容的地方————————————
        String title = List.get(position).st_name;
        String img =List.get(position).st_img;;
        String price =List.get(position).st_price;
        String weizhi =List.get(position).st_address;
        String grade = List.get(position).st_grade;
        String pinglun=List.get(position).st_pinglun;


        if(holder instanceof ViewHolder){
            holder.itemView.setOnClickListener(getOnClickListener(position));
            new  load_image(((ViewHolder) holder).image).execute(url+img);
           ((ViewHolder) holder).biaoti.setText(title);
           ((ViewHolder) holder).money.setText("￥"+price);
           ((ViewHolder) holder).pinglun.setText(pinglun+"条评论");
           ((ViewHolder) holder).pingfen.setText(grade+"分");
           ((ViewHolder) holder).weizhi.setText(weizhi);


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
        boolean t;
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
        return List.size();
    }

}
