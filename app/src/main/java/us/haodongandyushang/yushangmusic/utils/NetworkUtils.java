package us.haodongandyushang.yushangmusic.utils;

import com.elvishew.xlog.XLog;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import us.haodongandyushang.yushangmusic.BaseActivity;

public class NetworkUtils {
    public static OkHttpClient client;//重写请求基类  .... is4yNo-
    public static boolean logInfo = true;

    public NetworkUtils() {
        client = new OkHttpClient();
    }

    public static void sendRequest(IKRequest ikRequest) {



        client.newCall(ikRequest.request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                XLog.e("Network Error On :" + ikRequest.URL);
                XLog.e("Detail:" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                ikRequest.onResult(call, response);
            }
        });


    }

    public static void sendRequestWithLoading(IKRequest ikRequest, BaseActivity b) {

        b.showDialog();
        client.newCall(ikRequest.request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                b.dismissDialog();
                XLog.e("Network Error On :" + ikRequest.URL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                b.dismissDialog();
                ikRequest.onResult(call, response);
            }
        });


    }
}
