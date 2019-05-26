package com.google.ar.sceneform.ux;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class HandMotionView extends ImageView {
    private static final long ANIMATION_SPEED_MS = 2500;
    private HandMotionAnimation animation;

    public HandMotionView(Context context) {
        super(context);
    }

    public HandMotionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        clearAnimation();
        this.animation = new HandMotionAnimation((FrameLayout) ((Activity) getContext()).findViewById(C0024R.id.sceneform_hand_layout), this);
        this.animation.setRepeatCount(-1);
        this.animation.setDuration(ANIMATION_SPEED_MS);
        this.animation.setStartOffset(1000);
        startAnimation(this.animation);
    }
}
