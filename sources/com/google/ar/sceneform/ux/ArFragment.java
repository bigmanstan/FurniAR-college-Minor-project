package com.google.ar.sceneform.ux;

import android.util.Log;
import android.widget.Toast;
import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;

public class ArFragment extends BaseArFragment {
    private static final String TAG = "StandardArFragment";

    public boolean isArRequired() {
        return true;
    }

    public String[] getAdditionalPermissions() {
        return new String[0];
    }

    protected void handleSessionException(UnavailableException sessionException) {
        String message;
        if (sessionException instanceof UnavailableArcoreNotInstalledException) {
            message = "Please install ARCore";
        } else if (sessionException instanceof UnavailableApkTooOldException) {
            message = "Please update ARCore";
        } else if (sessionException instanceof UnavailableSdkTooOldException) {
            message = "Please update this app";
        } else if (sessionException instanceof UnavailableDeviceNotCompatibleException) {
            message = "This device does not support AR";
        } else {
            message = "Failed to create AR session";
        }
        String str = TAG;
        String str2 = "Error: ";
        String valueOf = String.valueOf(message);
        Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), sessionException);
        Toast.makeText(requireActivity(), message, 1).show();
    }

    protected Config getSessionConfiguration(Session session) {
        return new Config(session);
    }
}
