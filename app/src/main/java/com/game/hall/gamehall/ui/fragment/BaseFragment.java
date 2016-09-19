package com.game.hall.gamehall.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/6/17.
 */
public abstract class BaseFragment extends Fragment {


    protected Activity mContext;

    protected View rootView;
    protected boolean isInitialize;
    protected boolean isCrateView;

    public abstract void initView();

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = onCreateRootView();
        } else {
            removeParent();
        }
        isCrateView = true;
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        removeParent();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    protected void removeParent() {
        if (rootView == null)
            return;
        ViewGroup viewGroup = (ViewGroup) rootView.getParent();
        if (viewGroup != null)
            viewGroup.removeView(rootView);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    protected abstract View onCreateRootView();

//    abstract void initView();

    public View findViewById(int resid) {
        return rootView.findViewById(resid);
    }


}
