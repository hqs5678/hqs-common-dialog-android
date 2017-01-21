package com.hqs.common.view.tabbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hqs.common.utils.ViewUtil;

import java.util.ArrayList;

/**
 * Created by apple on 16/9/1.
 */

public class TabBarView extends RelativeLayout{

    private final String tag = "TabBarView";

    private ArrayList<TabBarItem> tabBarItems;
    private ArrayList<ItemView> itemViews;
    private Context context;
    private int currentSelectedIndex = -1;
    private OnSelectedTabBarItemListener onSelectedTabBarItemListener;
    private OnClickTabBarItemListener onClickTabBarItemListener;

    private int titleColor = Color.BLACK;
    private int barTintColor = Color.CYAN;
    private int rippleColor = Color.BLUE;
    private int selectedTitleColor = Color.LTGRAY;
    private int titleSize = 16;


    public TabBarView(Context context) {
        super(context);
        this.context = context;
        setup();
    }

    public TabBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setup();
    }

    private void setup(){

    }

    public ArrayList<TabBarItem> getTabBarItems() {
        return tabBarItems;
    }

    public void setTabBarItems(ArrayList<TabBarItem> tabBarItems) {
        if(tabBarItems == null){
            return;
        }
        this.tabBarItems = tabBarItems;

        this.removeAllViews();
        // 添加控件
        addSubViews();
    }

    public int getCurrentSelectedIndex() {
        return currentSelectedIndex;
    }

    public void setCurrentSelectedIndex(int currentSelectedIndex) {
        if(itemViews == null){
            return;
        }
        if (currentSelectedIndex >= 0 && currentSelectedIndex < itemViews.size()){

            ItemView itemView = itemViews.get(currentSelectedIndex);
            selectItemView(itemView, tabBarItems.get(currentSelectedIndex));
        }
    }

    public OnSelectedTabBarItemListener getOnSelectedTabBarItemListener() {
        return onSelectedTabBarItemListener;
    }

    public void setOnSelectedTabBarItemListener(OnSelectedTabBarItemListener onSelectedTabBarItemListener) {
        this.onSelectedTabBarItemListener = onSelectedTabBarItemListener;
    }

    public OnClickTabBarItemListener getOnClickTabBarItemListener() {
        return onClickTabBarItemListener;
    }

    public void setOnClickTabBarItemListener(OnClickTabBarItemListener onClickTabBarItemListener) {
        this.onClickTabBarItemListener = onClickTabBarItemListener;
    }

    public int getSelectedTitleColor() {
        return selectedTitleColor;
    }

    public void setSelectedTitleColor(int selectedTitleColor) {
        this.selectedTitleColor = selectedTitleColor;
    }

    public int getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    // 设置 tab item appearance
    private void setAppearance(){
        int count = itemViews.size();
        for (int i = 0;i< count; i++){
            ItemView itemView = itemViews.get(i);
            itemView.titleView.setTextSize(titleSize);
            if(i == currentSelectedIndex){
                itemView.titleView.setTextColor(selectedTitleColor);
            }
            else{
                itemView.titleView.setTextColor(titleColor);
            }
        }
    }

    public int getBarTintColor() {
        return barTintColor;
    }

    public void setBarTintColor(int barTintColor) {
        this.barTintColor = barTintColor;
    }

    public int getRippleColor() {
        return rippleColor;
    }

    public void setRippleColor(int rippleColor) {
        this.rippleColor = rippleColor;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

        // 布局子控件
        this.layoutSubviews();
    }

    private void addSubViews(){
        if(itemViews == null){
            itemViews = new ArrayList<>();
        }
        int count = tabBarItems.size();

        for (int i = 0;i < count;i ++){
            final TabBarItem item = tabBarItems.get(i);

            final ItemView itemView = new ItemView(context);
            itemView.imageView.setImageResource(item.iconRes);
            itemView.titleView.setText(item.title);
            itemView.tag = i;
            itemView.titleView.setTextColor(titleColor);
            itemView.badgeView.setText(item.badgeValue);
            itemView.setBgColor(barTintColor);
            this.addView(itemView);
            itemViews.add(itemView);

            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectItemView(itemView, item);

                    if (onClickTabBarItemListener != null){
                        onClickTabBarItemListener.onClickTabBarItem(item, itemView, itemView.tag);
                    }
                }
            });
        }

        //log("addSubViews");

    }

    private void selectItemView(ItemView itemView, TabBarItem item){
        if (itemView.isSelected){
            return;
        }
        else {
            itemView.isSelected = true;
        }
        if(onSelectedTabBarItemListener != null){
            onSelectedTabBarItemListener.onSelectedTabBarItem(currentSelectedIndex, itemView.tag);
        }

        if(currentSelectedIndex != itemView.tag && currentSelectedIndex != -1){
            ItemView iView = itemViews.get(currentSelectedIndex);
            TabBarItem item2 = this.tabBarItems.get(currentSelectedIndex);
            iView.imageView.setImageResource(item2.iconRes);
            iView.titleView.setTextColor(titleColor);
            iView.isSelected = false;
        }

        currentSelectedIndex = itemView.tag;
        itemView.imageView.setImageResource(item.selectedIconRes);
        itemView.titleView.setTextColor(selectedTitleColor);
    }

    private void layoutSubviews(){

        if (itemViews == null){
            return;
        }

        int itemH = getHeight();
        int itemW = getWidth()/tabBarItems.size();

        int count = itemViews.size();
        for (int i = 0; i < count ; i ++){
            ItemView itemView = itemViews.get(i);
            LayoutParams params = new LayoutParams(itemW, itemH);
            params.leftMargin = itemW * i;
            itemView.setLayoutParams(params);
        }

        setAppearance();
    }

    public interface OnSelectedTabBarItemListener{
        void onSelectedTabBarItem(int oldIndex, int newIndex);
    }

    public interface OnClickTabBarItemListener {
        void onClickTabBarItem(TabBarItem item, ItemView itemView, int index);
    }

    public class ItemView extends RelativeLayout{


        private ImageView imageView;
        private TextView titleView;
        private BadgeView badgeView;
        private int bgColor = Color.BLUE;
        private int rippleColor = Color.YELLOW;
        private RelativeLayout bgview;  // 为了添加ripple 效果
        private Button button;  // 背景里面的button

        private OnClickListener listener;

        private int tag;
        private boolean isSelected = false;
        // badge view 相对于图片中心的偏移量
        private float badgeViewOffsetRate = 0.3f;

        // imageView 所占的高与父控件的比值
        private float imgWHRate = 0.56f;

        public ItemView(Context context) {
            super(context);

            bgview = new RelativeLayout(context);
            imageView = new ImageView(context);
            titleView = new TextView(context);
            badgeView = new BadgeView(context);

            // 添加背景view  实现ripple 效果
            addButton();

            this.addView(bgview);
            this.addView(imageView);
            this.addView(titleView);
            this.addView(badgeView);

            titleView.setTextSize(titleSize);
            badgeView.setBackgroundColor(Color.RED);
        }

        private void addButton(){
            button = new Button(context);
            LayoutParams pButton = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            int margin =  -1 * 15;
            pButton.topMargin = margin;
            pButton.bottomMargin = margin;
            pButton.leftMargin = margin;
            pButton.rightMargin = margin;
            button.setLayoutParams(pButton);
            bgview.addView(button);

            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemView.this.listener.onClick(v);
                }
            });
        }

        @Override
        public void setOnClickListener(OnClickListener l) {
            super.setOnClickListener(l);
            this.listener = l;
        }

        public void setBgColor(int color){
            this.bgColor = color;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ViewUtil.setRoundCornerToView(button, 0, rippleColor, bgColor);
            }
            else{
                button.setBackgroundColor(bgColor);
            }
        }


        @Override
        public void setLayoutParams(ViewGroup.LayoutParams params) {
            super.setLayoutParams(params);

            LayoutParams pButton = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            bgview.setLayoutParams(pButton);


            // 设置子控件位子
            // imageView
            int margin = (int) (params.height * 0.06);
            int imgW = (int) (params.height * imgWHRate);
            LayoutParams pImg = new LayoutParams(imgW,imgW);
            pImg.addRule(CENTER_HORIZONTAL,TRUE);
            pImg.addRule(ALIGN_PARENT_TOP, TRUE);
            pImg.topMargin = margin;
            imageView.setLayoutParams(pImg);

            // titleView
            int titleH = params.height - imgW - margin;
            LayoutParams pTitle = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,titleH);
            pTitle.addRule(ALIGN_PARENT_BOTTOM, TRUE);
            pTitle.addRule(CENTER_HORIZONTAL, TRUE);
            pTitle.topMargin = margin;
            titleView.setLayoutParams(pTitle);

            // badge view
            LayoutParams pBadge = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            pBadge.addRule(ALIGN_PARENT_LEFT, TRUE);
            pBadge.addRule(ALIGN_PARENT_TOP, TRUE);
            pBadge.topMargin = margin;
            pBadge.leftMargin = (int) (params.width * 0.5 + imgW * badgeViewOffsetRate);
            badgeView.setLayoutParams(pBadge);
        }

        public void setBadgeValue(String value){
            badgeView.setText(value);
        }

    }

    // 显示badge value 的View  背景颜色 红色    圆角
    public class BadgeView extends TextView{

        private int bgColor;
        private float cornerRadius;

        private Paint paint;

        public BadgeView(Context context) {
            super(context);

            paint = new Paint();
            paint.setAntiAlias(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                this.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            }
            setTextColor(Color.WHITE);
            setTextSize((float) (titleSize * 0.66));
        }

        @Override
        public void setBackgroundColor(int color) {
            super.setBackgroundColor(color);
            this.bgColor = color;
            paint.setColor(bgColor);
        }

        @Override
        public void setLayoutParams(ViewGroup.LayoutParams params) {
            super.setLayoutParams(params);

            setCornerRadius((float) (getHeight() * 0.5));
        }

        private void setCornerRadius(final float cornerRadius) {
            this.cornerRadius = cornerRadius;

            ShapeDrawable drawable = new ShapeDrawable();
            drawable.setShape(new Shape() {
                @Override
                public void draw(Canvas canvas, Paint paint) {
                    canvas.drawRoundRect(new RectF(0, 0, BadgeView.this.getWidth(), BadgeView.this.getHeight()), cornerRadius, cornerRadius, BadgeView.this.paint);
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                this.setBackground(drawable);
            }
        }

        @Override
        public void setText(CharSequence text, BufferType type) {
            super.setText(text, type);

            if(text == null || text.length() == 0){
                this.setVisibility(INVISIBLE);
                return;
            }
            else{
                this.setVisibility(VISIBLE);
            }

            // 设置badge value 中字体的位置
            int padding = 20;
            try{
                // 数字
                Integer.parseInt(text.toString());
                setPadding(padding, 0, padding, 3);

            } catch (Exception ex){
                // 非数字
                setPadding(padding, 0, padding, 6);
                if (text.charAt(text.length() - 1) == '+'){
                    setPadding(padding, 0, padding, 5);
                }
            }
        }
    }

    // log
    private void log(String logString) {
        Log.e(tag, logString);
    }

    private void log(int log) {
        Log.e(tag, log + "");
    }

    private void log(float log) {
        Log.e(tag, log + "");
    }

    private void log(double log) {
        Log.e(tag, log + "");
    }

    private void log(Object log) {
        Log.e(tag, log.toString());
    }
}


