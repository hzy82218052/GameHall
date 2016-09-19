package com.game.hall.gamehall.widget.drag;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.game.hall.gamehall.utils.BitmapUtils;
import com.game.hall.gamehall.utils.LogUtil;

/**
 * Created by hezhiyong on 2016/9/14.
 */
public class DragView extends FrameLayout {


    private Paint paint = new Paint();

    private DragItem showDrag;
    private DragItem lastDrag;

    private ValueAnimator animator;

    public void setShowDrag(DragItem showDrag) {
        this.showDrag = showDrag;

        if (lastDrag != null) {
            if (lastDrag.view.equals(showDrag.view))
                return;
        }

        if (animator != null) {
            animator.cancel();
            animator = null;
        }
        animator = ValueAnimator.ofFloat(1f, 1.1f);
        animator.setDuration(300);
        animator.start();

        invalidate();
    }

    public DragView(Context context) {
        this(context, null);
    }

    public DragView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (animator == null)
            return;
        if (animator.isRunning()) {
//            if (lastDrag != null) {
//                Matrix matrix = new Matrix();
//                matrix.postScale(1.0f / ratio, 1.0f / ratio); //长和宽放大缩小的比例
//
//                final int mBitmap_w = lastDrag.mBitmap.getWidth();
//                final int mBitmap_h = lastDrag.mBitmap.getHeight();
//
//                final Bitmap bitmap = Bitmap.createBitmap(lastDrag.mBitmap, 0, 0, mBitmap_w, mBitmap_h, matrix, true);
//
//                final float x = lastDrag.mCanvasX - (bitmap.getWidth() - mBitmap_w) / 2;
//                final float y = lastDrag.mCanvasX + (bitmap.getHeight() - mBitmap_h) / 2;
//
//                paint.setAntiAlias(true);
//                canvas.save();
//                if (bitmap != null)
//                    canvas.drawBitmap(bitmap, x, y, null);
//            }
            myDraw(canvas);
            invalidate();
        } else {
            myDraw(canvas);
            lastDrag = showDrag;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void destroyCache() {
        if (lastDrag != null)
            lastDrag.destroyDrawingCache();
        if (showDrag != null)
            showDrag.destroyDrawingCache();
        showDrag = null;
        lastDrag = null;
    }

    /**
     * 画图
     * @param canvas
     */
    private void myDraw(Canvas canvas) {
        float ratio = (Float) animator.getAnimatedValue();
        LogUtil.i("@hzy", "ratio----------" + ratio);
//            if (lastDrag != null) {
//                final Bitmap bitmap = lastDrag.mBitmap;
//
//                paint.setAntiAlias(true);
//                canvas.save();
//                if (bitmap != null)
//                    canvas.drawBitmap(bitmap, lastDrag.mCanvasX, lastDrag.mCanvasY, null);
//            }


        if (showDrag != null) {
            Matrix matrix = new Matrix();
            matrix.postScale(1.0f * ratio, 1.0f * ratio); //长和宽放大缩小的比例

            final int mBitmap_w = showDrag.mBitmap.getWidth();
            final int mBitmap_h = showDrag.mBitmap.getHeight();

            final Bitmap bitmap = Bitmap.createBitmap(showDrag.mBitmap, 0, 0, mBitmap_w, mBitmap_h, matrix, true);

            LogUtil.i("@hzy", "-------" + mBitmap_w + "-----" + mBitmap_h);
            LogUtil.i("@hzy", "bitmap-------" + bitmap.getWidth() + "  bitmap-----" + bitmap.getHeight());

            final Bitmap haloBitmap = BitmapUtils.addHaloForImage(bitmap, 5, 0xFF0099FF);
            final float x = showDrag.mCanvasX - ((float) (haloBitmap.getWidth() - mBitmap_w) / 2);
            final float y = showDrag.mCanvasY - ((float) (haloBitmap.getHeight() - mBitmap_h)) / 2;

            LogUtil.i("@hzy", "haloBitmap-------" + haloBitmap.getWidth() + "  haloBitmap-----" + haloBitmap.getHeight());
            paint.setAntiAlias(true);
            canvas.save();
            if (haloBitmap != null)
                canvas.drawBitmap(haloBitmap, x, y, new Paint());
        }
    }


}
