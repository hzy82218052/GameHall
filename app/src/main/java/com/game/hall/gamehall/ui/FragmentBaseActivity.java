package com.game.hall.gamehall.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.game.hall.gamehall.R;
import com.game.hall.gamehall.utils.WindowUtil;

import java.util.List;


/**
 * Created by Administrator on 2016/6/17.
 */
public abstract class FragmentBaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtil.bepaintStatusBar(this, getResources().getColor(R.color.colorTransparent));
        setContentView(getContent());
    }

    protected View getContent() {
        return View.inflate(this, getLayoutID(), null);
    }

    //加载布局
    protected abstract int getLayoutID();

    //需要替换的布局
    protected abstract int getFragmentID();

    //管理fragments
    protected abstract void handleFragments(FragmentTransaction transaction, Fragment currentFragment, List<Fragment> list);


//    protected void setBackFunction(){
//
//    }

    @Override
    protected void onResume() {
        super.onResume();
        AppForegroundStateManager.getInstance().onActivityVisible(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppForegroundStateManager.getInstance().onActivityNotVisible(this);
    }


    public void updateFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> list = manager.getFragments();
        handleFragments(transaction, fragment, list);
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(getFragmentID(), fragment);
        }
        transaction.commitAllowingStateLoss();
    }

    public void clearFragments() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> list = manager.getFragments();
        if (list == null)
            return;
        for (Fragment frag : list) {
            if (frag != null && frag.isAdded()) {
                transaction.remove(frag);
                transaction.commitAllowingStateLoss();
            }
        }
    }

}
