package com.google.firebase.events;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.concurrent.Executor;

@KeepForSdk
public interface Subscriber {
    @KeepForSdk
    <T> void subscribe(Class<T> cls, EventHandler<? super T> eventHandler);

    @KeepForSdk
    <T> void subscribe(Class<T> cls, EventHandler<? super T> eventHandler, Executor executor);

    @KeepForSdk
    <T> void unsubscribe(Class<T> cls, EventHandler<? super T> eventHandler);
}
