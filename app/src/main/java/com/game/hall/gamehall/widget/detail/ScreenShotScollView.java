package com.game.hall.gamehall.widget.detail;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.Image;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.game.hall.gamehall.R;

import java.util.List;

/**
 * 自定义 screenshot
 * Created by hezhiyong on 2016/9/7.
 */
public class ScreenShotScollView extends HorizontalScrollView {

    private int nums;//总数，
    private int itemWidth;//ITEM大小-
    private int itemHeight;//ITEM大小-
    private int interval;//ITEM间隔

    private LinearLayout linearLayout;//子view

    private List<ImageView> shotImages;//图片

    private Bitmap[] itemBitmaps;//所有的item图片

    private MyOnFocusChangeListener onFocusChangeListener;

    public void setMyOnFocusChangeListener(MyOnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    /**
     * 加载图片
     */
    public void setBitmaps(Bitmap[] itemBitmaps) {
        if (itemBitmaps == null) {
            return;
        }
        this.itemBitmaps = itemBitmaps;

        if (linearLayout.getChildCount() != 0) {
            linearLayout.removeAllViews();
        }

        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int screenHeight = wm.getDefaultDisplay().getHeight();
        int screenWidth = wm.getDefaultDisplay().getWidth();

        itemHeight = ((screenHeight * 220) / 1080);
        itemWidth = (screenHeight * 390) / 1080;

        nums = itemBitmaps.length;
        for (int i = 0; i < nums; i++) {
            final ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, itemHeight);
            params.setMargins(interval, 0, interval, 0);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(itemBitmaps[i]);
            imageView.setTag(i);
            imageView.setFocusable(true);
            imageView.setFocusableInTouchMode(true);
            imageView.setOnFocusChangeListener(focusChangeListener);
            linearLayout.addView(imageView);
        }
        postInvalidate();
    }

    /**
     * 清理图片
     */
    public void cleanBitmaps() {
        if (itemBitmaps == null) {
            return;
        }
        for (int i = 0; i < itemBitmaps.length; i++) {
            Bitmap bitmap = itemBitmaps[i];
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
                bitmap = null;
            }
        }
        itemBitmaps = null;
    }

    public ScreenShotScollView(Context context) {
        super(context, null);
    }

    public ScreenShotScollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ScreenShotScollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ScreenShot);
//        TypedArray typedArray2 = context.obtainStyledAttributes(attrs,
//                com.android.internal.R.styleable.View);
//        nums = attrs.getAttributeIntValue(R.styleable.ScreenShot_nums, nums);
        interval = typedArray.getDimensionPixelOffset(R.styleable.ScreenShot_interval, interval);

        linearLayout = new LinearLayout(context);
        ScrollView.LayoutParams lp = new ScrollView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(lp);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        this.addView(linearLayout);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
//        int count = linearLayout.getChildCount();
//        for (int i = 0; i < count; i++) {
//            View view = linearLayout.getChildAt(i);
//            if (view instanceof ImageView) {
//                view.measure(MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY));
//            }
//        }
//        ((View)linearLayout).measure(MeasureSpec.makeMeasureSpec(screenWidth-itemHeight*200/220, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY));
//        setMeasuredDimension(MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY));
    }

    //
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        linearLayout.layout(0, 0, 0, 0);
//        int count = linearLayout.getChildCount();
//        for (int i = 0; i < count; i++) {
//            View view = linearLayout.getChildAt(i);
//            if (view instanceof ImageView) {
//                view.layout(interval, 0, interval, 0);
//            }
//        }
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//    }

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

    public interface MyOnFocusChangeListener {
        public void onFocusChange(int select);
    }

}
