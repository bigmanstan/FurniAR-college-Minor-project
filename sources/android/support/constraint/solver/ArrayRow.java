package android.support.constraint.solver;

import android.support.constraint.solver.SolverVariable.Type;

public class ArrayRow implements Row {
    private static final boolean DEBUG = false;
    private static final float epsilon = 0.001f;
    float constantValue = 0.0f;
    boolean isSimpleDefinition = false;
    boolean used = false;
    SolverVariable variable = null;
    public final ArrayLinkedVariables variables;

    public ArrayRow(Cache cache) {
        this.variables = new ArrayLinkedVariables(this, cache);
    }

    boolean hasKeyVariable() {
        return this.variable != null && (this.variable.mType == Type.UNRESTRICTED || this.constantValue >= 0.0f);
    }

    public String toString() {
        return toReadableString();
    }

    String toReadableString() {
        StringBuilder stringBuilder;
        String s = "";
        if (this.variable == null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(s);
            stringBuilder.append("0");
            s = stringBuilder.toString();
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(s);
            stringBuilder.append(this.variable);
            s = stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(s);
        stringBuilder.append(" = ");
        s = stringBuilder.toString();
        boolean addedVariable = false;
        if (this.constantValue != 0.0f) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(s);
            stringBuilder2.append(this.constantValue);
            s = stringBuilder2.toString();
            addedVariable = true;
        }
        int count = this.variables.currentSize;
        for (int i = 0; i < count; i++) {
            SolverVariable v = this.variables.getVariable(i);
            if (v != null) {
                float amount = this.variables.getVariableValue(i);
                if (amount != 0.0f) {
                    StringBuilder stringBuilder3;
                    String name = v.toString();
                    StringBuilder stringBuilder4;
                    if (addedVariable) {
                        if (amount > 0.0f) {
                            stringBuilder3 = new StringBuilder();
                            stringBuilder3.append(s);
                            stringBuilder3.append(" + ");
                            s = stringBuilder3.toString();
                        } else {
                            stringBuilder4 = new StringBuilder();
                            stringBuilder4.append(s);
                            stringBuilder4.append(" - ");
                            s = stringBuilder4.toString();
                            amount *= -1.0f;
                        }
                    } else if (amount < 0.0f) {
                        stringBuilder4 = new StringBuilder();
                        stringBuilder4.append(s);
                        stringBuilder4.append("- ");
                        s = stringBuilder4.toString();
                        amount *= -1.0f;
                    }
                    if (amount == 1.0f) {
                        stringBuilder3 = new StringBuilder();
                        stringBuilder3.append(s);
                        stringBuilder3.append(name);
                        s = stringBuilder3.toString();
                    } else {
                        stringBuilder3 = new StringBuilder();
                        stringBuilder3.append(s);
                        stringBuilder3.append(amount);
                        stringBuilder3.append(" ");
                        stringBuilder3.append(name);
                        s = stringBuilder3.toString();
                    }
                    addedVariable = true;
                }
            }
        }
        if (addedVariable) {
            return s;
        }
        StringBuilder stringBuilder5 = new StringBuilder();
        stringBuilder5.append(s);
        stringBuilder5.append("0.0");
        return stringBuilder5.toString();
    }

    public void reset() {
        this.variable = null;
        this.variables.clear();
        this.constantValue = 0.0f;
        this.isSimpleDefinition = false;
    }

    boolean hasVariable(SolverVariable v) {
        return this.variables.containsKey(v);
    }

    ArrayRow createRowDefinition(SolverVariable variable, int value) {
        this.variable = variable;
        variable.computedValue = (float) value;
        this.constantValue = (float) value;
        this.isSimpleDefinition = true;
        return this;
    }

    public ArrayRow createRowEquals(SolverVariable variable, int value) {
        if (value < 0) {
            this.constantValue = (float) (value * -1);
            this.variables.put(variable, 1.0f);
        } else {
            this.constantValue = (float) value;
            this.variables.put(variable, -1.0f);
        }
        return this;
    }

    public ArrayRow createRowEquals(SolverVariable variableA, SolverVariable variableB, int margin) {
        boolean inverse = false;
        if (margin != 0) {
            int m = margin;
            if (m < 0) {
                m *= -1;
                inverse = true;
            }
            this.constantValue = (float) m;
        }
        if (inverse) {
            this.variables.put(variableA, 1.0f);
            this.variables.put(variableB, -1.0f);
        } else {
            this.variables.put(variableA, -1.0f);
            this.variables.put(variableB, 1.0f);
        }
        return this;
    }

    ArrayRow addSingleError(SolverVariable error, int sign) {
        this.variables.put(error, (float) sign);
        return this;
    }

