//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.hu.zc.basilic.application;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import cn.hu.zc.manager.ActivityManager;
import cn.hu.zc.manager.CrashManager;
import cn.hu.zc.tools.Looger;

public class Basilication extends Application {
    private static Basilication INSTANCE;

    public static Basilication get() {
        return INSTANCE;
    }

//    public static Handler getMainHandler() {
//        return Basilication.SingleMainHandler.MAINHANDLER;
//    }

    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        this.initLogger();
        ActivityManager.bind(this);
        CrashManager.get().init(this);
    }

    private void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter() {
            public boolean isLoggable(int priority, String tag) {
                return Looger.canLog();
            }
        });
    }
//    private static class SingleMainHandler {
//        static Handler MAINHANDLER = new Handler(Looper.getMainLooper());
//
//        private SingleMainHandler() {
//        }
//    }
}
