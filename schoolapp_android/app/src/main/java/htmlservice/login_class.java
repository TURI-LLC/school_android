package htmlservice;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

import javabean.JavaBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class login_class  {
    private ArrayList<JavaBean> beans = new ArrayList<>();
    private String user, pwd;
    private String addrss = "http://123.56.48.182:5000/api/check?id=123&&name=123";


    public boolean loginjosn(String user,String pwd) throws InterruptedException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(addrss)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

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



            }

        });
        Thread.sleep(1000);
        if (user.equals(beans.get(0).U_id)&&pwd.equals(beans.get(0).U_name)){
            return true;
        }
        else {
            return false;
        }
    }



}
