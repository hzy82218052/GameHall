package com.game.hall.gamehall.widget.recycler;

import android.support.v7.widget.RecyclerView;

import com.game.hall.gamehall.utils.CommonUtil;
import com.game.hall.gamehall.widget.recycler.cell.CellAdapter;

/**
 * Created by hezhiyong on 2016/9/16.
 */
public class MyHeightCalculatedCallback implements CellAdapter.HeightCalculatedCallback {
    private RecyclerView recyclerView;
    private boolean isWrapped = false;

    public MyHeightCalculatedCallback(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void onHeightCalculated(int height) {
        if (!isWrapped()) {
            recyclerView.getLayoutParams().height = height+ CommonUtil.dp2px(recyclerView.getContext(),20);
            recyclerView.requestLayout();
            isWrapped = true;
        }
    }

    @Override
    public boolean isWrapped() {
        return isWrapped;
    }
}
