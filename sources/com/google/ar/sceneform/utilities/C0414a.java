package com.google.ar.sceneform.utilities;

import android.content.res.AssetManager;
import java.util.concurrent.Callable;

/* renamed from: com.google.ar.sceneform.utilities.a */
final /* synthetic */ class C0414a implements Callable {
    /* renamed from: a */
    private final AssetManager f137a;
    /* renamed from: b */
    private final String f138b;

    C0414a(AssetManager assetManager, String str) {
        this.f137a = assetManager;
        this.f138b = str;
    }

    public final Object call() {
        return LoadHelper.lambda$fileUriToInputStreamCreator$0$LoadHelper(this.f137a, this.f138b);
    }
}
