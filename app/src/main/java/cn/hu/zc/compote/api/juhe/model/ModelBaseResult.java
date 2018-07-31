package cn.hu.zc.compote.api.juhe.model;

import cn.hu.zc.compote.api.common.adpt.NetAdapter;

abstract class ModelBaseResult<M> implements NetAdapter<M> {
    private String stat;//

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
