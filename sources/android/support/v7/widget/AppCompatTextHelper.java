package android.support.v7.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.content.res.ResourcesCompat.FontCallback;
import android.support.v4.widget.AutoSizeableTextView;
import android.support.v7.appcompat.C0015R;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;
import java.lang.ref.WeakReference;

@RequiresApi(9)
class AppCompatTextHelper {
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private boolean mAsyncFontPending;
    @NonNull
    private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableTopTint;
    private Typeface mFontTypeface;
    private int mStyle = 0;
    final TextView mView;

    static AppCompatTextHelper create(TextView textView) {
        if (VERSION.SDK_INT >= 17) {
            return new AppCompatTextHelperV17(textView);
        }
        return new AppCompatTextHelper(textView);
    }

    AppCompatTextHelper(TextView view) {
        this.mView = view;
        this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(this.mView);
    }

    @SuppressLint({"NewApi"})
    void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        AttributeSet attributeSet = attrs;
        int i = defStyleAttr;
        Context context = this.mView.getContext();
        AppCompatDrawableManager drawableManager = AppCompatDrawableManager.get();
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attributeSet, C0015R.styleable.AppCompatTextHelper, i, 0);
        int ap = a.getResourceId(C0015R.styleable.AppCompatTextHelper_android_textAppearance, -1);
        if (a.hasValue(C0015R.styleable.AppCompatTextHelper_android_drawableLeft)) {
            r0.mDrawableLeftTint = createTintInfo(context, drawableManager, a.getResourceId(C0015R.styleable.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (a.hasValue(C0015R.styleable.AppCompatTextHelper_android_drawableTop)) {
            r0.mDrawableTopTint = createTintInfo(context, drawableManager, a.getResourceId(C0015R.styleable.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (a.hasValue(C0015R.styleable.AppCompatTextHelper_android_drawableRight)) {
            r0.mDrawableRightTint = createTintInfo(context, drawableManager, a.getResourceId(C0015R.styleable.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (a.hasValue(C0015R.styleable.AppCompatTextHelper_android_drawableBottom)) {
            r0.mDrawableBottomTint = createTintInfo(context, drawableManager, a.getResourceId(C0015R.styleable.AppCompatTextHelper_android_drawableBottom, 0));
        }
        a.recycle();
        boolean hasPwdTm = r0.mView.getTransformationMethod() instanceof PasswordTransformationMethod;
        boolean allCaps = false;
        boolean z = false;
        ColorStateList textColor = null;
        ColorStateList textColorHint = null;
        ColorStateList textColorLink = null;
        if (ap != -1) {
            a = TintTypedArray.obtainStyledAttributes(context, ap, C0015R.styleable.TextAppearance);
            if (!hasPwdTm && a.hasValue(C0015R.styleable.TextAppearance_textAllCaps)) {
                allCaps = a.getBoolean(C0015R.styleable.TextAppearance_textAllCaps, false);
                z = true;
            }
            updateTypefaceAndStyle(context, a);
            if (VERSION.SDK_INT < 23) {
                if (a.hasValue(C0015R.styleable.TextAppearance_android_textColor)) {
                    textColor = a.getColorStateList(C0015R.styleable.TextAppearance_android_textColor);
                }
                if (a.hasValue(C0015R.styleable.TextAppearance_android_textColorHint)) {
                    textColorHint = a.getColorStateList(C0015R.styleable.TextAppearance_android_textColorHint);
                }
                if (a.hasValue(C0015R.styleable.TextAppearance_android_textColorLink)) {
                    textColorLink = a.getColorStateList(C0015R.styleable.TextAppearance_android_textColorLink);
                }
            }
            a.recycle();
        }
        a = TintTypedArray.obtainStyledAttributes(context, attributeSet, C0015R.styleable.TextAppearance, i, 0);
        if (!hasPwdTm && a.hasValue(C0015R.styleable.TextAppearance_textAllCaps)) {
            z = true;
            allCaps = a.getBoolean(C0015R.styleable.TextAppearance_textAllCaps, false);
        }
        if (VERSION.SDK_INT < 23) {
            if (a.hasValue(C0015R.styleable.TextAppearance_android_textColor)) {
                textColor = a.getColorStateList(C0015R.styleable.TextAppearance_android_textColor);
            }
            if (a.hasValue(C0015R.styleable.TextAppearance_android_textColorHint)) {
                textColorHint = a.getColorStateList(C0015R.styleable.TextAppearance_android_textColorHint);
            }
            if (a.hasValue(C0015R.styleable.TextAppearance_android_textColorLink)) {
                textColorLink = a.getColorStateList(C0015R.styleable.TextAppearance_android_textColorLink);
            }
        }
        updateTypefaceAndStyle(context, a);
        a.recycle();
        if (textColor != null) {
            r0.mView.setTextColor(textColor);
        }
        if (textColorHint != null) {
            r0.mView.setHintTextColor(textColorHint);
        }
        if (textColorLink != null) {
            r0.mView.setLinkTextColor(textColorLink);
        }
        if (!hasPwdTm && allCapsSet) {
            setAllCaps(allCaps);
        }
        if (r0.mFontTypeface != null) {
            r0.mView.setTypeface(r0.mFontTypeface, r0.mStyle);
        }
        r0.mAutoSizeTextHelper.loadFromAttributes(attributeSet, i);
        if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && r0.mAutoSizeTextHelper.getAutoSizeTextType() != 0) {
            int[] autoSizeTextSizesInPx = r0.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
            if (autoSizeTextSizesInPx.length > 0) {
                if (((float) r0.mView.getAutoSizeStepGranularity()) != -1.0f) {
                    r0.mView.setAutoSizeTextTypeUniformWithConfiguration(r0.mAutoSizeTextHelper.getAutoSizeMinTextSize(), r0.mAutoSizeTextHelper.getAutoSizeMaxTextSize(), r0.mAutoSizeTextHelper.getAutoSizeStepGranularity(), null);
                    return;
                }
                r0.mView.setAutoSizeTextTypeUniformWithPresetSizes(autoSizeTextSizesInPx, 0);
                return;
            }
        }
    }

    private void updateTypefaceAndStyle(Context context, TintTypedArray a) {
        this.mStyle = a.getInt(C0015R.styleable.TextAppearance_android_textStyle, this.mStyle);
        boolean z = true;
        if (!a.hasValue(C0015R.styleable.TextAppearance_android_fontFamily)) {
            if (!a.hasValue(C0015R.styleable.TextAppearance_fontFamily)) {
                if (a.hasValue(C0015R.styleable.TextAppearance_android_typeface)) {
                    this.mAsyncFontPending = false;
                    switch (a.getInt(C0015R.styleable.TextAppearance_android_typeface, 1)) {
                        case 1:
                            this.mFontTypeface = Typeface.SANS_SERIF;
                            break;
                        case 2:
                            this.mFontTypeface = Typeface.SERIF;
                            break;
                        case 3:
                            this.mFontTypeface = Typeface.MONOSPACE;
                            break;
                        default:
                            break;
                    }
                }
                return;
            }
        }
        this.mFontTypeface = null;
        int fontFamilyId = a.hasValue(C0015R.styleable.TextAppearance_fontFamily) ? C0015R.styleable.TextAppearance_fontFamily : C0015R.styleable.TextAppearance_android_fontFamily;
        if (!context.isRestricted()) {
            final WeakReference<TextView> textViewWeak = new WeakReference(this.mView);
            try {
                this.mFontTypeface = a.getFont(fontFamilyId, this.mStyle, new FontCallback() {
                    public void onFontRetrieved(@NonNull Typeface typeface) {
                        AppCompatTextHelper.this.onAsyncTypefaceReceived(textViewWeak, typeface);
                    }

                    public void onFontRetrievalFailed(int reason) {
                    }
                });
                if (this.mFontTypeface != null) {
                    z = false;
                }
                this.mAsyncFontPending = z;
            } catch (UnsupportedOperationException e) {
            }
        }
        if (this.mFontTypeface == null) {
            String fontFamilyName = a.getString(fontFamilyId);
            if (fontFamilyName != null) {
                this.mFontTypeface = Typeface.create(fontFamilyName, this.mStyle);
            }
        }
    }

    private void onAsyncTypefaceReceived(WeakReference<TextView> textViewWeak, Typeface typeface) {
        if (this.mAsyncFontPending) {
            this.mFontTypeface = typeface;
            TextView textView = (TextView) textViewWeak.get();
            if (textView != null) {
                textView.setTypeface(typeface, this.mStyle);
            }
        }
    }

    void onSetTextAppearance(Context context, int resId) {
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, resId, C0015R.styleable.TextAppearance);
        if (a.hasValue(C0015R.styleable.TextAppearance_textAllCaps)) {
            setAllCaps(a.getBoolean(C0015R.styleable.TextAppearance_textAllCaps, false));
        }
        if (VERSION.SDK_INT < 23 && a.hasValue(C0015R.styleable.TextAppearance_android_textColor)) {
            ColorStateList textColor = a.getColorStateList(C0015R.styleable.TextAppearance_android_textColor);
            if (textColor != null) {
                this.mView.setTextColor(textColor);
            }
        }
        updateTypefaceAndStyle(context, a);
        a.recycle();
        if (this.mFontTypeface != null) {
            this.mView.setTypeface(this.mFontTypeface, this.mStyle);
        }
    }

    void setAllCaps(boolean allCaps) {
        this.mView.setAllCaps(allCaps);
    }

    void applyCompoundDrawablesTints() {
        if (this.mDrawableLeftTint != null || this.mDrawableTopTint != null || this.mDrawableRightTint != null || this.mDrawableBottomTint != null) {
            Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableLeftTint);
            applyCompoundDrawableTint(compoundDrawables[1], this.mDrawableTopTint);
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableRightTint);
            applyCompoundDrawableTint(compoundDrawables[3], this.mDrawableBottomTint);
        }
    }

    final void applyCompoundDrawableTint(Drawable drawable, TintInfo info) {
        if (drawable != null && info != null) {
            AppCompatDrawableManager.tintDrawable(drawable, info, this.mView.getDrawableState());
        }
    }

    protected static TintInfo createTintInfo(Context context, AppCompatDrawableManager drawableManager, int drawableId) {
        ColorStateList tintList = drawableManager.getTintList(context, drawableId);
        if (tintList == null) {
            return null;
        }
        TintInfo tintInfo = new TintInfo();
        tintInfo.mHasTintList = true;
        tintInfo.mTintList = tintList;
        return tintInfo;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
            autoSizeText();
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    void setTextSize(int unit, float size) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && !isAutoSizeEnabled()) {
            setTextSizeInternal(unit, size);
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    void autoSizeText() {
        this.mAutoSizeTextHelper.autoSizeText();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    boolean isAutoSizeEnabled() {
        return this.mAutoSizeTextHelper.isAutoSizeEnabled();
    }

    private void setTextSizeInternal(int unit, float size) {
        this.mAutoSizeTextHelper.setTextSizeInternal(unit, size);
    }

    void setAutoSizeTextTypeWithDefaults(int autoSizeTextType) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeWithDefaults(autoSizeTextType);
    }

    void setAutoSizeTextTypeUniformWithConfiguration(int autoSizeMinTextSize, int autoSizeMaxTextSize, int autoSizeStepGranularity, int unit) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithConfiguration(autoSizeMinTextSize, autoSizeMaxTextSize, autoSizeStepGranularity, unit);
    }

    void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull int[] presetSizes, int unit) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(presetSizes, unit);
    }

    int getAutoSizeTextType() {
        return this.mAutoSizeTextHelper.getAutoSizeTextType();
    }

    int getAutoSizeStepGranularity() {
        return this.mAutoSizeTextHelper.getAutoSizeStepGranularity();
    }

    int getAutoSizeMinTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMinTextSize();
    }

    int getAutoSizeMaxTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMaxTextSize();
    }

    int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
    }
}
