package android.support.v7.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.util.Xml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

final class AppCompatColorStateListInflater {
    private static final int DEFAULT_COLOR = -65536;

    private AppCompatColorStateListInflater() {
    }

    @NonNull
    public static ColorStateList createFromXml(@NonNull Resources r, @NonNull XmlPullParser parser, @Nullable Theme theme) throws XmlPullParserException, IOException {
        AttributeSet attrs = Xml.asAttributeSet(parser);
        while (true) {
            int next = parser.next();
            int type = next;
            if (next == 2 || type == 1) {
                if (type == 2) {
                    return createFromXmlInner(r, parser, attrs, theme);
                }
                throw new XmlPullParserException("No start tag found");
            }
        }
        if (type == 2) {
            return createFromXmlInner(r, parser, attrs, theme);
        }
        throw new XmlPullParserException("No start tag found");
    }

    @NonNull
    private static ColorStateList createFromXmlInner(@NonNull Resources r, @NonNull XmlPullParser parser, @NonNull AttributeSet attrs, @Nullable Theme theme) throws XmlPullParserException, IOException {
        String name = parser.getName();
        if (name.equals("selector")) {
            return inflate(r, parser, attrs, theme);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(parser.getPositionDescription());
        stringBuilder.append(": invalid color state list tag ");
        stringBuilder.append(name);
        throw new XmlPullParserException(stringBuilder.toString());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.content.res.ColorStateList inflate(@android.support.annotation.NonNull android.content.res.Resources r21, @android.support.annotation.NonNull org.xmlpull.v1.XmlPullParser r22, @android.support.annotation.NonNull android.util.AttributeSet r23, @android.support.annotation.Nullable android.content.res.Resources.Theme r24) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
        r0 = r23;
        r1 = r22.getDepth();
        r2 = 1;
        r1 = r1 + r2;
        r3 = -65536; // 0xffffffffffff0000 float:NaN double:NaN;
        r4 = 20;
        r4 = new int[r4][];
        r5 = r4.length;
        r5 = new int[r5];
        r7 = r3;
        r3 = 0;
    L_0x0013:
        r8 = r22.next();
        r9 = r8;
        if (r8 == r2) goto L_0x00f2;
    L_0x001a:
        r8 = r22.getDepth();
        r10 = r8;
        if (r8 >= r1) goto L_0x002f;
    L_0x0021:
        r8 = 3;
        if (r9 == r8) goto L_0x0025;
    L_0x0024:
        goto L_0x002f;
    L_0x0025:
        r11 = r21;
        r12 = r24;
        r16 = r1;
        r18 = r7;
        goto L_0x00fa;
    L_0x002f:
        r8 = 2;
        if (r9 != r8) goto L_0x00e3;
    L_0x0032:
        if (r10 > r1) goto L_0x00e3;
    L_0x0034:
        r8 = r22.getName();
        r11 = "item";
        r8 = r8.equals(r11);
        if (r8 != 0) goto L_0x004b;
        r11 = r21;
        r12 = r24;
        r16 = r1;
        r18 = r7;
        goto L_0x00eb;
    L_0x004b:
        r8 = android.support.v7.appcompat.C0015R.styleable.ColorStateListItem;
        r11 = r21;
        r12 = r24;
        r8 = obtainAttributes(r11, r12, r0, r8);
        r13 = android.support.v7.appcompat.C0015R.styleable.ColorStateListItem_android_color;
        r14 = -65281; // 0xffffffffffff00ff float:NaN double:NaN;
        r13 = r8.getColor(r13, r14);
        r14 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r15 = android.support.v7.appcompat.C0015R.styleable.ColorStateListItem_android_alpha;
        r15 = r8.hasValue(r15);
        if (r15 == 0) goto L_0x006f;
    L_0x0068:
        r15 = android.support.v7.appcompat.C0015R.styleable.ColorStateListItem_android_alpha;
        r14 = r8.getFloat(r15, r14);
        goto L_0x007d;
    L_0x006f:
        r15 = android.support.v7.appcompat.C0015R.styleable.ColorStateListItem_alpha;
        r15 = r8.hasValue(r15);
        if (r15 == 0) goto L_0x007d;
    L_0x0077:
        r15 = android.support.v7.appcompat.C0015R.styleable.ColorStateListItem_alpha;
        r14 = r8.getFloat(r15, r14);
    L_0x007d:
        r8.recycle();
        r15 = 0;
        r2 = r23.getAttributeCount();
        r6 = new int[r2];
        r16 = r1;
        r1 = r15;
        r15 = 0;
    L_0x008b:
        if (r15 >= r2) goto L_0x00bc;
    L_0x008d:
        r17 = r2;
        r2 = r0.getAttributeNameResource(r15);
        r18 = r7;
        r7 = 16843173; // 0x10101a5 float:2.3694738E-38 double:8.321633E-317;
        if (r2 == r7) goto L_0x00b5;
    L_0x009a:
        r7 = 16843551; // 0x101031f float:2.3695797E-38 double:8.32182E-317;
        if (r2 == r7) goto L_0x00b5;
    L_0x009f:
        r7 = android.support.v7.appcompat.C0015R.attr.alpha;
        if (r2 == r7) goto L_0x00b5;
    L_0x00a3:
        r7 = r1 + 1;
        r19 = r7;
        r7 = 0;
        r20 = r0.getAttributeBooleanValue(r15, r7);
        if (r20 == 0) goto L_0x00b0;
    L_0x00ae:
        r7 = r2;
        goto L_0x00b1;
    L_0x00b0:
        r7 = -r2;
    L_0x00b1:
        r6[r1] = r7;
        r1 = r19;
    L_0x00b5:
        r15 = r15 + 1;
        r2 = r17;
        r7 = r18;
        goto L_0x008b;
    L_0x00bc:
        r17 = r2;
        r18 = r7;
        r2 = android.util.StateSet.trimStateSet(r6, r1);
        r6 = modulateColorAlpha(r13, r14);
        if (r3 == 0) goto L_0x00d1;
    L_0x00ca:
        r7 = r2.length;
        if (r7 != 0) goto L_0x00ce;
    L_0x00cd:
        goto L_0x00d1;
    L_0x00ce:
        r7 = r18;
        goto L_0x00d2;
    L_0x00d1:
        r7 = r6;
    L_0x00d2:
        r5 = android.support.v7.content.res.GrowingArrayUtils.append(r5, r3, r6);
        r15 = android.support.v7.content.res.GrowingArrayUtils.append(r4, r3, r2);
        r4 = r15;
        r4 = (int[][]) r4;
        r3 = r3 + 1;
        r1 = r16;
        goto L_0x00ef;
    L_0x00e3:
        r11 = r21;
        r12 = r24;
        r16 = r1;
        r18 = r7;
    L_0x00eb:
        r1 = r16;
        r7 = r18;
    L_0x00ef:
        r2 = 1;
        goto L_0x0013;
    L_0x00f2:
        r11 = r21;
        r12 = r24;
        r16 = r1;
        r18 = r7;
    L_0x00fa:
        r1 = new int[r3];
        r2 = new int[r3][];
        r6 = 0;
        java.lang.System.arraycopy(r5, r6, r1, r6, r3);
        java.lang.System.arraycopy(r4, r6, r2, r6, r3);
        r6 = new android.content.res.ColorStateList;
        r6.<init>(r2, r1);
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.content.res.AppCompatColorStateListInflater.inflate(android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.content.res.Resources$Theme):android.content.res.ColorStateList");
    }

    private static TypedArray obtainAttributes(Resources res, Theme theme, AttributeSet set, int[] attrs) {
        if (theme == null) {
            return res.obtainAttributes(set, attrs);
        }
        return theme.obtainStyledAttributes(set, attrs, 0, 0);
    }

    private static int modulateColorAlpha(int color, float alphaMod) {
        return ColorUtils.setAlphaComponent(color, Math.round(((float) Color.alpha(color)) * alphaMod));
    }
}
