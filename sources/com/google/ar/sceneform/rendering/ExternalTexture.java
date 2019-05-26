package com.google.ar.sceneform.rendering;

import android.graphics.SurfaceTexture;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Surface;
import com.google.android.filament.Engine;
import com.google.android.filament.Stream;
import com.google.android.filament.Stream.Builder;
import com.google.android.filament.Texture;
import com.google.android.filament.Texture.InternalFormat;
import com.google.android.filament.Texture.Sampler;
import com.google.ar.sceneform.utilities.AndroidPreconditions;
import com.google.ar.sceneform.utilities.Preconditions;

public class ExternalTexture {
    private static final String TAG = ExternalTexture.class.getSimpleName();
    @Nullable
    private Stream filamentStream;
    @Nullable
    private Texture filamentTexture;
    @Nullable
    private final Surface surface;
    @Nullable
    private final SurfaceTexture surfaceTexture;

    public ExternalTexture() {
        Object surfaceTexture = new SurfaceTexture(0);
        surfaceTexture.detachFromGLContext();
        this.surfaceTexture = surfaceTexture;
        this.surface = new Surface(surfaceTexture);
        initialize(new Builder().stream(surfaceTexture).build(EngineInstance.getEngine()));
    }

    ExternalTexture(int textureId, int width, int height) {
        this.surfaceTexture = null;
        this.surface = null;
        initialize(new Builder().stream((long) textureId).width(width).height(height).build(EngineInstance.getEngine()));
    }

    public SurfaceTexture getSurfaceTexture() {
        return (SurfaceTexture) Preconditions.checkNotNull(this.surfaceTexture);
    }

    public Surface getSurface() {
        return (Surface) Preconditions.checkNotNull(this.surface);
    }

    Texture getFilamentTexture() {
        return (Texture) Preconditions.checkNotNull(this.filamentTexture);
    }

    Stream getFilamentStream() {
        return (Stream) Preconditions.checkNotNull(this.filamentStream);
    }

    protected void finalize() throws Throwable {
        try {
            ThreadPools.getMainExecutor().execute(new -$$Lambda$ExternalTexture$RuuVY3TCNW9qD5dW_fIYryEr4pU());
        } catch (Exception e) {
            Log.e(TAG, "Error while Finalizing ExternalTexture.", e);
        } catch (Throwable th) {
            super.finalize();
        }
        super.finalize();
    }

    void dispose() {
        AndroidPreconditions.checkUiThread();
        Engine engine = EngineInstance.getEngine();
        if (engine != null && engine.isValid()) {
            Texture filamentTexture = this.filamentTexture;
            this.filamentTexture = null;
            if (filamentTexture != null) {
                engine.destroyTexture(filamentTexture);
            }
            Stream filamentStream = this.filamentStream;
            if (filamentStream != null) {
                engine.destroyStream(filamentStream);
                this.filamentStream = null;
            }
        }
    }

    private void initialize(Stream filamentStream) {
        Engine engine = EngineInstance.getEngine();
        this.filamentStream = filamentStream;
        Sampler textureSampler = Sampler.SAMPLER_EXTERNAL;
        this.filamentTexture = new Texture.Builder().sampler(textureSampler).format(InternalFormat.RGB8).build(engine);
        this.filamentTexture.setExternalStream(engine, filamentStream);
    }
}
