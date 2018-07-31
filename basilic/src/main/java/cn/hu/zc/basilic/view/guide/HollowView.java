package cn.hu.zc.basilic.view.guide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class HollowView<HV extends HollowView> extends FrameLayout {
    private @ColorInt
    final int DEF_BACKGROUND_COLOR = Color.argb(196, 0, 0, 0);
    private final Direct DEF_INSTRUCT_TEXT_DIRECT = Direct.BOT;
    private final int DEF_INSTRUCT_TEXT_GRAVITY = Gravity.CENTER;
    private int width;//控件宽度
    private int height;//高度
    private Paint drawPaint;
    private PorterDuffXfermode porterDuffXfermodeDSTIN;
    private View targetView;//高亮显示的view
    private @ColorInt
    int backgroundColor;//背景颜色
    private OnTouchListener onTouchListener;//背景触摸事件
    private boolean canTouchBelow = false;//能否触摸到背景后面的控件
    private Shape highlightShape = Shape.RECTANGLE;
    private float targetViewLeft;
    private float targetViewTop;
    private float targetViewRight;
    private float targetViewBot;
    private String instructText;//提示文字
    private Direct instructTextDirect;//提示文本框位置
    private int instructTextGravity;//提示文字在框内的位置
    private int instructTextOffsetX;//x偏移
    private int instructTextOffsetY;//y偏移
    private TextView instructTv;
    //


    public HollowView(Context context) {
        this(context, null);
    }

    public HollowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HollowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        drawPaint = new Paint();
        porterDuffXfermodeDSTIN = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        drawPaint.setAntiAlias(true);
//        drawPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.INNER));
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置背景颜色
        canvas.drawColor(getBackgroundColor());
        //
        if (targetView != null) {
            refreshTargetView();
            //
            drawPaint.setColor(Color.WHITE);
            drawPaint.setXfermode(porterDuffXfermodeDSTIN);
            switch (highlightShape) {
                case RECTANGLE:
                    drawRectangle(canvas);
                    break;
                case CIRCLE:
                    drawCircle(canvas);
                    break;
                case RING:
                    break;
            }
            drawPaint.setXfermode(null);
        }
        drawInstructText();
    }

    private void refreshTargetView() {
        if (targetView == null) return;
        targetViewLeft = targetView.getX();
        targetViewTop = targetView.getY();
        targetViewRight = targetViewLeft + targetView.getWidth();
        targetViewBot = targetViewTop + targetView.getHeight();
    }

    /**
     * 画矩形
     */
    private void drawRectangle(Canvas canvas) {
        canvas.drawRect(targetViewLeft, targetViewTop, targetViewRight, targetViewBot, drawPaint);
    }

    private void drawCircle(Canvas canvas) {
        float w = targetViewRight - targetViewLeft;
        float h = targetViewBot - targetViewTop;
        float ratio = Math.max(w, h) / 2;
        canvas.drawCircle(targetViewLeft + w / 2, targetViewTop + h / 2, ratio, drawPaint);
    }

    /**
     * 添加说明文字
     */
    private void drawInstructText() {
        instructTv = getInstructTv();
        int index = indexOfChild(instructTv);
        if (index < 0) {
            addView(instructTv);
        }
        instructTv.setText(instructText);
        instructTv.setGravity(getInstructTextGravity());
        FrameLayout.LayoutParams layoutParams = (LayoutParams) instructTv.getLayoutParams();
        float startX;
        float startY;
        switch (getInstructTextDirect()) {
            case LEFT:
                layoutParams.width = (int) targetViewLeft;
                layoutParams.height = LayoutParams.MATCH_PARENT;
                startX = 0;
                startY = targetViewTop;
                break;
            case TOP:
                layoutParams.width = LayoutParams.MATCH_PARENT;
                layoutParams.height = (int) targetViewTop;
                startX = targetViewLeft;
                startY = 0;
                break;
            case RIGHT:
                layoutParams.width = (int) (width - targetViewRight);
                layoutParams.height = LayoutParams.MATCH_PARENT;
                startX = targetViewRight;
                startY = 0;
                break;
            case BOT:
                layoutParams.width = LayoutParams.MATCH_PARENT;
                layoutParams.height = (int) (height - targetViewBot);
                startX = 0;
                startY = targetViewBot;
                break;
            default:
                startX = 0;
                startY = 0;
                break;
        }
        layoutParams.leftMargin = (int) startX + instructTextOffsetX;
        layoutParams.topMargin = (int) startY + instructTextOffsetY;
        instructTv.setLayoutParams(layoutParams);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.onTouchListener != null &&
                this.onTouchListener.onTouch(this, event, targetViewLeft, targetViewTop, targetViewRight, targetViewBot)) {
            return true;
        }
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (x >= targetViewLeft && x <= targetViewRight && y >= targetViewTop && y <= targetViewBot) {
                    if (onTouchListener != null) {
                        return onTouchListener.touchTarget(this, event, targetViewLeft, targetViewTop, targetViewRight, targetViewBot);
                    }
                } else {
                    if (onTouchListener != null) {
                        return onTouchListener.touchOut(this, event, targetViewLeft, targetViewTop, targetViewRight, targetViewBot);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return !canTouchBelow;
    }

    //---------------------------------------setter------------------------------------------------

    /**
     * @param view 设置高亮view
     */
    public HV targetView(View view) {
        targetView = view;
        return (HV) this;
    }

    /**
     * 设置说明文字
     */
    public HV text(String text) {
        instructText = text;
        return (HV) this;
    }

    /**
     * @return 设置文字处于高亮的方向与偏移量
     */
    public HV direct(Direct direct, int gravity, int offsetX, int offsetY) {
        instructTextDirect = direct;
        instructTextGravity = gravity;
        instructTextOffsetX = offsetX;
        instructTextOffsetY = offsetY;
        return (HV) this;
    }

    /**
     * @param color 背景色
     * @param alpha 透明度
     */
    public HV backgroundColor(@ColorInt int color, @FloatRange(from = 0, to = 1) float alpha) {
        int a = color >> 24 & 0xff;
        int r = color >> 16 & 0xff;
        int g = color >> 8 & 0xff;
        int b = color & 0xff;
        backgroundColor = ((int) (alpha * a)) << 24 | r << 16 | g << 8 | b;
        return (HV) this;
    }


    public HV onTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
        return (HV) this;
    }

    /**
     * @param canTouchBelow 能否触摸到背景后面的控件
     */
    public HV canTouchBelow(boolean canTouchBelow) {
        this.canTouchBelow = canTouchBelow;
        return (HV) this;
    }

    /**
     * @param highlightShape 高亮的形状
     */
    public HV highlightShape(Shape highlightShape) {
        this.highlightShape = highlightShape;
        return (HV) this;
    }


    //--------------------------------------------getter----------------------------------


    public @ColorInt
    int getBackgroundColor() {
        return backgroundColor == 0 ? DEF_BACKGROUND_COLOR : backgroundColor;
    }

    public Direct getInstructTextDirect() {
        return instructTextDirect == null ? DEF_INSTRUCT_TEXT_DIRECT : instructTextDirect;
    }

    public int getInstructTextGravity() {
        return instructTextGravity == 0 ? DEF_INSTRUCT_TEXT_GRAVITY : instructTextGravity;
    }

    public TextView getInstructTv() {
        if (instructTv == null) {
            instructTv = new TextView(getContext());
            FrameLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            instructTv.setLayoutParams(layoutParams);
        }
        return instructTv;
    }

    //----------------------------------class-----------------------------------------------
    public enum Shape {
        /**
         * 长方形
         */
        RECTANGLE, RING, CIRCLE
    }

    public enum Direct {
        LEFT, TOP, RIGHT, BOT
    }

    public interface OnTouchListener {
        /**
         * targetView's l,t,r,b
         *
         * @return 拦截
         */
        boolean onTouch(View v, MotionEvent event, float l, float t, float r, float b);

        /**
         * targetView's l,t,r,b
         *
         * @return 拦截
         */
        boolean touchTarget(View view, MotionEvent event, float l, float t, float r, float b);

        /**
         * targetView's l,t,r,b
         *
         * @return 拦截
         */
        boolean touchOut(View view, MotionEvent event, float l, float t, float r, float b);
    }
}
