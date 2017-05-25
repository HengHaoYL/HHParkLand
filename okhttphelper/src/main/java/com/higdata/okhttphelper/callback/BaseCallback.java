package com.higdata.okhttphelper.callback;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 响应回调的基类
 *
 * @param <T> 响应回调解析结果的泛型，和 {@link BaseCallback#parseResponse(Response)} 的类型相关联
 */
public abstract class BaseCallback<T> {
    /**
     * 解析服务端的返回的响应
     *
     * @param response 响应
     * @return 返回值类型为 {@link BaseCallback} 的泛型
     * @throws IOException 解析失败抛出异常
     */
    public abstract T parseResponse(Response response) throws IOException;

    /**
     * 请求回调，执行在请求开始之前
     */
    public void onStart() {
    }

    /**
     * 请求回调，执行在请求结束之后
     */
    public void onFinish() {
    }

    /**
     * 请求回调，执行在请求发生错误或服务器返回错误时
     *
     * @param request 请求实体
     * @param e       发生的错误
     * @param code    响应码/错误码
     */
    public abstract void onFailure(Request request, Exception e, int code);

    /**
     * 请求回调，执行在请求成功后
     *
     * @param response 由{@link BaseCallback#parseResponse(Response response)}解析后的响应结果
     */
    public abstract void onSuccess(T response);
}
