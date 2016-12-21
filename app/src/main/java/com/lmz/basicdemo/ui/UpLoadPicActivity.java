package com.lmz.basicdemo.ui;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lmz.baselibrary.listener.IPermissionResultListener;
import com.lmz.baselibrary.ui.BaseActivity;
import com.lmz.baselibrary.util.glide.GliderHelper;
import com.lmz.basicdemo.R;
import com.lmz.basicdemo.present.implPresenter.UpLoadPicPresent;
import com.lmz.basicdemo.present.implView.IUploadPicView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class UpLoadPicActivity extends BaseActivity implements IUploadPicView {

    @BindView(R.id.iv_upload)
    ImageView ivUpload;
    @BindView(R.id.pb_upload)
    ProgressBar pbUpload;

    UpLoadPicPresent mUpLoadPicPresent;


    //图片
    File file;

    @Override
    protected void initConvetView(Bundle saveInstanceState) {
        setContentView(R.layout.activity_up_load_pic);
    }

    @Override
    protected void initView() {
        creteFile();
    }

    @Override
    protected void initData() {
        mUpLoadPicPresent = new UpLoadPicPresent(this);
    }

    @OnClick(R.id.iv_upload)
    public void uploadPic() {
        permissionRequest("给我打开相机", new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                101, new IPermissionResultListener() {
                    @Override
                    public void onPermissionSuccess() {
                        String state = Environment.getExternalStorageState();
                        if (state.equals(Environment.MEDIA_MOUNTED)) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                            startActivityForResult(intent, 101);
                        }
                    }

                    @Override
                    public void onPermissionFaild() {

                    }
                });

    }

    /**
     * 随便写了个图片的文件
     */
    private void creteFile() {
        file = new File(Environment.getExternalStorageDirectory(), "user_pic.png");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            Luban.get(UpLoadPicActivity.this)
                    .load(file)
                    .putGear(Luban.THIRD_GEAR)
                    .asObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            Log.e("TAG", "开始了");
                        }
                    })
                    .doOnError(new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Log.e("TAG", "失败了" + throwable.getMessage());
                            throwable.printStackTrace();
                        }
                    })
                    .onErrorResumeNext(new Func1<Throwable, Observable<? extends File>>() {
                        @Override
                        public Observable<? extends File> call(Throwable throwable) {
                            return Observable.empty();
                        }
                    })
                    .subscribe(new Action1<File>() {
                        @Override
                        public void call(File file1) {
                            Log.e("TAG", "file-->" + file.getName());
                            mUpLoadPicPresent.upLoadPic(file1);
                        }
                    });
            //  }
        }
    }

    @Override
    public void UpLoadPic() {
        Log.e("TAG", "上传成功");
    }

    /**
     * 更新prograssBar
     *
     * @param prograss
     * @param totle
     */
    @Override
    public void upLoadPrograss(final long prograss, final long totle, final boolean finish) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pbUpload.setMax((int) totle);
                pbUpload.setProgress((int) prograss);
                if (finish) {
                    GliderHelper.loadImage(file, ivUpload, null);
                }
            }
        });


    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hiteProgressDialog() {

    }

    @Override
    public void showError(String error) {
        Log.e("TAG", "error" + error);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        mUpLoadPicPresent.unsubcrible();
        super.onDestroy();
    }
}
