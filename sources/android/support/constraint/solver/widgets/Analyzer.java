package android.support.constraint.solver.widgets;

import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Analyzer {
    private Analyzer() {
    }

    public static void determineGroups(ConstraintWidgetContainer layoutWidget) {
        if ((layoutWidget.getOptimizationLevel() & 32) != 32) {
            singleGroup(layoutWidget);
            return;
        }
        boolean hasWrapContent;
        int measuredWidth;
        int measuredHeight;
        layoutWidget.mSkipSolver = true;
        layoutWidget.mGroupsWrapOptimized = false;
        layoutWidget.mHorizontalWrapOptimized = false;
        layoutWidget.mVerticalWrapOptimized = false;
        List<ConstraintWidget> widgets = layoutWidget.mChildren;
        List<ConstraintWidgetGroup> widgetGroups = layoutWidget.mWidgetGroups;
        boolean horizontalWrapContent = layoutWidget.getHorizontalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT;
        boolean verticalWrapContent = layoutWidget.getVerticalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT;
        if (!horizontalWrapContent) {
            if (!verticalWrapContent) {
                hasWrapContent = false;
                widgetGroups.clear();
                for (ConstraintWidget widget : widgets) {
                    widget.mBelongingGroup = null;
                    widget.mGroupsToSolver = false;
                    widget.resetResolutionNodes();
                }
                for (ConstraintWidget widget2 : widgets) {
                    if (widget2.mBelongingGroup != null && !determineGroups(widget2, widgetGroups, hasWrapContent)) {
                        singleGroup(layoutWidget);
                        layoutWidget.mSkipSolver = false;
                        return;
                    }
                }
                measuredWidth = 0;
                measuredHeight = 0;
                for (ConstraintWidgetGroup group : widgetGroups) {
                    measuredWidth = Math.max(measuredWidth, getMaxDimension(group, 0));
                    measuredHeight = Math.max(measuredHeight, getMaxDimension(group, 1));
                }
                if (horizontalWrapContent) {
                    layoutWidget.setHorizontalDimensionBehaviour(DimensionBehaviour.FIXED);
                    layoutWidget.setWidth(measuredWidth);
                    layoutWidget.mGroupsWrapOptimized = true;
                    layoutWidget.mHorizontalWrapOptimized = true;
                    layoutWidget.mWrapFixedWidth = measuredWidth;
                }
                if (verticalWrapContent) {
                    layoutWidget.setVerticalDimensionBehaviour(DimensionBehaviour.FIXED);
                    layoutWidget.setHeight(measuredHeight);
                    layoutWidget.mGroupsWrapOptimized = true;
                    layoutWidget.mVerticalWrapOptimized = true;
                    layoutWidget.mWrapFixedHeight = measuredHeight;
                }
                setPosition(widgetGroups, 0, layoutWidget.getWidth());
                setPosition(widgetGroups, 1, layoutWidget.getHeight());
            }
        }
        hasWrapContent = true;
        widgetGroups.clear();
        for (ConstraintWidget widget22 : widgets) {
            widget22.mBelongingGroup = null;
            widget22.mGroupsToSolver = false;
            widget22.resetResolutionNodes();
        }
        for (ConstraintWidget widget222 : widgets) {
            if (widget222.mBelongingGroup != null) {
            }
        }
        measuredWidth = 0;
        measuredHeight = 0;
        for (ConstraintWidgetGroup group2 : widgetGroups) {
            measuredWidth = Math.max(measuredWidth, getMaxDimension(group2, 0));
            measuredHeight = Math.max(measuredHeight, getMaxDimension(group2, 1));
        }
        if (horizontalWrapContent) {
            layoutWidget.setHorizontalDimensionBehaviour(DimensionBehaviour.FIXED);
            layoutWidget.setWidth(measuredWidth);
            layoutWidget.mGroupsWrapOptimized = true;
            layoutWidget.mHorizontalWrapOptimized = true;
            layoutWidget.mWrapFixedWidth = measuredWidth;
        }
        if (verticalWrapContent) {
            layoutWidget.setVerticalDimensionBehaviour(DimensionBehaviour.FIXED);
            layoutWidget.setHeight(measuredHeight);
            layoutWidget.mGroupsWrapOptimized = true;
            layoutWidget.mVerticalWrapOptimized = true;
            layoutWidget.mWrapFixedHeight = measuredHeight;
        }
        setPosition(widgetGroups, 0, layoutWidget.getWidth());
        setPosition(widgetGroups, 1, layoutWidget.getHeight());
    }

    private static boolean determineGroups(ConstraintWidget widget, List<ConstraintWidgetGroup> widgetGroups, boolean hasWrapContent) {
        ConstraintWidgetGroup traverseList = new ConstraintWidgetGroup(new ArrayList(), true);
        widgetGroups.add(traverseList);
        return traverse(widget, traverseList, widgetGroups, hasWrapContent);
    }

    private static boolean traverse(ConstraintWidget widget, ConstraintWidgetGroup upperGroup, List<ConstraintWidgetGroup> widgetGroups, boolean hasWrapContent) {
        if (widget == null) {
            return true;
        }
        widget.mOptimizerMeasured = false;
        ConstraintWidgetContainer layoutWidget = (ConstraintWidgetContainer) widget.getParent();
        if (widget.mBelongingGroup == null) {
            boolean z;
            int widgetsCount;
            widget.mOptimizerMeasurable = true;
            upperGroup.mConstrainedGroup.add(widget);
            widget.mBelongingGroup = upperGroup;
            if (widget.mLeft.mTarget == null && widget.mRight.mTarget == null && widget.mTop.mTarget == null && widget.mBottom.mTarget == null && widget.mBaseline.mTarget == null && widget.mCenter.mTarget == null) {
                invalidate(layoutWidget, widget, upperGroup);
                if (hasWrapContent) {
                    return false;
                }
            }
            if (!(widget.mTop.mTarget == null || widget.mBottom.mTarget == null)) {
                if (layoutWidget.getVerticalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT) {
                    z = true;
                } else {
                    z = false;
                }
                if (hasWrapContent) {
                    invalidate(layoutWidget, widget, upperGroup);
                    return false;
                } else if (!(widget.mTop.mTarget.mOwner == widget.getParent() && widget.mBottom.mTarget.mOwner == widget.getParent())) {
                    invalidate(layoutWidget, widget, upperGroup);
                }
            }
            if (!(widget.mLeft.mTarget == null || widget.mRight.mTarget == null)) {
                if (layoutWidget.getHorizontalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT) {
                    z = true;
                } else {
                    z = false;
                }
                if (hasWrapContent) {
                    invalidate(layoutWidget, widget, upperGroup);
                    return false;
                } else if (!(widget.mLeft.mTarget.mOwner == widget.getParent() && widget.mRight.mTarget.mOwner == widget.getParent())) {
                    invalidate(layoutWidget, widget, upperGroup);
                }
            }
            if (((widget.getHorizontalDimensionBehaviour() == DimensionBehaviour.MATCH_CONSTRAINT ? 1 : 0) ^ (widget.getVerticalDimensionBehaviour() == DimensionBehaviour.MATCH_CONSTRAINT ? 1 : 0)) != 0 && widget.mDimensionRatio != 0.0f) {
                resolveDimensionRatio(widget);
            } else if (widget.getHorizontalDimensionBehaviour() == DimensionBehaviour.MATCH_CONSTRAINT || widget.getVerticalDimensionBehaviour() == DimensionBehaviour.MATCH_CONSTRAINT) {
                invalidate(layoutWidget, widget, upperGroup);
                if (hasWrapContent) {
                    return false;
                }
            }
            if (((widget.mLeft.mTarget == null && widget.mRight.mTarget == null) || ((widget.mLeft.mTarget != null && widget.mLeft.mTarget.mOwner == widget.mParent && widget.mRight.mTarget == null) || ((widget.mRight.mTarget != null && widget.mRight.mTarget.mOwner == widget.mParent && widget.mLeft.mTarget == null) || (widget.mLeft.mTarget != null && widget.mLeft.mTarget.mOwner == widget.mParent && widget.mRight.mTarget != null && widget.mRight.mTarget.mOwner == widget.mParent)))) && widget.mCenter.mTarget == null && !(widget instanceof Guideline) && !(widget instanceof Helper)) {
                upperGroup.mStartHorizontalWidgets.add(widget);
            }
            if (((widget.mTop.mTarget == null && widget.mBottom.mTarget == null) || ((widget.mTop.mTarget != null && widget.mTop.mTarget.mOwner == widget.mParent && widget.mBottom.mTarget == null) || ((widget.mBottom.mTarget != null && widget.mBottom.mTarget.mOwner == widget.mParent && widget.mTop.mTarget == null) || (widget.mTop.mTarget != null && widget.mTop.mTarget.mOwner == widget.mParent && widget.mBottom.mTarget != null && widget.mBottom.mTarget.mOwner == widget.mParent)))) && widget.mCenter.mTarget == null && widget.mBaseline.mTarget == null && !(widget instanceof Guideline) && !(widget instanceof Helper)) {
                upperGroup.mStartVerticalWidgets.add(widget);
            }
            if (widget instanceof Helper) {
                invalidate(layoutWidget, widget, upperGroup);
                if (hasWrapContent) {
                    return false;
                }
                Helper hWidget = (Helper) widget;
                for (widgetsCount = 0; widgetsCount < hWidget.mWidgetsCount; widgetsCount++) {
                    if (!traverse(hWidget.mWidgets[widgetsCount], upperGroup, widgetGroups, hasWrapContent)) {
                        return false;
                    }
                }
            }
            for (ConstraintAnchor anchor : widget.mListAnchors) {
                if (!(anchor.mTarget == null || anchor.mTarget.mOwner == widget.getParent())) {
                    if (anchor.mType == Type.CENTER) {
                        invalidate(layoutWidget, widget, upperGroup);
                        if (hasWrapContent) {
                            return false;
                        }
                    }
                    setConnection(anchor);
                    if (!traverse(anchor.mTarget.mOwner, upperGroup, widgetGroups, hasWrapContent)) {
                        return false;
                    }
                }
            }
            return true;
        }
        if (widget.mBelongingGroup != upperGroup) {
            upperGroup.mConstrainedGroup.addAll(widget.mBelongingGroup.mConstrainedGroup);
            upperGroup.mStartHorizontalWidgets.addAll(widget.mBelongingGroup.mStartHorizontalWidgets);
            upperGroup.mStartVerticalWidgets.addAll(widget.mBelongingGroup.mStartVerticalWidgets);
            if (!widget.mBelongingGroup.mSkipSolver) {
                upperGroup.mSkipSolver = false;
            }
            widgetGroups.remove(widget.mBelongingGroup);
            for (ConstraintWidget auxWidget : widget.mBelongingGroup.mConstrainedGroup) {
                auxWidget.mBelongingGroup = upperGroup;
            }
        }
        return true;
    }

    private static void invalidate(ConstraintWidgetContainer layoutWidget, ConstraintWidget widget, ConstraintWidgetGroup group) {
        group.mSkipSolver = false;
        layoutWidget.mSkipSolver = false;
        widget.mOptimizerMeasurable = false;
    }

    private static int getMaxDimension(ConstraintWidgetGroup group, int orientation) {
        int offset = orientation * 2;
        List<ConstraintWidget> startWidgets = group.getStartWidgets(orientation);
        int size = startWidgets.size();
        int dimension = 0;
        for (int i = 0; i < size; i++) {
            boolean topLeftFlow;
            ConstraintWidget widget = (ConstraintWidget) startWidgets.get(i);
            if (widget.mListAnchors[offset + 1].mTarget != null) {
                if (widget.mListAnchors[offset].mTarget == null || widget.mListAnchors[offset + 1].mTarget == null) {
                    topLeftFlow = false;
                    dimension = Math.max(dimension, getMaxDimensionTraversal(widget, orientation, topLeftFlow, 0));
                }
            }
            topLeftFlow = true;
            dimension = Math.max(dimension, getMaxDimensionTraversal(widget, orientation, topLeftFlow, 0));
        }
        group.mGroupDimensions[orientation] = dimension;
        return dimension;
    }

    private static int getMaxDimensionTraversal(ConstraintWidget widget, int orientation, boolean topLeftFlow, int depth) {
        ConstraintWidget constraintWidget = widget;
        int i = orientation;
        boolean z = topLeftFlow;
        boolean hasBaseline = false;
        if (!constraintWidget.mOptimizerMeasurable) {
            return 0;
        }
        int baselinePreDistance;
        int baselinePostDistance;
        int startOffset;
        int endOffset;
        int flow;
        int aux;
        int dimensionPost;
        int endOffset2;
        int startOffset2;
        int postTemp;
        int dimensionPre = 0;
        int dimensionPost2 = 0;
        if (constraintWidget.mBaseline.mTarget != null && i == 1) {
            hasBaseline = true;
        }
        if (z) {
            baselinePreDistance = widget.getBaselineDistance();
            baselinePostDistance = widget.getHeight() - widget.getBaselineDistance();
            startOffset = i * 2;
            endOffset = startOffset + 1;
        } else {
            baselinePreDistance = widget.getHeight() - widget.getBaselineDistance();
            baselinePostDistance = widget.getBaselineDistance();
            endOffset = i * 2;
            startOffset = endOffset + 1;
        }
        if (constraintWidget.mListAnchors[endOffset].mTarget == null || constraintWidget.mListAnchors[startOffset].mTarget != null) {
            flow = 1;
        } else {
            flow = -1;
            aux = startOffset;
            startOffset = endOffset;
            endOffset = aux;
        }
        if (hasBaseline) {
            aux = depth - baselinePreDistance;
        } else {
            aux = depth;
        }
        int dimension = (constraintWidget.mListAnchors[startOffset].getMargin() * flow) + getParentBiasOffset(widget, orientation);
        int downDepth = dimension + aux;
        int postTemp2 = (i == 0 ? widget.getWidth() : widget.getHeight()) * flow;
        Iterator it = constraintWidget.mListAnchors[startOffset].getResolutionNode().dependents.iterator();
        while (it.hasNext()) {
            dimensionPost = dimensionPost2;
            Iterator it2 = it;
            dimensionPre = Math.max(dimensionPre, getMaxDimensionTraversal(((ResolutionAnchor) ((ResolutionNode) it.next())).myAnchor.mOwner, i, z, downDepth));
            dimensionPost2 = dimensionPost;
            it = it2;
        }
        dimensionPost = dimensionPost2;
        Iterator it3 = constraintWidget.mListAnchors[endOffset].getResolutionNode().dependents.iterator();
        int dimensionPost3 = dimensionPost;
        while (it3.hasNext()) {
            Iterator it4 = it3;
            ResolutionAnchor anchor = (ResolutionAnchor) ((ResolutionNode) it3.next());
            endOffset2 = endOffset;
            dimensionPost3 = Math.max(dimensionPost3, getMaxDimensionTraversal(anchor.myAnchor.mOwner, i, z, postTemp2 + downDepth));
            it3 = it4;
            endOffset = endOffset2;
        }
        endOffset2 = endOffset;
        if (hasBaseline) {
            dimensionPre -= baselinePreDistance;
            dimensionPost3 += baselinePostDistance;
        } else {
            dimensionPost3 += (i == 0 ? widget.getWidth() : widget.getHeight()) * flow;
        }
        dimensionPost2 = 0;
        if (i == 1) {
            Iterator it5 = constraintWidget.mBaseline.getResolutionNode().dependents.iterator();
            while (it5.hasNext()) {
                Iterator it6 = it5;
                ResolutionAnchor anchor2 = (ResolutionAnchor) ((ResolutionNode) it5.next());
                startOffset2 = startOffset;
                if (flow == 1) {
                    postTemp = postTemp2;
                    dimensionPost2 = Math.max(dimensionPost2, getMaxDimensionTraversal(anchor2.myAnchor.mOwner, i, z, baselinePreDistance + downDepth));
                } else {
                    postTemp = postTemp2;
                    dimensionPost2 = Math.max(dimensionPost2, getMaxDimensionTraversal(anchor2.myAnchor.mOwner, i, z, (baselinePostDistance * flow) + downDepth));
                }
                it5 = it6;
                startOffset = startOffset2;
                postTemp2 = postTemp;
            }
            startOffset2 = startOffset;
            postTemp = postTemp2;
            if (constraintWidget.mBaseline.getResolutionNode().dependents.size() > 0 && !hasBaseline) {
                if (flow == 1) {
                    dimensionPost2 += baselinePreDistance;
                } else {
                    dimensionPost2 -= baselinePostDistance;
                }
            }
        } else {
            startOffset2 = startOffset;
            postTemp = postTemp2;
        }
        startOffset = dimension;
        dimension += Math.max(dimensionPre, Math.max(dimensionPost3, dimensionPost2));
        endOffset = aux + startOffset;
        postTemp2 = endOffset + postTemp;
        if (flow == -1) {
            downDepth = postTemp2;
            postTemp2 = endOffset;
            endOffset = downDepth;
        }
        if (z) {
            Optimizer.setOptimizedWidget(constraintWidget, i, endOffset);
            constraintWidget.setFrame(endOffset, postTemp2, i);
        } else {
            constraintWidget.mBelongingGroup.addWidgetsToSet(constraintWidget, i);
            constraintWidget.setRelativePositioning(endOffset, i);
        }
        if (widget.getDimensionBehaviour(orientation) == DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mDimensionRatio != 0.0f) {
            constraintWidget.mBelongingGroup.addWidgetsToSet(constraintWidget, i);
        }
        if (!(constraintWidget.mListAnchors[startOffset2].mTarget == null || constraintWidget.mListAnchors[endOffset2].mTarget == null)) {
            ConstraintWidget parent = widget.getParent();
            if (constraintWidget.mListAnchors[startOffset2].mTarget.mOwner == parent && constraintWidget.mListAnchors[endOffset2].mTarget.mOwner == parent) {
                constraintWidget.mBelongingGroup.addWidgetsToSet(constraintWidget, i);
            }
        }
        return dimension;
    }

    private static void setConnection(ConstraintAnchor originAnchor) {
        ResolutionNode originNode = originAnchor.getResolutionNode();
        if (originAnchor.mTarget != null && originAnchor.mTarget.mTarget != originAnchor) {
            originAnchor.mTarget.getResolutionNode().addDependent(originNode);
        }
    }

    private static void singleGroup(ConstraintWidgetContainer layoutWidget) {
        layoutWidget.mWidgetGroups.clear();
        layoutWidget.mWidgetGroups.add(0, new ConstraintWidgetGroup(layoutWidget.mChildren));
    }

    public static void setPosition(List<ConstraintWidgetGroup> groups, int orientation, int containerLength) {
        int groupsSize = groups.size();
        for (int i = 0; i < groupsSize; i++) {
            for (ConstraintWidget widget : ((ConstraintWidgetGroup) groups.get(i)).getWidgetsToSet(orientation)) {
                if (widget.mOptimizerMeasurable) {
                    updateSizeDependentWidgets(widget, orientation, containerLength);
                }
            }
        }
    }

    private static void updateSizeDependentWidgets(ConstraintWidget widget, int orientation, int containerLength) {
        int offset = orientation * 2;
        ConstraintAnchor startAnchor = widget.mListAnchors[offset];
        ConstraintAnchor endAnchor = widget.mListAnchors[offset + 1];
        boolean hasBias = (startAnchor.mTarget == null || endAnchor.mTarget == null) ? false : true;
        if (hasBias) {
            Optimizer.setOptimizedWidget(widget, orientation, getParentBiasOffset(widget, orientation) + startAnchor.getMargin());
        } else if (widget.mDimensionRatio == 0.0f || widget.getDimensionBehaviour(orientation) != DimensionBehaviour.MATCH_CONSTRAINT) {
            int end = containerLength - widget.getRelativePositioning(orientation);
            start = end - widget.getLength(orientation);
            widget.setFrame(start, end, orientation);
            Optimizer.setOptimizedWidget(widget, orientation, start);
        } else {
            start = resolveDimensionRatio(widget);
            int start = (int) widget.mListAnchors[offset].getResolutionNode().resolvedOffset;
            int end2 = start + start;
            endAnchor.getResolutionNode().resolvedTarget = startAnchor.getResolutionNode();
            endAnchor.getResolutionNode().resolvedOffset = (float) start;
            endAnchor.getResolutionNode().state = 1;
            widget.setFrame(start, end2, orientation);
        }
    }

    private static int getParentBiasOffset(ConstraintWidget widget, int orientation) {
        int offset = orientation * 2;
        ConstraintAnchor startAnchor = widget.mListAnchors[offset];
        ConstraintAnchor endAnchor = widget.mListAnchors[offset + 1];
        if (startAnchor.mTarget == null || startAnchor.mTarget.mOwner != widget.mParent || endAnchor.mTarget == null || endAnchor.mTarget.mOwner != widget.mParent) {
            return 0;
        }
        return (int) (((float) (((widget.mParent.getLength(orientation) - startAnchor.getMargin()) - endAnchor.getMargin()) - widget.getLength(orientation))) * (orientation == 0 ? widget.mHorizontalBiasPercent : widget.mVerticalBiasPercent));
    }

    private static int resolveDimensionRatio(ConstraintWidget widget) {
        int length = -1;
        if (widget.getHorizontalDimensionBehaviour() == DimensionBehaviour.MATCH_CONSTRAINT) {
            if (widget.mDimensionRatioSide == 0) {
                length = (int) (((float) widget.getHeight()) * widget.mDimensionRatio);
            } else {
                length = (int) (((float) widget.getHeight()) / widget.mDimensionRatio);
            }
            widget.setWidth(length);
        } else if (widget.getVerticalDimensionBehaviour() == DimensionBehaviour.MATCH_CONSTRAINT) {
            if (widget.mDimensionRatioSide == 1) {
                length = (int) (((float) widget.getWidth()) * widget.mDimensionRatio);
            } else {
                length = (int) (((float) widget.getWidth()) / widget.mDimensionRatio);
            }
            widget.setHeight(length);
        }
        return length;
    }
}
