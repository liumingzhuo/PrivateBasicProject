package com.lmz.baselibrary.util.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;


/**
 * Created by wl on 2016/8/28.
 */
public abstract class GliderHelper {


    //图片加载监听器
    public interface ImageLoadListener {

        /**
         * 图片加载成功回调
         *
         * @param uri      图片url 或资源id 或 文件
         * @param view     目标载体，不传则为空
         * @param resource 返回的资源,GlideDrawable或者Bitmap或者GifDrawable,ImageView.setImageRecourse设置
         */
        <T, K> void onLoadingComplete(T uri, ImageView view, K resource);

        /**
         * 图片加载异常返回
         *
         * @param source   图片地址、File、资源id
         * @param errorMsg 异常信息
         */
        <T> void onLoadingError(T source, String errorMsg);

    }


    /**
     * 根据上下文和 url获取 Glide的DrawableTypeRequest
     *
     * @param context 上下文
     * @param url     图片连接
     * @param <T>     Context类型
     * @param <K>     url类型
     * @return 返回DrawableTypeRequst<K> 类型
     */
    private static <T, K> DrawableTypeRequest<K> getContext(T context, K url) {
        DrawableTypeRequest<K> type = null;
        if (context instanceof android.support.v4.app.Fragment) {
            type = Glide.with((android.support.v4.app.Fragment) context).load(url);
        } else if (context instanceof android.app.Fragment) {
            type = Glide.with((android.app.Fragment) context).load(url);
        } else if (context instanceof Activity) {    //包括FragmentActivity
            type = Glide.with((Activity) context).load(url);
        } else if (context instanceof Context) {
            type = Glide.with((Context) context).load(url);
        }
        return type;
    }


    /**
     * 图片加载监听类
     *
     * @param <T> 图片链接 的类型
     * @param <K> 图片资源返回类型
     * @param <Z> 返回的图片url
     */
    private static class GlideListener<T, K, Z> implements RequestListener<T, K> {

        ImageLoadListener imageLoadListener = null;
        Z url;
        ImageView imageView = null;

        GlideListener(ImageLoadListener imageLoadListener, Z url, ImageView view) {
            this.imageLoadListener = imageLoadListener;
            this.url = url;
            this.imageView = view;
        }

        GlideListener(ImageLoadListener imageLoadListener, Z url) {
            this.imageLoadListener = imageLoadListener;
            this.url = url;
        }

        GlideListener(Z url) {
            this.url = url;
        }

        @Override
        public boolean onResourceReady(K resource, T model, Target<K> target, boolean isFromMemoryCache, boolean isFirstResource) {
            if (null != imageLoadListener) {
                if (imageView != null) {
                    imageLoadListener.onLoadingComplete(url, imageView, resource);
                } else {
                    imageLoadListener.onLoadingComplete(url, null, resource);
                }
            }

            return false;
        }

        @Override
        public boolean onException(Exception e, T model, Target<K> target, boolean isFirstResource) {
            if (imageLoadListener != null) {
                imageLoadListener.onLoadingError(url, e.getMessage());
            }
            return false;
        }
    }


    /**
     * 获取存储器上的图片,回调返回GlideDrawable
     *
     * @param context           上下文年
     * @param file              文件File
     * @param imageLoadListener 回调监听器
     */
    public static <T> void loadImage(T context, final File file, final ImageLoadListener imageLoadListener) {
        DrawableTypeRequest<File> type = getContext(context, file);
        type.listener(new GlideListener<File, GlideDrawable, File>(imageLoadListener, file));
    }

    /**
     * 获取资源中的图片，回调返回GlideDrawable
     *
     * @param context           上下文
     * @param resourceId        资源id
     * @param imageLoadListener 回调监听器
     */
    public static <T> void loadImage(T context, final int resourceId, final ImageLoadListener imageLoadListener) {
        DrawableTypeRequest<Integer> type = getContext(context, resourceId);
        type.listener(new GlideListener<Integer, GlideDrawable, Integer>(imageLoadListener, resourceId));

    }

    /**
     * 获取网络图片，回调返回 GlideDrawable
     *
     * @param context           上下文
     * @param url               图片url
     * @param imageLoadListener 回调监听器
     */
    public static <T> void loadImage(T context, final String url, final ImageLoadListener imageLoadListener) {
        DrawableTypeRequest<String> type = getContext(context, url);
        type.listener(new GlideListener<String, GlideDrawable, String>(imageLoadListener, url));
    }

    /**
     * 加载存储器上的图片到目标载体
     *
     * @param file      文件File
     * @param imageView 要显示到的图片ImageView
     */
    public static void loadImage(final File file, final ImageView imageView, final ImageLoadListener imageLoadListener) {
        Glide.with(imageView.getContext())
                .load(file)
                .listener(new GlideListener<File, GlideDrawable, File>(imageLoadListener, file, imageView))
                .into(imageView);


    }

    /**
     * 加载资源中的图片到目标载体
     *
     * @param resourceId 资源id
     * @param imageView  图片View
     */
    public static void loadImage(final int resourceId, final ImageView imageView, final ImageLoadListener imageLoadListener) {
        Glide.with(imageView.getContext())
                .load(resourceId)
                .listener(new GlideListener<Integer, GlideDrawable, Integer>(imageLoadListener, resourceId, imageView))
                .into(imageView);
    }


