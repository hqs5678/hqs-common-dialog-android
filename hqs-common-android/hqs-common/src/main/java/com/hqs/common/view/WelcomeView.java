package com.hqs.common.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hqs.common.R;

import java.util.ArrayList;

/**
 * Created by apple on 16/8/31.
 */

public class WelcomeView extends RelativeLayout {

    private final String tag = "WelcomeView";

    private ViewPager viewPager;
    private ViewPagerAapter adapter;
    private Context context;
    private PageControlView pageControlView;
    private RoundButton button;

    private ButtonClickHandle buttonClickHandle;
    private PageChangeHandle pageChangeHandle;

    private ArrayList<Integer> imagesRes = new ArrayList<>();

    public WelcomeView(Context context) {
        super(context);
        this.context = context;
        setup();
    }

    public WelcomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setup();
    }

    private void setup() {

        viewPager = new ViewPager(this.context);
        adapter = new ViewPagerAapter();
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int prePosition = -1;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                pageControlView.onPageScrolled(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                pageControlView.setCurrentPageIndex(position);
                if (pageChangeHandle != null){
                    pageChangeHandle.onPageChanged(position);
                }

                Animation animation = null;

                if (pageControlView.currentPageIndex == imagesRes.size() - 1){
                    animation = new AlphaAnimation(0, 1);
                    button.setEnabled(true);
                }
                else{
                    button.setEnabled(false);
                    if(prePosition == imagesRes.size() - 1){
                        animation = new AlphaAnimation(1, 0);
                    }
                }
                if (animation != null){
                    animation.setDuration(400);
                    animation.setFillAfter(true);

                    button.startAnimation(animation);
                }


                log("onPageSelected   " + position );

                prePosition = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        this.addView(viewPager);

        pageControlView = new PageControlView(context);
        this.addView(pageControlView);

        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        float density = dm.density;

        // 添加按钮
        button = new RoundButton(context);
        button.setText("开始体验");
        LayoutParams params = new LayoutParams((int) (120 * density), (int) (38 * density));
        params.bottomMargin = (int) (100 * density);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        button.setPadding(1, 1, 1, 1);
        button.setLayoutParams(params);
        this.addView(button);

        // 刚启动时 隐藏按钮
        Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(1);
        animation.setFillAfter(true);
        button.startAnimation(animation);

        // 设置按钮事件
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonClickHandle != null){
                    buttonClickHandle.onClickButton((RoundButton) v);
                }
            }
        });

        button.setEnabled(false);
    }

    public ArrayList<Integer> getImagesRes() {
        return imagesRes;
    }

    public void setImagesRes(ArrayList<Integer> imagesRes) {
        if (imagesRes == null) {
            return;
        }
        this.imagesRes = imagesRes;
        this.adapter.notifyDataSetChanged();
        this.pageControlView.pageCount = imagesRes.size();
    }

    public void setTintColor(int color){
        this.pageControlView.tintColor = color;
    }

    public void setSelectTintColor(int color){
        this.pageControlView.selectedTintColor = color;
    }

    public void setPageChangeHandle(PageChangeHandle pageChangeHandle) {
        this.pageChangeHandle = pageChangeHandle;
    }

    public void setButtonClickHandle(ButtonClickHandle buttonClickHandle){
        this.buttonClickHandle = buttonClickHandle;
    }

    public RoundButton getButton() {
        return button;
    }


    class ViewPagerAapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imagesRes.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ViewPagerItem item = new ViewPagerItem(context);
            item.setBackgroundResource(imagesRes.get(position));
            container.addView(item);

            return item;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    class ViewPagerItem extends ImageView {

        public ViewPagerItem(Context context) {
            super(context);
        }
    }


    class PageControlView extends View {

        public int pageCount;
        public int currentPageIndex = 0;

        public float spacing = 0;
        public float pointRadius = 0;

        public int tintColor = Color.LTGRAY;
        public int selectedTintColor = Color.DKGRAY;
        public Paint paint;
        public float positionOffset;

        public PageControlView(Context context) {
            super(context);

            setup();
        }

        private void setup() {

            pointRadius = getResources().getDimension(R.dimen.pointRadius);
            spacing = pointRadius;

            paint = new Paint();
            paint.setAntiAlias(true);
        }


        @Override
        protected void onDraw(Canvas canvas) {

            // 画小圆
            float originX = (float) ((this.getWidth() - (pointRadius * 2 * pageCount + spacing * (pageCount - 1))) * 0.5 + pointRadius);
            float cx = 0;
            float cy = (float) (this.getHeight() - this.getWidth() * 0.15);
            for (int i = 0; i < pageCount; i++) {
                cx = originX + i * (pointRadius * 2 + spacing);
                paint.setColor(tintColor);
                canvas.drawCircle(cx, cy, pointRadius, paint);
            }
            cx = originX + (currentPageIndex + positionOffset) * (pointRadius * 2 + spacing);
            paint.setColor(selectedTintColor);
            canvas.drawCircle(cx, cy, pointRadius, paint);
        }

        public void onPageScrolled(int position, float positionOffset) {

            if (position < currentPageIndex) {
                this.positionOffset =  positionOffset - 1;
            } else {
                this.positionOffset = positionOffset;
            }

            invalidate();
        }

        public void setCurrentPageIndex(int currentPageIndex) {
            this.currentPageIndex = currentPageIndex;
            this.positionOffset = 0;
            invalidate();
        }
    }

    public interface PageChangeHandle {
        void onPageChanged(int position);
    }

    public interface ButtonClickHandle {
        void onClickButton(RoundButton button);
    }

    public class RoundButton extends Button{

        private Paint paint;
        private int normalColor;

        public RoundButton(Context context) {
            super(context);
            paint = new Paint();
            paint.setAntiAlias(true);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public void setCornerRadius(final float cornerRadius, int backgroundColorRes) {

            try{
                paint.setColor(context.getResources().getColor(backgroundColorRes));
            } catch (Exception e){

            }


            ShapeDrawable drawable = new ShapeDrawable();
            drawable.setShape(new Shape() {
                @Override
                public void draw(Canvas canvas, Paint paint) {
                    canvas.drawRoundRect(new RectF(0, 0, RoundButton.this.getWidth(),RoundButton.this.getHeight()), cornerRadius, cornerRadius, RoundButton.this.paint);
                }
            });


            this.setBackground(drawable);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public boolean onTouchEvent(MotionEvent event) {


            if(this.isEnabled()){
                super.onTouchEvent(event);

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        this.setTextColor(getHighlightColor());
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_OUTSIDE:
                        this.setTextColor(normalColor);
                        break;
                }
                return true;
            }
            return false;
        }

        @Override
        public void setHighlightColor(int color) {
            super.setHighlightColor(color);
        }

        public void setNormalColor(int normalColor) {
            this.normalColor = normalColor;
            this.setTextColor(normalColor);
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




