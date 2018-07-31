package cn.hu.zc.compote.api.common.model;

public class ModelNetData<T> extends ModelBaseNetData {

    private T data;//数据

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
