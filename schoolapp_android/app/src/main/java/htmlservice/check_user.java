package htmlservice;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.logging.LogRecord;

import javabean.JavaBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class check_user {


    private String user, pwd;
    private String addrss = "http://123.56.48.182:5000/api/check_user?id=";


    public boolean checkuser(String user)  {
        addrss+=user;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(addrss)
                .get()
                .build();
        Call call = client.newCall(request);
        final CountDownLatch latch=new CountDownLatch(1);
        final String user2=user;
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
        if (user2.equals(beans.get(0).U_id)){
            return true;
        }
        else {
            return false;
        }
    }



}
