package com.hqs.common.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hqs.common.R;
import com.hqs.common.utils.ViewUtil;

import java.lang.ref.WeakReference;

/**
 * Created by apple on 2016/10/27.
 */

public class QDialog {

    private Activity activity;
    private static RelativeLayout contentView;
    private static OnDialogClickListener dialogClickListener;

    private static int enterAnim = R.anim.dialog_in_qs;
    private static int exitAnim = R.anim.dialog_out_qs;

    private static Button leftButton;
    private static Button rightButton;
    private TextView tvMessage;
    private TextView tvDivider0;
    private TextView tvDivider1;
    private static int margin = 40;

    private static boolean cancelable = false;
    private static int backgroundRes = -1;
    private static WeakReference<Activity> dialogActivity;

    public QDialog(Activity activity) {
        this.activity = activity;
        setup();
    }


    private void setup() {

        LayoutInflater inflater = LayoutInflater.from(activity);
        contentView = (RelativeLayout) inflater.inflate(R.layout.dialog_cancelable, null);

        leftButton = (Button) contentView.findViewById(R.id.btn_left);
        rightButton = (Button) contentView.findViewById(R.id.btn_right);
        tvMessage = (TextView) contentView.findViewById(R.id.tv_message);
        tvDivider1 = (TextView) contentView.findViewById(R.id.tv_divider1);
        tvDivider0 = (TextView) contentView.findViewById(R.id.tv_divider);

        ViewUtil.setRoundCornerToView(leftButton, 0, Color.GRAY, Color.WHITE);
        ViewUtil.setRoundCornerToView(rightButton, 0, Color.GRAY, Color.WHITE);
    }

    public QDialog setSingleButtonMode() {
        leftButton.setVisibility(View.GONE);
        tvDivider1.setVisibility(View.GONE);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rightButton.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        rightButton.setLayoutParams(layoutParams);
        return this;
    }

    public void show(String message, OnDialogClickListener onDialogClickListener) {
        tvMessage.setText(message);
        dialogClickListener = onDialogClickListener;

        Intent intent = new Intent(activity, DialogActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    public Button getLeftButton(){
        return leftButton;
    }

    public Button getRightButton(){
        return rightButton;
    }

    public void dismiss() {
        if (dialogActivity != null){
            DialogActivity activity = (DialogActivity) dialogActivity.get();
            if (activity != null){
                activity.onFinish();
                if (dialogClickListener != null) {
                    dialogClickListener.onCancel();
                }
                dialogActivity = null;
            }
        }
    }

    public OnDialogClickListener getOnDialogClickListener() {
        return dialogClickListener;
    }

    public QDialog setDividerHeight(int h) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tvDivider0.getLayoutParams();
        params.height = h;
        tvDivider0.setLayoutParams(params);

        params = (RelativeLayout.LayoutParams) tvDivider1.getLayoutParams();
        params.width = h;
        tvDivider1.setLayoutParams(params);
        return this;
    }


    public QDialog setLeftButtonText(String text){
        leftButton.setText(text);
        return this;
    }

    public QDialog setLeftButtonTextColor(int color){
        leftButton.setTextColor(color);
        return this;
    }

    public QDialog setRightButtonText(String text){
        rightButton.setText(text);
        return this;
    }

    public QDialog setRightButtonTextColor(int color){
        rightButton.setTextColor(color);
        return this;
    }

    public QDialog setAnimation(int enterAnim, int exitAnim){
        QDialog.enterAnim = enterAnim;
        QDialog.exitAnim = exitAnim;
        return this;
    }

    public QDialog setDividerColor(int color) {
        tvDivider0.setBackgroundColor(color);
        tvDivider1.setBackgroundColor(color);
        return this;
    }

    public QDialog setBackgroundRes(int res) {
        QDialog.backgroundRes = res;
        return this;
    }

    public interface OnDialogClickListener {
        void onClickRightButton();
        void onClickLeftButton();
        void onCancel();
    }

    public QDialog setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public static void destroy(){
        QDialog.backgroundRes = -1;
        QDialog.contentView = null;
        QDialog.cancelable = false;
        QDialog.dialogActivity = null;
        QDialog.leftButton = null;
        QDialog.rightButton = null;
        QDialog.dialogClickListener = null;
    }

    public void release(){
        this.activity = null;
        this.tvMessage = null;
        this.tvDivider1 = null;
        this.tvDivider0 = null;
    }



    public static class DialogActivity extends Activity {

        private RelativeLayout relativeLayout;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            dialogActivity = new WeakReference<Activity>(this);

            relativeLayout = new RelativeLayout(this);
            this.setContentView(relativeLayout);
            if (backgroundRes == -1){
                relativeLayout.setBackgroundResource(R.color.dialogBackgroundColor);
            }
            else {
                relativeLayout.setBackgroundResource(backgroundRes);
            }


            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cancelable) {
                        if (dialogClickListener != null) {
                            dialogClickListener.onCancel();
                        }
                        onFinish();
                    }
                }
            });

            relativeLayout.addView(contentView);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            layoutParams.leftMargin = margin;
            layoutParams.rightMargin = margin;
            contentView.setLayoutParams(layoutParams);
            contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            Animation animation = AnimationUtils.loadAnimation(this, enterAnim);
            relativeLayout.setAnimation(animation);
            animation.start();


            leftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogClickListener != null) {
                        dialogClickListener.onClickLeftButton();
                    }
                    onFinish();
                }
            });
            rightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogClickListener != null) {
                        dialogClickListener.onClickRightButton();
                    }
                    onFinish();
                }
            });
        }

        private void onFinish(){
            relativeLayout.clearAnimation();
            Animation animation = AnimationUtils.loadAnimation(this, exitAnim);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    finish();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            relativeLayout.setAnimation(animation);
            animation.start();

        }

        @Override
        protected void onDestroy() {
            QDialog.destroy();
            super.onDestroy();
        }

        @Override
        public void finish() {
            super.finish();
            overridePendingTransition(0, 0);
        }
    }
}
