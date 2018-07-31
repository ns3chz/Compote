package cn.hu.zc.basilic.view.recycler.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.hu.zc.basilic.model.ModelSelectAbstra;
import cn.hu.zc.basilic.view.recycler.holder.BaseRecyclerViewHolder;
import cn.hu.zc.basilic.view.recycler.listener.OnRecyclerItemSelectedListener;
import cn.hu.zc.tools.CptTools;
import cn.hu.zc.tools.ReflectTools;

/**
 * 自定义布局
 * Created by Administrator on 2017/6/26.
 */

public class AdapterRecyclerGridSelectHolder<MODEL extends ModelSelectAbstra> extends BaseRecyclerHeaderFooterAdapter<RecyclerView.ViewHolder> {

    private Class<?> mHolderClass;
    private RecyclerView.ViewHolder mViewHolder;
    private
    @LayoutRes
    int mLayoutRes = 0;

    private List<MODEL> dataList;
    private List<MODEL> selectModelList;
    //
    private boolean canMultiCheck = false;//多选
    private boolean canUnselect = true;//能否不选
    private int showCount = -1;//只显示的item个数
    //
    private int SELECT_POSITION = 0;
    private boolean isResetSelected = true;
    private int currentSelectPosition = -1;//记录当前选择的角标，仅触发该回调
    private int lastSelectPosition = -1;//记录当前选择的角标，仅触发该回调
    private boolean isShowAll = true;//是否显示完全

    //
    private OnRecyclerItemSelectedListener<MODEL> mOnRecyclerItemSelectedListener;
    private OnBindViewHolderListener mOnBindViewHolderListener;

    public AdapterRecyclerGridSelectHolder(Context ctx, List<MODEL> list) {
        super(ctx);
        this.dataList = list;
        this.selectModelList = new ArrayList<>();
    }

    public void setOnBindViewHolderListener(OnBindViewHolderListener listener) {
        this.mOnBindViewHolderListener = listener;
    }

