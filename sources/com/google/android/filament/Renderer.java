package com.google.android.filament;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import com.google.android.filament.Texture.PixelBufferDescriptor;
import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.ReadOnlyBufferException;

public class Renderer {
    private final Engine mEngine;
    private long mNativeObject;

    private static native boolean nBeginFrame(long j, long j2);

    private static native void nEndFrame(long j);

    private static native int nReadPixels(long j, long j2, int i, int i2, int i3, int i4, Buffer buffer, int i5, int i6, int i7, int i8, int i9, int i10, int i11, Object obj, Runnable runnable);

    private static native void nRender(long j, long j2);

    Renderer(@NonNull Engine engine, long nativeRenderer) {
        this.mEngine = engine;
        this.mNativeObject = nativeRenderer;
    }

    @NonNull
    public Engine getEngine() {
        return this.mEngine;
    }

    public boolean beginFrame(@NonNull SwapChain swapChain) {
        return nBeginFrame(getNativeObject(), swapChain.getNativeObject());
    }

    public void endFrame() {
        nEndFrame(getNativeObject());
    }

    public void render(@NonNull View view) {
        nRender(getNativeObject(), view.getNativeObject());
    }

    public void readPixels(@IntRange(from = 0) int xoffset, @IntRange(from = 0) int yoffset, @IntRange(from = 0) int width, @IntRange(from = 0) int height, @NonNull PixelBufferDescriptor buffer) {
        PixelBufferDescriptor pixelBufferDescriptor = buffer;
        if (pixelBufferDescriptor.storage.isReadOnly()) {
            Renderer renderer = this;
            throw new ReadOnlyBufferException();
        }
        if (nReadPixels(getNativeObject(), this.mEngine.getNativeObject(), xoffset, yoffset, width, height, pixelBufferDescriptor.storage, pixelBufferDescriptor.storage.remaining(), pixelBufferDescriptor.left, pixelBufferDescriptor.top, pixelBufferDescriptor.type.ordinal(), pixelBufferDescriptor.alignment, pixelBufferDescriptor.stride, pixelBufferDescriptor.format.ordinal(), pixelBufferDescriptor.handler, pixelBufferDescriptor.callback) < 0) {
            throw new BufferOverflowException();
        }
    }

    long getNativeObject() {
        if (this.mNativeObject != 0) {
            return this.mNativeObject;
        }
        throw new IllegalStateException("Calling method on destroyed Renderer");
    }

    void clearNativeObject() {
        this.mNativeObject = 0;
    }
}
