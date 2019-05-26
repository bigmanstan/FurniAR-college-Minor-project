package com.google.android.filament;

import android.support.annotation.NonNull;

abstract class Platform {
    private static Platform mCurrentPlatform = null;

    private static class UnknownPlatform extends Platform {
        private UnknownPlatform() {
        }

        void log(String message) {
            System.out.println(message);
        }

        void warn(String message) {
            System.out.println(message);
        }

        boolean validateStreamSource(Object object) {
            return false;
        }

        boolean validateSurface(Object object) {
            return false;
        }

        boolean validateSharedContext(Object object) {
            return false;
        }

        long getSharedContextNativeHandle(Object sharedContext) {
            return 0;
        }
    }

    abstract long getSharedContextNativeHandle(Object obj);

    abstract void log(String str);

    abstract boolean validateSharedContext(Object obj);

    abstract boolean validateStreamSource(Object obj);

    abstract boolean validateSurface(Object obj);

    abstract void warn(String str);

    static boolean isAndroid() {
        return "The Android Project".equalsIgnoreCase(System.getProperty("java.vendor"));
    }

    static boolean isWindows() {
        return System.getProperty("os.name").contains("Windows");
    }

    static boolean isMacOS() {
        return System.getProperty("os.name").contains("Mac OS X");
    }

    static boolean isLinux() {
        return System.getProperty("os.name").contains("Linux") && !isAndroid();
    }

    @NonNull
    static Platform get() {
        if (mCurrentPlatform == null) {
            try {
                if (isAndroid()) {
                    mCurrentPlatform = (Platform) Class.forName("com.google.android.filament.AndroidPlatform").newInstance();
                } else {
                    mCurrentPlatform = (Platform) Class.forName("com.google.android.filament.DesktopPlatform").newInstance();
                }
            } catch (Exception e) {
            }
            if (mCurrentPlatform == null) {
                mCurrentPlatform = new UnknownPlatform();
            }
        }
        return mCurrentPlatform;
    }

    Platform() {
    }
}
