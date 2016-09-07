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

public class DrawBitmapMeshMagicLampActivity extends BaseActivity {

    final static public String TITLE = "DrawBitmapMesh 神灯效果";

    Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
    Bitmap mBitmap;
    ValueAnimator mAnimator;

    static final float MOVE_TOTAL_RATIO = 0.7f;
    static int TWIST_MAX_HEIGHT;
    static int BOTTOM_WIDTH;
    static final private int MESH_WIDTH = 20, MESH_HEIGHT = 20;
    private float[] verts = new float[ (MESH_WIDTH+1) * (MESH_HEIGHT+1) * 2];

    int mTouchDownX,mTouchDownY;
    boolean mIsHiding;

    @Override
    protected View createContentView() {
        setTitle(TITLE+" :点击画面");
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.picture);
        mAnimator = ValueAnimator.ofFloat(1f, 0f);
        mAnimator.setDuration(1000);
        BOTTOM_WIDTH = 1;
        TWIST_MAX_HEIGHT = mBitmap.getHeight()/2/2;

        return new View(this){
            @Override
            public void draw(Canvas canvas) {
                if (mAnimator.isRunning()) {
                    buildMeshVertexs(verts, mBitmap, mTouchDownX, mTouchDownY, (Float)mAnimator.getAnimatedValue());
                    canvas.drawBitmapMesh(mBitmap, MESH_WIDTH, MESH_HEIGHT, verts, 0, null, 0, mPaint);

                    invalidate();
                } else {
                    if (!mIsHiding) {
                        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
                    }
                }
            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mTouchDownX = (int)event.getX();
                    mTouchDownY = (int)event.getY();
                    if (!mIsHiding) {
                        mAnimator.start();
                    } else {
                        mAnimator.reverse();
                    }
                    mIsHiding = !mIsHiding;
                }
                invalidate();
                return true;
            }
        };
    }

    static private void buildMeshVertexs(float[] verts, Bitmap bitmap, int startX, int startY, float ratio) {
        float yRatio = 1-Math.max(0, (MOVE_TOTAL_RATIO-ratio)/MOVE_TOTAL_RATIO);
        float xRatio = Math.max(0, ratio-MOVE_TOTAL_RATIO)/(1-MOVE_TOTAL_RATIO);

        final int bitmapWidth = bitmap.getWidth();
        final int rightWidth = bitmapWidth - startX;
        final int bitmapHeight = bitmap.getHeight();
        final int bottomHeight = bitmapHeight - startY;

        float startVertexY = startY * (1 - yRatio);
        float endVertexY = startY + yRatio * bottomHeight;
        float startMiddleVertexX = startX * (1 - xRatio) - BOTTOM_WIDTH/2;
        float endMiddleVertexX = startX + rightWidth*xRatio + BOTTOM_WIDTH/2;

        float topTwistHeight = Math.min(TWIST_MAX_HEIGHT, startY);
        float bottomTwistHeight = Math.min(TWIST_MAX_HEIGHT, bottomHeight);
        float topTwistHeightHalf = topTwistHeight/2;
        float bottomTwistHeightHalf = bottomTwistHeight/2;

        float startTwistVertexY = startY - topTwistHeight;
        float endTwistVertexY = startY + bottomTwistHeight;

        float stepY = (endVertexY - startVertexY) / MESH_HEIGHT;

        float topTwistLeftRate = topTwistHeight/startMiddleVertexX;
        float topTwistRightRate = topTwistHeight/(bitmapWidth - endMiddleVertexX);
        float bottomTwistLeftRate = bottomTwistHeight/startMiddleVertexX;
        float bottomTwistRightRate = bottomTwistHeight/(bitmapWidth - endMiddleVertexX);

        for (int y = 0; y < MESH_HEIGHT+1; y++) {
            float vertexY = stepY * y + startVertexY;
            boolean notNeedTwist = vertexY <= startTwistVertexY || vertexY >= endTwistVertexY;
            boolean isTopTwist = vertexY < startY;

            float twistRatio;
            float startVertexX = 0;
            float endVertexX = bitmapWidth;
            if (!notNeedTwist) {
                if (isTopTwist) {
                    float overTwistVertexY = Math.max(0, vertexY - startTwistVertexY);
                    boolean isFirstHalf = overTwistVertexY < topTwistHeightHalf;
                    if (isFirstHalf) {
                        twistRatio = overTwistVertexY / topTwistHeightHalf;
                        startVertexX = (twistRatio*twistRatio)*(topTwistHeightHalf)/topTwistLeftRate;
                        endVertexX = bitmapWidth - (twistRatio*twistRatio)*(topTwistHeightHalf)/topTwistRightRate;
                    } else {
                        twistRatio = (overTwistVertexY - topTwistHeightHalf) / topTwistHeightHalf;
                        float tempRate = 1-(1-twistRatio)*(1-twistRatio);
                        startVertexX = startMiddleVertexX/2 + (tempRate)*(topTwistHeightHalf)/topTwistLeftRate;
                        endVertexX = bitmapWidth - (tempRate)*(topTwistHeightHalf)/topTwistRightRate
                                -(bitmapWidth - endMiddleVertexX)/2;
                    }
                } else {
                    float overTwistVertexY = bottomTwistHeight - Math.max(0, vertexY - startY);
                    boolean isFirstHalf = overTwistVertexY < bottomTwistHeightHalf;
                    if (isFirstHalf) {
                        twistRatio = overTwistVertexY / bottomTwistHeightHalf;
                        startVertexX = (twistRatio*twistRatio)*(bottomTwistHeightHalf)/bottomTwistLeftRate;
                        endVertexX = bitmapWidth - (twistRatio*twistRatio)*(bottomTwistHeightHalf)/bottomTwistRightRate;
                    } else {
                        twistRatio = (overTwistVertexY - bottomTwistHeightHalf) / bottomTwistHeightHalf;
                        float tempRate = 1-(1-twistRatio)*(1-twistRatio);
                        startVertexX = startMiddleVertexX/2 + (tempRate)*(bottomTwistHeightHalf)/bottomTwistLeftRate;
                        endVertexX = bitmapWidth - (tempRate)*(bottomTwistHeightHalf)/bottomTwistRightRate
                                -(bitmapWidth - endMiddleVertexX)/2;
                    }
                }
            }

            float stepX = (endVertexX - startVertexX) / MESH_WIDTH;
            for (int x = 0; x < MESH_WIDTH+1; x++) {
                int indexX = ((y*(MESH_WIDTH+1))+x)*2, indexY = indexX+1;

                verts[indexX] = startVertexX + stepX*x;
                verts[indexY] = vertexY;
            }
        }
    }
}
