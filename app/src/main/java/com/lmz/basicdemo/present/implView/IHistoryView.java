package com.lmz.basicdemo.present.implView;

import com.lmz.baselibrary.present.implView.IBasePresenter;
import com.lmz.basicdemo.model.HistoryModle;

/**
 * 作者：LMZ on 2016/12/20 0020 17:24
 */
public interface IHistoryView  extends IBasePresenter{
    void updataHistoryData(HistoryModle historyModle);
}
