package android.support.v7.recyclerview.extensions;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v7.util.DiffUtil.ItemCallback;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class AsyncDifferConfig<T> {
    @NonNull
    private final Executor mBackgroundThreadExecutor;
    @NonNull
    private final ItemCallback<T> mDiffCallback;
    @NonNull
    private final Executor mMainThreadExecutor;

    public static final class Builder<T> {
        private static Executor sDiffExecutor = null;
        private static final Object sExecutorLock = new Object();
        private static final Executor sMainThreadExecutor = new MainThreadExecutor();
        private Executor mBackgroundThreadExecutor;
        private final ItemCallback<T> mDiffCallback;
        private Executor mMainThreadExecutor;

        private static class MainThreadExecutor implements Executor {
            final Handler mHandler;

            private MainThreadExecutor() {
                this.mHandler = new Handler(Looper.getMainLooper());
            }

            public void execute(@NonNull Runnable command) {
                this.mHandler.post(command);
            }
        }

        public Builder(@NonNull ItemCallback<T> diffCallback) {
            this.mDiffCallback = diffCallback;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        @NonNull
        public Builder<T> setMainThreadExecutor(Executor executor) {
            this.mMainThreadExecutor = executor;
            return this;
        }

        @NonNull
        public Builder<T> setBackgroundThreadExecutor(Executor executor) {
            this.mBackgroundThreadExecutor = executor;
            return this;
        }

        @NonNull
        public AsyncDifferConfig<T> build() {
            if (this.mMainThreadExecutor == null) {
                this.mMainThreadExecutor = sMainThreadExecutor;
            }
            if (this.mBackgroundThreadExecutor == null) {
                synchronized (sExecutorLock) {
                    if (sDiffExecutor == null) {
                        sDiffExecutor = Executors.newFixedThreadPool(2);
                    }
                }
                this.mBackgroundThreadExecutor = sDiffExecutor;
            }
            return new AsyncDifferConfig(this.mMainThreadExecutor, this.mBackgroundThreadExecutor, this.mDiffCallback);
        }
    }

    private AsyncDifferConfig(@NonNull Executor mainThreadExecutor, @NonNull Executor backgroundThreadExecutor, @NonNull ItemCallback<T> diffCallback) {
        this.mMainThreadExecutor = mainThreadExecutor;
        this.mBackgroundThreadExecutor = backgroundThreadExecutor;
        this.mDiffCallback = diffCallback;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @NonNull
    public Executor getMainThreadExecutor() {
        return this.mMainThreadExecutor;
    }

    @NonNull
    public Executor getBackgroundThreadExecutor() {
        return this.mBackgroundThreadExecutor;
    }

    @NonNull
    public ItemCallback<T> getDiffCallback() {
        return this.mDiffCallback;
    }
}
