package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import java.util.ArrayList;

class Chain {
    private static final boolean DEBUG = false;

    Chain() {
    }

    static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem system, int orientation) {
        int offset;
        int chainsSize;
        ChainHead[] chainsArray;
        if (orientation == 0) {
            offset = 0;
            chainsSize = constraintWidgetContainer.mHorizontalChainsSize;
            chainsArray = constraintWidgetContainer.mHorizontalChainsArray;
        } else {
            offset = 2;
            chainsSize = constraintWidgetContainer.mVerticalChainsSize;
            chainsArray = constraintWidgetContainer.mVerticalChainsArray;
        }
        for (int i = 0; i < chainsSize; i++) {
            ChainHead first = chainsArray[i];
            first.define();
            if (!constraintWidgetContainer.optimizeFor(4)) {
                applyChainConstraints(constraintWidgetContainer, system, orientation, offset, first);
            } else if (!Optimizer.applyChainOptimized(constraintWidgetContainer, system, orientation, offset, first)) {
                applyChainConstraints(constraintWidgetContainer, system, orientation, offset, first);
            }
        }
    }

    static void applyChainConstraints(ConstraintWidgetContainer container, LinearSystem system, int orientation, int offset, ChainHead chainHead) {
        boolean isChainSpread;
        boolean isChainSpread2;
        boolean z;
        ConstraintWidget widget;
        float f;
        ConstraintWidget constraintWidget;
        int i;
        ConstraintWidget widget2;
        ArrayList<ConstraintWidget> listMatchConstraints;
        ConstraintWidget lastVisibleWidget;
        ConstraintWidget firstVisibleWidget;
        ConstraintWidget constraintWidget2;
        ConstraintWidget constraintWidget3;
        SolverVariable beginTarget;
        ConstraintAnchor begin;
        ConstraintWidgetContainer constraintWidgetContainer = container;
        LinearSystem linearSystem = system;
        ChainHead chainHead2 = chainHead;
        ConstraintWidget first = chainHead2.mFirst;
        ConstraintWidget last = chainHead2.mLast;
        ConstraintWidget firstVisibleWidget2 = chainHead2.mFirstVisibleWidget;
        ConstraintWidget lastVisibleWidget2 = chainHead2.mLastVisibleWidget;
        ConstraintWidget head = chainHead2.mHead;
        ConstraintWidget widget3 = first;
        float totalWeights = chainHead2.mTotalWeight;
        ConstraintWidget firstMatchConstraintsWidget = chainHead2.mFirstMatchConstraintWidget;
        ConstraintWidget previousMatchConstraintsWidget = chainHead2.mLastMatchConstraintWidget;
        boolean isChainPacked = widget3;
        boolean isChainSpreadInside = null;
        boolean done = false;
        boolean isWrapContent = constraintWidgetContainer.mListDimensionBehaviors[orientation] == DimensionBehaviour.WRAP_CONTENT;
        if (orientation == 0) {
            isChainSpread = head.mHorizontalChainStyle == 0;
            isChainSpread2 = head.mHorizontalChainStyle;
            z = head.mHorizontalChainStyle == 2;
            widget = isChainPacked;
            boolean isChainPacked2 = isChainSpreadInside;
        } else {
            boolean isChainSpread3 = head.mVerticalChainStyle == 0;
            isChainSpread2 = head.mVerticalChainStyle;
            z = head.mVerticalChainStyle == 2;
            widget = isChainPacked;
            ConstraintWidget next = isChainSpreadInside;
            isChainSpread = isChainSpread3;
        }
        isChainSpreadInside = isChainSpread2;
        isChainPacked = z;
        while (true) {
            ConstraintWidget previousMatchConstraintsWidget2 = previousMatchConstraintsWidget;
            if (done) {
                break;
            }
            ConstraintWidget constraintWidget4;
            ConstraintAnchor begin2 = widget.mListAnchors[offset];
            int strength = 4;
            if (isWrapContent || isChainPacked) {
                strength = 1;
            }
            int margin = begin2.getMargin();
            if (!(begin2.mTarget == null || widget == first)) {
                margin += begin2.mTarget.getMargin();
            }
            int margin2 = margin;
            if (isChainPacked && widget != first && widget != firstVisibleWidget2) {
                strength = 6;
            } else if (isChainSpread && isWrapContent) {
                strength = 4;
            }
            int strength2 = strength;
            if (begin2.mTarget != null) {
                if (widget == firstVisibleWidget2) {
                    f = totalWeights;
                    constraintWidget = firstMatchConstraintsWidget;
                    linearSystem.addGreaterThan(begin2.mSolverVariable, begin2.mTarget.mSolverVariable, margin2, 5);
                } else {
                    f = totalWeights;
                    constraintWidget = firstMatchConstraintsWidget;
                    linearSystem.addGreaterThan(begin2.mSolverVariable, begin2.mTarget.mSolverVariable, margin2, 6);
                }
                linearSystem.addEquality(begin2.mSolverVariable, begin2.mTarget.mSolverVariable, margin2, strength2);
            } else {
                f = totalWeights;
                constraintWidget = firstMatchConstraintsWidget;
            }
            ConstraintAnchor constraintAnchor;
            if (isWrapContent) {
                if (widget.getVisibility() == 8 || widget.mListDimensionBehaviors[orientation] != DimensionBehaviour.MATCH_CONSTRAINT) {
                    constraintAnchor = begin2;
                    margin2 = 0;
                } else {
                    margin2 = 0;
                    linearSystem.addGreaterThan(widget.mListAnchors[offset + 1].mSolverVariable, widget.mListAnchors[offset].mSolverVariable, 0, 5);
                }
                linearSystem.addGreaterThan(widget.mListAnchors[offset].mSolverVariable, constraintWidgetContainer.mListAnchors[offset].mSolverVariable, margin2, 6);
            } else {
                constraintAnchor = begin2;
            }
            ConstraintAnchor nextAnchor = widget.mListAnchors[offset + 1].mTarget;
            if (nextAnchor != null) {
                constraintWidget4 = nextAnchor.mOwner;
                if (constraintWidget4.mListAnchors[offset].mTarget == null || constraintWidget4.mListAnchors[offset].mTarget.mOwner != widget) {
                    constraintWidget4 = null;
                }
            } else {
                constraintWidget4 = null;
            }
            next = constraintWidget4;
            if (next != null) {
                widget = next;
            } else {
                done = true;
            }
            previousMatchConstraintsWidget = previousMatchConstraintsWidget2;
            totalWeights = f;
            firstMatchConstraintsWidget = constraintWidget;
        }
        f = totalWeights;
        constraintWidget = firstMatchConstraintsWidget;
        if (lastVisibleWidget2 != null && last.mListAnchors[offset + 1].mTarget != null) {
            nextAnchor = lastVisibleWidget2.mListAnchors[offset + 1];
            linearSystem.addLowerThan(nextAnchor.mSolverVariable, last.mListAnchors[offset + 1].mTarget.mSolverVariable, -nextAnchor.getMargin(), 5);
        }
        if (isWrapContent) {
            linearSystem.addGreaterThan(constraintWidgetContainer.mListAnchors[offset + 1].mSolverVariable, last.mListAnchors[offset + 1].mSolverVariable, last.mListAnchors[offset + 1].getMargin(), 6);
        }
        ConstraintAnchor end = chainHead2.mWeightedMatchConstraintsWidgets;
        if (end != null) {
            margin2 = end.size();
            if (margin2 > 1) {
                ConstraintWidget lastMatch = null;
                float lastWeight = 0.0f;
                if (chainHead2.mHasUndefinedWeights && !chainHead2.mHasComplexMatchWeights) {
                    f = (float) chainHead2.mWidgetsMatchCount;
                }
                i = 0;
                while (i < margin2) {
                    int count;
                    Object end2;
                    float currentWeight;
                    firstMatchConstraintsWidget = (ConstraintWidget) end.get(i);
                    float currentWeight2 = firstMatchConstraintsWidget.mWeight[orientation];
                    if (currentWeight2 < 0.0f) {
                        if (chainHead2.mHasComplexMatchWeights) {
                            count = margin2;
                            widget2 = widget;
                            listMatchConstraints = end;
                            linearSystem.addEquality(firstMatchConstraintsWidget.mListAnchors[offset + 1].mSolverVariable, firstMatchConstraintsWidget.mListAnchors[offset].mSolverVariable, null, 4);
                            i++;
                            margin2 = count;
                            widget = widget2;
                            end2 = listMatchConstraints;
                            constraintWidgetContainer = container;
                        } else {
                            count = margin2;
                            widget2 = widget;
                            listMatchConstraints = end;
                            currentWeight = 1.0f;
                        }
                    } else {
                        currentWeight = currentWeight2;
                        count = margin2;
                        widget2 = widget;
                        listMatchConstraints = end;
                    }
                    if (currentWeight == 0.0f) {
                        linearSystem.addEquality(firstMatchConstraintsWidget.mListAnchors[offset + 1].mSolverVariable, firstMatchConstraintsWidget.mListAnchors[offset].mSolverVariable, 0, 6);
                    } else {
                        ConstraintWidget lastMatch2;
                        if (lastMatch != null) {
                            SolverVariable begin3 = lastMatch.mListAnchors[offset].mSolverVariable;
                            SolverVariable end3 = lastMatch.mListAnchors[offset + 1].mSolverVariable;
                            SolverVariable nextBegin = firstMatchConstraintsWidget.mListAnchors[offset].mSolverVariable;
                            SolverVariable nextEnd = firstMatchConstraintsWidget.mListAnchors[offset + 1].mSolverVariable;
                            lastMatch2 = lastMatch;
                            lastMatch = system.createRow();
                            lastMatch.createRowEqualMatchDimensions(lastWeight, f, currentWeight, begin3, end3, nextBegin, nextEnd);
                            linearSystem.addConstraint(lastMatch);
                        } else {
                            lastMatch2 = lastMatch;
                        }
                        lastMatch = firstMatchConstraintsWidget;
                        lastWeight = currentWeight;
                    }
                    i++;
                    margin2 = count;
                    widget = widget2;
                    end2 = listMatchConstraints;
                    constraintWidgetContainer = container;
                }
            }
        }
        widget2 = widget;
        listMatchConstraints = end;
        SolverVariable beginTarget2;
        SolverVariable endTarget;
        ConstraintAnchor begin4;
        if (firstVisibleWidget2 != null) {
            if (firstVisibleWidget2 != lastVisibleWidget2) {
                if (!isChainPacked) {
                    ConstraintWidget constraintWidget5 = head;
                    lastVisibleWidget = lastVisibleWidget2;
                    firstVisibleWidget = firstVisibleWidget2;
                    constraintWidget2 = widget2;
                    Object obj = listMatchConstraints;
                }
            }
            nextAnchor = first.mListAnchors[offset];
            begin2 = last.mListAnchors[offset + 1];
            beginTarget2 = first.mListAnchors[offset].mTarget != null ? first.mListAnchors[offset].mTarget.mSolverVariable : null;
            endTarget = last.mListAnchors[offset + 1].mTarget != null ? last.mListAnchors[offset + 1].mTarget.mSolverVariable : null;
            if (firstVisibleWidget2 == lastVisibleWidget2) {
                nextAnchor = firstVisibleWidget2.mListAnchors[offset];
                begin2 = firstVisibleWidget2.mListAnchors[offset + 1];
            }
            begin4 = nextAnchor;
            end = begin2;
            if (beginTarget2 == null || endTarget == null) {
                lastVisibleWidget = lastVisibleWidget2;
                firstVisibleWidget = firstVisibleWidget2;
                constraintWidget2 = widget2;
                ArrayList<ConstraintWidget> arrayList = listMatchConstraints;
            } else {
                float bias;
                if (orientation == 0) {
                    bias = head.mHorizontalBiasPercent;
                } else {
                    bias = head.mVerticalBiasPercent;
                }
                lastVisibleWidget = lastVisibleWidget2;
                firstVisibleWidget = firstVisibleWidget2;
                system.addCentering(begin4.mSolverVariable, beginTarget2, begin4.getMargin(), bias, endTarget, end.mSolverVariable, end.getMargin(), 5);
            }
            firstVisibleWidget2 = firstVisibleWidget;
            constraintWidget3 = last;
            linearSystem = system;
            if (!isChainSpread) {
                if (!isChainSpreadInside) {
                    ConstraintWidget constraintWidget6 = firstVisibleWidget2;
                    last = constraintWidget3;
                    return;
                }
            }
            if (firstVisibleWidget2 == null) {
                nextAnchor = firstVisibleWidget2.mListAnchors[offset];
                begin2 = lastVisibleWidget.mListAnchors[offset + 1];
                beginTarget = nextAnchor.mTarget == null ? nextAnchor.mTarget.mSolverVariable : null;
                nextBegin = begin2.mTarget == null ? begin2.mTarget.mSolverVariable : null;
                last = constraintWidget3;
                if (last != lastVisibleWidget) {
                    ConstraintAnchor realEnd = last.mListAnchors[offset + 1];
                    nextBegin = realEnd.mTarget == null ? realEnd.mTarget.mSolverVariable : null;
                }
                beginTarget2 = nextBegin;
                if (firstVisibleWidget2 == lastVisibleWidget) {
                    nextAnchor = firstVisibleWidget2.mListAnchors[offset];
                    begin2 = firstVisibleWidget2.mListAnchors[offset + 1];
                }
                begin = nextAnchor;
                ConstraintAnchor end4 = begin2;
                if (beginTarget != null || beginTarget2 == null) {
                }
                strength = begin.getMargin();
                if (lastVisibleWidget == null) {
                    lastVisibleWidget = last;
                }
                system.addCentering(begin.mSolverVariable, beginTarget, strength, 0.5f, beginTarget2, end4.mSolverVariable, lastVisibleWidget.mListAnchors[offset + 1].getMargin(), 5);
                return;
            }
            last = constraintWidget3;
            return;
        }
        lastVisibleWidget = lastVisibleWidget2;
        firstVisibleWidget = firstVisibleWidget2;
        constraintWidget2 = widget2;
        arrayList = listMatchConstraints;
        ConstraintWidget last2;
        boolean applyFixedEquality;
        if (!isChainSpread || firstVisibleWidget == null) {
            last2 = last;
            int i2 = 8;
            if (!isChainSpreadInside || firstVisibleWidget == null) {
                firstVisibleWidget2 = firstVisibleWidget;
                constraintWidget3 = last2;
                linearSystem = system;
                if (isChainSpread) {
                    if (!isChainSpreadInside) {
                        ConstraintWidget constraintWidget62 = firstVisibleWidget2;
                        last = constraintWidget3;
                        return;
                    }
                }
                if (firstVisibleWidget2 == null) {
                    last = constraintWidget3;
                    return;
                }
                nextAnchor = firstVisibleWidget2.mListAnchors[offset];
                begin2 = lastVisibleWidget.mListAnchors[offset + 1];
                if (nextAnchor.mTarget == null) {
                }
                beginTarget = nextAnchor.mTarget == null ? nextAnchor.mTarget.mSolverVariable : null;
                if (begin2.mTarget == null) {
                }
                last = constraintWidget3;
                if (last != lastVisibleWidget) {
                    ConstraintAnchor realEnd2 = last.mListAnchors[offset + 1];
                    if (realEnd2.mTarget == null) {
                    }
                    nextBegin = realEnd2.mTarget == null ? realEnd2.mTarget.mSolverVariable : null;
                }
                beginTarget2 = nextBegin;
                if (firstVisibleWidget2 == lastVisibleWidget) {
                    nextAnchor = firstVisibleWidget2.mListAnchors[offset];
                    begin2 = firstVisibleWidget2.mListAnchors[offset + 1];
                }
                begin = nextAnchor;
                ConstraintAnchor end42 = begin2;
                if (beginTarget != null) {
                }
            }
            ConstraintWidget widget4;
            ConstraintAnchor endTarget2;
            ConstraintAnchor end5;
            ConstraintWidget firstVisibleWidget3;
            widget3 = firstVisibleWidget;
            constraintWidget4 = firstVisibleWidget;
            applyFixedEquality = chainHead2.mWidgetsMatchCount > 0 && chainHead2.mWidgetsCount == chainHead2.mWidgetsMatchCount;
            firstVisibleWidget2 = widget3;
            lastVisibleWidget2 = constraintWidget4;
            while (firstVisibleWidget2 != null) {
                widget3 = firstVisibleWidget2.mNextChainWidget[orientation];
                while (widget3 != null && widget3.getVisibility() == r14) {
                    widget3 = widget3.mNextChainWidget[orientation];
                }
                if (firstVisibleWidget2 == firstVisibleWidget || firstVisibleWidget2 == lastVisibleWidget || widget3 == null) {
                    constraintWidget2 = lastVisibleWidget2;
                    widget4 = firstVisibleWidget2;
                    next = widget3;
                } else {
                    SolverVariable beginNext;
                    ConstraintAnchor beginNextAnchor;
                    SolverVariable beginNextTarget;
                    ConstraintWidget next2;
                    if (widget3 == lastVisibleWidget) {
                        widget3 = null;
                    }
                    head = widget3;
                    begin4 = firstVisibleWidget2.mListAnchors[offset];
                    nextEnd = begin4.mSolverVariable;
                    if (begin4.mTarget != null) {
                        end3 = begin4.mTarget.mSolverVariable;
                    }
                    SolverVariable beginTarget3 = lastVisibleWidget2.mListAnchors[offset + 1].mSolverVariable;
                    SolverVariable beginNext2 = null;
                    int beginMargin = begin4.getMargin();
                    i2 = firstVisibleWidget2.mListAnchors[offset + 1].getMargin();
                    ConstraintAnchor beginNextAnchor2;
                    if (head != null) {
                        beginNextAnchor2 = null;
                        nextAnchor = head.mListAnchors[offset];
                        beginNext = nextAnchor.mSolverVariable;
                        beginNextAnchor = nextAnchor;
                        beginNextTarget = nextAnchor.mTarget != null ? nextAnchor.mTarget.mSolverVariable : null;
                    } else {
                        beginNextAnchor2 = null;
                        nextAnchor = firstVisibleWidget2.mListAnchors[offset + 1].mTarget;
                        if (nextAnchor != null) {
                            beginNext2 = nextAnchor.mSolverVariable;
                        }
                        beginNextAnchor = nextAnchor;
                        beginNextTarget = firstVisibleWidget2.mListAnchors[offset + 1].mSolverVariable;
                        beginNext = beginNext2;
                    }
                    if (beginNextAnchor != null) {
                        i2 += beginNextAnchor.getMargin();
                    }
                    if (lastVisibleWidget2 != null) {
                        beginMargin += lastVisibleWidget2.mListAnchors[offset + 1].getMargin();
                    }
                    margin2 = 4;
                    if (applyFixedEquality) {
                        margin2 = 6;
                    }
                    int strength3 = margin2;
                    if (nextEnd == null || beginTarget3 == null || beginNext == null || beginNextTarget == null) {
                        next2 = head;
                        constraintWidget2 = lastVisibleWidget2;
                        widget4 = firstVisibleWidget2;
                    } else {
                        next2 = head;
                        constraintWidget2 = lastVisibleWidget2;
                        widget4 = firstVisibleWidget2;
                        system.addCentering(nextEnd, beginTarget3, beginMargin, 0.5f, beginNext, beginNextTarget, i2, strength3);
                    }
                    next = next2;
                }
                lastVisibleWidget2 = widget4.getVisibility() != 8 ? widget4 : constraintWidget2;
                firstVisibleWidget2 = next;
                i2 = 8;
            }
            constraintWidget2 = lastVisibleWidget2;
            widget4 = firstVisibleWidget2;
            ConstraintAnchor begin5 = firstVisibleWidget.mListAnchors[offset];
            ConstraintAnchor beginTarget4 = first.mListAnchors[offset].mTarget;
            begin = lastVisibleWidget.mListAnchors[offset + 1];
            head = last2;
            begin4 = head.mListAnchors[offset + 1].mTarget;
            ConstraintAnchor constraintAnchor2;
            if (beginTarget4 == null) {
                endTarget2 = begin4;
                constraintWidget3 = head;
                end5 = begin;
                constraintAnchor2 = beginTarget4;
                firstVisibleWidget3 = firstVisibleWidget;
                linearSystem = system;
            } else if (firstVisibleWidget != lastVisibleWidget) {
                previousMatchConstraintsWidget = firstVisibleWidget;
                linearSystem = system;
                linearSystem.addEquality(begin5.mSolverVariable, beginTarget4.mSolverVariable, begin5.getMargin(), 5);
                firstVisibleWidget3 = previousMatchConstraintsWidget;
                endTarget2 = begin4;
                constraintWidget3 = head;
                end5 = begin;
                constraintAnchor2 = beginTarget4;
            } else {
                previousMatchConstraintsWidget = firstVisibleWidget;
                linearSystem = system;
                if (begin4 != null) {
                    firstVisibleWidget3 = previousMatchConstraintsWidget;
                    endTarget2 = begin4;
                    constraintWidget3 = head;
                    end5 = begin;
                    system.addCentering(begin5.mSolverVariable, beginTarget4.mSolverVariable, begin5.getMargin(), 0.5f, begin.mSolverVariable, begin4.mSolverVariable, begin.getMargin(), 5);
                } else {
                    firstVisibleWidget3 = previousMatchConstraintsWidget;
                    endTarget2 = begin4;
                    constraintWidget3 = head;
                    end5 = begin;
                    constraintAnchor2 = beginTarget4;
                }
            }
            nextAnchor = endTarget2;
            if (nextAnchor != null) {
                firstVisibleWidget2 = firstVisibleWidget3;
                if (firstVisibleWidget2 != lastVisibleWidget) {
                    linearSystem.addEquality(end5.mSolverVariable, nextAnchor.mSolverVariable, -end5.getMargin(), 5);
                }
            } else {
                firstVisibleWidget2 = firstVisibleWidget3;
            }
            if (isChainSpread) {
                if (!isChainSpreadInside) {
                    ConstraintWidget constraintWidget622 = firstVisibleWidget2;
                    last = constraintWidget3;
                    return;
                }
            }
            if (firstVisibleWidget2 == null) {
                nextAnchor = firstVisibleWidget2.mListAnchors[offset];
                begin2 = lastVisibleWidget.mListAnchors[offset + 1];
                if (nextAnchor.mTarget == null) {
                }
                beginTarget = nextAnchor.mTarget == null ? nextAnchor.mTarget.mSolverVariable : null;
                if (begin2.mTarget == null) {
                }
                last = constraintWidget3;
                if (last != lastVisibleWidget) {
                    ConstraintAnchor realEnd22 = last.mListAnchors[offset + 1];
                    if (realEnd22.mTarget == null) {
                    }
                    nextBegin = realEnd22.mTarget == null ? realEnd22.mTarget.mSolverVariable : null;
                }
                beginTarget2 = nextBegin;
                if (firstVisibleWidget2 == lastVisibleWidget) {
                    nextAnchor = firstVisibleWidget2.mListAnchors[offset];
                    begin2 = firstVisibleWidget2.mListAnchors[offset + 1];
                }
                begin = nextAnchor;
                ConstraintAnchor end422 = begin2;
                if (beginTarget != null) {
                }
            }
            last = constraintWidget3;
            return;
        }
        ConstraintWidget widget5;
        widget3 = firstVisibleWidget;
        constraintWidget4 = firstVisibleWidget;
        applyFixedEquality = chainHead2.mWidgetsMatchCount > 0 && chainHead2.mWidgetsCount == chainHead2.mWidgetsMatchCount;
        firstVisibleWidget2 = widget3;
        lastVisibleWidget2 = constraintWidget4;
        while (firstVisibleWidget2 != null) {
            ConstraintAnchor beginNextAnchor3;
            SolverVariable beginNextTarget2;
            head = firstVisibleWidget2.mNextChainWidget[orientation];
            while (head != null) {
                constraintWidget4 = 8;
                if (head.getVisibility() != 8) {
                    break;
                }
                head = head.mNextChainWidget[orientation];
            }
            constraintWidget4 = 8;
            if (head == null) {
                if (firstVisibleWidget2 != lastVisibleWidget) {
                    ConstraintWidget next3 = head;
                    ConstraintWidget previousVisibleWidget = lastVisibleWidget2;
                    widget5 = firstVisibleWidget2;
                    last2 = last;
                    last = constraintWidget4;
                    lastVisibleWidget2 = widget5.getVisibility() != last ? widget5 : previousVisibleWidget;
                    firstVisibleWidget2 = next3;
                    last = last2;
                }
            }
            begin4 = firstVisibleWidget2.mListAnchors[offset];
            nextEnd = begin4.mSolverVariable;
            end3 = begin4.mTarget != null ? begin4.mTarget.mSolverVariable : null;
            if (lastVisibleWidget2 != firstVisibleWidget2) {
                end3 = lastVisibleWidget2.mListAnchors[offset + 1].mSolverVariable;
            } else if (firstVisibleWidget2 == firstVisibleWidget && lastVisibleWidget2 == firstVisibleWidget2) {
                end3 = first.mListAnchors[offset].mTarget != null ? first.mListAnchors[offset].mTarget.mSolverVariable : null;
            }
            beginTarget2 = end3;
            nextBegin = null;
            int beginMargin2 = begin4.getMargin();
            i = firstVisibleWidget2.mListAnchors[offset + 1].getMargin();
            ConstraintAnchor beginNextAnchor4;
            if (head != null) {
                beginNextAnchor4 = null;
                nextAnchor = head.mListAnchors[offset];
                nextBegin = nextAnchor.mSolverVariable;
                beginNextAnchor3 = nextAnchor;
                beginNextTarget2 = firstVisibleWidget2.mListAnchors[offset + 1].mSolverVariable;
                endTarget = nextBegin;
            } else {
                beginNextAnchor4 = null;
                nextAnchor = last.mListAnchors[offset + 1].mTarget;
                if (nextAnchor != null) {
                    nextBegin = nextAnchor.mSolverVariable;
                }
                ConstraintAnchor beginNextAnchor5 = nextAnchor;
                beginNextTarget2 = firstVisibleWidget2.mListAnchors[offset + 1].mSolverVariable;
                endTarget = nextBegin;
                beginNextAnchor3 = beginNextAnchor5;
            }
            if (beginNextAnchor3 != null) {
                i += beginNextAnchor3.getMargin();
            }
            margin = i;
            if (lastVisibleWidget2 != null) {
                beginMargin2 += lastVisibleWidget2.mListAnchors[offset + 1].getMargin();
            }
            if (nextEnd == null || beginTarget2 == null || endTarget == null || beginNextTarget2 == null) {
                next3 = head;
                previousVisibleWidget = lastVisibleWidget2;
                widget5 = firstVisibleWidget2;
                last2 = last;
                last = 8;
                if (widget5.getVisibility() != last) {
                }
                firstVisibleWidget2 = next3;
                last = last2;
            } else {
                margin2 = beginMargin2;
                if (firstVisibleWidget2 == firstVisibleWidget) {
                    margin2 = firstVisibleWidget.mListAnchors[offset].getMargin();
                }
                int margin1 = margin2;
                margin2 = margin;
                if (firstVisibleWidget2 == lastVisibleWidget) {
                    margin2 = lastVisibleWidget.mListAnchors[offset + 1].getMargin();
                }
                int margin22 = margin2;
                margin2 = 4;
                if (applyFixedEquality) {
                    margin2 = 6;
                }
                last2 = last;
                last = 8;
                next3 = head;
                previousVisibleWidget = lastVisibleWidget2;
                widget5 = firstVisibleWidget2;
                system.addCentering(nextEnd, beginTarget2, margin1, 0.5f, endTarget, beginNextTarget2, margin22, margin2);
                if (widget5.getVisibility() != last) {
                }
                firstVisibleWidget2 = next3;
                last = last2;
            }
        }
        widget5 = firstVisibleWidget2;
        firstVisibleWidget2 = firstVisibleWidget;
        constraintWidget3 = last;
        linearSystem = system;
        if (isChainSpread) {
            if (!isChainSpreadInside) {
                ConstraintWidget constraintWidget6222 = firstVisibleWidget2;
                last = constraintWidget3;
                return;
            }
        }
        if (firstVisibleWidget2 == null) {
            last = constraintWidget3;
            return;
        }
        nextAnchor = firstVisibleWidget2.mListAnchors[offset];
        begin2 = lastVisibleWidget.mListAnchors[offset + 1];
        if (nextAnchor.mTarget == null) {
        }
        beginTarget = nextAnchor.mTarget == null ? nextAnchor.mTarget.mSolverVariable : null;
        if (begin2.mTarget == null) {
        }
        last = constraintWidget3;
        if (last != lastVisibleWidget) {
            ConstraintAnchor realEnd222 = last.mListAnchors[offset + 1];
            if (realEnd222.mTarget == null) {
            }
            nextBegin = realEnd222.mTarget == null ? realEnd222.mTarget.mSolverVariable : null;
        }
        beginTarget2 = nextBegin;
        if (firstVisibleWidget2 == lastVisibleWidget) {
            nextAnchor = firstVisibleWidget2.mListAnchors[offset];
            begin2 = firstVisibleWidget2.mListAnchors[offset + 1];
        }
        begin = nextAnchor;
        ConstraintAnchor end4222 = begin2;
        if (beginTarget != null) {
        }
    }
}
