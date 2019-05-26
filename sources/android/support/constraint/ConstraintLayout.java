package android.support.constraint;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.support.constraint.solver.Metrics;
import android.support.constraint.solver.widgets.Analyzer;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.constraint.solver.widgets.ConstraintAnchor.Strength;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import android.support.constraint.solver.widgets.ConstraintWidgetContainer;
import android.support.constraint.solver.widgets.Guideline;
import android.support.constraint.solver.widgets.ResolutionAnchor;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import java.util.ArrayList;
import java.util.HashMap;

public class ConstraintLayout extends ViewGroup {
    static final boolean ALLOWS_EMBEDDED = false;
    private static final boolean CACHE_MEASURED_DIMENSION = false;
    private static final boolean DEBUG = false;
    public static final int DESIGN_INFO_ID = 0;
    private static final String TAG = "ConstraintLayout";
    private static final boolean USE_CONSTRAINTS_HELPER = true;
    public static final String VERSION = "ConstraintLayout-1.1.3";
    SparseArray<View> mChildrenByIds = new SparseArray();
    private ArrayList<ConstraintHelper> mConstraintHelpers = new ArrayList(4);
    private ConstraintSet mConstraintSet = null;
    private int mConstraintSetId = -1;
    private HashMap<String, Integer> mDesignIds = new HashMap();
    private boolean mDirtyHierarchy = USE_CONSTRAINTS_HELPER;
    private int mLastMeasureHeight = -1;
    int mLastMeasureHeightMode = 0;
    int mLastMeasureHeightSize = -1;
    private int mLastMeasureWidth = -1;
    int mLastMeasureWidthMode = 0;
    int mLastMeasureWidthSize = -1;
    ConstraintWidgetContainer mLayoutWidget = new ConstraintWidgetContainer();
    private int mMaxHeight = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    private int mMaxWidth = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    private Metrics mMetrics;
    private int mMinHeight = 0;
    private int mMinWidth = 0;
    private int mOptimizationLevel = 7;
    private final ArrayList<ConstraintWidget> mVariableDimensionsWidgets = new ArrayList(100);

    public static class LayoutParams extends MarginLayoutParams {
        public static final int BASELINE = 5;
        public static final int BOTTOM = 4;
        public static final int CHAIN_PACKED = 2;
        public static final int CHAIN_SPREAD = 0;
        public static final int CHAIN_SPREAD_INSIDE = 1;
        public static final int END = 7;
        public static final int HORIZONTAL = 0;
        public static final int LEFT = 1;
        public static final int MATCH_CONSTRAINT = 0;
        public static final int MATCH_CONSTRAINT_PERCENT = 2;
        public static final int MATCH_CONSTRAINT_SPREAD = 0;
        public static final int MATCH_CONSTRAINT_WRAP = 1;
        public static final int PARENT_ID = 0;
        public static final int RIGHT = 2;
        public static final int START = 6;
        public static final int TOP = 3;
        public static final int UNSET = -1;
        public static final int VERTICAL = 1;
        public int baselineToBaseline;
        public int bottomToBottom;
        public int bottomToTop;
        public float circleAngle;
        public int circleConstraint;
        public int circleRadius;
        public boolean constrainedHeight;
        public boolean constrainedWidth;
        public String dimensionRatio;
        int dimensionRatioSide;
        float dimensionRatioValue;
        public int editorAbsoluteX;
        public int editorAbsoluteY;
        public int endToEnd;
        public int endToStart;
        public int goneBottomMargin;
        public int goneEndMargin;
        public int goneLeftMargin;
        public int goneRightMargin;
        public int goneStartMargin;
        public int goneTopMargin;
        public int guideBegin;
        public int guideEnd;
        public float guidePercent;
        public boolean helped;
        public float horizontalBias;
        public int horizontalChainStyle;
        boolean horizontalDimensionFixed;
        public float horizontalWeight;
        boolean isGuideline;
        boolean isHelper;
        boolean isInPlaceholder;
        public int leftToLeft;
        public int leftToRight;
        public int matchConstraintDefaultHeight;
        public int matchConstraintDefaultWidth;
        public int matchConstraintMaxHeight;
        public int matchConstraintMaxWidth;
        public int matchConstraintMinHeight;
        public int matchConstraintMinWidth;
        public float matchConstraintPercentHeight;
        public float matchConstraintPercentWidth;
        boolean needsBaseline;
        public int orientation;
        int resolveGoneLeftMargin;
        int resolveGoneRightMargin;
        int resolvedGuideBegin;
        int resolvedGuideEnd;
        float resolvedGuidePercent;
        float resolvedHorizontalBias;
        int resolvedLeftToLeft;
        int resolvedLeftToRight;
        int resolvedRightToLeft;
        int resolvedRightToRight;
        public int rightToLeft;
        public int rightToRight;
        public int startToEnd;
        public int startToStart;
        public int topToBottom;
        public int topToTop;
        public float verticalBias;
        public int verticalChainStyle;
        boolean verticalDimensionFixed;
        public float verticalWeight;
        ConstraintWidget widget;

        private static class Table {
            public static final int ANDROID_ORIENTATION = 1;
            public static final int LAYOUT_CONSTRAINED_HEIGHT = 28;
            public static final int LAYOUT_CONSTRAINED_WIDTH = 27;
            public static final int LAYOUT_CONSTRAINT_BASELINE_CREATOR = 43;
            public static final int LAYOUT_CONSTRAINT_BASELINE_TO_BASELINE_OF = 16;
            public static final int LAYOUT_CONSTRAINT_BOTTOM_CREATOR = 42;
            public static final int LAYOUT_CONSTRAINT_BOTTOM_TO_BOTTOM_OF = 15;
            public static final int LAYOUT_CONSTRAINT_BOTTOM_TO_TOP_OF = 14;
            public static final int LAYOUT_CONSTRAINT_CIRCLE = 2;
            public static final int LAYOUT_CONSTRAINT_CIRCLE_ANGLE = 4;
            public static final int LAYOUT_CONSTRAINT_CIRCLE_RADIUS = 3;
            public static final int LAYOUT_CONSTRAINT_DIMENSION_RATIO = 44;
            public static final int LAYOUT_CONSTRAINT_END_TO_END_OF = 20;
            public static final int LAYOUT_CONSTRAINT_END_TO_START_OF = 19;
            public static final int LAYOUT_CONSTRAINT_GUIDE_BEGIN = 5;
            public static final int LAYOUT_CONSTRAINT_GUIDE_END = 6;
            public static final int LAYOUT_CONSTRAINT_GUIDE_PERCENT = 7;
            public static final int LAYOUT_CONSTRAINT_HEIGHT_DEFAULT = 32;
            public static final int LAYOUT_CONSTRAINT_HEIGHT_MAX = 37;
            public static final int LAYOUT_CONSTRAINT_HEIGHT_MIN = 36;
            public static final int LAYOUT_CONSTRAINT_HEIGHT_PERCENT = 38;
            public static final int LAYOUT_CONSTRAINT_HORIZONTAL_BIAS = 29;
            public static final int LAYOUT_CONSTRAINT_HORIZONTAL_CHAINSTYLE = 47;
            public static final int LAYOUT_CONSTRAINT_HORIZONTAL_WEIGHT = 45;
            public static final int LAYOUT_CONSTRAINT_LEFT_CREATOR = 39;
            public static final int LAYOUT_CONSTRAINT_LEFT_TO_LEFT_OF = 8;
            public static final int LAYOUT_CONSTRAINT_LEFT_TO_RIGHT_OF = 9;
            public static final int LAYOUT_CONSTRAINT_RIGHT_CREATOR = 41;
            public static final int LAYOUT_CONSTRAINT_RIGHT_TO_LEFT_OF = 10;
            public static final int LAYOUT_CONSTRAINT_RIGHT_TO_RIGHT_OF = 11;
            public static final int LAYOUT_CONSTRAINT_START_TO_END_OF = 17;
            public static final int LAYOUT_CONSTRAINT_START_TO_START_OF = 18;
            public static final int LAYOUT_CONSTRAINT_TOP_CREATOR = 40;
            public static final int LAYOUT_CONSTRAINT_TOP_TO_BOTTOM_OF = 13;
            public static final int LAYOUT_CONSTRAINT_TOP_TO_TOP_OF = 12;
            public static final int LAYOUT_CONSTRAINT_VERTICAL_BIAS = 30;
            public static final int LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE = 48;
            public static final int LAYOUT_CONSTRAINT_VERTICAL_WEIGHT = 46;
            public static final int LAYOUT_CONSTRAINT_WIDTH_DEFAULT = 31;
            public static final int LAYOUT_CONSTRAINT_WIDTH_MAX = 34;
            public static final int LAYOUT_CONSTRAINT_WIDTH_MIN = 33;
            public static final int LAYOUT_CONSTRAINT_WIDTH_PERCENT = 35;
            public static final int LAYOUT_EDITOR_ABSOLUTEX = 49;
            public static final int LAYOUT_EDITOR_ABSOLUTEY = 50;
            public static final int LAYOUT_GONE_MARGIN_BOTTOM = 24;
            public static final int LAYOUT_GONE_MARGIN_END = 26;
            public static final int LAYOUT_GONE_MARGIN_LEFT = 21;
            public static final int LAYOUT_GONE_MARGIN_RIGHT = 23;
            public static final int LAYOUT_GONE_MARGIN_START = 25;
            public static final int LAYOUT_GONE_MARGIN_TOP = 22;
            public static final int UNUSED = 0;
            public static final SparseIntArray map = new SparseIntArray();

            private Table() {
            }

