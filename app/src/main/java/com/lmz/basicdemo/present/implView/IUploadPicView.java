package com.lmz.basicdemo.present.implView;

import com.lmz.baselibrary.present.implView.IBasePresenter;

/**
 * 作者：LMZ on 2016/12/21 0021 13:39
 */
public interface IUploadPicView extends IBasePresenter {
    void UpLoadPic();

    void upLoadPrograss(long prograss, long totle, boolean finish);
}
