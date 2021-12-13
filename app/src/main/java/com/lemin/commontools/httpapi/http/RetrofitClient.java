package com.lemin.commontools.httpapi.http;

import com.lemin.commontools.httpapi.gson.GsonAdapter;
import com.lemin.commontools.httpapi.gson.GsonFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * RetrofitClient工具类
 */

public class RetrofitClient {

    private static RetrofitClient instance;

    private Retrofit.Builder mRetrofitBuilder;
    private OkHttpClient.Builder mOkHttpBuilder;

    RetrofitClient() {

        mOkHttpBuilder = HttpClient.getInstance().getBuilder();
        mRetrofitBuilder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonFactory.create(GsonAdapter.buildGson()));
    }

    public static RetrofitClient getInstance() {

        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }

        }
        return instance;
    }

    public Retrofit.Builder getRetrofitBuilder() {
        return mRetrofitBuilder;
    }

    public Retrofit getRetrofit() {
        return mRetrofitBuilder.client(mOkHttpBuilder.build()).build();
    }

}
