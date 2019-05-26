package com.google.ar.sceneform.rendering;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;

class ViewAttachmentManager {
    private static final String VIEW_RENDERABLE_WINDOW = "ViewRenderableWindow";
    private final FrameLayout frameLayout;
    private final View ownerView;
    private final LayoutParams viewLayoutParams;
    private final WindowManager.LayoutParams windowLayoutParams = createWindowLayoutParams();
    private final WindowManager windowManager;

    ViewAttachmentManager(Context context, View ownerView) {
        this.ownerView = ownerView;
        this.windowManager = (WindowManager) context.getSystemService("window");
        this.frameLayout = new FrameLayout(context);
        this.viewLayoutParams = createViewLayoutParams();
    }

    FrameLayout getFrameLayout() {
        return this.frameLayout;
    }

    void onResume() {
        this.ownerView.post(new -$$Lambda$ViewAttachmentManager$zHzKkfBniDn8VWbgoIAhBR19Pac());
    }

    public static /* synthetic */ void lambda$onResume$0(ViewAttachmentManager viewAttachmentManager) {
        if (viewAttachmentManager.frameLayout.getParent() == null && viewAttachmentManager.ownerView.isAttachedToWindow()) {
            viewAttachmentManager.windowManager.addView(viewAttachmentManager.frameLayout, viewAttachmentManager.windowLayoutParams);
        }
    }

    void onPause() {
        if (this.frameLayout.getParent() != null) {
            this.windowManager.removeView(this.frameLayout);
        }
    }

    void addView(View view) {
        if (view.getParent() != this.frameLayout) {
            this.frameLayout.addView(view, this.viewLayoutParams);
        }
    }

    void removeView(View view) {
        if (view.getParent() == this.frameLayout) {
            this.frameLayout.removeView(view);
        }
    }

    private static WindowManager.LayoutParams createWindowLayoutParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(-2, -2, 1000, 16777752, -3);
        params.setTitle(VIEW_RENDERABLE_WINDOW);
        return params;
    }

    private static LayoutParams createViewLayoutParams() {
        return new LayoutParams(-2, -2);
    }
}
