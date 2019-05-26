package com.google.ar.sceneform.rendering;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.PorterDuff.Mode;
import android.support.annotation.Nullable;
import android.view.Surface;
import android.view.View;
import android.widget.LinearLayout;
import com.google.ar.sceneform.utilities.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;

class RenderViewToExternalTexture extends LinearLayout {
    private final ExternalTexture externalTexture;
    private boolean hasDrawnToSurfaceTexture = false;
    private final ArrayList<OnViewSizeChangedListener> onViewSizeChangedListeners = new ArrayList();
    private final Picture picture = new Picture();
    private final View view;
    @Nullable
    private ViewAttachmentManager viewAttachmentManager;

    public interface OnViewSizeChangedListener {
        void onViewSizeChanged(int i, int i2);
    }

    RenderViewToExternalTexture(Context context, View view) {
        super(context);
        Preconditions.checkNotNull(view, "Parameter \"view\" was null.");
        this.externalTexture = new ExternalTexture();
        this.view = view;
        addView(view);
    }

    void addOnViewSizeChangedListener(OnViewSizeChangedListener onViewSizeChangedListener) {
        if (!this.onViewSizeChangedListeners.contains(onViewSizeChangedListener)) {
            this.onViewSizeChangedListeners.add(onViewSizeChangedListener);
        }
    }

    void removeOnViewSizeChangedListener(OnViewSizeChangedListener onViewSizeChangedListener) {
        this.onViewSizeChangedListeners.remove(onViewSizeChangedListener);
    }

    ExternalTexture getExternalTexture() {
        return this.externalTexture;
    }

    boolean hasDrawnToSurfaceTexture() {
        return this.hasDrawnToSurfaceTexture;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.externalTexture.getSurfaceTexture().setDefaultBufferSize(this.view.getWidth(), this.view.getHeight());
    }

    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        Iterator it = this.onViewSizeChangedListeners.iterator();
        while (it.hasNext()) {
            ((OnViewSizeChangedListener) it.next()).onViewSizeChanged(width, height);
        }
    }

    public void dispatchDraw(Canvas canvas) {
        Surface targetSurface = this.externalTexture.getSurface();
        if (targetSurface.isValid()) {
            if (this.view.isDirty()) {
                Canvas pictureCanvas = this.picture.beginRecording(this.view.getWidth(), this.view.getHeight());
                pictureCanvas.drawColor(0, Mode.CLEAR);
                super.dispatchDraw(pictureCanvas);
                this.picture.endRecording();
                Canvas surfaceCanvas = targetSurface.lockHardwareCanvas();
                this.picture.draw(surfaceCanvas);
                targetSurface.unlockCanvasAndPost(surfaceCanvas);
                this.hasDrawnToSurfaceTexture = true;
            }
            invalidate();
        }
    }

    void attachView(ViewAttachmentManager viewAttachmentManager) {
        if (this.viewAttachmentManager == null) {
            this.viewAttachmentManager = viewAttachmentManager;
            viewAttachmentManager.addView(this);
        } else if (this.viewAttachmentManager != viewAttachmentManager) {
            throw new IllegalStateException("Cannot use the same ViewRenderable with multiple SceneViews.");
        }
    }

    void detachView() {
        if (this.viewAttachmentManager != null) {
            this.viewAttachmentManager.removeView(this);
            this.viewAttachmentManager = null;
        }
    }

    void releaseResources() {
        detachView();
    }
}