    public ArrayRow createRowGreaterThan(SolverVariable variableA, SolverVariable variableB, SolverVariable slack, int margin) {
        boolean inverse = false;
        if (margin != 0) {
            int m = margin;
            if (m < 0) {
                m *= -1;
                inverse = true;
            }
            this.constantValue = (float) m;
        }
        if (inverse) {
            this.variables.put(variableA, 1.0f);
            this.variables.put(variableB, -1.0f);
            this.variables.put(slack, -1.0f);
        } else {
            this.variables.put(variableA, -1.0f);
            this.variables.put(variableB, 1.0f);
            this.variables.put(slack, 1.0f);
        }
        return this;
    }

    public ArrayRow createRowGreaterThan(SolverVariable a, int b, SolverVariable slack) {
        this.constantValue = (float) b;
        this.variables.put(a, -1.0f);
        return this;
    }

    public ArrayRow createRowLowerThan(SolverVariable variableA, SolverVariable variableB, SolverVariable slack, int margin) {
        boolean inverse = false;
        if (margin != 0) {
            int m = margin;
            if (m < 0) {
                m *= -1;
                inverse = true;
            }
            this.constantValue = (float) m;
        }
        if (inverse) {
            this.variables.put(variableA, 1.0f);
            this.variables.put(variableB, -1.0f);
            this.variables.put(slack, 1.0f);
        } else {
            this.variables.put(variableA, -1.0f);
            this.variables.put(variableB, 1.0f);
            this.variables.put(slack, -1.0f);
        }
        return this;
    }

    public ArrayRow createRowEqualMatchDimensions(float currentWeight, float totalWeights, float nextWeight, SolverVariable variableStartA, SolverVariable variableEndA, SolverVariable variableStartB, SolverVariable variableEndB) {
        this.constantValue = 0.0f;
        if (totalWeights != 0.0f) {
            if (currentWeight != nextWeight) {
                if (currentWeight == 0.0f) {
                    this.variables.put(variableStartA, 1.0f);
                    this.variables.put(variableEndA, -1.0f);
                } else if (nextWeight == 0.0f) {
                    this.variables.put(variableStartB, 1.0f);
                    this.variables.put(variableEndB, -1.0f);
                } else {
                    float w = (currentWeight / totalWeights) / (nextWeight / totalWeights);
                    this.variables.put(variableStartA, 1.0f);
                    this.variables.put(variableEndA, -1.0f);
                    this.variables.put(variableEndB, w);
                    this.variables.put(variableStartB, -w);
                }
                return this;
            }
        }
        this.variables.put(variableStartA, 1.0f);
        this.variables.put(variableEndA, -1.0f);
        this.variables.put(variableEndB, 1.0f);
        this.variables.put(variableStartB, -1.0f);
        return this;
    }

    public ArrayRow createRowEqualDimension(float currentWeight, float totalWeights, float nextWeight, SolverVariable variableStartA, int marginStartA, SolverVariable variableEndA, int marginEndA, SolverVariable variableStartB, int marginStartB, SolverVariable variableEndB, int marginEndB) {
        ArrayRow arrayRow = this;
        SolverVariable solverVariable = variableStartA;
        int i = marginStartA;
        SolverVariable solverVariable2 = variableEndA;
        SolverVariable solverVariable3 = variableStartB;
        int i2 = marginStartB;
        SolverVariable solverVariable4 = variableEndB;
        int i3 = marginEndB;
        if (totalWeights != 0.0f) {
            if (currentWeight != nextWeight) {
                float w = (currentWeight / totalWeights) / (nextWeight / totalWeights);
                arrayRow.constantValue = (((float) ((-i) - marginEndA)) + (((float) i2) * w)) + (((float) i3) * w);
                arrayRow.variables.put(solverVariable, 1.0f);
                arrayRow.variables.put(solverVariable2, -1.0f);
                arrayRow.variables.put(solverVariable4, w);
                arrayRow.variables.put(solverVariable3, -w);
                return arrayRow;
            }
        }
        arrayRow.constantValue = (float) ((((-i) - marginEndA) + i2) + i3);
        arrayRow.variables.put(solverVariable, 1.0f);
        arrayRow.variables.put(solverVariable2, -1.0f);
        arrayRow.variables.put(solverVariable4, 1.0f);
        arrayRow.variables.put(solverVariable3, -1.0f);
        return arrayRow;
    }

