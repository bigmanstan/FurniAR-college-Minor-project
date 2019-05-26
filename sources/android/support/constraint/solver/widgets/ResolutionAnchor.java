package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;

public class ResolutionAnchor extends ResolutionNode {
    public static final int BARRIER_CONNECTION = 5;
    public static final int CENTER_CONNECTION = 2;
    public static final int CHAIN_CONNECTION = 4;
    public static final int DIRECT_CONNECTION = 1;
    public static final int MATCH_CONNECTION = 3;
    public static final int UNCONNECTED = 0;
    float computedValue;
    private ResolutionDimension dimension = null;
    private int dimensionMultiplier = 1;
    ConstraintAnchor myAnchor;
    float offset;
    private ResolutionAnchor opposite;
    private ResolutionDimension oppositeDimension = null;
    private int oppositeDimensionMultiplier = 1;
    private float oppositeOffset;
    float resolvedOffset;
    ResolutionAnchor resolvedTarget;
    ResolutionAnchor target;
    int type = 0;

    public ResolutionAnchor(ConstraintAnchor anchor) {
        this.myAnchor = anchor;
    }

    public void remove(ResolutionDimension resolutionDimension) {
        if (this.dimension == resolutionDimension) {
            this.dimension = null;
            this.offset = (float) this.dimensionMultiplier;
        } else if (this.dimension == this.oppositeDimension) {
            this.oppositeDimension = null;
            this.oppositeOffset = (float) this.oppositeDimensionMultiplier;
        }
        resolve();
    }

