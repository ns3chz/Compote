package cn.hu.zc.basilic.view.recycler.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.hu.zc.basilic.R;
import cn.hu.zc.basilic.model.ModelSelectAbstra;
import cn.hu.zc.basilic.view.recycler.holder.HolderGridSelect;
import cn.hu.zc.basilic.view.recycler.listener.OnRecyclerItemSelectedListener;
import cn.hu.zc.tools.CptTools;
import cn.hu.zc.tools.UITools;

/**
 * Created by Administrator on 2017/6/26.
 */

public class AdapterRecyclerGridSelectText<MODEL extends ModelSelectAbstra> extends BaseRecyclerHeaderFooterAdapter<HolderGridSelect> {
    private List<MODEL> dataList;
    private List<MODEL> selectModelList;
    private List<Integer> imageDataList;
    private ImageGravity imageGravity;
    private float imageScales = 1f;//图片缩放比例
    //
    private boolean canMultiCheck = false;//多选
    private boolean canUnselect = true;//能否不选
    private int showCount = -1;//只显示的item个数
    //
    private
    @DrawableRes
    int itemBackground = 0;//背景色，state_selected状态
    private
    @ColorRes
    int itemTextColor = 0;//文字颜色，state_selected状态
    private float textSize = 0;//字体大小
    private int[] textMargin = {5, 5, 5, 5};
    private int[] textPadding = {5, 5, 5, 5};
    private int itemHeight = 0;
    private int itemWidth = 0;
    private int textGravity = Gravity.CENTER;
    private int SELECT_POSITION = 0;
    private boolean reloadLayoutParams = true;//重新设置布局参数
    private boolean isResetSelected = false;
    private int currentSelectPosition = -1;//记录当前选择的角标，仅触发该回调
    //    private int lastSelectPosition = -1;//记录当前选择的角标，仅触发该回调
    private boolean isShowAll = true;//是否显示完全
    //
    private OnRecyclerItemSelectedListener<MODEL> mOnRecyclerItemSelectedListener;

    public AdapterRecyclerGridSelectText(Context ctx, List<MODEL> list) {
        super(ctx);
        this.dataList = list;
        this.selectModelList = new ArrayList<>();
    }

    /**
     */
    @Override
    public void onBindViewHolderr(HolderGridSelect holder, final int position) {

        holder.selectTv.setText(dataList.get(position).getName());
        if (reloadLayoutParams) {
            //属性设置
            holder.selectTv.setGravity(textGravity);
            if (itemBackground != 0) {
                holder.selectTv.setBackgroundResource(itemBackground);
            }
            if (itemTextColor != 0) {//字体颜色
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.selectTv.setTextColor(mCtx.getResources().getColorStateList(itemTextColor, mCtx.getTheme()));
                } else {
                    holder.selectTv.setTextColor(mCtx.getResources().getColorStateList(itemTextColor));
                }
            }
            if (textSize != 0) {//字体大小
                holder.selectTv.setTextSize(textSize);
            }

            //设置图片
            if (imageDataList != null && imageDataList.size() > position) {
                switch (imageGravity) {
                    case LEFT:
                        UITools.setCompoundDrawables(holder.selectTv, imageScales, imageDataList.get(position), 0, 0, 0);
                        break;
                    case TOP:
                        UITools.setCompoundDrawables(holder.selectTv, imageScales, 0, imageDataList.get(position), 0, 0);
                        break;
                    case RIGHT:
                        UITools.setCompoundDrawables(holder.selectTv, imageScales, 0, 0, imageDataList.get(position), 0);
                        break;
                    case BOTTOM:
                        UITools.setCompoundDrawables(holder.selectTv, imageScales, 0, 0, 0, imageDataList.get(position));
                        break;
                }
            } else {
                //需要清除图片，否则会复用
                holder.selectTv.setCompoundDrawables(null, null, null, null);
            }
            //设置padding
            if (textPadding != null && textPadding.length == 4) {
                holder.selectTv.setPadding(textPadding[0], textPadding[1], textPadding[2], textPadding[3]);
            }
            //textview参数设置
            ViewGroup.LayoutParams params = holder.selectTv.getLayoutParams();
            if ((textMargin != null && textMargin.length == 4)) {
                if (params instanceof GridLayoutManager.LayoutParams) {
                    //设置margin
//                        ((RelativeLayout.LayoutParams) params).setMargins(textMargin[0], textMargin[1], textMargin[2], textMargin[3]);
                    ((GridLayoutManager.LayoutParams) params).setMargins(textMargin[0], textMargin[1], textMargin[2], textMargin[3]);

                }
            }
            if (itemHeight != 0) {
                //设置height
                if (itemHeight == ViewGroup.LayoutParams.WRAP_CONTENT || itemHeight == ViewGroup.LayoutParams.MATCH_PARENT) {
                    params.height = itemHeight;
                } else {
                    params.height = UITools.dp2px(itemHeight);
                }
            }
            if (itemWidth != 0) {
                //设置width
                if (itemWidth == ViewGroup.LayoutParams.WRAP_CONTENT || itemWidth == ViewGroup.LayoutParams.MATCH_PARENT) {
                    params.width = itemWidth;
                } else {
                    params.width = UITools.dp2px(itemWidth);
                }
            }
            //重新设置
            holder.selectTv.setLayoutParams(params);
            //

            if (position == getItemCounts()) {
                reloadLayoutParams = false;
            }
        }


