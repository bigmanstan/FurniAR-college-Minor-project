package com.google.android.gms.common.util;

import com.google.android.gms.common.internal.Objects;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

@VisibleForTesting
public final class ArrayUtils {

    private static class zza {
        HashMap<Object, zza> zzzb;

        zza(int i) {
            this.zzzb = new HashMap(i);
        }

        private final zza zzd(Object obj) {
            zza zza = (zza) this.zzzb.get(obj);
            if (zza != null) {
                return zza;
            }
            zza = new zza();
            this.zzzb.put(obj, zza);
            return zza;
        }

        final void zzb(Object obj) {
            zza zzd = zzd(obj);
            zzd.count++;
        }

        final void zzc(Object obj) {
            zza zzd = zzd(obj);
            zzd.count--;
        }
    }

    private ArrayUtils() {
    }

    public static int[] appendToArray(int[] iArr, int i) {
        if (iArr != null) {
            if (iArr.length != 0) {
                iArr = Arrays.copyOf(iArr, iArr.length + 1);
                iArr[iArr.length - 1] = i;
                return iArr;
            }
        }
        iArr = new int[1];
        iArr[iArr.length - 1] = i;
        return iArr;
    }

    public static <T> T[] appendToArray(T[] tArr, T t) {
        if (tArr == null) {
            if (t == null) {
                throw new IllegalArgumentException("Cannot generate array of generic type w/o class info");
            }
        }
        tArr = tArr == null ? (Object[]) Array.newInstance(t.getClass(), 1) : Arrays.copyOf(tArr, tArr.length + 1);
        tArr[tArr.length - 1] = t;
        return tArr;
    }

    public static <T> T[] concat(T[]... tArr) {
        if (tArr.length == 0) {
            return (Object[]) Array.newInstance(tArr.getClass(), 0);
        }
        int i = 0;
        int i2 = i;
        while (i < tArr.length) {
            i2 += tArr[i].length;
            i++;
        }
        Object copyOf = Arrays.copyOf(tArr[0], i2);
        i2 = tArr[0].length;
        for (int i3 = 1; i3 < tArr.length; i3++) {
            Object obj = tArr[i3];
            System.arraycopy(obj, 0, copyOf, i2, obj.length);
            i2 += obj.length;
        }
        return copyOf;
    }

    public static byte[] concatByteArrays(byte[]... bArr) {
        if (bArr.length == 0) {
            return new byte[0];
        }
        int i = 0;
        int i2 = i;
        while (i < bArr.length) {
            i2 += bArr[i].length;
            i++;
        }
        Object copyOf = Arrays.copyOf(bArr[0], i2);
        i2 = bArr[0].length;
        for (int i3 = 1; i3 < bArr.length; i3++) {
            Object obj = bArr[i3];
            System.arraycopy(obj, 0, copyOf, i2, obj.length);
            i2 += obj.length;
        }
        return copyOf;
    }

