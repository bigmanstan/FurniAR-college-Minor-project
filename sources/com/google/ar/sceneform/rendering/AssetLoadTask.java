package com.google.ar.sceneform.rendering;

import java.io.IOException;

public interface AssetLoadTask {
    void createAsset();

    boolean loadData() throws IOException;
}
