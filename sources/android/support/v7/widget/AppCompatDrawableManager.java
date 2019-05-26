package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.LruCache;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.appcompat.C0015R;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({Scope.LIBRARY_GROUP})
public final class AppCompatDrawableManager {
    private static final int[] COLORFILTER_COLOR_BACKGROUND_MULTIPLY = new int[]{C0015R.drawable.abc_popup_background_mtrl_mult, C0015R.drawable.abc_cab_background_internal_bg, C0015R.drawable.abc_menu_hardkey_panel_mtrl_mult};
    private static final int[] COLORFILTER_COLOR_CONTROL_ACTIVATED = new int[]{C0015R.drawable.abc_textfield_activated_mtrl_alpha, C0015R.drawable.abc_textfield_search_activated_mtrl_alpha, C0015R.drawable.abc_cab_background_top_mtrl_alpha, C0015R.drawable.abc_text_cursor_material, C0015R.drawable.abc_text_select_handle_left_mtrl_dark, C0015R.drawable.abc_text_select_handle_middle_mtrl_dark, C0015R.drawable.abc_text_select_handle_right_mtrl_dark, C0015R.drawable.abc_text_select_handle_left_mtrl_light, C0015R.drawable.abc_text_select_handle_middle_mtrl_light, C0015R.drawable.abc_text_select_handle_right_mtrl_light};
    private static final int[] COLORFILTER_TINT_COLOR_CONTROL_NORMAL = new int[]{C0015R.drawable.abc_textfield_search_default_mtrl_alpha, C0015R.drawable.abc_textfield_default_mtrl_alpha, C0015R.drawable.abc_ab_share_pack_mtrl_alpha};
    private static final ColorFilterLruCache COLOR_FILTER_CACHE = new ColorFilterLruCache(6);
    private static final boolean DEBUG = false;
    private static final Mode DEFAULT_MODE = Mode.SRC_IN;
    private static AppCompatDrawableManager INSTANCE = null;
    private static final String PLATFORM_VD_CLAZZ = "android.graphics.drawable.VectorDrawable";
    private static final String SKIP_DRAWABLE_TAG = "appcompat_skip_skip";
    private static final String TAG = "AppCompatDrawableManag";
    private static final int[] TINT_CHECKABLE_BUTTON_LIST = new int[]{C0015R.drawable.abc_btn_check_material, C0015R.drawable.abc_btn_radio_material};
    private static final int[] TINT_COLOR_CONTROL_NORMAL = new int[]{C0015R.drawable.abc_ic_commit_search_api_mtrl_alpha, C0015R.drawable.abc_seekbar_tick_mark_material, C0015R.drawable.abc_ic_menu_share_mtrl_alpha, C0015R.drawable.abc_ic_menu_copy_mtrl_am_alpha, C0015R.drawable.abc_ic_menu_cut_mtrl_alpha, C0015R.drawable.abc_ic_menu_selectall_mtrl_alpha, C0015R.drawable.abc_ic_menu_paste_mtrl_am_alpha};
    private static final int[] TINT_COLOR_CONTROL_STATE_LIST = new int[]{C0015R.drawable.abc_tab_indicator_material, C0015R.drawable.abc_textfield_search_material};
    private ArrayMap<String, InflateDelegate> mDelegates;
    private final Object mDrawableCacheLock = new Object();
    private final WeakHashMap<Context, LongSparseArray<WeakReference<ConstantState>>> mDrawableCaches = new WeakHashMap(0);
    private boolean mHasCheckedVectorDrawableSetup;
    private SparseArrayCompat<String> mKnownDrawableIdTags;
    private WeakHashMap<Context, SparseArrayCompat<ColorStateList>> mTintLists;
    private TypedValue mTypedValue;

    private interface InflateDelegate {
        Drawable createFromXmlInner(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Theme theme);
    }

