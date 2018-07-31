package cn.hu.zc.compote.api.juhe.model;

import cn.hu.zc.compote.api.common.adpt.ModelBaseAdptNetData;
import cn.hu.zc.compote.api.common.adpt.NetAdapter;
import cn.hu.zc.compote.api.common.model.ModelNetData;

public class ModelBaseJuheNetData<T extends NetAdapter<M>, M> extends ModelBaseAdptNetData<ModelNetData<M>> {
    private String reason;//返回说明
    private int error_code;//返回码
    private ModelResult<T, M> result;//结果

    @Override
    public ModelNetData<M> convert() {
        ModelNetData<M> mModelNetData = new ModelNetData<>();
        mModelNetData.setReCode(getError_code() + "");
        mModelNetData.setReason(getReason());
        ModelResult<T, M> result = getResult();
        if (result != null) {
            mModelNetData.setData(result.convert());
        }
        return mModelNetData;
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

    public ModelResult<T, M> getResult() {
        return result;
    }

    public void setResult(ModelResult<T, M> result) {
        this.result = result;
    }
}
