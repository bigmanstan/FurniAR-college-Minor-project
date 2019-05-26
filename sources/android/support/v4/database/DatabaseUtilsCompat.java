package android.support.v4.database;

import android.text.TextUtils;

@Deprecated
public final class DatabaseUtilsCompat {
    private DatabaseUtilsCompat() {
    }

    @Deprecated
    public static String concatenateWhere(String a, String b) {
        if (TextUtils.isEmpty(a)) {
            return b;
        }
        if (TextUtils.isEmpty(b)) {
            return a;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(a);
        stringBuilder.append(") AND (");
        stringBuilder.append(b);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Deprecated
    public static String[] appendSelectionArgs(String[] originalValues, String[] newValues) {
        if (originalValues != null) {
            if (originalValues.length != 0) {
                String[] result = new String[(originalValues.length + newValues.length)];
                System.arraycopy(originalValues, 0, result, 0, originalValues.length);
                System.arraycopy(newValues, 0, result, originalValues.length, newValues.length);
                return result;
            }
        }
        return newValues;
    }
}
