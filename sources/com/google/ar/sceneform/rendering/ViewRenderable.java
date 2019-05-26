package com.google.ar.sceneform.rendering;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.google.ar.sceneform.collision.Box;
import com.google.ar.sceneform.math.Matrix;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.RenderViewToExternalTexture.OnViewSizeChangedListener;
import com.google.ar.sceneform.resources.ResourceRegistry;
import com.google.ar.sceneform.utilities.AndroidPreconditions;
import com.google.ar.sceneform.utilities.Preconditions;
import java.util.OptionalInt;
import java.util.concurrent.CompletableFuture;

@RequiresApi(api = 24)
public class ViewRenderable extends Renderable {
    private static final String TAG = ViewRenderable.class.getSimpleName();
    private HorizontalAlignment horizontalAlignment = HorizontalAlignment.CENTER;
    private boolean isInitialized;
    private final OnViewSizeChangedListener onViewSizeChangedListener = new -$$Lambda$ViewRenderable$WJvAbSMSNTPgUdaMeuzsbfBYWic();
    private VerticalAlignment verticalAlignment = VerticalAlignment.BOTTOM;
    private final View view;
    @Nullable
    private ViewRenderableInternalData viewRenderableData;
    private final Matrix viewScaleMatrix = new Matrix();
    private ViewSizer viewSizer;

    public enum HorizontalAlignment {
        LEFT,
        CENTER,
        RIGHT
    }

    public enum VerticalAlignment {
        BOTTOM,
        CENTER,
        TOP
    }

    public static final class Builder extends Builder<ViewRenderable, Builder> {
        private static final int DEFAULT_DP_TO_METERS = 250;
        private HorizontalAlignment horizontalAlignment;
        private OptionalInt resourceId;
        private VerticalAlignment verticalAlignment;
        @Nullable
        private View view;
        private ViewSizer viewSizer;

        public /* bridge */ /* synthetic */ Boolean hasSource() {
            return super.hasSource();
        }

        private Builder() {
            this.viewSizer = new DpToMetersViewSizer(250);
            this.verticalAlignment = VerticalAlignment.BOTTOM;
            this.horizontalAlignment = HorizontalAlignment.CENTER;
            this.resourceId = OptionalInt.empty();
        }

        public Builder setView(Context context, View view) {
            this.view = view;
            this.context = context;
            this.registryId = view;
            return this;
        }

        public Builder setView(Context context, int resourceId) {
            this.resourceId = OptionalInt.of(resourceId);
            this.context = context;
            this.registryId = null;
            return this;
        }

        public Builder setSizer(ViewSizer viewSizer) {
            Preconditions.checkNotNull(viewSizer, "Parameter \"viewSizer\" was null.");
            this.viewSizer = viewSizer;
            return this;
        }

        public Builder setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
            this.horizontalAlignment = horizontalAlignment;
            return this;
        }

        public Builder setVerticalAlignment(VerticalAlignment verticalAlignment) {
            this.verticalAlignment = verticalAlignment;
            return this;
        }

        public CompletableFuture<ViewRenderable> build() {
            if (!(hasSource().booleanValue() || this.context == null)) {
                setSource(this.context, C0023R.raw.sceneform_view_renderable);
            }
            this.registryId = this.view;
            return super.build();
        }

        protected ViewRenderable makeRenderable() {
            if (this.view != null) {
                return new ViewRenderable(this, this.view);
            }
            return new ViewRenderable(this, inflateViewFromResourceId());
        }

        protected Class<ViewRenderable> getRenderableClass() {
            return ViewRenderable.class;
        }

        protected ResourceRegistry<ViewRenderable> getRenderableRegistry() {
            return ResourceManager.getInstance().getViewRenderableRegistry();
        }

        protected Builder getSelf() {
            return this;
        }

        protected void checkPreconditions() {
            boolean hasView;
            super.checkPreconditions();
            if (!this.resourceId.isPresent()) {
                if (this.view == null) {
                    hasView = false;
                    if (hasView) {
                        throw new AssertionError("ViewRenderable must have a source.");
                    } else if (!this.resourceId.isPresent()) {
                        if (this.view == null) {
                            throw new AssertionError("ViewRenderable must have a resourceId or a view as a source. This one has both.");
                        }
                    }
                }
            }
            hasView = true;
            if (hasView) {
                throw new AssertionError("ViewRenderable must have a source.");
            } else if (!this.resourceId.isPresent()) {
            } else {
                if (this.view == null) {
                    throw new AssertionError("ViewRenderable must have a resourceId or a view as a source. This one has both.");
                }
            }
        }

