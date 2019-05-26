package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

public final class zzcc extends Fragment implements LifecycleFragment {
    private static WeakHashMap<FragmentActivity, WeakReference<zzcc>> zzla = new WeakHashMap();
    private Map<String, LifecycleCallback> zzlb = new ArrayMap();
    private int zzlc = 0;
    private Bundle zzld;

    public static zzcc zza(FragmentActivity fragmentActivity) {
        zzcc zzcc;
        WeakReference weakReference = (WeakReference) zzla.get(fragmentActivity);
        if (weakReference != null) {
            zzcc = (zzcc) weakReference.get();
            if (zzcc != null) {
                return zzcc;
            }
        }
        try {
            zzcc = (zzcc) fragmentActivity.getSupportFragmentManager().findFragmentByTag("SupportLifecycleFragmentImpl");
            if (zzcc == null || zzcc.isRemoving()) {
                zzcc = new zzcc();
                fragmentActivity.getSupportFragmentManager().beginTransaction().add((Fragment) zzcc, "SupportLifecycleFragmentImpl").commitAllowingStateLoss();
            }
            zzla.put(fragmentActivity, new WeakReference(zzcc));
            return zzcc;
        } catch (Throwable e) {
            throw new IllegalStateException("Fragment with tag SupportLifecycleFragmentImpl is not a SupportLifecycleFragmentImpl", e);
        }
    }

    public final void addCallback(String str, @NonNull LifecycleCallback lifecycleCallback) {
        if (this.zzlb.containsKey(str)) {
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 59);
            stringBuilder.append("LifecycleCallback with tag ");
            stringBuilder.append(str);
            stringBuilder.append(" already added to this fragment.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.zzlb.put(str, lifecycleCallback);
        if (this.zzlc > 0) {
            new Handler(Looper.getMainLooper()).post(new zzcd(this, lifecycleCallback, str));
        }
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        for (LifecycleCallback dump : this.zzlb.values()) {
            dump.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    public final <T extends LifecycleCallback> T getCallbackOrNull(String str, Class<T> cls) {
        return (LifecycleCallback) cls.cast(this.zzlb.get(str));
    }

    public final /* synthetic */ Activity getLifecycleActivity() {
        return getActivity();
    }

    public final boolean isCreated() {
        return this.zzlc > 0;
    }

    public final boolean isStarted() {
        return this.zzlc >= 2;
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        for (LifecycleCallback onActivityResult : this.zzlb.values()) {
            onActivityResult.onActivityResult(i, i2, intent);
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzlc = 1;
        this.zzld = bundle;
        for (Entry entry : this.zzlb.entrySet()) {
            ((LifecycleCallback) entry.getValue()).onCreate(bundle != null ? bundle.getBundle((String) entry.getKey()) : null);
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        this.zzlc = 5;
        for (LifecycleCallback onDestroy : this.zzlb.values()) {
            onDestroy.onDestroy();
        }
    }

    public final void onResume() {
        super.onResume();
        this.zzlc = 3;
        for (LifecycleCallback onResume : this.zzlb.values()) {
            onResume.onResume();
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle != null) {
            for (Entry entry : this.zzlb.entrySet()) {
                Bundle bundle2 = new Bundle();
                ((LifecycleCallback) entry.getValue()).onSaveInstanceState(bundle2);
                bundle.putBundle((String) entry.getKey(), bundle2);
            }
        }
    }

    public final void onStart() {
        super.onStart();
        this.zzlc = 2;
        for (LifecycleCallback onStart : this.zzlb.values()) {
            onStart.onStart();
        }
    }

    public final void onStop() {
        super.onStop();
        this.zzlc = 4;
        for (LifecycleCallback onStop : this.zzlb.values()) {
            onStop.onStop();
        }
    }
}
