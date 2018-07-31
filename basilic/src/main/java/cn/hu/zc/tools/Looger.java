package cn.hu.zc.tools;

import android.support.annotation.IntRange;
import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;

/**
 * Created by HuZC on 2017/11/1.
 */

public final class Looger {
    // -----------------------------------------------------------------------------------------------------
    // --------------LOOOOOOOOOG--------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------

    private final static String DEFAULT_LOG_TAG = Looger.class.getCanonicalName();
    private static boolean canLog = true;

    public static boolean canLog() {
        return canLog;
    }

    public static void setCanLog(boolean canLog) {
        Looger.canLog = canLog;
    }

    //---------------------------------------------------------------------------------


    /**
     */
    private static <T> void log(T t, String text, @IntRange(from = 1, to = 7) int deep, Object... obj) {
        if (canLog()) {
            if (text == null) {
                text = "";
            }
            String tag;
            tag = t == null ? DEFAULT_LOG_TAG : (t instanceof String ? (String) t : t.getClass().getCanonicalName());
            switch (deep) {
                case 1:
                    Logger.t(tag).v(text, obj);
                    break;
                case 2:
                    Logger.t(tag).d(text, obj);
                    break;
                case 3:
                    Logger.t(tag).i(text, obj);
                    break;
                case 4:
                    Logger.t(tag).w(text, obj);
                    break;
                case 5:
                    Logger.t(tag).e(text, obj);
                    break;
                case 6:
                    //打印json
                    Logger.t(tag).json(text);
                    break;
                case 7:
                    //打印xml
                    Logger.t(tag).xml(text);
                    break;
            }
        }
    }


    public static <T> void logV(T tag, String str, Object... obj) {
        log(tag, str, 1, obj);
    }


    public static <T> void logD(T tag, String str, Object... obj) {
        log(tag, str, 2, obj);
    }

    public static <T> void logI(T tag, String str, Object... obj) {
        log(tag, str, 3, obj);
    }

    public static <T> void logW(T tag, String str, Object... obj) {
        log(tag, str, 4, obj);
    }

    public static <T> void logE(T tag, String str, Object... obj) {
        log(tag, str, 5, obj);
    }

    public static <T> void json(T tag, String json) {
        log(tag, json, 6);
    }

    public static <T> void xml(T tag, String json) {
        log(tag, json, 7);
    }

    public static <T> void log(T tag, String str) {
        if (canLog()) {
            Log.w(tag == null ? DEFAULT_LOG_TAG : tag.getClass().getCanonicalName(), str);
        }
    }
}
