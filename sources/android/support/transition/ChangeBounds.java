package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import java.util.Map;

public class ChangeBounds extends Transition {
    private static final Property<View, PointF> BOTTOM_RIGHT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class, "bottomRight") {
        public void set(View view, PointF bottomRight) {
            ViewUtils.setLeftTopRightBottom(view, view.getLeft(), view.getTop(), Math.round(bottomRight.x), Math.round(bottomRight.y));
        }

        public PointF get(View view) {
            return null;
        }
    };
    private static final Property<ViewBounds, PointF> BOTTOM_RIGHT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class, "bottomRight") {
        public void set(ViewBounds viewBounds, PointF bottomRight) {
            viewBounds.setBottomRight(bottomRight);
        }

        public PointF get(ViewBounds viewBounds) {
            return null;
        }
    };
    private static final Property<Drawable, PointF> DRAWABLE_ORIGIN_PROPERTY = new Property<Drawable, PointF>(PointF.class, "boundsOrigin") {
        private Rect mBounds = new Rect();

        public void set(Drawable object, PointF value) {
            object.copyBounds(this.mBounds);
            this.mBounds.offsetTo(Math.round(value.x), Math.round(value.y));
            object.setBounds(this.mBounds);
        }

        public PointF get(Drawable object) {
            object.copyBounds(this.mBounds);
            return new PointF((float) this.mBounds.left, (float) this.mBounds.top);
        }
    };
    private static final Property<View, PointF> POSITION_PROPERTY = new Property<View, PointF>(PointF.class, "position") {
        public void set(View view, PointF topLeft) {
            int left = Math.round(topLeft.x);
            int top = Math.round(topLeft.y);
            ViewUtils.setLeftTopRightBottom(view, left, top, view.getWidth() + left, view.getHeight() + top);
        }

        public PointF get(View view) {
            return null;
        }
    };
    private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";
    private static final String PROPNAME_CLIP = "android:changeBounds:clip";
    private static final String PROPNAME_PARENT = "android:changeBounds:parent";
    private static final String PROPNAME_WINDOW_X = "android:changeBounds:windowX";
    private static final String PROPNAME_WINDOW_Y = "android:changeBounds:windowY";
    private static final Property<View, PointF> TOP_LEFT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class, "topLeft") {
        public void set(View view, PointF topLeft) {
            ViewUtils.setLeftTopRightBottom(view, Math.round(topLeft.x), Math.round(topLeft.y), view.getRight(), view.getBottom());
        }

        public PointF get(View view) {
            return null;
        }
    };
    private static final Property<ViewBounds, PointF> TOP_LEFT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class, "topLeft") {
        public void set(ViewBounds viewBounds, PointF topLeft) {
            viewBounds.setTopLeft(topLeft);
        }

        public PointF get(ViewBounds viewBounds) {
            return null;
        }
    };
    private static RectEvaluator sRectEvaluator = new RectEvaluator();
    private static final String[] sTransitionProperties = new String[]{PROPNAME_BOUNDS, PROPNAME_CLIP, PROPNAME_PARENT, PROPNAME_WINDOW_X, PROPNAME_WINDOW_Y};
    private boolean mReparent;
    private boolean mResizeClip;
    private int[] mTempLocation;

    private static class ViewBounds {
        private int mBottom;
        private int mBottomRightCalls;
        private int mLeft;
        private int mRight;
        private int mTop;
        private int mTopLeftCalls;
        private View mView;

        ViewBounds(View view) {
            this.mView = view;
        }

        void setTopLeft(PointF topLeft) {
            this.mLeft = Math.round(topLeft.x);
            this.mTop = Math.round(topLeft.y);
            this.mTopLeftCalls++;
            if (this.mTopLeftCalls == this.mBottomRightCalls) {
                setLeftTopRightBottom();
            }
        }

        void setBottomRight(PointF bottomRight) {
            this.mRight = Math.round(bottomRight.x);
            this.mBottom = Math.round(bottomRight.y);
            this.mBottomRightCalls++;
            if (this.mTopLeftCalls == this.mBottomRightCalls) {
                setLeftTopRightBottom();
            }
        }

        private void setLeftTopRightBottom() {
            ViewUtils.setLeftTopRightBottom(this.mView, this.mLeft, this.mTop, this.mRight, this.mBottom);
            this.mTopLeftCalls = 0;
            this.mBottomRightCalls = 0;
        }
    }

    public ChangeBounds() {
        this.mTempLocation = new int[2];
        this.mResizeClip = false;
        this.mReparent = false;
    }

    public ChangeBounds(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTempLocation = new int[2];
        this.mResizeClip = false;
        this.mReparent = false;
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.CHANGE_BOUNDS);
        boolean resizeClip = TypedArrayUtils.getNamedBoolean(a, (XmlResourceParser) attrs, "resizeClip", 0, false);
        a.recycle();
        setResizeClip(resizeClip);
    }

    @Nullable
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public void setResizeClip(boolean resizeClip) {
        this.mResizeClip = resizeClip;
    }

    public boolean getResizeClip() {
        return this.mResizeClip;
    }

    private void captureValues(TransitionValues values) {
        View view = values.view;
        if (ViewCompat.isLaidOut(view) || view.getWidth() != 0 || view.getHeight() != 0) {
            values.values.put(PROPNAME_BOUNDS, new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            values.values.put(PROPNAME_PARENT, values.view.getParent());
            if (this.mReparent) {
                values.view.getLocationInWindow(this.mTempLocation);
                values.values.put(PROPNAME_WINDOW_X, Integer.valueOf(this.mTempLocation[0]));
                values.values.put(PROPNAME_WINDOW_Y, Integer.valueOf(this.mTempLocation[1]));
            }
            if (this.mResizeClip) {
                values.values.put(PROPNAME_CLIP, ViewCompat.getClipBounds(view));
            }
        }
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private boolean parentMatches(View startParent, View endParent) {
        if (!this.mReparent) {
            return true;
        }
        boolean z = true;
        TransitionValues endValues = getMatchedTransitionValues(startParent, true);
        if (endValues == null) {
            if (startParent != endParent) {
                z = false;
            }
            return z;
        }
        if (endParent != endValues.view) {
            z = false;
        }
        return z;
    }

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        ViewGroup viewGroup;
        TransitionValues endParentVals;
        ChangeBounds changeBounds = this;
        TransitionValues transitionValues = startValues;
        TransitionValues transitionValues2 = endValues;
        if (transitionValues != null) {
            if (transitionValues2 != null) {
                Map<String, Object> startParentVals = transitionValues.values;
                Map<String, Object> endParentVals2 = transitionValues2.values;
                ViewGroup startParent = (ViewGroup) startParentVals.get(PROPNAME_PARENT);
                ViewGroup endParent = (ViewGroup) endParentVals2.get(PROPNAME_PARENT);
                Map<String, Object> map;
                Map<String, Object> map2;
                ViewGroup viewGroup2;
                ViewGroup viewGroup3;
                if (startParent == null) {
                    viewGroup = sceneRoot;
                    map = startParentVals;
                    map2 = endParentVals2;
                    viewGroup2 = startParent;
                    viewGroup3 = endParent;
                    endParentVals = transitionValues2;
                } else if (endParent == null) {
                    viewGroup = sceneRoot;
                    map = startParentVals;
                    map2 = endParentVals2;
                    viewGroup2 = startParent;
                    viewGroup3 = endParent;
                    endParentVals = transitionValues2;
                } else {
                    View view = transitionValues2.view;
                    int endTop;
                    View view2;
                    int startWidth;
                    TransitionValues transitionValues3;
                    if (parentMatches(startParent, endParent)) {
                        Rect startBounds = (Rect) transitionValues.values.get(PROPNAME_BOUNDS);
                        Rect endBounds = (Rect) transitionValues2.values.get(PROPNAME_BOUNDS);
                        int startLeft = startBounds.left;
                        int endLeft = endBounds.left;
                        int startTop = startBounds.top;
                        endTop = endBounds.top;
                        int startRight = startBounds.right;
                        startParentVals = endBounds.right;
                        int startBottom = startBounds.bottom;
                        startParent = endBounds.bottom;
                        int startWidth2 = startRight - startLeft;
                        int startHeight = startBottom - startTop;
                        int endWidth = startParentVals - endLeft;
                        int endHeight = startParent - endTop;
                        View view3 = view;
                        Rect startClip = (Rect) transitionValues.values.get(PROPNAME_CLIP);
                        Rect endClip = (Rect) transitionValues2.values.get(PROPNAME_CLIP);
                        int numChanges = 0;
                        if (!((startWidth2 == 0 || startHeight == 0) && (endWidth == 0 || endHeight == 0))) {
                            if (!(startLeft == endLeft && startTop == endTop)) {
                                numChanges = 0 + 1;
                            }
                            if (!(startRight == startParentVals && startBottom == startParent)) {
                                numChanges++;
                            }
                        }
                        if (!(startClip == null || startClip.equals(endClip)) || (startClip == null && endClip != null)) {
                            numChanges++;
                        }
                        if (numChanges > 0) {
                            Animator anim;
                            Rect startClip2 = startClip;
                            Rect endClip2 = endClip;
                            int i;
                            int i2;
                            int i3;
                            int i4;
                            int i5;
                            int i6;
                            Map<String, Object> map3;
                            int i7;
                            Rect rect;
                            int i8;
                            int i9;
                            if (changeBounds.mResizeClip) {
                                int endLeft2;
                                ObjectAnimator positionAnimator;
                                int startTop2;
                                int startLeft2;
                                final Rect finalClip;
                                Rect startClip3;
                                Rect endClip3;
                                int endHeight2;
                                ObjectAnimator clipAnimator;
                                int endWidth2;
                                C01008 c01008;
                                C01008 c010082;
                                final View view4;
                                int i10;
                                int i11;
                                i = numChanges;
                                view2 = view3;
                                startWidth = startWidth2;
                                startWidth2 = Math.max(startWidth, endWidth);
                                int startRight2 = startRight;
                                i2 = startBottom;
                                ViewUtils.setLeftTopRightBottom(view2, startLeft, startTop, startLeft + startWidth2, startTop + Math.max(startHeight, endHeight));
                                if (startLeft == endLeft) {
                                    if (startTop == endTop) {
                                        endLeft2 = endLeft;
                                        positionAnimator = null;
                                        startTop2 = startTop;
                                        startLeft2 = startLeft;
                                        i3 = startTop2;
                                        finalClip = endClip2;
                                        if (startClip2 != null) {
                                            startRight = 0;
                                            startClip3 = new Rect(0, 0, startWidth, startHeight);
                                        } else {
                                            startRight = 0;
                                            startClip3 = startClip2;
                                        }
                                        if (endClip2 != null) {
                                            endClip3 = new Rect(startRight, startRight, endWidth, endHeight);
                                        } else {
                                            endClip3 = endClip2;
                                        }
                                        if (startClip3.equals(endClip3) != null) {
                                            ViewCompat.setClipBounds(view2, startClip3);
                                            endHeight2 = endHeight;
                                            clipAnimator = null;
                                            endWidth2 = endWidth;
                                            i4 = endHeight2;
                                            i5 = startWidth;
                                            c01008 = c010082;
                                            endClip2 = endClip3;
                                            i6 = startRight2;
                                            view4 = view2;
                                            startLeft = endLeft2;
                                            endParent = ObjectAnimator.ofObject(view2, "clipBounds", sRectEvaluator, new Object[]{startClip3, endClip3});
                                            endWidth = endTop;
                                            startHeight = startParentVals;
                                            map3 = startParentVals;
                                            startParentVals = 1;
                                            i7 = endTop;
                                            endTop = startParent;
                                            c010082 = new AnimatorListenerAdapter() {
                                                private boolean mIsCanceled;

                                                public void onAnimationCancel(Animator animation) {
                                                    this.mIsCanceled = true;
                                                }

                                                public void onAnimationEnd(Animator animation) {
                                                    if (!this.mIsCanceled) {
                                                        ViewCompat.setClipBounds(view4, finalClip);
                                                        ViewUtils.setLeftTopRightBottom(view4, startLeft, endWidth, startHeight, endTop);
                                                    }
                                                }
                                            };
                                            endParent.addListener(c01008);
                                        } else {
                                            endClip2 = endClip3;
                                            rect = startClip3;
                                            i8 = endWidth;
                                            i9 = startHeight;
                                            i7 = endTop;
                                            i5 = startWidth;
                                            map3 = startParentVals;
                                            i10 = startWidth2;
                                            i6 = startRight2;
                                            i11 = startLeft2;
                                            view3 = endLeft2;
                                            startParentVals = true;
                                            endParent = null;
                                        }
                                        anim = TransitionUtils.mergeAnimators(positionAnimator, endParent);
                                    }
                                }
                                ObjectAnimator positionAnimator2 = null;
                                startLeft2 = startLeft;
                                startTop2 = startTop;
                                endLeft2 = endLeft;
                                positionAnimator = ObjectAnimatorUtils.ofPointF(view2, POSITION_PROPERTY, getPathMotion().getPath((float) startLeft, (float) startTop, (float) endLeft, (float) endTop));
                                i3 = startTop2;
                                finalClip = endClip2;
                                if (startClip2 != null) {
                                    startRight = 0;
                                    startClip3 = startClip2;
                                } else {
                                    startRight = 0;
                                    startClip3 = new Rect(0, 0, startWidth, startHeight);
                                }
                                if (endClip2 != null) {
                                    endClip3 = endClip2;
                                } else {
                                    endClip3 = new Rect(startRight, startRight, endWidth, endHeight);
                                }
                                if (startClip3.equals(endClip3) != null) {
                                    endClip2 = endClip3;
                                    rect = startClip3;
                                    i8 = endWidth;
                                    i9 = startHeight;
                                    i7 = endTop;
                                    i5 = startWidth;
                                    map3 = startParentVals;
                                    i10 = startWidth2;
                                    i6 = startRight2;
                                    i11 = startLeft2;
                                    view3 = endLeft2;
                                    startParentVals = true;
                                    endParent = null;
                                } else {
                                    ViewCompat.setClipBounds(view2, startClip3);
                                    endHeight2 = endHeight;
                                    clipAnimator = null;
                                    endWidth2 = endWidth;
                                    i4 = endHeight2;
                                    i5 = startWidth;
                                    c01008 = c010082;
                                    endClip2 = endClip3;
                                    i6 = startRight2;
                                    view4 = view2;
                                    startLeft = endLeft2;
                                    endParent = ObjectAnimator.ofObject(view2, "clipBounds", sRectEvaluator, new Object[]{startClip3, endClip3});
                                    endWidth = endTop;
                                    startHeight = startParentVals;
                                    map3 = startParentVals;
                                    startParentVals = 1;
                                    i7 = endTop;
                                    endTop = startParent;
                                    c010082 = /* anonymous class already generated */;
                                    endParent.addListener(c01008);
                                }
                                anim = TransitionUtils.mergeAnimators(positionAnimator, endParent);
                            } else {
                                view2 = view3;
                                ViewUtils.setLeftTopRightBottom(view2, startLeft, startTop, startRight, startBottom);
                                View view5;
                                int i12;
                                int endRight;
                                if (numChanges != 2) {
                                    rect = endHeight;
                                    i9 = endWidth;
                                    i8 = startHeight;
                                    view5 = view2;
                                    i12 = startWidth2;
                                    i = numChanges;
                                    if (startLeft != endLeft) {
                                        view2 = view5;
                                    } else if (startTop != endTop) {
                                        view2 = view5;
                                    } else {
                                        view2 = view5;
                                        anim = ObjectAnimatorUtils.ofPointF(view2, BOTTOM_RIGHT_ONLY_PROPERTY, getPathMotion().getPath((float) startRight, (float) startBottom, (float) startParentVals, (float) startParent));
                                        view3 = endLeft;
                                        endRight = startParentVals;
                                        i4 = rect;
                                        i5 = i12;
                                    }
                                    anim = ObjectAnimatorUtils.ofPointF(view2, TOP_LEFT_ONLY_PROPERTY, getPathMotion().getPath((float) startLeft, (float) startTop, (float) endLeft, (float) endTop));
                                    view3 = endLeft;
                                    endRight = startParentVals;
                                    i4 = rect;
                                    i5 = i12;
                                } else if (startWidth2 == endWidth && startHeight == endHeight) {
                                    int endHeight3 = endHeight;
                                    i8 = startHeight;
                                    i9 = endWidth;
                                    anim = ObjectAnimatorUtils.ofPointF(view2, POSITION_PROPERTY, getPathMotion().getPath((float) startLeft, (float) startTop, (float) endLeft, (float) endTop));
                                    view3 = endLeft;
                                    i6 = startRight;
                                    i3 = startTop;
                                    i7 = endTop;
                                    map3 = startParentVals;
                                    i2 = startBottom;
                                    i5 = startWidth2;
                                    i4 = endHeight3;
                                } else {
                                    rect = endHeight;
                                    i9 = endWidth;
                                    i8 = startHeight;
                                    i = numChanges;
                                    endHeight = new ViewBounds(view2);
                                    i12 = startWidth2;
                                    Path topLeftPath = getPathMotion().getPath((float) startLeft, (float) startTop, (float) endLeft, (float) endTop);
                                    startHeight = ObjectAnimatorUtils.ofPointF(endHeight, TOP_LEFT_PROPERTY, topLeftPath);
                                    view5 = view2;
                                    ObjectAnimator bottomRightAnimator = ObjectAnimatorUtils.ofPointF(endHeight, BOTTOM_RIGHT_PROPERTY, getPathMotion().getPath((float) startRight, (float) startBottom, (float) startParentVals, (float) startParent));
                                    AnimatorSet set = new AnimatorSet();
                                    set.playTogether(new Animator[]{startHeight, bottomRightAnimator});
                                    startWidth2 = set;
                                    set.addListener(new AnimatorListenerAdapter() {
                                        private ViewBounds mViewBounds = endHeight;
                                    });
                                    view3 = endLeft;
                                    endRight = startParentVals;
                                    anim = startWidth2;
                                    i4 = rect;
                                    view2 = view5;
                                }
                                startParentVals = true;
                                startClip2 = startLeft;
                                int i13 = i9;
                                i9 = i8;
                                i8 = i13;
                            }
                            if (view2.getParent() instanceof ViewGroup) {
                                final ViewGroup parent = (ViewGroup) view2.getParent();
                                ViewGroupUtils.suppressLayout(parent, startParentVals);
                                addListener(new TransitionListenerAdapter() {
                                    boolean mCanceled = null;

                                    public void onTransitionCancel(@NonNull Transition transition) {
                                        ViewGroupUtils.suppressLayout(parent, false);
                                        this.mCanceled = true;
                                    }

                                    public void onTransitionEnd(@NonNull Transition transition) {
                                        if (!this.mCanceled) {
                                            ViewGroupUtils.suppressLayout(parent, false);
                                        }
                                        transition.removeListener(this);
                                    }

                                    public void onTransitionPause(@NonNull Transition transition) {
                                        ViewGroupUtils.suppressLayout(parent, false);
                                    }

                                    public void onTransitionResume(@NonNull Transition transition) {
                                        ViewGroupUtils.suppressLayout(parent, true);
                                    }
                                });
                            }
                            return anim;
                        }
                        transitionValues3 = startValues;
                        endParentVals = endValues;
                    } else {
                        map2 = endParentVals2;
                        viewGroup2 = startParent;
                        viewGroup3 = endParent;
                        view2 = view;
                        transitionValues3 = startValues;
                        endTop = ((Integer) transitionValues3.values.get(PROPNAME_WINDOW_X)).intValue();
                        startWidth = ((Integer) transitionValues3.values.get(PROPNAME_WINDOW_Y)).intValue();
                        endParentVals = endValues;
                        startParent = ((Integer) endParentVals.values.get(PROPNAME_WINDOW_X)).intValue();
                        endParent = ((Integer) endParentVals.values.get(PROPNAME_WINDOW_Y)).intValue();
                        if (endTop == startParent) {
                            if (startWidth != endParent) {
                            }
                        }
                        sceneRoot.getLocationInWindow(changeBounds.mTempLocation);
                        Bitmap bitmap = Bitmap.createBitmap(view2.getWidth(), view2.getHeight(), Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmap);
                        view2.draw(canvas);
                        final Drawable bitmapDrawable = new BitmapDrawable(bitmap);
                        float transitionAlpha = ViewUtils.getTransitionAlpha(view2);
                        ViewUtils.setTransitionAlpha(view2, 0.0f);
                        ViewUtils.getOverlay(sceneRoot).add(bitmapDrawable);
                        Canvas canvas2 = canvas;
                        Bitmap bitmap2 = bitmap;
                        PropertyValuesHolder origin = PropertyValuesHolderUtils.ofPointF(DRAWABLE_ORIGIN_PROPERTY, getPathMotion().getPath((float) (endTop - changeBounds.mTempLocation[0]), (float) (startWidth - changeBounds.mTempLocation[1]), (float) (startParent - changeBounds.mTempLocation[0]), (float) (endParent - changeBounds.mTempLocation[1])));
                        final ViewGroup viewGroup4 = sceneRoot;
                        Drawable drawable = bitmapDrawable;
                        AnonymousClass10 anonymousClass10 = r0;
                        final View view6 = view2;
                        ObjectAnimator anim2 = ObjectAnimator.ofPropertyValuesHolder(bitmapDrawable, new PropertyValuesHolder[]{origin});
                        final float f = transitionAlpha;
                        AnonymousClass10 anonymousClass102 = new AnimatorListenerAdapter() {
                            public void onAnimationEnd(Animator animation) {
                                ViewUtils.getOverlay(viewGroup4).remove(bitmapDrawable);
                                ViewUtils.setTransitionAlpha(view6, f);
                            }
                        };
                        anim2.addListener(anonymousClass10);
                        return anim2;
                    }
                    return null;
                }
                return null;
            }
        }
        viewGroup = sceneRoot;
        endParentVals = transitionValues2;
        return null;
    }
}
