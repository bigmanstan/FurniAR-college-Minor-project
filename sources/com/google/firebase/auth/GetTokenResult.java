package com.google.firebase.auth;

import android.support.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.Map;

public class GetTokenResult {
    private String zza;
    private Map<String, Object> zzb;

    @KeepForSdk
    public GetTokenResult(String token, Map<String, Object> claims) {
        this.zza = token;
        this.zzb = claims;
    }

    @Nullable
    public String getToken() {
        return this.zza;
    }

    public long getExpirationTimestamp() {
        return zza("exp");
    }

    public long getAuthTimestamp() {
        return zza("auth_time");
    }

    public long getIssuedAtTimestamp() {
        return zza("iat");
    }

    @Nullable
    public String getSignInProvider() {
        Map map = (Map) this.zzb.get("firebase");
        if (map != null) {
            return (String) map.get("sign_in_provider");
        }
        return null;
    }

    public Map<String, Object> getClaims() {
        return this.zzb;
    }

    private long zza(String str) {
        Integer num = (Integer) this.zzb.get(str);
        return num == null ? 0 : num.longValue();
    }
}
