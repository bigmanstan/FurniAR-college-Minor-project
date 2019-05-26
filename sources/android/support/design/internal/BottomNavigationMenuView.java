package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.C0008R;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SynchronizedPool;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;

@RestrictTo({Scope.LIBRARY_GROUP})
public class BottomNavigationMenuView extends ViewGroup implements MenuView {
    private static final long ACTIVE_ANIMATION_DURATION_MS = 115;
    private final int mActiveItemMaxWidth;
    private BottomNavigationItemView[] mButtons;
    private final int mInactiveItemMaxWidth;
    private final int mInactiveItemMinWidth;
    private int mItemBackgroundRes;
    private final int mItemHeight;
    private ColorStateList mItemIconTint;
    private final Pool<BottomNavigationItemView> mItemPool;
    private ColorStateList mItemTextColor;
    private MenuBuilder mMenu;
    private final OnClickListener mOnClickListener;
    private BottomNavigationPresenter mPresenter;
    private int mSelectedItemId;
    private int mSelectedItemPosition;
    private final TransitionSet mSet;
    private boolean mShiftingMode;
    private int[] mTempChildWidths;

    /* renamed from: android.support.design.internal.BottomNavigationMenuView$1 */
    class C00541 implements OnClickListener {
        C00541() {
        }

        public void onClick(View v) {
            MenuItem item = ((BottomNavigationItemView) v).getItemData();
            if (!BottomNavigationMenuView.this.mMenu.performItemAction(item, BottomNavigationMenuView.this.mPresenter, 0)) {
                item.setChecked(true);
            }
        }
    }

    public BottomNavigationMenuView(Context context) {
        this(context, null);
    }

