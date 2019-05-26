package android.support.v7.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

class CardViewEclairMr1 implements CardViewImpl {
    final RectF sCornerRect = new RectF();

    /* renamed from: android.support.v7.widget.CardViewEclairMr1$1 */
    class C04981 implements RoundRectHelper {
        C04981() {
        }

        public void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius, Paint paint) {
            C04981 c04981 = this;
            Canvas canvas2 = canvas;
            RectF rectF = bounds;
            float twoRadius = cornerRadius * 2.0f;
            float innerWidth = (bounds.width() - twoRadius) - 1.0f;
            float innerHeight = (bounds.height() - twoRadius) - 1.0f;
            if (cornerRadius >= 1.0f) {
                float roundedCornerRadius = cornerRadius + 0.5f;
                CardViewEclairMr1.this.sCornerRect.set(-roundedCornerRadius, -roundedCornerRadius, roundedCornerRadius, roundedCornerRadius);
                int saved = canvas.save();
                canvas2.translate(rectF.left + roundedCornerRadius, rectF.top + roundedCornerRadius);
                int saved2 = saved;
                canvas.drawArc(CardViewEclairMr1.this.sCornerRect, 180.0f, 90.0f, true, paint);
                canvas2.translate(innerWidth, 0.0f);
                canvas2.rotate(90.0f);
                canvas.drawArc(CardViewEclairMr1.this.sCornerRect, 180.0f, 90.0f, true, paint);
                canvas2.translate(innerHeight, 0.0f);
                canvas2.rotate(90.0f);
                Paint paint2 = paint;
                canvas.drawArc(CardViewEclairMr1.this.sCornerRect, 180.0f, 90.0f, true, paint2);
                canvas2.translate(innerWidth, 0.0f);
                canvas2.rotate(90.0f);
                canvas.drawArc(CardViewEclairMr1.this.sCornerRect, 180.0f, 90.0f, true, paint2);
                canvas2.restoreToCount(saved2);
                canvas.drawRect((rectF.left + roundedCornerRadius) - 1.0f, rectF.top, (rectF.right - roundedCornerRadius) + 1.0f, rectF.top + roundedCornerRadius, paint);
                canvas.drawRect((rectF.left + roundedCornerRadius) - 1.0f, (rectF.bottom - roundedCornerRadius) + 1.0f, (rectF.right - roundedCornerRadius) + 1.0f, rectF.bottom, paint);
            }
            canvas.drawRect(rectF.left, Math.max(0.0f, cornerRadius - 1.0f) + rectF.top, rectF.right, (rectF.bottom - cornerRadius) + 1.0f, paint);
        }
    }

    CardViewEclairMr1() {
    }

    public void initStatic() {
        RoundRectDrawableWithShadow.sRoundRectHelper = new C04981();
    }

    public void initialize(CardViewDelegate cardView, Context context, int backgroundColor, float radius, float elevation, float maxElevation) {
        RoundRectDrawableWithShadow background = createBackground(context, backgroundColor, radius, elevation, maxElevation);
        background.setAddPaddingForCorners(cardView.getPreventCornerOverlap());
        cardView.setBackgroundDrawable(background);
        updatePadding(cardView);
    }

    RoundRectDrawableWithShadow createBackground(Context context, int backgroundColor, float radius, float elevation, float maxElevation) {
        return new RoundRectDrawableWithShadow(context.getResources(), backgroundColor, radius, elevation, maxElevation);
    }

    public void updatePadding(CardViewDelegate cardView) {
        Rect shadowPadding = new Rect();
        getShadowBackground(cardView).getMaxShadowAndCornerPadding(shadowPadding);
        cardView.setMinWidthHeightInternal((int) Math.ceil((double) getMinWidth(cardView)), (int) Math.ceil((double) getMinHeight(cardView)));
        cardView.setShadowPadding(shadowPadding.left, shadowPadding.top, shadowPadding.right, shadowPadding.bottom);
    }

    public void onCompatPaddingChanged(CardViewDelegate cardView) {
    }

    public void onPreventCornerOverlapChanged(CardViewDelegate cardView) {
        getShadowBackground(cardView).setAddPaddingForCorners(cardView.getPreventCornerOverlap());
        updatePadding(cardView);
    }

    public void setBackgroundColor(CardViewDelegate cardView, int color) {
        getShadowBackground(cardView).setColor(color);
    }

    public void setRadius(CardViewDelegate cardView, float radius) {
        getShadowBackground(cardView).setCornerRadius(radius);
        updatePadding(cardView);
    }

    public float getRadius(CardViewDelegate cardView) {
        return getShadowBackground(cardView).getCornerRadius();
    }

    public void setElevation(CardViewDelegate cardView, float elevation) {
        getShadowBackground(cardView).setShadowSize(elevation);
    }

    public float getElevation(CardViewDelegate cardView) {
        return getShadowBackground(cardView).getShadowSize();
    }

    public void setMaxElevation(CardViewDelegate cardView, float maxElevation) {
        getShadowBackground(cardView).setMaxShadowSize(maxElevation);
        updatePadding(cardView);
    }

    public float getMaxElevation(CardViewDelegate cardView) {
        return getShadowBackground(cardView).getMaxShadowSize();
    }

    public float getMinWidth(CardViewDelegate cardView) {
        return getShadowBackground(cardView).getMinWidth();
    }

    public float getMinHeight(CardViewDelegate cardView) {
        return getShadowBackground(cardView).getMinHeight();
    }

    private RoundRectDrawableWithShadow getShadowBackground(CardViewDelegate cardView) {
        return (RoundRectDrawableWithShadow) cardView.getBackground();
    }
}