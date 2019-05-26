package android.support.design.widget;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SwipeDismissBehavior<V extends View> extends Behavior<V> {
    private static final float DEFAULT_ALPHA_END_DISTANCE = 0.5f;
    private static final float DEFAULT_ALPHA_START_DISTANCE = 0.0f;
    private static final float DEFAULT_DRAG_DISMISS_THRESHOLD = 0.5f;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    public static final int SWIPE_DIRECTION_ANY = 2;
    public static final int SWIPE_DIRECTION_END_TO_START = 1;
    public static final int SWIPE_DIRECTION_START_TO_END = 0;
    float mAlphaEndSwipeDistance = 0.5f;
    float mAlphaStartSwipeDistance = 0.0f;
    private final Callback mDragCallback = new C04491();
    float mDragDismissThreshold = 0.5f;
    private boolean mInterceptingEvents;
    OnDismissListener mListener;
    private float mSensitivity = 0.0f;
    private boolean mSensitivitySet;
    int mSwipeDirection = 2;
    ViewDragHelper mViewDragHelper;

    public interface OnDismissListener {
        void onDismiss(View view);

        void onDragStateChanged(int i);
    }

    private class SettleRunnable implements Runnable {
        private final boolean mDismiss;
        private final View mView;

        SettleRunnable(View view, boolean dismiss) {
            this.mView = view;
            this.mDismiss = dismiss;
        }

        public void run() {
            if (SwipeDismissBehavior.this.mViewDragHelper != null && SwipeDismissBehavior.this.mViewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.mView, this);
            } else if (this.mDismiss && SwipeDismissBehavior.this.mListener != null) {
                SwipeDismissBehavior.this.mListener.onDismiss(this.mView);
            }
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    private @interface SwipeDirection {
    }

    /* renamed from: android.support.design.widget.SwipeDismissBehavior$1 */
    class C04491 extends Callback {
        private static final int INVALID_POINTER_ID = -1;
        private int mActivePointerId = -1;
        private int mOriginalCapturedViewLeft;

        C04491() {
        }

        public boolean tryCaptureView(View child, int pointerId) {
            return this.mActivePointerId == -1 && SwipeDismissBehavior.this.canSwipeDismissView(child);
        }

        public void onViewCaptured(View capturedChild, int activePointerId) {
            this.mActivePointerId = activePointerId;
            this.mOriginalCapturedViewLeft = capturedChild.getLeft();
            ViewParent parent = capturedChild.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }

        public void onViewDragStateChanged(int state) {
            if (SwipeDismissBehavior.this.mListener != null) {
                SwipeDismissBehavior.this.mListener.onDragStateChanged(state);
            }
        }

        public void onViewReleased(View child, float xvel, float yvel) {
            int targetLeft;
            this.mActivePointerId = -1;
            int childWidth = child.getWidth();
            boolean dismiss = false;
            if (shouldDismiss(child, xvel)) {
                targetLeft = child.getLeft() < this.mOriginalCapturedViewLeft ? this.mOriginalCapturedViewLeft - childWidth : this.mOriginalCapturedViewLeft + childWidth;
                dismiss = true;
            } else {
                targetLeft = this.mOriginalCapturedViewLeft;
            }
            if (SwipeDismissBehavior.this.mViewDragHelper.settleCapturedViewAt(targetLeft, child.getTop())) {
                ViewCompat.postOnAnimation(child, new SettleRunnable(child, dismiss));
            } else if (dismiss && SwipeDismissBehavior.this.mListener != null) {
                SwipeDismissBehavior.this.mListener.onDismiss(child);
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean shouldDismiss(android.view.View r7, float r8) {
            /*
            r6 = this;
            r0 = 0;
            r1 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1));
            r2 = 0;
            r3 = 1;
            if (r1 == 0) goto L_0x0044;
        L_0x0007:
            r1 = android.support.v4.view.ViewCompat.getLayoutDirection(r7);
            if (r1 != r3) goto L_0x000f;
        L_0x000d:
            r1 = r3;
            goto L_0x0010;
        L_0x000f:
            r1 = r2;
        L_0x0010:
            r4 = android.support.design.widget.SwipeDismissBehavior.this;
            r4 = r4.mSwipeDirection;
            r5 = 2;
            if (r4 != r5) goto L_0x0018;
        L_0x0017:
            return r3;
        L_0x0018:
            r4 = android.support.design.widget.SwipeDismissBehavior.this;
            r4 = r4.mSwipeDirection;
            if (r4 != 0) goto L_0x002d;
        L_0x001e:
            if (r1 == 0) goto L_0x0027;
        L_0x0020:
            r0 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1));
            if (r0 >= 0) goto L_0x0026;
        L_0x0024:
            r2 = r3;
            goto L_0x002c;
        L_0x0026:
            goto L_0x002c;
        L_0x0027:
            r0 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1));
            if (r0 <= 0) goto L_0x0026;
        L_0x002b:
            goto L_0x0024;
        L_0x002c:
            return r2;
        L_0x002d:
            r4 = android.support.design.widget.SwipeDismissBehavior.this;
            r4 = r4.mSwipeDirection;
            if (r4 != r3) goto L_0x0042;
        L_0x0033:
            if (r1 == 0) goto L_0x003c;
        L_0x0035:
            r0 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1));
            if (r0 <= 0) goto L_0x003b;
        L_0x0039:
            r2 = r3;
            goto L_0x0041;
        L_0x003b:
            goto L_0x0041;
        L_0x003c:
            r0 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1));
            if (r0 >= 0) goto L_0x003b;
        L_0x0040:
            goto L_0x0039;
        L_0x0041:
            return r2;
            return r2;
        L_0x0044:
            r0 = r7.getLeft();
            r1 = r6.mOriginalCapturedViewLeft;
            r0 = r0 - r1;
            r1 = r7.getWidth();
            r1 = (float) r1;
            r4 = android.support.design.widget.SwipeDismissBehavior.this;
            r4 = r4.mDragDismissThreshold;
            r1 = r1 * r4;
            r1 = java.lang.Math.round(r1);
            r4 = java.lang.Math.abs(r0);
            if (r4 < r1) goto L_0x0061;
        L_0x005f:
            r2 = r3;
        L_0x0061:
            return r2;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.SwipeDismissBehavior.1.shouldDismiss(android.view.View, float):boolean");
        }

        public int getViewHorizontalDragRange(View child) {
            return child.getWidth();
        }

        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int min;
            int max;
            boolean isRtl = ViewCompat.getLayoutDirection(child) == 1;
            if (SwipeDismissBehavior.this.mSwipeDirection == 0) {
                if (isRtl) {
                    min = this.mOriginalCapturedViewLeft - child.getWidth();
                    max = this.mOriginalCapturedViewLeft;
                } else {
                    min = this.mOriginalCapturedViewLeft;
                    max = this.mOriginalCapturedViewLeft + child.getWidth();
                }
            } else if (SwipeDismissBehavior.this.mSwipeDirection != 1) {
                min = this.mOriginalCapturedViewLeft - child.getWidth();
                max = this.mOriginalCapturedViewLeft + child.getWidth();
            } else if (isRtl) {
                min = this.mOriginalCapturedViewLeft;
                max = this.mOriginalCapturedViewLeft + child.getWidth();
            } else {
                min = this.mOriginalCapturedViewLeft - child.getWidth();
                max = this.mOriginalCapturedViewLeft;
            }
            return SwipeDismissBehavior.clamp(min, left, max);
        }

        public int clampViewPositionVertical(View child, int top, int dy) {
            return child.getTop();
        }

        public void onViewPositionChanged(View child, int left, int top, int dx, int dy) {
            float startAlphaDistance = ((float) this.mOriginalCapturedViewLeft) + (((float) child.getWidth()) * SwipeDismissBehavior.this.mAlphaStartSwipeDistance);
            float endAlphaDistance = ((float) this.mOriginalCapturedViewLeft) + (((float) child.getWidth()) * SwipeDismissBehavior.this.mAlphaEndSwipeDistance);
            if (((float) left) <= startAlphaDistance) {
                child.setAlpha(1.0f);
            } else if (((float) left) >= endAlphaDistance) {
                child.setAlpha(0.0f);
            } else {
                child.setAlpha(SwipeDismissBehavior.clamp(0.0f, 1.0f - SwipeDismissBehavior.fraction(startAlphaDistance, endAlphaDistance, (float) left), 1.0f));
            }
        }
    }

    public void setListener(OnDismissListener listener) {
        this.mListener = listener;
    }

    public void setSwipeDirection(int direction) {
        this.mSwipeDirection = direction;
    }

    public void setDragDismissDistance(float distance) {
        this.mDragDismissThreshold = clamp(0.0f, distance, 1.0f);
    }

    public void setStartAlphaSwipeDistance(float fraction) {
        this.mAlphaStartSwipeDistance = clamp(0.0f, fraction, 1.0f);
    }

    public void setEndAlphaSwipeDistance(float fraction) {
        this.mAlphaEndSwipeDistance = clamp(0.0f, fraction, 1.0f);
    }

    public void setSensitivity(float sensitivity) {
        this.mSensitivity = sensitivity;
        this.mSensitivitySet = true;
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        boolean dispatchEventToHelper = this.mInterceptingEvents;
        int actionMasked = event.getActionMasked();
        if (actionMasked != 3) {
            switch (actionMasked) {
                case 0:
                    this.mInterceptingEvents = parent.isPointInChildBounds(child, (int) event.getX(), (int) event.getY());
                    dispatchEventToHelper = this.mInterceptingEvents;
                    break;
                case 1:
                    break;
                default:
                    break;
            }
        }
        this.mInterceptingEvents = false;
        if (!dispatchEventToHelper) {
            return false;
        }
        ensureViewDragHelper(parent);
        return this.mViewDragHelper.shouldInterceptTouchEvent(event);
    }

    public boolean onTouchEvent(CoordinatorLayout parent, V v, MotionEvent event) {
        if (this.mViewDragHelper == null) {
            return false;
        }
        this.mViewDragHelper.processTouchEvent(event);
        return true;
    }

    public boolean canSwipeDismissView(@NonNull View view) {
        return true;
    }

    private void ensureViewDragHelper(ViewGroup parent) {
        if (this.mViewDragHelper == null) {
            ViewDragHelper create;
            if (this.mSensitivitySet) {
                create = ViewDragHelper.create(parent, this.mSensitivity, this.mDragCallback);
            } else {
                create = ViewDragHelper.create(parent, this.mDragCallback);
            }
            this.mViewDragHelper = create;
        }
    }

    static float clamp(float min, float value, float max) {
        return Math.min(Math.max(min, value), max);
    }

    static int clamp(int min, int value, int max) {
        return Math.min(Math.max(min, value), max);
    }

    public int getDragState() {
        return this.mViewDragHelper != null ? this.mViewDragHelper.getViewDragState() : 0;
    }

    static float fraction(float startValue, float endValue, float value) {
        return (value - startValue) / (endValue - startValue);
    }
}
