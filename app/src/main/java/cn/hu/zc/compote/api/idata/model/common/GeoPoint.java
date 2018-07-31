package cn.hu.zc.compote.api.idata.model.common;

import cn.hu.zc.compote.api.common.adpt.NetAdapter;
import cn.hu.zc.compote.api.common.model.com.GeoPointCom;

public class GeoPoint implements NetAdapter<GeoPointCom> {
    private long lat;//纬度
    private long lon;//经度

    @Override
    public GeoPointCom convert() {
        GeoPointCom geoPointCom = new GeoPointCom();
        geoPointCom.setLatitude(getLat());
        geoPointCom.setLongitude(getLon());
        return geoPointCom;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLon() {
        return lon;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }
}
