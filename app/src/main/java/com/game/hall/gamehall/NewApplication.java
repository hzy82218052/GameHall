package com.game.hall.gamehall;

import android.app.Application;

import com.game.hall.gamehall.net.okhttputils.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by hezhiyong on 2016/9/9.
 */
public class NewApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
