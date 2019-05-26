package android.support.v4.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.arch.lifecycle.ViewModelStore;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment.SavedState;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.util.ArraySet;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.LogWriter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater.Factory2;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: FragmentManager */
final class FragmentManagerImpl extends FragmentManager implements Factory2 {
    static final Interpolator ACCELERATE_CUBIC = new AccelerateInterpolator(1.5f);
    static final Interpolator ACCELERATE_QUINT = new AccelerateInterpolator(2.5f);
    static final int ANIM_DUR = 220;
    public static final int ANIM_STYLE_CLOSE_ENTER = 3;
    public static final int ANIM_STYLE_CLOSE_EXIT = 4;
    public static final int ANIM_STYLE_FADE_ENTER = 5;
    public static final int ANIM_STYLE_FADE_EXIT = 6;
    public static final int ANIM_STYLE_OPEN_ENTER = 1;
    public static final int ANIM_STYLE_OPEN_EXIT = 2;
    static boolean DEBUG = false;
    static final Interpolator DECELERATE_CUBIC = new DecelerateInterpolator(1.5f);
    static final Interpolator DECELERATE_QUINT = new DecelerateInterpolator(2.5f);
    static final String TAG = "FragmentManager";
    static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    static final String TARGET_STATE_TAG = "android:target_state";
    static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    static final String VIEW_STATE_TAG = "android:view_state";
    static Field sAnimationListenerField = null;
    SparseArray<Fragment> mActive;
    final ArrayList<Fragment> mAdded = new ArrayList();
    ArrayList<Integer> mAvailBackStackIndices;
    ArrayList<BackStackRecord> mBackStack;
    ArrayList<OnBackStackChangedListener> mBackStackChangeListeners;
    ArrayList<BackStackRecord> mBackStackIndices;
    FragmentContainer mContainer;
    ArrayList<Fragment> mCreatedMenus;
    int mCurState = 0;
    boolean mDestroyed;
    Runnable mExecCommit = new C01281();
    boolean mExecutingActions;
    boolean mHavePendingDeferredStart;
    FragmentHostCallback mHost;
    private final CopyOnWriteArrayList<Pair<FragmentLifecycleCallbacks, Boolean>> mLifecycleCallbacks = new CopyOnWriteArrayList();
    boolean mNeedMenuInvalidate;
    int mNextFragmentIndex = 0;
    String mNoTransactionsBecause;
    Fragment mParent;
    ArrayList<OpGenerator> mPendingActions;
    ArrayList<StartEnterTransitionListener> mPostponedTransactions;
    Fragment mPrimaryNav;
    FragmentManagerNonConfig mSavedNonConfig;
    SparseArray<Parcelable> mStateArray = null;
    Bundle mStateBundle = null;
    boolean mStateSaved;
    boolean mStopped;
    ArrayList<Fragment> mTmpAddedFragments;
    ArrayList<Boolean> mTmpIsPop;
    ArrayList<BackStackRecord> mTmpRecords;

    /* compiled from: FragmentManager */
    /* renamed from: android.support.v4.app.FragmentManagerImpl$1 */
    class C01281 implements Runnable {
        C01281() {
        }

        public void run() {
            FragmentManagerImpl.this.execPendingActions();
        }
    }

    /* compiled from: FragmentManager */
    private static class AnimationListenerWrapper implements AnimationListener {
        private final AnimationListener mWrapped;

        private AnimationListenerWrapper(AnimationListener wrapped) {
            this.mWrapped = wrapped;
        }

        @CallSuper
        public void onAnimationStart(Animation animation) {
            if (this.mWrapped != null) {
                this.mWrapped.onAnimationStart(animation);
            }
        }

        @CallSuper
        public void onAnimationEnd(Animation animation) {
            if (this.mWrapped != null) {
                this.mWrapped.onAnimationEnd(animation);
            }
        }

        @CallSuper
        public void onAnimationRepeat(Animation animation) {
            if (this.mWrapped != null) {
                this.mWrapped.onAnimationRepeat(animation);
            }
        }
    }

    /* compiled from: FragmentManager */
    private static class AnimationOrAnimator {
        public final Animation animation;
        public final Animator animator;

        private AnimationOrAnimator(Animation animation) {
            this.animation = animation;
            this.animator = null;
            if (animation == null) {
                throw new IllegalStateException("Animation cannot be null");
            }
        }

        private AnimationOrAnimator(Animator animator) {
            this.animation = null;
            this.animator = animator;
            if (animator == null) {
                throw new IllegalStateException("Animator cannot be null");
            }
        }
    }

    /* compiled from: FragmentManager */
    private static class AnimatorOnHWLayerIfNeededListener extends AnimatorListenerAdapter {
        View mView;

        AnimatorOnHWLayerIfNeededListener(View v) {
            this.mView = v;
        }

        public void onAnimationStart(Animator animation) {
            this.mView.setLayerType(2, null);
        }

        public void onAnimationEnd(Animator animation) {
            this.mView.setLayerType(0, null);
            animation.removeListener(this);
        }
    }

    /* compiled from: FragmentManager */
    private static class EndViewTransitionAnimator extends AnimationSet implements Runnable {
        private final View mChild;
        private boolean mEnded;
        private final ViewGroup mParent;
        private boolean mTransitionEnded;

        EndViewTransitionAnimator(@NonNull Animation animation, @NonNull ViewGroup parent, @NonNull View child) {
            super(false);
            this.mParent = parent;
            this.mChild = child;
            addAnimation(animation);
        }

        public boolean getTransformation(long currentTime, Transformation t) {
            if (this.mEnded) {
                return this.mTransitionEnded ^ true;
            }
            if (!super.getTransformation(currentTime, t)) {
                this.mEnded = true;
                OneShotPreDrawListener.add(this.mParent, this);
            }
            return true;
        }

        public boolean getTransformation(long currentTime, Transformation outTransformation, float scale) {
            if (this.mEnded) {
                return this.mTransitionEnded ^ true;
            }
            if (!super.getTransformation(currentTime, outTransformation, scale)) {
                this.mEnded = true;
                OneShotPreDrawListener.add(this.mParent, this);
            }
            return true;
        }

        public void run() {
            this.mParent.endViewTransition(this.mChild);
            this.mTransitionEnded = true;
        }
    }

    /* compiled from: FragmentManager */
    static class FragmentTag {
        public static final int[] Fragment = new int[]{16842755, 16842960, 16842961};
        public static final int Fragment_id = 1;
        public static final int Fragment_name = 0;
        public static final int Fragment_tag = 2;

        FragmentTag() {
        }
    }

