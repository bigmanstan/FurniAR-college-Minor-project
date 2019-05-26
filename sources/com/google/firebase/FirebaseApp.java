package com.google.firebase;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.BackgroundDetector;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.ProcessUtils;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.components.Component;
import com.google.firebase.components.zzc;
import com.google.firebase.components.zzd;
import com.google.firebase.events.Event;
import com.google.firebase.events.Publisher;
import com.google.firebase.internal.InternalTokenProvider;
import com.google.firebase.internal.InternalTokenResult;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.concurrent.GuardedBy;

public class FirebaseApp {
    public static final String DEFAULT_APP_NAME = "[DEFAULT]";
    @GuardedBy("LOCK")
    static final Map<String, FirebaseApp> zza = new ArrayMap();
    private static final List<String> zzb = Arrays.asList(new String[]{"com.google.firebase.auth.FirebaseAuth", "com.google.firebase.iid.FirebaseInstanceId"});
    private static final List<String> zzc = Collections.singletonList("com.google.firebase.crash.FirebaseCrash");
    private static final List<String> zzd = Arrays.asList(new String[]{"com.google.android.gms.measurement.AppMeasurement"});
    private static final List<String> zze = Arrays.asList(new String[0]);
    private static final Set<String> zzf = Collections.emptySet();
    private static final Object zzg = new Object();
    private static final Executor zzh = new zza();
    private final Context zzi;
    private final String zzj;
    private final FirebaseOptions zzk;
    private final zzd zzl;
    private final SharedPreferences zzm;
    private final Publisher zzn;
    private final AtomicBoolean zzo = new AtomicBoolean(false);
    private final AtomicBoolean zzp = new AtomicBoolean();
    private final AtomicBoolean zzq;
    private final List<IdTokenListener> zzr = new CopyOnWriteArrayList();
    private final List<BackgroundStateChangeListener> zzs = new CopyOnWriteArrayList();
    private final List<zza> zzt = new CopyOnWriteArrayList();
    private InternalTokenProvider zzu;
    private IdTokenListenersCountChangedListener zzv;

    @KeepForSdk
    public interface BackgroundStateChangeListener {
        void onBackgroundStateChanged(boolean z);
    }

    @KeepForSdk
    public interface IdTokenListener {
        void onIdTokenChanged(@NonNull InternalTokenResult internalTokenResult);
    }

    @KeepForSdk
    public interface IdTokenListenersCountChangedListener {
        void onListenerCountChanged(int i);
    }

    static class zza implements Executor {
        private static final Handler zza = new Handler(Looper.getMainLooper());

        private zza() {
        }

        public final void execute(@NonNull Runnable command) {
            zza.post(command);
        }
    }

    @TargetApi(24)
    static class zzb extends BroadcastReceiver {
        private static AtomicReference<zzb> zza = new AtomicReference();
        private final Context zzb;

        static /* synthetic */ void zza(Context context) {
            if (zza.get() == null) {
                BroadcastReceiver zzb = new zzb(context);
                if (zza.compareAndSet(null, zzb)) {
                    context.registerReceiver(zzb, new IntentFilter("android.intent.action.USER_UNLOCKED"));
                }
            }
        }

        private zzb(Context context) {
            this.zzb = context;
        }

        public final void onReceive(Context context, Intent intent) {
            synchronized (FirebaseApp.zzg) {
                for (FirebaseApp zza : FirebaseApp.zza.values()) {
                    zza.zze();
                }
            }
            this.zzb.unregisterReceiver(this);
        }
    }

    /* renamed from: com.google.firebase.FirebaseApp$1 */
    class C05581 implements com.google.android.gms.common.api.internal.BackgroundDetector.BackgroundStateChangeListener {
        C05581() {
        }

        public final void onBackgroundStateChanged(boolean background) {
            FirebaseApp.onBackgroundStateChanged(background);
        }
    }

    @NonNull
    public Context getApplicationContext() {
        zzc();
        return this.zzi;
    }

    @NonNull
    public String getName() {
        zzc();
        return this.zzj;
    }

    @NonNull
    public FirebaseOptions getOptions() {
        zzc();
        return this.zzk;
    }

    public boolean equals(Object o) {
        if (o instanceof FirebaseApp) {
            return this.zzj.equals(((FirebaseApp) o).getName());
        }
        return false;
    }

