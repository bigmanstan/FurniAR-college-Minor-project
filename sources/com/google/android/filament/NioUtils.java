package com.google.android.filament;

import android.support.annotation.NonNull;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

final class NioUtils {

    enum BufferType {
        BYTE,
        CHAR,
        SHORT,
        INT,
        LONG,
        FLOAT,
        DOUBLE
    }

    private NioUtils() {
    }

    static long getBasePointer(@NonNull Buffer b, long address, int sizeShift) {
        return address != 0 ? ((long) (b.position() << sizeShift)) + address : 0;
    }

    static Object getBaseArray(@NonNull Buffer b) {
        return b.hasArray() ? b.array() : null;
    }

    static int getBaseArrayOffset(@NonNull Buffer b, int sizeShift) {
        return b.hasArray() ? (b.arrayOffset() + b.position()) << sizeShift : 0;
    }

    static int getBufferType(@NonNull Buffer b) {
        if (b instanceof ByteBuffer) {
            return BufferType.BYTE.ordinal();
        }
        if (b instanceof CharBuffer) {
            return BufferType.CHAR.ordinal();
        }
        if (b instanceof ShortBuffer) {
            return BufferType.SHORT.ordinal();
        }
        if (b instanceof IntBuffer) {
            return BufferType.INT.ordinal();
        }
        if (b instanceof LongBuffer) {
            return BufferType.LONG.ordinal();
        }
        if (b instanceof FloatBuffer) {
            return BufferType.FLOAT.ordinal();
        }
        return BufferType.DOUBLE.ordinal();
    }
}
