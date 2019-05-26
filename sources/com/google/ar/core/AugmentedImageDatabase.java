package com.google.ar.core;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;

public class AugmentedImageDatabase {
    long nativeHandle;
    long sessionHandle;

    AugmentedImageDatabase(long j, long j2) {
        this.sessionHandle = j;
        this.nativeHandle = j2;
    }

    public AugmentedImageDatabase(Session session) {
        this(session.nativeHandle, nativeCreate(session.nativeHandle));
    }

    private static ByteBuffer convertBitmapToGrayscaleDirectBuffer(Bitmap bitmap) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        ColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        Paint paint = new Paint();
        paint.setColorFilter(colorMatrixColorFilter);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        new Canvas(createBitmap).drawBitmap(bitmap, 0.0f, 0.0f, paint);
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(bitmap.getWidth() * bitmap.getHeight());
        for (int i = 0; i < bitmap.getHeight(); i++) {
            for (int i2 = 0; i2 < bitmap.getWidth(); i2++) {
                allocateDirect.put((bitmap.getWidth() * i) + i2, (byte) Color.red(createBitmap.getPixel(i2, i)));
            }
        }
        return allocateDirect;
    }

    public static AugmentedImageDatabase deserialize(Session session, InputStream inputStream) throws IOException {
        return new AugmentedImageDatabase(session.nativeHandle, nativeDeserialize(session.nativeHandle, loadDirectByteBuffer(inputStream)));
    }

    private static ByteBuffer loadDirectByteBuffer(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(Math.max(32, inputStream.available()));
        byte[] bArr = new byte[8192];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                byte[] toByteArray = byteArrayOutputStream.toByteArray();
                ByteBuffer allocateDirect = ByteBuffer.allocateDirect(toByteArray.length);
                allocateDirect.put(toByteArray);
                return allocateDirect;
            }
        }
    }

    private native int nativeAddImage(long j, long j2, String str, ByteBuffer byteBuffer, int i, int i2, int i3);

    private native int nativeAddImageWithPhysicalSize(long j, long j2, String str, ByteBuffer byteBuffer, int i, int i2, int i3, float f);

    private static native long nativeCreate(long j);

    private static native long nativeDeserialize(long j, ByteBuffer byteBuffer);

    private native int nativeGetNumImages(long j, long j2);

    private native void nativeReleaseDatabase(long j);

    private native ByteBuffer nativeSerialize(long j, long j2);

    public int addImage(String str, Bitmap bitmap) {
        return nativeAddImage(this.sessionHandle, this.nativeHandle, str, convertBitmapToGrayscaleDirectBuffer(bitmap), bitmap.getWidth(), bitmap.getHeight(), bitmap.getWidth());
    }

    public int addImage(String str, Bitmap bitmap, float f) {
        return nativeAddImageWithPhysicalSize(this.sessionHandle, this.nativeHandle, str, convertBitmapToGrayscaleDirectBuffer(bitmap), bitmap.getWidth(), bitmap.getHeight(), bitmap.getWidth(), f);
    }

    protected void finalize() throws Throwable {
        if (this.nativeHandle != 0) {
            nativeReleaseDatabase(this.nativeHandle);
        }
        super.finalize();
    }

    public int getNumImages() {
        return nativeGetNumImages(this.sessionHandle, this.nativeHandle);
    }

    public void serialize(OutputStream outputStream) throws IOException {
        Channels.newChannel(outputStream).write(nativeSerialize(this.sessionHandle, this.nativeHandle));
    }
}
