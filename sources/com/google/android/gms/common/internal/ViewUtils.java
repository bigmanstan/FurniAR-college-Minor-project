package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

public class ViewUtils {
    private ViewUtils() {
    }

    public static String getXmlAttributeString(String str, String str2, Context context, AttributeSet attributeSet, boolean z, boolean z2, String str3) {
        StringBuilder stringBuilder;
        str = attributeSet == null ? null : attributeSet.getAttributeValue(str, str2);
        if (str != null && str.startsWith("@string/") && z) {
            String substring = str.substring(8);
            String packageName = context.getPackageName();
            TypedValue typedValue = new TypedValue();
            try {
                Resources resources = context.getResources();
                stringBuilder = new StringBuilder((String.valueOf(packageName).length() + 8) + String.valueOf(substring).length());
                stringBuilder.append(packageName);
                stringBuilder.append(":string/");
                stringBuilder.append(substring);
                resources.getValue(stringBuilder.toString(), typedValue, true);
            } catch (NotFoundException e) {
                stringBuilder = new StringBuilder((String.valueOf(str2).length() + 30) + String.valueOf(str).length());
                stringBuilder.append("Could not find resource for ");
                stringBuilder.append(str2);
                stringBuilder.append(": ");
                stringBuilder.append(str);
                Log.w(str3, stringBuilder.toString());
            }
            if (typedValue.string != null) {
                str = typedValue.string.toString();
            } else {
                String valueOf = String.valueOf(typedValue);
                StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(str2).length() + 28) + String.valueOf(valueOf).length());
                stringBuilder2.append("Resource ");
                stringBuilder2.append(str2);
                stringBuilder2.append(" was not a string: ");
                stringBuilder2.append(valueOf);
                Log.w(str3, stringBuilder2.toString());
            }
        }
        if (z2 && str == null) {
            stringBuilder = new StringBuilder(String.valueOf(str2).length() + 33);
            stringBuilder.append("Required XML attribute \"");
            stringBuilder.append(str2);
            stringBuilder.append("\" missing");
            Log.w(str3, stringBuilder.toString());
        }
        return str;
    }
}
