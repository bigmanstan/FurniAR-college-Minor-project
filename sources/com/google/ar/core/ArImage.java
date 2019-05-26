package com.google.ar.core;

import android.graphics.Rect;
import android.media.Image.Plane;
import com.google.ar.core.exceptions.FatalException;
import java.nio.ByteBuffer;
import p000a.p001a.C0041a;
import p000a.p001a.C0042b;

public class ArImage extends C0041a {
    long nativeHandle;

    /* renamed from: com.google.ar.core.ArImage$a */
    class C0523a extends C0042b {
        /* renamed from: a */
        private final long f156a;
        /* renamed from: b */
        private final int f157b;
        /* renamed from: c */
        private final /* synthetic */ ArImage f158c;

        public C0523a(ArImage arImage, long j, int i) {
            this.f158c = arImage;
            this.f156a = j;
            this.f157b = i;
        }

        public final ByteBuffer getBuffer() {
            return this.f158c.nativeGetBuffer(this.f156a, this.f157b).asReadOnlyBuffer();
        }

        public final int getPixelStride() {
            int access$100 = this.f158c.nativeGetPixelStride(this.f156a, this.f157b);
            if (access$100 != -1) {
                return access$100;
            }
            throw new FatalException("Unknown error in ArImage.Plane.getPixelStride().");
        }

        public final int getRowStride() {
            int access$000 = this.f158c.nativeGetRowStride(this.f156a, this.f157b);
            if (access$000 != -1) {
                return access$000;
            }
            throw new FatalException("Unknown error in ArImage.Plane.getRowStride().");
        }
    }

    public ArImage(long j) {
        this.nativeHandle = j;
    }

    private native void nativeClose(long j);

    private native ByteBuffer nativeGetBuffer(long j, int i);

    private native int nativeGetFormat(long j);

    private native int nativeGetHeight(long j);

    private native int nativeGetNumberOfPlanes(long j);

    private native int nativeGetPixelStride(long j, int i);

    private native int nativeGetRowStride(long j, int i);

    private native long nativeGetTimestamp(long j);

    private native int nativeGetWidth(long j);

    static native void nativeLoadSymbols();

    public void close() {
        nativeClose(this.nativeHandle);
        this.nativeHandle = 0;
    }

    public Rect getCropRect() {
        throw new UnsupportedOperationException("Crop rect is unknown in this image.");
    }

    public int getFormat() {
        int nativeGetFormat = nativeGetFormat(this.nativeHandle);
        if (nativeGetFormat != -1) {
            return nativeGetFormat;
        }
        throw new FatalException("Unknown error in ArImage.getFormat().");
    }

    public int getHeight() {
        int nativeGetHeight = nativeGetHeight(this.nativeHandle);
        if (nativeGetHeight != -1) {
            return nativeGetHeight;
        }
        throw new FatalException("Unknown error in ArImage.getHeight().");
    }

    public Plane[] getPlanes() {
        int nativeGetNumberOfPlanes = nativeGetNumberOfPlanes(this.nativeHandle);
        if (nativeGetNumberOfPlanes != -1) {
            Plane[] planeArr = new C0523a[nativeGetNumberOfPlanes];
            for (int i = 0; i < nativeGetNumberOfPlanes; i++) {
                planeArr[i] = new C0523a(this, this.nativeHandle, i);
            }
            return planeArr;
        }
        throw new FatalException("Unknown error in ArImage.getPlanes().");
    }

    public long getTimestamp() {
        long nativeGetTimestamp = nativeGetTimestamp(this.nativeHandle);
        if (nativeGetTimestamp != -1) {
            return nativeGetTimestamp;
        }
        throw new FatalException("Unknown error in ArImage.getTimestamp().");
    }

    public int getWidth() {
        int nativeGetWidth = nativeGetWidth(this.nativeHandle);
        if (nativeGetWidth != -1) {
            return nativeGetWidth;
        }
        throw new FatalException("Unknown error in ArImage.getWidth().");
    }

    public void setCropRect(Rect rect) {
        throw new UnsupportedOperationException("This is a read-only image.");
    }

    public void setTimestamp(long j) {
        throw new UnsupportedOperationException("This is a read-only image.");
    }
}
