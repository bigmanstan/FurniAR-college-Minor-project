package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.ActionProvider.SubUiVisibilityListener;
import android.support.v4.view.GravityCompat;
import android.support.v7.appcompat.C0015R;
import android.support.v7.view.ActionBarPolicy;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.ActionMenuItemView.PopupCallback;
import android.support.v7.view.menu.BaseMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.MenuView.ItemView;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionMenuView.ActionMenuChildView;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;

class ActionMenuPresenter extends BaseMenuPresenter implements SubUiVisibilityListener {
    private static final String TAG = "ActionMenuPresenter";
    private final SparseBooleanArray mActionButtonGroups = new SparseBooleanArray();
    ActionButtonSubmenu mActionButtonPopup;
    private int mActionItemWidthLimit;
    private boolean mExpandedActionViewsExclusive;
    private int mMaxItems;
    private boolean mMaxItemsSet;
    private int mMinCellSize;
    int mOpenSubMenuId;
    OverflowMenuButton mOverflowButton;
    OverflowPopup mOverflowPopup;
    private Drawable mPendingOverflowIcon;
    private boolean mPendingOverflowIconSet;
    private ActionMenuPopupCallback mPopupCallback;
    final PopupPresenterCallback mPopupPresenterCallback = new PopupPresenterCallback();
    OpenOverflowRunnable mPostedOpenRunnable;
    private boolean mReserveOverflow;
    private boolean mReserveOverflowSet;
    private View mScrapActionButtonView;
    private boolean mStrictWidthLimit;
    private int mWidthLimit;
    private boolean mWidthLimitSet;

    private class OpenOverflowRunnable implements Runnable {
        private OverflowPopup mPopup;

        public OpenOverflowRunnable(OverflowPopup popup) {
            this.mPopup = popup;
        }

        public void run() {
            if (ActionMenuPresenter.this.mMenu != null) {
                ActionMenuPresenter.this.mMenu.changeMenuMode();
            }
            View menuView = (View) ActionMenuPresenter.this.mMenuView;
            if (!(menuView == null || menuView.getWindowToken() == null || !this.mPopup.tryShow())) {
                ActionMenuPresenter.this.mOverflowPopup = this.mPopup;
            }
            ActionMenuPresenter.this.mPostedOpenRunnable = null;
        }
    }

    private static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new C02941();
        public int openSubMenuId;

        /* renamed from: android.support.v7.widget.ActionMenuPresenter$SavedState$1 */
        static class C02941 implements Creator<SavedState> {
            C02941() {
            }

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }

        SavedState() {
        }

