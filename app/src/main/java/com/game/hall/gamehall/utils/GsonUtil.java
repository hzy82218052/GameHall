package com.game.hall.gamehall.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/23.
 */
public class GsonUtil {

    private Gson gson;

    private static GsonUtil util;

    private GsonUtil(){
        gson = new Gson();
    }

    public static GsonUtil getInstance(){
        if(util==null){
            util = new GsonUtil();
        }
        return util;
    }


    public <T> T formJsonList(String jsonStr,Class<T> zz){
        Type type = new TypeToken<ArrayList<T>>(){}.getType();
        return gson.fromJson(jsonStr,type);
    }

    public <T> T formJson(String jsonStr,Class<T> zz){
        return gson.fromJson(jsonStr,zz);
    }

}
