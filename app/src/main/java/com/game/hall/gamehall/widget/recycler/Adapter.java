package com.game.hall.gamehall.widget.recycler;

import android.view.ViewGroup;

/**
 * Created by hezhiyong on 2016/9/16.
 */
public abstract class Adapter<VH extends ViewHolder> {

    public abstract int getCellsCount();

    public abstract VH onCreateViewHolder(ViewGroup viewGroup);

    public abstract void onBindViewHolder(VH viewHolder, int i);
}