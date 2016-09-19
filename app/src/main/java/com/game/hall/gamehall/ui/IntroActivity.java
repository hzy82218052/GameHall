package com.game.hall.gamehall.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.enrique.stackblur.StackBlurManager;
import com.game.hall.gamehall.R;

/**
 * Created by Administrator on 2016/9/12.
 */
public class IntroActivity extends Activity {

    public static final String BITMAP = "bitmap";
    public static final String CONTENT = "content";

    public static Bitmap bitmap;//背景
    private String content;//内容

    private ImageView bg;
    private TextView intro;

    private StackBlurManager _stackBlurManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_view_intro);

        bg = (ImageView) findViewById(R.id.intro_bg);
        intro = (TextView) findViewById(R.id.content);

        content = getIntent().getStringExtra(CONTENT);

        if (bitmap != null) {
            int radius = 10 * 5;
            _stackBlurManager = new StackBlurManager(bitmap);
            bg.setImageBitmap(_stackBlurManager.process(radius));
        }
        if (!TextUtils.isEmpty(content)) {
            intro.setText(content);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(_stackBlurManager!=null){
            _stackBlurManager=null;
        }
        if (bitmap != null) {
            if (bitmap.isRecycled())
                bitmap.recycle();
            bitmap = null;
        }
    }
}
