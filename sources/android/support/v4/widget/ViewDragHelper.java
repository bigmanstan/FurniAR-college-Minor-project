package android.support.v4.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import java.util.Arrays;

public class ViewDragHelper {
    private static final int BASE_SETTLE_DURATION = 256;
    public static final int DIRECTION_ALL = 3;
    public static final int DIRECTION_HORIZONTAL = 1;
    public static final int DIRECTION_VERTICAL = 2;
    public static final int EDGE_ALL = 15;
    public static final int EDGE_BOTTOM = 8;
    public static final int EDGE_LEFT = 1;
    public static final int EDGE_RIGHT = 2;
    private static final int EDGE_SIZE = 20;
    public static final int EDGE_TOP = 4;
    public static final int INVALID_POINTER = -1;
    private static final int MAX_SETTLE_DURATION = 600;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "ViewDragHelper";
    private static final Interpolator sInterpolator = new C02601();
    private int mActivePointerId = -1;
    private final Callback mCallback;
    private View mCapturedView;
    private int mDragState;
    private int[] mEdgeDragsInProgress;
    private int[] mEdgeDragsLocked;
    private int mEdgeSize;
    private int[] mInitialEdgesTouched;
    private float[] mInitialMotionX;
    private float[] mInitialMotionY;
    private float[] mLastMotionX;
    private float[] mLastMotionY;
    private float mMaxVelocity;
    private float mMinVelocity;
    private final ViewGroup mParentView;
    private int mPointersDown;
    private boolean mReleaseInProgress;
    private OverScroller mScroller;
    private final Runnable mSetIdleRunnable = new C02612();
    private int mTouchSlop;
    private int mTrackingEdges;
    private VelocityTracker mVelocityTracker;

    /* renamed from: android.support.v4.widget.ViewDragHelper$1 */
    static class C02601 implements Interpolator {
        C02601() {
        }

        public float getInterpolation(float t) {
            t -= 1.0f;
            return ((((t * t) * t) * t) * t) + 1.0f;
        }
    }

    /* renamed from: android.support.v4.widget.ViewDragHelper$2 */
    class C02612 implements Runnable {
        C02612() {
        }

        public void run() {
            ViewDragHelper.this.setDragState(0);
        }
    }

    public static abstract class Callback {
        public abstract boolean tryCaptureView(@NonNull View view, int i);

