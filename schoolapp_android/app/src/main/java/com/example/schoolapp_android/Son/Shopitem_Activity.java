package com.example.schoolapp_android.Son;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.schoolapp_android.R;
import com.example.schoolapp_android.extend.shopitemAdapeter;
import com.example.schoolapp_android.extend.shoptypeAdapeter;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.TreeSet;

import htmlservice.check_store;
import javabean.JavaBean;

public class Shopitem_Activity extends AppCompatActivity {
    private String user;
    private String st_id;
    private String url="http://xxschoolapp.oss-cn-beijing.aliyuncs.com/img/";
    private ArrayList<JavaBean> shopitem;
    private  ArrayList<String> type=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        //接管系统标题栏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("点餐");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent=getIntent();
        user=intent.getStringExtra("user");
        st_id=intent.getStringExtra("st_id");
        System.out.println("商品id"+st_id);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new getshopxinxi().execute();
        new getshopitem().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class getshopxinxi extends AsyncTask<Void,String, ArrayList<JavaBean>>{

        @Override
        protected ArrayList<JavaBean> doInBackground(Void... voids) {
            check_store a=new check_store();

            return a.check_storexinxi(st_id);
        }

        @Override
        protected void onPostExecute(ArrayList<JavaBean> javaBeans) {
            super.onPostExecute(javaBeans);
            ImageView img=findViewById(R.id.shop_img);
            TextView title =findViewById(R.id.shop_title);
            TextView address=findViewById(R.id.shop_address);
            TextView pingfen =findViewById(R.id.shop_pingfen);
            new load_image(img).execute(url+javaBeans.get(0).st_img);
            title.setText(javaBeans.get(0).st_name);
            address.setText(javaBeans.get(0).st_address);
            pingfen.setText(javaBeans.get(0).st_grade);


        }
    }
    /**
     * 设置left的商品类型列表————————————————————————
     * 第一步，获取所有的商品类型
     * 第二部，检索商品类型去除重复项
     * 第三部，动态生成
     * 第四部，添加点击事件传参给下一个
     */
    private class getshopitem extends AsyncTask<Void,String,ArrayList<JavaBean>>{

        @Override
        protected ArrayList<JavaBean> doInBackground(Void... voids) {
            check_store a=new check_store();

            return a.check_shopitem(st_id);
        }

        @Override
        protected void onPostExecute(ArrayList<JavaBean> javaBeans) {
            super.onPostExecute(javaBeans);
            shopitem=javaBeans;


            for(int i=0;i<shopitem.size();i++){
                type.add(shopitem.get(i).sp_type);
            }
            TreeSet<String> hset=new TreeSet<String>(type);
            type.clear();
            type.addAll(hset);


            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.shopitem_type_recy);
            //
            LinearLayoutManager layoutManager = new LinearLayoutManager(Shopitem_Activity.this);
            recyclerView.setLayoutManager(layoutManager);
            //news recyclerview适配器
            shoptypeAdapeter adapter = new shoptypeAdapeter(type);
            //取消自带的滚动
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(adapter);//设置适配器
            adapter.setOnItemClickListener(new shoptypeAdapeter.OnItemClickListener() {
                @Override
                public void onClick(int position) {

                    setshopitemtype(type.get(position));
                }
            });

        }
    }
    /**
     * 获取商品信息类型，并动态生成，并指向下一个异步方法
     */
        private void setshopitemtype(String type){
            System.out.println("定位="+type);
           ArrayList<JavaBean> shopitemoktype =new ArrayList<>();
            for(int i=0;i<shopitem.size();i++){
                if(shopitem.get(i).sp_type.equals(type)){
                   shopitemoktype.add(shopitem.get(i));
                }
            }
            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.shopitem_recy);
            //
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            //news recyclerview适配器
            final shopitemAdapeter adapter = new shopitemAdapeter(shopitemoktype);
            //取消自带的滚动
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(adapter);//设置适配器
            adapter.setItemListener(new shopitemAdapeter.onRecyclerItemClickerListener() {
                @Override
                public void onRecyclerItemClick(View view, Object data, int position) {
                    //加号点击事件
                    if(view.getId()==R.id.shopitem_jiahao){
                    adapter.getDataList().get(position).sp_number+=1;
                    adapter.notifyItemChanged(position);
                    }
                    //减号点击事件
                    if(view.getId()==R.id.shopitem_jianhao){
                        adapter.getDataList().get(position).sp_number-=1;
                        adapter.notifyItemChanged(position);
                    }

                }
            });

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


}
