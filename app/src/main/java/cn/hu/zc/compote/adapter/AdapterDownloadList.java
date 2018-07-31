package cn.hu.zc.compote.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.hu.zc.basilic.view.recycler.adapter.BaseRecyclerHeaderFooterAdapter;
import cn.hu.zc.compote.R;
import cn.hu.zc.compote.adapter.holder.HolderDownloadItem;
import cn.hu.zc.compote.model.ModelDownload;

public class AdapterDownloadList extends BaseRecyclerHeaderFooterAdapter<HolderDownloadItem> {
    private List<ModelDownload> modelDownloadList;

    public AdapterDownloadList(Context ctx) {
        super(ctx);
    }

    @Override
    public View onCreateContentViews(ViewGroup parent, int viewType) {
        return mLayoutInflater.inflate(R.layout.adpt_item_download, parent, false);
    }

    @Override
    public HolderDownloadItem onCompatCreateViewHolders(View view, int viewType) {
        return new HolderDownloadItem(view);
    }

    @Override
    public void onBindViewHolderr(HolderDownloadItem viewHolder, int position) {
        ModelDownload modelDownload = modelDownloadList.get(position);
        viewHolder.title.setText(modelDownload.getName());
    }

    @Override
    public int getItemCounts() {
        return modelDownloadList == null ? 0 : modelDownloadList.size();
    }

    public void setDataList(List<ModelDownload> modelDownloadList) {
        this.modelDownloadList = modelDownloadList;
    }
}
