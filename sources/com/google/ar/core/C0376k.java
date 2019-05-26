package com.google.ar.core;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

/* renamed from: com.google.ar.core.k */
final class C0376k implements AnimatorUpdateListener {
    /* renamed from: a */
    private final /* synthetic */ int f64a;
    /* renamed from: b */
    private final /* synthetic */ int f65b;
    /* renamed from: c */
    private final /* synthetic */ int f66c;
    /* renamed from: d */
    private final /* synthetic */ InstallActivity f67d;

    C0376k(InstallActivity installActivity, int i, int i2, int i3) {
        this.f67d = installActivity;
        this.f64a = i;
        this.f65b = i2;
        this.f66c = i3;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        float animatedFraction = 1.0f - valueAnimator.getAnimatedFraction();
        float animatedFraction2 = valueAnimator.getAnimatedFraction();
        this.f67d.getWindow().setLayout((int) ((((float) this.f64a) * animatedFraction) + (((float) this.f65b) * animatedFraction2)), (int) ((((float) this.f66c) * animatedFraction) + (((float) this.f65b) * animatedFraction2)));
        this.f67d.getWindow().getDecorView().refreshDrawableState();
    }
}
