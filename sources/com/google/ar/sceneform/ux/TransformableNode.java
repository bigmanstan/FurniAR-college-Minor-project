package com.google.ar.sceneform.ux;

public class TransformableNode extends BaseTransformableNode {
    private final RotationController rotationController;
    private final ScaleController scaleController;
    private final TranslationController translationController;

    public TransformableNode(TransformationSystem transformationSystem) {
        super(transformationSystem);
        this.translationController = new TranslationController(this, transformationSystem.getDragRecognizer());
        addTransformationController(this.translationController);
        this.scaleController = new ScaleController(this, transformationSystem.getPinchRecognizer());
        addTransformationController(this.scaleController);
        this.rotationController = new RotationController(this, transformationSystem.getTwistRecognizer());
        addTransformationController(this.rotationController);
    }

    public TranslationController getTranslationController() {
        return this.translationController;
    }

    public ScaleController getScaleController() {
        return this.scaleController;
    }

    public RotationController getRotationController() {
        return this.rotationController;
    }
}
