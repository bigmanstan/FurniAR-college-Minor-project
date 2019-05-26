package com.google.ar.sceneform.utilities;

import android.content.res.AssetManager;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public final class RendercoreBufferUtils {
    private static final int DEFAULT_BLOCK_SIZE = 8192;
    private static final String TAG = RendercoreBufferUtils.class.getSimpleName();

    private RendercoreBufferUtils() {
    }

    private static int copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[8192];
        int i = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read > 0) {
                i += read;
                outputStream.write(bArr, 0, read);
            } else {
                outputStream.flush();
                return i;
            }
        }
    }

    public static ByteBuffer copyByteBuffer(ByteBuffer byteBuffer) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[8192];
        int limit = byteBuffer.limit();
        while (byteBuffer.position() < limit) {
            int position = byteBuffer.position();
            byteBuffer.get(bArr, 0, Math.min(8192, limit - position));
            byteArrayOutputStream.write(bArr, 0, byteBuffer.position() - position);
        }
        byteArrayOutputStream.flush();
        return ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
    }

    public static ByteBuffer readFile(AssetManager assetManager, String str) {
        String message;
        try {
            InputStream open = assetManager.open(str);
            ByteBuffer readStream = readStream(open);
            if (open != null) {
                try {
                    open.close();
                } catch (IOException e) {
                    String str2 = TAG;
                    message = e.getMessage();
                    StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 46) + String.valueOf(message).length());
                    stringBuilder.append("Failed to close the input stream from file ");
                    stringBuilder.append(str);
                    stringBuilder.append(" - ");
                    stringBuilder.append(message);
                    Log.e(str2, stringBuilder.toString());
                }
            }
            return readStream;
        } catch (IOException e2) {
            String str3 = TAG;
            message = e2.getMessage();
            StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(str).length() + 23) + String.valueOf(message).length());
            stringBuilder2.append("Failed to read file ");
            stringBuilder2.append(str);
            stringBuilder2.append(" - ");
            stringBuilder2.append(message);
            Log.e(str3, stringBuilder2.toString());
            return null;
        }
    }

    public static ByteBuffer readStream(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        ByteBuffer wrap;
        try {
            wrap = ByteBuffer.wrap(toByteArray(inputStream));
        } catch (IOException e) {
            String str = TAG;
            String str2 = "Failed to read stream - ";
            String valueOf = String.valueOf(e.getMessage());
            Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            wrap = null;
        }
        return wrap;
    }

    private static byte[] toByteArray(InputStream inputStream) throws IOException {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copy(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
