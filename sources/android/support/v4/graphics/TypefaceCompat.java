package android.support.v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry;
import android.support.v4.provider.FontsContractCompat.FontInfo;
import android.support.v4.util.LruCache;

@RestrictTo({Scope.LIBRARY_GROUP})
public class TypefaceCompat {
    private static final String TAG = "TypefaceCompat";
    private static final LruCache<String, Typeface> sTypefaceCache = new LruCache(16);
    private static final TypefaceCompatImpl sTypefaceCompatImpl;

    interface TypefaceCompatImpl {
        Typeface createFromFontFamilyFilesResourceEntry(Context context, FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, Resources resources, int i);

        Typeface createFromFontInfo(Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontInfo[] fontInfoArr, int i);

        Typeface createFromResourcesFontFile(Context context, Resources resources, int i, String str, int i2);
    }

    static {
        if (VERSION.SDK_INT >= 26) {
            sTypefaceCompatImpl = new TypefaceCompatApi26Impl();
        } else if (VERSION.SDK_INT >= 24 && TypefaceCompatApi24Impl.isUsable()) {
            sTypefaceCompatImpl = new TypefaceCompatApi24Impl();
        } else if (VERSION.SDK_INT >= 21) {
            sTypefaceCompatImpl = new TypefaceCompatApi21Impl();
        } else {
            sTypefaceCompatImpl = new TypefaceCompatBaseImpl();
        }
    }

    private TypefaceCompat() {
    }

    @Nullable
    public static Typeface findFromCache(@NonNull Resources resources, int id, int style) {
        return (Typeface) sTypefaceCache.get(createResourceUid(resources, id, style));
    }

    private static String createResourceUid(Resources resources, int id, int style) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(resources.getResourcePackageName(id));
        stringBuilder.append("-");
        stringBuilder.append(id);
        stringBuilder.append("-");
        stringBuilder.append(style);
        return stringBuilder.toString();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.support.annotation.Nullable
    public static android.graphics.Typeface createFromResourcesFamilyXml(@android.support.annotation.NonNull android.content.Context r11, @android.support.annotation.NonNull android.support.v4.content.res.FontResourcesParserCompat.FamilyResourceEntry r12, @android.support.annotation.NonNull android.content.res.Resources r13, int r14, int r15, @android.support.annotation.Nullable android.support.v4.content.res.ResourcesCompat.FontCallback r16, @android.support.annotation.Nullable android.os.Handler r17, boolean r18) {
        /*
        r0 = r12;
        r8 = r16;
        r9 = r17;
        r1 = r0 instanceof android.support.v4.content.res.FontResourcesParserCompat.ProviderResourceEntry;
        if (r1 == 0) goto L_0x003a;
    L_0x0009:
        r10 = r0;
        r10 = (android.support.v4.content.res.FontResourcesParserCompat.ProviderResourceEntry) r10;
        r1 = 0;
        r2 = 1;
        if (r18 == 0) goto L_0x001a;
    L_0x0010:
        r3 = r10.getFetchStrategy();
        if (r3 != 0) goto L_0x0018;
    L_0x0016:
        r5 = r2;
        goto L_0x001d;
    L_0x0018:
        r5 = r1;
        goto L_0x001d;
    L_0x001a:
        if (r8 != 0) goto L_0x0018;
    L_0x001c:
        goto L_0x0016;
    L_0x001d:
        if (r18 == 0) goto L_0x0025;
    L_0x001f:
        r1 = r10.getTimeout();
    L_0x0023:
        r6 = r1;
        goto L_0x0027;
    L_0x0025:
        r1 = -1;
        goto L_0x0023;
    L_0x0027:
        r2 = r10.getRequest();
        r1 = r11;
        r3 = r16;
        r4 = r17;
        r7 = r15;
        r1 = android.support.v4.provider.FontsContractCompat.getFontSync(r1, r2, r3, r4, r5, r6, r7);
        r3 = r11;
        r4 = r13;
        r5 = r15;
        goto L_0x0052;
    L_0x003a:
        r1 = sTypefaceCompatImpl;
        r2 = r0;
        r2 = (android.support.v4.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry) r2;
        r3 = r11;
        r4 = r13;
        r5 = r15;
        r1 = r1.createFromFontFamilyFilesResourceEntry(r11, r2, r13, r15);
        if (r8 == 0) goto L_0x0052;
    L_0x0048:
        if (r1 == 0) goto L_0x004e;
    L_0x004a:
        r8.callbackSuccessAsync(r1, r9);
        goto L_0x0052;
    L_0x004e:
        r2 = -3;
        r8.callbackFailAsync(r2, r9);
    L_0x0052:
        if (r1 == 0) goto L_0x005d;
    L_0x0054:
        r2 = sTypefaceCache;
        r6 = createResourceUid(r13, r14, r15);
        r2.put(r6, r1);
    L_0x005d:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.TypefaceCompat.createFromResourcesFamilyXml(android.content.Context, android.support.v4.content.res.FontResourcesParserCompat$FamilyResourceEntry, android.content.res.Resources, int, int, android.support.v4.content.res.ResourcesCompat$FontCallback, android.os.Handler, boolean):android.graphics.Typeface");
    }

    @Nullable
    public static Typeface createFromResourcesFontFile(@NonNull Context context, @NonNull Resources resources, int id, String path, int style) {
        Typeface typeface = sTypefaceCompatImpl.createFromResourcesFontFile(context, resources, id, path, style);
        if (typeface != null) {
            sTypefaceCache.put(createResourceUid(resources, id, style), typeface);
        }
        return typeface;
    }

    @Nullable
    public static Typeface createFromFontInfo(@NonNull Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontInfo[] fonts, int style) {
        return sTypefaceCompatImpl.createFromFontInfo(context, cancellationSignal, fonts, style);
    }
}
