package com.google.android.gms.common.internal;

import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Objects {

    public static final class ToStringHelper {
        private final List<String> zzul;
        private final Object zzum;

        private ToStringHelper(Object obj) {
            this.zzum = Preconditions.checkNotNull(obj);
            this.zzul = new ArrayList();
        }

        public final ToStringHelper add(String str, @Nullable Object obj) {
            List list = this.zzul;
            str = (String) Preconditions.checkNotNull(str);
            String valueOf = String.valueOf(obj);
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 1) + String.valueOf(valueOf).length());
            stringBuilder.append(str);
            stringBuilder.append("=");
            stringBuilder.append(valueOf);
            list.add(stringBuilder.toString());
            return this;
        }

        public final ToStringHelper addValue(@Nullable Object obj) {
            this.zzul.add(String.valueOf(obj));
            return this;
        }

        public final String toString() {
            StringBuilder stringBuilder = new StringBuilder(100);
            stringBuilder.append(this.zzum.getClass().getSimpleName());
            stringBuilder.append('{');
            int size = this.zzul.size();
            for (int i = 0; i < size; i++) {
                stringBuilder.append((String) this.zzul.get(i));
                if (i < size - 1) {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

    private Objects() {
        throw new AssertionError("Uninstantiable");
    }

    public static boolean equal(@Nullable Object obj, @Nullable Object obj2) {
        if (obj != obj2) {
            if (obj == null || !obj.equals(obj2)) {
                return false;
            }
        }
        return true;
    }

    public static int hashCode(Object... objArr) {
        return Arrays.hashCode(objArr);
    }

    public static ToStringHelper toStringHelper(Object obj) {
        return new ToStringHelper(obj);
    }
}
