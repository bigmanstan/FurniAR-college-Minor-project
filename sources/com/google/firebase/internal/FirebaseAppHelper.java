package com.google.firebase.internal;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseApp.IdTokenListener;
import com.google.firebase.auth.GetTokenResult;

@KeepForSdk
public class FirebaseAppHelper {
    @KeepForSdk
    public static Task<GetTokenResult> getToken(FirebaseApp app, boolean forceRefresh) {
        return app.getToken(forceRefresh);
    }

    @KeepForSdk
    public static void addIdTokenListener(FirebaseApp app, IdTokenListener listener) {
        app.addIdTokenListener(listener);
    }

    @KeepForSdk
    public static void removeIdTokenListener(FirebaseApp app, IdTokenListener listener) {
        app.removeIdTokenListener(listener);
    }

    @KeepForSdk
    public static String getUid(FirebaseApp app) throws FirebaseApiNotAvailableException {
        return app.getUid();
    }
}
