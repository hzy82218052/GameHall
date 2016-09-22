package com.game.hall.gamehall.widget.recycler.cell;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.game.hall.gamehall.R;
import com.game.hall.gamehall.utils.LogUtil;
import com.game.hall.gamehall.widget.recycler.Adapter;
import com.game.hall.gamehall.widget.recycler.PlaceHolderViewHolder;
import com.game.hall.gamehall.widget.recycler.cell.CellViewHolder;

/**
 * Created by florentchampigny on 31/08/15.
 */
public class GridAdapter extends RecyclerView.Adapter {

    public static final int PLACEHOLDER_START = 3000;
    public static final int PLACEHOLDER_END = 3001;
    //    public static final int PLACEHOLDER_START_SIZE = 1;
//    public static final int PLACEHOLDER_END_SIZE = 1;
    public static final int CELL = 3002;

    final protected Adapter adapter;
    protected HeightCalculatedCallback heightCalculatedCallback;

    public GridAdapter(Adapter adapter, HeightCalculatedCallback heightCalculatedCallback) {
        this.adapter = adapter;
        this.heightCalculatedCallback = heightCalculatedCallback;
    }

    @Override
    public int getItemViewType(int position) {
//        int yu = position % 6;
//        int yu_ = (position+1)%6;
//        if (yu == 0)
//            return PLACEHOLDER_START;
//        else if (yu_ == 0)
//            return PLACEHOLDER_END;
//        else
        return CELL;
    }

    public interface HeightCalculatedCallback {
        void onHeightCalculated(int height);

        boolean isWrapped();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        final View view;
        switch (type) {
//            case PLACEHOLDER_START:
//                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_placeholder2, viewGroup, false);
//                return new PlaceHolderViewHolder(view, true, -1);
//            case PLACEHOLDER_END:
//                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_placeholder2, viewGroup, false);
//                return new PlaceHolderViewHolder(view, true, -1);
            default:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_cell_grid, viewGroup, false);

                //simulate wrap_content
                view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        if (heightCalculatedCallback != null)
                            heightCalculatedCallback.onHeightCalculated(view.getHeight());
                        view.getViewTreeObserver().removeOnPreDrawListener(this);
                        return false;
                    }
                });
        }

        return new GridViewHolder(view, adapter);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        LogUtil.i("@hzy", "onBindViewHolder---------" + position);
        if (viewHolder instanceof GridViewHolder) {
            final GridViewHolder cellViewHolder = (GridViewHolder) viewHolder;
            if (position == 0)
                cellViewHolder.setEnlarged(false);
            else
                cellViewHolder.setEnlarged(true);

            cellViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClick != null) {
                        onItemClick.onItemClick(v, position);
                        LogUtil.i("@hzy", "onClick---------" + position);
//                            cellViewHolder.newPosition(position);
                    }
                }
            });

            cellViewHolder.newPosition(position);

            cellViewHolder.onBind();
        }
    }

    @Override
    public int getItemCount() {
        return this.adapter.getCellsCount();
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        public void onItemClick(View view, int position);
    }

}
