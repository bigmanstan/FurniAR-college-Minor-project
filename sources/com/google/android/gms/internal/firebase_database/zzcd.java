package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.Logger.Level;

final /* synthetic */ class zzcd {
    static final /* synthetic */ int[] zzhn = new int[Level.values().length];

    static {
        try {
            zzhn[Level.DEBUG.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            zzhn[Level.INFO.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            zzhn[Level.WARN.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
        try {
            zzhn[Level.ERROR.ordinal()] = 4;
        } catch (NoSuchFieldError e4) {
        }
        try {
            zzhn[Level.NONE.ordinal()] = 5;
        } catch (NoSuchFieldError e5) {
        }
    }
}
