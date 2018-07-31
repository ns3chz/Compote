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

public class ScaleAnimation extends MyValueAnimation {

    private ValueAnimator animator = ValueAnimator.ofFloat(0.0F, 1.0F);
    private long during;
    private int repeatCount = 0;
    private @RepeatMode
    int repeatMode = ValueAnimator.RESTART;
    private TimeInterpolator timeInterpolator;
    private float distansX;
    private float distansY;
    private boolean isReverse = false;
    private View view;
    private boolean canScaleX = false;
    private boolean canScaleY = false;
    private AnimatorListener myAnimatorListener;
    private AnimatorUpdateListener animatorUpdateListener = new AnimatorUpdateListener() {
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = (Float) animation.getAnimatedValue();
            if (ScaleAnimation.this.isReverse) {
                value = 1.0F - value;
            }

            if (ScaleAnimation.this.view != null) {
                if (ScaleAnimation.this.canScaleX) {
                    ScaleAnimation.this.view.setScaleX(value * ScaleAnimation.this.distansX);
                }

                if (ScaleAnimation.this.canScaleY) {
                    ScaleAnimation.this.view.setScaleY(value * ScaleAnimation.this.distansY);
                }
            }

        }
    };
    private AnimatorListener animatorListener = new AnimatorListener() {
        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            ScaleAnimation.this.canScaleX = false;
            ScaleAnimation.this.canScaleY = false;
        }

        public void onAnimationCancel(Animator animation) {
        }

        public void onAnimationRepeat(Animator animation) {
        }
    };

    private ScaleAnimation() {
    }

    public static ScaleAnimation get() {
        return new ScaleAnimation();
    }

    public ScaleAnimation start() {
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

    public ScaleAnimation setDuring(long during) {
        this.during = during;
        return this;
    }

    public ScaleAnimation setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
        return this;
    }

    public ScaleAnimation setRepeatMode(@RepeatMode int repeatMode) {
        this.repeatMode = repeatMode;
        return this;
    }

    public ScaleAnimation setTimeInterpolator(TimeInterpolator timeInterpolator) {
        this.timeInterpolator = timeInterpolator;
        return this;
    }

    public ScaleAnimation setDistansX(float distansX) {
        this.canScaleX = true;
        this.distansX = distansX;
        return this;
    }

    public ScaleAnimation setDistansY(float distansY) {
        this.canScaleY = true;
        this.distansY = distansY;
        return this;
    }

    public ScaleAnimation setView(View view) {
        this.view = view;
        return this;
    }

    public ScaleAnimation setReverse(boolean reverse) {
        this.isReverse = reverse;
        return this;
    }

    public ScaleAnimation ofFloat(float... values) {
        this.animator = ValueAnimator.ofFloat(values);
        return this;
    }
}
