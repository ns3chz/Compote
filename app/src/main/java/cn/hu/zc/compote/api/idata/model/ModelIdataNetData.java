package cn.hu.zc.compote.api.idata.model;

import cn.hu.zc.compote.api.common.adpt.NetAdapter;
import cn.hu.zc.compote.api.common.model.ModelNetData;

public class ModelIdataNetData<T extends NetAdapter<M>, M> extends ModelIdataBaseNetData<ModelNetData<M>> {

    private T data;//数据

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public ModelNetData<M> convert() {
        ModelNetData<M> mModelNetData = new ModelNetData<>();
        mModelNetData.setReCode(getRetcode());
        mModelNetData.setApiCode(getAppCode());
        mModelNetData.setApiType(getDataType());
        mModelNetData.setHasNext(isHasNext());
        mModelNetData.setPageToken(getPageToken());
        T data = getData();
        mModelNetData.setData(data == null ? null : data.convert());
        return mModelNetData;
    }
}
