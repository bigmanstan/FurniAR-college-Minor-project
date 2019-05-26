package com.google.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.StringResourceValueReader;
import com.google.android.gms.common.util.Strings;

public final class FirebaseOptions {
    private final String zza;
    private final String zzb;
    private final String zzc;
    private final String zzd;
    private final String zze;
    private final String zzf;
    private final String zzg;

    public static final class Builder {
        private String zza;
        private String zzb;
        private String zzc;
        private String zzd;
        private String zze;
        private String zzf;
        private String zzg;

        public Builder(FirebaseOptions options) {
            this.zzb = options.zzb;
            this.zza = options.zza;
            this.zzc = options.zzc;
            this.zzd = options.zzd;
            this.zze = options.zze;
            this.zzf = options.zzf;
            this.zzg = options.zzg;
        }

        public final Builder setApiKey(@NonNull String apiKey) {
            this.zza = Preconditions.checkNotEmpty(apiKey, "ApiKey must be set.");
            return this;
        }

        public final Builder setApplicationId(@NonNull String applicationId) {
            this.zzb = Preconditions.checkNotEmpty(applicationId, "ApplicationId must be set.");
            return this;
        }

        public final Builder setDatabaseUrl(@Nullable String databaseUrl) {
            this.zzc = databaseUrl;
            return this;
        }

        public final Builder setGaTrackingId(@Nullable String gaTrackingId) {
            this.zzd = gaTrackingId;
            return this;
        }

        public final Builder setGcmSenderId(@Nullable String gcmSenderId) {
            this.zze = gcmSenderId;
            return this;
        }

        public final Builder setStorageBucket(@Nullable String storageBucket) {
            this.zzf = storageBucket;
            return this;
        }

        public final Builder setProjectId(@Nullable String projectId) {
            this.zzg = projectId;
            return this;
        }

        public final FirebaseOptions build() {
            return new FirebaseOptions(this.zzb, this.zza, this.zzc, this.zzd, this.zze, this.zzf, this.zzg);
        }
    }

    private FirebaseOptions(@NonNull String applicationId, @NonNull String apiKey, @Nullable String databaseUrl, @Nullable String gaTrackingId, @Nullable String gcmSenderId, @Nullable String storageBucket, @Nullable String projectId) {
        Preconditions.checkState(Strings.isEmptyOrWhitespace(applicationId) ^ 1, "ApplicationId must be set.");
        this.zzb = applicationId;
        this.zza = apiKey;
        this.zzc = databaseUrl;
        this.zzd = gaTrackingId;
        this.zze = gcmSenderId;
        this.zzf = storageBucket;
        this.zzg = projectId;
    }

    public static FirebaseOptions fromResource(Context context) {
        StringResourceValueReader stringResourceValueReader = new StringResourceValueReader(context);
        Object string = stringResourceValueReader.getString("google_app_id");
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return new FirebaseOptions(string, stringResourceValueReader.getString("google_api_key"), stringResourceValueReader.getString("firebase_database_url"), stringResourceValueReader.getString("ga_trackingId"), stringResourceValueReader.getString("gcm_defaultSenderId"), stringResourceValueReader.getString("google_storage_bucket"), stringResourceValueReader.getString("project_id"));
    }

    public final String getApiKey() {
        return this.zza;
    }

    public final String getApplicationId() {
        return this.zzb;
    }

    public final String getDatabaseUrl() {
        return this.zzc;
    }

    public final String getGaTrackingId() {
        return this.zzd;
    }

    public final String getGcmSenderId() {
        return this.zze;
    }

    public final String getStorageBucket() {
        return this.zzf;
    }

    public final String getProjectId() {
        return this.zzg;
    }

    public final boolean equals(Object o) {
        if (!(o instanceof FirebaseOptions)) {
            return false;
        }
        FirebaseOptions firebaseOptions = (FirebaseOptions) o;
        if (Objects.equal(this.zzb, firebaseOptions.zzb) && Objects.equal(this.zza, firebaseOptions.zza) && Objects.equal(this.zzc, firebaseOptions.zzc) && Objects.equal(this.zzd, firebaseOptions.zzd) && Objects.equal(this.zze, firebaseOptions.zze) && Objects.equal(this.zzf, firebaseOptions.zzf) && Objects.equal(this.zzg, firebaseOptions.zzg)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzb, this.zza, this.zzc, this.zzd, this.zze, this.zzf, this.zzg);
    }

    public final String toString() {
        return Objects.toStringHelper(this).add("applicationId", this.zzb).add("apiKey", this.zza).add("databaseUrl", this.zzc).add("gcmSenderId", this.zze).add("storageBucket", this.zzf).add("projectId", this.zzg).toString();
    }
}
