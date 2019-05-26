package com.google.ar.sceneform.rendering;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES30;

public class GLHelper {
    private static final int EGL_OPENGL_ES3_BIT = 64;
    private static final String TAG = GLHelper.class.getSimpleName();

    public static EGLContext makeContext() {
        return makeContext(EGL14.EGL_NO_CONTEXT);
    }

    public static EGLContext makeContext(EGLContext shareContext) {
        EGLDisplay display = EGL14.eglGetDisplay(0);
        EGL14.eglInitialize(display, null, 0, null, 0);
        EGLConfig[] configs = new EGLConfig[1];
        EGLDisplay eGLDisplay = display;
        EGL14.eglChooseConfig(eGLDisplay, new int[]{12352, 64, 12344}, 0, configs, 0, 1, new int[]{0}, 0);
        EGLContext context = EGL14.eglCreateContext(display, configs[0], shareContext, new int[]{12440, 3, 12344}, 0);
        EGLSurface surface = EGL14.eglCreatePbufferSurface(display, configs[0], new int[]{12375, 1, 12374, 1, 12344}, 0);
        if (EGL14.eglMakeCurrent(display, surface, surface, context)) {
            return context;
        }
        throw new IllegalStateException("Error making GL context.");
    }

    public static int createCameraTexture() {
        int[] textures = new int[1];
        GLES30.glGenTextures(1, textures, 0);
        int result = textures[0];
        GLES30.glBindTexture(36197, result);
        GLES30.glTexParameteri(36197, 10242, 33071);
        GLES30.glTexParameteri(36197, 10243, 33071);
        GLES30.glTexParameteri(36197, 10241, 9728);
        GLES30.glTexParameteri(36197, 10240, 9728);
        return result;
    }
}
