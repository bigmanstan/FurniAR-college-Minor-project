package io.github.yavski.fabspeeddial;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.design.widget.CoordinatorLayout.LayoutParams;
import android.support.design.widget.Snackbar.SnackbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.util.AttributeSet;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class FabSpeedDialBehaviour extends Behavior<FabSpeedDial> {
    private static final boolean SNACKBAR_BEHAVIOR_ENABLED = (VERSION.SDK_INT >= 11);
    private float mFabTranslationY;
    private ViewPropertyAnimatorCompat mFabTranslationYAnimator;
    private Rect mTmpRect;

    public FabSpeedDialBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean layoutDependsOn(CoordinatorLayout parent, FabSpeedDial child, View dependency) {
        return SNACKBAR_BEHAVIOR_ENABLED && (dependency instanceof SnackbarLayout);
    }

    public boolean onDependentViewChanged(CoordinatorLayout parent, FabSpeedDial child, View dependency) {
        if (dependency instanceof SnackbarLayout) {
            updateFabTranslationForSnackbar(parent, child, dependency);
        } else if (dependency instanceof AppBarLayout) {
            updateFabVisibility(parent, (AppBarLayout) dependency, child);
        }
        return false;
    }

    private void updateFabTranslationForSnackbar(CoordinatorLayout parent, FabSpeedDial fab, View snackbar) {
        if (fab.getVisibility() == 0) {
            float targetTransY = getFabTranslationYForSnackbar(parent, fab);
            if (this.mFabTranslationY != targetTransY) {
                float currentTransY = ViewCompat.getTranslationY(fab);
                if (this.mFabTranslationYAnimator != null) {
                    this.mFabTranslationYAnimator.cancel();
                }
                if (Math.abs(currentTransY - targetTransY) > ((float) fab.getHeight()) * 0.667f) {
                    this.mFabTranslationYAnimator = ViewCompat.animate(fab).setInterpolator(FabSpeedDial.FAST_OUT_SLOW_IN_INTERPOLATOR).translationY(targetTransY);
                    this.mFabTranslationYAnimator.start();
                } else {
                    ViewCompat.setTranslationY(fab, targetTransY);
                }
                this.mFabTranslationY = targetTransY;
            }
        }
    }

    private float getFabTranslationYForSnackbar(CoordinatorLayout parent, FabSpeedDial fab) {
        float minOffset = 0.0f;
        List<View> dependencies = parent.getDependencies(fab);
        int z = dependencies.size();
        for (int i = 0; i < z; i++) {
            View view = (View) dependencies.get(i);
            if ((view instanceof SnackbarLayout) && parent.doViewsOverlap(fab, view)) {
                minOffset = Math.min(minOffset, ViewCompat.getTranslationY(view) - ((float) view.getHeight()));
            }
        }
        return minOffset;
    }

    public boolean onLayoutChild(CoordinatorLayout parent, FabSpeedDial child, int layoutDirection) {
        List<View> dependencies = parent.getDependencies(child);
        int count = dependencies.size();
        for (int i = 0; i < count; i++) {
            View dependency = (View) dependencies.get(i);
            if ((dependency instanceof AppBarLayout) && updateFabVisibility(parent, (AppBarLayout) dependency, child)) {
                break;
            }
        }
        parent.onLayoutChild(child, layoutDirection);
        return true;
    }

    private boolean updateFabVisibility(CoordinatorLayout parent, AppBarLayout appBarLayout, FabSpeedDial child) {
        if (((LayoutParams) child.getLayoutParams()).getAnchorId() != appBarLayout.getId()) {
            return false;
        }
        if (this.mTmpRect == null) {
            this.mTmpRect = new Rect();
        }
        Rect rect = this.mTmpRect;
        ViewGroupUtils.getDescendantRect(parent, appBarLayout, rect);
        if (getMinimumHeightForVisibleOverlappingContent(appBarLayout) == -1) {
            return true;
        }
        int i = rect.bottom;
        return true;
    }

    private int getMinimumHeightForVisibleOverlappingContent(AppBarLayout appBarLayout) {
        try {
            Method method = appBarLayout.getClass().getDeclaredMethod("getMinimumHeightForVisibleOverlappingContent", new Class[0]);
            method.setAccessible(true);
            return ((Integer) method.invoke(appBarLayout, null)).intValue();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return -1;
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
            return -1;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return -1;
        }
    }
}
