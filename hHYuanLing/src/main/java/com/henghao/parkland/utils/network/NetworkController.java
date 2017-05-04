package com.henghao.parkland.utils.network;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.henghao.parkland.utils.network.callback.BaseCallback;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;

public class NetworkController {
    private static OkHttpClient client;
    private static Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            Data data = (Data) msg.obj;
            switch (msg.what) {
                case RESULT_FAILURE:
                    data.baseCallback.onFailure(data.request, data.e, data.code);
                    break;
                case RESULT_SUCCESS:
                    data.baseCallback.onSuccess(data.result);
                    break;
            }
            data.baseCallback.onFinish();
        }
    };

    private static final int RESULT_FAILURE = 0;
    private static final int RESULT_SUCCESS = 1;

    public static synchronized OkHttpClient getClientInstance() {
        if (client == null) {
            client = new OkHttpClient();
        }
        return client;
    }

    public static RequestBody buildBody(Map<String, Object> params) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (String key : params.keySet()) {
            builder.add(key, String.valueOf(params.get(key)));
        }
        return builder.build();
    }

    public static Request buildRequest(String url, RequestBody body, String method) {
        Request request = new Request.Builder()
                .url(url)
                .method(method, body)
                .build();
        return request;
    }

    public static Call buildCall(OkHttpClient mClient, Request request) {
        Call call = mClient.newCall(request);
        return call;
    }

    public static Call enqueue(Call call, Callback callback) {
        call.enqueue(callback);
        return call;
    }

    private static Callback buildCallback(final BaseCallback baseCallback) {
        return new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                Message msg = new Message();
                msg.what = RESULT_FAILURE;
                msg.obj = new Data(baseCallback, request, e, -1);
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                if (response.code() == 200) {
                    Message msg = new Message();
                    msg.what = RESULT_SUCCESS;
                    msg.obj = new Data(baseCallback, baseCallback.parseResponse(response));
                    handler.sendMessage(msg);
                } else {
                    String eMessage = response.body().string();
                    response.body().close();
                    Data data = new Data(baseCallback, response.request(), new RequestException(eMessage), response.code());
                    Message msg = new Message();
                    msg.what = RESULT_FAILURE;
                    msg.obj = data;
                    handler.sendMessage(msg);
                }
            }
        };
    }

    public static Call doRequest(String url, BaseCallback callback, Map<String, Object> params) {
        RequestBody body = buildBody(params);
        Request request = buildRequest(url, body, "POST");
        Call call = buildCall(getClientInstance(), request);
        callback.onStart();
        Log.i("NetworkController", "doRequest: url = " + url + " params = " + params.toString());
        return enqueue(call, buildCallback(callback));
    }

    private static class Data {
        BaseCallback baseCallback;
        Request request;
        Exception e;
        Object result;
        int code;

        Data(BaseCallback baseCallback, Object result) {
            this.baseCallback = baseCallback;
            this.result = result;
        }

        Data(BaseCallback baseCallback, Request request, Exception e, int code) {
            this.baseCallback = baseCallback;
            this.request = request;
            this.e = e;
            this.code = code;
        }
    }
}
