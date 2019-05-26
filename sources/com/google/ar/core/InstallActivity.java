package com.google.ar.core;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.ar.core.ArCoreApk.Availability;
import com.google.ar.core.ArCoreApk.InstallBehavior;
import com.google.ar.core.ArCoreApk.UserMessageType;
import com.google.ar.core.annotations.UsedByReflection;
import com.google.ar.core.exceptions.FatalException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;
import java.util.concurrent.atomic.AtomicReference;

@TargetApi(24)
@UsedByReflection("AndroidManifest.xml")
public class InstallActivity extends Activity {
    private static final int BOX_SIZE_DP = 280;
    private static final int INSTALLING_TEXT_BOTTOM_MARGIN_DP = 30;
    static final String INSTALL_BEHAVIOR_KEY = "behavior";
    static final String MESSAGE_TYPE_KEY = "message";
    private static final String TAG = "ARCore-InstallActivity";
    private boolean finished;
    private InstallBehavior installBehavior;
    private boolean installStarted;
    private C0379n lastEvent = C0379n.CANCELLED;
    private UserMessageType messageType;
    private final ContextThemeWrapper themeWrapper = new ContextThemeWrapper(this, 16974394);
    private boolean waitingForCompletion;

    private void animateToSpinner() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i = (int) (displayMetrics.density * 280.0f);
        int width = getWindow().getDecorView().getWidth();
        int height = getWindow().getDecorView().getHeight();
        setContentView(new RelativeLayout(this));
        getWindow().getDecorView().setMinimumWidth(i);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        ofFloat.setDuration(300);
        ofFloat.addUpdateListener(new C0376k(this, width, i, height));
        ofFloat.addListener(new C0377l(this));
        ofFloat.start();
    }

    private void closeInstaller() {
        startActivity(new Intent(this, InstallActivity.class).setFlags(67108864));
    }

    private void finishWithFailure(Exception exception) {
        C0555h.m144a().f161a = exception;
        C0555h.m144a().m151b();
        this.finished = true;
        super.finish();
    }

    private boolean isOptional() {
        return this.installBehavior == InstallBehavior.OPTIONAL;
    }

    private void showEducationDialog() {
        setContentView(C0021R.layout.__arcore_education);
        findViewById(C0021R.id.__arcore_cancelButton).setOnClickListener(new C0374i(this));
        if (!isOptional()) {
            findViewById(C0021R.id.__arcore_cancelButton).setVisibility(8);
        }
        findViewById(C0021R.id.__arcore_continueButton).setOnClickListener(new C0375j(this));
        ((TextView) findViewById(C0021R.id.__arcore_messageText)).setText(this.messageType.ordinal() != 1 ? C0021R.string.__arcore_install_app : C0021R.string.__arcore_install_feature);
    }

    private void showSpinner() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i = (int) (displayMetrics.density * 280.0f);
        getWindow().setLayout(i, i);
        View relativeLayout = new RelativeLayout(this.themeWrapper);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13);
        View progressBar = new ProgressBar(this.themeWrapper);
        progressBar.setIndeterminate(true);
        progressBar.setLayoutParams(layoutParams);
        relativeLayout.addView(progressBar);
        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(14);
        layoutParams.addRule(12);
        layoutParams.bottomMargin = (int) (displayMetrics.density * 30.0f);
        View textView = new TextView(this.themeWrapper);
        textView.setText(C0021R.string.__arcore_installing);
        textView.setLayoutParams(layoutParams);
        relativeLayout.addView(textView);
        setContentView(relativeLayout);
        getWindow().setLayout(i, i);
    }

    private void startInstaller() {
        this.installStarted = true;
        this.lastEvent = C0379n.CANCELLED;
        C0555h.m144a().m150a((Context) this).m77a((Activity) this, new C0380o(this));
    }

    public void finish() {
        finishWithFailure(new UnavailableUserDeclinedInstallationException());
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder(27);
        stringBuilder.append("activityResult: ");
        stringBuilder.append(i2);
        Log.i(str, stringBuilder.toString());
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            try {
                finishWithFailure(new FatalException("Install activity was suspended and recreated."));
            } catch (Throwable e) {
                finishWithFailure(new FatalException("Exception starting install flow", e));
            }
        } else {
            this.messageType = (UserMessageType) getIntent().getSerializableExtra(MESSAGE_TYPE_KEY);
            this.installBehavior = (InstallBehavior) getIntent().getSerializableExtra(INSTALL_BEHAVIOR_KEY);
            if (this.messageType != null) {
                if (this.installBehavior != null) {
                    setTheme(16974394);
                    getWindow().requestFeature(1);
                    setFinishOnTouchOutside(isOptional());
                    if (this.messageType == UserMessageType.USER_ALREADY_INFORMED) {
                        showSpinner();
                        return;
                    }
                    AtomicReference atomicReference = new AtomicReference(Availability.UNKNOWN_CHECKING);
                    C0555h.m144a().m150a((Context) this).m79a((Context) this, new C0556z(atomicReference));
                    int ordinal = ((Availability) atomicReference.get()).ordinal();
                    if (ordinal == 0) {
                        Log.w(TAG, "Preliminary compatibility check failed.");
                    } else if (ordinal == 3) {
                        finishWithFailure(new UnavailableDeviceNotCompatibleException());
                        return;
                    }
                    showEducationDialog();
                    return;
                }
            }
            Log.e(TAG, "missing intent data.");
            finishWithFailure(new FatalException("Install activity launched without config data."));
        }
    }

    public void onDestroy() {
        if (!this.finished) {
            C0555h.m144a().m151b();
        }
        super.onDestroy();
    }

    protected void onNewIntent(Intent intent) {
    }

    protected void onResume() {
        super.onResume();
        if (!this.installStarted) {
            if (this.messageType == UserMessageType.USER_ALREADY_INFORMED) {
                startInstaller();
            }
        } else if (!this.finished) {
            synchronized (this) {
                if (this.lastEvent == C0379n.CANCELLED) {
                    finish();
                } else if (this.lastEvent == C0379n.ACCEPTED) {
                    this.waitingForCompletion = true;
                } else {
                    finishWithFailure(C0555h.m144a().f161a);
                }
            }
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("didResume", true);
    }
}
