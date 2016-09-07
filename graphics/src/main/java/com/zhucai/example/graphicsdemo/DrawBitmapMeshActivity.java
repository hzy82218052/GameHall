package com.zhucai.example.graphicsdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.*;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhucai.example.graphicsdemo.main.BaseActivity;

public class DrawBitmapMeshActivity extends BaseActivity {

    final static public String TITLE = "DrawBitmapMesh 画面扭曲";

    Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
    Bitmap mBitmap;
    final int meshWidth = 20;
    final int meshHeight = 20;
    final float[] verts = new float[(meshWidth + 1) * (meshHeight+1) * 2];

    float mTouchDownY;

    @Override
    protected View createContentView() {
        setTitle(TITLE+" :手指触摸向下拉");
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tree);
        updateMesh(0);

        return new View(this){
            @Override
            public void draw(Canvas canvas) {
                canvas.translate((getWidth()-mBitmap.getWidth())/2, (getHeight()-mBitmap.getHeight())/2);
                canvas.drawBitmapMesh(mBitmap, meshWidth, meshHeight, verts, 0, null, 0, mPaint);
            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        updateMesh(0);
                        mTouchDownY = event.getY();
                        break;
                    }

                    case MotionEvent.ACTION_MOVE: {
                        float offsetY = event.getY() - mTouchDownY;
                        updateMesh(offsetY / mBitmap.getHeight());
                        break;
                    }

                    case MotionEvent.ACTION_UP: {
                        float offsetY = event.getY() - mTouchDownY;
                        ValueAnimator animator = ValueAnimator.ofFloat(offsetY / mBitmap.getHeight(), 0);
                        animator.setInterpolator(new AnticipateOvershootInterpolator());
                        animator.addUpdateListener(new AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float ratio = (Float)animation.getAnimatedValue();
                                updateMesh(ratio);
                                invalidate();
                            }
                        });
                        animator.start();
                        break;
                    }
                }
                invalidate();
                return true;
            }
        };
    }

    void updateMesh(float ratio) {
        final float width = mBitmap.getWidth();
        final float height = mBitmap.getHeight();
        final int pointWidth = meshWidth+1;
        final int pointHeight = meshHeight+1;

        float yTwistBottom = ratio * height;

        final float xStep = width/meshWidth;
        float xMiddle = width/2;

        for (int x = 0; x < pointWidth; x++) {
            float xCoord = xStep * x;
            float xDistance = xCoord <= xMiddle ? xCoord : width - xCoord;
            float xRatio = 1f - xDistance/xMiddle;
            float yTop = yTwistBottom * (1f - xRatio*xRatio);

            for (int y = 0; y < pointHeight; y++) {
                final float yStep = (height-yTop)/meshHeight;

                float yCoord = yTop + yStep * y;

                int indexX = y * pointWidth * 2 + x * 2;
                int indexY = indexX + 1;

                verts[indexX] = xCoord;
                verts[indexY] = yCoord;
            }
        }
    }
}
