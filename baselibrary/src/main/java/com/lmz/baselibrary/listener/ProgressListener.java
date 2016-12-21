package com.lmz.baselibrary.listener;

/**
 * 作者：LMZ on 2016/12/21 0021 16:11
 */
public interface ProgressListener {
    void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish);
}
