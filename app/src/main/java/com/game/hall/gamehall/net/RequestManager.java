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

    public static void getHallGames(String title,String gametype,Callback callback){
        OkHttpUtils.getInstance().
                post()
                .addParams("title",title)
                .addParams("gametype",gametype)
                .url(Constans.URL.GAMEHALL)
                .build()
                .execute(callback);
    }
    public static void getGameDetails(String gameid,Callback callback){
        OkHttpUtils.getInstance().
                post()
                .url(Constans.URL.GAMEDETAIL)
                .addParams("gameid", gameid)
                .build()
                .execute(callback);
    }

    public static void getGameSearch(String keyword,Callback callback){
        OkHttpUtils.getInstance().
                post()
                .url(Constans.URL.GAMEDETAIL)
                .addParams("keyword", keyword)
                .build()
                .execute(callback);
    }

}
