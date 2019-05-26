package com.google.ar.sceneform.ux;

import android.view.MotionEvent;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Node.OnTapListener;
import java.util.ArrayList;

public abstract class BaseTransformableNode extends Node implements OnTapListener {
    private final ArrayList<BaseTransformationController<?>> controllers = new ArrayList();
    private final TransformationSystem transformationSystem;

    public BaseTransformableNode(TransformationSystem transformationSystem) {
        this.transformationSystem = transformationSystem;
        setOnTapListener(this);
    }

    public TransformationSystem getTransformationSystem() {
        return this.transformationSystem;
    }

    public boolean isTransforming() {
        for (int i = 0; i < this.controllers.size(); i++) {
            if (((BaseTransformationController) this.controllers.get(i)).isTransforming()) {
                return true;
            }
        }
        return false;
    }

    public boolean isSelected() {
        return this.transformationSystem.getSelectedNode() == this;
    }

    public boolean select() {
        return this.transformationSystem.selectNode(this);
    }

    public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
        select();
    }

    protected void addTransformationController(BaseTransformationController<?> transformationController) {
        this.controllers.add(transformationController);
    }

    protected void removeTransformationController(BaseTransformationController<?> transformationController) {
        this.controllers.remove(transformationController);
    }
}
