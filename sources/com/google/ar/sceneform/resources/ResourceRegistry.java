package com.google.ar.sceneform.resources;

import com.google.ar.sceneform.utilities.Preconditions;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ResourceRegistry<T> {
    private static final String TAG = ResourceRegistry.class.getSimpleName();
    private final Map<Object, CompletableFuture<T>> futureRegistry = new HashMap();
    private final Object lock = new Object();
    private final Map<Object, WeakReference<T>> registry = new HashMap();

    public CompletableFuture<T> get(Object obj) {
        Preconditions.checkNotNull(obj, "Parameter 'id' was null.");
        synchronized (this.lock) {
            CompletableFuture<T> completedFuture;
            WeakReference weakReference = (WeakReference) this.registry.get(obj);
            if (weakReference != null) {
                Object obj2 = weakReference.get();
                if (obj2 != null) {
                    completedFuture = CompletableFuture.completedFuture(obj2);
                    return completedFuture;
                }
                this.registry.remove(obj);
            }
            completedFuture = (CompletableFuture) this.futureRegistry.get(obj);
            return completedFuture;
        }
    }

    final /* synthetic */ void lambda$register$0$ResourceRegistry(Object obj, CompletableFuture completableFuture, Object obj2) {
        synchronized (this) {
            synchronized (this.lock) {
                if (((CompletableFuture) this.futureRegistry.get(obj)) == completableFuture) {
                    this.futureRegistry.remove(obj);
                    this.registry.put(obj, new WeakReference(obj2));
                }
            }
        }
    }

    public void register(Object obj, CompletableFuture<T> completableFuture) {
        Preconditions.checkNotNull(obj, "Parameter 'id' was null.");
        Preconditions.checkNotNull(completableFuture, "Parameter 'futureResource' was null.");
        if (completableFuture.isDone()) {
            Object checkNotNull = Preconditions.checkNotNull(completableFuture.getNow(null));
            synchronized (this.lock) {
                this.registry.put(obj, new WeakReference(checkNotNull));
                this.futureRegistry.remove(obj);
            }
            return;
        }
        synchronized (this.lock) {
            this.futureRegistry.put(obj, completableFuture);
            this.registry.remove(obj);
        }
        completableFuture.thenAccept(new C0413a(this, obj, completableFuture));
    }
}
