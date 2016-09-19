package com.game.hall.gamehall.widget.drag;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by hezhiyong on 2016/9/14.
 */
public class DragItem {

    public int mCanvasX;//x坐标位置
    public int mCanvasY;//y坐标位置

    public int mWidth;//宽
    public int mHeight;//宽

    public Bitmap mBitmap;//图片

    public View view;

    public static DragItem createDragItem(View view) {
        DragItem dragItem = new DragItem();

        dragItem.view = view;

        dragItem.mWidth = view.getWidth();
        dragItem.mHeight = view.getWidth();

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        dragItem.mCanvasX = location[0];
        dragItem.mCanvasY = location[1];

        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();

        dragItem.mBitmap = bitmap;

        return dragItem;
    }

    public void destroyDrawingCache() {
        if (view != null)
            view.destroyDrawingCache();
        if (mBitmap != null) {
            if (!mBitmap.isRecycled()) {
                mBitmap.recycle();
            }
            mBitmap = null;
        }
        view = null;
    }

}
