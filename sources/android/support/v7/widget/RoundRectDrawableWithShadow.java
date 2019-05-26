package android.support.v7.widget;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.support.v7.cardview.C0016R;

class RoundRectDrawableWithShadow extends Drawable {
    static final double COS_45 = Math.cos(Math.toRadians(45.0d));
    static final float SHADOW_MULTIPLIER = 1.5f;
    static RoundRectHelper sRoundRectHelper;
    private boolean mAddPaddingForCorners = true;
    final RectF mCardBounds;
    float mCornerRadius;
    Paint mCornerShadowPaint;
    Path mCornerShadowPath;
    private boolean mDirty = true;
    Paint mEdgeShadowPaint;
    final int mInsetShadow;
    float mMaxShadowSize;
    Paint mPaint;
    private boolean mPrintedShadowClipWarning = false;
    float mRawMaxShadowSize;
    float mRawShadowSize;
    private final int mShadowEndColor;
    float mShadowSize;
    private final int mShadowStartColor;

    interface RoundRectHelper {
        void drawRoundRect(Canvas canvas, RectF rectF, float f, Paint paint);
    }

    RoundRectDrawableWithShadow(Resources resources, int backgroundColor, float radius, float shadowSize, float maxShadowSize) {
        this.mShadowStartColor = resources.getColor(C0016R.color.cardview_shadow_start_color);
        this.mShadowEndColor = resources.getColor(C0016R.color.cardview_shadow_end_color);
        this.mInsetShadow = resources.getDimensionPixelSize(C0016R.dimen.cardview_compat_inset_shadow);
        this.mPaint = new Paint(5);
        this.mPaint.setColor(backgroundColor);
        this.mCornerShadowPaint = new Paint(5);
        this.mCornerShadowPaint.setStyle(Style.FILL);
        this.mCornerRadius = (float) ((int) (0.5f + radius));
        this.mCardBounds = new RectF();
        this.mEdgeShadowPaint = new Paint(this.mCornerShadowPaint);
        this.mEdgeShadowPaint.setAntiAlias(false);
        setShadowSize(shadowSize, maxShadowSize);
    }

    private int toEven(float value) {
        int i = (int) (1056964608 + value);
        if (i % 2 == 1) {
            return i - 1;
        }
        return i;
    }

