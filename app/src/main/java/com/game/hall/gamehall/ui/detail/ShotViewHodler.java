package com.game.hall.gamehall.ui.detail;

import android.view.View;
import android.widget.ImageView;

import com.game.hall.gamehall.widget.recycler.ViewHolder;

/**
 * Created by hezhiyong on 2016/9/16.
 */
public class ShotViewHodler extends ViewHolder {

    protected ImageView shot;

    public ShotViewHodler(View itemView) {
        super(itemView);
        if (itemView instanceof ImageView) {
            shot = (ImageView) itemView;
        }
    }
}
