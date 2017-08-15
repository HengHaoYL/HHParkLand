package com.higdata.okhttphelper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.higdata.okhttphelper.callback.BaseCallback;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
                    data.baseCallback.onFailure(data.e, data.code);
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
    public static RequestBody buildBody(Map<String, Object> params, List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (params != null) {
            for (String key : params.keySet()) {
                builder.addFormDataPart(key, String.valueOf(params.get(key)));
            }
        }
        if (files != null) {
            for (File file : files) {
                builder.addFormDataPart("files", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            }
        }
        return builder.build();
    }

    /**
     * 构建Json字符串的请求体
     *
     * @param jsonString
     * @return
     */
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
    public static Request buildRequest(String url, RequestBody body, String method, Map<String, String> headers) {
        Request.Builder requestBuilder = new Request.Builder();
        if (headers != null) {
            for (String header : headers.keySet()) {
                requestBuilder.addHeader(header, headers.get(header));
            }
        }
        Request request = requestBuilder
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
            public void onFailure(Call call, IOException e) {
                Message msg = Message.obtain();
                msg.what = RESULT_FAILURE;
                msg.obj = new Data(baseCallback, e, -1);
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    Message msg = Message.obtain();
                    msg.what = RESULT_SUCCESS;
                    msg.obj = new Data(baseCallback, baseCallback.parseResponse(response));
                    handler.sendMessage(msg);
                } else {
                    String eMessage = response.body().string();
                    response.body().close();
                    Data data = new Data(baseCallback, new RuntimeException(eMessage), response.code());
                    Message msg = Message.obtain();
                    msg.what = RESULT_FAILURE;
                    msg.obj = data;
                    handler.sendMessage(msg);
                }
            }
        };
    }

    /**
     * 提交一个Form表单请求
     *
     * @param url      请求url
     * @param params   请求参数
     * @param headers  请求头
     * @param callback 请求回调
     * @return {@link Call}
     */
    public static Call doRequest(String url, Map<String, Object> params, Map<String, String> headers, BaseCallback callback) {
        return doRequest(url, params, null, headers, callback);
    }

    /**
     * 提交无参数的GET请求
     *
     * @param url      请求url
     * @param callback 请求回调
     * @param headers  请求头
     * @return {@link Call}
     */
    public static Call doRequest(String url, Map<String, String> headers, BaseCallback callback) {
        getClientInstance();
        Request request = buildRequest(url, null, "GET", headers);
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
     * 提交一个带文件的请求
     *
     * @param url      请求url
     * @param params   请求参数
     * @param files    要上传的文件
     * @param headers  请求头
     * @param callback 请求回调
     * @return {@link Call}
     */
    public static Call doRequest(String url, Map<String, Object> params, List<File> files, Map<String, String> headers, BaseCallback callback) {
        if (params != null) {
            Log.i("OkHttpController", "doRequest: url = " + url + " params = " + params.toString());
        }
        getClientInstance();
        RequestBody body = buildBody(params, files);
        Request request = buildRequest(url, body, "POST", headers);
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
     * @param headers    请求头
     * @param callback   回调
     * @return {@link Call}
     */
    public static Call doJsonRequest(String url, String jsonString, Map<String, String> headers, BaseCallback callback) {
        Log.i("OkHttpController", "doRequest: url = " + url + " params = " + jsonString);
        getClientInstance();
        RequestBody body = buildJsonBody(jsonString);
        Request request = buildRequest(url, body, "POST", headers);
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
        Exception e;
        Object result;
        int code;

        Data() {
        }

        Data(BaseCallback baseCallback, Object result) {
            this.baseCallback = baseCallback;
            this.result = result;
        }

        Data(BaseCallback baseCallback, Exception e, int code) {
            this.baseCallback = baseCallback;
            this.e = e;
            this.code = code;
        }
    }
}
