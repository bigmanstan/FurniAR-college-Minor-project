package com.google.android.gms.common.api;

import android.support.annotation.NonNull;

public class Response<T extends Result> {
    private T zzdm;

    protected Response(@NonNull T t) {
        this.zzdm = t;
    }

    @NonNull
    protected T getResult() {
        return this.zzdm;
    }

    public void setResult(@NonNull T t) {
        this.zzdm = t;
    }
}