    public static boolean contains(byte[] bArr, byte b) {
        if (bArr == null) {
            return false;
        }
        for (byte b2 : bArr) {
            if (b2 == b) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(char[] cArr, char c) {
        if (cArr == null) {
            return false;
        }
        for (char c2 : cArr) {
            if (c2 == c) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(double[] dArr, double d) {
        if (dArr == null) {
            return false;
        }
        for (double d2 : dArr) {
            if (d2 == d) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(float[] fArr, float f, float f2) {
        if (fArr == null) {
            return false;
        }
        float f3 = f - f2;
        f += f2;
        for (float f4 : fArr) {
            if (f3 <= f4 && f4 <= f) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(int[] iArr, int i) {
        if (iArr == null) {
            return false;
        }
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean contains(T[] tArr, T t) {
        return indexOf(tArr, t) >= 0;
    }

    public static boolean contains(short[] sArr, short s) {
        if (sArr == null) {
            return false;
        }
        for (short s2 : sArr) {
            if (s2 == s) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(boolean[] zArr, boolean z) {
        if (zArr == null) {
            return false;
        }
        for (boolean z2 : zArr) {
            if (z2 == z) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsIgnoreCase(String[] strArr, String str) {
        if (strArr == null) {
            return false;
        }
        for (String str2 : strArr) {
            if (str2 == str) {
                return true;
            }
            if (str2 != null && str2.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean equalsAnyOrder(Object[] objArr, Object[] objArr2) {
        if (objArr == objArr2) {
            return true;
        }
        int length = objArr == null ? 0 : objArr.length;
        int length2 = objArr2 == null ? 0 : objArr2.length;
        if (length == 0 && length2 == 0) {
            return true;
        }
        if (length != length2) {
            return false;
        }
        zza zza = new zza(length);
        for (Object zzb : objArr) {
            zza.zzb(zzb);
        }
        for (Object zzc : objArr2) {
            zza.zzc(zzc);
        }
        for (zza zza2 : zza.zzzb.values()) {
            if (zza2.count != 0) {
                return false;
            }
        }
        return true;
    }

    public static <T> int indexOf(T[] tArr, T t) {
        int i = 0;
        int length = tArr != null ? tArr.length : 0;
        while (i < length) {
            if (Objects.equal(tArr[i], t)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList();
    }

    public static <T> int rearrange(T[] tArr, Predicate<T> predicate) {
        int i = 0;
        if (tArr != null) {
            if (tArr.length != 0) {
                int length = tArr.length;
                int i2 = 0;
                while (i < length) {
                    if (predicate.apply(tArr[i])) {
                        if (i2 != i) {
                            T t = tArr[i2];
                            tArr[i2] = tArr[i];
                            tArr[i] = t;
                        }
                        i2++;
                    }
                    i++;
                }
                return i2;
            }
        }
        return 0;
    }

    public static int[] removeAll(int[] iArr, int... iArr2) {
        if (iArr == null) {
            return null;
        }
        if (iArr2 != null) {
            if (iArr2.length != 0) {
                int i;
                int[] iArr3 = new int[iArr.length];
                int i2 = 0;
                int length;
                int i3;
                int i4;
                if (iArr2.length == 1) {
                    length = iArr.length;
                    i3 = 0;
                    i = i3;
                    while (i3 < length) {
                        i4 = iArr[i3];
                        if (iArr2[0] != i4) {
                            int i5 = i + 1;
                            iArr3[i] = i4;
                            i = i5;
                        }
                        i3++;
                    }
                } else {
                    length = iArr.length;
                    i = 0;
                    while (i2 < length) {
                        i3 = iArr[i2];
                        if (!contains(iArr2, i3)) {
                            i4 = i + 1;
                            iArr3[i] = i3;
                            i = i4;
                        }
                        i2++;
                    }
                }
                return resize(iArr3, i);
            }
        }
        return Arrays.copyOf(iArr, iArr.length);
    }

    public static <T> T[] removeAll(T[] tArr, T... tArr2) {
        if (tArr == null) {
            return null;
        }
        if (tArr2 != null) {
            if (tArr2.length != 0) {
                int i;
                Object[] objArr = (Object[]) Array.newInstance(tArr2.getClass().getComponentType(), tArr.length);
                int i2 = 0;
                int length;
                if (tArr2.length == 1) {
                    length = tArr.length;
                    int i3 = 0;
                    i = i3;
                    while (i3 < length) {
                        Object obj = tArr[i3];
                        if (!Objects.equal(tArr2[0], obj)) {
                            int i4 = i + 1;
                            objArr[i] = obj;
                            i = i4;
                        }
                        i3++;
                    }
                } else {
                    length = tArr.length;
                    i = 0;
                    while (i2 < length) {
                        Object obj2 = tArr[i2];
                        if (!contains((Object[]) tArr2, obj2)) {
                            int i5 = i + 1;
                            objArr[i] = obj2;
                            i = i5;
                        }
                        i2++;
                    }
                }
                return resize(objArr, i);
            }
        }
        return Arrays.copyOf(tArr, tArr.length);
    }

    public static int[] resize(int[] iArr, int i) {
        if (iArr == null) {
            return null;
        }
        if (i != iArr.length) {
            iArr = Arrays.copyOf(iArr, i);
        }
        return iArr;
    }

    public static <T> T[] resize(T[] tArr, int i) {
        if (tArr == null) {
            return null;
        }
        if (i != tArr.length) {
            tArr = Arrays.copyOf(tArr, i);
        }
        return tArr;
    }

    public static <T> ArrayList<T> toArrayList(Collection<T> collection) {
        return collection == null ? null : new ArrayList(collection);
    }

    public static <T> ArrayList<T> toArrayList(T[] tArr) {
        ArrayList<T> arrayList = new ArrayList(r0);
        for (Object add : tArr) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public static long[] toLongArray(Collection<Long> collection) {
        int i = 0;
        if (collection != null) {
            if (collection.size() != 0) {
                long[] jArr = new long[collection.size()];
                for (Long longValue : collection) {
                    int i2 = i + 1;
                    jArr[i] = longValue.longValue();
                    i = i2;
                }
                return jArr;
            }
        }
        return new long[0];
    }

    public static long[] toLongArray(Long[] lArr) {
        int i = 0;
        if (lArr == null) {
            return new long[0];
        }
        long[] jArr = new long[lArr.length];
        while (i < lArr.length) {
            jArr[i] = lArr[i].longValue();
            i++;
        }
        return jArr;
    }

    public static int[] toPrimitiveArray(Collection<Integer> collection) {
        int i = 0;
        if (collection != null) {
            if (collection.size() != 0) {
                int[] iArr = new int[collection.size()];
                for (Integer intValue : collection) {
                    int i2 = i + 1;
                    iArr[i] = intValue.intValue();
                    i = i2;
                }
                return iArr;
            }
        }
        return new int[0];
    }

    public static int[] toPrimitiveArray(Integer[] numArr) {
        int i = 0;
        if (numArr == null) {
            return new int[0];
        }
        int[] iArr = new int[numArr.length];
        while (i < numArr.length) {
            iArr[i] = numArr[i].intValue();
            i++;
        }
        return iArr;
    }

    public static String[] toStringArray(Collection<String> collection) {
        if (collection != null) {
            if (collection.size() != 0) {
                return (String[]) collection.toArray(new String[collection.size()]);
            }
        }
        return new String[0];
    }

    public static Boolean[] toWrapperArray(boolean[] zArr) {
        if (zArr == null) {
            return null;
        }
        int length = zArr.length;
        Boolean[] boolArr = new Boolean[length];
        for (int i = 0; i < length; i++) {
            boolArr[i] = Boolean.valueOf(zArr[i]);
        }
        return boolArr;
    }

    public static Byte[] toWrapperArray(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        Byte[] bArr2 = new Byte[length];
        for (int i = 0; i < length; i++) {
            bArr2[i] = Byte.valueOf(bArr[i]);
        }
        return bArr2;
    }

    public static Character[] toWrapperArray(char[] cArr) {
        if (cArr == null) {
            return null;
        }
        int length = cArr.length;
        Character[] chArr = new Character[length];
        for (int i = 0; i < length; i++) {
            chArr[i] = Character.valueOf(cArr[i]);
        }
        return chArr;
    }

    public static Double[] toWrapperArray(double[] dArr) {
        if (dArr == null) {
            return null;
        }
        int length = dArr.length;
        Double[] dArr2 = new Double[length];
        for (int i = 0; i < length; i++) {
            dArr2[i] = Double.valueOf(dArr[i]);
        }
        return dArr2;
    }

    public static Float[] toWrapperArray(float[] fArr) {
        if (fArr == null) {
            return null;
        }
        int length = fArr.length;
        Float[] fArr2 = new Float[length];
        for (int i = 0; i < length; i++) {
            fArr2[i] = Float.valueOf(fArr[i]);
        }
        return fArr2;
    }

    public static Integer[] toWrapperArray(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int length = iArr.length;
        Integer[] numArr = new Integer[length];
        for (int i = 0; i < length; i++) {
            numArr[i] = Integer.valueOf(iArr[i]);
        }
        return numArr;
    }

    public static Long[] toWrapperArray(long[] jArr) {
        if (jArr == null) {
            return null;
        }
        int length = jArr.length;
        Long[] lArr = new Long[length];
        for (int i = 0; i < length; i++) {
            lArr[i] = Long.valueOf(jArr[i]);
        }
        return lArr;
    }

    public static Short[] toWrapperArray(short[] sArr) {
        if (sArr == null) {
            return null;
        }
        int length = sArr.length;
        Short[] shArr = new Short[length];
        for (int i = 0; i < length; i++) {
            shArr[i] = Short.valueOf(sArr[i]);
        }
        return shArr;
    }

    public static void writeArray(StringBuilder stringBuilder, double[] dArr) {
        int length = dArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Double.toString(dArr[i]));
        }
    }

    public static void writeArray(StringBuilder stringBuilder, float[] fArr) {
        int length = fArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Float.toString(fArr[i]));
        }
    }

    public static void writeArray(StringBuilder stringBuilder, int[] iArr) {
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Integer.toString(iArr[i]));
        }
    }

    public static void writeArray(StringBuilder stringBuilder, long[] jArr) {
        int length = jArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Long.toString(jArr[i]));
        }
    }

    public static <T> void writeArray(StringBuilder stringBuilder, T[] tArr) {
        int length = tArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(tArr[i].toString());
        }
    }

    public static void writeArray(StringBuilder stringBuilder, boolean[] zArr) {
        int length = zArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Boolean.toString(zArr[i]));
        }
    }

    public static void writeStringArray(StringBuilder stringBuilder, String[] strArr) {
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append("\"");
            stringBuilder.append(strArr[i]);
            stringBuilder.append("\"");
        }
    }
}
