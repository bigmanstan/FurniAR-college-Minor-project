package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

public class ImageViewCompat {
    static final ImageViewCompatImpl IMPL;

    interface ImageViewCompatImpl {
        ColorStateList getImageTintList(ImageView imageView);

        Mode getImageTintMode(ImageView imageView);

        void setImageTintList(ImageView imageView, ColorStateList colorStateList);

        void setImageTintMode(ImageView imageView, Mode mode);
    }

    static class BaseViewCompatImpl implements ImageViewCompatImpl {
        BaseViewCompatImpl() {
        }

        public ColorStateList getImageTintList(ImageView view) {
            return view instanceof TintableImageSourceView ? ((TintableImageSourceView) view).getSupportImageTintList() : null;
        }

        public void setImageTintList(ImageView view, ColorStateList tintList) {
            if (view instanceof TintableImageSourceView) {
                ((TintableImageSourceView) view).setSupportImageTintList(tintList);
            }
        }

        public void setImageTintMode(ImageView view, Mode mode) {
            if (view instanceof TintableImageSourceView) {
                ((TintableImageSourceView) view).setSupportImageTintMode(mode);
            }
        }

        public Mode getImageTintMode(ImageView view) {
            return view instanceof TintableImageSourceView ? ((TintableImageSourceView) view).getSupportImageTintMode() : null;
        }
    }

    @RequiresApi(21)
    static class LollipopViewCompatImpl extends BaseViewCompatImpl {
        LollipopViewCompatImpl() {
        }

        public ColorStateList getImageTintList(ImageView view) {
            return view.getImageTintList();
        }

        public void setImageTintList(ImageView view, ColorStateList tintList) {
            view.setImageTintList(tintList);
            if (VERSION.SDK_INT == 21) {
                Drawable imageViewDrawable = view.getDrawable();
                boolean hasTint = (view.getImageTintList() == null || view.getImageTintMode() == null) ? false : true;
                if (imageViewDrawable != null && hasTint) {
                    if (imageViewDrawable.isStateful()) {
                        imageViewDrawable.setState(view.getDrawableState());
                    }
                    view.setImageDrawable(imageViewDrawable);
                }
            }
        }

        public void setImageTintMode(ImageView view, Mode mode) {
            view.setImageTintMode(mode);
            if (VERSION.SDK_INT == 21) {
                Drawable imageViewDrawable = view.getDrawable();
                boolean hasTint = (view.getImageTintList() == null || view.getImageTintMode() == null) ? false : true;
                if (imageViewDrawable != null && hasTint) {
                    if (imageViewDrawable.isStateful()) {
                        imageViewDrawable.setState(view.getDrawableState());
                    }
                    view.setImageDrawable(imageViewDrawable);
                }
            }
        }

        public Mode getImageTintMode(ImageView view) {
            return view.getImageTintMode();
        }
    }

    static {
        if (VERSION.SDK_INT >= 21) {
            IMPL = new LollipopViewCompatImpl();
        } else {
            IMPL = new BaseViewCompatImpl();
        }
    }

    @Nullable
    public static ColorStateList getImageTintList(@NonNull ImageView view) {
        return IMPL.getImageTintList(view);
    }

    public static void setImageTintList(@NonNull ImageView view, @Nullable ColorStateList tintList) {
        IMPL.setImageTintList(view, tintList);
    }

    @Nullable
    public static Mode getImageTintMode(@NonNull ImageView view) {
        return IMPL.getImageTintMode(view);
    }

    public static void setImageTintMode(@NonNull ImageView view, @Nullable Mode mode) {
        IMPL.setImageTintMode(view, mode);
    }

    private ImageViewCompat() {
    }
}