        SavedState(Parcel in) {
            this.openSubMenuId = in.readInt();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.openSubMenuId);
        }
    }

    private class ActionMenuPopupCallback extends PopupCallback {
        ActionMenuPopupCallback() {
        }

        public ShowableListMenu getPopup() {
            return ActionMenuPresenter.this.mActionButtonPopup != null ? ActionMenuPresenter.this.mActionButtonPopup.getPopup() : null;
        }
    }

    private class PopupPresenterCallback implements Callback {
        PopupPresenterCallback() {
        }

        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            boolean z = false;
            if (subMenu == null) {
                return false;
            }
            ActionMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder) subMenu).getItem().getItemId();
            Callback cb = ActionMenuPresenter.this.getCallback();
            if (cb != null) {
                z = cb.onOpenSubMenu(subMenu);
            }
            return z;
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
            if (menu instanceof SubMenuBuilder) {
                menu.getRootMenu().close(false);
            }
            Callback cb = ActionMenuPresenter.this.getCallback();
            if (cb != null) {
                cb.onCloseMenu(menu, allMenusAreClosing);
            }
        }
    }

    private class ActionButtonSubmenu extends MenuPopupHelper {
        public ActionButtonSubmenu(Context context, SubMenuBuilder subMenu, View anchorView) {
            super(context, subMenu, anchorView, false, C0015R.attr.actionOverflowMenuStyle);
            if (!((MenuItemImpl) subMenu.getItem()).isActionButton()) {
                setAnchorView(ActionMenuPresenter.this.mOverflowButton == null ? (View) ActionMenuPresenter.this.mMenuView : ActionMenuPresenter.this.mOverflowButton);
            }
            setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
        }

        protected void onDismiss() {
            ActionMenuPresenter.this.mActionButtonPopup = null;
            ActionMenuPresenter.this.mOpenSubMenuId = 0;
            super.onDismiss();
        }
    }

    private class OverflowMenuButton extends AppCompatImageView implements ActionMenuChildView {
        private final float[] mTempPts = new float[2];

        public OverflowMenuButton(Context context) {
            super(context, null, C0015R.attr.actionOverflowButtonStyle);
            setClickable(true);
            setFocusable(true);
            setVisibility(0);
            setEnabled(true);
            TooltipCompat.setTooltipText(this, getContentDescription());
            setOnTouchListener(new ForwardingListener(this, ActionMenuPresenter.this) {
                public ShowableListMenu getPopup() {
                    if (ActionMenuPresenter.this.mOverflowPopup == null) {
                        return null;
                    }
                    return ActionMenuPresenter.this.mOverflowPopup.getPopup();
                }

                public boolean onForwardingStarted() {
                    ActionMenuPresenter.this.showOverflowMenu();
                    return true;
                }

                public boolean onForwardingStopped() {
                    if (ActionMenuPresenter.this.mPostedOpenRunnable != null) {
                        return false;
                    }
                    ActionMenuPresenter.this.hideOverflowMenu();
                    return true;
                }
            });
        }

        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            ActionMenuPresenter.this.showOverflowMenu();
            return true;
        }

        public boolean needsDividerBefore() {
            return false;
        }

        public boolean needsDividerAfter() {
            return false;
        }

        protected boolean setFrame(int l, int t, int r, int b) {
            boolean changed = super.setFrame(l, t, r, b);
            Drawable d = getDrawable();
            Drawable bg = getBackground();
            if (!(d == null || bg == null)) {
                int width = getWidth();
                int height = getHeight();
                int halfEdge = Math.max(width, height) / 2;
                int centerX = (width + (getPaddingLeft() - getPaddingRight())) / 2;
                int centerY = (height + (getPaddingTop() - getPaddingBottom())) / 2;
                DrawableCompat.setHotspotBounds(bg, centerX - halfEdge, centerY - halfEdge, centerX + halfEdge, centerY + halfEdge);
            }
            return changed;
        }
    }

    private class OverflowPopup extends MenuPopupHelper {
        public OverflowPopup(Context context, MenuBuilder menu, View anchorView, boolean overflowOnly) {
            super(context, menu, anchorView, overflowOnly, C0015R.attr.actionOverflowMenuStyle);
            setGravity(GravityCompat.END);
            setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
        }

        protected void onDismiss() {
            if (ActionMenuPresenter.this.mMenu != null) {
                ActionMenuPresenter.this.mMenu.close();
            }
            ActionMenuPresenter.this.mOverflowPopup = null;
            super.onDismiss();
        }
    }

    public ActionMenuPresenter(Context context) {
        super(context, C0015R.layout.abc_action_menu_layout, C0015R.layout.abc_action_menu_item_layout);
    }

    public void initForMenu(@NonNull Context context, @Nullable MenuBuilder menu) {
        super.initForMenu(context, menu);
        Resources res = context.getResources();
        ActionBarPolicy abp = ActionBarPolicy.get(context);
        if (!this.mReserveOverflowSet) {
            this.mReserveOverflow = abp.showsOverflowMenuButton();
        }
        if (!this.mWidthLimitSet) {
            this.mWidthLimit = abp.getEmbeddedMenuWidthLimit();
        }
        if (!this.mMaxItemsSet) {
            this.mMaxItems = abp.getMaxActionButtons();
        }
        int width = this.mWidthLimit;
        if (this.mReserveOverflow) {
            if (this.mOverflowButton == null) {
                this.mOverflowButton = new OverflowMenuButton(this.mSystemContext);
                if (this.mPendingOverflowIconSet) {
                    this.mOverflowButton.setImageDrawable(this.mPendingOverflowIcon);
                    this.mPendingOverflowIcon = null;
                    this.mPendingOverflowIconSet = false;
                }
                int spec = MeasureSpec.makeMeasureSpec(0, 0);
                this.mOverflowButton.measure(spec, spec);
            }
            width -= this.mOverflowButton.getMeasuredWidth();
        } else {
            this.mOverflowButton = null;
        }
        this.mActionItemWidthLimit = width;
        this.mMinCellSize = (int) (res.getDisplayMetrics().density * 56.0f);
        this.mScrapActionButtonView = null;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (!this.mMaxItemsSet) {
            this.mMaxItems = ActionBarPolicy.get(this.mContext).getMaxActionButtons();
        }
        if (this.mMenu != null) {
            this.mMenu.onItemsChanged(true);
        }
    }

    public void setWidthLimit(int width, boolean strict) {
        this.mWidthLimit = width;
        this.mStrictWidthLimit = strict;
        this.mWidthLimitSet = true;
    }

    public void setReserveOverflow(boolean reserveOverflow) {
        this.mReserveOverflow = reserveOverflow;
        this.mReserveOverflowSet = true;
    }

    public void setItemLimit(int itemCount) {
        this.mMaxItems = itemCount;
        this.mMaxItemsSet = true;
    }

    public void setExpandedActionViewsExclusive(boolean isExclusive) {
        this.mExpandedActionViewsExclusive = isExclusive;
    }

    public void setOverflowIcon(Drawable icon) {
        if (this.mOverflowButton != null) {
            this.mOverflowButton.setImageDrawable(icon);
            return;
        }
        this.mPendingOverflowIconSet = true;
        this.mPendingOverflowIcon = icon;
    }

    public Drawable getOverflowIcon() {
        if (this.mOverflowButton != null) {
            return this.mOverflowButton.getDrawable();
        }
        if (this.mPendingOverflowIconSet) {
            return this.mPendingOverflowIcon;
        }
        return null;
    }

    public MenuView getMenuView(ViewGroup root) {
        MenuView oldMenuView = this.mMenuView;
        MenuView result = super.getMenuView(root);
        if (oldMenuView != result) {
            ((ActionMenuView) result).setPresenter(this);
        }
        return result;
    }

    public View getItemView(MenuItemImpl item, View convertView, ViewGroup parent) {
        View actionView = item.getActionView();
        if (actionView == null || item.hasCollapsibleActionView()) {
            actionView = super.getItemView(item, convertView, parent);
        }
        actionView.setVisibility(item.isActionViewExpanded() ? 8 : 0);
        ActionMenuView menuParent = (ActionMenuView) parent;
        LayoutParams lp = actionView.getLayoutParams();
        if (!menuParent.checkLayoutParams(lp)) {
            actionView.setLayoutParams(menuParent.generateLayoutParams(lp));
        }
        return actionView;
    }

    public void bindItemView(MenuItemImpl item, ItemView itemView) {
        itemView.initialize(item, 0);
        ActionMenuItemView actionItemView = (ActionMenuItemView) itemView;
        actionItemView.setItemInvoker(this.mMenuView);
        if (this.mPopupCallback == null) {
            this.mPopupCallback = new ActionMenuPopupCallback();
        }
        actionItemView.setPopupCallback(this.mPopupCallback);
    }

    public boolean shouldIncludeItem(int childIndex, MenuItemImpl item) {
        return item.isActionButton();
    }

    public void updateMenuView(boolean cleared) {
        ArrayList<MenuItemImpl> actionItems;
        int i;
        super.updateMenuView(cleared);
        ((View) this.mMenuView).requestLayout();
        boolean z = false;
        if (this.mMenu != null) {
            actionItems = this.mMenu.getActionItems();
            int count = actionItems.size();
            for (i = 0; i < count; i++) {
                ActionProvider provider = ((MenuItemImpl) actionItems.get(i)).getSupportActionProvider();
                if (provider != null) {
                    provider.setSubUiVisibilityListener(this);
                }
            }
        }
        actionItems = this.mMenu != null ? this.mMenu.getNonActionItems() : null;
        boolean hasOverflow = false;
        if (this.mReserveOverflow && actionItems != null) {
            i = actionItems.size();
            if (i == 1) {
                hasOverflow = ((MenuItemImpl) actionItems.get(0)).isActionViewExpanded() ^ 1;
            } else {
                if (i > 0) {
                    z = true;
                }
                hasOverflow = z;
            }
        }
        if (hasOverflow) {
            if (this.mOverflowButton == null) {
                this.mOverflowButton = new OverflowMenuButton(this.mSystemContext);
            }
            ViewGroup parent = (ViewGroup) this.mOverflowButton.getParent();
            if (parent != this.mMenuView) {
                if (parent != null) {
                    parent.removeView(this.mOverflowButton);
                }
                ActionMenuView menuView = this.mMenuView;
                menuView.addView(this.mOverflowButton, menuView.generateOverflowButtonLayoutParams());
            }
        } else if (this.mOverflowButton != null && this.mOverflowButton.getParent() == this.mMenuView) {
            ((ViewGroup) this.mMenuView).removeView(this.mOverflowButton);
        }
        ((ActionMenuView) this.mMenuView).setOverflowReserved(this.mReserveOverflow);
    }

    public boolean filterLeftoverView(ViewGroup parent, int childIndex) {
        if (parent.getChildAt(childIndex) == this.mOverflowButton) {
            return false;
        }
        return super.filterLeftoverView(parent, childIndex);
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
        int i = 0;
        if (!subMenu.hasVisibleItems()) {
            return false;
        }
        SubMenuBuilder topSubMenu = subMenu;
        while (topSubMenu.getParentMenu() != this.mMenu) {
            topSubMenu = (SubMenuBuilder) topSubMenu.getParentMenu();
        }
        View anchor = findViewForItem(topSubMenu.getItem());
        if (anchor == null) {
            return false;
        }
        this.mOpenSubMenuId = subMenu.getItem().getItemId();
        boolean preserveIconSpacing = false;
        int count = subMenu.size();
        while (i < count) {
            MenuItem childItem = subMenu.getItem(i);
            if (childItem.isVisible() && childItem.getIcon() != null) {
                preserveIconSpacing = true;
                break;
            }
            i++;
        }
        this.mActionButtonPopup = new ActionButtonSubmenu(this.mContext, subMenu, anchor);
        this.mActionButtonPopup.setForceShowIcon(preserveIconSpacing);
        this.mActionButtonPopup.show();
        super.onSubMenuSelected(subMenu);
        return true;
    }

    private View findViewForItem(MenuItem item) {
        ViewGroup parent = this.mMenuView;
        if (parent == null) {
            return null;
        }
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            if ((child instanceof ItemView) && ((ItemView) child).getItemData() == item) {
                return child;
            }
        }
        return null;
    }

    public boolean showOverflowMenu() {
        if (!this.mReserveOverflow || isOverflowMenuShowing() || this.mMenu == null || this.mMenuView == null || this.mPostedOpenRunnable != null || this.mMenu.getNonActionItems().isEmpty()) {
            return false;
        }
        this.mPostedOpenRunnable = new OpenOverflowRunnable(new OverflowPopup(this.mContext, this.mMenu, this.mOverflowButton, true));
        ((View) this.mMenuView).post(this.mPostedOpenRunnable);
        super.onSubMenuSelected(null);
        return true;
    }

    public boolean hideOverflowMenu() {
        if (this.mPostedOpenRunnable == null || this.mMenuView == null) {
            MenuPopupHelper popup = this.mOverflowPopup;
            if (popup == null) {
                return false;
            }
            popup.dismiss();
            return true;
        }
        ((View) this.mMenuView).removeCallbacks(this.mPostedOpenRunnable);
        this.mPostedOpenRunnable = null;
        return true;
    }

    public boolean dismissPopupMenus() {
        return hideOverflowMenu() | hideSubMenus();
    }

    public boolean hideSubMenus() {
        if (this.mActionButtonPopup == null) {
            return false;
        }
        this.mActionButtonPopup.dismiss();
        return true;
    }

    public boolean isOverflowMenuShowing() {
        return this.mOverflowPopup != null && this.mOverflowPopup.isShowing();
    }

    public boolean isOverflowMenuShowPending() {
        if (this.mPostedOpenRunnable == null) {
            if (!isOverflowMenuShowing()) {
                return false;
            }
        }
        return true;
    }

    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    public boolean flagActionItems() {
        ArrayList<MenuItemImpl> visibleItems;
        ActionMenuPresenter actionMenuPresenter;
        int itemsSize;
        int requiredItems;
        int querySpec;
        ViewGroup parent;
        int requestedItems;
        if (this.mMenu != null) {
            visibleItems = actionMenuPresenter.mMenu.getVisibleItems();
            itemsSize = visibleItems.size();
        } else {
            visibleItems = null;
            itemsSize = 0;
        }
        int maxActions = actionMenuPresenter.mMaxItems;
        int widthLimit = actionMenuPresenter.mActionItemWidthLimit;
        int querySpec2 = MeasureSpec.makeMeasureSpec(0, 0);
        ViewGroup parent2 = actionMenuPresenter.mMenuView;
        int requiredItems2 = 0;
        int requestedItems2 = 0;
        int firstActionWidth = 0;
        boolean hasOverflow = false;
        int maxActions2 = maxActions;
        for (maxActions = 0; maxActions < itemsSize; maxActions++) {
            MenuItemImpl item = (MenuItemImpl) visibleItems.get(maxActions);
            if (item.requiresActionButton()) {
                requiredItems2++;
            } else if (item.requestsActionButton()) {
                requestedItems2++;
            } else {
                hasOverflow = true;
            }
            if (actionMenuPresenter.mExpandedActionViewsExclusive && item.isActionViewExpanded()) {
                maxActions2 = 0;
            }
        }
        if (actionMenuPresenter.mReserveOverflow && (hasOverflow || requiredItems2 + requestedItems2 > maxActions2)) {
            maxActions2--;
        }
        maxActions2 -= requiredItems2;
        SparseBooleanArray seenGroups = actionMenuPresenter.mActionButtonGroups;
        seenGroups.clear();
        int cellSize = 0;
        int cellsRemaining = 0;
        if (actionMenuPresenter.mStrictWidthLimit) {
            cellsRemaining = widthLimit / actionMenuPresenter.mMinCellSize;
            cellSize = actionMenuPresenter.mMinCellSize + ((widthLimit % actionMenuPresenter.mMinCellSize) / cellsRemaining);
        }
        int i = 0;
        while (i < itemsSize) {
            int itemsSize2;
            MenuItemImpl item2 = (MenuItemImpl) visibleItems.get(i);
            if (item2.requiresActionButton()) {
                int widthLimit2;
                itemsSize2 = itemsSize;
                itemsSize = actionMenuPresenter.getItemView(item2, actionMenuPresenter.mScrapActionButtonView, parent2);
                requiredItems = requiredItems2;
                if (actionMenuPresenter.mScrapActionButtonView == null) {
                    actionMenuPresenter.mScrapActionButtonView = itemsSize;
                }
                if (actionMenuPresenter.mStrictWidthLimit) {
                    cellsRemaining -= ActionMenuView.measureChildForCells(itemsSize, cellSize, cellsRemaining, querySpec2, 0);
                } else {
                    itemsSize.measure(querySpec2, querySpec2);
                }
                requiredItems2 = itemsSize.getMeasuredWidth();
                widthLimit -= requiredItems2;
                if (firstActionWidth == 0) {
                    firstActionWidth = requiredItems2;
                }
                View v = itemsSize;
                itemsSize = item2.getGroupId();
                if (itemsSize != 0) {
                    widthLimit2 = widthLimit;
                    widthLimit = 1;
                    seenGroups.put(itemsSize, true);
                } else {
                    widthLimit2 = widthLimit;
                    widthLimit = 1;
                }
                item2.setIsActionButton(widthLimit);
                querySpec = querySpec2;
                parent = parent2;
                requestedItems = requestedItems2;
                widthLimit = widthLimit2;
            } else {
                itemsSize2 = itemsSize;
                requiredItems = requiredItems2;
                if (item2.requestsActionButton() != 0) {
                    boolean isAction;
                    boolean isAction2;
                    View v2;
                    int cells;
                    MenuItemImpl requestedItems3;
                    itemsSize = item2.getGroupId();
                    boolean inGroup = seenGroups.get(itemsSize);
                    if (maxActions2 <= 0) {
                        if (!inGroup) {
                            requestedItems = requestedItems2;
                            isAction = false;
                            if (isAction) {
                                isAction2 = isAction;
                                v2 = actionMenuPresenter.getItemView(item2, actionMenuPresenter.mScrapActionButtonView, parent2);
                                parent = parent2;
                                if (actionMenuPresenter.mScrapActionButtonView == null) {
                                    actionMenuPresenter.mScrapActionButtonView = v2;
                                }
                                if (actionMenuPresenter.mStrictWidthLimit) {
                                    cells = ActionMenuView.measureChildForCells(v2, cellSize, cellsRemaining, querySpec2, 0);
                                    cellsRemaining -= cells;
                                    if (cells == 0) {
                                        parent2 = false;
                                    } else {
                                        parent2 = isAction2;
                                    }
                                } else {
                                    v2.measure(querySpec2, querySpec2);
                                    parent2 = isAction2;
                                }
                                cells = v2.getMeasuredWidth();
                                widthLimit -= cells;
                                if (firstActionWidth == 0) {
                                    firstActionWidth = cells;
                                }
                                querySpec = querySpec2;
                                if (actionMenuPresenter.mStrictWidthLimit != 0) {
                                    querySpec2 = (widthLimit >= 0 ? 1 : 0) & parent2;
                                } else {
                                    querySpec2 = parent2 & (widthLimit + firstActionWidth > 0 ? 1 : 0);
                                }
                            } else {
                                querySpec = querySpec2;
                                parent = parent2;
                                querySpec2 = isAction;
                            }
                            if (querySpec2 == 0 && itemsSize != 0) {
                                seenGroups.put(itemsSize, true);
                            } else if (inGroup) {
                                seenGroups.put(itemsSize, false);
                                parent2 = null;
                                while (parent2 < i) {
                                    requestedItems3 = (MenuItemImpl) visibleItems.get(parent2);
                                    if (requestedItems3.getGroupId() == itemsSize) {
                                        if (requestedItems3.isActionButton()) {
                                            maxActions2++;
                                        }
                                        requestedItems3.setIsActionButton(false);
                                    }
                                    parent2++;
                                    actionMenuPresenter = this;
                                }
                            }
                            if (querySpec2 != 0) {
                                maxActions2--;
                            }
                            item2.setIsActionButton(querySpec2);
                        }
                    }
                    if (widthLimit > 0) {
                        requestedItems = requestedItems2;
                        if (!actionMenuPresenter.mStrictWidthLimit || cellsRemaining > 0) {
                            isAction = true;
                            if (isAction) {
                                querySpec = querySpec2;
                                parent = parent2;
                                querySpec2 = isAction;
                            } else {
                                isAction2 = isAction;
                                v2 = actionMenuPresenter.getItemView(item2, actionMenuPresenter.mScrapActionButtonView, parent2);
                                parent = parent2;
                                if (actionMenuPresenter.mScrapActionButtonView == null) {
                                    actionMenuPresenter.mScrapActionButtonView = v2;
                                }
                                if (actionMenuPresenter.mStrictWidthLimit) {
                                    v2.measure(querySpec2, querySpec2);
                                    parent2 = isAction2;
                                } else {
                                    cells = ActionMenuView.measureChildForCells(v2, cellSize, cellsRemaining, querySpec2, 0);
                                    cellsRemaining -= cells;
                                    if (cells == 0) {
                                        parent2 = isAction2;
                                    } else {
                                        parent2 = false;
                                    }
                                }
                                cells = v2.getMeasuredWidth();
                                widthLimit -= cells;
                                if (firstActionWidth == 0) {
                                    firstActionWidth = cells;
                                }
                                querySpec = querySpec2;
                                if (actionMenuPresenter.mStrictWidthLimit != 0) {
                                    if (widthLimit + firstActionWidth > 0) {
                                    }
                                    querySpec2 = parent2 & (widthLimit + firstActionWidth > 0 ? 1 : 0);
                                } else {
                                    if (widthLimit >= 0) {
                                    }
                                    querySpec2 = (widthLimit >= 0 ? 1 : 0) & parent2;
                                }
                            }
                            if (querySpec2 == 0) {
                            }
                            if (inGroup) {
                                seenGroups.put(itemsSize, false);
                                parent2 = null;
                                while (parent2 < i) {
                                    requestedItems3 = (MenuItemImpl) visibleItems.get(parent2);
                                    if (requestedItems3.getGroupId() == itemsSize) {
                                        if (requestedItems3.isActionButton()) {
                                            maxActions2++;
                                        }
                                        requestedItems3.setIsActionButton(false);
                                    }
                                    parent2++;
                                    actionMenuPresenter = this;
                                }
                            }
                            if (querySpec2 != 0) {
                                maxActions2--;
                            }
                            item2.setIsActionButton(querySpec2);
                        }
                    } else {
                        requestedItems = requestedItems2;
                    }
                    isAction = false;
                    if (isAction) {
                        isAction2 = isAction;
                        v2 = actionMenuPresenter.getItemView(item2, actionMenuPresenter.mScrapActionButtonView, parent2);
                        parent = parent2;
                        if (actionMenuPresenter.mScrapActionButtonView == null) {
                            actionMenuPresenter.mScrapActionButtonView = v2;
                        }
                        if (actionMenuPresenter.mStrictWidthLimit) {
                            cells = ActionMenuView.measureChildForCells(v2, cellSize, cellsRemaining, querySpec2, 0);
                            cellsRemaining -= cells;
                            if (cells == 0) {
                                parent2 = false;
                            } else {
                                parent2 = isAction2;
                            }
                        } else {
                            v2.measure(querySpec2, querySpec2);
                            parent2 = isAction2;
                        }
                        cells = v2.getMeasuredWidth();
                        widthLimit -= cells;
                        if (firstActionWidth == 0) {
                            firstActionWidth = cells;
                        }
                        querySpec = querySpec2;
                        if (actionMenuPresenter.mStrictWidthLimit != 0) {
                            if (widthLimit >= 0) {
                            }
                            querySpec2 = (widthLimit >= 0 ? 1 : 0) & parent2;
                        } else {
                            if (widthLimit + firstActionWidth > 0) {
                            }
                            querySpec2 = parent2 & (widthLimit + firstActionWidth > 0 ? 1 : 0);
                        }
                    } else {
                        querySpec = querySpec2;
                        parent = parent2;
                        querySpec2 = isAction;
                    }
                    if (querySpec2 == 0) {
                    }
                    if (inGroup) {
                        seenGroups.put(itemsSize, false);
                        parent2 = null;
                        while (parent2 < i) {
                            requestedItems3 = (MenuItemImpl) visibleItems.get(parent2);
                            if (requestedItems3.getGroupId() == itemsSize) {
                                if (requestedItems3.isActionButton()) {
                                    maxActions2++;
                                }
                                requestedItems3.setIsActionButton(false);
                            }
                            parent2++;
                            actionMenuPresenter = this;
                        }
                    }
                    if (querySpec2 != 0) {
                        maxActions2--;
                    }
                    item2.setIsActionButton(querySpec2);
                } else {
                    querySpec = querySpec2;
                    parent = parent2;
                    requestedItems = requestedItems2;
                    item2.setIsActionButton(false);
                    i++;
                    itemsSize = itemsSize2;
                    requiredItems2 = requiredItems;
                    requestedItems2 = requestedItems;
                    parent2 = parent;
                    querySpec2 = querySpec;
                    actionMenuPresenter = this;
                }
            }
            i++;
            itemsSize = itemsSize2;
            requiredItems2 = requiredItems;
            requestedItems2 = requestedItems;
            parent2 = parent;
            querySpec2 = querySpec;
            actionMenuPresenter = this;
        }
        querySpec = querySpec2;
        parent = parent2;
        requiredItems = requiredItems2;
        requestedItems = requestedItems2;
        return true;
    }

    public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        dismissPopupMenus();
        super.onCloseMenu(menu, allMenusAreClosing);
    }

    public Parcelable onSaveInstanceState() {
        SavedState state = new SavedState();
        state.openSubMenuId = this.mOpenSubMenuId;
        return state;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            SavedState saved = (SavedState) state;
            if (saved.openSubMenuId > 0) {
                MenuItem item = this.mMenu.findItem(saved.openSubMenuId);
                if (item != null) {
                    onSubMenuSelected((SubMenuBuilder) item.getSubMenu());
                }
            }
        }
    }

    public void onSubUiVisibilityChanged(boolean isVisible) {
        if (isVisible) {
            super.onSubMenuSelected(null);
        } else if (this.mMenu != null) {
            this.mMenu.close(false);
        }
    }

    public void setMenuView(ActionMenuView menuView) {
        this.mMenuView = menuView;
        menuView.initialize(this.mMenu);
    }
}
