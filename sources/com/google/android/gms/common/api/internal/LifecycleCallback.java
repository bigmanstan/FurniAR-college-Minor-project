package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.MainThread;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.io.FileDescriptor;
import java.io.PrintWriter;

@KeepForSdk
public class LifecycleCallback {
    @KeepForSdk
    protected final LifecycleFragment mLifecycleFragment;

    @KeepForSdk
    protected LifecycleCallback(LifecycleFragment lifecycleFragment) {
        this.mLifecycleFragment = lifecycleFragment;
    }

    @Keep
    private static LifecycleFragment getChimeraLifecycleFragmentImpl(LifecycleActivity lifecycleActivity) {
        throw new IllegalStateException("Method not available in SDK.");
    }

    @KeepForSdk
    public static LifecycleFragment getFragment(Activity activity) {
        return getFragment(new LifecycleActivity(activity));
    }

    @KeepForSdk
    protected static LifecycleFragment getFragment(LifecycleActivity lifecycleActivity) {
        if (lifecycleActivity.zzbv()) {
            return zzcc.zza(lifecycleActivity.zzby());
        }
        if (lifecycleActivity.zzbw()) {
            return zzbr.zzc(lifecycleActivity.zzbx());
        }
        throw new IllegalArgumentException("Can't get fragment for unexpected activity.");
    }

    @MainThread
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public final Activity getActivity() {
        return this.mLifecycleFragment.getLifecycleActivity();
    }

    @MainThread
    public void onActivityResult(int i, int i2, Intent intent) {
    }

    @MainThread
    public void onCreate(Bundle bundle) {
    }

    @MainThread
    public void onDestroy() {
    }

    @MainThread
    public void onResume() {
    }

    @MainThread
    public void onSaveInstanceState(Bundle bundle) {
    }

    @MainThread
    public void onStart() {
    }

    @KeepForSdk
    @MainThread
    public void onStop() {
    }
}
