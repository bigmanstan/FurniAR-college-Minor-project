package android.support.transition;

import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransitionValues {
    final ArrayList<Transition> mTargetedTransitions = new ArrayList();
    public final Map<String, Object> values = new HashMap();
    public View view;

    public boolean equals(Object other) {
        if ((other instanceof TransitionValues) && this.view == ((TransitionValues) other).view && this.values.equals(((TransitionValues) other).values)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.view.hashCode() * 31) + this.values.hashCode();
    }

    public String toString() {
        String returnValue = new StringBuilder();
        returnValue.append("TransitionValues@");
        returnValue.append(Integer.toHexString(hashCode()));
        returnValue.append(":\n");
        returnValue = returnValue.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(returnValue);
        stringBuilder.append("    view = ");
        stringBuilder.append(this.view);
        stringBuilder.append("\n");
        returnValue = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(returnValue);
        stringBuilder.append("    values:");
        returnValue = stringBuilder.toString();
        for (String s : this.values.keySet()) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(returnValue);
            stringBuilder2.append("    ");
            stringBuilder2.append(s);
            stringBuilder2.append(": ");
            stringBuilder2.append(this.values.get(s));
            stringBuilder2.append("\n");
            returnValue = stringBuilder2.toString();
        }
        return returnValue;
    }
}
