package android.arch.core.executor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import java.util.concurrent.Executor;

@RestrictTo({Scope.LIBRARY_GROUP})
public class ArchTaskExecutor extends TaskExecutor {
    @NonNull
    private static final Executor sIOThreadExecutor = new C00442();
    private static volatile ArchTaskExecutor sInstance;
    @NonNull
    private static final Executor sMainThreadExecutor = new C00431();
    @NonNull
    private TaskExecutor mDefaultTaskExecutor = new DefaultTaskExecutor();
    @NonNull
    private TaskExecutor mDelegate = this.mDefaultTaskExecutor;

    /* renamed from: android.arch.core.executor.ArchTaskExecutor$1 */
    static class C00431 implements Executor {
        C00431() {
        }

        public void execute(Runnable command) {
            ArchTaskExecutor.getInstance().postToMainThread(command);
        }
    }

    /* renamed from: android.arch.core.executor.ArchTaskExecutor$2 */
    static class C00442 implements Executor {
        C00442() {
        }

        public void execute(Runnable command) {
            ArchTaskExecutor.getInstance().executeOnDiskIO(command);
        }
    }

    private ArchTaskExecutor() {
    }

    @NonNull
    public static ArchTaskExecutor getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (ArchTaskExecutor.class) {
            if (sInstance == null) {
                sInstance = new ArchTaskExecutor();
            }
        }
        return sInstance;
    }

    public void setDelegate(@Nullable TaskExecutor taskExecutor) {
        this.mDelegate = taskExecutor == null ? this.mDefaultTaskExecutor : taskExecutor;
    }

    public void executeOnDiskIO(Runnable runnable) {
        this.mDelegate.executeOnDiskIO(runnable);
    }

    public void postToMainThread(Runnable runnable) {
        this.mDelegate.postToMainThread(runnable);
    }

    @NonNull
    public static Executor getMainThreadExecutor() {
        return sMainThreadExecutor;
    }

    @NonNull
    public static Executor getIOThreadExecutor() {
        return sIOThreadExecutor;
    }

    public boolean isMainThread() {
        return this.mDelegate.isMainThread();
    }
}
