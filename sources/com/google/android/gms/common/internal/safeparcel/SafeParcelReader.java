package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SafeParcelReader {

    public static class ParseException extends RuntimeException {
        public ParseException(String str, Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int dataSize = parcel.dataSize();
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 41);
            stringBuilder.append(str);
            stringBuilder.append(" Parcel: pos=");
            stringBuilder.append(dataPosition);
            stringBuilder.append(" size=");
            stringBuilder.append(dataSize);
            super(stringBuilder.toString());
        }
    }

    private SafeParcelReader() {
    }

    public static BigDecimal createBigDecimal(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        byte[] createByteArray = parcel.createByteArray();
        int readInt = parcel.readInt();
        parcel.setDataPosition(dataPosition + i);
        return new BigDecimal(new BigInteger(createByteArray), readInt);
    }

    public static BigDecimal[] createBigDecimalArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        int readInt = parcel.readInt();
        BigDecimal[] bigDecimalArr = new BigDecimal[readInt];
        for (int i2 = 0; i2 < readInt; i2++) {
            byte[] createByteArray = parcel.createByteArray();
            bigDecimalArr[i2] = new BigDecimal(new BigInteger(createByteArray), parcel.readInt());
        }
        parcel.setDataPosition(dataPosition + i);
        return bigDecimalArr;
    }

    public static BigInteger createBigInteger(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        byte[] createByteArray = parcel.createByteArray();
        parcel.setDataPosition(dataPosition + i);
        return new BigInteger(createByteArray);
    }

    public static BigInteger[] createBigIntegerArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        int readInt = parcel.readInt();
        BigInteger[] bigIntegerArr = new BigInteger[readInt];
        for (int i2 = 0; i2 < readInt; i2++) {
            bigIntegerArr[i2] = new BigInteger(parcel.createByteArray());
        }
        parcel.setDataPosition(dataPosition + i);
        return bigIntegerArr;
    }

    public static boolean[] createBooleanArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        boolean[] createBooleanArray = parcel.createBooleanArray();
        parcel.setDataPosition(dataPosition + i);
        return createBooleanArray;
    }

    public static ArrayList<Boolean> createBooleanList(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        ArrayList<Boolean> arrayList = new ArrayList();
        int readInt = parcel.readInt();
        for (int i2 = 0; i2 < readInt; i2++) {
            arrayList.add(Boolean.valueOf(parcel.readInt() != 0));
        }
        parcel.setDataPosition(dataPosition + i);
        return arrayList;
    }

    public static Bundle createBundle(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        Bundle readBundle = parcel.readBundle();
        parcel.setDataPosition(dataPosition + i);
        return readBundle;
    }

    public static byte[] createByteArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        byte[] createByteArray = parcel.createByteArray();
        parcel.setDataPosition(dataPosition + i);
        return createByteArray;
    }

    public static byte[][] createByteArrayArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        int readInt = parcel.readInt();
        byte[][] bArr = new byte[readInt][];
        for (int i2 = 0; i2 < readInt; i2++) {
            bArr[i2] = parcel.createByteArray();
        }
        parcel.setDataPosition(dataPosition + i);
        return bArr;
    }

    public static SparseArray<byte[]> createByteArraySparseArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        int readInt = parcel.readInt();
        SparseArray<byte[]> sparseArray = new SparseArray(readInt);
        for (int i2 = 0; i2 < readInt; i2++) {
            sparseArray.append(parcel.readInt(), parcel.createByteArray());
        }
        parcel.setDataPosition(dataPosition + i);
        return sparseArray;
    }

    public static char[] createCharArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        char[] createCharArray = parcel.createCharArray();
        parcel.setDataPosition(dataPosition + i);
        return createCharArray;
    }

    public static double[] createDoubleArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        double[] createDoubleArray = parcel.createDoubleArray();
        parcel.setDataPosition(dataPosition + i);
        return createDoubleArray;
    }

    public static ArrayList<Double> createDoubleList(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        ArrayList<Double> arrayList = new ArrayList();
        int readInt = parcel.readInt();
        for (int i2 = 0; i2 < readInt; i2++) {
            arrayList.add(Double.valueOf(parcel.readDouble()));
        }
        parcel.setDataPosition(dataPosition + i);
        return arrayList;
    }

    public static SparseArray<Double> createDoubleSparseArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        SparseArray<Double> sparseArray = new SparseArray();
        int readInt = parcel.readInt();
        for (int i2 = 0; i2 < readInt; i2++) {
            sparseArray.append(parcel.readInt(), Double.valueOf(parcel.readDouble()));
        }
        parcel.setDataPosition(dataPosition + i);
        return sparseArray;
    }

    public static float[] createFloatArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        float[] createFloatArray = parcel.createFloatArray();
        parcel.setDataPosition(dataPosition + i);
        return createFloatArray;
    }

    public static ArrayList<Float> createFloatList(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        ArrayList<Float> arrayList = new ArrayList();
        int readInt = parcel.readInt();
        for (int i2 = 0; i2 < readInt; i2++) {
            arrayList.add(Float.valueOf(parcel.readFloat()));
        }
        parcel.setDataPosition(dataPosition + i);
        return arrayList;
    }

    public static SparseArray<Float> createFloatSparseArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        SparseArray<Float> sparseArray = new SparseArray();
        int readInt = parcel.readInt();
        for (int i2 = 0; i2 < readInt; i2++) {
            sparseArray.append(parcel.readInt(), Float.valueOf(parcel.readFloat()));
        }
        parcel.setDataPosition(dataPosition + i);
        return sparseArray;
    }

    public static IBinder[] createIBinderArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        IBinder[] createBinderArray = parcel.createBinderArray();
        parcel.setDataPosition(dataPosition + i);
        return createBinderArray;
    }

    public static ArrayList<IBinder> createIBinderList(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        ArrayList<IBinder> createBinderArrayList = parcel.createBinderArrayList();
        parcel.setDataPosition(dataPosition + i);
        return createBinderArrayList;
    }

    public static SparseArray<IBinder> createIBinderSparseArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        int readInt = parcel.readInt();
        SparseArray<IBinder> sparseArray = new SparseArray(readInt);
        for (int i2 = 0; i2 < readInt; i2++) {
            sparseArray.append(parcel.readInt(), parcel.readStrongBinder());
        }
        parcel.setDataPosition(dataPosition + i);
        return sparseArray;
    }

    public static int[] createIntArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        int[] createIntArray = parcel.createIntArray();
        parcel.setDataPosition(dataPosition + i);
        return createIntArray;
    }

    public static ArrayList<Integer> createIntegerList(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        ArrayList<Integer> arrayList = new ArrayList();
        int readInt = parcel.readInt();
        for (int i2 = 0; i2 < readInt; i2++) {
            arrayList.add(Integer.valueOf(parcel.readInt()));
        }
        parcel.setDataPosition(dataPosition + i);
        return arrayList;
    }

    public static long[] createLongArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        long[] createLongArray = parcel.createLongArray();
        parcel.setDataPosition(dataPosition + i);
        return createLongArray;
    }

    public static ArrayList<Long> createLongList(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        ArrayList<Long> arrayList = new ArrayList();
        int readInt = parcel.readInt();
        for (int i2 = 0; i2 < readInt; i2++) {
            arrayList.add(Long.valueOf(parcel.readLong()));
        }
        parcel.setDataPosition(dataPosition + i);
        return arrayList;
    }

    public static Parcel createParcel(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        obtain.appendFrom(parcel, dataPosition, i);
        parcel.setDataPosition(dataPosition + i);
        return obtain;
    }

    public static Parcel[] createParcelArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        int readInt = parcel.readInt();
        Parcel[] parcelArr = new Parcel[readInt];
        for (int i2 = 0; i2 < readInt; i2++) {
            int readInt2 = parcel.readInt();
            if (readInt2 != 0) {
                int dataPosition2 = parcel.dataPosition();
                Parcel obtain = Parcel.obtain();
                obtain.appendFrom(parcel, dataPosition2, readInt2);
                parcelArr[i2] = obtain;
                parcel.setDataPosition(dataPosition2 + readInt2);
            } else {
                parcelArr[i2] = null;
            }
        }
        parcel.setDataPosition(dataPosition + i);
        return parcelArr;
    }

    public static ArrayList<Parcel> createParcelList(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        int readInt = parcel.readInt();
        ArrayList<Parcel> arrayList = new ArrayList();
        for (int i2 = 0; i2 < readInt; i2++) {
            int readInt2 = parcel.readInt();
            if (readInt2 != 0) {
                int dataPosition2 = parcel.dataPosition();
                Parcel obtain = Parcel.obtain();
                obtain.appendFrom(parcel, dataPosition2, readInt2);
                arrayList.add(obtain);
                parcel.setDataPosition(dataPosition2 + readInt2);
            } else {
                arrayList.add(null);
            }
        }
        parcel.setDataPosition(dataPosition + i);
        return arrayList;
    }

    public static SparseArray<Parcel> createParcelSparseArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        int readInt = parcel.readInt();
        SparseArray<Parcel> sparseArray = new SparseArray();
        for (int i2 = 0; i2 < readInt; i2++) {
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            if (readInt3 != 0) {
                int dataPosition2 = parcel.dataPosition();
                Parcel obtain = Parcel.obtain();
                obtain.appendFrom(parcel, dataPosition2, readInt3);
                sparseArray.append(readInt2, obtain);
                parcel.setDataPosition(dataPosition2 + readInt3);
            } else {
                sparseArray.append(readInt2, null);
            }
        }
        parcel.setDataPosition(dataPosition + i);
        return sparseArray;
    }

    public static <T extends Parcelable> T createParcelable(Parcel parcel, int i, Creator<T> creator) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        Parcelable parcelable = (Parcelable) creator.createFromParcel(parcel);
        parcel.setDataPosition(dataPosition + i);
        return parcelable;
    }

    public static SparseBooleanArray createSparseBooleanArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        SparseBooleanArray readSparseBooleanArray = parcel.readSparseBooleanArray();
        parcel.setDataPosition(dataPosition + i);
        return readSparseBooleanArray;
    }

    public static SparseIntArray createSparseIntArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        SparseIntArray sparseIntArray = new SparseIntArray();
        int readInt = parcel.readInt();
        for (int i2 = 0; i2 < readInt; i2++) {
            sparseIntArray.append(parcel.readInt(), parcel.readInt());
        }
        parcel.setDataPosition(dataPosition + i);
        return sparseIntArray;
    }

    public static SparseLongArray createSparseLongArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        SparseLongArray sparseLongArray = new SparseLongArray();
        int readInt = parcel.readInt();
        for (int i2 = 0; i2 < readInt; i2++) {
            sparseLongArray.append(parcel.readInt(), parcel.readLong());
        }
        parcel.setDataPosition(dataPosition + i);
        return sparseLongArray;
    }

    public static String createString(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        String readString = parcel.readString();
        parcel.setDataPosition(dataPosition + i);
        return readString;
    }

    public static String[] createStringArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        String[] createStringArray = parcel.createStringArray();
        parcel.setDataPosition(dataPosition + i);
        return createStringArray;
    }

    public static ArrayList<String> createStringList(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        ArrayList<String> createStringArrayList = parcel.createStringArrayList();
        parcel.setDataPosition(dataPosition + i);
        return createStringArrayList;
    }

    public static SparseArray<String> createStringSparseArray(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        SparseArray<String> sparseArray = new SparseArray();
        int readInt = parcel.readInt();
        for (int i2 = 0; i2 < readInt; i2++) {
            sparseArray.append(parcel.readInt(), parcel.readString());
        }
        parcel.setDataPosition(dataPosition + i);
        return sparseArray;
    }

    public static <T> T[] createTypedArray(Parcel parcel, int i, Creator<T> creator) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        T[] createTypedArray = parcel.createTypedArray(creator);
        parcel.setDataPosition(dataPosition + i);
        return createTypedArray;
    }

    public static <T> ArrayList<T> createTypedList(Parcel parcel, int i, Creator<T> creator) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        ArrayList<T> createTypedArrayList = parcel.createTypedArrayList(creator);
        parcel.setDataPosition(dataPosition + i);
        return createTypedArrayList;
    }

    public static <T> SparseArray<T> createTypedSparseArray(Parcel parcel, int i, Creator<T> creator) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        int readInt = parcel.readInt();
        SparseArray<T> sparseArray = new SparseArray();
        for (int i2 = 0; i2 < readInt; i2++) {
            sparseArray.append(parcel.readInt(), parcel.readInt() != 0 ? creator.createFromParcel(parcel) : null);
        }
        parcel.setDataPosition(dataPosition + i);
        return sparseArray;
    }

    public static void ensureAtEnd(Parcel parcel, int i) {
        if (parcel.dataPosition() != i) {
            StringBuilder stringBuilder = new StringBuilder(37);
            stringBuilder.append("Overread allowed size end=");
            stringBuilder.append(i);
            throw new ParseException(stringBuilder.toString(), parcel);
        }
    }

    public static int getFieldId(int i) {
        return i & SupportMenu.USER_MASK;
    }

    public static boolean readBoolean(Parcel parcel, int i) {
        zza(parcel, i, 4);
        return parcel.readInt() != 0;
    }

    public static Boolean readBooleanObject(Parcel parcel, int i) {
        int readSize = readSize(parcel, i);
        if (readSize == 0) {
            return null;
        }
        zza(parcel, i, readSize, 4);
        return Boolean.valueOf(parcel.readInt() != 0);
    }

    public static byte readByte(Parcel parcel, int i) {
        zza(parcel, i, 4);
        return (byte) parcel.readInt();
    }

    public static char readChar(Parcel parcel, int i) {
        zza(parcel, i, 4);
        return (char) parcel.readInt();
    }

    public static double readDouble(Parcel parcel, int i) {
        zza(parcel, i, 8);
        return parcel.readDouble();
    }

    public static Double readDoubleObject(Parcel parcel, int i) {
        int readSize = readSize(parcel, i);
        if (readSize == 0) {
            return null;
        }
        zza(parcel, i, readSize, 8);
        return Double.valueOf(parcel.readDouble());
    }

    public static float readFloat(Parcel parcel, int i) {
        zza(parcel, i, 4);
        return parcel.readFloat();
    }

    public static Float readFloatObject(Parcel parcel, int i) {
        int readSize = readSize(parcel, i);
        if (readSize == 0) {
            return null;
        }
        zza(parcel, i, readSize, 4);
        return Float.valueOf(parcel.readFloat());
    }

    public static int readHeader(Parcel parcel) {
        return parcel.readInt();
    }

    public static IBinder readIBinder(Parcel parcel, int i) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i == 0) {
            return null;
        }
        IBinder readStrongBinder = parcel.readStrongBinder();
        parcel.setDataPosition(dataPosition + i);
        return readStrongBinder;
    }

    public static int readInt(Parcel parcel, int i) {
        zza(parcel, i, 4);
        return parcel.readInt();
    }

    public static Integer readIntegerObject(Parcel parcel, int i) {
        int readSize = readSize(parcel, i);
        if (readSize == 0) {
            return null;
        }
        zza(parcel, i, readSize, 4);
        return Integer.valueOf(parcel.readInt());
    }

    public static void readList(Parcel parcel, int i, List list, ClassLoader classLoader) {
        i = readSize(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (i != 0) {
            parcel.readList(list, classLoader);
            parcel.setDataPosition(dataPosition + i);
        }
    }

    public static long readLong(Parcel parcel, int i) {
        zza(parcel, i, 8);
        return parcel.readLong();
    }

    public static Long readLongObject(Parcel parcel, int i) {
        int readSize = readSize(parcel, i);
        if (readSize == 0) {
            return null;
        }
        zza(parcel, i, readSize, 8);
        return Long.valueOf(parcel.readLong());
    }

    public static short readShort(Parcel parcel, int i) {
        zza(parcel, i, 4);
        return (short) parcel.readInt();
    }

    public static int readSize(Parcel parcel, int i) {
        return (i & SupportMenu.CATEGORY_MASK) != SupportMenu.CATEGORY_MASK ? (i >> 16) & SupportMenu.USER_MASK : parcel.readInt();
    }

    public static void skipUnknownField(Parcel parcel, int i) {
        parcel.setDataPosition(parcel.dataPosition() + readSize(parcel, i));
    }

    public static int validateObjectHeader(Parcel parcel) {
        int readHeader = readHeader(parcel);
        int readSize = readSize(parcel, readHeader);
        int dataPosition = parcel.dataPosition();
        if (getFieldId(readHeader) != 20293) {
            String str = "Expected object header. Got 0x";
            String valueOf = String.valueOf(Integer.toHexString(readHeader));
            throw new ParseException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), parcel);
        }
        readSize += dataPosition;
        if (readSize >= dataPosition && readSize <= parcel.dataSize()) {
            return readSize;
        }
        StringBuilder stringBuilder = new StringBuilder(54);
        stringBuilder.append("Size read is invalid start=");
        stringBuilder.append(dataPosition);
        stringBuilder.append(" end=");
        stringBuilder.append(readSize);
        throw new ParseException(stringBuilder.toString(), parcel);
    }

    private static void zza(Parcel parcel, int i, int i2) {
        i = readSize(parcel, i);
        if (i != i2) {
            String toHexString = Integer.toHexString(i);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(toHexString).length() + 46);
            stringBuilder.append("Expected size ");
            stringBuilder.append(i2);
            stringBuilder.append(" got ");
            stringBuilder.append(i);
            stringBuilder.append(" (0x");
            stringBuilder.append(toHexString);
            stringBuilder.append(")");
            throw new ParseException(stringBuilder.toString(), parcel);
        }
    }

    private static void zza(Parcel parcel, int i, int i2, int i3) {
        if (i2 != i3) {
            String toHexString = Integer.toHexString(i2);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(toHexString).length() + 46);
            stringBuilder.append("Expected size ");
            stringBuilder.append(i3);
            stringBuilder.append(" got ");
            stringBuilder.append(i2);
            stringBuilder.append(" (0x");
            stringBuilder.append(toHexString);
            stringBuilder.append(")");
            throw new ParseException(stringBuilder.toString(), parcel);
        }
    }
}