            static {
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf, 8);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toRightOf, 9);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintRight_toLeftOf, 10);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintRight_toRightOf, 11);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintTop_toTopOf, 12);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintTop_toBottomOf, 13);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toTopOf, 14);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf, 15);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf, 16);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintCircle, 2);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintCircleRadius, 3);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintCircleAngle, 4);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_editor_absoluteX, 49);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_editor_absoluteY, 50);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintGuide_begin, 5);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintGuide_end, 6);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintGuide_percent, 7);
                map.append(C0005R.styleable.ConstraintLayout_Layout_android_orientation, 1);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintStart_toEndOf, 17);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintStart_toStartOf, 18);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toStartOf, 19);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toEndOf, 20);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_goneMarginLeft, 21);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_goneMarginTop, 22);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_goneMarginRight, 23);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_goneMarginBottom, 24);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_goneMarginStart, 25);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_goneMarginEnd, 26);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_bias, 29);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintVertical_bias, 30);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintDimensionRatio, 44);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_weight, 45);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintVertical_weight, 46);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle, 47);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintVertical_chainStyle, 48);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constrainedWidth, 27);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constrainedHeight, 28);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintWidth_default, 31);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintHeight_default, 32);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintWidth_min, 33);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintWidth_max, 34);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintWidth_percent, 35);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintHeight_min, 36);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintHeight_max, 37);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintHeight_percent, 38);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintLeft_creator, 39);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintTop_creator, 40);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintRight_creator, 41);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintBottom_creator, 42);
                map.append(C0005R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_creator, 43);
            }
        }

        public void reset() {
            if (this.widget != null) {
                this.widget.reset();
            }
        }

        public LayoutParams(LayoutParams source) {
            super(source);
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.circleConstraint = -1;
            this.circleRadius = 0;
            this.circleAngle = 0.0f;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.goneLeftMargin = -1;
            this.goneTopMargin = -1;
            this.goneRightMargin = -1;
            this.goneBottomMargin = -1;
            this.goneStartMargin = -1;
            this.goneEndMargin = -1;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.dimensionRatioValue = 0.0f;
            this.dimensionRatioSide = 1;
            this.horizontalWeight = -1.0f;
            this.verticalWeight = -1.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.matchConstraintDefaultWidth = 0;
            this.matchConstraintDefaultHeight = 0;
            this.matchConstraintMinWidth = 0;
            this.matchConstraintMinHeight = 0;
            this.matchConstraintMaxWidth = 0;
            this.matchConstraintMaxHeight = 0;
            this.matchConstraintPercentWidth = 1.0f;
            this.matchConstraintPercentHeight = 1.0f;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.constrainedWidth = false;
            this.constrainedHeight = false;
            this.horizontalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            this.verticalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            this.needsBaseline = false;
            this.isGuideline = false;
            this.isHelper = false;
            this.isInPlaceholder = false;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolveGoneLeftMargin = -1;
            this.resolveGoneRightMargin = -1;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
            this.helped = false;
            this.guideBegin = source.guideBegin;
            this.guideEnd = source.guideEnd;
            this.guidePercent = source.guidePercent;
            this.leftToLeft = source.leftToLeft;
            this.leftToRight = source.leftToRight;
            this.rightToLeft = source.rightToLeft;
            this.rightToRight = source.rightToRight;
            this.topToTop = source.topToTop;
            this.topToBottom = source.topToBottom;
            this.bottomToTop = source.bottomToTop;
            this.bottomToBottom = source.bottomToBottom;
            this.baselineToBaseline = source.baselineToBaseline;
            this.circleConstraint = source.circleConstraint;
            this.circleRadius = source.circleRadius;
            this.circleAngle = source.circleAngle;
            this.startToEnd = source.startToEnd;
            this.startToStart = source.startToStart;
            this.endToStart = source.endToStart;
            this.endToEnd = source.endToEnd;
            this.goneLeftMargin = source.goneLeftMargin;
            this.goneTopMargin = source.goneTopMargin;
            this.goneRightMargin = source.goneRightMargin;
            this.goneBottomMargin = source.goneBottomMargin;
            this.goneStartMargin = source.goneStartMargin;
            this.goneEndMargin = source.goneEndMargin;
            this.horizontalBias = source.horizontalBias;
            this.verticalBias = source.verticalBias;
            this.dimensionRatio = source.dimensionRatio;
            this.dimensionRatioValue = source.dimensionRatioValue;
            this.dimensionRatioSide = source.dimensionRatioSide;
            this.horizontalWeight = source.horizontalWeight;
            this.verticalWeight = source.verticalWeight;
            this.horizontalChainStyle = source.horizontalChainStyle;
            this.verticalChainStyle = source.verticalChainStyle;
            this.constrainedWidth = source.constrainedWidth;
            this.constrainedHeight = source.constrainedHeight;
            this.matchConstraintDefaultWidth = source.matchConstraintDefaultWidth;
            this.matchConstraintDefaultHeight = source.matchConstraintDefaultHeight;
            this.matchConstraintMinWidth = source.matchConstraintMinWidth;
            this.matchConstraintMaxWidth = source.matchConstraintMaxWidth;
            this.matchConstraintMinHeight = source.matchConstraintMinHeight;
            this.matchConstraintMaxHeight = source.matchConstraintMaxHeight;
            this.matchConstraintPercentWidth = source.matchConstraintPercentWidth;
            this.matchConstraintPercentHeight = source.matchConstraintPercentHeight;
            this.editorAbsoluteX = source.editorAbsoluteX;
            this.editorAbsoluteY = source.editorAbsoluteY;
            this.orientation = source.orientation;
            this.horizontalDimensionFixed = source.horizontalDimensionFixed;
            this.verticalDimensionFixed = source.verticalDimensionFixed;
            this.needsBaseline = source.needsBaseline;
            this.isGuideline = source.isGuideline;
            this.resolvedLeftToLeft = source.resolvedLeftToLeft;
            this.resolvedLeftToRight = source.resolvedLeftToRight;
            this.resolvedRightToLeft = source.resolvedRightToLeft;
            this.resolvedRightToRight = source.resolvedRightToRight;
            this.resolveGoneLeftMargin = source.resolveGoneLeftMargin;
            this.resolveGoneRightMargin = source.resolveGoneRightMargin;
            this.resolvedHorizontalBias = source.resolvedHorizontalBias;
            this.widget = source.widget;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public LayoutParams(android.content.Context r21, android.util.AttributeSet r22) {
            /*
            r20 = this;
            r1 = r20;
            r20.<init>(r21, r22);
            r2 = -1;
            r1.guideBegin = r2;
            r1.guideEnd = r2;
            r0 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
            r1.guidePercent = r0;
            r1.leftToLeft = r2;
            r1.leftToRight = r2;
            r1.rightToLeft = r2;
            r1.rightToRight = r2;
            r1.topToTop = r2;
            r1.topToBottom = r2;
            r1.bottomToTop = r2;
            r1.bottomToBottom = r2;
            r1.baselineToBaseline = r2;
            r1.circleConstraint = r2;
            r3 = 0;
            r1.circleRadius = r3;
            r4 = 0;
            r1.circleAngle = r4;
            r1.startToEnd = r2;
            r1.startToStart = r2;
            r1.endToStart = r2;
            r1.endToEnd = r2;
            r1.goneLeftMargin = r2;
            r1.goneTopMargin = r2;
            r1.goneRightMargin = r2;
            r1.goneBottomMargin = r2;
            r1.goneStartMargin = r2;
            r1.goneEndMargin = r2;
            r5 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
            r1.horizontalBias = r5;
            r1.verticalBias = r5;
            r6 = 0;
            r1.dimensionRatio = r6;
            r1.dimensionRatioValue = r4;
            r6 = 1;
            r1.dimensionRatioSide = r6;
            r1.horizontalWeight = r0;
            r1.verticalWeight = r0;
            r1.horizontalChainStyle = r3;
            r1.verticalChainStyle = r3;
            r1.matchConstraintDefaultWidth = r3;
            r1.matchConstraintDefaultHeight = r3;
            r1.matchConstraintMinWidth = r3;
            r1.matchConstraintMinHeight = r3;
            r1.matchConstraintMaxWidth = r3;
            r1.matchConstraintMaxHeight = r3;
            r0 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
            r1.matchConstraintPercentWidth = r0;
            r1.matchConstraintPercentHeight = r0;
            r1.editorAbsoluteX = r2;
            r1.editorAbsoluteY = r2;
            r1.orientation = r2;
            r1.constrainedWidth = r3;
            r1.constrainedHeight = r3;
            r1.horizontalDimensionFixed = r6;
            r1.verticalDimensionFixed = r6;
            r1.needsBaseline = r3;
            r1.isGuideline = r3;
            r1.isHelper = r3;
            r1.isInPlaceholder = r3;
            r1.resolvedLeftToLeft = r2;
            r1.resolvedLeftToRight = r2;
            r1.resolvedRightToLeft = r2;
            r1.resolvedRightToRight = r2;
            r1.resolveGoneLeftMargin = r2;
            r1.resolveGoneRightMargin = r2;
            r1.resolvedHorizontalBias = r5;
            r0 = new android.support.constraint.solver.widgets.ConstraintWidget;
            r0.<init>();
            r1.widget = r0;
            r1.helped = r3;
            r0 = android.support.constraint.C0005R.styleable.ConstraintLayout_Layout;
            r5 = r21;
            r7 = r22;
            r8 = r5.obtainStyledAttributes(r7, r0);
            r9 = r8.getIndexCount();
            r0 = r3;
        L_0x00a0:
            r10 = r0;
            if (r10 >= r9) goto L_0x0459;
        L_0x00a3:
            r11 = r8.getIndex(r10);
            r0 = android.support.constraint.ConstraintLayout.LayoutParams.Table.map;
            r12 = r0.get(r11);
            r13 = -2;
            switch(r12) {
                case 0: goto L_0x044b;
                case 1: goto L_0x043d;
                case 2: goto L_0x0428;
                case 3: goto L_0x041c;
                case 4: goto L_0x0402;
                case 5: goto L_0x03f8;
                case 6: goto L_0x03ee;
                case 7: goto L_0x03e4;
                case 8: goto L_0x03cc;
                case 9: goto L_0x03b3;
                case 10: goto L_0x039a;
                case 11: goto L_0x0381;
                case 12: goto L_0x0368;
                case 13: goto L_0x034f;
                case 14: goto L_0x0336;
                case 15: goto L_0x031d;
                case 16: goto L_0x0304;
                case 17: goto L_0x02eb;
                case 18: goto L_0x02d2;
                case 19: goto L_0x02b9;
                case 20: goto L_0x02a3;
                case 21: goto L_0x0298;
                case 22: goto L_0x028d;
                case 23: goto L_0x0282;
                case 24: goto L_0x0277;
                case 25: goto L_0x026c;
                case 26: goto L_0x0261;
                case 27: goto L_0x0256;
                case 28: goto L_0x024b;
                case 29: goto L_0x0240;
                case 30: goto L_0x0235;
                case 31: goto L_0x0221;
                case 32: goto L_0x020c;
                case 33: goto L_0x01f7;
                case 34: goto L_0x01e2;
                case 35: goto L_0x01d5;
                case 36: goto L_0x01c0;
                case 37: goto L_0x01aa;
                case 38: goto L_0x019c;
                case 39: goto L_0x019a;
                case 40: goto L_0x0198;
                case 41: goto L_0x0196;
                case 42: goto L_0x0194;
                case 43: goto L_0x00b1;
                case 44: goto L_0x00ea;
                case 45: goto L_0x00e1;
                case 46: goto L_0x00d8;
                case 47: goto L_0x00d1;
                case 48: goto L_0x00ca;
                case 49: goto L_0x00c1;
                case 50: goto L_0x00b8;
                default: goto L_0x00b1;
            };
        L_0x00b1:
            r19 = r3;
            r3 = r2;
            r2 = r19;
            goto L_0x0450;
        L_0x00b8:
            r0 = r1.editorAbsoluteY;
            r0 = r8.getDimensionPixelOffset(r11, r0);
            r1.editorAbsoluteY = r0;
            goto L_0x00b1;
        L_0x00c1:
            r0 = r1.editorAbsoluteX;
            r0 = r8.getDimensionPixelOffset(r11, r0);
            r1.editorAbsoluteX = r0;
            goto L_0x00b1;
        L_0x00ca:
            r0 = r8.getInt(r11, r3);
            r1.verticalChainStyle = r0;
            goto L_0x00b1;
        L_0x00d1:
            r0 = r8.getInt(r11, r3);
            r1.horizontalChainStyle = r0;
            goto L_0x00b1;
        L_0x00d8:
            r0 = r1.verticalWeight;
            r0 = r8.getFloat(r11, r0);
            r1.verticalWeight = r0;
            goto L_0x00b1;
        L_0x00e1:
            r0 = r1.horizontalWeight;
            r0 = r8.getFloat(r11, r0);
            r1.horizontalWeight = r0;
            goto L_0x00b1;
        L_0x00ea:
            r0 = r8.getString(r11);
            r1.dimensionRatio = r0;
            r0 = 2143289344; // 0x7fc00000 float:NaN double:1.058925634E-314;
            r1.dimensionRatioValue = r0;
            r1.dimensionRatioSide = r2;
            r0 = r1.dimensionRatio;
            if (r0 == 0) goto L_0x00b1;
        L_0x00fa:
            r0 = r1.dimensionRatio;
            r13 = r0.length();
            r0 = r1.dimensionRatio;
            r14 = 44;
            r0 = r0.indexOf(r14);
            if (r0 <= 0) goto L_0x012c;
        L_0x010a:
            r14 = r13 + -1;
            if (r0 >= r14) goto L_0x012c;
        L_0x010e:
            r14 = r1.dimensionRatio;
            r14 = r14.substring(r3, r0);
            r15 = "W";
            r15 = r14.equalsIgnoreCase(r15);
            if (r15 == 0) goto L_0x011f;
        L_0x011c:
            r1.dimensionRatioSide = r3;
            goto L_0x0129;
        L_0x011f:
            r15 = "H";
            r15 = r14.equalsIgnoreCase(r15);
            if (r15 == 0) goto L_0x0129;
        L_0x0127:
            r1.dimensionRatioSide = r6;
        L_0x0129:
            r0 = r0 + 1;
            goto L_0x012d;
        L_0x012c:
            r0 = 0;
        L_0x012d:
            r14 = r0;
            r0 = r1.dimensionRatio;
            r15 = 58;
            r15 = r0.indexOf(r15);
            if (r15 < 0) goto L_0x017e;
        L_0x0138:
            r0 = r13 + -1;
            if (r15 >= r0) goto L_0x017e;
        L_0x013c:
            r0 = r1.dimensionRatio;
            r16 = r0.substring(r14, r15);
            r0 = r1.dimensionRatio;
            r2 = r15 + 1;
            r2 = r0.substring(r2);
            r0 = r16.length();
            if (r0 <= 0) goto L_0x017d;
        L_0x0150:
            r0 = r2.length();
            if (r0 <= 0) goto L_0x017d;
        L_0x0156:
            r0 = java.lang.Float.parseFloat(r16);	 Catch:{ NumberFormatException -> 0x017c }
            r17 = java.lang.Float.parseFloat(r2);	 Catch:{ NumberFormatException -> 0x017c }
            r18 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
            if (r18 <= 0) goto L_0x017b;
        L_0x0162:
            r18 = (r17 > r4 ? 1 : (r17 == r4 ? 0 : -1));
            if (r18 <= 0) goto L_0x017b;
        L_0x0166:
            r3 = r1.dimensionRatioSide;	 Catch:{ NumberFormatException -> 0x017c }
            if (r3 != r6) goto L_0x0173;
        L_0x016a:
            r3 = r17 / r0;
            r3 = java.lang.Math.abs(r3);	 Catch:{ NumberFormatException -> 0x017c }
            r1.dimensionRatioValue = r3;	 Catch:{ NumberFormatException -> 0x017c }
            goto L_0x017b;
        L_0x0173:
            r3 = r0 / r17;
            r3 = java.lang.Math.abs(r3);	 Catch:{ NumberFormatException -> 0x017c }
            r1.dimensionRatioValue = r3;	 Catch:{ NumberFormatException -> 0x017c }
        L_0x017b:
            goto L_0x017d;
        L_0x017c:
            r0 = move-exception;
        L_0x017d:
            goto L_0x0192;
        L_0x017e:
            r0 = r1.dimensionRatio;
            r2 = r0.substring(r14);
            r0 = r2.length();
            if (r0 <= 0) goto L_0x0192;
        L_0x018a:
            r0 = java.lang.Float.parseFloat(r2);	 Catch:{ NumberFormatException -> 0x0191 }
            r1.dimensionRatioValue = r0;	 Catch:{ NumberFormatException -> 0x0191 }
            goto L_0x0192;
        L_0x0191:
            r0 = move-exception;
        L_0x0192:
            goto L_0x021e;
        L_0x0194:
            goto L_0x021e;
        L_0x0196:
            goto L_0x021e;
        L_0x0198:
            goto L_0x021e;
        L_0x019a:
            goto L_0x021e;
        L_0x019c:
            r0 = r1.matchConstraintPercentHeight;
            r0 = r8.getFloat(r11, r0);
            r0 = java.lang.Math.max(r4, r0);
            r1.matchConstraintPercentHeight = r0;
            goto L_0x021e;
        L_0x01aa:
            r0 = r1.matchConstraintMaxHeight;	 Catch:{ Exception -> 0x01b4 }
            r0 = r8.getDimensionPixelSize(r11, r0);	 Catch:{ Exception -> 0x01b4 }
            r1.matchConstraintMaxHeight = r0;	 Catch:{ Exception -> 0x01b4 }
            goto L_0x021e;
        L_0x01b4:
            r0 = move-exception;
            r2 = r1.matchConstraintMaxHeight;
            r2 = r8.getInt(r11, r2);
            if (r2 != r13) goto L_0x01bf;
        L_0x01bd:
            r1.matchConstraintMaxHeight = r13;
        L_0x01bf:
            goto L_0x021e;
        L_0x01c0:
            r0 = r1.matchConstraintMinHeight;	 Catch:{ Exception -> 0x01c9 }
            r0 = r8.getDimensionPixelSize(r11, r0);	 Catch:{ Exception -> 0x01c9 }
            r1.matchConstraintMinHeight = r0;	 Catch:{ Exception -> 0x01c9 }
            goto L_0x021e;
        L_0x01c9:
            r0 = move-exception;
            r2 = r1.matchConstraintMinHeight;
            r2 = r8.getInt(r11, r2);
            if (r2 != r13) goto L_0x01d4;
        L_0x01d2:
            r1.matchConstraintMinHeight = r13;
        L_0x01d4:
            goto L_0x021e;
        L_0x01d5:
            r0 = r1.matchConstraintPercentWidth;
            r0 = r8.getFloat(r11, r0);
            r0 = java.lang.Math.max(r4, r0);
            r1.matchConstraintPercentWidth = r0;
            goto L_0x021e;
        L_0x01e2:
            r0 = r1.matchConstraintMaxWidth;	 Catch:{ Exception -> 0x01eb }
            r0 = r8.getDimensionPixelSize(r11, r0);	 Catch:{ Exception -> 0x01eb }
            r1.matchConstraintMaxWidth = r0;	 Catch:{ Exception -> 0x01eb }
            goto L_0x021e;
        L_0x01eb:
            r0 = move-exception;
            r2 = r1.matchConstraintMaxWidth;
            r2 = r8.getInt(r11, r2);
            if (r2 != r13) goto L_0x01f6;
        L_0x01f4:
            r1.matchConstraintMaxWidth = r13;
        L_0x01f6:
            goto L_0x021e;
        L_0x01f7:
            r0 = r1.matchConstraintMinWidth;	 Catch:{ Exception -> 0x0200 }
            r0 = r8.getDimensionPixelSize(r11, r0);	 Catch:{ Exception -> 0x0200 }
            r1.matchConstraintMinWidth = r0;	 Catch:{ Exception -> 0x0200 }
            goto L_0x021e;
        L_0x0200:
            r0 = move-exception;
            r2 = r1.matchConstraintMinWidth;
            r2 = r8.getInt(r11, r2);
            if (r2 != r13) goto L_0x020b;
        L_0x0209:
            r1.matchConstraintMinWidth = r13;
        L_0x020b:
            goto L_0x021e;
        L_0x020c:
            r2 = 0;
            r0 = r8.getInt(r11, r2);
            r1.matchConstraintDefaultHeight = r0;
            r0 = r1.matchConstraintDefaultHeight;
            if (r0 != r6) goto L_0x021e;
        L_0x0217:
            r0 = "ConstraintLayout";
            r2 = "layout_constraintHeight_default=\"wrap\" is deprecated.\nUse layout_height=\"WRAP_CONTENT\" and layout_constrainedHeight=\"true\" instead.";
            android.util.Log.e(r0, r2);
        L_0x021e:
            r2 = 0;
            goto L_0x0426;
        L_0x0221:
            r2 = 0;
            r0 = r8.getInt(r11, r2);
            r1.matchConstraintDefaultWidth = r0;
            r0 = r1.matchConstraintDefaultWidth;
            if (r0 != r6) goto L_0x0426;
        L_0x022c:
            r0 = "ConstraintLayout";
            r3 = "layout_constraintWidth_default=\"wrap\" is deprecated.\nUse layout_width=\"WRAP_CONTENT\" and layout_constrainedWidth=\"true\" instead.";
            android.util.Log.e(r0, r3);
            goto L_0x0426;
        L_0x0235:
            r2 = r3;
            r0 = r1.verticalBias;
            r0 = r8.getFloat(r11, r0);
            r1.verticalBias = r0;
            goto L_0x0426;
        L_0x0240:
            r2 = r3;
            r0 = r1.horizontalBias;
            r0 = r8.getFloat(r11, r0);
            r1.horizontalBias = r0;
            goto L_0x0426;
        L_0x024b:
            r2 = r3;
            r0 = r1.constrainedHeight;
            r0 = r8.getBoolean(r11, r0);
            r1.constrainedHeight = r0;
            goto L_0x0426;
        L_0x0256:
            r2 = r3;
            r0 = r1.constrainedWidth;
            r0 = r8.getBoolean(r11, r0);
            r1.constrainedWidth = r0;
            goto L_0x0426;
        L_0x0261:
            r2 = r3;
            r0 = r1.goneEndMargin;
            r0 = r8.getDimensionPixelSize(r11, r0);
            r1.goneEndMargin = r0;
            goto L_0x0426;
        L_0x026c:
            r2 = r3;
            r0 = r1.goneStartMargin;
            r0 = r8.getDimensionPixelSize(r11, r0);
            r1.goneStartMargin = r0;
            goto L_0x0426;
        L_0x0277:
            r2 = r3;
            r0 = r1.goneBottomMargin;
            r0 = r8.getDimensionPixelSize(r11, r0);
            r1.goneBottomMargin = r0;
            goto L_0x0426;
        L_0x0282:
            r2 = r3;
            r0 = r1.goneRightMargin;
            r0 = r8.getDimensionPixelSize(r11, r0);
            r1.goneRightMargin = r0;
            goto L_0x0426;
        L_0x028d:
            r2 = r3;
            r0 = r1.goneTopMargin;
            r0 = r8.getDimensionPixelSize(r11, r0);
            r1.goneTopMargin = r0;
            goto L_0x0426;
        L_0x0298:
            r2 = r3;
            r0 = r1.goneLeftMargin;
            r0 = r8.getDimensionPixelSize(r11, r0);
            r1.goneLeftMargin = r0;
            goto L_0x0426;
        L_0x02a3:
            r2 = r3;
            r0 = r1.endToEnd;
            r0 = r8.getResourceId(r11, r0);
            r1.endToEnd = r0;
            r0 = r1.endToEnd;
            r3 = -1;
            if (r0 != r3) goto L_0x0450;
        L_0x02b1:
            r0 = r8.getInt(r11, r3);
            r1.endToEnd = r0;
            goto L_0x0450;
        L_0x02b9:
            r19 = r3;
            r3 = r2;
            r2 = r19;
            r0 = r1.endToStart;
            r0 = r8.getResourceId(r11, r0);
            r1.endToStart = r0;
            r0 = r1.endToStart;
            if (r0 != r3) goto L_0x0450;
        L_0x02ca:
            r0 = r8.getInt(r11, r3);
            r1.endToStart = r0;
            goto L_0x0450;
        L_0x02d2:
            r19 = r3;
            r3 = r2;
            r2 = r19;
            r0 = r1.startToStart;
            r0 = r8.getResourceId(r11, r0);
            r1.startToStart = r0;
            r0 = r1.startToStart;
            if (r0 != r3) goto L_0x0450;
        L_0x02e3:
            r0 = r8.getInt(r11, r3);
            r1.startToStart = r0;
            goto L_0x0450;
        L_0x02eb:
            r19 = r3;
            r3 = r2;
            r2 = r19;
            r0 = r1.startToEnd;
            r0 = r8.getResourceId(r11, r0);
            r1.startToEnd = r0;
            r0 = r1.startToEnd;
            if (r0 != r3) goto L_0x0450;
        L_0x02fc:
            r0 = r8.getInt(r11, r3);
            r1.startToEnd = r0;
            goto L_0x0450;
        L_0x0304:
            r19 = r3;
            r3 = r2;
            r2 = r19;
            r0 = r1.baselineToBaseline;
            r0 = r8.getResourceId(r11, r0);
            r1.baselineToBaseline = r0;
            r0 = r1.baselineToBaseline;
            if (r0 != r3) goto L_0x0450;
        L_0x0315:
            r0 = r8.getInt(r11, r3);
            r1.baselineToBaseline = r0;
            goto L_0x0450;
        L_0x031d:
            r19 = r3;
            r3 = r2;
            r2 = r19;
            r0 = r1.bottomToBottom;
            r0 = r8.getResourceId(r11, r0);
            r1.bottomToBottom = r0;
            r0 = r1.bottomToBottom;
            if (r0 != r3) goto L_0x0450;
        L_0x032e:
            r0 = r8.getInt(r11, r3);
            r1.bottomToBottom = r0;
            goto L_0x0450;
        L_0x0336:
            r19 = r3;
            r3 = r2;
            r2 = r19;
            r0 = r1.bottomToTop;
            r0 = r8.getResourceId(r11, r0);
            r1.bottomToTop = r0;
            r0 = r1.bottomToTop;
            if (r0 != r3) goto L_0x0450;
        L_0x0347:
            r0 = r8.getInt(r11, r3);
            r1.bottomToTop = r0;
            goto L_0x0450;
        L_0x034f:
            r19 = r3;
            r3 = r2;
            r2 = r19;
            r0 = r1.topToBottom;
            r0 = r8.getResourceId(r11, r0);
            r1.topToBottom = r0;
            r0 = r1.topToBottom;
            if (r0 != r3) goto L_0x0450;
        L_0x0360:
            r0 = r8.getInt(r11, r3);
            r1.topToBottom = r0;
            goto L_0x0450;
        L_0x0368:
            r19 = r3;
            r3 = r2;
            r2 = r19;
            r0 = r1.topToTop;
            r0 = r8.getResourceId(r11, r0);
            r1.topToTop = r0;
            r0 = r1.topToTop;
            if (r0 != r3) goto L_0x0450;
        L_0x0379:
            r0 = r8.getInt(r11, r3);
            r1.topToTop = r0;
            goto L_0x0450;
        L_0x0381:
            r19 = r3;
            r3 = r2;
            r2 = r19;
            r0 = r1.rightToRight;
            r0 = r8.getResourceId(r11, r0);
            r1.rightToRight = r0;
            r0 = r1.rightToRight;
            if (r0 != r3) goto L_0x0450;
        L_0x0392:
            r0 = r8.getInt(r11, r3);
            r1.rightToRight = r0;
            goto L_0x0450;
        L_0x039a:
            r19 = r3;
            r3 = r2;
            r2 = r19;
            r0 = r1.rightToLeft;
            r0 = r8.getResourceId(r11, r0);
            r1.rightToLeft = r0;
            r0 = r1.rightToLeft;
            if (r0 != r3) goto L_0x0450;
        L_0x03ab:
            r0 = r8.getInt(r11, r3);
            r1.rightToLeft = r0;
            goto L_0x0450;
        L_0x03b3:
            r19 = r3;
            r3 = r2;
            r2 = r19;
            r0 = r1.leftToRight;
            r0 = r8.getResourceId(r11, r0);
            r1.leftToRight = r0;
            r0 = r1.leftToRight;
            if (r0 != r3) goto L_0x0450;
        L_0x03c4:
            r0 = r8.getInt(r11, r3);
            r1.leftToRight = r0;
            goto L_0x0450;
        L_0x03cc:
            r19 = r3;
            r3 = r2;
            r2 = r19;
            r0 = r1.leftToLeft;
            r0 = r8.getResourceId(r11, r0);
            r1.leftToLeft = r0;
            r0 = r1.leftToLeft;
            if (r0 != r3) goto L_0x0450;
        L_0x03dd:
            r0 = r8.getInt(r11, r3);
            r1.leftToLeft = r0;
            goto L_0x0426;
        L_0x03e4:
            r2 = r3;
            r0 = r1.guidePercent;
            r0 = r8.getFloat(r11, r0);
            r1.guidePercent = r0;
            goto L_0x0426;
        L_0x03ee:
            r2 = r3;
            r0 = r1.guideEnd;
            r0 = r8.getDimensionPixelOffset(r11, r0);
            r1.guideEnd = r0;
            goto L_0x0426;
        L_0x03f8:
            r2 = r3;
            r0 = r1.guideBegin;
            r0 = r8.getDimensionPixelOffset(r11, r0);
            r1.guideBegin = r0;
            goto L_0x0426;
        L_0x0402:
            r2 = r3;
            r0 = r1.circleAngle;
            r0 = r8.getFloat(r11, r0);
            r3 = 1135869952; // 0x43b40000 float:360.0 double:5.611943214E-315;
            r0 = r0 % r3;
            r1.circleAngle = r0;
            r0 = r1.circleAngle;
            r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
            if (r0 >= 0) goto L_0x0426;
        L_0x0414:
            r0 = r1.circleAngle;
            r0 = r3 - r0;
            r0 = r0 % r3;
            r1.circleAngle = r0;
            goto L_0x0426;
        L_0x041c:
            r2 = r3;
            r0 = r1.circleRadius;
            r0 = r8.getDimensionPixelSize(r11, r0);
            r1.circleRadius = r0;
        L_0x0426:
            r3 = -1;
            goto L_0x0450;
        L_0x0428:
            r2 = r3;
            r0 = r1.circleConstraint;
            r0 = r8.getResourceId(r11, r0);
            r1.circleConstraint = r0;
            r0 = r1.circleConstraint;
            r3 = -1;
            if (r0 != r3) goto L_0x0450;
        L_0x0436:
            r0 = r8.getInt(r11, r3);
            r1.circleConstraint = r0;
            goto L_0x0450;
        L_0x043d:
            r19 = r3;
            r3 = r2;
            r2 = r19;
            r0 = r1.orientation;
            r0 = r8.getInt(r11, r0);
            r1.orientation = r0;
            goto L_0x0450;
        L_0x044b:
            r19 = r3;
            r3 = r2;
            r2 = r19;
        L_0x0450:
            r0 = r10 + 1;
            r19 = r3;
            r3 = r2;
            r2 = r19;
            goto L_0x00a0;
        L_0x0459:
            r8.recycle();
            r20.validate();
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.ConstraintLayout.LayoutParams.<init>(android.content.Context, android.util.AttributeSet):void");
        }

        public void validate() {
            this.isGuideline = false;
            this.horizontalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            this.verticalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            if (this.width == -2 && this.constrainedWidth) {
                this.horizontalDimensionFixed = false;
                this.matchConstraintDefaultWidth = 1;
            }
            if (this.height == -2 && this.constrainedHeight) {
                this.verticalDimensionFixed = false;
                this.matchConstraintDefaultHeight = 1;
            }
            if (this.width == 0 || this.width == -1) {
                this.horizontalDimensionFixed = false;
                if (this.width == 0 && this.matchConstraintDefaultWidth == 1) {
                    this.width = -2;
                    this.constrainedWidth = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                }
            }
            if (this.height == 0 || this.height == -1) {
                this.verticalDimensionFixed = false;
                if (this.height == 0 && this.matchConstraintDefaultHeight == 1) {
                    this.height = -2;
                    this.constrainedHeight = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                }
            }
            if (this.guidePercent != -1.0f || this.guideBegin != -1 || this.guideEnd != -1) {
                this.isGuideline = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                this.horizontalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                this.verticalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                if (!(this.widget instanceof Guideline)) {
                    this.widget = new Guideline();
                }
                ((Guideline) this.widget).setOrientation(this.orientation);
            }
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.circleConstraint = -1;
            this.circleRadius = 0;
            this.circleAngle = 0.0f;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.goneLeftMargin = -1;
            this.goneTopMargin = -1;
            this.goneRightMargin = -1;
            this.goneBottomMargin = -1;
            this.goneStartMargin = -1;
            this.goneEndMargin = -1;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.dimensionRatioValue = 0.0f;
            this.dimensionRatioSide = 1;
            this.horizontalWeight = -1.0f;
            this.verticalWeight = -1.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.matchConstraintDefaultWidth = 0;
            this.matchConstraintDefaultHeight = 0;
            this.matchConstraintMinWidth = 0;
            this.matchConstraintMinHeight = 0;
            this.matchConstraintMaxWidth = 0;
            this.matchConstraintMaxHeight = 0;
            this.matchConstraintPercentWidth = 1.0f;
            this.matchConstraintPercentHeight = 1.0f;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.constrainedWidth = false;
            this.constrainedHeight = false;
            this.horizontalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            this.verticalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            this.needsBaseline = false;
            this.isGuideline = false;
            this.isHelper = false;
            this.isInPlaceholder = false;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolveGoneLeftMargin = -1;
            this.resolveGoneRightMargin = -1;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
            this.helped = false;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams source) {
            super(source);
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.circleConstraint = -1;
            this.circleRadius = 0;
            this.circleAngle = 0.0f;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.goneLeftMargin = -1;
            this.goneTopMargin = -1;
            this.goneRightMargin = -1;
            this.goneBottomMargin = -1;
            this.goneStartMargin = -1;
            this.goneEndMargin = -1;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.dimensionRatioValue = 0.0f;
            this.dimensionRatioSide = 1;
            this.horizontalWeight = -1.0f;
            this.verticalWeight = -1.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.matchConstraintDefaultWidth = 0;
            this.matchConstraintDefaultHeight = 0;
            this.matchConstraintMinWidth = 0;
            this.matchConstraintMinHeight = 0;
            this.matchConstraintMaxWidth = 0;
            this.matchConstraintMaxHeight = 0;
            this.matchConstraintPercentWidth = 1.0f;
            this.matchConstraintPercentHeight = 1.0f;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.constrainedWidth = false;
            this.constrainedHeight = false;
            this.horizontalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            this.verticalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            this.needsBaseline = false;
            this.isGuideline = false;
            this.isHelper = false;
            this.isInPlaceholder = false;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolveGoneLeftMargin = -1;
            this.resolveGoneRightMargin = -1;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
            this.helped = false;
        }

        @TargetApi(17)
        public void resolveLayoutDirection(int layoutDirection) {
            int preLeftMargin = this.leftMargin;
            int preRightMargin = this.rightMargin;
            super.resolveLayoutDirection(layoutDirection);
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolveGoneLeftMargin = -1;
            this.resolveGoneRightMargin = -1;
            this.resolveGoneLeftMargin = this.goneLeftMargin;
            this.resolveGoneRightMargin = this.goneRightMargin;
            this.resolvedHorizontalBias = this.horizontalBias;
            this.resolvedGuideBegin = this.guideBegin;
            this.resolvedGuideEnd = this.guideEnd;
            this.resolvedGuidePercent = this.guidePercent;
            if (1 == getLayoutDirection() ? ConstraintLayout.USE_CONSTRAINTS_HELPER : false) {
                boolean startEndDefined = false;
                if (this.startToEnd != -1) {
                    this.resolvedRightToLeft = this.startToEnd;
                    startEndDefined = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                } else if (this.startToStart != -1) {
                    this.resolvedRightToRight = this.startToStart;
                    startEndDefined = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                }
                if (this.endToStart != -1) {
                    this.resolvedLeftToRight = this.endToStart;
                    startEndDefined = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                }
                if (this.endToEnd != -1) {
                    this.resolvedLeftToLeft = this.endToEnd;
                    startEndDefined = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                }
                if (this.goneStartMargin != -1) {
                    this.resolveGoneRightMargin = this.goneStartMargin;
                }
                if (this.goneEndMargin != -1) {
                    this.resolveGoneLeftMargin = this.goneEndMargin;
                }
                if (startEndDefined) {
                    this.resolvedHorizontalBias = 1.0f - this.horizontalBias;
                }
                if (this.isGuideline && this.orientation == 1) {
                    if (this.guidePercent != -1.0f) {
                        this.resolvedGuidePercent = 1.0f - this.guidePercent;
                        this.resolvedGuideBegin = -1;
                        this.resolvedGuideEnd = -1;
                    } else if (this.guideBegin != -1) {
                        this.resolvedGuideEnd = this.guideBegin;
                        this.resolvedGuideBegin = -1;
                        this.resolvedGuidePercent = -1.0f;
                    } else if (this.guideEnd != -1) {
                        this.resolvedGuideBegin = this.guideEnd;
                        this.resolvedGuideEnd = -1;
                        this.resolvedGuidePercent = -1.0f;
                    }
                }
            } else {
                if (this.startToEnd != -1) {
                    this.resolvedLeftToRight = this.startToEnd;
                }
                if (this.startToStart != -1) {
                    this.resolvedLeftToLeft = this.startToStart;
                }
                if (this.endToStart != -1) {
                    this.resolvedRightToLeft = this.endToStart;
                }
                if (this.endToEnd != -1) {
                    this.resolvedRightToRight = this.endToEnd;
                }
                if (this.goneStartMargin != -1) {
                    this.resolveGoneLeftMargin = this.goneStartMargin;
                }
                if (this.goneEndMargin != -1) {
                    this.resolveGoneRightMargin = this.goneEndMargin;
                }
            }
            if (this.endToStart == -1 && this.endToEnd == -1 && this.startToStart == -1 && this.startToEnd == -1) {
                if (this.rightToLeft != -1) {
                    this.resolvedRightToLeft = this.rightToLeft;
                    if (this.rightMargin <= 0 && preRightMargin > 0) {
                        this.rightMargin = preRightMargin;
                    }
                } else if (this.rightToRight != -1) {
                    this.resolvedRightToRight = this.rightToRight;
                    if (this.rightMargin <= 0 && preRightMargin > 0) {
                        this.rightMargin = preRightMargin;
                    }
                }
                if (this.leftToLeft != -1) {
                    this.resolvedLeftToLeft = this.leftToLeft;
                    if (this.leftMargin <= 0 && preLeftMargin > 0) {
                        this.leftMargin = preLeftMargin;
                    }
                } else if (this.leftToRight != -1) {
                    this.resolvedLeftToRight = this.leftToRight;
                    if (this.leftMargin <= 0 && preLeftMargin > 0) {
                        this.leftMargin = preLeftMargin;
                    }
                }
            }
        }
    }

    public void setDesignInformation(int type, Object value1, Object value2) {
        if (type == 0 && (value1 instanceof String) && (value2 instanceof Integer)) {
            if (this.mDesignIds == null) {
                this.mDesignIds = new HashMap();
            }
            String name = (String) value1;
            int index = name.indexOf("/");
            if (index != -1) {
                name = name.substring(index + 1);
            }
            this.mDesignIds.put(name, Integer.valueOf(((Integer) value2).intValue()));
        }
    }

    public Object getDesignInformation(int type, Object value) {
        if (type == 0 && (value instanceof String)) {
            String name = (String) value;
            if (this.mDesignIds != null && this.mDesignIds.containsKey(name)) {
                return this.mDesignIds.get(name);
            }
        }
        return null;
    }

    public ConstraintLayout(Context context) {
        super(context);
        init(null);
    }

    public ConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void setId(int id) {
        this.mChildrenByIds.remove(getId());
        super.setId(id);
        this.mChildrenByIds.put(getId(), this);
    }

    private void init(AttributeSet attrs) {
        this.mLayoutWidget.setCompanionWidget(this);
        this.mChildrenByIds.put(getId(), this);
        this.mConstraintSet = null;
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, C0005R.styleable.ConstraintLayout_Layout);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == C0005R.styleable.ConstraintLayout_Layout_android_minWidth) {
                    this.mMinWidth = a.getDimensionPixelOffset(attr, this.mMinWidth);
                } else if (attr == C0005R.styleable.ConstraintLayout_Layout_android_minHeight) {
                    this.mMinHeight = a.getDimensionPixelOffset(attr, this.mMinHeight);
                } else if (attr == C0005R.styleable.ConstraintLayout_Layout_android_maxWidth) {
                    this.mMaxWidth = a.getDimensionPixelOffset(attr, this.mMaxWidth);
                } else if (attr == C0005R.styleable.ConstraintLayout_Layout_android_maxHeight) {
                    this.mMaxHeight = a.getDimensionPixelOffset(attr, this.mMaxHeight);
                } else if (attr == C0005R.styleable.ConstraintLayout_Layout_layout_optimizationLevel) {
                    this.mOptimizationLevel = a.getInt(attr, this.mOptimizationLevel);
                } else if (attr == C0005R.styleable.ConstraintLayout_Layout_constraintSet) {
                    int id = a.getResourceId(attr, 0);
                    try {
                        this.mConstraintSet = new ConstraintSet();
                        this.mConstraintSet.load(getContext(), id);
                    } catch (NotFoundException e) {
                        this.mConstraintSet = null;
                    }
                    this.mConstraintSetId = id;
                }
            }
            a.recycle();
        }
        this.mLayoutWidget.setOptimizationLevel(this.mOptimizationLevel);
    }

    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (VERSION.SDK_INT < 14) {
            onViewAdded(child);
        }
    }

    public void removeView(View view) {
        super.removeView(view);
        if (VERSION.SDK_INT < 14) {
            onViewRemoved(view);
        }
    }

    public void onViewAdded(View view) {
        if (VERSION.SDK_INT >= 14) {
            super.onViewAdded(view);
        }
        ConstraintWidget widget = getViewWidget(view);
        if ((view instanceof Guideline) && !(widget instanceof Guideline)) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.widget = new Guideline();
            layoutParams.isGuideline = USE_CONSTRAINTS_HELPER;
            ((Guideline) layoutParams.widget).setOrientation(layoutParams.orientation);
        }
        if (view instanceof ConstraintHelper) {
            ConstraintHelper helper = (ConstraintHelper) view;
            helper.validateParams();
            ((LayoutParams) view.getLayoutParams()).isHelper = USE_CONSTRAINTS_HELPER;
            if (!this.mConstraintHelpers.contains(helper)) {
                this.mConstraintHelpers.add(helper);
            }
        }
        this.mChildrenByIds.put(view.getId(), view);
        this.mDirtyHierarchy = USE_CONSTRAINTS_HELPER;
    }

    public void onViewRemoved(View view) {
        if (VERSION.SDK_INT >= 14) {
            super.onViewRemoved(view);
        }
        this.mChildrenByIds.remove(view.getId());
        ConstraintWidget widget = getViewWidget(view);
        this.mLayoutWidget.remove(widget);
        this.mConstraintHelpers.remove(view);
        this.mVariableDimensionsWidgets.remove(widget);
        this.mDirtyHierarchy = USE_CONSTRAINTS_HELPER;
    }

    public void setMinWidth(int value) {
        if (value != this.mMinWidth) {
            this.mMinWidth = value;
            requestLayout();
        }
    }

    public void setMinHeight(int value) {
        if (value != this.mMinHeight) {
            this.mMinHeight = value;
            requestLayout();
        }
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public void setMaxWidth(int value) {
        if (value != this.mMaxWidth) {
            this.mMaxWidth = value;
            requestLayout();
        }
    }

    public void setMaxHeight(int value) {
        if (value != this.mMaxHeight) {
            this.mMaxHeight = value;
            requestLayout();
        }
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    private void updateHierarchy() {
        int count = getChildCount();
        boolean recompute = false;
        for (int i = 0; i < count; i++) {
            if (getChildAt(i).isLayoutRequested()) {
                recompute = USE_CONSTRAINTS_HELPER;
                break;
            }
        }
        if (recompute) {
            this.mVariableDimensionsWidgets.clear();
            setChildrenConstraints();
        }
    }

    private void setChildrenConstraints() {
        int i;
        int i2;
        View view;
        String IdAsString;
        ConstraintLayout constraintLayout = this;
        boolean isInEditMode = isInEditMode();
        int count = getChildCount();
        boolean z = false;
        int i3 = -1;
        if (isInEditMode) {
            i = 0;
            while (true) {
                i2 = i;
                if (i2 >= count) {
                    break;
                }
                view = getChildAt(i2);
                try {
                    IdAsString = getResources().getResourceName(view.getId());
                    setDesignInformation(0, IdAsString, Integer.valueOf(view.getId()));
                    int slashIndex = IdAsString.indexOf(47);
                    if (slashIndex != -1) {
                        IdAsString = IdAsString.substring(slashIndex + 1);
                    }
                    getTargetWidget(view.getId()).setDebugName(IdAsString);
                } catch (NotFoundException e) {
                }
                i = i2 + 1;
            }
        }
        for (i = 0; i < count; i++) {
            ConstraintWidget widget = getViewWidget(getChildAt(i));
            if (widget != null) {
                widget.reset();
            }
        }
        if (constraintLayout.mConstraintSetId != -1) {
            for (i = 0; i < count; i++) {
                View child = getChildAt(i);
                if (child.getId() == constraintLayout.mConstraintSetId && (child instanceof Constraints)) {
                    constraintLayout.mConstraintSet = ((Constraints) child).getConstraintSet();
                }
            }
        }
        if (constraintLayout.mConstraintSet != null) {
            constraintLayout.mConstraintSet.applyToInternal(constraintLayout);
        }
        constraintLayout.mLayoutWidget.removeAllChildren();
        i2 = constraintLayout.mConstraintHelpers.size();
        if (i2 > 0) {
            for (i = 0; i < i2; i++) {
                ((ConstraintHelper) constraintLayout.mConstraintHelpers.get(i)).updatePreLayout(constraintLayout);
            }
        }
        for (i = 0; i < count; i++) {
            view = getChildAt(i);
            if (view instanceof Placeholder) {
                ((Placeholder) view).updatePreLayout(constraintLayout);
            }
        }
        i = 0;
        while (true) {
            int i4 = i;
            int helperCount;
            if (i4 < count) {
                int resolvedGuideBegin;
                int i5;
                boolean z2;
                View child2 = getChildAt(i4);
                ConstraintWidget widget2 = getViewWidget(child2);
                if (widget2 != null) {
                    LayoutParams layoutParams = (LayoutParams) child2.getLayoutParams();
                    layoutParams.validate();
                    if (layoutParams.helped) {
                        layoutParams.helped = z;
                    } else if (isInEditMode) {
                        try {
                            IdAsString = getResources().getResourceName(child2.getId());
                            setDesignInformation(z, IdAsString, Integer.valueOf(child2.getId()));
                            getTargetWidget(child2.getId()).setDebugName(IdAsString.substring(IdAsString.indexOf("id/") + 3));
                        } catch (NotFoundException e2) {
                        }
                    }
                    widget2.setVisibility(child2.getVisibility());
                    if (layoutParams.isInPlaceholder) {
                        widget2.setVisibility(8);
                    }
                    widget2.setCompanionWidget(child2);
                    constraintLayout.mLayoutWidget.add(widget2);
                    if (!(layoutParams.verticalDimensionFixed && layoutParams.horizontalDimensionFixed)) {
                        constraintLayout.mVariableDimensionsWidgets.add(widget2);
                    }
                    int resolvedGuideEnd;
                    if (layoutParams.isGuideline) {
                        Guideline guideline = (Guideline) widget2;
                        resolvedGuideBegin = layoutParams.resolvedGuideBegin;
                        resolvedGuideEnd = layoutParams.resolvedGuideEnd;
                        float resolvedGuidePercent = layoutParams.resolvedGuidePercent;
                        if (VERSION.SDK_INT < 17) {
                            resolvedGuideBegin = layoutParams.guideBegin;
                            resolvedGuideEnd = layoutParams.guideEnd;
                            resolvedGuidePercent = layoutParams.guidePercent;
                        }
                        if (resolvedGuidePercent != -1.0f) {
                            guideline.setGuidePercent(resolvedGuidePercent);
                        } else if (resolvedGuideBegin != i3) {
                            guideline.setGuideBegin(resolvedGuideBegin);
                        } else if (resolvedGuideEnd != i3) {
                            guideline.setGuideEnd(resolvedGuideEnd);
                        }
                    } else if (!(layoutParams.leftToLeft == i3 && layoutParams.leftToRight == i3 && layoutParams.rightToLeft == i3 && layoutParams.rightToRight == i3 && layoutParams.startToStart == i3 && layoutParams.startToEnd == i3 && layoutParams.endToStart == i3 && layoutParams.endToEnd == i3 && layoutParams.topToTop == i3 && layoutParams.topToBottom == i3 && layoutParams.bottomToTop == i3 && layoutParams.bottomToBottom == i3 && layoutParams.baselineToBaseline == i3 && layoutParams.editorAbsoluteX == i3 && layoutParams.editorAbsoluteY == i3 && layoutParams.circleConstraint == i3 && layoutParams.width != i3 && layoutParams.height != i3)) {
                        int resolvedLeftToRight;
                        LayoutParams layoutParams2;
                        i = layoutParams.resolvedLeftToLeft;
                        resolvedGuideBegin = layoutParams.resolvedLeftToRight;
                        resolvedGuideEnd = layoutParams.resolvedRightToLeft;
                        int resolvedRightToRight = layoutParams.resolvedRightToRight;
                        int resolveGoneLeftMargin = layoutParams.resolveGoneLeftMargin;
                        int resolveGoneRightMargin = layoutParams.resolveGoneRightMargin;
                        i3 = layoutParams.resolvedHorizontalBias;
                        int resolvedLeftToLeft = i;
                        if (VERSION.SDK_INT < 17) {
                            int i6;
                            int i7;
                            i = layoutParams.leftToLeft;
                            resolvedLeftToRight = layoutParams.leftToRight;
                            resolvedGuideEnd = layoutParams.rightToLeft;
                            resolvedRightToRight = layoutParams.rightToRight;
                            resolvedGuideBegin = layoutParams.goneLeftMargin;
                            resolveGoneRightMargin = layoutParams.goneRightMargin;
                            i3 = layoutParams.horizontalBias;
                            if (i == -1 && resolvedLeftToRight == -1) {
                                i6 = i;
                                if (layoutParams.startToStart != -1) {
                                    i = layoutParams.startToStart;
                                    if (resolvedGuideEnd == -1 || resolvedRightToRight != -1) {
                                        i7 = i;
                                    } else {
                                        i7 = i;
                                        if (layoutParams.endToStart != -1) {
                                            resolvedGuideEnd = layoutParams.endToStart;
                                        } else if (layoutParams.endToEnd != -1) {
                                            resolvedRightToRight = layoutParams.endToEnd;
                                        }
                                    }
                                    resolvedLeftToLeft = resolveGoneRightMargin;
                                    resolveGoneRightMargin = resolvedLeftToRight;
                                    resolvedLeftToRight = -1;
                                    i = i7;
                                    resolveGoneLeftMargin = resolvedGuideEnd;
                                    resolvedGuideEnd = i3;
                                    i3 = resolvedGuideBegin;
                                } else if (layoutParams.startToEnd != -1) {
                                    resolvedLeftToRight = layoutParams.startToEnd;
                                }
                            } else {
                                i6 = i;
                            }
                            i = i6;
                            if (resolvedGuideEnd == -1) {
                            }
                            i7 = i;
                            resolvedLeftToLeft = resolveGoneRightMargin;
                            resolveGoneRightMargin = resolvedLeftToRight;
                            resolvedLeftToRight = -1;
                            i = i7;
                            resolveGoneLeftMargin = resolvedGuideEnd;
                            resolvedGuideEnd = i3;
                            i3 = resolvedGuideBegin;
                        } else {
                            resolvedLeftToRight = -1;
                            i = resolvedLeftToLeft;
                            resolvedLeftToLeft = resolveGoneRightMargin;
                            resolveGoneRightMargin = resolvedGuideBegin;
                            int i8 = resolvedGuideEnd;
                            resolvedGuideEnd = i3;
                            i3 = resolveGoneLeftMargin;
                            resolveGoneLeftMargin = i8;
                        }
                        View view2;
                        if (layoutParams.circleConstraint != resolvedLeftToRight) {
                            ConstraintWidget target = getTargetWidget(layoutParams.circleConstraint);
                            if (target != null) {
                                i5 = count;
                                widget2.connectCircularConstraint(target, layoutParams.circleAngle, layoutParams.circleRadius);
                            } else {
                                i5 = count;
                            }
                            int i9 = i;
                            helperCount = i2;
                            view2 = child2;
                            i = resolvedGuideEnd;
                            count = resolvedRightToRight;
                            i2 = resolveGoneLeftMargin;
                            layoutParams2 = layoutParams;
                        } else {
                            float resolvedLeftToLeft2;
                            ConstraintWidget target2;
                            i5 = count;
                            if (i != -1) {
                                count = getTargetWidget(i);
                                if (count != 0) {
                                    resolvedLeftToLeft2 = resolvedGuideEnd;
                                    ConstraintWidget constraintWidget = count;
                                    ConstraintWidget target3 = count;
                                    count = resolvedRightToRight;
                                    helperCount = i2;
                                    i2 = resolveGoneLeftMargin;
                                    layoutParams2 = layoutParams;
                                    widget2.immediateConnect(Type.LEFT, constraintWidget, Type.LEFT, layoutParams.leftMargin, i3);
                                } else {
                                    helperCount = i2;
                                    view2 = child2;
                                    resolvedLeftToLeft2 = resolvedGuideEnd;
                                    count = resolvedRightToRight;
                                    i2 = resolveGoneLeftMargin;
                                    layoutParams2 = layoutParams;
                                }
                            } else {
                                helperCount = i2;
                                view2 = child2;
                                resolvedLeftToLeft2 = resolvedGuideEnd;
                                count = resolvedRightToRight;
                                i2 = resolveGoneLeftMargin;
                                layoutParams2 = layoutParams;
                                if (resolveGoneRightMargin != -1) {
                                    target2 = getTargetWidget(resolveGoneRightMargin);
                                    if (target2 != null) {
                                        widget2.immediateConnect(Type.LEFT, target2, Type.RIGHT, layoutParams2.leftMargin, i3);
                                    }
                                }
                            }
                            if (i2 != -1) {
                                target2 = getTargetWidget(i2);
                                if (target2 != null) {
                                    widget2.immediateConnect(Type.RIGHT, target2, Type.LEFT, layoutParams2.rightMargin, resolvedLeftToLeft);
                                }
                            } else if (count != -1) {
                                target2 = getTargetWidget(count);
                                if (target2 != null) {
                                    widget2.immediateConnect(Type.RIGHT, target2, Type.RIGHT, layoutParams2.rightMargin, resolvedLeftToLeft);
                                }
                            }
                            if (layoutParams2.topToTop != -1) {
                                target2 = getTargetWidget(layoutParams2.topToTop);
                                if (target2 != null) {
                                    widget2.immediateConnect(Type.TOP, target2, Type.TOP, layoutParams2.topMargin, layoutParams2.goneTopMargin);
                                }
                            } else if (layoutParams2.topToBottom != -1) {
                                target2 = getTargetWidget(layoutParams2.topToBottom);
                                if (target2 != null) {
                                    widget2.immediateConnect(Type.TOP, target2, Type.BOTTOM, layoutParams2.topMargin, layoutParams2.goneTopMargin);
                                }
                            }
                            if (layoutParams2.bottomToTop != -1) {
                                target2 = getTargetWidget(layoutParams2.bottomToTop);
                                if (target2 != null) {
                                    widget2.immediateConnect(Type.BOTTOM, target2, Type.TOP, layoutParams2.bottomMargin, layoutParams2.goneBottomMargin);
                                }
                            } else if (layoutParams2.bottomToBottom != -1) {
                                target2 = getTargetWidget(layoutParams2.bottomToBottom);
                                if (target2 != null) {
                                    widget2.immediateConnect(Type.BOTTOM, target2, Type.BOTTOM, layoutParams2.bottomMargin, layoutParams2.goneBottomMargin);
                                }
                            }
                            if (layoutParams2.baselineToBaseline != -1) {
                                View view3 = (View) constraintLayout.mChildrenByIds.get(layoutParams2.baselineToBaseline);
                                ConstraintWidget target4 = getTargetWidget(layoutParams2.baselineToBaseline);
                                if (!(target4 == null || view3 == null || !(view3.getLayoutParams() instanceof LayoutParams))) {
                                    LayoutParams targetParams = (LayoutParams) view3.getLayoutParams();
                                    layoutParams2.needsBaseline = USE_CONSTRAINTS_HELPER;
                                    targetParams.needsBaseline = USE_CONSTRAINTS_HELPER;
                                    ConstraintAnchor baseline = widget2.getAnchor(Type.BASELINE);
                                    baseline.connect(target4.getAnchor(Type.BASELINE), 0, -1, Strength.STRONG, 0, USE_CONSTRAINTS_HELPER);
                                    widget2.getAnchor(Type.TOP).reset();
                                    widget2.getAnchor(Type.BOTTOM).reset();
                                }
                            }
                            if (resolvedLeftToLeft2 >= 0.0f && resolvedLeftToLeft2 != 0.5f) {
                                widget2.setHorizontalBiasPercent(resolvedLeftToLeft2);
                            }
                            if (layoutParams2.verticalBias >= 0.0f && layoutParams2.verticalBias != 0.5f) {
                                widget2.setVerticalBiasPercent(layoutParams2.verticalBias);
                            }
                        }
                        if (isInEditMode && !(layoutParams2.editorAbsoluteX == -1 && layoutParams2.editorAbsoluteY == -1)) {
                            widget2.setOrigin(layoutParams2.editorAbsoluteX, layoutParams2.editorAbsoluteY);
                        }
                        if (layoutParams2.horizontalDimensionFixed) {
                            widget2.setHorizontalDimensionBehaviour(DimensionBehaviour.FIXED);
                            widget2.setWidth(layoutParams2.width);
                        } else if (layoutParams2.width == -1) {
                            widget2.setHorizontalDimensionBehaviour(DimensionBehaviour.MATCH_PARENT);
                            widget2.getAnchor(Type.LEFT).mMargin = layoutParams2.leftMargin;
                            widget2.getAnchor(Type.RIGHT).mMargin = layoutParams2.rightMargin;
                        } else {
                            widget2.setHorizontalDimensionBehaviour(DimensionBehaviour.MATCH_CONSTRAINT);
                            widget2.setWidth(0);
                        }
                        if (layoutParams2.verticalDimensionFixed) {
                            z2 = false;
                            resolvedGuideBegin = -1;
                            widget2.setVerticalDimensionBehaviour(DimensionBehaviour.FIXED);
                            widget2.setHeight(layoutParams2.height);
                        } else {
                            resolvedGuideBegin = -1;
                            if (layoutParams2.height == -1) {
                                widget2.setVerticalDimensionBehaviour(DimensionBehaviour.MATCH_PARENT);
                                widget2.getAnchor(Type.TOP).mMargin = layoutParams2.topMargin;
                                widget2.getAnchor(Type.BOTTOM).mMargin = layoutParams2.bottomMargin;
                                z2 = false;
                            } else {
                                widget2.setVerticalDimensionBehaviour(DimensionBehaviour.MATCH_CONSTRAINT);
                                z2 = false;
                                widget2.setHeight(0);
                            }
                        }
                        if (layoutParams2.dimensionRatio != null) {
                            widget2.setDimensionRatio(layoutParams2.dimensionRatio);
                        }
                        widget2.setHorizontalWeight(layoutParams2.horizontalWeight);
                        widget2.setVerticalWeight(layoutParams2.verticalWeight);
                        widget2.setHorizontalChainStyle(layoutParams2.horizontalChainStyle);
                        widget2.setVerticalChainStyle(layoutParams2.verticalChainStyle);
                        widget2.setHorizontalMatchStyle(layoutParams2.matchConstraintDefaultWidth, layoutParams2.matchConstraintMinWidth, layoutParams2.matchConstraintMaxWidth, layoutParams2.matchConstraintPercentWidth);
                        widget2.setVerticalMatchStyle(layoutParams2.matchConstraintDefaultHeight, layoutParams2.matchConstraintMinHeight, layoutParams2.matchConstraintMaxHeight, layoutParams2.matchConstraintPercentHeight);
                        i = i4 + 1;
                        z = z2;
                        i3 = resolvedGuideBegin;
                        count = i5;
                        i2 = helperCount;
                    }
                }
                i5 = count;
                z2 = z;
                resolvedGuideBegin = i3;
                helperCount = i2;
                i = i4 + 1;
                z = z2;
                i3 = resolvedGuideBegin;
                count = i5;
                i2 = helperCount;
            } else {
                helperCount = i2;
                return;
            }
        }
    }

    private final ConstraintWidget getTargetWidget(int id) {
        if (id == 0) {
            return this.mLayoutWidget;
        }
        View view = (View) this.mChildrenByIds.get(id);
        if (view == null) {
            view = findViewById(id);
            if (!(view == null || view == this || view.getParent() != this)) {
                onViewAdded(view);
            }
        }
        if (view == this) {
            return this.mLayoutWidget;
        }
        return view == null ? null : ((LayoutParams) view.getLayoutParams()).widget;
    }

    public final ConstraintWidget getViewWidget(View view) {
        if (view == this) {
            return this.mLayoutWidget;
        }
        return view == null ? null : ((LayoutParams) view.getLayoutParams()).widget;
    }

    private void internalMeasureChildren(int parentWidthSpec, int parentHeightSpec) {
        ConstraintLayout constraintLayout = this;
        int i = parentWidthSpec;
        int i2 = parentHeightSpec;
        int heightPadding = getPaddingTop() + getPaddingBottom();
        int widthPadding = getPaddingLeft() + getPaddingRight();
        int widgetsCount = getChildCount();
        int i3 = 0;
        while (i3 < widgetsCount) {
            View child = constraintLayout.getChildAt(i3);
            if (child.getVisibility() != 8) {
                LayoutParams params = (LayoutParams) child.getLayoutParams();
                ConstraintWidget widget = params.widget;
                if (!params.isGuideline) {
                    if (!params.isHelper) {
                        boolean doMeasure;
                        boolean didWrapMeasureWidth;
                        boolean didWrapMeasureHeight;
                        int childWidthMeasureSpec;
                        int childWidthMeasureSpec2;
                        Metrics metrics;
                        int baseline;
                        widget.setVisibility(child.getVisibility());
                        int width = params.width;
                        int height = params.height;
                        if (!(params.horizontalDimensionFixed || params.verticalDimensionFixed || ((!params.horizontalDimensionFixed && params.matchConstraintDefaultWidth == 1) || params.width == -1))) {
                            if (!params.verticalDimensionFixed) {
                                if (params.matchConstraintDefaultHeight != 1) {
                                    if (params.height == -1) {
                                    }
                                }
                            }
                            doMeasure = false;
                            didWrapMeasureWidth = false;
                            didWrapMeasureHeight = false;
                            if (doMeasure) {
                                if (width == 0) {
                                    childWidthMeasureSpec = getChildMeasureSpec(i, widthPadding, -2);
                                    didWrapMeasureWidth = USE_CONSTRAINTS_HELPER;
                                } else if (width != -1) {
                                    childWidthMeasureSpec = getChildMeasureSpec(i, widthPadding, -1);
                                } else {
                                    if (width == -2) {
                                        didWrapMeasureWidth = USE_CONSTRAINTS_HELPER;
                                    }
                                    childWidthMeasureSpec = getChildMeasureSpec(i, widthPadding, width);
                                }
                                childWidthMeasureSpec2 = childWidthMeasureSpec;
                                if (height == 0) {
                                    childWidthMeasureSpec = getChildMeasureSpec(i2, heightPadding, -2);
                                    didWrapMeasureHeight = USE_CONSTRAINTS_HELPER;
                                } else if (height != -1) {
                                    childWidthMeasureSpec = getChildMeasureSpec(i2, heightPadding, -1);
                                } else {
                                    if (height == -2) {
                                        didWrapMeasureHeight = USE_CONSTRAINTS_HELPER;
                                    }
                                    childWidthMeasureSpec = getChildMeasureSpec(i2, heightPadding, height);
                                }
                                child.measure(childWidthMeasureSpec2, childWidthMeasureSpec);
                                if (constraintLayout.mMetrics != null) {
                                    metrics = constraintLayout.mMetrics;
                                    metrics.measures++;
                                }
                                widget.setWidthWrapContent(width != -2 ? USE_CONSTRAINTS_HELPER : false);
                                widget.setHeightWrapContent(height != -2 ? USE_CONSTRAINTS_HELPER : false);
                                width = child.getMeasuredWidth();
                                height = child.getMeasuredHeight();
                            }
                            widget.setWidth(width);
                            widget.setHeight(height);
                            if (didWrapMeasureWidth) {
                                widget.setWrapWidth(width);
                            }
                            if (didWrapMeasureHeight) {
                                widget.setWrapHeight(height);
                            }
                            if (params.needsBaseline) {
                                baseline = child.getBaseline();
                                if (baseline != -1) {
                                    widget.setBaselineDistance(baseline);
                                }
                            }
                        }
                        doMeasure = USE_CONSTRAINTS_HELPER;
                        didWrapMeasureWidth = false;
                        didWrapMeasureHeight = false;
                        if (doMeasure) {
                            if (width == 0) {
                                childWidthMeasureSpec = getChildMeasureSpec(i, widthPadding, -2);
                                didWrapMeasureWidth = USE_CONSTRAINTS_HELPER;
                            } else if (width != -1) {
                                if (width == -2) {
                                    didWrapMeasureWidth = USE_CONSTRAINTS_HELPER;
                                }
                                childWidthMeasureSpec = getChildMeasureSpec(i, widthPadding, width);
                            } else {
                                childWidthMeasureSpec = getChildMeasureSpec(i, widthPadding, -1);
                            }
                            childWidthMeasureSpec2 = childWidthMeasureSpec;
                            if (height == 0) {
                                childWidthMeasureSpec = getChildMeasureSpec(i2, heightPadding, -2);
                                didWrapMeasureHeight = USE_CONSTRAINTS_HELPER;
                            } else if (height != -1) {
                                if (height == -2) {
                                    didWrapMeasureHeight = USE_CONSTRAINTS_HELPER;
                                }
                                childWidthMeasureSpec = getChildMeasureSpec(i2, heightPadding, height);
                            } else {
                                childWidthMeasureSpec = getChildMeasureSpec(i2, heightPadding, -1);
                            }
                            child.measure(childWidthMeasureSpec2, childWidthMeasureSpec);
                            if (constraintLayout.mMetrics != null) {
                                metrics = constraintLayout.mMetrics;
                                metrics.measures++;
                            }
                            if (width != -2) {
                            }
                            widget.setWidthWrapContent(width != -2 ? USE_CONSTRAINTS_HELPER : false);
                            if (height != -2) {
                            }
                            widget.setHeightWrapContent(height != -2 ? USE_CONSTRAINTS_HELPER : false);
                            width = child.getMeasuredWidth();
                            height = child.getMeasuredHeight();
                        }
                        widget.setWidth(width);
                        widget.setHeight(height);
                        if (didWrapMeasureWidth) {
                            widget.setWrapWidth(width);
                        }
                        if (didWrapMeasureHeight) {
                            widget.setWrapHeight(height);
                        }
                        if (params.needsBaseline) {
                            baseline = child.getBaseline();
                            if (baseline != -1) {
                                widget.setBaselineDistance(baseline);
                            }
                        }
                    }
                }
            }
            i3++;
            constraintLayout = this;
            i = parentWidthSpec;
        }
    }

    private void updatePostMeasures() {
        int i;
        int widgetsCount = getChildCount();
        int i2 = 0;
        for (i = 0; i < widgetsCount; i++) {
            View child = getChildAt(i);
            if (child instanceof Placeholder) {
                ((Placeholder) child).updatePostMeasure(this);
            }
        }
        i = this.mConstraintHelpers.size();
        if (i > 0) {
            while (i2 < i) {
                ((ConstraintHelper) this.mConstraintHelpers.get(i2)).updatePostMeasure(this);
                i2++;
            }
        }
    }

    private void internalMeasureDimensions(int parentWidthSpec, int parentHeightSpec) {
        int width;
        boolean didWrapMeasureWidth;
        boolean didWrapMeasureHeight;
        int childHeightMeasureSpec;
        int height;
        int widthPadding;
        int widgetsCount;
        ConstraintLayout constraintLayout = this;
        int i = parentWidthSpec;
        int i2 = parentHeightSpec;
        int heightPadding = getPaddingTop() + getPaddingBottom();
        int widthPadding2 = getPaddingLeft() + getPaddingRight();
        int widgetsCount2 = getChildCount();
        int i3 = 0;
        while (true) {
            int i4 = 8;
            if (i3 >= widgetsCount2) {
                break;
            }
            View child = getChildAt(i3);
            if (child.getVisibility() != 8) {
                LayoutParams params = (LayoutParams) child.getLayoutParams();
                ConstraintWidget widget = params.widget;
                if (!params.isGuideline) {
                    if (!params.isHelper) {
                        widget.setVisibility(child.getVisibility());
                        width = params.width;
                        int height2 = params.height;
                        if (width != 0) {
                            if (height2 != 0) {
                                didWrapMeasureWidth = false;
                                didWrapMeasureHeight = false;
                                if (width == -2) {
                                    didWrapMeasureWidth = USE_CONSTRAINTS_HELPER;
                                }
                                int childWidthMeasureSpec = getChildMeasureSpec(i, widthPadding2, width);
                                if (height2 == -2) {
                                    didWrapMeasureHeight = USE_CONSTRAINTS_HELPER;
                                }
                                childHeightMeasureSpec = getChildMeasureSpec(i2, heightPadding, height2);
                                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
                                if (constraintLayout.mMetrics != null) {
                                    Metrics metrics = constraintLayout.mMetrics;
                                    metrics.measures++;
                                } else {
                                    int i5 = childHeightMeasureSpec;
                                }
                                widget.setWidthWrapContent(width == -2 ? USE_CONSTRAINTS_HELPER : false);
                                widget.setHeightWrapContent(height2 == -2 ? USE_CONSTRAINTS_HELPER : false);
                                width = child.getMeasuredWidth();
                                height = child.getMeasuredHeight();
                                widget.setWidth(width);
                                widget.setHeight(height);
                                if (didWrapMeasureWidth) {
                                    widget.setWrapWidth(width);
                                }
                                if (didWrapMeasureHeight) {
                                    widget.setWrapHeight(height);
                                }
                                if (params.needsBaseline) {
                                    int baseline = child.getBaseline();
                                    if (baseline != -1) {
                                        widget.setBaselineDistance(baseline);
                                    }
                                }
                                if (params.horizontalDimensionFixed && params.verticalDimensionFixed) {
                                    widget.getResolutionWidth().resolve(width);
                                    widget.getResolutionHeight().resolve(height);
                                }
                            }
                        }
                        widget.getResolutionWidth().invalidate();
                        widget.getResolutionHeight().invalidate();
                    }
                }
            }
            i3++;
        }
        constraintLayout.mLayoutWidget.solveGraph();
        width = 0;
        while (width < widgetsCount2) {
            int heightPadding2;
            int i6;
            View child2 = constraintLayout.getChildAt(width);
            if (child2.getVisibility() != i4) {
                LayoutParams params2 = (LayoutParams) child2.getLayoutParams();
                ConstraintWidget widget2 = params2.widget;
                if (params2.isGuideline) {
                    heightPadding2 = heightPadding;
                    widthPadding = widthPadding2;
                    widgetsCount = widgetsCount2;
                    i6 = width;
                } else if (!params2.isHelper) {
                    widget2.setVisibility(child2.getVisibility());
                    childWidthMeasureSpec = params2.width;
                    childHeightMeasureSpec = params2.height;
                    if (childWidthMeasureSpec == 0 || childHeightMeasureSpec == 0) {
                        ResolutionAnchor left = widget2.getAnchor(Type.LEFT).getResolutionNode();
                        ResolutionAnchor right = widget2.getAnchor(Type.RIGHT).getResolutionNode();
                        boolean bothHorizontal = (widget2.getAnchor(Type.LEFT).getTarget() == null || widget2.getAnchor(Type.RIGHT).getTarget() == null) ? false : USE_CONSTRAINTS_HELPER;
                        ResolutionAnchor top = widget2.getAnchor(Type.TOP).getResolutionNode();
                        widgetsCount = widgetsCount2;
                        widgetsCount2 = widget2.getAnchor(Type.BOTTOM).getResolutionNode();
                        i6 = width;
                        width = (widget2.getAnchor(Type.TOP).getTarget() == null || widget2.getAnchor(Type.BOTTOM).getTarget() == null) ? 0 : 1;
                        if (childWidthMeasureSpec == 0 && childHeightMeasureSpec == 0 && bothHorizontal && width != 0) {
                            heightPadding2 = heightPadding;
                            widthPadding = widthPadding2;
                        } else {
                            int childWidthMeasureSpec2;
                            boolean resolveHeight;
                            int childHeightMeasureSpec2;
                            View child3;
                            Metrics metrics2;
                            didWrapMeasureWidth = false;
                            didWrapMeasureHeight = false;
                            LayoutParams params3 = params2;
                            View child4 = child2;
                            boolean resolveWidth = constraintLayout.mLayoutWidget.getHorizontalDimensionBehaviour() != DimensionBehaviour.WRAP_CONTENT ? USE_CONSTRAINTS_HELPER : false;
                            boolean resolveHeight2 = constraintLayout.mLayoutWidget.getVerticalDimensionBehaviour() != DimensionBehaviour.WRAP_CONTENT ? USE_CONSTRAINTS_HELPER : false;
                            if (!resolveWidth) {
                                widget2.getResolutionWidth().invalidate();
                            }
                            if (!resolveHeight2) {
                                widget2.getResolutionHeight().invalidate();
                            }
                            if (childWidthMeasureSpec == 0) {
                                if (resolveWidth && widget2.isSpreadWidth() && bothHorizontal && left.isResolved() && right.isResolved()) {
                                    childWidthMeasureSpec = (int) (right.getResolvedValue() - left.getResolvedValue());
                                    widget2.getResolutionWidth().resolve(childWidthMeasureSpec);
                                    height = getChildMeasureSpec(i, widthPadding2, childWidthMeasureSpec);
                                } else {
                                    childWidthMeasureSpec2 = getChildMeasureSpec(i, widthPadding2, -2);
                                    didWrapMeasureWidth = USE_CONSTRAINTS_HELPER;
                                    resolveWidth = false;
                                    height = childWidthMeasureSpec2;
                                    if (childHeightMeasureSpec != 0) {
                                        resolveHeight = resolveHeight2;
                                        if (childHeightMeasureSpec == -1) {
                                            childWidthMeasureSpec2 = getChildMeasureSpec(i2, heightPadding, -1);
                                        } else {
                                            if (childHeightMeasureSpec == -2) {
                                                didWrapMeasureHeight = USE_CONSTRAINTS_HELPER;
                                            }
                                            childHeightMeasureSpec2 = getChildMeasureSpec(i2, heightPadding, childHeightMeasureSpec);
                                            child3 = child4;
                                            child3.measure(height, childHeightMeasureSpec2);
                                            if (this.mMetrics != null) {
                                                heightPadding2 = heightPadding;
                                                widthPadding = widthPadding2;
                                            } else {
                                                metrics2 = constraintLayout.mMetrics;
                                                heightPadding2 = heightPadding;
                                                widthPadding = widthPadding2;
                                                metrics2.measures++;
                                            }
                                            if (childWidthMeasureSpec == -2) {
                                            }
                                            widget2.setWidthWrapContent(childWidthMeasureSpec == -2 ? 1 : 0);
                                            if (childHeightMeasureSpec == -2) {
                                            }
                                            widget2.setHeightWrapContent(childHeightMeasureSpec == -2 ? 1 : 0);
                                            heightPadding = child3.getMeasuredWidth();
                                            widthPadding2 = child3.getMeasuredHeight();
                                            widget2.setWidth(heightPadding);
                                            widget2.setHeight(widthPadding2);
                                            if (didWrapMeasureWidth) {
                                                widget2.setWrapWidth(heightPadding);
                                            }
                                            if (didWrapMeasureHeight) {
                                                widget2.setWrapHeight(widthPadding2);
                                            }
                                            if (resolveWidth) {
                                                widget2.getResolutionWidth().remove();
                                            } else {
                                                widget2.getResolutionWidth().resolve(heightPadding);
                                            }
                                            if (resolveHeight) {
                                                widget2.getResolutionHeight().remove();
                                            } else {
                                                widget2.getResolutionHeight().resolve(widthPadding2);
                                            }
                                            if (!params3.needsBaseline) {
                                                childHeightMeasureSpec = child3.getBaseline();
                                                if (childHeightMeasureSpec == -1) {
                                                    widget2.setBaselineDistance(childHeightMeasureSpec);
                                                }
                                            }
                                        }
                                    } else if (!resolveHeight2 && widget2.isSpreadHeight() && width != 0 && top.isResolved() && widgetsCount2.isResolved()) {
                                        resolveHeight = resolveHeight2;
                                        childHeightMeasureSpec = (int) (widgetsCount2.getResolvedValue() - top.getResolvedValue());
                                        widget2.getResolutionHeight().resolve(childHeightMeasureSpec);
                                        childHeightMeasureSpec2 = getChildMeasureSpec(i2, heightPadding, childHeightMeasureSpec);
                                        child3 = child4;
                                        child3.measure(height, childHeightMeasureSpec2);
                                        if (this.mMetrics != null) {
                                            metrics2 = constraintLayout.mMetrics;
                                            heightPadding2 = heightPadding;
                                            widthPadding = widthPadding2;
                                            metrics2.measures++;
                                        } else {
                                            heightPadding2 = heightPadding;
                                            widthPadding = widthPadding2;
                                        }
                                        widget2.setWidthWrapContent(childWidthMeasureSpec == -2 ? 1 : 0);
                                        widget2.setHeightWrapContent(childHeightMeasureSpec == -2 ? 1 : 0);
                                        heightPadding = child3.getMeasuredWidth();
                                        widthPadding2 = child3.getMeasuredHeight();
                                        widget2.setWidth(heightPadding);
                                        widget2.setHeight(widthPadding2);
                                        if (didWrapMeasureWidth) {
                                            widget2.setWrapWidth(heightPadding);
                                        }
                                        if (didWrapMeasureHeight) {
                                            widget2.setWrapHeight(widthPadding2);
                                        }
                                        if (resolveWidth) {
                                            widget2.getResolutionWidth().resolve(heightPadding);
                                        } else {
                                            widget2.getResolutionWidth().remove();
                                        }
                                        if (resolveHeight) {
                                            widget2.getResolutionHeight().resolve(widthPadding2);
                                        } else {
                                            widget2.getResolutionHeight().remove();
                                        }
                                        if (!params3.needsBaseline) {
                                            childHeightMeasureSpec = child3.getBaseline();
                                            if (childHeightMeasureSpec == -1) {
                                                widget2.setBaselineDistance(childHeightMeasureSpec);
                                            }
                                        }
                                    } else {
                                        childWidthMeasureSpec2 = getChildMeasureSpec(i2, heightPadding, true);
                                        didWrapMeasureHeight = USE_CONSTRAINTS_HELPER;
                                        resolveHeight = false;
                                    }
                                    childHeightMeasureSpec2 = childWidthMeasureSpec2;
                                    child3 = child4;
                                    child3.measure(height, childHeightMeasureSpec2);
                                    if (this.mMetrics != null) {
                                        metrics2 = constraintLayout.mMetrics;
                                        heightPadding2 = heightPadding;
                                        widthPadding = widthPadding2;
                                        metrics2.measures++;
                                    } else {
                                        heightPadding2 = heightPadding;
                                        widthPadding = widthPadding2;
                                    }
                                    if (childWidthMeasureSpec == -2) {
                                    }
                                    widget2.setWidthWrapContent(childWidthMeasureSpec == -2 ? 1 : 0);
                                    if (childHeightMeasureSpec == -2) {
                                    }
                                    widget2.setHeightWrapContent(childHeightMeasureSpec == -2 ? 1 : 0);
                                    heightPadding = child3.getMeasuredWidth();
                                    widthPadding2 = child3.getMeasuredHeight();
                                    widget2.setWidth(heightPadding);
                                    widget2.setHeight(widthPadding2);
                                    if (didWrapMeasureWidth) {
                                        widget2.setWrapWidth(heightPadding);
                                    }
                                    if (didWrapMeasureHeight) {
                                        widget2.setWrapHeight(widthPadding2);
                                    }
                                    if (resolveWidth) {
                                        widget2.getResolutionWidth().resolve(heightPadding);
                                    } else {
                                        widget2.getResolutionWidth().remove();
                                    }
                                    if (resolveHeight) {
                                        widget2.getResolutionHeight().resolve(widthPadding2);
                                    } else {
                                        widget2.getResolutionHeight().remove();
                                    }
                                    if (!params3.needsBaseline) {
                                        childHeightMeasureSpec = child3.getBaseline();
                                        if (childHeightMeasureSpec == -1) {
                                            widget2.setBaselineDistance(childHeightMeasureSpec);
                                        }
                                    }
                                }
                            } else if (childWidthMeasureSpec == -1) {
                                childWidthMeasureSpec2 = getChildMeasureSpec(i, widthPadding2, -1);
                                height = childWidthMeasureSpec2;
                                if (childHeightMeasureSpec != 0) {
                                    resolveHeight = resolveHeight2;
                                    if (childHeightMeasureSpec == -1) {
                                        if (childHeightMeasureSpec == -2) {
                                            didWrapMeasureHeight = USE_CONSTRAINTS_HELPER;
                                        }
                                        childHeightMeasureSpec2 = getChildMeasureSpec(i2, heightPadding, childHeightMeasureSpec);
                                        child3 = child4;
                                        child3.measure(height, childHeightMeasureSpec2);
                                        if (this.mMetrics != null) {
                                            heightPadding2 = heightPadding;
                                            widthPadding = widthPadding2;
                                        } else {
                                            metrics2 = constraintLayout.mMetrics;
                                            heightPadding2 = heightPadding;
                                            widthPadding = widthPadding2;
                                            metrics2.measures++;
                                        }
                                        if (childWidthMeasureSpec == -2) {
                                        }
                                        widget2.setWidthWrapContent(childWidthMeasureSpec == -2 ? 1 : 0);
                                        if (childHeightMeasureSpec == -2) {
                                        }
                                        widget2.setHeightWrapContent(childHeightMeasureSpec == -2 ? 1 : 0);
                                        heightPadding = child3.getMeasuredWidth();
                                        widthPadding2 = child3.getMeasuredHeight();
                                        widget2.setWidth(heightPadding);
                                        widget2.setHeight(widthPadding2);
                                        if (didWrapMeasureWidth) {
                                            widget2.setWrapWidth(heightPadding);
                                        }
                                        if (didWrapMeasureHeight) {
                                            widget2.setWrapHeight(widthPadding2);
                                        }
                                        if (resolveWidth) {
                                            widget2.getResolutionWidth().remove();
                                        } else {
                                            widget2.getResolutionWidth().resolve(heightPadding);
                                        }
                                        if (resolveHeight) {
                                            widget2.getResolutionHeight().remove();
                                        } else {
                                            widget2.getResolutionHeight().resolve(widthPadding2);
                                        }
                                        if (!params3.needsBaseline) {
                                            childHeightMeasureSpec = child3.getBaseline();
                                            if (childHeightMeasureSpec == -1) {
                                                widget2.setBaselineDistance(childHeightMeasureSpec);
                                            }
                                        }
                                    } else {
                                        childWidthMeasureSpec2 = getChildMeasureSpec(i2, heightPadding, -1);
                                    }
                                } else {
                                    if (!resolveHeight2) {
                                    }
                                    childWidthMeasureSpec2 = getChildMeasureSpec(i2, heightPadding, true);
                                    didWrapMeasureHeight = USE_CONSTRAINTS_HELPER;
                                    resolveHeight = false;
                                }
                                childHeightMeasureSpec2 = childWidthMeasureSpec2;
                                child3 = child4;
                                child3.measure(height, childHeightMeasureSpec2);
                                if (this.mMetrics != null) {
                                    metrics2 = constraintLayout.mMetrics;
                                    heightPadding2 = heightPadding;
                                    widthPadding = widthPadding2;
                                    metrics2.measures++;
                                } else {
                                    heightPadding2 = heightPadding;
                                    widthPadding = widthPadding2;
                                }
                                if (childWidthMeasureSpec == -2) {
                                }
                                widget2.setWidthWrapContent(childWidthMeasureSpec == -2 ? 1 : 0);
                                if (childHeightMeasureSpec == -2) {
                                }
                                widget2.setHeightWrapContent(childHeightMeasureSpec == -2 ? 1 : 0);
                                heightPadding = child3.getMeasuredWidth();
                                widthPadding2 = child3.getMeasuredHeight();
                                widget2.setWidth(heightPadding);
                                widget2.setHeight(widthPadding2);
                                if (didWrapMeasureWidth) {
                                    widget2.setWrapWidth(heightPadding);
                                }
                                if (didWrapMeasureHeight) {
                                    widget2.setWrapHeight(widthPadding2);
                                }
                                if (resolveWidth) {
                                    widget2.getResolutionWidth().resolve(heightPadding);
                                } else {
                                    widget2.getResolutionWidth().remove();
                                }
                                if (resolveHeight) {
                                    widget2.getResolutionHeight().resolve(widthPadding2);
                                } else {
                                    widget2.getResolutionHeight().remove();
                                }
                                if (!params3.needsBaseline) {
                                    childHeightMeasureSpec = child3.getBaseline();
                                    if (childHeightMeasureSpec == -1) {
                                        widget2.setBaselineDistance(childHeightMeasureSpec);
                                    }
                                }
                            } else {
                                if (childWidthMeasureSpec == -2) {
                                    didWrapMeasureWidth = USE_CONSTRAINTS_HELPER;
                                }
                                height = getChildMeasureSpec(i, widthPadding2, childWidthMeasureSpec);
                            }
                            childWidthMeasureSpec2 = height;
                            height = childWidthMeasureSpec2;
                            if (childHeightMeasureSpec != 0) {
                                if (!resolveHeight2) {
                                }
                                childWidthMeasureSpec2 = getChildMeasureSpec(i2, heightPadding, true);
                                didWrapMeasureHeight = USE_CONSTRAINTS_HELPER;
                                resolveHeight = false;
                            } else {
                                resolveHeight = resolveHeight2;
                                if (childHeightMeasureSpec == -1) {
                                    childWidthMeasureSpec2 = getChildMeasureSpec(i2, heightPadding, -1);
                                } else {
                                    if (childHeightMeasureSpec == -2) {
                                        didWrapMeasureHeight = USE_CONSTRAINTS_HELPER;
                                    }
                                    childHeightMeasureSpec2 = getChildMeasureSpec(i2, heightPadding, childHeightMeasureSpec);
                                    child3 = child4;
                                    child3.measure(height, childHeightMeasureSpec2);
                                    if (this.mMetrics != null) {
                                        heightPadding2 = heightPadding;
                                        widthPadding = widthPadding2;
                                    } else {
                                        metrics2 = constraintLayout.mMetrics;
                                        heightPadding2 = heightPadding;
                                        widthPadding = widthPadding2;
                                        metrics2.measures++;
                                    }
                                    if (childWidthMeasureSpec == -2) {
                                    }
                                    widget2.setWidthWrapContent(childWidthMeasureSpec == -2 ? 1 : 0);
                                    if (childHeightMeasureSpec == -2) {
                                    }
                                    widget2.setHeightWrapContent(childHeightMeasureSpec == -2 ? 1 : 0);
                                    heightPadding = child3.getMeasuredWidth();
                                    widthPadding2 = child3.getMeasuredHeight();
                                    widget2.setWidth(heightPadding);
                                    widget2.setHeight(widthPadding2);
                                    if (didWrapMeasureWidth) {
                                        widget2.setWrapWidth(heightPadding);
                                    }
                                    if (didWrapMeasureHeight) {
                                        widget2.setWrapHeight(widthPadding2);
                                    }
                                    if (resolveWidth) {
                                        widget2.getResolutionWidth().remove();
                                    } else {
                                        widget2.getResolutionWidth().resolve(heightPadding);
                                    }
                                    if (resolveHeight) {
                                        widget2.getResolutionHeight().remove();
                                    } else {
                                        widget2.getResolutionHeight().resolve(widthPadding2);
                                    }
                                    if (!params3.needsBaseline) {
                                        childHeightMeasureSpec = child3.getBaseline();
                                        if (childHeightMeasureSpec == -1) {
                                            widget2.setBaselineDistance(childHeightMeasureSpec);
                                        }
                                    }
                                }
                            }
                            childHeightMeasureSpec2 = childWidthMeasureSpec2;
                            child3 = child4;
                            child3.measure(height, childHeightMeasureSpec2);
                            if (this.mMetrics != null) {
                                metrics2 = constraintLayout.mMetrics;
                                heightPadding2 = heightPadding;
                                widthPadding = widthPadding2;
                                metrics2.measures++;
                            } else {
                                heightPadding2 = heightPadding;
                                widthPadding = widthPadding2;
                            }
                            if (childWidthMeasureSpec == -2) {
                            }
                            widget2.setWidthWrapContent(childWidthMeasureSpec == -2 ? 1 : 0);
                            if (childHeightMeasureSpec == -2) {
                            }
                            widget2.setHeightWrapContent(childHeightMeasureSpec == -2 ? 1 : 0);
                            heightPadding = child3.getMeasuredWidth();
                            widthPadding2 = child3.getMeasuredHeight();
                            widget2.setWidth(heightPadding);
                            widget2.setHeight(widthPadding2);
                            if (didWrapMeasureWidth) {
                                widget2.setWrapWidth(heightPadding);
                            }
                            if (didWrapMeasureHeight) {
                                widget2.setWrapHeight(widthPadding2);
                            }
                            if (resolveWidth) {
                                widget2.getResolutionWidth().resolve(heightPadding);
                            } else {
                                widget2.getResolutionWidth().remove();
                            }
                            if (resolveHeight) {
                                widget2.getResolutionHeight().resolve(widthPadding2);
                            } else {
                                widget2.getResolutionHeight().remove();
                            }
                            if (!params3.needsBaseline) {
                                childHeightMeasureSpec = child3.getBaseline();
                                if (childHeightMeasureSpec == -1) {
                                    widget2.setBaselineDistance(childHeightMeasureSpec);
                                }
                            }
                        }
                    }
                }
                width = i6 + 1;
                widgetsCount2 = widgetsCount;
                heightPadding = heightPadding2;
                widthPadding2 = widthPadding;
                i = parentWidthSpec;
                i2 = parentHeightSpec;
                i4 = 8;
            }
            heightPadding2 = heightPadding;
            widthPadding = widthPadding2;
            widgetsCount = widgetsCount2;
            i6 = width;
            width = i6 + 1;
            widgetsCount2 = widgetsCount;
            heightPadding = heightPadding2;
            widthPadding2 = widthPadding;
            i = parentWidthSpec;
            i2 = parentHeightSpec;
            i4 = 8;
        }
        widthPadding = widthPadding2;
        widgetsCount = widgetsCount2;
    }

    public void fillMetrics(Metrics metrics) {
        this.mMetrics = metrics;
        this.mLayoutWidget.fillMetrics(metrics);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height;
        int i;
        int i2 = widthMeasureSpec;
        int i3 = heightMeasureSpec;
        long time = System.currentTimeMillis();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        this.mLayoutWidget.setX(paddingLeft);
        this.mLayoutWidget.setY(paddingTop);
        this.mLayoutWidget.setMaxWidth(this.mMaxWidth);
        this.mLayoutWidget.setMaxHeight(this.mMaxHeight);
        if (VERSION.SDK_INT >= 17) {
            r0.mLayoutWidget.setRtl(getLayoutDirection() == 1 ? USE_CONSTRAINTS_HELPER : false);
        }
        setSelfDimensionBehaviour(widthMeasureSpec, heightMeasureSpec);
        int startingWidth = r0.mLayoutWidget.getWidth();
        int startingHeight = r0.mLayoutWidget.getHeight();
        boolean runAnalyzer = false;
        if (r0.mDirtyHierarchy) {
            r0.mDirtyHierarchy = false;
            updateHierarchy();
            runAnalyzer = USE_CONSTRAINTS_HELPER;
        }
        boolean optimiseDimensions = (r0.mOptimizationLevel & 8) == 8 ? USE_CONSTRAINTS_HELPER : false;
        if (optimiseDimensions) {
            r0.mLayoutWidget.preOptimize();
            r0.mLayoutWidget.optimizeForDimensions(startingWidth, startingHeight);
            internalMeasureDimensions(widthMeasureSpec, heightMeasureSpec);
        } else {
            internalMeasureChildren(widthMeasureSpec, heightMeasureSpec);
        }
        updatePostMeasures();
        if (getChildCount() > 0 && runAnalyzer) {
            Analyzer.determineGroups(r0.mLayoutWidget);
        }
        if (r0.mLayoutWidget.mGroupsWrapOptimized) {
            if (r0.mLayoutWidget.mHorizontalWrapOptimized && widthMode == Integer.MIN_VALUE) {
                if (r0.mLayoutWidget.mWrapFixedWidth < widthSize) {
                    r0.mLayoutWidget.setWidth(r0.mLayoutWidget.mWrapFixedWidth);
                }
                r0.mLayoutWidget.setHorizontalDimensionBehaviour(DimensionBehaviour.FIXED);
            }
            if (r0.mLayoutWidget.mVerticalWrapOptimized && heightMode == Integer.MIN_VALUE) {
                if (r0.mLayoutWidget.mWrapFixedHeight < heightSize) {
                    r0.mLayoutWidget.setHeight(r0.mLayoutWidget.mWrapFixedHeight);
                }
                r0.mLayoutWidget.setVerticalDimensionBehaviour(DimensionBehaviour.FIXED);
            }
        }
        int REMEASURES_A = 0;
        if ((r0.mOptimizationLevel & 32) == 32) {
            int width = r0.mLayoutWidget.getWidth();
            height = r0.mLayoutWidget.getHeight();
            if (r0.mLastMeasureWidth == width || widthMode != ErrorDialogData.SUPPRESSED) {
                i = 0;
            } else {
                i = 0;
                Analyzer.setPosition(r0.mLayoutWidget.mWidgetGroups, 0, width);
            }
            if (r0.mLastMeasureHeight != height && heightMode == ErrorDialogData.SUPPRESSED) {
                Analyzer.setPosition(r0.mLayoutWidget.mWidgetGroups, 1, height);
            }
            if (r0.mLayoutWidget.mHorizontalWrapOptimized && r0.mLayoutWidget.mWrapFixedWidth > widthSize) {
                Analyzer.setPosition(r0.mLayoutWidget.mWidgetGroups, 0, widthSize);
            }
            if (r0.mLayoutWidget.mVerticalWrapOptimized && r0.mLayoutWidget.mWrapFixedHeight > heightSize) {
                Analyzer.setPosition(r0.mLayoutWidget.mWidgetGroups, 1, heightSize);
            }
        } else {
            i = 0;
        }
        if (getChildCount() > 0) {
            solveLinearSystem("First pass");
        }
        int sizeDependentWidgetsCount = r0.mVariableDimensionsWidgets.size();
        height = getPaddingBottom() + paddingTop;
        int widthPadding = paddingLeft + getPaddingRight();
        int childState = 0;
        int paddingTop2;
        int startingWidth2;
        if (sizeDependentWidgetsCount > 0) {
            ConstraintWidget widget;
            int sizeDependentWidgetsCount2;
            int startingWidth3;
            int startingHeight2;
            boolean needSolverPass = false;
            boolean containerWrapWidth = r0.mLayoutWidget.getHorizontalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT ? USE_CONSTRAINTS_HELPER : false;
            widthMode = r0.mLayoutWidget.getVerticalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT ? 1 : 0;
            paddingLeft = childState;
            heightMode = Math.max(r0.mLayoutWidget.getWidth(), r0.mMinWidth);
            heightSize = Math.max(r0.mLayoutWidget.getHeight(), r0.mMinHeight);
            widthSize = 0;
            while (widthSize < sizeDependentWidgetsCount) {
                int i4;
                paddingTop2 = paddingTop;
                widget = (ConstraintWidget) r0.mVariableDimensionsWidgets.get(widthSize);
                sizeDependentWidgetsCount2 = sizeDependentWidgetsCount;
                View sizeDependentWidgetsCount3 = (View) widget.getCompanionWidget();
                if (sizeDependentWidgetsCount3 == null) {
                    i4 = widthSize;
                    startingWidth3 = startingWidth;
                    startingHeight2 = startingHeight;
                } else {
                    startingHeight2 = startingHeight;
                    LayoutParams params = (LayoutParams) sizeDependentWidgetsCount3.getLayoutParams();
                    startingWidth3 = startingWidth;
                    if (params.isHelper) {
                        i4 = widthSize;
                    } else if (params.isGuideline) {
                        i4 = widthSize;
                    } else {
                        i4 = widthSize;
                        if (sizeDependentWidgetsCount3.getVisibility() != 8) {
                            if (!optimiseDimensions || widget.getResolutionWidth().isResolved() == 0 || widget.getResolutionHeight().isResolved() == 0) {
                                int widthSpec = 0;
                                int heightSpec = 0;
                                if (params.width != -2 || params.horizontalDimensionFixed == 0) {
                                    widthSize = MeasureSpec.makeMeasureSpec(widget.getWidth(), ErrorDialogData.SUPPRESSED);
                                } else {
                                    widthSize = getChildMeasureSpec(i2, widthPadding, params.width);
                                }
                                if (params.height == -2 && params.verticalDimensionFixed) {
                                    i2 = getChildMeasureSpec(i3, height, params.height);
                                } else {
                                    i2 = MeasureSpec.makeMeasureSpec(widget.getHeight(), ErrorDialogData.SUPPRESSED);
                                }
                                sizeDependentWidgetsCount3.measure(widthSize, i2);
                                if (r0.mMetrics != null) {
                                    Metrics metrics = r0.mMetrics;
                                    metrics.additionalMeasures++;
                                }
                                REMEASURES_A++;
                                i2 = sizeDependentWidgetsCount3.getMeasuredWidth();
                                i3 = sizeDependentWidgetsCount3.getMeasuredHeight();
                                if (i2 != widget.getWidth()) {
                                    widget.setWidth(i2);
                                    if (optimiseDimensions) {
                                        widget.getResolutionWidth().resolve(i2);
                                    }
                                    if (!containerWrapWidth || widget.getRight() <= heightMode) {
                                    } else {
                                        heightMode = Math.max(heightMode, widget.getRight() + widget.getAnchor(Type.RIGHT).getMargin());
                                    }
                                    needSolverPass = USE_CONSTRAINTS_HELPER;
                                }
                                if (i3 != widget.getHeight()) {
                                    widget.setHeight(i3);
                                    if (optimiseDimensions) {
                                        widget.getResolutionHeight().resolve(i3);
                                    }
                                    if (widthMode != 0 && widget.getBottom() > heightSize) {
                                        heightSize = Math.max(heightSize, widget.getBottom() + widget.getAnchor(Type.BOTTOM).getMargin());
                                    }
                                    needSolverPass = USE_CONSTRAINTS_HELPER;
                                }
                                if (params.needsBaseline) {
                                    i2 = sizeDependentWidgetsCount3.getBaseline();
                                    if (!(i2 == -1 || i2 == widget.getBaselineDistance())) {
                                        widget.setBaselineDistance(i2);
                                        needSolverPass = USE_CONSTRAINTS_HELPER;
                                    }
                                }
                                if (VERSION.SDK_INT >= 11) {
                                    paddingLeft = combineMeasuredStates(paddingLeft, sizeDependentWidgetsCount3.getMeasuredState());
                                }
                            }
                        }
                    }
                }
                widthSize = i4 + 1;
                paddingTop = paddingTop2;
                sizeDependentWidgetsCount = sizeDependentWidgetsCount2;
                startingHeight = startingHeight2;
                startingWidth = startingWidth3;
                i2 = widthMeasureSpec;
                i3 = heightMeasureSpec;
            }
            sizeDependentWidgetsCount2 = sizeDependentWidgetsCount;
            paddingTop2 = paddingTop;
            startingWidth3 = startingWidth;
            startingHeight2 = startingHeight;
            if (needSolverPass) {
                i3 = startingWidth3;
                r0.mLayoutWidget.setWidth(i3);
                r0.mLayoutWidget.setHeight(startingHeight2);
                if (optimiseDimensions) {
                    r0.mLayoutWidget.solveGraph();
                }
                solveLinearSystem("2nd pass");
                boolean needSolverPass2 = false;
                if (r0.mLayoutWidget.getWidth() < heightMode) {
                    r0.mLayoutWidget.setWidth(heightMode);
                    needSolverPass2 = USE_CONSTRAINTS_HELPER;
                }
                if (r0.mLayoutWidget.getHeight() < heightSize) {
                    r0.mLayoutWidget.setHeight(heightSize);
                    needSolverPass2 = USE_CONSTRAINTS_HELPER;
                }
                if (needSolverPass2) {
                    solveLinearSystem("3rd pass");
                }
            } else {
                sizeDependentWidgetsCount = startingHeight2;
                i3 = startingWidth3;
            }
            int i5 = 0;
            while (true) {
                i2 = i5;
                widthSize = sizeDependentWidgetsCount2;
                if (i2 >= widthSize) {
                    break;
                }
                boolean containerWrapWidth2;
                widget = (ConstraintWidget) r0.mVariableDimensionsWidgets.get(i2);
                View startingWidth4 = (View) widget.getCompanionWidget();
                if (startingWidth4 == null) {
                    startingWidth2 = i3;
                } else {
                    startingWidth2 = i3;
                    if (!(startingWidth4.getMeasuredWidth() == widget.getWidth() && startingWidth4.getMeasuredHeight() == widget.getHeight())) {
                        if (widget.getVisibility() != 8) {
                            i3 = MeasureSpec.makeMeasureSpec(widget.getWidth(), ErrorDialogData.SUPPRESSED);
                            containerWrapWidth2 = containerWrapWidth;
                            containerWrapWidth = MeasureSpec.makeMeasureSpec(widget.getHeight(), ErrorDialogData.SUPPRESSED);
                            startingWidth4.measure(i3, containerWrapWidth);
                            int widthSpec2;
                            boolean heightSpec2;
                            if (r0.mMetrics != null) {
                                Metrics metrics2 = r0.mMetrics;
                                widthSpec2 = i3;
                                heightSpec2 = containerWrapWidth;
                                metrics2.additionalMeasures++;
                            } else {
                                widthSpec2 = i3;
                                heightSpec2 = containerWrapWidth;
                            }
                            i++;
                        } else {
                            containerWrapWidth2 = containerWrapWidth;
                        }
                        i5 = i2 + 1;
                        sizeDependentWidgetsCount2 = widthSize;
                        i3 = startingWidth2;
                        containerWrapWidth = containerWrapWidth2;
                    }
                }
                containerWrapWidth2 = containerWrapWidth;
                i5 = i2 + 1;
                sizeDependentWidgetsCount2 = widthSize;
                i3 = startingWidth2;
                containerWrapWidth = containerWrapWidth2;
            }
        } else {
            int i6 = widthSize;
            int i7 = heightMode;
            int i8 = heightSize;
            int i9 = paddingLeft;
            paddingTop2 = paddingTop;
            startingWidth2 = startingWidth;
            paddingLeft = childState;
        }
        i2 = r0.mLayoutWidget.getWidth() + widthPadding;
        i3 = r0.mLayoutWidget.getHeight() + height;
        if (VERSION.SDK_INT >= 11) {
            heightMode = resolveSizeAndState(i3, heightMeasureSpec, paddingLeft << 16) & ViewCompat.MEASURED_SIZE_MASK;
            widthMode = Math.min(r0.mMaxWidth, resolveSizeAndState(i2, widthMeasureSpec, paddingLeft) & ViewCompat.MEASURED_SIZE_MASK);
            heightMode = Math.min(r0.mMaxHeight, heightMode);
            if (r0.mLayoutWidget.isWidthMeasuredTooSmall()) {
                widthMode |= 16777216;
            }
            if (r0.mLayoutWidget.isHeightMeasuredTooSmall()) {
                heightMode |= 16777216;
            }
            setMeasuredDimension(widthMode, heightMode);
            r0.mLastMeasureWidth = widthMode;
            r0.mLastMeasureHeight = heightMode;
            return;
        }
        width = widthMeasureSpec;
        heightSize = heightMeasureSpec;
        setMeasuredDimension(i2, i3);
        r0.mLastMeasureWidth = i2;
        r0.mLastMeasureHeight = i3;
    }

    private void setSelfDimensionBehaviour(int widthMeasureSpec, int heightMeasureSpec) {
        ConstraintLayout constraintLayout = this;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightPadding = getPaddingTop() + getPaddingBottom();
        int widthPadding = getPaddingLeft() + getPaddingRight();
        DimensionBehaviour widthBehaviour = DimensionBehaviour.FIXED;
        DimensionBehaviour heightBehaviour = DimensionBehaviour.FIXED;
        int desiredWidth = 0;
        int desiredHeight = 0;
        android.view.ViewGroup.LayoutParams params = getLayoutParams();
        if (widthMode == Integer.MIN_VALUE) {
            widthBehaviour = DimensionBehaviour.WRAP_CONTENT;
            desiredWidth = widthSize;
        } else if (widthMode == 0) {
            widthBehaviour = DimensionBehaviour.WRAP_CONTENT;
        } else if (widthMode == ErrorDialogData.SUPPRESSED) {
            desiredWidth = Math.min(constraintLayout.mMaxWidth, widthSize) - widthPadding;
        }
        if (heightMode == Integer.MIN_VALUE) {
            heightBehaviour = DimensionBehaviour.WRAP_CONTENT;
            desiredHeight = heightSize;
        } else if (heightMode == 0) {
            heightBehaviour = DimensionBehaviour.WRAP_CONTENT;
        } else if (heightMode == ErrorDialogData.SUPPRESSED) {
            desiredHeight = Math.min(constraintLayout.mMaxHeight, heightSize) - heightPadding;
        }
        constraintLayout.mLayoutWidget.setMinWidth(0);
        constraintLayout.mLayoutWidget.setMinHeight(0);
        constraintLayout.mLayoutWidget.setHorizontalDimensionBehaviour(widthBehaviour);
        constraintLayout.mLayoutWidget.setWidth(desiredWidth);
        constraintLayout.mLayoutWidget.setVerticalDimensionBehaviour(heightBehaviour);
        constraintLayout.mLayoutWidget.setHeight(desiredHeight);
        constraintLayout.mLayoutWidget.setMinWidth((constraintLayout.mMinWidth - getPaddingLeft()) - getPaddingRight());
        constraintLayout.mLayoutWidget.setMinHeight((constraintLayout.mMinHeight - getPaddingTop()) - getPaddingBottom());
    }

    protected void solveLinearSystem(String reason) {
        this.mLayoutWidget.layout();
        if (this.mMetrics != null) {
            Metrics metrics = this.mMetrics;
            metrics.resolutions++;
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int i;
        ConstraintLayout constraintLayout = this;
        int widgetsCount = getChildCount();
        boolean isInEditMode = isInEditMode();
        int i2 = 0;
        for (i = 0; i < widgetsCount; i++) {
            View child = getChildAt(i);
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            ConstraintWidget widget = params.widget;
            if (child.getVisibility() != 8 || params.isGuideline || params.isHelper || isInEditMode) {
                if (!params.isInPlaceholder) {
                    int l = widget.getDrawX();
                    int t = widget.getDrawY();
                    int r = widget.getWidth() + l;
                    int b = widget.getHeight() + t;
                    child.layout(l, t, r, b);
                    if (child instanceof Placeholder) {
                        View content = ((Placeholder) child).getContent();
                        if (content != null) {
                            content.setVisibility(0);
                            content.layout(l, t, r, b);
                        }
                    }
                }
            }
        }
        i = constraintLayout.mConstraintHelpers.size();
        if (i > 0) {
            while (i2 < i) {
                ((ConstraintHelper) constraintLayout.mConstraintHelpers.get(i2)).updatePostLayout(this);
                i2++;
            }
        }
    }

    public void setOptimizationLevel(int level) {
        this.mLayoutWidget.setOptimizationLevel(level);
    }

    public int getOptimizationLevel() {
        return this.mLayoutWidget.getOptimizationLevel();
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public void setConstraintSet(ConstraintSet set) {
        this.mConstraintSet = set;
    }

    public View getViewById(int id) {
        return (View) this.mChildrenByIds.get(id);
    }

    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isInEditMode()) {
            int count = getChildCount();
            float cw = (float) getWidth();
            float ch = (float) getHeight();
            float ow = 1080.0f;
            int i = 0;
            int i2 = 0;
            while (i2 < count) {
                int count2;
                float cw2;
                float ch2;
                float ow2;
                View child = getChildAt(i2);
                if (child.getVisibility() == 8) {
                    count2 = count;
                    cw2 = cw;
                    ch2 = ch;
                    ow2 = ow;
                } else {
                    String tag = child.getTag();
                    if (tag != null && (tag instanceof String)) {
                        String[] split = tag.split(",");
                        if (split.length == 4) {
                            int x = Integer.parseInt(split[i]);
                            int y = Integer.parseInt(split[1]);
                            i = (int) ((((float) x) / ow) * cw);
                            x = (int) ((((float) y) / 1920.0f) * ch);
                            y = (int) ((((float) Integer.parseInt(split[2])) / ow) * cw);
                            int h = (int) ((((float) Integer.parseInt(split[3])) / 1920.0f) * ch);
                            Paint paint = new Paint();
                            count2 = count;
                            paint.setColor(SupportMenu.CATEGORY_MASK);
                            cw2 = cw;
                            ch2 = ch;
                            ow2 = ow;
                            Canvas canvas2 = canvas;
                            Paint paint2 = paint;
                            canvas2.drawLine((float) i, (float) x, (float) (i + y), (float) x, paint2);
                            canvas2.drawLine((float) (i + y), (float) x, (float) (i + y), (float) (x + h), paint2);
                            canvas2.drawLine((float) (i + y), (float) (x + h), (float) i, (float) (x + h), paint2);
                            canvas2.drawLine((float) i, (float) (x + h), (float) i, (float) x, paint2);
                            paint.setColor(-16711936);
                            canvas2.drawLine((float) i, (float) x, (float) (i + y), (float) (x + h), paint2);
                            canvas2.drawLine((float) i, (float) (x + h), (float) (i + y), (float) x, paint2);
                        }
                    }
                    count2 = count;
                    cw2 = cw;
                    ch2 = ch;
                    ow2 = ow;
                }
                i2++;
                count = count2;
                cw = cw2;
                ch = ch2;
                ow = ow2;
                i = 0;
            }
        }
        ConstraintLayout constraintLayout = this;
    }

    public void requestLayout() {
        super.requestLayout();
        this.mDirtyHierarchy = USE_CONSTRAINTS_HELPER;
        this.mLastMeasureWidth = -1;
        this.mLastMeasureHeight = -1;
        this.mLastMeasureWidthSize = -1;
        this.mLastMeasureHeightSize = -1;
        this.mLastMeasureWidthMode = 0;
        this.mLastMeasureHeightMode = 0;
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }
}
