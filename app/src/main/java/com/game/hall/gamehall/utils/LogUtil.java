package com.game.hall.gamehall.utils;

import android.util.Log;

/**
 * Created by Administrator on 2016/7/18.
 */
public class LogUtil {

    private static boolean DEBUG =true;

    public static void i(String TAG,String s){
        if (DEBUG) {
            Log.i(TAG, s);
        }
    }

}
