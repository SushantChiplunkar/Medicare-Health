package com.test.medicarehealth.interceptor;

import java.io.IOException;
import java.util.TimeZone;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .header("X-ERPApplication-timezone-id", TimeZone.getDefault().getID())
                .method(request.method(), request.body())
                .build();

        return chain.proceed(request);
    }
}
