package cn.hu.zc.tools;

import android.content.ClipData;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import java.util.Set;

public class SynTools {

    /**
     * @param main  the main intent,stay main params
     * @param other other param
     * @return combine intent
     */
    public static Intent mergeIntent(Intent main, Intent other) {
        if (main == null) {
            return other == null ? new Intent() : other;
        }
        Intent result = new Intent(main);
        if (other == null) {
            return result;
        }
        String action = result.getAction();
        Uri data = result.getData();
        String type = result.getType();
        String aPackage = result.getPackage();
        ComponentName component = result.getComponent();
        int flags = result.getFlags();
        Set<String> categories = result.getCategories();
        Bundle extras = result.getExtras();
        Rect sourceBounds = result.getSourceBounds();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            Intent selector = result.getSelector();
            if (selector == null) {
                result.setSelector(other.getSelector());
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ClipData clipData = result.getClipData();
            if (clipData == null) {
                result.setClipData(other.getClipData());
            }
        }
        //
        if (action == null || action.length() == 0) {
            result.setAction(other.getAction());
        }
        if (data == null) {
            result.setData(other.getData());
        }
        if (type == null || type.length() == 0) {
            result.setType(other.getType());
        }
        if (aPackage == null || aPackage.length() == 0) {
            result.setPackage(other.getPackage());
        }
        if (component == null) {
            result.setComponent(other.getComponent());
        }
        if (flags == 0) {
            result.setFlags(other.getFlags());
        }
        if (categories == null) {
            Set<String> othercate = other.getCategories();
            if (othercate != null) {
                for (String cate : othercate) {
                    result.addCategory(cate);
                }
            }
        }
        if (extras == null && other.getExtras() != null) {
            result.putExtras(other.getExtras());
        }
        if (sourceBounds != null) {
            result.setSourceBounds(sourceBounds);
        }
        return result;
    }


}
