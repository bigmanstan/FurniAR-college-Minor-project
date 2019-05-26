package edu.wpi.jlyu.sceneformfurniture;

import android.content.Context;
import android.util.Log;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.Transaction.Handler;
import com.google.firebase.database.Transaction.Result;
import com.google.firebase.database.ValueEventListener;

/* compiled from: StoreManager */
class StorageManager {
    private static final int INITIAL_SHORT_CODE = 142;
    private static final String KEY_NEXT_SHORT_CODE = "next_short_code";
    private static final String KEY_PREFIX = "anchor;";
    private static final String KEY_ROOT_DIR = "shared_anchor_codelab_root";
    private static final String TAG = StorageManager.class.getName();
    private final DatabaseReference rootRef;

    /* compiled from: StoreManager */
    interface CloudAnchorIdListener {
        void onCloudAnchorIdAvailable(String str);
    }

    /* compiled from: StoreManager */
    interface ShortCodeListener {
        void onShortCodeAvailable(Integer num);
    }

    StorageManager(Context context) {
        this.rootRef = FirebaseDatabase.getInstance(FirebaseApp.initializeApp(context)).getReference().child(KEY_ROOT_DIR);
        DatabaseReference.goOnline();
    }

    void nextShortCode(final ShortCodeListener listener) {
        this.rootRef.child(KEY_NEXT_SHORT_CODE).runTransaction(new Handler() {
            public Result doTransaction(MutableData currentData) {
                Integer shortCode = (Integer) currentData.getValue(Integer.class);
                if (shortCode == null) {
                    shortCode = Integer.valueOf(141);
                }
                currentData.setValue(Integer.valueOf(shortCode.intValue() + 1));
                return Transaction.success(currentData);
            }

            public void onComplete(DatabaseError error, boolean committed, DataSnapshot currentData) {
                if (committed) {
                    listener.onShortCodeAvailable((Integer) currentData.getValue(Integer.class));
                    return;
                }
                Log.e(StorageManager.TAG, "Firebase Error", error.toException());
                listener.onShortCodeAvailable(null);
            }
        });
    }

    void storeUsingShortCode(int shortCode, String cloudAnchorId) {
        DatabaseReference databaseReference = this.rootRef;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(KEY_PREFIX);
        stringBuilder.append(shortCode);
        databaseReference.child(stringBuilder.toString()).setValue(cloudAnchorId);
    }

    void getCloudAnchorID(int shortCode, final CloudAnchorIdListener listener) {
        DatabaseReference databaseReference = this.rootRef;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(KEY_PREFIX);
        stringBuilder.append(shortCode);
        databaseReference.child(stringBuilder.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onCloudAnchorIdAvailable(String.valueOf(dataSnapshot.getValue()));
            }

            public void onCancelled(DatabaseError error) {
                Log.e(StorageManager.TAG, "The database operation for getCloudAnchorID was cancelled.", error.toException());
                listener.onCloudAnchorIdAvailable(null);
            }
        });
    }
}
