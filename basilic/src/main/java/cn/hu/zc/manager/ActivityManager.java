package cn.hu.zc.manager;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.WindowManager;

import java.lang.ref.SoftReference;
import java.util.Stack;

import cn.hu.zc.tools.Looger;
import cn.hu.zc.tools.SynTools;


/**
 * Activity soft management
 * Created by HuZC on 2017/3/23.
 */

public final class ActivityManager {
    private static IntentAdapter baseIntentAdapter;


    private ActivityManager() {

    }

//    /**
//     * @return get single obj
//     */
//    public static ActivityManager get() {
//        if (INSTANCE == null) {
//            synchronized (ActivityManager.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new ActivityManager();
//                }
//            }
//        }
//        return INSTANCE;
//    }

    //正常启动---------------------------------------------------------
    public static void startActivity(Context context, Intent intent) {
        context.startActivity(getBaseActivityIntent(intent));
    }

    public static void startActivityForResult(Activity activity, Intent intent, int requestCode) {
        activity.startActivityForResult(getBaseActivityIntent(intent), requestCode);
    }

    public static void startActivity(Context context, Class<?> clazz) {
        context.startActivity(getBaseActivityIntent(context, clazz));
    }

    public static void startActivityForResult(Activity activity, Class<?> clazz, int requestCode) {
        activity.startActivityForResult(getBaseActivityIntent(activity, clazz), requestCode);
    }

