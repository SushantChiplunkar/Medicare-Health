package com.test.medicarehealth.config;

import com.test.medicarehealth.interceptor.ErrorHandlingInterceptor;
import com.test.medicarehealth.interceptor.ServiceInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static final String BASE_URL = "https://api.spaceflightnewsapi.net";

    // Own api builder
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());


    private static Retrofit retrofit = builder.build();
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    // Without auth service for own api
    public static <S> S createService(
            Class<S> serviceClass) {
        httpClient.addInterceptor(new ErrorHandlingInterceptor());
        httpClient.addInterceptor(new ServiceInterceptor());
        builder.client(httpClient.build());
        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }
}
