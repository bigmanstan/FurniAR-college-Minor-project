package android.support.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AttributeSet;
import org.xmlpull.v1.XmlPullParser;

public class ArcMotion extends PathMotion {
    private static final float DEFAULT_MAX_ANGLE_DEGREES = 70.0f;
    private static final float DEFAULT_MAX_TANGENT = ((float) Math.tan(Math.toRadians(35.0d)));
    private static final float DEFAULT_MIN_ANGLE_DEGREES = 0.0f;
    private float mMaximumAngle = DEFAULT_MAX_ANGLE_DEGREES;
    private float mMaximumTangent = DEFAULT_MAX_TANGENT;
    private float mMinimumHorizontalAngle = 0.0f;
    private float mMinimumHorizontalTangent = 0.0f;
    private float mMinimumVerticalAngle = 0.0f;
    private float mMinimumVerticalTangent = 0.0f;

    public ArcMotion(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.ARC_MOTION);
        XmlPullParser parser = (XmlPullParser) attrs;
        setMinimumVerticalAngle(TypedArrayUtils.getNamedFloat(a, parser, "minimumVerticalAngle", 1, 0.0f));
        setMinimumHorizontalAngle(TypedArrayUtils.getNamedFloat(a, parser, "minimumHorizontalAngle", 0, 0.0f));
        setMaximumAngle(TypedArrayUtils.getNamedFloat(a, parser, "maximumAngle", 2, DEFAULT_MAX_ANGLE_DEGREES));
        a.recycle();
    }

    public void setMinimumHorizontalAngle(float angleInDegrees) {
        this.mMinimumHorizontalAngle = angleInDegrees;
        this.mMinimumHorizontalTangent = toTangent(angleInDegrees);
    }

    public float getMinimumHorizontalAngle() {
        return this.mMinimumHorizontalAngle;
    }

    public void setMinimumVerticalAngle(float angleInDegrees) {
        this.mMinimumVerticalAngle = angleInDegrees;
        this.mMinimumVerticalTangent = toTangent(angleInDegrees);
    }

    public float getMinimumVerticalAngle() {
        return this.mMinimumVerticalAngle;
    }

    public void setMaximumAngle(float angleInDegrees) {
        this.mMaximumAngle = angleInDegrees;
        this.mMaximumTangent = toTangent(angleInDegrees);
    }

    public float getMaximumAngle() {
        return this.mMaximumAngle;
    }

    private static float toTangent(float arcInDegrees) {
        if (arcInDegrees >= 0.0f && arcInDegrees <= 90.0f) {
            return (float) Math.tan(Math.toRadians((double) (arcInDegrees / 2.0f)));
        }
        throw new IllegalArgumentException("Arc must be between 0 and 90 degrees");
    }

    public Path getPath(float startX, float startY, float endX, float endY) {
        float eDistY;
        float ey;
        float ex;
        float minimumArcDist2;
        ArcMotion arcMotion = this;
        float f = startX;
        float f2 = startY;
        Path path = new Path();
        path.moveTo(f, f2);
        float deltaX = endX - f;
        float deltaY = endY - f2;
        float h2 = (deltaX * deltaX) + (deltaY * deltaY);
        float dx = (f + endX) / 2.0f;
        float dy = (f2 + endY) / 2.0f;
        float midDist2 = h2 * 0.25f;
        boolean isMovingUpwards = f2 > endY;
        if (Math.abs(deltaX) < Math.abs(deltaY)) {
            eDistY = Math.abs(h2 / (deltaY * 2.0f));
            if (isMovingUpwards) {
                ey = endY + eDistY;
                ex = endX;
            } else {
                ey = f2 + eDistY;
                ex = f;
            }
            minimumArcDist2 = (arcMotion.mMinimumVerticalTangent * midDist2) * arcMotion.mMinimumVerticalTangent;
            float f3 = ex;
            ex = ey;
            ey = f3;
        } else {
            eDistY = h2 / (deltaX * 2.0f);
            if (isMovingUpwards) {
                ey = f + eDistY;
                ex = startY;
            } else {
                ey = endX - eDistY;
                ex = endY;
            }
            minimumArcDist2 = (arcMotion.mMinimumHorizontalTangent * midDist2) * arcMotion.mMinimumHorizontalTangent;
        }
        float minimumArcDist22 = minimumArcDist2;
        float arcDistX = dx - ey;
        float arcDistY = dy - ex;
        float arcDist2 = (arcDistX * arcDistX) + (arcDistY * arcDistY);
        float maximumArcDist2 = (arcMotion.mMaximumTangent * midDist2) * arcMotion.mMaximumTangent;
        eDistY = 0.0f;
        if (arcDist2 < minimumArcDist22) {
            eDistY = minimumArcDist22;
        } else if (arcDist2 > maximumArcDist2) {
            eDistY = maximumArcDist2;
        }
        float newArcDistance2 = eDistY;
        if (newArcDistance2 != 0.0f) {
            minimumArcDist2 = (float) Math.sqrt((double) (newArcDistance2 / arcDist2));
            ey = dx + ((ey - dx) * minimumArcDist2);
            ex = dy + ((ex - dy) * minimumArcDist2);
        }
        float ex2 = ey;
        float ey2 = ex;
        path.cubicTo((f + ex2) / 2.0f, (f2 + ey2) / 2.0f, (ex2 + endX) / 2.0f, (ey2 + endY) / 2.0f, endX, endY);
        return path;
    }
}
