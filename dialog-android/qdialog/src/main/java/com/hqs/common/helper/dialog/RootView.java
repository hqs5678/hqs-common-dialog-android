package com.hqs.common.helper.dialog;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.hqs.common.utils.Log;

/**
 * Created by super on 2017/7/10.
 */

public class RootView extends RelativeLayout {

    private Runnable onDetachedFromWindow;

    public RootView(Context context) {
        super(context);
    }

    public RootView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (onDetachedFromWindow != null){
            onDetachedFromWindow.run();
        }
    }

    public void setOnDetachedFromWindow(Runnable onDetachedFromWindow) {
        this.onDetachedFromWindow = onDetachedFromWindow;
    }
}
