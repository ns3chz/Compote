package cn.hu.zc.basilic.view.recycler.adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.TimeUnit;

import cn.hu.zc.basilic.view.recycler.holder.BaseRecyclerViewHolder;
import cn.hu.zc.basilic.view.recycler.interf.RecyclerAdapterInterface;
import cn.hu.zc.basilic.view.recycler.listener.OnRecyclerViewItemClickListener;
import cn.hu.zc.tools.UITools;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * //TODO 只考虑LinearLayoutManager,待优化,
 * //Tips:在继承之后，若要设置header，footer，onCompatCreateViewHolders返回需不为空
 * Created by Administrator on 2017/7/13.
 */

public abstract class BaseRecyclerHeaderFooterAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RecyclerAdapterInterface<VH> {

    private static final int ITEM_TYPE_HEADER = -1;
    private static final int ITEM_TYPE_FOOTER = -2;

    protected RecyclerView.ViewHolder headerViewHolder;
    protected RecyclerView.ViewHolder footerViewHolder;
    protected OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    protected LayoutInflater mLayoutInflater;

    protected Context mCtx;
    private Disposable refreshDisposable;

    public BaseRecyclerHeaderFooterAdapter(Context ctx) {
        mCtx = ctx;
        this.mLayoutInflater = LayoutInflater.from(ctx);
    }


    @Override
    public final void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (headerViewHolder != null) {
            position--;
        }
        if (position < 0 || position >= getItemCounts()) {
            return;
        }
        if (onRecyclerViewItemClickListener != null) {
            final int pos = position;
            if (holder != null && holder.itemView != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onRecyclerViewItemClickListener != null) {
                            onRecyclerViewItemClickListener.onClick(holder, pos, getClickData((VH) holder, pos));
                        }
                    }
                });
            }
        }
        onBindViewHolderr((VH) holder, position);
    }


    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup realContentView, int viewType) {
        View view = onCreateView(realContentView, viewType);
        switch (viewType) {
            case ITEM_TYPE_HEADER:
//                return this.onCompatCreateViewHolders(view, viewType);
//                return this.onCompatCreateViewHolders(view, viewType);
                return headerViewHolder;
            case ITEM_TYPE_FOOTER:
//                return this.onCompatCreateViewHolders(view, viewType);
                return footerViewHolder;
            default:
                return this.onCompatCreateViewHolders(view, viewType);
        }
    }

    public final View onCreateView(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_HEADER:
//                if (headerLayoutRes != headerLayoutResOld) {
//                    headerView = LayoutInflater.from(mCtx).inflate(headerLayoutRes, parent, false);
//                    handleHeader(headerView);
//                }
                if (headerViewHolder == null || headerViewHolder.itemView == null) {
                    return new View(mCtx);
                }
                return headerViewHolder.itemView;
            case ITEM_TYPE_FOOTER:
//                if (footerLayoutRes != footerLayoutResOld) {
//                    footerView = LayoutInflater.from(mCtx).inflate(footerLayoutRes, parent, false);
//                    handleFooter(footerView);
//                }
                if (footerViewHolder == null || footerViewHolder.itemView == null) {
                    return new View(mCtx);
                }
                return footerViewHolder.itemView;
            default:
                return this.onCreateContentViews(parent, viewType);
        }
    }

    @Override
    public final int getItemViewType(int position) {
        if (position == 0 && headerViewHolder != null) {
            return ITEM_TYPE_HEADER;
        } else if (position == getItemCount() - 1 && footerViewHolder != null) {
            return ITEM_TYPE_FOOTER;
        } else {
            //
            if (headerViewHolder != null) {
                return this.getItemViewTypes(position - 1);
            } else {
                return this.getItemViewTypes(position);
            }
        }
    }

    @Override
    public final int getItemCount() {

        //个数加上header和footer
        return this.getItemCounts() + (headerViewHolder == null ? 0 : 1) + (footerViewHolder == null ? 0 : 1);
    }

    /**
     * 点击返回的数据
     */
    public Object getClickData(VH holder, int position) {
        return null;
    }

    //------------------------------------set----------------------------------------------------


    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener<?> listener) {
        this.onRecyclerViewItemClickListener = listener;
    }

    /**
     * @param holder 设置header
     */
    public void setHeaderHolder(RecyclerView.ViewHolder holder) {
        headerViewHolder = holder;

//        setHeaderHolder(mLayoutInflater.inflate(layoutRes, null));
        notifyDataChanged();
    }

    /**
     * 设置header 宽高，若header为空，则创建一个空白header
     *
     * @param width  宽
     * @param height 高
     * @param color  背景颜色
     */
    public void setHeaderHolder(int width, int height, int color) {
        if (headerViewHolder == null) {
            //若header为空，则创建空白header
            View view = new View(mCtx);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(UITools.dp2px(width), UITools.dp2px(height));
            view.setLayoutParams(params);
            view.setBackgroundColor(color);
            headerViewHolder = new BaseRecyclerViewHolder(view);
        } else {
            View itemView = headerViewHolder.itemView;
            if (itemView == null) return;
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.width = UITools.dp2px(width);
            layoutParams.height = UITools.dp2px(height);
            itemView.setLayoutParams(layoutParams);
            itemView.setBackgroundColor(color);
        }
//        setHeaderHolder(mLayoutInflater.inflate(layoutRes, null));
        notifyDataChanged();
    }

    /**
     * 去掉header
     */
    public void removeHeaderHolder() {
        headerViewHolder = null;
        notifyDataChanged();
    }


    /**
     * 设置footer
     */
    public void setFooterHolder(RecyclerView.ViewHolder holder) {
        footerViewHolder = holder;
//        setFooterHolder(mLayoutInflater.inflate(layoutRes, null));
        notifyDataChanged();
    }

    /**
     * 设置footer 宽高，若footer为空，则创建一个空白footer
     *
     * @param width  宽
     * @param height 高
     * @param color  背景颜色
     */
    public void setFooterHolder(int width, int height, int color) {
        if (footerViewHolder == null) {
            //若footer为空，则创建空白footer
            View view = new View(mCtx);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(UITools.dp2px(width), UITools.dp2px(height));
            view.setLayoutParams(params);
            view.setBackgroundColor(color);
            footerViewHolder = new BaseRecyclerViewHolder(view);
        } else {
            View itemView = footerViewHolder.itemView;
            if (itemView == null) return;
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.width = UITools.dp2px(width);
            layoutParams.height = UITools.dp2px(height);
            itemView.setLayoutParams(layoutParams);
            itemView.setBackgroundColor(color);
        }
//        setHeaderHolder(mLayoutInflater.inflate(layoutRes, null));
        notifyDataChanged();
    }

    /**
     * 设置footer高度
     */
    public void setEmptyFooterHeight(int height) {
        View view = new View(mCtx);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);

        view.setLayoutParams(params);
        RecyclerView.ViewHolder holder = new BaseRecyclerViewHolder(view);
        setFooterHolder(holder);
