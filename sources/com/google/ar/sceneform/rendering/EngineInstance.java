package com.google.ar.sceneform.rendering;

import android.opengl.EGLContext;
import android.support.annotation.Nullable;
import com.google.android.filament.Engine;
import com.google.android.filament.Filament;

public class EngineInstance {
    @Nullable
    private static Engine engine = null;
    @Nullable
    private static EGLContext glContext = null;

    public static Engine getEngine() {
        createEngine();
        if (engine != null) {
            return engine;
        }
        throw new IllegalStateException("Filament Engine creation has failed.");
    }

    public static EGLContext getGlContext() {
        createEngine();
        if (glContext != null) {
            return glContext;
        }
        throw new IllegalStateException("Filament Engine creation has failed.");
    }

    private static void createEngine() {
        if (engine == null || glContext == null) {
            Filament.init();
            glContext = GLHelper.makeContext();
            engine = Engine.create(glContext);
            if (engine == null || glContext == null) {
                throw new IllegalStateException("Filament Engine creation has failed.");
            }
        }
    }
}