    /**
     */
    @Override
    public void onBindViewHolderr(RecyclerView.ViewHolder holder, final int position) {

//        holder.selectTv.setText(dataList.get(position).getName());
        if (mOnBindViewHolderListener != null) {
            mOnBindViewHolderListener.onBindViewHolders(holder, dataList.get(position), position);
        }

        //若设置了父布局点击，则不能选择，点击监听设置
        if (onRecyclerViewItemClickListener == null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isSelected = v.isSelected();
//                    GloHelper.logD(getClass(),"recycler is selecting , isSelected="+!isSelected);
                    //
                    if (!canUnselect && isSelected && selectModelList.size() == 1) {
                        //当必须选择时，当前最后一个选中状态不能取消

                    } else {
                        select(position, !isSelected);
//                        lastSelectPosition = position;//当前角标需要重置，所以上一次的角标在select后设置
                    }

                }
            });
            // 在onBindViewHolders中改变状态，否则刷新会将状态刷掉
            //重置选中状态
            MODEL model = dataList.get(position);
            if (model != null) {
                model.setIndex(position);
            }
            if (isResetSelected) {
                holder.itemView.setSelected(false);
                if (model != null) {
                    model.setSelected(false);
                }
                if (position == getItemCounts() - 1) {
                    isResetSelected = false;
                }
            } else {
                //通过列表判断是否选择
                boolean isContain = model != null && model.isSelected();
                //每次刷新时，view的select状态会重置为false
                holder.itemView.setSelected(isContain);
                if (isContain) {
                    lastSelectPosition = position;
                }
                checkSelect(model, isContain);

                //
                if (currentSelectPosition == position) {
                    //选择事件
                    if (this.mOnRecyclerItemSelectedListener != null) {
                        if (model != null) {
                            if (model.getUnCauseSelectListenerTimes() <= 0) {
                                //不处罚次数小于1时，触发监听
                                this.mOnRecyclerItemSelectedListener.selected(holder, model, position, isContain);
                            } else {
                                //消耗1
                                model.setUnCauseSelectListenerTimes(model.getUnCauseSelectListenerTimes() - 1);
                            }
                        }
                    }
                    //重置当前角标
                    currentSelectPosition = -1;
                }
            }

        }


    }

    /**
     */
    private void select(int position, boolean flag) {
        //默认触发
        select(position, flag, 0);
    }

    /**
     * @param flag    选中，或取消选中
     * @param uncause 不触发监听次数
     */
    private void select(int position, boolean flag, int uncause) {
        currentSelectPosition = position;
        //判断是否可多选
        if (!canMultiCheck) {
//            this.selectPositionList.clear();
            this.selectModelList.clear();
            if (CptTools.isInSize(dataList, lastSelectPosition)) {
                this.dataList.get(lastSelectPosition).setSelected(false);
            }
        }
        //判断是否outOfIndex
        if (CptTools.isInSize(dataList, position)) {
            MODEL data = dataList.get(position);
            if (data != null) {
                data.setSelected(flag);
                data.setUnCauseSelectListenerTimes(uncause);
                checkSelect(data, flag);
            }
        }
        // 能多选则只刷新点击的item(否则刷新全部,若其他item有选中，会再次走回调selected方法)，
        // 单选则刷新全部，刷掉其余状态
        if (canMultiCheck) {
            notifyItemChanged(position);
        } else {
            notifyDataSetChanged();//fresh
        }
        lastSelectPosition = position;//当前角标需要重置，所以上一次的角标在select后设置
    }

    private void checkSelect(MODEL model, boolean isSelected) {
        if (model == null) return;
        if (isSelected) {
            //添加文本到列表
            if (!this.selectModelList.contains(model)) {
                this.selectModelList.add(model);
            }
        } else {
            //删除
            if (this.selectModelList.contains(model)) {
                this.selectModelList.remove(model);
            }
        }
    }

    @Override
    public View onCreateContentViews(ViewGroup parent, int viewType) {
        View view = null;
        if (mLayoutRes != 0) {
            view = mLayoutInflater.inflate(mLayoutRes, parent, false);
        }
        return view;
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolders(View realContentView, int viewType) {
        if (realContentView == null) return new BaseRecyclerViewHolder(new View(mCtx));

        RecyclerView.ViewHolder holder = null;
        if (this.mHolderClass != null) {
            Object o = ReflectTools.newInstance(this.mHolderClass, new Class[]{View.class}, new Object[]{realContentView});
            if (o instanceof RecyclerView.ViewHolder) {
                holder = (RecyclerView.ViewHolder) o;
            }
        }

        if (holder == null) {
            holder = new BaseRecyclerViewHolder(realContentView);
        }

        return holder;
    }

    @Override
    public int getItemCounts() {
        if (dataList == null) return 0;
        if (this.mLayoutRes != 0 && this.mHolderClass != null) {
            int count = this.dataList.size();
            if (this.showCount < 0) {
                this.isShowAll = true;
                return count;
            } else {
                this.isShowAll = count <= this.showCount;
                return this.isShowAll ? count : this.showCount;
            }
        } else {
            return 0;
        }
    }

    @Override
    public MODEL getClickData(RecyclerView.ViewHolder holder, int position) {
        return dataList == null ? null : dataList.get(position);
    }

    /**
     * 设置了item点击事件后无效
     */
    public void setOnRecyclerItemSelectedListener(OnRecyclerItemSelectedListener<MODEL> listener) {
        this.mOnRecyclerItemSelectedListener = listener;
    }

    //----------------------------------------------------------------------------------------------

    /**
     * 能否多选
     */
    public void setCanMultiCheck(boolean canMultiCheck) {
        this.canMultiCheck = canMultiCheck;
    }

    /**
     * 能否不选
     */
    public void setCanUnselect(boolean canUnselect) {
        this.canUnselect = canUnselect;
    }

    /**
     * showCount < 0:显示全部,大于实际count显示实际个数
     *
     * @param showCount 只显示showCount个item
     */
    public void setShowCount(int showCount) {
        this.showCount = showCount;
    }

    /**
     * 设置选中位置
     */
    public void setSelected(int index) {
        this.SELECT_POSITION = index;
        //主动选择
        if (SELECT_POSITION >= 0 && SELECT_POSITION < dataList.size()) {
            select(SELECT_POSITION, true);
        }
        this.SELECT_POSITION = -1;
    }

    /**
     * item布局的holder
     */
    public void setViewHolder(RecyclerView.ViewHolder mHolder) {
        this.mHolderClass = mHolder == null ? null : mHolder.getClass();
        notifyDataChanged();
    }

    public void setViewHolder(Class<?> clazz) {
        this.mHolderClass = clazz;
        this.notifyDataChanged();
    }

    /**
     * item布局
     */
    public void setLayoutRes(@LayoutRes int mRes) {
        this.mLayoutRes = mRes;
        notifyDataChanged();
    }

    /**
     * 重置选择
     */
    public void resetSelected() {
        selectModelList.clear();
        isResetSelected = true;
    }

    /**
     * 删除某一个item
     */
    public void deleteItems(int position) {
        MODEL model = dataList.get(position);
        if (selectModelList.contains(model)) {
            selectModelList.remove(model);
        }
        if (dataList.contains(model)) {
            dataList.remove(model);
        }
//        notifyItemRemoved(position);
        notifyDataChanged();
    }

    /**
     * 返回选择列表
     */
    public List<MODEL> getSelectModelList() {
        return selectModelList;
    }


    //----------------------------------------------------------------------------------------------

    /**
     * 是否所有item全部可以显示,isShowAll == false:最多显示showCount个item
     */
    public boolean isShowAll() {
        return isShowAll;
    }

    /**
     */
    public void setResetSelected(boolean resetSelected) {
        isResetSelected = resetSelected;
    }

    public interface OnBindViewHolderListener<MOD extends ModelSelectAbstra> {
        void onBindViewHolders(RecyclerView.ViewHolder holder, MOD model, int position);
    }
}
