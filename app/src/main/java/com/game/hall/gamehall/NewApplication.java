package com.game.hall.gamehall;

import android.app.Application;

import com.game.hall.download.download.conf.UGAppConfManager;
import com.game.hall.gamehall.net.okhttputils.OkHttpUtils;
import com.game.hall.gamehall.ui.AppForegroundStateManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by hezhiyong on 2016/9/9.
 */
public class NewApplication extends Application implements AppForegroundStateManager.OnAppForegroundStateChangeListener {

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

        UGAppConfManager.getManager().init(this, false);
    }

    @Override
    public void onAppForegroundStateChange(AppForegroundStateManager.AppForegroundState newState) {
        if (AppForegroundStateManager.AppForegroundState.IN_FOREGROUND == newState) {//应用内
            // App just entered the foreground. Do something here!
        } else {//应用外
            // App just entered the background. Do something here!
        }
    }
}
