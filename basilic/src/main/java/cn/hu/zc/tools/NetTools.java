package cn.hu.zc.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Patterns;
import android.webkit.URLUtil;

import java.util.regex.Pattern;

public class NetTools {
    /**
     * Network Connect Type
     */
    public enum NCT {
        MOBILE, WIFI, NONE, ETHERNET, BLUETOOTH, VPN,
        DUMMY, MOBILE_DUN, WIMAX
    }

    public enum NCT_TYPE {
        WIFI, MOBILE, MOBILE_UMTS, MOBILE_GPRS, MOBILE_EDGE,
        MOBILE_EVDO_0, MOBILE_CDMA, MOBILE_EVDO_A, MOBILE_1xRTT,
        MOBILE_HSDPA, MOBILE_HSUPA, MOBILE_HSPA, MOBILE_IDEN,
        MOBILE_EVDO_B, MOBILE_LTE, MOBILE_EHRPD, MOBILE_HSPAP
    }

    /**
     * @return wifi或数据网络是否连接, isNetworkConnected(context)!=null时连接
     */
    @SuppressLint("MissingPermission")
    public static NCT_TYPE isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if (info != null && info.isConnectedOrConnecting()) {
                        return NCT_TYPE.WIFI;
                    }
                    info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    if (info != null && info.isConnectedOrConnecting()) {
                        switch (info.getSubtype()) {
                            //--------------------Added in API level 1---------------------
                            //(3G)联通  ~ 400-7000 kbps
                            case TelephonyManager.NETWORK_TYPE_UMTS:
                                return NCT_TYPE.MOBILE_UMTS;
                            //(2.5G) 移动和联通  ~ 100 kbps
                            case TelephonyManager.NETWORK_TYPE_GPRS:
                                return NCT_TYPE.MOBILE_GPRS;
                            //(2.75G) 2.5G 到 3G 的过渡  移动和联通 ~ 50-100 kbps
                            case TelephonyManager.NETWORK_TYPE_EDGE:
                                return NCT_TYPE.MOBILE_EDGE;
                            //-----------------Added in API level 4---------------------
                            //( 3G )电信   ~ 400-1000 kbps
                            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                                return NCT_TYPE.MOBILE_EVDO_0;
                            //(2G 电信)  ~ 14-64 kbps
                            case TelephonyManager.NETWORK_TYPE_CDMA:
                                return NCT_TYPE.MOBILE_CDMA;
                            //(3.5G) 属于3G过渡   ~ 600-1400 kbps
                            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                                return NCT_TYPE.MOBILE_EVDO_A;
                            //( 2G )  ~ 50-100 kbps
                            case TelephonyManager.NETWORK_TYPE_1xRTT:
                                return NCT_TYPE.MOBILE_1xRTT;
                            //---------------------Added in API level 5--------------------
                            //(3.5G )  ~ 2-14 Mbps
                            case TelephonyManager.NETWORK_TYPE_HSDPA:
                                return NCT_TYPE.MOBILE_HSDPA;
                            //( 3.5G )  ~ 1-23 Mbps
                            case TelephonyManager.NETWORK_TYPE_HSUPA:
                                return NCT_TYPE.MOBILE_HSUPA;
                            //( 3G ) 联通  ~ 700-1700 kbps
                            case TelephonyManager.NETWORK_TYPE_HSPA:
                                return NCT_TYPE.MOBILE_HSPA;
                            //---------------------Added in API level 8---------------------
                            //(2G )  ~25 kbps
                            case TelephonyManager.NETWORK_TYPE_IDEN:
                                return NCT_TYPE.MOBILE_IDEN;
                            //---------------------Added in API level 9---------------------
                            //3G-3.5G  ~ 5 Mbps
                            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                                return NCT_TYPE.MOBILE_EVDO_B;
                            //---------------------Added in API level 11--------------------
                            //(4G) ~ 10+ Mbps
                            case TelephonyManager.NETWORK_TYPE_LTE:
                                return NCT_TYPE.MOBILE_LTE;
                            //3G(3G到4G的升级产物)  ~ 1-2 Mbps
                            case TelephonyManager.NETWORK_TYPE_EHRPD:
                                return NCT_TYPE.MOBILE_EHRPD;
                            //--------------------Added in API level 13-------------------
                            //( 3G )  ~ 10-20 Mbps
                            case TelephonyManager.NETWORK_TYPE_HSPAP:
                                return NCT_TYPE.MOBILE_HSPAP;
                        }
                        return NCT_TYPE.MOBILE;
                    }
                }
            }
        }
        return null;
    }

    @SuppressLint("MissingPermission")
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    return info != null && info.isConnectedOrConnecting();
                }
            }
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    return info != null && info.isConnectedOrConnecting();
                }
            }
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public static boolean isEthernetConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
                    return info != null && info.isConnectedOrConnecting();
                }
            }
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public static boolean isBluetoothConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);
                    return info != null && info.isConnectedOrConnecting();
                }
            }
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public static boolean isDummyConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_DUMMY);
                    return info != null && info.isConnectedOrConnecting();
                }
            }
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public static boolean isMobileDummyConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE_DUN);
                    return info != null && info.isConnectedOrConnecting();
                }
            }
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public static boolean isWiMaxConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
                    return info != null && info.isConnectedOrConnecting();
                }
            }
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public static boolean isVPNConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    NetworkInfo info = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        info = manager.getNetworkInfo(ConnectivityManager.TYPE_VPN);
                    }
                    return info != null && info.isConnectedOrConnecting();
                }
            }
        }
        return false;
    }

    /**
     * @return 判断字符串是否是网络连接
     */
    public static boolean isUrl(String text) {
        if (text == null || text.length() == 0) return false;
        return Patterns.WEB_URL.matcher(text).matches() || URLUtil.isValidUrl(text);
    }

    /**
     * @return 检查url是否规范
     */
    public static String getRegularUrl(String url) {
        if (url == null || url.length() == 0) return "";
        String regUrl = url;
        //加上https头
        if (!regUrl.startsWith("http://") || !regUrl.startsWith("https://")) {
            regUrl = "https://" + regUrl;
        }
        return regUrl;
    }

    /**
     * @return 得到百度查询text的网络链接
     */
    public static String getBaiduQuery(String queryText) {
        String wd;
        if (queryText == null || queryText.length() == 0) {
            wd = "";
        } else {
            wd = queryText;
        }
        return "https://www.baidu.com/s?wd=" + wd;
    }
}
