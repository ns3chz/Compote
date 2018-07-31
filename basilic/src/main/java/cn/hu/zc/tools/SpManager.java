//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.hu.zc.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class SpManager {

    private static final Map<String, SharedPreferences> spList = new HashMap<>();
    private static final Map<SharedPreferences, Editor> editorList = new HashMap<>();
    private static SharedPreferences sp;
    private static Editor mEditor;

    public static SharedPreferences getSp(Context context, String spName) {
        if (context == null) {
            return null;
        } else {
            synchronized (spList) {
                if (!spList.containsKey(spName)) {
                    sp = context.getSharedPreferences(spName, 0);
                    spList.put(spName, sp);
                    return sp;
                } else {
                    return spList.get(spName);
                }
            }
        }
    }

    public static Editor getEditor(SharedPreferences spre) {
        if (spre == null) {
            return null;
        } else {
            synchronized (editorList) {
                if (!editorList.containsKey(spre)) {
                    mEditor = spre.edit();
                    editorList.put(spre, mEditor);
                    return mEditor;
                } else {
                    return editorList.get(spre);
                }
            }
        }
    }

    public static void putString2Sp(Context context, String spName, String key, String value) {
        if (context != null) {
            sp = getSp(context, spName);
            mEditor = getEditor(sp);
            mEditor.putString(key, value);
            mEditor.commit();
        } else {
            Log.e("SharedPreferenceManager", "SharedPreferenceManager HandleSharePre:context is null!!!");
        }

    }

    public static String getStringFromSp(Context context, String spName, String key, String defValue) {
        try {
            sp = getSp(context, spName);
            return sp.getString(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defValue;
        }
    }

    public static void putInt2Sp(Context context, String spName, String key, int value) {
        if (context != null) {
            sp = getSp(context, spName);
            mEditor = getEditor(sp);
            mEditor.putInt(key, value);
            mEditor.commit();
        } else {
            Log.e("SharedPreferenceManager", "SharedPreferenceManager HandleSharePre:context is null!!!");
        }

    }

    public static int getIntFromSp(Context context, String spName, String key, int defValue) {
        try {
            sp = getSp(context, spName);
            return sp.getInt(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defValue;
        }
    }

    public static void putBoolean2Sp(Context context, String spName, String key, boolean value) {
        if (context != null) {
            sp = getSp(context, spName);
            mEditor = getEditor(sp);
            mEditor.putBoolean(key, value);
            mEditor.commit();
        } else {
            Log.e("SharedPreferenceManager", "SharedPreferenceManager HandleSharePre:context is null!!!");
        }

    }

    public static boolean getBooleanFromSp(Context context, String spName, String key, boolean defValue) {
        try {
            sp = getSp(context, spName);
            return sp.getBoolean(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defValue;
        }
    }

    public static void putLong2Sp(Context context, String spName, String key, long value) {
        if (context != null) {
            sp = getSp(context, spName);
            mEditor = getEditor(sp);
            mEditor.putLong(key, value);
            mEditor.commit();
        } else {
            Log.e("SharedPreferenceManager", "SharedPreferenceManager HandleSharePre:context is null!!!");
        }

    }

    public static long getLongFromSp(Context context, String spName, String key, long defValue) {
        try {
            sp = getSp(context, spName);
            return sp.getLong(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defValue;
        }
    }

    public static void putFloat2Sp(Context context, String spName, String key, float value) {
        if (context != null) {
            sp = getSp(context, spName);
            mEditor = getEditor(sp);
            mEditor.putFloat(key, value);
            mEditor.commit();
        } else {
            Log.e("SharedPreferenceManager", "SharedPreferenceManager HandleSharePre:context is null!!!");
        }

    }

    public static float getFloatFromSp(Context context, String spName, String key, float defValue) {
        try {
            sp = getSp(context, spName);
            return sp.getFloat(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defValue;
        }
    }

    public static <T> void putObject2Sp(Context context, String spName, String key, T t) {
        if (context != null) {
            sp = getSp(context, spName);
            mEditor = getEditor(sp);
            mEditor.putString(key, JsonTools.parseObject2JsonString(t));
            mEditor.commit();
        } else {
            Log.e("SharedPreferenceManager", "SharedPreferenceManager HandleSharePre:context is null!!!");
        }

    }

    public static <T> T getBeanFromSp(Context context, String spName, String key, String defBeanJson, Class<T> clazz) {
        try {
            sp = getSp(context, spName);
            return JsonTools.parseJson2Bean(sp.getString(key, defBeanJson), clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonTools.parseJson2Bean(defBeanJson, clazz);
        }
    }

    public static <T> T getBeanFromSp(Context context, String spName, String key, String defBeanJson, Type type) {
        try {
            sp = getSp(context, spName);
            return JsonTools.parseJson2Bean(sp.getString(key, defBeanJson), type);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonTools.parseJson2Bean(defBeanJson, type);
        }
    }
}
