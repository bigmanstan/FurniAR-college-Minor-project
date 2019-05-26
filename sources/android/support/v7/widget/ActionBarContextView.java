package android.support.v7.widget;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.appcompat.C0015R;
import android.support.v7.view.ActionMode;
import android.support.v7.view.menu.MenuBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;

@RestrictTo({Scope.LIBRARY_GROUP})
public class ActionBarContextView extends AbsActionBarView {
    private static final String TAG = "ActionBarContextView";
    private View mClose;
    private int mCloseItemLayout;
    private View mCustomView;
    private CharSequence mSubtitle;
    private int mSubtitleStyleRes;
    private TextView mSubtitleView;
    private CharSequence mTitle;
    private LinearLayout mTitleLayout;
    private boolean mTitleOptional;
    private int mTitleStyleRes;
    private TextView mTitleView;

    public /* bridge */ /* synthetic */ void animateToVisibility(int i) {
        super.animateToVisibility(i);
    }

    public /* bridge */ /* synthetic */ boolean canShowOverflowMenu() {
        return super.canShowOverflowMenu();
    }

    public /* bridge */ /* synthetic */ void dismissPopupMenus() {
        super.dismissPopupMenus();
    }

    public /* bridge */ /* synthetic */ int getAnimatedVisibility() {
        return super.getAnimatedVisibility();
    }

    public /* bridge */ /* synthetic */ int getContentHeight() {
        return super.getContentHeight();
    }

    public /* bridge */ /* synthetic */ boolean isOverflowMenuShowPending() {
        return super.isOverflowMenuShowPending();
    }

    public /* bridge */ /* synthetic */ boolean isOverflowReserved() {
        return super.isOverflowReserved();
    }

    public /* bridge */ /* synthetic */ boolean onHoverEvent(MotionEvent motionEvent) {
        return super.onHoverEvent(motionEvent);
    }

    public /* bridge */ /* synthetic */ boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    public /* bridge */ /* synthetic */ void postShowOverflowMenu() {
        super.postShowOverflowMenu();
    }

    public /* bridge */ /* synthetic */ void setVisibility(int i) {
        super.setVisibility(i);
    }

    public /* bridge */ /* synthetic */ ViewPropertyAnimatorCompat setupAnimatorToVisibility(int i, long j) {
        return super.setupAnimatorToVisibility(i, j);
    }

    public ActionBarContextView(Context context) {
        this(context, null);
    }

    public ActionBarContextView(Context context, AttributeSet attrs) {
        this(context, attrs, C0015R.attr.actionModeStyle);
    }

