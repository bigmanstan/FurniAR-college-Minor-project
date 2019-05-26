package com.google.android.gms.internal.firebase_database;

import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;

final class zzkr implements CompletionListener {
    private final /* synthetic */ TaskCompletionSource zzur;

    zzkr(TaskCompletionSource taskCompletionSource) {
        this.zzur = taskCompletionSource;
    }

    public final void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
        if (databaseError != null) {
            this.zzur.setException(databaseError.toException());
        } else {
            this.zzur.setResult(null);
        }
    }
}