    public int hashCode() {
        return this.zzj.hashCode();
    }

    public String toString() {
        return Objects.toStringHelper(this).add("name", this.zzj).add("options", this.zzk).toString();
    }

    public static List<FirebaseApp> getApps(Context context) {
        List arrayList;
        synchronized (zzg) {
            arrayList = new ArrayList(zza.values());
        }
        return arrayList;
    }

    @Nullable
    public static FirebaseApp getInstance() {
        FirebaseApp firebaseApp;
        synchronized (zzg) {
            firebaseApp = (FirebaseApp) zza.get(DEFAULT_APP_NAME);
            if (firebaseApp != null) {
            } else {
                StringBuilder stringBuilder = new StringBuilder("Default FirebaseApp is not initialized in this process ");
                stringBuilder.append(ProcessUtils.getMyProcessName());
                stringBuilder.append(". Make sure to call FirebaseApp.initializeApp(Context) first.");
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
        return firebaseApp;
    }

    public static FirebaseApp getInstance(@NonNull String name) {
        FirebaseApp firebaseApp;
        synchronized (zzg) {
            firebaseApp = (FirebaseApp) zza.get(name.trim());
            if (firebaseApp != null) {
            } else {
                String str;
                Iterable zzd = zzd();
                if (zzd.isEmpty()) {
                    str = "";
                } else {
                    StringBuilder stringBuilder = new StringBuilder("Available app names: ");
                    stringBuilder.append(TextUtils.join(", ", zzd));
                    str = stringBuilder.toString();
                }
                throw new IllegalStateException(String.format("FirebaseApp with name %s doesn't exist. %s", new Object[]{name, str}));
            }
        }
        return firebaseApp;
    }

    @Nullable
    public static FirebaseApp initializeApp(Context context) {
        synchronized (zzg) {
            if (zza.containsKey(DEFAULT_APP_NAME)) {
                FirebaseApp instance = getInstance();
                return instance;
            }
            FirebaseOptions fromResource = FirebaseOptions.fromResource(context);
            if (fromResource == null) {
                return null;
            }
            FirebaseApp initializeApp = initializeApp(context, fromResource);
            return initializeApp;
        }
    }

    public static FirebaseApp initializeApp(Context context, FirebaseOptions options) {
        return initializeApp(context, options, DEFAULT_APP_NAME);
    }

    public static FirebaseApp initializeApp(Context context, FirebaseOptions options, String name) {
        FirebaseApp firebaseApp;
        if (PlatformVersion.isAtLeastIceCreamSandwich() && (context.getApplicationContext() instanceof Application)) {
            BackgroundDetector.initialize((Application) context.getApplicationContext());
            BackgroundDetector.getInstance().addListener(new C05581());
        }
        name = name.trim();
        if (context.getApplicationContext() != null) {
            context = context.getApplicationContext();
        }
        synchronized (zzg) {
            boolean containsKey = zza.containsKey(name) ^ 1;
            StringBuilder stringBuilder = new StringBuilder("FirebaseApp name ");
            stringBuilder.append(name);
            stringBuilder.append(" already exists!");
            Preconditions.checkState(containsKey, stringBuilder.toString());
            Preconditions.checkNotNull(context, "Application context cannot be null.");
            firebaseApp = new FirebaseApp(context, name, options);
            zza.put(name, firebaseApp);
        }
        firebaseApp.zze();
        return firebaseApp;
    }

    public static void onBackgroundStateChanged(boolean background) {
        synchronized (zzg) {
            Iterator it = new ArrayList(zza.values()).iterator();
            while (it.hasNext()) {
                FirebaseApp firebaseApp = (FirebaseApp) it.next();
                if (firebaseApp.zzo.get()) {
                    firebaseApp.zza(background);
                }
            }
        }
    }

    public void setTokenProvider(@NonNull InternalTokenProvider tokenProvider) {
        this.zzu = (InternalTokenProvider) Preconditions.checkNotNull(tokenProvider);
    }

    public void setIdTokenListenersCountChangedListener(@NonNull IdTokenListenersCountChangedListener listener) {
        this.zzv = (IdTokenListenersCountChangedListener) Preconditions.checkNotNull(listener);
        this.zzv.onListenerCountChanged(this.zzr.size());
    }

    public Task<GetTokenResult> getToken(boolean forceRefresh) {
        zzc();
        if (this.zzu == null) {
            return Tasks.forException(new FirebaseApiNotAvailableException("firebase-auth is not linked, please fall back to unauthenticated mode."));
        }
        return this.zzu.getAccessToken(forceRefresh);
    }

    @Nullable
    public String getUid() throws FirebaseApiNotAvailableException {
        zzc();
        if (this.zzu != null) {
            return this.zzu.getUid();
        }
        throw new FirebaseApiNotAvailableException("firebase-auth is not linked, please fall back to unauthenticated mode.");
    }

    public void delete() {
        if (this.zzp.compareAndSet(false, true)) {
            synchronized (zzg) {
                zza.remove(this.zzj);
            }
            Iterator it = this.zzt.iterator();
            while (it.hasNext()) {
                it.next();
            }
        }
    }

    public <T> T get(Class<T> anInterface) {
        zzc();
        return this.zzl.get(anInterface);
    }

    public void setAutomaticResourceManagementEnabled(boolean enabled) {
        zzc();
        if (this.zzo.compareAndSet(enabled ^ 1, enabled)) {
            boolean isInBackground = BackgroundDetector.getInstance().isInBackground();
            if (enabled && isInBackground) {
                zza(true);
            } else if (!enabled && isInBackground) {
                zza(false);
            }
        }
    }

    public boolean isAutomaticDataCollectionEnabled() {
        zzc();
        return this.zzq.get();
    }

    public void setAutomaticDataCollectionEnabled(boolean enabled) {
        zzc();
        if (this.zzq.compareAndSet(enabled ^ 1, enabled)) {
            this.zzm.edit().putBoolean("firebase_automatic_data_collection_enabled", enabled).commit();
            this.zzn.publish(new Event(AutomaticDataCollectionChange.class, new AutomaticDataCollectionChange(enabled)));
        }
    }

    protected FirebaseApp(Context applicationContext, String name, FirebaseOptions options) {
        this.zzi = (Context) Preconditions.checkNotNull(applicationContext);
        this.zzj = Preconditions.checkNotEmpty(name);
        this.zzk = (FirebaseOptions) Preconditions.checkNotNull(options);
        this.zzv = new com.google.firebase.internal.zza();
        this.zzm = applicationContext.getSharedPreferences("com.google.firebase.common.prefs", 0);
        this.zzq = new AtomicBoolean(zzb());
        Iterable zza = new zzc(applicationContext).zza();
        this.zzl = new zzd(zzh, zza, Component.of(applicationContext, Context.class, new Class[0]), Component.of(this, FirebaseApp.class, new Class[0]), Component.of(options, FirebaseOptions.class, new Class[0]));
        this.zzn = (Publisher) this.zzl.get(Publisher.class);
    }

    private boolean zzb() {
        if (this.zzm.contains("firebase_automatic_data_collection_enabled")) {
            return this.zzm.getBoolean("firebase_automatic_data_collection_enabled", true);
        }
        try {
            PackageManager packageManager = this.zzi.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(this.zzi.getPackageName(), 128);
                if (!(applicationInfo == null || applicationInfo.metaData == null || !applicationInfo.metaData.containsKey("firebase_automatic_data_collection_enabled"))) {
                    return applicationInfo.metaData.getBoolean("firebase_automatic_data_collection_enabled");
                }
            }
        } catch (NameNotFoundException e) {
        }
        return true;
    }

    private void zzc() {
        Preconditions.checkState(this.zzp.get() ^ 1, "FirebaseApp was deleted");
    }

    public List<IdTokenListener> getListeners() {
        zzc();
        return this.zzr;
    }

    @VisibleForTesting
    public boolean isDefaultApp() {
        return DEFAULT_APP_NAME.equals(getName());
    }

    @UiThread
    public void notifyIdTokenListeners(@NonNull InternalTokenResult tokenResult) {
        Log.d("FirebaseApp", "Notifying auth state listeners.");
        int i = 0;
        for (IdTokenListener onIdTokenChanged : this.zzr) {
            onIdTokenChanged.onIdTokenChanged(tokenResult);
            i++;
        }
        Log.d("FirebaseApp", String.format("Notified %d auth state listeners.", new Object[]{Integer.valueOf(i)}));
    }

    private void zza(boolean z) {
        Log.d("FirebaseApp", "Notifying background state change listeners.");
        for (BackgroundStateChangeListener onBackgroundStateChanged : this.zzs) {
            onBackgroundStateChanged.onBackgroundStateChanged(z);
        }
    }

    public void addIdTokenListener(@NonNull IdTokenListener listener) {
        zzc();
        Preconditions.checkNotNull(listener);
        this.zzr.add(listener);
        this.zzv.onListenerCountChanged(this.zzr.size());
    }

    public void removeIdTokenListener(@NonNull IdTokenListener listenerToRemove) {
        zzc();
        Preconditions.checkNotNull(listenerToRemove);
        this.zzr.remove(listenerToRemove);
        this.zzv.onListenerCountChanged(this.zzr.size());
    }

    public void addBackgroundStateChangeListener(BackgroundStateChangeListener listener) {
        zzc();
        if (this.zzo.get() && BackgroundDetector.getInstance().isInBackground()) {
            listener.onBackgroundStateChanged(true);
        }
        this.zzs.add(listener);
    }

    public void removeBackgroundStateChangeListener(BackgroundStateChangeListener listener) {
        zzc();
        this.zzs.remove(listener);
    }

    public String getPersistenceKey() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Base64Utils.encodeUrlSafeNoPadding(getName().getBytes()));
        stringBuilder.append("+");
        stringBuilder.append(Base64Utils.encodeUrlSafeNoPadding(getOptions().getApplicationId().getBytes()));
        return stringBuilder.toString();
    }

    public void addLifecycleEventListener(@NonNull zza listener) {
        zzc();
        Preconditions.checkNotNull(listener);
        this.zzt.add(listener);
    }

    public void removeLifecycleEventListener(@NonNull zza listener) {
        zzc();
        Preconditions.checkNotNull(listener);
        this.zzt.remove(listener);
    }

    @VisibleForTesting
    public static void clearInstancesForTest() {
        synchronized (zzg) {
            zza.clear();
        }
    }

    public static String getPersistenceKey(String name, FirebaseOptions options) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Base64Utils.encodeUrlSafeNoPadding(name.getBytes()));
        stringBuilder.append("+");
        stringBuilder.append(Base64Utils.encodeUrlSafeNoPadding(options.getApplicationId().getBytes()));
        return stringBuilder.toString();
    }

    private static List<String> zzd() {
        List<String> arrayList = new ArrayList();
        synchronized (zzg) {
            for (FirebaseApp name : zza.values()) {
                arrayList.add(name.getName());
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private void zze() {
        boolean isDeviceProtectedStorage = ContextCompat.isDeviceProtectedStorage(this.zzi);
        if (isDeviceProtectedStorage) {
            zzb.zza(this.zzi);
        } else {
            this.zzl.zza(isDefaultApp());
        }
        zza(FirebaseApp.class, this, zzb, isDeviceProtectedStorage);
        if (isDefaultApp()) {
            zza(FirebaseApp.class, this, zzc, isDeviceProtectedStorage);
            zza(Context.class, this.zzi, zzd, isDeviceProtectedStorage);
        }
    }

    private static <T> void zza(Class<T> cls, T t, Iterable<String> iterable, boolean z) {
        for (String str : iterable) {
            if (z) {
                try {
                    if (zze.contains(str)) {
                    }
                } catch (ClassNotFoundException e) {
                    if (zzf.contains(str)) {
                        t = new StringBuilder();
                        t.append(str);
                        t.append(" is missing, but is required. Check if it has been removed by Proguard.");
                        throw new IllegalStateException(t.toString());
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(str);
                    stringBuilder.append(" is not linked. Skipping initialization.");
                    Log.d("FirebaseApp", stringBuilder.toString());
                } catch (NoSuchMethodException e2) {
                    t = new StringBuilder();
                    t.append(str);
                    t.append("#getInstance has been removed by Proguard. Add keep rule to prevent it.");
                    throw new IllegalStateException(t.toString());
                } catch (Throwable e3) {
                    Log.wtf("FirebaseApp", "Firebase API initialization failure.", e3);
                } catch (Throwable e4) {
                    StringBuilder stringBuilder2 = new StringBuilder("Failed to initialize ");
                    stringBuilder2.append(str);
                    Log.wtf("FirebaseApp", stringBuilder2.toString(), e4);
                }
            }
            Method method = Class.forName(str).getMethod("getInstance", new Class[]{cls});
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {
                method.invoke(null, new Object[]{t});
            }
        }
    }
}
