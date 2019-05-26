package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v7.appcompat.C0015R;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.ar.core.ImageMetadata;

@RestrictTo({Scope.LIBRARY_GROUP})
public class ActionBarContainer extends FrameLayout {
    private View mActionBarView;
    Drawable mBackground;
    private View mContextView;
    private int mHeight;
    boolean mIsSplit;
    boolean mIsStacked;
    private boolean mIsTransitioning;
    Drawable mSplitBackground;
    Drawable mStackedBackground;
    private View mTabContainer;

    public ActionBarContainer(Context context) {
        this(context, null);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ActionBarContainer(android.content.Context r6, android.util.AttributeSet r7) {
        /*
        r5 = this;
        r5.<init>(r6, r7);
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 21;
        if (r0 < r1) goto L_0x000f;
    L_0x0009:
        r0 = new android.support.v7.widget.ActionBarBackgroundDrawableV21;
        r0.<init>(r5);
        goto L_0x0014;
    L_0x000f:
        r0 = new android.support.v7.widget.ActionBarBackgroundDrawable;
        r0.<init>(r5);
    L_0x0014:
        android.support.v4.view.ViewCompat.setBackground(r5, r0);
        r1 = android.support.v7.appcompat.C0015R.styleable.ActionBar;
        r1 = r6.obtainStyledAttributes(r7, r1);
        r2 = android.support.v7.appcompat.C0015R.styleable.ActionBar_background;
        r2 = r1.getDrawable(r2);
        r5.mBackground = r2;
        r2 = android.support.v7.appcompat.C0015R.styleable.ActionBar_backgroundStacked;
        r2 = r1.getDrawable(r2);
        r5.mStackedBackground = r2;
        r2 = android.support.v7.appcompat.C0015R.styleable.ActionBar_height;
        r3 = -1;
        r2 = r1.getDimensionPixelSize(r2, r3);
        r5.mHeight = r2;
        r2 = r5.getId();
        r3 = android.support.v7.appcompat.C0015R.id.split_action_bar;
        r4 = 1;
        if (r2 != r3) goto L_0x0049;
    L_0x003f:
        r5.mIsSplit = r4;
        r2 = android.support.v7.appcompat.C0015R.styleable.ActionBar_backgroundSplit;
        r2 = r1.getDrawable(r2);
        r5.mSplitBackground = r2;
    L_0x0049:
        r1.recycle();
        r2 = r5.mIsSplit;
        r3 = 0;
        if (r2 == 0) goto L_0x0058;
    L_0x0051:
        r2 = r5.mSplitBackground;
        if (r2 != 0) goto L_0x0057;
    L_0x0055:
        r3 = r4;
        goto L_0x0061;
    L_0x0057:
        goto L_0x0061;
    L_0x0058:
        r2 = r5.mBackground;
        if (r2 != 0) goto L_0x0057;
    L_0x005c:
        r2 = r5.mStackedBackground;
        if (r2 != 0) goto L_0x0057;
    L_0x0060:
        goto L_0x0055;
    L_0x0061:
        r5.setWillNotDraw(r3);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActionBarContainer.<init>(android.content.Context, android.util.AttributeSet):void");
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        this.mActionBarView = findViewById(C0015R.id.action_bar);
        this.mContextView = findViewById(C0015R.id.action_context_bar);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setPrimaryBackground(android.graphics.drawable.Drawable r6) {
        /*
        r5 = this;
        r0 = r5.mBackground;
        if (r0 == 0) goto L_0x000f;
    L_0x0004:
        r0 = r5.mBackground;
        r1 = 0;
        r0.setCallback(r1);
        r0 = r5.mBackground;
        r5.unscheduleDrawable(r0);
    L_0x000f:
        r5.mBackground = r6;
        if (r6 == 0) goto L_0x0037;
    L_0x0013:
        r6.setCallback(r5);
        r0 = r5.mActionBarView;
        if (r0 == 0) goto L_0x0037;
    L_0x001a:
        r0 = r5.mBackground;
        r1 = r5.mActionBarView;
        r1 = r1.getLeft();
        r2 = r5.mActionBarView;
        r2 = r2.getTop();
        r3 = r5.mActionBarView;
        r3 = r3.getRight();
        r4 = r5.mActionBarView;
        r4 = r4.getBottom();
        r0.setBounds(r1, r2, r3, r4);
    L_0x0037:
        r0 = r5.mIsSplit;
        r1 = 0;
        r2 = 1;
        if (r0 == 0) goto L_0x0044;
    L_0x003d:
        r0 = r5.mSplitBackground;
        if (r0 != 0) goto L_0x0043;
    L_0x0041:
        r1 = r2;
        goto L_0x004d;
    L_0x0043:
        goto L_0x004d;
    L_0x0044:
        r0 = r5.mBackground;
        if (r0 != 0) goto L_0x0043;
    L_0x0048:
        r0 = r5.mStackedBackground;
        if (r0 != 0) goto L_0x0043;
    L_0x004c:
        goto L_0x0041;
    L_0x004d:
        r5.setWillNotDraw(r1);
        r5.invalidate();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActionBarContainer.setPrimaryBackground(android.graphics.drawable.Drawable):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setStackedBackground(android.graphics.drawable.Drawable r6) {
        /*
        r5 = this;
        r0 = r5.mStackedBackground;
        if (r0 == 0) goto L_0x000f;
    L_0x0004:
        r0 = r5.mStackedBackground;
        r1 = 0;
        r0.setCallback(r1);
        r0 = r5.mStackedBackground;
        r5.unscheduleDrawable(r0);
    L_0x000f:
        r5.mStackedBackground = r6;
        if (r6 == 0) goto L_0x003b;
    L_0x0013:
        r6.setCallback(r5);
        r0 = r5.mIsStacked;
        if (r0 == 0) goto L_0x003b;
    L_0x001a:
        r0 = r5.mStackedBackground;
        if (r0 == 0) goto L_0x003b;
    L_0x001e:
        r0 = r5.mStackedBackground;
        r1 = r5.mTabContainer;
        r1 = r1.getLeft();
        r2 = r5.mTabContainer;
        r2 = r2.getTop();
        r3 = r5.mTabContainer;
        r3 = r3.getRight();
        r4 = r5.mTabContainer;
        r4 = r4.getBottom();
        r0.setBounds(r1, r2, r3, r4);
    L_0x003b:
        r0 = r5.mIsSplit;
        r1 = 0;
        r2 = 1;
        if (r0 == 0) goto L_0x0048;
    L_0x0041:
        r0 = r5.mSplitBackground;
        if (r0 != 0) goto L_0x0047;
    L_0x0045:
        r1 = r2;
        goto L_0x0051;
    L_0x0047:
        goto L_0x0051;
    L_0x0048:
        r0 = r5.mBackground;
        if (r0 != 0) goto L_0x0047;
    L_0x004c:
        r0 = r5.mStackedBackground;
        if (r0 != 0) goto L_0x0047;
    L_0x0050:
        goto L_0x0045;
    L_0x0051:
        r5.setWillNotDraw(r1);
        r5.invalidate();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActionBarContainer.setStackedBackground(android.graphics.drawable.Drawable):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setSplitBackground(android.graphics.drawable.Drawable r5) {
        /*
        r4 = this;
        r0 = r4.mSplitBackground;
        if (r0 == 0) goto L_0x000f;
    L_0x0004:
        r0 = r4.mSplitBackground;
        r1 = 0;
        r0.setCallback(r1);
        r0 = r4.mSplitBackground;
        r4.unscheduleDrawable(r0);
    L_0x000f:
        r4.mSplitBackground = r5;
        r0 = 0;
        if (r5 == 0) goto L_0x002c;
    L_0x0014:
        r5.setCallback(r4);
        r1 = r4.mIsSplit;
        if (r1 == 0) goto L_0x002c;
    L_0x001b:
        r1 = r4.mSplitBackground;
        if (r1 == 0) goto L_0x002c;
    L_0x001f:
        r1 = r4.mSplitBackground;
        r2 = r4.getMeasuredWidth();
        r3 = r4.getMeasuredHeight();
        r1.setBounds(r0, r0, r2, r3);
    L_0x002c:
        r1 = r4.mIsSplit;
        r2 = 1;
        if (r1 == 0) goto L_0x0038;
    L_0x0031:
        r1 = r4.mSplitBackground;
        if (r1 != 0) goto L_0x0037;
    L_0x0035:
        r0 = r2;
        goto L_0x0041;
    L_0x0037:
        goto L_0x0041;
    L_0x0038:
        r1 = r4.mBackground;
        if (r1 != 0) goto L_0x0037;
    L_0x003c:
        r1 = r4.mStackedBackground;
        if (r1 != 0) goto L_0x0037;
    L_0x0040:
        goto L_0x0035;
    L_0x0041:
        r4.setWillNotDraw(r0);
        r4.invalidate();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActionBarContainer.setSplitBackground(android.graphics.drawable.Drawable):void");
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        boolean isVisible = visibility == 0;
        if (this.mBackground != null) {
            this.mBackground.setVisible(isVisible, false);
        }
        if (this.mStackedBackground != null) {
            this.mStackedBackground.setVisible(isVisible, false);
        }
        if (this.mSplitBackground != null) {
            this.mSplitBackground.setVisible(isVisible, false);
        }
    }

    protected boolean verifyDrawable(Drawable who) {
        return (who == this.mBackground && !this.mIsSplit) || ((who == this.mStackedBackground && this.mIsStacked) || ((who == this.mSplitBackground && this.mIsSplit) || super.verifyDrawable(who)));
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mBackground != null && this.mBackground.isStateful()) {
            this.mBackground.setState(getDrawableState());
        }
        if (this.mStackedBackground != null && this.mStackedBackground.isStateful()) {
            this.mStackedBackground.setState(getDrawableState());
        }
        if (this.mSplitBackground != null && this.mSplitBackground.isStateful()) {
            this.mSplitBackground.setState(getDrawableState());
        }
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mBackground != null) {
            this.mBackground.jumpToCurrentState();
        }
        if (this.mStackedBackground != null) {
            this.mStackedBackground.jumpToCurrentState();
        }
        if (this.mSplitBackground != null) {
            this.mSplitBackground.jumpToCurrentState();
        }
    }

    public void setTransitioning(boolean isTransitioning) {
        this.mIsTransitioning = isTransitioning;
        setDescendantFocusability(isTransitioning ? ImageMetadata.HOT_PIXEL_MODE : 262144);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!this.mIsTransitioning) {
            if (!super.onInterceptTouchEvent(ev)) {
                return false;
            }
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        return true;
    }

    public boolean onHoverEvent(MotionEvent ev) {
        super.onHoverEvent(ev);
        return true;
    }

    public void setTabContainer(ScrollingTabContainerView tabView) {
        if (this.mTabContainer != null) {
            removeView(this.mTabContainer);
        }
        this.mTabContainer = tabView;
        if (tabView != null) {
            addView(tabView);
            LayoutParams lp = tabView.getLayoutParams();
            lp.width = -1;
            lp.height = -2;
            tabView.setAllowCollapse(false);
        }
    }

    public View getTabContainer() {
        return this.mTabContainer;
    }

    public ActionMode startActionModeForChild(View child, Callback callback) {
        return null;
    }

    public ActionMode startActionModeForChild(View child, Callback callback, int type) {
        if (type != 0) {
            return super.startActionModeForChild(child, callback, type);
        }
        return null;
    }

    private boolean isCollapsed(View view) {
        if (!(view == null || view.getVisibility() == 8)) {
            if (view.getMeasuredHeight() != 0) {
                return false;
            }
        }
        return true;
    }

    private int getMeasuredHeightWithMargins(View view) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view.getLayoutParams();
        return (view.getMeasuredHeight() + lp.topMargin) + lp.bottomMargin;
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mActionBarView == null && MeasureSpec.getMode(heightMeasureSpec) == Integer.MIN_VALUE && this.mHeight >= 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.min(this.mHeight, MeasureSpec.getSize(heightMeasureSpec)), Integer.MIN_VALUE);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mActionBarView != null) {
            int mode = MeasureSpec.getMode(heightMeasureSpec);
            if (!(this.mTabContainer == null || this.mTabContainer.getVisibility() == 8 || mode == ErrorDialogData.SUPPRESSED)) {
                int topMarginForTabs;
                if (!isCollapsed(this.mActionBarView)) {
                    topMarginForTabs = getMeasuredHeightWithMargins(this.mActionBarView);
                } else if (isCollapsed(this.mContextView)) {
                    topMarginForTabs = 0;
                } else {
                    topMarginForTabs = getMeasuredHeightWithMargins(this.mContextView);
                }
                setMeasuredDimension(getMeasuredWidth(), Math.min(getMeasuredHeightWithMargins(this.mTabContainer) + topMarginForTabs, mode == Integer.MIN_VALUE ? MeasureSpec.getSize(heightMeasureSpec) : ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED));
            }
        }
    }

    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View tabContainer = this.mTabContainer;
        boolean hasTabs = (tabContainer == null || tabContainer.getVisibility() == 8) ? false : true;
        if (!(tabContainer == null || tabContainer.getVisibility() == 8)) {
            int containerHeight = getMeasuredHeight();
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) tabContainer.getLayoutParams();
            tabContainer.layout(l, (containerHeight - tabContainer.getMeasuredHeight()) - lp.bottomMargin, r, containerHeight - lp.bottomMargin);
        }
        boolean needsInvalidate = false;
        if (!this.mIsSplit) {
            if (this.mBackground != null) {
                if (this.mActionBarView.getVisibility() == 0) {
                    this.mBackground.setBounds(this.mActionBarView.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom());
                } else if (this.mContextView == null || this.mContextView.getVisibility() != 0) {
                    this.mBackground.setBounds(0, 0, 0, 0);
                } else {
                    this.mBackground.setBounds(this.mContextView.getLeft(), this.mContextView.getTop(), this.mContextView.getRight(), this.mContextView.getBottom());
                }
                needsInvalidate = true;
            }
            this.mIsStacked = hasTabs;
            if (hasTabs && this.mStackedBackground != null) {
                this.mStackedBackground.setBounds(tabContainer.getLeft(), tabContainer.getTop(), tabContainer.getRight(), tabContainer.getBottom());
                needsInvalidate = true;
            }
        } else if (this.mSplitBackground != null) {
            this.mSplitBackground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            needsInvalidate = true;
        }
        if (needsInvalidate) {
            invalidate();
        }
    }
}
