package com.google.ar.sceneform.rendering;

public interface AssetHolder {
    void onLoadCancelled(AssetLoadTask assetLoadTask);

    void onLoadFinished(AssetLoadTask assetLoadTask);
}
