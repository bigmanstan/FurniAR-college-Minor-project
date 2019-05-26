package com.google.ar.core;

import android.app.Activity;
import android.content.Context;
import com.google.ar.core.exceptions.FatalException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;

public class ArCoreApk {
    static final int AR_AVAILABILITY_SUPPORTED_APK_TOO_OLD = 202;
    static final int AR_AVAILABILITY_SUPPORTED_INSTALLED = 203;
    static final int AR_AVAILABILITY_SUPPORTED_NOT_INSTALLED = 201;
    static final int AR_AVAILABILITY_UNKNOWN_CHECKING = 1;
    static final int AR_AVAILABILITY_UNKNOWN_ERROR = 0;
    static final int AR_AVAILABILITY_UNKNOWN_TIMED_OUT = 2;
    static final int AR_AVAILABILITY_UNSUPPORTED_DEVICE_NOT_CAPABLE = 100;
    static final int AR_INSTALL_BEHAVIOR_OPTIONAL = 1;
    static final int AR_INSTALL_BEHAVIOR_REQUIRED = 0;
    static final int AR_INSTALL_STATUS_INSTALLED = 0;
    static final int AR_INSTALL_STATUS_INSTALL_REQUESTED = 1;
    static final int AR_INSTALL_USER_MESSAGE_TYPE_APPLICATION = 0;
    static final int AR_INSTALL_USER_MESSAGE_TYPE_FEATURE = 1;
    static final int AR_INSTALL_USER_MESSAGE_TYPE_USER_ALREADY_INFORMED = 2;

    public enum Availability {
        UNKNOWN_ERROR(0),
        UNKNOWN_CHECKING(1),
        UNKNOWN_TIMED_OUT(2),
        UNSUPPORTED_DEVICE_NOT_CAPABLE(100),
        SUPPORTED_NOT_INSTALLED(ArCoreApk.AR_AVAILABILITY_SUPPORTED_NOT_INSTALLED),
        SUPPORTED_APK_TOO_OLD(ArCoreApk.AR_AVAILABILITY_SUPPORTED_APK_TOO_OLD),
        SUPPORTED_INSTALLED(ArCoreApk.AR_AVAILABILITY_SUPPORTED_INSTALLED);
        
        final int nativeCode;

        private Availability(int i) {
            this.nativeCode = i;
        }

        static Availability forNumber(int i) {
            for (Availability availability : values()) {
                if (availability.nativeCode == i) {
                    return availability;
                }
            }
            StringBuilder stringBuilder = new StringBuilder(59);
            stringBuilder.append("Unexpected value for native Availability, value=");
            stringBuilder.append(i);
            throw new FatalException(stringBuilder.toString());
        }

        public boolean isSupported() {
            return false;
        }

        public boolean isTransient() {
            return false;
        }

        public boolean isUnknown() {
            return false;
        }

        public boolean isUnsupported() {
            return false;
        }
    }

    public enum InstallBehavior {
        REQUIRED(0),
        OPTIONAL(1);
        
        final int nativeCode;

        private InstallBehavior(int i) {
            this.nativeCode = i;
        }

        static InstallBehavior forNumber(int i) {
            for (InstallBehavior installBehavior : values()) {
                if (installBehavior.nativeCode == i) {
                    return installBehavior;
                }
            }
            StringBuilder stringBuilder = new StringBuilder(62);
            stringBuilder.append("Unexpected value for native InstallBehavior, value=");
            stringBuilder.append(i);
            throw new FatalException(stringBuilder.toString());
        }
    }

    public enum InstallStatus {
        INSTALLED(0),
        INSTALL_REQUESTED(1);
        
        final int nativeCode;

        private InstallStatus(int i) {
            this.nativeCode = i;
        }

        static InstallStatus forNumber(int i) {
            for (InstallStatus installStatus : values()) {
                if (installStatus.nativeCode == i) {
                    return installStatus;
                }
            }
            StringBuilder stringBuilder = new StringBuilder(60);
            stringBuilder.append("Unexpected value for native InstallStatus, value=");
            stringBuilder.append(i);
            throw new FatalException(stringBuilder.toString());
        }
    }

    public enum UserMessageType {
        APPLICATION(0),
        FEATURE(1),
        USER_ALREADY_INFORMED(2);
        
        final int nativeCode;

        private UserMessageType(int i) {
            this.nativeCode = i;
        }

        static UserMessageType forNumber(int i) {
            for (UserMessageType userMessageType : values()) {
                if (userMessageType.nativeCode == i) {
                    return userMessageType;
                }
            }
            StringBuilder stringBuilder = new StringBuilder(62);
            stringBuilder.append("Unexpected value for native UserMessageType, value=");
            stringBuilder.append(i);
            throw new FatalException(stringBuilder.toString());
        }
    }

    /* renamed from: com.google.ar.core.ArCoreApk$a */
    interface C0370a {
        /* renamed from: a */
        void mo1554a(Availability availability);
    }

    protected ArCoreApk() {
    }

    public static ArCoreApk getInstance() {
        return C0555h.m144a();
    }

    private native void nativeFunctionToForceJavahOutput();

    public Availability checkAvailability(Context context) {
        throw new UnsupportedOperationException("Stub");
    }

    public InstallStatus requestInstall(Activity activity, boolean z) throws UnavailableDeviceNotCompatibleException, UnavailableUserDeclinedInstallationException {
        throw new UnsupportedOperationException("Stub");
    }

    public InstallStatus requestInstall(Activity activity, boolean z, InstallBehavior installBehavior, UserMessageType userMessageType) throws UnavailableDeviceNotCompatibleException, UnavailableUserDeclinedInstallationException {
        throw new UnsupportedOperationException("Stub");
    }
}
