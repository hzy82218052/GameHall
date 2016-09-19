package com.game.hall.gamehall.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dd.processbutton.iml.ActionProcessButton;
import com.game.hall.gamehall.R;
import com.game.hall.gamehall.mode.response.GameHallResponse;
import com.game.hall.gamehall.net.RequestManager;
import com.game.hall.gamehall.net.okhttputils.callback.Callback;
import com.game.hall.gamehall.utils.BitmapUtils;
import com.game.hall.gamehall.utils.ChangeCharset;
import com.google.gson.Gson;

import java.net.URLDecoder;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/7.
 */
public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ActionProcessButton mDown = (ActionProcessButton) findViewById(R.id.game_down);
        mDown.setMode(ActionProcessButton.Mode.PROGRESS);
        mDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("@hzy","--------------str=");
                RequestManager.getHallGames(new Callback<GameHallResponse>() {
                    @Override
                    public GameHallResponse parseNetworkResponse(Response response, int id) throws Exception {
                        String str = response.body().string();
                        String gbk = ChangeCharset.changeCharset(str,ChangeCharset.UTF_8);
                        Log.i("@hzy","--------------str="+ URLDecoder.decode(str));
                        System.out.println("--------------str="+ str.toString());
                        return new Gson().fromJson(str,GameHallResponse.class);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(GameHallResponse response, int id) {


                    }
                });
                RequestManager.getGameDetails("1",new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        String str =response.body().string();
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Object response, int id) {

                    }
                });


                IntroActivity.bitmap = BitmapUtils.getCurrentActivityShot(TestActivity.this);
                Intent wechatIntent = new Intent(TestActivity.this,IntroActivity.class);
                wechatIntent.putExtra(IntroActivity.CONTENT, "------------haoaaddjkfsjfkldsjfkdj");
                startActivity(wechatIntent);
                TestActivity.this.overridePendingTransition(
                        android.view.animation.Animation.INFINITE,
                        android.view.animation.Animation.INFINITE);

                //@TODO 下载
//                final ProgressGenerator progressGenerator = new ProgressGenerator(new ProgressGenerator.OnCompleteListener() {
//                    @Override
//                    public void onComplete() {
//                        Toast.makeText(TestActivity.this, R.string.game_down_success, Toast.LENGTH_LONG).show();
//                    }
//                });
//                progressGenerator.start(mDown);
            }
        });
    }
}
