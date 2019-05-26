package com.google.android.gms.common.util;

import android.text.TextUtils;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@VisibleForTesting
public final class JsonUtils {
    private static final Pattern zzaae = Pattern.compile("\\\\.");
    private static final Pattern zzaaf = Pattern.compile("[\\\\\"/\b\f\n\r\t]");

    private JsonUtils() {
    }

    public static boolean areJsonStringsEquivalent(String str, String str2) {
        if (str == null && str2 == null) {
            return true;
        }
        if (str != null) {
            if (str2 != null) {
                try {
                    return areJsonValuesEquivalent(new JSONObject(str), new JSONObject(str2));
                } catch (JSONException e) {
                    try {
                        return areJsonValuesEquivalent(new JSONArray(str), new JSONArray(str2));
                    } catch (JSONException e2) {
                    }
                }
            }
        }
        return false;
    }

    public static boolean areJsonValuesEquivalent(Object obj, Object obj2) {
        if (obj == null && obj2 == null) {
            return true;
        }
        if (obj != null) {
            if (obj2 != null) {
                if ((obj instanceof JSONObject) && (obj2 instanceof JSONObject)) {
                    JSONObject jSONObject = (JSONObject) obj;
                    JSONObject jSONObject2 = (JSONObject) obj2;
                    if (jSONObject.length() != jSONObject2.length()) {
                        return false;
                    }
                    Iterator keys = jSONObject.keys();
                    while (keys.hasNext()) {
                        String str = (String) keys.next();
                        if (!jSONObject2.has(str)) {
                            return false;
                        }
                        try {
                            if (!areJsonValuesEquivalent(jSONObject.get(str), jSONObject2.get(str))) {
                                return false;
                            }
                        } catch (JSONException e) {
                            return false;
                        }
                    }
                    return true;
                } else if (!(obj instanceof JSONArray) || !(obj2 instanceof JSONArray)) {
                    return obj.equals(obj2);
                } else {
                    JSONArray jSONArray = (JSONArray) obj;
                    JSONArray jSONArray2 = (JSONArray) obj2;
                    if (jSONArray.length() != jSONArray2.length()) {
                        return false;
                    }
                    int i = 0;
                    while (i < jSONArray.length()) {
                        try {
                            if (!areJsonValuesEquivalent(jSONArray.get(i), jSONArray2.get(i))) {
                                return false;
                            }
                            i++;
                        } catch (JSONException e2) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static String escapeString(String str) {
        if (!TextUtils.isEmpty(str)) {
            Matcher matcher = zzaaf.matcher(str);
            StringBuffer stringBuffer = null;
            while (matcher.find()) {
                String str2;
                if (stringBuffer == null) {
                    stringBuffer = new StringBuffer();
                }
                char charAt = matcher.group().charAt(0);
                if (charAt == '\"') {
                    str2 = "\\\\\\\"";
                } else if (charAt == '/') {
                    str2 = "\\\\/";
                } else if (charAt != '\\') {
                    switch (charAt) {
                        case '\b':
                            str2 = "\\\\b";
                            break;
                        case '\t':
                            str2 = "\\\\t";
                            break;
                        case '\n':
                            str2 = "\\\\n";
                            break;
                        default:
                            switch (charAt) {
                                case '\f':
                                    str2 = "\\\\f";
                                    break;
                                case '\r':
                                    str2 = "\\\\r";
                                    break;
                                default:
                                    continue;
                                    continue;
                            }
                    }
                } else {
                    str2 = "\\\\\\\\";
                }
                matcher.appendReplacement(stringBuffer, str2);
            }
            if (stringBuffer == null) {
                return str;
            }
            matcher.appendTail(stringBuffer);
            str = stringBuffer.toString();
        }
        return str;
    }

    public static String unescapeString(String str) {
        if (!TextUtils.isEmpty(str)) {
            Object unescape = UnicodeUtils.unescape(str);
            Matcher matcher = zzaae.matcher(unescape);
            StringBuffer stringBuffer = null;
            while (matcher.find()) {
                String str2;
                if (stringBuffer == null) {
                    stringBuffer = new StringBuffer();
                }
                char charAt = matcher.group().charAt(1);
                if (charAt == '\"') {
                    str2 = "\"";
                } else if (charAt == '/') {
                    str2 = "/";
                } else if (charAt == '\\') {
                    str2 = "\\\\";
                } else if (charAt == 'b') {
                    str2 = "\b";
                } else if (charAt == 'f') {
                    str2 = "\f";
                } else if (charAt == 'n') {
                    str2 = "\n";
                } else if (charAt == 'r') {
                    str2 = "\r";
                } else if (charAt == 't') {
                    str2 = "\t";
                } else {
                    throw new IllegalStateException("Found an escaped character that should never be.");
                }
                matcher.appendReplacement(stringBuffer, str2);
            }
            if (stringBuffer == null) {
                return unescape;
            }
            matcher.appendTail(stringBuffer);
            str = stringBuffer.toString();
        }
        return str;
    }
}
