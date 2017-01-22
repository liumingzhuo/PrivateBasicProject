package com.lmz.basicdemo.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lmz.baselibrary.util.HandlerUtil;
import com.lmz.basicdemo.R;

/**
 * 别用 错的！！！！
 */
public class LoadingDialog {
    private static Animation mAnimation;

    //static ？？？？？？？？
    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        // main.xml中的ImageView
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.ll_loading);
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.iv_loading);
        TextView tipTextView = (TextView) v.findViewById(R.id.tv_loading);// 提示文字
        // 加载动画
        mAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimation.setRepeatCount(Integer.MAX_VALUE);
        mAnimation.setDuration(500);
        mAnimation.setInterpolator(new LinearInterpolator());
        spaceshipImage.setAnimation(mAnimation);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.LoadingDialogStyle);// 创建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;
    }

    public static Dialog createLoadingDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        // main.xml中的ImageView
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.ll_loading);
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.iv_loading);
        TextView tipTextView = (TextView) v.findViewById(R.id.tv_loading);// 提示文字
        // 加载动画
        mAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimation.setRepeatCount(Integer.MAX_VALUE);
        mAnimation.setDuration(500);
        mAnimation.setInterpolator(new LinearInterpolator());
        spaceshipImage.setAnimation(mAnimation);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        // tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.LoadingDialogStyle);// 创建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;
    }


    public static void showDialog(final Dialog dialog) {
        if (dialog != null) {
            if (HandlerUtil.isUIThread()) {
                showDialogMainThread(dialog);
            } else {
                HandlerUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showDialogMainThread(dialog);
                    }
                });
            }
        }
    }

    public static void dismiisDialog(final Dialog dialog) {
        if (dialog != null) {
            if (HandlerUtil.isUIThread()) {
                dismissDialogMainThread(dialog);
            } else {
                HandlerUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissDialogMainThread(dialog);
                    }
                });
            }
        }
    }

    private static void showDialogMainThread(Dialog dialog) {
        if (dialog != null) {
            dialog.show();
        }
    }

    private static void dismissDialogMainThread(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
