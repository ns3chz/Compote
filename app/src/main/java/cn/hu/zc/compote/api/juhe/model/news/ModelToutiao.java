package cn.hu.zc.compote.api.juhe.model.news;

import java.util.ArrayList;
import java.util.List;

import cn.hu.zc.compote.api.common.adpt.NetAdapter;
import cn.hu.zc.compote.api.common.model.news.ModelNews;

public class ModelToutiao implements NetAdapter<ModelNews> {
    private String uniquekey;//
    private String title;//
    private String date;
    private String category;//类型
    private String author_name;//
    private String url;//
    private String thumbnail_pic_s;//图片
    private String thumbnail_pic_s02;//图片
    private String thumbnail_pic_s03;//图片

    @Override
    public ModelNews convert() {
        ModelNews modelNews = new ModelNews();
        modelNews.setId(getUniquekey());
        modelNews.setTitle(getTitle());
        modelNews.setPublishDateStr(getDate());
        modelNews.setType(getCategory());
        modelNews.setAuthor(getAuthor_name());
        modelNews.setUrl(getUrl());
        modelNews.setImagePreviewUrl(getThumbnail_pic_s());
        String thumbnail_pic_s02 = getThumbnail_pic_s02();
        String thumbnail_pic_s03 = getThumbnail_pic_s03();
        List<String> imgList = new ArrayList<>();
        if (thumbnail_pic_s02 != null && thumbnail_pic_s02.length() > 0) {
            imgList.add(thumbnail_pic_s02);
        }
        if (thumbnail_pic_s03 != null && thumbnail_pic_s03.length() > 0) {
            imgList.add(thumbnail_pic_s03);
        }
        modelNews.setImageUrlList(imgList);
        return modelNews;
    }

    public String getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public String getThumbnail_pic_s02() {
        return thumbnail_pic_s02;
    }

    public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
        this.thumbnail_pic_s02 = thumbnail_pic_s02;
    }

    public String getThumbnail_pic_s03() {
        return thumbnail_pic_s03;
    }

    public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
        this.thumbnail_pic_s03 = thumbnail_pic_s03;
    }
}
