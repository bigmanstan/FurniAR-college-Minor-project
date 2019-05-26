package com.google.ar.core;

import com.google.ar.core.exceptions.FatalException;

public enum TrackingState {
    TRACKING(0),
    PAUSED(1),
    STOPPED(2);
    
    final int nativeCode;

    private TrackingState(int i) {
        this.nativeCode = i;
    }

    static TrackingState forNumber(int i) {
        for (TrackingState trackingState : values()) {
            if (trackingState.nativeCode == i) {
                return trackingState;
            }
        }
        StringBuilder stringBuilder = new StringBuilder(60);
        stringBuilder.append("Unexpected value for native TrackingState, value=");
        stringBuilder.append(i);
        throw new FatalException(stringBuilder.toString());
    }
}
