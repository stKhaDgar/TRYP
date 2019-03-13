package com.rdev.tryp.trip.detailFragment;

import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class DetailsAnimation {

    private final static String STATE_OPEN = "state_open";
    private final static String STATE_CLOSE = "state_close";

    private View view;
    private ImageView imageView;
    private View carView;
    private String state;

    public DetailsAnimation(View view, ImageView imageView, View carView) {
        this.view = view;
        this.imageView = imageView;
        this.carView = carView;
        state = STATE_CLOSE;
    }

    public void start() {
        if (state == STATE_CLOSE) {
            open();
        } else {
            close();
        }
    }

    private void open() {
        TranslateAnimation imageAnimation = new TranslateAnimation(0, 0, 0, -200);
        imageAnimation.setDuration(1000);
        imageAnimation.setFillAfter(true);
        imageView.startAnimation(imageAnimation);

        TranslateAnimation carImageAnimation = new TranslateAnimation(0, 0, -10, 40);
        carImageAnimation.setDuration(1000);
        carImageAnimation.setFillAfter(true);
        carView.startAnimation(carImageAnimation);

        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -400);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        view.startAnimation(animation);

        state = STATE_OPEN;
    }

    private void close() {
        TranslateAnimation imageAnimation = new TranslateAnimation(0, 0, -200, 0);
        imageAnimation.setDuration(1000);
        imageAnimation.setFillAfter(true);
        imageView.startAnimation(imageAnimation);

        TranslateAnimation carImageAnimation = new TranslateAnimation(0, 0, 40, -10);
        carImageAnimation.setDuration(1000);
        carImageAnimation.setFillAfter(true);
        carView.startAnimation(carImageAnimation);

        TranslateAnimation animation = new TranslateAnimation(0, 0, -400, 0);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        view.startAnimation(animation);

        state = STATE_CLOSE;
    }
}
