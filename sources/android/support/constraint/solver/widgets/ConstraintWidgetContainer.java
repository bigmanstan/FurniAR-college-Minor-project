package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.Metrics;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConstraintWidgetContainer extends WidgetContainer {
    private static final boolean DEBUG = false;
    static final boolean DEBUG_GRAPH = false;
    private static final boolean DEBUG_LAYOUT = false;
    private static final int MAX_ITERATIONS = 8;
    private static final boolean USE_SNAPSHOT = true;
    int mDebugSolverPassCount;
    public boolean mGroupsWrapOptimized;
    private boolean mHeightMeasuredTooSmall;
    ChainHead[] mHorizontalChainsArray;
    int mHorizontalChainsSize;
    public boolean mHorizontalWrapOptimized;
    private boolean mIsRtl;
    private int mOptimizationLevel;
    int mPaddingBottom;
    int mPaddingLeft;
    int mPaddingRight;
    int mPaddingTop;
    public boolean mSkipSolver;
    private Snapshot mSnapshot;
    protected LinearSystem mSystem;
    ChainHead[] mVerticalChainsArray;
    int mVerticalChainsSize;
    public boolean mVerticalWrapOptimized;
    public List<ConstraintWidgetGroup> mWidgetGroups;
    private boolean mWidthMeasuredTooSmall;
    public int mWrapFixedHeight;
    public int mWrapFixedWidth;

    public void fillMetrics(Metrics metrics) {
        this.mSystem.fillMetrics(metrics);
    }

    public ConstraintWidgetContainer() {
        this.mIsRtl = false;
        this.mSystem = new LinearSystem();
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
        this.mVerticalChainsArray = new ChainHead[4];
        this.mHorizontalChainsArray = new ChainHead[4];
        this.mWidgetGroups = new ArrayList();
        this.mGroupsWrapOptimized = false;
        this.mHorizontalWrapOptimized = false;
        this.mVerticalWrapOptimized = false;
        this.mWrapFixedWidth = 0;
        this.mWrapFixedHeight = 0;
        this.mOptimizationLevel = 7;
        this.mSkipSolver = false;
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        this.mDebugSolverPassCount = 0;
    }

    public ConstraintWidgetContainer(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.mIsRtl = false;
        this.mSystem = new LinearSystem();
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
        this.mVerticalChainsArray = new ChainHead[4];
        this.mHorizontalChainsArray = new ChainHead[4];
        this.mWidgetGroups = new ArrayList();
        this.mGroupsWrapOptimized = false;
        this.mHorizontalWrapOptimized = false;
        this.mVerticalWrapOptimized = false;
        this.mWrapFixedWidth = 0;
        this.mWrapFixedHeight = 0;
        this.mOptimizationLevel = 7;
        this.mSkipSolver = false;
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        this.mDebugSolverPassCount = 0;
    }

    public ConstraintWidgetContainer(int width, int height) {
        super(width, height);
        this.mIsRtl = false;
        this.mSystem = new LinearSystem();
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
        this.mVerticalChainsArray = new ChainHead[4];
        this.mHorizontalChainsArray = new ChainHead[4];
        this.mWidgetGroups = new ArrayList();
        this.mGroupsWrapOptimized = false;
        this.mHorizontalWrapOptimized = false;
        this.mVerticalWrapOptimized = false;
        this.mWrapFixedWidth = 0;
        this.mWrapFixedHeight = 0;
        this.mOptimizationLevel = 7;
        this.mSkipSolver = false;
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        this.mDebugSolverPassCount = 0;
    }

    public void setOptimizationLevel(int value) {
        this.mOptimizationLevel = value;
    }

    public int getOptimizationLevel() {
        return this.mOptimizationLevel;
    }

    public boolean optimizeFor(int feature) {
        return (this.mOptimizationLevel & feature) == feature ? USE_SNAPSHOT : false;
    }

    public String getType() {
        return "ConstraintLayout";
    }

    public void reset() {
        this.mSystem.reset();
        this.mPaddingLeft = 0;
        this.mPaddingRight = 0;
        this.mPaddingTop = 0;
        this.mPaddingBottom = 0;
        this.mWidgetGroups.clear();
        this.mSkipSolver = false;
        super.reset();
    }

    public boolean isWidthMeasuredTooSmall() {
        return this.mWidthMeasuredTooSmall;
    }

    public boolean isHeightMeasuredTooSmall() {
        return this.mHeightMeasuredTooSmall;
    }

    public boolean addChildrenToSolver(LinearSystem system) {
        addToSolver(system);
        int count = this.mChildren.size();
        for (int i = 0; i < count; i++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            if (widget instanceof ConstraintWidgetContainer) {
                DimensionBehaviour horizontalBehaviour = widget.mListDimensionBehaviors[0];
                DimensionBehaviour verticalBehaviour = widget.mListDimensionBehaviors[1];
                if (horizontalBehaviour == DimensionBehaviour.WRAP_CONTENT) {
                    widget.setHorizontalDimensionBehaviour(DimensionBehaviour.FIXED);
                }
                if (verticalBehaviour == DimensionBehaviour.WRAP_CONTENT) {
                    widget.setVerticalDimensionBehaviour(DimensionBehaviour.FIXED);
                }
                widget.addToSolver(system);
                if (horizontalBehaviour == DimensionBehaviour.WRAP_CONTENT) {
                    widget.setHorizontalDimensionBehaviour(horizontalBehaviour);
                }
                if (verticalBehaviour == DimensionBehaviour.WRAP_CONTENT) {
                    widget.setVerticalDimensionBehaviour(verticalBehaviour);
                }
            } else {
                Optimizer.checkMatchParent(this, system, widget);
                widget.addToSolver(system);
            }
        }
        if (this.mHorizontalChainsSize > 0) {
            Chain.applyChainConstraints(this, system, 0);
        }
        if (this.mVerticalChainsSize > 0) {
            Chain.applyChainConstraints(this, system, 1);
        }
        return USE_SNAPSHOT;
    }

    public void updateChildrenFromSolver(LinearSystem system, boolean[] flags) {
        flags[2] = false;
        updateFromSolver(system);
        int count = this.mChildren.size();
        for (int i = 0; i < count; i++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            widget.updateFromSolver(system);
            if (widget.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT && widget.getWidth() < widget.getWrapWidth()) {
                flags[2] = USE_SNAPSHOT;
            }
            if (widget.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT && widget.getHeight() < widget.getWrapHeight()) {
                flags[2] = USE_SNAPSHOT;
            }
        }
    }

    public void setPadding(int left, int top, int right, int bottom) {
        this.mPaddingLeft = left;
        this.mPaddingTop = top;
        this.mPaddingRight = right;
        this.mPaddingBottom = bottom;
    }

    public void setRtl(boolean isRtl) {
        this.mIsRtl = isRtl;
    }

    public boolean isRtl() {
        return this.mIsRtl;
    }

    public void analyze(int optimizationLevel) {
        super.analyze(optimizationLevel);
        int count = this.mChildren.size();
        for (int i = 0; i < count; i++) {
            ((ConstraintWidget) this.mChildren.get(i)).analyze(optimizationLevel);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void layout() {
        /*
        r27 = this;
        r1 = r27;
        r2 = r1.mX;
        r3 = r1.mY;
        r0 = r27.getWidth();
        r4 = 0;
        r5 = java.lang.Math.max(r4, r0);
        r0 = r27.getHeight();
        r6 = java.lang.Math.max(r4, r0);
        r1.mWidthMeasuredTooSmall = r4;
        r1.mHeightMeasuredTooSmall = r4;
        r0 = r1.mParent;
        if (r0 == 0) goto L_0x0046;
    L_0x001f:
        r0 = r1.mSnapshot;
        if (r0 != 0) goto L_0x002a;
    L_0x0023:
        r0 = new android.support.constraint.solver.widgets.Snapshot;
        r0.<init>(r1);
        r1.mSnapshot = r0;
    L_0x002a:
        r0 = r1.mSnapshot;
        r0.updateFrom(r1);
        r0 = r1.mPaddingLeft;
        r1.setX(r0);
        r0 = r1.mPaddingTop;
        r1.setY(r0);
        r27.resetAnchors();
        r0 = r1.mSystem;
        r0 = r0.getCache();
        r1.resetSolverVariables(r0);
        goto L_0x004a;
    L_0x0046:
        r1.mX = r4;
        r1.mY = r4;
    L_0x004a:
        r0 = r1.mOptimizationLevel;
        r7 = 32;
        r8 = 8;
        r9 = 1;
        if (r0 == 0) goto L_0x006a;
    L_0x0053:
        r0 = r1.optimizeFor(r8);
        if (r0 != 0) goto L_0x005c;
    L_0x0059:
        r27.optimizeReset();
    L_0x005c:
        r0 = r1.optimizeFor(r7);
        if (r0 != 0) goto L_0x0065;
    L_0x0062:
        r27.optimize();
    L_0x0065:
        r0 = r1.mSystem;
        r0.graphOptimizer = r9;
        goto L_0x006e;
    L_0x006a:
        r0 = r1.mSystem;
        r0.graphOptimizer = r4;
    L_0x006e:
        r0 = 0;
        r10 = r1.mListDimensionBehaviors;
        r10 = r10[r9];
        r11 = r1.mListDimensionBehaviors;
        r11 = r11[r4];
        r27.resetChains();
        r12 = r1.mWidgetGroups;
        r12 = r12.size();
        if (r12 != 0) goto L_0x0093;
    L_0x0082:
        r12 = r1.mWidgetGroups;
        r12.clear();
        r12 = r1.mWidgetGroups;
        r13 = new android.support.constraint.solver.widgets.ConstraintWidgetGroup;
        r14 = r1.mChildren;
        r13.<init>(r14);
        r12.add(r4, r13);
    L_0x0093:
        r12 = 0;
        r13 = r1.mWidgetGroups;
        r13 = r13.size();
        r14 = r1.mChildren;
        r15 = r27.getHorizontalDimensionBehaviour();
        r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (r15 == r8) goto L_0x00af;
    L_0x00a4:
        r8 = r27.getVerticalDimensionBehaviour();
        r15 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (r8 != r15) goto L_0x00ad;
    L_0x00ac:
        goto L_0x00af;
    L_0x00ad:
        r8 = r4;
        goto L_0x00b0;
    L_0x00af:
        r8 = r9;
    L_0x00b0:
        r15 = r12;
        r12 = r0;
        r0 = r4;
    L_0x00b3:
        r17 = r0;
        r9 = r17;
        if (r9 >= r13) goto L_0x033a;
    L_0x00b9:
        r0 = r1.mSkipSolver;
        if (r0 != 0) goto L_0x033a;
    L_0x00bd:
        r0 = r1.mWidgetGroups;
        r0 = r0.get(r9);
        r0 = (android.support.constraint.solver.widgets.ConstraintWidgetGroup) r0;
        r0 = r0.mSkipSolver;
        if (r0 == 0) goto L_0x00ce;
        r22 = r13;
        goto L_0x0330;
    L_0x00ce:
        r0 = r1.optimizeFor(r7);
        if (r0 == 0) goto L_0x0103;
    L_0x00d4:
        r0 = r27.getHorizontalDimensionBehaviour();
        r7 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED;
        if (r0 != r7) goto L_0x00f5;
    L_0x00dc:
        r0 = r27.getVerticalDimensionBehaviour();
        r7 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED;
        if (r0 != r7) goto L_0x00f5;
    L_0x00e4:
        r0 = r1.mWidgetGroups;
        r0 = r0.get(r9);
        r0 = (android.support.constraint.solver.widgets.ConstraintWidgetGroup) r0;
        r0 = r0.getWidgetsToSolve();
        r0 = (java.util.ArrayList) r0;
        r1.mChildren = r0;
        goto L_0x0103;
    L_0x00f5:
        r0 = r1.mWidgetGroups;
        r0 = r0.get(r9);
        r0 = (android.support.constraint.solver.widgets.ConstraintWidgetGroup) r0;
        r0 = r0.mConstrainedGroup;
        r0 = (java.util.ArrayList) r0;
        r1.mChildren = r0;
    L_0x0103:
        r27.resetChains();
        r0 = r1.mChildren;
        r7 = r0.size();
        r0 = 0;
        r15 = r4;
    L_0x010e:
        if (r15 >= r7) goto L_0x012a;
    L_0x0110:
        r4 = r1.mChildren;
        r4 = r4.get(r15);
        r4 = (android.support.constraint.solver.widgets.ConstraintWidget) r4;
        r18 = r0;
        r0 = r4 instanceof android.support.constraint.solver.widgets.WidgetContainer;
        if (r0 == 0) goto L_0x0124;
    L_0x011e:
        r0 = r4;
        r0 = (android.support.constraint.solver.widgets.WidgetContainer) r0;
        r0.layout();
    L_0x0124:
        r15 = r15 + 1;
        r0 = r18;
        r4 = 0;
        goto L_0x010e;
    L_0x012a:
        r18 = r0;
        r0 = 1;
    L_0x012d:
        r4 = r0;
        if (r4 == 0) goto L_0x031b;
    L_0x0130:
        r15 = r18 + 1;
        r0 = r1.mSystem;	 Catch:{ Exception -> 0x017d }
        r0.reset();	 Catch:{ Exception -> 0x017d }
        r27.resetChains();	 Catch:{ Exception -> 0x017d }
        r0 = r1.mSystem;	 Catch:{ Exception -> 0x017d }
        r1.createObjectVariables(r0);	 Catch:{ Exception -> 0x017d }
        r0 = 0;
    L_0x0140:
        if (r0 >= r7) goto L_0x0160;
    L_0x0142:
        r19 = r4;
        r4 = r1.mChildren;	 Catch:{ Exception -> 0x015a }
        r4 = r4.get(r0);	 Catch:{ Exception -> 0x015a }
        r4 = (android.support.constraint.solver.widgets.ConstraintWidget) r4;	 Catch:{ Exception -> 0x015a }
        r20 = r12;
        r12 = r1.mSystem;	 Catch:{ Exception -> 0x0179 }
        r4.createObjectVariables(r12);	 Catch:{ Exception -> 0x0179 }
        r0 = r0 + 1;
        r4 = r19;
        r12 = r20;
        goto L_0x0140;
    L_0x015a:
        r0 = move-exception;
        r20 = r12;
        r4 = r19;
        goto L_0x0182;
    L_0x0160:
        r19 = r4;
        r20 = r12;
        r0 = r1.mSystem;	 Catch:{ Exception -> 0x0179 }
        r0 = r1.addChildrenToSolver(r0);	 Catch:{ Exception -> 0x0179 }
        r4 = r0;
        if (r4 == 0) goto L_0x0175;
    L_0x016d:
        r0 = r1.mSystem;	 Catch:{ Exception -> 0x0173 }
        r0.minimize();	 Catch:{ Exception -> 0x0173 }
        goto L_0x0175;
    L_0x0173:
        r0 = move-exception;
        goto L_0x0182;
        r22 = r13;
        goto L_0x01a1;
    L_0x0179:
        r0 = move-exception;
        r4 = r19;
        goto L_0x0182;
    L_0x017d:
        r0 = move-exception;
        r19 = r4;
        r20 = r12;
    L_0x0182:
        r0.printStackTrace();
        r12 = java.lang.System.out;
        r21 = r4;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r22 = r13;
        r13 = "EXCEPTION : ";
        r4.append(r13);
        r4.append(r0);
        r4 = r4.toString();
        r12.println(r4);
        r4 = r21;
    L_0x01a1:
        if (r4 == 0) goto L_0x01ad;
    L_0x01a3:
        r12 = r1.mSystem;
        r13 = android.support.constraint.solver.widgets.Optimizer.flags;
        r1.updateChildrenFromSolver(r12, r13);
        r23 = r4;
        goto L_0x01fd;
    L_0x01ad:
        r12 = r1.mSystem;
        r1.updateFromSolver(r12);
        r12 = 0;
    L_0x01b3:
        if (r12 >= r7) goto L_0x01fb;
    L_0x01b5:
        r13 = r1.mChildren;
        r13 = r13.get(r12);
        r13 = (android.support.constraint.solver.widgets.ConstraintWidget) r13;
        r0 = r13.mListDimensionBehaviors;
        r17 = 0;
        r0 = r0[r17];
        r23 = r4;
        r4 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        if (r0 != r4) goto L_0x01db;
    L_0x01c9:
        r0 = r13.getWidth();
        r4 = r13.getWrapWidth();
        if (r0 >= r4) goto L_0x01db;
    L_0x01d3:
        r0 = android.support.constraint.solver.widgets.Optimizer.flags;
        r4 = 1;
        r17 = 2;
        r0[r17] = r4;
        goto L_0x01fd;
    L_0x01db:
        r4 = 1;
        r0 = r13.mListDimensionBehaviors;
        r0 = r0[r4];
        r4 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        if (r0 != r4) goto L_0x01f6;
    L_0x01e4:
        r0 = r13.getHeight();
        r4 = r13.getWrapHeight();
        if (r0 >= r4) goto L_0x01f6;
    L_0x01ee:
        r0 = android.support.constraint.solver.widgets.Optimizer.flags;
        r4 = 1;
        r17 = 2;
        r0[r17] = r4;
        goto L_0x01fd;
    L_0x01f6:
        r12 = r12 + 1;
        r4 = r23;
        goto L_0x01b3;
    L_0x01fb:
        r23 = r4;
    L_0x01fd:
        r0 = 0;
        if (r8 == 0) goto L_0x0282;
    L_0x0200:
        r4 = 8;
        if (r15 >= r4) goto L_0x0282;
    L_0x0204:
        r12 = android.support.constraint.solver.widgets.Optimizer.flags;
        r13 = 2;
        r12 = r12[r13];
        if (r12 == 0) goto L_0x0282;
    L_0x020b:
        r12 = 0;
        r13 = 0;
        r4 = r13;
        r13 = r12;
        r12 = 0;
    L_0x0210:
        if (r12 >= r7) goto L_0x023d;
    L_0x0212:
        r24 = r0;
        r0 = r1.mChildren;
        r0 = r0.get(r12);
        r0 = (android.support.constraint.solver.widgets.ConstraintWidget) r0;
        r25 = r7;
        r7 = r0.mX;
        r16 = r0.getWidth();
        r7 = r7 + r16;
        r13 = java.lang.Math.max(r13, r7);
        r7 = r0.mY;
        r16 = r0.getHeight();
        r7 = r7 + r16;
        r4 = java.lang.Math.max(r4, r7);
        r12 = r12 + 1;
        r0 = r24;
        r7 = r25;
        goto L_0x0210;
    L_0x023d:
        r24 = r0;
        r25 = r7;
        r0 = r1.mMinWidth;
        r0 = java.lang.Math.max(r0, r13);
        r7 = r1.mMinHeight;
        r4 = java.lang.Math.max(r7, r4);
        r7 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (r11 != r7) goto L_0x0264;
    L_0x0251:
        r7 = r27.getWidth();
        if (r7 >= r0) goto L_0x0264;
    L_0x0257:
        r1.setWidth(r0);
        r7 = r1.mListDimensionBehaviors;
        r12 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        r13 = 0;
        r7[r13] = r12;
        r12 = 1;
        r7 = 1;
        goto L_0x0268;
    L_0x0264:
        r12 = r20;
        r7 = r24;
    L_0x0268:
        r13 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (r10 != r13) goto L_0x0280;
    L_0x026c:
        r13 = r27.getHeight();
        if (r13 >= r4) goto L_0x0280;
    L_0x0272:
        r1.setHeight(r4);
        r13 = r1.mListDimensionBehaviors;
        r16 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        r17 = 1;
        r13[r17] = r16;
        r12 = 1;
        r0 = 1;
        goto L_0x028a;
    L_0x0280:
        r0 = r7;
        goto L_0x028a;
    L_0x0282:
        r24 = r0;
        r25 = r7;
        r12 = r20;
        r0 = r24;
    L_0x028a:
        r4 = r1.mMinWidth;
        r7 = r27.getWidth();
        r4 = java.lang.Math.max(r4, r7);
        r7 = r27.getWidth();
        if (r4 <= r7) goto L_0x02a7;
    L_0x029a:
        r1.setWidth(r4);
        r7 = r1.mListDimensionBehaviors;
        r13 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED;
        r16 = 0;
        r7[r16] = r13;
        r12 = 1;
        r0 = 1;
    L_0x02a7:
        r7 = r1.mMinHeight;
        r13 = r27.getHeight();
        r7 = java.lang.Math.max(r7, r13);
        r13 = r27.getHeight();
        if (r7 <= r13) goto L_0x02c4;
    L_0x02b7:
        r1.setHeight(r7);
        r13 = r1.mListDimensionBehaviors;
        r16 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED;
        r17 = 1;
        r13[r17] = r16;
        r12 = 1;
        r0 = 1;
    L_0x02c4:
        if (r12 != 0) goto L_0x0311;
    L_0x02c6:
        r13 = r1.mListDimensionBehaviors;
        r16 = 0;
        r13 = r13[r16];
        r26 = r0;
        r0 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (r13 != r0) goto L_0x02ec;
    L_0x02d2:
        if (r5 <= 0) goto L_0x02ec;
    L_0x02d4:
        r0 = r27.getWidth();
        if (r0 <= r5) goto L_0x02ec;
    L_0x02da:
        r13 = 1;
        r1.mWidthMeasuredTooSmall = r13;
        r12 = 1;
        r0 = r1.mListDimensionBehaviors;
        r13 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED;
        r16 = 0;
        r0[r16] = r13;
        r1.setWidth(r5);
        r0 = 1;
        r26 = r0;
    L_0x02ec:
        r0 = r1.mListDimensionBehaviors;
        r13 = 1;
        r0 = r0[r13];
        r13 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (r0 != r13) goto L_0x030e;
    L_0x02f5:
        if (r6 <= 0) goto L_0x030e;
    L_0x02f7:
        r0 = r27.getHeight();
        if (r0 <= r6) goto L_0x030e;
    L_0x02fd:
        r13 = 1;
        r1.mHeightMeasuredTooSmall = r13;
        r0 = 1;
        r12 = r1.mListDimensionBehaviors;
        r16 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED;
        r12[r13] = r16;
        r1.setHeight(r6);
        r4 = 1;
        r12 = r0;
        r0 = r4;
        goto L_0x0313;
    L_0x030e:
        r0 = r26;
        goto L_0x0313;
    L_0x0311:
        r26 = r0;
    L_0x0313:
        r18 = r15;
        r13 = r22;
        r7 = r25;
        goto L_0x012d;
    L_0x031b:
        r19 = r4;
        r25 = r7;
        r20 = r12;
        r22 = r13;
        r0 = r1.mWidgetGroups;
        r0 = r0.get(r9);
        r0 = (android.support.constraint.solver.widgets.ConstraintWidgetGroup) r0;
        r0.updateUnresolvedWidgets();
        r15 = r18;
    L_0x0330:
        r0 = r9 + 1;
        r13 = r22;
        r4 = 0;
        r7 = 32;
        r9 = 1;
        goto L_0x00b3;
    L_0x033a:
        r22 = r13;
        r0 = r14;
        r0 = (java.util.ArrayList) r0;
        r1.mChildren = r0;
        r0 = r1.mParent;
        if (r0 == 0) goto L_0x0371;
    L_0x0345:
        r0 = r1.mMinWidth;
        r4 = r27.getWidth();
        r0 = java.lang.Math.max(r0, r4);
        r4 = r1.mMinHeight;
        r7 = r27.getHeight();
        r4 = java.lang.Math.max(r4, r7);
        r7 = r1.mSnapshot;
        r7.applyTo(r1);
        r7 = r1.mPaddingLeft;
        r7 = r7 + r0;
        r9 = r1.mPaddingRight;
        r7 = r7 + r9;
        r1.setWidth(r7);
        r7 = r1.mPaddingTop;
        r7 = r7 + r4;
        r9 = r1.mPaddingBottom;
        r7 = r7 + r9;
        r1.setHeight(r7);
        goto L_0x0375;
    L_0x0371:
        r1.mX = r2;
        r1.mY = r3;
    L_0x0375:
        if (r12 == 0) goto L_0x0381;
    L_0x0377:
        r0 = r1.mListDimensionBehaviors;
        r4 = 0;
        r0[r4] = r11;
        r0 = r1.mListDimensionBehaviors;
        r4 = 1;
        r0[r4] = r10;
    L_0x0381:
        r0 = r1.mSystem;
        r0 = r0.getCache();
        r1.resetSolverVariables(r0);
        r0 = r27.getRootConstraintContainer();
        if (r1 != r0) goto L_0x0393;
    L_0x0390:
        r27.updateDrawPosition();
    L_0x0393:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintWidgetContainer.layout():void");
    }

    public void preOptimize() {
        optimizeReset();
        analyze(this.mOptimizationLevel);
    }

    public void solveGraph() {
        ResolutionAnchor leftNode = getAnchor(Type.LEFT).getResolutionNode();
        ResolutionAnchor topNode = getAnchor(Type.TOP).getResolutionNode();
        leftNode.resolve(null, 0.0f);
        topNode.resolve(null, 0.0f);
    }

    public void resetGraph() {
        ResolutionAnchor leftNode = getAnchor(Type.LEFT).getResolutionNode();
        ResolutionAnchor topNode = getAnchor(Type.TOP).getResolutionNode();
        leftNode.invalidateAnchors();
        topNode.invalidateAnchors();
        leftNode.resolve(null, 0.0f);
        topNode.resolve(null, 0.0f);
    }

    public void optimizeForDimensions(int width, int height) {
        if (!(this.mListDimensionBehaviors[0] == DimensionBehaviour.WRAP_CONTENT || this.mResolutionWidth == null)) {
            this.mResolutionWidth.resolve(width);
        }
        if (this.mListDimensionBehaviors[1] != DimensionBehaviour.WRAP_CONTENT && this.mResolutionHeight != null) {
            this.mResolutionHeight.resolve(height);
        }
    }

    public void optimizeReset() {
        int count = this.mChildren.size();
        resetResolutionNodes();
        for (int i = 0; i < count; i++) {
            ((ConstraintWidget) this.mChildren.get(i)).resetResolutionNodes();
        }
    }

    public void optimize() {
        if (!optimizeFor(8)) {
            analyze(this.mOptimizationLevel);
        }
        solveGraph();
    }

    public boolean handlesInternalConstraints() {
        return false;
    }

    public ArrayList<Guideline> getVerticalGuidelines() {
        ArrayList<Guideline> guidelines = new ArrayList();
        int mChildrenSize = this.mChildren.size();
        for (int i = 0; i < mChildrenSize; i++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            if (widget instanceof Guideline) {
                Guideline guideline = (Guideline) widget;
                if (guideline.getOrientation() == 1) {
                    guidelines.add(guideline);
                }
            }
        }
        return guidelines;
    }

    public ArrayList<Guideline> getHorizontalGuidelines() {
        ArrayList<Guideline> guidelines = new ArrayList();
        int mChildrenSize = this.mChildren.size();
        for (int i = 0; i < mChildrenSize; i++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            if (widget instanceof Guideline) {
                Guideline guideline = (Guideline) widget;
                if (guideline.getOrientation() == 0) {
                    guidelines.add(guideline);
                }
            }
        }
        return guidelines;
    }

    public LinearSystem getSystem() {
        return this.mSystem;
    }

    private void resetChains() {
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
    }

    void addChain(ConstraintWidget constraintWidget, int type) {
        ConstraintWidget widget = constraintWidget;
        if (type == 0) {
            addHorizontalChain(widget);
        } else if (type == 1) {
            addVerticalChain(widget);
        }
    }

    private void addHorizontalChain(ConstraintWidget widget) {
        if (this.mHorizontalChainsSize + 1 >= this.mHorizontalChainsArray.length) {
            this.mHorizontalChainsArray = (ChainHead[]) Arrays.copyOf(this.mHorizontalChainsArray, this.mHorizontalChainsArray.length * 2);
        }
        this.mHorizontalChainsArray[this.mHorizontalChainsSize] = new ChainHead(widget, 0, isRtl());
        this.mHorizontalChainsSize++;
    }

    private void addVerticalChain(ConstraintWidget widget) {
        if (this.mVerticalChainsSize + 1 >= this.mVerticalChainsArray.length) {
            this.mVerticalChainsArray = (ChainHead[]) Arrays.copyOf(this.mVerticalChainsArray, this.mVerticalChainsArray.length * 2);
        }
        this.mVerticalChainsArray[this.mVerticalChainsSize] = new ChainHead(widget, 1, isRtl());
        this.mVerticalChainsSize++;
    }

    public List<ConstraintWidgetGroup> getWidgetGroups() {
        return this.mWidgetGroups;
    }
}
