package io.github.yavski.fabspeeddial;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.DefaultBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuBuilder.Callback;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import io.github.yavski.fabmenu.C0040R;
import java.util.HashMap;
import java.util.Map;

@DefaultBehavior(FabSpeedDialBehaviour.class)
public class FabSpeedDial extends LinearLayout implements OnClickListener {
    public static final int BOTTOM_END = 0;
    public static final int BOTTOM_START = 1;
    private static final int DEFAULT_MENU_POSITION = 0;
    public static final FastOutSlowInInterpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new FastOutSlowInInterpolator();
    private static final String TAG = FabSpeedDial.class.getSimpleName();
    public static final int TOP_END = 2;
    public static final int TOP_START = 3;
    private static final int VSYNC_RHYTHM = 16;
    private Map<CardView, MenuItem> cardViewMenuItemMap;
    FloatingActionButton fab;
    private ColorStateList fabBackgroundTint;
    private Drawable fabDrawable;
    private ColorStateList fabDrawableTint;
    private int fabGravity;
    private Map<FloatingActionButton, MenuItem> fabMenuItemMap;
    private boolean isAnimating;
    private int menuId;
    private LinearLayout menuItemsLayout;
    private MenuListener menuListener;
    private ColorStateList miniFabBackgroundTint;
    private int[] miniFabBackgroundTintArray;
    private ColorStateList miniFabDrawableTint;
    private ColorStateList miniFabTitleBackgroundTint;
    private int miniFabTitleTextColor;
    private int[] miniFabTitleTextColorArray;
    private boolean miniFabTitlesEnabled;
    private NavigationMenu navigationMenu;
    private boolean shouldOpenMenu;
    private View touchGuard = null;
    private Drawable touchGuardDrawable;
    private boolean useTouchGuard;

    /* renamed from: io.github.yavski.fabspeeddial.FabSpeedDial$1 */
    class C04291 implements OnClickListener {
        C04291() {
        }

        public void onClick(View v) {
            if (!FabSpeedDial.this.isAnimating) {
                if (FabSpeedDial.this.isMenuOpen()) {
                    FabSpeedDial.this.closeMenu();
                } else {
                    FabSpeedDial.this.openMenu();
                }
            }
        }
    }

    public interface MenuListener {
        void onMenuClosed();

        boolean onMenuItemSelected(MenuItem menuItem);

        boolean onPrepareMenu(NavigationMenu navigationMenu);
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new C04301();
        boolean isShowingMenu;

        /* renamed from: io.github.yavski.fabspeeddial.FabSpeedDial$SavedState$1 */
        static class C04301 implements Creator<SavedState> {
            C04301() {
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        }

        public SavedState(Parcel source) {
            super(source);
            boolean z = true;
            if (source.readInt() != 1) {
                z = false;
            }
            this.isShowingMenu = z;
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.isShowingMenu);
        }
    }

    /* renamed from: io.github.yavski.fabspeeddial.FabSpeedDial$2 */
    class C05632 implements Callback {
        C05632() {
        }

        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
            return FabSpeedDial.this.menuListener != null && FabSpeedDial.this.menuListener.onMenuItemSelected(item);
        }

