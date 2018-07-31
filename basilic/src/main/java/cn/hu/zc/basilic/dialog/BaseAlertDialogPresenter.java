package cn.hu.zc.basilic.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;

import cn.hu.zc.basilic.R;


/**
 * 需要设置statusbar颜色时，必须添加id为status_bar的view布局
 * Created by Administrator on 2017/6/20.
 */

public abstract class BaseAlertDialogPresenter {
    protected Context mContext;
    protected AlertDialog.Builder dialogBuilder;
    protected AlertDialog alertDialog;
    protected LayoutInflater mLayoutInflater;
    protected boolean shouldReloadWindowParam = true;//重新show时，重新加载一遍屏幕参数，若参数有改变需调用notifyWindowParamChanged()设置为true
    protected float SCREEN_DIM = 0.4f;//window背景的透明度，0~100%，默认0.4f
    protected float DIALOG_WIDTH = 0.8f;//dialog宽度，0~100%，默认0.8f
    protected float DIALOG_HEIGHT = WindowManager.LayoutParams.WRAP_CONTENT;
    protected View statusBarView;
    //
    private int statusBarColor;


    public BaseAlertDialogPresenter(Context context) {
        this(context, Color.TRANSPARENT);

    }

    /**
     * @param color 状态栏颜色
     */
    public BaseAlertDialogPresenter(Context context, @ColorInt int color) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(this.mContext);
        this.statusBarColor = color;
        build();
        create();
    }

    /**
     * 创建Builder
     */
    private AlertDialog.Builder build() {
        this.dialogBuilder = new AlertDialog.Builder(this.mContext, setStyle());

        return this.dialogBuilder;
    }

    /**
     * 创建AlertDialog
     */
    private AlertDialog create() {
        this.alertDialog = this.dialogBuilder.create();
        if (this.alertDialog == null) return null;
        //根View
        View rootView = setView();
        if (rootView == null) {
            int res = setRes();
            if (res != 0) {
                rootView = this.mLayoutInflater.inflate(res, null);
            }
        }
        if (rootView != null) {
            statusBarView = rootView.findViewById(R.id.status_bar);
        }
        //检测是否有statusbar，设置全屏和颜色
        if (statusBarView != null && alertDialog != null && alertDialog.getWindow() != null) {
//            StatusBarCompat.setWindowFullScreen(alertDialog.getWindow());
            alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //
            ViewGroup.LayoutParams statusBarLayoutParams = statusBarView.getLayoutParams();
            statusBarLayoutParams.height = getStatusbarHeight(mContext);
            statusBarView.setLayoutParams(statusBarLayoutParams);
            statusBarView.setBackgroundColor(statusBarColor);
            statusBarView.setVisibility(View.VISIBLE);
        }
        //
        if (rootView != null) {
            buildParam(this.dialogBuilder, this.alertDialog, rootView);//向外提供builder、dialog与rootview
            this.alertDialog.setView(rootView);//填充view'
        } else {
            Log.e(getClass().getName(), "rootView is NULL .");
        }
//        this.alertDialog.setContentView(rootView);

        return this.alertDialog;
    }

    /**
     * 设置dialog参数
     */
    private void resetWindowParam() {
        Window window = this.alertDialog.getWindow();
        if (window == null) return;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        WindowManager windowManager = window.getWindowManager();
        if (layoutParams == null || windowManager == null) return;
        this.resetWindowParam(window, windowManager, layoutParams);//设置屏幕参数
        window.setAttributes(layoutParams);//将修改重新设置到屏幕

    }

    /**
     * 显示dialog
     */
    public void show() {
        if (this.alertDialog == null) return;
        this.showBefore();
        this.alertDialog.show();
        //重新加载屏幕参数
        if (this.shouldReloadWindowParam) {
            resetWindowParam();
            this.shouldReloadWindowParam = false;
        }
    }

    /**
     * 关闭dialog
     */
    public void dissmiss() {
        if (this.alertDialog == null) return;
        this.alertDialog.dismiss();
    }

    /**
     * 重新显示会再次加载屏幕参数，走setWindowParam(windowManager,windowParam);
     */
    public void notifyWindowParamChanged() {
        this.shouldReloadWindowParam = true;
    }


    /**
     * dialog的风格
     *
     * @return style id
     */
    protected
    @StyleRes
    int setStyle() {
        //主要是noActionBar属性，若没有这个属性，设置dialog位置为top时会留出actionbar的距离
        return android.R.style.Theme_Holo_Dialog_NoActionBar;
    }

    /**
     * 调用show方法前做的操作
     */
    public void showBefore() {

    }

    /**
     * 布局id
     *
     * @return layout id
     */
    public abstract
    @LayoutRes
    int setRes();

    public View setView() {
        return null;
    }

    /**
     * 添加布局前，设置参数
     *
     * @param alertDialogBuilder builder
     * @param alertDialog        dialog
     * @param parent             dialog根view
     */
    public abstract void buildParam(@NonNull AlertDialog.Builder alertDialogBuilder, @NonNull AlertDialog alertDialog, @NonNull View parent);

    /**
     * 管理屏幕
     *
     * @param windowManager 屏幕管理
     * @param windowParam   屏幕参数对象
     */
    public void resetWindowParam(@NonNull Window window, @NonNull WindowManager windowManager, @NonNull WindowManager.LayoutParams windowParam) {
        //设置dialog宽度
        if (DIALOG_WIDTH >= 0 && DIALOG_WIDTH <= 1) {
            windowParam.width = (int) (DIALOG_WIDTH * windowManager.getDefaultDisplay().getWidth());
        } else {
            windowParam.width = (int) DIALOG_WIDTH;
        }
        //设置dialog高度
        if (DIALOG_HEIGHT >= 0 && DIALOG_HEIGHT <= 1) {
            windowParam.height = (int) (DIALOG_HEIGHT * windowManager.getDefaultDisplay().getWidth());
        } else {
            windowParam.height = (int) DIALOG_HEIGHT;
        }
        //
        windowParam.gravity = Gravity.CENTER;
//         window.setDimAmount(SCREEN_DIM);//设置dialog外背景的透明度
        windowParam.dimAmount = SCREEN_DIM;//亦可设置dialog外背景的透明度
        //
        window.setBackgroundDrawableResource(android.R.color.transparent);  //设置背景为透明，否则有黑底
    }

    //----------------------------------------------setter-------------------------------------------------


    /**
     * @param SCREEN_DIM //内容外的屏幕透明度，0~100%，默认0.4f
     */
    public final void setScreenDim(float SCREEN_DIM) {
        this.SCREEN_DIM = SCREEN_DIM;
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = getWindowLayoutParams();
        if (window != null && layoutParams != null) {
            layoutParams.dimAmount = SCREEN_DIM;
            window.setAttributes(layoutParams);
        }
    }

    /**
     * @param DIALOG_WIDTH //dialog宽度，0~100%屏幕宽度，默认0.8f
     */
    public final void setDialogWidth(float DIALOG_WIDTH) {
        Window window = getWindow();
        WindowManager windowManager = getWindowManager();
        WindowManager.LayoutParams layoutParams = getWindowLayoutParams();
        if (window != null && layoutParams != null && windowManager != null) {
            if (DIALOG_WIDTH > 1) {
                this.DIALOG_WIDTH = DIALOG_WIDTH;
            } else if (DIALOG_WIDTH >= 0 && DIALOG_WIDTH <= 1) {
                layoutParams.width = (int) (DIALOG_WIDTH * windowManager.getDefaultDisplay().getHeight());
            } else {
                return;
            }

            window.setAttributes(layoutParams);
        }
    }

    /**
     * @param DIALOG_HEIGHT //dialog高度，0~100%屏幕高度，默认WindowManager.LayoutParams.WRAP_CONTENT包裹内容
     */
    public final void setDialogHeight(float DIALOG_HEIGHT) {
        Window window = getWindow();
        WindowManager windowManager = getWindowManager();
        WindowManager.LayoutParams layoutParams = getWindowLayoutParams();
        if (window != null && layoutParams != null && windowManager != null) {
            if (DIALOG_HEIGHT > 1) {
                this.DIALOG_HEIGHT = DIALOG_HEIGHT;
            } else if (DIALOG_HEIGHT >= 0 && DIALOG_HEIGHT <= 1) {
                layoutParams.height = (int) (DIALOG_HEIGHT * windowManager.getDefaultDisplay().getHeight());
            } else {
                return;
            }

            window.setAttributes(layoutParams);
        }
    }

    //----------------------------------------------getter-------------------------------------------------


    /**
     */
    public Window getWindow() {
        if (alertDialog != null) {
            return alertDialog.getWindow();
        }
        return null;
    }

    /**
     */
    public WindowManager getWindowManager() {
        Window window = getWindow();
        if (window != null) {
            return window.getWindowManager();
        }
        return null;
    }

    /**
     */
    public WindowManager.LayoutParams getWindowLayoutParams() {
        Window window = getWindow();
        if (window != null) {
            return window.getAttributes();
        }
        return null;
    }

    /**
     * @return 获取状态栏高度
     */
    public static int getStatusbarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 38;// 默认�?38，貌似大部分是这样的

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    /**
     * 是否显示
     */
    public boolean isShown() {
        if (alertDialog != null) {
            return alertDialog.isShowing();
        }
        return false;
    }
    //------------------------------------Tools----------------------------------------------------

    private DisplayMetrics displayMetrics;

    private DisplayMetrics getDisplayMetrics() {
        if (mContext == null) return null;
        if (displayMetrics == null) {
            displayMetrics = mContext.getResources().getDisplayMetrics();
        }
        return displayMetrics;
    }

    //---------------------------listener--------------------------------------------------

    /**
     * dialog消失监听
     */
    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        if (alertDialog != null) {
            alertDialog.setOnDismissListener(onDismissListener);
        }
    }

    /**
     * 确认监听
     */
    public interface OnDialogConfirmListener {
        void confirm();
    }

    public OnDialogConfirmListener onDialogConfirmListener;

    public void setOnDialogConfirmListener(OnDialogConfirmListener listener) {
        this.onDialogConfirmListener = listener;
    }

    /**
     * 取消监听
     */
    public interface OnDialogCancelListener {
        void cancel();
    }

    public OnDialogCancelListener onDialogCancelListener;

    public void setOnDialogCancelListener(OnDialogCancelListener listener) {
        onDialogCancelListener = listener;
    }

}
