package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v7.cardview.C0016R;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;

public class CardView extends FrameLayout implements CardViewDelegate {
    private static final int[] COLOR_BACKGROUND_ATTR = new int[]{16842801};
    private static final CardViewImpl IMPL;
    private boolean mCompatPadding;
    private final Rect mContentPadding = new Rect();
    private boolean mPreventCornerOverlap;
    private final Rect mShadowBounds = new Rect();
    private int mUserSetMinHeight;
    private int mUserSetMinWidth;

    static {
        if (VERSION.SDK_INT >= 21) {
            IMPL = new CardViewApi21();
        } else if (VERSION.SDK_INT >= 17) {
            IMPL = new CardViewJellybeanMr1();
        } else {
            IMPL = new CardViewEclairMr1();
        }
        IMPL.initStatic();
    }

    public CardView(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    public void setPadding(int left, int top, int right, int bottom) {
    }

    public void setPaddingRelative(int start, int top, int end, int bottom) {
    }

    public boolean getUseCompatPadding() {
        return this.mCompatPadding;
    }

    public void setUseCompatPadding(boolean useCompatPadding) {
        if (this.mCompatPadding != useCompatPadding) {
            this.mCompatPadding = useCompatPadding;
            IMPL.onCompatPaddingChanged(this);
        }
    }

    public void setContentPadding(int left, int top, int right, int bottom) {
        this.mContentPadding.set(left, top, right, bottom);
        IMPL.updatePadding(this);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (IMPL instanceof CardViewApi21) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == Integer.MIN_VALUE || widthMode == ErrorDialogData.SUPPRESSED) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max((int) Math.ceil((double) IMPL.getMinWidth(this)), MeasureSpec.getSize(widthMeasureSpec)), widthMode);
        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == Integer.MIN_VALUE || heightMode == ErrorDialogData.SUPPRESSED) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max((int) Math.ceil((double) IMPL.getMinHeight(this)), MeasureSpec.getSize(heightMeasureSpec)), heightMode);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        int backgroundColor;
        CardView cardView = this;
        TypedArray a = context.obtainStyledAttributes(attrs, C0016R.styleable.CardView, defStyleAttr, C0016R.style.CardView);
        if (a.hasValue(C0016R.styleable.CardView_cardBackgroundColor)) {
            backgroundColor = a.getColor(C0016R.styleable.CardView_cardBackgroundColor, 0);
        } else {
            Resources resources;
            int i;
            TypedArray aa = getContext().obtainStyledAttributes(COLOR_BACKGROUND_ATTR);
            int themeColorBackground = aa.getColor(0, 0);
            aa.recycle();
            float[] hsv = new float[3];
            Color.colorToHSV(themeColorBackground, hsv);
            if (hsv[2] > 0.5f) {
                resources = getResources();
                i = C0016R.color.cardview_light_background;
            } else {
                resources = getResources();
                i = C0016R.color.cardview_dark_background;
            }
            backgroundColor = resources.getColor(i);
        }
        float radius = a.getDimension(C0016R.styleable.CardView_cardCornerRadius, 0.0f);
        float elevation = a.getDimension(C0016R.styleable.CardView_cardElevation, 0.0f);
        float maxElevation = a.getDimension(C0016R.styleable.CardView_cardMaxElevation, 0.0f);
        cardView.mCompatPadding = a.getBoolean(C0016R.styleable.CardView_cardUseCompatPadding, false);
        cardView.mPreventCornerOverlap = a.getBoolean(C0016R.styleable.CardView_cardPreventCornerOverlap, true);
        int defaultPadding = a.getDimensionPixelSize(C0016R.styleable.CardView_contentPadding, 0);
        cardView.mContentPadding.left = a.getDimensionPixelSize(C0016R.styleable.CardView_contentPaddingLeft, defaultPadding);
        cardView.mContentPadding.top = a.getDimensionPixelSize(C0016R.styleable.CardView_contentPaddingTop, defaultPadding);
        cardView.mContentPadding.right = a.getDimensionPixelSize(C0016R.styleable.CardView_contentPaddingRight, defaultPadding);
        cardView.mContentPadding.bottom = a.getDimensionPixelSize(C0016R.styleable.CardView_contentPaddingBottom, defaultPadding);
        if (elevation > maxElevation) {
            maxElevation = elevation;
        }
        float maxElevation2 = maxElevation;
        cardView.mUserSetMinWidth = a.getDimensionPixelSize(C0016R.styleable.CardView_android_minWidth, 0);
        cardView.mUserSetMinHeight = a.getDimensionPixelSize(C0016R.styleable.CardView_android_minHeight, 0);
        a.recycle();
        IMPL.initialize(this, context, backgroundColor, radius, elevation, maxElevation2);
    }

    public void setMinimumWidth(int minWidth) {
        this.mUserSetMinWidth = minWidth;
        super.setMinimumWidth(minWidth);
    }

    public void setMinimumHeight(int minHeight) {
        this.mUserSetMinHeight = minHeight;
        super.setMinimumHeight(minHeight);
    }

    public void setMinWidthHeightInternal(int width, int height) {
        if (width > this.mUserSetMinWidth) {
            super.setMinimumWidth(width);
        }
        if (height > this.mUserSetMinHeight) {
            super.setMinimumHeight(height);
        }
    }

    public void setCardBackgroundColor(int color) {
        IMPL.setBackgroundColor(this, color);
    }

    public int getContentPaddingLeft() {
        return this.mContentPadding.left;
    }

    public int getContentPaddingRight() {
        return this.mContentPadding.right;
    }

    public int getContentPaddingTop() {
        return this.mContentPadding.top;
    }

    public int getContentPaddingBottom() {
        return this.mContentPadding.bottom;
    }

    public void setRadius(float radius) {
        IMPL.setRadius(this, radius);
    }

    public float getRadius() {
        return IMPL.getRadius(this);
    }

    public void setShadowPadding(int left, int top, int right, int bottom) {
        this.mShadowBounds.set(left, top, right, bottom);
        super.setPadding(this.mContentPadding.left + left, this.mContentPadding.top + top, this.mContentPadding.right + right, this.mContentPadding.bottom + bottom);
    }

    public void setCardElevation(float elevation) {
        IMPL.setElevation(this, elevation);
    }

    public float getCardElevation() {
        return IMPL.getElevation(this);
    }

    public void setMaxCardElevation(float maxElevation) {
        IMPL.setMaxElevation(this, maxElevation);
    }

    public float getMaxCardElevation() {
        return IMPL.getMaxElevation(this);
    }

    public boolean getPreventCornerOverlap() {
        return this.mPreventCornerOverlap;
    }

    public void setPreventCornerOverlap(boolean preventCornerOverlap) {
        if (preventCornerOverlap != this.mPreventCornerOverlap) {
            this.mPreventCornerOverlap = preventCornerOverlap;
            IMPL.onPreventCornerOverlapChanged(this);
        }
    }
}
