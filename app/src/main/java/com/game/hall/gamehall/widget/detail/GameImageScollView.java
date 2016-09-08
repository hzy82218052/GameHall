package com.game.hall.gamehall.widget.detail;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.game.hall.download.bean.AppBean;
import com.game.hall.gamehall.R;
import com.game.hall.gamehall.widget.GameImageView;

import java.util.List;

/**
 * 自定义 screenshot
 * Created by hezhiyong on 2016/9/7.
 */
public class GameImageScollView extends HorizontalScrollView implements View.OnClickListener {

    private int nums;//总数，
    private int itemWidth;//ITEM大小-
    private int itemHeight;//ITEM大小-
    private int interval;//ITEM间隔

    private LinearLayout linearLayout;//子view

    private List<GameImageView> shotImages;//图片

    private List<AppBean> datas;//所有的item

    private MyGameListener onFocusChangeListener;

    public void setMyOnFocusChangeListener(MyGameListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    /**
     * 加载图片
     */
    public void setItems(List<AppBean> datas) {
        if (datas == null) {
            return;
        }
        this.datas = datas;

        if (linearLayout.getChildCount() != 0) {
            linearLayout.removeAllViews();
        }
        nums = datas.size();
        for (int i = 0; i < nums; i++) {
            final GameImageView imageView = (GameImageView) View.inflate(getContext(), R.layout.game_view_detail_reta, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(interval, 0, interval, 0);
            imageView.setLayoutParams(params);
            imageView.setTxt(datas.get(i).getAppname());
            imageView.setTxtSize(14);
            imageView.setDrawableResource(R.mipmap.game_test1);
            imageView.setTag(i);
            imageView.setOnClickListener(this);
            imageView.setFocusable(true);
            imageView.setFocusableInTouchMode(true);
            imageView.setOnFocusChangeListener(focusChangeListener);
            linearLayout.addView(imageView);
        }
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View v = linearLayout.getChildAt(i);
            LinearLayout.MarginLayoutParams lm = (LinearLayout.MarginLayoutParams) v.getLayoutParams();
            Log.i("@hzy", "---lm.left=" + lm.leftMargin + "------lm.right=" + lm.rightMargin);
        }
        postInvalidate();
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View v = linearLayout.getChildAt(i);
            LinearLayout.MarginLayoutParams lm = (LinearLayout.MarginLayoutParams) v.getLayoutParams();
            Log.i("@hzy", "---2lm.left=" + lm.leftMargin + "------2lm.right=" + lm.rightMargin);
        }
    }

    public GameImageScollView(Context context) {
        super(context, null);
    }

    public GameImageScollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GameImageScollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ScreenShot);
//        TypedArray typedArray2 = context.obtainStyledAttributes(attrs,
//                com.android.internal.R.styleable.View);
//        nums = attrs.getAttributeIntValue(R.styleable.ScreenShot_nums, nums);
        itemWidth = typedArray.getDimensionPixelOffset(R.styleable.ScreenShot_itemWidth, itemWidth);
        itemHeight = typedArray.getDimensionPixelOffset(R.styleable.ScreenShot_itemHeight, itemHeight);
        interval = typedArray.getDimensionPixelOffset(R.styleable.ScreenShot_interval, interval);

        linearLayout = new LinearLayout(context);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(lp);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        this.addView(linearLayout);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private OnFocusChangeListener focusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                Integer integer = (Integer) v.getTag();
                if (onFocusChangeListener != null)
                    onFocusChangeListener.onFocusChange(integer);
            }
        }
    };

    @Override
    public void onClick(View v) {
        Integer integer = (Integer) v.getTag();
        final AppBean appBean = datas.get(integer);
        if (onFocusChangeListener != null) {
            onFocusChangeListener.onClick(appBean);
        }
    }


    public interface MyGameListener {
        public void onFocusChange(int select);

        public void onClick(AppBean bean);
    }

}