        public void onMenuModeChange(MenuBuilder menu) {
        }
    }

    /* renamed from: io.github.yavski.fabspeeddial.FabSpeedDial$3 */
    class C05853 extends ViewPropertyAnimatorListenerAdapter {
        C05853() {
        }

        public void onAnimationStart(View view) {
            super.onAnimationStart(view);
            FabSpeedDial.this.isAnimating = true;
        }

        public void onAnimationEnd(View view) {
            super.onAnimationEnd(view);
            FabSpeedDial.this.menuItemsLayout.removeAllViews();
            FabSpeedDial.this.isAnimating = false;
        }
    }

    /* renamed from: io.github.yavski.fabspeeddial.FabSpeedDial$4 */
    class C05864 extends ViewPropertyAnimatorListenerAdapter {
        C05864() {
        }

        public void onAnimationStart(View view) {
            super.onAnimationStart(view);
            FabSpeedDial.this.isAnimating = true;
        }

        public void onAnimationEnd(View view) {
            super.onAnimationEnd(view);
            FabSpeedDial.this.isAnimating = false;
        }
    }

    private FabSpeedDial(Context context) {
        super(context);
    }

    public FabSpeedDial(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @TargetApi(11)
    public FabSpeedDial(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, C0040R.styleable.FabSpeedDial, 0, 0);
        resolveCompulsoryAttributes(typedArray);
        resolveOptionalAttributes(typedArray);
        typedArray.recycle();
        if (isGravityBottom()) {
            LayoutInflater.from(context).inflate(C0040R.layout.fab_speed_dial_bottom, this, true);
        } else {
            LayoutInflater.from(context).inflate(C0040R.layout.fab_speed_dial_top, this, true);
        }
        if (isGravityEnd()) {
            setGravity(GravityCompat.END);
        }
        this.menuItemsLayout = (LinearLayout) findViewById(C0040R.id.menu_items_layout);
        setOrientation(1);
        newNavigationMenu();
        int menuItemCount = this.navigationMenu.size();
        this.fabMenuItemMap = new HashMap(menuItemCount);
        this.cardViewMenuItemMap = new HashMap(menuItemCount);
    }

    private void resolveCompulsoryAttributes(TypedArray typedArray) {
        if (typedArray.hasValue(C0040R.styleable.FabSpeedDial_fabMenu)) {
            this.menuId = typedArray.getResourceId(C0040R.styleable.FabSpeedDial_fabMenu, 0);
            if (typedArray.hasValue(C0040R.styleable.FabSpeedDial_fabGravity)) {
                this.fabGravity = typedArray.getInt(C0040R.styleable.FabSpeedDial_fabGravity, 0);
                return;
            }
            throw new AndroidRuntimeException("You must specify the gravity of the Fab.");
        }
        throw new AndroidRuntimeException("You must provide the id of the menu resource.");
    }

    private void resolveOptionalAttributes(TypedArray typedArray) {
        this.fabDrawable = typedArray.getDrawable(C0040R.styleable.FabSpeedDial_fabDrawable);
        if (this.fabDrawable == null) {
            this.fabDrawable = ContextCompat.getDrawable(getContext(), C0040R.drawable.fab_add_clear_selector);
        }
        this.fabDrawableTint = typedArray.getColorStateList(C0040R.styleable.FabSpeedDial_fabDrawableTint);
        if (this.fabDrawableTint == null) {
            this.fabDrawableTint = getColorStateList(C0040R.color.fab_drawable_tint);
        }
        if (typedArray.hasValue(C0040R.styleable.FabSpeedDial_fabBackgroundTint)) {
            this.fabBackgroundTint = typedArray.getColorStateList(C0040R.styleable.FabSpeedDial_fabBackgroundTint);
        }
        this.miniFabBackgroundTint = typedArray.getColorStateList(C0040R.styleable.FabSpeedDial_miniFabBackgroundTint);
        if (this.miniFabBackgroundTint == null) {
            this.miniFabBackgroundTint = getColorStateList(C0040R.color.fab_background_tint);
        }
        if (typedArray.hasValue(C0040R.styleable.FabSpeedDial_miniFabBackgroundTintList)) {
            TypedArray miniFabBackgroundTintRes = getResources().obtainTypedArray(typedArray.getResourceId(C0040R.styleable.FabSpeedDial_miniFabBackgroundTintList, 0));
            this.miniFabBackgroundTintArray = new int[miniFabBackgroundTintRes.length()];
            for (int i = 0; i < miniFabBackgroundTintRes.length(); i++) {
                this.miniFabBackgroundTintArray[i] = miniFabBackgroundTintRes.getResourceId(i, 0);
            }
            miniFabBackgroundTintRes.recycle();
        }
        this.miniFabDrawableTint = typedArray.getColorStateList(C0040R.styleable.FabSpeedDial_miniFabDrawableTint);
        if (this.miniFabDrawableTint == null) {
            this.miniFabDrawableTint = getColorStateList(C0040R.color.mini_fab_drawable_tint);
        }
        this.miniFabTitleBackgroundTint = typedArray.getColorStateList(C0040R.styleable.FabSpeedDial_miniFabTitleBackgroundTint);
        if (this.miniFabTitleBackgroundTint == null) {
            this.miniFabTitleBackgroundTint = getColorStateList(C0040R.color.mini_fab_title_background_tint);
        }
        this.miniFabTitlesEnabled = typedArray.getBoolean(C0040R.styleable.FabSpeedDial_miniFabTitlesEnabled, true);
        this.miniFabTitleTextColor = typedArray.getColor(C0040R.styleable.FabSpeedDial_miniFabTitleTextColor, ContextCompat.getColor(getContext(), C0040R.color.title_text_color));
        if (typedArray.hasValue(C0040R.styleable.FabSpeedDial_miniFabTitleTextColorList)) {
            TypedArray miniFabTitleTextColorTa = getResources().obtainTypedArray(typedArray.getResourceId(C0040R.styleable.FabSpeedDial_miniFabTitleTextColorList, 0));
            this.miniFabTitleTextColorArray = new int[miniFabTitleTextColorTa.length()];
            for (int i2 = 0; i2 < miniFabTitleTextColorTa.length(); i2++) {
                this.miniFabTitleTextColorArray[i2] = miniFabTitleTextColorTa.getResourceId(i2, 0);
            }
            miniFabTitleTextColorTa.recycle();
        }
        this.touchGuardDrawable = typedArray.getDrawable(C0040R.styleable.FabSpeedDial_touchGuardDrawable);
        this.useTouchGuard = typedArray.getBoolean(C0040R.styleable.FabSpeedDial_touchGuard, true);
    }

    protected void onAttachedToWindow() {
        ViewParent parent;
        super.onAttachedToWindow();
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        int coordinatorLayoutOffset = getResources().getDimensionPixelSize(C0040R.dimen.coordinator_layout_offset);
        if (this.fabGravity != 0) {
            if (this.fabGravity != 2) {
                layoutParams.setMargins(coordinatorLayoutOffset, 0, 0, 0);
                this.menuItemsLayout.setLayoutParams(layoutParams);
                this.fab = (FloatingActionButton) findViewById(C0040R.id.fab);
                this.fab.setImageDrawable(this.fabDrawable);
                if (VERSION.SDK_INT >= 21) {
                    this.fab.setImageTintList(this.fabDrawableTint);
                }
                if (this.fabBackgroundTint != null) {
                    this.fab.setBackgroundTintList(this.fabBackgroundTint);
                }
                this.fab.setOnClickListener(new C04291());
                setFocusableInTouchMode(true);
                if (this.useTouchGuard) {
                    parent = getParent();
                    this.touchGuard = new View(getContext());
                    this.touchGuard.setOnClickListener(this);
                    this.touchGuard.setWillNotDraw(true);
                    this.touchGuard.setVisibility(8);
                    if (this.touchGuardDrawable != null) {
                        if (VERSION.SDK_INT < 16) {
                            this.touchGuard.setBackground(this.touchGuardDrawable);
                        } else {
                            this.touchGuard.setBackgroundDrawable(this.touchGuardDrawable);
                        }
                    }
                    if (parent instanceof FrameLayout) {
                        FrameLayout frameLayout = (FrameLayout) parent;
                        frameLayout.addView(this.touchGuard, frameLayout.indexOfChild(this));
                    } else if (parent instanceof CoordinatorLayout) {
                        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) parent;
                        coordinatorLayout.addView(this.touchGuard, coordinatorLayout.indexOfChild(this));
                    } else if (parent instanceof RelativeLayout) {
                        Log.d(TAG, "touchGuard requires that the parent of this FabSpeedDialer be a FrameLayout or RelativeLayout");
                    } else {
                        ((RelativeLayout) parent).addView(this.touchGuard, ((RelativeLayout) parent).indexOfChild(this), new RelativeLayout.LayoutParams(-1, -1));
                    }
                }
                setOnClickListener(this);
                if (this.shouldOpenMenu) {
                    openMenu();
                }
            }
        }
        layoutParams.setMargins(0, 0, coordinatorLayoutOffset, 0);
        this.menuItemsLayout.setLayoutParams(layoutParams);
        this.fab = (FloatingActionButton) findViewById(C0040R.id.fab);
        this.fab.setImageDrawable(this.fabDrawable);
        if (VERSION.SDK_INT >= 21) {
            this.fab.setImageTintList(this.fabDrawableTint);
        }
        if (this.fabBackgroundTint != null) {
            this.fab.setBackgroundTintList(this.fabBackgroundTint);
        }
        this.fab.setOnClickListener(new C04291());
        setFocusableInTouchMode(true);
        if (this.useTouchGuard) {
            parent = getParent();
            this.touchGuard = new View(getContext());
            this.touchGuard.setOnClickListener(this);
            this.touchGuard.setWillNotDraw(true);
            this.touchGuard.setVisibility(8);
            if (this.touchGuardDrawable != null) {
                if (VERSION.SDK_INT < 16) {
                    this.touchGuard.setBackgroundDrawable(this.touchGuardDrawable);
                } else {
                    this.touchGuard.setBackground(this.touchGuardDrawable);
                }
            }
            if (parent instanceof FrameLayout) {
                FrameLayout frameLayout2 = (FrameLayout) parent;
                frameLayout2.addView(this.touchGuard, frameLayout2.indexOfChild(this));
            } else if (parent instanceof CoordinatorLayout) {
                CoordinatorLayout coordinatorLayout2 = (CoordinatorLayout) parent;
                coordinatorLayout2.addView(this.touchGuard, coordinatorLayout2.indexOfChild(this));
            } else if (parent instanceof RelativeLayout) {
                Log.d(TAG, "touchGuard requires that the parent of this FabSpeedDialer be a FrameLayout or RelativeLayout");
            } else {
                ((RelativeLayout) parent).addView(this.touchGuard, ((RelativeLayout) parent).indexOfChild(this), new RelativeLayout.LayoutParams(-1, -1));
            }
        }
        setOnClickListener(this);
        if (this.shouldOpenMenu) {
            openMenu();
        }
    }

    private void newNavigationMenu() {
        this.navigationMenu = new NavigationMenu(getContext());
        new SupportMenuInflater(getContext()).inflate(this.menuId, this.navigationMenu);
        this.navigationMenu.setCallback(new C05632());
    }

    public void onClick(View v) {
        this.fab.setSelected(false);
        removeFabMenuItems();
        if (this.menuListener != null) {
            if (v != this) {
                if (v != this.touchGuard) {
                    if (v instanceof FloatingActionButton) {
                        this.menuListener.onMenuItemSelected((MenuItem) this.fabMenuItemMap.get(v));
                        return;
                    } else if (v instanceof CardView) {
                        this.menuListener.onMenuItemSelected((MenuItem) this.cardViewMenuItemMap.get(v));
                        return;
                    } else {
                        return;
                    }
                }
            }
            this.menuListener.onMenuClosed();
            return;
        }
        Log.d(TAG, "You haven't provided a MenuListener.");
    }

    protected Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.isShowingMenu = isMenuOpen();
        return ss;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            SavedState ss = (SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            this.shouldOpenMenu = ss.isShowingMenu;
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

    public boolean isMenuOpen() {
        return this.menuItemsLayout.getChildCount() > 0;
    }

    public void openMenu() {
        if (ViewCompat.isAttachedToWindow(this)) {
            requestFocus();
            boolean showMenu = true;
            if (this.menuListener != null) {
                newNavigationMenu();
                showMenu = this.menuListener.onPrepareMenu(this.navigationMenu);
            }
            if (showMenu) {
                addMenuItems();
                this.fab.setSelected(true);
            } else {
                this.fab.setSelected(false);
            }
        }
    }

    public void closeMenu() {
        if (ViewCompat.isAttachedToWindow(this) && isMenuOpen()) {
            this.fab.setSelected(false);
            removeFabMenuItems();
            if (this.menuListener != null) {
                this.menuListener.onMenuClosed();
            }
        }
    }

    public void show() {
        if (ViewCompat.isAttachedToWindow(this)) {
            setVisibility(0);
            this.fab.show();
        }
    }

    public void hide() {
        if (ViewCompat.isAttachedToWindow(this)) {
            if (isMenuOpen()) {
                closeMenu();
            }
            this.fab.hide();
        }
    }

    private void addMenuItems() {
        ViewCompat.setAlpha(this.menuItemsLayout, 1.0f);
        for (int i = 0; i < this.navigationMenu.size(); i++) {
            MenuItem menuItem = this.navigationMenu.getItem(i);
            if (menuItem.isVisible()) {
                this.menuItemsLayout.addView(createFabMenuItem(menuItem));
            }
        }
        animateFabMenuItemsIn();
    }

    private View createFabMenuItem(MenuItem menuItem) {
        ViewGroup fabMenuItem = (ViewGroup) LayoutInflater.from(getContext()).inflate(getMenuItemLayoutId(), this, false);
        FloatingActionButton miniFab = (FloatingActionButton) fabMenuItem.findViewById(C0040R.id.mini_fab);
        CardView cardView = (CardView) fabMenuItem.findViewById(C0040R.id.card_view);
        TextView titleView = (TextView) fabMenuItem.findViewById(C0040R.id.title_view);
        this.fabMenuItemMap.put(miniFab, menuItem);
        this.cardViewMenuItemMap.put(cardView, menuItem);
        miniFab.setImageDrawable(menuItem.getIcon());
        miniFab.setOnClickListener(this);
        cardView.setOnClickListener(this);
        ViewCompat.setAlpha(miniFab, 0.0f);
        ViewCompat.setAlpha(cardView, 0.0f);
        CharSequence title = menuItem.getTitle();
        if (TextUtils.isEmpty(title) || !this.miniFabTitlesEnabled) {
            fabMenuItem.removeView(cardView);
        } else {
            cardView.setCardBackgroundColor(this.miniFabTitleBackgroundTint.getDefaultColor());
            titleView.setText(title);
            titleView.setTypeface(null, 1);
            titleView.setTextColor(this.miniFabTitleTextColor);
            if (this.miniFabTitleTextColorArray != null) {
                titleView.setTextColor(ContextCompat.getColorStateList(getContext(), this.miniFabTitleTextColorArray[menuItem.getOrder()]));
            }
        }
        miniFab.setBackgroundTintList(this.miniFabBackgroundTint);
        if (this.miniFabBackgroundTintArray != null) {
            miniFab.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), this.miniFabBackgroundTintArray[menuItem.getOrder()]));
        }
        if (VERSION.SDK_INT >= 21) {
            miniFab.setImageTintList(this.miniFabDrawableTint);
        }
        return fabMenuItem;
    }

    private void removeFabMenuItems() {
        if (this.touchGuard != null) {
            this.touchGuard.setVisibility(8);
        }
        ViewCompat.animate(this.menuItemsLayout).setDuration((long) getResources().getInteger(17694720)).alpha(0.0f).setInterpolator(new FastOutLinearInInterpolator()).setListener(new C05853()).start();
    }

    private void animateFabMenuItemsIn() {
        int i = 0;
        if (this.touchGuard != null) {
            this.touchGuard.setVisibility(0);
        }
        int count = this.menuItemsLayout.getChildCount();
        View cardView;
        if (isGravityBottom()) {
            for (i = count - 1; i >= 0; i--) {
                View fabMenuItem = this.menuItemsLayout.getChildAt(i);
                animateViewIn(fabMenuItem.findViewById(C0040R.id.mini_fab), Math.abs((count - 1) - i));
                cardView = fabMenuItem.findViewById(C0040R.id.card_view);
                if (cardView != null) {
                    animateViewIn(cardView, Math.abs((count - 1) - i));
                }
            }
            return;
        }
        while (i < count) {
            fabMenuItem = this.menuItemsLayout.getChildAt(i);
            animateViewIn(fabMenuItem.findViewById(C0040R.id.mini_fab), i);
            cardView = fabMenuItem.findViewById(C0040R.id.card_view);
            if (cardView != null) {
                animateViewIn(cardView, i);
            }
            i++;
        }
    }

    private void animateViewIn(View view, int position) {
        float offsetY = (float) getResources().getDimensionPixelSize(C0040R.dimen.keyline_1);
        ViewCompat.setScaleX(view, 0.25f);
        ViewCompat.setScaleY(view, 0.25f);
        ViewCompat.setY(view, ViewCompat.getY(view) + offsetY);
        ViewCompat.animate(view).setDuration((long) getResources().getInteger(17694720)).scaleX(1.0f).scaleY(1.0f).translationYBy(-offsetY).alpha(1.0f).setStartDelay((long) ((position * 4) * 16)).setInterpolator(new FastOutSlowInInterpolator()).setListener(new C05864()).start();
    }

    private int getMenuItemLayoutId() {
        if (isGravityEnd()) {
            return C0040R.layout.fab_menu_item_end;
        }
        return C0040R.layout.fab_menu_item_start;
    }

    private boolean isGravityBottom() {
        if (this.fabGravity != 0) {
            return this.fabGravity == 1;
        } else {
            return true;
        }
    }

    private boolean isGravityEnd() {
        if (this.fabGravity != 0) {
            if (this.fabGravity != 2) {
                return false;
            }
        }
        return true;
    }

    private ColorStateList getColorStateList(int colorRes) {
        int[][] states = new int[4][];
        states[0] = new int[]{16842910};
        states[1] = new int[]{-16842910};
        states[2] = new int[]{-16842912};
        states[3] = new int[]{16842919};
        int color = ContextCompat.getColor(getContext(), colorRes);
        return new ColorStateList(states, new int[]{color, color, color, color});
    }

    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (!isMenuOpen() || event.getKeyCode() != 4 || event.getAction() != 1 || event.getRepeatCount() != 0) {
            return super.dispatchKeyEventPreIme(event);
        }
        closeMenu();
        return true;
    }
}
