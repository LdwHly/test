package com.dx.log.logapplication.service;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

/**
 * Created by jlqiu on 2015/6/3.
 */
public class ScreenUtils {


    public static final String SCREAN_IMAGE_NAME = "screan.PNG";
    public static Bitmap screanCachebmp = null;

    private ScreenUtils()
    {  
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static float getDensity(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.density;
    }

    /**
     * 获得状态栏的高度 
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context)
    {

        int statusHeight = -1;
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏 
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 滚动ListView到指定位置
     *
     * @param pos
     */
    public static  void setListViewPos(ListView listView, int pos) {
        if(listView != null) {
            if (android.os.Build.VERSION.SDK_INT >= 8) {
                listView.smoothScrollToPosition(pos);
            } else {
                listView.setSelection(pos);
            }
        }
    }


    public static  void resetViewSize(View layout, int size){
        ViewGroup.LayoutParams  lp = layout.getLayoutParams();
        lp.width = size;
        lp.height = size;
        layout.setLayoutParams(lp);
    }

    public static Bitmap getViewToBitmap(View view) {
        Bitmap bitmap = null;
        if(view != null) {
            view.setDrawingCacheEnabled(true);
            view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            view.setDrawingCacheBackgroundColor(Color.WHITE);

            try {
                // 把一个View转换成图片
                Bitmap cachebmp = loadBitmapFromView(view);
                // 添加水印
                bitmap = Bitmap.createBitmap(createWatermarkBitmap(cachebmp, ""));
            } catch (Exception e) {
                view.destroyDrawingCache();
                return bitmap;
            }

            view.destroyDrawingCache();
        }
        return  bitmap;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if(drawable == null){
            return null;
        }
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    public static Bitmap snapShotWithoutStatusBarAndTitle(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap= view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;//获取状态栏的高
        int titleBarHeight = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();//获取标题栏的高
        int width = getScreenWidth(activity);//获取屏幕的宽
        int height = getScreenHeight(activity);//获取屏幕的高
        int totalBarHeight=titleBarHeight+statusBarHeight;
        Bitmap bm= Bitmap.createBitmap(bitmap, 0, totalBarHeight , width, height - totalBarHeight);
        view.destroyDrawingCache();
        return bm;
    }


    private static  Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }

    // 为图片target添加水印
    private static  Bitmap createWatermarkBitmap(Bitmap target, String str) {
        int w = target.getWidth();
        int h = target.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        Paint p = new Paint();

        // 水印的颜色
        p.setColor(Color.RED);

        // 水印的字体大小
        p.setTextSize(16);

        p.setAntiAlias(true);// 去锯齿

        canvas.drawBitmap(target, 0, 0, p);

        // 在中间位置开始添加水印
        canvas.drawText(str, w / 2, h / 2, p);

//        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.save();
        canvas.restore();

        return bmp;
    }

}
