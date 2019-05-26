package android.support.v7.widget;

import android.os.SystemClock;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v7.view.menu.ShowableListMenu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewParent;

@RestrictTo({Scope.LIBRARY_GROUP})
public abstract class ForwardingListener implements OnTouchListener, OnAttachStateChangeListener {
    private int mActivePointerId;
    private Runnable mDisallowIntercept;
    private boolean mForwarding;
    private final int mLongPressTimeout;
    private final float mScaledTouchSlop;
    final View mSrc;
    private final int mTapTimeout;
    private final int[] mTmpLocation = new int[2];
    private Runnable mTriggerLongPress;

    private class DisallowIntercept implements Runnable {
        DisallowIntercept() {
        }

        public void run() {
            ViewParent parent = ForwardingListener.this.mSrc.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }
    }

    private class TriggerLongPress implements Runnable {
        TriggerLongPress() {
        }

        public void run() {
            ForwardingListener.this.onLongPress();
        }
    }

    public abstract ShowableListMenu getPopup();

    public ForwardingListener(View src) {
        this.mSrc = src;
        src.setLongClickable(true);
        src.addOnAttachStateChangeListener(this);
        this.mScaledTouchSlop = (float) ViewConfiguration.get(src.getContext()).getScaledTouchSlop();
        this.mTapTimeout = ViewConfiguration.getTapTimeout();
        this.mLongPressTimeout = (this.mTapTimeout + ViewConfiguration.getLongPressTimeout()) / 2;
    }

    public boolean onTouch(View v, MotionEvent event) {
        boolean z;
        MotionEvent motionEvent = event;
        boolean wasForwarding = this.mForwarding;
        if (wasForwarding) {
            if (!onTouchForwarded(motionEvent)) {
                if (onForwardingStopped()) {
                    z = false;
                }
            }
            z = true;
        } else {
            z = onTouchObserved(motionEvent) && onForwardingStarted();
            if (z) {
                long now = SystemClock.uptimeMillis();
                MotionEvent e = MotionEvent.obtain(now, now, 3, 0.0f, 0.0f, 0);
                r0.mSrc.onTouchEvent(e);
                e.recycle();
            }
        }
        r0.mForwarding = z;
        if (!z) {
            if (!wasForwarding) {
                return false;
            }
        }
        return true;
    }

    public void onViewAttachedToWindow(View v) {
    }

    public void onViewDetachedFromWindow(View v) {
        this.mForwarding = false;
        this.mActivePointerId = -1;
        if (this.mDisallowIntercept != null) {
            this.mSrc.removeCallbacks(this.mDisallowIntercept);
        }
    }

    protected boolean onForwardingStarted() {
        ShowableListMenu popup = getPopup();
        if (!(popup == null || popup.isShowing())) {
            popup.show();
        }
        return true;
    }

    protected boolean onForwardingStopped() {
        ShowableListMenu popup = getPopup();
        if (popup != null && popup.isShowing()) {
            popup.dismiss();
        }
        return true;
    }

    private boolean onTouchObserved(MotionEvent srcEvent) {
        View src = this.mSrc;
        if (!src.isEnabled()) {
            return false;
        }
        switch (srcEvent.getActionMasked()) {
            case 0:
                this.mActivePointerId = srcEvent.getPointerId(0);
                if (this.mDisallowIntercept == null) {
                    this.mDisallowIntercept = new DisallowIntercept();
                }
                src.postDelayed(this.mDisallowIntercept, (long) this.mTapTimeout);
                if (this.mTriggerLongPress == null) {
                    this.mTriggerLongPress = new TriggerLongPress();
                }
                src.postDelayed(this.mTriggerLongPress, (long) this.mLongPressTimeout);
                break;
            case 1:
            case 3:
                clearCallbacks();
                break;
            case 2:
                int activePointerIndex = srcEvent.findPointerIndex(this.mActivePointerId);
                if (activePointerIndex >= 0) {
                    if (pointInView(src, srcEvent.getX(activePointerIndex), srcEvent.getY(activePointerIndex), this.mScaledTouchSlop)) {
                        break;
                    }
                    clearCallbacks();
                    src.getParent().requestDisallowInterceptTouchEvent(true);
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    private void clearCallbacks() {
        if (this.mTriggerLongPress != null) {
            this.mSrc.removeCallbacks(this.mTriggerLongPress);
        }
        if (this.mDisallowIntercept != null) {
            this.mSrc.removeCallbacks(this.mDisallowIntercept);
        }
    }

    void onLongPress() {
        clearCallbacks();
        View src = this.mSrc;
        if (src.isEnabled()) {
            if (!src.isLongClickable()) {
                if (onForwardingStarted()) {
                    src.getParent().requestDisallowInterceptTouchEvent(true);
                    long now = SystemClock.uptimeMillis();
                    MotionEvent e = MotionEvent.obtain(now, now, 3, 0.0f, 0.0f, 0);
                    src.onTouchEvent(e);
                    e.recycle();
                    this.mForwarding = true;
                }
            }
        }
    }

    private boolean onTouchForwarded(MotionEvent srcEvent) {
        View src = this.mSrc;
        ShowableListMenu popup = getPopup();
        boolean z = false;
        if (popup != null) {
            if (popup.isShowing()) {
                DropDownListView dst = (DropDownListView) popup.getListView();
                if (dst != null) {
                    if (dst.isShown()) {
                        MotionEvent dstEvent = MotionEvent.obtainNoHistory(srcEvent);
                        toGlobalMotionEvent(src, dstEvent);
                        toLocalMotionEvent(dst, dstEvent);
                        boolean handled = dst.onForwardedEvent(dstEvent, this.mActivePointerId);
                        dstEvent.recycle();
                        int action = srcEvent.getActionMasked();
                        boolean keepForwarding = (action == 1 || action == 3) ? false : true;
                        if (handled && keepForwarding) {
                            z = true;
                        }
                        return z;
                    }
                }
                return false;
            }
        }
        return false;
    }

    private static boolean pointInView(View view, float localX, float localY, float slop) {
        return localX >= (-slop) && localY >= (-slop) && localX < ((float) (view.getRight() - view.getLeft())) + slop && localY < ((float) (view.getBottom() - view.getTop())) + slop;
    }

    private boolean toLocalMotionEvent(View view, MotionEvent event) {
        int[] loc = this.mTmpLocation;
        view.getLocationOnScreen(loc);
        event.offsetLocation((float) (-loc[0]), (float) (-loc[1]));
        return true;
    }

    private boolean toGlobalMotionEvent(View view, MotionEvent event) {
        int[] loc = this.mTmpLocation;
        view.getLocationOnScreen(loc);
        event.offsetLocation((float) loc[0], (float) loc[1]);
        return true;
    }
}
