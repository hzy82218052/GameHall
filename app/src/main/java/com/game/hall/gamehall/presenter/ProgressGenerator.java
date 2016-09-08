package com.game.hall.gamehall.presenter;

import android.os.Handler;
import android.util.Log;

import com.dd.processbutton.ProcessButton;
import com.game.hall.download.bean.AppBean;
import com.game.hall.download.download.conf.UGAppConf;

import java.util.Random;

public class ProgressGenerator {

    public interface OnCompleteListener {

        public void onComplete();
    }

    private OnCompleteListener mListener;
    private int mProgress;

    public ProgressGenerator(OnCompleteListener listener) {
        mListener = listener;
    }

    public void start(final ProcessButton button) {
        final AppBean bean = (AppBean) button.getTag();
        final UGAppConf appConf = bean.getAppConf();

        UGAppConf.UGOnChangeListenner changeListenner = new UGAppConf.UGOnChangeListenner() {

            @Override
            public void onChange(final UGAppConf conf, int state) {
                button.setProgress(conf.percent);
                if (conf.percent == 100) {
                    mListener.onComplete();
                    button.setEnabled(true);
                }
            }
        };
        appConf.addChangeListenner(changeListenner);
        switch (appConf.state) {
            case UGAppConf.STATE_INIT:
            case UGAppConf.STATE_WAIT:
            case UGAppConf.STATE_DOWNING:
            case UGAppConf.STATE_PAUSE:
            case UGAppConf.STATE_UPDATE:
                appConf.downApp(button.getContext());
                button.setEnabled(false);
                break;
            case UGAppConf.STATE_FINISH:
                appConf.installApp(button.getContext());
                button.setEnabled(true);
                break;
            case UGAppConf.STATE_INSTALL:
                appConf.openApp(button.getContext());
                button.setEnabled(true);
                break;
        }

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mProgress += 1;
//                button.setProgress(mProgress);
//                if (mProgress < 100) {
//                    handler.postDelayed(this, generateDelay());
//                } else {
//                    mListener.onComplete();
//                }
//            }
//        }, generateDelay());
    }

    private Random random = new Random();

    private int generateDelay() {
        return random.nextInt(10);
    }
}
