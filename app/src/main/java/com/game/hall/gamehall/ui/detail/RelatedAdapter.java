package com.game.hall.gamehall.ui.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.game.hall.download.bean.AppBean;
import com.game.hall.gamehall.R;
import com.game.hall.gamehall.widget.detail.GameImageView2;
import com.game.hall.gamehall.widget.recycler.Adapter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hezhiyong on 2016/9/16.
 */
public class RelatedAdapter extends Adapter<RelatedViewHolder> {

    private List<AppBean> list;

    public RelatedAdapter(List<AppBean> list) {
        this.list = list;
    }

    @Override
    public int getCellsCount() {
        return list.size();
    }

    @Override
    public RelatedViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_cell_item, viewGroup, false);
        return new RelatedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RelatedViewHolder viewHolder, int i) {

        final AppBean bean = list.get(i);
        Picasso.with(viewHolder.gameImageView.getContext()).load(R.mipmap.game_02).into(viewHolder.gameImageView.getImageView());
        viewHolder.gameImageView.setTxt(bean.getAppname());
//        String url = "http://www.lorempixel.com/40" + viewHolder.row + "/40" + viewHolder.cell + "/";
//        Picasso.with(viewHolder.gameImageView.getContext()).load(url).into(viewHolder.gameImageView.getImageView());
    }
}
