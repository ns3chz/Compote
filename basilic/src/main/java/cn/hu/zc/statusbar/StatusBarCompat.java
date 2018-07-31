package cn.hu.zc.statusbar;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.hu.zc.tools.CptTools;

/**
 * Utils for status bar
 * Created by qiu on 3/29/16.
 */
public class StatusBarCompat {
    //Get alpha color

    /**
     * return statusBar's Height in pixels
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;

        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelOffset(resId);
        }
        return statusBarHeight;
    }

    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int statusColor) {
        setStatusBarColor(activity.getWindow(), statusColor, false);
    }


    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int statusColor, int alpha) {
        setStatusBarColor(activity, statusColor, alpha, false);
    }

    /**
     * @param activity
     * @param statusColor
     * @param below
     * @param alpha
     */
    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int statusColor, int alpha, boolean below) {
        setStatusBarColor(activity, statusColor, alpha, below, true);
    }

    /**
     * @param statusColor    状态栏颜色
     * @param alpha          透明度: 0 - 255
     * @param below          状态栏是否压住布局
     * @param canTransparent 能否全透明,false:当透明度为255时，呈黑色
     */
    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int statusColor, int alpha, boolean below, boolean canTransparent) {
        setStatusBarColor(activity.getWindow(), statusColor, alpha, below, canTransparent);
    }

    /**
     * @param statusColor    状态栏颜色
     * @param alpha          透明度: 0 - 255
     * @param below          状态栏是否压住布局
     * @param canTransparent 能否全透明,false:当透明度为255时，呈黑色
     */
    public static void setStatusBarColor(@NonNull Window window, @ColorInt int statusColor, int alpha, boolean below, boolean canTransparent) {
        setStatusBarColor(window, CptTools.calculateColor(statusColor, alpha, canTransparent), below);
    }

    private static void setStatusBarColor(@NonNull Window window, @ColorInt int statusColor, boolean below) {
        synchronized (window) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //fix状态栏是否压住布局
                StatusBarCompatLollipop.setStatusBarColor(window, statusColor, below);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                StatusBarCompatKitKat.setStatusBarColor(window, statusColor, below);
            }
            //若状态栏背景色为白色，文字设置为深色
//        boolean isColorDark = CptTools.colorIsDark(statusColor);
////        if (!colorIsDark(statusColor)) {
//        if (setMiuiStatusBarDarkMode(window, !isColorDark)) {
//            //miui系统设置成功
//        } else if (setMeizuStatusBarDarkIcon(window, !isColorDark)) {
//            //flyme系统设置成功
//        }
//        }
        }
    }

//    public static void setWindowFullScreen(@NonNull Window window) {
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
////        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
////        }
//    }

    /**
     * unUse
     */
    private static void translucentStatusBar(@NonNull Activity activity) {
        translucentStatusBar(activity, false);
    }

    /**
     * unUse
     * change to full screen mode
     *
     * @param hideStatusBarBackground hide status bar alpha Background when SDK > 21, true if hide it
     */
    private static void translucentStatusBar(@NonNull Activity activity, boolean hideStatusBarBackground) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.translucentStatusBar(activity, hideStatusBarBackground);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.translucentStatusBar(activity);
        }
    }

    /**
     * @param activity
     * @param appBarLayout
     * @param collapsingToolbarLayout
     * @param toolbar
     * @param statusColor
     */
    public static void setStatusBarColorForCollapsingToolbar(@NonNull Activity activity, AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout,
                                                             Toolbar toolbar, @ColorInt int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //6.0以上
            activity.getWindow().setStatusBarColor(statusColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.setStatusBarColorForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.setStatusBarColorForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusColor);
        }
    }


    /**
     * 小米状态栏文字深色和浅色设置
     */
    public static boolean setMiuiStatusBarDarkMode(Window window, boolean darkmode) {
        try {
            Class<? extends Window> clazz = window.getClass();
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(window, darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * flyme状态栏文字深色和浅色设置
     */
    public static boolean setMeizuStatusBarDarkIcon(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
