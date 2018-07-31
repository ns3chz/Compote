//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.hu.zc.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import cn.hu.zc.listener.JustListener;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public final class UITools {
    public UITools() {
    }

    public static Disposable runOnUIthread(Runnable runnable) {
        return runOnUIthread(runnable, 0L);
    }

    public static Disposable runOnUIthread(Runnable runnable, long time) {
        return Observable.just(runnable).subscribeOn(Schedulers.io())
                .delay(time, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Runnable>() {
                    public void accept(Runnable runnable) throws Exception {
                        if (runnable != null) {
                            runnable.run();
                        }

                    }
                }, new Consumer<Throwable>() {
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    /**
     * parse dp to px
     */
    public static int dp2px(float dp) {
        return (int) (dp * SystemParam.get().density + 0.5F);
    }

    /**
     * parse px to dp
     */
    public static int px2dp(float px) {
        return (int) (px / SystemParam.get().density + 0.5f);
    }

    /**
     * parse sp to px
     */
    public static int sp2px(float sp) {
        return (int) (sp * SystemParam.get().scaleDensity + 0.5f);
    }

    /**
     * parse px to sp
     */
    public static int px2sp(float px) {
        return (int) (px / SystemParam.get().scaleDensity + 0.5f);
    }

    /**
     * 获取当前界面的根view
     */
    public static ViewGroup getContentView(Activity activity) {
        ViewGroup contentView = activity.findViewById(android.R.id.content);
        if (contentView == null || contentView.getChildCount() != 1) {
            throw new IllegalStateException("未找到" + activity.getClass().getCanonicalName() + "根view的子view");
        }
        return contentView;
    }

    /**
     * 获取当前界面布局的根view
     */
    public static View getBodyView(Activity activity) {
        ViewGroup contentView = getContentView(activity);
        if (contentView == null) return null;
        return contentView.getChildAt(0);
    }

    /**
     * 向界面的根view添加子view，并处于最下层
     */
    public static void addViewToRoot(Activity activity, View view) {
        ViewGroup rootView = getContentView(activity);
        if (rootView == null) {
            return;
        }
        //获取内容
        rootView.addView(view, 0);
//        View content = rootView.getChildAt(0);
//        rootView.removeAllViews();
//        //
//        rootView.addView(view);
//        rootView.addView(content);
    }

    /**
     * 将界面根view提取出来添加到viewgroup中，并重新部署
     */
    public static void addRootToGroup(Activity activity, ViewGroup viewGroup) {
        ViewGroup rootView = getContentView(activity);
        if (rootView == null) {
            return;
        }
        //获取内容
        View content = rootView.getChildAt(0);
        rootView.removeAllViews();
        viewGroup.addView(content);
        rootView.addView(viewGroup);
    }

    /**
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        if (bitmap != null) {
            Bitmap.Config cfg = bitmap.getConfig();
            Log.d("convertViewToBitmap", "---- cache.getConfig() = " + cfg);
        }
        return bitmap;
    }

    /**
     * 获取当前界面得到焦点的view，若为edittext，则填入文字
     */
    public static void setStringToFocusEditText(Activity activity, String string) {
        View view = activity.getCurrentFocus();
        if (view != null && view instanceof EditText) {
            ((EditText) view).setText(string);
        }
    }

    /**
     * 拿到当前获取到焦点的edittext
     */
    public static EditText getFocusEditText(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null && view instanceof EditText) {
            return ((EditText) view);
        }
        return null;
    }

    /**
     * 設置view漸變的出現于消失
     */
    public static void showViewGently(final View view, long time,
                                      final boolean show) {
        if ((!view.isShown()) ^ show) {
            return;
        }
//        view.setVisibility(show ? View.GONE : View.VISIBLE);
        if (view.getAnimation() != null) {
            return;
        }

        Animation anim;
        if (show) {
            anim = new AlphaAnimation(view.getAlpha(), 1.0f);
        } else {
            anim = new AlphaAnimation(view.getAlpha(), 0.0f);
        }
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (show) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
                //

                // 回收bitmap
                // sendGoneVideoViewMsg();
            }
        });
        anim.setDuration(time);
        view.startAnimation(anim);
    }

    public static void showViewGently(final View view, long time,
                                      final boolean show, final JustListener.JustaListener listener) {
        if ((!view.isShown()) ^ show) {
            return;
        }
//        view.setVisibility(show ? View.GONE : View.VISIBLE);
        if (view.getAnimation() != null) {
            return;
        }
        Animation anim = null;
        if (show) {
            anim = new AlphaAnimation(view.getAlpha(), 1.0f);
        } else {
            anim = new AlphaAnimation(view.getAlpha(), 0.0f);
        }
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (show) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
                //
                if (listener != null) {
                    listener.listen();
                }

                // 回收bitmap
                // sendGoneVideoViewMsg();
            }
        });
        anim.setDuration(time);
        view.startAnimation(anim);
    }

    private static Animation delayAnim = null;

    public static void showViewGentlyDelay(final View view, long time,
                                           int delay, final boolean show, final JustListener.JustaListener listener) {
        if ((!view.isShown()) ^ show) {
            return;
        }
//        view.setVisibility(show ? View.GONE : View.VISIBLE);
        if (view.getAnimation() != null) {
            return;
        }
        if (show) {
            delayAnim = new AlphaAnimation(view.getAlpha(), 1.0f);
        } else {
            delayAnim = new AlphaAnimation(view.getAlpha(), 0.0f);
        }
        delayAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (show) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
                //
                if (listener != null) {
                    listener.listen();
                }
                // 回收bitmap
                // sendGoneVideoViewMsg();
            }
        });
        delayAnim.setDuration(time);
        JustListener.ListenDelay.listen(delay, new JustListener.JustaListener() {

            @Override
            public void listen() {
                view.startAnimation(delayAnim);

            }
        });
        // view.startAnimation(anim);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 设置textview左上右下图片
     */
    public static void setCompoundDrawables(TextView tv, @DrawableRes int l, @DrawableRes int t, @DrawableRes int r, @DrawableRes int b) {
        setCompoundDrawables(tv, l, t, r, b, 0, 0);
    }

    /**
     * 设置textview左上右下图片
     *
     * @param dx 水平偏移量
     * @param dy 竖直偏移量
     */
    public static void setCompoundDrawables(TextView tv, @DrawableRes int l, @DrawableRes int t, @DrawableRes int r, @DrawableRes int b, int dx, int dy) {
        if (tv == null) return;
        Context context = tv.getContext();
        Drawable left = null;
        Drawable right = null;
        Drawable top = null;
        Drawable bot = null;
        if (l != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                left = context.getResources().getDrawable(l, context.getTheme());
            } else {
                left = context.getResources().getDrawable(l);
            }
            left.setBounds(dx, dy, dx + left.getMinimumWidth(), dy + left.getMinimumHeight());
        }
        if (t != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                top = context.getResources().getDrawable(t, context.getTheme());
            } else {
                top = context.getResources().getDrawable(t);
            }
            top.setBounds(dx, dy, dx + top.getMinimumWidth(), dy + top.getMinimumHeight());
        }
        if (r != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                right = context.getResources().getDrawable(r, context.getTheme());
            } else {
                right = context.getResources().getDrawable(r);
            }
            right.setBounds(dx, dy, dx + right.getMinimumWidth(), dy + right.getMinimumHeight());
        }
        if (b != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                bot = context.getResources().getDrawable(b, context.getTheme());
            } else {
                bot = context.getResources().getDrawable(b);
            }
            bot.setBounds(dx, dy, dx + bot.getMinimumWidth(), dy + bot.getMinimumHeight());
        }
        tv.setCompoundDrawables(left, top, right, bot);
    }

    /**
     * 设置textview左上右下图片
     *
     * @param scales 缩放比例
     */
    public static void setCompoundDrawables(TextView tv, float scales, @DrawableRes int l, @DrawableRes int t, @DrawableRes int r, @DrawableRes int b) {
        if (tv == null) return;
        Context context = tv.getContext();
        Drawable left = null;
        Drawable right = null;
        Drawable top = null;
        Drawable bot = null;
        if (l != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                left = context.getResources().getDrawable(l, context.getTheme());
            } else {
                left = context.getResources().getDrawable(l);
            }
            //
            int lWidth = left.getMinimumWidth();
            int lHeight = left.getMinimumHeight();
            int lsWidth = (int) (lWidth * scales);
            int lsHeight = (int) (lHeight * scales);
            //
            left.setBounds(0, 0, lsWidth, lsHeight);
        }
        if (t != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                top = context.getResources().getDrawable(t, context.getTheme());
            } else {
                top = context.getResources().getDrawable(t);
            }
            int tWidth = top.getMinimumWidth();
            int tHeight = top.getMinimumHeight();
            int tsWidth = (int) (tWidth * scales);
            int tsHeight = (int) (tHeight * scales);
            //
            top.setBounds(0, 0, tsWidth, tsHeight);
        }
        if (r != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                right = context.getResources().getDrawable(r, context.getTheme());
            } else {
                right = context.getResources().getDrawable(r);
            }
            int rWidth = right.getMinimumWidth();
            int rHeight = right.getMinimumHeight();
            int rsWidth = (int) (rWidth * scales);
            int rsHeight = (int) (rHeight * scales);
            //
            right.setBounds(0, 0, rsWidth, rsHeight);
        }
        if (b != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                bot = context.getResources().getDrawable(b, context.getTheme());
            } else {
                bot = context.getResources().getDrawable(b);
            }
            int bWidth = bot.getMinimumWidth();
            int bHeight = bot.getMinimumHeight();
            int bsWidth = (int) (bWidth * scales);
            int bsHeight = (int) (bHeight * scales);
            //
            bot.setBounds(0, 0, bsWidth, bsHeight);
        }
        tv.setCompoundDrawables(left, top, right, bot);
    }

    /**
     * 判断view是否可以滚动 up:手势从上往下，down手势从下往上
     *
     * @param direction up:0,down:1
     */
    public static boolean canScroll(View view, int direction) {
        if (direction == 1) {
            //向下滚动
            if (Build.VERSION.SDK_INT < 14) {
                if (view instanceof AbsListView) {
                    final AbsListView absListView = (AbsListView) view;
                    return absListView.getChildCount() > 0
                            && (absListView.getLastVisiblePosition() < absListView.getChildCount() - 1
                            || absListView.getChildAt(absListView.getChildCount() - 1).getBottom() > absListView.getPaddingBottom());
                } else {
                    return view.canScrollVertically(1) || view.getScrollY() < 0;
                }
            } else {
                return view.canScrollVertically(1);
            }
        } else if (direction == 0) {
            //向上滚动
            if (Build.VERSION.SDK_INT < 14) {
                if (view instanceof AbsListView) {
                    final AbsListView absListView = (AbsListView) view;
                    return absListView.getChildCount() > 0
                            && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                            .getTop() < absListView.getPaddingTop());
                } else {
                    return view.canScrollVertically(-1) || view.getScrollY() > 0;
                }
            } else {
                return view.canScrollVertically(-1);
            }
        } else {
            Log.e("canScroll?", "direction is wrong!!!");
        }
        return false;
    }
}
