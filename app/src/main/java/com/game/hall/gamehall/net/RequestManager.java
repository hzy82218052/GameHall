package com.game.hall.gamehall.net;

import com.game.hall.download.bean.AppBean;
import com.game.hall.gamehall.constans.Constans;
import com.game.hall.gamehall.net.okhttputils.OkHttpUtils;
import com.game.hall.gamehall.net.okhttputils.callback.Callback;

import java.util.List;

/**
 * Created by hezhiyong on 2016/9/12.
 */
public class RequestManager {

    public static void getHallGames(Callback callback){
        OkHttpUtils.getInstance().
                post()
                .url(Constans.URL.GAMEHALL)
                .build()
                .execute(callback);
    }
    public static void getGameDetails(Callback callback){
        OkHttpUtils.getInstance().
                post()
                .url(Constans.URL.GAMEDETAIL)
                .addParams("gameid", "1")
                .build()
                .execute(callback);
    }

}
