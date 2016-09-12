package com.game.hall.gamehall.net.okhttputils.builder;


import com.game.hall.gamehall.net.okhttputils.OkHttpUtils;
import com.game.hall.gamehall.net.okhttputils.request.OtherRequest;
import com.game.hall.gamehall.net.okhttputils.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