    //view关联启动---------------------------------------------------------
    public static void startActivity(Activity activity, Intent intent, View shareElement, String shareElementName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && shareElement != null && shareElementName != null && shareElementName.length() > 0) {
            shareElement.setTransitionName(shareElementName);
            ActivityCompat.startActivity(activity, getBaseActivityIntent(intent),
                    ActivityOptions.makeSceneTransitionAnimation(activity, shareElement, shareElementName).toBundle());
        } else {
            startActivity(activity, intent);
        }
    }

    public static void startActivity(Activity activity, Class<?> clazz, View shareElement, String shareElementName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && shareElement != null && shareElementName != null && shareElementName.length() > 0) {
            shareElement.setTransitionName(shareElementName);
            ActivityCompat.startActivity(activity, getBaseActivityIntent(activity, clazz),
                    ActivityOptions.makeSceneTransitionAnimation(activity, shareElement, shareElementName).toBundle());
        } else {
            startActivity(activity, clazz);
        }
    }

    public static void startActivityForResult(Activity activity, Intent intent, int requestCode, View shareElement, String shareElementName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && shareElement != null && shareElementName != null && shareElementName.length() > 0) {
            shareElement.setTransitionName(shareElementName);
            ActivityCompat.startActivityForResult(activity, getBaseActivityIntent(intent), requestCode,
                    ActivityOptions.makeSceneTransitionAnimation(activity, shareElement, shareElementName).toBundle());
        } else {
            startActivityForResult(activity, intent, requestCode);
        }
    }

    public static void startActivityForResult(Activity activity, Class<?> clazz, int requestCode, View shareElement, String shareElementName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && shareElement != null && shareElementName != null && shareElementName.length() > 0) {
            shareElement.setTransitionName(shareElementName);
            ActivityCompat.startActivityForResult(activity, getBaseActivityIntent(activity, clazz), requestCode,
                    ActivityOptions.makeSceneTransitionAnimation(activity, shareElement, shareElementName).toBundle());
        } else {
            startActivityForResult(activity, clazz, requestCode);
        }
    }

    //动画启动---------------------------------------------------------
    public static void startActivity(Context context, Intent intent, int enterResId, int exitResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityCompat.startActivity(context, getBaseActivityIntent(intent),
                    ActivityOptions.makeCustomAnimation(context, enterResId, exitResId).toBundle());
        } else {
            startActivity(context, intent);
        }
    }

    public static void startActivityForResult(Activity activity, Intent intent, int requestCode, int enterResId, int exitResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityCompat.startActivityForResult(activity, getBaseActivityIntent(intent), requestCode,
                    ActivityOptions.makeCustomAnimation(activity, enterResId, exitResId).toBundle());
        } else {
            startActivityForResult(activity, intent, requestCode);
        }
    }

    public static void startActivity(Context context, Class<?> clazz, int enterResId, int exitResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityCompat.startActivity(context, getBaseActivityIntent(context, clazz),
                    ActivityOptions.makeCustomAnimation(context, enterResId, exitResId).toBundle());
        } else {
            startActivity(context, clazz);
        }
    }

    public static void startActivityForResult(Activity activity, Class<?> clazz, int requestCode, int enterResId, int exitResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityCompat.startActivityForResult(activity, getBaseActivityIntent(activity, clazz), requestCode,
                    ActivityOptions.makeCustomAnimation(activity, enterResId, exitResId).toBundle());
        } else {
            startActivityForResult(activity, clazz, requestCode);
        }
    }

    //-------------------------------------------------------------------------------------------------------------

    public interface IntentAdapter {
        public Intent getIntent();
    }

    /**
     * @param adapter def intent adapter,put def intent params
     */
    public static void setBaseIntentAdapter(IntentAdapter adapter) {
        baseIntentAdapter = adapter;
    }

    /**
     * @param context packageContent
     * @param clazz   cls
     * @return intent to activity
     */
    private static Intent getBaseActivityIntent(Context context, Class<?> clazz) {
        Intent intent = null;
        if (baseIntentAdapter != null) {
            intent = baseIntentAdapter.getIntent();
        }
        if (intent == null) {
            intent = new Intent();
        }
        intent.setClass(context, clazz);
        return intent;
    }

    /**
     * @param intent intent
     * @return intent to activity
     */
    private static Intent getBaseActivityIntent(Intent intent) {
        Intent result = null;
        if (baseIntentAdapter != null) {
            Intent baseIntent = baseIntentAdapter.getIntent();
            result = SynTools.mergeIntent(intent, baseIntent);
        }
        if (result == null) {
            result = new Intent(intent);
        }
        return result;
    }


    //---------------------------------------------------

    /**
     * @param application 通过application观察各个activity的生命周期
     */
    public static void bind(@NonNull Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ActivityManager.Binder.get().add(activity);
                Looger.log(activity, "onActivityCreated hash: " + activity.hashCode());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                ActivityManager.Binder.get().putActivityTop(activity);
                Looger.log(activity, "onActivityStarted hash: " + activity.hashCode());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Looger.log(activity, "onActivityResumed hash: " + activity.hashCode());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Looger.log(activity, "onActivityPaused hash: " + activity.hashCode());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Looger.log(activity, "onActivityStopped hash: " + activity.hashCode());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Looger.log(activity, "onActivitySaveInstanceState hash: " + activity.hashCode());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Looger.log(activity, "onActivityDestroyed hash: " + activity.hashCode());
                ActivityManager.Binder.get().remove(activity);
            }
        });
    }

    public static class Binder {
        private static Binder INSTANCE;
        private Stack<SoftReference<Activity>> mActivityStack;

        private Binder() {
            this.mActivityStack = new Stack<>();
        }

        /**
         * @return get single obj
         */
        public static Binder get() {
            if (INSTANCE == null) {
                synchronized (Binder.class) {
                    if (INSTANCE == null) {
                        INSTANCE = new Binder();
                    }
                }
            }
            return INSTANCE;
        }

        /**
         * add activity to manage list
         * standard模式下，相同类型开启的activity时不同实例；
         * 其他模式下，会将activity从原来的位置抹除掉，放置在栈顶，不抹除可能会在返回时打开不存在的activity
         */
        public void add(Activity activity) {
//        Looger.log(getClass().getSimpleName(), "<—————————" + mActivityStack.size() + "———————————>");
            if (activity == null) {
                Looger.log(this.getClass().getSimpleName(), "add activity null!!!");
                return;
            }
            synchronized (this) {
                mActivityStack.push(new SoftReference<>(activity));
//            Looger.log(getClass().getSimpleName(), "压入Activity至栈顶: " + activity.getClass().getCanonicalName() +
//                    "(taskId:" + activity.getTaskId() + " hashCode:" + activity.hashCode() + ")");
//            Looger.log(getClass().getSimpleName(), "<—————————" + mActivityStack.size() + "———————————>");
            }
        }

        /**
         * finish activity
         */
        public void remove(Activity activity) {//TODO 在onstart中将activity提到栈顶
//        Looger.log(getClass().getSimpleName(), "<—————————" + mActivityStack.size() + "———————————>");
            if (activity == null) {
                Looger.log(this.getClass().getSimpleName(), "remove activity null!!!");
                return;
            }
            synchronized (this) {
                //TODO
                for (int i = 0; i < mActivityStack.size(); i++) {
                    SoftReference<Activity> peek = mActivityStack.get(i);
                    if (peek == null) continue;
                    if (activity.equals(peek.get())) {
                        mActivityStack.remove(peek);
//                    Looger.log(getClass().getSimpleName(), "移除Activity: " + activity.getClass().getCanonicalName() +
//                            "(taskId:" + activity.getTaskId() + " hashCode:" + activity.hashCode() + ")");
                        break;
                    }
                }
//            Looger.log(getClass().getSimpleName(), "<—————————" + mActivityStack.size() + "———————————>");
            }
        }

        /**
         * 在stack列表中置顶activity
         */
        public void putActivityTop(Activity activity) {
            if (activity == null) {
                Looger.log(this.getClass().getSimpleName(), "finish() null activity,or activity is finishing!!!");
                return;
            }
            synchronized (this) {
                for (int i = 0; i < mActivityStack.size(); i++) {
                    SoftReference<Activity> peek = mActivityStack.get(i);
                    if (peek == null) continue;
                    if (activity.equals(peek.get())) {
                        if (i == mActivityStack.size() - 1) break;
                        mActivityStack.remove(peek);
                        mActivityStack.push(peek);
//                    Looger.log(getClass().getSimpleName(), "置顶Activity: " + activity.getClass().getCanonicalName() +
//                            "(taskId:" + activity.getTaskId() + " hashCode:" + activity.hashCode() + ")");
                        break;
                    }
                }
            }
        }

        /**
         * @return activity opened count
         */
        public synchronized int getCount() {
            return mActivityStack.size();
        }

        /**
         * 获取当前activity
         */
        public synchronized Activity getCurrent() {
            SoftReference<Activity> reference = mActivityStack.peek();
            return reference.get();
        }

        /**
         * finish all activity
         */
        public synchronized void quitApp() {
            for (int i = 0; i < mActivityStack.size(); i++) {
                SoftReference<Activity> reference = mActivityStack.get(i);
                Activity activity = reference.get();
                if (activity == null || activity.isFinishing()) continue;
                activity.finish();
            }
        }

        /**
         * 清空管理列表
         */
        public synchronized void clearAll() {
            mActivityStack.clear();
        }

    }

    /**
     * 返回至桌面
     *
     * @param context content
     */
    public static void gobackHome(Context context) {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(i);
    }

    /**
     * 返回至桌面
     *
     * @param enterAnim home enter anim
     * @param exitAnim  activity exit anim
     */
    public static void gobackHome(Activity activity, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        int animIn = enterAnim;
        int animOut = exitAnim;
        if (animIn == 0) animIn = android.R.anim.fade_in;
        if (animOut == 0) animOut = android.R.anim.fade_out;
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        activity.startActivity(i);
        activity.overridePendingTransition(animIn, animOut);
    }

    /**
     * @return 判断当前手机是否是全屏
     */
    public static boolean isFullScreen(Activity activity) {
        int flag = activity.getWindow().getAttributes().flags;
        return (flag & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

}
