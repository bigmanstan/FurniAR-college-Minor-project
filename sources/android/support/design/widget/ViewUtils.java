package android.support.design.widget;

import android.graphics.PorterDuff.Mode;

class ViewUtils {
    ViewUtils() {
    }

    static Mode parseTintMode(int value, Mode defaultMode) {
        if (value == 3) {
            return Mode.SRC_OVER;
        }
        if (value == 5) {
            return Mode.SRC_IN;
        }
        if (value == 9) {
            return Mode.SRC_ATOP;
        }
        switch (value) {
            case 14:
                return Mode.MULTIPLY;
            case 15:
                return Mode.SCREEN;
            default:
                return defaultMode;
        }
    }
}
