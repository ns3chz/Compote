package cn.hu.zc.compote.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.hu.zc.basilic.view.recycler.adapter.BaseRecyclerHeaderFooterAdapter;
import cn.hu.zc.compote.R;
import cn.hu.zc.compote.adapter.holder.HolderHomeNewsImgmuil;
import cn.hu.zc.compote.adapter.holder.HolderHomeNewsImgsin;
import cn.hu.zc.compote.adapter.holder.HolderHomeNewsText;
import cn.hu.zc.compote.api.common.model.news.ModelNews;

public class AdapterHomeNews extends BaseRecyclerHeaderFooterAdapter<RecyclerView.ViewHolder> {
    private List<ModelNews> newsList;

    public AdapterHomeNews(Context ctx) {
        super(ctx);
    }

    @Override
    public View onCreateContentViews(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ItemType.TEXT:
                return mLayoutInflater.inflate(R.layout.adpt_item_home_news_text, parent, false);
            case ItemType.IMG_SIN:
                return mLayoutInflater.inflate(R.layout.adpt_item_home_news_imgsin, parent, false);
            case ItemType.IMG_MUIL:
                return mLayoutInflater.inflate(R.layout.adpt_item_home_news_imgmuil, parent, false);
        }
        return mLayoutInflater.inflate(R.layout.adpt_item_home_news_text, parent, false);
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolders(View view, int viewType) {
        switch (viewType) {
            case ItemType.TEXT:
                return new HolderHomeNewsText(view);
            case ItemType.IMG_SIN:
                return new HolderHomeNewsImgsin(view);
            case ItemType.IMG_MUIL:
                return new HolderHomeNewsImgmuil(view);
        }
        return new HolderHomeNewsText(view);
    }

    @Override
    public void onBindViewHolderr(RecyclerView.ViewHolder viewHolder, int position) {
        ModelNews modelNews = newsList.get(position);
        String title = modelNews.getTitle();
        String author = modelNews.getAuthor();
        if (viewHolder instanceof HolderHomeNewsText) {
            HolderHomeNewsText holderHomeNewsText = (HolderHomeNewsText) viewHolder;
            holderHomeNewsText.title.setText(title);
            holderHomeNewsText.tvFrom.setText(author);
        } else if (viewHolder instanceof HolderHomeNewsImgsin) {
            HolderHomeNewsImgsin holderHomeNewsImgsin = ((HolderHomeNewsImgsin) viewHolder);
            holderHomeNewsImgsin.title.setText(title);
            holderHomeNewsImgsin.tvFrom.setText(author);
        } else if (viewHolder instanceof HolderHomeNewsImgmuil) {
            HolderHomeNewsImgmuil holderHomeNewsImgmuil = ((HolderHomeNewsImgmuil) viewHolder);
            holderHomeNewsImgmuil.title.setText(title);
            holderHomeNewsImgmuil.tvFrom.setText(author);
        }

    }

    public void setNewsList(List<ModelNews> newsList) {
        this.newsList = newsList;
    }

    public List<ModelNews> getNewsList() {
        return newsList;
    }

    @Override
    public int getItemCounts() {
        return newsList == null ? 0 : newsList.size();
    }

    @Override
    public int getItemViewTypes(int position) {
        if (newsList == null) return 0;
        ModelNews modelNews = newsList.get(position);
        if (modelNews == null) return 0;
        List<String> imageUrlList = modelNews.getImageUrlList();
        if (imageUrlList == null || imageUrlList.size() == 0) return 0;
        if (imageUrlList.size() == 1) return ItemType.IMG_SIN;
        return ItemType.IMG_MUIL;
    }

    @Override
    public ModelNews getClickData(RecyclerView.ViewHolder holder, int position) {
        return newsList == null ? null : newsList.get(position);
    }

    /**
     * item类型
     */
    public static class ItemType {
        public static final int TEXT = 0;//纯文字
        public static final int IMG_SIN = 1;//单个图片
        public static final int IMG_MUIL = 2;//多个图片
    }
}