    public String toString() {
        StringBuilder stringBuilder;
        if (this.state != 1) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("{ ");
            stringBuilder.append(this.myAnchor);
            stringBuilder.append(" UNRESOLVED} type: ");
            stringBuilder.append(sType(this.type));
            return stringBuilder.toString();
        } else if (this.resolvedTarget == this) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append(this.myAnchor);
            stringBuilder.append(", RESOLVED: ");
            stringBuilder.append(this.resolvedOffset);
            stringBuilder.append("]  type: ");
            stringBuilder.append(sType(this.type));
            return stringBuilder.toString();
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append(this.myAnchor);
            stringBuilder.append(", RESOLVED: ");
            stringBuilder.append(this.resolvedTarget);
            stringBuilder.append(":");
            stringBuilder.append(this.resolvedOffset);
            stringBuilder.append("] type: ");
            stringBuilder.append(sType(this.type));
            return stringBuilder.toString();
        }
    }

    public void resolve(ResolutionAnchor target, float offset) {
        if (this.state == 0 || !(this.resolvedTarget == target || this.resolvedOffset == offset)) {
            this.resolvedTarget = target;
            this.resolvedOffset = offset;
            if (this.state == 1) {
                invalidate();
            }
            didResolve();
        }
    }

    String sType(int type) {
        if (type == 1) {
            return "DIRECT";
        }
        if (type == 2) {
            return "CENTER";
        }
        if (type == 3) {
            return "MATCH";
        }
        if (type == 4) {
            return "CHAIN";
        }
        if (type == 5) {
            return "BARRIER";
        }
        return "UNCONNECTED";
    }

    public void resolve() {
        boolean isEndAnchor = true;
        if (this.state != 1 && this.type != 4) {
            if (this.dimension != null) {
                if (this.dimension.state == 1) {
                    this.offset = ((float) this.dimensionMultiplier) * this.dimension.value;
                } else {
                    return;
                }
            }
            if (this.oppositeDimension != null) {
                if (this.oppositeDimension.state == 1) {
                    this.oppositeOffset = ((float) this.oppositeDimensionMultiplier) * this.oppositeDimension.value;
                } else {
                    return;
                }
            }
            if (this.type == 1 && (this.target == null || this.target.state == 1)) {
                if (this.target == null) {
                    this.resolvedTarget = this;
                    this.resolvedOffset = this.offset;
                } else {
                    this.resolvedTarget = this.target.resolvedTarget;
                    this.resolvedOffset = this.target.resolvedOffset + this.offset;
                }
                didResolve();
            } else if (this.type == 2 && this.target != null && this.target.state == 1 && this.opposite != null && this.opposite.target != null && this.opposite.target.state == 1) {
                float distance;
                float f;
                int margin;
                int oppositeMargin;
                if (LinearSystem.getMetrics() != null) {
                    r0 = LinearSystem.getMetrics();
                    r0.centerConnectionResolved++;
                }
                this.resolvedTarget = this.target.resolvedTarget;
                this.opposite.resolvedTarget = this.opposite.target.resolvedTarget;
                if (this.myAnchor.mType != Type.RIGHT) {
                    if (this.myAnchor.mType != Type.BOTTOM) {
                        isEndAnchor = false;
                    }
                }
                if (isEndAnchor) {
                    distance = this.target.resolvedOffset - this.opposite.target.resolvedOffset;
                } else {
                    distance = this.opposite.target.resolvedOffset - this.target.resolvedOffset;
                }
                if (this.myAnchor.mType != Type.LEFT) {
                    if (this.myAnchor.mType != Type.RIGHT) {
                        distance -= (float) this.myAnchor.mOwner.getHeight();
                        f = this.myAnchor.mOwner.mVerticalBiasPercent;
                        margin = this.myAnchor.getMargin();
                        oppositeMargin = this.opposite.myAnchor.getMargin();
                        if (this.myAnchor.getTarget() == this.opposite.myAnchor.getTarget()) {
                            f = 0.5f;
                            margin = 0;
                            oppositeMargin = 0;
                        }
                        distance = (distance - ((float) margin)) - ((float) oppositeMargin);
                        if (isEndAnchor) {
                            this.resolvedOffset = (this.target.resolvedOffset + ((float) margin)) + (distance * f);
                            this.opposite.resolvedOffset = (this.opposite.target.resolvedOffset - ((float) oppositeMargin)) - ((1.0f - f) * distance);
                        } else {
                            this.opposite.resolvedOffset = (this.opposite.target.resolvedOffset + ((float) oppositeMargin)) + (distance * f);
                            this.resolvedOffset = (this.target.resolvedOffset - ((float) margin)) - ((1.0f - f) * distance);
                        }
                        didResolve();
                        this.opposite.didResolve();
                    }
                }
                distance -= (float) this.myAnchor.mOwner.getWidth();
                f = this.myAnchor.mOwner.mHorizontalBiasPercent;
                margin = this.myAnchor.getMargin();
                oppositeMargin = this.opposite.myAnchor.getMargin();
                if (this.myAnchor.getTarget() == this.opposite.myAnchor.getTarget()) {
                    f = 0.5f;
                    margin = 0;
                    oppositeMargin = 0;
                }
                distance = (distance - ((float) margin)) - ((float) oppositeMargin);
                if (isEndAnchor) {
                    this.resolvedOffset = (this.target.resolvedOffset + ((float) margin)) + (distance * f);
                    this.opposite.resolvedOffset = (this.opposite.target.resolvedOffset - ((float) oppositeMargin)) - ((1.0f - f) * distance);
                } else {
                    this.opposite.resolvedOffset = (this.opposite.target.resolvedOffset + ((float) oppositeMargin)) + (distance * f);
                    this.resolvedOffset = (this.target.resolvedOffset - ((float) margin)) - ((1.0f - f) * distance);
                }
                didResolve();
                this.opposite.didResolve();
            } else if (this.type == 3 && this.target != null && this.target.state == 1 && this.opposite != null && this.opposite.target != null && this.opposite.target.state == 1) {
                if (LinearSystem.getMetrics() != null) {
                    r0 = LinearSystem.getMetrics();
                    r0.matchConnectionResolved++;
                }
                this.resolvedTarget = this.target.resolvedTarget;
                this.opposite.resolvedTarget = this.opposite.target.resolvedTarget;
                this.resolvedOffset = this.target.resolvedOffset + this.offset;
                this.opposite.resolvedOffset = this.opposite.target.resolvedOffset + this.opposite.offset;
                didResolve();
                this.opposite.didResolve();
            } else if (this.type == 5) {
                this.myAnchor.mOwner.resolve();
            }
        }
    }

    public void setType(int type) {
        this.type = type;
    }

    public void reset() {
        super.reset();
        this.target = null;
        this.offset = 0.0f;
        this.dimension = null;
        this.dimensionMultiplier = 1;
        this.oppositeDimension = null;
        this.oppositeDimensionMultiplier = 1;
        this.resolvedTarget = null;
        this.resolvedOffset = 0.0f;
        this.computedValue = 0.0f;
        this.opposite = null;
        this.oppositeOffset = 0.0f;
        this.type = 0;
    }

    public void update() {
        ConstraintAnchor targetAnchor = this.myAnchor.getTarget();
        if (targetAnchor != null) {
            if (targetAnchor.getTarget() == this.myAnchor) {
                this.type = 4;
                targetAnchor.getResolutionNode().type = 4;
            }
            int margin = this.myAnchor.getMargin();
            if (this.myAnchor.mType == Type.RIGHT || this.myAnchor.mType == Type.BOTTOM) {
                margin = -margin;
            }
            dependsOn(targetAnchor.getResolutionNode(), margin);
        }
    }

    public void dependsOn(int type, ResolutionAnchor node, int offset) {
        this.type = type;
        this.target = node;
        this.offset = (float) offset;
        this.target.addDependent(this);
    }

    public void dependsOn(ResolutionAnchor node, int offset) {
        this.target = node;
        this.offset = (float) offset;
        this.target.addDependent(this);
    }

    public void dependsOn(ResolutionAnchor node, int multiplier, ResolutionDimension dimension) {
        this.target = node;
        this.target.addDependent(this);
        this.dimension = dimension;
        this.dimensionMultiplier = multiplier;
        this.dimension.addDependent(this);
    }

    public void setOpposite(ResolutionAnchor opposite, float oppositeOffset) {
        this.opposite = opposite;
        this.oppositeOffset = oppositeOffset;
    }

    public void setOpposite(ResolutionAnchor opposite, int multiplier, ResolutionDimension dimension) {
        this.opposite = opposite;
        this.oppositeDimension = dimension;
        this.oppositeDimensionMultiplier = multiplier;
    }

    void addResolvedValue(LinearSystem system) {
        SolverVariable sv = this.myAnchor.getSolverVariable();
        if (this.resolvedTarget == null) {
            system.addEquality(sv, (int) (this.resolvedOffset + 0.5f));
        } else {
            system.addEquality(sv, system.createObjectVariable(this.resolvedTarget.myAnchor), (int) (this.resolvedOffset + 0.5f), 6);
        }
    }

    public float getResolvedValue() {
        return this.resolvedOffset;
    }
}
