package cn.hu.zc;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;

public class RatioUtils {
	private static TypedArray array;
	private static float ratio;

	public static float getRatio(Context context, AttributeSet attrs,
			int[] styleAble, int styleIndex, int defalt) {
		array = context.obtainStyledAttributes(attrs, styleAble);
		ratio = array.getFloat(styleIndex, defalt);
		array.recycle();
		return ratio;
	}

	/**
	 * 宽/高，宽已知
	 * 
	 * @param widthMeasureSpec
	 * @param ratio
	 * @return
	 */
	public static int X2Y(int widthMeasureSpec, float ratio) {
		// int width = MeasureSpec.getSize(widthMeasureSpec);
		// float height = width / ratio;
		// return MeasureSpec.makeMeasureSpec((int) height,
		// MeasureSpec.EXACTLY);
		return MeasureSpec.makeMeasureSpec(
				(int) (MeasureSpec.getSize(widthMeasureSpec) / ratio),
				MeasureSpec.EXACTLY);
	}

	/**
	 * 高/宽,高已知
	 * 
	 * @param heightMeasureSpec
	 * @param ratio
	 * @return
	 */
	public static int Y2X(int heightMeasureSpec, float ratio) {
		// int height = MeasureSpec.getSize(heightMeasureSpec);
		// float width = height * ratio;
		// return MeasureSpec.makeMeasureSpec((int) width, MeasureSpec.EXACTLY);
		return MeasureSpec.makeMeasureSpec(
				(int) (MeasureSpec.getSize(heightMeasureSpec) * ratio),
				MeasureSpec.EXACTLY);
	}
}
