package com.higdata.okhttphelper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.higdata.okhttphelper.callback.BaseCallback;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings({"WeakerAccess", "UnnecessaryLocalVariable"})
public class OkHttpController {
    private static OkHttpClient client;
    private static final int RESULT_START = 0;
    private static final int RESULT_FAILURE = 1;
    private static final int RESULT_SUCCESS = 2;

    /**
     * 主线程handler
     */
    private static Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            Data data = (Data) msg.obj;
            switch (msg.what) {
                case RESULT_START:
                    data.baseCallback.onStart();
                    break;
                case RESULT_FAILURE:
                    data.baseCallback.onFailure(data.request, data.e, data.code);
                    data.baseCallback.onFinish();
                    break;
                case RESULT_SUCCESS:
                    //noinspection unchecked
                    data.baseCallback.onSuccess(data.result);
                    data.baseCallback.onFinish();
                    break;
            }
        }
    };

    /**
     * 构建请求体
     *
     * @param params 请求参数
     * @param files  上传文件
     * @return 请求体
     */
    public static RequestBody buildBody(Map<String, Object> params, Map<String, File> files) {
        MultipartBuilder builder = new MultipartBuilder();
        if (params != null) {
            for (String key : params.keySet()) {
                builder.addFormDataPart(key, String.valueOf(params.get(key)));
            }
        }
        if (files != null) {
            for (String key : files.keySet()) {
                File file = files.get(key);
                builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            }
        }
        return builder.build();
    }

    public static RequestBody buildJsonBody(String jsonString) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonString);
        return body;
    }

    /**
     * 构建请求
     *
     * @param url    请求url
     * @param body   请求体
     * @param method 请求方法
     * @return 请求
     */
    public static Request buildRequest(String url, RequestBody body, String method) {
        @SuppressWarnings("UnnecessaryLocalVariable")
        Request request = new Request.Builder()
                .url(url)
                .method(method, body)
                .build();
        return request;
    }

    /**
     * 构建Call，主要用于对请求的控制，比如取消请求等<br>
     * 关于Call的更多信息请查看{@link Call}
     *
     * @param mClient OkHttp客户端
     * @param request 请求
     * @return {@link Call}
     */
    public static Call buildCall(OkHttpClient mClient, Request request) {
        return mClient.newCall(request);
    }

    /**
     * 构建OkHttp所需的请求回调
     *
     * @param baseCallback 回调
     * @return OkHttp回调
     */
    public static Callback buildCallback(final BaseCallback baseCallback) {
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
                    Data data = new Data(baseCallback, response.request(), new RuntimeException(eMessage), response.code());
                    Message msg = new Message();
                    msg.what = RESULT_FAILURE;
                    msg.obj = data;
                    handler.sendMessage(msg);
                }
            }
        };
    }

    /**
     * 提交一个请求
     *
     * @param url      请求url
     * @param params   请求参数
     * @param callback 请求回调
     * @return {@link Call}
     */
    public static Call doRequest(String url, Map<String, Object> params, BaseCallback callback) {
        return doRequest(url, params, null, callback);
    }

    /**
     * 提交一个带文件的请求
     *
     * @param url      请求url
     * @param params   请求参数
     * @param files    要上传的文件
     * @param callback 请求回调
     * @return {@link Call}
     */
    public static Call doRequest(String url, Map<String, Object> params, Map<String, File> files, BaseCallback callback) {
        Log.i("OkHttpController", "doRequest: url = " + url + " params = " + params.toString());
        getClientInstance();
        RequestBody body = buildBody(params, files);
        Request request = buildRequest(url, body, "POST");
        Data data = new Data();
        data.baseCallback = callback;
        Message msg = new Message();
        msg.what = RESULT_START;
        msg.obj = data;
        handler.sendMessage(msg);
        Call call = buildCall(client, request);
        call.enqueue(buildCallback(callback));
        return call;
    }

    /**
     * 提交Json请求
     *
     * @param url        请求url
     * @param jsonString Json数据
     * @param callback   回调
     * @return {@link Call}
     */
    public static Call doJsonRequest(String url, String jsonString, BaseCallback callback) {
        Log.i("OkHttpController", "doRequest: url = " + url + " params = " + jsonString);
        getClientInstance();
        RequestBody body = buildJsonBody(jsonString);
        Request request = buildRequest(url, body, "POST");
        Data data = new Data();
        data.baseCallback = callback;
        Message msg = new Message();
        msg.what = RESULT_START;
        msg.obj = data;
        handler.sendMessage(msg);
        Call call = buildCall(client, request);
        call.enqueue(buildCallback(callback));
        return call;
    }

    /**
     * 获取Client单例
     */
    private static synchronized void getClientInstance() {
        if (client == null) {
            client = new OkHttpClient();
        }
    }

    /**
     * 用于线程通讯的数据实体类
     */
    private static class Data {
        BaseCallback baseCallback;
        Request request;
        Exception e;
        Object result;
        int code;

        Data() {
        }

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
