package cn.hu.zc.compote.fragment.home;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import cn.hu.zc.basilic.fragment.BaseFragment;
import cn.hu.zc.basilic.view.guide.HollowAlertDialog;
import cn.hu.zc.basilic.view.guide.HollowView;
import cn.hu.zc.basilic.view.recycler.RecyclerViewFx;
import cn.hu.zc.basilic.view.recycler.decoration.VerticalRecyclerItemDecoration;
import cn.hu.zc.basilic.view.recycler.listener.OnRecyclerViewItemClickListener;
import cn.hu.zc.basilic.view.recycler.lmanage.OpenLinearLayoutManager;
import cn.hu.zc.basilic.view.search.ExtendSearchView;
import cn.hu.zc.compote.R;
import cn.hu.zc.compote.activity.CommonWebActivity;
import cn.hu.zc.compote.activity.DownloadActivity;
import cn.hu.zc.compote.adapter.AdapterHomeNews;
import cn.hu.zc.compote.api.RetrofitBuilder;
import cn.hu.zc.compote.api.common.model.ModelNetDataList;
import cn.hu.zc.compote.api.common.model.news.ModelNews;
import cn.hu.zc.compote.api.juhe.model.ModelBaseJuheNetDataList;
import cn.hu.zc.compote.api.juhe.model.news.ModelToutiao;
import cn.hu.zc.compote.manage.SqlManager;
import cn.hu.zc.ratioview.RatioImageView;
import cn.hu.zc.statusbar.StatusBarCompat;
import cn.hu.zc.tools.CptTools;
import cn.hu.zc.tools.Looger;
import cn.hu.zc.tools.NetTools;
import cn.hu.zc.tools.ReflectTools;
import cn.hu.zc.tools.Toaster;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HeadPageFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.searchview)
    ExtendSearchView searchview;
    @BindView(R.id.riv_download_ui)
    RatioImageView rivDownloadUi;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.rv_news)
    RecyclerViewFx rvNews;
    private int statusToolbarColor;
    private int statusAppbarColor;
    private int statusCurrentColor;
    private Disposable newsDisposable;
    private AdapterHomeNews adapterHomeNews;

    @Override
    public int setLayoutRes() {
        return R.layout.fragment_page_head;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        //
        rivDownloadUi.setFocusable(true);
        rivDownloadUi.setFocusableInTouchMode(true);
        rivDownloadUi.requestFocus();
        initRecycler();
        setStatusbar();
        searchview.setCursorDrawableRes(R.drawable.cursor_white_thin);
        searchview.getSearchSrcText().setTextColor(getContext().getResources().getColor(R.color.search_dark));
    }

    private void initRecycler() {
        adapterHomeNews = new AdapterHomeNews(getContext());
        rvNews.setLayoutManager(new OpenLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvNews.setAdapter(adapterHomeNews);
        rvNews.addItemDecoration(new VerticalRecyclerItemDecoration(Color.TRANSPARENT, 10));
    }

    private void setStatusbar() {
        statusToolbarColor = getContext().getResources().getColor(R.color.colorPrimary);
        statusAppbarColor = getContext().getResources().getColor(R.color.colorPrimaryDark);
        statusCurrentColor = statusToolbarColor;

        StatusBarCompat.setStatusBarColor(getActivity(), statusCurrentColor);
    }

    @Override
    public void listener() {
        searchview.setOnClickListener(this);
        rivDownloadUi.setOnClickListener(this);
        //滑动监听
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            private CptTools.ColorTranslate colorTranslate = new CptTools.ColorTranslate(statusToolbarColor, statusAppbarColor);

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int offset = Math.abs(verticalOffset);
                float per = 1f * offset / StatusBarCompat.getStatusBarHeight(getContext());
                if (per < 0) {
                    per = 0f;
                } else if (per > 1) {
                    per = 1f;
                }
                statusCurrentColor = colorTranslate.translate(per);
                Log.e("ccccccc", verticalOffset + " , " + per + " , sta: " + statusToolbarColor + " appbar: " + statusAppbarColor + " new: " + statusCurrentColor);
                StatusBarCompat.setStatusBarColor(getActivity(), statusCurrentColor);

            }
        });
        //搜索提示
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private Cursor cursor;

            @Override
            public boolean onQueryTextSubmit(String query) {
                boolean isUrl = NetTools.isUrl(query);
                if (isUrl) {
                    CommonWebActivity.openDef(getActivity(), NetTools.getRegularUrl(query), null, null);
                } else {
                    CommonWebActivity.openDef(getActivity(), NetTools.getBaiduQuery(query), null, null);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cursor = TextUtils.isEmpty(newText) ? null : SqlManager.queryHeadSearchText(getContext(), newText);
                if (searchview.getSuggestionsAdapter() == null) {
                    searchview.setSuggestionsAdapter(SqlManager.getHeadSimpleCursorAdapter(getContext(), cursor));
                } else {
                    searchview.getSuggestionsAdapter().changeCursor(cursor);
                }
                return false;
            }
        });
        //搜索提示选择监听
        searchview.getSearchSrcText().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CursorAdapter suggestionsAdapter = searchview.getSuggestionsAdapter();
                if (suggestionsAdapter == null) {
                    Toaster.toast(getContext(), "queryCursorAdapter==null");
                    return;
                }
                String itemText;
                View item = suggestionsAdapter.getView(position, view, parent);
                View tv = item.findViewById(R.id.tv_text);
                if (tv instanceof TextView) {
                    itemText = ((TextView) tv).getText().toString();
                    searchview.setQuery(itemText, true);
                } else {
                    itemText = "unknown";
                    Toaster.toast(getContext(), "onItemClick : " + position + " text : " + itemText);
                }
            }
        });
        //头条item点击
        adapterHomeNews.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener<ModelNews>() {
            @Override
            public void onClick(RecyclerView.ViewHolder holder, int position, ModelNews data) {
                if (data == null) {
                    Toaster.toast(getContext(), "data==null");
                    return;
                }
                CommonWebActivity.openNews(getActivity(), data.getUrl(), holder.itemView, getContext().getResources().getString(R.string.transname_com_webview));
            }
        });
    }


    @Override
    public void loadData() {
        requestToutiao();
    }

    private void requestToutiao() {
        newsDisposable = RetrofitBuilder.getJuhe().getTouTiao("")
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ModelBaseJuheNetDataList<ModelToutiao, ModelNews>>() {
                    @Override
                    public void accept(ModelBaseJuheNetDataList<ModelToutiao, ModelNews> modelNews) throws Exception {
                        ModelNetDataList<ModelNews> news = modelNews.convert();
                        if (news == null) return;
                        List<ModelNews> data = news.getData();
                        List<ModelNews> adptDataList = adapterHomeNews.getNewsList();
                        if ((data == null || data.size() == 0) && adptDataList != null) return;
                        adapterHomeNews.setNewsList(data);
                        adapterHomeNews.notifyDataChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        Looger.log("news", "onError");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Looger.log("news", "onComplete");
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchview:
//                ReflectTools.call(searchview, SearchView.class, "onSearchClicked", null);
                HollowAlertDialog hollowAlertDialog = new HollowAlertDialog(getContext());
                hollowAlertDialog.hollowView.targetView(searchview)
                        .direct(HollowView.Direct.BOT, Gravity.CENTER, 0, 0)
                        .text("和斯蒂芬斯蒂芬合适的客户是打开");
                hollowAlertDialog.hollowView.getInstructTv().setTextColor(Color.WHITE);
                hollowAlertDialog.hollowView.getInstructTv().setTextSize(30);
                hollowAlertDialog.show();
                break;
            case R.id.riv_download_ui:
                String transitionDownload = getContext().getResources().getString(R.string.transname_com_download);
                DownloadActivity.open(getActivity(), v, transitionDownload);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (newsDisposable != null && !newsDisposable.isDisposed()) {
            newsDisposable.dispose();
        }
    }
}