    ArrayRow createRowCentering(SolverVariable variableA, SolverVariable variableB, int marginA, float bias, SolverVariable variableC, SolverVariable variableD, int marginB) {
        if (variableB == variableC) {
            this.variables.put(variableA, 1.0f);
            this.variables.put(variableD, 1.0f);
            this.variables.put(variableB, -2.0f);
            return this;
        }
        if (bias == 0.5f) {
            this.variables.put(variableA, 1.0f);
            this.variables.put(variableB, -1.0f);
            this.variables.put(variableC, -1.0f);
            this.variables.put(variableD, 1.0f);
            if (marginA > 0 || marginB > 0) {
                this.constantValue = (float) ((-marginA) + marginB);
            }
        } else if (bias <= 0.0f) {
            this.variables.put(variableA, -1.0f);
            this.variables.put(variableB, 1.0f);
            this.constantValue = (float) marginA;
        } else if (bias >= 1.0f) {
            this.variables.put(variableC, -1.0f);
            this.variables.put(variableD, 1.0f);
            this.constantValue = (float) marginB;
        } else {
            this.variables.put(variableA, (1.0f - bias) * 1.0f);
            this.variables.put(variableB, (1.0f - bias) * -1.0f);
            this.variables.put(variableC, -1.0f * bias);
            this.variables.put(variableD, bias * 1.0f);
            if (marginA > 0 || marginB > 0) {
                this.constantValue = (((float) (-marginA)) * (1.0f - bias)) + (((float) marginB) * bias);
            }
        }
        return this;
    }

    public ArrayRow addError(LinearSystem system, int strength) {
        this.variables.put(system.createErrorVariable(strength, "ep"), 1.0f);
        this.variables.put(system.createErrorVariable(strength, "em"), -1.0f);
        return this;
    }

    ArrayRow createRowDimensionPercent(SolverVariable variableA, SolverVariable variableB, SolverVariable variableC, float percent) {
        this.variables.put(variableA, -1.0f);
        this.variables.put(variableB, 1.0f - percent);
        this.variables.put(variableC, percent);
        return this;
    }

    public ArrayRow createRowDimensionRatio(SolverVariable variableA, SolverVariable variableB, SolverVariable variableC, SolverVariable variableD, float ratio) {
        this.variables.put(variableA, -1.0f);
        this.variables.put(variableB, 1.0f);
        this.variables.put(variableC, ratio);
        this.variables.put(variableD, -ratio);
        return this;
    }

    public ArrayRow createRowWithAngle(SolverVariable at, SolverVariable ab, SolverVariable bt, SolverVariable bb, float angleComponent) {
        this.variables.put(bt, 0.5f);
        this.variables.put(bb, 0.5f);
        this.variables.put(at, -0.5f);
        this.variables.put(ab, -0.5f);
        this.constantValue = -angleComponent;
        return this;
    }

    int sizeInBytes() {
        int size = 0;
        if (this.variable != null) {
            size = 0 + 4;
        }
        return ((size + 4) + 4) + this.variables.sizeInBytes();
    }

    void ensurePositiveConstant() {
        if (this.constantValue < 0.0f) {
            this.constantValue *= -1.0f;
            this.variables.invert();
        }
    }

    boolean chooseSubject(LinearSystem system) {
        boolean addedExtra = false;
        SolverVariable pivotCandidate = this.variables.chooseSubject(system);
        if (pivotCandidate == null) {
            addedExtra = true;
        } else {
            pivot(pivotCandidate);
        }
        if (this.variables.currentSize == 0) {
            this.isSimpleDefinition = true;
        }
        return addedExtra;
    }

    SolverVariable pickPivot(SolverVariable exclude) {
        return this.variables.getPivotCandidate(null, exclude);
    }

    void pivot(SolverVariable v) {
        if (this.variable != null) {
            this.variables.put(this.variable, -1.0f);
            this.variable = null;
        }
        float amount = this.variables.remove(v, true) * -1.0f;
        this.variable = v;
        if (amount != 1.0f) {
            this.constantValue /= amount;
            this.variables.divideByAmount(amount);
        }
    }

    public boolean isEmpty() {
        return this.variable == null && this.constantValue == 0.0f && this.variables.currentSize == 0;
    }

    public SolverVariable getPivotCandidate(LinearSystem system, boolean[] avoid) {
        return this.variables.getPivotCandidate(avoid, null);
    }

    public void clear() {
        this.variables.clear();
        this.variable = null;
        this.constantValue = 0.0f;
    }

    public void initFromRow(Row row) {
        if (row instanceof ArrayRow) {
            ArrayRow copiedRow = (ArrayRow) row;
            this.variable = null;
            this.variables.clear();
            for (int i = 0; i < copiedRow.variables.currentSize; i++) {
                this.variables.add(copiedRow.variables.getVariable(i), copiedRow.variables.getVariableValue(i), true);
            }
        }
    }

    public void addError(SolverVariable error) {
        float weight = 1.0f;
        if (error.strength == 1) {
            weight = 1.0f;
        } else if (error.strength == 2) {
            weight = 1000.0f;
        } else if (error.strength == 3) {
            weight = 1000000.0f;
        } else if (error.strength == 4) {
            weight = 1.0E9f;
        } else if (error.strength == 5) {
            weight = 1.0E12f;
        }
        this.variables.put(error, weight);
    }

    public SolverVariable getKey() {
        return this.variable;
    }
}
