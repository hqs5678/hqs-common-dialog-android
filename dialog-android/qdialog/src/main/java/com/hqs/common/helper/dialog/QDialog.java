package com.hqs.common.helper.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hqs.common.utils.ScreenUtils;
import com.hqs.common.utils.ViewUtil;

import java.lang.ref.WeakReference;

/**
 * Created by apple on 2016/10/27.
 */

public class QDialog {

    private WeakReference<Activity> activityWeakReference;

    private DialogParam dialogParam;
    private QDialogViewComponent dialogViewComponent;

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

    public QDialog show(String message, OnDialogClickListener onDialogClickListener) {
        if (dialogParam.onShowing){
            return this;
        }
        dialogParam.message = message;
        dialogParam.dialogClickListener = onDialogClickListener;
        dialogParam.onShowing = true;



        Activity activity = activityWeakReference.get();
        dialogViewComponent = new QDialogViewComponent(ViewUtil.getRootView(activity));
        dialogViewComponent.onDialogViewListener = new OnDialogViewListener() {
            @Override
            public void onDismiss() {
                dismiss();
            }
        };
        return this;
    }

    // 添加返回按钮点击事件
    // 需要在调用者的activity中调用
    public boolean onBackPressed() {
        if (dialogViewComponent != null) {
            if (dialogParam.cancelable){
                dialogViewComponent.onDismiss();
            }
            return true;
        }
        return false;
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
        dialogViewComponent = null;
        dialogParam = null;
        activityWeakReference = null;
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

    public QDialog setMessageTextColor(int color) {
        dialogParam.messageTextColor = color;
        return this;
    }

    /**
     * 设置背景颜色
     * @param color
     * @return
     */

    public QDialog setBackgroundColor(int color) {
        dialogParam.backgroundColor = color;
        return this;
    }

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


    public class QDialogViewComponent {

        private Context context;
        private ViewGroup parent;
        private RootView rootView;
        private ViewGroup contentView;
        private CardView dialogView;
        private Button singleButton;
        private Button leftButton;
        private Button rightButton;
        private TextView tvMessage;
        private TextView tvDivider0;
        private TextView tvDivider1;
        private View bgView;
        private OnDialogViewListener onDialogViewListener;
        private View.OnClickListener onDismissClickListener;
        private float n = 8;
        private float originS = 0;
        private float originX = 0;
        private float originY = 0;

        public QDialogViewComponent(ViewGroup parent) {
            this.parent = parent;
            this.context = parent.getContext();
            setupRootView();
            setupContentView();
            enter();
        }

        /**
         * 设置根视图
         */
        private void setupRootView() {
            rootView = new RootView(context);
            parent.addView(rootView);

            ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams();
            layoutParams.width = parent.getWidth();
            layoutParams.height = parent.getHeight();
            rootView.setLayoutParams(layoutParams);

            if (parent instanceof LinearLayout){
                LinearLayout linearLayout = (LinearLayout) parent;
                if (linearLayout.getOrientation() == LinearLayout.HORIZONTAL){
                    originX = -1 * parent.getWidth();
                    rootView.setX(originX);
                }
                else{
                    originY = -1 * parent.getHeight();
                    rootView.setY(originY);
                }
            }


            rootView.setOnDetachedFromWindow(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            });

            bgView = new View(context);
            bgView.setAlpha(0);
            bgView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            rootView.addView(bgView);

            if (dialogParam.backgroundColor == -1){
                bgView.setBackgroundResource(R.color.q_dialogBackgroundColor);
            }
            else{
                bgView.setBackgroundColor(dialogParam.backgroundColor);
            }

            onDismissClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (dialogParam != null && dialogParam.cancelable) {
                        if (dialogParam.dialogClickListener != null) {
                            dialogParam.dialogClickListener.onCancel();
                        }
                        onDismiss();
                    }
                }
            };
            bgView.setOnClickListener(onDismissClickListener);
        }

        /**
         * 初始化对话框
         */
        private void setupContentView() {

            if (dialogParam.margin == 0){
                dialogParam.margin = (int) (30 * ScreenUtils.density(context));
            }

            LayoutInflater inflater = LayoutInflater.from(context);
            contentView = (ViewGroup) inflater.inflate(R.layout.q_dialog_layout, null);
            contentView.setAlpha(0);
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
            dialogView = (CardView) contentView.findViewById(R.id.card_view);


            leftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (rootView.isEnabled()) {
                        if (dialogParam != null && dialogParam.dialogClickListener != null) {
                            dialogParam.dialogClickListener.onClickLeftButton();
                        }
                        onDismiss();
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
                        onDismiss();
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
                        onDismiss();
                    }
                }
            });

            if (dialogParam.contentBackgroundColor == -1) {
                dialogParam.contentBackgroundColor = context.getResources().getColor(R.color.q_dialogContentBackgroundColor);
            }
            if (dialogParam.buttonRippleColor == -1){
                dialogParam.buttonRippleColor = context.getResources().getColor(R.color.q_dialogButtonRippleColor);
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
            dialogView.setCardBackgroundColor(dialogParam.contentBackgroundColor);
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
            if (dialogParam.messageTextColor != -1) {
                tvMessage.setTextColor(dialogParam.messageTextColor);
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
            originS = ScreenUtils.screenW(context) / (ScreenUtils.screenW(context) - dialogParam.margin * 2) + 0.1f;
            contentView.setScaleX(originS);
            contentView.setScaleY(originS);
            rootView.setEnabled(false);

            rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    contentView.postOnAnimation(new AnimRunnable());
                }
            });
        }

        private class AnimRunnable implements Runnable{

            float minS = 0.001f;
            float maxS = 0.054f;
            @Override
            public void run() {

                float s = contentView.getScaleX();
                float step = (s - 1) / n;
                if (step < minS){
                    step = minS;
                }
                else if (step > maxS){
                    step = maxS;
                }
                float alpha = 1 - (s - 1) / (originS - 1);
                bgView.setAlpha(alpha);
                contentView.setAlpha(alpha);

                s = s - step;

                contentView.setScaleX(s);
                contentView.setScaleY(s);
                if (s > 1){
                    contentView.postOnAnimationDelayed(new AnimRunnable(), 10);
                }
                else{
                    rootView.setEnabled(true);
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
            else{
                leftButton.setVisibility(View.VISIBLE);
                tvDivider1.setVisibility(View.VISIBLE);
                rightButton.setVisibility(View.VISIBLE);
                singleButton.setVisibility(View.INVISIBLE);
            }
        }

        /**
         * 在finish时调用的方法
         * 设置退出的动画
         */
        private void onDismiss() {
            if (dialogParam == null || !rootView.isEnabled()){
                return;
            }
            rootView.setEnabled(false);
            rootView.clearAnimation();

            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    rootView.setEnabled(false);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    parent.removeView(rootView);
                    onDialogViewListener.onDismiss();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            float x = originX * 0.5f;
            float y = originY * 0.5f;
            if (x == 0){
                x = rootView.getWidth() * 0.5f;
            }
            if (y == 0){
                 y = rootView.getHeight() * 0.5f;
            }

            Animation scaleAnim = new ScaleAnimation(1, originS, 1, originS, x, y);

            AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(animation);
            animationSet.addAnimation(scaleAnim);
            animationSet.setFillAfter(true);
            animationSet.setDuration(230);


            animationSet.setInterpolator(context, R.anim.decelerate_factor_interpolator_qs);

            rootView.setAnimation(animationSet);
            animation.start();
        }

    }

    /**
     * 保存dialog的属性
     */
    private class DialogParam {
        private int margin = 0;
        private String message = null;
        private String leftButtonText = null;
        private String rightButtonText = null;
        private String singleButtonText = null;
        private int leftButtonTextColor = -1;
        private int rightButtonTextColor = -1;
        private int messageTextColor = -1;
        private int singleButtonTextColor = -1;
        private int dividerColor = -1;
        private int dividerHeight = -1;
        private boolean cancelable = false;
        private boolean isSingleButtonMode = false;
        private int contentBackgroundColor = -1;
        private int backgroundColor = -1;
        private int buttonRippleColor = -1;
        private OnDialogClickListener dialogClickListener = null;
        private boolean onShowing = false;
    }

    private interface OnDialogViewListener {
        void onDismiss();
    }
}

