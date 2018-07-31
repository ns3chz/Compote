package cn.hu.zc.basilic.view.recycler.callback;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;

public class ItemHelperCallback<T> extends ItemTouchHelper.Callback {
    private int dragFlag;
    private int swipeFlag;
    private int startPosition;
    private int endPosition;
    private int currentActionState;//当前action状态
    private @ActionState
    int thisActionState;//此次手势action状态
    private RecyclerView mRecyclerView;
    private List<T> dataList;
    private OnMovedListener<T> onMovedListener;

    public ItemHelperCallback(int dragFlag, int swipeFlag) {
        this(dragFlag, swipeFlag, null);
    }

    public ItemHelperCallback(int dragFlag, int swipeFlag, List<T> list) {
        this(dragFlag, swipeFlag, list, null);
    }

    public ItemHelperCallback(int dragFlag, int swipeFlag, List<T> list, OnMovedListener<T> listener) {
        this.dragFlag = dragFlag;
        this.swipeFlag = swipeFlag;
        this.dataList = list;
        this.onMovedListener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (mRecyclerView != recyclerView) {
            mRecyclerView = recyclerView;
        }
        return makeMovementFlags(getDragFlag(recyclerView, viewHolder), getSwipeFlag(recyclerView, viewHolder));
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (mRecyclerView != recyclerView) {
            mRecyclerView = recyclerView;
        }
        if (mRecyclerView == null) return false;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) return false;
        //滑动事件
        if (dataList != null) {
            int from = viewHolder.getAdapterPosition();
            endPosition = target.getAdapterPosition();
            Collections.swap(dataList, from, endPosition);
            adapter.notifyItemMoved(from, endPosition);
            //其他操作
            if (onMovedListener != null) {
                onMovedListener.onMoved(dataList, from, endPosition);
            }
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        int listSize;
        if (viewHolder != null && actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            //手势开始
            startPosition = viewHolder.getAdapterPosition();
            thisActionState = actionState;
            if (onMovedListener != null) {
                onMovedListener.onStart(dataList, startPosition, thisActionState);
            }
            Log.e("ItemDragHelper", "开始" + (thisActionState == ItemTouchHelper.ACTION_STATE_DRAG ? "拖拽" : "侧滑 , ") + "actionState is " + actionState);
        } else if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
            //手势结束
            if (currentActionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                //停止拖拽
                //修正position
                if (dataList != null) {
                    listSize = dataList.size();
                    if (startPosition >= 0 && startPosition < listSize && endPosition >= 0 && endPosition < listSize) {
                        if (mRecyclerView != null) {
                            RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
                            if (adapter != null) {
                                adapter.notifyItemRangeChanged(Math.min(startPosition, endPosition), Math.abs(startPosition - endPosition) + 1);
                            }
                        }
                    }
                }
            } else if (currentActionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                //停止侧滑

            }
            //回调
            if (onMovedListener != null) {
                onMovedListener.onEnd(dataList, endPosition, thisActionState);
            }
            Log.e("ItemDragHelper", "结束" + (thisActionState == ItemTouchHelper.ACTION_STATE_DRAG ? "拖拽" : "侧滑 , ") + "actionState is " + actionState);
        }

        currentActionState = actionState;
    }

    //----------------------------------------------------------------------------------------------
    public abstract class OnMovedListener<M> {
        public void onStart(List<M> list, int startPos, @ActionState int actionState) {
        }

        public abstract void onMoved(List<M> list, int posFrom, int posTo);

        public void onEnd(List<M> list, int endPos, @ActionState int actionState) {
        }

    }

    @IntDef({ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.ACTION_STATE_DRAG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ActionState {
    }

    //----------------------------------------------------------------------------------------------

    public int getDragFlag(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return dragFlag;
    }

    public void setDragFlag(int dragFlag) {
        this.dragFlag = dragFlag;
    }

    public int getSwipeFlag(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return swipeFlag;
    }

    public void setSwipeFlag(int swipeFlag) {
        this.swipeFlag = swipeFlag;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public OnMovedListener<T> getOnMovedListener() {
        return onMovedListener;
    }

    public void setOnMovedListener(OnMovedListener<T> onMovedListener) {
        this.onMovedListener = onMovedListener;
    }
}
