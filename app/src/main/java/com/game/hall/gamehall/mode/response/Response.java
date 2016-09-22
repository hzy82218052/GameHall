package com.game.hall.gamehall.mode.response;

import android.text.TextUtils;

/**
 * Created by hezhiyong on 2016/9/12.
 */
public class Response {

    public String code;
    public String msg;

    /**
     *是否成功
     * @return
     */
    public boolean isSuccess() {
        if (!TextUtils.isEmpty(code) && TextUtils.equals(code, "100")) {
            return true;
        }

        return false;

    }

}
