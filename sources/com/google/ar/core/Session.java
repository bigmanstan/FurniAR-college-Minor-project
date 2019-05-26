package com.google.ar.core;

import android.content.Context;
import android.os.Build.VERSION;
import com.google.ar.core.annotations.UsedByNative;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.core.exceptions.FatalException;
import com.google.ar.core.exceptions.NotYetAvailableException;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Session {
    static final int AR_ERROR_ANCHOR_NOT_SUPPORTED_FOR_HOSTING = -16;
    static final int AR_ERROR_CAMERA_NOT_AVAILABLE = -13;
    static final int AR_ERROR_CAMERA_PERMISSION_NOT_GRANTED = -9;
    static final int AR_ERROR_CLOUD_ANCHORS_NOT_CONFIGURED = -14;
    static final int AR_ERROR_DATA_INVALID_FORMAT = -18;
    static final int AR_ERROR_DATA_UNSUPPORTED_VERSION = -19;
    static final int AR_ERROR_DEADLINE_EXCEEDED = -10;
    static final int AR_ERROR_FATAL = -2;
    static final int AR_ERROR_ILLEGAL_STATE = -20;
    static final int AR_ERROR_IMAGE_INSUFFICIENT_QUALITY = -17;
    static final int AR_ERROR_INTERNET_PERMISSION_NOT_GRANTED = -15;
    static final int AR_ERROR_INVALID_ARGUMENT = -1;
    static final int AR_ERROR_MISSING_GL_CONTEXT = -7;
    static final int AR_ERROR_NOT_TRACKING = -5;
    static final int AR_ERROR_NOT_YET_AVAILABLE = -12;
    static final int AR_ERROR_RESOURCE_EXHAUSTED = -11;
    static final int AR_ERROR_SESSION_NOT_PAUSED = -4;
    static final int AR_ERROR_SESSION_PAUSED = -3;
    static final int AR_ERROR_TEXTURE_NOT_SET = -6;
    static final int AR_ERROR_UNSUPPORTED_CONFIGURATION = -8;
    static final int AR_SUCCESS = 0;
    static final int AR_TRACKABLE_AUGMENTED_IMAGE = 1095893252;
    static final int AR_TRACKABLE_BASE_TRACKABLE = 1095893248;
    static final int AR_TRACKABLE_NOT_VALID = 0;
    static final int AR_TRACKABLE_PLANE = 1095893249;
    static final int AR_TRACKABLE_POINT = 1095893250;
    static final int AR_TRACKABLE_UNKNOWN_TO_JAVA = -1;
    static final int AR_TRACKING_STATE_PAUSED = 1;
    static final int AR_TRACKING_STATE_STOPPED = 2;
    static final int AR_TRACKING_STATE_TRACKING = 0;
    static final int AR_UNAVAILABLE_APK_TOO_OLD = -103;
    static final int AR_UNAVAILABLE_ARCORE_NOT_INSTALLED = -100;
    static final int AR_UNAVAILABLE_DEVICE_NOT_COMPATIBLE = -101;
    static final int AR_UNAVAILABLE_SDK_TOO_OLD = -104;
    static final int AR_UNAVAILABLE_USER_DECLINED_INSTALLATION = -105;
    private static final String TAG = "ARCore-Session";
    private Context context;
    long nativeHandle;
    private final Object syncObject;

    /* renamed from: com.google.ar.core.Session$a */
    enum C0372a {
        SUCCESS(0),
        ERROR_INVALID_ARGUMENT(-1),
        ERROR_FATAL(-2),
        ERROR_SESSION_PAUSED(-3),
        ERROR_SESSION_NOT_PAUSED(-4),
        ERROR_NOT_TRACKING(Session.AR_ERROR_NOT_TRACKING),
        ERROR_TEXTURE_NOT_SET(-6),
        ERROR_MISSING_GL_CONTEXT(-7),
        ERROR_UNSUPPORTED_CONFIGURATION(-8),
        ERROR_CAMERA_PERMISSION_NOT_GRANTED(-9),
        ERROR_DEADLINE_EXCEEDED(-10),
        ERROR_RESOURCE_EXHAUSTED(-11),
        ERROR_NOT_YET_AVAILABLE(Session.AR_ERROR_NOT_YET_AVAILABLE),
        ERROR_CAMERA_NOT_AVAILABLE(Session.AR_ERROR_CAMERA_NOT_AVAILABLE),
        ERROR_ANCHOR_NOT_SUPPORTED_FOR_HOSTING(Session.AR_ERROR_ANCHOR_NOT_SUPPORTED_FOR_HOSTING),
        ERROR_IMAGE_INSUFFICIENT_QUALITY(Session.AR_ERROR_IMAGE_INSUFFICIENT_QUALITY),
        ERROR_DATA_INVALID_FORMAT(Session.AR_ERROR_DATA_INVALID_FORMAT),
        ERROR_DATA_UNSUPPORTED_VERSION(Session.AR_ERROR_DATA_UNSUPPORTED_VERSION),
        ERROR_ILLEGAL_STATE(Session.AR_ERROR_ILLEGAL_STATE),
        ERROR_CLOUD_ANCHORS_NOT_CONFIGURED(Session.AR_ERROR_CLOUD_ANCHORS_NOT_CONFIGURED),
        ERROR_INTERNET_PERMISSION_NOT_GRANTED(Session.AR_ERROR_INTERNET_PERMISSION_NOT_GRANTED),
        UNAVAILABLE_ARCORE_NOT_INSTALLED(Session.AR_UNAVAILABLE_ARCORE_NOT_INSTALLED),
        UNAVAILABLE_DEVICE_NOT_COMPATIBLE(Session.AR_UNAVAILABLE_DEVICE_NOT_COMPATIBLE),
        UNAVAILABLE_APK_TOO_OLD(Session.AR_UNAVAILABLE_APK_TOO_OLD),
        UNAVAILABLE_SDK_TOO_OLD(Session.AR_UNAVAILABLE_SDK_TOO_OLD),
        UNAVAILABLE_USER_DECLINED_INSTALLATION(Session.AR_UNAVAILABLE_USER_DECLINED_INSTALLATION);
        
        /* renamed from: A */
        private final int f55A;

        private C0372a(int i) {
            this.f55A = i;
        }

        /* renamed from: a */
        static C0372a m55a(int i) {
            for (C0372a c0372a : (C0372a[]) f28B.clone()) {
                if (c0372a.f55A == i) {
                    return c0372a;
                }
            }
            StringBuilder stringBuilder = new StringBuilder(34);
            stringBuilder.append("Unexpected error code: ");
            stringBuilder.append(i);
            throw new FatalException(stringBuilder.toString());
        }

        /* renamed from: a */
        public abstract void mo1548a() throws UnavailableException, NotYetAvailableException, CameraNotAvailableException;
    }

    /* renamed from: com.google.ar.core.Session$b */
    enum C0373b {
        PLANE(Session.AR_TRACKABLE_PLANE, Plane.class),
        POINT(Session.AR_TRACKABLE_POINT, Point.class),
        AUGMENTED_IMAGE(Session.AR_TRACKABLE_AUGMENTED_IMAGE, AugmentedImage.class);
        
        /* renamed from: d */
        private final int f60d;
        /* renamed from: e */
        private final Class<?> f61e;

        private C0373b(int i, Class<? extends Trackable> cls) {
            this.f60d = i;
            this.f61e = cls;
        }

        /* renamed from: a */
        public static C0373b m58a(int i) {
            for (C0373b c0373b : C0373b.m60a()) {
                if (c0373b.f60d == i) {
                    return c0373b;
                }
            }
            return null;
        }

        /* renamed from: a */
        public static C0373b m59a(Class<? extends Trackable> cls) {
            for (C0373b c0373b : C0373b.m60a()) {
                if (c0373b.f61e.equals(cls)) {
                    return c0373b;
                }
            }
            return null;
        }

        /* renamed from: a */
        private static C0373b[] m60a() {
            return (C0373b[]) f59f.clone();
        }

        /* renamed from: a */
        public abstract Trackable mo1553a(long j, Session session);
    }

    protected Session() {
        this.syncObject = new Object();
        this.nativeHandle = 0;
        this.context = null;
    }

    Session(long j) {
        this.syncObject = new Object();
        this.nativeHandle = j;
    }

    public Session(Context context) throws UnavailableArcoreNotInstalledException, UnavailableApkTooOldException, UnavailableSdkTooOldException {
        this.syncObject = new Object();
        System.loadLibrary("arcore_sdk_jni");
        this.context = context;
        this.nativeHandle = nativeCreateSession(context.getApplicationContext());
        loadDynamicSymbolsAfterSessionCreate();
    }

    static int getNativeTrackableFilterFromClass(Class<? extends Trackable> cls) {
        if (cls == Trackable.class) {
            return AR_TRACKABLE_BASE_TRACKABLE;
        }
        C0373b a = C0373b.m59a((Class) cls);
        return a != null ? a.f60d : -1;
    }

    static void loadDynamicSymbolsAfterSessionCreate() {
        if (VERSION.SDK_INT >= 24) {
            ArImage.nativeLoadSymbols();
            ImageMetadata.nativeLoadSymbols();
        }
    }

    private native long[] nativeAcquireAllAnchors(long j);

    private native void nativeConfigure(long j, long j2);

    private native long nativeCreateAnchor(long j, Pose pose);

    private static native long nativeCreateSession(Context context);

    private static native void nativeDestroySession(long j);

    private native long nativeGetCameraConfig(long j);

    private native void nativeGetConfig(long j, long j2);

    private native long[] nativeGetSupportedCameraConfigs(long j);

    private native long nativeHostCloudAnchor(long j, long j2);

    private native boolean nativeIsSupported(long j, long j2);

    private native void nativePause(long j);

    private native long nativeResolveCloudAnchor(long j, String str);

    private native void nativeResume(long j);

    private native int nativeSetCameraConfig(long j, long j2);

    private native void nativeSetCameraTextureName(long j, int i);

    private native void nativeSetDisplayGeometry(long j, int i, int i2, int i3);

    private native void nativeUpdate(long j, long j2);

    @UsedByNative("session_jni.cc")
    static void throwExceptionFromArStatus(int i) throws UnavailableException, NotYetAvailableException, CameraNotAvailableException {
        C0372a.m55a(i).mo1548a();
    }

    public void configure(Config config) {
        nativeConfigure(this.nativeHandle, config.nativeHandle);
    }

    Collection<Anchor> convertNativeAnchorsToCollection(long[] jArr) {
        List arrayList = new ArrayList(jArr.length);
        for (long anchor : jArr) {
            arrayList.add(new Anchor(anchor, this));
        }
        return Collections.unmodifiableList(arrayList);
    }

    List<CameraConfig> convertNativeCameraConfigsToCollection(long[] jArr) {
        List arrayList = new ArrayList(jArr.length);
        for (long cameraConfig : jArr) {
            arrayList.add(new CameraConfig(this, cameraConfig));
        }
        return Collections.unmodifiableList(arrayList);
    }

    <T extends Trackable> Collection<T> convertNativeTrackablesToCollection(Class<T> cls, long[] jArr) {
        List arrayList = new ArrayList(jArr.length);
        for (long createTrackable : jArr) {
            Trackable createTrackable2 = createTrackable(createTrackable);
            if (createTrackable2 != null) {
                arrayList.add((Trackable) cls.cast(createTrackable2));
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Anchor createAnchor(Pose pose) {
        return new Anchor(nativeCreateAnchor(this.nativeHandle, pose), this);
    }

    Trackable createTrackable(long j) {
        C0373b a = C0373b.m58a(TrackableBase.internalGetType(this.nativeHandle, j));
        if (a != null) {
            return a.mo1553a(j, this);
        }
        TrackableBase.internalReleaseNativeHandle(j);
        return null;
    }

    protected void finalize() throws Throwable {
        if (this.nativeHandle != 0) {
            nativeDestroySession(this.nativeHandle);
        }
        super.finalize();
    }

    public Collection<Anchor> getAllAnchors() {
        return convertNativeAnchorsToCollection(nativeAcquireAllAnchors(this.nativeHandle));
    }

    public <T extends Trackable> Collection<T> getAllTrackables(Class<T> cls) {
        int nativeTrackableFilterFromClass = getNativeTrackableFilterFromClass(cls);
        return nativeTrackableFilterFromClass == -1 ? Collections.emptyList() : convertNativeTrackablesToCollection(cls, nativeAcquireAllTrackables(this.nativeHandle, nativeTrackableFilterFromClass));
    }

    public CameraConfig getCameraConfig() {
        return new CameraConfig(this, nativeGetCameraConfig(this.nativeHandle));
    }

    public Config getConfig() {
        Config config = new Config(this);
        getConfig(config);
        return config;
    }

    public void getConfig(Config config) {
        nativeGetConfig(this.nativeHandle, config.nativeHandle);
    }

    public List<CameraConfig> getSupportedCameraConfigs() {
        return convertNativeCameraConfigsToCollection(nativeGetSupportedCameraConfigs(this.nativeHandle));
    }

    public Anchor hostCloudAnchor(Anchor anchor) {
        return new Anchor(nativeHostCloudAnchor(this.nativeHandle, anchor.nativeHandle), this);
    }

    @Deprecated
    public boolean isSupported(Config config) {
        return nativeIsSupported(this.nativeHandle, config.nativeHandle);
    }

    native long[] nativeAcquireAllTrackables(long j, int i);

    public void pause() {
        nativePause(this.nativeHandle);
    }

    public Anchor resolveCloudAnchor(String str) {
        return new Anchor(nativeResolveCloudAnchor(this.nativeHandle, str), this);
    }

    public void resume() throws CameraNotAvailableException {
        nativeResume(this.nativeHandle);
    }

    public void setCameraConfig(CameraConfig cameraConfig) {
        nativeSetCameraConfig(this.nativeHandle, cameraConfig.nativeHandle);
    }

    public void setCameraTextureName(int i) {
        nativeSetCameraTextureName(this.nativeHandle, i);
    }

    public void setDisplayGeometry(int i, int i2, int i3) {
        nativeSetDisplayGeometry(this.nativeHandle, i, i2, i3);
    }

    public Frame update() throws CameraNotAvailableException {
        Frame frame;
        synchronized (this.syncObject) {
            frame = new Frame(this);
            nativeUpdate(this.nativeHandle, frame.nativeHandle);
        }
        return frame;
    }
}
