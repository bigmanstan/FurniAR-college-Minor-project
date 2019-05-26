package android.support.v4.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.Typeface.Builder;
import android.graphics.fonts.FontVariationAxis;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry;
import android.support.v4.content.res.FontResourcesParserCompat.FontFileResourceEntry;
import android.support.v4.provider.FontsContractCompat;
import android.support.v4.provider.FontsContractCompat.FontInfo;
import android.util.Log;
import java.io.IOException;
import java.lang.reflect.AbstractMethod;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Map;

@RequiresApi(26)
@RestrictTo({Scope.LIBRARY_GROUP})
public class TypefaceCompatApi26Impl extends TypefaceCompatApi21Impl {
    private static final String ABORT_CREATION_METHOD = "abortCreation";
    private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
    private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String FREEZE_METHOD = "freeze";
    private static final int RESOLVE_BY_FONT_TABLE = -1;
    private static final String TAG = "TypefaceCompatApi26Impl";
    private static final Method sAbortCreation;
    private static final Method sAddFontFromAssetManager;
    private static final Method sAddFontFromBuffer;
    private static final Method sCreateFromFamiliesWithDefault;
    private static final Class sFontFamily;
    private static final Constructor sFontFamilyCtor;
    private static final Method sFreeze;

    static {
        Class fontFamilyClass;
        Constructor fontFamilyCtor;
        Method addFontMethod;
        Method createFromFamiliesWithDefaultMethod;
        ReflectiveOperationException e;
        Method abortCreationMethod;
        String str;
        StringBuilder stringBuilder;
        Method freezeMethod;
        Method createFromFamiliesWithDefaultMethod2;
        AbstractMethod fontFamilyCtor2;
        Method method = null;
        try {
            fontFamilyClass = Class.forName(FONT_FAMILY_CLASS);
            try {
                fontFamilyCtor = fontFamilyClass.getConstructor(new Class[0]);
                try {
                    addFontMethod = fontFamilyClass.getMethod(ADD_FONT_FROM_ASSET_MANAGER_METHOD, new Class[]{AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, FontVariationAxis[].class});
                    try {
                        createFromFamiliesWithDefaultMethod = fontFamilyClass.getMethod(ADD_FONT_FROM_BUFFER_METHOD, new Class[]{ByteBuffer.class, Integer.TYPE, FontVariationAxis[].class, Integer.TYPE, Integer.TYPE});
                    } catch (ClassNotFoundException e2) {
                        e = e2;
                        createFromFamiliesWithDefaultMethod = null;
                        abortCreationMethod = createFromFamiliesWithDefaultMethod;
                        str = TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Unable to collect necessary methods for class ");
                        stringBuilder.append(e.getClass().getName());
                        Log.e(str, stringBuilder.toString(), e);
                        fontFamilyClass = null;
                        fontFamilyCtor = null;
                        addFontMethod = null;
                        method = null;
                        freezeMethod = null;
                        abortCreationMethod = null;
                        createFromFamiliesWithDefaultMethod2 = null;
                        sFontFamilyCtor = fontFamilyCtor;
                        sFontFamily = fontFamilyClass;
                        sAddFontFromAssetManager = addFontMethod;
                        sAddFontFromBuffer = method;
                        sFreeze = freezeMethod;
                        sAbortCreation = abortCreationMethod;
                        sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod2;
                    }
                } catch (ClassNotFoundException e3) {
                    e = e3;
                    addFontMethod = null;
                    createFromFamiliesWithDefaultMethod = addFontMethod;
                    abortCreationMethod = createFromFamiliesWithDefaultMethod;
                    str = TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Unable to collect necessary methods for class ");
                    stringBuilder.append(e.getClass().getName());
                    Log.e(str, stringBuilder.toString(), e);
                    fontFamilyClass = null;
                    fontFamilyCtor = null;
                    addFontMethod = null;
                    method = null;
                    freezeMethod = null;
                    abortCreationMethod = null;
                    createFromFamiliesWithDefaultMethod2 = null;
                    sFontFamilyCtor = fontFamilyCtor;
                    sFontFamily = fontFamilyClass;
                    sAddFontFromAssetManager = addFontMethod;
                    sAddFontFromBuffer = method;
                    sFreeze = freezeMethod;
                    sAbortCreation = abortCreationMethod;
                    sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod2;
                }
                try {
                    freezeMethod = fontFamilyClass.getMethod(FREEZE_METHOD, new Class[0]);
                } catch (ClassNotFoundException e4) {
                    e = e4;
                    abortCreationMethod = null;
                    method = createFromFamiliesWithDefaultMethod;
                    createFromFamiliesWithDefaultMethod = abortCreationMethod;
                    str = TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Unable to collect necessary methods for class ");
                    stringBuilder.append(e.getClass().getName());
                    Log.e(str, stringBuilder.toString(), e);
                    fontFamilyClass = null;
                    fontFamilyCtor = null;
                    addFontMethod = null;
                    method = null;
                    freezeMethod = null;
                    abortCreationMethod = null;
                    createFromFamiliesWithDefaultMethod2 = null;
                    sFontFamilyCtor = fontFamilyCtor;
                    sFontFamily = fontFamilyClass;
                    sAddFontFromAssetManager = addFontMethod;
                    sAddFontFromBuffer = method;
                    sFreeze = freezeMethod;
                    sAbortCreation = abortCreationMethod;
                    sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod2;
                }
            } catch (ClassNotFoundException e5) {
                e = e5;
                fontFamilyCtor2 = null;
                addFontMethod = fontFamilyCtor2;
                createFromFamiliesWithDefaultMethod = addFontMethod;
                abortCreationMethod = createFromFamiliesWithDefaultMethod;
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to collect necessary methods for class ");
                stringBuilder.append(e.getClass().getName());
                Log.e(str, stringBuilder.toString(), e);
                fontFamilyClass = null;
                fontFamilyCtor = null;
                addFontMethod = null;
                method = null;
                freezeMethod = null;
                abortCreationMethod = null;
                createFromFamiliesWithDefaultMethod2 = null;
                sFontFamilyCtor = fontFamilyCtor;
                sFontFamily = fontFamilyClass;
                sAddFontFromAssetManager = addFontMethod;
                sAddFontFromBuffer = method;
                sFreeze = freezeMethod;
                sAbortCreation = abortCreationMethod;
                sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod2;
            }
            try {
                abortCreationMethod = fontFamilyClass.getMethod(ABORT_CREATION_METHOD, new Class[0]);
                try {
                    Object familyArray = Array.newInstance(fontFamilyClass, 1);
                    method = Typeface.class.getDeclaredMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, new Class[]{familyArray.getClass(), Integer.TYPE, Integer.TYPE});
                    method.setAccessible(true);
                    createFromFamiliesWithDefaultMethod2 = method;
                    method = createFromFamiliesWithDefaultMethod;
                } catch (ClassNotFoundException e6) {
                    e = e6;
                    Method method2 = createFromFamiliesWithDefaultMethod;
                    createFromFamiliesWithDefaultMethod = method;
                    method = method2;
                    str = TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Unable to collect necessary methods for class ");
                    stringBuilder.append(e.getClass().getName());
                    Log.e(str, stringBuilder.toString(), e);
                    fontFamilyClass = null;
                    fontFamilyCtor = null;
                    addFontMethod = null;
                    method = null;
                    freezeMethod = null;
                    abortCreationMethod = null;
                    createFromFamiliesWithDefaultMethod2 = null;
                    sFontFamilyCtor = fontFamilyCtor;
                    sFontFamily = fontFamilyClass;
                    sAddFontFromAssetManager = addFontMethod;
                    sAddFontFromBuffer = method;
                    sFreeze = freezeMethod;
                    sAbortCreation = abortCreationMethod;
                    sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod2;
                }
            } catch (ClassNotFoundException e7) {
                e = e7;
                abortCreationMethod = null;
                method = createFromFamiliesWithDefaultMethod;
                createFromFamiliesWithDefaultMethod = abortCreationMethod;
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to collect necessary methods for class ");
                stringBuilder.append(e.getClass().getName());
                Log.e(str, stringBuilder.toString(), e);
                fontFamilyClass = null;
                fontFamilyCtor = null;
                addFontMethod = null;
                method = null;
                freezeMethod = null;
                abortCreationMethod = null;
                createFromFamiliesWithDefaultMethod2 = null;
                sFontFamilyCtor = fontFamilyCtor;
                sFontFamily = fontFamilyClass;
                sAddFontFromAssetManager = addFontMethod;
                sAddFontFromBuffer = method;
                sFreeze = freezeMethod;
                sAbortCreation = abortCreationMethod;
                sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod2;
            }
        } catch (ClassNotFoundException e8) {
            e = e8;
            fontFamilyCtor2 = null;
            addFontMethod = fontFamilyCtor2;
            createFromFamiliesWithDefaultMethod = addFontMethod;
            abortCreationMethod = createFromFamiliesWithDefaultMethod;
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to collect necessary methods for class ");
            stringBuilder.append(e.getClass().getName());
            Log.e(str, stringBuilder.toString(), e);
            fontFamilyClass = null;
            fontFamilyCtor = null;
            addFontMethod = null;
            method = null;
            freezeMethod = null;
            abortCreationMethod = null;
            createFromFamiliesWithDefaultMethod2 = null;
            sFontFamilyCtor = fontFamilyCtor;
            sFontFamily = fontFamilyClass;
            sAddFontFromAssetManager = addFontMethod;
            sAddFontFromBuffer = method;
            sFreeze = freezeMethod;
            sAbortCreation = abortCreationMethod;
            sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod2;
        }
        sFontFamilyCtor = fontFamilyCtor;
        sFontFamily = fontFamilyClass;
        sAddFontFromAssetManager = addFontMethod;
        sAddFontFromBuffer = method;
        sFreeze = freezeMethod;
        sAbortCreation = abortCreationMethod;
        sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod2;
    }

    private static boolean isFontFamilyPrivateAPIAvailable() {
        if (sAddFontFromAssetManager == null) {
            Log.w(TAG, "Unable to collect necessary private methods. Fallback to legacy implementation.");
        }
        return sAddFontFromAssetManager != null;
    }

    private static Object newFamily() {
        try {
            return sFontFamilyCtor.newInstance(new Object[0]);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean addFontFromAssetManager(Context context, Object family, String fileName, int ttcIndex, int weight, int style) {
        try {
            return ((Boolean) sAddFontFromAssetManager.invoke(family, new Object[]{context.getAssets(), fileName, Integer.valueOf(0), Boolean.valueOf(false), Integer.valueOf(ttcIndex), Integer.valueOf(weight), Integer.valueOf(style), null})).booleanValue();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean addFontFromBuffer(Object family, ByteBuffer buffer, int ttcIndex, int weight, int style) {
        try {
            return ((Boolean) sAddFontFromBuffer.invoke(family, new Object[]{buffer, Integer.valueOf(ttcIndex), null, Integer.valueOf(weight), Integer.valueOf(style)})).booleanValue();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static Typeface createFromFamiliesWithDefault(Object family) {
        try {
            Array.set(Array.newInstance(sFontFamily, 1), 0, family);
            return (Typeface) sCreateFromFamiliesWithDefault.invoke(null, new Object[]{familyArray, Integer.valueOf(-1), Integer.valueOf(-1)});
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean freeze(Object family) {
        try {
            return ((Boolean) sFreeze.invoke(family, new Object[0])).booleanValue();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static void abortCreation(Object family) {
        try {
            sAbortCreation.invoke(family, new Object[0]);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontFamilyFilesResourceEntry entry, Resources resources, int style) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromFontFamilyFilesResourceEntry(context, entry, resources, style);
        }
        Object fontFamily = newFamily();
        FontFileResourceEntry[] entries = entry.getEntries();
        int length = entries.length;
        int i = 0;
        while (i < length) {
            FontFileResourceEntry fontFile = entries[i];
            if (addFontFromAssetManager(context, fontFamily, fontFile.getFileName(), 0, fontFile.getWeight(), fontFile.isItalic())) {
                i++;
            } else {
                abortCreation(fontFamily);
                return null;
            }
        }
        if (freeze(fontFamily)) {
            return createFromFamiliesWithDefault(fontFamily);
        }
        return null;
    }

    public Typeface createFromFontInfo(Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontInfo[] fonts, int style) {
        ParcelFileDescriptor pfd;
        Throwable th;
        Throwable th2;
        CancellationSignal cancellationSignal2 = cancellationSignal;
        FontInfo[] fontInfoArr = fonts;
        int i = style;
        if (fontInfoArr.length < 1) {
            return null;
        }
        if (isFontFamilyPrivateAPIAvailable()) {
            TypefaceCompatApi26Impl typefaceCompatApi26Impl = this;
            Map<Uri, ByteBuffer> uriBuffer = FontsContractCompat.prepareFontData(context, fontInfoArr, cancellationSignal2);
            Object fontFamily = newFamily();
            boolean atLeastOneFont = false;
            for (FontInfo font : fontInfoArr) {
                ByteBuffer fontBuffer = (ByteBuffer) uriBuffer.get(font.getUri());
                if (fontBuffer != null) {
                    if (addFontFromBuffer(fontFamily, fontBuffer, font.getTtcIndex(), font.getWeight(), font.isItalic())) {
                        atLeastOneFont = true;
                    } else {
                        abortCreation(fontFamily);
                        return null;
                    }
                }
            }
            if (!atLeastOneFont) {
                abortCreation(fontFamily);
                return null;
            } else if (freeze(fontFamily)) {
                return Typeface.create(createFromFamiliesWithDefault(fontFamily), i);
            } else {
                return null;
            }
        }
        FontInfo bestFont = findBestInfo(fontInfoArr, i);
        try {
            pfd = context.getContentResolver().openFileDescriptor(bestFont.getUri(), "r", cancellationSignal2);
            if (pfd == null) {
                if (pfd != null) {
                    pfd.close();
                }
                return null;
            }
            try {
                Typeface build = new Builder(pfd.getFileDescriptor()).setWeight(bestFont.getWeight()).setItalic(bestFont.isItalic()).build();
                if (pfd != null) {
                    pfd.close();
                }
                return build;
            } catch (Throwable th3) {
                th2 = th3;
                th = th3;
            }
        } catch (IOException e) {
            return null;
        }
        if (pfd != null) {
            if (th2 != null) {
                try {
                    pfd.close();
                } catch (Throwable th32) {
                    th2.addSuppressed(th32);
                }
            } else {
                pfd.close();
            }
        }
        throw th;
        throw th;
    }

    @Nullable
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int id, String path, int style) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromResourcesFontFile(context, resources, id, path, style);
        }
        Object fontFamily = newFamily();
        if (!addFontFromAssetManager(context, fontFamily, path, 0, -1, -1)) {
            abortCreation(fontFamily);
            return null;
        } else if (freeze(fontFamily)) {
            return createFromFamiliesWithDefault(fontFamily);
        } else {
            return null;
        }
    }
}
