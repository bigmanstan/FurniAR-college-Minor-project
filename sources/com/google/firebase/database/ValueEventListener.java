package com.google.firebase.database;

import android.support.annotation.NonNull;

public interface ValueEventListener {
    void onCancelled(@NonNull DatabaseError databaseError);

    void onDataChange(@NonNull DataSnapshot dataSnapshot);
}
