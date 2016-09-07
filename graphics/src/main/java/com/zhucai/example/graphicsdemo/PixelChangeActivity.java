package com.zhucai.example.graphicsdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.zhucai.example.graphicsdemo.main.BaseActivity;

import java.util.Arrays;

public class PixelChangeActivity extends BaseActivity {

    final static public String TITLE = "PixelChange 散落";

    int[] mOriginalPixels;
    int[] mAnimationPixels;
    int[] mPixelsXVelocity;
    int[] mPixelsYVelocity;
    Bitmap mBitmap;
    ValueAnimator mAnimator = ValueAnimator.ofFloat(1f, 0f);

    final int minXVelocity = -2;
    final int maxXVelocity = 2;
    final int minYVelocity = 1;
    final int maxYVelocity = 5;

    private int random(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    private void initPixelsVelocity() {
        final int width = mBitmap.getWidth();
        final int height = mBitmap.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int index = y * width + x;
                int xVelocity = random(minXVelocity, maxXVelocity);
                int yVelocity = random(minYVelocity, maxYVelocity);
                mPixelsXVelocity[index] = xVelocity;
                mPixelsYVelocity[index] = yVelocity;
            }
        }
    }

    private void updatePixels() {
        Arrays.fill(mAnimationPixels, 0x00000000);
        final int width = mBitmap.getWidth();
        final int height = mBitmap.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int index = y * width + x;
                int xVelocity = mPixelsXVelocity[index];
                int yVelocity = mPixelsYVelocity[index];

                int goX = x+xVelocity*mAnimationTick;
                int goY = y+yVelocity*mAnimationTick;
                if (goY >= height || goY < 0 || goX >= width || goX < 0) {
                    continue;
                }
                int goIndex = goY * width + goX;
                mAnimationPixels[goIndex] = mOriginalPixels[index];
            }
        }
    }

    private int mAnimationTick;
    @Override
    protected View createContentView() {
        setTitle(TITLE + " ：点击");

        mAnimator.setDuration(3000);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tree);
        mOriginalPixels = new int[mBitmap.getWidth()*mBitmap.getHeight()];
        mAnimationPixels = new int[mOriginalPixels.length];
        mPixelsXVelocity = new int[mOriginalPixels.length];
        mPixelsYVelocity = new int[mOriginalPixels.length];

        mBitmap.getPixels(mOriginalPixels, 0, mBitmap.getWidth(), 0, 0, mBitmap.getWidth(), mBitmap.getHeight());

        return new View(this){
            @Override
            protected void onDraw(Canvas canvas) {
                canvas.translate((getWidth() - mBitmap.getWidth())/2, (getHeight()-mBitmap.getHeight())/2);
                if (mAnimator.isRunning()) {
                    mAnimationTick++;
                    updatePixels();
                    canvas.drawBitmap(mAnimationPixels, 0, mBitmap.getWidth(), 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), true, null);
                    invalidate();
                } else {
                    canvas.drawBitmap(mOriginalPixels, 0, mBitmap.getWidth(), 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), true, null);
                }
            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        initPixelsVelocity();
                        mAnimationTick = 0;
                        mAnimator.start();
                        invalidate();
                        break;
                }
                return true;
            }
        };
    }

}
