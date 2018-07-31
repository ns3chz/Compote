//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.hu.zc.tools;

public final class Debuger {
    private static boolean isDebug = false;

    private Debuger() {
    }

    public static void isDebug(boolean is) {
        isDebug(is, is);
    }

    public static void isDebug(boolean is, boolean can) {
        isDebug = is;
        Looger.setCanLog(can);
    }

    public static boolean isDebug() {
        return isDebug;
    }
}
