package com.hqs.common.helper.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hqs.common.utils.DensityUtils;
import com.hqs.common.utils.Log;
import com.hqs.common.utils.ScreenUtils;
import com.hqs.common.utils.StatusBarUtil;
import com.hqs.common.utils.ViewUtil;

import java.lang.ref.WeakReference;

/**
 * Created by apple on 2016/10/27.
 */

public class QDialog {

    private static WeakReference<Activity> activityWeakReference;
    private static WeakReference<DialogActivity> dialogActivityWeakReference;

    public static DialogParam dialogParam;


    private QDialog(Activity activity) {
        if (dialogParam == null){
            dialogParam = new DialogParam();
        }
        if (dialogParam.onShowing){
            return;
        }
        activityWeakReference = new WeakReference<>(activity);
    }

    public static QDialog create(Activity activity) {
        return new QDialog(activity);
    }


    public QDialog setSingleButtonMode() {
        dialogParam.isSingleButtonMode = true;
        return this;
    }

    public void show(String message, OnDialogClickListener onDialogClickListener) {
        if (dialogParam.onShowing){
            return;
        }
        dialogParam.message = message;
        dialogParam.dialogClickListener = onDialogClickListener;
        dialogParam.onShowing = true;

        Activity activity = activityWeakReference.get();
        Intent intent = new Intent(activity, DialogActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    /**
     * 设置对话框的颜色
     * @param color
     * @return
     */
    public QDialog setContentBackgroundColor(int color) {
        dialogParam.contentBackgroundColor = color;
        return this;
    }

    public void dismiss() {
        DialogActivity dialogActivity = dialogActivityWeakReference.get();
        if (dialogActivity != null) {
            dialogActivity.onFinish();
            if (dialogParam.dialogClickListener != null) {
                dialogParam.dialogClickListener.onCancel();
            }
            dialogActivityWeakReference.clear();
            dialogActivityWeakReference = null;
        }
    }

    public OnDialogClickListener getOnDialogClickListener() {
        return dialogParam.dialogClickListener;
    }

    /**
     * 设置分割线粗细
     * @param h
     * @return
     */
    public QDialog setDividerHeight(int h) {
        dialogParam.dividerHeight = h;
        return this;
    }

    /**
     * 设置点击按钮时波纹效果颜色
     * @param color
     * @return
     */
    public QDialog setButtonRippleColor(int color) {
        dialogParam.buttonRippleColor = color;
        return this;
    }

    public QDialog setSingleButtonText(String text) {
        dialogParam.singleButtonText = text;
        return this;
    }

    public QDialog setSingleButtonTextColor(int color) {
        dialogParam.singleButtonTextColor = color;
        return this;
    }

    public QDialog setLeftButtonText(String text) {
        dialogParam.leftButtonText = text;
        return this;
    }

    public QDialog setLeftButtonTextColor(int color) {
        dialogParam.leftButtonTextColor = color;
        return this;
    }

    public QDialog setRightButtonText(String text) {
        dialogParam.rightButtonText = text;
        return this;
    }

    public QDialog setRightButtonTextColor(int color) {
        dialogParam.rightButtonTextColor = color;
        return this;
    }

    /**
     * 设置背景颜色
     * @param color
     * @return
     */
    public QDialog setDividerColor(int color) {
        dialogParam.rightButtonTextColor = color;
        return this;
    }

    public interface OnDialogClickListener {
        void onClickRightButton();

        void onClickLeftButton();

        void onCancel();
    }

    /**
     * 是否能够取消(点击按钮之外的部分)
     * @param cancelable
     * @return
     */
    public QDialog setCancelable(boolean cancelable) {
        dialogParam.cancelable = cancelable;
        return this;
    }

    /**
     * 释放资源
     */
    public static void destroy() {
        if (QDialog.activityWeakReference != null){
            QDialog.activityWeakReference.clear();
            QDialog.activityWeakReference = null;
        }
        if (QDialog.dialogActivityWeakReference != null) {
            QDialog.dialogActivityWeakReference.clear();
            QDialog.dialogActivityWeakReference = null;
        }
        QDialog.dialogParam = null;
    }


    public static class DialogActivity extends Activity {

        private RelativeLayout rootView;
        private CardView contentView;
        private Button singleButton;
        private Button leftButton;
        private Button rightButton;
        private TextView tvMessage;
        private TextView tvDivider0;
        private TextView tvDivider1;
        private View bgView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            StatusBarUtil.transparencyBar(this);
            ScreenUtils.setScreenOrientationPortrait(this);

            dialogActivityWeakReference = new WeakReference<>(this);

            setupRootView();
            setupContentView();

            enter();
        }

        /**
         * 设置根视图
         */
        private void setupRootView() {
            rootView = new RelativeLayout(this);
            this.setContentView(rootView);

            bgView = new View(this);
            bgView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            rootView.addView(bgView);

            bgView.setBackgroundResource(R.color.q_dialogBackgroundColor);

            bgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (dialogParam != null && dialogParam.cancelable) {
                        if (dialogParam.dialogClickListener != null) {
                            dialogParam.dialogClickListener.onCancel();
                        }
                        onFinish();
                    }
                }
            });
        }

        /**
         * 初始化对话框
         */
        private void setupContentView() {

            if (dialogParam.margin == 0){
                dialogParam.margin = (int) (30 * ScreenUtils.density(this));
            }

            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            contentView = (CardView) inflater.inflate(R.layout.q_dialog_layout, null);

            rootView.addView(contentView);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            layoutParams.leftMargin = dialogParam.margin;
            layoutParams.rightMargin = dialogParam.margin;
            contentView.setLayoutParams(layoutParams);
            contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            singleButton = (Button) contentView.findViewById(R.id.btn_single);
            leftButton = (Button) contentView.findViewById(R.id.btn_left);
            rightButton = (Button) contentView.findViewById(R.id.btn_right);
            tvMessage = (TextView) contentView.findViewById(R.id.tv_message);
            tvDivider1 = (TextView) contentView.findViewById(R.id.tv_divider1);
            tvDivider0 = (TextView) contentView.findViewById(R.id.tv_divider);

            leftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (rootView.isEnabled()) {
                        if (dialogParam != null && dialogParam.dialogClickListener != null) {
                            dialogParam.dialogClickListener.onClickLeftButton();
                        }
                        onFinish();
                    }

                }
            });
            rightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (rootView.isEnabled()){
                        if (dialogParam != null && dialogParam.dialogClickListener != null) {
                            dialogParam.dialogClickListener.onClickRightButton();
                        }
                        onFinish();
                    }

                }
            });
            singleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (rootView.isEnabled()){
                        if (dialogParam != null && dialogParam.dialogClickListener != null) {
                            dialogParam.dialogClickListener.onCancel();
                        }
                        onFinish();
                    }
                }
            });

            if (dialogParam.contentBackgroundColor == -1) {
                dialogParam.contentBackgroundColor = getResources().getColor(R.color.q_dialogContentBackgroundColor);
            }
            if (dialogParam.buttonRippleColor == -1){
                dialogParam.buttonRippleColor = getResources().getColor(R.color.q_dialogButtonRippleColor);
            }

            setSingleButtonMode();
            setupMessage();
            setContentBackgroundColor();
            setupButtonColor();
            setupButtonText();
            setupDividerHeight();
            setupDividerColor();
        }

        /**
         * setup message
         */
        private void setupMessage() {
            if (dialogParam.message != null) {
                tvMessage.setText(dialogParam.message);
            }
        }

        /**
         * 设置对话框颜色
         */
        private void setContentBackgroundColor() {
            contentView.setCardBackgroundColor(dialogParam.contentBackgroundColor);
            ViewUtil.setRoundCornerToView(leftButton, 0, dialogParam.buttonRippleColor,
                    dialogParam.contentBackgroundColor);
            ViewUtil.setRoundCornerToView(rightButton, 0, dialogParam.buttonRippleColor,
                    dialogParam.contentBackgroundColor);
        }

        /**
         * 设置按钮颜色
         */
        private void setupButtonColor() {
            if (dialogParam.leftButtonTextColor != -1) {
                leftButton.setTextColor(dialogParam.leftButtonTextColor);
            }
            if (dialogParam.rightButtonTextColor != -1) {
                rightButton.setTextColor(dialogParam.rightButtonTextColor);
            }
            if (dialogParam.singleButtonTextColor != -1) {
                singleButton.setTextColor(dialogParam.singleButtonTextColor);
            }
        }

        /**
         * 设置按钮文字
         */
        private void setupButtonText() {
            if (dialogParam.leftButtonText != null) {
                leftButton.setText(dialogParam.leftButtonText);
            }
            if (dialogParam.rightButtonText != null) {
                rightButton.setText(dialogParam.rightButtonText);
            }
            if (dialogParam.singleButtonText != null) {
                singleButton.setText(dialogParam.singleButtonText);
            }
        }

        /**
         * 设置分割线高度
         */
        private void setupDividerHeight() {
            if (dialogParam.dividerHeight != -1) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tvDivider0.getLayoutParams();
                params.height = dialogParam.dividerHeight;
                tvDivider0.setLayoutParams(params);

                params = (RelativeLayout.LayoutParams) tvDivider1.getLayoutParams();
                params.width = dialogParam.dividerHeight;
                tvDivider1.setLayoutParams(params);
            }
        }

        /**
         * 设置分割线颜色
         */
        private void setupDividerColor() {
            if (dialogParam.dividerColor != -1) {
                tvDivider0.setBackgroundColor(dialogParam.dividerColor);
                tvDivider1.setBackgroundColor(dialogParam.dividerColor);
            }
        }

        /**
         * 设置进入动画
         */
        private void enter() {

            originS = ScreenUtils.screenW(this) / (ScreenUtils.screenW(this) - dialogParam.margin * 2) + 0.5f;
            contentView.setScaleX(originS);
            contentView.setScaleY(originS);
            contentView.postOnAnimation(new AnimRunnable());
        }

        private float n = 6;
        private float originS = 0;
        private class AnimRunnable implements Runnable{

            float minS = 0.001f;

            @Override
            public void run() {

                float s = contentView.getScaleX();
                float step = (s - 1) / n;
                if (step < minS){
                    step = minS;
                }
                float alpha = 1 - (s - 1) / (originS - 1);
                bgView.setAlpha(alpha);
                contentView.setAlpha(alpha * 3);


                s = s - step;

                Log.print(alpha);
                contentView.setScaleX(s);
                contentView.setScaleY(s);
                if (s > 1){
                    contentView.postOnAnimationDelayed(new AnimRunnable(), 10);
                }
            }
        }

        /**
         * 只显示一个按钮
         * right button
         */
        private void setSingleButtonMode() {
            if (dialogParam.isSingleButtonMode) {
                leftButton.setVisibility(View.INVISIBLE);
                tvDivider1.setVisibility(View.INVISIBLE);
                rightButton.setVisibility(View.INVISIBLE);
                singleButton.setVisibility(View.VISIBLE);
            }
        }

        /**
         * 在finish时调用的方法
         * 设置退出的动画
         */
        private void onFinish() {
            if (dialogParam == null){
                return;
            }

            rootView.clearAnimation();

            Animation animation = AnimationUtils.loadAnimation(this, dialogParam.exitAnimRes);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    rootView.setEnabled(false);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    finish();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            rootView.setAnimation(animation);
            animation.start();

        }

        @Override
        protected void onDestroy() {
            super.onDestroy();

            QDialog.destroy();
        }

        /**
         * finish
         */
        @Override
        public void finish() {
            super.finish();
            overridePendingTransition(0, 0);
        }

        /**
         * 拦截返回按钮事件
         * @param keyCode
         * @param event
         * @return
         */
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (dialogParam == null) {
                return super.onKeyDown(keyCode, event);
            }
            if (keyCode == KeyEvent.KEYCODE_BACK && dialogParam.cancelable){
                if (this.rootView.isEnabled()){
                    this.rootView.setEnabled(false);
                    onFinish();
                }
            }
            return true;
        }


    }

    /**
     * 保存dialog的属性
     */
    private class DialogParam {
        private int exitAnimRes = R.anim.dialog_out_qs;
        private int margin = 0;
        private String message = null;
        private String leftButtonText = null;
        private String rightButtonText = null;
        private String singleButtonText = null;
        private int leftButtonTextColor = -1;
        private int rightButtonTextColor = -1;
        private int singleButtonTextColor = -1;
        private int dividerColor = -1;
        private int dividerHeight = -1;
        private boolean cancelable = false;
        private boolean isSingleButtonMode = false;
        private int contentBackgroundColor = -1;
        private int buttonRippleColor = -1;
        private OnDialogClickListener dialogClickListener = null;
        private boolean onShowing = false;
    }
}

