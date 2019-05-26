package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;

public class Optimizer {
    static final int FLAG_CHAIN_DANGLING = 1;
    static final int FLAG_RECOMPUTE_BOUNDS = 2;
    static final int FLAG_USE_OPTIMIZE = 0;
    public static final int OPTIMIZATION_BARRIER = 2;
    public static final int OPTIMIZATION_CHAIN = 4;
    public static final int OPTIMIZATION_DIMENSIONS = 8;
    public static final int OPTIMIZATION_DIRECT = 1;
    public static final int OPTIMIZATION_GROUPS = 32;
    public static final int OPTIMIZATION_NONE = 0;
    public static final int OPTIMIZATION_RATIO = 16;
    public static final int OPTIMIZATION_STANDARD = 7;
    static boolean[] flags = new boolean[3];

    static void checkMatchParent(ConstraintWidgetContainer container, LinearSystem system, ConstraintWidget widget) {
        if (container.mListDimensionBehaviors[0] != DimensionBehaviour.WRAP_CONTENT && widget.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_PARENT) {
            int left = widget.mLeft.mMargin;
            int right = container.getWidth() - widget.mRight.mMargin;
            widget.mLeft.mSolverVariable = system.createObjectVariable(widget.mLeft);
            widget.mRight.mSolverVariable = system.createObjectVariable(widget.mRight);
            system.addEquality(widget.mLeft.mSolverVariable, left);
            system.addEquality(widget.mRight.mSolverVariable, right);
            widget.mHorizontalResolution = 2;
            widget.setHorizontalDimension(left, right);
        }
        if (container.mListDimensionBehaviors[1] != DimensionBehaviour.WRAP_CONTENT && widget.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_PARENT) {
            left = widget.mTop.mMargin;
            right = container.getHeight() - widget.mBottom.mMargin;
            widget.mTop.mSolverVariable = system.createObjectVariable(widget.mTop);
            widget.mBottom.mSolverVariable = system.createObjectVariable(widget.mBottom);
            system.addEquality(widget.mTop.mSolverVariable, left);
            system.addEquality(widget.mBottom.mSolverVariable, right);
            if (widget.mBaselineDistance > 0 || widget.getVisibility() == 8) {
                widget.mBaseline.mSolverVariable = system.createObjectVariable(widget.mBaseline);
                system.addEquality(widget.mBaseline.mSolverVariable, widget.mBaselineDistance + left);
            }
            widget.mVerticalResolution = 2;
            widget.setVerticalDimension(left, right);
        }
    }

    private static boolean optimizableMatchConstraint(ConstraintWidget constraintWidget, int orientation) {
        if (constraintWidget.mListDimensionBehaviors[orientation] != DimensionBehaviour.MATCH_CONSTRAINT) {
            return false;
        }
        int i = 1;
        if (constraintWidget.mDimensionRatio != 0.0f) {
            DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
            if (orientation != 0) {
                i = 0;
            }
            return dimensionBehaviourArr[i] == DimensionBehaviour.MATCH_CONSTRAINT ? false : false;
        } else {
            if (orientation != 0) {
                if (constraintWidget.mMatchConstraintDefaultHeight == 0 && constraintWidget.mMatchConstraintMinHeight == 0) {
                    if (constraintWidget.mMatchConstraintMaxHeight != 0) {
                    }
                }
                return false;
            } else if (constraintWidget.mMatchConstraintDefaultWidth == 0 && constraintWidget.mMatchConstraintMinWidth == 0 && constraintWidget.mMatchConstraintMaxWidth == 0) {
                return true;
            } else {
                return false;
            }
            return true;
        }
    }

