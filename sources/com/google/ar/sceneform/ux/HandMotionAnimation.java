package com.google.ar.sceneform.ux;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class HandMotionAnimation extends Animation {
    private static final float HALF_PI = 1.5707964f;
    private static final float TWO_PI = 6.2831855f;
    private final View containerView;
    private final View handImageView;

    public HandMotionAnimation(View containerView, View handImageView) {
        this.handImageView = handImageView;
        this.containerView = containerView;
    }

    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float currentAngle = HALF_PI + (TWO_PI * interpolatedTime);
        float radius = this.handImageView.getResources().getDisplayMetrics().density * 25.0f;
        float xPos = ((radius * 2.0f) * ((float) Math.cos((double) currentAngle))) + (((float) this.containerView.getWidth()) / 2.0f);
        float yPos = ((((float) Math.sin((double) currentAngle)) * radius) + (((float) this.containerView.getHeight()) / 2.0f)) - (((float) this.handImageView.getHeight()) / 2.0f);
        this.handImageView.setX(xPos - (((float) this.handImageView.getWidth()) / 2.0f));
        this.handImageView.setY(yPos);
        this.handImageView.invalidate();
    }
}
