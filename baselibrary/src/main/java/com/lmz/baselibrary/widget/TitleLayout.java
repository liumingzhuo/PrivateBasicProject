package com.lmz.baselibrary.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lmz.baselibrary.R;


/**
 * 可复用标题栏
 * 在 xml 中写入 即可
 * <p/>
 */
public class TitleLayout extends RelativeLayout {

    /**
     * 标题栏返回按钮
     */
    private ImageView backButton;

    /**
     * 标题栏标题文字
     */
    private TextView titleText;

    /**
     * 标题栏右侧文字按钮，默认隐藏
     */
    private TextView rightButton;
    private ImageView rightImageButton;

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.multip_global_title,
                this,
                true
        );
        String title = null;
        boolean showBackButton = true;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleLayout);
        if (typedArray != null) {
            title = typedArray.getString(R.styleable.TitleLayout_titlelayout_title);
            if (!TextUtils.isEmpty(title)) {
                showBackButton = typedArray.getBoolean(R.styleable.TitleLayout_titlelayout_showback, false);
            }
        }


        if (view != null) {
            backButton = (ImageView) view.findViewById(R.id.title_back);
            titleText = (TextView) view.findViewById(R.id.title_text);
            rightButton = (TextView) view.findViewById(R.id.title_button);
            rightImageButton = (ImageView) view.findViewById(R.id.title_image_button);

            if (title != null) {
                setTitle(title);
            }

            setBackButtonVisibility(showBackButton);


            //为标题栏后退按钮绑定默认监听事件
            //监听事件默认调用当前Activity 的onBackPressed 方法
            backButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        activity.finish();
                    }
                }
            });
        }
        if (typedArray != null) {
            typedArray.recycle();
        }
    }

    /**
     * 设置标题栏标题文字
     *
     * @param title 标题文字
     */
    public void setTitle(CharSequence title) {

        if (title != null) {
            titleText.setText(title);
        }

    }

    public void setBackButton(int resId) {
        backButton.setVisibility(VISIBLE);
        backButton.setBackground(null);
        backButton.setImageResource(resId);
        //   backButton.setBackgroundResource(resId);
    }

    /**
     * 为右侧文字按钮设置文字并绑定监听器，同时自动显示右侧按钮
     *
     * @param buttonText                 右侧按钮文字
     * @param onRightButtonClickListener 右侧按钮 OnClickListener 监听器
     */
    public void setRightButton(
            CharSequence buttonText,
            OnClickListener onRightButtonClickListener) {

        if (onRightButtonClickListener != null && buttonText != null) {
            rightButton.setVisibility(VISIBLE);
            rightButton.setText(buttonText);
            rightButton.setOnClickListener(onRightButtonClickListener);
        }

    }

    public void setRightButton(CharSequence buttonText) {
        if (buttonText != null) {
            rightButton.setVisibility(VISIBLE);
            rightButton.setText(buttonText);
        }
    }

    public void setRightButton(int resId) {
        rightImageButton.setVisibility(VISIBLE);
        rightImageButton.setBackgroundResource(resId);
    }

    public void setRightButton(int resId, OnClickListener onRightButtonClickListener) {
        rightImageButton.setVisibility(VISIBLE);
        rightImageButton.setBackgroundResource(resId);
        if (onRightButtonClickListener != null) {
            rightImageButton.setOnClickListener(onRightButtonClickListener);
        }
    }

    public void setRightButtonOnClickListener(OnClickListener onRightButtonClickListener) {
        if (onRightButtonClickListener != null) {
            rightButton.setOnClickListener(onRightButtonClickListener);
        }
    }

    /**
     * 为标题栏后退按钮设置点击事件，由于已设置默认事件，如无特殊情况，不推荐重新设置监听器
     *
     * @param onBackButtonClickListener 后退按钮 OnClickListener 监听器
     */
    public void setOnBackButtonClickListener(OnClickListener onBackButtonClickListener) {
        if (onBackButtonClickListener != null) {
            backButton.setOnClickListener(onBackButtonClickListener);
        }
    }

    /**
     * 设置是否显示标题栏后退按钮，默认为显示
     *
     * @param visible 是否显示标题栏后退按钮
     */
    public void setBackButtonVisibility(boolean visible) {

        if (visible) {
            backButton.setVisibility(VISIBLE);
        } else {
            backButton.setVisibility(GONE);
        }

    }

}
