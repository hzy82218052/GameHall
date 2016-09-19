package com.game.hall.gamehall.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
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

    /**
     * @param map         image
     * @param haloWidthPx halo width, unit in pixel
     * @param haloColor   halo color
     * @return source image if haloWidth is zero
     */
    public static Bitmap addHaloForImage(Bitmap map, int haloWidthPx, int haloColor) {
        if (isValidBitmap(map)) {
            if (haloWidthPx < 0) {
                haloWidthPx = 20;
            }
            if (haloWidthPx != 0) {
                // method one
                Paint p = new Paint();
                p.setColor(haloColor);
                p.setAntiAlias(true);
                p.setFilterBitmap(true);
                MaskFilter bmf = new BlurMaskFilter(haloWidthPx, BlurMaskFilter.Blur.SOLID);
                p.setMaskFilter(bmf);
                Bitmap d = Bitmap.createBitmap(map.getWidth() + haloWidthPx * 2, map.getHeight() + haloWidthPx * 2, Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(d);
                c.drawBitmap(map.extractAlpha(), haloWidthPx, haloWidthPx, p);
                p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
                c.drawBitmap(map, haloWidthPx, haloWidthPx, p);
                map.recycle();
                System.gc();
                // end
                return d;
            }

        }
        return map;
    }

    public static boolean isValidBitmap(Bitmap map) {
        if (map != null) {
            if (!map.isRecycled()) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取圆角矩形图片方法
     *
     * @param bitmap
     * @param roundPx,一般设置成14
     * @return Bitmap
     * @author caizhiming
     */
    private Bitmap getRoundBitmap(Bitmap bitmap, int roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int x = bitmap.getWidth();

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;


    }
}
