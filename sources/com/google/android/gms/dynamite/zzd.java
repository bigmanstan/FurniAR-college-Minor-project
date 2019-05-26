package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule.LoadingException;
import com.google.android.gms.dynamite.DynamiteModule.VersionPolicy;
import com.google.android.gms.dynamite.DynamiteModule.VersionPolicy.IVersions;
import com.google.android.gms.dynamite.DynamiteModule.VersionPolicy.SelectionResult;

final class zzd implements VersionPolicy {
    zzd() {
    }

    public final SelectionResult selectModule(Context context, String str, IVersions iVersions) throws LoadingException {
        int i;
        SelectionResult selectionResult = new SelectionResult();
        selectionResult.localVersion = iVersions.getLocalVersion(context, str);
        selectionResult.remoteVersion = iVersions.getRemoteVersion(context, str, true);
        if (selectionResult.localVersion == 0 && selectionResult.remoteVersion == 0) {
            i = 0;
        } else if (selectionResult.localVersion >= selectionResult.remoteVersion) {
            i = -1;
        } else {
            selectionResult.selection = 1;
            return selectionResult;
        }
        selectionResult.selection = i;
        return selectionResult;
    }
}
