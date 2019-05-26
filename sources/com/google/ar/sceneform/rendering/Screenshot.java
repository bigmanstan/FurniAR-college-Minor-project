package com.google.ar.sceneform.rendering;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.SurfaceView;
import com.google.android.filament.Renderer;
import com.google.android.filament.Texture.Format;
import com.google.android.filament.Texture.PixelBufferDescriptor;
import com.google.android.filament.Texture.Type;
import com.google.ar.sceneform.rendering.Renderer.OnScreenshotListener;
import com.google.ar.sceneform.rendering.Renderer.ScreenshotType;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicBoolean;

@Deprecated
public class Screenshot {
    private static final String TAG = Screenshot.class.getSimpleName();
    private final AtomicBoolean atomicPboLocked;
    private final CameraStream cameraStream;
    private boolean dirty = false;
    private final Handler handler;
    private final PboCallback pboCallback;
    private final Renderer renderer;
    @Nullable
    private PixelBufferDescriptor screenshotCapturePbo = null;
    private int screenshotHeight = 0;
    @Nullable
    private ByteBuffer screenshotImageStorage = null;
    private int screenshotWidth = 0;
    private final SurfaceView surfaceView;
    private ScreenshotType type;

    private class PboCallback implements Runnable {
        @Nullable
        private OnScreenshotListener userListener;

        private PboCallback() {
            this.userListener = null;
        }

        void setUserListener(OnScreenshotListener userListener) {
            this.userListener = userListener;
        }

        OnScreenshotListener getUserListener() {
            checkForError();
            return this.userListener;
        }

        public void run() {
            checkForError();
            Screenshot.this.screenshotImageStorage.rewind();
            this.userListener.onScreenshotResult(Screenshot.this.screenshotImageStorage, Screenshot.this.screenshotWidth, Screenshot.this.screenshotHeight);
            Screenshot.this.atomicPboLocked.set(false);
        }

        private void checkForError() {
            if (this.userListener == null) {
                throw new AssertionError("Screenshot results listener should never be null and should have been caught much ealier, something has gone terribly wrong.");
            }
        }
    }

    public Screenshot(Renderer renderer, CameraStream cameraStream, SurfaceView surfaceView) {
        this.renderer = renderer;
        this.cameraStream = cameraStream;
        this.surfaceView = surfaceView;
        this.atomicPboLocked = new AtomicBoolean(false);
        this.pboCallback = new PboCallback();
        this.handler = new Handler();
    }

    public boolean queueCapture(ScreenshotType type, OnScreenshotListener listener) {
        if (this.atomicPboLocked.get()) {
            return false;
        }
        boolean result = false;
        this.type = type;
        if (allocateScreenshotBuffers(type)) {
            this.atomicPboLocked.set(true);
            this.pboCallback.setUserListener(listener);
            this.dirty = true;
            result = true;
        }
        return result;
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public void capture() {
        boolean success = true;
        this.screenshotImageStorage.rewind();
        if (this.type == ScreenshotType.CAMERA_STREAM) {
            success = this.cameraStream.captureCameraImage(this.screenshotCapturePbo);
        } else {
            this.renderer.readPixels(0, 0, this.screenshotWidth, this.screenshotHeight, this.screenshotCapturePbo);
        }
        if (!success) {
            this.pboCallback.getUserListener().onScreenshotResult(null, 0, 0);
            this.atomicPboLocked.set(false);
        }
        this.dirty = false;
    }

    private boolean allocateScreenshotBuffers(ScreenshotType type) {
        int width;
        int height;
        if (type == ScreenshotType.CAMERA_STREAM) {
            width = this.cameraStream.getCameraWidth();
            height = this.cameraStream.getCameraHeight();
        } else {
            width = this.surfaceView.getWidth();
            height = this.surfaceView.getHeight();
        }
        if (width >= 1) {
            if (height >= 1) {
                if (!(this.screenshotImageStorage != null && this.screenshotWidth == width && this.screenshotHeight == height)) {
                    this.screenshotWidth = width;
                    this.screenshotHeight = height;
                    this.screenshotImageStorage = ByteBuffer.allocateDirect((this.screenshotWidth * 4) * this.screenshotHeight).order(ByteOrder.nativeOrder());
                    this.screenshotCapturePbo = new PixelBufferDescriptor(this.screenshotImageStorage, Format.RGBA, Type.UBYTE);
                    if (this.screenshotCapturePbo != null) {
                        this.screenshotCapturePbo.stride = this.screenshotWidth;
                        this.screenshotCapturePbo.setCallback(this.handler, this.pboCallback);
                    }
                }
                if (this.screenshotImageStorage != null) {
                    if (this.screenshotCapturePbo != null) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }
}
