package us.haodongandyushang.yushangmusic.utils;

import com.elvishew.xlog.XLog;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import us.haodongandyushang.yushangmusic.GlobalParams;

public class IKRequest {
    String URL;
    HashMap<String, String> params = new HashMap<>();
    RequestType method;
    IKCallBack callback;
    Request request;
    boolean fullURL=true;
    boolean withDebug=true;


    public IKRequest(String URL, HashMap<String, String> params, RequestType method, IKCallBack callback, Request request) {
        this.URL = URL;
        this.params = params;
        this.method = method;
        this.callback = callback;
        this.request = request;
    }

    public IKRequest() {
    }

    public IKRequest url(String URL) {
        this.URL = URL;
        return this;
    }
    public IKRequest debug(boolean withDebug) {
        this.withDebug = withDebug;
        return this;
    }
    public IKRequest fullUrl(boolean fullURL) {
        this.fullURL = fullURL;
        return this;
    }
    public IKRequest data(HashMap<String, String> params) {
        this.params = params;
        return this;
    }

    public IKRequest method(RequestType m) {
        this.method = m;
        return this;
    }

    public IKRequest callback(IKCallBack callback) {
        this.callback = callback;
        return this;
    }

    public IKRequest bulid() {
        String reqeustUrl=fullURL?URL: GlobalParams.BASE_URL + URL;
        if (method == RequestType.POST) {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
            RequestBody body = builder.build();//制作formdata类型的请求体


            this.request = new Request.Builder().url(reqeustUrl).
                    post(body).build();
            return this;
        } else {
            if (!params.isEmpty()) {
                reqeustUrl += "?" + getData(params);
            }

            this.request = new Request.Builder()
                    .url(reqeustUrl)
                    .build();
            return this;

        }


    }

    void onResult(Call call, Response response) throws IOException {
        assert response.body() != null;
        if (response.code() == 200) {
            String result = response.body().string();
            if(withDebug){
                XLog.e("网络请求:" + System.lineSeparator() + "URL:" + GlobalParams.BASE_URL + this.URL + System.lineSeparator() + "data:" + IKRequest.getData(this.params) + System.lineSeparator() + "请求结果:" + System.lineSeparator() + result);

            }

            callback.onSuccess(result);
        } else {
            // SweetAlert.showErrorWithInfo("网络请求错误:" + response.code());
            if(withDebug){
                XLog.e("网络请求错误:" + System.lineSeparator() + "URL:" + GlobalParams.BASE_URL + this.URL + System.lineSeparator() + "data:" + IKRequest.getData(this.params) + System.lineSeparator() + "请求结果:" + System.lineSeparator() + response.code());

            }

        }

    }

    public static String getData(HashMap<String, String> data) {
        String path = "";
        for (Map.Entry<String, String> entry : data.entrySet()) {
            path += entry.getKey() + "=" + entry.getValue() + "&";
        }
        return path;
    }


}