    public BottomNavigationMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mItemPool = new SynchronizedPool(5);
        this.mShiftingMode = true;
        this.mSelectedItemId = 0;
        this.mSelectedItemPosition = 0;
        Resources res = getResources();
        this.mInactiveItemMaxWidth = res.getDimensionPixelSize(C0008R.dimen.design_bottom_navigation_item_max_width);
        this.mInactiveItemMinWidth = res.getDimensionPixelSize(C0008R.dimen.design_bottom_navigation_item_min_width);
        this.mActiveItemMaxWidth = res.getDimensionPixelSize(C0008R.dimen.design_bottom_navigation_active_item_max_width);
        this.mItemHeight = res.getDimensionPixelSize(C0008R.dimen.design_bottom_navigation_height);
        this.mSet = new AutoTransition();
        this.mSet.setOrdering(0);
        this.mSet.setDuration((long) ACTIVE_ANIMATION_DURATION_MS);
        this.mSet.setInterpolator(new FastOutSlowInInterpolator());
        this.mSet.addTransition(new TextScale());
        this.mOnClickListener = new C00541();
        this.mTempChildWidths = new int[5];
    }

    public void initialize(MenuBuilder menu) {
        this.mMenu = menu;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int count = getChildCount();
        int heightSpec = MeasureSpec.makeMeasureSpec(this.mItemHeight, ErrorDialogData.SUPPRESSED);
        int activeWidth;
        if (this.mShiftingMode) {
            int inactiveCount = count - 1;
            activeWidth = Math.min(width - (r0.mInactiveItemMinWidth * inactiveCount), r0.mActiveItemMaxWidth);
            int inactiveWidth = Math.min((width - activeWidth) / inactiveCount, r0.mInactiveItemMaxWidth);
            int extra = (width - activeWidth) - (inactiveWidth * inactiveCount);
            int i = 0;
            while (i < count) {
                r0.mTempChildWidths[i] = i == r0.mSelectedItemPosition ? activeWidth : inactiveWidth;
                if (extra > 0) {
                    int[] iArr = r0.mTempChildWidths;
                    iArr[i] = iArr[i] + 1;
                    extra--;
                }
                i++;
            }
        } else {
            int childWidth = Math.min(width / (count == 0 ? 1 : count), r0.mActiveItemMaxWidth);
            int extra2 = width - (childWidth * count);
            for (activeWidth = 0; activeWidth < count; activeWidth++) {
                r0.mTempChildWidths[activeWidth] = childWidth;
                if (extra2 > 0) {
                    int[] iArr2 = r0.mTempChildWidths;
                    iArr2[activeWidth] = iArr2[activeWidth] + 1;
                    extra2--;
                }
            }
        }
        int totalWidth = 0;
        for (inactiveCount = 0; inactiveCount < count; inactiveCount++) {
            View child = getChildAt(inactiveCount);
            if (child.getVisibility() != 8) {
                child.measure(MeasureSpec.makeMeasureSpec(r0.mTempChildWidths[inactiveCount], ErrorDialogData.SUPPRESSED), heightSpec);
                child.getLayoutParams().width = child.getMeasuredWidth();
                totalWidth += child.getMeasuredWidth();
            }
        }
        setMeasuredDimension(View.resolveSizeAndState(totalWidth, MeasureSpec.makeMeasureSpec(totalWidth, ErrorDialogData.SUPPRESSED), 0), View.resolveSizeAndState(r0.mItemHeight, heightSpec, 0));
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int count = getChildCount();
        int width = right - left;
        int height = bottom - top;
        int used = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                if (ViewCompat.getLayoutDirection(this) == 1) {
                    child.layout((width - used) - child.getMeasuredWidth(), 0, width - used, height);
                } else {
                    child.layout(used, 0, child.getMeasuredWidth() + used, height);
                }
                used += child.getMeasuredWidth();
            }
        }
    }

    public int getWindowAnimations() {
        return 0;
    }

    public void setIconTintList(ColorStateList tint) {
        this.mItemIconTint = tint;
        if (this.mButtons != null) {
            for (BottomNavigationItemView item : this.mButtons) {
                item.setIconTintList(tint);
            }
        }
    }

    @Nullable
    public ColorStateList getIconTintList() {
        return this.mItemIconTint;
    }

    public void setItemTextColor(ColorStateList color) {
        this.mItemTextColor = color;
        if (this.mButtons != null) {
            for (BottomNavigationItemView item : this.mButtons) {
                item.setTextColor(color);
            }
        }
    }

    public ColorStateList getItemTextColor() {
        return this.mItemTextColor;
    }

    public void setItemBackgroundRes(int background) {
        this.mItemBackgroundRes = background;
        if (this.mButtons != null) {
            for (BottomNavigationItemView item : this.mButtons) {
                item.setItemBackground(background);
            }
        }
    }

    public int getItemBackgroundRes() {
        return this.mItemBackgroundRes;
    }

    public void setPresenter(BottomNavigationPresenter presenter) {
        this.mPresenter = presenter;
    }

    public void buildMenuView() {
        removeAllViews();
        if (this.mButtons != null) {
            for (BottomNavigationItemView item : this.mButtons) {
                this.mItemPool.release(item);
            }
        }
        if (this.mMenu.size() == 0) {
            this.mSelectedItemId = 0;
            this.mSelectedItemPosition = 0;
            this.mButtons = null;
            return;
        }
        this.mButtons = new BottomNavigationItemView[this.mMenu.size()];
        this.mShiftingMode = this.mMenu.size() > 3;
        for (int i = 0; i < this.mMenu.size(); i++) {
            this.mPresenter.setUpdateSuspended(true);
            this.mMenu.getItem(i).setCheckable(true);
            this.mPresenter.setUpdateSuspended(false);
            BottomNavigationItemView child = getNewItem();
            this.mButtons[i] = child;
            child.setIconTintList(this.mItemIconTint);
            child.setTextColor(this.mItemTextColor);
            child.setItemBackground(this.mItemBackgroundRes);
            child.setShiftingMode(this.mShiftingMode);
            child.initialize((MenuItemImpl) this.mMenu.getItem(i), 0);
            child.setItemPosition(i);
            child.setOnClickListener(this.mOnClickListener);
            addView(child);
        }
        this.mSelectedItemPosition = Math.min(this.mMenu.size() - 1, this.mSelectedItemPosition);
        this.mMenu.getItem(this.mSelectedItemPosition).setChecked(true);
    }

    public void updateMenuView() {
        int menuSize = this.mMenu.size();
        if (menuSize != this.mButtons.length) {
            buildMenuView();
            return;
        }
        int i;
        int previousSelectedId = this.mSelectedItemId;
        for (i = 0; i < menuSize; i++) {
            MenuItem item = this.mMenu.getItem(i);
            if (item.isChecked()) {
                this.mSelectedItemId = item.getItemId();
                this.mSelectedItemPosition = i;
            }
        }
        if (previousSelectedId != this.mSelectedItemId) {
            TransitionManager.beginDelayedTransition(this, this.mSet);
        }
        for (i = 0; i < menuSize; i++) {
            this.mPresenter.setUpdateSuspended(true);
            this.mButtons[i].initialize((MenuItemImpl) this.mMenu.getItem(i), 0);
            this.mPresenter.setUpdateSuspended(false);
        }
    }

    private BottomNavigationItemView getNewItem() {
        BottomNavigationItemView item = (BottomNavigationItemView) this.mItemPool.acquire();
        if (item == null) {
            return new BottomNavigationItemView(getContext());
        }
        return item;
    }

    public int getSelectedItemId() {
        return this.mSelectedItemId;
    }

    void tryRestoreSelectedItemId(int itemId) {
        int size = this.mMenu.size();
        for (int i = 0; i < size; i++) {
            MenuItem item = this.mMenu.getItem(i);
            if (itemId == item.getItemId()) {
                this.mSelectedItemId = itemId;
                this.mSelectedItemPosition = i;
                item.setChecked(true);
                return;
            }
        }
    }
}
