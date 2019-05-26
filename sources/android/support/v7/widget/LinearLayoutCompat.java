package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.C0015R;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LinearLayoutCompat extends ViewGroup {
    public static final int HORIZONTAL = 0;
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private boolean mBaselineAligned;
    private int mBaselineAlignedChildIndex;
    private int mBaselineChildTop;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mGravity;
    private int[] mMaxAscent;
    private int[] mMaxDescent;
    private int mOrientation;
    private int mShowDividers;
    private int mTotalLength;
    private boolean mUseLargestChild;
    private float mWeightSum;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DividerMode {
    }

    public static class LayoutParams extends MarginLayoutParams {
        public int gravity;
        public float weight;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.gravity = -1;
            TypedArray a = c.obtainStyledAttributes(attrs, C0015R.styleable.LinearLayoutCompat_Layout);
            this.weight = a.getFloat(C0015R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0f);
            this.gravity = a.getInt(C0015R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.gravity = -1;
            this.weight = 0.0f;
        }

        public LayoutParams(int width, int height, float weight) {
            super(width, height);
            this.gravity = -1;
            this.weight = weight;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams p) {
            super(p);
            this.gravity = -1;
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
            this.gravity = -1;
        }

        public LayoutParams(LayoutParams source) {
            super(source);
            this.gravity = -1;
            this.weight = source.weight;
            this.gravity = source.gravity;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationMode {
    }

    public LinearLayoutCompat(Context context) {
        this(context, null);
    }

    public LinearLayoutCompat(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayoutCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, C0015R.styleable.LinearLayoutCompat, defStyleAttr, 0);
        int index = a.getInt(C0015R.styleable.LinearLayoutCompat_android_orientation, -1);
        if (index >= 0) {
            setOrientation(index);
        }
        index = a.getInt(C0015R.styleable.LinearLayoutCompat_android_gravity, -1);
        if (index >= 0) {
            setGravity(index);
        }
        boolean baselineAligned = a.getBoolean(C0015R.styleable.LinearLayoutCompat_android_baselineAligned, true);
        if (!baselineAligned) {
            setBaselineAligned(baselineAligned);
        }
        this.mWeightSum = a.getFloat(C0015R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.mBaselineAlignedChildIndex = a.getInt(C0015R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.mUseLargestChild = a.getBoolean(C0015R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(a.getDrawable(C0015R.styleable.LinearLayoutCompat_divider));
        this.mShowDividers = a.getInt(C0015R.styleable.LinearLayoutCompat_showDividers, 0);
        this.mDividerPadding = a.getDimensionPixelSize(C0015R.styleable.LinearLayoutCompat_dividerPadding, 0);
        a.recycle();
    }

    public void setShowDividers(int showDividers) {
        if (showDividers != this.mShowDividers) {
            requestLayout();
        }
        this.mShowDividers = showDividers;
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public void setDividerDrawable(Drawable divider) {
        if (divider != this.mDivider) {
            this.mDivider = divider;
            boolean z = false;
            if (divider != null) {
                this.mDividerWidth = divider.getIntrinsicWidth();
                this.mDividerHeight = divider.getIntrinsicHeight();
            } else {
                this.mDividerWidth = 0;
                this.mDividerHeight = 0;
            }
            if (divider == null) {
                z = true;
            }
            setWillNotDraw(z);
            requestLayout();
        }
    }

    public void setDividerPadding(int padding) {
        this.mDividerPadding = padding;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    protected void onDraw(Canvas canvas) {
        if (this.mDivider != null) {
            if (this.mOrientation == 1) {
                drawDividersVertical(canvas);
            } else {
                drawDividersHorizontal(canvas);
            }
        }
    }

    void drawDividersVertical(Canvas canvas) {
        int count = getVirtualChildCount();
        int i = 0;
        while (i < count) {
            View child = getVirtualChildAt(i);
            if (!(child == null || child.getVisibility() == 8 || !hasDividerBeforeChildAt(i))) {
                drawHorizontalDivider(canvas, (child.getTop() - ((LayoutParams) child.getLayoutParams()).topMargin) - this.mDividerHeight);
            }
            i++;
        }
        if (hasDividerBeforeChildAt(count)) {
            int bottom;
            View child2 = getVirtualChildAt(count - 1);
            if (child2 == null) {
                bottom = (getHeight() - getPaddingBottom()) - this.mDividerHeight;
            } else {
                bottom = child2.getBottom() + ((LayoutParams) child2.getLayoutParams()).bottomMargin;
            }
            drawHorizontalDivider(canvas, bottom);
        }
    }

    void drawDividersHorizontal(Canvas canvas) {
        int count = getVirtualChildCount();
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int i = 0;
        while (i < count) {
            View child = getVirtualChildAt(i);
            if (!(child == null || child.getVisibility() == 8 || !hasDividerBeforeChildAt(i))) {
                int position;
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (isLayoutRtl) {
                    position = child.getRight() + lp.rightMargin;
                } else {
                    position = (child.getLeft() - lp.leftMargin) - this.mDividerWidth;
                }
                drawVerticalDivider(canvas, position);
            }
            i++;
        }
        if (hasDividerBeforeChildAt(count)) {
            int position2;
            View child2 = getVirtualChildAt(count - 1);
            if (child2 != null) {
                LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                if (isLayoutRtl) {
                    position2 = (child2.getLeft() - lp2.leftMargin) - this.mDividerWidth;
                } else {
                    position2 = child2.getRight() + lp2.rightMargin;
                }
            } else if (isLayoutRtl) {
                position2 = getPaddingLeft();
            } else {
                position2 = (getWidth() - getPaddingRight()) - this.mDividerWidth;
            }
            drawVerticalDivider(canvas, position2);
        }
    }

    void drawHorizontalDivider(Canvas canvas, int top) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, top, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + top);
        this.mDivider.draw(canvas);
    }

    void drawVerticalDivider(Canvas canvas, int left) {
        this.mDivider.setBounds(left, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + left, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    public boolean isBaselineAligned() {
        return this.mBaselineAligned;
    }

    public void setBaselineAligned(boolean baselineAligned) {
        this.mBaselineAligned = baselineAligned;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild;
    }

    public void setMeasureWithLargestChildEnabled(boolean enabled) {
        this.mUseLargestChild = enabled;
    }

    public int getBaseline() {
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        if (getChildCount() > this.mBaselineAlignedChildIndex) {
            View child = getChildAt(this.mBaselineAlignedChildIndex);
            int childBaseline = child.getBaseline();
            if (childBaseline != -1) {
                int childTop = this.mBaselineChildTop;
                if (this.mOrientation == 1) {
                    int majorGravity = this.mGravity & 112;
                    if (majorGravity != 48) {
                        if (majorGravity == 16) {
                            childTop += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.mTotalLength) / 2;
                        } else if (majorGravity == 80) {
                            childTop = ((getBottom() - getTop()) - getPaddingBottom()) - this.mTotalLength;
                        }
                    }
                }
                return (((LayoutParams) child.getLayoutParams()).topMargin + childTop) + childBaseline;
            } else if (this.mBaselineAlignedChildIndex == 0) {
                return -1;
            } else {
                throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
            }
        }
        throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    public void setBaselineAlignedChildIndex(int i) {
        if (i < 0 || i >= getChildCount()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("base aligned child index out of range (0, ");
            stringBuilder.append(getChildCount());
            stringBuilder.append(")");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.mBaselineAlignedChildIndex = i;
    }

    View getVirtualChildAt(int index) {
        return getChildAt(index);
    }

    int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    public void setWeightSum(float weightSum) {
        this.mWeightSum = Math.max(0.0f, weightSum);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mOrientation == 1) {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        } else {
            measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @RestrictTo({Scope.LIBRARY})
    protected boolean hasDividerBeforeChildAt(int childIndex) {
        boolean hasVisibleViewBefore = false;
        if (childIndex == 0) {
            if ((this.mShowDividers & 1) != 0) {
                hasVisibleViewBefore = true;
            }
            return hasVisibleViewBefore;
        } else if (childIndex == getChildCount()) {
            if ((this.mShowDividers & 4) != 0) {
                hasVisibleViewBefore = true;
            }
            return hasVisibleViewBefore;
        } else if ((this.mShowDividers & 2) == 0) {
            return false;
        } else {
            hasVisibleViewBefore = false;
            for (int i = childIndex - 1; i >= 0; i--) {
                if (getChildAt(i).getVisibility() != 8) {
                    hasVisibleViewBefore = true;
                    break;
                }
            }
            return hasVisibleViewBefore;
        }
    }

    void measureVertical(int widthMeasureSpec, int heightMeasureSpec) {
        int weightedMaxWidth;
        int count;
        int heightMode;
        boolean skippedMeasure;
        int largestChildHeight;
        int maxWidth;
        float totalWeight;
        int delta;
        boolean z;
        int i;
        int i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        this.mTotalLength = 0;
        int childState = 0;
        float totalWeight2 = 0.0f;
        int count2 = getVirtualChildCount();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode2 = MeasureSpec.getMode(heightMeasureSpec);
        boolean skippedMeasure2 = false;
        int baselineChildIndex = this.mBaselineAlignedChildIndex;
        boolean useLargestChild = this.mUseLargestChild;
        boolean matchWidth = false;
        int alternativeMaxWidth = 0;
        int maxWidth2 = 0;
        int i5 = 0;
        int weightedMaxWidth2 = 0;
        int largestChildHeight2 = 0;
        boolean allFillParent = true;
        while (true) {
            weightedMaxWidth = weightedMaxWidth2;
            if (i5 >= count2) {
                break;
            }
            boolean matchWidthLocally;
            int margin;
            View child = getVirtualChildAt(i5);
            int childState2;
            if (child == null) {
                childState2 = childState;
                r7.mTotalLength += measureNullChild(i5);
                count = count2;
                heightMode = heightMode2;
                weightedMaxWidth2 = weightedMaxWidth;
                childState = childState2;
            } else {
                childState2 = childState;
                int maxWidth3 = maxWidth2;
                if (child.getVisibility() == 8) {
                    i5 += getChildrenSkipCount(child, i5);
                    count = count2;
                    heightMode = heightMode2;
                    weightedMaxWidth2 = weightedMaxWidth;
                    childState = childState2;
                    maxWidth2 = maxWidth3;
                } else {
                    LayoutParams lp;
                    int maxWidth4;
                    int i6;
                    int largestChildHeight3;
                    boolean allFillParent2;
                    if (hasDividerBeforeChildAt(i5)) {
                        r7.mTotalLength += r7.mDividerHeight;
                    }
                    LayoutParams lp2 = (LayoutParams) child.getLayoutParams();
                    float totalWeight3 = totalWeight2 + lp2.weight;
                    int i7;
                    if (heightMode2 == ErrorDialogData.SUPPRESSED && lp2.height == 0 && lp2.weight > 0.0f) {
                        childState = r7.mTotalLength;
                        i7 = i5;
                        r7.mTotalLength = Math.max(childState, (lp2.topMargin + childState) + lp2.bottomMargin);
                        lp = lp2;
                        count = count2;
                        heightMode = heightMode2;
                        skippedMeasure = true;
                        i4 = weightedMaxWidth;
                        i3 = childState2;
                        maxWidth4 = maxWidth3;
                        i6 = i7;
                        count2 = alternativeMaxWidth;
                    } else {
                        i7 = i5;
                        i5 = Integer.MIN_VALUE;
                        if (lp2.height == 0 && lp2.weight > 0.0f) {
                            i5 = 0;
                            lp2.height = -2;
                        }
                        i6 = i7;
                        i3 = childState2;
                        LayoutParams lp3 = lp2;
                        maxWidth4 = maxWidth3;
                        skippedMeasure = skippedMeasure2;
                        largestChildHeight = largestChildHeight2;
                        View child2 = child;
                        heightMode = heightMode2;
                        i4 = weightedMaxWidth;
                        heightMode2 = i5;
                        count = count2;
                        count2 = alternativeMaxWidth;
                        measureChildBeforeLayout(child, i6, widthMeasureSpec, 0, heightMeasureSpec, totalWeight3 == 0.0f ? r7.mTotalLength : 0);
                        if (heightMode2 != Integer.MIN_VALUE) {
                            lp = lp3;
                            lp.height = heightMode2;
                        } else {
                            lp = lp3;
                        }
                        childState = child2.getMeasuredHeight();
                        maxWidth2 = r7.mTotalLength;
                        child = child2;
                        r7.mTotalLength = Math.max(maxWidth2, (((maxWidth2 + childState) + lp.topMargin) + lp.bottomMargin) + getNextLocationOffset(child));
                        if (useLargestChild) {
                            largestChildHeight2 = Math.max(childState, largestChildHeight);
                        } else {
                            largestChildHeight2 = largestChildHeight;
                        }
                    }
                    if (baselineChildIndex >= 0) {
                        childState = i6;
                        if (baselineChildIndex == childState + 1) {
                            r7.mBaselineChildTop = r7.mTotalLength;
                        }
                    } else {
                        childState = i6;
                    }
                    if (childState < baselineChildIndex) {
                        if (lp.weight > 0.0f) {
                            throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                        }
                    }
                    matchWidthLocally = false;
                    if (widthMode != ErrorDialogData.SUPPRESSED) {
                        if (lp.width == -1) {
                            matchWidth = true;
                            matchWidthLocally = true;
                        }
                    }
                    margin = lp.leftMargin + lp.rightMargin;
                    heightMode2 = child.getMeasuredWidth() + margin;
                    largestChildHeight = Math.max(maxWidth4, heightMode2);
                    alternativeMaxWidth = View.combineMeasuredStates(i3, child.getMeasuredState());
                    if (allFillParent) {
                        largestChildHeight3 = largestChildHeight2;
                        if (lp.width == -1) {
                            allFillParent2 = true;
                            if (lp.weight <= 0.0f) {
                                i4 = Math.max(i4, matchWidthLocally ? margin : heightMode2);
                                i3 = count2;
                            } else {
                                i3 = Math.max(count2, matchWidthLocally ? margin : heightMode2);
                            }
                            i5 = childState + getChildrenSkipCount(child, childState);
                            allFillParent = allFillParent2;
                            childState = alternativeMaxWidth;
                            alternativeMaxWidth = i3;
                            weightedMaxWidth2 = i4;
                            maxWidth2 = largestChildHeight;
                            totalWeight2 = totalWeight3;
                            skippedMeasure2 = skippedMeasure;
                            largestChildHeight2 = largestChildHeight3;
                        }
                    } else {
                        largestChildHeight3 = largestChildHeight2;
                    }
                    allFillParent2 = false;
                    if (lp.weight <= 0.0f) {
                        if (matchWidthLocally) {
                        }
                        i3 = Math.max(count2, matchWidthLocally ? margin : heightMode2);
                    } else {
                        if (matchWidthLocally) {
                        }
                        i4 = Math.max(i4, matchWidthLocally ? margin : heightMode2);
                        i3 = count2;
                    }
                    i5 = childState + getChildrenSkipCount(child, childState);
                    allFillParent = allFillParent2;
                    childState = alternativeMaxWidth;
                    alternativeMaxWidth = i3;
                    weightedMaxWidth2 = i4;
                    maxWidth2 = largestChildHeight;
                    totalWeight2 = totalWeight3;
                    skippedMeasure2 = skippedMeasure;
                    largestChildHeight2 = largestChildHeight3;
                }
            }
            i5++;
            heightMode2 = heightMode;
            count2 = count;
            i3 = widthMeasureSpec;
            i4 = heightMeasureSpec;
        }
        i3 = childState;
        i5 = maxWidth2;
        count = count2;
        heightMode = heightMode2;
        skippedMeasure = skippedMeasure2;
        i4 = weightedMaxWidth;
        largestChildHeight = largestChildHeight2;
        count2 = alternativeMaxWidth;
        if (r7.mTotalLength > 0) {
            childState = count;
            if (hasDividerBeforeChildAt(childState)) {
                r7.mTotalLength += r7.mDividerHeight;
            }
        } else {
            childState = count;
        }
        if (useLargestChild) {
            maxWidth2 = heightMode;
            if (maxWidth2 != Integer.MIN_VALUE) {
                if (maxWidth2 != 0) {
                    maxWidth = i5;
                }
            }
            r7.mTotalLength = 0;
            largestChildHeight2 = 0;
            while (largestChildHeight2 < childState) {
                child = getVirtualChildAt(largestChildHeight2);
                if (child == null) {
                    r7.mTotalLength += measureNullChild(largestChildHeight2);
                } else if (child.getVisibility() == 8) {
                    largestChildHeight2 += getChildrenSkipCount(child, largestChildHeight2);
                } else {
                    LayoutParams lp4 = (LayoutParams) child.getLayoutParams();
                    heightMode2 = r7.mTotalLength;
                    maxWidth = i5;
                    r7.mTotalLength = Math.max(heightMode2, (((heightMode2 + largestChildHeight) + lp4.topMargin) + lp4.bottomMargin) + getNextLocationOffset(child));
                    largestChildHeight2++;
                    i5 = maxWidth;
                }
                maxWidth = i5;
                largestChildHeight2++;
                i5 = maxWidth;
            }
            maxWidth = i5;
        } else {
            maxWidth = i5;
            maxWidth2 = heightMode;
        }
        r7.mTotalLength += getPaddingTop() + getPaddingBottom();
        weightedMaxWidth2 = i4;
        largestChildHeight2 = heightMeasureSpec;
        i4 = View.resolveSizeAndState(Math.max(r7.mTotalLength, getSuggestedMinimumHeight()), largestChildHeight2, 0);
        i5 = i4 & ViewCompat.MEASURED_SIZE_MASK;
        alternativeMaxWidth = i5 - r7.mTotalLength;
        int i8;
        if (skippedMeasure) {
            i8 = weightedMaxWidth2;
            totalWeight = totalWeight2;
            delta = alternativeMaxWidth;
        } else if (alternativeMaxWidth == 0 || totalWeight2 <= 0.0f) {
            count2 = Math.max(count2, weightedMaxWidth2);
            if (useLargestChild && maxWidth2 != ErrorDialogData.SUPPRESSED) {
                int i9 = 0;
                while (true) {
                    heightMode2 = i9;
                    if (heightMode2 >= childState) {
                        break;
                    }
                    heightSize = i5;
                    View child3 = getVirtualChildAt(heightMode2);
                    if (child3 != null) {
                        i8 = weightedMaxWidth2;
                        totalWeight = totalWeight2;
                        if (child3.getVisibility() == 8) {
                            delta = alternativeMaxWidth;
                        } else {
                            LayoutParams weightedMaxWidth3 = (LayoutParams) child3.getLayoutParams();
                            totalWeight2 = weightedMaxWidth3.weight;
                            if (totalWeight2 > 0.0f) {
                                LayoutParams lp5 = weightedMaxWidth3;
                                delta = alternativeMaxWidth;
                                child3.measure(MeasureSpec.makeMeasureSpec(child3.getMeasuredWidth(), ErrorDialogData.SUPPRESSED), MeasureSpec.makeMeasureSpec(largestChildHeight, ErrorDialogData.SUPPRESSED));
                            } else {
                                delta = alternativeMaxWidth;
                            }
                        }
                    } else {
                        i8 = weightedMaxWidth2;
                        totalWeight = totalWeight2;
                        delta = alternativeMaxWidth;
                    }
                    i9 = heightMode2 + 1;
                    i5 = heightSize;
                    weightedMaxWidth2 = i8;
                    totalWeight2 = totalWeight;
                    alternativeMaxWidth = delta;
                }
            }
            i8 = weightedMaxWidth2;
            totalWeight = totalWeight2;
            delta = alternativeMaxWidth;
            int i10 = maxWidth2;
            z = useLargestChild;
            i = largestChildHeight;
            i2 = baselineChildIndex;
            alternativeMaxWidth = maxWidth;
            heightMode2 = delta;
            largestChildHeight = widthMeasureSpec;
            if (!(allFillParent || widthMode == ErrorDialogData.SUPPRESSED)) {
                alternativeMaxWidth = count2;
            }
            setMeasuredDimension(View.resolveSizeAndState(Math.max(alternativeMaxWidth + (getPaddingLeft() + getPaddingRight()), getSuggestedMinimumWidth()), largestChildHeight, i3), i4);
            if (matchWidth) {
                forceUniformWidth(childState, largestChildHeight2);
            }
        } else {
            heightSize = i5;
            i8 = weightedMaxWidth2;
            totalWeight = totalWeight2;
            delta = alternativeMaxWidth;
        }
        totalWeight2 = r7.mWeightSum > 0.0f ? r7.mWeightSum : totalWeight;
        r7.mTotalLength = 0;
        i5 = 0;
        alternativeMaxWidth = maxWidth;
        heightMode2 = delta;
        while (i5 < childState) {
            child = getVirtualChildAt(i5);
            z = useLargestChild;
            i = largestChildHeight;
            if (child.getVisibility() == 8) {
                i10 = maxWidth2;
                i2 = baselineChildIndex;
                largestChildHeight = widthMeasureSpec;
            } else {
                float weightSum;
                boolean matchWidthLocally2;
                boolean allFillParent3;
                int alternativeMaxWidth2;
                LayoutParams lp6 = (LayoutParams) child.getLayoutParams();
                float childExtra = lp6.weight;
                if (childExtra > 0.0f) {
                    i2 = baselineChildIndex;
                    baselineChildIndex = (int) ((((float) heightMode2) * childExtra) / totalWeight2);
                    float weightSum2 = totalWeight2 - childExtra;
                    int delta2 = heightMode2 - baselineChildIndex;
                    margin = getChildMeasureSpec(widthMeasureSpec, ((getPaddingLeft() + getPaddingRight()) + lp6.leftMargin) + lp6.rightMargin, lp6.width);
                    if (lp6.height != 0) {
                        i10 = maxWidth2;
                    } else if (maxWidth2 != ErrorDialogData.SUPPRESSED) {
                        i10 = maxWidth2;
                    } else {
                        i10 = maxWidth2;
                        child.measure(margin, MeasureSpec.makeMeasureSpec(baselineChildIndex > 0 ? baselineChildIndex : 0, ErrorDialogData.SUPPRESSED));
                        int i11 = baselineChildIndex;
                        i3 = View.combineMeasuredStates(i3, child.getMeasuredState() & InputDeviceCompat.SOURCE_ANY);
                        totalWeight2 = weightSum2;
                        heightMode2 = delta2;
                    }
                    maxWidth2 = child.getMeasuredHeight() + baselineChildIndex;
                    if (maxWidth2 < 0) {
                        maxWidth2 = 0;
                    }
                    child.measure(margin, MeasureSpec.makeMeasureSpec(maxWidth2, ErrorDialogData.SUPPRESSED));
                    i3 = View.combineMeasuredStates(i3, child.getMeasuredState() & InputDeviceCompat.SOURCE_ANY);
                    totalWeight2 = weightSum2;
                    heightMode2 = delta2;
                } else {
                    i10 = maxWidth2;
                    float f = childExtra;
                    i2 = baselineChildIndex;
                    largestChildHeight = widthMeasureSpec;
                }
                maxWidth2 = lp6.leftMargin + lp6.rightMargin;
                baselineChildIndex = child.getMeasuredWidth() + maxWidth2;
                alternativeMaxWidth = Math.max(alternativeMaxWidth, baselineChildIndex);
                int margin2 = maxWidth2;
                if (widthMode != ErrorDialogData.SUPPRESSED) {
                    weightSum = totalWeight2;
                    if (lp6.width == -1) {
                        matchWidthLocally = true;
                        margin = Math.max(count2, matchWidthLocally ? margin2 : baselineChildIndex);
                        if (allFillParent) {
                            matchWidthLocally2 = matchWidthLocally;
                        } else {
                            matchWidthLocally2 = matchWidthLocally;
                            if (lp6.width == -1) {
                                allFillParent3 = true;
                                maxWidth2 = r7.mTotalLength;
                                alternativeMaxWidth2 = margin;
                                r7.mTotalLength = Math.max(maxWidth2, (((maxWidth2 + child.getMeasuredHeight()) + lp6.topMargin) + lp6.bottomMargin) + getNextLocationOffset(child));
                                allFillParent = allFillParent3;
                                totalWeight2 = weightSum;
                                count2 = alternativeMaxWidth2;
                            }
                        }
                        allFillParent3 = false;
                        maxWidth2 = r7.mTotalLength;
                        alternativeMaxWidth2 = margin;
                        r7.mTotalLength = Math.max(maxWidth2, (((maxWidth2 + child.getMeasuredHeight()) + lp6.topMargin) + lp6.bottomMargin) + getNextLocationOffset(child));
                        allFillParent = allFillParent3;
                        totalWeight2 = weightSum;
                        count2 = alternativeMaxWidth2;
                    }
                } else {
                    weightSum = totalWeight2;
                }
                matchWidthLocally = false;
                if (matchWidthLocally) {
                }
                margin = Math.max(count2, matchWidthLocally ? margin2 : baselineChildIndex);
                if (allFillParent) {
                    matchWidthLocally2 = matchWidthLocally;
                } else {
                    matchWidthLocally2 = matchWidthLocally;
                    if (lp6.width == -1) {
                        allFillParent3 = true;
                        maxWidth2 = r7.mTotalLength;
                        alternativeMaxWidth2 = margin;
                        r7.mTotalLength = Math.max(maxWidth2, (((maxWidth2 + child.getMeasuredHeight()) + lp6.topMargin) + lp6.bottomMargin) + getNextLocationOffset(child));
                        allFillParent = allFillParent3;
                        totalWeight2 = weightSum;
                        count2 = alternativeMaxWidth2;
                    }
                }
                allFillParent3 = false;
                maxWidth2 = r7.mTotalLength;
                alternativeMaxWidth2 = margin;
                r7.mTotalLength = Math.max(maxWidth2, (((maxWidth2 + child.getMeasuredHeight()) + lp6.topMargin) + lp6.bottomMargin) + getNextLocationOffset(child));
                allFillParent = allFillParent3;
                totalWeight2 = weightSum;
                count2 = alternativeMaxWidth2;
            }
            i5++;
            useLargestChild = z;
            largestChildHeight = i;
            baselineChildIndex = i2;
            maxWidth2 = i10;
        }
        z = useLargestChild;
        i = largestChildHeight;
        i2 = baselineChildIndex;
        largestChildHeight = widthMeasureSpec;
        r7.mTotalLength += getPaddingTop() + getPaddingBottom();
        alternativeMaxWidth = count2;
        setMeasuredDimension(View.resolveSizeAndState(Math.max(alternativeMaxWidth + (getPaddingLeft() + getPaddingRight()), getSuggestedMinimumWidth()), largestChildHeight, i3), i4);
        if (matchWidth) {
            forceUniformWidth(childState, largestChildHeight2);
        }
    }

    private void forceUniformWidth(int count, int heightMeasureSpec) {
        int uniformMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), ErrorDialogData.SUPPRESSED);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
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

    void measureHorizontal(int widthMeasureSpec, int heightMeasureSpec) {
        boolean baselineAligned;
        int widthMode;
        int childBaseline;
        int alternativeMaxHeight;
        int i;
        int maxHeight;
        View child;
        LayoutParams lp;
        int i2;
        int i3;
        float totalWeight;
        int i4;
        int widthSize;
        int alternativeMaxHeight2;
        int i5;
        int widthSizeAndState;
        int count;
        int i6;
        boolean z;
        float weightSum;
        float weightSum2;
        View child2;
        LayoutParams useLargestChild;
        float childExtra;
        float weightSum3;
        int i7;
        boolean widthMode2;
        boolean widthSizeAndState2;
        int i8 = widthMeasureSpec;
        int i9 = heightMeasureSpec;
        this.mTotalLength = 0;
        float totalWeight2 = 0.0f;
        int count2 = getVirtualChildCount();
        int widthMode3 = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (this.mMaxAscent == null || r7.mMaxDescent == null) {
            r7.mMaxAscent = new int[4];
            r7.mMaxDescent = new int[4];
        }
        int[] maxAscent = r7.mMaxAscent;
        int[] maxDescent = r7.mMaxDescent;
        boolean matchHeight = false;
        maxAscent[3] = -1;
        maxAscent[2] = -1;
        maxAscent[1] = -1;
        maxAscent[0] = -1;
        maxDescent[3] = -1;
        maxDescent[2] = -1;
        maxDescent[1] = -1;
        maxDescent[0] = -1;
        boolean baselineAligned2 = r7.mBaselineAligned;
        boolean skippedMeasure = false;
        boolean useLargestChild2 = r7.mUseLargestChild;
        boolean isExactly = widthMode3 == ErrorDialogData.SUPPRESSED;
        int childState = 0;
        int largestChildWidth = 0;
        boolean matchHeight2 = matchHeight;
        matchHeight = true;
        int maxHeight2 = 0;
        int i10 = 0;
        int alternativeMaxHeight3 = 0;
        int weightedMaxHeight = 0;
        while (i10 < count2) {
            View child3 = getVirtualChildAt(i10);
            int largestChildWidth2;
            if (child3 == null) {
                largestChildWidth2 = largestChildWidth;
                r7.mTotalLength += measureNullChild(i10);
                baselineAligned = baselineAligned2;
                widthMode = widthMode3;
                largestChildWidth = largestChildWidth2;
            } else {
                largestChildWidth2 = largestChildWidth;
                int weightedMaxHeight2 = weightedMaxHeight;
                if (child3.getVisibility() == 8) {
                    i10 += getChildrenSkipCount(child3, i10);
                    baselineAligned = baselineAligned2;
                    widthMode = widthMode3;
                    largestChildWidth = largestChildWidth2;
                    weightedMaxHeight = weightedMaxHeight2;
                } else {
                    LayoutParams lp2;
                    int alternativeMaxHeight4;
                    int maxHeight3;
                    int weightedMaxHeight3;
                    int i11;
                    boolean matchHeightLocally;
                    int margin;
                    boolean allFillParent;
                    if (hasDividerBeforeChildAt(i10)) {
                        r7.mTotalLength += r7.mDividerWidth;
                    }
                    LayoutParams lp3 = (LayoutParams) child3.getLayoutParams();
                    float totalWeight3 = totalWeight2 + lp3.weight;
                    int i12;
                    if (widthMode3 == ErrorDialogData.SUPPRESSED && lp3.width == 0 && lp3.weight > 0.0f) {
                        if (isExactly) {
                            i12 = i10;
                            r7.mTotalLength += lp3.leftMargin + lp3.rightMargin;
                        } else {
                            i12 = i10;
                            i10 = r7.mTotalLength;
                            r7.mTotalLength = Math.max(i10, (lp3.leftMargin + i10) + lp3.rightMargin);
                        }
                        if (baselineAligned2) {
                            largestChildWidth = MeasureSpec.makeMeasureSpec(0, 0);
                            child3.measure(largestChildWidth, largestChildWidth);
                            lp2 = lp3;
                            alternativeMaxHeight4 = alternativeMaxHeight3;
                            maxHeight3 = maxHeight2;
                            baselineAligned = baselineAligned2;
                            widthMode = widthMode3;
                            lp3 = largestChildWidth2;
                            weightedMaxHeight3 = weightedMaxHeight2;
                            i11 = i12;
                            widthMode3 = -1;
                        } else {
                            skippedMeasure = true;
                            lp2 = lp3;
                            alternativeMaxHeight4 = alternativeMaxHeight3;
                            maxHeight3 = maxHeight2;
                            baselineAligned = baselineAligned2;
                            widthMode = widthMode3;
                            weightedMaxHeight3 = weightedMaxHeight2;
                            i11 = i12;
                            widthMode3 = -1;
                            matchHeightLocally = false;
                            matchHeight2 = true;
                            matchHeightLocally = true;
                            weightedMaxHeight = lp2.topMargin + lp2.bottomMargin;
                            alternativeMaxHeight3 = child3.getMeasuredHeight() + weightedMaxHeight;
                            maxHeight2 = View.combineMeasuredStates(childState, child3.getMeasuredState());
                            if (baselineAligned) {
                                childBaseline = child3.getBaseline();
                                if (childBaseline != widthMode3) {
                                    if (lp2.gravity < 0) {
                                    }
                                    i8 = ((((lp2.gravity < 0 ? r7.mGravity : lp2.gravity) & 112) >> 4) & -2) >> 1;
                                    maxAscent[i8] = Math.max(maxAscent[i8], childBaseline);
                                    margin = weightedMaxHeight;
                                    maxDescent[i8] = Math.max(maxDescent[i8], alternativeMaxHeight3 - childBaseline);
                                    weightedMaxHeight = Math.max(maxHeight3, alternativeMaxHeight3);
                                    if (!matchHeight) {
                                    }
                                    if (lp2.weight <= 0.0f) {
                                        if (matchHeightLocally) {
                                        }
                                        widthMode3 = Math.max(weightedMaxHeight3, matchHeightLocally ? margin : alternativeMaxHeight3);
                                        alternativeMaxHeight = alternativeMaxHeight4;
                                    } else {
                                        widthMode3 = weightedMaxHeight3;
                                        if (matchHeightLocally) {
                                        }
                                        alternativeMaxHeight = Math.max(alternativeMaxHeight4, matchHeightLocally ? margin : alternativeMaxHeight3);
                                    }
                                    i8 = i11;
                                    i10 = i8 + getChildrenSkipCount(child3, i8);
                                    childState = maxHeight2;
                                    matchHeight = allFillParent;
                                    alternativeMaxHeight3 = alternativeMaxHeight;
                                    totalWeight2 = totalWeight3;
                                    largestChildWidth = largestChildWidth2;
                                    maxHeight2 = weightedMaxHeight;
                                    weightedMaxHeight = widthMode3;
                                }
                            }
                            margin = weightedMaxHeight;
                            weightedMaxHeight = Math.max(maxHeight3, alternativeMaxHeight3);
                            if (matchHeight) {
                            }
                            if (lp2.weight <= 0.0f) {
                                widthMode3 = weightedMaxHeight3;
                                if (matchHeightLocally) {
                                }
                                alternativeMaxHeight = Math.max(alternativeMaxHeight4, matchHeightLocally ? margin : alternativeMaxHeight3);
                            } else {
                                if (matchHeightLocally) {
                                }
                                widthMode3 = Math.max(weightedMaxHeight3, matchHeightLocally ? margin : alternativeMaxHeight3);
                                alternativeMaxHeight = alternativeMaxHeight4;
                            }
                            i8 = i11;
                            i10 = i8 + getChildrenSkipCount(child3, i8);
                            childState = maxHeight2;
                            matchHeight = allFillParent;
                            alternativeMaxHeight3 = alternativeMaxHeight;
                            totalWeight2 = totalWeight3;
                            largestChildWidth = largestChildWidth2;
                            maxHeight2 = weightedMaxHeight;
                            weightedMaxHeight = widthMode3;
                        }
                    } else {
                        i12 = i10;
                        i10 = Integer.MIN_VALUE;
                        if (lp3.width == 0 && lp3.weight > 0.0f) {
                            i10 = 0;
                            lp3.width = -2;
                        }
                        i11 = i12;
                        int largestChildWidth3 = largestChildWidth2;
                        LayoutParams lp4 = lp3;
                        weightedMaxHeight3 = weightedMaxHeight2;
                        alternativeMaxHeight4 = alternativeMaxHeight3;
                        maxHeight3 = maxHeight2;
                        i8 = i10;
                        baselineAligned = baselineAligned2;
                        widthMode = widthMode3;
                        widthMode3 = -1;
                        measureChildBeforeLayout(child3, i11, widthMeasureSpec, totalWeight3 == 0.0f ? r7.mTotalLength : 0, heightMeasureSpec, 0);
                        if (i8 != Integer.MIN_VALUE) {
                            lp2 = lp4;
                            lp2.width = i8;
                        } else {
                            lp2 = lp4;
                        }
                        largestChildWidth = child3.getMeasuredWidth();
                        if (isExactly) {
                            r7.mTotalLength += ((lp2.leftMargin + largestChildWidth) + lp2.rightMargin) + getNextLocationOffset(child3);
                        } else {
                            weightedMaxHeight = r7.mTotalLength;
                            r7.mTotalLength = Math.max(weightedMaxHeight, (((weightedMaxHeight + largestChildWidth) + lp2.leftMargin) + lp2.rightMargin) + getNextLocationOffset(child3));
                        }
                        if (useLargestChild2) {
                            largestChildWidth2 = Math.max(largestChildWidth, largestChildWidth3);
                            matchHeightLocally = false;
                            if (heightMode != ErrorDialogData.SUPPRESSED && lp2.height == widthMode3) {
                                matchHeight2 = true;
                                matchHeightLocally = true;
                            }
                            weightedMaxHeight = lp2.topMargin + lp2.bottomMargin;
                            alternativeMaxHeight3 = child3.getMeasuredHeight() + weightedMaxHeight;
                            maxHeight2 = View.combineMeasuredStates(childState, child3.getMeasuredState());
                            if (baselineAligned) {
                                childBaseline = child3.getBaseline();
                                if (childBaseline != widthMode3) {
                                    i8 = ((((lp2.gravity < 0 ? r7.mGravity : lp2.gravity) & 112) >> 4) & -2) >> 1;
                                    maxAscent[i8] = Math.max(maxAscent[i8], childBaseline);
                                    margin = weightedMaxHeight;
                                    maxDescent[i8] = Math.max(maxDescent[i8], alternativeMaxHeight3 - childBaseline);
                                    weightedMaxHeight = Math.max(maxHeight3, alternativeMaxHeight3);
                                    allFillParent = matchHeight && lp2.height == -1;
                                    if (lp2.weight <= 0.0f) {
                                        widthMode3 = Math.max(weightedMaxHeight3, matchHeightLocally ? margin : alternativeMaxHeight3);
                                        alternativeMaxHeight = alternativeMaxHeight4;
                                    } else {
                                        widthMode3 = weightedMaxHeight3;
                                        alternativeMaxHeight = Math.max(alternativeMaxHeight4, matchHeightLocally ? margin : alternativeMaxHeight3);
                                    }
                                    i8 = i11;
                                    i10 = i8 + getChildrenSkipCount(child3, i8);
                                    childState = maxHeight2;
                                    matchHeight = allFillParent;
                                    alternativeMaxHeight3 = alternativeMaxHeight;
                                    totalWeight2 = totalWeight3;
                                    largestChildWidth = largestChildWidth2;
                                    maxHeight2 = weightedMaxHeight;
                                    weightedMaxHeight = widthMode3;
                                }
                            }
                            margin = weightedMaxHeight;
                            weightedMaxHeight = Math.max(maxHeight3, alternativeMaxHeight3);
                            if (matchHeight) {
                            }
                            if (lp2.weight <= 0.0f) {
                                widthMode3 = weightedMaxHeight3;
                                if (matchHeightLocally) {
                                }
                                alternativeMaxHeight = Math.max(alternativeMaxHeight4, matchHeightLocally ? margin : alternativeMaxHeight3);
                            } else {
                                if (matchHeightLocally) {
                                }
                                widthMode3 = Math.max(weightedMaxHeight3, matchHeightLocally ? margin : alternativeMaxHeight3);
                                alternativeMaxHeight = alternativeMaxHeight4;
                            }
                            i8 = i11;
                            i10 = i8 + getChildrenSkipCount(child3, i8);
                            childState = maxHeight2;
                            matchHeight = allFillParent;
                            alternativeMaxHeight3 = alternativeMaxHeight;
                            totalWeight2 = totalWeight3;
                            largestChildWidth = largestChildWidth2;
                            maxHeight2 = weightedMaxHeight;
                            weightedMaxHeight = widthMode3;
                        } else {
                            lp3 = largestChildWidth3;
                        }
                    }
                    largestChildWidth2 = lp3;
                    matchHeightLocally = false;
                    matchHeight2 = true;
                    matchHeightLocally = true;
                    weightedMaxHeight = lp2.topMargin + lp2.bottomMargin;
                    alternativeMaxHeight3 = child3.getMeasuredHeight() + weightedMaxHeight;
                    maxHeight2 = View.combineMeasuredStates(childState, child3.getMeasuredState());
                    if (baselineAligned) {
                        childBaseline = child3.getBaseline();
                        if (childBaseline != widthMode3) {
                            if (lp2.gravity < 0) {
                            }
                            i8 = ((((lp2.gravity < 0 ? r7.mGravity : lp2.gravity) & 112) >> 4) & -2) >> 1;
                            maxAscent[i8] = Math.max(maxAscent[i8], childBaseline);
                            margin = weightedMaxHeight;
                            maxDescent[i8] = Math.max(maxDescent[i8], alternativeMaxHeight3 - childBaseline);
                            weightedMaxHeight = Math.max(maxHeight3, alternativeMaxHeight3);
                            if (matchHeight) {
                            }
                            if (lp2.weight <= 0.0f) {
                                if (matchHeightLocally) {
                                }
                                widthMode3 = Math.max(weightedMaxHeight3, matchHeightLocally ? margin : alternativeMaxHeight3);
                                alternativeMaxHeight = alternativeMaxHeight4;
                            } else {
                                widthMode3 = weightedMaxHeight3;
                                if (matchHeightLocally) {
                                }
                                alternativeMaxHeight = Math.max(alternativeMaxHeight4, matchHeightLocally ? margin : alternativeMaxHeight3);
                            }
                            i8 = i11;
                            i10 = i8 + getChildrenSkipCount(child3, i8);
                            childState = maxHeight2;
                            matchHeight = allFillParent;
                            alternativeMaxHeight3 = alternativeMaxHeight;
                            totalWeight2 = totalWeight3;
                            largestChildWidth = largestChildWidth2;
                            maxHeight2 = weightedMaxHeight;
                            weightedMaxHeight = widthMode3;
                        }
                    }
                    margin = weightedMaxHeight;
                    weightedMaxHeight = Math.max(maxHeight3, alternativeMaxHeight3);
                    if (matchHeight) {
                    }
                    if (lp2.weight <= 0.0f) {
                        widthMode3 = weightedMaxHeight3;
                        if (matchHeightLocally) {
                        }
                        alternativeMaxHeight = Math.max(alternativeMaxHeight4, matchHeightLocally ? margin : alternativeMaxHeight3);
                    } else {
                        if (matchHeightLocally) {
                        }
                        widthMode3 = Math.max(weightedMaxHeight3, matchHeightLocally ? margin : alternativeMaxHeight3);
                        alternativeMaxHeight = alternativeMaxHeight4;
                    }
                    i8 = i11;
                    i10 = i8 + getChildrenSkipCount(child3, i8);
                    childState = maxHeight2;
                    matchHeight = allFillParent;
                    alternativeMaxHeight3 = alternativeMaxHeight;
                    totalWeight2 = totalWeight3;
                    largestChildWidth = largestChildWidth2;
                    maxHeight2 = weightedMaxHeight;
                    weightedMaxHeight = widthMode3;
                }
            }
            i10++;
            baselineAligned2 = baselineAligned;
            widthMode3 = widthMode;
            i8 = widthMeasureSpec;
        }
        i8 = maxHeight2;
        baselineAligned = baselineAligned2;
        widthMode = widthMode3;
        alternativeMaxHeight = childState;
        widthMode3 = weightedMaxHeight;
        weightedMaxHeight = largestChildWidth;
        if (r7.mTotalLength > 0 && hasDividerBeforeChildAt(count2)) {
            r7.mTotalLength += r7.mDividerWidth;
        }
        if (maxAscent[1] == -1 && maxAscent[0] == -1 && maxAscent[2] == -1) {
            if (maxAscent[3] == -1) {
                i = alternativeMaxHeight;
                maxHeight2 = i8;
                if (useLargestChild2) {
                    maxHeight = maxHeight2;
                    i10 = widthMode;
                } else {
                    i10 = widthMode;
                    if (i10 != Integer.MIN_VALUE) {
                        if (i10 == 0) {
                            maxHeight = maxHeight2;
                        }
                    }
                    r7.mTotalLength = 0;
                    largestChildWidth = 0;
                    while (largestChildWidth < count2) {
                        child = getVirtualChildAt(largestChildWidth);
                        if (child == null) {
                            r7.mTotalLength += measureNullChild(largestChildWidth);
                        } else if (child.getVisibility() != 8) {
                            largestChildWidth += getChildrenSkipCount(child, largestChildWidth);
                        } else {
                            lp = (LayoutParams) child.getLayoutParams();
                            if (isExactly) {
                                i2 = largestChildWidth;
                                maxHeight = maxHeight2;
                                largestChildWidth = r7.mTotalLength;
                                r7.mTotalLength = Math.max(largestChildWidth, (((largestChildWidth + weightedMaxHeight) + lp.leftMargin) + lp.rightMargin) + getNextLocationOffset(child));
                            } else {
                                i2 = largestChildWidth;
                                maxHeight = maxHeight2;
                                r7.mTotalLength += ((lp.leftMargin + weightedMaxHeight) + lp.rightMargin) + getNextLocationOffset(child);
                            }
                            largestChildWidth = i2 + 1;
                            maxHeight2 = maxHeight;
                        }
                        i2 = largestChildWidth;
                        maxHeight = maxHeight2;
                        largestChildWidth = i2 + 1;
                        maxHeight2 = maxHeight;
                    }
                    maxHeight = maxHeight2;
                }
                r7.mTotalLength += getPaddingLeft() + getPaddingRight();
                i8 = View.resolveSizeAndState(Math.max(r7.mTotalLength, getSuggestedMinimumWidth()), widthMeasureSpec, 0);
                largestChildWidth = i8 & ViewCompat.MEASURED_SIZE_MASK;
                alternativeMaxHeight = largestChildWidth - r7.mTotalLength;
                if (!skippedMeasure) {
                    i3 = weightedMaxHeight;
                    weightedMaxHeight = alternativeMaxHeight3;
                    totalWeight = totalWeight2;
                } else if (alternativeMaxHeight != 0 || totalWeight2 <= 0.0f) {
                    alternativeMaxHeight3 = Math.max(alternativeMaxHeight3, widthMode3);
                    if (useLargestChild2) {
                        if (i10 == ErrorDialogData.SUPPRESSED) {
                            i4 = 0;
                            while (true) {
                                i9 = i4;
                                if (i9 < count2) {
                                    break;
                                }
                                widthSize = largestChildWidth;
                                largestChildWidth = getVirtualChildAt(i9);
                                if (largestChildWidth == 0) {
                                    alternativeMaxHeight2 = alternativeMaxHeight3;
                                    totalWeight = totalWeight2;
                                    if (largestChildWidth.getVisibility() != 8) {
                                        i3 = weightedMaxHeight;
                                    } else {
                                        LayoutParams lp5 = (LayoutParams) largestChildWidth.getLayoutParams();
                                        totalWeight2 = lp5.weight;
                                        if (totalWeight2 <= 0.0f) {
                                            i3 = weightedMaxHeight;
                                            largestChildWidth.measure(MeasureSpec.makeMeasureSpec(weightedMaxHeight, ErrorDialogData.SUPPRESSED), MeasureSpec.makeMeasureSpec(largestChildWidth.getMeasuredHeight(), ErrorDialogData.SUPPRESSED));
                                        } else {
                                            i3 = weightedMaxHeight;
                                        }
                                    }
                                } else {
                                    i3 = weightedMaxHeight;
                                    alternativeMaxHeight2 = alternativeMaxHeight3;
                                    totalWeight = totalWeight2;
                                }
                                i4 = i9 + 1;
                                largestChildWidth = widthSize;
                                alternativeMaxHeight3 = alternativeMaxHeight2;
                                totalWeight2 = totalWeight;
                                weightedMaxHeight = i3;
                            }
                        } else {
                            i3 = weightedMaxHeight;
                            alternativeMaxHeight2 = alternativeMaxHeight3;
                            totalWeight = totalWeight2;
                            alternativeMaxHeight3 = ErrorDialogData.SUPPRESSED;
                            i5 = i10;
                            i10 = alternativeMaxHeight;
                            widthSizeAndState = i8;
                            count = count2;
                            i6 = widthMode3;
                            z = useLargestChild2;
                            alternativeMaxHeight = heightMeasureSpec;
                            if (!(matchHeight || heightMode == ErrorDialogData.SUPPRESSED)) {
                                maxHeight = alternativeMaxHeight2;
                            }
                            setMeasuredDimension(widthSizeAndState | (i & ViewCompat.MEASURED_STATE_MASK), View.resolveSizeAndState(Math.max(maxHeight + (getPaddingTop() + getPaddingBottom()), getSuggestedMinimumHeight()), alternativeMaxHeight, i << 16));
                            if (matchHeight2) {
                                weightedMaxHeight = widthMeasureSpec;
                                return;
                            }
                            forceUniformHeight(count, widthMeasureSpec);
                        }
                    }
                    widthSize = largestChildWidth;
                    i3 = weightedMaxHeight;
                    alternativeMaxHeight2 = alternativeMaxHeight3;
                    totalWeight = totalWeight2;
                    i5 = i10;
                    i10 = alternativeMaxHeight;
                    widthSizeAndState = i8;
                    count = count2;
                    i6 = widthMode3;
                    z = useLargestChild2;
                    alternativeMaxHeight = heightMeasureSpec;
                    maxHeight = alternativeMaxHeight2;
                    setMeasuredDimension(widthSizeAndState | (i & ViewCompat.MEASURED_STATE_MASK), View.resolveSizeAndState(Math.max(maxHeight + (getPaddingTop() + getPaddingBottom()), getSuggestedMinimumHeight()), alternativeMaxHeight, i << 16));
                    if (matchHeight2) {
                        weightedMaxHeight = widthMeasureSpec;
                        return;
                    }
                    forceUniformHeight(count, widthMeasureSpec);
                } else {
                    widthSize = largestChildWidth;
                    i3 = weightedMaxHeight;
                    weightedMaxHeight = alternativeMaxHeight3;
                    totalWeight = totalWeight2;
                }
                weightSum = r7.mWeightSum <= 0.0f ? r7.mWeightSum : totalWeight;
                maxAscent[3] = -1;
                maxAscent[2] = -1;
                maxAscent[1] = -1;
                maxAscent[0] = -1;
                maxDescent[3] = -1;
                maxDescent[2] = -1;
                maxDescent[1] = -1;
                maxDescent[0] = -1;
                childBaseline = -1;
                r7.mTotalLength = 0;
                alternativeMaxHeight3 = weightedMaxHeight;
                i9 = i;
                weightSum2 = weightSum;
                largestChildWidth = 0;
                while (largestChildWidth < count2) {
                    i6 = widthMode3;
                    child2 = getVirtualChildAt(largestChildWidth);
                    if (child2 == null) {
                        z = useLargestChild2;
                        if (child2.getVisibility() != 8) {
                            i5 = i10;
                            i10 = alternativeMaxHeight;
                            widthSizeAndState = i8;
                            count = count2;
                            alternativeMaxHeight = heightMeasureSpec;
                        } else {
                            useLargestChild = (LayoutParams) child2.getLayoutParams();
                            childExtra = useLargestChild.weight;
                            if (childExtra <= 0.0f) {
                                count = count2;
                                count2 = (int) ((((float) alternativeMaxHeight) * childExtra) / weightSum2);
                                alternativeMaxHeight -= count2;
                                weightSum3 = weightSum2 - childExtra;
                                weightSum2 = ((getPaddingTop() + getPaddingBottom()) + useLargestChild.topMargin) + useLargestChild.bottomMargin;
                                maxHeight2 = useLargestChild.height;
                                i7 = alternativeMaxHeight;
                                widthSizeAndState = i8;
                                alternativeMaxHeight = heightMeasureSpec;
                                i8 = ErrorDialogData.SUPPRESSED;
                                weightSum2 = getChildMeasureSpec(alternativeMaxHeight, weightSum2, maxHeight2);
                                if (useLargestChild.width == 0) {
                                    if (i10 != ErrorDialogData.SUPPRESSED) {
                                        child2.measure(MeasureSpec.makeMeasureSpec(count2 <= 0 ? count2 : 0, ErrorDialogData.SUPPRESSED), weightSum2);
                                        i5 = i10;
                                        i9 = View.combineMeasuredStates(i9, child2.getMeasuredState() & ViewCompat.MEASURED_STATE_MASK);
                                    }
                                }
                                maxHeight2 = child2.getMeasuredWidth() + count2;
                                if (maxHeight2 < 0) {
                                    maxHeight2 = 0;
                                }
                                i5 = i10;
                                child2.measure(MeasureSpec.makeMeasureSpec(maxHeight2, ErrorDialogData.SUPPRESSED), weightSum2);
                                i9 = View.combineMeasuredStates(i9, child2.getMeasuredState() & ViewCompat.MEASURED_STATE_MASK);
                            } else {
                                i5 = i10;
                                float f = childExtra;
                                i10 = alternativeMaxHeight;
                                widthSizeAndState = i8;
                                count = count2;
                                alternativeMaxHeight = heightMeasureSpec;
                                i8 = ErrorDialogData.SUPPRESSED;
                                i7 = i10;
                                weightSum3 = weightSum2;
                            }
                            if (isExactly) {
                                i10 = r7.mTotalLength;
                                r7.mTotalLength = Math.max(i10, (((child2.getMeasuredWidth() + i10) + useLargestChild.leftMargin) + useLargestChild.rightMargin) + getNextLocationOffset(child2));
                            } else {
                                r7.mTotalLength += ((child2.getMeasuredWidth() + useLargestChild.leftMargin) + useLargestChild.rightMargin) + getNextLocationOffset(child2);
                            }
                            widthMode2 = heightMode == i8 && useLargestChild.height == -1;
                            weightedMaxHeight = useLargestChild.topMargin + useLargestChild.bottomMargin;
                            maxHeight2 = child2.getMeasuredHeight() + weightedMaxHeight;
                            childBaseline = Math.max(childBaseline, maxHeight2);
                            alternativeMaxHeight3 = Math.max(alternativeMaxHeight3, widthMode2 ? weightedMaxHeight : maxHeight2);
                            widthSizeAndState2 = matchHeight && useLargestChild.height == -1;
                            if (baselineAligned) {
                                count2 = child2.getBaseline();
                                boolean matchHeightLocally2 = widthMode2;
                                if (count2 != -1) {
                                    i10 = (useLargestChild.gravity >= 0 ? r7.mGravity : useLargestChild.gravity) & 112;
                                    int index = ((i10 >> 4) & -2) >> 1;
                                    int gravity = i10;
                                    maxAscent[index] = Math.max(maxAscent[index], count2);
                                    maxDescent[index] = Math.max(maxDescent[index], maxHeight2 - count2);
                                    matchHeight = widthSizeAndState2;
                                    weightSum2 = weightSum3;
                                    largestChildWidth++;
                                    widthMode3 = i6;
                                    useLargestChild2 = z;
                                    count2 = count;
                                    alternativeMaxHeight = i7;
                                    i8 = widthSizeAndState;
                                    i10 = i5;
                                    maxHeight2 = widthMeasureSpec;
                                }
                            }
                            matchHeight = widthSizeAndState2;
                            weightSum2 = weightSum3;
                            largestChildWidth++;
                            widthMode3 = i6;
                            useLargestChild2 = z;
                            count2 = count;
                            alternativeMaxHeight = i7;
                            i8 = widthSizeAndState;
                            i10 = i5;
                            maxHeight2 = widthMeasureSpec;
                        }
                    } else {
                        i5 = i10;
                        i10 = alternativeMaxHeight;
                        widthSizeAndState = i8;
                        count = count2;
                        z = useLargestChild2;
                        alternativeMaxHeight = heightMeasureSpec;
                    }
                    i7 = i10;
                    largestChildWidth++;
                    widthMode3 = i6;
                    useLargestChild2 = z;
                    count2 = count;
                    alternativeMaxHeight = i7;
                    i8 = widthSizeAndState;
                    i10 = i5;
                    maxHeight2 = widthMeasureSpec;
                }
                widthSizeAndState = i8;
                count = count2;
                i6 = widthMode3;
                z = useLargestChild2;
                alternativeMaxHeight = heightMeasureSpec;
                r7.mTotalLength += getPaddingLeft() + getPaddingRight();
                if (maxAscent[1] == -1 && maxAscent[0] == -1 && maxAscent[2] == -1) {
                    if (maxAscent[3] != -1) {
                        maxHeight2 = childBaseline;
                        alternativeMaxHeight2 = alternativeMaxHeight3;
                        maxHeight = maxHeight2;
                        i = i9;
                        maxHeight = alternativeMaxHeight2;
                        setMeasuredDimension(widthSizeAndState | (i & ViewCompat.MEASURED_STATE_MASK), View.resolveSizeAndState(Math.max(maxHeight + (getPaddingTop() + getPaddingBottom()), getSuggestedMinimumHeight()), alternativeMaxHeight, i << 16));
                        if (matchHeight2) {
                            forceUniformHeight(count, widthMeasureSpec);
                        }
                        weightedMaxHeight = widthMeasureSpec;
                        return;
                    }
                }
                maxHeight2 = Math.max(childBaseline, Math.max(maxAscent[3], Math.max(maxAscent[0], Math.max(maxAscent[1], maxAscent[2]))) + Math.max(maxDescent[3], Math.max(maxDescent[0], Math.max(maxDescent[1], maxDescent[2]))));
                alternativeMaxHeight2 = alternativeMaxHeight3;
                maxHeight = maxHeight2;
                i = i9;
                maxHeight = alternativeMaxHeight2;
                setMeasuredDimension(widthSizeAndState | (i & ViewCompat.MEASURED_STATE_MASK), View.resolveSizeAndState(Math.max(maxHeight + (getPaddingTop() + getPaddingBottom()), getSuggestedMinimumHeight()), alternativeMaxHeight, i << 16));
                if (matchHeight2) {
                    weightedMaxHeight = widthMeasureSpec;
                    return;
                }
                forceUniformHeight(count, widthMeasureSpec);
            }
        }
        i = alternativeMaxHeight;
        maxHeight2 = Math.max(i8, Math.max(maxAscent[3], Math.max(maxAscent[0], Math.max(maxAscent[1], maxAscent[2]))) + Math.max(maxDescent[3], Math.max(maxDescent[0], Math.max(maxDescent[1], maxDescent[2]))));
        if (useLargestChild2) {
            maxHeight = maxHeight2;
            i10 = widthMode;
        } else {
            i10 = widthMode;
            if (i10 != Integer.MIN_VALUE) {
                if (i10 == 0) {
                    maxHeight = maxHeight2;
                }
            }
            r7.mTotalLength = 0;
            largestChildWidth = 0;
            while (largestChildWidth < count2) {
                child = getVirtualChildAt(largestChildWidth);
                if (child == null) {
                    r7.mTotalLength += measureNullChild(largestChildWidth);
                } else if (child.getVisibility() != 8) {
                    lp = (LayoutParams) child.getLayoutParams();
                    if (isExactly) {
                        i2 = largestChildWidth;
                        maxHeight = maxHeight2;
                        largestChildWidth = r7.mTotalLength;
                        r7.mTotalLength = Math.max(largestChildWidth, (((largestChildWidth + weightedMaxHeight) + lp.leftMargin) + lp.rightMargin) + getNextLocationOffset(child));
                    } else {
                        i2 = largestChildWidth;
                        maxHeight = maxHeight2;
                        r7.mTotalLength += ((lp.leftMargin + weightedMaxHeight) + lp.rightMargin) + getNextLocationOffset(child);
                    }
                    largestChildWidth = i2 + 1;
                    maxHeight2 = maxHeight;
                } else {
                    largestChildWidth += getChildrenSkipCount(child, largestChildWidth);
                }
                i2 = largestChildWidth;
                maxHeight = maxHeight2;
                largestChildWidth = i2 + 1;
                maxHeight2 = maxHeight;
            }
            maxHeight = maxHeight2;
        }
        r7.mTotalLength += getPaddingLeft() + getPaddingRight();
        i8 = View.resolveSizeAndState(Math.max(r7.mTotalLength, getSuggestedMinimumWidth()), widthMeasureSpec, 0);
        largestChildWidth = i8 & ViewCompat.MEASURED_SIZE_MASK;
        alternativeMaxHeight = largestChildWidth - r7.mTotalLength;
        if (!skippedMeasure) {
            i3 = weightedMaxHeight;
            weightedMaxHeight = alternativeMaxHeight3;
            totalWeight = totalWeight2;
        } else {
            if (alternativeMaxHeight != 0) {
            }
            alternativeMaxHeight3 = Math.max(alternativeMaxHeight3, widthMode3);
            if (useLargestChild2) {
                if (i10 == ErrorDialogData.SUPPRESSED) {
                    i3 = weightedMaxHeight;
                    alternativeMaxHeight2 = alternativeMaxHeight3;
                    totalWeight = totalWeight2;
                    alternativeMaxHeight3 = ErrorDialogData.SUPPRESSED;
                    i5 = i10;
                    i10 = alternativeMaxHeight;
                    widthSizeAndState = i8;
                    count = count2;
                    i6 = widthMode3;
                    z = useLargestChild2;
                    alternativeMaxHeight = heightMeasureSpec;
                    maxHeight = alternativeMaxHeight2;
                    setMeasuredDimension(widthSizeAndState | (i & ViewCompat.MEASURED_STATE_MASK), View.resolveSizeAndState(Math.max(maxHeight + (getPaddingTop() + getPaddingBottom()), getSuggestedMinimumHeight()), alternativeMaxHeight, i << 16));
                    if (matchHeight2) {
                        forceUniformHeight(count, widthMeasureSpec);
                    }
                    weightedMaxHeight = widthMeasureSpec;
                    return;
                }
                i4 = 0;
                while (true) {
                    i9 = i4;
                    if (i9 < count2) {
                        break;
                    }
                    widthSize = largestChildWidth;
                    largestChildWidth = getVirtualChildAt(i9);
                    if (largestChildWidth == 0) {
                        i3 = weightedMaxHeight;
                        alternativeMaxHeight2 = alternativeMaxHeight3;
                        totalWeight = totalWeight2;
                    } else {
                        alternativeMaxHeight2 = alternativeMaxHeight3;
                        totalWeight = totalWeight2;
                        if (largestChildWidth.getVisibility() != 8) {
                            LayoutParams lp52 = (LayoutParams) largestChildWidth.getLayoutParams();
                            totalWeight2 = lp52.weight;
                            if (totalWeight2 <= 0.0f) {
                                i3 = weightedMaxHeight;
                            } else {
                                i3 = weightedMaxHeight;
                                largestChildWidth.measure(MeasureSpec.makeMeasureSpec(weightedMaxHeight, ErrorDialogData.SUPPRESSED), MeasureSpec.makeMeasureSpec(largestChildWidth.getMeasuredHeight(), ErrorDialogData.SUPPRESSED));
                            }
                        } else {
                            i3 = weightedMaxHeight;
                        }
                    }
                    i4 = i9 + 1;
                    largestChildWidth = widthSize;
                    alternativeMaxHeight3 = alternativeMaxHeight2;
                    totalWeight2 = totalWeight;
                    weightedMaxHeight = i3;
                }
            }
            widthSize = largestChildWidth;
            i3 = weightedMaxHeight;
            alternativeMaxHeight2 = alternativeMaxHeight3;
            totalWeight = totalWeight2;
            i5 = i10;
            i10 = alternativeMaxHeight;
            widthSizeAndState = i8;
            count = count2;
            i6 = widthMode3;
            z = useLargestChild2;
            alternativeMaxHeight = heightMeasureSpec;
            maxHeight = alternativeMaxHeight2;
            setMeasuredDimension(widthSizeAndState | (i & ViewCompat.MEASURED_STATE_MASK), View.resolveSizeAndState(Math.max(maxHeight + (getPaddingTop() + getPaddingBottom()), getSuggestedMinimumHeight()), alternativeMaxHeight, i << 16));
            if (matchHeight2) {
                weightedMaxHeight = widthMeasureSpec;
                return;
            }
            forceUniformHeight(count, widthMeasureSpec);
        }
        if (r7.mWeightSum <= 0.0f) {
        }
        weightSum = r7.mWeightSum <= 0.0f ? r7.mWeightSum : totalWeight;
        maxAscent[3] = -1;
        maxAscent[2] = -1;
        maxAscent[1] = -1;
        maxAscent[0] = -1;
        maxDescent[3] = -1;
        maxDescent[2] = -1;
        maxDescent[1] = -1;
        maxDescent[0] = -1;
        childBaseline = -1;
        r7.mTotalLength = 0;
        alternativeMaxHeight3 = weightedMaxHeight;
        i9 = i;
        weightSum2 = weightSum;
        largestChildWidth = 0;
        while (largestChildWidth < count2) {
            i6 = widthMode3;
            child2 = getVirtualChildAt(largestChildWidth);
            if (child2 == null) {
                i5 = i10;
                i10 = alternativeMaxHeight;
                widthSizeAndState = i8;
                count = count2;
                z = useLargestChild2;
                alternativeMaxHeight = heightMeasureSpec;
            } else {
                z = useLargestChild2;
                if (child2.getVisibility() != 8) {
                    useLargestChild = (LayoutParams) child2.getLayoutParams();
                    childExtra = useLargestChild.weight;
                    if (childExtra <= 0.0f) {
                        i5 = i10;
                        float f2 = childExtra;
                        i10 = alternativeMaxHeight;
                        widthSizeAndState = i8;
                        count = count2;
                        alternativeMaxHeight = heightMeasureSpec;
                        i8 = ErrorDialogData.SUPPRESSED;
                        i7 = i10;
                        weightSum3 = weightSum2;
                    } else {
                        count = count2;
                        count2 = (int) ((((float) alternativeMaxHeight) * childExtra) / weightSum2);
                        alternativeMaxHeight -= count2;
                        weightSum3 = weightSum2 - childExtra;
                        weightSum2 = ((getPaddingTop() + getPaddingBottom()) + useLargestChild.topMargin) + useLargestChild.bottomMargin;
                        maxHeight2 = useLargestChild.height;
                        i7 = alternativeMaxHeight;
                        widthSizeAndState = i8;
                        alternativeMaxHeight = heightMeasureSpec;
                        i8 = ErrorDialogData.SUPPRESSED;
                        weightSum2 = getChildMeasureSpec(alternativeMaxHeight, weightSum2, maxHeight2);
                        if (useLargestChild.width == 0) {
                            if (i10 != ErrorDialogData.SUPPRESSED) {
                                if (count2 <= 0) {
                                }
                                child2.measure(MeasureSpec.makeMeasureSpec(count2 <= 0 ? count2 : 0, ErrorDialogData.SUPPRESSED), weightSum2);
                                i5 = i10;
                                i9 = View.combineMeasuredStates(i9, child2.getMeasuredState() & ViewCompat.MEASURED_STATE_MASK);
                            }
                        }
                        maxHeight2 = child2.getMeasuredWidth() + count2;
                        if (maxHeight2 < 0) {
                            maxHeight2 = 0;
                        }
                        i5 = i10;
                        child2.measure(MeasureSpec.makeMeasureSpec(maxHeight2, ErrorDialogData.SUPPRESSED), weightSum2);
                        i9 = View.combineMeasuredStates(i9, child2.getMeasuredState() & ViewCompat.MEASURED_STATE_MASK);
                    }
                    if (isExactly) {
                        i10 = r7.mTotalLength;
                        r7.mTotalLength = Math.max(i10, (((child2.getMeasuredWidth() + i10) + useLargestChild.leftMargin) + useLargestChild.rightMargin) + getNextLocationOffset(child2));
                    } else {
                        r7.mTotalLength += ((child2.getMeasuredWidth() + useLargestChild.leftMargin) + useLargestChild.rightMargin) + getNextLocationOffset(child2);
                    }
                    if (heightMode == i8) {
                    }
                    weightedMaxHeight = useLargestChild.topMargin + useLargestChild.bottomMargin;
                    maxHeight2 = child2.getMeasuredHeight() + weightedMaxHeight;
                    childBaseline = Math.max(childBaseline, maxHeight2);
                    if (widthMode2) {
                    }
                    alternativeMaxHeight3 = Math.max(alternativeMaxHeight3, widthMode2 ? weightedMaxHeight : maxHeight2);
                    if (!matchHeight) {
                    }
                    if (baselineAligned) {
                        count2 = child2.getBaseline();
                        boolean matchHeightLocally22 = widthMode2;
                        if (count2 != -1) {
                            if (useLargestChild.gravity >= 0) {
                            }
                            i10 = (useLargestChild.gravity >= 0 ? r7.mGravity : useLargestChild.gravity) & 112;
                            int index2 = ((i10 >> 4) & -2) >> 1;
                            int gravity2 = i10;
                            maxAscent[index2] = Math.max(maxAscent[index2], count2);
                            maxDescent[index2] = Math.max(maxDescent[index2], maxHeight2 - count2);
                            matchHeight = widthSizeAndState2;
                            weightSum2 = weightSum3;
                            largestChildWidth++;
                            widthMode3 = i6;
                            useLargestChild2 = z;
                            count2 = count;
                            alternativeMaxHeight = i7;
                            i8 = widthSizeAndState;
                            i10 = i5;
                            maxHeight2 = widthMeasureSpec;
                        }
                    }
                    matchHeight = widthSizeAndState2;
                    weightSum2 = weightSum3;
                    largestChildWidth++;
                    widthMode3 = i6;
                    useLargestChild2 = z;
                    count2 = count;
                    alternativeMaxHeight = i7;
                    i8 = widthSizeAndState;
                    i10 = i5;
                    maxHeight2 = widthMeasureSpec;
                } else {
                    i5 = i10;
                    i10 = alternativeMaxHeight;
                    widthSizeAndState = i8;
                    count = count2;
                    alternativeMaxHeight = heightMeasureSpec;
                }
            }
            i7 = i10;
            largestChildWidth++;
            widthMode3 = i6;
            useLargestChild2 = z;
            count2 = count;
            alternativeMaxHeight = i7;
            i8 = widthSizeAndState;
            i10 = i5;
            maxHeight2 = widthMeasureSpec;
        }
        widthSizeAndState = i8;
        count = count2;
        i6 = widthMode3;
        z = useLargestChild2;
        alternativeMaxHeight = heightMeasureSpec;
        r7.mTotalLength += getPaddingLeft() + getPaddingRight();
        if (maxAscent[3] != -1) {
            maxHeight2 = childBaseline;
            alternativeMaxHeight2 = alternativeMaxHeight3;
            maxHeight = maxHeight2;
            i = i9;
            maxHeight = alternativeMaxHeight2;
            setMeasuredDimension(widthSizeAndState | (i & ViewCompat.MEASURED_STATE_MASK), View.resolveSizeAndState(Math.max(maxHeight + (getPaddingTop() + getPaddingBottom()), getSuggestedMinimumHeight()), alternativeMaxHeight, i << 16));
            if (matchHeight2) {
                forceUniformHeight(count, widthMeasureSpec);
            }
            weightedMaxHeight = widthMeasureSpec;
            return;
        }
        maxHeight2 = Math.max(childBaseline, Math.max(maxAscent[3], Math.max(maxAscent[0], Math.max(maxAscent[1], maxAscent[2]))) + Math.max(maxDescent[3], Math.max(maxDescent[0], Math.max(maxDescent[1], maxDescent[2]))));
        alternativeMaxHeight2 = alternativeMaxHeight3;
        maxHeight = maxHeight2;
        i = i9;
        maxHeight = alternativeMaxHeight2;
        setMeasuredDimension(widthSizeAndState | (i & ViewCompat.MEASURED_STATE_MASK), View.resolveSizeAndState(Math.max(maxHeight + (getPaddingTop() + getPaddingBottom()), getSuggestedMinimumHeight()), alternativeMaxHeight, i << 16));
        if (matchHeight2) {
            weightedMaxHeight = widthMeasureSpec;
            return;
        }
        forceUniformHeight(count, widthMeasureSpec);
    }

    private void forceUniformHeight(int count, int widthMeasureSpec) {
        int uniformMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), ErrorDialogData.SUPPRESSED);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.height == -1) {
                    int oldWidth = lp.width;
                    lp.width = child.getMeasuredWidth();
                    measureChildWithMargins(child, widthMeasureSpec, 0, uniformMeasureSpec, 0);
                    lp.width = oldWidth;
                }
            }
        }
    }

    int getChildrenSkipCount(View child, int index) {
        return 0;
    }

    int measureNullChild(int childIndex) {
        return 0;
    }

    void measureChildBeforeLayout(View child, int childIndex, int widthMeasureSpec, int totalWidth, int heightMeasureSpec, int totalHeight) {
        measureChildWithMargins(child, widthMeasureSpec, totalWidth, heightMeasureSpec, totalHeight);
    }

    int getLocationOffset(View child) {
        return 0;
    }

    int getNextLocationOffset(View child) {
        return 0;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (this.mOrientation == 1) {
            layoutVertical(l, t, r, b);
        } else {
            layoutHorizontal(l, t, r, b);
        }
    }

    void layoutVertical(int left, int top, int right, int bottom) {
        int paddingTop;
        int paddingLeft = getPaddingLeft();
        int width = right - left;
        int childRight = width - getPaddingRight();
        int childSpace = (width - paddingLeft) - getPaddingRight();
        int count = getVirtualChildCount();
        int majorGravity = this.mGravity & 112;
        int minorGravity = this.mGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (majorGravity == 16) {
            paddingTop = getPaddingTop() + (((bottom - top) - r6.mTotalLength) / 2);
        } else if (majorGravity != 80) {
            paddingTop = getPaddingTop();
        } else {
            paddingTop = ((getPaddingTop() + bottom) - top) - r6.mTotalLength;
        }
        int i = 0;
        while (true) {
            int i2 = i;
            int paddingLeft2;
            if (i2 < count) {
                int majorGravity2;
                View child = getVirtualChildAt(i2);
                if (child == null) {
                    paddingTop += measureNullChild(i2);
                    majorGravity2 = majorGravity;
                    paddingLeft2 = paddingLeft;
                } else if (child.getVisibility() != 8) {
                    int childWidth = child.getMeasuredWidth();
                    int childHeight = child.getMeasuredHeight();
                    LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    int gravity = lp.gravity;
                    if (gravity < 0) {
                        gravity = minorGravity;
                    }
                    int layoutDirection = ViewCompat.getLayoutDirection(this);
                    int gravity2 = gravity;
                    gravity = GravityCompat.getAbsoluteGravity(gravity, layoutDirection) & 7;
                    majorGravity2 = majorGravity;
                    gravity = gravity != 1 ? gravity != 5 ? lp.leftMargin + paddingLeft : (childRight - childWidth) - lp.rightMargin : ((((childSpace - childWidth) / 2) + paddingLeft) + lp.leftMargin) - lp.rightMargin;
                    if (hasDividerBeforeChildAt(i2) != 0) {
                        paddingTop += r6.mDividerHeight;
                    }
                    gravity2 = paddingTop + lp.topMargin;
                    LayoutParams lp2 = lp;
                    View child2 = child;
                    paddingLeft2 = paddingLeft;
                    paddingLeft = i2;
                    setChildFrame(child, gravity, gravity2 + getLocationOffset(child), childWidth, childHeight);
                    i2 = paddingLeft + getChildrenSkipCount(child2, paddingLeft);
                    paddingTop = gravity2 + ((childHeight + lp2.bottomMargin) + getNextLocationOffset(child2));
                } else {
                    majorGravity2 = majorGravity;
                    paddingLeft2 = paddingLeft;
                    paddingLeft = i2;
                }
                i = i2 + 1;
                majorGravity = majorGravity2;
                paddingLeft = paddingLeft2;
            } else {
                paddingLeft2 = paddingLeft;
                return;
            }
        }
    }

    void layoutHorizontal(int left, int top, int right, int bottom) {
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int paddingTop = getPaddingTop();
        int height = bottom - top;
        int childBottom = height - getPaddingBottom();
        int childSpace = (height - paddingTop) - getPaddingBottom();
        int count = getVirtualChildCount();
        int majorGravity = this.mGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int minorGravity = this.mGravity & 112;
        boolean baselineAligned = this.mBaselineAligned;
        int[] maxAscent = this.mMaxAscent;
        int[] maxDescent = this.mMaxDescent;
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int absoluteGravity = GravityCompat.getAbsoluteGravity(majorGravity, layoutDirection);
        if (absoluteGravity != 1) {
            if (absoluteGravity != 5) {
                absoluteGravity = getPaddingLeft();
            } else {
                absoluteGravity = ((getPaddingLeft() + right) - left) - r6.mTotalLength;
            }
        } else {
            int i = layoutDirection;
            absoluteGravity = getPaddingLeft() + (((right - left) - r6.mTotalLength) / 2);
        }
        layoutDirection = absoluteGravity;
        absoluteGravity = 0;
        int dir = 1;
        if (isLayoutRtl) {
            absoluteGravity = count - 1;
            dir = -1;
        }
        int i2 = 0;
        int childLeft = layoutDirection;
        while (true) {
            layoutDirection = i2;
            int[] maxAscent2;
            boolean baselineAligned2;
            int majorGravity2;
            int count2;
            boolean isLayoutRtl2;
            if (layoutDirection < count) {
                int[] maxDescent2;
                int childIndex = absoluteGravity + (dir * layoutDirection);
                View child = getVirtualChildAt(childIndex);
                if (child == null) {
                    childLeft += measureNullChild(childIndex);
                    maxDescent2 = maxDescent;
                    maxAscent2 = maxAscent;
                    baselineAligned2 = baselineAligned;
                    majorGravity2 = majorGravity;
                    count2 = count;
                    isLayoutRtl2 = isLayoutRtl;
                } else {
                    int i3 = layoutDirection;
                    majorGravity2 = majorGravity;
                    if (child.getVisibility() != 8) {
                        int childBaseline;
                        i2 = child.getMeasuredWidth();
                        int childHeight = child.getMeasuredHeight();
                        LayoutParams lp = (LayoutParams) child.getLayoutParams();
                        int childBaseline2 = -1;
                        if (baselineAligned) {
                            baselineAligned2 = baselineAligned;
                            if (lp.height != -1) {
                                childBaseline = child.getBaseline();
                                layoutDirection = lp.gravity;
                                if (layoutDirection < 0) {
                                    layoutDirection = minorGravity;
                                }
                                layoutDirection &= 112;
                                count2 = count;
                                if (layoutDirection != 16) {
                                    layoutDirection = ((((childSpace - childHeight) / 2) + paddingTop) + lp.topMargin) - lp.bottomMargin;
                                } else if (layoutDirection != 48) {
                                    layoutDirection = lp.topMargin + paddingTop;
                                    if (childBaseline != -1) {
                                        layoutDirection += maxAscent[1] - childBaseline;
                                    }
                                } else if (layoutDirection == 80) {
                                    layoutDirection = paddingTop;
                                } else {
                                    layoutDirection = (childBottom - childHeight) - lp.bottomMargin;
                                    if (childBaseline != -1) {
                                        layoutDirection -= maxDescent[2] - (child.getMeasuredHeight() - childBaseline);
                                    }
                                }
                                if (hasDividerBeforeChildAt(childIndex)) {
                                    childLeft += r6.mDividerWidth;
                                }
                                childLeft += lp.leftMargin;
                                maxDescent2 = maxDescent;
                                maxAscent2 = maxAscent;
                                isLayoutRtl2 = isLayoutRtl;
                                isLayoutRtl = lp;
                                setChildFrame(child, childLeft + getLocationOffset(child), layoutDirection, i2, childHeight);
                                childLeft += (i2 + isLayoutRtl.rightMargin) + getNextLocationOffset(child);
                                layoutDirection = i3 + getChildrenSkipCount(child, childIndex);
                            }
                        } else {
                            baselineAligned2 = baselineAligned;
                        }
                        childBaseline = childBaseline2;
                        layoutDirection = lp.gravity;
                        if (layoutDirection < 0) {
                            layoutDirection = minorGravity;
                        }
                        layoutDirection &= 112;
                        count2 = count;
                        if (layoutDirection != 16) {
                            layoutDirection = ((((childSpace - childHeight) / 2) + paddingTop) + lp.topMargin) - lp.bottomMargin;
                        } else if (layoutDirection != 48) {
                            layoutDirection = lp.topMargin + paddingTop;
                            if (childBaseline != -1) {
                                layoutDirection += maxAscent[1] - childBaseline;
                            }
                        } else if (layoutDirection == 80) {
                            layoutDirection = (childBottom - childHeight) - lp.bottomMargin;
                            if (childBaseline != -1) {
                                layoutDirection -= maxDescent[2] - (child.getMeasuredHeight() - childBaseline);
                            }
                        } else {
                            layoutDirection = paddingTop;
                        }
                        if (hasDividerBeforeChildAt(childIndex)) {
                            childLeft += r6.mDividerWidth;
                        }
                        childLeft += lp.leftMargin;
                        maxDescent2 = maxDescent;
                        maxAscent2 = maxAscent;
                        isLayoutRtl2 = isLayoutRtl;
                        isLayoutRtl = lp;
                        setChildFrame(child, childLeft + getLocationOffset(child), layoutDirection, i2, childHeight);
                        childLeft += (i2 + isLayoutRtl.rightMargin) + getNextLocationOffset(child);
                        layoutDirection = i3 + getChildrenSkipCount(child, childIndex);
                    } else {
                        maxDescent2 = maxDescent;
                        maxAscent2 = maxAscent;
                        baselineAligned2 = baselineAligned;
                        count2 = count;
                        isLayoutRtl2 = isLayoutRtl;
                        layoutDirection = i3;
                    }
                }
                i2 = layoutDirection + 1;
                majorGravity = majorGravity2;
                baselineAligned = baselineAligned2;
                maxDescent = maxDescent2;
                count = count2;
                maxAscent = maxAscent2;
                isLayoutRtl = isLayoutRtl2;
            } else {
                maxAscent2 = maxAscent;
                baselineAligned2 = baselineAligned;
                majorGravity2 = majorGravity;
                count2 = count;
                isLayoutRtl2 = isLayoutRtl;
                return;
            }
        }
    }

    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }

    public void setOrientation(int orientation) {
        if (this.mOrientation != orientation) {
            this.mOrientation = orientation;
            requestLayout();
        }
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setGravity(int gravity) {
        if (this.mGravity != gravity) {
            if ((GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK & gravity) == 0) {
                gravity |= GravityCompat.START;
            }
            if ((gravity & 112) == 0) {
                gravity |= 48;
            }
            this.mGravity = gravity;
            requestLayout();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    public void setHorizontalGravity(int horizontalGravity) {
        int gravity = horizontalGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if ((GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK & this.mGravity) != gravity) {
            this.mGravity = (this.mGravity & -8388616) | gravity;
            requestLayout();
        }
    }

    public void setVerticalGravity(int verticalGravity) {
        int gravity = verticalGravity & 112;
        if ((this.mGravity & 112) != gravity) {
            this.mGravity = (this.mGravity & -113) | gravity;
            requestLayout();
        }
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    protected LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -2);
        }
        if (this.mOrientation == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    protected LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(LinearLayoutCompat.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(LinearLayoutCompat.class.getName());
    }
}
