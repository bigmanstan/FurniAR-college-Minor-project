package io.github.yavski.fabspeeddial;

import android.support.design.internal.NavigationMenu;
import android.view.MenuItem;
import io.github.yavski.fabspeeddial.FabSpeedDial.MenuListener;

public class SimpleMenuListenerAdapter implements MenuListener {
    public boolean onPrepareMenu(NavigationMenu navigationMenu) {
        return true;
    }

    public boolean onMenuItemSelected(MenuItem menuItem) {
        return false;
    }

    public void onMenuClosed() {
    }
}
