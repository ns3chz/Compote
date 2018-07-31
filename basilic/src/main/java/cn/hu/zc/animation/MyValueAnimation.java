package cn.hu.zc.animation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.animation.ValueAnimator.RESTART;
import static android.animation.ValueAnimator.REVERSE;

public class MyValueAnimation {
    @IntDef({RESTART, REVERSE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatMode {}
}
