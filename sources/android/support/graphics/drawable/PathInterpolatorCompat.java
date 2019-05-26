package android.support.graphics.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.PathParser;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.animation.Interpolator;
import org.xmlpull.v1.XmlPullParser;

@RestrictTo({Scope.LIBRARY_GROUP})
public class PathInterpolatorCompat implements Interpolator {
    public static final double EPSILON = 1.0E-5d;
    public static final int MAX_NUM_POINTS = 3000;
    private static final float PRECISION = 0.002f;
    private float[] mX;
    private float[] mY;

    public PathInterpolatorCompat(Context context, AttributeSet attrs, XmlPullParser parser) {
        this(context.getResources(), context.getTheme(), attrs, parser);
    }

    public PathInterpolatorCompat(Resources res, Theme theme, AttributeSet attrs, XmlPullParser parser) {
        TypedArray a = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_PATH_INTERPOLATOR);
        parseInterpolatorFromTypeArray(a, parser);
        a.recycle();
    }

    private void parseInterpolatorFromTypeArray(TypedArray a, XmlPullParser parser) {
        if (TypedArrayUtils.hasAttribute(parser, "pathData")) {
            String pathData = TypedArrayUtils.getNamedString(a, parser, "pathData", 4);
            Path path = PathParser.createPathFromPathData(pathData);
            if (path != null) {
                initPath(path);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("The path is null, which is created from ");
            stringBuilder.append(pathData);
            throw new InflateException(stringBuilder.toString());
        } else if (!TypedArrayUtils.hasAttribute(parser, "controlX1")) {
            throw new InflateException("pathInterpolator requires the controlX1 attribute");
        } else if (TypedArrayUtils.hasAttribute(parser, "controlY1")) {
            float x1 = TypedArrayUtils.getNamedFloat(a, parser, "controlX1", 0, 0.0f);
            float y1 = TypedArrayUtils.getNamedFloat(a, parser, "controlY1", 1, 0.0f);
            boolean hasX2 = TypedArrayUtils.hasAttribute(parser, "controlX2");
            if (hasX2 != TypedArrayUtils.hasAttribute(parser, "controlY2")) {
                throw new InflateException("pathInterpolator requires both controlX2 and controlY2 for cubic Beziers.");
            } else if (hasX2) {
                initCubic(x1, y1, TypedArrayUtils.getNamedFloat(a, parser, "controlX2", 2, 0.0f), TypedArrayUtils.getNamedFloat(a, parser, "controlY2", 3, 0.0f));
            } else {
                initQuad(x1, y1);
            }
        } else {
            throw new InflateException("pathInterpolator requires the controlY1 attribute");
        }
    }

    private void initQuad(float controlX, float controlY) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.quadTo(controlX, controlY, 1.0f, 1.0f);
        initPath(path);
    }

    private void initCubic(float x1, float y1, float x2, float y2) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(x1, y1, x2, y2, 1.0f, 1.0f);
        initPath(path);
    }

    private void initPath(Path path) {
        int i = 0;
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float pathLength = pathMeasure.getLength();
        int numPoints = Math.min(MAX_NUM_POINTS, ((int) (pathLength / PRECISION)) + 1);
        if (numPoints > 0) {
            int i2;
            this.mX = new float[numPoints];
            this.mY = new float[numPoints];
            float[] position = new float[2];
            for (i2 = 0; i2 < numPoints; i2++) {
                pathMeasure.getPosTan((((float) i2) * pathLength) / ((float) (numPoints - 1)), position, null);
                this.mX[i2] = position[0];
                this.mY[i2] = position[1];
            }
            if (((double) Math.abs(this.mX[0])) > 1.0E-5d || ((double) Math.abs(this.mY[0])) > 1.0E-5d || ((double) Math.abs(this.mX[numPoints - 1] - 1.0f)) > 1.0E-5d || ((double) Math.abs(this.mY[numPoints - 1] - 1.0f)) > 1.0E-5d) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("The Path must start at (0,0) and end at (1,1) start: ");
                stringBuilder.append(this.mX[0]);
                stringBuilder.append(",");
                stringBuilder.append(this.mY[0]);
                stringBuilder.append(" end:");
                stringBuilder.append(this.mX[numPoints - 1]);
                stringBuilder.append(",");
                stringBuilder.append(this.mY[numPoints - 1]);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            float prevX = 0.0f;
            i2 = 0;
            while (i < numPoints) {
                int componentIndex = i2 + 1;
                i2 = this.mX[i2];
                if (i2 >= prevX) {
                    this.mX[i] = i2;
                    int prevX2 = i2;
                    i++;
                    i2 = componentIndex;
                } else {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("The Path cannot loop back on itself, x :");
                    stringBuilder2.append(i2);
                    throw new IllegalArgumentException(stringBuilder2.toString());
                }
            }
            if (pathMeasure.nextContour()) {
                throw new IllegalArgumentException("The Path should be continuous, can't have 2+ contours");
            }
            return;
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("The Path has a invalid length ");
        stringBuilder3.append(pathLength);
        throw new IllegalArgumentException(stringBuilder3.toString());
    }

    public float getInterpolation(float t) {
        if (t <= 0.0f) {
            return 0.0f;
        }
        if (t >= 1.0f) {
            return 1.0f;
        }
        int startIndex = 0;
        int endIndex = this.mX.length - 1;
        while (endIndex - startIndex > 1) {
            int midIndex = (startIndex + endIndex) / 2;
            if (t < this.mX[midIndex]) {
                endIndex = midIndex;
            } else {
                startIndex = midIndex;
            }
        }
        float xRange = this.mX[endIndex] - this.mX[startIndex];
        if (xRange == 0.0f) {
            return this.mY[startIndex];
        }
        float fraction = (t - this.mX[startIndex]) / xRange;
        float startY = this.mY[startIndex];
        return ((this.mY[endIndex] - startY) * fraction) + startY;
    }
}
