package com.lmz.baselibrary.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.lmz.baselibrary.BaseApplication;
import com.lmz.baselibrary.R;
import com.lmz.baselibrary.listener.IPermissionResultListener;
import com.lmz.baselibrary.widget.StatusBarCompat;

import butterknife.ButterKnife;


/**
 * 作者：LMZ on 2016/12/20 0020 14:15
 */
public abstract class BaseActivity extends AppCompatActivity {
    private IPermissionResultListener mIPermissionResultListener;
    private int mRequestCode;
    public static BaseActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        initConvetView(savedInstanceState);
        ButterKnife.bind(this);
        ((BaseApplication) BaseApplication.getApplication()).addActivity(this);
        initView();
        initData();
        //  SetTranslanteBar();
        SetStatusBarColor(R.color.tc_black);
    }

    protected abstract void initConvetView(Bundle saveInstanceState);

    protected abstract void initView();

    protected abstract void initData();


    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        activity = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((BaseApplication) BaseApplication.getApplication()).removeActivity(this);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, color));
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this);
    }


    protected void permissionRequest(String msg, String[] permissions, int requestCode, IPermissionResultListener iPermissionResultListener) {
        if (permissions == null || permissions.length == 0) {
            return;
        }
        mRequestCode = requestCode;
        mIPermissionResultListener = iPermissionResultListener;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(permissions)) { //如果没有权限 需要申请
                requestPermission(msg, permissions, requestCode);
            } else {  //有权限
                mIPermissionResultListener.onPermissionSuccess();
            }
        } else {
            if (mIPermissionResultListener != null) {
                mIPermissionResultListener.onPermissionSuccess();
            }
        }

    }

    /**
     * 检查权限
     */
    private boolean checkPermission(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 申请权限
     */
    private void requestPermission(String msg, String[] permissions, int requestCode) {
        //先判断是否已经拒绝过了
        if (shouldShowRequestPermission(permissions)) {
            //弹出Dialog 给用户说明
            showRequestDialog(msg, permissions, requestCode);
        } else {
            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
        }
    }

    /**
     * 再次申请时
     */
    private boolean shouldShowRequestPermission(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 弹出的Dialog
     */
    private void showRequestDialog(String msg, final String[] permissions, final int requestCode) {
        new AlertDialog.Builder(this)
                .setTitle("友好提醒")
                .setMessage(msg)
                .setPositiveButton("好，给你", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
                    }
                })
                .setNegativeButton("我拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mRequestCode) {
            if (checkRequestResult(grantResults)) {
                if (mIPermissionResultListener != null) {  //回调成功
                    mIPermissionResultListener.onPermissionSuccess();
                }
            } else {
                if (mIPermissionResultListener != null) {
                    mIPermissionResultListener.onPermissionFaild();
                }
            }
        }
    }

    /**
     * 检查回调结果
     */
    private boolean checkRequestResult(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}
