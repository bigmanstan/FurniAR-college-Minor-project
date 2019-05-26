package android.support.v7.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.PointerIconCompat;
import android.support.v7.appcompat.C0015R;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

@RestrictTo({Scope.LIBRARY_GROUP})
class TooltipPopup {
    private static final String TAG = "TooltipPopup";
    private final View mContentView;
    private final Context mContext;
    private final LayoutParams mLayoutParams = new LayoutParams();
    private final TextView mMessageView;
    private final int[] mTmpAnchorPos = new int[2];
    private final int[] mTmpAppPos = new int[2];
    private final Rect mTmpDisplayFrame = new Rect();

    TooltipPopup(Context context) {
        this.mContext = context;
        this.mContentView = LayoutInflater.from(this.mContext).inflate(C0015R.layout.abc_tooltip, null);
        this.mMessageView = (TextView) this.mContentView.findViewById(C0015R.id.message);
        this.mLayoutParams.setTitle(getClass().getSimpleName());
        this.mLayoutParams.packageName = this.mContext.getPackageName();
        this.mLayoutParams.type = PointerIconCompat.TYPE_HAND;
        this.mLayoutParams.width = -2;
        this.mLayoutParams.height = -2;
        this.mLayoutParams.format = -3;
        this.mLayoutParams.windowAnimations = C0015R.style.Animation_AppCompat_Tooltip;
        this.mLayoutParams.flags = 24;
    }

    void show(View anchorView, int anchorX, int anchorY, boolean fromTouch, CharSequence tooltipText) {
        if (isShowing()) {
            hide();
        }
        this.mMessageView.setText(tooltipText);
        computePosition(anchorView, anchorX, anchorY, fromTouch, this.mLayoutParams);
        ((WindowManager) this.mContext.getSystemService("window")).addView(this.mContentView, this.mLayoutParams);
    }

    void hide() {
        if (isShowing()) {
            ((WindowManager) this.mContext.getSystemService("window")).removeView(this.mContentView);
        }
    }

    boolean isShowing() {
        return this.mContentView.getParent() != null;
    }

    private void computePosition(View anchorView, int anchorX, int anchorY, boolean fromTouch, LayoutParams outParams) {
        int offsetX;
        int offsetAbove;
        int offsetBelow;
        LayoutParams layoutParams = outParams;
        layoutParams.token = anchorView.getApplicationWindowToken();
        int tooltipPreciseAnchorThreshold = this.mContext.getResources().getDimensionPixelOffset(C0015R.dimen.tooltip_precise_anchor_threshold);
        if (anchorView.getWidth() >= tooltipPreciseAnchorThreshold) {
            offsetX = anchorX;
        } else {
            offsetX = anchorView.getWidth() / 2;
        }
        if (anchorView.getHeight() >= tooltipPreciseAnchorThreshold) {
            offsetAbove = r0.mContext.getResources().getDimensionPixelOffset(C0015R.dimen.tooltip_precise_anchor_extra_offset);
            offsetBelow = anchorY + offsetAbove;
            offsetAbove = anchorY - offsetAbove;
        } else {
            offsetBelow = anchorView.getHeight();
            offsetAbove = 0;
        }
        layoutParams.gravity = 49;
        int tooltipOffset = r0.mContext.getResources().getDimensionPixelOffset(fromTouch ? C0015R.dimen.tooltip_y_offset_touch : C0015R.dimen.tooltip_y_offset_non_touch);
        View appView = getAppRootView(anchorView);
        if (appView == null) {
            Log.e(TAG, "Cannot find app view");
            return;
        }
        int resourceId;
        appView.getWindowVisibleDisplayFrame(r0.mTmpDisplayFrame);
        if (r0.mTmpDisplayFrame.left >= 0 || r0.mTmpDisplayFrame.top >= 0) {
        } else {
            int statusBarHeight;
            Resources res = r0.mContext.getResources();
            resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId != 0) {
                statusBarHeight = res.getDimensionPixelSize(resourceId);
            } else {
                statusBarHeight = 0;
            }
            DisplayMetrics metrics = res.getDisplayMetrics();
            r0.mTmpDisplayFrame.set(0, statusBarHeight, metrics.widthPixels, metrics.heightPixels);
        }
        appView.getLocationOnScreen(r0.mTmpAppPos);
        anchorView.getLocationOnScreen(r0.mTmpAnchorPos);
        int[] iArr = r0.mTmpAnchorPos;
        iArr[0] = iArr[0] - r0.mTmpAppPos[0];
        iArr = r0.mTmpAnchorPos;
        iArr[1] = iArr[1] - r0.mTmpAppPos[1];
        layoutParams.x = (r0.mTmpAnchorPos[0] + offsetX) - (appView.getWidth() / 2);
        int spec = MeasureSpec.makeMeasureSpec(0, 0);
        r0.mContentView.measure(spec, spec);
        int tooltipHeight = r0.mContentView.getMeasuredHeight();
        resourceId = ((r0.mTmpAnchorPos[1] + offsetAbove) - tooltipOffset) - tooltipHeight;
        int yBelow = (r0.mTmpAnchorPos[1] + offsetBelow) + tooltipOffset;
        if (fromTouch) {
            if (resourceId >= 0) {
                layoutParams.y = resourceId;
            } else {
                layoutParams.y = yBelow;
            }
        } else if (yBelow + tooltipHeight <= r0.mTmpDisplayFrame.height()) {
            layoutParams.y = yBelow;
        } else {
            layoutParams.y = resourceId;
        }
    }

    private static View getAppRootView(View anchorView) {
        View rootView = anchorView.getRootView();
        ViewGroup.LayoutParams lp = rootView.getLayoutParams();
        if ((lp instanceof LayoutParams) && ((LayoutParams) lp).type == 2) {
            return rootView;
        }
        for (Context context = anchorView.getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
            if (context instanceof Activity) {
                return ((Activity) context).getWindow().getDecorView();
            }
        }
        return rootView;
    }
}
