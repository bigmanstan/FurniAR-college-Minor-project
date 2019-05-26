package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.appcompat.C0015R;

class ThemeUtils {
    private static final int[] APPCOMPAT_CHECK_ATTRS = new int[]{C0015R.attr.colorPrimary};

    ThemeUtils() {
    }

    static void checkAppCompatTheme(Context context) {
        TypedArray a = context.obtainStyledAttributes(APPCOMPAT_CHECK_ATTRS);
        boolean failed = a.hasValue(0) ^ 1;
        a.recycle();
        if (failed) {
            throw new IllegalArgumentException("You need to use a Theme.AppCompat theme (or descendant) with the design library.");
        }
    }
}
