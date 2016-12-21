package com.lmz.basicdemo.api;


import com.cpoopc.retrofitrxcache.RxCacheResult;
import com.lmz.basicdemo.model.BaseEntity;
import com.lmz.basicdemo.model.HistoryModle;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 作者：LMZ on 2016/12/20 0020 16:46
 */
public interface BasicApi {
    @FormUrlEncoded
    @POST("Api/Forms/forms.html")
    Observable<RxCacheResult<HistoryModle>> history(
            @Field("ApiToken") String ApiToken,
            @Field("Time") String time,
            @Field("sid") String sid);

    /**
     * 图片上传
     */
    @Multipart
    @POST("Api/Picture/Picture.html")
    Observable<BaseEntity> picture(
            @Query("ApiToken") String ApiToken,
            @Query("Time") String Time,
            @Query("uploadname") String uploadname,
            @Query("uptype") int uptype,
            @PartMap Map<String, RequestBody> param
    );
}
