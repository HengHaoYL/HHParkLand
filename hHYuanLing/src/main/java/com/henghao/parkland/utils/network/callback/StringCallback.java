package com.henghao.parkland.utils.network.callback;

import com.squareup.okhttp.Response;

import java.io.IOException;

public abstract class StringCallback extends BaseCallback<String> {

    @Override
    public String parseResponse(Response response) throws IOException {
        String result = response.body().toString();
        response.body().close();
        return null;
    }
}
