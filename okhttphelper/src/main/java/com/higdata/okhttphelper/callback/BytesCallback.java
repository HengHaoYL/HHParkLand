package com.higdata.okhttphelper.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * 请求基类 {@link BaseCallback} 的抽象实现
 */
public abstract class BytesCallback extends BaseCallback<byte[]> {

    /**
     * 将响应结果解析为输入流数据
     *
     * @param response 响应
     * @return 响应结果
     * @throws IOException 解析失败时抛出错误
     */
    @Override
    public byte[] parseResponse(Response response) throws IOException {
        byte[] bytes = response.body().bytes();
        response.close();
        return bytes;
    }
}
