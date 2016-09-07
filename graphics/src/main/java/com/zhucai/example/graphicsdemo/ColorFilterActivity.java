package com.zhucai.example.graphicsdemo;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.zhucai.example.graphicsdemo.main.BaseActivity;

public class ColorFilterActivity extends BaseActivity {

    final static public String TITLE = "ColorFilter使用示例";

    Bitmap mIcon;
    Paint mPaint1 = new Paint();
    Paint mPaint2 = new Paint();
    Paint mPaint3 = new Paint();

    @Override
    protected View createContentView() {
        setTitle(TITLE+" ：触摸按下");
        mIcon = BitmapFactory.decodeResource(getResources(), R.drawable.test_icon);

        return new View(this){
            @Override
            public void draw(Canvas canvas) {
                canvas.drawColor(0xffbbffbb);

                canvas.drawBitmap(mIcon, 0, 0, mPaint1);

                canvas.translate(mIcon.getWidth()*2, 0);
                canvas.drawBitmap(mIcon, 0, 0, mPaint2);

                canvas.translate(mIcon.getWidth()*2, 0);
                canvas.drawBitmap(mIcon, 0, 0, mPaint3);
            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPaint1.setColorFilter(new LightingColorFilter(0xff555555, 0x00000000));
                        mPaint2.setColorFilter(new PorterDuffColorFilter(0xAA000000, Mode.SRC_ATOP));
                        mPaint3.setColorFilter(new ColorMatrixColorFilter(new float[]{
                                0.3f, 0f, 0f, 0f, 0f,
                                0f, 0.3f, 0f, 0f, 0f,
                                0f, 0f, 0.3f, 0f, 0f,
                                0f, 0f, 0f, 1f, 0f,
                        }));
                        break;

                    case MotionEvent.ACTION_UP:
                        // 简单的setColorFilter(null)就可以，这里是为了演示用法
                        mPaint1.setColorFilter(new LightingColorFilter(0xffffffff, 0));
                        mPaint2.setColorFilter(new PorterDuffColorFilter(0xffffffff, Mode.DST_IN));
                        mPaint3.setColorFilter(new ColorMatrixColorFilter(new float[]{
                                1f, 0f, 0f, 0f, 0f,
                                0f, 1f, 0f, 0f, 0f,
                                0f, 0f, 1f, 0f, 0f,
                                0f, 0f, 0f, 1f, 0f,
                        }));
                        break;
                }
                invalidate();
                return true;
            }
        };
    }
}
