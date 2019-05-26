package android.support.constraint.solver;

import java.util.Arrays;

public class SolverVariable {
    private static final boolean INTERNAL_DEBUG = false;
    static final int MAX_STRENGTH = 7;
    public static final int STRENGTH_BARRIER = 7;
    public static final int STRENGTH_EQUALITY = 5;
    public static final int STRENGTH_FIXED = 6;
    public static final int STRENGTH_HIGH = 3;
    public static final int STRENGTH_HIGHEST = 4;
    public static final int STRENGTH_LOW = 1;
    public static final int STRENGTH_MEDIUM = 2;
    public static final int STRENGTH_NONE = 0;
    private static int uniqueConstantId = 1;
    private static int uniqueErrorId = 1;
    private static int uniqueId = 1;
    private static int uniqueSlackId = 1;
    private static int uniqueUnrestrictedId = 1;
    public float computedValue;
    int definitionId;
    public int id;
    ArrayRow[] mClientEquations;
    int mClientEquationsCount;
    private String mName;
    Type mType;
    public int strength;
    float[] strengthVector;
    public int usageInRowCount;

    public enum Type {
        UNRESTRICTED,
        CONSTANT,
        SLACK,
        ERROR,
        UNKNOWN
    }

    static void increaseErrorId() {
        uniqueErrorId++;
    }

    private static String getUniqueName(Type type, String prefix) {
        if (prefix != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(prefix);
            stringBuilder.append(uniqueErrorId);
            return stringBuilder.toString();
        }
        int i;
        switch (type) {
            case UNRESTRICTED:
                stringBuilder = new StringBuilder();
                stringBuilder.append("U");
                i = uniqueUnrestrictedId + 1;
                uniqueUnrestrictedId = i;
                stringBuilder.append(i);
                return stringBuilder.toString();
            case CONSTANT:
                stringBuilder = new StringBuilder();
                stringBuilder.append("C");
                i = uniqueConstantId + 1;
                uniqueConstantId = i;
                stringBuilder.append(i);
                return stringBuilder.toString();
            case SLACK:
                stringBuilder = new StringBuilder();
                stringBuilder.append("S");
                i = uniqueSlackId + 1;
                uniqueSlackId = i;
                stringBuilder.append(i);
                return stringBuilder.toString();
            case ERROR:
                stringBuilder = new StringBuilder();
                stringBuilder.append("e");
                i = uniqueErrorId + 1;
                uniqueErrorId = i;
                stringBuilder.append(i);
                return stringBuilder.toString();
            case UNKNOWN:
                stringBuilder = new StringBuilder();
                stringBuilder.append("V");
                i = uniqueId + 1;
                uniqueId = i;
                stringBuilder.append(i);
                return stringBuilder.toString();
            default:
                throw new AssertionError(type.name());
        }
    }

    public SolverVariable(String name, Type type) {
        this.id = -1;
        this.definitionId = -1;
        this.strength = 0;
        this.strengthVector = new float[7];
        this.mClientEquations = new ArrayRow[8];
        this.mClientEquationsCount = 0;
        this.usageInRowCount = 0;
        this.mName = name;
        this.mType = type;
    }

    public SolverVariable(Type type, String prefix) {
        this.id = -1;
        this.definitionId = -1;
        this.strength = 0;
        this.strengthVector = new float[7];
        this.mClientEquations = new ArrayRow[8];
        this.mClientEquationsCount = 0;
        this.usageInRowCount = 0;
        this.mType = type;
    }

    void clearStrengths() {
        for (int i = 0; i < 7; i++) {
            this.strengthVector[i] = 0.0f;
        }
    }

    String strengthsToString() {
        String representation = new StringBuilder();
        representation.append(this);
        representation.append("[");
        representation = representation.toString();
        boolean negative = false;
        boolean empty = true;
        for (int j = 0; j < this.strengthVector.length; j++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(representation);
            stringBuilder.append(this.strengthVector[j]);
            representation = stringBuilder.toString();
            if (this.strengthVector[j] > 0.0f) {
                negative = false;
            } else if (this.strengthVector[j] < 0.0f) {
                negative = true;
            }
            if (this.strengthVector[j] != 0.0f) {
                empty = false;
            }
            if (j < this.strengthVector.length - 1) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(representation);
                stringBuilder.append(", ");
                representation = stringBuilder.toString();
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(representation);
                stringBuilder.append("] ");
                representation = stringBuilder.toString();
            }
        }
        if (negative) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(representation);
            stringBuilder2.append(" (-)");
            representation = stringBuilder2.toString();
        }
        if (!empty) {
            return representation;
        }
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(representation);
        stringBuilder2.append(" (*)");
        return stringBuilder2.toString();
    }

    public final void addToRow(ArrayRow row) {
        int i = 0;
        while (i < this.mClientEquationsCount) {
            if (this.mClientEquations[i] != row) {
                i++;
            } else {
                return;
            }
        }
        if (this.mClientEquationsCount >= this.mClientEquations.length) {
            this.mClientEquations = (ArrayRow[]) Arrays.copyOf(this.mClientEquations, this.mClientEquations.length * 2);
        }
        this.mClientEquations[this.mClientEquationsCount] = row;
        this.mClientEquationsCount++;
    }

    public final void removeFromRow(ArrayRow row) {
        int count = this.mClientEquationsCount;
        int j = 0;
        for (int i = 0; i < count; i++) {
            if (this.mClientEquations[i] == row) {
                while (j < (count - i) - 1) {
                    this.mClientEquations[i + j] = this.mClientEquations[(i + j) + 1];
                    j++;
                }
                this.mClientEquationsCount--;
                return;
            }
        }
    }

    public final void updateReferencesWithNewDefinition(ArrayRow definition) {
        int count = this.mClientEquationsCount;
        for (int i = 0; i < count; i++) {
            this.mClientEquations[i].variables.updateFromRow(this.mClientEquations[i], definition, false);
        }
        this.mClientEquationsCount = 0;
    }

    public void reset() {
        this.mName = null;
        this.mType = Type.UNKNOWN;
        this.strength = 0;
        this.id = -1;
        this.definitionId = -1;
        this.computedValue = 0.0f;
        this.mClientEquationsCount = 0;
        this.usageInRowCount = 0;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setType(Type type, String prefix) {
        this.mType = type;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.mName);
        return stringBuilder.toString();
    }
}
