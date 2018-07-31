package cn.hu.zc.tools;

import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * ComputeTools
 */
public class CptTools {
    public static int parseInt(String str, int def) {
        if (str == null) {
            return def;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException var5) {
            var5.printStackTrace();
            return def;
        }
    }

    public static float parseFloat(String str, float def) {
        if (str == null) {
            return def;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return def;
        }
    }

    public static double parseDouble(String str, double def) {
        if (str == null) {
            return def;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return def;
        }
    }

    public static long parseLong(String str, long def) {
        if (str == null) {
            return def;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return def;
        }
    }

    /**
     * @param number  数字
     * @param minInt  minInt
     * @param maxInt  maxInt
     * @param minFrac minFrac
     * @param maxFrac maxFrac
     * @return string
     */
    public static String numbConstrain(Number number, int minInt, int maxInt, int minFrac, int maxFrac) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMinimumIntegerDigits(minInt);
        numberFormat.setMaximumIntegerDigits(maxInt);
        numberFormat.setMinimumFractionDigits(minFrac);
        numberFormat.setMaximumFractionDigits(maxFrac);
        return numberFormat.format(number);
    }

    /**
     * @return bol arrs is all equal
     */
    public static boolean isEquals(boolean... bol) {
        if (bol == null) {
            return false;
        } else if (bol.length == 1) {
            return true;
        } else {
            boolean fir = bol[0];

            for (int i = 1; i < bol.length; ++i) {
                if (fir ^ bol[i]) {
                    return false;
                }
            }

            return true;
        }
    }

    public static String formatTimeMillis(long time, String roleStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(roleStr);
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    /**
     * @return index is in collections size
     */
    public static boolean isInSize(Collection collections, int index) {
        return collections != null && index >= 0 && index < collections.size();
    }

    public static float getFontsWidth(Paint paint, String string) {
        return paint.measureText(string);
    }

    public static float getFountsWidth(int textsize, String text) {
        Paint paint = new Paint();
        paint.setTextSize((float) textsize);
        return paint.measureText(text);
    }

    public static float getFontsHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return fontMetrics.descent - fontMetrics.ascent;
    }

    public static float getFontsCenterY(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (fontMetrics.descent - fontMetrics.ascent) / 2.0F + fontMetrics.ascent;
    }

    /**
     * @param color          color
     * @param alpha          alpha
     * @param canTransparent canTransparent
     * @return caculate color
     */
    public static int calculateColor(@ColorInt int color, @IntRange(from = 0, to = 255) int alpha, boolean canTransparent) {
        float a = alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        int alp = 0;
        if (canTransparent) {
            alp = color >> 24 & 0xff;
            alp = (int) (alp * a + 0.5);
        }
        return (canTransparent ? alp : 0xff) << 24 | red << 16 | green << 8 | blue;
    }


    /**
     * @param color color
     * @return is dark
     */
    public static boolean colorIsDark(@ColorInt int color) {
        int alp = color >> 24 & 0xff;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
//        Log.d("color_rgb", "alp:" + alp + ",red" + red + ",green:" + green + ",blue:" + blue+",color"+color);
        float a = alp / 255;//透明度比例
        return (red + green + blue) * a < 660;
    }

    public static class ColorTranslate {
        private final @ColorInt
        int oldColor;
        private final @ColorInt
        int newColor;
        private final int oa;
        private final int or;
        private final int og;
        private final int ob;
        //
        private final int na;
        private final int nr;
        private final int ng;
        private final int nb;
        //
        private final int ca;
        private final int cr;
        private final int cg;
        private final int cb;
        //

        public ColorTranslate(int oldColor, int newColor) {
            this.oldColor = oldColor;
            this.newColor = newColor;
            oa = oldColor >> 24 & 0xff;
            or = oldColor >> 16 & 0xff;
            og = oldColor >> 8 & 0xff;
            ob = oldColor & 0xff;
            //
            na = newColor >> 24 & 0xff;
            nr = newColor >> 16 & 0xff;
            ng = newColor >> 8 & 0xff;
            nb = newColor & 0xff;
            //差值
            ca = oa - na;
            cr = or - nr;
            cg = og - ng;
            cb = ob - nb;
        }

        /**
         * 将颜色old过度为新颜色new
         *
         * @param percent 百分比
         * @return 新颜色
         */
        public @ColorInt
        int translate(@FloatRange(from = 0, to = 1) float percent) {
            //
            int a = (int) (oa - ca * percent);
            int r = (int) (or - cr * percent);
            int g = (int) (og - cg * percent);
            int b = (int) (ob - cb * percent);
            return a << 24 | r << 16 | g << 8 | b;
        }
    }

}
