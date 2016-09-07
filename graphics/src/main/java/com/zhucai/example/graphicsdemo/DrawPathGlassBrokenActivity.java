package com.zhucai.example.graphicsdemo;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader.TileMode;
import android.view.MotionEvent;
import android.view.View;

import com.zhucai.example.graphicsdemo.main.BaseActivity;

public class DrawPathGlassBrokenActivity extends BaseActivity {

    final static public String TITLE = "DrawPath 碎玻璃";

    Bitmap mBitmap;
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
    final int xPathCount = 6;
    final int yPathCount = 5;

    Path[] mPaths = new Path[xPathCount*yPathCount];
    MotionData[] mMotionDatas = new MotionData[xPathCount*yPathCount];
    ValueAnimator mAnimator;
    Camera mCamera = new Camera();

    static class MotionData {
        public float moveX;
        public float moveY;
        public float rotationX;
        public float rotationY;
        public float scale;
        public float rotationXVelocity;
        public float rotationYVelocity;
        public float moveXVelocity;
        public float moveYVelocity;
        public float scaleVelocity;
    }

    private float mScale = 1f;

    @Override
    protected View createContentView() {
        setTitle(TITLE+" :点击屏幕");
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.picture);
        mPaint.setShader(new BitmapShader(mBitmap, TileMode.CLAMP, TileMode.CLAMP));
        mAnimator = ValueAnimator.ofFloat(1f, 0f);
        mAnimator.setDuration(3000);

        return new View(this){
            @Override
            public void draw(Canvas canvas) {
                canvas.translate((getWidth()-mBitmap.getWidth())/2, (getHeight()-mBitmap.getHeight())/2);
                if (mAnimator.isRunning()) {
                    final float width = mBitmap.getWidth();
                    final float height = mBitmap.getHeight();
                    final float xStep = width/xPathCount;
                    final float yStep = height/yPathCount;

                    mScale -= 0.02f;

                    for (int x = 0; x < xPathCount; x++) {
                        for (int y = 0; y < yPathCount; y++) {
                            int pathIndex = y*xPathCount+x;
                            Path path = mPaths[pathIndex];
                            MotionData motionData = mMotionDatas[pathIndex];

                            motionData.rotationX += motionData.rotationXVelocity;
                            motionData.rotationX %= 360;
                            motionData.rotationY += motionData.rotationYVelocity;
                            motionData.rotationY %= 360;
                            motionData.moveX += motionData.moveXVelocity;
                            motionData.moveY += motionData.moveYVelocity;
                            motionData.scale -= motionData.scaleVelocity;
                            if (motionData.scale <= 0) continue;

                            canvas.save();

                            canvas.translate(motionData.moveX, motionData.moveY);

                            float centerX = x * xStep+xStep/2;
                            float centerY = y * yStep +yStep/2;
                            canvas.translate(centerX, centerY);

                            mCamera.save();
                            Matrix matrix = new Matrix();
                            mCamera.rotate(motionData.rotationX, motionData.rotationY, 0);
                            mCamera.getMatrix(matrix);
                            canvas.concat(matrix);
                            mCamera.restore();

                            canvas.scale(motionData.scale, motionData.scale);
                            canvas.translate(-centerX, -centerY);

                            canvas.drawPath(path, mPaint);
                            canvas.restore();
                        }
                    }
                    invalidate();
                } else {
                    canvas.drawRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), mPaint);
                }
            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    initializePaths();
                    if (mAnimator.isRunning()) {
                        mAnimator.cancel();
                    }
                    mScale = 1f;
                    mAnimator.start();
                    invalidate();
                }
                return true;
            }
        };
    }

    final int randomRadius = 100;

    void initializePaths() {
        final float width = mBitmap.getWidth();
        final float height = mBitmap.getHeight();
        final int xPointCount = xPathCount+1;
        final int yPointCount = yPathCount+1;
        int[] points = new int[xPointCount * yPointCount * 2];
        final float xStep = width/xPathCount;
        final float yStep = height/yPathCount;

        for (int x = 0; x < xPointCount; x++) {
            for (int y = 0; y < yPointCount; y++) {
                int xValue = (int) (xStep * x + random(-randomRadius, randomRadius));
                int yValue = (int) (yStep * y + random(-randomRadius, randomRadius));

                if (x == 0) xValue = 0;
                if (x == xPointCount - 1) xValue = (int)width;
                if (y == 0) yValue = 0;
                if (y == yPointCount - 1) yValue = (int)height;

                int xIndex = (y*xPointCount+x)*2;
                int yIndex = xIndex+1;
                points[xIndex] = xValue;
                points[yIndex] = yValue;
            }
        }

        for (int x = 0; x < xPathCount; x++) {
            for (int y = 0; y < yPathCount; y++) {
                int pathIndex = y*xPathCount+x;
                Path path = mPaths[pathIndex];
                if (path == null) {
                    path = new Path();
                    mPaths[pathIndex] = path;
                    mMotionDatas[pathIndex] = new MotionData();
                }
                else path.reset();

                final float randomRotationRadius = 10f;
                final float randomMoveRadius = 5f;
                mMotionDatas[pathIndex].moveXVelocity = random(-randomMoveRadius, randomMoveRadius);
                mMotionDatas[pathIndex].moveYVelocity = random(-randomMoveRadius, randomMoveRadius);
                mMotionDatas[pathIndex].rotationXVelocity = random(-randomRotationRadius, randomRotationRadius);
                mMotionDatas[pathIndex].rotationYVelocity = random(-randomRotationRadius, randomRotationRadius);
                mMotionDatas[pathIndex].scaleVelocity = random(0.005f, 0.02f);
                mMotionDatas[pathIndex].rotationX = 0;
                mMotionDatas[pathIndex].rotationY = 0;
                mMotionDatas[pathIndex].moveX = 0;
                mMotionDatas[pathIndex].moveY = 0;
                mMotionDatas[pathIndex].scale = 1f;

                int firstLineX = (y*xPointCount+x)*2;
                int firstLineY = firstLineX+1;
                int secondLineX = ((y+1)*xPointCount+x)*2;
                int secondLineY = secondLineX+1;

                path.moveTo(points[firstLineX], points[firstLineY]);
                path.lineTo(points[firstLineX+2], points[firstLineY+2]);
                path.lineTo(points[secondLineX+2], points[secondLineY+2]);
                path.lineTo(points[secondLineX], points[secondLineY]);
                path.close();
            }
        }
    }

    static private int random(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    static private float random(float min, float max) {
        return (float)(Math.random() * (max - min) + min);
    }
}
