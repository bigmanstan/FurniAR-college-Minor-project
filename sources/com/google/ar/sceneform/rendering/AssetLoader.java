package com.google.ar.sceneform.rendering;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

public class AssetLoader {
    private static final String TAG = AssetLoader.class.getSimpleName();
    private final AssetLoaderLock assetLoaderLock = new AssetLoaderLock();
    private final ExecutorService loadExecutor;
    private final HashMap<AssetHolder, LoadRequest> loadingRequests = new HashMap();
    private final HashMap<AssetHolder, LoadRequest> readyRequests = new HashMap();

    private static class AssetLoaderLock {
        private AssetLoaderLock() {
        }
    }

    private static class LoadRequest {
        private boolean cancelled;
        private final AssetHolder holder;
        private final AssetLoadTask task;

        LoadRequest(AssetHolder holder, AssetLoadTask task) {
            this.holder = holder;
            this.task = task;
        }

        AssetHolder getHolder() {
            return this.holder;
        }

        void cancel() {
            if (!this.cancelled) {
                this.holder.onLoadCancelled(this.task);
                this.cancelled = true;
            }
        }

        boolean isCancelled() {
            return this.cancelled;
        }
    }

    AssetLoader(ExecutorService loadExecutor) {
        this.loadExecutor = loadExecutor;
    }

    public void createAssets() {
        ArrayList<LoadRequest> createRequests = null;
        synchronized (this.assetLoaderLock) {
            if (!this.readyRequests.isEmpty()) {
                createRequests = new ArrayList(this.readyRequests.values());
                this.readyRequests.clear();
            }
        }
        if (createRequests != null) {
            Iterator it = createRequests.iterator();
            while (it.hasNext()) {
                LoadRequest request = (LoadRequest) it.next();
                if (!(request == null || request.cancelled)) {
                    request.task.createAsset();
                    request.holder.onLoadFinished(request.task);
                }
            }
        }
    }

    public void runLoadTask(AssetHolder holder, AssetLoadTask task) {
        synchronized (this.assetLoaderLock) {
            LoadRequest request = (LoadRequest) this.loadingRequests.get(holder);
            if (request != null) {
                request.cancel();
            }
            request = (LoadRequest) this.readyRequests.get(holder);
            if (request != null) {
                request.cancel();
            }
            LoadRequest loadRequest = new LoadRequest(holder, task);
            this.loadingRequests.put(holder, loadRequest);
            this.loadExecutor.execute(new -$$Lambda$AssetLoader$Jdb8M1vmtZpNIxZABL9oLMUs3g0(this, task, holder, loadRequest));
        }
    }

    public static /* synthetic */ void lambda$runLoadTask$0(AssetLoader assetLoader, AssetLoadTask task, AssetHolder holder, LoadRequest loadRequest) {
        boolean loadSuccessful = false;
        try {
            loadSuccessful = task.loadData();
        } catch (IOException e) {
            IOException problem = e;
        }
        synchronized (assetLoader.assetLoaderLock) {
            if (assetLoader.loadingRequests.get(holder) == loadRequest) {
                assetLoader.loadingRequests.remove(holder);
            }
            if (!loadRequest.isCancelled() && loadSuccessful) {
                assetLoader.readyRequests.put(holder, loadRequest);
            }
        }
    }
}
