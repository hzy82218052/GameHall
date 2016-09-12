package com.game.hall.gamehall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dd.processbutton.iml.ActionProcessButton;
import com.game.hall.gamehall.utils.BitmapUtils;

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
