package cn.hu.zc.tools;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.hu.zc.basilic.application.Basilication;

/**
 * Created by HuZC on 2017/10/26.
 */

public final class Toaster {
    // -----------------------------------------------------------------------------------------------------
    // -----------jump_Toast(response_fast)----------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------
    private static Toast globalCustomViewToast;

    private static Toast globalImgToast;
    private static Toast globalToast;

//    private static LinearLayout globalToastCustomView;
//    private static LinearLayout globalToastImgView;

    /**
     * LOG_STRING
     *
     * @param text text
     */
    public static void toast(final Context context, final String text) {
        UITools.runOnUIthread(new Runnable() {
            @Override
            public void run() {
                if (globalToast == null) {
                    globalToast = Toast.makeText(context, text,
                            Toast.LENGTH_SHORT);
                } else {
                    globalToast.setText(text);
                }
                globalToast.show();
            }
        });
    }

    /**
     * CUSTOM_TOAST_POSITION
     *
     * @param text    text
     * @param gravity gravity
     * @param xOffset xOffset
     * @param yOffset yOffset
     */
    public static void toast(final Context context, final String text,
                             final int gravity, final int xOffset, final int yOffset) {
        UITools.runOnUIthread(new Runnable() {
            @Override
            public void run() {
                if (globalToast == null) {
                    globalToast = Toast.makeText(context, text,
                            Toast.LENGTH_SHORT);
                } else {
                    globalToast.setText(text);
                }
                globalToast.setGravity(gravity, xOffset, yOffset);

                globalToast.show();
            }
        });
    }

    /**
     * TOAST_IMAGE_STRING
     *
     * @param text          text
     * @param imgResourseId resourseId
     * @param gravity       gravity
     * @param xOffset       xOffset
     * @param yOffset       yOffset
     */
    public static void toast(final Context context, final String text,
                             final int imgResourseId, final int gravity, final int xOffset,
                             final int yOffset) {
        UITools.runOnUIthread(new Runnable() {
            @Override
            public void run() {
                if (globalImgToast == null) {
                    globalImgToast = Toast.makeText(context, text,
                            Toast.LENGTH_SHORT);
                } else {
                    globalImgToast.setText(text);
                }
                // set_gravity
                globalImgToast.setGravity(gravity, xOffset, yOffset);
                // set_image
                // solve_add_image_repeat,remove_view_before_textview
                LinearLayout globalToastImgView = (LinearLayout) globalImgToast
                        .getView();
                int childViewCount = globalToastImgView.getChildCount();
                if (childViewCount > 1) {
                    for (int i = 0; i < childViewCount; i++) {
                        if (i != globalToastImgView.getChildCount() - 1) {
                            globalToastImgView.removeView(globalToastImgView
                                    .getChildAt(i));
                        }
                    }
                }
                // imageView_cant_be_convert_to_a_local_variable_field,otherwise_cause_child_already_has_parent
                ImageView globalToastImage = new ImageView(Basilication.get());
                globalToastImage.setImageResource(imgResourseId);

                globalToastImgView.addView(globalToastImage, 0);
                globalImgToast.show();
            }
        });
    }

    /**
     * toast custom view
     *
     * @param view    view
     * @param gravity gravity
     * @param xOffset xOffset
     * @param yOffset yOffset
     */
    public static void toast(final Context context, final View view,
                             final int gravity, final int xOffset, final int yOffset) {
        UITools.runOnUIthread(new Runnable() {
            @Override
            public void run() {
                if (globalCustomViewToast == null) {
                    globalCustomViewToast = Toast.makeText(context, null,
                            Toast.LENGTH_SHORT);
                }
                // set Gravity
                globalCustomViewToast.setGravity(gravity, xOffset, yOffset);
                // set Image
                LinearLayout globalToastCustomView = (LinearLayout) globalCustomViewToast
                        .getView();
                globalToastCustomView.removeAllViews();

                globalToastCustomView.addView(view);
                globalCustomViewToast.show();
            }
        });
    }
}
