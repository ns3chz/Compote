//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.hu.zc.animation;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;

public class TransAnimation extends MyValueAnimation {
    private ValueAnimator animator = ValueAnimator.ofFloat(0.0F, 1.0F);
    private long during;
    private int repeatCount = 0;
    private @RepeatMode
    int repeatMode = ValueAnimator.RESTART;
    private TimeInterpolator timeInterpolator;
    private int distansX;
    private int distansY;
    private boolean isReverse = false;
    private View view;
    private AnimatorListener myAnimatorListener;
    private AnimatorUpdateListener animatorUpdateListener = new AnimatorUpdateListener() {
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = (Float) animation.getAnimatedValue();
            if (TransAnimation.this.isReverse) {
                value = 1.0F - value;
            }

            if (TransAnimation.this.view != null) {
                TransAnimation.this.view.setTranslationX(value * (float) TransAnimation.this.distansX);
                TransAnimation.this.view.setTranslationY(value * (float) TransAnimation.this.distansY);
            }

        }
    };
    private AnimatorListener animatorListener = new AnimatorListener() {
        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
        }

        public void onAnimationCancel(Animator animation) {
        }

        public void onAnimationRepeat(Animator animation) {
        }
    };

    private TransAnimation() {
    }

    public static TransAnimation get() {
        return new TransAnimation();
    }

    public TransAnimation start() {
        if (this.during > 0L) {
            this.animator.setDuration(this.during);
            if (this.timeInterpolator != null) {
                this.animator.setInterpolator(this.timeInterpolator);
            }

            this.animator.setRepeatCount(this.repeatCount);
            this.animator.setRepeatMode(this.repeatMode);
            this.animator.removeAllUpdateListeners();
            this.animator.removeAllListeners();
            this.animator.addUpdateListener(this.animatorUpdateListener);
            this.animator.addListener(this.animatorListener);
            if (this.myAnimatorListener != null) {
                this.animator.addListener(this.myAnimatorListener);
            }

            this.animator.start();
        }

        return this;
    }

    public void setMyAnimatorListener(AnimatorListener myAnimatorListener) {
        this.myAnimatorListener = myAnimatorListener;
    }

    public TransAnimation setDuring(long during) {
        this.during = during;
        return this;
    }

    public TransAnimation setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
        return this;
    }

    public TransAnimation setRepeatMode(@RepeatMode int repeatMode) {
        this.repeatMode = repeatMode;
        return this;
    }

    public TransAnimation setTimeInterpolator(TimeInterpolator timeInterpolator) {
        this.timeInterpolator = timeInterpolator;
        return this;
    }

    public TransAnimation setDistansX(int distansX) {
        this.distansX = distansX;
        return this;
    }

    public TransAnimation setDistansY(int distansY) {
        this.distansY = distansY;
        return this;
    }

    public TransAnimation setView(View view) {
        this.view = view;
        return this;
    }

    public TransAnimation setReverse(boolean reverse) {
        this.isReverse = reverse;
        return this;
    }

    public TransAnimation ofFloat(float... values) {
        this.animator = ValueAnimator.ofFloat(values);
        return this;
    }
}
