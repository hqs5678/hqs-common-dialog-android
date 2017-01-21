package com.hqs.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hqs.common.R;


/**
 * Created by apple on 16/9/29.
 */

public class NavigationBar extends RelativeLayout{

    public RelativeLayout contentView;
    public ImageView backImageView;
    public TextView leftItemView;
    public TextView rightItemView;
    public TextView titleView;
    public RelativeLayout rlLeftItem;
    public RelativeLayout rlRightItem;


    private OnClickListener onLeftItemClickListener;
    private OnClickListener onRightItemClickListener;

    public NavigationBar(Context context) {
        super(context);
        initView(context);
    }
 
    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){

        contentView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.navigation_bar_layout, null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(params);
        this.addView(contentView);

        backImageView = (ImageView) contentView.findViewById(R.id.backImageView);
        titleView = (TextView) contentView.findViewById(R.id.title);
        leftItemView = (TextView) contentView.findViewById(R.id.leftItem);
        rightItemView = (TextView) contentView.findViewById(R.id.rightItem);
        rlLeftItem = (RelativeLayout) contentView.findViewById(R.id.rl_left_item);
        rlRightItem = (RelativeLayout) contentView.findViewById(R.id.rl_right_item);

        if (rlLeftItem != null){
            rlLeftItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onLeftItemClickListener != null){
                        onLeftItemClickListener.onClick(v);
                    }
                }
            });
            rlRightItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onRightItemClickListener != null){
                        onRightItemClickListener.onClick(v);
                    }
                }
            });
        }
    }
    public void setTitle(String title){
        titleView.setText(title);
    }

    public void setLeftItem(String title){

        if (title == null || title.trim().length() == 0){
            rlLeftItem.setVisibility(View.INVISIBLE);
        }
        else{
            leftItemView.setText(title);
            backImageView.setVisibility(View.GONE);
            rlLeftItem.setVisibility(View.VISIBLE);
        }
    }

    public void setRightItem(String title){

        if (title == null || title.trim().length() == 0){
            rlRightItem.setVisibility(View.INVISIBLE);
        }
        else{
            rlRightItem.setVisibility(VISIBLE);
            rightItemView.setText(title);
        }
    }

    public OnClickListener getOnRightItemClickListener() {
        return onRightItemClickListener;
    }

    public void setOnRightItemClickListener(OnClickListener onRightItemClickListener) {
        this.onRightItemClickListener = onRightItemClickListener;
    }

    public OnClickListener getOnLeftItemClickListener() {
        return onLeftItemClickListener;
    }

    public void setOnLeftItemClickListener(OnClickListener onLeftItemClickListener) {
        this.onLeftItemClickListener = onLeftItemClickListener;
    }

    public void showBackButton(){
        rlLeftItem.setVisibility(View.VISIBLE);
        leftItemView.setVisibility(View.INVISIBLE);
    }

    public void hideBackButton(){
        rlLeftItem.setVisibility(View.INVISIBLE);
    }

}
