package cn.hu.zc.compote.api.idata.model;

import cn.hu.zc.compote.api.common.adpt.ModelBaseAdptNetData;

abstract class ModelIdataBaseNetData<M> extends ModelBaseAdptNetData<M> {
    private String retcode;//返回码
    private String appCode;//api名
    private String dataType;//api类型
    private boolean hasNext;//是否有下一页
    private int pageToken;//翻页值

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getPageToken() {
        return pageToken;
    }

    public void setPageToken(int pageToken) {
        this.pageToken = pageToken;
    }

}
