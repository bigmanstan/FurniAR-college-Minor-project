package android.support.v4.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleRes;
import android.support.v4.os.BuildCompat;
import android.text.Editable;
import android.util.Log;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class TextViewCompat {
    public static final int AUTO_SIZE_TEXT_TYPE_NONE = 0;
    public static final int AUTO_SIZE_TEXT_TYPE_UNIFORM = 1;
    static final TextViewCompatBaseImpl IMPL;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AutoSizeTextType {
    }

    static class TextViewCompatBaseImpl {
        private static final int LINES = 1;
        private static final String LOG_TAG = "TextViewCompatBase";
        private static Field sMaxModeField;
        private static boolean sMaxModeFieldFetched;
        private static Field sMaximumField;
        private static boolean sMaximumFieldFetched;
        private static Field sMinModeField;
        private static boolean sMinModeFieldFetched;
        private static Field sMinimumField;
        private static boolean sMinimumFieldFetched;

        TextViewCompatBaseImpl() {
        }

        public void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
            textView.setCompoundDrawables(start, top, end, bottom);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
            textView.setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
            textView.setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom);
        }

        private static Field retrieveField(String fieldName) {
            Field field = null;
            try {
                field = TextView.class.getDeclaredField(fieldName);
                field.setAccessible(true);
            } catch (NoSuchFieldException e) {
                String str = LOG_TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not retrieve ");
                stringBuilder.append(fieldName);
                stringBuilder.append(" field.");
                Log.e(str, stringBuilder.toString());
            }
            return field;
        }

        private static int retrieveIntFromField(Field field, TextView textView) {
            try {
                return field.getInt(textView);
            } catch (IllegalAccessException e) {
                String str = LOG_TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not retrieve value of ");
                stringBuilder.append(field.getName());
                stringBuilder.append(" field.");
                Log.d(str, stringBuilder.toString());
                return -1;
            }
        }

        public int getMaxLines(TextView textView) {
            if (!sMaxModeFieldFetched) {
                sMaxModeField = retrieveField("mMaxMode");
                sMaxModeFieldFetched = true;
            }
            if (sMaxModeField != null && retrieveIntFromField(sMaxModeField, textView) == 1) {
                if (!sMaximumFieldFetched) {
                    sMaximumField = retrieveField("mMaximum");
                    sMaximumFieldFetched = true;
                }
                if (sMaximumField != null) {
                    return retrieveIntFromField(sMaximumField, textView);
                }
            }
            return -1;
        }

        public int getMinLines(TextView textView) {
            if (!sMinModeFieldFetched) {
                sMinModeField = retrieveField("mMinMode");
                sMinModeFieldFetched = true;
            }
            if (sMinModeField != null && retrieveIntFromField(sMinModeField, textView) == 1) {
                if (!sMinimumFieldFetched) {
                    sMinimumField = retrieveField("mMinimum");
                    sMinimumFieldFetched = true;
                }
                if (sMinimumField != null) {
                    return retrieveIntFromField(sMinimumField, textView);
                }
            }
            return -1;
        }

        public void setTextAppearance(TextView textView, @StyleRes int resId) {
            textView.setTextAppearance(textView.getContext(), resId);
        }

        public Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
            return textView.getCompoundDrawables();
        }

        public void setAutoSizeTextTypeWithDefaults(TextView textView, int autoSizeTextType) {
            if (textView instanceof AutoSizeableTextView) {
                ((AutoSizeableTextView) textView).setAutoSizeTextTypeWithDefaults(autoSizeTextType);
            }
        }

        public void setAutoSizeTextTypeUniformWithConfiguration(TextView textView, int autoSizeMinTextSize, int autoSizeMaxTextSize, int autoSizeStepGranularity, int unit) throws IllegalArgumentException {
            if (textView instanceof AutoSizeableTextView) {
                ((AutoSizeableTextView) textView).setAutoSizeTextTypeUniformWithConfiguration(autoSizeMinTextSize, autoSizeMaxTextSize, autoSizeStepGranularity, unit);
            }
        }

        public void setAutoSizeTextTypeUniformWithPresetSizes(TextView textView, @NonNull int[] presetSizes, int unit) throws IllegalArgumentException {
            if (textView instanceof AutoSizeableTextView) {
                ((AutoSizeableTextView) textView).setAutoSizeTextTypeUniformWithPresetSizes(presetSizes, unit);
            }
        }

        public int getAutoSizeTextType(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeTextType();
            }
            return 0;
        }

        public int getAutoSizeStepGranularity(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeStepGranularity();
            }
            return -1;
        }

        public int getAutoSizeMinTextSize(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeMinTextSize();
            }
            return -1;
        }

        public int getAutoSizeMaxTextSize(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeMaxTextSize();
            }
            return -1;
        }

        public int[] getAutoSizeTextAvailableSizes(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeTextAvailableSizes();
            }
            return new int[0];
        }

        public void setCustomSelectionActionModeCallback(TextView textView, Callback callback) {
            textView.setCustomSelectionActionModeCallback(callback);
        }
    }

    @RequiresApi(16)
    static class TextViewCompatApi16Impl extends TextViewCompatBaseImpl {
        TextViewCompatApi16Impl() {
        }

        public int getMaxLines(TextView textView) {
            return textView.getMaxLines();
        }

        public int getMinLines(TextView textView) {
            return textView.getMinLines();
        }
    }

    @RequiresApi(17)
    static class TextViewCompatApi17Impl extends TextViewCompatApi16Impl {
        TextViewCompatApi17Impl() {
        }

        public void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
            boolean z = true;
            if (textView.getLayoutDirection() != 1) {
                z = false;
            }
            boolean rtl = z;
            textView.setCompoundDrawables(rtl ? end : start, top, rtl ? start : end, bottom);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
            boolean z = true;
            if (textView.getLayoutDirection() != 1) {
                z = false;
            }
            boolean rtl = z;
            textView.setCompoundDrawablesWithIntrinsicBounds(rtl ? end : start, top, rtl ? start : end, bottom);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
            boolean z = true;
            if (textView.getLayoutDirection() != 1) {
                z = false;
            }
            boolean rtl = z;
            textView.setCompoundDrawablesWithIntrinsicBounds(rtl ? end : start, top, rtl ? start : end, bottom);
        }

        public Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
            boolean z = true;
            if (textView.getLayoutDirection() != 1) {
                z = false;
            }
            boolean rtl = z;
            Drawable[] compounds = textView.getCompoundDrawables();
            if (rtl) {
                Drawable start = compounds[2];
                Drawable end = compounds[0];
                compounds[0] = start;
                compounds[2] = end;
            }
            return compounds;
        }
    }

    @RequiresApi(18)
    static class TextViewCompatApi18Impl extends TextViewCompatApi17Impl {
        TextViewCompatApi18Impl() {
        }

        public void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
            textView.setCompoundDrawablesRelative(start, top, end, bottom);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        }

        public Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
            return textView.getCompoundDrawablesRelative();
        }
    }

    @RequiresApi(23)
    static class TextViewCompatApi23Impl extends TextViewCompatApi18Impl {
        TextViewCompatApi23Impl() {
        }

        public void setTextAppearance(@NonNull TextView textView, @StyleRes int resId) {
            textView.setTextAppearance(resId);
        }
    }

    @RequiresApi(26)
    static class TextViewCompatApi26Impl extends TextViewCompatApi23Impl {
        TextViewCompatApi26Impl() {
        }

        public void setCustomSelectionActionModeCallback(final TextView textView, final Callback callback) {
            if (VERSION.SDK_INT == 26 || VERSION.SDK_INT == 27) {
                textView.setCustomSelectionActionModeCallback(new Callback() {
                    private static final int MENU_ITEM_ORDER_PROCESS_TEXT_INTENT_ACTIONS_START = 100;
                    private boolean mCanUseMenuBuilderReferences;
                    private boolean mInitializedMenuBuilderReferences = null;
                    private Class mMenuBuilderClass;
                    private Method mMenuBuilderRemoveItemAtMethod;

                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        return callback.onCreateActionMode(mode, menu);
                    }

                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        recomputeProcessTextMenuItems(menu);
                        return callback.onPrepareActionMode(mode, menu);
                    }

                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        return callback.onActionItemClicked(mode, item);
                    }

                    public void onDestroyActionMode(ActionMode mode) {
                        callback.onDestroyActionMode(mode);
                    }

                    private void recomputeProcessTextMenuItems(Menu menu) {
                        Context context = textView.getContext();
                        PackageManager packageManager = context.getPackageManager();
                        if (!this.mInitializedMenuBuilderReferences) {
                            this.mInitializedMenuBuilderReferences = true;
                            try {
                                this.mMenuBuilderClass = Class.forName("com.android.internal.view.menu.MenuBuilder");
                                this.mMenuBuilderRemoveItemAtMethod = this.mMenuBuilderClass.getDeclaredMethod("removeItemAt", new Class[]{Integer.TYPE});
                                this.mCanUseMenuBuilderReferences = true;
                            } catch (ClassNotFoundException e) {
                                this.mMenuBuilderClass = null;
                                this.mMenuBuilderRemoveItemAtMethod = null;
                                this.mCanUseMenuBuilderReferences = false;
                            }
                        }
                        try {
                            Method removeItemAtMethod;
                            int i;
                            if (this.mCanUseMenuBuilderReferences && this.mMenuBuilderClass.isInstance(menu)) {
                                removeItemAtMethod = this.mMenuBuilderRemoveItemAtMethod;
                            } else {
                                removeItemAtMethod = menu.getClass().getDeclaredMethod("removeItemAt", new Class[]{Integer.TYPE});
                            }
                            for (i = menu.size() - 1; i >= 0; i--) {
                                MenuItem item = menu.getItem(i);
                                if (item.getIntent() != null && "android.intent.action.PROCESS_TEXT".equals(item.getIntent().getAction())) {
                                    removeItemAtMethod.invoke(menu, new Object[]{Integer.valueOf(i)});
                                }
                            }
                            List<ResolveInfo> supportedActivities = getSupportedActivities(context, packageManager);
                            for (i = 0; i < supportedActivities.size(); i++) {
                                ResolveInfo info = (ResolveInfo) supportedActivities.get(i);
                                menu.add(0, 0, i + 100, info.loadLabel(packageManager)).setIntent(createProcessTextIntentForResolveInfo(info, textView)).setShowAsAction(1);
                            }
                        } catch (NoSuchMethodException e2) {
                        }
                    }

                    private List<ResolveInfo> getSupportedActivities(Context context, PackageManager packageManager) {
                        List<ResolveInfo> supportedActivities = new ArrayList();
                        if (!(context instanceof Activity)) {
                            return supportedActivities;
                        }
                        for (ResolveInfo info : packageManager.queryIntentActivities(createProcessTextIntent(), 0)) {
                            if (isSupportedActivity(info, context)) {
                                supportedActivities.add(info);
                            }
                        }
                        return supportedActivities;
                    }

                    private boolean isSupportedActivity(ResolveInfo info, Context context) {
                        boolean z = true;
                        if (context.getPackageName().equals(info.activityInfo.packageName)) {
                            return true;
                        }
                        if (!info.activityInfo.exported) {
                            return false;
                        }
                        if (info.activityInfo.permission != null) {
                            if (context.checkSelfPermission(info.activityInfo.permission) != 0) {
                                z = false;
                            }
                        }
                        return z;
                    }

                    private Intent createProcessTextIntentForResolveInfo(ResolveInfo info, TextView textView) {
                        return createProcessTextIntent().putExtra("android.intent.extra.PROCESS_TEXT_READONLY", isEditable(textView) ^ 1).setClassName(info.activityInfo.packageName, info.activityInfo.name);
                    }

                    private boolean isEditable(TextView textView) {
                        return (textView instanceof Editable) && textView.onCheckIsTextEditor() && textView.isEnabled();
                    }

                    private Intent createProcessTextIntent() {
                        return new Intent().setAction("android.intent.action.PROCESS_TEXT").setType("text/plain");
                    }
                });
            } else {
                super.setCustomSelectionActionModeCallback(textView, callback);
            }
        }
    }

    @RequiresApi(27)
    static class TextViewCompatApi27Impl extends TextViewCompatApi26Impl {
        TextViewCompatApi27Impl() {
        }

        public void setAutoSizeTextTypeWithDefaults(TextView textView, int autoSizeTextType) {
            textView.setAutoSizeTextTypeWithDefaults(autoSizeTextType);
        }

        public void setAutoSizeTextTypeUniformWithConfiguration(TextView textView, int autoSizeMinTextSize, int autoSizeMaxTextSize, int autoSizeStepGranularity, int unit) throws IllegalArgumentException {
            textView.setAutoSizeTextTypeUniformWithConfiguration(autoSizeMinTextSize, autoSizeMaxTextSize, autoSizeStepGranularity, unit);
        }

        public void setAutoSizeTextTypeUniformWithPresetSizes(TextView textView, @NonNull int[] presetSizes, int unit) throws IllegalArgumentException {
            textView.setAutoSizeTextTypeUniformWithPresetSizes(presetSizes, unit);
        }

        public int getAutoSizeTextType(TextView textView) {
            return textView.getAutoSizeTextType();
        }

        public int getAutoSizeStepGranularity(TextView textView) {
            return textView.getAutoSizeStepGranularity();
        }

        public int getAutoSizeMinTextSize(TextView textView) {
            return textView.getAutoSizeMinTextSize();
        }

        public int getAutoSizeMaxTextSize(TextView textView) {
            return textView.getAutoSizeMaxTextSize();
        }

        public int[] getAutoSizeTextAvailableSizes(TextView textView) {
            return textView.getAutoSizeTextAvailableSizes();
        }
    }

    private TextViewCompat() {
    }

    static {
        if (BuildCompat.isAtLeastOMR1()) {
            IMPL = new TextViewCompatApi27Impl();
        } else if (VERSION.SDK_INT >= 26) {
            IMPL = new TextViewCompatApi26Impl();
        } else if (VERSION.SDK_INT >= 23) {
            IMPL = new TextViewCompatApi23Impl();
        } else if (VERSION.SDK_INT >= 18) {
            IMPL = new TextViewCompatApi18Impl();
        } else if (VERSION.SDK_INT >= 17) {
            IMPL = new TextViewCompatApi17Impl();
        } else if (VERSION.SDK_INT >= 16) {
            IMPL = new TextViewCompatApi16Impl();
        } else {
            IMPL = new TextViewCompatBaseImpl();
        }
    }

    public static void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
        IMPL.setCompoundDrawablesRelative(textView, start, top, end, bottom);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
        IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds(textView, start, top, end, bottom);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
        IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds(textView, start, top, end, bottom);
    }

    public static int getMaxLines(@NonNull TextView textView) {
        return IMPL.getMaxLines(textView);
    }

    public static int getMinLines(@NonNull TextView textView) {
        return IMPL.getMinLines(textView);
    }

    public static void setTextAppearance(@NonNull TextView textView, @StyleRes int resId) {
        IMPL.setTextAppearance(textView, resId);
    }

    @NonNull
    public static Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
        return IMPL.getCompoundDrawablesRelative(textView);
    }

    public static void setAutoSizeTextTypeWithDefaults(@NonNull TextView textView, int autoSizeTextType) {
        IMPL.setAutoSizeTextTypeWithDefaults(textView, autoSizeTextType);
    }

    public static void setAutoSizeTextTypeUniformWithConfiguration(@NonNull TextView textView, int autoSizeMinTextSize, int autoSizeMaxTextSize, int autoSizeStepGranularity, int unit) throws IllegalArgumentException {
        IMPL.setAutoSizeTextTypeUniformWithConfiguration(textView, autoSizeMinTextSize, autoSizeMaxTextSize, autoSizeStepGranularity, unit);
    }

    public static void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull TextView textView, @NonNull int[] presetSizes, int unit) throws IllegalArgumentException {
        IMPL.setAutoSizeTextTypeUniformWithPresetSizes(textView, presetSizes, unit);
    }

    public static int getAutoSizeTextType(@NonNull TextView textView) {
        return IMPL.getAutoSizeTextType(textView);
    }

    public static int getAutoSizeStepGranularity(@NonNull TextView textView) {
        return IMPL.getAutoSizeStepGranularity(textView);
    }

    public static int getAutoSizeMinTextSize(@NonNull TextView textView) {
        return IMPL.getAutoSizeMinTextSize(textView);
    }

    public static int getAutoSizeMaxTextSize(@NonNull TextView textView) {
        return IMPL.getAutoSizeMaxTextSize(textView);
    }

    @NonNull
    public static int[] getAutoSizeTextAvailableSizes(@NonNull TextView textView) {
        return IMPL.getAutoSizeTextAvailableSizes(textView);
    }

    public static void setCustomSelectionActionModeCallback(@NonNull TextView textView, @NonNull Callback callback) {
        IMPL.setCustomSelectionActionModeCallback(textView, callback);
    }
}
