package com.game.hall.gamehall.widget.recycler.cell;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.game.hall.gamehall.R;
import com.game.hall.gamehall.widget.recycler.Adapter;
import com.game.hall.gamehall.widget.recycler.ViewHolder;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 28/08/15.
 */
public class GridViewHolder extends RecyclerView.ViewHolder {

    final static float scaleEnlarged = 1.1f;
    final static float scaleReduced = 1.0f;

    protected CardView cardView;
    protected boolean enlarged = false;

//    protected int position;

    protected final Adapter adapter;
    protected final ViewHolder viewHolder;

    Animator currentAnimator;

//    public void setPosition(int position) {
//        this.position = position;
//    }

    public GridViewHolder(View itemView, Adapter adapter) {
        super(itemView);
        this.adapter = adapter;

        cardView = (CardView) itemView.findViewById(R.id.cardView);
        this.viewHolder = adapter.onCreateViewHolder(cardView);
        cardView.addView(viewHolder.itemView);
    }

    public void enlarge(boolean withAnimation) {
        if (!enlarged) {
            if (currentAnimator != null) {
                currentAnimator.cancel();
                currentAnimator = null;
            }

            Log.i("@hzy", "-----enlarge" + viewHolder.itemView.getId());
            int duration = withAnimation ? 300 : 0;

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(duration);

            List<Animator> animatorList = new ArrayList<>();
            animatorList.add(ObjectAnimator.ofFloat(cardView, "scaleX", scaleEnlarged));
            animatorList.add(ObjectAnimator.ofFloat(cardView, "scaleY", scaleEnlarged));

            //animatorList.add(ObjectAnimator.ofFloat(cardView, "translationX", translationX));
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    cardView.setCardElevation(6);
                    currentAnimator = null;
                }
            });

            animatorSet.playTogether(animatorList);
            currentAnimator = animatorSet;
            animatorSet.start();

            enlarged = true;
        }
    }

    public void reduce(boolean withAnimation) {
        if (enlarged) {
            if (currentAnimator != null) {
                currentAnimator.cancel();
                currentAnimator = null;
            }

            Log.i("@hzy", "-----reduce" + viewHolder.itemView.getId());

            int duration = withAnimation ? 300 : 0;

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(duration);

            List<Animator> animatorList = new ArrayList<>();
            animatorList.add(ObjectAnimator.ofFloat(cardView, "scaleX", scaleReduced));
            animatorList.add(ObjectAnimator.ofFloat(cardView, "scaleY", scaleReduced));

            //animatorList.add(ObjectAnimator.ofFloat(cardView, "translationX", translationX));
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    cardView.setCardElevation(5);
                    currentAnimator = null;
                }
            });

            animatorSet.playTogether(animatorList);
            currentAnimator = animatorSet;
            animatorSet.start();

            enlarged = false;
        }
    }

    public void newPosition(int position) {
        if (position == 0)
            enlarge(true);
        else
            reduce(true);
    }

    public void onBind() {
        int cell = getAdapterPosition();
        viewHolder.cell = cell;
        adapter.onBindViewHolder(viewHolder, cell);
    }

    public boolean isEnlarged() {
        return enlarged;
    }

    public void setEnlarged(boolean enlarged) {
        this.enlarged = enlarged;
    }

}
