package com.game.hall.gamehall.ui.detail;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.hall.gamehall.R;
import com.game.hall.gamehall.widget.detail.GameImageView2;
import com.game.hall.gamehall.widget.recycler.ViewHolder;


/**
 * Created by florentchampigny on 28/08/15.
 */
public class RelatedViewHolder extends ViewHolder {

    protected GameImageView2 gameImageView;
    protected TextView txt;
    protected ImageView image;

    public RelatedViewHolder(View itemView) {
        super(itemView);

        gameImageView = (GameImageView2) itemView.findViewById(R.id.game_cell_card);
//        txt = (TextView)itemView.findViewById(R.id.textView);
//        image = (ImageView)itemView.findViewById(R.id.imageView);

    }
}
