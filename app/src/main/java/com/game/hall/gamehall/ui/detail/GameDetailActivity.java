package com.game.hall.gamehall.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.game.hall.download.bean.AppBean;
import com.game.hall.gamehall.R;
import com.game.hall.gamehall.mode.bean.GameDetail;
import com.game.hall.gamehall.mode.response.GameDetailResponse;
import com.game.hall.gamehall.net.RequestManager;
import com.game.hall.gamehall.net.okhttputils.callback.Callback;
import com.game.hall.gamehall.presenter.ProgressGenerator;
import com.game.hall.gamehall.ui.IntroActivity;
import com.game.hall.gamehall.utils.BitmapUtils;
import com.game.hall.gamehall.utils.GsonUtil;
import com.game.hall.gamehall.utils.LogUtil;
import com.game.hall.gamehall.widget.detail.TitleLine;
import com.game.hall.gamehall.widget.recycler.MyRecyclerView;
import com.game.hall.gamehall.widget.recycler.cell.CellAdapter;
import com.game.hall.gamehall.widget.recycler.cell.CellViewHolder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by hezhiyong on 2016/9/5.
 */
public class GameDetailActivity extends Activity {

    public static final String GAMEID = "gameId";

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
    //左右箭头
    private ImageView shot_left;
    private ImageView shot_right;
    private ImageView related_left;
    private ImageView related_right;
    //    private ScreenShotScollView mSSS_shot;//图片截图
//    private GameImageScollView mSSS_related;//游戏相关
    private MyRecyclerView mRecycler_shot;//图片截图
    private MyRecyclerView mRecycler_related;//游戏相关

    private List<String> mShots = new ArrayList<String>();
    private List<AppBean> gameRelateds = new ArrayList<AppBean>();

