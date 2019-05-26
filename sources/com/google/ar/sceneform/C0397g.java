package com.google.ar.sceneform;

import java.util.function.Consumer;

/* renamed from: com.google.ar.sceneform.g */
final /* synthetic */ class C0397g implements Consumer {
    /* renamed from: a */
    private final FrameTime f113a;

    C0397g(FrameTime frameTime) {
        this.f113a = frameTime;
    }

    public final void accept(Object obj) {
        ((Node) obj).dispatchUpdate(this.f113a);
    }
}
