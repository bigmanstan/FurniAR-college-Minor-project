package com.google.ar.sceneform.utilities;

import android.content.Context;
import android.os.Bundle;

public class ArCoreVersion {
    private static final String METADATA_KEY_MIN_APK_VERSION = "com.google.ar.core.min_apk_version";
    public static final int VERSION_CODE_1_3 = 180604036;

    public static int getMinArCoreVersionCode(Context context) {
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            if (bundle.containsKey(METADATA_KEY_MIN_APK_VERSION)) {
                return bundle.getInt(METADATA_KEY_MIN_APK_VERSION);
            }
            throw new IllegalStateException("Application manifest must contain meta-data.com.google.ar.core.min_apk_version");
        } catch (Throwable e) {
            throw new IllegalStateException("Could not load application package metadata.", e);
        }
    }
}
