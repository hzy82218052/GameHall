package com.game.hall.gamehall.widget.recycler;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.game.hall.gamehall.utils.CommonUtil;
import com.game.hall.gamehall.utils.LogUtil;
import com.game.hall.gamehall.widget.recycler.cell.CellAdapter;
import com.game.hall.gamehall.widget.recycler.cell.CellViewHolder;

/**
 * Created by Administrator on 2016/8/30.
 */
public class MyRecyclerView extends RecyclerView {

    /**
     * 记录当前第一个View
     */
    private View mCurrentView;

    private OnItemScrollChangeListener mItemScrollChangeListener;

    public void setOnItemScrollChangeListener(
            OnItemScrollChangeListener mItemScrollChangeListener) {
        this.mItemScrollChangeListener = mItemScrollChangeListener;
    }

    public interface OnItemScrollChangeListener {
        void onChange(View view, int position);
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
//        this.addOnScrollListener(getScrollListener());
    }

//    @Override
//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
////        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
////        super.onMeasure(widthMeasureSpec, expandSpec);
//
//    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

//        mCurrentView = getChildAt(0);
//
//        if (mItemScrollChangeListener != null) {
//            mItemScrollChangeListener.onChange(mCurrentView,
//                    getChildPosition(mCurrentView));
//        }
    }

//    private OnScrollListener getScrollListener() {
//        return new OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            }
//
//            /**
//             *
//             * 滚动时，判断当前第一个View是否发生变化，发生才回调
//             */
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                View newView = getChildAt(0);
//
//                if (mItemScrollChangeListener != null) {
//                    if (newView != null && newView != mCurrentView) {
//                        mCurrentView = newView;
//                        mItemScrollChangeListener.onChange(mCurrentView,
//                                getChildPosition(mCurrentView));
//
//                    }
//                }
//
//            }
//        };
//    }


    private int DEFALUT_POSTION = 1;
    private boolean isWrapped = false;

    /**
     * 初始化
     *
     * @param adapter
     */
    public void initRecycler(final RecyclerView recyclerView, com.game.hall.gamehall.widget.recycler.Adapter adapter) {

        LinearLayoutManager myLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(myLayoutManager);
        recyclerView.setHasFixedSize(true);


        final CellAdapter cellAdapter = new CellAdapter(adapter, new CellAdapter.HeightCalculatedCallback() {
            @Override
            public void onHeightCalculated(int height) {
                if(!isWrapped){
                    recyclerView.getLayoutParams().height = height+CommonUtil.dp2px(getContext(),20);
                    recyclerView.requestLayout();
                    isWrapped = true;
                }

            }

            @Override
            public boolean isWrapped() {
                return false;
            }
        });

        cellAdapter.setOnItemClick(new CellAdapter.OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < MyRecyclerView.this.getChildCount(); ++i) {
                    RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(MyRecyclerView.this.getChildAt(i));
                    if (viewHolder instanceof CellViewHolder) {
                        LogUtil.i("@hzy", "onScrolled---------" + i);

                        CellViewHolder cellViewHolder = ((CellViewHolder) viewHolder);

                        if (cellViewHolder.getPosition() == position) {
                            cellViewHolder.enlarge(true);
                        } else {
                            cellViewHolder.reduce(true);
                        }
//                        cellViewHolder.newPosition(i);
                    }
                }
            }
        });

        recyclerView.setAdapter(cellAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                for (int i = 0; i < recyclerView.getChildCount(); ++i) {
                    RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                    if (viewHolder instanceof CellViewHolder) {
                        Log.i("@hzy", "onScrolled---------" + i);
                        CellViewHolder cellViewHolder = ((CellViewHolder) viewHolder);
                        if (cellViewHolder.getPosition() == DEFALUT_POSTION) {
                            cellViewHolder.enlarge(true);
                        } else {
                            cellViewHolder.reduce(true);
                        }
                    }
                }
            }
        });
    }

}
