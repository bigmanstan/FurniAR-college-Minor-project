package com.google.ar.sceneform.ux;

import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.google.ar.sceneform.HitTestResult;
import java.util.ArrayList;

public class TransformationSystem implements TransformationGestureDetector {
    private final DragGestureRecognizer dragGestureRecognizer;
    private final GesturePointersUtility gesturePointersUtility;
    private final PinchGestureRecognizer pinchGestureRecognizer;
    private final ArrayList<BaseGestureRecognizer<?>> recognizers = new ArrayList();
    @Nullable
    private BaseTransformableNode selectedNode;
    private SelectionVisualizer selectionVisualizer;
    private final TwistGestureRecognizer twistGestureRecognizer;

    public TransformationSystem(DisplayMetrics displayMetrics, SelectionVisualizer selectionVisualizer) {
        this.selectionVisualizer = selectionVisualizer;
        this.gesturePointersUtility = new GesturePointersUtility(displayMetrics);
        this.dragGestureRecognizer = new DragGestureRecognizer(this.gesturePointersUtility);
        addGestureRecognizer(this.dragGestureRecognizer);
        this.pinchGestureRecognizer = new PinchGestureRecognizer(this.gesturePointersUtility);
        addGestureRecognizer(this.pinchGestureRecognizer);
        this.twistGestureRecognizer = new TwistGestureRecognizer(this.gesturePointersUtility);
        addGestureRecognizer(this.twistGestureRecognizer);
    }

    public void setSelectionVisualizer(SelectionVisualizer selectionVisualizer) {
        if (this.selectedNode != null) {
            this.selectionVisualizer.removeSelectionVisual(this.selectedNode);
        }
        this.selectionVisualizer = selectionVisualizer;
        if (this.selectedNode != null) {
            this.selectionVisualizer.applySelectionVisual(this.selectedNode);
        }
    }

    public SelectionVisualizer getSelectionVisualizer() {
        return this.selectionVisualizer;
    }

    @Deprecated
    public TransformationGestureDetector getGestureDetector() {
        return this;
    }

    public GesturePointersUtility getGesturePointersUtility() {
        return this.gesturePointersUtility;
    }

    public DragGestureRecognizer getDragRecognizer() {
        return this.dragGestureRecognizer;
    }

    public PinchGestureRecognizer getPinchRecognizer() {
        return this.pinchGestureRecognizer;
    }

    public TwistGestureRecognizer getTwistRecognizer() {
        return this.twistGestureRecognizer;
    }

    public void addGestureRecognizer(BaseGestureRecognizer<?> gestureRecognizer) {
        this.recognizers.add(gestureRecognizer);
    }

    @Nullable
    public BaseTransformableNode getSelectedNode() {
        return this.selectedNode;
    }

    public boolean selectNode(@Nullable BaseTransformableNode node) {
        if (!deselectNode()) {
            return false;
        }
        if (node != null) {
            this.selectedNode = node;
            this.selectionVisualizer.applySelectionVisual(this.selectedNode);
        }
        return true;
    }

    public void onTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
        for (int i = 0; i < this.recognizers.size(); i++) {
            ((BaseGestureRecognizer) this.recognizers.get(i)).onTouch(hitTestResult, motionEvent);
        }
    }

    private boolean deselectNode() {
        if (this.selectedNode == null) {
            return true;
        }
        if (this.selectedNode.isTransforming()) {
            return false;
        }
        this.selectionVisualizer.removeSelectionVisual(this.selectedNode);
        this.selectedNode = null;
        return true;
    }
}
