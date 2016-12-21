package com.lmz.basicdemo.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：LMZ on 2016/12/20 0020 16:11
 */
public class ApiManager2 {
    private static ApiManager2 apiManager;

    public static ApiManager2 getInstance() {
        synchronized (ApiManager2.class) {
            if (apiManager == null) {
                apiManager = new ApiManager2();
            }
        }
        return apiManager;
    }

    BasicApi basicApi;
    private Object object = new Object();

    public BasicApi getBasicService() {
        if (basicApi == null) {
            synchronized (object.getClass()) {
                if (basicApi == null) {
                    basicApi = new Retrofit.Builder()
                            .baseUrl("http://jingyi.hzxmnet.com/")
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(BasicApi.class);
                }
            }
        }
        return basicApi;
    }

}