    @RequiresApi(11)
    private static class AvdcInflateDelegate implements InflateDelegate {
        AvdcInflateDelegate() {
        }

        public Drawable createFromXmlInner(@NonNull Context context, @NonNull XmlPullParser parser, @NonNull AttributeSet attrs, @Nullable Theme theme) {
            try {
                return AnimatedVectorDrawableCompat.createFromXmlInner(context, context.getResources(), parser, attrs, theme);
            } catch (Exception e) {
                Log.e("AvdcInflateDelegate", "Exception while inflating <animated-vector>", e);
                return null;
            }
        }
    }

    private static class ColorFilterLruCache extends LruCache<Integer, PorterDuffColorFilter> {
        public ColorFilterLruCache(int maxSize) {
            super(maxSize);
        }

        PorterDuffColorFilter get(int color, Mode mode) {
            return (PorterDuffColorFilter) get(Integer.valueOf(generateCacheKey(color, mode)));
        }

        PorterDuffColorFilter put(int color, Mode mode, PorterDuffColorFilter filter) {
            return (PorterDuffColorFilter) put(Integer.valueOf(generateCacheKey(color, mode)), filter);
        }

        private static int generateCacheKey(int color, Mode mode) {
            return (((1 * 31) + color) * 31) + mode.hashCode();
        }
    }

    private static class VdcInflateDelegate implements InflateDelegate {
        VdcInflateDelegate() {
        }

        public Drawable createFromXmlInner(@NonNull Context context, @NonNull XmlPullParser parser, @NonNull AttributeSet attrs, @Nullable Theme theme) {
            try {
                return VectorDrawableCompat.createFromXmlInner(context.getResources(), parser, attrs, theme);
            } catch (Exception e) {
                Log.e("VdcInflateDelegate", "Exception while inflating <vector>", e);
                return null;
            }
        }
    }

    public static AppCompatDrawableManager get() {
        if (INSTANCE == null) {
            INSTANCE = new AppCompatDrawableManager();
            installDefaultInflateDelegates(INSTANCE);
        }
        return INSTANCE;
    }

    private static void installDefaultInflateDelegates(@NonNull AppCompatDrawableManager manager) {
        if (VERSION.SDK_INT < 24) {
            manager.addDelegate("vector", new VdcInflateDelegate());
            manager.addDelegate("animated-vector", new AvdcInflateDelegate());
        }
    }

    public Drawable getDrawable(@NonNull Context context, @DrawableRes int resId) {
        return getDrawable(context, resId, false);
    }

    Drawable getDrawable(@NonNull Context context, @DrawableRes int resId, boolean failIfNotKnown) {
        checkVectorDrawableSetup(context);
        Drawable drawable = loadDrawableFromDelegates(context, resId);
        if (drawable == null) {
            drawable = createDrawableIfNeeded(context, resId);
        }
        if (drawable == null) {
            drawable = ContextCompat.getDrawable(context, resId);
        }
        if (drawable != null) {
            drawable = tintDrawable(context, resId, failIfNotKnown, drawable);
        }
        if (drawable != null) {
            DrawableUtils.fixDrawable(drawable);
        }
        return drawable;
    }

    public void onConfigurationChanged(@NonNull Context context) {
        synchronized (this.mDrawableCacheLock) {
            LongSparseArray<WeakReference<ConstantState>> cache = (LongSparseArray) this.mDrawableCaches.get(context);
            if (cache != null) {
                cache.clear();
            }
        }
    }

    private static long createCacheKey(TypedValue tv) {
        return (((long) tv.assetCookie) << 32) | ((long) tv.data);
    }

