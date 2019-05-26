package com.google.ar.sceneform.rendering;

import com.google.ar.sceneform.utilities.Preconditions;
import java.io.ByteArrayInputStream;
import java.util.concurrent.Callable;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$LoadRenderableFromRcbTask$7_jtDu88FMNNSTruOc9Clhv8WBs implements Callable {
    private final /* synthetic */ ByteArrayInputStream f$0;

    public /* synthetic */ -$$Lambda$LoadRenderableFromRcbTask$7_jtDu88FMNNSTruOc9Clhv8WBs(ByteArrayInputStream byteArrayInputStream) {
        this.f$0 = byteArrayInputStream;
    }

    public final Object call() {
        return Preconditions.checkNotNull(this.f$0);
    }
}
