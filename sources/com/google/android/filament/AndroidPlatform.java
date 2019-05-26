package com.google.android.filament;

import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.EGLContext;
import android.util.Log;
import android.view.Surface;

final class AndroidPlatform extends Platform {
    private static final String LOG_TAG = "Filament";

    static {
        EGL14.eglGetDisplay(0);
    }

    AndroidPlatform() {
    }

    void log(String message) {
        Log.d(LOG_TAG, message);
    }

    void warn(String message) {
        Log.w(LOG_TAG, message);
    }

    boolean validateStreamSource(Object object) {
        return object instanceof SurfaceTexture;
    }

    boolean validateSurface(Object object) {
        return object instanceof Surface;
    }

    boolean validateSharedContext(Object object) {
        return object instanceof EGLContext;
    }

    long getSharedContextNativeHandle(Object sharedContext) {
        return ((EGLContext) sharedContext).getNativeHandle();
    }
}
