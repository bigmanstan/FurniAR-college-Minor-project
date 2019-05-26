package com.google.ar.sceneform;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;
import android.view.MotionEvent;
import android.view.SurfaceView;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.Renderer;
import com.google.ar.sceneform.utilities.AndroidPreconditions;
import com.google.ar.sceneform.utilities.MovingAverageMillisecondsTracker;
import com.google.ar.sceneform.utilities.Preconditions;

public class SceneView extends SurfaceView implements FrameCallback {
    private static final String TAG = SceneView.class.getSimpleName();
    private Color backgroundColor;
    private volatile boolean debugEnabled = false;
    private final MovingAverageMillisecondsTracker frameRenderTracker = new MovingAverageMillisecondsTracker();
    private final FrameTime frameTime = new FrameTime();
    private final MovingAverageMillisecondsTracker frameTotalTracker = new MovingAverageMillisecondsTracker();
    private final MovingAverageMillisecondsTracker frameUpdateTracker = new MovingAverageMillisecondsTracker();
    private boolean isInitialized = false;
    private Renderer renderer = null;
    private Scene scene;

    public SceneView(Context context) {
        super(context);
        initialize();
    }

    public SceneView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize();
    }

    private void doRender() {
        Renderer renderer = this.renderer;
        if (renderer != null) {
            if (this.debugEnabled) {
                this.frameRenderTracker.beginSample();
            }
            renderer.render(this.debugEnabled);
            if (this.debugEnabled) {
                this.frameRenderTracker.endSample();
            }
        }
    }

    private void doUpdate(long j) {
        if (this.debugEnabled) {
            this.frameUpdateTracker.beginSample();
        }
        this.frameTime.update(j);
        this.scene.dispatchUpdate(this.frameTime);
        if (this.debugEnabled) {
            this.frameUpdateTracker.endSample();
        }
    }

    private void initialize() {
        if (this.isInitialized) {
            Log.w(TAG, "SceneView already initialized.");
            return;
        }
        if (AndroidPreconditions.isMinAndroidApiLevel()) {
            this.renderer = new Renderer(this);
            if (this.backgroundColor != null) {
                this.renderer.setClearColor(this.backgroundColor);
            }
            this.scene = new Scene(this);
            this.renderer.setCameraProvider(this.scene.getCamera());
        } else {
            Log.e(TAG, "Sceneform requires Android N or later");
            this.renderer = null;
        }
        this.isInitialized = true;
    }

    public void destroy() {
        if (this.renderer != null) {
            this.renderer.dispose();
        }
    }

    public void doFrame(long j) {
        Choreographer.getInstance().postFrameCallback(this);
        if (this.debugEnabled) {
            this.frameTotalTracker.beginSample();
        }
        if (onBeginFrame()) {
            doUpdate(j);
            doRender();
        }
        if (this.debugEnabled) {
            this.frameTotalTracker.endSample();
            if ((System.currentTimeMillis() / 1000) % 60 == 0) {
                String str = TAG;
                double average = this.frameRenderTracker.getAverage();
                StringBuilder stringBuilder = new StringBuilder(52);
                stringBuilder.append(" PERF COUNTER: frameRender: ");
                stringBuilder.append(average);
                Log.d(str, stringBuilder.toString());
                str = TAG;
                average = this.frameTotalTracker.getAverage();
                StringBuilder stringBuilder2 = new StringBuilder(51);
                stringBuilder2.append(" PERF COUNTER: frameTotal: ");
                stringBuilder2.append(average);
                Log.d(str, stringBuilder2.toString());
                str = TAG;
                average = this.frameUpdateTracker.getAverage();
                stringBuilder = new StringBuilder(52);
                stringBuilder.append(" PERF COUNTER: frameUpdate: ");
                stringBuilder.append(average);
                Log.d(str, stringBuilder.toString());
            }
        }
    }

    public void enableDebug(boolean z) {
        this.debugEnabled = z;
    }

    public Renderer getRenderer() {
        return this.renderer;
    }

    public Scene getScene() {
        return this.scene;
    }

    public boolean isDebugEnabled() {
        return this.debugEnabled;
    }

    protected boolean onBeginFrame() {
        return true;
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        ((Renderer) Preconditions.checkNotNull(this.renderer)).setDesiredSize(i3 - i, i4 - i2);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!super.onTouchEvent(motionEvent)) {
            this.scene.onTouchEvent(motionEvent);
        }
        return true;
    }

    public void pause() {
        Choreographer.getInstance().removeFrameCallback(this);
        if (this.renderer != null) {
            this.renderer.onPause();
        }
    }

    public void resume() throws CameraNotAvailableException {
        if (this.renderer != null) {
            this.renderer.onResume();
        }
        Choreographer.getInstance().removeFrameCallback(this);
        Choreographer.getInstance().postFrameCallback(this);
    }

    public void setBackground(Drawable drawable) {
        if (drawable instanceof ColorDrawable) {
            this.backgroundColor = new Color(((ColorDrawable) drawable).getColor());
            if (this.renderer != null) {
                this.renderer.setClearColor(this.backgroundColor);
            }
            return;
        }
        this.backgroundColor = null;
        if (this.renderer != null) {
            this.renderer.setDefaultClearColor();
        }
        super.setBackground(drawable);
    }
}
