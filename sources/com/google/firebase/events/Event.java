package com.google.firebase.events;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;

@KeepForSdk
public class Event<T> {
    private final Class<T> zza;
    private final T zzb;

    @KeepForSdk
    public Event(Class<T> type, T payload) {
        this.zza = (Class) Preconditions.checkNotNull(type);
        this.zzb = Preconditions.checkNotNull(payload);
    }

    @KeepForSdk
    public Class<T> getType() {
        return this.zza;
    }

    @KeepForSdk
    public T getPayload() {
        return this.zzb;
    }

    public String toString() {
        return String.format("Event{type: %s, payload: %s}", new Object[]{this.zza, this.zzb});
    }
}
