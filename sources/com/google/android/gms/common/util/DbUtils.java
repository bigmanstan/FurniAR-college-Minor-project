package com.google.android.gms.common.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.stable.zzk;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Set;
import javax.annotation.Nullable;

public final class DbUtils {
    private DbUtils() {
    }

    public static void clearDatabase(SQLiteDatabase sQLiteDatabase) {
        zza(sQLiteDatabase, "table", "sqlite_sequence", "android_metadata");
        zza(sQLiteDatabase, "trigger", new String[0]);
        zza(sQLiteDatabase, "view", new String[0]);
    }

    public static long countCurrentRowBytes(Cursor cursor) {
        return countCurrentRowBytes(cursor, Charset.forName("UTF-8"));
    }

    public static long countCurrentRowBytes(Cursor cursor, Charset charset) {
        long j = 0;
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            long j2;
            int length;
            switch (cursor.getType(i)) {
                case 0:
                case 1:
                case 2:
                    j2 = 4;
                    break;
                case 3:
                    length = cursor.getString(i).getBytes(charset).length;
                    break;
                case 4:
                    length = cursor.getBlob(i).length;
                    break;
                default:
                    continue;
            }
            j2 = (long) length;
            j += j2;
        }
        return j;
    }

    public static long getDatabaseSize(Context context, String str) {
        try {
            File databasePath = context.getDatabasePath(str);
            if (databasePath != null) {
                return databasePath.length();
            }
        } catch (SecurityException e) {
            String str2 = "DbUtils";
            String str3 = "Failed to get db size for ";
            str = String.valueOf(str);
            Log.w(str2, str.length() != 0 ? str3.concat(str) : new String(str3));
        }
        return 0;
    }

    @Nullable
    public static Integer getIntegerFromCursor(Cursor cursor, int i) {
        return getIntegerFromCursor(cursor, i, null);
    }

    @Nullable
    public static Integer getIntegerFromCursor(Cursor cursor, int i, @Nullable Integer num) {
        if (i >= 0) {
            if (!cursor.isNull(i)) {
                return Integer.valueOf(cursor.getInt(i));
            }
        }
        return num;
    }

    @Nullable
    public static Long getLongFromCursor(Cursor cursor, int i) {
        return getLongFromCursor(cursor, i, null);
    }

    @Nullable
    public static Long getLongFromCursor(Cursor cursor, int i, @Nullable Long l) {
        if (i >= 0) {
            if (!cursor.isNull(i)) {
                return Long.valueOf(cursor.getLong(i));
            }
        }
        return l;
    }

    @Nullable
    public static String getStringFromCursor(Cursor cursor, int i) {
        return getStringFromCursor(cursor, i, null);
    }

    @Nullable
    public static String getStringFromCursor(Cursor cursor, int i, @Nullable String str) {
        if (i >= 0) {
            if (!cursor.isNull(i)) {
                return cursor.getString(i);
            }
        }
        return str;
    }

    public static void putIntegerIntoContentValues(ContentValues contentValues, String str, @Nullable Integer num) {
        if (num != null) {
            contentValues.put(str, num);
        } else {
            contentValues.putNull(str);
        }
    }

    public static void putLongIntoContentValues(ContentValues contentValues, String str, @Nullable Long l) {
        if (l != null) {
            contentValues.put(str, l);
        } else {
            contentValues.putNull(str);
        }
    }

    public static void putStringIntoContentValues(ContentValues contentValues, String str, @Nullable String str2) {
        if (str2 != null) {
            contentValues.put(str, str2);
        } else {
            contentValues.putNull(str);
        }
    }

    private static void zza(SQLiteDatabase sQLiteDatabase, String str, String... strArr) {
        boolean z;
        SQLiteDatabase sQLiteDatabase2;
        Cursor query;
        Set of;
        String string;
        if (!("table".equals(str) || "view".equals(str))) {
            if (!"trigger".equals(str)) {
                z = false;
                Preconditions.checkArgument(z);
                sQLiteDatabase2 = sQLiteDatabase;
                query = sQLiteDatabase2.query("SQLITE_MASTER", new String[]{"name"}, "type == ?", new String[]{str}, null, null, null);
                of = CollectionUtils.setOf((Object[]) strArr);
                while (query.moveToNext()) {
                    string = query.getString(0);
                    if (!of.contains(string)) {
                        StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 8) + String.valueOf(string).length());
                        stringBuilder.append("DROP ");
                        stringBuilder.append(str);
                        stringBuilder.append(" '");
                        stringBuilder.append(string);
                        stringBuilder.append("'");
                        sQLiteDatabase.execSQL(stringBuilder.toString());
                    }
                }
                if (query != null) {
                    query.close();
                }
            }
        }
        z = true;
        Preconditions.checkArgument(z);
        sQLiteDatabase2 = sQLiteDatabase;
        query = sQLiteDatabase2.query("SQLITE_MASTER", new String[]{"name"}, "type == ?", new String[]{str}, null, null, null);
        try {
            of = CollectionUtils.setOf((Object[]) strArr);
            while (query.moveToNext()) {
                string = query.getString(0);
                if (!of.contains(string)) {
                    StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(str).length() + 8) + String.valueOf(string).length());
                    stringBuilder2.append("DROP ");
                    stringBuilder2.append(str);
                    stringBuilder2.append(" '");
                    stringBuilder2.append(string);
                    stringBuilder2.append("'");
                    sQLiteDatabase.execSQL(stringBuilder2.toString());
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Throwable th) {
            zzk.zza(r1, th);
        }
    }
}
