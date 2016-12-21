package com.lmz.basicdemo.present.implPresenter;

import android.util.Log;

import com.lmz.baselibrary.listener.ProgressListener;
import com.lmz.baselibrary.present.implPresenter.BasePresenterImpl;
import com.lmz.baselibrary.util.Contant;
import com.lmz.baselibrary.util.UploadFileRequestBody;
import com.lmz.basicdemo.api.ApiManager2;
import com.lmz.basicdemo.model.BaseEntity;
import com.lmz.basicdemo.present.IUploadPic;
import com.lmz.basicdemo.present.implView.IUploadPicView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：LMZ on 2016/12/21 0021 13:39
 */
public class UpLoadPicPresent extends BasePresenterImpl implements IUploadPic {
    IUploadPicView mIUploadPicView;

    public UpLoadPicPresent(IUploadPicView mIUploadPic) {
        this.mIUploadPicView = mIUploadPic;
    }

    @Override
    public void upLoadPic(File file) {
        Map<String, RequestBody> param = new HashMap<>();
        UploadFileRequestBody fileRequestBody = new UploadFileRequestBody(file, new ProgressListener() {
            @Override
            public void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish) {
                mIUploadPicView.upLoadPrograss(hasWrittenLen, totalLen, hasFinish);
            }
        });
        param.put("picture\";filename=\"" + file.getName(), fileRequestBody);
        Log.e("tag", "param-->" + param.size());
        String ApiToken = Contant.addMd5("uploadname", "uptype");
        Subscription s = ApiManager2.getInstance().getBasicService().picture(ApiToken, System.currentTimeMillis() + "", "picture", 1, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "error-->" + e.toString());
                        mIUploadPicView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        mIUploadPicView.UpLoadPic();
                    }
                });
        addSubscription(s);
    }


}