        //若设置了父布局点击，则不能选择，点击监听设置
        if (onRecyclerViewItemClickListener == null) {
            holder.selectTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isSelected = v.isSelected();
                    //
                    if (!canUnselect && isSelected && selectModelList.size() == 1) {
                        //当必须选择时，当前最后一个选中状态不能取消

                    } else {
                        select(position, !isSelected);

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
                holder.selectTv.setSelected(false);
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
                holder.selectTv.setSelected(isContain);
//                if (isContain) {
//                    lastSelectPosition = position;
//                }
                updateSelect(model, isContain);

                //
                if (currentSelectPosition == position) {
                    //选择事件
                    if (this.mOnRecyclerItemSelectedListener != null) {
                        if (model.getUnCauseSelectListenerTimes() <= 0) {
                            //不处罚次数小于1时，触发监听
                            this.mOnRecyclerItemSelectedListener.selected(holder, model, position, isContain);
                        } else {
                            //消耗1
                            model.setUnCauseSelectListenerTimes(model.getUnCauseSelectListenerTimes() - 1);
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
            for (int i = 0; i < dataList.size(); i++) {
                MODEL model = dataList.get(i);
                if (model == null) continue;
                model.setSelected(false);
            }
        }
        //判断是否outOfIndex
        if (CptTools.isInSize(dataList, position)) {
            MODEL data = dataList.get(position);
            if (data != null) {
                data.setSelected(flag);
                data.setUnCauseSelectListenerTimes(uncause);
                updateSelect(data, flag);
            }
        }
        // 能多选则只刷新点击的item(否则刷新全部,若其他item有选中，会再次走回调selected方法)，
        // 单选则刷新全部，刷掉其余状态
        if (canMultiCheck) {
            notifyItemChanged(position);
        } else {
            notifyDataSetChanged();//fresh
        }
//        lastSelectPosition = position;//
    }

    private void updateSelect(MODEL model, boolean isSelected) {
        model.setSelected(isSelected);
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
        View view = mLayoutInflater.inflate(R.layout.item_recycler_grid_select, parent, false);
        return view;
    }

    @Override
    public HolderGridSelect onCompatCreateViewHolders(View realContentView, int viewType) {
        return new HolderGridSelect(realContentView);
    }

    @Override
    public int getItemCounts() {
        if (dataList == null) return 0;
        int count = dataList.size();
        //小于0显示全部
        if (this.showCount < 0) {
            isShowAll = true;
            return count;
        }
        isShowAll = count <= this.showCount;

        //需要显示的个数大于实际个数，则只显示实际个数，否则显示showcount个
        return isShowAll ? count : this.showCount;
    }

    @Override
    public MODEL getClickData(HolderGridSelect holder, int position) {
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
     * 图片资源
     */
    public void setImageDataList(List<Integer> imageDataList) {
        this.imageDataList = imageDataList;
        this.reloadLayoutParams = true;
    }

    /**
     * 图片资源
     */
    public void setImageDataList(Integer... resArr) {
        List<Integer> list = null;
        if (resArr != null) {
            list = new ArrayList<>(Arrays.asList(resArr));
        }
        setImageDataList(list);
    }

    /**
     * 图片位于文字的位置
     */
    public void setImageGravity(ImageGravity imageGravity) {
        this.imageGravity = imageGravity;
        this.reloadLayoutParams = true;
    }

    /**
     * item背景，选中与非选中
     */
    public void setItemBackground(@DrawableRes int itemBackground) {
        this.itemBackground = itemBackground;
        this.reloadLayoutParams = true;
    }

    /**
     * item文字颜色，选中与非选中
     */
    public void setItemTextColor(@ColorRes int itemTextColor) {
        this.itemTextColor = itemTextColor;
        this.reloadLayoutParams = true;
    }

    /**
     * 字体大小,sp
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
        this.reloadLayoutParams = true;
    }

    /**
     * item高度，dp
     */
    public void setTextHeight(int HEIGHT) {
        //不在此计算为px值
        this.itemHeight = HEIGHT;
        this.reloadLayoutParams = true;
    }

    /**
     * item宽度，dp
     */
    public void setTextWidth(int width) {
        //不在此计算为px值
        this.itemWidth = width;
        this.reloadLayoutParams = true;
    }

    /**
     * 文字gravity
     */
    public void setTextGravity(int GRAVITY) {
        this.textGravity = GRAVITY;
        this.reloadLayoutParams = true;
    }


    /**
     * 图片的缩放比
     */
    public void setImageScales(float scales) {
        this.imageScales = scales;
        if (this.imageScales < 0) {
            this.imageScales = 0;
        }
        if (this.imageScales > 1) {
            this.imageScales = 1;
        }
        this.reloadLayoutParams = true;
    }

    /**
     * 文字间隔,单位pixel
     */
    public void setTextMargin(int... margin) {
//        if (margin != null) {
//            for (int i = 0; i < margin.length; i++) {
//                margin[i] = UITools.dp2px(margin[i]);
//            }
//        }
        this.textMargin = margin;
        this.reloadLayoutParams = true;
    }

    /**
     * 文字间隔,单位pixel
     */
    public void setTextPadding(int... padding) {
//        if (padding != null) {
//            for (int i = 0; i < padding.length; i++) {
//                padding[i] = UITools.dp2px(padding[i]);
//            }
//        }
        this.textPadding = padding;
        this.reloadLayoutParams = true;
    }

    //--------------------------------------------

    /**
     * @param text 选择文本为text的item，并触发事件
     */
    public void select(String text) {
        select(text, 0);
    }

    /**
     * 通过文本选择
     *
     * @param uncause 不触发监听次数，该次不触发，设置1
     */
    public void select(String text, int uncause) {
        if (dataList == null) return;
        for (int i = 0; i < dataList.size(); i++) {
            MODEL model = dataList.get(i);
            if (model.getName().equalsIgnoreCase(text)) {
                select(i, true, uncause);
                break;
            }
        }
    }

    /**
     * @return name tag is selected
     */
    public boolean isSelected(String name) {
        List<MODEL> selectModelList = this.getSelectModelList();
        if (name != null && name.length() > 0 && selectModelList != null) {
            for (int i = 0; i < selectModelList.size(); ++i) {
                MODEL model = selectModelList.get(i);
                if (model != null && name.equals(model.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
//--------------------------------------------

    /**
     * @param index 选择角标位index的item，并触发事件
     */
    public void setSelected(int index) {
        setSelected(index, 0);
    }

    /**
     * 设置选中位置
     *
     * @param uncause 不触发监听次数，该次不触发，设置1
     */
    public void setSelected(int index, int uncause) {
        this.SELECT_POSITION = index;
        //主动选择
        if (SELECT_POSITION >= 0 && SELECT_POSITION < dataList.size()) {
            select(SELECT_POSITION, true, uncause);
        }
        this.SELECT_POSITION = -1;
    }

    //--------------------------------------------

    /**
     * 重置选择
     */
    public void resetSelected() {
        selectModelList.clear();
        isResetSelected = true;
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

    public enum ImageGravity {
        LEFT, TOP, RIGHT, BOTTOM, NONE
    }
}
