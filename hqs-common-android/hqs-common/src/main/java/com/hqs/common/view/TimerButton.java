package com.hqs.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hqs.common.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by apple on 2016/10/25.
 */

public class TimerButton extends RelativeLayout {

    private int backgroundColorNormalRes = -1;
    private int backgroundColorDisableRes = -1;
    private String titleNormal;
    public int totalTime = -1;
    private int curTime = -1;

    private Context context;
    private TextView titleView;

    public Handler handler = null;

    private Timer timer;

    public TimerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setup();


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TimerButton);
        int titleColor = a.getColor(R.styleable.TimerButton_titleColor, Color.BLUE);
        totalTime = a.getInteger(R.styleable.TimerButton_totalTime, 60);
        titleNormal = a.getString(R.styleable.TimerButton_titleNormal);
        a.recycle();

        titleView.setTextColor(titleColor);
        titleView.setText(titleNormal);

    }

    public TimerButton(Context context) {
        super(context);
        this.context = context;
        setup();
    }

    private void setup(){
        titleView = new TextView(context);
        this.addView(titleView);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT, TRUE);
        titleView.setLayoutParams(params);

        this.setBackgroundColor(Color.GREEN);
    }

    public void startTimer(){

        if (handler == null){
            Log.e("timerButton", "handler is null, please set handler before call the function (startTimer())");
            return;
        }
        if (timer == null){
            timer = new Timer();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (curTime < 0){
                        curTime = totalTime;
                    }
                    String text = curTime + "s";

                    if (curTime == 0){
                        timer.cancel();
                        timer = null;
                        curTime = -1;

                        text = titleNormal;
                    }

                    final String t = text;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            setTitle(t.toLowerCase());
                        }
                    });

                    curTime --;
                }
            };
            timer.schedule(task, 0, 1000);
        }
    }

    public void startTimer(int totalTime){
        this.totalTime = totalTime;
        this.startTimer();
    }

    public int getBackgroundColorNormalRes() {
        return backgroundColorNormalRes;
    }

    public void setBackgroundColorNormalRes(int backgroundColorNormalRes) {
        this.backgroundColorNormalRes = backgroundColorNormalRes;
    }

    public int getBackgroundColorDisableRes() {
        return backgroundColorDisableRes;
    }

    public void setBackgroundColorDisableRes(int backgroundColorDisableRes) {
        this.backgroundColorDisableRes = backgroundColorDisableRes;
    }

    public String getTitleNormal() {
        return titleNormal;
    }

    public void setTitleNormal(String titleNormal) {
        this.titleNormal = titleNormal;
    }

    public void setTitle(String title){
        titleView.setText(title);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (curTime < 0){
            return super.onTouchEvent(event);
        }
        else{
            return true;
        }
    }
}
