package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class FragmentHostCallback<E> extends FragmentContainer {
    private final Activity mActivity;
    final Context mContext;
    final FragmentManagerImpl mFragmentManager;
    private final Handler mHandler;
    final int mWindowAnimations;

    @Nullable
    public abstract E onGetHost();

    public FragmentHostCallback(Context context, Handler handler, int windowAnimations) {
        this(context instanceof Activity ? (Activity) context : null, context, handler, windowAnimations);
    }

    FragmentHostCallback(FragmentActivity activity) {
        this(activity, activity, activity.mHandler, 0);
    }

    FragmentHostCallback(Activity activity, Context context, Handler handler, int windowAnimations) {
        this.mFragmentManager = new FragmentManagerImpl();
        this.mActivity = activity;
        this.mContext = context;
        this.mHandler = handler;
        this.mWindowAnimations = windowAnimations;
    }

    public void onDump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
    }

    public boolean onShouldSaveFragmentState(Fragment fragment) {
        return true;
    }

    @NonNull
    public LayoutInflater onGetLayoutInflater() {
        return LayoutInflater.from(this.mContext);
    }

    public void onSupportInvalidateOptionsMenu() {
    }

    public void onStartActivityFromFragment(Fragment fragment, Intent intent, int requestCode) {
        onStartActivityFromFragment(fragment, intent, requestCode, null);
    }

    public void onStartActivityFromFragment(Fragment fragment, Intent intent, int requestCode, @Nullable Bundle options) {
        if (requestCode == -1) {
            this.mContext.startActivity(intent);
            return;
        }
        throw new IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
    }

    public void onStartIntentSenderFromFragment(Fragment fragment, IntentSender intent, int requestCode, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws SendIntentException {
        if (requestCode == -1) {
            ActivityCompat.startIntentSenderForResult(this.mActivity, intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
        } else {
            FragmentHostCallback fragmentHostCallback = this;
            throw new IllegalStateException("Starting intent sender with a requestCode requires a FragmentActivity host");
        }
    }

    public void onRequestPermissionsFromFragment(@NonNull Fragment fragment, @NonNull String[] permissions, int requestCode) {
    }

    public boolean onShouldShowRequestPermissionRationale(@NonNull String permission) {
        return false;
    }

    public boolean onHasWindowAnimations() {
        return true;
    }

    public int onGetWindowAnimations() {
        return this.mWindowAnimations;
    }

    @Nullable
    public View onFindViewById(int id) {
        return null;
    }

    public boolean onHasView() {
        return true;
    }

    Activity getActivity() {
        return this.mActivity;
    }

    Context getContext() {
        return this.mContext;
    }

    Handler getHandler() {
        return this.mHandler;
    }

    FragmentManagerImpl getFragmentManagerImpl() {
        return this.mFragmentManager;
    }

    void onAttachFragment(Fragment fragment) {
    }
}
