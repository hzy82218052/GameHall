package com.game.hall.gamehall.widget.detail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by hezhiyong on 2016/9/16.
 */
public class ShotImageView extends ImageView{
    public ShotImageView(Context context) {
        super(context);
    }

    public ShotImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShotImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int screenHeight = wm.getDefaultDisplay().getHeight();

        int itemHeight = ((screenHeight * 220) / 1080);
        int itemWidth = (screenHeight * 390) / 1080;

        setMeasuredDimension(itemWidth,itemHeight);
    }

}
