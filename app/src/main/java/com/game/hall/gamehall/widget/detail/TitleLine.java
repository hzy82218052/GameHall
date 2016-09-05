package com.game.hall.gamehall.widget.detail;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.game.hall.gamehall.R;

/**
 * 线条状Tab
 * Created by hezhiyong on 2016/9/5.
 */
public class TitleLine extends View {

    private int divided = 4;//分为几份
    private int dividedHight = 10 ;//高度
    private int dividedColor = 0xFF0099FF;//选中颜色
    private int bg_color= 0xFFCCCCCC;//默认线条颜色
    private int bg_height = 5;//默认线条高度

    private Paint paint;


    public TitleLine(Context context) {
        this(context, null);
    }

    public TitleLine(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    void init(Context context,AttributeSet attrs){

//        final Resources.Theme theme = context.getTheme();

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.TitleLine);
//        TypedArray typedArray2 = context.obtainStyledAttributes(attrs,
//                com.android.internal.R.styleable.View);
        divided = attrs.getAttributeIntValue(R.styleable.TitleLine_divided,divided);
        dividedHight= typedArray.getDimensionPixelSize(R.styleable.TitleLine_dividedHight,dividedHight);
        dividedColor = typedArray.getColor(R.styleable.TitleLine_dividedColor,dividedColor);
        bg_color = typedArray.getColor(R.styleable.TitleLine_bg_color,bg_color);
        dividedHight = typedArray.getDimensionPixelSize(R.styleable.TitleLine_bg_height,bg_height);
    }

    public void setDivided(int divided) {
        this.divided = divided;
    }

    public void setDividedHight(int dividedHight) {
        this.dividedHight = dividedHight;
    }

    public void setDividedColor(int dividedColor) {
        this.dividedColor = dividedColor;
    }

    public void setBg_height(int bg_height) {
        this.bg_height = bg_height;
    }

    public void setBg_color(int bg_color) {
        this.bg_color = bg_color;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        float topDividedHeight = (float) (height-dividedHight)/2;
        float topBgheight = (float) (height-bg_height)/2;


        paint = new Paint();
        paint.setAntiAlias(true);
        if (topBgheight<=0){
            topBgheight = 0;
        }

        paint.setColor(bg_color);
        paint.setStrokeWidth(topBgheight);
        canvas.drawLine(0,topBgheight,width,topBgheight+bg_height,paint);
        canvas.save();


        if(divided<2){
            divided = 2;
        }
        float dividewidth = width/divided;
        if (topDividedHeight<=0){
            topDividedHeight = 0;
        }
        paint.setColor(dividedColor);
        paint.setStrokeWidth(topDividedHeight);
        canvas.drawLine(0,topDividedHeight,0+dividewidth,topDividedHeight,paint);
    }
}
