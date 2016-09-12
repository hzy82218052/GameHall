package com.game.hall.gamehall.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Display;
import android.view.View;

/**
 * Created by Administrator on 2016/9/12.
 */
public class BitmapUtils {

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static Bitmap getCurrentActivityShot(Activity activity) {
// 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();
// 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = activity.getWindowManager().getDefaultDisplay();
// 获取屏幕宽和高
        int widths = display.getWidth();
        int heights = display.getHeight();
// 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
// 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                statusBarHeights, widths, heights - statusBarHeights);
// 销毁缓存信息
        view.destroyDrawingCache();
        return bmp;
    }

    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        // 将bitmap转换成drawable
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        return bd;
    }

}
