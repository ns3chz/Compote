package cn.hu.zc.compote.model;

public class ModelDownload extends ModelBase {
    private String link;//链接
    private String torrent;//torrent
    private String name;//资源名称
    private String type;//资源类型
    private long total;//总大小，byte
    private long downloadSize;//已下载大小，byte
    private String imgUrl;//资源图片
    private long publicMillis;//资源发布毫秒值
    private String publicDate;//资源发布时间
    private long downloadMillis;//资源开始下载毫秒值
    private String downloadDate;//资源开始下载时间

    public String getLink() {
        return link == null ? "" : link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTorrent() {
        return torrent == null ? "" : torrent;
    }

    public void setTorrent(String torrent) {
        this.torrent = torrent;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getDownloadSize() {
        return downloadSize;
    }

    public void setDownloadSize(long downloadSize) {
        this.downloadSize = downloadSize;
    }

    public String getImgUrl() {
        return imgUrl == null ? "" : imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getPublicMillis() {
        return publicMillis;
    }

    public void setPublicMillis(long publicMillis) {
        this.publicMillis = publicMillis;
    }

    public String getPublicDate() {
        return publicDate == null ? "" : publicDate;
    }

    public void setPublicDate(String publicDate) {
        this.publicDate = publicDate;
    }

    public long getDownloadMillis() {
        return downloadMillis;
    }

    public void setDownloadMillis(long downloadMillis) {
        this.downloadMillis = downloadMillis;
    }

    public String getDownloadDate() {
        return downloadDate == null ? "" : downloadDate;
    }

    public void setDownloadDate(String downloadDate) {
        this.downloadDate = downloadDate;
    }
}


