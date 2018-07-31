package cn.hu.zc.compote.api.juhe.model;

import cn.hu.zc.compote.api.common.adpt.NetAdapter;

public class ModelResult<T extends NetAdapter<M>, M> extends ModelBaseResult<M> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public M convert() {
        T data = getData();
        M m = null;
        if (data != null) {
            m = data.convert();
        }
        return m;
    }
}
