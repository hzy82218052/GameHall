package com.game.hall.gamehall.utils;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hezhiyong on 2016/9/16.
 */
public class AnimatorSetUtil implements Serializable {

    private AnimatorSetUtil() {

    }

    public static class ThisHodler {
        static final AnimatorSetUtil INSTANCE = new AnimatorSetUtil();
    }


    public static AnimatorSetUtil getInstance() {
        return ThisHodler.INSTANCE;
    }

    public Object readResolve() {
        return getInstance();
    }


    final static float scaleEnlarged = 1.2f;
    final static float scaleReduced = 1.0f;

    protected boolean enlarged = false;

    protected Animator currentAnimator;
    protected Animator nextAnimator;

    protected int index = 0;

    public void enlarge(boolean withAnimation, final View cardView) {
        if (currentAnimator != null) {
            currentAnimator.cancel();
            currentAnimator = null;
        }

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
                if (cardView instanceof CardView)
                    ((CardView) cardView).setCardElevation(5);
                currentAnimator = null;
            }
        });

        animatorSet.playTogether(animatorList);
        currentAnimator = animatorSet;
        animatorSet.start();

        enlarged = true;
    }

    public void reduce(boolean withAnimation, final View cardView) {
        if (nextAnimator != null) {
            nextAnimator.cancel();
            nextAnimator = null;
        }

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
                if (cardView instanceof CardView)
                    ((CardView) cardView).setCardElevation(5);
                nextAnimator = null;
            }
        });

        animatorSet.playTogether(animatorList);
        nextAnimator = animatorSet;
        animatorSet.start();

        enlarged = false;
    }

}
