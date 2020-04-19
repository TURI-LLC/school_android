package htmlservice;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HtmlService {

    //异步请求
    public static void sendRequestWithOkhttp(String address, Callback callback)
    {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    //同步请求
    public static String sendRequestWithOkhttp2(String address) throws IOException {

        OkHttpClient client= new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .build();
        Request request=new Request.Builder().url(address).build();
        Response response =client.newCall(request).execute();
        String msg=response.body().string();
        return msg;


    }

}