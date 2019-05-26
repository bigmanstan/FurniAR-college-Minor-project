package com.google.firebase.database;

final class zzp implements ValueEventListener {
    private final /* synthetic */ ValueEventListener zzav;
    private final /* synthetic */ Query zzaw;

    zzp(Query query, ValueEventListener valueEventListener) {
        this.zzaw = query;
        this.zzav = valueEventListener;
    }

    public final void onCancelled(DatabaseError databaseError) {
        this.zzav.onCancelled(databaseError);
    }

    public final void onDataChange(DataSnapshot dataSnapshot) {
        this.zzaw.removeEventListener((ValueEventListener) this);
        this.zzav.onDataChange(dataSnapshot);
    }
}
