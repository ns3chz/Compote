package cn.hu.zc.compote.api.idata.model;

import java.util.ArrayList;
import java.util.List;

import cn.hu.zc.compote.api.common.adpt.NetAdapter;
import cn.hu.zc.compote.api.common.model.ModelNetDataList;

public class ModelIdataNetDataList<T extends NetAdapter<M>, M> extends ModelIdataBaseNetData<ModelNetDataList<M>> {

    private List<T> data;//数据

    @Override
    public ModelNetDataList<M> convert() {
        ModelNetDataList<M> mModelNetData = new ModelNetDataList<>();
        mModelNetData.setReCode(getRetcode());
        mModelNetData.setApiCode(getAppCode());
        mModelNetData.setApiType(getDataType());
        mModelNetData.setHasNext(isHasNext());
        mModelNetData.setPageToken(getPageToken());
        List<T> data = getData();
        if (data != null) {
            List<M> mData = null;
            for (T t : data) {
                if (t == null) continue;
                M m = t.convert();
                if (m == null) continue;
                if (mData == null) {
                    mData = new ArrayList<>();
                }
                mData.add(m);
            }
            mModelNetData.setData(mData);
        }
        return mModelNetData;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
