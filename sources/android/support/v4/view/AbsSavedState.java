package android.support.v4.view;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class AbsSavedState implements Parcelable {
    public static final Creator<AbsSavedState> CREATOR = new C02282();
    public static final AbsSavedState EMPTY_STATE = new C04771();
    private final Parcelable mSuperState;

    /* renamed from: android.support.v4.view.AbsSavedState$2 */
    static class C02282 implements ClassLoaderCreator<AbsSavedState> {
        C02282() {
        }

        public AbsSavedState createFromParcel(Parcel in, ClassLoader loader) {
            if (in.readParcelable(loader) == null) {
                return AbsSavedState.EMPTY_STATE;
            }
            throw new IllegalStateException("superState must be null");
        }

        public AbsSavedState createFromParcel(Parcel in) {
            return createFromParcel(in, null);
        }

        public AbsSavedState[] newArray(int size) {
            return new AbsSavedState[size];
        }
    }

    /* renamed from: android.support.v4.view.AbsSavedState$1 */
    static class C04771 extends AbsSavedState {
        C04771() {
            super();
        }
    }

    private AbsSavedState() {
        this.mSuperState = null;
    }

    protected AbsSavedState(@NonNull Parcelable superState) {
        if (superState != null) {
            this.mSuperState = superState != EMPTY_STATE ? superState : null;
            return;
        }
        throw new IllegalArgumentException("superState must not be null");
    }

    protected AbsSavedState(@NonNull Parcel source) {
        this(source, null);
    }

    protected AbsSavedState(@NonNull Parcel source, @Nullable ClassLoader loader) {
        Parcelable superState = source.readParcelable(loader);
        this.mSuperState = superState != null ? superState : EMPTY_STATE;
    }

    @Nullable
    public final Parcelable getSuperState() {
        return this.mSuperState;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mSuperState, flags);
    }
}