    static void analyze(int optimisationLevel, ConstraintWidget widget) {
        ConstraintWidget constraintWidget = widget;
        widget.updateResolutionNodes();
        ResolutionAnchor leftNode = constraintWidget.mLeft.getResolutionNode();
        ResolutionAnchor topNode = constraintWidget.mTop.getResolutionNode();
        ResolutionAnchor rightNode = constraintWidget.mRight.getResolutionNode();
        ResolutionAnchor bottomNode = constraintWidget.mBottom.getResolutionNode();
        boolean optimiseDimensions = (optimisationLevel & 8) == 8;
        boolean isOptimizableHorizontalMatch = constraintWidget.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT && optimizableMatchConstraint(constraintWidget, 0);
        if (!(leftNode.type == 4 || rightNode.type == 4)) {
            if (constraintWidget.mListDimensionBehaviors[0] != DimensionBehaviour.FIXED) {
                if (!isOptimizableHorizontalMatch || widget.getVisibility() != 8) {
                    if (isOptimizableHorizontalMatch) {
                        int width = widget.getWidth();
                        leftNode.setType(1);
                        rightNode.setType(1);
                        if (constraintWidget.mLeft.mTarget == null && constraintWidget.mRight.mTarget == null) {
                            if (optimiseDimensions) {
                                rightNode.dependsOn(leftNode, 1, widget.getResolutionWidth());
                            } else {
                                rightNode.dependsOn(leftNode, width);
                            }
                        } else if (constraintWidget.mLeft.mTarget == null || constraintWidget.mRight.mTarget != null) {
                            if (constraintWidget.mLeft.mTarget != null || constraintWidget.mRight.mTarget == null) {
                                if (!(constraintWidget.mLeft.mTarget == null || constraintWidget.mRight.mTarget == null)) {
                                    if (optimiseDimensions) {
                                        widget.getResolutionWidth().addDependent(leftNode);
                                        widget.getResolutionWidth().addDependent(rightNode);
                                    }
                                    if (constraintWidget.mDimensionRatio == 0.0f) {
                                        leftNode.setType(3);
                                        rightNode.setType(3);
                                        leftNode.setOpposite(rightNode, 0.0f);
                                        rightNode.setOpposite(leftNode, 0.0f);
                                    } else {
                                        leftNode.setType(2);
                                        rightNode.setType(2);
                                        leftNode.setOpposite(rightNode, (float) (-width));
                                        rightNode.setOpposite(leftNode, (float) width);
                                        constraintWidget.setWidth(width);
                                    }
                                }
                            } else if (optimiseDimensions) {
                                leftNode.dependsOn(rightNode, -1, widget.getResolutionWidth());
                            } else {
                                leftNode.dependsOn(rightNode, -width);
                            }
                        } else if (optimiseDimensions) {
                            rightNode.dependsOn(leftNode, 1, widget.getResolutionWidth());
                        } else {
                            rightNode.dependsOn(leftNode, width);
                        }
                    }
                }
            }
            if (constraintWidget.mLeft.mTarget == null && constraintWidget.mRight.mTarget == null) {
                leftNode.setType(1);
                rightNode.setType(1);
                if (optimiseDimensions) {
                    rightNode.dependsOn(leftNode, 1, widget.getResolutionWidth());
                } else {
                    rightNode.dependsOn(leftNode, widget.getWidth());
                }
            } else if (constraintWidget.mLeft.mTarget != null && constraintWidget.mRight.mTarget == null) {
                leftNode.setType(1);
                rightNode.setType(1);
                if (optimiseDimensions) {
                    rightNode.dependsOn(leftNode, 1, widget.getResolutionWidth());
                } else {
                    rightNode.dependsOn(leftNode, widget.getWidth());
                }
            } else if (constraintWidget.mLeft.mTarget == null && constraintWidget.mRight.mTarget != null) {
                leftNode.setType(1);
                rightNode.setType(1);
                leftNode.dependsOn(rightNode, -widget.getWidth());
                if (optimiseDimensions) {
                    leftNode.dependsOn(rightNode, -1, widget.getResolutionWidth());
                } else {
                    leftNode.dependsOn(rightNode, -widget.getWidth());
                }
            } else if (!(constraintWidget.mLeft.mTarget == null || constraintWidget.mRight.mTarget == null)) {
                leftNode.setType(2);
                rightNode.setType(2);
                if (optimiseDimensions) {
                    widget.getResolutionWidth().addDependent(leftNode);
                    widget.getResolutionWidth().addDependent(rightNode);
                    leftNode.setOpposite(rightNode, -1, widget.getResolutionWidth());
                    rightNode.setOpposite(leftNode, 1, widget.getResolutionWidth());
                } else {
                    leftNode.setOpposite(rightNode, (float) (-widget.getWidth()));
                    rightNode.setOpposite(leftNode, (float) widget.getWidth());
                }
            }
        }
        boolean z = constraintWidget.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT && optimizableMatchConstraint(constraintWidget, 1);
        boolean isOptimizableVerticalMatch = z;
        if (topNode.type != 4 && bottomNode.type != 4) {
            if (constraintWidget.mListDimensionBehaviors[1] != DimensionBehaviour.FIXED) {
                if (!isOptimizableVerticalMatch || widget.getVisibility() != 8) {
                    if (isOptimizableVerticalMatch) {
                        int height = widget.getHeight();
                        topNode.setType(1);
                        bottomNode.setType(1);
                        if (constraintWidget.mTop.mTarget == null && constraintWidget.mBottom.mTarget == null) {
                            if (optimiseDimensions) {
                                bottomNode.dependsOn(topNode, 1, widget.getResolutionHeight());
                                return;
                            } else {
                                bottomNode.dependsOn(topNode, height);
                                return;
                            }
                        } else if (constraintWidget.mTop.mTarget == null || constraintWidget.mBottom.mTarget != null) {
                            if (constraintWidget.mTop.mTarget != null || constraintWidget.mBottom.mTarget == null) {
                                if (constraintWidget.mTop.mTarget != null && constraintWidget.mBottom.mTarget != null) {
                                    if (optimiseDimensions) {
                                        widget.getResolutionHeight().addDependent(topNode);
                                        widget.getResolutionWidth().addDependent(bottomNode);
                                    }
                                    if (constraintWidget.mDimensionRatio == 0.0f) {
                                        topNode.setType(3);
                                        bottomNode.setType(3);
                                        topNode.setOpposite(bottomNode, 0.0f);
                                        bottomNode.setOpposite(topNode, 0.0f);
                                        return;
                                    }
                                    topNode.setType(2);
                                    bottomNode.setType(2);
                                    topNode.setOpposite(bottomNode, (float) (-height));
                                    bottomNode.setOpposite(topNode, (float) height);
                                    constraintWidget.setHeight(height);
                                    if (constraintWidget.mBaselineDistance > 0) {
                                        constraintWidget.mBaseline.getResolutionNode().dependsOn(1, topNode, constraintWidget.mBaselineDistance);
                                        return;
                                    }
                                    return;
                                }
                                return;
                            } else if (optimiseDimensions) {
                                topNode.dependsOn(bottomNode, -1, widget.getResolutionHeight());
                                return;
                            } else {
                                topNode.dependsOn(bottomNode, -height);
                                return;
                            }
                        } else if (optimiseDimensions) {
                            bottomNode.dependsOn(topNode, 1, widget.getResolutionHeight());
                            return;
                        } else {
                            bottomNode.dependsOn(topNode, height);
                            return;
                        }
                    }
                    return;
                }
            }
            if (constraintWidget.mTop.mTarget == null && constraintWidget.mBottom.mTarget == null) {
                topNode.setType(1);
                bottomNode.setType(1);
                if (optimiseDimensions) {
                    bottomNode.dependsOn(topNode, 1, widget.getResolutionHeight());
                } else {
                    bottomNode.dependsOn(topNode, widget.getHeight());
                }
                if (constraintWidget.mBaseline.mTarget != null) {
                    constraintWidget.mBaseline.getResolutionNode().setType(1);
                    topNode.dependsOn(1, constraintWidget.mBaseline.getResolutionNode(), -constraintWidget.mBaselineDistance);
                }
            } else if (constraintWidget.mTop.mTarget != null && constraintWidget.mBottom.mTarget == null) {
                topNode.setType(1);
                bottomNode.setType(1);
                if (optimiseDimensions) {
                    bottomNode.dependsOn(topNode, 1, widget.getResolutionHeight());
                } else {
                    bottomNode.dependsOn(topNode, widget.getHeight());
                }
                if (constraintWidget.mBaselineDistance > 0) {
                    constraintWidget.mBaseline.getResolutionNode().dependsOn(1, topNode, constraintWidget.mBaselineDistance);
                }
            } else if (constraintWidget.mTop.mTarget == null && constraintWidget.mBottom.mTarget != null) {
                topNode.setType(1);
                bottomNode.setType(1);
                if (optimiseDimensions) {
                    topNode.dependsOn(bottomNode, -1, widget.getResolutionHeight());
                } else {
                    topNode.dependsOn(bottomNode, -widget.getHeight());
                }
                if (constraintWidget.mBaselineDistance > 0) {
                    constraintWidget.mBaseline.getResolutionNode().dependsOn(1, topNode, constraintWidget.mBaselineDistance);
                }
            } else if (constraintWidget.mTop.mTarget != null && constraintWidget.mBottom.mTarget != null) {
                topNode.setType(2);
                bottomNode.setType(2);
                if (optimiseDimensions) {
                    topNode.setOpposite(bottomNode, -1, widget.getResolutionHeight());
                    bottomNode.setOpposite(topNode, 1, widget.getResolutionHeight());
                    widget.getResolutionHeight().addDependent(topNode);
                    widget.getResolutionWidth().addDependent(bottomNode);
                } else {
                    topNode.setOpposite(bottomNode, (float) (-widget.getHeight()));
                    bottomNode.setOpposite(topNode, (float) widget.getHeight());
                }
                if (constraintWidget.mBaselineDistance > 0) {
                    constraintWidget.mBaseline.getResolutionNode().dependsOn(1, topNode, constraintWidget.mBaselineDistance);
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean applyChainOptimized(android.support.constraint.solver.widgets.ConstraintWidgetContainer r39, android.support.constraint.solver.LinearSystem r40, int r41, int r42, android.support.constraint.solver.widgets.ChainHead r43) {
        /*
        r0 = r40;
        r1 = r41;
        r2 = r43;
        r3 = r2.mFirst;
        r4 = r2.mLast;
        r5 = r2.mFirstVisibleWidget;
        r6 = r2.mLastVisibleWidget;
        r7 = r2.mHead;
        r8 = r3;
        r9 = 0;
        r10 = 0;
        r11 = 0;
        r12 = r2.mTotalWeight;
        r13 = r2.mFirstMatchConstraintWidget;
        r14 = r2.mLastMatchConstraintWidget;
        r2 = r39;
        r15 = r8;
        r8 = r2.mListDimensionBehaviors;
        r8 = r8[r1];
        r2 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        r16 = 0;
        r17 = r9;
        if (r8 != r2) goto L_0x002b;
    L_0x0029:
        r2 = 1;
        goto L_0x002d;
    L_0x002b:
        r2 = r16;
    L_0x002d:
        r8 = 0;
        r18 = 0;
        r19 = 0;
        if (r1 != 0) goto L_0x0054;
    L_0x0034:
        r9 = r7.mHorizontalChainStyle;
        if (r9 != 0) goto L_0x003a;
    L_0x0038:
        r9 = 1;
        goto L_0x003c;
    L_0x003a:
        r9 = r16;
    L_0x003c:
        r8 = r9;
        r9 = r7.mHorizontalChainStyle;
        r21 = r2;
        r2 = 1;
        if (r9 != r2) goto L_0x0046;
    L_0x0044:
        r2 = 1;
        goto L_0x0048;
    L_0x0046:
        r2 = r16;
    L_0x0048:
        r9 = r7.mHorizontalChainStyle;
        r22 = r2;
        r2 = 2;
        if (r9 != r2) goto L_0x0051;
    L_0x004f:
        r2 = 1;
        goto L_0x0053;
    L_0x0051:
        r2 = r16;
    L_0x0053:
        goto L_0x0075;
    L_0x0054:
        r21 = r2;
        r2 = r7.mVerticalChainStyle;
        if (r2 != 0) goto L_0x005c;
    L_0x005a:
        r2 = 1;
        goto L_0x005e;
    L_0x005c:
        r2 = r16;
    L_0x005e:
        r8 = r2;
        r2 = r7.mVerticalChainStyle;
        r9 = 1;
        if (r2 != r9) goto L_0x0066;
    L_0x0064:
        r2 = 1;
        goto L_0x0068;
    L_0x0066:
        r2 = r16;
    L_0x0068:
        r9 = r7.mVerticalChainStyle;
        r23 = r2;
        r2 = 2;
        if (r9 != r2) goto L_0x0071;
    L_0x006f:
        r2 = 1;
        goto L_0x0073;
    L_0x0071:
        r2 = r16;
    L_0x0073:
        r22 = r23;
    L_0x0075:
        r9 = 0;
        r18 = 0;
        r24 = r7;
        r7 = r11;
        r11 = r15;
        r15 = r9;
        r9 = r16;
    L_0x007f:
        r19 = 0;
        r25 = r13;
        r13 = 8;
        if (r10 != 0) goto L_0x014b;
    L_0x0087:
        r26 = r10;
        r10 = r11.getVisibility();
        if (r10 == r13) goto L_0x00d2;
    L_0x008f:
        r9 = r9 + 1;
        if (r1 != 0) goto L_0x009a;
    L_0x0093:
        r10 = r11.getWidth();
        r10 = (float) r10;
        r15 = r15 + r10;
        goto L_0x00a0;
    L_0x009a:
        r10 = r11.getHeight();
        r10 = (float) r10;
        r15 = r15 + r10;
    L_0x00a0:
        if (r11 == r5) goto L_0x00ac;
    L_0x00a2:
        r10 = r11.mListAnchors;
        r10 = r10[r42];
        r10 = r10.getMargin();
        r10 = (float) r10;
        r15 = r15 + r10;
    L_0x00ac:
        if (r11 == r6) goto L_0x00ba;
    L_0x00ae:
        r10 = r11.mListAnchors;
        r20 = r42 + 1;
        r10 = r10[r20];
        r10 = r10.getMargin();
        r10 = (float) r10;
        r15 = r15 + r10;
    L_0x00ba:
        r10 = r11.mListAnchors;
        r10 = r10[r42];
        r10 = r10.getMargin();
        r10 = (float) r10;
        r18 = r18 + r10;
        r10 = r11.mListAnchors;
        r20 = r42 + 1;
        r10 = r10[r20];
        r10 = r10.getMargin();
        r10 = (float) r10;
        r18 = r18 + r10;
    L_0x00d2:
        r10 = r11.mListAnchors;
        r10 = r10[r42];
        r27 = r9;
        r9 = r11.getVisibility();
        if (r9 == r13) goto L_0x010e;
    L_0x00de:
        r9 = r11.mListDimensionBehaviors;
        r9 = r9[r1];
        r13 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        if (r9 != r13) goto L_0x010e;
    L_0x00e6:
        r7 = r7 + 1;
        if (r1 != 0) goto L_0x00f8;
    L_0x00ea:
        r9 = r11.mMatchConstraintDefaultWidth;
        if (r9 == 0) goto L_0x00ef;
    L_0x00ee:
        return r16;
    L_0x00ef:
        r9 = r11.mMatchConstraintMinWidth;
        if (r9 != 0) goto L_0x00f7;
    L_0x00f3:
        r9 = r11.mMatchConstraintMaxWidth;
        if (r9 == 0) goto L_0x0106;
    L_0x00f7:
        return r16;
    L_0x00f8:
        r9 = r11.mMatchConstraintDefaultHeight;
        if (r9 == 0) goto L_0x00fd;
    L_0x00fc:
        return r16;
    L_0x00fd:
        r9 = r11.mMatchConstraintMinHeight;
        if (r9 != 0) goto L_0x010d;
    L_0x0101:
        r9 = r11.mMatchConstraintMaxHeight;
        if (r9 == 0) goto L_0x0106;
    L_0x0105:
        goto L_0x010d;
    L_0x0106:
        r9 = r11.mDimensionRatio;
        r9 = (r9 > r19 ? 1 : (r9 == r19 ? 0 : -1));
        if (r9 == 0) goto L_0x010e;
    L_0x010c:
        return r16;
    L_0x010d:
        return r16;
    L_0x010e:
        r9 = r11.mListAnchors;
        r13 = r42 + 1;
        r9 = r9[r13];
        r9 = r9.mTarget;
        if (r9 == 0) goto L_0x0134;
    L_0x0118:
        r13 = r9.mOwner;
        r28 = r7;
        r7 = r13.mListAnchors;
        r7 = r7[r42];
        r7 = r7.mTarget;
        if (r7 == 0) goto L_0x0132;
    L_0x0124:
        r7 = r13.mListAnchors;
        r7 = r7[r42];
        r7 = r7.mTarget;
        r7 = r7.mOwner;
        if (r7 == r11) goto L_0x012f;
    L_0x012e:
        goto L_0x0132;
    L_0x012f:
        r17 = r13;
        goto L_0x0139;
    L_0x0132:
        r7 = 0;
        goto L_0x0137;
    L_0x0134:
        r28 = r7;
        r7 = 0;
    L_0x0137:
        r17 = r7;
    L_0x0139:
        if (r17 == 0) goto L_0x0141;
    L_0x013b:
        r7 = r17;
        r11 = r7;
        r10 = r26;
        goto L_0x0143;
    L_0x0141:
        r7 = 1;
        r10 = r7;
    L_0x0143:
        r13 = r25;
        r9 = r27;
        r7 = r28;
        goto L_0x007f;
    L_0x014b:
        r26 = r10;
        r10 = r3.mListAnchors;
        r10 = r10[r42];
        r10 = r10.getResolutionNode();
        r13 = r4.mListAnchors;
        r20 = r42 + 1;
        r13 = r13[r20];
        r13 = r13.getResolutionNode();
        r29 = r14;
        r14 = r10.target;
        if (r14 == 0) goto L_0x0488;
    L_0x0165:
        r14 = r13.target;
        if (r14 != 0) goto L_0x0178;
    L_0x0169:
        r30 = r2;
        r32 = r6;
        r37 = r7;
        r33 = r8;
        r38 = r9;
        r35 = r13;
        r13 = r0;
        goto L_0x0495;
    L_0x0178:
        r14 = r10.target;
        r14 = r14.state;
        r0 = 1;
        if (r14 != r0) goto L_0x0479;
    L_0x017f:
        r14 = r13.target;
        r14 = r14.state;
        if (r14 == r0) goto L_0x0195;
    L_0x0185:
        r30 = r2;
        r32 = r6;
        r37 = r7;
        r33 = r8;
        r38 = r9;
        r35 = r13;
        r13 = r40;
        goto L_0x0487;
    L_0x0195:
        if (r7 <= 0) goto L_0x019a;
    L_0x0197:
        if (r7 == r9) goto L_0x019a;
    L_0x0199:
        return r16;
    L_0x019a:
        r0 = 0;
        if (r2 != 0) goto L_0x01a1;
    L_0x019d:
        if (r8 != 0) goto L_0x01a1;
    L_0x019f:
        if (r22 == 0) goto L_0x01ba;
    L_0x01a1:
        if (r5 == 0) goto L_0x01ac;
    L_0x01a3:
        r14 = r5.mListAnchors;
        r14 = r14[r42];
        r14 = r14.getMargin();
        r0 = (float) r14;
    L_0x01ac:
        if (r6 == 0) goto L_0x01ba;
    L_0x01ae:
        r14 = r6.mListAnchors;
        r20 = r42 + 1;
        r14 = r14[r20];
        r14 = r14.getMargin();
        r14 = (float) r14;
        r0 = r0 + r14;
    L_0x01ba:
        r14 = r10.target;
        r14 = r14.resolvedOffset;
        r30 = r2;
        r2 = r13.target;
        r2 = r2.resolvedOffset;
        r20 = 0;
        r23 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1));
        if (r23 >= 0) goto L_0x01cf;
    L_0x01ca:
        r23 = r2 - r14;
        r23 = r23 - r15;
        goto L_0x01d3;
    L_0x01cf:
        r23 = r14 - r2;
        r23 = r23 - r15;
    L_0x01d3:
        r27 = 1;
        if (r7 <= 0) goto L_0x02c1;
    L_0x01d7:
        if (r7 != r9) goto L_0x02c1;
    L_0x01d9:
        r20 = r11.getParent();
        if (r20 == 0) goto L_0x01f0;
    L_0x01df:
        r31 = r2;
        r2 = r11.getParent();
        r2 = r2.mListDimensionBehaviors;
        r2 = r2[r1];
        r32 = r6;
        r6 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (r2 != r6) goto L_0x01f4;
    L_0x01ef:
        return r16;
    L_0x01f0:
        r31 = r2;
        r32 = r6;
    L_0x01f4:
        r23 = r23 + r15;
        r23 = r23 - r18;
        r2 = r3;
        r6 = r2;
        r2 = r14;
    L_0x01fb:
        if (r6 == 0) goto L_0x02b5;
    L_0x01fd:
        r11 = android.support.constraint.solver.LinearSystem.sMetrics;
        if (r11 == 0) goto L_0x0222;
    L_0x0201:
        r11 = android.support.constraint.solver.LinearSystem.sMetrics;
        r33 = r8;
        r34 = r9;
        r8 = r11.nonresolvedWidgets;
        r8 = r8 - r27;
        r11.nonresolvedWidgets = r8;
        r8 = android.support.constraint.solver.LinearSystem.sMetrics;
        r35 = r13;
        r36 = r14;
        r13 = r8.resolvedWidgets;
        r13 = r13 + r27;
        r8.resolvedWidgets = r13;
        r8 = android.support.constraint.solver.LinearSystem.sMetrics;
        r13 = r8.chainConnectionResolved;
        r13 = r13 + r27;
        r8.chainConnectionResolved = r13;
        goto L_0x022a;
    L_0x0222:
        r33 = r8;
        r34 = r9;
        r35 = r13;
        r36 = r14;
    L_0x022a:
        r8 = r6.mNextChainWidget;
        r17 = r8[r1];
        if (r17 != 0) goto L_0x0236;
    L_0x0230:
        if (r6 != r4) goto L_0x0233;
    L_0x0232:
        goto L_0x0236;
    L_0x0233:
        r13 = r40;
        goto L_0x02a9;
    L_0x0236:
        r8 = (float) r7;
        r8 = r23 / r8;
        r9 = (r12 > r19 ? 1 : (r12 == r19 ? 0 : -1));
        if (r9 <= 0) goto L_0x0251;
    L_0x023d:
        r9 = r6.mWeight;
        r9 = r9[r1];
        r11 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r9 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1));
        if (r9 != 0) goto L_0x0249;
    L_0x0247:
        r8 = 0;
        goto L_0x0251;
    L_0x0249:
        r9 = r6.mWeight;
        r9 = r9[r1];
        r9 = r9 * r23;
        r8 = r9 / r12;
    L_0x0251:
        r9 = r6.getVisibility();
        r11 = 8;
        if (r9 != r11) goto L_0x025a;
    L_0x0259:
        r8 = 0;
    L_0x025a:
        r9 = r6.mListAnchors;
        r9 = r9[r42];
        r9 = r9.getMargin();
        r9 = (float) r9;
        r2 = r2 + r9;
        r9 = r6.mListAnchors;
        r9 = r9[r42];
        r9 = r9.getResolutionNode();
        r11 = r10.resolvedTarget;
        r9.resolve(r11, r2);
        r9 = r6.mListAnchors;
        r11 = r42 + 1;
        r9 = r9[r11];
        r9 = r9.getResolutionNode();
        r11 = r10.resolvedTarget;
        r13 = r2 + r8;
        r9.resolve(r11, r13);
        r9 = r6.mListAnchors;
        r9 = r9[r42];
        r9 = r9.getResolutionNode();
        r13 = r40;
        r9.addResolvedValue(r13);
        r9 = r6.mListAnchors;
        r11 = r42 + 1;
        r9 = r9[r11];
        r9 = r9.getResolutionNode();
        r9.addResolvedValue(r13);
        r2 = r2 + r8;
        r9 = r6.mListAnchors;
        r11 = r42 + 1;
        r9 = r9[r11];
        r9 = r9.getMargin();
        r9 = (float) r9;
        r2 = r2 + r9;
    L_0x02a9:
        r6 = r17;
        r8 = r33;
        r9 = r34;
        r13 = r35;
        r14 = r36;
        goto L_0x01fb;
    L_0x02b5:
        r33 = r8;
        r34 = r9;
        r35 = r13;
        r36 = r14;
        r13 = r40;
        r8 = 1;
        return r8;
    L_0x02c1:
        r31 = r2;
        r32 = r6;
        r33 = r8;
        r34 = r9;
        r35 = r13;
        r36 = r14;
        r13 = r40;
        r2 = (r23 > r19 ? 1 : (r23 == r19 ? 0 : -1));
        if (r2 >= 0) goto L_0x02db;
    L_0x02d3:
        r8 = 0;
        r22 = 0;
        r2 = 1;
        r30 = r2;
        r33 = r8;
    L_0x02db:
        if (r30 == 0) goto L_0x0378;
    L_0x02dd:
        r23 = r23 - r0;
        r2 = r3;
        r6 = r3.getBiasPercent(r1);
        r6 = r6 * r23;
        r14 = r36 + r6;
        r11 = r2;
        r23 = r14;
    L_0x02eb:
        if (r11 == 0) goto L_0x0372;
    L_0x02ed:
        r2 = android.support.constraint.solver.LinearSystem.sMetrics;
        if (r2 == 0) goto L_0x0309;
    L_0x02f1:
        r2 = android.support.constraint.solver.LinearSystem.sMetrics;
        r8 = r2.nonresolvedWidgets;
        r8 = r8 - r27;
        r2.nonresolvedWidgets = r8;
        r2 = android.support.constraint.solver.LinearSystem.sMetrics;
        r8 = r2.resolvedWidgets;
        r8 = r8 + r27;
        r2.resolvedWidgets = r8;
        r2 = android.support.constraint.solver.LinearSystem.sMetrics;
        r8 = r2.chainConnectionResolved;
        r8 = r8 + r27;
        r2.chainConnectionResolved = r8;
    L_0x0309:
        r2 = r11.mNextChainWidget;
        r17 = r2[r1];
        if (r17 != 0) goto L_0x0311;
    L_0x030f:
        if (r11 != r4) goto L_0x036e;
    L_0x0311:
        r2 = 0;
        if (r1 != 0) goto L_0x031a;
    L_0x0314:
        r6 = r11.getWidth();
        r2 = (float) r6;
        goto L_0x031f;
    L_0x031a:
        r6 = r11.getHeight();
        r2 = (float) r6;
    L_0x031f:
        r6 = r11.mListAnchors;
        r6 = r6[r42];
        r6 = r6.getMargin();
        r6 = (float) r6;
        r6 = r23 + r6;
        r8 = r11.mListAnchors;
        r8 = r8[r42];
        r8 = r8.getResolutionNode();
        r9 = r10.resolvedTarget;
        r8.resolve(r9, r6);
        r8 = r11.mListAnchors;
        r9 = r42 + 1;
        r8 = r8[r9];
        r8 = r8.getResolutionNode();
        r9 = r10.resolvedTarget;
        r14 = r6 + r2;
        r8.resolve(r9, r14);
        r8 = r11.mListAnchors;
        r8 = r8[r42];
        r8 = r8.getResolutionNode();
        r8.addResolvedValue(r13);
        r8 = r11.mListAnchors;
        r9 = r42 + 1;
        r8 = r8[r9];
        r8 = r8.getResolutionNode();
        r8.addResolvedValue(r13);
        r6 = r6 + r2;
        r8 = r11.mListAnchors;
        r9 = r42 + 1;
        r8 = r8[r9];
        r8 = r8.getMargin();
        r8 = (float) r8;
        r23 = r6 + r8;
    L_0x036e:
        r11 = r17;
        goto L_0x02eb;
    L_0x0372:
        r37 = r7;
        r38 = r34;
        goto L_0x0477;
    L_0x0378:
        if (r33 != 0) goto L_0x037c;
    L_0x037a:
        if (r22 == 0) goto L_0x0372;
    L_0x037c:
        if (r33 == 0) goto L_0x0381;
    L_0x037e:
        r23 = r23 - r0;
        goto L_0x0385;
    L_0x0381:
        if (r22 == 0) goto L_0x0385;
    L_0x0383:
        r23 = r23 - r0;
    L_0x0385:
        r2 = r3;
        r9 = r34 + 1;
        r6 = (float) r9;
        r6 = r23 / r6;
        if (r22 == 0) goto L_0x039d;
    L_0x038d:
        r8 = r34;
        r9 = 1;
        if (r8 <= r9) goto L_0x0398;
    L_0x0392:
        r9 = r8 + -1;
        r9 = (float) r9;
        r6 = r23 / r9;
        goto L_0x039f;
    L_0x0398:
        r9 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r6 = r23 / r9;
        goto L_0x039f;
    L_0x039d:
        r8 = r34;
    L_0x039f:
        r9 = r36;
        r11 = r3.getVisibility();
        r14 = 8;
        if (r11 == r14) goto L_0x03aa;
    L_0x03a9:
        r9 = r9 + r6;
    L_0x03aa:
        if (r22 == 0) goto L_0x03ba;
    L_0x03ac:
        r11 = 1;
        if (r8 <= r11) goto L_0x03ba;
    L_0x03af:
        r11 = r5.mListAnchors;
        r11 = r11[r42];
        r11 = r11.getMargin();
        r11 = (float) r11;
        r9 = r36 + r11;
    L_0x03ba:
        if (r33 == 0) goto L_0x03c8;
    L_0x03bc:
        if (r5 == 0) goto L_0x03c8;
    L_0x03be:
        r11 = r5.mListAnchors;
        r11 = r11[r42];
        r11 = r11.getMargin();
        r11 = (float) r11;
        r9 = r9 + r11;
    L_0x03c8:
        r11 = r2;
        r23 = r9;
    L_0x03cb:
        if (r11 == 0) goto L_0x0473;
    L_0x03cd:
        r2 = android.support.constraint.solver.LinearSystem.sMetrics;
        if (r2 == 0) goto L_0x03ee;
    L_0x03d1:
        r2 = android.support.constraint.solver.LinearSystem.sMetrics;
        r37 = r7;
        r38 = r8;
        r7 = r2.nonresolvedWidgets;
        r7 = r7 - r27;
        r2.nonresolvedWidgets = r7;
        r2 = android.support.constraint.solver.LinearSystem.sMetrics;
        r7 = r2.resolvedWidgets;
        r7 = r7 + r27;
        r2.resolvedWidgets = r7;
        r2 = android.support.constraint.solver.LinearSystem.sMetrics;
        r7 = r2.chainConnectionResolved;
        r7 = r7 + r27;
        r2.chainConnectionResolved = r7;
        goto L_0x03f2;
    L_0x03ee:
        r37 = r7;
        r38 = r8;
    L_0x03f2:
        r2 = r11.mNextChainWidget;
        r17 = r2[r1];
        if (r17 != 0) goto L_0x03fe;
    L_0x03f8:
        if (r11 != r4) goto L_0x03fb;
    L_0x03fa:
        goto L_0x03fe;
    L_0x03fb:
        r8 = 8;
        goto L_0x046b;
    L_0x03fe:
        r2 = 0;
        if (r1 != 0) goto L_0x0407;
    L_0x0401:
        r7 = r11.getWidth();
        r2 = (float) r7;
        goto L_0x040c;
    L_0x0407:
        r7 = r11.getHeight();
        r2 = (float) r7;
    L_0x040c:
        if (r11 == r5) goto L_0x0419;
    L_0x040e:
        r7 = r11.mListAnchors;
        r7 = r7[r42];
        r7 = r7.getMargin();
        r7 = (float) r7;
        r23 = r23 + r7;
    L_0x0419:
        r7 = r23;
        r8 = r11.mListAnchors;
        r8 = r8[r42];
        r8 = r8.getResolutionNode();
        r9 = r10.resolvedTarget;
        r8.resolve(r9, r7);
        r8 = r11.mListAnchors;
        r9 = r42 + 1;
        r8 = r8[r9];
        r8 = r8.getResolutionNode();
        r9 = r10.resolvedTarget;
        r14 = r7 + r2;
        r8.resolve(r9, r14);
        r8 = r11.mListAnchors;
        r8 = r8[r42];
        r8 = r8.getResolutionNode();
        r8.addResolvedValue(r13);
        r8 = r11.mListAnchors;
        r9 = r42 + 1;
        r8 = r8[r9];
        r8 = r8.getResolutionNode();
        r8.addResolvedValue(r13);
        r8 = r11.mListAnchors;
        r9 = r42 + 1;
        r8 = r8[r9];
        r8 = r8.getMargin();
        r8 = (float) r8;
        r8 = r8 + r2;
        r23 = r7 + r8;
        if (r17 == 0) goto L_0x03fb;
    L_0x0461:
        r7 = r17.getVisibility();
        r8 = 8;
        if (r7 == r8) goto L_0x046b;
    L_0x0469:
        r23 = r23 + r6;
    L_0x046b:
        r11 = r17;
        r7 = r37;
        r8 = r38;
        goto L_0x03cb;
    L_0x0473:
        r37 = r7;
        r38 = r8;
    L_0x0477:
        r2 = 1;
        return r2;
    L_0x0479:
        r30 = r2;
        r32 = r6;
        r37 = r7;
        r33 = r8;
        r38 = r9;
        r35 = r13;
        r13 = r40;
    L_0x0487:
        return r16;
    L_0x0488:
        r30 = r2;
        r32 = r6;
        r37 = r7;
        r33 = r8;
        r38 = r9;
        r35 = r13;
        r13 = r0;
    L_0x0495:
        return r16;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.Optimizer.applyChainOptimized(android.support.constraint.solver.widgets.ConstraintWidgetContainer, android.support.constraint.solver.LinearSystem, int, int, android.support.constraint.solver.widgets.ChainHead):boolean");
    }

    static void setOptimizedWidget(ConstraintWidget widget, int orientation, int resolvedOffset) {
        int startOffset = orientation * 2;
        int endOffset = startOffset + 1;
        widget.mListAnchors[startOffset].getResolutionNode().resolvedTarget = widget.getParent().mLeft.getResolutionNode();
        widget.mListAnchors[startOffset].getResolutionNode().resolvedOffset = (float) resolvedOffset;
        widget.mListAnchors[startOffset].getResolutionNode().state = 1;
        widget.mListAnchors[endOffset].getResolutionNode().resolvedTarget = widget.mListAnchors[startOffset].getResolutionNode();
        widget.mListAnchors[endOffset].getResolutionNode().resolvedOffset = (float) widget.getLength(orientation);
        widget.mListAnchors[endOffset].getResolutionNode().state = 1;
    }
}
