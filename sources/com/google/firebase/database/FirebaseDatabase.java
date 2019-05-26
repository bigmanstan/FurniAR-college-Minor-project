package com.google.firebase.database;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.google.android.gms.internal.firebase_database.zzcc;
import com.google.android.gms.internal.firebase_database.zzch;
import com.google.android.gms.internal.firebase_database.zzck;
import com.google.android.gms.internal.firebase_database.zzdn;
import com.google.android.gms.internal.firebase_database.zzdo;
import com.google.android.gms.internal.firebase_database.zzko;
import com.google.android.gms.internal.firebase_database.zzkq;
import com.google.android.gms.internal.firebase_database.zzks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.Logger.Level;
import java.util.HashMap;
import java.util.Map;

public class FirebaseDatabase {
    private static final Map<String, Map<zzdn, FirebaseDatabase>> zzae = new HashMap();
    private final FirebaseApp zzaf;
    private final zzdn zzag;
    private final zzcc zzah;
    private zzck zzai;

    private FirebaseDatabase(FirebaseApp firebaseApp, zzdn zzdn, zzcc zzcc) {
        this.zzaf = firebaseApp;
        this.zzag = zzdn;
        this.zzah = zzcc;
    }

    @NonNull
    public static FirebaseDatabase getInstance() {
        FirebaseApp instance = FirebaseApp.getInstance();
        if (instance != null) {
            return getInstance(instance, instance.getOptions().getDatabaseUrl());
        }
        throw new DatabaseException("You must call FirebaseApp.initialize() first.");
    }

    @NonNull
    public static FirebaseDatabase getInstance(@NonNull FirebaseApp firebaseApp) {
        return getInstance(firebaseApp, firebaseApp.getOptions().getDatabaseUrl());
    }

    @NonNull
    public static synchronized FirebaseDatabase getInstance(@NonNull FirebaseApp firebaseApp, @NonNull String str) {
        FirebaseDatabase firebaseDatabase;
        synchronized (FirebaseDatabase.class) {
            if (TextUtils.isEmpty(str)) {
                throw new DatabaseException("Failed to get FirebaseDatabase instance: Specify DatabaseURL within FirebaseApp or from your getInstance() call.");
            }
            Map map = (Map) zzae.get(firebaseApp.getName());
            if (map == null) {
                map = new HashMap();
                zzae.put(firebaseApp.getName(), map);
            }
            zzko zzx = zzkq.zzx(str);
            if (zzx.zzap.isEmpty()) {
                firebaseDatabase = (FirebaseDatabase) map.get(zzx.zzag);
                if (firebaseDatabase == null) {
                    zzcc zzcc = new zzcc();
                    if (!firebaseApp.isDefaultApp()) {
                        zzcc.zzr(firebaseApp.getName());
                    }
                    zzcc.zza(firebaseApp);
                    FirebaseDatabase firebaseDatabase2 = new FirebaseDatabase(firebaseApp, zzx.zzag, zzcc);
                    map.put(zzx.zzag, firebaseDatabase2);
                    firebaseDatabase = firebaseDatabase2;
                }
            } else {
                String zzch = zzx.zzap.toString();
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 113) + String.valueOf(zzch).length());
                stringBuilder.append("Specified Database URL '");
                stringBuilder.append(str);
                stringBuilder.append("' is invalid. It should point to the root of a Firebase Database but it includes a path: ");
                stringBuilder.append(zzch);
                throw new DatabaseException(stringBuilder.toString());
            }
        }
        return firebaseDatabase;
    }

    @NonNull
    public static FirebaseDatabase getInstance(@NonNull String str) {
        FirebaseApp instance = FirebaseApp.getInstance();
        if (instance != null) {
            return getInstance(instance, str);
        }
        throw new DatabaseException("You must call FirebaseApp.initialize() first.");
    }

    @NonNull
    public static String getSdkVersion() {
        return "3.0.0";
    }

    private final void zzb(String str) {
        if (this.zzai != null) {
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 77);
            stringBuilder.append("Calls to ");
            stringBuilder.append(str);
            stringBuilder.append("() must be made before any other usage of FirebaseDatabase instance.");
            throw new DatabaseException(stringBuilder.toString());
        }
    }

    private final synchronized void zzc() {
        if (this.zzai == null) {
            this.zzai = zzdo.zza(this.zzah, this.zzag, this);
        }
    }

    @NonNull
    public FirebaseApp getApp() {
        return this.zzaf;
    }

    @NonNull
    public DatabaseReference getReference() {
        zzc();
        return new DatabaseReference(this.zzai, zzch.zzbt());
    }

    @NonNull
    public DatabaseReference getReference(@NonNull String str) {
        zzc();
        if (str != null) {
            zzks.zzac(str);
            return new DatabaseReference(this.zzai, new zzch(str));
        }
        throw new NullPointerException("Can't pass null for argument 'pathString' in FirebaseDatabase.getReference()");
    }

    @NonNull
    public DatabaseReference getReferenceFromUrl(@NonNull String str) {
        zzc();
        if (str != null) {
            zzko zzx = zzkq.zzx(str);
            if (zzx.zzag.zzct.equals(this.zzai.zzcb().zzct)) {
                return new DatabaseReference(this.zzai, zzx.zzap);
            }
            String databaseReference = getReference().toString();
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 93) + String.valueOf(databaseReference).length());
            stringBuilder.append("Invalid URL (");
            stringBuilder.append(str);
            stringBuilder.append(") passed to getReference().  URL was expected to match configured Database URL: ");
            stringBuilder.append(databaseReference);
            throw new DatabaseException(stringBuilder.toString());
        }
        throw new NullPointerException("Can't pass null for argument 'url' in FirebaseDatabase.getReferenceFromUrl()");
    }

    public void goOffline() {
        zzc();
        zzdo.zzk(this.zzai);
    }

    public void goOnline() {
        zzc();
        zzdo.zzl(this.zzai);
    }

    public void purgeOutstandingWrites() {
        zzc();
        this.zzai.zzc(new zzg(this));
    }

    public synchronized void setLogLevel(@NonNull Level level) {
        zzb("setLogLevel");
        this.zzah.setLogLevel(level);
    }

    public synchronized void setPersistenceCacheSizeBytes(long j) {
        zzb("setPersistenceCacheSizeBytes");
        this.zzah.setPersistenceCacheSizeBytes(j);
    }

    public synchronized void setPersistenceEnabled(boolean z) {
        zzb("setPersistenceEnabled");
        this.zzah.setPersistenceEnabled(z);
    }
}
