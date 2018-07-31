//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.hu.zc.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

public class JsonTools {
    private static Gson mGson;

    private JsonTools() {
    }

    public static <T> T parseJson2Bean(String json, Class<T> clazz) {
        if (json != null && json.length() != 0) {
            mGson = (new GsonBuilder()).disableHtmlEscaping().create();
            T t = null;

            try {
                t = mGson.fromJson(json, clazz);
            } catch (JsonSyntaxException e) {
                Looger.logE("parseJson2Bean", "parseJson2Bean...requestFailed!!!");
                e.printStackTrace();
            }

            return t;
        } else {
            return null;
        }
    }

    public static <T> T parseJson2Bean(String json, Type type) {
        if (json != null && json.length() != 0) {
            mGson = (new GsonBuilder()).disableHtmlEscaping().create();
            T t = null;

            try {
                t = mGson.fromJson(json, type);
            } catch (JsonSyntaxException e) {
                Looger.logE("parseJson2Bean", "parseJson2Bean...requestFailed!!!");
                e.printStackTrace();
            }

            return t;
        } else {
            return null;
        }
    }

    public static String parseObject2JsonString(Object bean) {
        if (bean == null) {
            return null;
        } else {
            mGson = (new GsonBuilder()).disableHtmlEscaping().create();
            String mJson = "";

            try {
                mJson = mGson.toJson(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return mJson;
        }
    }
}