//        setFooterHolder(mLayoutInflater.inflate(layoutRes, null));
        notifyDataChanged();
    }

    /**
     * 去掉footer
     */
    public void removeFooterHolder() {
        footerViewHolder = null;
        notifyDataChanged();
    }

    /**
     * 是否有header
     */
    public boolean hasHeader() {
        return headerViewHolder != null;
    }

    /**
     * 是否有footer
     */
    public boolean hasFooter() {
        return footerViewHolder != null;
    }

    /**
     * 该角标位置是否为头布局
     */
    public boolean isHeader(int position) {
        return hasHeader() && position == 0;
    }

    /**
     * 该角标位置是否为尾布局
     */
    public boolean isFooter(int position) {
        return hasFooter() && (position == getItemCount() - 1);
    }

    public final void notifyDataChanged() {
        this.notifyDataChanged(5L);
    }

    public void notifyDataChanged(long mille) {
        if (this.refreshDisposable != null && !this.refreshDisposable.isDisposed()) {
            this.refreshDisposable.dispose();
        }

        refreshDisposable = Observable.just(this).subscribeOn(Schedulers.io()).delay(mille, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseRecyclerHeaderFooterAdapter<VH>>() {
            public void accept(BaseRecyclerHeaderFooterAdapter<VH> adapter) throws Exception {
                adapter.notifyDataSetChanged();
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeader(position) || isFooter(position)) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * item类型
     */
    public @IntRange(from = 0, to = Integer.MAX_VALUE)
    int getItemViewTypes(int position) {
        return 0;
    }

    //------------------------------------abstract-----------------------------------------------
//    public abstract int getItemCounts();
//
//    //    protected abstract int getItemViewTypes(int position);
//    public abstract VH onCompatCreateViewHolders(View realContentView, int viewType);
//
//    protected abstract View onCreateContentViews(ViewGroup parent, int viewType);
//
//    protected abstract void onBindViewHolderr(VH holder, int position);
}
