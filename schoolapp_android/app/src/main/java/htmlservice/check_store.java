package htmlservice;



import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javabean.JavaBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class check_store {



    private String addrss = "http://123.56.48.182:5000/api/check_store?id=";
    private String addrss2 = "http://123.56.48.182:5000/api/check_shopitem?id=";
    private String addrss3 = "http://123.56.48.182:5000/api/check_storexinxi?id=";
    public ArrayList<JavaBean> check_store(String user)  {
        addrss+=user;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(addrss)
                .get()
                .build();
        Call call = client.newCall(request);
        final CountDownLatch latch=new CountDownLatch(1);
        final ArrayList<JavaBean> beans = new ArrayList<>();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                latch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                ResponseBody resbody = response.body();
                final String json = resbody.string();

                Gson gson = new Gson();
                JsonParser jsonParser = new JsonParser();
                JsonArray jsonArray = jsonParser.parse(json).getAsJsonArray();


                for (JsonElement bean : jsonArray) {

                    JavaBean bean1 = gson.fromJson(bean, JavaBean.class);
                    beans.add(bean1);


                }

                latch.countDown();



            }

        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return beans;

    }



    public ArrayList<JavaBean> check_shopitem(String id)  {
        addrss2+=id;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(addrss2)
                .get()
                .build();
        Call call = client.newCall(request);
        final CountDownLatch latch=new CountDownLatch(1);
        final ArrayList<JavaBean> beans = new ArrayList<>();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                latch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                ResponseBody resbody = response.body();
                final String json = resbody.string();

                Gson gson = new Gson();
                JsonParser jsonParser = new JsonParser();
                JsonArray jsonArray = jsonParser.parse(json).getAsJsonArray();

                for (JsonElement bean : jsonArray) {

                    JavaBean bean1 = gson.fromJson(bean, JavaBean.class);
                    beans.add(bean1);


                }

                latch.countDown();



            }

        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return beans;

    }
    /**
     * 获取商店所有的信息
     */
    public ArrayList<JavaBean> check_storexinxi(String id)  {
        addrss3+=id;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(addrss3)
                .get()
                .build();
        Call call = client.newCall(request);
        final CountDownLatch latch=new CountDownLatch(1);
        final ArrayList<JavaBean> beans = new ArrayList<>();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                latch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                ResponseBody resbody = response.body();
                final String json = resbody.string();

                Gson gson = new Gson();
                JsonParser jsonParser = new JsonParser();
                JsonArray jsonArray = jsonParser.parse(json).getAsJsonArray();


                for (JsonElement bean : jsonArray) {

                    JavaBean bean1 = gson.fromJson(bean, JavaBean.class);
                    beans.add(bean1);


                }

                latch.countDown();



            }

        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return beans;

    }



}
