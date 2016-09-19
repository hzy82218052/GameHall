package com.game.hall.gamehall.widget.detail;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.hall.gamehall.R;

/**
 * 自定义Win8方块
 * Created by hezhiyong on 16-9-3.
 */
public class GameImageView2 extends FrameLayout {

    /**
     * 布局形式只能是
     * <p/>
     * *********** ******** ************
     * * vertical* *square* *horizontal*
     * *         * *      * *horizontal*
     * *********** ******** *horizontal*
     * *horizontal*
     * ******** *********** *horizontal*
     * *square* * vertical* *horizontal*
     * *      * *         * *horizontal*
     * ******** *********** ************
     */


    private SapeMode mode = SapeMode.SQUARE;

    private int screenWidth;//屏宽
    private int screenHeight;//屏高


    private int width;//宽
    private int height;//高
    private float maxHeight = 334;//最高高度

    private int drawableResource;
    private int txtSize;
    private String txt;

    private ImageView imageView;
    private TextView textView;

    public GameImageView2(Context context) {
        this(context, null);
    }

    public GameImageView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameImageView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setDrawableResource(int drawableResource) {
        this.drawableResource = drawableResource;
        imageView.setImageResource(drawableResource);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        invalidate();
    }

    public void setTxt(String txt) {
        this.txt = txt;
        textView.setText(txt);
        invalidate();
    }

    public void setTxtSize(int txtSize) {
        this.txtSize = txtSize;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
    }

    public SapeMode getMode() {
        return mode;
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        View.inflate(getContext(), R.layout.game_view_item, this);

        imageView = (ImageView) findViewById(R.id.game_image);
        textView = (TextView) findViewById(R.id.game_txt);


        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.GameImageView);

        int value = typedArray.getInt(R.styleable.GameImageView_sapemode, 0);
        mode = getSapeMode(value);
        initWidthAndHeight();
    }


    private void initWidthAndHeight() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();

        maxHeight = screenHeight * 220 / 1080;

        switch (mode) {
            case VERTICAL:
                height = (int) (maxHeight * 680 / 334);
                width = (int) maxHeight;
                break;
            case HORIZONTAL:
                height = (int) maxHeight;
                width = (int) (maxHeight * 780 / 334);
                break;
            case SQUARE:
                height = (int) maxHeight;
                width = (int) maxHeight;
                break;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        int childState = 0;
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (view instanceof ImageView) {
                Log.i("@hzy", "---" + "Width=" + width + "    " + "viewHeight=" + height);
                view.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
            } else {
                if (view.getVisibility() != view.GONE)
                    view.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), heightMeasureSpec);
            }
            childState = combineMeasuredStates(childState, view.getMeasuredState());
        }

//        int widths = resolveSizeAndState(width, widthMeasureSpec, childState);
//        int heights = resolveSizeAndState(height, heightMeasureSpec,
//                childState << MEASURED_HEIGHT_STATE_SHIFT);
        setMeasuredDimension(width, height);
//        measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
//        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }


//    @Override
//    public boolean isFocused() {
//        return true;
//    }

    public enum SapeMode {
        VERTICAL(0),//竖形 370*680
        HORIZONTAL(1),//横行 780*334
        SQUARE(2);//正方形 334*334

        private int value;

        private SapeMode(int value) {
            this.value = value;
        }

    }

    public SapeMode getSapeMode(int value) {
        SapeMode[] values = SapeMode.values();
        for (SapeMode sapeMode : values) {
            if (sapeMode.value == value) {
                return sapeMode;
            }
        }
        return null;
    }


}
