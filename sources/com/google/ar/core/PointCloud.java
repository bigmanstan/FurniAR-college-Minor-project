package com.google.ar.core;

import com.google.ar.core.exceptions.DeadlineExceededException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class PointCloud {
    private long nativeHandle;
    private final Session session;

    protected PointCloud() {
        this.nativeHandle = 0;
        this.session = null;
        this.nativeHandle = 0;
    }

    PointCloud(Session session, long j) {
        this.nativeHandle = 0;
        this.session = session;
        this.nativeHandle = j;
    }

    private native ByteBuffer nativeGetData(long j, long j2);

    private native long nativeGetTimestamp(long j, long j2);

    private native void nativeReleasePointCloud(long j);

    protected void finalize() throws Throwable {
        if (this.nativeHandle != 0) {
            nativeReleasePointCloud(this.nativeHandle);
        }
        super.finalize();
    }

    public FloatBuffer getPoints() {
        if (this.nativeHandle != 0) {
            ByteBuffer nativeGetData = nativeGetData(this.session.nativeHandle, this.nativeHandle);
            if (nativeGetData == null) {
                nativeGetData = ByteBuffer.allocateDirect(0);
            }
            return nativeGetData.order(ByteOrder.nativeOrder()).asFloatBuffer();
        }
        throw new DeadlineExceededException();
    }

    public long getTimestamp() {
        if (this.nativeHandle != 0) {
            return nativeGetTimestamp(this.session.nativeHandle, this.nativeHandle);
        }
        throw new DeadlineExceededException();
    }

    public void release() {
        nativeReleasePointCloud(this.nativeHandle);
        this.nativeHandle = 0;
    }
}
