package cn.hu.zc.compote.api.common.model.com;

import cn.hu.zc.compote.model.ModelBase;

public class GeoPointCom extends ModelBase {
    private long latitude;//纬度
    private long longitude;//经度

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }
}
