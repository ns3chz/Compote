package cn.hu.zc.tools;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.AppOpsManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.telephony.SmsManager;

import java.util.ArrayList;

/**
 * 系统设置ui
 */
public class SystemUI {
    public static final int RESULT_CODE_SMS_NO_MSG = 1000;//短信返回码，没有信息
    public static final int RESULT_CODE_SMS_NO_PERMISSION = 1001;//短信返回码，没有权限

    /**
     * 打开系统设置
     */
    public static void openSystemSet(Context context) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 打开账号添加界面
     */
    public static void openAddAccount(Context context) {
        Intent intent = new Intent(Settings.ACTION_ADD_ACCOUNT);
        context.startActivity(intent);
    }

    /**
     * 打开飞行模式设置
     */
    public static void openAirplaneMode(Context context) {
        Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 打开APN设置
     */
    public static void openAPN(Context context) {
        Intent intent = new Intent(Settings.ACTION_APN_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 通知权访问设置的界面(通知使用权)
     */
    public static void openNotificationListener(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            context.startActivity(intent);
        } else {
            openSystemSet(context);
        }

    }


    /**
     * 蓝牙设置
     */
    public static void openBluetooth(Context context) {
        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 定位设置界面
     */
    public static void openLocationSource(Context context) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 双卡和移动网络设置界面
     */
    public static void openDataRomaing(Context context) {
        Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 声音设置
     */
    public static void openSound(Context context) {
        Intent intent = new Intent(Settings.ACTION_SOUND_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 语言设置
     */
    public static void openLanguage(Context context) {
        Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 打开辅助功能界面
     */
    public static void openAccessibility(Context context) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 安全设置界面
     */
    public static void openSecurity(Context context) {
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * VPN设置界面,可能不存在
     */
    public static void openVPN(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Intent intent = new Intent(Settings.ACTION_VPN_SETTINGS);
            context.startActivity(intent);
        } else {
            openSystemSet(context);
        }
    }

    /**
     * 勿扰权限设置的界面
     */
    public static void openNotificationPolicyAccess(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            context.startActivity(intent);
        } else {
            openSystemSet(context);
        }

    }

    /**
     * 应用详细信息
     */
    public static void openApplicationDetails(Context context, String pkg) {
        if (pkg == null || pkg.length() == 0) {
            openApplicationSettings(context);
            return;
        }
        Uri uri = Uri.parse("package:" + pkg);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
        context.startActivity(intent);
    }

    /**
     * 开发者选项设置
     */
    public static void openApplicationDev(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 显示设置，以允许应用程序相关的设置配置
     */
    public static void openApplicationSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 电池信息界面
     */
    public static void openBatterySaver(Context context) {
        Intent intent = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, 0);
        if (resolveInfo != null) {
            //check that the battery app exists on device
            context.startActivity(intent);
        } else {
            openSystemSet(context);
        }
    }

    /**
     * 向系统发送消息
     */
    public static class Send {
        /**
         * 打开网页
         *
         * @param url "http://..."
         */
        public static void openUrl(Context context, String url) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }

        /**
         * 显示地图
         *
         * @param longitude 经度
         * @param latitude  纬度
         */
        public static void openLocate(Context context, String longitude, String latitude) {
            Uri uri = Uri.parse("geo:" + longitude + "," + latitude);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }

        /**
         * 转到拨号界面
         *
         * @param phoneNum 电话号码
         */
        public static void call(Context context, String phoneNum) {
            Uri uri = Uri.parse("tel:" + phoneNum);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            context.startActivity(intent);
        }

        /**
         * 发送短信//
         * （TODO 当超过发送长度时，分段发送,判断中英文，中文只能发送70，英文可以发送140）
         *
         * @param phone           号码
         * @param msg             信息
         * @param sentReceiver    发送成功广播监听.(若没有权限，则模拟返回码)
         *                        getResultCode()==
         *                        { Activity.RESULT_OK-发送成功;
         *                        SmsManager.RESULT_ERROR_GENERIC_FAILURE;
         *                        SmsManager.RESULT_ERROR_RADIO_OFF;
         *                        SmsManager.RESULT_ERROR_NULL_PDU;}
         *                        {
         *                        RESULT_CODE_SMS_NO_MSG-内容为空；
         *                        RESULT_CODE_SMS_NO_PERMISSION-没有发送短信的权限
         *                        }
         * @param deliverReceiver 接收成功广播监听
         */
        public static void sms(Activity context, String phone, String msg,
                               BroadcastReceiver sentReceiver, BroadcastReceiver deliverReceiver) {
            int sms = ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS);
            boolean b = ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.SEND_SMS);
            int c = PermissionChecker.checkSelfPermission(context, Manifest.permission.SEND_SMS);
//            AppOpsManagerCompat.n
            boolean hasPermiss = sms == PackageManager.PERMISSION_GRANTED;
            if (msg == null || msg.length() == 0 || hasPermiss) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
                intent.putExtra("sms_body", msg);
                context.startActivity(intent);
                //
                int resultCode;
                if (msg == null || msg.length() == 0) {
                    resultCode = RESULT_CODE_SMS_NO_MSG;
                } else {
                    resultCode = RESULT_CODE_SMS_NO_PERMISSION;
                }
                if (sentReceiver != null) {
                    sentReceiver.setResult(resultCode, null, null);
                    sentReceiver.onReceive(context, new Intent());
                }
                if (deliverReceiver != null) {
                    deliverReceiver.setResult(resultCode, null, null);
                    deliverReceiver.onReceive(context, new Intent());
                }
                return;
            }
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> divideMessage = smsManager.divideMessage(msg);//分段
            ArrayList<PendingIntent> pendingIntents = new ArrayList<>();
            //接收发送成功的广播
            if (sentReceiver != null) {
                String ACTION_SENT_SMS = "ACTION_SENT_SMS";//发送的action
                Intent sentIntent = new Intent(ACTION_SENT_SMS);
                PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, sentIntent, 0);
                context.registerReceiver(sentReceiver, new IntentFilter(ACTION_SENT_SMS));
                pendingIntents.add(sentPI);
            }
            //
            if (deliverReceiver != null) {
                String ACTION_DELIVERED_SMS = "ACTION_DELIVERED_SMS";//接收的action
                Intent deliverIntent = new Intent(ACTION_DELIVERED_SMS);
                PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0, deliverIntent, 0);
                context.registerReceiver(deliverReceiver, new IntentFilter(ACTION_DELIVERED_SMS));
                pendingIntents.add(deliverPI);
            }
            smsManager.sendMultipartTextMessage(phone, null, divideMessage, pendingIntents, null);
        }

        /**
         * 卸载应用
         *
         * @param pkg 包名
         */
        public static void uninstall(Context context, String pkg) {
            Uri uri = Uri.fromParts("package", pkg, null);
            Intent intent = new Intent(Intent.ACTION_DELETE, uri);
            context.startActivity(intent);
        }

        /**
         * 安装应用
         *
         * @param url 安装包路径
         */
        public static void install(Context context, String url) {
            Uri uri = Uri.fromParts("package", url, null);
            Intent intent = new Intent(Intent.ACTION_PACKAGE_ADDED, uri);
            context.startActivity(intent);
        }
    }

}
