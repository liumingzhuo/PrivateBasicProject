package com.lmz.baselibrary;

import android.app.Application;
import android.content.Context;

import com.lmz.baselibrary.ui.BaseActivity;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * 作者：LMZ on 2016/12/20 0020 14:18
 */
public class BaseApplication extends Application {
    private static BaseApplication baseApplication;
    private BaseApplication application;
    private static List<BaseActivity> activities;
    private static int MainID;

    public static Application getContext() {
        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        activities = new LinkedList<>();
        MainID = android.os.Process.myTid();
    }

    /**
     * 获取application
     */
    public static Context getApplication() {
        return baseApplication;
    }

    /**
     * 获取主线程ID
     */
    public static int getMainID() {
        return MainID;
    }

    /**
     * 添加一个activity
     */
    public void addActivity(BaseActivity activity) {
        activities.add(activity);
    }

    /**
     * 结束一个activity
     */
    public void removeActivity(BaseActivity activity) {
        activities.remove(activity);
    }

    /**
     * 结束当前所有的activity
     */
    public static void clearActivitys() {
        ListIterator<BaseActivity> iterator = activities.listIterator();
        BaseActivity activity;
        while (iterator.hasNext()) {
            activity = iterator.next();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 退出App
     */
    public static void quiteApplication() {
        clearActivitys();
        System.exit(0);
    }
}
