package net.zombie_sama.okhttphelper.callback;

import com.squareup.okhttp.Response;

import java.io.IOException;

public abstract class StringCallback extends BaseCallback<String> {

    @Override
    public String parseResponse(Response response) throws IOException {
        String result = response.body().string();
        response.body().close();
        return result;
    }
}
