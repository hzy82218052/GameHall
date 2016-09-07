package com.zhucai.example.graphicsdemo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.zhucai.example.graphicsdemo.main.BaseActivity;

public class MatrixPolyToPolyActivity extends BaseActivity {

    final static public String TITLE = "Matrix PolyToPoly 图片缩放变形";

    Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG|Paint.ANTI_ALIAS_FLAG);
    Bitmap mBitmap;
    float[] mIdentityMatrix = new float[3*3];
    float[] mMatrixValue = new float[3*3];
    Matrix mMatrix = new Matrix();
    Matrix mMatrixBack = new Matrix();
    Matrix mPolyToPolyMatrix = new Matrix();

    SparseArray<Float> mXData = new SparseArray<Float>();
    SparseArray<Float> mYData = new SparseArray<Float>();

    float[] mPointsSrc = new float[8];
    float[] mPointsDst = new float[8];

    ValueAnimator mBackAnimator;

    @Override
    protected View createContentView() {
        setTitle(TITLE+" :触摸，最多四根手指");
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.picture);
        new Matrix().getValues(mIdentityMatrix);

        return new View(this){
            @Override
            public void draw(Canvas canvas) {
                canvas.drawBitmap(mBitmap, mMatrix, mPaint);
            }

            private void resetData(MotionEvent event, boolean remove) {
                mMatrixBack.set(mMatrix);
                int count = Math.min(4, event.getPointerCount());
                for (int i = 0; i < count; i++) {
                    int id = event.getPointerId(i);
                    mXData.put(id, event.getX(i));
                    mYData.put(id, event.getY(i));
                }
                if (remove) {
                    mXData.remove(event.getPointerId(event.getActionIndex()));
                    mYData.remove(event.getPointerId(event.getActionIndex()));
                }
            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN: {
                        if (mBackAnimator != null && mBackAnimator.isRunning()) {
                            mBackAnimator.cancel();
                        }
                        resetData(event, false);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        int useCount = 0;
                        for (int i = 0; i < event.getPointerCount(); i++) {
                            int id = event.getPointerId(i);
                            if (mXData.indexOfKey(id) < 0) {
                                continue;
                            }
                            mPointsSrc[useCount*2] = mXData.get(id);
                            mPointsSrc[useCount*2+1] = mYData.get(id);
                            mPointsDst[useCount*2] = event.getX(i);
                            mPointsDst[useCount*2+1] = event.getY(i);
                            useCount++;
                            if (useCount == 4) break;
                        }

                        if (mPolyToPolyMatrix.setPolyToPoly(mPointsSrc, 0, mPointsDst, 0, useCount)) {
                            mMatrix.set(mMatrixBack);
                            mMatrix.postConcat(mPolyToPolyMatrix);
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP: {
                        resetData(event, true);

                        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                            final float[] mMatrixValueInit = new float[3*3];
                            mMatrix.getValues(mMatrixValueInit);
                            mBackAnimator = ValueAnimator.ofFloat(1f, 0f);
                            mBackAnimator.setDuration(3000);
                            mBackAnimator.addUpdateListener(new AnimatorUpdateListener() {
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    float ratio = (Float)animation.getAnimatedValue();
                                    for (int i = 0; i < mMatrixValue.length; i++) {
                                        float offset = mMatrixValueInit[i] - mIdentityMatrix[i];
                                        mMatrixValue[i] = mIdentityMatrix[i] + ratio * offset;
                                    }
                                    mMatrix.setValues(mMatrixValue);
                                    invalidate();
                                }
                            });
                            mBackAnimator.start();
                        }
                        break;
                    }
                }

                invalidate();

                return true;
            }
        };
    }
}
