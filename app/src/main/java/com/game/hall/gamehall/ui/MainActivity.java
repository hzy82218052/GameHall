package com.game.hall.gamehall.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.game.hall.download.download.conf.UGAppConfManager;
import com.game.hall.gamehall.R;
import com.game.hall.gamehall.ui.fragment.BaseFragment;
import com.game.hall.gamehall.ui.fragment.Win8Fragment;
import com.game.hall.gamehall.widget.drag.DragView;
import com.game.hall.gamehall.widget.drag.DragViewHodler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 首页
 */
public class MainActivity extends FragmentBaseActivity implements View.OnClickListener {

//    http://blog.csdn.net/lmj623565791/article/details/38173061/
//    http://blog.csdn.net/wujainew/article/details/50973249
//    http://blog.csdn.net/lmj623565791/article/details/38140505

    private ViewGroup title;//标题
    private ViewGroup titleGroup;//标题
    private ViewGroup typeGroup;//类型
    private TextView selectTitle = null;//当前选中标题view
    private TextView selectType = null;//当前选中标题view

    private View mSearch;//搜索

    private DragView mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    //碎片集合
    private Map<String, Win8Fragment> fragmentMaps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initListener();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.game_activity_home;
    }

    @Override
    protected int getFragmentID() {
        return R.id.lay_frame;
    }

    @Override
    protected void handleFragments(FragmentTransaction transaction, Fragment currentFragment, List<Fragment> list) {
        if (list != null) {
            for (Fragment frag : list) {
                if (frag != null && frag instanceof BaseFragment) {
                    if (frag != currentFragment) {
                        transaction.hide(frag);
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UGAppConfManager.getManager().exit(this);
        clearFragments();
        DragViewHodler.getInstance().cleanCache();
    }

    /**
     * 初始化
     */
    private void initView() {
        title = (ViewGroup) findViewById(R.id.layout_title);
        titleGroup = (ViewGroup) findViewById(R.id.game_title);
        typeGroup = (ViewGroup) findViewById(R.id.game_type);
        mSearch = findViewById(R.id.game_search);

        /** 初始化标题 */
        selectTitle = (TextView) titleGroup.getChildAt(0);
        selectType = (TextView) typeGroup.getChildAt(0);
        selectTitle.setSelected(true);
        selectType.setSelected(true);

        setTitleData(title);

        DragViewHodler.getInstance().setDragView(getDragView());
        updateWin8();
    }

    /**
     * 初始化监听
     */
    void initListener(){
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private DragView getDragView() {
        return (DragView) findViewById(R.id.drawer_layout);
    }

    /**
     * 设置标题的数据
     *
     * @param viewGroup
     */
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
            if (i == 0) {
                txt.setSelected(true);
            }
            txt.setOnClickListener(this);
        }
    }

    //设置监听事件
    @Override
    public void onClick(View v) {
        ViewGroup parent = (ViewGroup) v.getParent();
        switch (parent.getId()) {
            case R.id.game_title:

                if (selectTitle != null) {
                    if (selectTitle.equals(v))
                        return;
                    selectTitle.setSelected(false);
                }

                v.setSelected(true);
                selectTitle = (TextView) v;
                break;
            case R.id.game_type:
                if (selectType != null) {
                    if (selectType.equals(v))
                        return;
                    selectType.setSelected(false);
                }

                v.setSelected(true);
                selectType = (TextView) v;
                break;
        }
        updateWin8();
    }


    /**
     * 更新Win8碎片界面
     */
    public void updateWin8() {
        final String title = selectTitle.getText().toString();
        final String type = selectType.getText().toString();

        String key = title + "|" + type;

        //添加Fragment
        if (fragmentMaps == null)
            fragmentMaps = new HashMap<String, Win8Fragment>();

        Win8Fragment win8Fragment = fragmentMaps.get(key);
        if (win8Fragment == null) {
            win8Fragment = new Win8Fragment();
            Bundle arguments = new Bundle();
            arguments.putString(Win8Fragment.TITLE, title);
            arguments.putString(Win8Fragment.GAMETYPE, type);
            win8Fragment.setArguments(arguments);
            fragmentMaps.put(key, win8Fragment);
        }
        updateFragment(win8Fragment);
    }

}