    public void setAddPaddingForCorners(boolean addPaddingForCorners) {
        this.mAddPaddingForCorners = addPaddingForCorners;
        invalidateSelf();
    }

    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
        this.mCornerShadowPaint.setAlpha(alpha);
        this.mEdgeShadowPaint.setAlpha(alpha);
    }

    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.mDirty = true;
    }

    void setShadowSize(float shadowSize, float maxShadowSize) {
        StringBuilder stringBuilder;
        if (shadowSize < 0.0f) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid shadow size ");
            stringBuilder.append(shadowSize);
            stringBuilder.append(". Must be >= 0");
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (maxShadowSize >= 0.0f) {
            shadowSize = (float) toEven(shadowSize);
            maxShadowSize = (float) toEven(maxShadowSize);
            if (shadowSize > maxShadowSize) {
                shadowSize = maxShadowSize;
                if (!this.mPrintedShadowClipWarning) {
                    this.mPrintedShadowClipWarning = true;
                }
            }
            if (this.mRawShadowSize != shadowSize || this.mRawMaxShadowSize != maxShadowSize) {
                this.mRawShadowSize = shadowSize;
                this.mRawMaxShadowSize = maxShadowSize;
                this.mShadowSize = (float) ((int) (((SHADOW_MULTIPLIER * shadowSize) + ((float) this.mInsetShadow)) + 0.5f));
                this.mMaxShadowSize = ((float) this.mInsetShadow) + maxShadowSize;
                this.mDirty = true;
                invalidateSelf();
            }
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid max shadow size ");
            stringBuilder.append(maxShadowSize);
            stringBuilder.append(". Must be >= 0");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public boolean getPadding(Rect padding) {
        int vOffset = (int) Math.ceil((double) calculateVerticalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
        int hOffset = (int) Math.ceil((double) calculateHorizontalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
        padding.set(hOffset, vOffset, hOffset, vOffset);
        return true;
    }

    static float calculateVerticalPadding(float maxShadowSize, float cornerRadius, boolean addPaddingForCorners) {
        if (addPaddingForCorners) {
            return (float) (((double) (SHADOW_MULTIPLIER * maxShadowSize)) + ((1.0d - COS_45) * ((double) cornerRadius)));
        }
        return SHADOW_MULTIPLIER * maxShadowSize;
    }

    static float calculateHorizontalPadding(float maxShadowSize, float cornerRadius, boolean addPaddingForCorners) {
        if (addPaddingForCorners) {
            return (float) (((double) maxShadowSize) + ((1.0d - COS_45) * ((double) cornerRadius)));
        }
        return maxShadowSize;
    }

    public void setColorFilter(ColorFilter cf) {
        this.mPaint.setColorFilter(cf);
    }

    public int getOpacity() {
        return -3;
    }

    void setCornerRadius(float radius) {
        if (radius >= 0.0f) {
            radius = (float) ((int) (0.5f + radius));
            if (this.mCornerRadius != radius) {
                this.mCornerRadius = radius;
                this.mDirty = true;
                invalidateSelf();
                return;
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid radius ");
        stringBuilder.append(radius);
        stringBuilder.append(". Must be >= 0");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void draw(Canvas canvas) {
        if (this.mDirty) {
            buildComponents(getBounds());
            this.mDirty = false;
        }
        canvas.translate(0.0f, this.mRawShadowSize / 2.0f);
        drawShadow(canvas);
        canvas.translate(0.0f, (-this.mRawShadowSize) / 2.0f);
        sRoundRectHelper.drawRoundRect(canvas, this.mCardBounds, this.mCornerRadius, this.mPaint);
    }

    private void drawShadow(Canvas canvas) {
        float edgeShadowTop = (-this.mCornerRadius) - this.mShadowSize;
        float inset = (this.mCornerRadius + ((float) this.mInsetShadow)) + (this.mRawShadowSize / 2.0f);
        boolean z = false;
        boolean drawHorizontalEdges = this.mCardBounds.width() - (inset * 2.0f) > 0.0f;
        if (this.mCardBounds.height() - (inset * 2.0f) > 0.0f) {
            z = true;
        }
        boolean drawVerticalEdges = z;
        int saved = canvas.save();
        canvas.translate(this.mCardBounds.left + inset, this.mCardBounds.top + inset);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (drawHorizontalEdges) {
            canvas.drawRect(0.0f, edgeShadowTop, this.mCardBounds.width() - (inset * 2.0f), -this.mCornerRadius, this.mEdgeShadowPaint);
        }
        canvas.restoreToCount(saved);
        saved = canvas.save();
        canvas.translate(this.mCardBounds.right - inset, this.mCardBounds.bottom - inset);
        canvas.rotate(180.0f);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (drawHorizontalEdges) {
            canvas.drawRect(0.0f, edgeShadowTop, this.mCardBounds.width() - (inset * 2.0f), (-this.mCornerRadius) + this.mShadowSize, this.mEdgeShadowPaint);
        }
        canvas.restoreToCount(saved);
        saved = canvas.save();
        canvas.translate(this.mCardBounds.left + inset, this.mCardBounds.bottom - inset);
        canvas.rotate(270.0f);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (drawVerticalEdges) {
            canvas.drawRect(0.0f, edgeShadowTop, this.mCardBounds.height() - (inset * 2.0f), -this.mCornerRadius, this.mEdgeShadowPaint);
        }
        canvas.restoreToCount(saved);
        saved = canvas.save();
        canvas.translate(this.mCardBounds.right - inset, this.mCardBounds.top + inset);
        canvas.rotate(90.0f);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (drawVerticalEdges) {
            canvas.drawRect(0.0f, edgeShadowTop, this.mCardBounds.height() - (2.0f * inset), -this.mCornerRadius, this.mEdgeShadowPaint);
        }
        canvas.restoreToCount(saved);
    }

    private void buildShadowCorners() {
        RectF innerBounds = new RectF(-this.mCornerRadius, -this.mCornerRadius, this.mCornerRadius, this.mCornerRadius);
        RectF outerBounds = new RectF(innerBounds);
        outerBounds.inset(-this.mShadowSize, -this.mShadowSize);
        if (this.mCornerShadowPath == null) {
            r0.mCornerShadowPath = new Path();
        } else {
            r0.mCornerShadowPath.reset();
        }
        r0.mCornerShadowPath.setFillType(FillType.EVEN_ODD);
        r0.mCornerShadowPath.moveTo(-r0.mCornerRadius, 0.0f);
        r0.mCornerShadowPath.rLineTo(-r0.mShadowSize, 0.0f);
        r0.mCornerShadowPath.arcTo(outerBounds, 180.0f, 90.0f, false);
        r0.mCornerShadowPath.arcTo(innerBounds, 270.0f, -90.0f, false);
        r0.mCornerShadowPath.close();
        float startRatio = r0.mCornerRadius / (r0.mCornerRadius + r0.mShadowSize);
        r0.mCornerShadowPaint.setShader(new RadialGradient(0.0f, 0.0f, r0.mShadowSize + r0.mCornerRadius, new int[]{r0.mShadowStartColor, r0.mShadowStartColor, r0.mShadowEndColor}, new float[]{0.0f, startRatio, 1.0f}, TileMode.CLAMP));
        r0.mEdgeShadowPaint.setShader(new LinearGradient(0.0f, (-r0.mCornerRadius) + r0.mShadowSize, 0.0f, (-r0.mCornerRadius) - r0.mShadowSize, new int[]{r0.mShadowStartColor, r0.mShadowStartColor, r0.mShadowEndColor}, new float[]{0.0f, 0.5f, 1.0f}, TileMode.CLAMP));
        r0.mEdgeShadowPaint.setAntiAlias(false);
    }

    private void buildComponents(Rect bounds) {
        float verticalOffset = this.mRawMaxShadowSize * SHADOW_MULTIPLIER;
        this.mCardBounds.set(((float) bounds.left) + this.mRawMaxShadowSize, ((float) bounds.top) + verticalOffset, ((float) bounds.right) - this.mRawMaxShadowSize, ((float) bounds.bottom) - verticalOffset);
        buildShadowCorners();
    }

    float getCornerRadius() {
        return this.mCornerRadius;
    }

    void getMaxShadowAndCornerPadding(Rect into) {
        getPadding(into);
    }

    void setShadowSize(float size) {
        setShadowSize(size, this.mRawMaxShadowSize);
    }

    void setMaxShadowSize(float size) {
        setShadowSize(this.mRawShadowSize, size);
    }

    float getShadowSize() {
        return this.mRawShadowSize;
    }

    float getMaxShadowSize() {
        return this.mRawMaxShadowSize;
    }

    float getMinWidth() {
        return ((this.mRawMaxShadowSize + ((float) this.mInsetShadow)) * 2.0f) + (Math.max(this.mRawMaxShadowSize, (this.mCornerRadius + ((float) this.mInsetShadow)) + (this.mRawMaxShadowSize / 2.0f)) * 2.0f);
    }

    float getMinHeight() {
        return (((this.mRawMaxShadowSize * SHADOW_MULTIPLIER) + ((float) this.mInsetShadow)) * 2.0f) + (Math.max(this.mRawMaxShadowSize, (this.mCornerRadius + ((float) this.mInsetShadow)) + ((this.mRawMaxShadowSize * SHADOW_MULTIPLIER) / 2.0f)) * 2.0f);
    }

    public void setColor(int color) {
        this.mPaint.setColor(color);
        invalidateSelf();
    }
}
