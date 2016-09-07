package com.zhucai.example.graphicsdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.os.HandlerThread;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.zhucai.example.graphicsdemo.main.BaseActivity;

public class CircleProgressActivity_remove extends BaseActivity {

    final static public String TITLE = "CircleProgress2";

    Bitmap mCircleBitmap;
    Bitmap mHandBitmap;
    Paint mPaint = new Paint();
    SweepGradient mSweepGradient;
    Matrix mSweepMatrix = new Matrix();

    float mRotate;

    float mCurrentAngle;
    float mVelocity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE);

        mCircleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.second_progress);
        mHandBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hour_progress_hand);
    }

    @Override
    protected View createContentView() {
        return new View(this){
            @Override
            protected void onSizeChanged(int w, int h, int oldw, int oldh) {
                super.onSizeChanged(w, h, oldw, oldh);

//                mSweepGradient = new SweepGradient(mCircleBitmap.getWidth()/2, mCircleBitmap.getHeight()/2, 0x00000000, 0xff000000);
                int[] colors = new int[]{0, 0xff000000, 0};
                float[] positions = new float[]{0, 0.3f, 1f};
                mSweepGradient = new SweepGradient(mCircleBitmap.getWidth()/2, mCircleBitmap.getHeight()/2, colors, positions);
                mSweepGradient.setLocalMatrix(mSweepMatrix);

                BitmapShader bitmapShader = new BitmapShader(mCircleBitmap, TileMode.CLAMP, TileMode.CLAMP);
                ComposeShader composeShader = new ComposeShader(mSweepGradient, bitmapShader, Mode.SRC_IN);
                mPaint.setShader(composeShader);
            }

            @Override
            protected void onDraw(Canvas canvas) {
                canvas.translate((getWidth() - mCircleBitmap.getWidth())/2, (getHeight()-mCircleBitmap.getHeight())/2);

                int dx = mCircleBitmap.getWidth()/2;
                int dy = mCircleBitmap.getHeight()/2;
                mSweepMatrix.reset();
//                mSweepMatrix.preTranslate(dx, dy);
//                mSweepMatrix.preRotate(mCurrentAngle);
//                mSweepMatrix.preTranslate(-dx, -dy);
//                mSweepGradient.setLocalMatrix(mSweepMatrix);

//                canvas.drawPaint(mPaint);
//                canvas.drawArc(new RectF(0, 0, mCircleBitmap.getWidth(), mCircleBitmap.getHeight()),
//                        0, 80, true, mPaint);

                canvas.drawRect(-200, -200, 200, 200, mPaint);

                canvas.rotate(mCurrentAngle + 90, mHandBitmap.getWidth()/2, mHandBitmap.getHeight()/2);
                canvas.drawBitmap(mHandBitmap, 0, 0, null);

                invalidate();
            }

            private float mDownAngle;
            @Override
            public boolean onTouchEvent(MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        mDownAngle = calcAngle(event.getX(), event.getY());
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float angle = calcAngle(event.getX(), event.getY());
                        mVelocity = Math.abs(angle - mCurrentAngle) % 360 * 2 + 5;
                        mCurrentAngle = angle;
                        break;

                    case MotionEvent.ACTION_UP:
                        break;
                }
                invalidate();
                return true;
            }

            private float calcAngle(float x, float y) {
                float centerX = getWidth()/2f;
                float centerY = getHeight()/2f;
                return (float) Math.toDegrees(Math.atan2(y - centerY, x - centerX));
            }
        };
    }
}
