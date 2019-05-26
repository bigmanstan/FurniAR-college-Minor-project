package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.C0015R;
import android.support.v7.widget.LinearLayoutCompat.LayoutParams;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;

@RestrictTo({Scope.LIBRARY_GROUP})
public class AlertDialogLayout extends LinearLayoutCompat {
    public AlertDialogLayout(@Nullable Context context) {
        super(context);
    }

    public AlertDialogLayout(@Nullable Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!tryOnMeasure(widthMeasureSpec, heightMeasureSpec)) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private boolean tryOnMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        int id;
        int childHeightSpec;
        int heightToGive;
        View buttonPanel;
        AlertDialogLayout alertDialogLayout = this;
        int i2 = widthMeasureSpec;
        int i3 = heightMeasureSpec;
        int count = getChildCount();
        View middlePanel = null;
        View buttonPanel2 = null;
        View topPanel = null;
        for (i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                id = child.getId();
                if (id == C0015R.id.topPanel) {
                    topPanel = child;
                } else if (id == C0015R.id.buttonPanel) {
                    buttonPanel2 = child;
                } else {
                    if (id != C0015R.id.contentPanel) {
                        if (id != C0015R.id.customPanel) {
                            return false;
                        }
                    }
                    if (middlePanel != null) {
                        return false;
                    }
                    middlePanel = child;
                }
            }
        }
        i = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int childState = 0;
        int usedHeight = getPaddingTop() + getPaddingBottom();
        if (topPanel != null) {
            topPanel.measure(i2, 0);
            usedHeight += topPanel.getMeasuredHeight();
            childState = View.combineMeasuredStates(0, topPanel.getMeasuredState());
        }
        int buttonHeight = 0;
        int buttonWantsHeight = 0;
        if (buttonPanel2 != null) {
            buttonPanel2.measure(i2, 0);
            buttonHeight = resolveMinimumHeight(buttonPanel2);
            buttonWantsHeight = buttonPanel2.getMeasuredHeight() - buttonHeight;
            usedHeight += buttonHeight;
            childState = View.combineMeasuredStates(childState, buttonPanel2.getMeasuredState());
        }
        id = 0;
        if (middlePanel != null) {
            if (i == 0) {
                childHeightSpec = 0;
                View view = topPanel;
            } else {
                childHeightSpec = MeasureSpec.makeMeasureSpec(Math.max(0, heightSize - usedHeight), i);
            }
            middlePanel.measure(i2, childHeightSpec);
            id = middlePanel.getMeasuredHeight();
            usedHeight += id;
            childState = View.combineMeasuredStates(childState, middlePanel.getMeasuredState());
        }
        int remainingHeight = heightSize - usedHeight;
        if (buttonPanel2 != null) {
            usedHeight -= buttonHeight;
            childHeightSpec = Math.min(remainingHeight, buttonWantsHeight);
            if (childHeightSpec > 0) {
                remainingHeight -= childHeightSpec;
                buttonHeight += childHeightSpec;
            }
            int remainingHeight2 = remainingHeight;
            buttonPanel2.measure(i2, MeasureSpec.makeMeasureSpec(buttonHeight, ErrorDialogData.SUPPRESSED));
            usedHeight += buttonPanel2.getMeasuredHeight();
            childState = View.combineMeasuredStates(childState, buttonPanel2.getMeasuredState());
            remainingHeight = remainingHeight2;
        }
        if (middlePanel == null || remainingHeight <= 0) {
        } else {
            usedHeight -= id;
            heightToGive = remainingHeight;
            int remainingHeight3 = remainingHeight - heightToGive;
            middlePanel.measure(i2, MeasureSpec.makeMeasureSpec(id + heightToGive, i));
            usedHeight += middlePanel.getMeasuredHeight();
            childState = View.combineMeasuredStates(childState, middlePanel.getMeasuredState());
            remainingHeight = remainingHeight3;
        }
        heightToGive = 0;
        i = 0;
        while (i < count) {
            View child2 = getChildAt(i);
            int remainingHeight4 = remainingHeight;
            buttonPanel = buttonPanel2;
            if (child2.getVisibility() != 8) {
                heightToGive = Math.max(heightToGive, child2.getMeasuredWidth());
            }
            i++;
            remainingHeight = remainingHeight4;
            buttonPanel2 = buttonPanel;
        }
        buttonPanel = buttonPanel2;
        setMeasuredDimension(View.resolveSizeAndState(heightToGive + (getPaddingLeft() + getPaddingRight()), i2, childState), View.resolveSizeAndState(usedHeight, i3, 0));
        if (widthMode != ErrorDialogData.SUPPRESSED) {
            forceUniformWidth(count, i3);
        }
        return true;
    }

    private void forceUniformWidth(int count, int heightMeasureSpec) {
        int uniformMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), ErrorDialogData.SUPPRESSED);
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.width == -1) {
                    int oldHeight = lp.height;
                    lp.height = child.getMeasuredHeight();
                    measureChildWithMargins(child, uniformMeasureSpec, 0, heightMeasureSpec, 0);
                    lp.height = oldHeight;
                }
            }
        }
    }

    private static int resolveMinimumHeight(View v) {
        int minHeight = ViewCompat.getMinimumHeight(v);
        if (minHeight > 0) {
            return minHeight;
        }
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            if (vg.getChildCount() == 1) {
                return resolveMinimumHeight(vg.getChildAt(0));
            }
        }
        return 0;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int paddingTop;
        AlertDialogLayout alertDialogLayout = this;
        int paddingLeft = getPaddingLeft();
        int width = right - left;
        int childRight = width - getPaddingRight();
        int childSpace = (width - paddingLeft) - getPaddingRight();
        int totalLength = getMeasuredHeight();
        int count = getChildCount();
        int gravity = getGravity();
        int majorGravity = gravity & 112;
        int minorGravity = gravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (majorGravity == 16) {
            paddingTop = getPaddingTop() + (((bottom - top) - totalLength) / 2);
        } else if (majorGravity != 80) {
            paddingTop = getPaddingTop();
        } else {
            paddingTop = ((getPaddingTop() + bottom) - top) - totalLength;
        }
        Drawable dividerDrawable = getDividerDrawable();
        int i = 0;
        int dividerHeight = dividerDrawable == null ? 0 : dividerDrawable.getIntrinsicHeight();
        while (true) {
            int i2 = i;
            int count2;
            if (i2 < count) {
                int i3;
                int majorGravity2;
                View child = alertDialogLayout.getChildAt(i2);
                if (child == null || child.getVisibility() == 8) {
                    i3 = i2;
                    majorGravity2 = majorGravity;
                    count2 = count;
                } else {
                    int childWidth = child.getMeasuredWidth();
                    int childHeight = child.getMeasuredHeight();
                    LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    int layoutGravity = lp.gravity;
                    if (layoutGravity < 0) {
                        layoutGravity = minorGravity;
                    }
                    layoutGravity = GravityCompat.getAbsoluteGravity(layoutGravity, ViewCompat.getLayoutDirection(this)) & 7;
                    majorGravity2 = majorGravity;
                    layoutGravity = layoutGravity != 1 ? layoutGravity != 5 ? lp.leftMargin + paddingLeft : (childRight - childWidth) - lp.rightMargin : ((((childSpace - childWidth) / 2) + paddingLeft) + lp.leftMargin) - lp.rightMargin;
                    if (alertDialogLayout.hasDividerBeforeChildAt(i2)) {
                        paddingTop += dividerHeight;
                    }
                    int childTop = paddingTop + lp.topMargin;
                    i3 = i2;
                    LayoutParams lp2 = lp;
                    count2 = count;
                    setChildFrame(child, layoutGravity, childTop, childWidth, childHeight);
                    paddingTop = childTop + (childHeight + lp2.bottomMargin);
                }
                i = i3 + 1;
                majorGravity = majorGravity2;
                count = count2;
                alertDialogLayout = this;
            } else {
                count2 = count;
                return;
            }
        }
    }

    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }
}
