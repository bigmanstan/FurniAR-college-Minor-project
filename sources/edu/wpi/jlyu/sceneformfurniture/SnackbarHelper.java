package edu.wpi.jlyu.sceneformfurniture;

import android.app.Activity;
import android.support.design.widget.BaseTransientBottomBar.BaseCallback;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.View.OnClickListener;

public final class SnackbarHelper {
    private static final int BACKGROUND_COLOR = -1087229390;
    private Snackbar messageSnackbar;

    /* renamed from: edu.wpi.jlyu.sceneformfurniture.SnackbarHelper$1 */
    class C00331 implements Runnable {
        C00331() {
        }

        public void run() {
            if (SnackbarHelper.this.messageSnackbar != null) {
                SnackbarHelper.this.messageSnackbar.dismiss();
            }
            SnackbarHelper.this.messageSnackbar = null;
        }
    }

    private enum DismissBehavior {
        HIDE,
        SHOW,
        FINISH
    }

    public boolean isShowing() {
        return this.messageSnackbar != null;
    }

    public void showMessage(Activity activity, String message) {
        show(activity, message, DismissBehavior.HIDE);
    }

    public void showMessageWithDismiss(Activity activity, String message) {
        show(activity, message, DismissBehavior.SHOW);
    }

    public void showError(Activity activity, String errorMessage) {
        show(activity, errorMessage, DismissBehavior.FINISH);
    }

    public void hide(Activity activity) {
        activity.runOnUiThread(new C00331());
    }

    private void show(final Activity activity, final String message, final DismissBehavior dismissBehavior) {
        activity.runOnUiThread(new Runnable() {

            /* renamed from: edu.wpi.jlyu.sceneformfurniture.SnackbarHelper$2$1 */
            class C00341 implements OnClickListener {
                C00341() {
                }

                public void onClick(View v) {
                    SnackbarHelper.this.messageSnackbar.dismiss();
                }
            }

            /* renamed from: edu.wpi.jlyu.sceneformfurniture.SnackbarHelper$2$2 */
            class C00352 extends BaseCallback<Snackbar> {
                C00352() {
                }

                public void onDismissed(Snackbar transientBottomBar, int event) {
                    super.onDismissed(transientBottomBar, event);
                    activity.finish();
                }
            }

            public void run() {
                SnackbarHelper.this.messageSnackbar = Snackbar.make(activity.findViewById(16908290), message, -2);
                SnackbarHelper.this.messageSnackbar.getView().setBackgroundColor(SnackbarHelper.BACKGROUND_COLOR);
                if (dismissBehavior != DismissBehavior.HIDE) {
                    SnackbarHelper.this.messageSnackbar.setAction("Dismiss", new C00341());
                    if (dismissBehavior == DismissBehavior.FINISH) {
                        SnackbarHelper.this.messageSnackbar.addCallback(new C00352());
                    }
                }
                SnackbarHelper.this.messageSnackbar.show();
            }
        });
    }
}
