package com.google.ar.sceneform.utilities;

import android.content.Context;
import android.net.Uri;
import java.util.concurrent.Callable;

/* renamed from: com.google.ar.sceneform.utilities.b */
final /* synthetic */ class C0415b implements Callable {
    /* renamed from: a */
    private final Context f139a;
    /* renamed from: b */
    private final Uri f140b;

    C0415b(Context context, Uri uri) {
        this.f139a = context;
        this.f140b = uri;
    }

    public final Object call() {
        return this.f139a.getContentResolver().openInputStream(this.f140b);
    }
}
