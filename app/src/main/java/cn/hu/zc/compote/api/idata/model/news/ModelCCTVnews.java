package cn.hu.zc.compote.api.idata.model.news;

import java.util.List;

import cn.hu.zc.compote.api.common.adpt.NetAdapter;
import cn.hu.zc.compote.api.common.model.news.ModelNews;
import cn.hu.zc.compote.api.idata.model.common.GeoPoint;

public class ModelCCTVnews implements NetAdapter<ModelNews> {
    private String id;//新闻id
    private String posterId;//发布者ID
    private int viewCount;//观看数
    private String catPathKey;//总的分类路径
    private String posterScreenName;//发布者名称
    private String catName1;// 1级分类名
    private String tags;//标签列表
    private String url;//新闻链接
    private String publishDateStr;//发布时间（UTC时间
    private GeoPoint geoPoint;//经纬度
    private long publishDate;//发布日期时间戳
    private int likeCount;//新闻的点赞数
    private int commentCount;//评论数
    private List<String> videoUrls;//视频连接
    private int shareCount;//转发数
    private String location;//位置
    private String title;//标题
    private String content;//新闻内容
    private String imageUrls;//预览图片
    private int catId1;//一级分类ID
    private float durationMin;//视频时长，单位分钟
    private String city;//新闻所在城市
    private String district;//新闻所在区
    private String state;//新闻所在省

    @Override
    public ModelNews convert() {
        ModelNews modelNews = new ModelNews();
        modelNews.setId(getId());
        modelNews.setPosterId(getPosterId());
        modelNews.setViewCount(getViewCount());
        modelNews.setCatPathKey(getCatPathKey());
        modelNews.setAuthor(getPosterScreenName());
        modelNews.setCatName1(getCatName1());
        modelNews.setTags(getTags());
        modelNews.setUrl(getUrl());
        modelNews.setPublishDateStr(getPublishDateStr());
        GeoPoint geoPoint = getGeoPoint();
        modelNews.setGeoPoint(geoPoint == null ? null : geoPoint.convert());
        modelNews.setPublishDate(getPublishDate());
        modelNews.setLikeCount(getLikeCount());
        modelNews.setCommentCount(getCommentCount());
        modelNews.setVideoUrls(getVideoUrls());
        modelNews.setShareCount(getShareCount());
        modelNews.setLocation(getLocation());
        modelNews.setTitle(getTitle());
        modelNews.setContent(getContent());
        modelNews.setImagePreviewUrl(getImageUrls());
        modelNews.setCatId1(getCatId1());
        modelNews.setVideoDuring(getDurationMin());
        modelNews.setCity(getCity());
        modelNews.setDistrict(getDistrict());
        modelNews.setProvince(getState());
        return modelNews;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getCatPathKey() {
        return catPathKey;
    }

    public void setCatPathKey(String catPathKey) {
        this.catPathKey = catPathKey;
    }

    public String getPosterScreenName() {
        return posterScreenName;
    }

    public void setPosterScreenName(String posterScreenName) {
        this.posterScreenName = posterScreenName;
    }

    public String getCatName1() {
        return catName1;
    }

    public void setCatName1(String catName1) {
        this.catName1 = catName1;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishDateStr() {
        return publishDateStr;
    }

    public void setPublishDateStr(String publishDateStr) {
        this.publishDateStr = publishDateStr;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public long getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(long publishDate) {
        this.publishDate = publishDate;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public List<String> getVideoUrls() {
        return videoUrls;
    }

    public void setVideoUrls(List<String> videoUrls) {
        this.videoUrls = videoUrls;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public int getCatId1() {
        return catId1;
    }

    public void setCatId1(int catId1) {
        this.catId1 = catId1;
    }

    public float getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(float durationMin) {
        this.durationMin = durationMin;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
