package cn.hu.zc.compote.api.common.model;

import cn.hu.zc.compote.model.ModelBase;

class ModelBaseNetData extends ModelBase {
    private String reCode;//返回码
    private String reason;//返回说明
    private String apiCode;//api名
    private String apiType;//api类型
    private boolean hasNext;//是否有下一页
    private int pageToken;//翻页值

    public String getReCode() {
        return reCode;
    }

    public void setReCode(String reCode) {
        this.reCode = reCode;
    }

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public String getApiType() {
        return apiType;
    }

    public void setApiType(String apiType) {
        this.apiType = apiType;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
