package com.henghao.parkland.utils.http;

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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by 晏琦云 on 2017/2/22.
 * 网络访问工具类
 */

public class OkhttpUtils {
    private OkHttpClient okHttpClient;
    private Request.Builder builder;
    private Request request;
    private static final String TAG = "OkhttpUtils";
    private HttpCallBack callBack;
    private String result_str;//返回的字符串
    private byte[] bytes;

    public byte[] getBytes() {
        return bytes;
    }

    public String getResult_str() {
        return result_str;
    }

    public OkhttpUtils() {
        okHttpClient = new OkHttpClient();
        builder = new Request.Builder();
    }

    /**
     * GET请求
     *
     * @param url
     * @return
     */
    public void doGet(String url) {
        request = builder.url(url).get().build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, "onFailure: " + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                callBack.result_str(response.body().string());
            }
        });
    }

    /**
     * POST请求
     *
     * @param url
     * @param map
     * @return
     */
    public String doPost(String url, Map<String, String> map) {
        String str = null;
        FormEncodingBuilder requestBodyBuilder = new FormEncodingBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            requestBodyBuilder.add(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = requestBodyBuilder.build();
        request = builder.post(requestBody).url(url).build();
        Call call = okHttpClient.newCall(this.request);
        try {
            Response response = call.execute();
            str = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 下载文件，转成byte[]
     *
     * @param url
     * @return
     */
    public void download(String url) {
        request = builder.url(url).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, "onFailure: " + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                bytes = response.body().bytes();
            }
        });
    }

    /**
     * 上传，包括参数
     *
     * @param url
     * @param str_map
     * @param file_map
     * @return
     */
    public void upload(String url, Map<String, String> str_map, List<File> file_map) {
        MultipartBuilder multipartBuilder = new MultipartBuilder();
        multipartBuilder.type(MultipartBuilder.FORM);//
        for (Map.Entry<String, String> entry : str_map.entrySet()) {
            multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        for (File file : file_map) {
            multipartBuilder.addFormDataPart(file.getName(), file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }
        RequestBody requestBody = multipartBuilder.build();
        request = builder.post(requestBody).url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, "onFailure: " + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                callBack.result_str(response.body().string());
            }
        });
    }

    public interface HttpCallBack {
        public void result_str(String str);

        public void result_btyes(byte[] bytes);
    }

}
