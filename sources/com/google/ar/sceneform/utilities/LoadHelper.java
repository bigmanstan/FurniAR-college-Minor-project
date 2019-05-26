package com.google.ar.sceneform.utilities;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.Callable;

public class LoadHelper {
    private static final String DRAWABLE_RESOURCE_TYPE = "drawable";
    private static final String RAW_RESOURCE_TYPE = "raw";
    private static final char SLASH_DELIMETER = '/';

    private LoadHelper() {
    }

    private static Callable<InputStream> androidResourceUriToInputStreamCreator(Context context, Uri uri) {
        String path = uri.getPath();
        path = path.substring(1, path.lastIndexOf(47));
        if (!path.equals(RAW_RESOURCE_TYPE)) {
            if (!path.equals(DRAWABLE_RESOURCE_TYPE)) {
                String valueOf = String.valueOf(uri);
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(path).length() + 71) + String.valueOf(valueOf).length());
                stringBuilder.append("Unknown resource resourceType '");
                stringBuilder.append(path);
                stringBuilder.append("' in uri '");
                stringBuilder.append(valueOf);
                stringBuilder.append("'. Resource will not be loaded");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        return new C0415b(context, uri);
    }

    private static boolean assetExists(AssetManager assetManager, String str) throws IOException {
        String[] list;
        int lastIndexOf = str.lastIndexOf(47);
        if (lastIndexOf != -1) {
            String substring = str.substring(lastIndexOf + 1);
            list = assetManager.list(str.substring(0, lastIndexOf));
            str = substring;
        } else {
            list = assetManager.list("");
        }
        if (list != null) {
            for (Object equals : list) {
                if (str.equals(equals)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Callable<InputStream> fileUriToInputStreamCreator(Context context, Uri uri) {
        String path;
        AssetManager assets = context.getAssets();
        if (uri.getAuthority() == null) {
            path = uri.getPath();
        } else if (uri.getPath().isEmpty()) {
            path = uri.getAuthority();
        } else {
            String valueOf = String.valueOf(uri.getAuthority());
            path = String.valueOf(uri.getPath());
            path = path.length() != 0 ? valueOf.concat(path) : new String(valueOf);
        }
        return new C0414a(assets, path);
    }

    public static Callable<InputStream> fromUri(Context context, Uri uri) {
        Preconditions.checkNotNull(uri, "Parameter \"sourceUri\" was null.");
        Preconditions.checkNotNull(context, "Parameter \"context\" was null.");
        return isFileAsset(uri).booleanValue() ? fileUriToInputStreamCreator(context, uri) : isAndroidResource(uri).booleanValue() ? androidResourceUriToInputStreamCreator(context, uri) : remoteUriToInputStreamCreator(uri);
    }

    public static Boolean isAndroidResource(Uri uri) {
        Preconditions.checkNotNull(uri, "Parameter \"sourceUri\" was null.");
        return Boolean.valueOf(TextUtils.equals("android.resource", uri.getScheme()));
    }

    public static Boolean isFileAsset(Uri uri) {
        boolean z;
        Preconditions.checkNotNull(uri, "Parameter \"sourceUri\" was null.");
        CharSequence scheme = uri.getScheme();
        if (!TextUtils.isEmpty(scheme)) {
            if (!Objects.equals("file", scheme)) {
                z = false;
                return Boolean.valueOf(z);
            }
        }
        z = true;
        return Boolean.valueOf(z);
    }

    static final /* synthetic */ InputStream lambda$fileUriToInputStreamCreator$0$LoadHelper(AssetManager assetManager, String str) throws Exception {
        return assetExists(assetManager, str) ? assetManager.open(str) : new FileInputStream(new File(str));
    }

    private static Callable<InputStream> remoteUriToInputStreamCreator(Uri uri) {
        try {
            URL url = new URL(uri.toString());
            url.getClass();
            return C0416c.m97a(url);
        } catch (Throwable e) {
            String valueOf = String.valueOf(uri);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 23);
            stringBuilder.append("Unable to parse url: '");
            stringBuilder.append(valueOf);
            stringBuilder.append("'");
            throw new IllegalArgumentException(stringBuilder.toString(), e);
        }
    }

    private static Uri resolve(Uri uri, Uri uri2) {
        try {
            return Uri.parse(new URI(uri.toString()).resolve(new URI(uri2.toString())).toString());
        } catch (Throwable e) {
            throw new IllegalArgumentException("Unable to parse Uri.", e);
        }
    }

    public static Uri resolveUri(Uri uri, Uri uri2) {
        return uri2 == null ? uri : resolve(uri2, uri);
    }

    public static Uri resourceToUri(Context context, int i) {
        Resources resources = context.getResources();
        return new Builder().scheme("android.resource").authority(resources.getResourcePackageName(i)).appendPath(resources.getResourceTypeName(i)).appendPath(resources.getResourceEntryName(i)).build();
    }
}
