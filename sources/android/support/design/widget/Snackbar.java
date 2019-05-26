package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StringRes;
import android.support.design.C0008R;
import android.support.design.internal.SnackbarContentLayout;
import android.support.design.widget.BaseTransientBottomBar.BaseCallback;
import android.support.design.widget.BaseTransientBottomBar.ContentViewCallback;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;

public final class Snackbar extends BaseTransientBottomBar<Snackbar> {
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_LONG = 0;
    public static final int LENGTH_SHORT = -1;
    @Nullable
    private BaseCallback<Snackbar> mCallback;

    public static class Callback extends BaseCallback<Snackbar> {
        public static final int DISMISS_EVENT_ACTION = 1;
        public static final int DISMISS_EVENT_CONSECUTIVE = 4;
        public static final int DISMISS_EVENT_MANUAL = 3;
        public static final int DISMISS_EVENT_SWIPE = 0;
        public static final int DISMISS_EVENT_TIMEOUT = 2;

        public void onShown(Snackbar sb) {
        }

        public void onDismissed(Snackbar transientBottomBar, int event) {
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final class SnackbarLayout extends SnackbarBaseLayout {
        public SnackbarLayout(Context context) {
            super(context);
        }

        public SnackbarLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int childCount = getChildCount();
            int availableWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (child.getLayoutParams().width == -1) {
                    child.measure(MeasureSpec.makeMeasureSpec(availableWidth, ErrorDialogData.SUPPRESSED), MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(), ErrorDialogData.SUPPRESSED));
                }
            }
        }
    }

    private Snackbar(ViewGroup parent, View content, ContentViewCallback contentViewCallback) {
        super(parent, content, contentViewCallback);
    }

    @NonNull
    public static Snackbar make(@NonNull View view, @NonNull CharSequence text, int duration) {
        ViewGroup parent = findSuitableParent(view);
        if (parent != null) {
            SnackbarContentLayout content = (SnackbarContentLayout) LayoutInflater.from(parent.getContext()).inflate(C0008R.layout.design_layout_snackbar_include, parent, false);
            Snackbar snackbar = new Snackbar(parent, content, content);
            snackbar.setText(text);
            snackbar.setDuration(duration);
            return snackbar;
        }
        throw new IllegalArgumentException("No suitable parent found from the given view. Please provide a valid view.");
    }

    @NonNull
    public static Snackbar make(@NonNull View view, @StringRes int resId, int duration) {
        return make(view, view.getResources().getText(resId), duration);
    }

    private static ViewGroup findSuitableParent(View view) {
        View view2 = view;
        ViewGroup fallback = null;
        while (!(view2 instanceof CoordinatorLayout)) {
            if (view2 instanceof FrameLayout) {
                if (view2.getId() == 16908290) {
                    return (ViewGroup) view2;
                }
                fallback = (ViewGroup) view2;
            }
            if (view2 != null) {
                ViewParent parent = view2.getParent();
                view2 = parent instanceof View ? (View) parent : null;
                continue;
            }
            if (view2 == null) {
                return fallback;
            }
        }
        return (ViewGroup) view2;
    }

    @NonNull
    public Snackbar setText(@NonNull CharSequence message) {
        ((SnackbarContentLayout) this.mView.getChildAt(0)).getMessageView().setText(message);
        return this;
    }

    @NonNull
    public Snackbar setText(@StringRes int resId) {
        return setText(getContext().getText(resId));
    }

    @NonNull
    public Snackbar setAction(@StringRes int resId, OnClickListener listener) {
        return setAction(getContext().getText(resId), listener);
    }

    @NonNull
    public Snackbar setAction(CharSequence text, final OnClickListener listener) {
        TextView tv = ((SnackbarContentLayout) this.mView.getChildAt(0)).getActionView();
        if (!TextUtils.isEmpty(text)) {
            if (listener != null) {
                tv.setVisibility(0);
                tv.setText(text);
                tv.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        listener.onClick(view);
                        Snackbar.this.dispatchDismiss(1);
                    }
                });
                return this;
            }
        }
        tv.setVisibility(8);
        tv.setOnClickListener(null);
        return this;
    }

    @NonNull
    public Snackbar setActionTextColor(ColorStateList colors) {
        ((SnackbarContentLayout) this.mView.getChildAt(0)).getActionView().setTextColor(colors);
        return this;
    }

    @NonNull
    public Snackbar setActionTextColor(@ColorInt int color) {
        ((SnackbarContentLayout) this.mView.getChildAt(0)).getActionView().setTextColor(color);
        return this;
    }

    @Deprecated
    @NonNull
    public Snackbar setCallback(Callback callback) {
        if (this.mCallback != null) {
            removeCallback(this.mCallback);
        }
        if (callback != null) {
            addCallback(callback);
        }
        this.mCallback = callback;
        return this;
    }
}
