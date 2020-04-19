package htmlservice;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpEntity {
    private static int timeout=10;//网络连接超时
    private static int readTimeout=30;//网络读取超时
    private String url="http://123.56.48.182:5000/api/1?id=1";
    private OkHttpClient client;
    private Request request;
    Callback callback;
    public HttpEntity(Callback callback){
        this.client =new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout,TimeUnit.SECONDS)
                .build();
        this.request=new Request.Builder().url(this.url).build();
        this.callback=callback;

    }
    public void request(){
        client.newCall(request).enqueue(callback);
    }



}