    private Drawable createDrawableIfNeeded(@NonNull Context context, @DrawableRes int resId) {
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        TypedValue tv = this.mTypedValue;
        context.getResources().getValue(resId, tv, true);
        long key = createCacheKey(tv);
        Drawable dr = getCachedDrawable(context, key);
        if (dr != null) {
            return dr;
        }
        if (resId == C0015R.drawable.abc_cab_background_top_material) {
            dr = new LayerDrawable(new Drawable[]{getDrawable(context, C0015R.drawable.abc_cab_background_internal_bg), getDrawable(context, C0015R.drawable.abc_cab_background_top_mtrl_alpha)});
        }
        if (dr != null) {
            dr.setChangingConfigurations(tv.changingConfigurations);
            addDrawableToCache(context, key, dr);
        }
        return dr;
    }

    private Drawable tintDrawable(@NonNull Context context, @DrawableRes int resId, boolean failIfNotKnown, @NonNull Drawable drawable) {
        ColorStateList tintList = getTintList(context, resId);
        if (tintList != null) {
            if (DrawableUtils.canSafelyMutateDrawable(drawable)) {
                drawable = drawable.mutate();
            }
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTintList(drawable, tintList);
            Mode tintMode = getTintMode(resId);
            if (tintMode != null) {
                DrawableCompat.setTintMode(drawable, tintMode);
            }
            return drawable;
        } else if (resId == C0015R.drawable.abc_seekbar_track_material) {
            ld = (LayerDrawable) drawable;
            setPorterDuffColorFilter(ld.findDrawableByLayerId(16908288), ThemeUtils.getThemeAttrColor(context, C0015R.attr.colorControlNormal), DEFAULT_MODE);
            setPorterDuffColorFilter(ld.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor(context, C0015R.attr.colorControlNormal), DEFAULT_MODE);
            setPorterDuffColorFilter(ld.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor(context, C0015R.attr.colorControlActivated), DEFAULT_MODE);
            return drawable;
        } else {
            if (!(resId == C0015R.drawable.abc_ratingbar_material || resId == C0015R.drawable.abc_ratingbar_indicator_material)) {
                if (resId != C0015R.drawable.abc_ratingbar_small_material) {
                    if (tintDrawableUsingColorFilter(context, resId, drawable) || !failIfNotKnown) {
                        return drawable;
                    }
                    return null;
                }
            }
            ld = (LayerDrawable) drawable;
            setPorterDuffColorFilter(ld.findDrawableByLayerId(16908288), ThemeUtils.getDisabledThemeAttrColor(context, C0015R.attr.colorControlNormal), DEFAULT_MODE);
            setPorterDuffColorFilter(ld.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor(context, C0015R.attr.colorControlActivated), DEFAULT_MODE);
            setPorterDuffColorFilter(ld.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor(context, C0015R.attr.colorControlActivated), DEFAULT_MODE);
            return drawable;
        }
    }