        private View inflateViewFromResourceId() {
            if (this.context != null) {
                return LayoutInflater.from(this.context).inflate(this.resourceId.getAsInt(), new FrameLayout(this.context), false);
            }
            throw new AssertionError("Context cannot be null");
        }
    }

    public static /* synthetic */ void lambda$new$0(ViewRenderable viewRenderable, int width, int height) {
        if (viewRenderable.isInitialized) {
            viewRenderable.updateSuggestedCollisionShape();
        }
    }

    public View getView() {
        return this.view;
    }

    @Deprecated
    public float getMetersToPixelsRatio() {
        float pixelsToMetersRatio = getPixelsToMetersRatio();
        if (pixelsToMetersRatio == 0.0f) {
            return 0.0f;
        }
        return 1.0f / pixelsToMetersRatio;
    }

    @Deprecated
    public float getPixelsToMetersRatio() {
        int width = this.view.getWidth();
        Vector3 size = this.viewSizer.getSize(this.view);
        if (size.f120x == 0.0f) {
            return 0.0f;
        }
        return ((float) width) / size.f120x;
    }

    @Deprecated
    public void setPixelsToMetersRatio(int pixelsToMetersRatio) {
        setSizer(new DpToMetersViewSizer((int) ViewRenderableHelpers.convertPxToDp(pixelsToMetersRatio)));
    }

    public ViewRenderable makeCopy() {
        return new ViewRenderable(this);
    }

    ViewRenderable(Builder builder, View view) {
        super((Builder) builder);
        Preconditions.checkNotNull(view, "Parameter \"view\" was null.");
        this.view = view;
        this.viewSizer = builder.viewSizer;
        this.horizontalAlignment = builder.horizontalAlignment;
        this.verticalAlignment = builder.verticalAlignment;
        RenderViewToExternalTexture renderView = new RenderViewToExternalTexture(view.getContext(), view);
        renderView.addOnViewSizeChangedListener(this.onViewSizeChangedListener);
        this.viewRenderableData = new ViewRenderableInternalData(renderView);
        this.viewRenderableData.retain();
        this.collisionShape = new Box(Vector3.zero());
    }

    ViewRenderable(ViewRenderable other) {
        super((Renderable) other);
        this.view = other.view;
        this.viewSizer = other.viewSizer;
        this.horizontalAlignment = other.horizontalAlignment;
        this.verticalAlignment = other.verticalAlignment;
        this.viewRenderableData = (ViewRenderableInternalData) Preconditions.checkNotNull(other.viewRenderableData);
        this.viewRenderableData.retain();
        this.viewRenderableData.getRenderView().addOnViewSizeChangedListener(this.onViewSizeChangedListener);
    }

    public ViewSizer getSizer() {
        return this.viewSizer;
    }

    public void setSizer(ViewSizer viewSizer) {
        Preconditions.checkNotNull(viewSizer, "Parameter \"viewSizer\" was null.");
        this.viewSizer = viewSizer;
        updateSuggestedCollisionShape();
    }

    public HorizontalAlignment getHorizontalAlignment() {
        return this.horizontalAlignment;
    }

    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        updateSuggestedCollisionShape();
    }

    public VerticalAlignment getVerticalAlignment() {
        return this.verticalAlignment;
    }

    public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
        updateSuggestedCollisionShape();
    }

    public Matrix getFinalModelMatrix(Matrix originalMatrix) {
        Preconditions.checkNotNull(originalMatrix, "Parameter \"originalMatrix\" was null.");
        Vector3 size = this.viewSizer.getSize(this.view);
        this.viewScaleMatrix.makeScale(new Vector3(size.f120x, size.f121y, 1.0f));
        this.viewScaleMatrix.setTranslation(new Vector3(getOffsetRatioForAlignment(this.horizontalAlignment) * size.f120x, getOffsetRatioForAlignment(this.verticalAlignment) * size.f121y, 0.0f));
        Matrix.multiply(originalMatrix, this.viewScaleMatrix, this.viewScaleMatrix);
        return this.viewScaleMatrix;
    }

    void prepareForDraw() {
        if (!getId().isEmpty()) {
            RenderViewToExternalTexture renderViewToExternalTexture = ((ViewRenderableInternalData) Preconditions.checkNotNull(this.viewRenderableData)).getRenderView();
            if (renderViewToExternalTexture.isAttachedToWindow()) {
                if (renderViewToExternalTexture.isLaidOut()) {
                    if (renderViewToExternalTexture.hasDrawnToSurfaceTexture()) {
                        if (!this.isInitialized) {
                            getMaterial().setExternalTexture("viewTexture", renderViewToExternalTexture.getExternalTexture());
                            updateSuggestedCollisionShape();
                            this.isInitialized = true;
                        }
                        super.prepareForDraw();
                    }
                }
            }
        }
    }

    void attachToRenderer(Renderer renderer) {
        ((ViewRenderableInternalData) Preconditions.checkNotNull(this.viewRenderableData)).getRenderView().attachView(renderer.getViewAttachmentManager());
    }

    void detatchFromRenderer() {
    }

    private void updateSuggestedCollisionShape() {
        if (!getId().isEmpty()) {
            Box box = this.collisionShape;
            if (box != null) {
                RenderableInternalData renderableData = getRenderableData();
                Vector3 viewSize = this.viewSizer.getSize(this.view);
                Vector3 size = renderableData.getSizeAabb();
                size.f120x *= viewSize.f120x;
                size.f121y *= viewSize.f121y;
                Vector3 center = renderableData.getCenterAabb();
                center.f120x *= viewSize.f120x;
                center.f121y *= viewSize.f121y;
                center.f120x += getOffsetRatioForAlignment(this.horizontalAlignment) * size.f120x;
                center.f121y += getOffsetRatioForAlignment(this.verticalAlignment) * size.f121y;
                box.setSize(size);
                box.setCenter(center);
            }
        }
    }

    private float getOffsetRatioForAlignment(HorizontalAlignment horizontalAlignment) {
        RenderableInternalData data = getRenderableData();
        Vector3 centerAabb = data.getCenterAabb();
        Vector3 extentsAabb = data.getExtentsAabb();
        switch (horizontalAlignment) {
            case LEFT:
                return (-centerAabb.f120x) + extentsAabb.f120x;
            case CENTER:
                return -centerAabb.f120x;
            case RIGHT:
                return (-centerAabb.f120x) - extentsAabb.f120x;
            default:
                String valueOf = String.valueOf(horizontalAlignment);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 29);
                stringBuilder.append("Invalid HorizontalAlignment: ");
                stringBuilder.append(valueOf);
                throw new IllegalStateException(stringBuilder.toString());
        }
    }

    private float getOffsetRatioForAlignment(VerticalAlignment verticalAlignment) {
        RenderableInternalData data = getRenderableData();
        Vector3 centerAabb = data.getCenterAabb();
        Vector3 extentsAabb = data.getExtentsAabb();
        switch (verticalAlignment) {
            case BOTTOM:
                return (-centerAabb.f121y) + extentsAabb.f121y;
            case CENTER:
                return -centerAabb.f121y;
            case TOP:
                return (-centerAabb.f121y) - extentsAabb.f121y;
            default:
                String valueOf = String.valueOf(verticalAlignment);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 27);
                stringBuilder.append("Invalid VerticalAlignment: ");
                stringBuilder.append(valueOf);
                throw new IllegalStateException(stringBuilder.toString());
        }
    }

    protected void finalize() throws Throwable {
        try {
            ThreadPools.getMainExecutor().execute(new -$$Lambda$ViewRenderable$TWzm7QPQq0tTLYMjmu_N-GKZLWo());
        } catch (Exception e) {
            Log.e(TAG, "Error while Finalizing View Renderable.", e);
        } catch (Throwable th) {
            super.finalize();
        }
        super.finalize();
    }

    void dispose() {
        AndroidPreconditions.checkUiThread();
        ViewRenderableInternalData viewRenderableData = this.viewRenderableData;
        if (viewRenderableData != null) {
            viewRenderableData.getRenderView().removeOnViewSizeChangedListener(this.onViewSizeChangedListener);
            viewRenderableData.release();
            this.viewRenderableData = null;
        }
    }

    public static Builder builder() {
        AndroidPreconditions.checkMinAndroidApiLevel();
        return new Builder();
    }
}
