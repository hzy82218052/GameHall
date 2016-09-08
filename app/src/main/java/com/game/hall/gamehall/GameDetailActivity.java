package com.game.hall.gamehall;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.game.hall.download.bean.AppBean;
import com.game.hall.gamehall.mode.bean.GameDetail;
import com.game.hall.gamehall.presenter.ProgressGenerator;
import com.game.hall.gamehall.widget.detail.GameImageScollView;
import com.game.hall.gamehall.widget.detail.ScreenShotScollView;
import com.game.hall.gamehall.widget.detail.TitleLine;

import java.util.ArrayList;
import java.util.List;

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
    private TextView mIntc;//介绍
    private ActionProcessButton mDown;//下载

    private TitleLine mTL_shot;//截图
    private TitleLine mTL_related;//相关
    private ScreenShotScollView mSSS_shot;//图片截图
    private GameImageScollView mSSS_related;//游戏相关


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
        mIntc = (TextView) findViewById(R.id.game_intc);
        mDown = (ActionProcessButton) findViewById(R.id.game_down);


        mTL_shot = (TitleLine) findViewById(R.id.titile_screenshot);
        mTL_related = (TitleLine) findViewById(R.id.titile_related);
        mSSS_shot = (ScreenShotScollView) findViewById(R.id.scroll_screenshot);
        mSSS_related = (GameImageScollView) findViewById(R.id.scroll_related);


        setLinstener();
    }

    void setLinstener() {
        mDown.setMode(ActionProcessButton.Mode.PROGRESS);
        mDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //@TODO 下载
                final ProgressGenerator progressGenerator = new ProgressGenerator(new ProgressGenerator.OnCompleteListener() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(GameDetailActivity.this, R.string.game_down_success, Toast.LENGTH_LONG).show();
                    }
                });
                progressGenerator.start(mDown);
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
        mSSS_related.setMyOnFocusChangeListener(new GameImageScollView.MyGameListener() {
            @Override
            public void onFocusChange(int select) {
                mTL_related.setCurrentItem(select);
            }

            @Override
            public void onClick(AppBean bean) {
                //TODO 跳转游戏详情
            }
        });
    }


    /**
     * 请求
     */
    private void requestData() {
        GameDetail detail = createDetail();

        mIcon.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.game_test1));
        Integer star = 50 / 5 * Integer.parseInt(detail.mStar);
        mRatingbar.setMax(5);
        mRatingbar.setNumStars(Integer.parseInt(detail.mStar));

        mTitle.setText(detail.getAppname());
        mStar.setText(detail.mStar);
        mNum.setText(detail.getAppdownloadnum());
        mType.setText(detail.mType);

        mTL_shot.setDivided(detail.shots.length);
        mTL_related.setDivided(detail.relateds.size());
        mSSS_shot.setBitmaps(detail.shots);
        mSSS_related.setItems(detail.relateds);

        mDown.setTag(detail);

    }


    private GameDetail createDetail() {
        GameDetail detail = new GameDetail();
        detail.setAppcode(167);
        detail.setApppackagename("com.qqgame.hlddz");
        detail.setApplink("http://113.107.216.43/imtt.dd.qq.com/16891/2D4C5564FAD16B59F41F2A969DB8685D.apk?mkey=57cad5ae53b64884&f=d688&c=0&fsname=com.qqgame.hlddz_5.12.007_167.apk&csr=4d5s&p=.apk");
        detail.setAppsize("1000000");
        detail.setRptkey("1000");
        detail.setAppname("欢乐斗地主");//标题
        detail.mStar = "4";//星级别
        detail.setAppdownloadnum("10000");//数量
        detail.mType = "体育竞技";//类型
        detail.mTypes = new int[]{R.mipmap.game_handle, R.mipmap.game_remote_control};//类型
        detail.mIntc = "欢乐斗地主111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111";//介绍

        Bitmap shot = BitmapFactory.decodeResource(getResources(), R.mipmap.game_test2);
        Bitmap related = BitmapFactory.decodeResource(getResources(), R.mipmap.game_test1);
        detail.shots = new Bitmap[]{shot, shot, shot, shot, shot};//截图

        List<AppBean> datas = new ArrayList<AppBean>();
        for (int i = 0; i < 8; i++) {
            AppBean bean = new AppBean();
            bean.setAppcode(167);
            bean.setAppname("欢乐斗地主" + i);
            bean.setApppackagename("com.qqgame.hlddz");
            bean.setApplink("http://113.107.216.43/imtt.dd.qq.com/16891/2D4C5564FAD16B59F41F2A969DB8685D.apk?mkey=57cad5ae53b64884&f=d688&c=0&fsname=com.qqgame.hlddz_5.12.007_167.apk&csr=4d5s&p=.apk");
            bean.setAppsize("1000000" + i);
            bean.setRptkey("1000" + i);
            datas.add(bean);
        }
        detail.relateds = datas;//相关

        return detail;
    }

}
