package android.support.design.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.C0008R;
import android.support.design.widget.BaseTransientBottomBar.ContentViewCallback;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;

@RestrictTo({Scope.LIBRARY_GROUP})
public class SnackbarContentLayout extends LinearLayout implements ContentViewCallback {
    private Button mActionView;
    private int mMaxInlineActionWidth;
    private int mMaxWidth;
    private TextView mMessageView;

    public SnackbarContentLayout(Context context) {
        this(context, null);
    }

    public SnackbarContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, C0008R.styleable.SnackbarLayout);
        this.mMaxWidth = a.getDimensionPixelSize(C0008R.styleable.SnackbarLayout_android_maxWidth, -1);
        this.mMaxInlineActionWidth = a.getDimensionPixelSize(C0008R.styleable.SnackbarLayout_maxActionInlineWidth, -1);
        a.recycle();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mMessageView = (TextView) findViewById(C0008R.id.snackbar_text);
        this.mActionView = (Button) findViewById(C0008R.id.snackbar_action);
    }

    public TextView getMessageView() {
        return this.mMessageView;
    }

    public Button getActionView() {
        return this.mActionView;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mMaxWidth > 0 && getMeasuredWidth() > this.mMaxWidth) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(this.mMaxWidth, ErrorDialogData.SUPPRESSED);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        int multiLineVPadding = getResources().getDimensionPixelSize(C0008R.dimen.design_snackbar_padding_vertical_2lines);
        int singleLineVPadding = getResources().getDimensionPixelSize(C0008R.dimen.design_snackbar_padding_vertical);
        boolean isMultiLine = this.mMessageView.getLayout().getLineCount() > 1;
        boolean remeasure = false;
        if (!isMultiLine || this.mMaxInlineActionWidth <= 0 || this.mActionView.getMeasuredWidth() <= this.mMaxInlineActionWidth) {
            int messagePadding = isMultiLine ? multiLineVPadding : singleLineVPadding;
            if (updateViewsWithinLayout(0, messagePadding, messagePadding)) {
                remeasure = true;
            }
        } else if (updateViewsWithinLayout(1, multiLineVPadding, multiLineVPadding - singleLineVPadding)) {
            remeasure = true;
        }
        if (remeasure) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private boolean updateViewsWithinLayout(int orientation, int messagePadTop, int messagePadBottom) {
        boolean changed = false;
        if (orientation != getOrientation()) {
            setOrientation(orientation);
            changed = true;
        }
        if (this.mMessageView.getPaddingTop() == messagePadTop && this.mMessageView.getPaddingBottom() == messagePadBottom) {
            return changed;
        }
        updateTopBottomPadding(this.mMessageView, messagePadTop, messagePadBottom);
        return true;
    }

    private static void updateTopBottomPadding(View view, int topPadding, int bottomPadding) {
        if (ViewCompat.isPaddingRelative(view)) {
            ViewCompat.setPaddingRelative(view, ViewCompat.getPaddingStart(view), topPadding, ViewCompat.getPaddingEnd(view), bottomPadding);
        } else {
            view.setPadding(view.getPaddingLeft(), topPadding, view.getPaddingRight(), bottomPadding);
        }
    }

    public void animateContentIn(int delay, int duration) {
        this.mMessageView.setAlpha(0.0f);
        this.mMessageView.animate().alpha(1.0f).setDuration((long) duration).setStartDelay((long) delay).start();
        if (this.mActionView.getVisibility() == 0) {
            this.mActionView.setAlpha(0.0f);
            this.mActionView.animate().alpha(1.0f).setDuration((long) duration).setStartDelay((long) delay).start();
        }
    }

    public void animateContentOut(int delay, int duration) {
        this.mMessageView.setAlpha(1.0f);
        this.mMessageView.animate().alpha(0.0f).setDuration((long) duration).setStartDelay((long) delay).start();
        if (this.mActionView.getVisibility() == 0) {
            this.mActionView.setAlpha(1.0f);
            this.mActionView.animate().alpha(0.0f).setDuration((long) duration).setStartDelay((long) delay).start();
        }
    }
}