    /* compiled from: FragmentManager */
    interface OpGenerator {
        boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2);
    }

    /* compiled from: FragmentManager */
    private static class AnimateOnHWLayerIfNeededListener extends AnimationListenerWrapper {
        View mView;

        /* compiled from: FragmentManager */
        /* renamed from: android.support.v4.app.FragmentManagerImpl$AnimateOnHWLayerIfNeededListener$1 */
        class C01321 implements Runnable {
            C01321() {
            }

            public void run() {
                AnimateOnHWLayerIfNeededListener.this.mView.setLayerType(0, null);
            }
        }

        AnimateOnHWLayerIfNeededListener(View v, AnimationListener listener) {
            super(listener);
            this.mView = v;
        }

        @CallSuper
        public void onAnimationEnd(Animation animation) {
            if (!ViewCompat.isAttachedToWindow(this.mView)) {
                if (VERSION.SDK_INT < 24) {
                    this.mView.setLayerType(0, null);
                    super.onAnimationEnd(animation);
                }
            }
            this.mView.post(new C01321());
            super.onAnimationEnd(animation);
        }
    }

    /* compiled from: FragmentManager */
    private class PopBackStackState implements OpGenerator {
        final int mFlags;
        final int mId;
        final String mName;

        PopBackStackState(String name, int id, int flags) {
            this.mName = name;
            this.mId = id;
            this.mFlags = flags;
        }

        public boolean generateOps(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop) {
            if (FragmentManagerImpl.this.mPrimaryNav != null && this.mId < 0 && this.mName == null) {
                FragmentManager childManager = FragmentManagerImpl.this.mPrimaryNav.peekChildFragmentManager();
                if (childManager != null && childManager.popBackStackImmediate()) {
                    return false;
                }
            }
            return FragmentManagerImpl.this.popBackStackState(records, isRecordPop, this.mName, this.mId, this.mFlags);
        }
    }

    /* compiled from: FragmentManager */
    static class StartEnterTransitionListener implements OnStartEnterTransitionListener {
        private final boolean mIsBack;
        private int mNumPostponed;
        private final BackStackRecord mRecord;

        StartEnterTransitionListener(BackStackRecord record, boolean isBack) {
            this.mIsBack = isBack;
            this.mRecord = record;
        }

        public void onStartEnterTransition() {
            this.mNumPostponed--;
            if (this.mNumPostponed == 0) {
                this.mRecord.mManager.scheduleCommit();
            }
        }

        public void startListening() {
            this.mNumPostponed++;
        }

        public boolean isReady() {
            return this.mNumPostponed == 0;
        }

        public void completeTransaction() {
            boolean z = false;
            boolean canceled = this.mNumPostponed > 0;
            FragmentManagerImpl manager = this.mRecord.mManager;
            int numAdded = manager.mAdded.size();
            for (int i = 0; i < numAdded; i++) {
                Fragment fragment = (Fragment) manager.mAdded.get(i);
                fragment.setOnStartEnterTransitionListener(null);
                if (canceled && fragment.isPostponed()) {
                    fragment.startPostponedEnterTransition();
                }
            }
            FragmentManagerImpl fragmentManagerImpl = this.mRecord.mManager;
            BackStackRecord backStackRecord = this.mRecord;
            boolean z2 = this.mIsBack;
            if (!canceled) {
                z = true;
            }
            fragmentManagerImpl.completeExecute(backStackRecord, z2, z, true);
        }

        public void cancelTransaction() {
            this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, false, false);
        }
    }

    FragmentManagerImpl() {
    }

    static boolean modifiesAlpha(AnimationOrAnimator anim) {
        if (anim.animation instanceof AlphaAnimation) {
            return true;
        }
        if (!(anim.animation instanceof AnimationSet)) {
            return modifiesAlpha(anim.animator);
        }
        List<Animation> anims = ((AnimationSet) anim.animation).getAnimations();
        for (int i = 0; i < anims.size(); i++) {
            if (anims.get(i) instanceof AlphaAnimation) {
                return true;
            }
        }
        return false;
    }

    static boolean modifiesAlpha(Animator anim) {
        if (anim == null) {
            return false;
        }
        if (anim instanceof ValueAnimator) {
            PropertyValuesHolder[] values = ((ValueAnimator) anim).getValues();
            for (PropertyValuesHolder propertyName : values) {
                if ("alpha".equals(propertyName.getPropertyName())) {
                    return true;
                }
            }
        } else if (anim instanceof AnimatorSet) {
            List<Animator> animList = ((AnimatorSet) anim).getChildAnimations();
            for (int i = 0; i < animList.size(); i++) {
                if (modifiesAlpha((Animator) animList.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean shouldRunOnHWLayer(View v, AnimationOrAnimator anim) {
        boolean z = false;
        if (v != null) {
            if (anim != null) {
                if (VERSION.SDK_INT >= 19 && v.getLayerType() == 0 && ViewCompat.hasOverlappingRendering(v) && modifiesAlpha(anim)) {
                    z = true;
                }
                return z;
            }
        }
        return false;
    }

    private void throwException(RuntimeException ex) {
        Log.e(TAG, ex.getMessage());
        Log.e(TAG, "Activity state:");
        PrintWriter pw = new PrintWriter(new LogWriter(TAG));
        if (this.mHost != null) {
            try {
                this.mHost.onDump("  ", null, pw, new String[0]);
            } catch (Exception e) {
                Log.e(TAG, "Failed dumping state", e);
            }
        } else {
            try {
                dump("  ", null, pw, new String[0]);
            } catch (Exception e2) {
                Log.e(TAG, "Failed dumping state", e2);
            }
        }
        throw ex;
    }

    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    public boolean executePendingTransactions() {
        boolean updates = execPendingActions();
        forcePostponedTransactions();
        return updates;
    }

    public void popBackStack() {
        enqueueAction(new PopBackStackState(null, -1, 0), false);
    }

    public boolean popBackStackImmediate() {
        checkStateLoss();
        return popBackStackImmediate(null, -1, 0);
    }

    public void popBackStack(String name, int flags) {
        enqueueAction(new PopBackStackState(name, -1, flags), false);
    }

    public boolean popBackStackImmediate(String name, int flags) {
        checkStateLoss();
        return popBackStackImmediate(name, -1, flags);
    }

    public void popBackStack(int id, int flags) {
        if (id >= 0) {
            enqueueAction(new PopBackStackState(null, id, flags), false);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad id: ");
        stringBuilder.append(id);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public boolean popBackStackImmediate(int id, int flags) {
        checkStateLoss();
        execPendingActions();
        if (id >= 0) {
            return popBackStackImmediate(null, id, flags);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad id: ");
        stringBuilder.append(id);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private boolean popBackStackImmediate(String name, int id, int flags) {
        execPendingActions();
        ensureExecReady(true);
        if (this.mPrimaryNav != null && id < 0 && name == null) {
            FragmentManager childManager = this.mPrimaryNav.peekChildFragmentManager();
            if (childManager != null && childManager.popBackStackImmediate()) {
                return true;
            }
        }
        boolean executePop = popBackStackState(this.mTmpRecords, this.mTmpIsPop, name, id, flags);
        if (executePop) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        doPendingDeferredStart();
        burpActive();
        return executePop;
    }

    public int getBackStackEntryCount() {
        return this.mBackStack != null ? this.mBackStack.size() : 0;
    }

    public BackStackEntry getBackStackEntryAt(int index) {
        return (BackStackEntry) this.mBackStack.get(index);
    }

    public void addOnBackStackChangedListener(OnBackStackChangedListener listener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList();
        }
        this.mBackStackChangeListeners.add(listener);
    }

    public void removeOnBackStackChangedListener(OnBackStackChangedListener listener) {
        if (this.mBackStackChangeListeners != null) {
            this.mBackStackChangeListeners.remove(listener);
        }
    }

    public void putFragment(Bundle bundle, String key, Fragment fragment) {
        if (fragment.mIndex < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(fragment);
            stringBuilder.append(" is not currently in the FragmentManager");
            throwException(new IllegalStateException(stringBuilder.toString()));
        }
        bundle.putInt(key, fragment.mIndex);
    }

    public Fragment getFragment(Bundle bundle, String key) {
        int index = bundle.getInt(key, -1);
        if (index == -1) {
            return null;
        }
        Fragment f = (Fragment) this.mActive.get(index);
        if (f == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment no longer exists for key ");
            stringBuilder.append(key);
            stringBuilder.append(": index ");
            stringBuilder.append(index);
            throwException(new IllegalStateException(stringBuilder.toString()));
        }
        return f;
    }

    public List<Fragment> getFragments() {
        if (this.mAdded.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<Fragment> list;
        synchronized (this.mAdded) {
            list = (List) this.mAdded.clone();
        }
        return list;
    }

    List<Fragment> getActiveFragments() {
        if (this.mActive == null) {
            return null;
        }
        int count = this.mActive.size();
        ArrayList<Fragment> fragments = new ArrayList(count);
        for (int i = 0; i < count; i++) {
            fragments.add(this.mActive.valueAt(i));
        }
        return fragments;
    }

    int getActiveFragmentCount() {
        if (this.mActive == null) {
            return 0;
        }
        return this.mActive.size();
    }

    public SavedState saveFragmentInstanceState(Fragment fragment) {
        if (fragment.mIndex < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(fragment);
            stringBuilder.append(" is not currently in the FragmentManager");
            throwException(new IllegalStateException(stringBuilder.toString()));
        }
        SavedState savedState = null;
        if (fragment.mState <= 0) {
            return null;
        }
        Bundle result = saveFragmentBasicState(fragment);
        if (result != null) {
            savedState = new SavedState(result);
        }
        return savedState;
    }

    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        if (this.mParent != null) {
            DebugUtils.buildShortClassTag(this.mParent, sb);
        } else {
            DebugUtils.buildShortClassTag(this.mHost, sb);
        }
        sb.append("}}");
        return sb.toString();
    }

    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        int N;
        int i;
        Fragment f;
        String innerPrefix = new StringBuilder();
        innerPrefix.append(prefix);
        innerPrefix.append("    ");
        innerPrefix = innerPrefix.toString();
        int i2 = 0;
        if (this.mActive != null) {
            N = this.mActive.size();
            if (N > 0) {
                writer.print(prefix);
                writer.print("Active Fragments in ");
                writer.print(Integer.toHexString(System.identityHashCode(this)));
                writer.println(":");
                for (i = 0; i < N; i++) {
                    f = (Fragment) this.mActive.valueAt(i);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i);
                    writer.print(": ");
                    writer.println(f);
                    if (f != null) {
                        f.dump(innerPrefix, fd, writer, args);
                    }
                }
            }
        }
        N = this.mAdded.size();
        if (N > 0) {
            writer.print(prefix);
            writer.println("Added Fragments:");
            for (i = 0; i < N; i++) {
                f = (Fragment) this.mAdded.get(i);
                writer.print(prefix);
                writer.print("  #");
                writer.print(i);
                writer.print(": ");
                writer.println(f.toString());
            }
        }
        if (this.mCreatedMenus != null) {
            N = this.mCreatedMenus.size();
            if (N > 0) {
                writer.print(prefix);
                writer.println("Fragments Created Menus:");
                for (i = 0; i < N; i++) {
                    f = (Fragment) this.mCreatedMenus.get(i);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i);
                    writer.print(": ");
                    writer.println(f.toString());
                }
            }
        }
        if (this.mBackStack != null) {
            N = this.mBackStack.size();
            if (N > 0) {
                writer.print(prefix);
                writer.println("Back Stack:");
                for (i = 0; i < N; i++) {
                    BackStackRecord bs = (BackStackRecord) this.mBackStack.get(i);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i);
                    writer.print(": ");
                    writer.println(bs.toString());
                    bs.dump(innerPrefix, fd, writer, args);
                }
            }
        }
        synchronized (this) {
            if (this.mBackStackIndices != null) {
                N = this.mBackStackIndices.size();
                if (N > 0) {
                    writer.print(prefix);
                    writer.println("Back Stack Indices:");
                    for (i = 0; i < N; i++) {
                        bs = (BackStackRecord) this.mBackStackIndices.get(i);
                        writer.print(prefix);
                        writer.print("  #");
                        writer.print(i);
                        writer.print(": ");
                        writer.println(bs);
                    }
                }
            }
            if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                writer.print(prefix);
                writer.print("mAvailBackStackIndices: ");
                writer.println(Arrays.toString(this.mAvailBackStackIndices.toArray()));
            }
        }
        if (this.mPendingActions != null) {
            N = this.mPendingActions.size();
            if (N > 0) {
                writer.print(prefix);
                writer.println("Pending Actions:");
                while (i2 < N) {
                    OpGenerator r = (OpGenerator) this.mPendingActions.get(i2);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i2);
                    writer.print(": ");
                    writer.println(r);
                    i2++;
                }
            }
        }
        writer.print(prefix);
        writer.println("FragmentManager misc state:");
        writer.print(prefix);
        writer.print("  mHost=");
        writer.println(this.mHost);
        writer.print(prefix);
        writer.print("  mContainer=");
        writer.println(this.mContainer);
        if (this.mParent != null) {
            writer.print(prefix);
            writer.print("  mParent=");
            writer.println(this.mParent);
        }
        writer.print(prefix);
        writer.print("  mCurState=");
        writer.print(this.mCurState);
        writer.print(" mStateSaved=");
        writer.print(this.mStateSaved);
        writer.print(" mStopped=");
        writer.print(this.mStopped);
        writer.print(" mDestroyed=");
        writer.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            writer.print(prefix);
            writer.print("  mNeedMenuInvalidate=");
            writer.println(this.mNeedMenuInvalidate);
        }
        if (this.mNoTransactionsBecause != null) {
            writer.print(prefix);
            writer.print("  mNoTransactionsBecause=");
            writer.println(this.mNoTransactionsBecause);
        }
    }

    static AnimationOrAnimator makeOpenCloseAnimation(Context context, float startScale, float endScale, float startAlpha, float endAlpha) {
        Animation set = new AnimationSet(false);
        ScaleAnimation scale = new ScaleAnimation(startScale, endScale, startScale, endScale, 1, 0.5f, 1, 0.5f);
        scale.setInterpolator(DECELERATE_QUINT);
        scale.setDuration(220);
        set.addAnimation(scale);
        AlphaAnimation alpha = new AlphaAnimation(startAlpha, endAlpha);
        alpha.setInterpolator(DECELERATE_CUBIC);
        alpha.setDuration(220);
        set.addAnimation(alpha);
        return new AnimationOrAnimator(set);
    }

    static AnimationOrAnimator makeFadeAnimation(Context context, float start, float end) {
        Animation anim = new AlphaAnimation(start, end);
        anim.setInterpolator(DECELERATE_CUBIC);
        anim.setDuration(220);
        return new AnimationOrAnimator(anim);
    }

    AnimationOrAnimator loadAnimation(Fragment fragment, int transit, boolean enter, int transitionStyle) {
        int nextAnim = fragment.getNextAnim();
        Animation animation = fragment.onCreateAnimation(transit, enter, nextAnim);
        if (animation != null) {
            return new AnimationOrAnimator(animation);
        }
        Animator animator = fragment.onCreateAnimator(transit, enter, nextAnim);
        if (animator != null) {
            return new AnimationOrAnimator(animator);
        }
        if (nextAnim != 0) {
            boolean isAnim = "anim".equals(this.mHost.getContext().getResources().getResourceTypeName(nextAnim));
            boolean successfulLoad = false;
            if (isAnim) {
                try {
                    animation = AnimationUtils.loadAnimation(this.mHost.getContext(), nextAnim);
                    if (animation != null) {
                        return new AnimationOrAnimator(animation);
                    }
                    successfulLoad = true;
                } catch (NotFoundException e) {
                    throw e;
                } catch (RuntimeException e2) {
                }
            }
            if (!successfulLoad) {
                try {
                    animator = AnimatorInflater.loadAnimator(this.mHost.getContext(), nextAnim);
                    if (animator != null) {
                        return new AnimationOrAnimator(animator);
                    }
                } catch (RuntimeException e3) {
                    if (isAnim) {
                        throw e3;
                    }
                    animation = AnimationUtils.loadAnimation(this.mHost.getContext(), nextAnim);
                    if (animation != null) {
                        return new AnimationOrAnimator(animation);
                    }
                }
            }
        }
        if (transit == 0) {
            return null;
        }
        int styleIndex = transitToStyleIndex(transit, enter);
        if (styleIndex < 0) {
            return null;
        }
        switch (styleIndex) {
            case 1:
                return makeOpenCloseAnimation(this.mHost.getContext(), 1.125f, 1.0f, 0.0f, 1.0f);
            case 2:
                return makeOpenCloseAnimation(this.mHost.getContext(), 1.0f, 0.975f, 1.0f, 0.0f);
            case 3:
                return makeOpenCloseAnimation(this.mHost.getContext(), 0.975f, 1.0f, 0.0f, 1.0f);
            case 4:
                return makeOpenCloseAnimation(this.mHost.getContext(), 1.0f, 1.075f, 1.0f, 0.0f);
            case 5:
                return makeFadeAnimation(this.mHost.getContext(), 0.0f, 1.0f);
            case 6:
                return makeFadeAnimation(this.mHost.getContext(), 1.0f, 0.0f);
            default:
                if (transitionStyle == 0 && this.mHost.onHasWindowAnimations()) {
                    transitionStyle = this.mHost.onGetWindowAnimations();
                }
                return transitionStyle == 0 ? null : null;
        }
    }

    public void performPendingDeferredStart(Fragment f) {
        if (f.mDeferStart) {
            if (this.mExecutingActions) {
                this.mHavePendingDeferredStart = true;
                return;
            }
            f.mDeferStart = false;
            moveToState(f, this.mCurState, 0, 0, false);
        }
    }

    private static void setHWLayerAnimListenerIfAlpha(View v, AnimationOrAnimator anim) {
        if (v != null) {
            if (anim != null) {
                if (shouldRunOnHWLayer(v, anim)) {
                    if (anim.animator != null) {
                        anim.animator.addListener(new AnimatorOnHWLayerIfNeededListener(v));
                    } else {
                        AnimationListener originalListener = getAnimationListener(anim.animation);
                        v.setLayerType(2, null);
                        anim.animation.setAnimationListener(new AnimateOnHWLayerIfNeededListener(v, originalListener));
                    }
                }
            }
        }
    }

    private static AnimationListener getAnimationListener(Animation animation) {
        AnimationListener originalListener = null;
        try {
            if (sAnimationListenerField == null) {
                sAnimationListenerField = Animation.class.getDeclaredField("mListener");
                sAnimationListenerField.setAccessible(true);
            }
            originalListener = (AnimationListener) sAnimationListenerField.get(animation);
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "No field with the name mListener is found in Animation class", e);
        } catch (IllegalAccessException e2) {
            Log.e(TAG, "Cannot access Animation's mListener field", e2);
        }
        return originalListener;
    }

    boolean isStateAtLeast(int state) {
        return this.mCurState >= state;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void moveToState(android.support.v4.app.Fragment r16, int r17, int r18, int r19, boolean r20) {
        /*
        r15 = this;
        r7 = r15;
        r8 = r16;
        r0 = r8.mAdded;
        r9 = 1;
        if (r0 == 0) goto L_0x0010;
    L_0x0008:
        r0 = r8.mDetached;
        if (r0 == 0) goto L_0x000d;
    L_0x000c:
        goto L_0x0010;
    L_0x000d:
        r0 = r17;
        goto L_0x0015;
    L_0x0010:
        r0 = r17;
        if (r0 <= r9) goto L_0x0015;
    L_0x0014:
        r0 = 1;
    L_0x0015:
        r1 = r8.mRemoving;
        if (r1 == 0) goto L_0x002b;
    L_0x0019:
        r1 = r8.mState;
        if (r0 <= r1) goto L_0x002b;
    L_0x001d:
        r1 = r8.mState;
        if (r1 != 0) goto L_0x0029;
    L_0x0021:
        r1 = r16.isInBackStack();
        if (r1 == 0) goto L_0x0029;
    L_0x0027:
        r0 = 1;
        goto L_0x002b;
    L_0x0029:
        r0 = r8.mState;
    L_0x002b:
        r1 = r8.mDeferStart;
        r10 = 4;
        r11 = 3;
        if (r1 == 0) goto L_0x0038;
    L_0x0031:
        r1 = r8.mState;
        if (r1 >= r10) goto L_0x0038;
    L_0x0035:
        if (r0 <= r11) goto L_0x0038;
    L_0x0037:
        r0 = 3;
    L_0x0038:
        r1 = r8.mState;
        r12 = 2;
        r13 = 0;
        r14 = 0;
        if (r1 > r0) goto L_0x02ff;
    L_0x003f:
        r1 = r8.mFromLayout;
        if (r1 == 0) goto L_0x0048;
    L_0x0043:
        r1 = r8.mInLayout;
        if (r1 != 0) goto L_0x0048;
    L_0x0047:
        return;
    L_0x0048:
        r1 = r16.getAnimatingAway();
        if (r1 != 0) goto L_0x0054;
    L_0x004e:
        r1 = r16.getAnimator();
        if (r1 == 0) goto L_0x0067;
    L_0x0054:
        r8.setAnimatingAway(r13);
        r8.setAnimator(r13);
        r3 = r16.getStateAfterAnimating();
        r4 = 0;
        r5 = 0;
        r6 = 1;
        r1 = r15;
        r2 = r16;
        r1.moveToState(r2, r3, r4, r5, r6);
    L_0x0067:
        r1 = r8.mState;
        switch(r1) {
            case 0: goto L_0x006e;
            case 1: goto L_0x01ad;
            case 2: goto L_0x02ad;
            case 3: goto L_0x02b1;
            case 4: goto L_0x02d3;
            default: goto L_0x006c;
        };
    L_0x006c:
        goto L_0x02f9;
    L_0x006e:
        if (r0 <= 0) goto L_0x01ad;
    L_0x0070:
        r1 = DEBUG;
        if (r1 == 0) goto L_0x008a;
    L_0x0074:
        r1 = "FragmentManager";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "moveto CREATED: ";
        r2.append(r3);
        r2.append(r8);
        r2 = r2.toString();
        android.util.Log.v(r1, r2);
    L_0x008a:
        r1 = r8.mSavedFragmentState;
        if (r1 == 0) goto L_0x00e1;
    L_0x008e:
        r1 = r8.mSavedFragmentState;
        r2 = r7.mHost;
        r2 = r2.getContext();
        r2 = r2.getClassLoader();
        r1.setClassLoader(r2);
        r1 = r8.mSavedFragmentState;
        r2 = "android:view_state";
        r1 = r1.getSparseParcelableArray(r2);
        r8.mSavedViewState = r1;
        r1 = r8.mSavedFragmentState;
        r2 = "android:target_state";
        r1 = r15.getFragment(r1, r2);
        r8.mTarget = r1;
        r1 = r8.mTarget;
        if (r1 == 0) goto L_0x00bf;
    L_0x00b5:
        r1 = r8.mSavedFragmentState;
        r2 = "android:target_req_state";
        r1 = r1.getInt(r2, r14);
        r8.mTargetRequestCode = r1;
    L_0x00bf:
        r1 = r8.mSavedUserVisibleHint;
        if (r1 == 0) goto L_0x00ce;
    L_0x00c3:
        r1 = r8.mSavedUserVisibleHint;
        r1 = r1.booleanValue();
        r8.mUserVisibleHint = r1;
        r8.mSavedUserVisibleHint = r13;
        goto L_0x00d8;
    L_0x00ce:
        r1 = r8.mSavedFragmentState;
        r2 = "android:user_visible_hint";
        r1 = r1.getBoolean(r2, r9);
        r8.mUserVisibleHint = r1;
    L_0x00d8:
        r1 = r8.mUserVisibleHint;
        if (r1 != 0) goto L_0x00e1;
    L_0x00dc:
        r8.mDeferStart = r9;
        if (r0 <= r11) goto L_0x00e1;
    L_0x00e0:
        r0 = 3;
    L_0x00e1:
        r1 = r7.mHost;
        r8.mHost = r1;
        r1 = r7.mParent;
        r8.mParentFragment = r1;
        r1 = r7.mParent;
        if (r1 == 0) goto L_0x00f2;
    L_0x00ed:
        r1 = r7.mParent;
        r1 = r1.mChildFragmentManager;
        goto L_0x00f8;
    L_0x00f2:
        r1 = r7.mHost;
        r1 = r1.getFragmentManagerImpl();
    L_0x00f8:
        r8.mFragmentManager = r1;
        r1 = r8.mTarget;
        if (r1 == 0) goto L_0x0143;
    L_0x00fe:
        r1 = r7.mActive;
        r2 = r8.mTarget;
        r2 = r2.mIndex;
        r1 = r1.get(r2);
        r2 = r8.mTarget;
        if (r1 != r2) goto L_0x011d;
    L_0x010c:
        r1 = r8.mTarget;
        r1 = r1.mState;
        if (r1 >= r9) goto L_0x0143;
    L_0x0112:
        r2 = r8.mTarget;
        r3 = 1;
        r4 = 0;
        r5 = 0;
        r6 = 1;
        r1 = r15;
        r1.moveToState(r2, r3, r4, r5, r6);
        goto L_0x0143;
    L_0x011d:
        r1 = new java.lang.IllegalStateException;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Fragment ";
        r2.append(r3);
        r2.append(r8);
        r3 = " declared target fragment ";
        r2.append(r3);
        r3 = r8.mTarget;
        r2.append(r3);
        r3 = " that does not belong to this FragmentManager!";
        r2.append(r3);
        r2 = r2.toString();
        r1.<init>(r2);
        throw r1;
    L_0x0143:
        r1 = r7.mHost;
        r1 = r1.getContext();
        r15.dispatchOnFragmentPreAttached(r8, r1, r14);
        r8.mCalled = r14;
        r1 = r7.mHost;
        r1 = r1.getContext();
        r8.onAttach(r1);
        r1 = r8.mCalled;
        if (r1 == 0) goto L_0x0191;
    L_0x015b:
        r1 = r8.mParentFragment;
        if (r1 != 0) goto L_0x0165;
    L_0x015f:
        r1 = r7.mHost;
        r1.onAttachFragment(r8);
        goto L_0x016a;
    L_0x0165:
        r1 = r8.mParentFragment;
        r1.onAttachFragment(r8);
    L_0x016a:
        r1 = r7.mHost;
        r1 = r1.getContext();
        r15.dispatchOnFragmentAttached(r8, r1, r14);
        r1 = r8.mIsCreated;
        if (r1 != 0) goto L_0x0187;
    L_0x0177:
        r1 = r8.mSavedFragmentState;
        r15.dispatchOnFragmentPreCreated(r8, r1, r14);
        r1 = r8.mSavedFragmentState;
        r8.performCreate(r1);
        r1 = r8.mSavedFragmentState;
        r15.dispatchOnFragmentCreated(r8, r1, r14);
        goto L_0x018e;
    L_0x0187:
        r1 = r8.mSavedFragmentState;
        r8.restoreChildFragmentState(r1);
        r8.mState = r9;
    L_0x018e:
        r8.mRetaining = r14;
        goto L_0x01ad;
    L_0x0191:
        r1 = new android.support.v4.app.SuperNotCalledException;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Fragment ";
        r2.append(r3);
        r2.append(r8);
        r3 = " did not call through to super.onAttach()";
        r2.append(r3);
        r2 = r2.toString();
        r1.<init>(r2);
        throw r1;
    L_0x01ad:
        r1 = r0;
        r15.ensureInflatedFragmentView(r16);
        if (r1 <= r9) goto L_0x02ac;
    L_0x01b3:
        r0 = DEBUG;
        if (r0 == 0) goto L_0x01cd;
    L_0x01b7:
        r0 = "FragmentManager";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "moveto ACTIVITY_CREATED: ";
        r2.append(r3);
        r2.append(r8);
        r2 = r2.toString();
        android.util.Log.v(r0, r2);
    L_0x01cd:
        r0 = r8.mFromLayout;
        if (r0 != 0) goto L_0x0297;
    L_0x01d1:
        r0 = 0;
        r2 = r8.mContainerId;
        if (r2 == 0) goto L_0x0247;
    L_0x01d6:
        r2 = r8.mContainerId;
        r3 = -1;
        if (r2 != r3) goto L_0x01f9;
    L_0x01db:
        r2 = new java.lang.IllegalArgumentException;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Cannot create fragment ";
        r3.append(r4);
        r3.append(r8);
        r4 = " for a container view with no id";
        r3.append(r4);
        r3 = r3.toString();
        r2.<init>(r3);
        r15.throwException(r2);
    L_0x01f9:
        r2 = r7.mContainer;
        r3 = r8.mContainerId;
        r2 = r2.onFindViewById(r3);
        r2 = (android.view.ViewGroup) r2;
        if (r2 != 0) goto L_0x0246;
    L_0x0205:
        r0 = r8.mRestored;
        if (r0 != 0) goto L_0x0246;
    L_0x0209:
        r0 = r16.getResources();	 Catch:{ NotFoundException -> 0x0214 }
        r3 = r8.mContainerId;	 Catch:{ NotFoundException -> 0x0214 }
        r0 = r0.getResourceName(r3);	 Catch:{ NotFoundException -> 0x0214 }
        goto L_0x0217;
    L_0x0214:
        r0 = move-exception;
        r0 = "unknown";
    L_0x0217:
        r3 = new java.lang.IllegalArgumentException;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "No view found for id 0x";
        r4.append(r5);
        r5 = r8.mContainerId;
        r5 = java.lang.Integer.toHexString(r5);
        r4.append(r5);
        r5 = " (";
        r4.append(r5);
        r4.append(r0);
        r5 = ") for fragment ";
        r4.append(r5);
        r4.append(r8);
        r4 = r4.toString();
        r3.<init>(r4);
        r15.throwException(r3);
    L_0x0246:
        r0 = r2;
    L_0x0247:
        r8.mContainer = r0;
        r2 = r8.mSavedFragmentState;
        r2 = r8.performGetLayoutInflater(r2);
        r3 = r8.mSavedFragmentState;
        r2 = r8.performCreateView(r2, r0, r3);
        r8.mView = r2;
        r2 = r8.mView;
        if (r2 == 0) goto L_0x0295;
    L_0x025b:
        r2 = r8.mView;
        r8.mInnerView = r2;
        r2 = r8.mView;
        r2.setSaveFromParentEnabled(r14);
        if (r0 == 0) goto L_0x026b;
    L_0x0266:
        r2 = r8.mView;
        r0.addView(r2);
    L_0x026b:
        r2 = r8.mHidden;
        if (r2 == 0) goto L_0x0276;
    L_0x026f:
        r2 = r8.mView;
        r3 = 8;
        r2.setVisibility(r3);
    L_0x0276:
        r2 = r8.mView;
        r3 = r8.mSavedFragmentState;
        r8.onViewCreated(r2, r3);
        r2 = r8.mView;
        r3 = r8.mSavedFragmentState;
        r15.dispatchOnFragmentViewCreated(r8, r2, r3, r14);
        r2 = r8.mView;
        r2 = r2.getVisibility();
        if (r2 != 0) goto L_0x0291;
    L_0x028c:
        r2 = r8.mContainer;
        if (r2 == 0) goto L_0x0291;
    L_0x0290:
        goto L_0x0292;
    L_0x0291:
        r9 = r14;
    L_0x0292:
        r8.mIsNewlyAdded = r9;
        goto L_0x0297;
    L_0x0295:
        r8.mInnerView = r13;
    L_0x0297:
        r0 = r8.mSavedFragmentState;
        r8.performActivityCreated(r0);
        r0 = r8.mSavedFragmentState;
        r15.dispatchOnFragmentActivityCreated(r8, r0, r14);
        r0 = r8.mView;
        if (r0 == 0) goto L_0x02aa;
    L_0x02a5:
        r0 = r8.mSavedFragmentState;
        r8.restoreViewState(r0);
    L_0x02aa:
        r8.mSavedFragmentState = r13;
    L_0x02ac:
        r0 = r1;
    L_0x02ad:
        if (r0 <= r12) goto L_0x02b1;
    L_0x02af:
        r8.mState = r11;
    L_0x02b1:
        if (r0 <= r11) goto L_0x02d3;
    L_0x02b3:
        r1 = DEBUG;
        if (r1 == 0) goto L_0x02cd;
    L_0x02b7:
        r1 = "FragmentManager";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "moveto STARTED: ";
        r2.append(r3);
        r2.append(r8);
        r2 = r2.toString();
        android.util.Log.v(r1, r2);
    L_0x02cd:
        r16.performStart();
        r15.dispatchOnFragmentStarted(r8, r14);
    L_0x02d3:
        if (r0 <= r10) goto L_0x02f9;
    L_0x02d5:
        r1 = DEBUG;
        if (r1 == 0) goto L_0x02ef;
    L_0x02d9:
        r1 = "FragmentManager";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "moveto RESUMED: ";
        r2.append(r3);
        r2.append(r8);
        r2 = r2.toString();
        android.util.Log.v(r1, r2);
    L_0x02ef:
        r16.performResume();
        r15.dispatchOnFragmentResumed(r8, r14);
        r8.mSavedFragmentState = r13;
        r8.mSavedViewState = r13;
    L_0x02f9:
        r2 = r18;
        r4 = r19;
        goto L_0x0471;
    L_0x02ff:
        r1 = r8.mState;
        if (r1 <= r0) goto L_0x02f9;
    L_0x0303:
        r1 = r8.mState;
        switch(r1) {
            case 1: goto L_0x03f7;
            case 2: goto L_0x036d;
            case 3: goto L_0x034e;
            case 4: goto L_0x032c;
            case 5: goto L_0x0309;
            default: goto L_0x0308;
        };
    L_0x0308:
        goto L_0x02f9;
    L_0x0309:
        r1 = 5;
        if (r0 >= r1) goto L_0x032c;
    L_0x030c:
        r1 = DEBUG;
        if (r1 == 0) goto L_0x0326;
    L_0x0310:
        r1 = "FragmentManager";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "movefrom RESUMED: ";
        r2.append(r3);
        r2.append(r8);
        r2 = r2.toString();
        android.util.Log.v(r1, r2);
    L_0x0326:
        r16.performPause();
        r15.dispatchOnFragmentPaused(r8, r14);
    L_0x032c:
        if (r0 >= r10) goto L_0x034e;
    L_0x032e:
        r1 = DEBUG;
        if (r1 == 0) goto L_0x0348;
    L_0x0332:
        r1 = "FragmentManager";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "movefrom STARTED: ";
        r2.append(r3);
        r2.append(r8);
        r2 = r2.toString();
        android.util.Log.v(r1, r2);
    L_0x0348:
        r16.performStop();
        r15.dispatchOnFragmentStopped(r8, r14);
    L_0x034e:
        if (r0 >= r11) goto L_0x036d;
    L_0x0350:
        r1 = DEBUG;
        if (r1 == 0) goto L_0x036a;
    L_0x0354:
        r1 = "FragmentManager";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "movefrom STOPPED: ";
        r2.append(r3);
        r2.append(r8);
        r2 = r2.toString();
        android.util.Log.v(r1, r2);
    L_0x036a:
        r16.performReallyStop();
    L_0x036d:
        if (r0 >= r12) goto L_0x03f7;
    L_0x036f:
        r1 = DEBUG;
        if (r1 == 0) goto L_0x0389;
    L_0x0373:
        r1 = "FragmentManager";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "movefrom ACTIVITY_CREATED: ";
        r2.append(r3);
        r2.append(r8);
        r2 = r2.toString();
        android.util.Log.v(r1, r2);
    L_0x0389:
        r1 = r8.mView;
        if (r1 == 0) goto L_0x039c;
    L_0x038d:
        r1 = r7.mHost;
        r1 = r1.onShouldSaveFragmentState(r8);
        if (r1 == 0) goto L_0x039c;
    L_0x0395:
        r1 = r8.mSavedViewState;
        if (r1 != 0) goto L_0x039c;
    L_0x0399:
        r15.saveFragmentViewState(r16);
    L_0x039c:
        r16.performDestroyView();
        r15.dispatchOnFragmentViewDestroyed(r8, r14);
        r1 = r8.mView;
        if (r1 == 0) goto L_0x03ea;
    L_0x03a6:
        r1 = r8.mContainer;
        if (r1 == 0) goto L_0x03ea;
    L_0x03aa:
        r1 = r8.mContainer;
        r2 = r8.mView;
        r1.endViewTransition(r2);
        r1 = r8.mView;
        r1.clearAnimation();
        r1 = 0;
        r2 = r7.mCurState;
        r3 = 0;
        if (r2 <= 0) goto L_0x03d7;
    L_0x03bc:
        r2 = r7.mDestroyed;
        if (r2 != 0) goto L_0x03d7;
    L_0x03c0:
        r2 = r8.mView;
        r2 = r2.getVisibility();
        if (r2 != 0) goto L_0x03d7;
    L_0x03c8:
        r2 = r8.mPostponedAlpha;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 < 0) goto L_0x03d7;
    L_0x03ce:
        r2 = r18;
        r4 = r19;
        r1 = r15.loadAnimation(r8, r2, r14, r4);
        goto L_0x03db;
    L_0x03d7:
        r2 = r18;
        r4 = r19;
    L_0x03db:
        r8.mPostponedAlpha = r3;
        if (r1 == 0) goto L_0x03e2;
    L_0x03df:
        r15.animateRemoveFragment(r8, r1, r0);
    L_0x03e2:
        r3 = r8.mContainer;
        r5 = r8.mView;
        r3.removeView(r5);
        goto L_0x03ee;
    L_0x03ea:
        r2 = r18;
        r4 = r19;
    L_0x03ee:
        r8.mContainer = r13;
        r8.mView = r13;
        r8.mInnerView = r13;
        r8.mInLayout = r14;
        goto L_0x03fb;
    L_0x03f7:
        r2 = r18;
        r4 = r19;
    L_0x03fb:
        if (r0 >= r9) goto L_0x0471;
    L_0x03fd:
        r1 = r7.mDestroyed;
        if (r1 == 0) goto L_0x0422;
    L_0x0401:
        r1 = r16.getAnimatingAway();
        if (r1 == 0) goto L_0x0412;
    L_0x0407:
        r1 = r16.getAnimatingAway();
        r8.setAnimatingAway(r13);
        r1.clearAnimation();
        goto L_0x0422;
    L_0x0412:
        r1 = r16.getAnimator();
        if (r1 == 0) goto L_0x0422;
    L_0x0418:
        r1 = r16.getAnimator();
        r8.setAnimator(r13);
        r1.cancel();
    L_0x0422:
        r1 = r16.getAnimatingAway();
        if (r1 != 0) goto L_0x046d;
    L_0x0428:
        r1 = r16.getAnimator();
        if (r1 == 0) goto L_0x042f;
    L_0x042e:
        goto L_0x046d;
    L_0x042f:
        r1 = DEBUG;
        if (r1 == 0) goto L_0x0449;
    L_0x0433:
        r1 = "FragmentManager";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r5 = "movefrom CREATED: ";
        r3.append(r5);
        r3.append(r8);
        r3 = r3.toString();
        android.util.Log.v(r1, r3);
    L_0x0449:
        r1 = r8.mRetaining;
        if (r1 != 0) goto L_0x0454;
    L_0x044d:
        r16.performDestroy();
        r15.dispatchOnFragmentDestroyed(r8, r14);
        goto L_0x0456;
    L_0x0454:
        r8.mState = r14;
    L_0x0456:
        r16.performDetach();
        r15.dispatchOnFragmentDetached(r8, r14);
        if (r20 != 0) goto L_0x0471;
    L_0x045e:
        r3 = r8.mRetaining;
        if (r3 != 0) goto L_0x0466;
    L_0x0462:
        r15.makeInactive(r16);
        goto L_0x0471;
    L_0x0466:
        r8.mHost = r13;
        r8.mParentFragment = r13;
        r8.mFragmentManager = r13;
        goto L_0x0471;
    L_0x046d:
        r8.setStateAfterAnimating(r0);
        r0 = 1;
    L_0x0471:
        r3 = r8.mState;
        if (r3 == r0) goto L_0x04a4;
    L_0x0475:
        r3 = "FragmentManager";
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "moveToState: Fragment state for ";
        r5.append(r6);
        r5.append(r8);
        r6 = " not updated inline; ";
        r5.append(r6);
        r6 = "expected state ";
        r5.append(r6);
        r5.append(r0);
        r6 = " found ";
        r5.append(r6);
        r6 = r8.mState;
        r5.append(r6);
        r5 = r5.toString();
        android.util.Log.w(r3, r5);
        r8.mState = r0;
    L_0x04a4:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.moveToState(android.support.v4.app.Fragment, int, int, int, boolean):void");
    }

    private void animateRemoveFragment(@NonNull final Fragment fragment, @NonNull AnimationOrAnimator anim, int newState) {
        final View viewToAnimate = fragment.mView;
        final ViewGroup container = fragment.mContainer;
        container.startViewTransition(viewToAnimate);
        fragment.setStateAfterAnimating(newState);
        if (anim.animation != null) {
            Animation animation = new EndViewTransitionAnimator(anim.animation, container, viewToAnimate);
            fragment.setAnimatingAway(fragment.mView);
            animation.setAnimationListener(new AnimationListenerWrapper(getAnimationListener(animation)) {

                /* compiled from: FragmentManager */
                /* renamed from: android.support.v4.app.FragmentManagerImpl$2$1 */
                class C01291 implements Runnable {
                    C01291() {
                    }

                    public void run() {
                        if (fragment.getAnimatingAway() != null) {
                            fragment.setAnimatingAway(null);
                            FragmentManagerImpl.this.moveToState(fragment, fragment.getStateAfterAnimating(), 0, 0, false);
                        }
                    }
                }

                public void onAnimationEnd(Animation animation) {
                    super.onAnimationEnd(animation);
                    container.post(new C01291());
                }
            });
            setHWLayerAnimListenerIfAlpha(viewToAnimate, anim);
            fragment.mView.startAnimation(animation);
            return;
        }
        Animator animator = anim.animator;
        fragment.setAnimator(anim.animator);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                container.endViewTransition(viewToAnimate);
                Animator animator = fragment.getAnimator();
                fragment.setAnimator(null);
                if (animator != null && container.indexOfChild(viewToAnimate) < 0) {
                    FragmentManagerImpl.this.moveToState(fragment, fragment.getStateAfterAnimating(), 0, 0, false);
                }
            }
        });
        animator.setTarget(fragment.mView);
        setHWLayerAnimListenerIfAlpha(fragment.mView, anim);
        animator.start();
    }

    void moveToState(Fragment f) {
        moveToState(f, this.mCurState, 0, 0, false);
    }

    void ensureInflatedFragmentView(Fragment f) {
        if (f.mFromLayout && !f.mPerformedCreateView) {
            f.mView = f.performCreateView(f.performGetLayoutInflater(f.mSavedFragmentState), null, f.mSavedFragmentState);
            if (f.mView != null) {
                f.mInnerView = f.mView;
                f.mView.setSaveFromParentEnabled(false);
                if (f.mHidden) {
                    f.mView.setVisibility(8);
                }
                f.onViewCreated(f.mView, f.mSavedFragmentState);
                dispatchOnFragmentViewCreated(f, f.mView, f.mSavedFragmentState, false);
                return;
            }
            f.mInnerView = null;
        }
    }

    void completeShowHideFragment(final Fragment fragment) {
        if (fragment.mView != null) {
            AnimationOrAnimator anim = loadAnimation(fragment, fragment.getNextTransition(), fragment.mHidden ^ true, fragment.getNextTransitionStyle());
            if (anim == null || anim.animator == null) {
                if (anim != null) {
                    setHWLayerAnimListenerIfAlpha(fragment.mView, anim);
                    fragment.mView.startAnimation(anim.animation);
                    anim.animation.start();
                }
                int visibility = (!fragment.mHidden || fragment.isHideReplaced()) ? 0 : 8;
                fragment.mView.setVisibility(visibility);
                if (fragment.isHideReplaced()) {
                    fragment.setHideReplaced(false);
                }
            } else {
                anim.animator.setTarget(fragment.mView);
                if (!fragment.mHidden) {
                    fragment.mView.setVisibility(0);
                } else if (fragment.isHideReplaced()) {
                    fragment.setHideReplaced(false);
                } else {
                    final ViewGroup container = fragment.mContainer;
                    final View animatingView = fragment.mView;
                    container.startViewTransition(animatingView);
                    anim.animator.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
                            container.endViewTransition(animatingView);
                            animation.removeListener(this);
                            if (fragment.mView != null) {
                                fragment.mView.setVisibility(8);
                            }
                        }
                    });
                }
                setHWLayerAnimListenerIfAlpha(fragment.mView, anim);
                anim.animator.start();
            }
        }
        if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        fragment.mHiddenChanged = false;
        fragment.onHiddenChanged(fragment.mHidden);
    }

    void moveFragmentToExpectedState(Fragment f) {
        if (f != null) {
            int nextState = this.mCurState;
            if (f.mRemoving) {
                if (f.isInBackStack()) {
                    nextState = Math.min(nextState, 1);
                } else {
                    nextState = Math.min(nextState, 0);
                }
            }
            moveToState(f, nextState, f.getNextTransition(), f.getNextTransitionStyle(), false);
            if (f.mView != null) {
                Fragment underFragment = findFragmentUnder(f);
                if (underFragment != null) {
                    View underView = underFragment.mView;
                    ViewGroup container = f.mContainer;
                    int underIndex = container.indexOfChild(underView);
                    int viewIndex = container.indexOfChild(f.mView);
                    if (viewIndex < underIndex) {
                        container.removeViewAt(viewIndex);
                        container.addView(f.mView, underIndex);
                    }
                }
                if (f.mIsNewlyAdded && f.mContainer != null) {
                    if (f.mPostponedAlpha > 0.0f) {
                        f.mView.setAlpha(f.mPostponedAlpha);
                    }
                    f.mPostponedAlpha = 0.0f;
                    f.mIsNewlyAdded = false;
                    AnimationOrAnimator anim = loadAnimation(f, f.getNextTransition(), true, f.getNextTransitionStyle());
                    if (anim != null) {
                        setHWLayerAnimListenerIfAlpha(f.mView, anim);
                        if (anim.animation != null) {
                            f.mView.startAnimation(anim.animation);
                        } else {
                            anim.animator.setTarget(f.mView);
                            anim.animator.start();
                        }
                    }
                }
            }
            if (f.mHiddenChanged) {
                completeShowHideFragment(f);
            }
        }
    }

    void moveToState(int newState, boolean always) {
        if (this.mHost == null) {
            if (newState != 0) {
                throw new IllegalStateException("No activity");
            }
        }
        if (always || newState != this.mCurState) {
            this.mCurState = newState;
            if (this.mActive != null) {
                int i;
                int numAdded = this.mAdded.size();
                for (i = 0; i < numAdded; i++) {
                    moveFragmentToExpectedState((Fragment) this.mAdded.get(i));
                }
                i = this.mActive.size();
                for (int i2 = 0; i2 < i; i2++) {
                    Fragment f = (Fragment) this.mActive.valueAt(i2);
                    if (f != null && ((f.mRemoving || f.mDetached) && !f.mIsNewlyAdded)) {
                        moveFragmentToExpectedState(f);
                    }
                }
                startPendingDeferredFragments();
                if (this.mNeedMenuInvalidate && this.mHost != null && this.mCurState == 5) {
                    this.mHost.onSupportInvalidateOptionsMenu();
                    this.mNeedMenuInvalidate = false;
                }
            }
        }
    }

    void startPendingDeferredFragments() {
        if (this.mActive != null) {
            for (int i = 0; i < this.mActive.size(); i++) {
                Fragment f = (Fragment) this.mActive.valueAt(i);
                if (f != null) {
                    performPendingDeferredStart(f);
                }
            }
        }
    }

    void makeActive(Fragment f) {
        if (f.mIndex < 0) {
            int i = this.mNextFragmentIndex;
            this.mNextFragmentIndex = i + 1;
            f.setIndex(i, this.mParent);
            if (this.mActive == null) {
                this.mActive = new SparseArray();
            }
            this.mActive.put(f.mIndex, f);
            if (DEBUG) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Allocated fragment index ");
                stringBuilder.append(f);
                Log.v(str, stringBuilder.toString());
            }
        }
    }

    void makeInactive(Fragment f) {
        if (f.mIndex >= 0) {
            if (DEBUG) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Freeing fragment index ");
                stringBuilder.append(f);
                Log.v(str, stringBuilder.toString());
            }
            this.mActive.put(f.mIndex, null);
            f.initState();
        }
    }

    public void addFragment(Fragment fragment, boolean moveToStateNow) {
        if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("add: ");
            stringBuilder.append(fragment);
            Log.v(str, stringBuilder.toString());
        }
        makeActive(fragment);
        if (!fragment.mDetached) {
            if (this.mAdded.contains(fragment)) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Fragment already added: ");
                stringBuilder.append(fragment);
                throw new IllegalStateException(stringBuilder.toString());
            }
            synchronized (this.mAdded) {
                this.mAdded.add(fragment);
            }
            fragment.mAdded = true;
            fragment.mRemoving = false;
            if (fragment.mView == null) {
                fragment.mHiddenChanged = false;
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            if (moveToStateNow) {
                moveToState(fragment);
            }
        }
    }

    public void removeFragment(Fragment fragment) {
        if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("remove: ");
            stringBuilder.append(fragment);
            stringBuilder.append(" nesting=");
            stringBuilder.append(fragment.mBackStackNesting);
            Log.v(str, stringBuilder.toString());
        }
        boolean inactive = fragment.isInBackStack() ^ true;
        if (!fragment.mDetached || inactive) {
            synchronized (this.mAdded) {
                this.mAdded.remove(fragment);
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.mAdded = false;
            fragment.mRemoving = true;
        }
    }

    public void hideFragment(Fragment fragment) {
        if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("hide: ");
            stringBuilder.append(fragment);
            Log.v(str, stringBuilder.toString());
        }
        if (!fragment.mHidden) {
            fragment.mHidden = true;
            fragment.mHiddenChanged = true ^ fragment.mHiddenChanged;
        }
    }

    public void showFragment(Fragment fragment) {
        if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("show: ");
            stringBuilder.append(fragment);
            Log.v(str, stringBuilder.toString());
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            fragment.mHiddenChanged ^= 1;
        }
    }

    public void detachFragment(Fragment fragment) {
        if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("detach: ");
            stringBuilder.append(fragment);
            Log.v(str, stringBuilder.toString());
        }
        if (!fragment.mDetached) {
            fragment.mDetached = true;
            if (fragment.mAdded) {
                if (DEBUG) {
                    String str2 = TAG;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("remove from detach: ");
                    stringBuilder2.append(fragment);
                    Log.v(str2, stringBuilder2.toString());
                }
                synchronized (this.mAdded) {
                    this.mAdded.remove(fragment);
                }
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
                fragment.mAdded = false;
            }
        }
    }

    public void attachFragment(Fragment fragment) {
        if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("attach: ");
            stringBuilder.append(fragment);
            Log.v(str, stringBuilder.toString());
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (!fragment.mAdded) {
                if (this.mAdded.contains(fragment)) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Fragment already added: ");
                    stringBuilder.append(fragment);
                    throw new IllegalStateException(stringBuilder.toString());
                }
                if (DEBUG) {
                    str = TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("add from attach: ");
                    stringBuilder.append(fragment);
                    Log.v(str, stringBuilder.toString());
                }
                synchronized (this.mAdded) {
                    this.mAdded.add(fragment);
                }
                fragment.mAdded = true;
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
            }
        }
    }

    public Fragment findFragmentById(int id) {
        int i;
        for (i = this.mAdded.size() - 1; i >= 0; i--) {
            Fragment f = (Fragment) this.mAdded.get(i);
            if (f != null && f.mFragmentId == id) {
                return f;
            }
        }
        if (this.mActive != null) {
            for (i = this.mActive.size() - 1; i >= 0; i--) {
                f = (Fragment) this.mActive.valueAt(i);
                if (f != null && f.mFragmentId == id) {
                    return f;
                }
            }
        }
        return null;
    }

    public Fragment findFragmentByTag(String tag) {
        int i;
        Fragment f;
        if (tag != null) {
            for (i = this.mAdded.size() - 1; i >= 0; i--) {
                f = (Fragment) this.mAdded.get(i);
                if (f != null && tag.equals(f.mTag)) {
                    return f;
                }
            }
        }
        if (!(this.mActive == null || tag == null)) {
            for (i = this.mActive.size() - 1; i >= 0; i--) {
                f = (Fragment) this.mActive.valueAt(i);
                if (f != null && tag.equals(f.mTag)) {
                    return f;
                }
            }
        }
        return null;
    }

    public Fragment findFragmentByWho(String who) {
        if (!(this.mActive == null || who == null)) {
            for (int i = this.mActive.size() - 1; i >= 0; i--) {
                Fragment f = (Fragment) this.mActive.valueAt(i);
                if (f != null) {
                    Fragment findFragmentByWho = f.findFragmentByWho(who);
                    f = findFragmentByWho;
                    if (findFragmentByWho != null) {
                        return f;
                    }
                }
            }
        }
        return null;
    }

    private void checkStateLoss() {
        if (isStateSaved()) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        } else if (this.mNoTransactionsBecause != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can not perform this action inside of ");
            stringBuilder.append(this.mNoTransactionsBecause);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public boolean isStateSaved() {
        if (!this.mStateSaved) {
            if (!this.mStopped) {
                return false;
            }
        }
        return true;
    }

    public void enqueueAction(OpGenerator action, boolean allowStateLoss) {
        if (!allowStateLoss) {
            checkStateLoss();
        }
        synchronized (this) {
            if (!this.mDestroyed) {
                if (this.mHost != null) {
                    if (this.mPendingActions == null) {
                        this.mPendingActions = new ArrayList();
                    }
                    this.mPendingActions.add(action);
                    scheduleCommit();
                    return;
                }
            }
            if (allowStateLoss) {
                return;
            }
            throw new IllegalStateException("Activity has been destroyed");
        }
    }

    private void scheduleCommit() {
        synchronized (this) {
            boolean pendingReady = false;
            boolean postponeReady = (this.mPostponedTransactions == null || this.mPostponedTransactions.isEmpty()) ? false : true;
            if (this.mPendingActions != null && this.mPendingActions.size() == 1) {
                pendingReady = true;
            }
            if (postponeReady || pendingReady) {
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                this.mHost.getHandler().post(this.mExecCommit);
            }
        }
    }

    public int allocBackStackIndex(BackStackRecord bse) {
        synchronized (this) {
            int index;
            if (this.mAvailBackStackIndices != null) {
                if (this.mAvailBackStackIndices.size() > 0) {
                    index = ((Integer) this.mAvailBackStackIndices.remove(this.mAvailBackStackIndices.size() - 1)).intValue();
                    if (DEBUG) {
                        String str = TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Adding back stack index ");
                        stringBuilder.append(index);
                        stringBuilder.append(" with ");
                        stringBuilder.append(bse);
                        Log.v(str, stringBuilder.toString());
                    }
                    this.mBackStackIndices.set(index, bse);
                    return index;
                }
            }
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new ArrayList();
            }
            index = this.mBackStackIndices.size();
            if (DEBUG) {
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Setting back stack index ");
                stringBuilder.append(index);
                stringBuilder.append(" to ");
                stringBuilder.append(bse);
                Log.v(str, stringBuilder.toString());
            }
            this.mBackStackIndices.add(bse);
            return index;
        }
    }

    public void setBackStackIndex(int index, BackStackRecord bse) {
        synchronized (this) {
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new ArrayList();
            }
            int N = this.mBackStackIndices.size();
            String str;
            StringBuilder stringBuilder;
            if (index < N) {
                if (DEBUG) {
                    str = TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Setting back stack index ");
                    stringBuilder.append(index);
                    stringBuilder.append(" to ");
                    stringBuilder.append(bse);
                    Log.v(str, stringBuilder.toString());
                }
                this.mBackStackIndices.set(index, bse);
            } else {
                while (N < index) {
                    this.mBackStackIndices.add(null);
                    if (this.mAvailBackStackIndices == null) {
                        this.mAvailBackStackIndices = new ArrayList();
                    }
                    if (DEBUG) {
                        str = TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Adding available back stack index ");
                        stringBuilder.append(N);
                        Log.v(str, stringBuilder.toString());
                    }
                    this.mAvailBackStackIndices.add(Integer.valueOf(N));
                    N++;
                }
                if (DEBUG) {
                    str = TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Adding back stack index ");
                    stringBuilder.append(index);
                    stringBuilder.append(" with ");
                    stringBuilder.append(bse);
                    Log.v(str, stringBuilder.toString());
                }
                this.mBackStackIndices.add(bse);
            }
        }
    }

    public void freeBackStackIndex(int index) {
        synchronized (this) {
            this.mBackStackIndices.set(index, null);
            if (this.mAvailBackStackIndices == null) {
                this.mAvailBackStackIndices = new ArrayList();
            }
            if (DEBUG) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Freeing back stack index ");
                stringBuilder.append(index);
                Log.v(str, stringBuilder.toString());
            }
            this.mAvailBackStackIndices.add(Integer.valueOf(index));
        }
    }

    private void ensureExecReady(boolean allowStateLoss) {
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        } else if (this.mHost == null) {
            throw new IllegalStateException("Fragment host has been destroyed");
        } else if (Looper.myLooper() == this.mHost.getHandler().getLooper()) {
            if (!allowStateLoss) {
                checkStateLoss();
            }
            if (this.mTmpRecords == null) {
                this.mTmpRecords = new ArrayList();
                this.mTmpIsPop = new ArrayList();
            }
            this.mExecutingActions = true;
            try {
                executePostponedTransaction(null, null);
            } finally {
                this.mExecutingActions = false;
            }
        } else {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
    }

    public void execSingleAction(OpGenerator action, boolean allowStateLoss) {
        if (!allowStateLoss || (this.mHost != null && !this.mDestroyed)) {
            ensureExecReady(allowStateLoss);
            if (action.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
                this.mExecutingActions = true;
                try {
                    removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
                } finally {
                    cleanupExec();
                }
            }
            doPendingDeferredStart();
            burpActive();
        }
    }

    private void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    public boolean execPendingActions() {
        ensureExecReady(true);
        boolean didSomething = false;
        while (generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
                cleanupExec();
                didSomething = true;
            } catch (Throwable th) {
                cleanupExec();
                throw th;
            }
        }
        doPendingDeferredStart();
        burpActive();
        return didSomething;
    }

    private void executePostponedTransaction(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop) {
        int numPostponed = this.mPostponedTransactions == null ? 0 : this.mPostponedTransactions.size();
        int i = 0;
        while (i < numPostponed) {
            int index;
            StartEnterTransitionListener listener = (StartEnterTransitionListener) this.mPostponedTransactions.get(i);
            if (!(records == null || listener.mIsBack)) {
                index = records.indexOf(listener.mRecord);
                if (index != -1 && ((Boolean) isRecordPop.get(index)).booleanValue()) {
                    listener.cancelTransaction();
                    i++;
                }
            }
            if (listener.isReady() || (records != null && listener.mRecord.interactsWith(records, 0, records.size()))) {
                this.mPostponedTransactions.remove(i);
                i--;
                numPostponed--;
                if (!(records == null || listener.mIsBack)) {
                    index = records.indexOf(listener.mRecord);
                    int index2 = index;
                    if (index != -1 && ((Boolean) isRecordPop.get(index2)).booleanValue()) {
                        listener.cancelTransaction();
                    }
                }
                listener.completeTransaction();
            }
            i++;
        }
    }

    private void removeRedundantOperationsAndExecute(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop) {
        if (records != null) {
            if (!records.isEmpty()) {
                if (isRecordPop == null || records.size() != isRecordPop.size()) {
                    throw new IllegalStateException("Internal error with the back stack records");
                }
                executePostponedTransaction(records, isRecordPop);
                int numRecords = records.size();
                int startIndex = 0;
                int recordNum = 0;
                while (recordNum < numRecords) {
                    if (!((BackStackRecord) records.get(recordNum)).mReorderingAllowed) {
                        if (startIndex != recordNum) {
                            executeOpsTogether(records, isRecordPop, startIndex, recordNum);
                        }
                        int reorderingEnd = recordNum + 1;
                        if (((Boolean) isRecordPop.get(recordNum)).booleanValue()) {
                            while (reorderingEnd < numRecords && ((Boolean) isRecordPop.get(reorderingEnd)).booleanValue() && !((BackStackRecord) records.get(reorderingEnd)).mReorderingAllowed) {
                                reorderingEnd++;
                            }
                        }
                        executeOpsTogether(records, isRecordPop, recordNum, reorderingEnd);
                        startIndex = reorderingEnd;
                        recordNum = reorderingEnd - 1;
                    }
                    recordNum++;
                }
                if (startIndex != numRecords) {
                    executeOpsTogether(records, isRecordPop, startIndex, numRecords);
                }
            }
        }
    }

    private void executeOpsTogether(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex) {
        ArrayList<BackStackRecord> arrayList = records;
        ArrayList<Boolean> arrayList2 = isRecordPop;
        int i = startIndex;
        int i2 = endIndex;
        boolean allowReordering = ((BackStackRecord) arrayList.get(i)).mReorderingAllowed;
        if (this.mTmpAddedFragments == null) {
            r6.mTmpAddedFragments = new ArrayList();
        } else {
            r6.mTmpAddedFragments.clear();
        }
        r6.mTmpAddedFragments.addAll(r6.mAdded);
        boolean addToBackStack = false;
        Fragment oldPrimaryNav = getPrimaryNavigationFragment();
        int recordNum = i;
        while (true) {
            boolean z = true;
            if (recordNum >= i2) {
                break;
            }
            Fragment trackAddedFragmentsInPop;
            BackStackRecord record = (BackStackRecord) arrayList.get(recordNum);
            if (((Boolean) arrayList2.get(recordNum)).booleanValue()) {
                trackAddedFragmentsInPop = record.trackAddedFragmentsInPop(r6.mTmpAddedFragments, oldPrimaryNav);
            } else {
                trackAddedFragmentsInPop = record.expandOps(r6.mTmpAddedFragments, oldPrimaryNav);
            }
            oldPrimaryNav = trackAddedFragmentsInPop;
            if (!addToBackStack) {
                if (!record.mAddToBackStack) {
                    z = false;
                }
            }
            addToBackStack = z;
            recordNum++;
        }
        r6.mTmpAddedFragments.clear();
        if (!allowReordering) {
            FragmentTransition.startTransitions(this, records, isRecordPop, startIndex, endIndex, false);
        }
        executeOps(records, isRecordPop, startIndex, endIndex);
        int postponeIndex = endIndex;
        if (allowReordering) {
            ArraySet<Fragment> addedFragments = new ArraySet();
            addAddedFragments(addedFragments);
            ArraySet<Fragment> addedFragments2 = addedFragments;
            recordNum = postponePostponableTransactions(records, isRecordPop, startIndex, endIndex, addedFragments);
            makeRemovedFragmentsInvisible(addedFragments2);
            postponeIndex = recordNum;
        }
        if (postponeIndex != i && allowReordering) {
            FragmentTransition.startTransitions(this, records, isRecordPop, startIndex, postponeIndex, true);
            moveToState(r6.mCurState, true);
        }
        for (recordNum = i; recordNum < i2; recordNum++) {
            record = (BackStackRecord) arrayList.get(recordNum);
            if (((Boolean) arrayList2.get(recordNum)).booleanValue() && record.mIndex >= 0) {
                freeBackStackIndex(record.mIndex);
                record.mIndex = -1;
            }
            record.runOnCommitRunnables();
        }
        if (addToBackStack) {
            reportBackStackChanged();
        }
    }

    private void makeRemovedFragmentsInvisible(ArraySet<Fragment> fragments) {
        int numAdded = fragments.size();
        for (int i = 0; i < numAdded; i++) {
            Fragment fragment = (Fragment) fragments.valueAt(i);
            if (!fragment.mAdded) {
                View view = fragment.getView();
                fragment.mPostponedAlpha = view.getAlpha();
                view.setAlpha(0.0f);
            }
        }
    }

    private int postponePostponableTransactions(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex, ArraySet<Fragment> added) {
        int postponeIndex = endIndex;
        for (int i = endIndex - 1; i >= startIndex; i--) {
            BackStackRecord record = (BackStackRecord) records.get(i);
            boolean isPop = ((Boolean) isRecordPop.get(i)).booleanValue();
            boolean isPostponed = record.isPostponed() && !record.interactsWith(records, i + 1, endIndex);
            if (isPostponed) {
                if (this.mPostponedTransactions == null) {
                    this.mPostponedTransactions = new ArrayList();
                }
                StartEnterTransitionListener listener = new StartEnterTransitionListener(record, isPop);
                this.mPostponedTransactions.add(listener);
                record.setOnStartPostponedListener(listener);
                if (isPop) {
                    record.executeOps();
                } else {
                    record.executePopOps(false);
                }
                postponeIndex--;
                if (i != postponeIndex) {
                    records.remove(i);
                    records.add(postponeIndex, record);
                }
                addAddedFragments(added);
            }
        }
        return postponeIndex;
    }

    private void completeExecute(BackStackRecord record, boolean isPop, boolean runTransitions, boolean moveToState) {
        if (isPop) {
            record.executePopOps(moveToState);
        } else {
            record.executeOps();
        }
        ArrayList<BackStackRecord> records = new ArrayList(1);
        ArrayList<Boolean> isRecordPop = new ArrayList(1);
        records.add(record);
        isRecordPop.add(Boolean.valueOf(isPop));
        if (runTransitions) {
            FragmentTransition.startTransitions(this, records, isRecordPop, 0, 1, true);
        }
        if (moveToState) {
            moveToState(this.mCurState, true);
        }
        if (this.mActive != null) {
            int numActive = this.mActive.size();
            for (int i = 0; i < numActive; i++) {
                Fragment fragment = (Fragment) this.mActive.valueAt(i);
                if (fragment != null && fragment.mView != null && fragment.mIsNewlyAdded && record.interactsWith(fragment.mContainerId)) {
                    if (fragment.mPostponedAlpha > 0.0f) {
                        fragment.mView.setAlpha(fragment.mPostponedAlpha);
                    }
                    if (moveToState) {
                        fragment.mPostponedAlpha = 0.0f;
                    } else {
                        fragment.mPostponedAlpha = -1.0f;
                        fragment.mIsNewlyAdded = false;
                    }
                }
            }
        }
    }

    private Fragment findFragmentUnder(Fragment f) {
        ViewGroup container = f.mContainer;
        View view = f.mView;
        if (container != null) {
            if (view != null) {
                for (int i = this.mAdded.indexOf(f) - 1; i >= 0; i--) {
                    Fragment underFragment = (Fragment) this.mAdded.get(i);
                    if (underFragment.mContainer == container && underFragment.mView != null) {
                        return underFragment;
                    }
                }
                return null;
            }
        }
        return null;
    }

    private static void executeOps(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            BackStackRecord record = (BackStackRecord) records.get(i);
            boolean moveToState = true;
            if (((Boolean) isRecordPop.get(i)).booleanValue()) {
                record.bumpBackStackNesting(-1);
                if (i != endIndex - 1) {
                    moveToState = false;
                }
                record.executePopOps(moveToState);
            } else {
                record.bumpBackStackNesting(1);
                record.executeOps();
            }
        }
    }

    private void addAddedFragments(ArraySet<Fragment> added) {
        if (this.mCurState >= 1) {
            int state = Math.min(this.mCurState, 4);
            int numAdded = this.mAdded.size();
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < numAdded) {
                    Fragment fragment = (Fragment) this.mAdded.get(i2);
                    if (fragment.mState < state) {
                        moveToState(fragment, state, fragment.getNextAnim(), fragment.getNextTransition(), false);
                        if (!(fragment.mView == null || fragment.mHidden || !fragment.mIsNewlyAdded)) {
                            added.add(fragment);
                        }
                    }
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        }
    }

    private void forcePostponedTransactions() {
        if (this.mPostponedTransactions != null) {
            while (!this.mPostponedTransactions.isEmpty()) {
                ((StartEnterTransitionListener) this.mPostponedTransactions.remove(0)).completeTransaction();
            }
        }
    }

    private void endAnimatingAwayFragments() {
        int i = 0;
        int numFragments = this.mActive == null ? 0 : this.mActive.size();
        while (i < numFragments) {
            Fragment fragment = (Fragment) this.mActive.valueAt(i);
            if (fragment != null) {
                if (fragment.getAnimatingAway() != null) {
                    int stateAfterAnimating = fragment.getStateAfterAnimating();
                    View animatingAway = fragment.getAnimatingAway();
                    Animation animation = animatingAway.getAnimation();
                    if (animation != null) {
                        animation.cancel();
                        animatingAway.clearAnimation();
                    }
                    fragment.setAnimatingAway(null);
                    moveToState(fragment, stateAfterAnimating, 0, 0, false);
                } else if (fragment.getAnimator() != null) {
                    fragment.getAnimator().end();
                }
            }
            i++;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean generateOpsForPendingActions(java.util.ArrayList<android.support.v4.app.BackStackRecord> r5, java.util.ArrayList<java.lang.Boolean> r6) {
        /*
        r4 = this;
        r0 = 0;
        monitor-enter(r4);
        r1 = r4.mPendingActions;	 Catch:{ all -> 0x003d }
        r2 = 0;
        if (r1 == 0) goto L_0x003b;
    L_0x0007:
        r1 = r4.mPendingActions;	 Catch:{ all -> 0x003d }
        r1 = r1.size();	 Catch:{ all -> 0x003d }
        if (r1 != 0) goto L_0x0010;
    L_0x000f:
        goto L_0x003b;
    L_0x0010:
        r1 = r4.mPendingActions;	 Catch:{ all -> 0x003d }
        r1 = r1.size();	 Catch:{ all -> 0x003d }
    L_0x0017:
        if (r2 >= r1) goto L_0x0029;
    L_0x0019:
        r3 = r4.mPendingActions;	 Catch:{ all -> 0x003d }
        r3 = r3.get(r2);	 Catch:{ all -> 0x003d }
        r3 = (android.support.v4.app.FragmentManagerImpl.OpGenerator) r3;	 Catch:{ all -> 0x003d }
        r3 = r3.generateOps(r5, r6);	 Catch:{ all -> 0x003d }
        r0 = r0 | r3;
        r2 = r2 + 1;
        goto L_0x0017;
    L_0x0029:
        r2 = r4.mPendingActions;	 Catch:{ all -> 0x003d }
        r2.clear();	 Catch:{ all -> 0x003d }
        r2 = r4.mHost;	 Catch:{ all -> 0x003d }
        r2 = r2.getHandler();	 Catch:{ all -> 0x003d }
        r3 = r4.mExecCommit;	 Catch:{ all -> 0x003d }
        r2.removeCallbacks(r3);	 Catch:{ all -> 0x003d }
        monitor-exit(r4);	 Catch:{ all -> 0x003d }
        return r0;
    L_0x003b:
        monitor-exit(r4);	 Catch:{ all -> 0x003d }
        return r2;
    L_0x003d:
        r1 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x003d }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.generateOpsForPendingActions(java.util.ArrayList, java.util.ArrayList):boolean");
    }

    void doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            this.mHavePendingDeferredStart = false;
            startPendingDeferredFragments();
        }
    }

    void reportBackStackChanged() {
        if (this.mBackStackChangeListeners != null) {
            for (int i = 0; i < this.mBackStackChangeListeners.size(); i++) {
                ((OnBackStackChangedListener) this.mBackStackChangeListeners.get(i)).onBackStackChanged();
            }
        }
    }

    void addBackStackState(BackStackRecord state) {
        if (this.mBackStack == null) {
            this.mBackStack = new ArrayList();
        }
        this.mBackStack.add(state);
    }

    boolean popBackStackState(ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, String name, int id, int flags) {
        if (this.mBackStack == null) {
            return false;
        }
        int last;
        if (name == null && id < 0 && (flags & 1) == 0) {
            last = this.mBackStack.size() - 1;
            if (last < 0) {
                return false;
            }
            records.add(this.mBackStack.remove(last));
            isRecordPop.add(Boolean.valueOf(true));
        } else {
            last = -1;
            if (name != null || id >= 0) {
                BackStackRecord bss;
                last = this.mBackStack.size() - 1;
                while (last >= 0) {
                    bss = (BackStackRecord) this.mBackStack.get(last);
                    if (name == null || !name.equals(bss.getName())) {
                        if (id >= 0 && id == bss.mIndex) {
                            break;
                        }
                        last--;
                    } else {
                        break;
                    }
                }
                if (last < 0) {
                    return false;
                }
                if ((flags & 1) != 0) {
                    last--;
                    while (last >= 0) {
                        bss = (BackStackRecord) this.mBackStack.get(last);
                        if ((name == null || !name.equals(bss.getName())) && (id < 0 || id != bss.mIndex)) {
                            break;
                        }
                        last--;
                    }
                }
            }
            if (last == this.mBackStack.size() - 1) {
                return false;
            }
            for (int i = this.mBackStack.size() - 1; i > last; i--) {
                records.add(this.mBackStack.remove(i));
                isRecordPop.add(Boolean.valueOf(true));
            }
        }
        return true;
    }

    FragmentManagerNonConfig retainNonConfig() {
        setRetaining(this.mSavedNonConfig);
        return this.mSavedNonConfig;
    }

    private static void setRetaining(FragmentManagerNonConfig nonConfig) {
        if (nonConfig != null) {
            List<Fragment> fragments = nonConfig.getFragments();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    fragment.mRetaining = true;
                }
            }
            List<FragmentManagerNonConfig> children = nonConfig.getChildNonConfigs();
            if (children != null) {
                for (FragmentManagerNonConfig child : children) {
                    setRetaining(child);
                }
            }
        }
    }

    void saveNonConfig() {
        ArrayList<Fragment> fragments = null;
        ArrayList<FragmentManagerNonConfig> childFragments = null;
        ArrayList<ViewModelStore> viewModelStores = null;
        if (this.mActive != null) {
            ArrayList<ViewModelStore> viewModelStores2 = null;
            ArrayList<FragmentManagerNonConfig> childFragments2 = null;
            ArrayList<Fragment> fragments2 = null;
            for (int i = 0; i < this.mActive.size(); i++) {
                Fragment f = (Fragment) this.mActive.valueAt(i);
                if (f != null) {
                    FragmentManagerNonConfig child;
                    int j;
                    if (f.mRetainInstance) {
                        if (fragments2 == null) {
                            fragments2 = new ArrayList();
                        }
                        fragments2.add(f);
                        f.mTargetIndex = f.mTarget != null ? f.mTarget.mIndex : -1;
                        if (DEBUG) {
                            String str = TAG;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("retainNonConfig: keeping retained ");
                            stringBuilder.append(f);
                            Log.v(str, stringBuilder.toString());
                        }
                    }
                    if (f.mChildFragmentManager != null) {
                        f.mChildFragmentManager.saveNonConfig();
                        child = f.mChildFragmentManager.mSavedNonConfig;
                    } else {
                        child = f.mChildNonConfig;
                    }
                    if (childFragments2 == null && child != null) {
                        childFragments2 = new ArrayList(this.mActive.size());
                        for (j = 0; j < i; j++) {
                            childFragments2.add(null);
                        }
                    }
                    if (childFragments2 != null) {
                        childFragments2.add(child);
                    }
                    if (viewModelStores2 == null && f.mViewModelStore != null) {
                        viewModelStores2 = new ArrayList(this.mActive.size());
                        for (j = 0; j < i; j++) {
                            viewModelStores2.add(null);
                        }
                    }
                    if (viewModelStores2 != null) {
                        viewModelStores2.add(f.mViewModelStore);
                    }
                }
            }
            fragments = fragments2;
            childFragments = childFragments2;
            viewModelStores = viewModelStores2;
        }
        if (fragments == null && childFragments == null && viewModelStores == null) {
            this.mSavedNonConfig = null;
        } else {
            this.mSavedNonConfig = new FragmentManagerNonConfig(fragments, childFragments, viewModelStores);
        }
    }

    void saveFragmentViewState(Fragment f) {
        if (f.mInnerView != null) {
            if (this.mStateArray == null) {
                this.mStateArray = new SparseArray();
            } else {
                this.mStateArray.clear();
            }
            f.mInnerView.saveHierarchyState(this.mStateArray);
            if (this.mStateArray.size() > 0) {
                f.mSavedViewState = this.mStateArray;
                this.mStateArray = null;
            }
        }
    }

    Bundle saveFragmentBasicState(Fragment f) {
        Bundle result = null;
        if (this.mStateBundle == null) {
            this.mStateBundle = new Bundle();
        }
        f.performSaveInstanceState(this.mStateBundle);
        dispatchOnFragmentSaveInstanceState(f, this.mStateBundle, false);
        if (!this.mStateBundle.isEmpty()) {
            result = this.mStateBundle;
            this.mStateBundle = null;
        }
        if (f.mView != null) {
            saveFragmentViewState(f);
        }
        if (f.mSavedViewState != null) {
            if (result == null) {
                result = new Bundle();
            }
            result.putSparseParcelableArray(VIEW_STATE_TAG, f.mSavedViewState);
        }
        if (!f.mUserVisibleHint) {
            if (result == null) {
                result = new Bundle();
            }
            result.putBoolean(USER_VISIBLE_HINT_TAG, f.mUserVisibleHint);
        }
        return result;
    }

    Parcelable saveAllState() {
        forcePostponedTransactions();
        endAnimatingAwayFragments();
        execPendingActions();
        this.mStateSaved = true;
        this.mSavedNonConfig = null;
        if (this.mActive != null) {
            if (this.mActive.size() > 0) {
                StringBuilder stringBuilder;
                int N = this.mActive.size();
                FragmentState[] active = new FragmentState[N];
                int i = 0;
                boolean haveFragments = false;
                for (int i2 = 0; i2 < N; i2++) {
                    Fragment f = (Fragment) this.mActive.valueAt(i2);
                    if (f != null) {
                        StringBuilder stringBuilder2;
                        if (f.mIndex < 0) {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("Failure saving state: active ");
                            stringBuilder.append(f);
                            stringBuilder.append(" has cleared index: ");
                            stringBuilder.append(f.mIndex);
                            throwException(new IllegalStateException(stringBuilder.toString()));
                        }
                        haveFragments = true;
                        FragmentState fs = new FragmentState(f);
                        active[i2] = fs;
                        if (f.mState <= 0 || fs.mSavedFragmentState != null) {
                            fs.mSavedFragmentState = f.mSavedFragmentState;
                        } else {
                            fs.mSavedFragmentState = saveFragmentBasicState(f);
                            if (f.mTarget != null) {
                                if (f.mTarget.mIndex < 0) {
                                    stringBuilder2 = new StringBuilder();
                                    stringBuilder2.append("Failure saving state: ");
                                    stringBuilder2.append(f);
                                    stringBuilder2.append(" has target not in fragment manager: ");
                                    stringBuilder2.append(f.mTarget);
                                    throwException(new IllegalStateException(stringBuilder2.toString()));
                                }
                                if (fs.mSavedFragmentState == null) {
                                    fs.mSavedFragmentState = new Bundle();
                                }
                                putFragment(fs.mSavedFragmentState, TARGET_STATE_TAG, f.mTarget);
                                if (f.mTargetRequestCode != 0) {
                                    fs.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, f.mTargetRequestCode);
                                }
                            }
                        }
                        if (DEBUG) {
                            String str = TAG;
                            stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("Saved state of ");
                            stringBuilder2.append(f);
                            stringBuilder2.append(": ");
                            stringBuilder2.append(fs.mSavedFragmentState);
                            Log.v(str, stringBuilder2.toString());
                        }
                    }
                }
                if (haveFragments) {
                    int[] added = null;
                    BackStackState[] backStack = null;
                    N = this.mAdded.size();
                    if (N > 0) {
                        added = new int[N];
                        for (int i3 = 0; i3 < N; i3++) {
                            added[i3] = ((Fragment) this.mAdded.get(i3)).mIndex;
                            if (added[i3] < 0) {
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("Failure saving state: active ");
                                stringBuilder.append(this.mAdded.get(i3));
                                stringBuilder.append(" has cleared index: ");
                                stringBuilder.append(added[i3]);
                                throwException(new IllegalStateException(stringBuilder.toString()));
                            }
                            if (DEBUG) {
                                String str2 = TAG;
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("saveAllState: adding fragment #");
                                stringBuilder.append(i3);
                                stringBuilder.append(": ");
                                stringBuilder.append(this.mAdded.get(i3));
                                Log.v(str2, stringBuilder.toString());
                            }
                        }
                    }
                    if (this.mBackStack != null) {
                        N = this.mBackStack.size();
                        if (N > 0) {
                            backStack = new BackStackState[N];
                            while (i < N) {
                                backStack[i] = new BackStackState((BackStackRecord) this.mBackStack.get(i));
                                if (DEBUG) {
                                    String str3 = TAG;
                                    StringBuilder stringBuilder3 = new StringBuilder();
                                    stringBuilder3.append("saveAllState: adding back stack #");
                                    stringBuilder3.append(i);
                                    stringBuilder3.append(": ");
                                    stringBuilder3.append(this.mBackStack.get(i));
                                    Log.v(str3, stringBuilder3.toString());
                                }
                                i++;
                            }
                        }
                    }
                    FragmentManagerState fms = new FragmentManagerState();
                    fms.mActive = active;
                    fms.mAdded = added;
                    fms.mBackStack = backStack;
                    if (this.mPrimaryNav != null) {
                        fms.mPrimaryNavActiveIndex = this.mPrimaryNav.mIndex;
                    }
                    fms.mNextFragmentIndex = this.mNextFragmentIndex;
                    saveNonConfig();
                    return fms;
                }
                if (DEBUG) {
                    Log.v(TAG, "saveAllState: no fragments!");
                }
                return null;
            }
        }
        return null;
    }

    void restoreAllState(Parcelable state, FragmentManagerNonConfig nonConfig) {
        FragmentManagerImpl fragmentManagerImpl = this;
        if (state != null) {
            FragmentManagerState fms = (FragmentManagerState) state;
            if (fms.mActive != null) {
                int count;
                int i;
                Fragment f;
                String str;
                StringBuilder stringBuilder;
                Fragment f2;
                List<FragmentManagerNonConfig> childNonConfigs = null;
                List<ViewModelStore> viewModelStores = null;
                if (nonConfig != null) {
                    List<Fragment> nonConfigFragments = nonConfig.getFragments();
                    childNonConfigs = nonConfig.getChildNonConfigs();
                    viewModelStores = nonConfig.getViewModelStores();
                    count = nonConfigFragments != null ? nonConfigFragments.size() : 0;
                    for (i = 0; i < count; i++) {
                        f = (Fragment) nonConfigFragments.get(i);
                        if (DEBUG) {
                            str = TAG;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("restoreAllState: re-attaching retained ");
                            stringBuilder.append(f);
                            Log.v(str, stringBuilder.toString());
                        }
                        int index = 0;
                        while (index < fms.mActive.length && fms.mActive[index].mIndex != f.mIndex) {
                            index++;
                        }
                        if (index == fms.mActive.length) {
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("Could not find active fragment with index ");
                            stringBuilder2.append(f.mIndex);
                            throwException(new IllegalStateException(stringBuilder2.toString()));
                        }
                        FragmentState fs = fms.mActive[index];
                        fs.mInstance = f;
                        f.mSavedViewState = null;
                        f.mBackStackNesting = 0;
                        f.mInLayout = false;
                        f.mAdded = false;
                        f.mTarget = null;
                        if (fs.mSavedFragmentState != null) {
                            fs.mSavedFragmentState.setClassLoader(fragmentManagerImpl.mHost.getContext().getClassLoader());
                            f.mSavedViewState = fs.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
                            f.mSavedFragmentState = fs.mSavedFragmentState;
                        }
                    }
                }
                List<ViewModelStore> viewModelStores2 = viewModelStores;
                List<FragmentManagerNonConfig> childNonConfigs2 = childNonConfigs;
                fragmentManagerImpl.mActive = new SparseArray(fms.mActive.length);
                int i2 = 0;
                while (i2 < fms.mActive.length) {
                    FragmentState fs2 = fms.mActive[i2];
                    if (fs2 != null) {
                        FragmentManagerNonConfig childNonConfig = null;
                        if (childNonConfigs2 != null && i2 < childNonConfigs2.size()) {
                            childNonConfig = (FragmentManagerNonConfig) childNonConfigs2.get(i2);
                        }
                        FragmentManagerNonConfig childNonConfig2 = childNonConfig;
                        ViewModelStore viewModelStore = null;
                        if (viewModelStores2 != null && i2 < viewModelStores2.size()) {
                            viewModelStore = (ViewModelStore) viewModelStores2.get(i2);
                        }
                        f2 = fs2.instantiate(fragmentManagerImpl.mHost, fragmentManagerImpl.mContainer, fragmentManagerImpl.mParent, childNonConfig2, viewModelStore);
                        if (DEBUG) {
                            String str2 = TAG;
                            StringBuilder stringBuilder3 = new StringBuilder();
                            stringBuilder3.append("restoreAllState: active #");
                            stringBuilder3.append(i2);
                            stringBuilder3.append(": ");
                            stringBuilder3.append(f2);
                            Log.v(str2, stringBuilder3.toString());
                        }
                        fragmentManagerImpl.mActive.put(f2.mIndex, f2);
                        fs2.mInstance = null;
                    }
                    i2++;
                }
                if (nonConfig != null) {
                    List<Fragment> nonConfigFragments2 = nonConfig.getFragments();
                    count = nonConfigFragments2 != null ? nonConfigFragments2.size() : 0;
                    for (i = 0; i < count; i++) {
                        f = (Fragment) nonConfigFragments2.get(i);
                        if (f.mTargetIndex >= 0) {
                            f.mTarget = (Fragment) fragmentManagerImpl.mActive.get(f.mTargetIndex);
                            if (f.mTarget == null) {
                                str = TAG;
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("Re-attaching retained fragment ");
                                stringBuilder.append(f);
                                stringBuilder.append(" target no longer exists: ");
                                stringBuilder.append(f.mTargetIndex);
                                Log.w(str, stringBuilder.toString());
                            }
                        }
                    }
                }
                fragmentManagerImpl.mAdded.clear();
                if (fms.mAdded != null) {
                    i2 = 0;
                    while (true) {
                        count = i2;
                        if (count >= fms.mAdded.length) {
                            break;
                        }
                        StringBuilder stringBuilder4;
                        f2 = (Fragment) fragmentManagerImpl.mActive.get(fms.mAdded[count]);
                        if (f2 == null) {
                            stringBuilder4 = new StringBuilder();
                            stringBuilder4.append("No instantiated fragment for index #");
                            stringBuilder4.append(fms.mAdded[count]);
                            throwException(new IllegalStateException(stringBuilder4.toString()));
                        }
                        f2.mAdded = true;
                        if (DEBUG) {
                            String str3 = TAG;
                            stringBuilder4 = new StringBuilder();
                            stringBuilder4.append("restoreAllState: added #");
                            stringBuilder4.append(count);
                            stringBuilder4.append(": ");
                            stringBuilder4.append(f2);
                            Log.v(str3, stringBuilder4.toString());
                        }
                        if (fragmentManagerImpl.mAdded.contains(f2)) {
                            throw new IllegalStateException("Already added!");
                        }
                        synchronized (fragmentManagerImpl.mAdded) {
                            fragmentManagerImpl.mAdded.add(f2);
                        }
                        i2 = count + 1;
                    }
                }
                if (fms.mBackStack != null) {
                    fragmentManagerImpl.mBackStack = new ArrayList(fms.mBackStack.length);
                    for (i2 = 0; i2 < fms.mBackStack.length; i2++) {
                        BackStackRecord bse = fms.mBackStack[i2].instantiate(fragmentManagerImpl);
                        if (DEBUG) {
                            String str4 = TAG;
                            StringBuilder stringBuilder5 = new StringBuilder();
                            stringBuilder5.append("restoreAllState: back stack #");
                            stringBuilder5.append(i2);
                            stringBuilder5.append(" (index ");
                            stringBuilder5.append(bse.mIndex);
                            stringBuilder5.append("): ");
                            stringBuilder5.append(bse);
                            Log.v(str4, stringBuilder5.toString());
                            PrintWriter pw = new PrintWriter(new LogWriter(TAG));
                            bse.dump("  ", pw, false);
                            pw.close();
                        }
                        fragmentManagerImpl.mBackStack.add(bse);
                        if (bse.mIndex >= 0) {
                            setBackStackIndex(bse.mIndex, bse);
                        }
                    }
                } else {
                    fragmentManagerImpl.mBackStack = null;
                }
                if (fms.mPrimaryNavActiveIndex >= 0) {
                    fragmentManagerImpl.mPrimaryNav = (Fragment) fragmentManagerImpl.mActive.get(fms.mPrimaryNavActiveIndex);
                }
                fragmentManagerImpl.mNextFragmentIndex = fms.mNextFragmentIndex;
            }
        }
    }

    private void burpActive() {
        if (this.mActive != null) {
            for (int i = this.mActive.size() - 1; i >= 0; i--) {
                if (this.mActive.valueAt(i) == null) {
                    this.mActive.delete(this.mActive.keyAt(i));
                }
            }
        }
    }

    public void attachController(FragmentHostCallback host, FragmentContainer container, Fragment parent) {
        if (this.mHost == null) {
            this.mHost = host;
            this.mContainer = container;
            this.mParent = parent;
            return;
        }
        throw new IllegalStateException("Already attached");
    }

    public void noteStateNotSaved() {
        this.mSavedNonConfig = null;
        int i = 0;
        this.mStateSaved = false;
        this.mStopped = false;
        int addedCount = this.mAdded.size();
        while (i < addedCount) {
            Fragment fragment = (Fragment) this.mAdded.get(i);
            if (fragment != null) {
                fragment.noteStateNotSaved();
            }
            i++;
        }
    }

    public void dispatchCreate() {
        this.mStateSaved = false;
        this.mStopped = false;
        dispatchStateChange(1);
    }

    public void dispatchActivityCreated() {
        this.mStateSaved = false;
        this.mStopped = false;
        dispatchStateChange(2);
    }

    public void dispatchStart() {
        this.mStateSaved = false;
        this.mStopped = false;
        dispatchStateChange(4);
    }

    public void dispatchResume() {
        this.mStateSaved = false;
        this.mStopped = false;
        dispatchStateChange(5);
    }

    public void dispatchPause() {
        dispatchStateChange(4);
    }

    public void dispatchStop() {
        this.mStopped = true;
        dispatchStateChange(3);
    }

    public void dispatchReallyStop() {
        dispatchStateChange(2);
    }

    public void dispatchDestroyView() {
        dispatchStateChange(1);
    }

    public void dispatchDestroy() {
        this.mDestroyed = true;
        execPendingActions();
        dispatchStateChange(0);
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
    }

    private void dispatchStateChange(int nextState) {
        try {
            this.mExecutingActions = true;
            moveToState(nextState, false);
            execPendingActions();
        } finally {
            this.mExecutingActions = false;
        }
    }

    public void dispatchMultiWindowModeChanged(boolean isInMultiWindowMode) {
        for (int i = this.mAdded.size() - 1; i >= 0; i--) {
            Fragment f = (Fragment) this.mAdded.get(i);
            if (f != null) {
                f.performMultiWindowModeChanged(isInMultiWindowMode);
            }
        }
    }

    public void dispatchPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        for (int i = this.mAdded.size() - 1; i >= 0; i--) {
            Fragment f = (Fragment) this.mAdded.get(i);
            if (f != null) {
                f.performPictureInPictureModeChanged(isInPictureInPictureMode);
            }
        }
    }

    public void dispatchConfigurationChanged(Configuration newConfig) {
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment f = (Fragment) this.mAdded.get(i);
            if (f != null) {
                f.performConfigurationChanged(newConfig);
            }
        }
    }

    public void dispatchLowMemory() {
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment f = (Fragment) this.mAdded.get(i);
            if (f != null) {
                f.performLowMemory();
            }
        }
    }

    public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        int i = 0;
        if (this.mCurState < 1) {
            return false;
        }
        int i2;
        ArrayList<Fragment> newMenus = null;
        boolean show = false;
        for (i2 = 0; i2 < this.mAdded.size(); i2++) {
            Fragment f = (Fragment) this.mAdded.get(i2);
            if (f != null && f.performCreateOptionsMenu(menu, inflater)) {
                show = true;
                if (newMenus == null) {
                    newMenus = new ArrayList();
                }
                newMenus.add(f);
            }
        }
        if (this.mCreatedMenus != null) {
            while (true) {
                i2 = i;
                if (i2 >= this.mCreatedMenus.size()) {
                    break;
                }
                Fragment f2 = (Fragment) this.mCreatedMenus.get(i2);
                if (newMenus == null || !newMenus.contains(f2)) {
                    f2.onDestroyOptionsMenu();
                }
                i = i2 + 1;
            }
        }
        this.mCreatedMenus = newMenus;
        return show;
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu) {
        int i = 0;
        if (this.mCurState < 1) {
            return false;
        }
        boolean show = false;
        while (i < this.mAdded.size()) {
            Fragment f = (Fragment) this.mAdded.get(i);
            if (f != null && f.performPrepareOptionsMenu(menu)) {
                show = true;
            }
            i++;
        }
        return show;
    }

    public boolean dispatchOptionsItemSelected(MenuItem item) {
        if (this.mCurState < 1) {
            return false;
        }
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment f = (Fragment) this.mAdded.get(i);
            if (f != null && f.performOptionsItemSelected(item)) {
                return true;
            }
        }
        return false;
    }

    public boolean dispatchContextItemSelected(MenuItem item) {
        if (this.mCurState < 1) {
            return false;
        }
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment f = (Fragment) this.mAdded.get(i);
            if (f != null && f.performContextItemSelected(item)) {
                return true;
            }
        }
        return false;
    }

    public void dispatchOptionsMenuClosed(Menu menu) {
        if (this.mCurState >= 1) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null) {
                    f.performOptionsMenuClosed(menu);
                }
            }
        }
    }

    public void setPrimaryNavigationFragment(Fragment f) {
        if (f != null) {
            if (this.mActive.get(f.mIndex) == f) {
                if (f.mHost != null) {
                    if (f.getFragmentManager() == this) {
                    }
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(f);
            stringBuilder.append(" is not an active fragment of FragmentManager ");
            stringBuilder.append(this);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.mPrimaryNav = f;
    }

    public Fragment getPrimaryNavigationFragment() {
        return this.mPrimaryNav;
    }

    public void registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks cb, boolean recursive) {
        this.mLifecycleCallbacks.add(new Pair(cb, Boolean.valueOf(recursive)));
    }

    public void unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks cb) {
        synchronized (this.mLifecycleCallbacks) {
            int N = this.mLifecycleCallbacks.size();
            for (int i = 0; i < N; i++) {
                if (((Pair) this.mLifecycleCallbacks.get(i)).first == cb) {
                    this.mLifecycleCallbacks.remove(i);
                    break;
                }
            }
        }
    }

    void dispatchOnFragmentPreAttached(Fragment f, Context context, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentPreAttached(f, context, true);
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentLifecycleCallbacks, Boolean> p = (Pair) it.next();
            if (!onlyRecursive || ((Boolean) p.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) p.first).onFragmentPreAttached(this, f, context);
            }
        }
    }

    void dispatchOnFragmentAttached(Fragment f, Context context, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentAttached(f, context, true);
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentLifecycleCallbacks, Boolean> p = (Pair) it.next();
            if (!onlyRecursive || ((Boolean) p.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) p.first).onFragmentAttached(this, f, context);
            }
        }
    }

    void dispatchOnFragmentPreCreated(Fragment f, Bundle savedInstanceState, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentPreCreated(f, savedInstanceState, true);
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentLifecycleCallbacks, Boolean> p = (Pair) it.next();
            if (!onlyRecursive || ((Boolean) p.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) p.first).onFragmentPreCreated(this, f, savedInstanceState);
            }
        }
    }

    void dispatchOnFragmentCreated(Fragment f, Bundle savedInstanceState, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentCreated(f, savedInstanceState, true);
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentLifecycleCallbacks, Boolean> p = (Pair) it.next();
            if (!onlyRecursive || ((Boolean) p.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) p.first).onFragmentCreated(this, f, savedInstanceState);
            }
        }
    }

    void dispatchOnFragmentActivityCreated(Fragment f, Bundle savedInstanceState, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentActivityCreated(f, savedInstanceState, true);
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentLifecycleCallbacks, Boolean> p = (Pair) it.next();
            if (!onlyRecursive || ((Boolean) p.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) p.first).onFragmentActivityCreated(this, f, savedInstanceState);
            }
        }
    }

    void dispatchOnFragmentViewCreated(Fragment f, View v, Bundle savedInstanceState, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentViewCreated(f, v, savedInstanceState, true);
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentLifecycleCallbacks, Boolean> p = (Pair) it.next();
            if (!onlyRecursive || ((Boolean) p.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) p.first).onFragmentViewCreated(this, f, v, savedInstanceState);
            }
        }
    }

    void dispatchOnFragmentStarted(Fragment f, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentStarted(f, true);
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentLifecycleCallbacks, Boolean> p = (Pair) it.next();
            if (!onlyRecursive || ((Boolean) p.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) p.first).onFragmentStarted(this, f);
            }
        }
    }

    void dispatchOnFragmentResumed(Fragment f, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentResumed(f, true);
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentLifecycleCallbacks, Boolean> p = (Pair) it.next();
            if (!onlyRecursive || ((Boolean) p.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) p.first).onFragmentResumed(this, f);
            }
        }
    }

    void dispatchOnFragmentPaused(Fragment f, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentPaused(f, true);
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentLifecycleCallbacks, Boolean> p = (Pair) it.next();
            if (!onlyRecursive || ((Boolean) p.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) p.first).onFragmentPaused(this, f);
            }
        }
    }

    void dispatchOnFragmentStopped(Fragment f, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentStopped(f, true);
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentLifecycleCallbacks, Boolean> p = (Pair) it.next();
            if (!onlyRecursive || ((Boolean) p.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) p.first).onFragmentStopped(this, f);
            }
        }
    }

    void dispatchOnFragmentSaveInstanceState(Fragment f, Bundle outState, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentSaveInstanceState(f, outState, true);
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentLifecycleCallbacks, Boolean> p = (Pair) it.next();
            if (!onlyRecursive || ((Boolean) p.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) p.first).onFragmentSaveInstanceState(this, f, outState);
            }
        }
    }

    void dispatchOnFragmentViewDestroyed(Fragment f, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentViewDestroyed(f, true);
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentLifecycleCallbacks, Boolean> p = (Pair) it.next();
            if (!onlyRecursive || ((Boolean) p.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) p.first).onFragmentViewDestroyed(this, f);
            }
        }
    }

    void dispatchOnFragmentDestroyed(Fragment f, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentDestroyed(f, true);
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentLifecycleCallbacks, Boolean> p = (Pair) it.next();
            if (!onlyRecursive || ((Boolean) p.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) p.first).onFragmentDestroyed(this, f);
            }
        }
    }

    void dispatchOnFragmentDetached(Fragment f, boolean onlyRecursive) {
        if (this.mParent != null) {
            FragmentManager parentManager = this.mParent.getFragmentManager();
            if (parentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentDetached(f, true);
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentLifecycleCallbacks, Boolean> p = (Pair) it.next();
            if (!onlyRecursive || ((Boolean) p.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) p.first).onFragmentDetached(this, f);
            }
        }
    }

    public static int reverseTransit(int transit) {
        if (transit == FragmentTransaction.TRANSIT_FRAGMENT_OPEN) {
            return 8194;
        }
        if (transit == FragmentTransaction.TRANSIT_FRAGMENT_FADE) {
            return FragmentTransaction.TRANSIT_FRAGMENT_FADE;
        }
        if (transit != 8194) {
            return 0;
        }
        return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
    }

    public static int transitToStyleIndex(int transit, boolean enter) {
        if (transit == FragmentTransaction.TRANSIT_FRAGMENT_OPEN) {
            return enter ? 1 : 2;
        } else if (transit == FragmentTransaction.TRANSIT_FRAGMENT_FADE) {
            return enter ? 5 : 6;
        } else if (transit != 8194) {
            return -1;
        } else {
            return enter ? 3 : 4;
        }
    }

    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        FragmentManagerImpl fragmentManagerImpl = this;
        Context context2 = context;
        AttributeSet attributeSet = attrs;
        if (!"fragment".equals(name)) {
            return null;
        }
        String fname = attributeSet.getAttributeValue(null, "class");
        TypedArray a = context2.obtainStyledAttributes(attributeSet, FragmentTag.Fragment);
        int i = 0;
        if (fname == null) {
            fname = a.getString(0);
        }
        String fname2 = fname;
        int id = a.getResourceId(1, -1);
        String tag = a.getString(2);
        a.recycle();
        if (!Fragment.isSupportFragmentClass(fragmentManagerImpl.mHost.getContext(), fname2)) {
            return null;
        }
        Fragment fragment;
        Fragment fragment2;
        if (parent != null) {
            i = parent.getId();
        }
        int containerId = i;
        if (containerId == -1 && id == -1) {
            if (tag == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(attrs.getPositionDescription());
                stringBuilder.append(": Must specify unique android:id, android:tag, or have a parent with an id for ");
                stringBuilder.append(fname2);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        Fragment fragment3 = id != -1 ? findFragmentById(id) : null;
        if (fragment3 == null && tag != null) {
            fragment3 = findFragmentByTag(tag);
        }
        if (fragment3 == null && containerId != -1) {
            fragment3 = findFragmentById(containerId);
        }
        if (DEBUG) {
            fname = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("onCreateView: id=0x");
            stringBuilder2.append(Integer.toHexString(id));
            stringBuilder2.append(" fname=");
            stringBuilder2.append(fname2);
            stringBuilder2.append(" existing=");
            stringBuilder2.append(fragment3);
            Log.v(fname, stringBuilder2.toString());
        }
        if (fragment3 == null) {
            Fragment fragment4 = fragmentManagerImpl.mContainer.instantiate(context2, fname2, null);
            fragment4.mFromLayout = true;
            fragment4.mFragmentId = id != 0 ? id : containerId;
            fragment4.mContainerId = containerId;
            fragment4.mTag = tag;
            fragment4.mInLayout = true;
            fragment4.mFragmentManager = fragmentManagerImpl;
            fragment4.mHost = fragmentManagerImpl.mHost;
            fragment4.onInflate(fragmentManagerImpl.mHost.getContext(), attributeSet, fragment4.mSavedFragmentState);
            addFragment(fragment4, true);
            fragment = fragment4;
        } else if (fragment3.mInLayout) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(attrs.getPositionDescription());
            stringBuilder.append(": Duplicate id 0x");
            stringBuilder.append(Integer.toHexString(id));
            stringBuilder.append(", tag ");
            stringBuilder.append(tag);
            stringBuilder.append(", or parent id 0x");
            stringBuilder.append(Integer.toHexString(containerId));
            stringBuilder.append(" with another fragment for ");
            stringBuilder.append(fname2);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else {
            fragment3.mInLayout = true;
            fragment3.mHost = fragmentManagerImpl.mHost;
            if (!fragment3.mRetaining) {
                fragment3.onInflate(fragmentManagerImpl.mHost.getContext(), attributeSet, fragment3.mSavedFragmentState);
            }
            fragment = fragment3;
        }
        if (fragmentManagerImpl.mCurState >= 1 || !fragment.mFromLayout) {
            fragment2 = fragment;
            moveToState(fragment2);
        } else {
            fragment2 = fragment;
            moveToState(fragment, 1, 0, 0, false);
        }
        if (fragment2.mView != null) {
            if (id != 0) {
                fragment2.mView.setId(id);
            }
            if (fragment2.mView.getTag() == null) {
                fragment2.mView.setTag(tag);
            }
            return fragment2.mView;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment ");
        stringBuilder.append(fname2);
        stringBuilder.append(" did not create a view.");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return onCreateView(null, name, context, attrs);
    }

    Factory2 getLayoutInflaterFactory() {
        return this;
    }
}
