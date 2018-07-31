package cn.hu.zc.model;

import android.content.BroadcastReceiver;

/**
 * Created by HuZC on 2017/12/20.
 */

public class ModelBroadcastReceiver {
    private String action;
    private BroadcastReceiver broadcastReceiver;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public BroadcastReceiver getBroadcastReceiver() {
        return broadcastReceiver;
    }

    public void setBroadcastReceiver(BroadcastReceiver broadcastReceiver) {
        this.broadcastReceiver = broadcastReceiver;
    }
}
