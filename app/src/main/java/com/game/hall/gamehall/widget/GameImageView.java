package com.game.hall.gamehall.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by hezhiyong on 16-9-3.
 */
public class GameImageView extends FrameLayout {

    public GameImageView(Context context) {
        this(context, null);
    }

    public GameImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        View.inflate(getContext(), R.layout.game_view_item,this);
    }

}
