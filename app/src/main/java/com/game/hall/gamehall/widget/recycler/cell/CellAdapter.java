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

/**
 * Created by florentchampigny on 31/08/15.
 */
public class CellAdapter extends RecyclerView.Adapter {

    public static final int PLACEHOLDER_START = 3000;
    public static final int PLACEHOLDER_END = 3001;
    public static final int PLACEHOLDER_START_SIZE = 1;
    public static final int PLACEHOLDER_END_SIZE = 1;
    public static final int CELL = 3002;

    final protected Adapter adapter;
    protected HeightCalculatedCallback heightCalculatedCallback;

    public CellAdapter(Adapter adapter, HeightCalculatedCallback heightCalculatedCallback) {
        this.adapter = adapter;
        this.heightCalculatedCallback = heightCalculatedCallback;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return PLACEHOLDER_START;
        else if (position == getItemCount() - 1)
            return PLACEHOLDER_END;
        else
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
            case PLACEHOLDER_START:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_placeholder, viewGroup, false);
                return new PlaceHolderViewHolder(view, true, -1);
            case PLACEHOLDER_END:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_placeholder, viewGroup, false);
                return new PlaceHolderViewHolder(view, true, -1);
            default:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_cell, viewGroup, false);

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

        return new CellViewHolder(view, adapter);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        LogUtil.i("@hzy", "onBindViewHolder---------" + position);
        if (viewHolder instanceof CellViewHolder) {
            final CellViewHolder cellViewHolder = (CellViewHolder) viewHolder;
            if (position == 1)
//            if (position == 0)
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

            cellViewHolder.newPosition(position - PLACEHOLDER_START_SIZE);
//            cellViewHolder.newPosition(position);

            cellViewHolder.onBind();
        }
    }

    @Override
    public int getItemCount() {
        return this.adapter.getCellsCount() + PLACEHOLDER_START_SIZE + PLACEHOLDER_END_SIZE;
//        return this.adapter.getCellsCount();
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        public void onItemClick(View view, int position);
    }

}