        public void onViewDragStateChanged(int state) {
        }

        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
        }

        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
        }

        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
        }

        public void onEdgeTouched(int edgeFlags, int pointerId) {
        }

        public boolean onEdgeLock(int edgeFlags) {
            return false;
        }

        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
        }

        public int getOrderedChildIndex(int index) {
            return index;
        }

        public int getViewHorizontalDragRange(@NonNull View child) {
            return 0;
        }

        public int getViewVerticalDragRange(@NonNull View child) {
            return 0;
        }

        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return 0;
        }

        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return 0;
        }
    }

    public static ViewDragHelper create(@NonNull ViewGroup forParent, @NonNull Callback cb) {
        return new ViewDragHelper(forParent.getContext(), forParent, cb);
    }

    public static ViewDragHelper create(@NonNull ViewGroup forParent, float sensitivity, @NonNull Callback cb) {
        ViewDragHelper helper = create(forParent, cb);
        helper.mTouchSlop = (int) (((float) helper.mTouchSlop) * (1.0f / sensitivity));
        return helper;
    }

    private ViewDragHelper(@NonNull Context context, @NonNull ViewGroup forParent, @NonNull Callback cb) {
        if (forParent == null) {
            throw new IllegalArgumentException("Parent view may not be null");
        } else if (cb != null) {
            this.mParentView = forParent;
            this.mCallback = cb;
            ViewConfiguration vc = ViewConfiguration.get(context);
            this.mEdgeSize = (int) ((20.0f * context.getResources().getDisplayMetrics().density) + 0.5f);
            this.mTouchSlop = vc.getScaledTouchSlop();
            this.mMaxVelocity = (float) vc.getScaledMaximumFlingVelocity();
            this.mMinVelocity = (float) vc.getScaledMinimumFlingVelocity();
            this.mScroller = new OverScroller(context, sInterpolator);
        } else {
            throw new IllegalArgumentException("Callback may not be null");
        }
    }

    public void setMinVelocity(float minVel) {
        this.mMinVelocity = minVel;
    }

    public float getMinVelocity() {
        return this.mMinVelocity;
    }

    public int getViewDragState() {
        return this.mDragState;
    }

    public void setEdgeTrackingEnabled(int edgeFlags) {
        this.mTrackingEdges = edgeFlags;
    }

    public int getEdgeSize() {
        return this.mEdgeSize;
    }

    public void captureChildView(@NonNull View childView, int activePointerId) {
        if (childView.getParent() == this.mParentView) {
            this.mCapturedView = childView;
            this.mActivePointerId = activePointerId;
            this.mCallback.onViewCaptured(childView, activePointerId);
            setDragState(1);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (");
        stringBuilder.append(this.mParentView);
        stringBuilder.append(")");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Nullable
    public View getCapturedView() {
        return this.mCapturedView;
    }

    public int getActivePointerId() {
        return this.mActivePointerId;
    }

    public int getTouchSlop() {
        return this.mTouchSlop;
    }

    public void cancel() {
        this.mActivePointerId = -1;
        clearMotionHistory();
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public void abort() {
        cancel();
        if (this.mDragState == 2) {
            int oldX = this.mScroller.getCurrX();
            int oldY = this.mScroller.getCurrY();
            this.mScroller.abortAnimation();
            int newX = this.mScroller.getCurrX();
            int newY = this.mScroller.getCurrY();
            this.mCallback.onViewPositionChanged(this.mCapturedView, newX, newY, newX - oldX, newY - oldY);
        }
        setDragState(0);
    }

    public boolean smoothSlideViewTo(@NonNull View child, int finalLeft, int finalTop) {
        this.mCapturedView = child;
        this.mActivePointerId = -1;
        boolean continueSliding = forceSettleCapturedViewAt(finalLeft, finalTop, 0, 0);
        if (!(continueSliding || this.mDragState != 0 || this.mCapturedView == null)) {
            this.mCapturedView = null;
        }
        return continueSliding;
    }

    public boolean settleCapturedViewAt(int finalLeft, int finalTop) {
        if (this.mReleaseInProgress) {
            return forceSettleCapturedViewAt(finalLeft, finalTop, (int) this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int) this.mVelocityTracker.getYVelocity(this.mActivePointerId));
        }
        throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
    }

    private boolean forceSettleCapturedViewAt(int finalLeft, int finalTop, int xvel, int yvel) {
        int startLeft = this.mCapturedView.getLeft();
        int startTop = this.mCapturedView.getTop();
        int dx = finalLeft - startLeft;
        int dy = finalTop - startTop;
        if (dx == 0 && dy == 0) {
            this.mScroller.abortAnimation();
            setDragState(0);
            return false;
        }
        this.mScroller.startScroll(startLeft, startTop, dx, dy, computeSettleDuration(this.mCapturedView, dx, dy, xvel, yvel));
        setDragState(2);
        return true;
    }

    private int computeSettleDuration(View child, int dx, int dy, int xvel, int yvel) {
        float f;
        float f2;
        float f3;
        View view = child;
        int xvel2 = clampMag(xvel, (int) this.mMinVelocity, (int) this.mMaxVelocity);
        int yvel2 = clampMag(yvel, (int) this.mMinVelocity, (int) this.mMaxVelocity);
        int absDx = Math.abs(dx);
        int absDy = Math.abs(dy);
        int absXVel = Math.abs(xvel2);
        int absYVel = Math.abs(yvel2);
        int addedVel = absXVel + absYVel;
        int addedDistance = absDx + absDy;
        if (xvel2 != 0) {
            f = (float) absXVel;
            f2 = (float) addedVel;
        } else {
            f = (float) absDx;
            f2 = (float) addedDistance;
        }
        f /= f2;
        if (yvel2 != 0) {
            f2 = (float) absYVel;
            f3 = (float) addedVel;
        } else {
            f2 = (float) absDy;
            f3 = (float) addedDistance;
        }
        f2 /= f3;
        return (int) ((((float) computeAxisDuration(dx, xvel2, r0.mCallback.getViewHorizontalDragRange(view))) * f) + (((float) computeAxisDuration(dy, yvel2, r0.mCallback.getViewVerticalDragRange(view))) * f2));
    }

    private int computeAxisDuration(int delta, int velocity, int motionRange) {
        if (delta == 0) {
            return 0;
        }
        int duration;
        int width = this.mParentView.getWidth();
        int halfWidth = width / 2;
        float distance = ((float) halfWidth) + (((float) halfWidth) * distanceInfluenceForSnapDuration(Math.min(1.0f, ((float) Math.abs(delta)) / ((float) width))));
        velocity = Math.abs(velocity);
        if (velocity > 0) {
            duration = Math.round(Math.abs(distance / ((float) velocity)) * 1000.0f) * 4;
        } else {
            duration = (int) ((1.0f + (((float) Math.abs(delta)) / ((float) motionRange))) * 1132462080);
        }
        return Math.min(duration, MAX_SETTLE_DURATION);
    }

    private int clampMag(int value, int absMin, int absMax) {
        int absValue = Math.abs(value);
        if (absValue < absMin) {
            return 0;
        }
        if (absValue <= absMax) {
            return value;
        }
        return value > 0 ? absMax : -absMax;
    }

    private float clampMag(float value, float absMin, float absMax) {
        float absValue = Math.abs(value);
        if (absValue < absMin) {
            return 0.0f;
        }
        if (absValue <= absMax) {
            return value;
        }
        return value > 0.0f ? absMax : -absMax;
    }

    private float distanceInfluenceForSnapDuration(float f) {
        return (float) Math.sin((double) ((f - 0.5f) * 0.47123894f));
    }

    public void flingCapturedView(int minLeft, int minTop, int maxLeft, int maxTop) {
        if (this.mReleaseInProgress) {
            this.mScroller.fling(this.mCapturedView.getLeft(), this.mCapturedView.getTop(), (int) this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int) this.mVelocityTracker.getYVelocity(this.mActivePointerId), minLeft, maxLeft, minTop, maxTop);
            setDragState(2);
            return;
        }
        throw new IllegalStateException("Cannot flingCapturedView outside of a call to Callback#onViewReleased");
    }

    public boolean continueSettling(boolean deferCallbacks) {
        if (this.mDragState == 2) {
            boolean keepGoing = this.mScroller.computeScrollOffset();
            int x = this.mScroller.getCurrX();
            int y = this.mScroller.getCurrY();
            int dx = x - this.mCapturedView.getLeft();
            int dy = y - this.mCapturedView.getTop();
            if (dx != 0) {
                ViewCompat.offsetLeftAndRight(this.mCapturedView, dx);
            }
            if (dy != 0) {
                ViewCompat.offsetTopAndBottom(this.mCapturedView, dy);
            }
            if (!(dx == 0 && dy == 0)) {
                this.mCallback.onViewPositionChanged(this.mCapturedView, x, y, dx, dy);
            }
            if (keepGoing && x == this.mScroller.getFinalX() && y == this.mScroller.getFinalY()) {
                this.mScroller.abortAnimation();
                keepGoing = false;
            }
            if (!keepGoing) {
                if (deferCallbacks) {
                    this.mParentView.post(this.mSetIdleRunnable);
                } else {
                    setDragState(0);
                }
            }
        }
        if (this.mDragState == 2) {
            return true;
        }
        return false;
    }

    private void dispatchViewReleased(float xvel, float yvel) {
        this.mReleaseInProgress = true;
        this.mCallback.onViewReleased(this.mCapturedView, xvel, yvel);
        this.mReleaseInProgress = false;
        if (this.mDragState == 1) {
            setDragState(0);
        }
    }

    private void clearMotionHistory() {
        if (this.mInitialMotionX != null) {
            Arrays.fill(this.mInitialMotionX, 0.0f);
            Arrays.fill(this.mInitialMotionY, 0.0f);
            Arrays.fill(this.mLastMotionX, 0.0f);
            Arrays.fill(this.mLastMotionY, 0.0f);
            Arrays.fill(this.mInitialEdgesTouched, 0);
            Arrays.fill(this.mEdgeDragsInProgress, 0);
            Arrays.fill(this.mEdgeDragsLocked, 0);
            this.mPointersDown = 0;
        }
    }

    private void clearMotionHistory(int pointerId) {
        if (this.mInitialMotionX != null) {
            if (isPointerDown(pointerId)) {
                this.mInitialMotionX[pointerId] = 0.0f;
                this.mInitialMotionY[pointerId] = 0.0f;
                this.mLastMotionX[pointerId] = 0.0f;
                this.mLastMotionY[pointerId] = 0.0f;
                this.mInitialEdgesTouched[pointerId] = 0;
                this.mEdgeDragsInProgress[pointerId] = 0;
                this.mEdgeDragsLocked[pointerId] = 0;
                this.mPointersDown &= ~(1 << pointerId);
            }
        }
    }

    private void ensureMotionHistorySizeForId(int pointerId) {
        if (this.mInitialMotionX == null || this.mInitialMotionX.length <= pointerId) {
            float[] imx = new float[(pointerId + 1)];
            float[] imy = new float[(pointerId + 1)];
            float[] lmx = new float[(pointerId + 1)];
            float[] lmy = new float[(pointerId + 1)];
            int[] iit = new int[(pointerId + 1)];
            int[] edip = new int[(pointerId + 1)];
            int[] edl = new int[(pointerId + 1)];
            if (this.mInitialMotionX != null) {
                System.arraycopy(this.mInitialMotionX, 0, imx, 0, this.mInitialMotionX.length);
                System.arraycopy(this.mInitialMotionY, 0, imy, 0, this.mInitialMotionY.length);
                System.arraycopy(this.mLastMotionX, 0, lmx, 0, this.mLastMotionX.length);
                System.arraycopy(this.mLastMotionY, 0, lmy, 0, this.mLastMotionY.length);
                System.arraycopy(this.mInitialEdgesTouched, 0, iit, 0, this.mInitialEdgesTouched.length);
                System.arraycopy(this.mEdgeDragsInProgress, 0, edip, 0, this.mEdgeDragsInProgress.length);
                System.arraycopy(this.mEdgeDragsLocked, 0, edl, 0, this.mEdgeDragsLocked.length);
            }
            this.mInitialMotionX = imx;
            this.mInitialMotionY = imy;
            this.mLastMotionX = lmx;
            this.mLastMotionY = lmy;
            this.mInitialEdgesTouched = iit;
            this.mEdgeDragsInProgress = edip;
            this.mEdgeDragsLocked = edl;
        }
    }

    private void saveInitialMotion(float x, float y, int pointerId) {
        ensureMotionHistorySizeForId(pointerId);
        float[] fArr = this.mInitialMotionX;
        this.mLastMotionX[pointerId] = x;
        fArr[pointerId] = x;
        fArr = this.mInitialMotionY;
        this.mLastMotionY[pointerId] = y;
        fArr[pointerId] = y;
        this.mInitialEdgesTouched[pointerId] = getEdgesTouched((int) x, (int) y);
        this.mPointersDown |= 1 << pointerId;
    }

    private void saveLastMotion(MotionEvent ev) {
        int pointerCount = ev.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            int pointerId = ev.getPointerId(i);
            if (isValidPointerForActionMove(pointerId)) {
                float x = ev.getX(i);
                float y = ev.getY(i);
                this.mLastMotionX[pointerId] = x;
                this.mLastMotionY[pointerId] = y;
            }
        }
    }

    public boolean isPointerDown(int pointerId) {
        return (this.mPointersDown & (1 << pointerId)) != 0;
    }

    void setDragState(int state) {
        this.mParentView.removeCallbacks(this.mSetIdleRunnable);
        if (this.mDragState != state) {
            this.mDragState = state;
            this.mCallback.onViewDragStateChanged(state);
            if (this.mDragState == 0) {
                this.mCapturedView = null;
            }
        }
    }

    boolean tryCaptureViewForDrag(View toCapture, int pointerId) {
        if (toCapture == this.mCapturedView && this.mActivePointerId == pointerId) {
            return true;
        }
        if (toCapture == null || !this.mCallback.tryCaptureView(toCapture, pointerId)) {
            return false;
        }
        this.mActivePointerId = pointerId;
        captureChildView(toCapture, pointerId);
        return true;
    }

    protected boolean canScroll(@NonNull View v, boolean checkV, int dx, int dy, int x, int y) {
        View view = v;
        boolean z = true;
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int scrollX = v.getScrollX();
            int scrollY = v.getScrollY();
            for (int i = group.getChildCount() - 1; i >= 0; i--) {
                View child = group.getChildAt(i);
                if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight() && y + scrollY >= child.getTop() && y + scrollY < child.getBottom()) {
                    if (canScroll(child, true, dx, dy, (x + scrollX) - child.getLeft(), (y + scrollY) - child.getTop())) {
                        return true;
                    }
                }
            }
        }
        if (checkV) {
            if (view.canScrollHorizontally(-dx)) {
                scrollY = dy;
            } else if (view.canScrollVertically(-dy)) {
            }
            return z;
        }
        scrollX = dx;
        scrollY = dy;
        z = false;
        return z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean shouldInterceptTouchEvent(@android.support.annotation.NonNull android.view.MotionEvent r22) {
        /*
        r21 = this;
        r0 = r21;
        r1 = r22;
        r2 = r22.getActionMasked();
        r3 = r22.getActionIndex();
        if (r2 != 0) goto L_0x0011;
    L_0x000e:
        r21.cancel();
    L_0x0011:
        r4 = r0.mVelocityTracker;
        if (r4 != 0) goto L_0x001b;
    L_0x0015:
        r4 = android.view.VelocityTracker.obtain();
        r0.mVelocityTracker = r4;
    L_0x001b:
        r4 = r0.mVelocityTracker;
        r4.addMovement(r1);
        r4 = 2;
        switch(r2) {
            case 0: goto L_0x012b;
            case 1: goto L_0x0122;
            case 2: goto L_0x0070;
            case 3: goto L_0x0122;
            case 4: goto L_0x0024;
            case 5: goto L_0x0039;
            case 6: goto L_0x002b;
            default: goto L_0x0024;
        };
    L_0x0024:
        r16 = r2;
        r17 = r3;
        r5 = 0;
        goto L_0x0161;
    L_0x002b:
        r4 = r1.getPointerId(r3);
        r0.clearMotionHistory(r4);
    L_0x0033:
        r16 = r2;
        r17 = r3;
        goto L_0x0129;
    L_0x0039:
        r7 = r1.getPointerId(r3);
        r8 = r1.getX(r3);
        r9 = r1.getY(r3);
        r0.saveInitialMotion(r8, r9, r7);
        r10 = r0.mDragState;
        if (r10 != 0) goto L_0x005e;
    L_0x004c:
        r4 = r0.mInitialEdgesTouched;
        r4 = r4[r7];
        r10 = r0.mTrackingEdges;
        r10 = r10 & r4;
        if (r10 == 0) goto L_0x005d;
    L_0x0055:
        r10 = r0.mCallback;
        r11 = r0.mTrackingEdges;
        r11 = r11 & r4;
        r10.onEdgeTouched(r11, r7);
    L_0x005d:
        goto L_0x0033;
    L_0x005e:
        r10 = r0.mDragState;
        if (r10 != r4) goto L_0x0033;
    L_0x0062:
        r4 = (int) r8;
        r10 = (int) r9;
        r4 = r0.findTopChildUnder(r4, r10);
        r10 = r0.mCapturedView;
        if (r4 != r10) goto L_0x006f;
    L_0x006c:
        r0.tryCaptureViewForDrag(r4, r7);
    L_0x006f:
        goto L_0x0033;
    L_0x0070:
        r4 = r0.mInitialMotionX;
        if (r4 == 0) goto L_0x0024;
    L_0x0074:
        r4 = r0.mInitialMotionY;
        if (r4 != 0) goto L_0x0079;
    L_0x0078:
        goto L_0x0033;
    L_0x0079:
        r4 = r22.getPointerCount();
        r7 = 0;
    L_0x007e:
        if (r7 >= r4) goto L_0x0118;
    L_0x0080:
        r8 = r1.getPointerId(r7);
        r9 = r0.isValidPointerForActionMove(r8);
        if (r9 != 0) goto L_0x0092;
    L_0x008a:
        r16 = r2;
        r17 = r3;
        r18 = r4;
        goto L_0x010e;
    L_0x0092:
        r9 = r1.getX(r7);
        r10 = r1.getY(r7);
        r11 = r0.mInitialMotionX;
        r11 = r11[r8];
        r11 = r9 - r11;
        r12 = r0.mInitialMotionY;
        r12 = r12[r8];
        r12 = r10 - r12;
        r13 = (int) r9;
        r14 = (int) r10;
        r13 = r0.findTopChildUnder(r13, r14);
        if (r13 == 0) goto L_0x00b6;
    L_0x00ae:
        r14 = r0.checkTouchSlop(r13, r11, r12);
        if (r14 == 0) goto L_0x00b6;
    L_0x00b4:
        r14 = 1;
        goto L_0x00b7;
    L_0x00b6:
        r14 = 0;
    L_0x00b7:
        if (r14 == 0) goto L_0x00f6;
    L_0x00b9:
        r15 = r13.getLeft();
        r5 = (int) r11;
        r5 = r5 + r15;
        r6 = r0.mCallback;
        r16 = r2;
        r2 = (int) r11;
        r2 = r6.clampViewPositionHorizontal(r13, r5, r2);
        r6 = r13.getTop();
        r17 = r3;
        r3 = (int) r12;
        r3 = r3 + r6;
        r18 = r4;
        r4 = r0.mCallback;
        r19 = r5;
        r5 = (int) r12;
        r4 = r4.clampViewPositionVertical(r13, r3, r5);
        r5 = r0.mCallback;
        r5 = r5.getViewHorizontalDragRange(r13);
        r20 = r3;
        r3 = r0.mCallback;
        r3 = r3.getViewVerticalDragRange(r13);
        if (r5 == 0) goto L_0x00ef;
    L_0x00eb:
        if (r5 <= 0) goto L_0x00fc;
    L_0x00ed:
        if (r2 != r15) goto L_0x00fc;
    L_0x00ef:
        if (r3 == 0) goto L_0x011e;
    L_0x00f1:
        if (r3 <= 0) goto L_0x00fc;
    L_0x00f3:
        if (r4 != r6) goto L_0x00fc;
    L_0x00f5:
        goto L_0x011e;
    L_0x00f6:
        r16 = r2;
        r17 = r3;
        r18 = r4;
    L_0x00fc:
        r0.reportNewEdgeDrags(r11, r12, r8);
        r2 = r0.mDragState;
        r3 = 1;
        if (r2 != r3) goto L_0x0105;
    L_0x0104:
        goto L_0x011e;
    L_0x0105:
        if (r14 == 0) goto L_0x010e;
    L_0x0107:
        r2 = r0.tryCaptureViewForDrag(r13, r8);
        if (r2 == 0) goto L_0x010e;
    L_0x010d:
        goto L_0x011e;
    L_0x010e:
        r7 = r7 + 1;
        r2 = r16;
        r3 = r17;
        r4 = r18;
        goto L_0x007e;
    L_0x0118:
        r16 = r2;
        r17 = r3;
        r18 = r4;
    L_0x011e:
        r21.saveLastMotion(r22);
        goto L_0x0129;
    L_0x0122:
        r16 = r2;
        r17 = r3;
        r21.cancel();
    L_0x0129:
        r5 = 0;
        goto L_0x0161;
    L_0x012b:
        r16 = r2;
        r17 = r3;
        r2 = r22.getX();
        r3 = r22.getY();
        r5 = 0;
        r6 = r1.getPointerId(r5);
        r0.saveInitialMotion(r2, r3, r6);
        r7 = (int) r2;
        r8 = (int) r3;
        r7 = r0.findTopChildUnder(r7, r8);
        r8 = r0.mCapturedView;
        if (r7 != r8) goto L_0x0150;
    L_0x0149:
        r8 = r0.mDragState;
        if (r8 != r4) goto L_0x0150;
    L_0x014d:
        r0.tryCaptureViewForDrag(r7, r6);
    L_0x0150:
        r4 = r0.mInitialEdgesTouched;
        r4 = r4[r6];
        r8 = r0.mTrackingEdges;
        r8 = r8 & r4;
        if (r8 == 0) goto L_0x0161;
    L_0x0159:
        r8 = r0.mCallback;
        r9 = r0.mTrackingEdges;
        r9 = r9 & r4;
        r8.onEdgeTouched(r9, r6);
    L_0x0161:
        r2 = r0.mDragState;
        r3 = 1;
        if (r2 != r3) goto L_0x0167;
    L_0x0166:
        goto L_0x0168;
    L_0x0167:
        r3 = r5;
    L_0x0168:
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.ViewDragHelper.shouldInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public void processTouchEvent(@NonNull MotionEvent ev) {
        int action = ev.getActionMasked();
        int actionIndex = ev.getActionIndex();
        if (action == 0) {
            cancel();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        int i = 0;
        float x;
        float y;
        int edgesTouched;
        int pointerCount;
        int pointerId;
        switch (action) {
            case 0:
                x = ev.getX();
                y = ev.getY();
                i = ev.getPointerId(0);
                View toCapture = findTopChildUnder((int) x, (int) y);
                saveInitialMotion(x, y, i);
                tryCaptureViewForDrag(toCapture, i);
                edgesTouched = this.mInitialEdgesTouched[i];
                if ((this.mTrackingEdges & edgesTouched) != 0) {
                    this.mCallback.onEdgeTouched(this.mTrackingEdges & edgesTouched, i);
                    return;
                }
                return;
            case 1:
                if (this.mDragState == 1) {
                    releaseViewForPointerUp();
                }
                cancel();
                return;
            case 2:
                if (this.mDragState != 1) {
                    pointerCount = ev.getPointerCount();
                    while (i < pointerCount) {
                        pointerId = ev.getPointerId(i);
                        if (isValidPointerForActionMove(pointerId)) {
                            float x2 = ev.getX(i);
                            float y2 = ev.getY(i);
                            float dx = x2 - this.mInitialMotionX[pointerId];
                            float dy = y2 - this.mInitialMotionY[pointerId];
                            reportNewEdgeDrags(dx, dy, pointerId);
                            if (this.mDragState != 1) {
                                View toCapture2 = findTopChildUnder((int) x2, (int) y2);
                                if (checkTouchSlop(toCapture2, dx, dy) && tryCaptureViewForDrag(toCapture2, pointerId)) {
                                }
                            }
                            saveLastMotion(ev);
                            return;
                        }
                        i++;
                    }
                    saveLastMotion(ev);
                    return;
                } else if (isValidPointerForActionMove(this.mActivePointerId)) {
                    i = ev.findPointerIndex(this.mActivePointerId);
                    x = ev.getX(i);
                    pointerId = (int) (x - this.mLastMotionX[this.mActivePointerId]);
                    edgesTouched = (int) (ev.getY(i) - this.mLastMotionY[this.mActivePointerId]);
                    dragTo(this.mCapturedView.getLeft() + pointerId, this.mCapturedView.getTop() + edgesTouched, pointerId, edgesTouched);
                    saveLastMotion(ev);
                    return;
                } else {
                    return;
                }
            case 3:
                if (this.mDragState == 1) {
                    dispatchViewReleased(0.0f, 0.0f);
                }
                cancel();
                return;
            case 5:
                i = ev.getPointerId(actionIndex);
                x = ev.getX(actionIndex);
                y = ev.getY(actionIndex);
                saveInitialMotion(x, y, i);
                if (this.mDragState == 0) {
                    tryCaptureViewForDrag(findTopChildUnder((int) x, (int) y), i);
                    edgesTouched = this.mInitialEdgesTouched[i];
                    if ((this.mTrackingEdges & edgesTouched) != 0) {
                        this.mCallback.onEdgeTouched(this.mTrackingEdges & edgesTouched, i);
                    }
                    return;
                } else if (isCapturedViewUnder((int) x, (int) y)) {
                    tryCaptureViewForDrag(this.mCapturedView, i);
                    return;
                } else {
                    return;
                }
            case 6:
                pointerCount = ev.getPointerId(actionIndex);
                if (this.mDragState == 1 && pointerCount == this.mActivePointerId) {
                    int newActivePointer = -1;
                    pointerId = ev.getPointerCount();
                    while (i < pointerId) {
                        edgesTouched = ev.getPointerId(i);
                        if (edgesTouched != this.mActivePointerId) {
                            if (findTopChildUnder((int) ev.getX(i), (int) ev.getY(i)) == this.mCapturedView && tryCaptureViewForDrag(this.mCapturedView, edgesTouched)) {
                                newActivePointer = this.mActivePointerId;
                                if (newActivePointer == -1) {
                                    releaseViewForPointerUp();
                                }
                            }
                        }
                        i++;
                    }
                    if (newActivePointer == -1) {
                        releaseViewForPointerUp();
                    }
                }
                clearMotionHistory(pointerCount);
                return;
            default:
                return;
        }
    }

    private void reportNewEdgeDrags(float dx, float dy, int pointerId) {
        int dragsStarted = 0;
        if (checkNewEdgeDrag(dx, dy, pointerId, 1)) {
            dragsStarted = 0 | 1;
        }
        if (checkNewEdgeDrag(dy, dx, pointerId, 4)) {
            dragsStarted |= 4;
        }
        if (checkNewEdgeDrag(dx, dy, pointerId, 2)) {
            dragsStarted |= 2;
        }
        if (checkNewEdgeDrag(dy, dx, pointerId, 8)) {
            dragsStarted |= 8;
        }
        if (dragsStarted != 0) {
            int[] iArr = this.mEdgeDragsInProgress;
            iArr[pointerId] = iArr[pointerId] | dragsStarted;
            this.mCallback.onEdgeDragStarted(dragsStarted, pointerId);
        }
    }

    private boolean checkNewEdgeDrag(float delta, float odelta, int pointerId, int edge) {
        float absDelta = Math.abs(delta);
        float absODelta = Math.abs(odelta);
        boolean z = false;
        if (!((this.mInitialEdgesTouched[pointerId] & edge) != edge || (this.mTrackingEdges & edge) == 0 || (this.mEdgeDragsLocked[pointerId] & edge) == edge || (this.mEdgeDragsInProgress[pointerId] & edge) == edge)) {
            if (absDelta > ((float) this.mTouchSlop) || absODelta > ((float) this.mTouchSlop)) {
                if (absDelta >= 0.5f * absODelta || !this.mCallback.onEdgeLock(edge)) {
                    if ((this.mEdgeDragsInProgress[pointerId] & edge) == 0 && absDelta > ((float) this.mTouchSlop)) {
                        z = true;
                    }
                    return z;
                }
                int[] iArr = this.mEdgeDragsLocked;
                iArr[pointerId] = iArr[pointerId] | edge;
                return false;
            }
        }
        return false;
    }

    private boolean checkTouchSlop(View child, float dx, float dy) {
        boolean z = false;
        if (child == null) {
            return false;
        }
        boolean checkHorizontal = this.mCallback.getViewHorizontalDragRange(child) > 0;
        boolean checkVertical = this.mCallback.getViewVerticalDragRange(child) > 0;
        if (checkHorizontal && checkVertical) {
            if ((dx * dx) + (dy * dy) > ((float) (this.mTouchSlop * this.mTouchSlop))) {
                z = true;
            }
            return z;
        } else if (checkHorizontal) {
            if (Math.abs(dx) > ((float) this.mTouchSlop)) {
                z = true;
            }
            return z;
        } else if (!checkVertical) {
            return false;
        } else {
            if (Math.abs(dy) > ((float) this.mTouchSlop)) {
                z = true;
            }
            return z;
        }
    }

    public boolean checkTouchSlop(int directions) {
        int count = this.mInitialMotionX.length;
        for (int i = 0; i < count; i++) {
            if (checkTouchSlop(directions, i)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTouchSlop(int directions, int pointerId) {
        boolean z = false;
        if (!isPointerDown(pointerId)) {
            return false;
        }
        boolean checkHorizontal = (directions & 1) == 1;
        boolean checkVertical = (directions & 2) == 2;
        float dx = this.mLastMotionX[pointerId] - this.mInitialMotionX[pointerId];
        float dy = this.mLastMotionY[pointerId] - this.mInitialMotionY[pointerId];
        if (checkHorizontal && checkVertical) {
            if ((dx * dx) + (dy * dy) > ((float) (this.mTouchSlop * this.mTouchSlop))) {
                z = true;
            }
            return z;
        } else if (checkHorizontal) {
            if (Math.abs(dx) > ((float) this.mTouchSlop)) {
                z = true;
            }
            return z;
        } else if (!checkVertical) {
            return false;
        } else {
            if (Math.abs(dy) > ((float) this.mTouchSlop)) {
                z = true;
            }
            return z;
        }
    }

    public boolean isEdgeTouched(int edges) {
        int count = this.mInitialEdgesTouched.length;
        for (int i = 0; i < count; i++) {
            if (isEdgeTouched(edges, i)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEdgeTouched(int edges, int pointerId) {
        return isPointerDown(pointerId) && (this.mInitialEdgesTouched[pointerId] & edges) != 0;
    }

    private void releaseViewForPointerUp() {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
        dispatchViewReleased(clampMag(this.mVelocityTracker.getXVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity), clampMag(this.mVelocityTracker.getYVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity));
    }

    private void dragTo(int left, int top, int dx, int dy) {
        int i = dx;
        int i2 = dy;
        int clampedX = left;
        int clampedY = top;
        int oldLeft = this.mCapturedView.getLeft();
        int oldTop = this.mCapturedView.getTop();
        if (i != 0) {
            clampedX = r0.mCallback.clampViewPositionHorizontal(r0.mCapturedView, left, i);
            ViewCompat.offsetLeftAndRight(r0.mCapturedView, clampedX - oldLeft);
        } else {
            int i3 = left;
        }
        if (i2 != 0) {
            clampedY = r0.mCallback.clampViewPositionVertical(r0.mCapturedView, top, i2);
            ViewCompat.offsetTopAndBottom(r0.mCapturedView, clampedY - oldTop);
        } else {
            int i4 = top;
        }
        if (i != 0 || i2 != 0) {
            r0.mCallback.onViewPositionChanged(r0.mCapturedView, clampedX, clampedY, clampedX - oldLeft, clampedY - oldTop);
        }
    }

    public boolean isCapturedViewUnder(int x, int y) {
        return isViewUnder(this.mCapturedView, x, y);
    }

    public boolean isViewUnder(@Nullable View view, int x, int y) {
        boolean z = false;
        if (view == null) {
            return false;
        }
        if (x >= view.getLeft() && x < view.getRight() && y >= view.getTop() && y < view.getBottom()) {
            z = true;
        }
        return z;
    }

    @Nullable
    public View findTopChildUnder(int x, int y) {
        for (int i = this.mParentView.getChildCount() - 1; i >= 0; i--) {
            View child = this.mParentView.getChildAt(this.mCallback.getOrderedChildIndex(i));
            if (x >= child.getLeft() && x < child.getRight() && y >= child.getTop() && y < child.getBottom()) {
                return child;
            }
        }
        return null;
    }

    private int getEdgesTouched(int x, int y) {
        int result = 0;
        if (x < this.mParentView.getLeft() + this.mEdgeSize) {
            result = 0 | 1;
        }
        if (y < this.mParentView.getTop() + this.mEdgeSize) {
            result |= 4;
        }
        if (x > this.mParentView.getRight() - this.mEdgeSize) {
            result |= 2;
        }
        if (y > this.mParentView.getBottom() - this.mEdgeSize) {
            return result | 8;
        }
        return result;
    }

    private boolean isValidPointerForActionMove(int pointerId) {
        if (isPointerDown(pointerId)) {
            return true;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ignoring pointerId=");
        stringBuilder.append(pointerId);
        stringBuilder.append(" because ACTION_DOWN was not received ");
        stringBuilder.append("for this pointer before ACTION_MOVE. It likely happened because ");
        stringBuilder.append(" ViewDragHelper did not receive all the events in the event stream.");
        Log.e(str, stringBuilder.toString());
        return false;
    }
}
