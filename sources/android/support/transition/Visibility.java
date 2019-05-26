package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.transition.Transition.TransitionListener;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class Visibility extends Transition {
    public static final int MODE_IN = 1;
    public static final int MODE_OUT = 2;
    private static final String PROPNAME_PARENT = "android:visibility:parent";
    private static final String PROPNAME_SCREEN_LOCATION = "android:visibility:screenLocation";
    static final String PROPNAME_VISIBILITY = "android:visibility:visibility";
    private static final String[] sTransitionProperties = new String[]{PROPNAME_VISIBILITY, PROPNAME_PARENT};
    private int mMode = 3;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    private static class VisibilityInfo {
        ViewGroup mEndParent;
        int mEndVisibility;
        boolean mFadeIn;
        ViewGroup mStartParent;
        int mStartVisibility;
        boolean mVisibilityChange;

        private VisibilityInfo() {
        }
    }

    private static class DisappearListener extends AnimatorListenerAdapter implements TransitionListener, AnimatorPauseListenerCompat {
        boolean mCanceled = false;
        private final int mFinalVisibility;
        private boolean mLayoutSuppressed;
        private final ViewGroup mParent;
        private final boolean mSuppressLayout;
        private final View mView;

        DisappearListener(View view, int finalVisibility, boolean suppressLayout) {
            this.mView = view;
            this.mFinalVisibility = finalVisibility;
            this.mParent = (ViewGroup) view.getParent();
            this.mSuppressLayout = suppressLayout;
            suppressLayout(true);
        }

        public void onAnimationPause(Animator animation) {
            if (!this.mCanceled) {
                ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
            }
        }

        public void onAnimationResume(Animator animation) {
            if (!this.mCanceled) {
                ViewUtils.setTransitionVisibility(this.mView, 0);
            }
        }

        public void onAnimationCancel(Animator animation) {
            this.mCanceled = true;
        }

        public void onAnimationRepeat(Animator animation) {
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            hideViewWhenNotCanceled();
        }

        public void onTransitionStart(@NonNull Transition transition) {
        }

        public void onTransitionEnd(@NonNull Transition transition) {
            hideViewWhenNotCanceled();
            transition.removeListener(this);
        }

        public void onTransitionCancel(@NonNull Transition transition) {
        }

        public void onTransitionPause(@NonNull Transition transition) {
            suppressLayout(false);
        }

        public void onTransitionResume(@NonNull Transition transition) {
            suppressLayout(true);
        }

        private void hideViewWhenNotCanceled() {
            if (!this.mCanceled) {
                ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
                if (this.mParent != null) {
                    this.mParent.invalidate();
                }
            }
            suppressLayout(false);
        }

        private void suppressLayout(boolean suppress) {
            if (this.mSuppressLayout && this.mLayoutSuppressed != suppress && this.mParent != null) {
                this.mLayoutSuppressed = suppress;
                ViewGroupUtils.suppressLayout(this.mParent, suppress);
            }
        }
    }

    public Visibility(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.VISIBILITY_TRANSITION);
        int mode = TypedArrayUtils.getNamedInt(a, (XmlResourceParser) attrs, "transitionVisibilityMode", 0, 0);
        a.recycle();
        if (mode != 0) {
            setMode(mode);
        }
    }

    public void setMode(int mode) {
        if ((mode & -4) == 0) {
            this.mMode = mode;
            return;
        }
        throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
    }

    public int getMode() {
        return this.mMode;
    }

    @Nullable
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    private void captureValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_VISIBILITY, Integer.valueOf(transitionValues.view.getVisibility()));
        transitionValues.values.put(PROPNAME_PARENT, transitionValues.view.getParent());
        int[] loc = new int[2];
        transitionValues.view.getLocationOnScreen(loc);
        transitionValues.values.put(PROPNAME_SCREEN_LOCATION, loc);
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public boolean isVisible(TransitionValues values) {
        boolean z = false;
        if (values == null) {
            return false;
        }
        View parent = (View) values.values.get(PROPNAME_PARENT);
        if (((Integer) values.values.get(PROPNAME_VISIBILITY)).intValue() == 0 && parent != null) {
            z = true;
        }
        return z;
    }

    private VisibilityInfo getVisibilityChangeInfo(TransitionValues startValues, TransitionValues endValues) {
        VisibilityInfo visInfo = new VisibilityInfo();
        visInfo.mVisibilityChange = false;
        visInfo.mFadeIn = false;
        if (startValues == null || !startValues.values.containsKey(PROPNAME_VISIBILITY)) {
            visInfo.mStartVisibility = -1;
            visInfo.mStartParent = null;
        } else {
            visInfo.mStartVisibility = ((Integer) startValues.values.get(PROPNAME_VISIBILITY)).intValue();
            visInfo.mStartParent = (ViewGroup) startValues.values.get(PROPNAME_PARENT);
        }
        if (endValues == null || !endValues.values.containsKey(PROPNAME_VISIBILITY)) {
            visInfo.mEndVisibility = -1;
            visInfo.mEndParent = null;
        } else {
            visInfo.mEndVisibility = ((Integer) endValues.values.get(PROPNAME_VISIBILITY)).intValue();
            visInfo.mEndParent = (ViewGroup) endValues.values.get(PROPNAME_PARENT);
        }
        if (startValues == null || endValues == null) {
            if (startValues == null && visInfo.mEndVisibility == 0) {
                visInfo.mFadeIn = true;
                visInfo.mVisibilityChange = true;
            } else if (endValues == null && visInfo.mStartVisibility == 0) {
                visInfo.mFadeIn = false;
                visInfo.mVisibilityChange = true;
            }
        } else if (visInfo.mStartVisibility == visInfo.mEndVisibility && visInfo.mStartParent == visInfo.mEndParent) {
            return visInfo;
        } else {
            if (visInfo.mStartVisibility != visInfo.mEndVisibility) {
                if (visInfo.mStartVisibility == 0) {
                    visInfo.mFadeIn = false;
                    visInfo.mVisibilityChange = true;
                } else if (visInfo.mEndVisibility == 0) {
                    visInfo.mFadeIn = true;
                    visInfo.mVisibilityChange = true;
                }
            } else if (visInfo.mEndParent == null) {
                visInfo.mFadeIn = false;
                visInfo.mVisibilityChange = true;
            } else if (visInfo.mStartParent == null) {
                visInfo.mFadeIn = true;
                visInfo.mVisibilityChange = true;
            }
        }
        return visInfo;
    }

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        VisibilityInfo visInfo = getVisibilityChangeInfo(startValues, endValues);
        if (!visInfo.mVisibilityChange || (visInfo.mStartParent == null && visInfo.mEndParent == null)) {
            return null;
        }
        if (visInfo.mFadeIn) {
            return onAppear(sceneRoot, startValues, visInfo.mStartVisibility, endValues, visInfo.mEndVisibility);
        }
        return onDisappear(sceneRoot, startValues, visInfo.mStartVisibility, endValues, visInfo.mEndVisibility);
    }

    public Animator onAppear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
        if ((this.mMode & 1) == 1) {
            if (endValues != null) {
                if (startValues == null) {
                    View endParent = (View) endValues.view.getParent();
                    if (getVisibilityChangeInfo(getMatchedTransitionValues(endParent, false), getTransitionValues(endParent, false)).mVisibilityChange) {
                        return null;
                    }
                }
                return onAppear(sceneRoot, endValues.view, startValues, endValues);
            }
        }
        return null;
    }

    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        return null;
    }

    public Animator onDisappear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
        ViewGroup viewGroup = sceneRoot;
        TransitionValues transitionValues = startValues;
        TransitionValues transitionValues2 = endValues;
        if ((this.mMode & 2) != 2) {
            return null;
        }
        int finalVisibility;
        View view;
        int originalVisibility;
        Animator animator;
        View startView = transitionValues != null ? transitionValues.view : null;
        View endView = transitionValues2 != null ? transitionValues2.view : null;
        View overlayView = null;
        View viewToKeep = null;
        if (endView != null) {
            if (endView.getParent() != null) {
                if (endVisibility == 4) {
                    viewToKeep = endView;
                } else if (startView == endView) {
                    viewToKeep = endView;
                } else {
                    overlayView = startView;
                }
                finalVisibility = endVisibility;
                if (overlayView != null || transitionValues == null) {
                    view = endView;
                    if (viewToKeep != null) {
                        return null;
                    }
                    originalVisibility = viewToKeep.getVisibility();
                    ViewUtils.setTransitionVisibility(viewToKeep, 0);
                    animator = onDisappear(viewGroup, viewToKeep, transitionValues, transitionValues2);
                    if (animator == null) {
                        DisappearListener disappearListener = new DisappearListener(viewToKeep, finalVisibility, true);
                        animator.addListener(disappearListener);
                        AnimatorUtils.addPauseListener(animator, disappearListener);
                        addListener(disappearListener);
                    } else {
                        ViewUtils.setTransitionVisibility(viewToKeep, originalVisibility);
                    }
                    return animator;
                }
                int[] screenLoc = (int[]) transitionValues.values.get(PROPNAME_SCREEN_LOCATION);
                int screenX = screenLoc[0];
                int screenY = screenLoc[1];
                int[] loc = new int[2];
                viewGroup.getLocationOnScreen(loc);
                overlayView.offsetLeftAndRight((screenX - loc[0]) - overlayView.getLeft());
                overlayView.offsetTopAndBottom((screenY - loc[1]) - overlayView.getTop());
                final ViewGroupOverlayImpl overlay = ViewGroupUtils.getOverlay(sceneRoot);
                overlay.add(overlayView);
                Animator animator2 = onDisappear(viewGroup, overlayView, transitionValues, transitionValues2);
                if (animator2 == null) {
                    overlay.remove(overlayView);
                    View view2 = startView;
                    view = endView;
                } else {
                    endView = overlayView;
                    animator2.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
                            overlay.remove(endView);
                        }
                    });
                }
                return animator2;
            }
        }
        int i = endVisibility;
        if (endView != null) {
            overlayView = endView;
        } else if (startView != null) {
            if (startView.getParent() == null) {
                overlayView = startView;
            } else if (startView.getParent() instanceof View) {
                View startParent = (View) startView.getParent();
                if (!getVisibilityChangeInfo(getTransitionValues(startParent, true), getMatchedTransitionValues(startParent, true)).mVisibilityChange) {
                    overlayView = TransitionUtils.copyViewImage(viewGroup, startView, startParent);
                } else if (startParent.getParent() == null) {
                    int id = startParent.getId();
                    if (!(id == -1 || viewGroup.findViewById(id) == null || !r0.mCanRemoveViews)) {
                        overlayView = startView;
                    }
                }
            }
        }
        finalVisibility = endVisibility;
        if (overlayView != null) {
        }
        view = endView;
        if (viewToKeep != null) {
            return null;
        }
        originalVisibility = viewToKeep.getVisibility();
        ViewUtils.setTransitionVisibility(viewToKeep, 0);
        animator = onDisappear(viewGroup, viewToKeep, transitionValues, transitionValues2);
        if (animator == null) {
            ViewUtils.setTransitionVisibility(viewToKeep, originalVisibility);
        } else {
            DisappearListener disappearListener2 = new DisappearListener(viewToKeep, finalVisibility, true);
            animator.addListener(disappearListener2);
            AnimatorUtils.addPauseListener(animator, disappearListener2);
            addListener(disappearListener2);
        }
        return animator;
    }

    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        return null;
    }

    public boolean isTransitionRequired(TransitionValues startValues, TransitionValues newValues) {
        boolean z = false;
        if (startValues == null && newValues == null) {
            return false;
        }
        if (startValues != null && newValues != null && newValues.values.containsKey(PROPNAME_VISIBILITY) != startValues.values.containsKey(PROPNAME_VISIBILITY)) {
            return false;
        }
        VisibilityInfo changeInfo = getVisibilityChangeInfo(startValues, newValues);
        if (changeInfo.mVisibilityChange && (changeInfo.mStartVisibility == 0 || changeInfo.mEndVisibility == 0)) {
            z = true;
        }
        return z;
    }
}
