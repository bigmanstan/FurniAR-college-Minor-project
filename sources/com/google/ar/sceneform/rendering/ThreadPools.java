package com.google.ar.sceneform.rendering;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;

class ThreadPools {
    private static Executor mainExecutor;
    private static Executor threadPoolExecutor;

    /* renamed from: com.google.ar.sceneform.rendering.ThreadPools$1 */
    class C04101 implements Executor {
        private final Handler handler = new Handler(Looper.getMainLooper());

        C04101() {
        }

        public void execute(Runnable runnable) {
            this.handler.post(runnable);
        }
    }

    private ThreadPools() {
    }

    public static Executor getMainExecutor() {
        if (mainExecutor == null) {
            mainExecutor = new C04101();
        }
        return mainExecutor;
    }

    public static void setMainExecutor(Executor executor) {
        mainExecutor = executor;
    }

    public static Executor getThreadPoolExecutor() {
        if (threadPoolExecutor == null) {
            return AsyncTask.THREAD_POOL_EXECUTOR;
        }
        return threadPoolExecutor;
    }

    public static void setThreadPoolExecutor(Executor executor) {
        threadPoolExecutor = executor;
    }
}
