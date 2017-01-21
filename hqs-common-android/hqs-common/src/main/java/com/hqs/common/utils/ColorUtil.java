package com.hqs.common.utils;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;

import java.util.Random;

/**
 * Created by super on 2016/12/26.
 */

public class ColorUtil {

    public static ColorStateList colorStateList(int normal) {
        return colorStateList(normal, normal);
    }

    public static ColorStateList colorStateList(int normal, int pressed) {
        return colorStateList(normal, pressed, pressed, pressed);
    }

    public static ColorStateList colorStateList(int normal, int pressed, int focused, int unable) {
        int[] colors = new int[]{pressed, focused, normal, focused, unable, focused};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{android.R.attr.state_selected};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    public static int randomColor(){
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public static int randomDarkColor(){
        Random random = new Random();
        return Color.argb(255, random.nextInt(100) + 5, random.nextInt(100) + 5, random.nextInt(100) + 5);
    }

    public static int randomLightColor(){
        Random random = new Random();
        return Color.argb(255, random.nextInt(156) + 100, random.nextInt(156) + 100, random.nextInt(156) + 100);
    }

    public static Drawable linearGradient(Point startPoint, Point endPoint, int[] colors, float[] positions, Shader.TileMode mode){

        MyColorDrawable colorDrawable = new MyColorDrawable();
        colorDrawable.type = MyColorDrawable.TYPE_LINEAR_GRADIENT;
        colorDrawable.startPoint = startPoint;
        colorDrawable.endPoint = endPoint;
        colorDrawable.colors = colors;
        colorDrawable.positions = positions;
        colorDrawable.tileMode = mode;

        return colorDrawable;
    }


    public static Drawable linearGradient(Point startPoint, Point endPoint, int[] colors, float[] positions){

        return linearGradient(startPoint, endPoint, colors, positions, Shader.TileMode.MIRROR);
    }

    public static Drawable linearGradientVertical(int[] colors, float[] positions){

        Point startPoint = new Point(0, 0);
        Point endPoint = null;

        return linearGradient(startPoint, endPoint, colors, positions, Shader.TileMode.MIRROR);
    }

    public static Drawable linearGradientHorizontal(int[] colors, float[] positions){

        Point startPoint = null;
        Point endPoint = new Point(0, 0);

        return linearGradient(startPoint, endPoint, colors, positions, Shader.TileMode.MIRROR);
    }

    public static Drawable linearGradientVertical(int color1, int color2){

        Point startPoint = new Point(0, 0);
        Point endPoint = null;
        int[] colors = new int[]{color1, color2};
        float[] positions = new float[]{0, 1};
        return linearGradient(startPoint, endPoint, colors, positions, Shader.TileMode.MIRROR);
    }

    public static Drawable linearGradientHorizontal(int color1, int color2){

        Point startPoint = null;
        Point endPoint = new Point(0, 0);
        int[] colors = new int[]{color1, color2};
        float[] positions = new float[]{0, 1};
        return linearGradient(startPoint, endPoint, colors, positions, Shader.TileMode.MIRROR);
    }


    public static Drawable radialGradient(Point centerPoint, float radius, int[] colors, float[] positions, Shader.TileMode mode){

        MyColorDrawable colorDrawable = new MyColorDrawable();
        colorDrawable.type = MyColorDrawable.TYPE_RADIAL_GRADIENT;
        colorDrawable.centerPoint = centerPoint;
        colorDrawable.radius = radius;
        colorDrawable.colors = colors;
        colorDrawable.positions = positions;
        colorDrawable.tileMode = mode;
        return colorDrawable;
    }

    public static Drawable radialGradient(Point centerPoint, float radius, int[] colors, float[] positions){

        return radialGradient(centerPoint, radius, colors, positions, Shader.TileMode.CLAMP);
    }

    public static Drawable sweepGradient(Point centerPoint, int[] colors, float[] positions){

        MyColorDrawable colorDrawable = new MyColorDrawable();
        colorDrawable.type = MyColorDrawable.TYPE_SWEEP_GRADIENT;
        colorDrawable.centerPoint = centerPoint;
        colorDrawable.colors = colors;
        colorDrawable.positions = positions;
        return colorDrawable;
    }



    private static class MyColorDrawable extends Drawable{

        private int alpha = 255;  // [0...255]
        private Paint mPaint;
        private Point startPoint;
        private Point endPoint;
        private int[] colors;
        private float[] positions;
        private Point centerPoint;
        private float radius;
        private Shader.TileMode tileMode = Shader.TileMode.MIRROR;

        final static int TYPE_LINEAR_GRADIENT = 0;
        final static int TYPE_RADIAL_GRADIENT = 1;
        final static int TYPE_SWEEP_GRADIENT = 2;

        private int type = TYPE_LINEAR_GRADIENT;

        public MyColorDrawable(){
            mPaint = new Paint();
        }

        @Override
        public void draw(Canvas canvas) {
            Rect bounds = getBounds();
            // vertical
            if(startPoint != null && startPoint.x == 0 && startPoint.y == 0 && endPoint == null){
                endPoint = new Point(0, bounds.height());
            }
            else if(startPoint == null && endPoint != null && endPoint.x == 0 && endPoint.y == 0){
                // horizontal
                startPoint = new Point(0, 0);
                endPoint.x = bounds.width();
            }
            else {
                if (startPoint == null){
                    startPoint = new Point(0, 0);
                }
                if (endPoint == null) {
                    endPoint = new Point(bounds.width(), bounds.height());
                }
            }
            if (type == TYPE_SWEEP_GRADIENT){
                SweepGradient sg = new SweepGradient(centerPoint.x, centerPoint.y, colors, positions);
                mPaint.setShader(sg);
            }
            else if (type == TYPE_RADIAL_GRADIENT){
                RadialGradient rg = new RadialGradient(centerPoint.x, centerPoint.y, radius, colors, positions, tileMode);
                mPaint.setShader(rg);
            }
            else {
                LinearGradient lg=new LinearGradient(startPoint.x, startPoint.y, endPoint.x, endPoint.y, colors, positions, tileMode);
                mPaint.setShader(lg);
            }
            mPaint.setAlpha(alpha);
            canvas.drawRect(getBounds(), mPaint);
        }

        @Override
        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }

        @Override
        public int getAlpha() {
            return alpha;
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return PixelFormat.OPAQUE;
        }
    }


}
