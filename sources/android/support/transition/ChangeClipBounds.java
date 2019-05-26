package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class ChangeClipBounds extends Transition {
    private static final String PROPNAME_BOUNDS = "android:clipBounds:bounds";
    private static final String PROPNAME_CLIP = "android:clipBounds:clip";
    private static final String[] sTransitionProperties = new String[]{PROPNAME_CLIP};

    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public ChangeClipBounds(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void captureValues(TransitionValues values) {
        View view = values.view;
        if (view.getVisibility() != 8) {
            Rect clip = ViewCompat.getClipBounds(view);
            values.values.put(PROPNAME_CLIP, clip);
            if (clip == null) {
                values.values.put(PROPNAME_BOUNDS, new Rect(0, 0, view.getWidth(), view.getHeight()));
            }
        }
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public Animator createAnimator(@NonNull ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (!(startValues == null || endValues == null || !startValues.values.containsKey(PROPNAME_CLIP))) {
            if (endValues.values.containsKey(PROPNAME_CLIP)) {
                Rect start = (Rect) startValues.values.get(PROPNAME_CLIP);
                Rect end = (Rect) endValues.values.get(PROPNAME_CLIP);
                boolean endIsNull = end == null;
                if (start == null && end == null) {
                    return null;
                }
                if (start == null) {
                    start = (Rect) startValues.values.get(PROPNAME_BOUNDS);
                } else if (end == null) {
                    end = (Rect) endValues.values.get(PROPNAME_BOUNDS);
                }
                if (start.equals(end)) {
                    return null;
                }
                ViewCompat.setClipBounds(endValues.view, start);
                ObjectAnimator animator = ObjectAnimator.ofObject(endValues.view, ViewUtils.CLIP_BOUNDS, new RectEvaluator(new Rect()), new Rect[]{start, end});
                if (endIsNull) {
                    final View endView = endValues.view;
                    animator.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
                            ViewCompat.setClipBounds(endView, null);
                        }
                    });
                }
                return animator;
            }
        }
        return null;
    }
}
