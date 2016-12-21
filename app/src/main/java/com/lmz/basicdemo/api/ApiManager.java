package com.lmz.basicdemo.api;

import com.cpoopc.retrofitrxcache.BasicCache;
import com.cpoopc.retrofitrxcache.RxCacheCallAdapterFactory;
import com.lmz.basicdemo.MyApplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：LMZ on 2016/12/20 0020 16:11
 */
public class ApiManager {
    private static ApiManager apiManager;

    public static ApiManager getInstance() {
        synchronized (ApiManager.class) {
            if (apiManager == null) {
                apiManager = new ApiManager();
            }
        }
        return apiManager;
    }

    BasicApi basicApi;
    private Object object = new Object();


    public BasicApi getBasicCacheService() {
        if (basicApi == null) {
            synchronized (object.getClass()) {
                if (basicApi == null) {
                    basicApi = new Retrofit.Builder()
                            .baseUrl("http://jingyi.hzxmnet.com/")
                            .addCallAdapterFactory(RxCacheCallAdapterFactory.create(BasicCache.fromCtx(MyApplication.getApplication()), false))
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(BasicApi.class);
                }
            }
        }
        return basicApi;
    }

}
