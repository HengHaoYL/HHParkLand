package net.zombie_sama.okhttphelper.callback;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public abstract class BaseCallback<T> {
    public abstract T parseResponse(Response response) throws IOException;

    public void onStart() {

    }

    public void onFinish() {

    }

    public abstract void onFailure(Request request, Exception e, int code);

    public abstract void onSuccess(T response);
}
