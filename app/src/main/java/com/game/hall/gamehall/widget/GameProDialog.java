package com.game.hall.gamehall.widget;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by hezhiyong on 2016/9/4.
 */
public class GameProDialog {

    private static ProgressDialog pd;

    public static ProgressDialog createProDialog(Context context) {
        pd = new ProgressDialog(context);// 创建ProgressDialog对象
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置进度条风格，风格为圆形，旋转的             
        pd.setTitle("提示");// 设置ProgressDialog 标题     
        pd.setMessage("正在下载游戏...");// 设置ProgressDialog提示信息
//        pd.setIcon(R.drawable.secondback);// 设置ProgressDialog标题图标
        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确     
        pd.setIndeterminate(false);
        pd.setCancelable(true); // 设置ProgressDialog 是否可以按退回键取消              
        pd.setProgress(100);// 设置ProgressDialog 进度条进度      
        pd.show(); // 让ProgressDialog显示
        return pd;
    }

}


