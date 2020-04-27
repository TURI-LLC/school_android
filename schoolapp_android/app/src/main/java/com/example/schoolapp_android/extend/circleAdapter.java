package com.example.schoolapp_android.extend;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp_android.R;

import java.io.InputStream;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import javabean.JavaBean;


public class circleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<JavaBean> circleList;
    private String url="http://xxschoolapp.oss-cn-beijing.aliyuncs.com/img/";

    //TODO: 创建recyclerview——————————定义接口————————————
    //定义内部类ViewHolder,并继承RecyclerView.ViewHolder。
    /**
     * viewHolder1为说说模式
     */
    static class ViewHolder1 extends RecyclerView.ViewHolder{
        ImageView touxiang;
        ImageView img1;
        ImageView img2;
        ImageView img3;
        ImageView img4;
        ImageView img5;
        ImageView img6;
        ImageView img7;
        ImageView img8;
        ImageView img9;
        TextView name;
        TextView date;
        TextView text;
        TextView like;
        TextView pinglun;

        //传入的View参数通常是RecyclerView子项的最外层布局。
        public ViewHolder1 (View view)
        {
            super(view);

            touxiang=(ImageView)view.findViewById(R.id.personPhoto);
            name=(TextView)view.findViewById(R.id.personName);
            date=(TextView)view.findViewById(R.id.personDate);
            text =(TextView)view.findViewById(R.id.textContent);
            img1=(ImageView)view.findViewById(R.id.circleimg1);
            img2=(ImageView)view.findViewById(R.id.circleimg2);
            img3=(ImageView)view.findViewById(R.id.circleimg3);
            img4=(ImageView)view.findViewById(R.id.circleimg4);
            img5=(ImageView)view.findViewById(R.id.circleimg5);
            img6=(ImageView)view.findViewById(R.id.circleimg6);
            img7=(ImageView)view.findViewById(R.id.circleimg7);
            img8=(ImageView)view.findViewById(R.id.circleimg8);
            img9=(ImageView)view.findViewById(R.id.circleimg9);
            like=(TextView)view.findViewById(R.id.like1);
            pinglun=(TextView)view.findViewById(R.id.pinglun1);

        }
    }
    /**
     * viewHolder2表白墙
     */
    static class ViewHolder2 extends RecyclerView.ViewHolder{
        ImageView  img;
        TextView text;
        TextView date;
        TextView name;
        TextView like;
        TextView pinglun;
        public ViewHolder2(View view) {
            super(view);
            img=(ImageView)view.findViewById(R.id.quanzi2_img);
            text =view.findViewById(R.id.quanzi2_text);
            date=(TextView)view.findViewById(R.id.quanzi2_date);
            name =(TextView)view.findViewById(R.id.xytt_item2_Content1_name);
            like=view.findViewById(R.id.quanzi2_lke);
            pinglun=view.findViewById(R.id.quanzi2_pinglun);
        }
    }

    /**
     * viewHolder3为兼职模式
     */
    static class ViewHolder3 extends RecyclerView.ViewHolder{
        ImageView touxiang;
        ImageView img1;
        ImageView img2;
        ImageView img3;
        TextView name;
        TextView text;
        TextView price;

        //传入的View参数通常是RecyclerView子项的最外层布局。
        public ViewHolder3 (View view)
        {
            super(view);

            touxiang=(ImageView)view.findViewById(R.id.quanzi3_touxiang);
            name=(TextView)view.findViewById(R.id.quanzi3_name);
            text=(TextView)view.findViewById(R.id.quanzi3_text);
            price=(TextView)view.findViewById(R.id.quanzi3_price);
            img1=(ImageView)view.findViewById(R.id.quanzi3_img1);
            img2=(ImageView)view.findViewById(R.id.quanzi3_img2);
            img3=(ImageView)view.findViewById(R.id.quanzi3_img3);

        }
    }





    //构造函数,用于把要展示的数据源传入,并赋予值给全局变量List。
    public circleAdapter(List<JavaBean> newslist){
        this.circleList = newslist;

    }

    @Override
    public int getItemViewType(int position){


        return circleList.get(position).Ci_type;

    }

    @Override
    //用于创建ViewHolder实例,并把加载的布局传入到构造函数去,再把ViewHolder实例返回。
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //传递窗口的地方————————————
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quanzi_item1, parent, false);
        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.quanzi_item2, parent, false);
        View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.quanzi_item3, parent, false);

        switch (viewType) {
            case 1:
                return new ViewHolder1(view);
            case 2:  return new ViewHolder3(view3);
            case 3:  return new ViewHolder3(view3);
            case 4:
                return new ViewHolder2(view2);
            default:   return new ViewHolder1(view);
        }

    }


    @Override
    //则是用于对子项的数据进行赋值,会在每个子项被滚动到屏幕内时执行
    //position得到当前index的List实例。
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        final int po=position;



        //设置内容的地方————————————
        String title = circleList.get(position).Ci_title;
        String text =circleList.get(position).Ci_text;
        String img =circleList.get(position).Ci_img;
        ArrayList<String> imgs=new ArrayList<>();
        if(img==null){img="0";}
        else{

            String im[]=img.split("[;]");
            for(int i=0;i<im.length;i++){
                imgs.add(im[i]);
            }
        }
        String like=circleList.get(position).Ci_like;
        if(like==null){like="0";}
        else{
            String likes[]=like.split("[;]");
            like =String.valueOf(likes.length);
        }

        String comment=circleList.get(position).Ci_comment;
        if(comment==null){comment="0";}
        else{
            String comments[]=comment.split("[;]");
            comment =String.valueOf(comments.length);
        }
        String date =circleList.get(position).Ci_date.substring(5,10);
        String price = circleList.get(position).Ci_price;
        String touxiang = circleList.get(position).U_img;
        String kick = circleList.get(position).U_kick;

        if(holder instanceof ViewHolder1){
            new load_image(((ViewHolder1) holder).touxiang,false).execute(url+touxiang);
            ((ViewHolder1) holder).text.setText(text);
            ((ViewHolder1) holder).date.setText(date);
            ((ViewHolder1) holder).name.setText(kick);
            ((ViewHolder1) holder).like.setText(like);
            ((ViewHolder1) holder).pinglun.setText(comment);
            for(int i=0;i<imgs.size();i++){
                switch (i){
                    case 0:new load_image(((ViewHolder1) holder).img1).execute(url+imgs.get(i));break;
                    case 1:new load_image(((ViewHolder1) holder).img2).execute(url+imgs.get(i));break;
                    case 2:new load_image(((ViewHolder1) holder).img3).execute(url+imgs.get(i));break;
                    case 3:new load_image(((ViewHolder1) holder).img4).execute(url+imgs.get(i));break;
                    case 4:new load_image(((ViewHolder1) holder).img5).execute(url+imgs.get(i));break;
                    case 5:new load_image(((ViewHolder1) holder).img6).execute(url+imgs.get(i));break;
                    case 6:new load_image(((ViewHolder1) holder).img7).execute(url+imgs.get(i));break;
                    case 7:new load_image(((ViewHolder1) holder).img8).execute(url+imgs.get(i));break;
                    case 8:new load_image(((ViewHolder1) holder).img9).execute(url+imgs.get(i));break;
                }
            }

            return;
        }
        if(holder instanceof ViewHolder2){
            ((ViewHolder2) holder).text.setText(text);
            ((ViewHolder2) holder).date.setText(date);
            ((ViewHolder2) holder).like.setText(like);
            ((ViewHolder2) holder).pinglun.setText(comment);
            if(imgs.size()>0)   new load_image(((ViewHolder2) holder).img).execute(url+imgs.get(0));


            return;
        }
        if(holder instanceof ViewHolder3){
            new load_image(((ViewHolder3) holder).touxiang,false).execute(url+touxiang);
            ((ViewHolder3) holder).name.setText(kick);
            ((ViewHolder3) holder).price.setText(price);
            ((ViewHolder3) holder).text.setText(text);
            for(int i=0;i<imgs.size();i++){
                switch (i){
                    case 0:new load_image(((ViewHolder3) holder).img1).execute(url+imgs.get(i));break;
                    case 1:new load_image(((ViewHolder3) holder).img2).execute(url+imgs.get(i));break;
                    case 2:new load_image(((ViewHolder3) holder).img3).execute(url+imgs.get(i));break;
                }
            }

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
        boolean t=true;
        load_image(ImageView img) {
            // list all the parameters like in normal class define

            this.img = img;
        }
        load_image(ImageView img,boolean t){
            this.t=t;
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


            if(t){
                LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)img.getLayoutParams();
                params.weight=1;

                img.setLayoutParams(params);

            }
            img.setImageDrawable(result);
        }

    }

    @Override
    //返回RecyclerView的子项数目
    public int getItemCount(){
        return circleList.size();
    }
//_______________清空




}
