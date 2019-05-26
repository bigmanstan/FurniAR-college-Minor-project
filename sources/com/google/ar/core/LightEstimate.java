package com.google.ar.core;

import com.google.ar.core.exceptions.FatalException;

public class LightEstimate {
    static final int AR_LIGHT_ESTIMATE_STATE_NOT_VALID = 0;
    static final int AR_LIGHT_ESTIMATE_STATE_VALID = 1;
    long nativeHandle;
    private final Session session;

    public enum State {
        NOT_VALID(0),
        VALID(1);
        
        final int nativeCode;

        private State(int i) {
            this.nativeCode = i;
        }

        static State forNumber(int i) {
            for (State state : values()) {
                if (state.nativeCode == i) {
                    return state;
                }
            }
            StringBuilder stringBuilder = new StringBuilder(66);
            stringBuilder.append("Unexpected value for native LightEstimate.State, value=");
            stringBuilder.append(i);
            throw new FatalException(stringBuilder.toString());
        }
    }

    protected LightEstimate() {
        this.nativeHandle = 0;
        this.session = null;
        this.nativeHandle = 0;
    }

    LightEstimate(Session session) {
        this.nativeHandle = 0;
        this.session = session;
        this.nativeHandle = nativeCreateLightEstimate(session.nativeHandle);
    }

    private static native long nativeCreateLightEstimate(long j);

    private static native void nativeDestroyLightEstimate(long j);

    private native void nativeGetColorCorrection(long j, long j2, float[] fArr, int i);

    private native float nativeGetPixelIntensity(long j, long j2);

    private native int nativeGetState(long j, long j2);

    protected void finalize() throws Throwable {
        if (this.nativeHandle != 0) {
            nativeDestroyLightEstimate(this.nativeHandle);
        }
        super.finalize();
    }

    public void getColorCorrection(float[] fArr, int i) {
        nativeGetColorCorrection(this.session.nativeHandle, this.nativeHandle, fArr, i);
    }

    public float getPixelIntensity() {
        return nativeGetPixelIntensity(this.session.nativeHandle, this.nativeHandle);
    }

    public State getState() {
        return State.forNumber(nativeGetState(this.session.nativeHandle, this.nativeHandle));
    }
}