    private String gameId;
    private GameDetailResponse response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity_detail);

        gameId = getIntent().getStringExtra(GAMEID);

        init();
        requestData();
    }


    /**
     * 初始化
     */
    void init() {
        mIcon = (ImageView) findViewById(R.id.game_detail_icon);

        mRatingbar = (RatingBar) findViewById(R.id.game_detail_ratingbar);
        mTitle = (TextView) findViewById(R.id.game_detail_title);
        mStar = (TextView) findViewById(R.id.game_detail_star);
        mNum = (TextView) findViewById(R.id.game_detail_downnum);
        mType = (TextView) findViewById(R.id.game_detail_type);
        mIntc = (TextView) findViewById(R.id.game_intc);
        mDown = (ActionProcessButton) findViewById(R.id.game_down);


        mTL_shot = (TitleLine) findViewById(R.id.titile_screenshot);
        mTL_related = (TitleLine) findViewById(R.id.titile_related);
        shot_left = (ImageView) findViewById(R.id.shot_arrow_left);
        shot_right = (ImageView) findViewById(R.id.shot_arrow_right);
        related_left = (ImageView) findViewById(R.id.related_arrow_left);
        related_right = (ImageView) findViewById(R.id.related_arrow_right);
        mRecycler_shot = (MyRecyclerView) findViewById(R.id.recycler_screenshot);
        mRecycler_related = (MyRecyclerView) findViewById(R.id.recycler_related);


        setLinstener();
        initRecycler();
    }

    /**
     * 初始化Recycler
     */
    void initRecycler() {
        ShotAdapter shotAdapter = new ShotAdapter(mShots);
        mRecycler_shot.initRecycler(mRecycler_shot, shotAdapter);

        RelatedAdapter relatedAdapter = new RelatedAdapter(gameRelateds);
        mRecycler_related.initRecycler(mRecycler_related, relatedAdapter);
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
                Intent wechatIntent = new Intent(GameDetailActivity.this, IntroActivity.class);
                IntroActivity.bitmap = BitmapUtils.getCurrentActivityShot(GameDetailActivity.this);
                final GameDetail detail = response.gamedetail;
                wechatIntent.putExtra(IntroActivity.CONTENT, detail.content);
                startActivity(wechatIntent);
                GameDetailActivity.this.overridePendingTransition(
                        android.view.animation.Animation.INFINITE,
                        android.view.animation.Animation.INFINITE);
//                mIntc.setText(detail.mIntc);
            }
        });


    }


    /**
     * 请求
     */
    private void requestData() {
        RequestManager.getGameDetails(gameId, new Callback<GameDetailResponse>() {
            @Override
            public GameDetailResponse parseNetworkResponse(Response response, int id) throws Exception {
                String str = response.body().string();
                LogUtil.i("@hzy", "getGameDetails----------" + str);
                return GsonUtil.getInstance().formJson(str, GameDetailResponse.class);
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(GameDetailResponse response, int id) {
                if (response.isSuccess()) {
                    if (response.gamedetail != null)
                        setDatas(response);
                }
                if (response.gamedetail == null) {
                    GameDetailResponse test = createDetail();
                    setDatas(test);
                }
            }
        });
    }


    int[] mTypes = null;

    /**
     * 测试数据
     *
     * @return
     */
    private GameDetailResponse createDetail() {
        GameDetailResponse gameDetailResponse = new GameDetailResponse();
        gameDetailResponse.code = "0";
        GameDetail detail = new GameDetail();
        detail.setAppcode(167);
        detail.setApppackagename("com.qqgame.hlddz");
        detail.setApplink("http://113.107.216.43/imtt.dd.qq.com/16891/2D4C5564FAD16B59F41F2A969DB8685D.apk?mkey=57cad5ae53b64884&f=d688&c=0&fsname=com.qqgame.hlddz_5.12.007_167.apk&csr=4d5s&p=.apk");
        detail.setAppsize("1000000");
        detail.setRptkey("1000");
        detail.setAppname("欢乐斗地主");//标题
        detail.appstar = "4";//星级别
        detail.setAppdownloadnum("10000");//数量
        detail.setGametype("体育竞技");//类型
        mTypes = new int[]{R.mipmap.game_handle, R.mipmap.game_remote_control};//类型
        detail.content = "欢乐斗地主111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111";//介绍

        Bitmap shot = BitmapFactory.decodeResource(getResources(), R.mipmap.game_test2);
        Bitmap related = BitmapFactory.decodeResource(getResources(), R.mipmap.game_test1);
        List<String> imageurl = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            imageurl.add("------");
        }

        detail.imageurl = imageurl;

        gameDetailResponse.gamedetail = detail;

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
        gameDetailResponse.app_recom = datas;

        return gameDetailResponse;
    }

    /**
     * 设置数据
     *
     * @param response
     */
    private void setDatas(GameDetailResponse response) {
        this.response = response;
        final GameDetail detail = response.gamedetail;
        List<AppBean> app_recom = response.app_recom;

        mIcon.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.game_test1));

        mTitle.setText(detail.getAppname());

        mRatingbar.setRating(Float.parseFloat(detail.appstar));
        mStar.setText(getString(R.string.game_star, detail.appstar));

        mNum.setText(getString(R.string.game_downnum,detail.getAppdownloadnum()));


        mType.setText(getString(R.string.game_type,detail.getGametype()));

        mType.append(getStringIcon(0));
        mType.setGravity(Gravity.CENTER_VERTICAL);

        mTL_shot.setDivided(detail.imageurl.size());
        mTL_related.setDivided(app_recom.size());

        mDown.setTag(detail);

        mShots.clear();
        mShots.addAll(detail.imageurl);
        mRecycler_shot.getAdapter().notifyDataSetChanged();

        gameRelateds.clear();
        gameRelateds.addAll(app_recom);
        mRecycler_related.getAdapter().notifyDataSetChanged();
    }


    private SpannableString getStringIcon(int type){

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.mipmap.game_handle);
        Matrix matrix = new Matrix();
        matrix.postScale(0.7f , 0.7f); //长和宽放大缩小的比例
        Bitmap bitmap = Bitmap.createBitmap(b,0,0,b.getWidth(),b.getHeight(),matrix,false);
        ImageSpan imgSpan = new ImageSpan(this, bitmap);
        SpannableString spanString = new SpannableString("icon");
        spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        if(!b.isRecycled()){
            b.recycle();
            b=null;
        }
        return spanString;
    }

    /**
     *
     * @param blow  //shot或者related  0、1
     * @param leftOrRight 0、1 左右
     * @param seletor 选中
     */
    private void setArrow(int blow,int leftOrRight,boolean seletor){
        View view = shot_left;
        switch(blow){
            case 0:
                view = leftOrRight==0?shot_left:shot_right;

                break;
            case 1:
                view = leftOrRight==0?related_left:related_right;
                break;
        }
        view.setSelected(seletor);

    }


}
