package com.hqs.common.utils;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.util.Log;
import android.view.View;

/**
 * Created by apple on 16/9/5.
 */

public class ViewUtil {

    public static void setRoundCornerToView(final View view, final float cornerRadius, boolean ripple, int backgroundColor) {
        setRippleDrawableRoundCorner(view, cornerRadius, ripple, lighterColor(backgroundColor), backgroundColor);
    }

    public static void setRoundCornerToView(final View view, final float cornerRadius, final int rippleColor, int backgroundColor) {
        setRippleDrawableRoundCorner(view, cornerRadius, true, rippleColor, backgroundColor);
    }

    private static void setRippleDrawableRoundCorner(final View view, final float cornerRadius, boolean ripple, final int backgroundColor) {
        setRippleDrawableRoundCorner(view, cornerRadius, ripple, lighterColor(backgroundColor), backgroundColor);
    }

    private static void setRippleDrawableRoundCorner(final View view, final float cornerRadius, boolean ripple, final int rippleColor, final int backgroundColor) {

        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.setShape(new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                paint.setColor(backgroundColor);
                canvas.drawRoundRect(new RectF(0, 0, view.getWidth(), view.getHeight()), cornerRadius, cornerRadius, paint);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ripple) {
                RippleDrawable newRippleDrawable = null;
                newRippleDrawable = new RippleDrawable(ColorStateList.valueOf(rippleColor), shapeDrawable, shapeDrawable);
                view.setBackground(newRippleDrawable);
            } else {
                view.setBackground(shapeDrawable);
            }
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(shapeDrawable);
            }
            else{
                view.setBackgroundColor(backgroundColor);
            }
        }

    }


    private static int lighterColor(int color) {
        int r = Color.red(color);
        int b = Color.blue(color);
        int g = Color.green(color);
        int a = Color.alpha(color);

        int offset = 20;
        a -= offset;

        int newColor = Color.argb(a, r, g, b);
        return newColor;
    }

    public static void log(String obj) {
        Log.e("====", obj);
    }

    public static void log(double obj) {
        Log.e("====", obj + "");
    }
}
