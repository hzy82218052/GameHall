package com.game.hall.gamehall.widget.drag;

import android.view.View;

import java.io.Serializable;

/**
 * Created by hezhiyong on 2016/9/16.
 */
public class DragViewHodler implements Serializable {

    private DragViewHodler() {

    }

    public static class ThisHodler {
        static final DragViewHodler INSTANCE = new DragViewHodler();
    }


    public static DragViewHodler getInstance() {
        return ThisHodler.INSTANCE;
    }

    public Object readResolve() {
        return getInstance();
    }


    private DragView dragView;

    public void setDragView(DragView dragView) {
        this.dragView = dragView;
    }

    public void zoomScale(View view) {
        dragView.setShowDrag(DragItem.createDragItem(view));
    }

    public void cleanCache() {
        if (dragView != null)
            dragView.destroyCache();
        dragView = null;
    }


}
