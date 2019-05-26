package android.support.design.internal;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.transition.Transition;
import android.support.transition.TransitionValues;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Map;

@RequiresApi(14)
@RestrictTo({Scope.LIBRARY_GROUP})
public class TextScale extends Transition {
    private static final String PROPNAME_SCALE = "android:textscale:scale";

    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues.view instanceof TextView) {
            transitionValues.values.put(PROPNAME_SCALE, Float.valueOf(transitionValues.view.getScaleX()));
        }
    }

    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (!(startValues == null || endValues == null || !(startValues.view instanceof TextView))) {
            if (endValues.view instanceof TextView) {
                final TextView view = endValues.view;
                Map<String, Object> startVals = startValues.values;
                Map<String, Object> endVals = endValues.values;
                float endSize = 1.0f;
                float startSize = startVals.get(PROPNAME_SCALE) != null ? ((Float) startVals.get(PROPNAME_SCALE)).floatValue() : 1.0f;
                if (endVals.get(PROPNAME_SCALE) != null) {
                    endSize = ((Float) endVals.get(PROPNAME_SCALE)).floatValue();
                }
                if (startSize == endSize) {
                    return null;
                }
                ValueAnimator animator = ValueAnimator.ofFloat(new float[]{startSize, endSize});
                animator.addUpdateListener(new AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float animatedValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        view.setScaleX(animatedValue);
                        view.setScaleY(animatedValue);
                    }
                });
                return animator;
            }
        }
        return null;
    }
}
