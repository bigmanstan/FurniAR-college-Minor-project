package com.google.ar.sceneform.rendering;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$AssetLoader$Jdb8M1vmtZpNIxZABL9oLMUs3g0 implements Runnable {
    private final /* synthetic */ AssetLoader f$0;
    private final /* synthetic */ AssetLoadTask f$1;
    private final /* synthetic */ AssetHolder f$2;
    private final /* synthetic */ LoadRequest f$3;

    public /* synthetic */ -$$Lambda$AssetLoader$Jdb8M1vmtZpNIxZABL9oLMUs3g0(AssetLoader assetLoader, AssetLoadTask assetLoadTask, AssetHolder assetHolder, LoadRequest loadRequest) {
        this.f$0 = assetLoader;
        this.f$1 = assetLoadTask;
        this.f$2 = assetHolder;
        this.f$3 = loadRequest;
    }

    public final void run() {
        AssetLoader.lambda$runLoadTask$0(this.f$0, this.f$1, this.f$2, this.f$3);
    }
}
