package net.zombie_sama.okhttphelper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import net.zombie_sama.okhttphelper.callback.BaseCallback;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class OkHttpController {
    private static OkHttpClient client;
    private static boolean initialized = false;
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

    public static void initClient() {
        client = new OkHttpClient();
        initialized = true;
    }

    private static synchronized OkHttpClient getClientInstance() {
        return client;
    }

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
     * @return 请求Call
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
     * @return 请求Call
     */
    public static Call doRequest(String url, Map<String, Object> params, Map<String, File> files, BaseCallback callback) {
        if (!initialized) {
            throw new RuntimeException("Client has not been initialize. Call OkHttpController.initClient() in your Application.");
        }
        RequestBody body = buildBody(params, files);
        Request request = buildRequest(url, body, "POST");
        Call call = buildCall(getClientInstance(), request);
        callback.onStart();
        Log.i("OkHttpController", "doRequest: url = " + url + " params = " + params.toString());
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
