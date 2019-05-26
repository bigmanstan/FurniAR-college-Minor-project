package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(21)
class FloatingActionButtonLollipop extends FloatingActionButtonImpl {
    private InsetDrawable mInsetDrawable;

    static class AlwaysStatefulGradientDrawable extends GradientDrawable {
        AlwaysStatefulGradientDrawable() {
        }

        public boolean isStateful() {
            return true;
        }
    }

    FloatingActionButtonLollipop(VisibilityAwareImageButton view, ShadowViewDelegate shadowViewDelegate) {
        super(view, shadowViewDelegate);
    }

    void setBackgroundDrawable(ColorStateList backgroundTint, Mode backgroundTintMode, int rippleColor, int borderWidth) {
        Drawable rippleContent;
        this.mShapeDrawable = DrawableCompat.wrap(createShapeDrawable());
        DrawableCompat.setTintList(this.mShapeDrawable, backgroundTint);
        if (backgroundTintMode != null) {
            DrawableCompat.setTintMode(this.mShapeDrawable, backgroundTintMode);
        }
        if (borderWidth > 0) {
            this.mBorderDrawable = createBorderDrawable(borderWidth, backgroundTint);
            rippleContent = new LayerDrawable(new Drawable[]{this.mBorderDrawable, this.mShapeDrawable});
        } else {
            this.mBorderDrawable = null;
            rippleContent = this.mShapeDrawable;
        }
        this.mRippleDrawable = new RippleDrawable(ColorStateList.valueOf(rippleColor), rippleContent, null);
        this.mContentBackground = this.mRippleDrawable;
        this.mShadowViewDelegate.setBackgroundDrawable(this.mRippleDrawable);
    }

    void setRippleColor(int rippleColor) {
        if (this.mRippleDrawable instanceof RippleDrawable) {
            ((RippleDrawable) this.mRippleDrawable).setColor(ColorStateList.valueOf(rippleColor));
        } else {
            super.setRippleColor(rippleColor);
        }
    }

    void onElevationsChanged(float elevation, float pressedTranslationZ) {
        FloatingActionButtonLollipop floatingActionButtonLollipop = this;
        float f = elevation;
        float f2 = pressedTranslationZ;
        if (VERSION.SDK_INT != 21) {
            StateListAnimator stateListAnimator = new StateListAnimator();
            AnimatorSet set = new AnimatorSet();
            set.play(ObjectAnimator.ofFloat(floatingActionButtonLollipop.mView, "elevation", new float[]{f}).setDuration(0)).with(ObjectAnimator.ofFloat(floatingActionButtonLollipop.mView, View.TRANSLATION_Z, new float[]{f2}).setDuration(100));
            set.setInterpolator(ANIM_INTERPOLATOR);
            stateListAnimator.addState(PRESSED_ENABLED_STATE_SET, set);
            set = new AnimatorSet();
            set.play(ObjectAnimator.ofFloat(floatingActionButtonLollipop.mView, "elevation", new float[]{f}).setDuration(0)).with(ObjectAnimator.ofFloat(floatingActionButtonLollipop.mView, View.TRANSLATION_Z, new float[]{f2}).setDuration(100));
            set.setInterpolator(ANIM_INTERPOLATOR);
            stateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, set);
            set = new AnimatorSet();
            List<Animator> animators = new ArrayList();
            animators.add(ObjectAnimator.ofFloat(floatingActionButtonLollipop.mView, "elevation", new float[]{f}).setDuration(0));
            if (VERSION.SDK_INT >= 22 && VERSION.SDK_INT <= 24) {
                animators.add(ObjectAnimator.ofFloat(floatingActionButtonLollipop.mView, View.TRANSLATION_Z, new float[]{floatingActionButtonLollipop.mView.getTranslationZ()}).setDuration(100));
            }
            animators.add(ObjectAnimator.ofFloat(floatingActionButtonLollipop.mView, View.TRANSLATION_Z, new float[]{0.0f}).setDuration(100));
            set.playSequentially((Animator[]) animators.toArray(new ObjectAnimator[0]));
            set.setInterpolator(ANIM_INTERPOLATOR);
            stateListAnimator.addState(ENABLED_STATE_SET, set);
            set = new AnimatorSet();
            set.play(ObjectAnimator.ofFloat(floatingActionButtonLollipop.mView, "elevation", new float[]{0.0f}).setDuration(0)).with(ObjectAnimator.ofFloat(floatingActionButtonLollipop.mView, View.TRANSLATION_Z, new float[]{0.0f}).setDuration(0));
            set.setInterpolator(ANIM_INTERPOLATOR);
            stateListAnimator.addState(EMPTY_STATE_SET, set);
            floatingActionButtonLollipop.mView.setStateListAnimator(stateListAnimator);
        } else if (floatingActionButtonLollipop.mView.isEnabled()) {
            floatingActionButtonLollipop.mView.setElevation(f);
            if (!floatingActionButtonLollipop.mView.isFocused()) {
                if (!floatingActionButtonLollipop.mView.isPressed()) {
                    floatingActionButtonLollipop.mView.setTranslationZ(0.0f);
                }
            }
            floatingActionButtonLollipop.mView.setTranslationZ(f2);
        } else {
            floatingActionButtonLollipop.mView.setElevation(0.0f);
            floatingActionButtonLollipop.mView.setTranslationZ(0.0f);
        }
        if (floatingActionButtonLollipop.mShadowViewDelegate.isCompatPaddingEnabled()) {
            updatePadding();
        }
    }

    public float getElevation() {
        return this.mView.getElevation();
    }

    void onCompatShadowChanged() {
        updatePadding();
    }

    void onPaddingUpdated(Rect padding) {
        if (this.mShadowViewDelegate.isCompatPaddingEnabled()) {
            this.mInsetDrawable = new InsetDrawable(this.mRippleDrawable, padding.left, padding.top, padding.right, padding.bottom);
            this.mShadowViewDelegate.setBackgroundDrawable(this.mInsetDrawable);
            return;
        }
        this.mShadowViewDelegate.setBackgroundDrawable(this.mRippleDrawable);
    }

    void onDrawableStateChanged(int[] state) {
    }

    void jumpDrawableToCurrentState() {
    }

    boolean requirePreDrawListener() {
        return false;
    }

    CircularBorderDrawable newCircularDrawable() {
        return new CircularBorderDrawableLollipop();
    }

    GradientDrawable newGradientDrawableForShape() {
        return new AlwaysStatefulGradientDrawable();
    }

    void getPadding(Rect rect) {
        if (this.mShadowViewDelegate.isCompatPaddingEnabled()) {
            float radius = this.mShadowViewDelegate.getRadius();
            float maxShadowSize = getElevation() + this.mPressedTranslationZ;
            int hPadding = (int) Math.ceil((double) ShadowDrawableWrapper.calculateHorizontalPadding(maxShadowSize, radius, false));
            int vPadding = (int) Math.ceil((double) ShadowDrawableWrapper.calculateVerticalPadding(maxShadowSize, radius, false));
            rect.set(hPadding, vPadding, hPadding, vPadding);
            return;
        }
        rect.set(0, 0, 0, 0);
    }
}