    /**
     * 加载圆形头像，用的是普通ImageView，有动画效果
     *
     * @param url               图片url
     * @param imageView         要显示到的ImageView
     * @param imageLoadListener 加载回调监听器
     * @param parms             设置占位符和加载失败图片
     */
    public static void loadCircleImage(final String url, final ImageView imageView, final ImageLoadListener imageLoadListener, final int... parms) {
        DrawableTypeRequest<String> type = Glide.with(imageView.getContext()).load(url);
        if (parms != null && parms.length > 0) {
            type.placeholder(parms[0]);   //占位符
            if (parms.length > 1) {
                type.error(parms[1]);    //图片加载失败显示图片
            }
        }
        type.transform(new CircleTransform(imageView.getContext()));
        type.listener(new GlideListener<String, GlideDrawable, String>(imageLoadListener, url, imageView))
                .into(imageView);
    }

    /**
     * 加载圆角图片，用的是普通ImageView，有动画效果
     *
     * @param url               图片url
     * @param imageView         要显示到的ImageView
     * @param imageLoadListener 加载回调监听器
     * @param radius            弧度
     * @param parms             设置占位符和加载失败图片
     */
    public static void loadRoundImage(final String url, final ImageView imageView, final int radius, final ImageLoadListener imageLoadListener, final int... parms) {
        DrawableTypeRequest<String> type = Glide.with(imageView.getContext()).load(url);
        if (parms != null && parms.length > 0) {
            type.placeholder(parms[0]);   //占位符
            if (parms.length > 1) {
                type.error(parms[1]);    //图片加载失败显示图片
            }
        }
        type.transform(new GlideRoundTransform(imageView.getContext(), radius));
        type.listener(new GlideListener<String, GlideDrawable, String>(imageLoadListener, url, imageView))
                .into(imageView);
    }


    /**
     * 加载网络图片到指定Imageview，支持CircleImageView
     *
     * @param url               图片url
     * @param imageView         要显示的Imageview
     * @param imageLoadListener 图片加载回调
     * @param parms             第一个是error的图片
     */
    public static void loadImage(final String url, final ImageView imageView, final ImageLoadListener imageLoadListener, final int... parms) {
        DrawableTypeRequest<String> type = Glide.with(imageView.getContext()).load(url);
        Log.d("getContext", "getContext:" + imageView.getContext());
        if (parms != null && parms.length > 0) {
            type.placeholder(parms[0]);   //占位符
            if (parms.length > 1) {
                type.error(parms[1]);    //图片加载失败显示图片
            }
        }

        //单张CircleImageView不允许动画，不然会不显示，listview适配器有setTag 才设置
//        if (imageView instanceof CircleImageView) {
//            type.dontAnimate();
//        }
        type.listener(new GlideListener<String, GlideDrawable, String>(imageLoadListener, url, imageView))
                .into(imageView);
    }


    /**
     * 加载bitmap，回调返回 Bitmap
     *
     * @param context           上下文
     * @param url               图片url
     * @param imageLoadListener 图片加载监听器
     * @param <T>               上下文类型
     */
    public <T> void loadImageBitmap(T context, final String url, final ImageLoadListener imageLoadListener) {
        DrawableTypeRequest<String> type = getContext(context, url);
        type.asBitmap().listener(new GlideListener<String, Bitmap, String>(imageLoadListener, url));
    }


    /**
     * 加载GifDrawable，回调返回 GifDrawable
     *
     * @param context           上下文
     * @param url               图片url
     * @param imageLoadListener 图片加载监听器
     */
    public static <T> void loadImageGif(T context, final String url, final ImageLoadListener imageLoadListener) {
        DrawableTypeRequest<String> type = getContext(context, url);
        type.asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new GlideListener<String, GifDrawable, String>(imageLoadListener, url));
    }


    /**
     * 加载gif图片到指定ImageView
     *
     * @param url               图片Url
     * @param imageView         图片View
     * @param imageLoadListener 图片加载监听器
     */
    public static void loadImageGif(final String url, final ImageView imageView, final ImageLoadListener imageLoadListener) {
        DrawableTypeRequest<String> type = Glide.with(imageView.getContext()).load(url);
        type.asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new GlideListener<String, GifDrawable, String>(imageLoadListener, url, imageView))
                .into(imageView);
    }


    /**
     * 释放内存
     *
     * @param context 上下文
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }


    /**
     * 取消所有正在下载或等待下载的任务。
     *
     * @param context 上下文
     */
    public static void cancelAllTasks(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 恢复所有任务
     */
    public static void resumeAllTasks(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 清除磁盘缓存
     *
     * @param context 上下文
     */
    public static void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }


    /**
     * 清除所有缓存
     *
     * @param context 上下文
     */
    public static void cleanAll(Context context) {
        clearDiskCache(context);
        clearMemory(context);
    }

    /**
     * 加载网络图片到指定Imageview，支持CircleImageView
     *
     * @param url               图片url
     * @param imageView         要显示的Imageview
     * @param imageLoadListener 图片加载回调
     * @param parms             第一个是error的图片
     */
    public static void MyLoadImage(final String url, final ImageView imageView, Context context, final ImageLoadListener imageLoadListener, final int... parms) {
        DrawableTypeRequest<String> type = Glide.with(context).load(url);
        Log.d("getContext", "getContext:" + imageView.getContext());
        if (parms != null && parms.length > 0) {
            type.placeholder(parms[0]);   //占位符
            if (parms.length > 1) {
                type.error(parms[1]);    //图片加载失败显示图片
            }
        }

        //单张CircleImageView不允许动画，不然会不显示，listview适配器有setTag 才设置
//        if (imageView instanceof CircleImageView) {
//            type.dontAnimate();
//        }
        type.listener(new GlideListener<String, GlideDrawable, String>(imageLoadListener, url, imageView))
                .into(imageView);
    }


}
