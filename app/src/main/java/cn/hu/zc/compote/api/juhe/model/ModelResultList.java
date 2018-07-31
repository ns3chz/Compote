package cn.hu.zc.compote.api.juhe.model;

import java.util.ArrayList;
import java.util.List;

import cn.hu.zc.compote.api.common.adpt.NetAdapter;

public class ModelResultList<T extends NetAdapter<M>, M> extends ModelBaseResult<List<M>> {
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public List<M> convert() {
        List<M> mList = null;
        if (data != null) {
            for (T t : data) {
                if (t == null) continue;
                M m = t.convert();
                if (m == null) continue;
                if (mList == null) {
                    mList = new ArrayList<>();
                }
                mList.add(m);
            }
        }
        return mList;
    }
}
