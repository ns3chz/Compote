package cn.hu.zc.compote.api.common.model;

import java.util.List;

public class ModelNetDataList<T> extends ModelBaseNetData{

    private List<T> data;//数据

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
