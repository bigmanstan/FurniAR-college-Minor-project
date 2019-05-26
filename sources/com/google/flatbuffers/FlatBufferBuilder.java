package com.google.flatbuffers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.Arrays;

public class FlatBufferBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final Charset utf8charset = Charset.forName("UTF-8");
    ByteBuffer bb;
    ByteBufferFactory bb_factory;
    ByteBuffer dst;
    CharsetEncoder encoder;
    boolean finished;
    boolean force_defaults;
    int minalign;
    boolean nested;
    int num_vtables;
    int object_start;
    int space;
    int vector_num_elems;
    int[] vtable;
    int vtable_in_use;
    int[] vtables;

    public interface ByteBufferFactory {
        ByteBuffer newByteBuffer(int i);
    }

    /* renamed from: com.google.flatbuffers.FlatBufferBuilder$a */
    static class C0421a extends InputStream {
        /* renamed from: a */
        private ByteBuffer f142a;

        public C0421a(ByteBuffer byteBuffer) {
            this.f142a = byteBuffer;
        }

        public final int read() throws IOException {
            try {
                return this.f142a.get() & 255;
            } catch (BufferUnderflowException e) {
                return -1;
            }
        }
    }

    public static final class HeapByteBufferFactory implements ByteBufferFactory {
        public final ByteBuffer newByteBuffer(int i) {
            return ByteBuffer.allocate(i).order(ByteOrder.LITTLE_ENDIAN);
        }
    }

    public FlatBufferBuilder() {
        this(1024);
    }

    public FlatBufferBuilder(int i) {
        this(i, new HeapByteBufferFactory());
    }

    public FlatBufferBuilder(int i, ByteBufferFactory byteBufferFactory) {
        this.minalign = 1;
        this.vtable = null;
        this.vtable_in_use = 0;
        this.nested = false;
        this.finished = false;
        this.vtables = new int[16];
        this.num_vtables = 0;
        this.vector_num_elems = 0;
        this.force_defaults = false;
        this.encoder = utf8charset.newEncoder();
        if (i <= 0) {
            i = 1;
        }
        this.space = i;
        this.bb_factory = byteBufferFactory;
        this.bb = byteBufferFactory.newByteBuffer(i);
    }

    public FlatBufferBuilder(ByteBuffer byteBuffer) {
        this.minalign = 1;
        this.vtable = null;
        this.vtable_in_use = 0;
        this.nested = false;
        this.finished = false;
        this.vtables = new int[16];
        this.num_vtables = 0;
        this.vector_num_elems = 0;
        this.force_defaults = false;
        this.encoder = utf8charset.newEncoder();
        init(byteBuffer, new HeapByteBufferFactory());
    }

    public FlatBufferBuilder(ByteBuffer byteBuffer, ByteBufferFactory byteBufferFactory) {
        this.minalign = 1;
        this.vtable = null;
        this.vtable_in_use = 0;
        this.nested = false;
        this.finished = false;
        this.vtables = new int[16];
        this.num_vtables = 0;
        this.vector_num_elems = 0;
        this.force_defaults = false;
        this.encoder = utf8charset.newEncoder();
        init(byteBuffer, byteBufferFactory);
    }

    @Deprecated
    private int dataStart() {
        finished();
        return this.space;
    }

    static ByteBuffer growByteBuffer(ByteBuffer byteBuffer, ByteBufferFactory byteBufferFactory) {
        int capacity = byteBuffer.capacity();
        if ((-1073741824 & capacity) == 0) {
            int i = capacity << 1;
            byteBuffer.position(0);
            ByteBuffer newByteBuffer = byteBufferFactory.newByteBuffer(i);
            newByteBuffer.position(i - capacity);
            newByteBuffer.put(byteBuffer);
            return newByteBuffer;
        }
        throw new AssertionError("FlatBuffers: cannot grow buffer beyond 2 gigabytes.");
    }

    public void Nested(int i) {
        if (i != offset()) {
            throw new AssertionError("FlatBuffers: struct must be serialized inline.");
        }
    }

    public void addBoolean(int i, boolean z, boolean z2) {
        if (this.force_defaults || z != z2) {
            addBoolean(z);
            slot(i);
        }
    }

    public void addBoolean(boolean z) {
        prep(1, 0);
        putBoolean(z);
    }

    public void addByte(byte b) {
        prep(1, 0);
        putByte(b);
    }

    public void addByte(int i, byte b, int i2) {
        if (this.force_defaults || b != i2) {
            addByte(b);
            slot(i);
        }
    }

    public void addDouble(double d) {
        prep(8, 0);
        putDouble(d);
    }

    public void addDouble(int i, double d, double d2) {
        if (this.force_defaults || d != d2) {
            addDouble(d);
            slot(i);
        }
    }

    public void addFloat(float f) {
        prep(4, 0);
        putFloat(f);
    }

    public void addFloat(int i, float f, double d) {
        if (this.force_defaults || ((double) f) != d) {
            addFloat(f);
            slot(i);
        }
    }

    public void addInt(int i) {
        prep(4, 0);
        putInt(i);
    }

    public void addInt(int i, int i2, int i3) {
        if (this.force_defaults || i2 != i3) {
            addInt(i2);
            slot(i);
        }
    }

    public void addLong(int i, long j, long j2) {
        if (this.force_defaults || j != j2) {
            addLong(j);
            slot(i);
        }
    }

    public void addLong(long j) {
        prep(8, 0);
        putLong(j);
    }

    public void addOffset(int i) {
        prep(4, 0);
        putInt((offset() - i) + 4);
    }

    public void addOffset(int i, int i2, int i3) {
        if (this.force_defaults || i2 != i3) {
            addOffset(i2);
            slot(i);
        }
    }

    public void addShort(int i, short s, int i2) {
        if (this.force_defaults || s != i2) {
            addShort(s);
            slot(i);
        }
    }

    public void addShort(short s) {
        prep(2, 0);
        putShort(s);
    }

    public void addStruct(int i, int i2, int i3) {
        if (i2 != i3) {
            Nested(i2);
            slot(i);
        }
    }

    public void clear() {
        this.space = this.bb.capacity();
        this.bb.clear();
        this.minalign = 1;
        while (this.vtable_in_use > 0) {
            int[] iArr = this.vtable;
            int i = this.vtable_in_use - 1;
            this.vtable_in_use = i;
            iArr[i] = 0;
        }
        this.vtable_in_use = 0;
        this.nested = false;
        this.finished = false;
        this.object_start = 0;
        this.num_vtables = 0;
        this.vector_num_elems = 0;
    }

    public int createByteVector(byte[] bArr) {
        int length = bArr.length;
        startVector(1, length, 1);
        ByteBuffer byteBuffer = this.bb;
        int i = this.space - length;
        this.space = i;
        byteBuffer.position(i);
        this.bb.put(bArr);
        return endVector();
    }

    public <T extends Table> int createSortedVectorOfTables(T t, int[] iArr) {
        t.sortTables(iArr, this.bb);
        return createVectorOfTables(iArr);
    }

    public int createString(CharSequence charSequence) {
        int length = (int) (((float) charSequence.length()) * this.encoder.maxBytesPerChar());
        if (this.dst == null || this.dst.capacity() < length) {
            this.dst = ByteBuffer.allocate(Math.max(128, length));
        }
        this.dst.clear();
        CoderResult encode = this.encoder.encode(charSequence instanceof CharBuffer ? (CharBuffer) charSequence : CharBuffer.wrap(charSequence), this.dst, true);
        if (encode.isError()) {
            try {
                encode.throwException();
            } catch (Throwable e) {
                throw new Error(e);
            }
        }
        this.dst.flip();
        return createString(this.dst);
    }

    public int createString(ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        addByte((byte) 0);
        startVector(1, remaining, 1);
        ByteBuffer byteBuffer2 = this.bb;
        int i = this.space - remaining;
        this.space = i;
        byteBuffer2.position(i);
        this.bb.put(byteBuffer);
        return endVector();
    }

    public ByteBuffer createUnintializedVector(int i, int i2, int i3) {
        int i4 = i * i2;
        startVector(i, i2, i3);
        ByteBuffer byteBuffer = this.bb;
        i2 = this.space - i4;
        this.space = i2;
        byteBuffer.position(i2);
        byteBuffer = this.bb.slice().order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.limit(i4);
        return byteBuffer;
    }

    public int createVectorOfTables(int[] iArr) {
        notNested();
        startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            addOffset(iArr[length]);
        }
        return endVector();
    }

    public ByteBuffer dataBuffer() {
        finished();
        return this.bb;
    }

    public int endObject() {
        if (this.vtable == null || !this.nested) {
            throw new AssertionError("FlatBuffers: endObject called without startObject");
        }
        addInt(0);
        int offset = offset();
        int i = this.vtable_in_use - 1;
        while (i >= 0 && this.vtable[i] == 0) {
            i--;
        }
        int i2 = i + 1;
        while (i >= 0) {
            addShort((short) (this.vtable[i] != 0 ? offset - this.vtable[i] : 0));
            i--;
        }
        addShort((short) (offset - this.object_start));
        addShort((short) ((i2 + 2) << 1));
        loop2:
        for (i2 = 0; i2 < this.num_vtables; i2++) {
            int capacity = this.bb.capacity() - this.vtables[i2];
            int i3 = this.space;
            short s = this.bb.getShort(capacity);
            if (s == this.bb.getShort(i3)) {
                short s2 = (short) 2;
                while (s2 < s) {
                    if (this.bb.getShort(capacity + s2) == this.bb.getShort(i3 + s2)) {
                        s2 += 2;
                    }
                }
                i = this.vtables[i2];
                break loop2;
            }
        }
        i = 0;
        if (i != 0) {
            this.space = this.bb.capacity() - offset;
            this.bb.putInt(this.space, i - offset);
        } else {
            if (this.num_vtables == this.vtables.length) {
                this.vtables = Arrays.copyOf(this.vtables, this.num_vtables << 1);
            }
            int[] iArr = this.vtables;
            i2 = this.num_vtables;
            this.num_vtables = i2 + 1;
            iArr[i2] = offset();
            this.bb.putInt(this.bb.capacity() - offset, offset() - offset);
        }
        this.nested = false;
        return offset;
    }

    public int endVector() {
        if (this.nested) {
            this.nested = false;
            putInt(this.vector_num_elems);
            return offset();
        }
        throw new AssertionError("FlatBuffers: endVector called without startVector");
    }

    public void finish(int i) {
        prep(this.minalign, 4);
        addOffset(i);
        this.bb.position(this.space);
        this.finished = true;
    }

    public void finish(int i, String str) {
        prep(this.minalign, 8);
        if (str.length() == 4) {
            for (int i2 = 3; i2 >= 0; i2--) {
                addByte((byte) str.charAt(i2));
            }
            finish(i);
            return;
        }
        throw new AssertionError("FlatBuffers: file identifier must be length 4");
    }

    public void finished() {
        if (!this.finished) {
            throw new AssertionError("FlatBuffers: you can only access the serialized buffer after it has been finished by FlatBufferBuilder.finish().");
        }
    }

    public FlatBufferBuilder forceDefaults(boolean z) {
        this.force_defaults = z;
        return this;
    }

    public FlatBufferBuilder init(ByteBuffer byteBuffer, ByteBufferFactory byteBufferFactory) {
        this.bb_factory = byteBufferFactory;
        this.bb = byteBuffer;
        this.bb.clear();
        this.bb.order(ByteOrder.LITTLE_ENDIAN);
        this.minalign = 1;
        this.space = this.bb.capacity();
        this.vtable_in_use = 0;
        this.nested = false;
        this.finished = false;
        this.object_start = 0;
        this.num_vtables = 0;
        this.vector_num_elems = 0;
        return this;
    }

    public void notNested() {
        if (this.nested) {
            throw new AssertionError("FlatBuffers: object serialization must not be nested.");
        }
    }

    public int offset() {
        return this.bb.capacity() - this.space;
    }

    public void pad(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            ByteBuffer byteBuffer = this.bb;
            int i3 = this.space - 1;
            this.space = i3;
            byteBuffer.put(i3, (byte) 0);
        }
    }

    public void prep(int i, int i2) {
        if (i > this.minalign) {
            this.minalign = i;
        }
        int i3 = ((~((this.bb.capacity() - this.space) + i2)) + 1) & (i - 1);
        while (this.space < (i3 + i) + i2) {
            int capacity = this.bb.capacity();
            this.bb = growByteBuffer(this.bb, this.bb_factory);
            this.space += this.bb.capacity() - capacity;
        }
        pad(i3);
    }

    public void putBoolean(boolean z) {
        ByteBuffer byteBuffer = this.bb;
        int i = this.space - 1;
        this.space = i;
        byteBuffer.put(i, (byte) z);
    }

    public void putByte(byte b) {
        ByteBuffer byteBuffer = this.bb;
        int i = this.space - 1;
        this.space = i;
        byteBuffer.put(i, b);
    }

    public void putDouble(double d) {
        ByteBuffer byteBuffer = this.bb;
        int i = this.space - 8;
        this.space = i;
        byteBuffer.putDouble(i, d);
    }

    public void putFloat(float f) {
        ByteBuffer byteBuffer = this.bb;
        int i = this.space - 4;
        this.space = i;
        byteBuffer.putFloat(i, f);
    }

    public void putInt(int i) {
        ByteBuffer byteBuffer = this.bb;
        int i2 = this.space - 4;
        this.space = i2;
        byteBuffer.putInt(i2, i);
    }

    public void putLong(long j) {
        ByteBuffer byteBuffer = this.bb;
        int i = this.space - 8;
        this.space = i;
        byteBuffer.putLong(i, j);
    }

    public void putShort(short s) {
        ByteBuffer byteBuffer = this.bb;
        int i = this.space - 2;
        this.space = i;
        byteBuffer.putShort(i, s);
    }

    public void required(int i, int i2) {
        int capacity = this.bb.capacity() - i;
        if ((this.bb.getShort((capacity - this.bb.getInt(capacity)) + i2) != (short) 0 ? 1 : null) == null) {
            StringBuilder stringBuilder = new StringBuilder(42);
            stringBuilder.append("FlatBuffers: field ");
            stringBuilder.append(i2);
            stringBuilder.append(" must be set");
            throw new AssertionError(stringBuilder.toString());
        }
    }

    public byte[] sizedByteArray() {
        return sizedByteArray(this.space, this.bb.capacity() - this.space);
    }

    public byte[] sizedByteArray(int i, int i2) {
        finished();
        byte[] bArr = new byte[i2];
        this.bb.position(i);
        this.bb.get(bArr);
        return bArr;
    }

    public InputStream sizedInputStream() {
        finished();
        ByteBuffer duplicate = this.bb.duplicate();
        duplicate.position(this.space);
        duplicate.limit(this.bb.capacity());
        return new C0421a(duplicate);
    }

    public void slot(int i) {
        this.vtable[i] = offset();
    }

    public void startObject(int i) {
        notNested();
        if (this.vtable == null || this.vtable.length < i) {
            this.vtable = new int[i];
        }
        this.vtable_in_use = i;
        Arrays.fill(this.vtable, 0, this.vtable_in_use, 0);
        this.nested = true;
        this.object_start = offset();
    }

    public void startVector(int i, int i2, int i3) {
        notNested();
        this.vector_num_elems = i2;
        i *= i2;
        prep(4, i);
        prep(i3, i);
        this.nested = true;
    }
}
