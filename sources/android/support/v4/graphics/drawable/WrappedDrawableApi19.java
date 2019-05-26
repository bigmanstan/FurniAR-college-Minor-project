package android.support.v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

@RequiresApi(19)
class WrappedDrawableApi19 extends WrappedDrawableApi14 {

    private static class DrawableWrapperStateKitKat extends DrawableWrapperState {
        DrawableWrapperStateKitKat(@Nullable DrawableWrapperState orig, @Nullable Resources res) {
            super(orig, res);
        }

        @NonNull
        public Drawable newDrawable(@Nullable Resources res) {
            return new WrappedDrawableApi19(this, res);
        }
    }

    WrappedDrawableApi19(Drawable drawable) {
        super(drawable);
    }

    WrappedDrawableApi19(DrawableWrapperState state, Resources resources) {
        super(state, resources);
    }

    public void setAutoMirrored(boolean mirrored) {
        this.mDrawable.setAutoMirrored(mirrored);
    }

    public boolean isAutoMirrored() {
        return this.mDrawable.isAutoMirrored();
    }

    @NonNull
    DrawableWrapperState mutateConstantState() {
        return new DrawableWrapperStateKitKat(this.mState, null);
    }
}
