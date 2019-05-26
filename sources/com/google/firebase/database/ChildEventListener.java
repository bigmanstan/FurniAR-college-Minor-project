package com.google.firebase.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface ChildEventListener {
    void onCancelled(@NonNull DatabaseError databaseError);

    void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String str);

    void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String str);

    void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String str);

    void onChildRemoved(@NonNull DataSnapshot dataSnapshot);
}
