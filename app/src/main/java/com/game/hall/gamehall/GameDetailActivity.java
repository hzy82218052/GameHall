package com.game.hall.gamehall;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.game.hall.gamehall.mode.bean.GameDetail;
import com.game.hall.gamehall.widget.detail.ScreenShotScollView;
import com.game.hall.gamehall.widget.detail.TitleLine;

/**
 * Created by hezhiyong on 2016/9/5.
 */
public class GameDetailActivity extends Activity {

    private ImageView mIcon;

    private RatingBar mRatingbar;

    private TextView mTitle;//标题
    private TextView mStar;//星级别
    private TextView mNum;//数量
    private TextView mType;//类型
    private TextView mDown;//下载
    private TextView mIntc;//介绍

    private TitleLine mTL_shot;//截图
    private TitleLine mTL_related;//相关
    private ScreenShotScollView mSSS_shot;//图片截图
    private ScreenShotScollView mSSS_related;//游戏相关


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity_detail);


        init();
        requestData();
    }


    void init() {
        mIcon = (ImageView) findViewById(R.id.game_detail_icon);

        mRatingbar = (RatingBar) findViewById(R.id.game_detail_ratingbar);
        mTitle = (TextView) findViewById(R.id.game_detail_star);
        mStar = (TextView) findViewById(R.id.game_detail_downnum);
        mNum = (TextView) findViewById(R.id.game_detail_title);
        mType = (TextView) findViewById(R.id.game_detail_type);
        mDown = (TextView) findViewById(R.id.game_down);
        mIntc = (TextView) findViewById(R.id.game_intc);


        mTL_shot = (TitleLine) findViewById(R.id.titile_screenshot);
        mTL_related = (TitleLine) findViewById(R.id.titile_related);
        mSSS_shot = (ScreenShotScollView) findViewById(R.id.scroll_screenshot);
        mSSS_related = (ScreenShotScollView) findViewById(R.id.scroll_related);

        setLinstener();
    }

    void setLinstener() {
        mDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //@TODO 下载
            }
        });
        mIntc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //@TODO 介绍
//                mIntc.setText(detail.mIntc);
            }
        });
        mSSS_shot.setMyOnFocusChangeListener(new ScreenShotScollView.MyOnFocusChangeListener() {
            @Override
            public void onFocusChange(int select) {
                mTL_shot.setCurrentItem(select);
            }
        });
        mSSS_related.setMyOnFocusChangeListener(new ScreenShotScollView.MyOnFocusChangeListener() {
            @Override
            public void onFocusChange(int select) {
                mTL_related.setCurrentItem(select);
            }
        });
    }


    /**
     * 请求
     */
    private void requestData() {
        GameDetail detail = new GameDetail();


        detail.mTitle = "欢乐斗地主";//标题
        detail.mStar = "4";//星级别
        detail.mNum = "10000";//数量
        detail.mType = "体育竞技";//类型
        detail.mTypes = new int[]{R.mipmap.game_handle, R.mipmap.game_remote_control};//类型
        detail.mIntc = "欢乐斗地主111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111";//介绍

        Bitmap shot = BitmapFactory.decodeResource(getResources(), R.mipmap.game_test2);
        Bitmap related = BitmapFactory.decodeResource(getResources(), R.mipmap.game_test1);
        detail.shots = new Bitmap[]{shot, shot, shot, shot, shot};//截图
        detail.relateds = new Bitmap[]{related, related, related};//相关

        mIcon.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.game_test1));
        Integer star = 50 / 5 * Integer.parseInt(detail.mStar);
        mRatingbar.setMax(50);
        mRatingbar.setNumStars(star);

        mTitle.setText(detail.mTitle);
        mStar.setText(detail.mStar);
        mNum.setText(detail.mNum);
        mType.setText(detail.mType);

        mSSS_shot.setBitmaps(detail.shots);
        mSSS_related.setBitmaps(detail.relateds);


    }


}
