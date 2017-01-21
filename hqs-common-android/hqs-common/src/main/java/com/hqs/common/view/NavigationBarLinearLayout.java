package com.hqs.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hqs.common.R;

/**
 * Created by apple on 2016/10/12.
 */

public class NavigationBarLinearLayout extends LinearLayout {

    public NavigationBar navigationBar;
    private Context context;

    public NavigationBarLinearLayout(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public NavigationBarLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        initView();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavigationBarLinearLayout);
        int nvBackgroundColor = a.getColor(R.styleable.NavigationBarLinearLayout_navigationBarBackgroundColor, Color.BLUE);
        int nvHeight = (int) a.getDimension(R.styleable.NavigationBarLinearLayout_navigationBarHeight, 60);
        String title = a.getString(R.styleable.NavigationBarLinearLayout_navigationBarTitle);
        a.recycle();

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, nvHeight);
        navigationBar.setLayoutParams(params);
        navigationBar.setBackgroundColor(nvBackgroundColor);
        navigationBar.setTitle(title);
    }

    private void initView(){
        navigationBar = new NavigationBar(context);

        addView(navigationBar);
    }


}
