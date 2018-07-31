package cn.hu.zc.permissiontools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;

public class PermissionManager {

    public static void check(@NonNull Context context, @NonNull String permission) {
        int res = PermissionChecker.checkSelfPermission(context, permission);
        switch (res) {
            case PermissionChecker.PERMISSION_GRANTED://已获取
                break;
            case PermissionChecker.PERMISSION_DENIED://被禁用
                break;
            //如果能够取消授权，就看现在是不是处于权限被允许的状态，如果不是，那就是用户主动关闭了权限
            case PermissionChecker.PERMISSION_DENIED_APP_OP:
                break;
        }
    }
}
