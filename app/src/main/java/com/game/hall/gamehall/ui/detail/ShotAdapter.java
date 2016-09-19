package com.game.hall.gamehall.ui.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.game.hall.gamehall.R;
import com.game.hall.gamehall.widget.recycler.Adapter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hezhiyong on 2016/9/16.
 */
public class ShotAdapter extends Adapter<ShotViewHodler> {

    private List<String> imageUrls;

    public ShotAdapter(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCellsCount() {
        return imageUrls.size();
    }

    @Override
    public ShotViewHodler onCreateViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_view_shot, viewGroup, false);
        return new ShotViewHodler(view);
    }

    @Override
    public void onBindViewHolder(ShotViewHodler viewHolder, int i) {
        Picasso.with(viewHolder.shot.getContext()).load(R.mipmap.game_test2).into(viewHolder.shot);
//        String url = "http://www.lorempixel.com/40" + viewHolder.row + "/40" + viewHolder.cell + "/";
//        Picasso.with(viewHolder.shot.getContext()).load(url).into(viewHolder.shot);
    }


}
