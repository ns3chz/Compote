package cn.hu.zc.compote.api.juhe.model;

import cn.hu.zc.compote.api.common.adpt.ModelBaseAdptNetData;
import cn.hu.zc.compote.api.common.adpt.NetAdapter;
import cn.hu.zc.compote.api.common.model.ModelNetDataList;

public class ModelBaseJuheNetDataList<T extends NetAdapter<M>, M> extends ModelBaseAdptNetData<ModelNetDataList<M>> {
    private String reason;//返回说明
    private int error_code;//返回码
    private ModelResultList<T, M> result;//结果

    @Override
    public ModelNetDataList<M> convert() {
        ModelNetDataList<M> mModelNetDataList = new ModelNetDataList<>();
        mModelNetDataList.setReCode(getError_code() + "");
        mModelNetDataList.setReason(getReason());
        ModelResultList<T, M> result = getResult();
        if (result != null) {
            mModelNetDataList.setData(result.convert());
        }
        return mModelNetDataList;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public ModelResultList<T, M> getResult() {
        return result;
    }

    public void setResult(ModelResultList<T, M> result) {
        this.result = result;
    }

}
