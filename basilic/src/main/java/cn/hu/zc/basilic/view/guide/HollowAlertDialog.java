package cn.hu.zc.basilic.view.guide;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import cn.hu.zc.basilic.dialog.BaseAlertDialogPresenter;

public class HollowAlertDialog extends BaseAlertDialogPresenter {

    public HollowView hollowView;

    public HollowAlertDialog(Context context) {
        super(context);
    }

    @Override
    public int setRes() {
        return 0;
    }

    @Override
    public View setView() {
        hollowView = new HollowView(mContext);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        hollowView.setLayoutParams(layoutParams);
        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        relativeLayout.addView(hollowView);
        return relativeLayout;
    }

    @Override
    public void buildParam(@NonNull AlertDialog.Builder alertDialogBuilder, @NonNull AlertDialog alertDialog, @NonNull View parent) {
    }

    @Override
    public void resetWindowParam(@NonNull Window window, @NonNull WindowManager windowManager, @NonNull WindowManager.LayoutParams windowParam) {
        super.resetWindowParam(window, windowManager, windowParam);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        windowParam.width = point.x;
        windowParam.height = point.y;
        window.setDimAmount(0);
        //
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(point.x, point.y);
//        hollowView.setLayoutParams(layoutParams);
    }
}
