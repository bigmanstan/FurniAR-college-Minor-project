package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.widgets.ConstraintAnchor.Strength;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;

public class ConstraintHorizontalLayout extends ConstraintWidgetContainer {
    private ContentAlignment mAlignment = ContentAlignment.MIDDLE;

    public enum ContentAlignment {
        BEGIN,
        MIDDLE,
        END,
        TOP,
        VERTICAL_MIDDLE,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public ConstraintHorizontalLayout(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public ConstraintHorizontalLayout(int width, int height) {
        super(width, height);
    }

    public void addToSolver(LinearSystem system) {
        if (this.mChildren.size() != 0) {
            ConstraintWidget previous = this;
            int mChildrenSize = this.mChildren.size();
            for (int i = 0; i < mChildrenSize; i++) {
                ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
                if (previous != this) {
                    widget.connect(Type.LEFT, previous, Type.RIGHT);
                    previous.connect(Type.RIGHT, widget, Type.LEFT);
                } else {
                    Strength strength = Strength.STRONG;
                    if (this.mAlignment == ContentAlignment.END) {
                        strength = Strength.WEAK;
                    }
                    Strength strength2 = strength;
                    widget.connect(Type.LEFT, previous, Type.LEFT, 0, strength2);
                }
                widget.connect(Type.TOP, (ConstraintWidget) this, Type.TOP);
                widget.connect(Type.BOTTOM, (ConstraintWidget) this, Type.BOTTOM);
                previous = widget;
            }
            if (previous != this) {
                Strength strength3 = Strength.STRONG;
                if (this.mAlignment == ContentAlignment.BEGIN) {
                    strength3 = Strength.WEAK;
                }
                previous.connect(Type.RIGHT, (ConstraintWidget) this, Type.RIGHT, 0, strength3);
            }
        }
        super.addToSolver(system);
    }
}
