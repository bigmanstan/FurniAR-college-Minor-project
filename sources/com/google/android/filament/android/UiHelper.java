package com.google.android.filament.android;

import android.graphics.SurfaceTexture;
import android.os.Build.VERSION;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;

public class UiHelper {
    private static final boolean LOGGING = false;
    private static final String LOG_TAG = "UiHelper";
    private int mDesiredHeight;
    private int mDesiredWidth;
    private boolean mHasSwapChain;
    private Object mNativeWindow;
    private RendererCallback mRenderCallback;
    private RenderSurface mRenderSurface;

    /* renamed from: com.google.android.filament.android.UiHelper$1 */
    class C03681 implements Callback {
        C03681() {
        }

        public void surfaceCreated(SurfaceHolder holder) {
            UiHelper.this.createSwapChain(holder.getSurface());
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            UiHelper.this.mRenderCallback.onResized(width, height);
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            UiHelper.this.destroySwapChain();
        }
    }

    /* renamed from: com.google.android.filament.android.UiHelper$2 */
    class C03692 implements SurfaceTextureListener {
        C03692() {
        }

        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
            if (VERSION.SDK_INT >= 15) {
                surfaceTexture.setDefaultBufferSize(UiHelper.this.mDesiredWidth, UiHelper.this.mDesiredHeight);
            }
            Surface surface = new Surface(surfaceTexture);
            ((TextureViewHandler) UiHelper.this.mRenderSurface).setSurface(surface);
            UiHelper.this.createSwapChain(surface);
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
            UiHelper.this.mRenderCallback.onResized(width, height);
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            UiHelper.this.destroySwapChain();
            return true;
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    }

    public enum ContextErrorPolicy {
        CHECK,
        DONT_CHECK
    }

    private interface RenderSurface {
        void detach();

        void resize(int i, int i2);
    }

    public interface RendererCallback {
        void onDetachedFromSurface();

        void onNativeWindowChanged(Surface surface);

        void onResized(int i, int i2);
    }

    private class SurfaceViewHandler implements RenderSurface {
        private SurfaceView mSurfaceView;

        SurfaceViewHandler(SurfaceView surface) {
            this.mSurfaceView = surface;
        }

        public void resize(int width, int height) {
            this.mSurfaceView.getHolder().setFixedSize(width, height);
        }

        public void detach() {
        }
    }

    private class TextureViewHandler implements RenderSurface {
        private Surface mSurface;
        private TextureView mTextureView;

        TextureViewHandler(TextureView surface) {
            this.mTextureView = surface;
        }

        public void resize(int width, int height) {
            if (VERSION.SDK_INT >= 15) {
                this.mTextureView.getSurfaceTexture().setDefaultBufferSize(width, height);
            }
            UiHelper.this.mRenderCallback.onResized(width, height);
        }

        public void detach() {
            setSurface(null);
        }

        void setSurface(Surface surface) {
            if (surface == null && this.mSurface != null) {
                this.mSurface.release();
            }
            this.mSurface = surface;
        }
    }

    public UiHelper() {
        this(ContextErrorPolicy.CHECK);
    }

    public UiHelper(ContextErrorPolicy policy) {
    }

    public UiHelper(int sampleCount, RendererCallback renderCallback) {
        this();
        setRenderCallback(renderCallback);
    }

    public void setRenderCallback(RendererCallback renderCallback) {
        this.mRenderCallback = renderCallback;
    }

    public void detach() {
        destroySwapChain();
        this.mNativeWindow = null;
        this.mRenderSurface = null;
    }

    protected void finalize() throws Throwable {
        super.finalize();
    }

    public boolean isReadyToRender() {
        return this.mHasSwapChain;
    }

    public void setDesiredSize(int width, int height) {
        this.mDesiredWidth = width;
        this.mDesiredHeight = height;
        if (this.mRenderSurface != null) {
            this.mRenderSurface.resize(width, height);
        }
    }

    public int getDesiredWidth() {
        return this.mDesiredWidth;
    }

    public int getDesiredHeight() {
        return this.mDesiredHeight;
    }

    public void attachTo(SurfaceView view) {
        Callback callback = new C03681();
        if (attach(view)) {
            this.mRenderSurface = new SurfaceViewHandler(view);
            SurfaceHolder holder = view.getHolder();
            holder.addCallback(callback);
            holder.setFixedSize(this.mDesiredWidth, this.mDesiredHeight);
            Surface surface = holder.getSurface();
            if (surface != null && surface.isValid()) {
                callback.surfaceCreated(holder);
            }
        }
    }

    public void attachTo(TextureView view) {
        SurfaceTextureListener listener = new C03692();
        if (attach(view)) {
            this.mRenderSurface = new TextureViewHandler(view);
            view.setSurfaceTextureListener(listener);
            if (view.isAvailable()) {
                listener.onSurfaceTextureAvailable(view.getSurfaceTexture(), this.mDesiredWidth, this.mDesiredHeight);
            }
        }
    }

    private boolean attach(Object nativeWindow) {
        if (this.mNativeWindow != null) {
            if (this.mNativeWindow == nativeWindow) {
                return false;
            }
            destroySwapChain();
        }
        this.mNativeWindow = nativeWindow;
        return true;
    }

    private void createSwapChain(Surface sur) {
        this.mRenderCallback.onNativeWindowChanged(sur);
        this.mHasSwapChain = true;
    }

    private void destroySwapChain() {
        if (this.mRenderSurface != null) {
            this.mRenderSurface.detach();
        }
        this.mRenderCallback.onDetachedFromSurface();
        this.mHasSwapChain = false;
    }
}
