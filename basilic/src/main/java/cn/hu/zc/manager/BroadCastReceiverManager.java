package cn.hu.zc.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import cn.hu.zc.model.ModelBroadcastReceiver;
import cn.hu.zc.tools.Looger;


/**
 * 广播管理，注册与注销
 * Created by Administrator on 2017/3/23.
 */

public final class BroadCastReceiverManager {
    /**
     */
    public static BroadCastReceiverManager get(Context ctx) {
        return new BroadCastReceiverManager(ctx);
    }

    private Context context;

    private BroadCastReceiverManager(Context ctx) {
        context = ctx;
    }

    //------------------------------------

    /**
     * String1:tag;
     * String2:action;
     */
    private final Map<String, ModelBroadcastReceiver> broadcastReceiverMaps = new HashMap<>();

    public void registerReceiver(String tag, String action, OnBrodCastReceivedListener listener) {
        registerReceiver(tag, action, 0, listener);
    }

    /**
     * 注册广播监听
     *
     * @param tag      mark it
     * @param action   broadcast's action
     * @param priority 优先级(-1000~1000)
     * @param listener editChangedListen receive
     */
    public void registerReceiver(String tag, final String action, int priority, final OnBrodCastReceivedListener listener) {
        if (isAlreadyHave(tag)) {
            Looger.log(this.getClass().getSimpleName(), "this tag broadcastReceiver is already have!");
            return;
        }
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (action == null || action.length() == 0 || action.equals(intent.getAction())) {
                    listener.received(context, intent);
                } else {
                    Looger.log(this.getClass().getSimpleName(),
                            "action is not equal:\naction : " + action + "\nintent : " + intent.getAction());
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(action);
        intentFilter.setPriority(priority);
        context.registerReceiver(broadcastReceiver, intentFilter);
        //
        ModelBroadcastReceiver broadcastReceiverModel = new ModelBroadcastReceiver();
        broadcastReceiverModel.setAction(action);
        broadcastReceiverModel.setBroadcastReceiver(broadcastReceiver);
        broadcastReceiverMaps.put(tag, broadcastReceiverModel);
//        Looger.log(this.getClass().getSimpleName(), "广播注册成功:" + "tag=" + tag + " ;acton=" + action);
    }

    /**
     * 反注册广播
     */
    public void unRegisterReceiver(String tag) {
        BroadcastReceiver broadCastReceiver = getBroadCastReceiver(tag);
        if (broadCastReceiver != null) {
            context.unregisterReceiver(broadCastReceiver);
            broadcastReceiverMaps.remove(tag);
//            Looger.log(this.getClass().getSimpleName(), "反注册广播完成：tag= " + tag);
        }
    }


    /**
     * @param tag broadcast tag
     * @return is regist?
     */
    public boolean isAlreadyHave(String tag) {
        return broadcastReceiverMaps.containsKey(tag);
    }

    /**
     * @param tag broadcast tag
     * @return get broadcast by tag
     */
    public BroadcastReceiver getBroadCastReceiver(String tag) {
        ModelBroadcastReceiver broadcastReceiverModel = broadcastReceiverMaps.get(tag);
        if (broadcastReceiverModel != null) {
            return broadcastReceiverModel.getBroadcastReceiver();
        } else {
            Log.e(this.getClass().getSimpleName(), "can not find broadcastReceiver!");
            return null;
        }
    }

    /**
     * @return 注册广播的个数
     */
    public int getCounts() {
        return broadcastReceiverMaps.size();
    }

    //-----------
    public interface OnBrodCastReceivedListener {
        public void received(Context context, Intent intent);
    }

}
