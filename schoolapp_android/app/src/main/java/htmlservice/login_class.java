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

public class login_class  {
    private ArrayList<JavaBean> beans = new ArrayList<>();




    public boolean loginjosn(String user,String pwd)  {
        String regex1 = "^[a-zA-Z][a-zA-Z0-9_]{5,15}$"; //验证用户名是否为id
        String regex2 ="^[0-9]{11,11}$"; //验证用户名是否为手机号
        String regex3 ="\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";//验证邮箱
        String ziduan ="";

        if(user.matches(regex1)){
            ziduan="id="+user;

        }else if(user.matches(regex2)){
            ziduan="phone="+user;

        }else if(user.matches(regex3)){
            ziduan="mail="+user;

        }else{
            return false;
        }
        String addrss = "http://123.56.48.182:5000/api/check?"+ziduan+"&&"+"password="+pwd;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(addrss)
                .get()
                .build();
        Call call = client.newCall(request);
        final CountDownLatch latch=new CountDownLatch(1);
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
        if (pwd.equals(beans.get(0).U_password)){
            return true;
        }
        else {
            return false;
        }
    }



}
