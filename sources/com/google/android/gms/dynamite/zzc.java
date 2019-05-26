package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule.LoadingException;
import com.google.android.gms.dynamite.DynamiteModule.VersionPolicy;
import com.google.android.gms.dynamite.DynamiteModule.VersionPolicy.IVersions;
import com.google.android.gms.dynamite.DynamiteModule.VersionPolicy.SelectionResult;

final class zzc implements VersionPolicy {
    zzc() {
    }

    public final SelectionResult selectModule(Context context, String str, IVersions iVersions) throws LoadingException {
        SelectionResult selectionResult = new SelectionResult();
        selectionResult.localVersion = iVersions.getLocalVersion(context, str);
        if (selectionResult.localVersion != 0) {
            selectionResult.selection = -1;
        } else {
            selectionResult.remoteVersion = iVersions.getRemoteVersion(context, str, true);
            if (selectionResult.remoteVersion != 0) {
                selectionResult.selection = 1;
            }
        }
        return selectionResult;
    }
}
