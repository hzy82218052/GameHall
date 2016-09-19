package com.game.hall.gamehall.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.game.hall.download.bean.AppBean;
import com.game.hall.gamehall.R;
import com.game.hall.gamehall.mode.response.GameHall;
import com.game.hall.gamehall.mode.response.GameHallResponse;
import com.game.hall.gamehall.net.RequestManager;
import com.game.hall.gamehall.net.okhttputils.callback.Callback;
import com.game.hall.gamehall.ui.detail.GameDetailActivity;
import com.game.hall.gamehall.utils.GsonUtil;
import com.game.hall.gamehall.utils.LogUtil;
import com.game.hall.gamehall.widget.GameImageView;
import com.game.hall.gamehall.widget.drag.DragViewHodler;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by hezhiyong on 2016/9/15.
 */
public class Win8Fragment extends BaseFragment implements View.OnClickListener, View.OnFocusChangeListener {

    public static final String TITLE = "title";
    public static final String GAMETYPE = "gameType";

    private String title;
    private String gameType;
    private int index = 0;//标记

    private List<AppBean> datas = new ArrayList<AppBean>();//数据
    private List<GameImageView> gameList = new ArrayList<GameImageView>();

    private ViewGroup win8;//Win8布局


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(TITLE);
        gameType = getArguments().getString(GAMETYPE);
    }

    @Override
    public void initView() {
        win8 = (ViewGroup) findViewById(R.id.merto_content);

        creatTestData();

//        requestDatas();
    }

    @Override
    protected View onCreateRootView() {
        return View.inflate(getContext(), R.layout.game_frame_win8, null);
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mContext, GameDetailActivity.class);
        startActivity(intent);
    }

    /**
     * 创建数据
     */
    private void creatTestData() {
        index = 0;
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
        setTestData(win8);
    }

    /**
     * 设置win8
     *
     * @param viewGroup
     */
    private void setTestData(ViewGroup viewGroup) {
        int count = viewGroup.getChildCount();
        gameList.clear();
        for (int i = 0; i < count; i++) {
            final View view = viewGroup.getChildAt(i);
            if (!(view instanceof GameImageView)) {
                if (view instanceof ViewGroup)
                    setTestData((ViewGroup) view);
                continue;
            }
            final GameImageView gameView = (GameImageView) view;
            gameView.setOnFocusChangeListener(this);
            gameView.setOnClickListener(this);
            final AppBean bean = datas.get(index);

            setTestBitmap(gameView);
            gameView.setTxt(bean.getAppname());
            gameView.setTag(bean);
            gameList.add(gameView);
            index++;
        }
    }

    /**
     * 设置数据
     *
     * @param gameView
     */
    private void setTestBitmap(GameImageView gameView) {
        int drawRes = R.mipmap.game_01;
        switch (gameView.getMode()) {
            case VERTICAL:
                drawRes = R.mipmap.game_05;
                break;
            case HORIZONTAL:
                drawRes = (int) (Math.random() * 2) == 1 ? R.mipmap.game_01 : R.mipmap.game_04;
                break;
            case SQUARE:
                drawRes = (int) (Math.random() * 2) == 1 ? R.mipmap.game_02 : R.mipmap.game_03;
                break;
        }
        gameView.setDrawableResource(drawRes);
    }


    /**
     * 请求数据
     */
    private void requestDatas() {
        RequestManager.getHallGames(callback);
    }

    Callback<GameHallResponse> callback = new Callback<GameHallResponse>() {
        @Override
        public GameHallResponse parseNetworkResponse(Response response, int id) throws Exception {
            String str = response.body().toString();
            LogUtil.i("@hzy", "gamehall--parse:" + str);
            return GsonUtil.getInstance().formJson(str, GameHallResponse.class);
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            setNodataOrNoNet();
        }

        @Override
        public void onResponse(GameHallResponse response, int id) {
            List<AppBean> list = new ArrayList<AppBean>();
            if (response.isSuccess()) {
                List<AppBean> data = response.data;
                if (data == null && data.size() == 0) {
                    return;
                }
                list.addAll(data);
            }
            setWin8Data(list);
        }
    };


    /**
     * 设置Win8界面
     *
     * @param list
     */
    private void setWin8Data(List<AppBean> list) {
        //考虑到无数据的情况
        if (list == null || list.size() == 0) {
            setNodataOrNoNet();
            return;
        }

        gameList.clear();
        for (int i = 0; i < list.size() && i < 7; i++) {
            final GameImageView gameView = getGameView(win8, i);
            gameView.setOnClickListener(this);

            final AppBean bean = datas.get(index);
            gameView.setTxt(bean.getAppname());
            gameView.setTag(bean);
            gameView.setOnFocusChangeListener(this);
            gameList.add(gameView);
            index++;
        }

        GameImageView game1 = gameList.get(1);
        GameImageView game2 = gameList.get(2);
        GameImageView game3 = gameList.get(3);
        GameImageView game4 = gameList.get(4);
        GameImageView game5 = gameList.get(5);
        GameImageView game6 = gameList.get(6);
        GameImageView game7 = gameList.get(7);

        //更改一下模式
        switch (list.size()) {
            case 1:
                game1.setVisibility(View.GONE);
                game2.setVisibility(View.GONE);
                game3.setVisibility(View.GONE);
                game4.setVisibility(View.GONE);
                game5.setVisibility(View.GONE);
                game6.setVisibility(View.GONE);
                game7.setVisibility(View.GONE);
                break;
            case 2:
                game1.setMode(GameImageView.SapeMode.VERTICAL);
                game2.setVisibility(View.GONE);
                game3.setVisibility(View.GONE);
                game4.setVisibility(View.GONE);
                game5.setVisibility(View.GONE);
                game6.setVisibility(View.GONE);
                game7.setVisibility(View.GONE);
                break;
            case 3:
                game1.setMode(GameImageView.SapeMode.VERTICAL);
                game2.setMode(GameImageView.SapeMode.VERTICAL);
                game3.setVisibility(View.GONE);
                game4.setVisibility(View.GONE);
                game5.setVisibility(View.GONE);
                game6.setVisibility(View.GONE);
                game7.setVisibility(View.GONE);
                break;
            case 4:
                game4.setVisibility(View.GONE);
                game5.setVisibility(View.GONE);
                game6.setVisibility(View.GONE);
                game7.setVisibility(View.GONE);
                break;
            case 5:
                game5.setVisibility(View.GONE);
                game6.setVisibility(View.GONE);
                game7.setVisibility(View.GONE);
                break;
            case 6:
                game5.setMode(GameImageView.SapeMode.VERTICAL);
                game6.setVisibility(View.GONE);
                game7.setVisibility(View.GONE);
                break;
            case 7:
                game6.setMode(GameImageView.SapeMode.VERTICAL);
                game7.setVisibility(View.GONE);
                break;
            case 8:
                break;
        }

    }


    /**
     * 获取item
     *
     * @param viewGroup
     * @param index
     * @return
     */
    private GameImageView getGameView(ViewGroup viewGroup, int index) {
        int count = viewGroup.getChildCount();
        GameImageView gameImageView = null;
        int current = 0;
        for (int i = 0; i < count; i++) {
            final View view = viewGroup.getChildAt(i);
            if (view instanceof GameImageView) {
                gameImageView = (GameImageView) view;
                if (current == index)
                    break;
                current++;
            } else if (!(view instanceof GameImageView) && view instanceof ViewGroup) {
                gameImageView = getGameView((ViewGroup) view, 0);
                if (current == index)
                    break;
                current++;
            }
        }
        return gameImageView;
    }

    /**
     * 设置无数据与无网络的情况
     */
    private void setNodataOrNoNet() {
        //TODO 设置无数据与无网络的情况
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            DragViewHodler.getInstance().zoomScale(v);
        }
    }
}