    private Drawable loadDrawableFromDelegates(@NonNull Context context, @DrawableRes int resId) {
        if (this.mDelegates == null || this.mDelegates.isEmpty()) {
            return null;
        }
        if (this.mKnownDrawableIdTags != null) {
            String cachedTagName = (String) this.mKnownDrawableIdTags.get(resId);
            if (!SKIP_DRAWABLE_TAG.equals(cachedTagName)) {
                if (cachedTagName == null || this.mDelegates.get(cachedTagName) != null) {
                }
            }
            return null;
        }
        this.mKnownDrawableIdTags = new SparseArrayCompat();
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        TypedValue tv = this.mTypedValue;
        Resources res = context.getResources();
        res.getValue(resId, tv, true);
        long key = createCacheKey(tv);
        Drawable dr = getCachedDrawable(context, key);
        if (dr != null) {
            return dr;
        }
        if (tv.string != null && tv.string.toString().endsWith(".xml")) {
            try {
                int type;
                String tagName;
                InflateDelegate delegate;
                XmlPullParser parser = res.getXml(resId);
                AttributeSet attrs = Xml.asAttributeSet(parser);
                while (true) {
                    int next = parser.next();
                    type = next;
                    if (next == 2 || type == 1) {
                        if (type != 2) {
                            tagName = parser.getName();
                            this.mKnownDrawableIdTags.append(resId, tagName);
                            delegate = (InflateDelegate) this.mDelegates.get(tagName);
                            if (delegate != null) {
                                dr = delegate.createFromXmlInner(context, parser, attrs, context.getTheme());
                            }
                            if (dr != null) {
                                dr.setChangingConfigurations(tv.changingConfigurations);
                                addDrawableToCache(context, key, dr);
                            }
                        } else {
                            throw new XmlPullParserException("No start tag found");
                        }
                    }
                }
                if (type != 2) {
                    throw new XmlPullParserException("No start tag found");
                }
                tagName = parser.getName();
                this.mKnownDrawableIdTags.append(resId, tagName);
                delegate = (InflateDelegate) this.mDelegates.get(tagName);
                if (delegate != null) {
                    dr = delegate.createFromXmlInner(context, parser, attrs, context.getTheme());
                }
                if (dr != null) {
                    dr.setChangingConfigurations(tv.changingConfigurations);
                    addDrawableToCache(context, key, dr);
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception while inflating drawable", e);
            }
        }
        if (dr == null) {
            this.mKnownDrawableIdTags.append(resId, SKIP_DRAWABLE_TAG);
        }
        return dr;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.drawable.Drawable getCachedDrawable(@android.support.annotation.NonNull android.content.Context r6, long r7) {
        /*
        r5 = this;
        r0 = r5.mDrawableCacheLock;
        monitor-enter(r0);
        r1 = r5.mDrawableCaches;	 Catch:{ all -> 0x002f }
        r1 = r1.get(r6);	 Catch:{ all -> 0x002f }
        r1 = (android.support.v4.util.LongSparseArray) r1;	 Catch:{ all -> 0x002f }
        r2 = 0;
        if (r1 != 0) goto L_0x0010;
    L_0x000e:
        monitor-exit(r0);	 Catch:{ all -> 0x002f }
        return r2;
    L_0x0010:
        r3 = r1.get(r7);	 Catch:{ all -> 0x002f }
        r3 = (java.lang.ref.WeakReference) r3;	 Catch:{ all -> 0x002f }
        if (r3 == 0) goto L_0x002d;
    L_0x0018:
        r4 = r3.get();	 Catch:{ all -> 0x002f }
        r4 = (android.graphics.drawable.Drawable.ConstantState) r4;	 Catch:{ all -> 0x002f }
        if (r4 == 0) goto L_0x002a;
    L_0x0020:
        r2 = r6.getResources();	 Catch:{ all -> 0x002f }
        r2 = r4.newDrawable(r2);	 Catch:{ all -> 0x002f }
        monitor-exit(r0);	 Catch:{ all -> 0x002f }
        return r2;
    L_0x002a:
        r1.delete(r7);	 Catch:{ all -> 0x002f }
    L_0x002d:
        monitor-exit(r0);	 Catch:{ all -> 0x002f }
        return r2;
    L_0x002f:
        r1 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x002f }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.AppCompatDrawableManager.getCachedDrawable(android.content.Context, long):android.graphics.drawable.Drawable");
    }

    private boolean addDrawableToCache(@NonNull Context context, long key, @NonNull Drawable drawable) {
        ConstantState cs = drawable.getConstantState();
        if (cs == null) {
            return false;
        }
        synchronized (this.mDrawableCacheLock) {
            LongSparseArray<WeakReference<ConstantState>> cache = (LongSparseArray) this.mDrawableCaches.get(context);
            if (cache == null) {
                cache = new LongSparseArray();
                this.mDrawableCaches.put(context, cache);
            }
            cache.put(key, new WeakReference(cs));
        }
        return true;
    }

    Drawable onDrawableLoadedFromResources(@NonNull Context context, @NonNull VectorEnabledTintResources resources, @DrawableRes int resId) {
        Drawable drawable = loadDrawableFromDelegates(context, resId);
        if (drawable == null) {
            drawable = resources.superGetDrawable(resId);
        }
        if (drawable != null) {
            return tintDrawable(context, resId, false, drawable);
        }
        return null;
    }

    static boolean tintDrawableUsingColorFilter(@NonNull Context context, @DrawableRes int resId, @NonNull Drawable drawable) {
        Mode tintMode = DEFAULT_MODE;
        boolean colorAttrSet = false;
        int colorAttr = 0;
        int alpha = -1;
        if (arrayContains(COLORFILTER_TINT_COLOR_CONTROL_NORMAL, resId)) {
            colorAttr = C0015R.attr.colorControlNormal;
            colorAttrSet = true;
        } else if (arrayContains(COLORFILTER_COLOR_CONTROL_ACTIVATED, resId)) {
            colorAttr = C0015R.attr.colorControlActivated;
            colorAttrSet = true;
        } else if (arrayContains(COLORFILTER_COLOR_BACKGROUND_MULTIPLY, resId)) {
            colorAttr = 16842801;
            colorAttrSet = true;
            tintMode = Mode.MULTIPLY;
        } else if (resId == C0015R.drawable.abc_list_divider_mtrl_alpha) {
            colorAttr = 16842800;
            colorAttrSet = true;
            alpha = Math.round(40.8f);
        } else if (resId == C0015R.drawable.abc_dialog_material_background) {
            colorAttr = 16842801;
            colorAttrSet = true;
        }
        if (!colorAttrSet) {
            return false;
        }
        if (DrawableUtils.canSafelyMutateDrawable(drawable)) {
            drawable = drawable.mutate();
        }
        drawable.setColorFilter(getPorterDuffColorFilter(ThemeUtils.getThemeAttrColor(context, colorAttr), tintMode));
        if (alpha != -1) {
            drawable.setAlpha(alpha);
        }
        return true;
    }

    private void addDelegate(@NonNull String tagName, @NonNull InflateDelegate delegate) {
        if (this.mDelegates == null) {
            this.mDelegates = new ArrayMap();
        }
        this.mDelegates.put(tagName, delegate);
    }

    private void removeDelegate(@NonNull String tagName, @NonNull InflateDelegate delegate) {
        if (this.mDelegates != null && this.mDelegates.get(tagName) == delegate) {
            this.mDelegates.remove(tagName);
        }
    }

    private static boolean arrayContains(int[] array, int value) {
        for (int id : array) {
            if (id == value) {
                return true;
            }
        }
        return false;
    }

    static Mode getTintMode(int resId) {
        if (resId == C0015R.drawable.abc_switch_thumb_material) {
            return Mode.MULTIPLY;
        }
        return null;
    }

    ColorStateList getTintList(@NonNull Context context, @DrawableRes int resId) {
        ColorStateList tint = getTintListFromCache(context, resId);
        if (tint == null) {
            if (resId == C0015R.drawable.abc_edit_text_material) {
                tint = AppCompatResources.getColorStateList(context, C0015R.color.abc_tint_edittext);
            } else if (resId == C0015R.drawable.abc_switch_track_mtrl_alpha) {
                tint = AppCompatResources.getColorStateList(context, C0015R.color.abc_tint_switch_track);
            } else if (resId == C0015R.drawable.abc_switch_thumb_material) {
                tint = createSwitchThumbColorStateList(context);
            } else if (resId == C0015R.drawable.abc_btn_default_mtrl_shape) {
                tint = createDefaultButtonColorStateList(context);
            } else if (resId == C0015R.drawable.abc_btn_borderless_material) {
                tint = createBorderlessButtonColorStateList(context);
            } else if (resId == C0015R.drawable.abc_btn_colored_material) {
                tint = createColoredButtonColorStateList(context);
            } else {
                if (resId != C0015R.drawable.abc_spinner_mtrl_am_alpha) {
                    if (resId != C0015R.drawable.abc_spinner_textfield_background_material) {
                        if (arrayContains(TINT_COLOR_CONTROL_NORMAL, resId)) {
                            tint = ThemeUtils.getThemeAttrColorStateList(context, C0015R.attr.colorControlNormal);
                        } else if (arrayContains(TINT_COLOR_CONTROL_STATE_LIST, resId)) {
                            tint = AppCompatResources.getColorStateList(context, C0015R.color.abc_tint_default);
                        } else if (arrayContains(TINT_CHECKABLE_BUTTON_LIST, resId)) {
                            tint = AppCompatResources.getColorStateList(context, C0015R.color.abc_tint_btn_checkable);
                        } else if (resId == C0015R.drawable.abc_seekbar_thumb_material) {
                            tint = AppCompatResources.getColorStateList(context, C0015R.color.abc_tint_seek_thumb);
                        }
                    }
                }
                tint = AppCompatResources.getColorStateList(context, C0015R.color.abc_tint_spinner);
            }
            if (tint != null) {
                addTintListToCache(context, resId, tint);
            }
        }
        return tint;
    }

    private ColorStateList getTintListFromCache(@NonNull Context context, @DrawableRes int resId) {
        ColorStateList colorStateList = null;
        if (this.mTintLists == null) {
            return null;
        }
        SparseArrayCompat<ColorStateList> tints = (SparseArrayCompat) this.mTintLists.get(context);
        if (tints != null) {
            colorStateList = (ColorStateList) tints.get(resId);
        }
        return colorStateList;
    }

    private void addTintListToCache(@NonNull Context context, @DrawableRes int resId, @NonNull ColorStateList tintList) {
        if (this.mTintLists == null) {
            this.mTintLists = new WeakHashMap();
        }
        SparseArrayCompat<ColorStateList> themeTints = (SparseArrayCompat) this.mTintLists.get(context);
        if (themeTints == null) {
            themeTints = new SparseArrayCompat();
            this.mTintLists.put(context, themeTints);
        }
        themeTints.append(resId, tintList);
    }

    private ColorStateList createDefaultButtonColorStateList(@NonNull Context context) {
        return createButtonColorStateList(context, ThemeUtils.getThemeAttrColor(context, C0015R.attr.colorButtonNormal));
    }

    private ColorStateList createBorderlessButtonColorStateList(@NonNull Context context) {
        return createButtonColorStateList(context, 0);
    }

    private ColorStateList createColoredButtonColorStateList(@NonNull Context context) {
        return createButtonColorStateList(context, ThemeUtils.getThemeAttrColor(context, C0015R.attr.colorAccent));
    }

    private ColorStateList createButtonColorStateList(@NonNull Context context, @ColorInt int baseColor) {
        states = new int[4][];
        colors = new int[4];
        int colorControlHighlight = ThemeUtils.getThemeAttrColor(context, C0015R.attr.colorControlHighlight);
        int disabledColor = ThemeUtils.getDisabledThemeAttrColor(context, C0015R.attr.colorButtonNormal);
        states[0] = ThemeUtils.DISABLED_STATE_SET;
        colors[0] = disabledColor;
        int i = 0 + 1;
        states[i] = ThemeUtils.PRESSED_STATE_SET;
        colors[i] = ColorUtils.compositeColors(colorControlHighlight, baseColor);
        i++;
        states[i] = ThemeUtils.FOCUSED_STATE_SET;
        colors[i] = ColorUtils.compositeColors(colorControlHighlight, baseColor);
        i++;
        states[i] = ThemeUtils.EMPTY_STATE_SET;
        colors[i] = baseColor;
        i++;
        return new ColorStateList(states, colors);
    }

    private ColorStateList createSwitchThumbColorStateList(Context context) {
        int[][] states = new int[3][];
        int[] colors = new int[3];
        ColorStateList thumbColor = ThemeUtils.getThemeAttrColorStateList(context, C0015R.attr.colorSwitchThumbNormal);
        int i;
        if (thumbColor == null || !thumbColor.isStateful()) {
            states[0] = ThemeUtils.DISABLED_STATE_SET;
            colors[0] = ThemeUtils.getDisabledThemeAttrColor(context, C0015R.attr.colorSwitchThumbNormal);
            i = 0 + 1;
            states[i] = ThemeUtils.CHECKED_STATE_SET;
            colors[i] = ThemeUtils.getThemeAttrColor(context, C0015R.attr.colorControlActivated);
            i++;
            states[i] = ThemeUtils.EMPTY_STATE_SET;
            colors[i] = ThemeUtils.getThemeAttrColor(context, C0015R.attr.colorSwitchThumbNormal);
            i++;
        } else {
            states[0] = ThemeUtils.DISABLED_STATE_SET;
            colors[0] = thumbColor.getColorForState(states[0], 0);
            i = 0 + 1;
            states[i] = ThemeUtils.CHECKED_STATE_SET;
            colors[i] = ThemeUtils.getThemeAttrColor(context, C0015R.attr.colorControlActivated);
            i++;
            states[i] = ThemeUtils.EMPTY_STATE_SET;
            colors[i] = thumbColor.getDefaultColor();
            i++;
        }
        return new ColorStateList(states, colors);
    }

    static void tintDrawable(Drawable drawable, TintInfo tint, int[] state) {
        if (!DrawableUtils.canSafelyMutateDrawable(drawable) || drawable.mutate() == drawable) {
            if (!tint.mHasTintList) {
                if (!tint.mHasTintMode) {
                    drawable.clearColorFilter();
                    if (VERSION.SDK_INT <= 23) {
                        drawable.invalidateSelf();
                    }
                    return;
                }
            }
            drawable.setColorFilter(createTintFilter(tint.mHasTintList ? tint.mTintList : null, tint.mHasTintMode ? tint.mTintMode : DEFAULT_MODE, state));
            if (VERSION.SDK_INT <= 23) {
                drawable.invalidateSelf();
            }
            return;
        }
        Log.d(TAG, "Mutated drawable is not the same instance as the input.");
    }

    private static PorterDuffColorFilter createTintFilter(ColorStateList tint, Mode tintMode, int[] state) {
        if (tint != null) {
            if (tintMode != null) {
                return getPorterDuffColorFilter(tint.getColorForState(state, 0), tintMode);
            }
        }
        return null;
    }

    public static PorterDuffColorFilter getPorterDuffColorFilter(int color, Mode mode) {
        PorterDuffColorFilter filter = COLOR_FILTER_CACHE.get(color, mode);
        if (filter != null) {
            return filter;
        }
        filter = new PorterDuffColorFilter(color, mode);
        COLOR_FILTER_CACHE.put(color, mode, filter);
        return filter;
    }

    private static void setPorterDuffColorFilter(Drawable d, int color, Mode mode) {
        if (DrawableUtils.canSafelyMutateDrawable(d)) {
            d = d.mutate();
        }
        d.setColorFilter(getPorterDuffColorFilter(color, mode == null ? DEFAULT_MODE : mode));
    }

    private void checkVectorDrawableSetup(@NonNull Context context) {
        if (!this.mHasCheckedVectorDrawableSetup) {
            this.mHasCheckedVectorDrawableSetup = true;
            Drawable d = getDrawable(context, C0015R.drawable.abc_vector_test);
            if (d == null || !isVectorDrawable(d)) {
                this.mHasCheckedVectorDrawableSetup = false;
                throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
            }
        }
    }

    private static boolean isVectorDrawable(@NonNull Drawable d) {
        if (!(d instanceof VectorDrawableCompat)) {
            if (!PLATFORM_VD_CLAZZ.equals(d.getClass().getName())) {
                return false;
            }
        }
        return true;
    }
}
