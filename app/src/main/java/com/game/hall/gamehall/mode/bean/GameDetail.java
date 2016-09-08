package com.game.hall.gamehall.mode.bean;

import android.graphics.Bitmap;

import com.game.hall.download.bean.AppBean;

import java.util.List;

/**
 * Created by hezhiyong on 2016/9/7.
 */
public class GameDetail extends AppBean{

    public String mIcon;//图片
    public String mStar;//星级别
    public String mType;//类型
    public int[] mTypes;//类型
    public String mIntc;//介绍

    public Bitmap[] shots;//截图
    public List<AppBean> relateds;//相关

}
