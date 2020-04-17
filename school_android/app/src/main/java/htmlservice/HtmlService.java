package htmlservice;

import okhttp3.*;

public class HtmlService {

    public static void sendRequestWithOkhttp(String address,okhttp3.Callback callback)
    {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

}