package com.lmz.basicdemo.present.implPresenter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.cpoopc.retrofitrxcache.RxCacheResult;
import com.lmz.baselibrary.present.implPresenter.BasePresenterImpl;
import com.lmz.baselibrary.util.Contant;
import com.lmz.basicdemo.adapters.HistoryAdapter;
import com.lmz.basicdemo.api.ApiManager;
import com.lmz.basicdemo.model.HistoryModle;
import com.lmz.basicdemo.present.IHistory;
import com.lmz.basicdemo.present.implView.IHistoryView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：LMZ on 2016/12/20 0020 17:25
 */
public class HistoryPresent extends BasePresenterImpl implements IHistory, SwipeRefreshLayout.OnRefreshListener {
    private IHistoryView mHistoryView;
    private HistoryAdapter mHistoryAdapter;
    private List<HistoryModle.DataEntity> historys = new ArrayList<>();

    public HistoryPresent(IHistoryView mHistoryView) {
        this.mHistoryView = mHistoryView;
        initAdapter();
    }

    private void initAdapter() {
        mHistoryAdapter = new HistoryAdapter(historys);
    }


    @Override
    public void getHistory() {
        String ApiToken = Contant.addMd5("sid");
        Subscription subsctription = ApiManager.getInstance().getBasicCacheService().history(ApiToken, System.currentTimeMillis() + "", "1")
                .subscribeOn(Schedulers.io())
                .map(new Func1<RxCacheResult<HistoryModle>, HistoryModle>() {
                    @Override
                    public HistoryModle call(RxCacheResult<HistoryModle> historyModleRxCacheResult) {
                        return historyModleRxCacheResult.getResultModel();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HistoryModle>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mHistoryView.hiteProgressDialog();
                        mHistoryView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(HistoryModle historyModle) {
                        mHistoryView.hiteProgressDialog();
                        if (historyModle.getCode().equals("0")) {
                            historys = historyModle.getData();
                            mHistoryAdapter.setNewData(historys);
                            mHistoryView.updataHistoryData(historyModle);
                        } else {
                            mHistoryView.showError(historyModle.getMsg());
                        }

                    }
                });

        addSubscription(subsctription);
    }

    @Override
    public HistoryAdapter getAdapter() {
        return mHistoryAdapter;
    }

    @Override
    public void onRefresh() {
        Log.e("TAG", "hahaha");
        getHistory();
    }
}