    public ActionBarContextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, C0015R.styleable.ActionMode, defStyle, 0);
        ViewCompat.setBackground(this, a.getDrawable(C0015R.styleable.ActionMode_background));
        this.mTitleStyleRes = a.getResourceId(C0015R.styleable.ActionMode_titleTextStyle, 0);
        this.mSubtitleStyleRes = a.getResourceId(C0015R.styleable.ActionMode_subtitleTextStyle, 0);
        this.mContentHeight = a.getLayoutDimension(C0015R.styleable.ActionMode_height, 0);
        this.mCloseItemLayout = a.getResourceId(C0015R.styleable.ActionMode_closeItemLayout, C0015R.layout.abc_action_mode_close_item_material);
        a.recycle();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.hideOverflowMenu();
            this.mActionMenuPresenter.hideSubMenus();
        }
    }

    public void setContentHeight(int height) {
        this.mContentHeight = height;
    }

    public void setCustomView(View view) {
        if (this.mCustomView != null) {
            removeView(this.mCustomView);
        }
        this.mCustomView = view;
        if (!(view == null || this.mTitleLayout == null)) {
            removeView(this.mTitleLayout);
            this.mTitleLayout = null;
        }
        if (view != null) {
            addView(view);
        }
        requestLayout();
    }

    public void setTitle(CharSequence title) {
        this.mTitle = title;
        initTitle();
    }

    public void setSubtitle(CharSequence subtitle) {
        this.mSubtitle = subtitle;
        initTitle();
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    private void initTitle() {
        if (this.mTitleLayout == null) {
            LayoutInflater.from(getContext()).inflate(C0015R.layout.abc_action_bar_title_item, this);
            this.mTitleLayout = (LinearLayout) getChildAt(getChildCount() - 1);
            this.mTitleView = (TextView) this.mTitleLayout.findViewById(C0015R.id.action_bar_title);
            this.mSubtitleView = (TextView) this.mTitleLayout.findViewById(C0015R.id.action_bar_subtitle);
            if (this.mTitleStyleRes != 0) {
                this.mTitleView.setTextAppearance(getContext(), this.mTitleStyleRes);
            }
            if (this.mSubtitleStyleRes != 0) {
                this.mSubtitleView.setTextAppearance(getContext(), this.mSubtitleStyleRes);
            }
        }
        this.mTitleView.setText(this.mTitle);
        this.mSubtitleView.setText(this.mSubtitle);
        boolean hasTitle = TextUtils.isEmpty(this.mTitle) ^ 1;
        boolean hasSubtitle = TextUtils.isEmpty(this.mSubtitle) ^ 1;
        int i = 8;
        this.mSubtitleView.setVisibility(hasSubtitle ? 0 : 8);
        LinearLayout linearLayout = this.mTitleLayout;
        if (!hasTitle) {
            if (!hasSubtitle) {
                linearLayout.setVisibility(i);
                if (this.mTitleLayout.getParent() == null) {
                    addView(this.mTitleLayout);
                }
            }
        }
        i = 0;
        linearLayout.setVisibility(i);
        if (this.mTitleLayout.getParent() == null) {
            addView(this.mTitleLayout);
        }
    }

    public void initForMode(final ActionMode mode) {
        if (this.mClose == null) {
            this.mClose = LayoutInflater.from(getContext()).inflate(this.mCloseItemLayout, this, false);
            addView(this.mClose);
        } else if (this.mClose.getParent() == null) {
            addView(this.mClose);
        }
        this.mClose.findViewById(C0015R.id.action_mode_close_button).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mode.finish();
            }
        });
        MenuBuilder menu = (MenuBuilder) mode.getMenu();
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.dismissPopupMenus();
        }
        this.mActionMenuPresenter = new ActionMenuPresenter(getContext());
        this.mActionMenuPresenter.setReserveOverflow(true);
        LayoutParams layoutParams = new LayoutParams(-2, -1);
        menu.addMenuPresenter(this.mActionMenuPresenter, this.mPopupContext);
        this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
        ViewCompat.setBackground(this.mMenuView, null);
        addView(this.mMenuView, layoutParams);
    }

    public void closeMode() {
        if (this.mClose == null) {
            killMode();
        }
    }

    public void killMode() {
        removeAllViews();
        this.mCustomView = null;
        this.mMenuView = null;
    }

    public boolean showOverflowMenu() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.showOverflowMenu();
        }
        return false;
    }

    public boolean hideOverflowMenu() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.hideOverflowMenu();
        }
        return false;
    }

    public boolean isOverflowMenuShowing() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.isOverflowMenuShowing();
        }
        return false;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(-1, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ViewParent viewParent = this;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        StringBuilder stringBuilder;
        if (widthMode != ErrorDialogData.SUPPRESSED) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(getClass().getSimpleName());
            stringBuilder.append(" can only be used ");
            stringBuilder.append("with android:layout_width=\"match_parent\" (or fill_parent)");
            throw new IllegalStateException(stringBuilder.toString());
        } else if (MeasureSpec.getMode(heightMeasureSpec) != 0) {
            int maxHeight;
            int contentWidth = MeasureSpec.getSize(widthMeasureSpec);
            if (viewParent.mContentHeight > 0) {
                maxHeight = viewParent.mContentHeight;
            } else {
                maxHeight = MeasureSpec.getSize(heightMeasureSpec);
            }
            int verticalPadding = getPaddingTop() + getPaddingBottom();
            int availableWidth = (contentWidth - getPaddingLeft()) - getPaddingRight();
            int height = maxHeight - verticalPadding;
            int childSpecHeight = MeasureSpec.makeMeasureSpec(height, Integer.MIN_VALUE);
            if (viewParent.mClose != null) {
                MarginLayoutParams lp = (MarginLayoutParams) viewParent.mClose.getLayoutParams();
                availableWidth = measureChildView(viewParent.mClose, availableWidth, childSpecHeight, 0) - (lp.leftMargin + lp.rightMargin);
            }
            if (viewParent.mMenuView != null && viewParent.mMenuView.getParent() == viewParent) {
                availableWidth = measureChildView(viewParent.mMenuView, availableWidth, childSpecHeight, 0);
            }
            if (viewParent.mTitleLayout != null && viewParent.mCustomView == null) {
                if (viewParent.mTitleOptional) {
                    viewParent.mTitleLayout.measure(MeasureSpec.makeMeasureSpec(0, 0), childSpecHeight);
                    int titleWidth = viewParent.mTitleLayout.getMeasuredWidth();
                    boolean titleFits = titleWidth <= availableWidth;
                    if (titleFits) {
                        availableWidth -= titleWidth;
                    }
                    viewParent.mTitleLayout.setVisibility(titleFits ? 0 : 8);
                } else {
                    availableWidth = measureChildView(viewParent.mTitleLayout, availableWidth, childSpecHeight, 0);
                }
            }
            if (viewParent.mCustomView != null) {
                LayoutParams lp2 = viewParent.mCustomView.getLayoutParams();
                int customWidthMode = lp2.width != -2 ? ErrorDialogData.SUPPRESSED : Integer.MIN_VALUE;
                viewParent.mCustomView.measure(MeasureSpec.makeMeasureSpec(lp2.width >= 0 ? Math.min(lp2.width, availableWidth) : availableWidth, customWidthMode), MeasureSpec.makeMeasureSpec(lp2.height >= 0 ? Math.min(lp2.height, height) : height, lp2.height != -2 ? ErrorDialogData.SUPPRESSED : Integer.MIN_VALUE));
            }
            if (viewParent.mContentHeight <= 0) {
                widthMode = 0;
                int count = getChildCount();
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 < count) {
                        int paddedViewHeight = getChildAt(i2).getMeasuredHeight() + verticalPadding;
                        if (paddedViewHeight > widthMode) {
                            widthMode = paddedViewHeight;
                        }
                        i = i2 + 1;
                    } else {
                        setMeasuredDimension(contentWidth, widthMode);
                        return;
                    }
                }
            }
            setMeasuredDimension(contentWidth, maxHeight);
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(getClass().getSimpleName());
            stringBuilder.append(" can only be used ");
            stringBuilder.append("with android:layout_height=\"wrap_content\"");
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        ActionBarContextView actionBarContextView = this;
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int x = isLayoutRtl ? (r - l) - getPaddingRight() : getPaddingLeft();
        int y = getPaddingTop();
        int contentHeight = ((b - t) - getPaddingTop()) - getPaddingBottom();
        if (!(actionBarContextView.mClose == null || actionBarContextView.mClose.getVisibility() == 8)) {
            MarginLayoutParams lp = (MarginLayoutParams) actionBarContextView.mClose.getLayoutParams();
            int startMargin = isLayoutRtl ? lp.rightMargin : lp.leftMargin;
            int endMargin = isLayoutRtl ? lp.leftMargin : lp.rightMargin;
            int x2 = AbsActionBarView.next(x, startMargin, isLayoutRtl);
            x = AbsActionBarView.next(positionChild(actionBarContextView.mClose, x2, y, contentHeight, isLayoutRtl) + x2, endMargin, isLayoutRtl);
        }
        int x3 = x;
        if (!(actionBarContextView.mTitleLayout == null || actionBarContextView.mCustomView != null || actionBarContextView.mTitleLayout.getVisibility() == 8)) {
            x3 += positionChild(actionBarContextView.mTitleLayout, x3, y, contentHeight, isLayoutRtl);
        }
        if (actionBarContextView.mCustomView != null) {
            x3 += positionChild(actionBarContextView.mCustomView, x3, y, contentHeight, isLayoutRtl);
        }
        x3 = isLayoutRtl ? getPaddingLeft() : (r - l) - getPaddingRight();
        if (actionBarContextView.mMenuView != null) {
            x3 += positionChild(actionBarContextView.mMenuView, x3, y, contentHeight, isLayoutRtl ^ 1);
        }
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == 32) {
            event.setSource(this);
            event.setClassName(getClass().getName());
            event.setPackageName(getContext().getPackageName());
            event.setContentDescription(this.mTitle);
            return;
        }
        super.onInitializeAccessibilityEvent(event);
    }

    public void setTitleOptional(boolean titleOptional) {
        if (titleOptional != this.mTitleOptional) {
            requestLayout();
        }
        this.mTitleOptional = titleOptional;
    }

    public boolean isTitleOptional() {
        return this.mTitleOptional;
    }
}
