package com.google.ar.sceneform.rendering;

import android.util.Log;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

class FutureHelper {
    private FutureHelper() {
    }

    static <T> CompletableFuture<T> logOnException(String tag, CompletableFuture<T> input, String errorMsg) {
        input.exceptionally(new -$$Lambda$FutureHelper$_bEL0yASDMpXlbrI_eNbaRh7Fn4(tag, errorMsg));
        return input;
    }

    static /* synthetic */ Object lambda$logOnException$0(String tag, String errorMsg, Throwable throwable) {
        Log.e(tag, errorMsg, throwable);
        throw new CompletionException(throwable);
    }
}
