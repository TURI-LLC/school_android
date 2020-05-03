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




    public class shopitemAdapeter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<JavaBean> list;
        private String url="http://xxschoolapp.oss-cn-beijing.aliyuncs.com/img/";
        private onRecyclerItemClickerListener mListener;
        //定义内部类ViewHolder,并继承RecyclerView.ViewHolder。

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


        /**
         * 点击
         */
        private View.OnClickListener getOnClickListener(final int po){
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null && v != null) {
                        mListener.onRecyclerItemClick(v, list.get(po), po);
                    }
                }
            };
        }
        public List<JavaBean> getDataList() {
            return list;
        }










        /**
         * viewHolder1为单图文模式
         */
        static class ViewHolder1 extends RecyclerView.ViewHolder{
            ImageView image;
            TextView sell;
            TextView title;
            TextView price;
            TextView number;
            ImageButton jianhao;
            ImageButton jiahao;

            //传入的View参数通常是RecyclerView子项的最外层布局。
            public ViewHolder1 (View view)
            {
                super(view);
                image=view.findViewById(R.id.shopitem_img);
                sell =view.findViewById(R.id.shopitem_sell);
                title=view.findViewById(R.id.shopitem_name);
                price=view.findViewById(R.id.shopitem_price);
                number=view.findViewById(R.id.shopitem_number);
                jiahao=view.findViewById(R.id.shopitem_jiahao);
                jianhao=view.findViewById(R.id.shopitem_jianhao);

            }
        }


        //构造函数,用于把要展示的数据源传入,并赋予值给全局变量List。
        public shopitemAdapeter(List<JavaBean> list){
            if(list!=null){
                if(this.list!=null){
                    this.list.clear();
                }
                this.list = list;
                notifyDataSetChanged();

                }

        }

        @Override
        public int getItemViewType(int position){

            return  1;

        }

        @Override
        //用于创建ViewHolder实例,并把加载的布局传入到构造函数去,再把ViewHolder实例返回。
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //传递窗口的地方————————————
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopitem_item1, parent, false);

            switch (viewType) {
                case 1:
                    return new ViewHolder1(view);

                default:   return new ViewHolder1(view);
            }

        }


        @Override
        //则是用于对子项的数据进行赋值,会在每个子项被滚动到屏幕内时执行
        //position得到当前index的List实例。
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
            final int po=position;


            //设置内容的地方————————————
            String title = list.get(position).sp_name;
            String sell =list.get(position).sp_sell;
            String price =list.get(position).sp_price;
            String img = list.get(position).sp_img;
            int number=list.get(position).sp_number;

            if(holder instanceof ViewHolder1){
                ((ViewHolder1) holder).title.setText(title);
                ((ViewHolder1) holder).sell.setText("已售："+sell);
                ((ViewHolder1) holder).price.setText("￥"+price);
//                ((ViewHolder1) holder).jiahao.setVisibility(View.GONE);
                ((ViewHolder1) holder).jiahao.setOnClickListener(getOnClickListener(position));
                ((ViewHolder1) holder).jianhao.setOnClickListener(getOnClickListener(position));
                if(number>0){
                    ((ViewHolder1) holder).jianhao.setVisibility(View.VISIBLE);
                    ((ViewHolder1) holder).number.setVisibility(View.VISIBLE);
                    ((ViewHolder1) holder).number.setText(String.valueOf(number));
                }else{
                    ((ViewHolder1) holder).jianhao.setVisibility(View.GONE);
                    ((ViewHolder1) holder).number.setVisibility(View.GONE);
                }




                img=url+img;
                new load_image(((ViewHolder1) holder).image).execute(img);

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
            return list.size();
        }








    }















