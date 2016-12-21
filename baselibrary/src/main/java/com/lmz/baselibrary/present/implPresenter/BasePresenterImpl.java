package com.lmz.baselibrary.present.implPresenter;

import com.lmz.baselibrary.present.BasePresenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 作者：LMZ on 2016/12/20 0020 17:11
 */
public class BasePresenterImpl implements BasePresenter {
    private CompositeSubscription mCompositeSubscription;

    protected void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void unsubcrible() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
