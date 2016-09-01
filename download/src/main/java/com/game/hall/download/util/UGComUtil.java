package com.game.hall.download.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by Administrator on 2016/8/25.
 */
public class UGComUtil {
    /**
     * 将图片设置为圆角
     * @param bitmap
     * @param roundPx
     * @return Bitmap
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int roundPx)
    {
        Bitmap output = null;
        try
        {
            if (bitmap != null && !bitmap.isRecycled())
            {
                output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);
                Canvas canvas = new Canvas(output);
                final int color = 0xff424242;
                final Paint paint = new Paint();
                final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                final RectF rectF = new RectF(rect);
                paint.setAntiAlias(true);
                canvas.drawARGB(0, 0, 0, 0);
                paint.setColor(color);
                canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
                paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(bitmap, rect, rect, paint);
                return output;
            }
            else
            {
                return new BitmapDrawable().getBitmap();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            if (output != null && !output.isRecycled())
            {
                output.recycle();
            }

            return new BitmapDrawable().getBitmap();
        }
    }
}
