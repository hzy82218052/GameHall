package com.game.hall.gamehall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.game.hall.download.bean.AppBean;
import com.game.hall.download.download.conf.UGAppConf;
import com.game.hall.download.download.conf.UGAppConfManager;
import com.game.hall.gamehall.widget.GameImageView;
import com.game.hall.gamehall.widget.GameProDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    http://blog.csdn.net/lmj623565791/article/details/38173061/
//    http://blog.csdn.net/wujainew/article/details/50973249
//    http://blog.csdn.net/lmj623565791/article/details/38140505

    private ViewGroup win8;
    private ViewGroup title;
    private View selectView = null;
    private List<AppBean> datas = new ArrayList<AppBean>();
    private ProgressDialog gameProDialog;
    private int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity_home);

        win8 = (ViewGroup) findViewById(R.id.game_win8);
        title = (ViewGroup) findViewById(R.id.layout_title);

        UGAppConfManager.getManager().init(this, false);

        setTitleData(title);
        creatTestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UGAppConfManager.getManager().exit(this);
    }

    private void creatTestData() {
        index = 0;
        for (int i = 0; i < 8; i++) {
            AppBean bean = new AppBean();
            bean.setAppcode(167);
            bean.setAppname("欢乐斗地主" + i);
            bean.setApppackagename("com.qqgame.hlddz");
            bean.setApplink("http://113.107.216.43/imtt.dd.qq.com/16891/2D4C5564FAD16B59F41F2A969DB8685D.apk?mkey=57cad5ae53b64884&f=d688&c=0&fsname=com.qqgame.hlddz_5.12.007_167.apk&csr=4d5s&p=.apk");
            bean.setAppsize("1000000" + i);
            bean.setRptkey("1000" + i);
            datas.add(bean);
        }
        setWin8Data(win8);
    }

    public void onMy(View view) {
        Intent intent = new Intent(this,GameDetailActivity.class);
        startActivity(intent);

//        int viewWidth = view.getWidth();
//        int viewHeight = view.getHeight();
//        Log.i("@hzy", "---" + "viewWidth=" + viewWidth + "    " + "viewHeight=" + viewHeight);
//        final AppBean bean = (AppBean) view.getTag();
//        final UGAppConf appConf = bean.getAppConf();
//
//        UGAppConf.UGOnChangeListenner changeListenner = new UGAppConf.UGOnChangeListenner() {
//
//            @Override
//            public void onChange(UGAppConf conf, int state) {
//                if (gameProDialog != null) {
//                    gameProDialog.setProgress(conf.percent);
//                    if (conf.percent == 100) {
//                        gameProDialog.cancel();
//                    }
//                }
//            }
//        };
//        appConf.addChangeListenner(changeListenner);
//        switch (appConf.state) {
//            case UGAppConf.STATE_INIT:
//            case UGAppConf.STATE_WAIT:
//            case UGAppConf.STATE_DOWNING:
//            case UGAppConf.STATE_PAUSE:
//            case UGAppConf.STATE_UPDATE:
//                gameProDialog = GameProDialog.createProDialog(this);
//                appConf.downApp(this);
//                break;
//            case UGAppConf.STATE_FINISH:
//                appConf.installApp(this);
//                break;
//            case UGAppConf.STATE_INSTALL:
//                appConf.openApp(this);
//                break;
//        }
    }

    private void setWin8Data(ViewGroup viewGroup) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            final View view = viewGroup.getChildAt(i);
            if (!(view instanceof GameImageView)) {
                if (view instanceof ViewGroup)
                    setWin8Data((ViewGroup) view);
                continue;
            }
            final GameImageView gameView = (GameImageView) view;
            final AppBean bean = datas.get(index);

            int drawRes = R.mipmap.game_01;
            switch (gameView.getMode()) {
                case VERTICAL:
                    drawRes = R.mipmap.game_05;
                    break;
                case HORIZONTAL:
                    drawRes = (int) (Math.random() * 2) == 1 ? R.mipmap.game_01 : R.mipmap.game_04;
                    break;
                case SQUARE:
                    drawRes = (int) (Math.random() * 2) == 1 ? R.mipmap.game_02 : R.mipmap.game_03;
                    break;
            }
            gameView.setDrawableResource(drawRes);
            gameView.setTxt(bean.getAppname());
            gameView.setTag(bean);
            index++;
        }
    }

    private void setTitleData(ViewGroup viewGroup) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            final View view = viewGroup.getChildAt(i);
            if (!(view instanceof TextView)) {
                if (view instanceof ViewGroup)
                    setTitleData((ViewGroup) view);
                continue;
            }

            final TextView txt = (TextView) view;
            txt.setOnClickListener(onClickListener);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(selectView!=null)
                selectView.setSelected(false);
            v.setSelected(true);
            selectView = v;
        }
    };

}
