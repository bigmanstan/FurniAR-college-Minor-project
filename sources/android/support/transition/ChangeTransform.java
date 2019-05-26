package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import org.xmlpull.v1.XmlPullParser;

public class ChangeTransform extends Transition {
    private static final Property<PathAnimatorMatrix, float[]> NON_TRANSLATIONS_PROPERTY = new Property<PathAnimatorMatrix, float[]>(float[].class, "nonTranslations") {
        public float[] get(PathAnimatorMatrix object) {
            return null;
        }

        public void set(PathAnimatorMatrix object, float[] value) {
            object.setValues(value);
        }
    };
    private static final String PROPNAME_INTERMEDIATE_MATRIX = "android:changeTransform:intermediateMatrix";
    private static final String PROPNAME_INTERMEDIATE_PARENT_MATRIX = "android:changeTransform:intermediateParentMatrix";
    private static final String PROPNAME_MATRIX = "android:changeTransform:matrix";
    private static final String PROPNAME_PARENT = "android:changeTransform:parent";
    private static final String PROPNAME_PARENT_MATRIX = "android:changeTransform:parentMatrix";
    private static final String PROPNAME_TRANSFORMS = "android:changeTransform:transforms";
    private static final boolean SUPPORTS_VIEW_REMOVAL_SUPPRESSION = (VERSION.SDK_INT >= 21);
    private static final Property<PathAnimatorMatrix, PointF> TRANSLATIONS_PROPERTY = new Property<PathAnimatorMatrix, PointF>(PointF.class, "translations") {
        public PointF get(PathAnimatorMatrix object) {
            return null;
        }

        public void set(PathAnimatorMatrix object, PointF value) {
            object.setTranslation(value);
        }
    };
    private static final String[] sTransitionProperties = new String[]{PROPNAME_MATRIX, PROPNAME_TRANSFORMS, PROPNAME_PARENT_MATRIX};
    private boolean mReparent = true;
    private Matrix mTempMatrix = new Matrix();
    private boolean mUseOverlay = true;

    private static class PathAnimatorMatrix {
        private final Matrix mMatrix = new Matrix();
        private float mTranslationX;
        private float mTranslationY;
        private final float[] mValues;
        private final View mView;

        PathAnimatorMatrix(View view, float[] values) {
            this.mView = view;
            this.mValues = (float[]) values.clone();
            this.mTranslationX = this.mValues[2];
            this.mTranslationY = this.mValues[5];
            setAnimationMatrix();
        }

        void setValues(float[] values) {
            System.arraycopy(values, 0, this.mValues, 0, values.length);
            setAnimationMatrix();
        }

        void setTranslation(PointF translation) {
            this.mTranslationX = translation.x;
            this.mTranslationY = translation.y;
            setAnimationMatrix();
        }

        private void setAnimationMatrix() {
            this.mValues[2] = this.mTranslationX;
            this.mValues[5] = this.mTranslationY;
            this.mMatrix.setValues(this.mValues);
            ViewUtils.setAnimationMatrix(this.mView, this.mMatrix);
        }

        Matrix getMatrix() {
            return this.mMatrix;
        }
    }

    private static class Transforms {
        final float mRotationX;
        final float mRotationY;
        final float mRotationZ;
        final float mScaleX;
        final float mScaleY;
        final float mTranslationX;
        final float mTranslationY;
        final float mTranslationZ;

        Transforms(View view) {
            this.mTranslationX = view.getTranslationX();
            this.mTranslationY = view.getTranslationY();
            this.mTranslationZ = ViewCompat.getTranslationZ(view);
            this.mScaleX = view.getScaleX();
            this.mScaleY = view.getScaleY();
            this.mRotationX = view.getRotationX();
            this.mRotationY = view.getRotationY();
            this.mRotationZ = view.getRotation();
        }

        public void restore(View view) {
            ChangeTransform.setTransforms(view, this.mTranslationX, this.mTranslationY, this.mTranslationZ, this.mScaleX, this.mScaleY, this.mRotationX, this.mRotationY, this.mRotationZ);
        }

        public boolean equals(Object that) {
            boolean z = false;
            if (!(that instanceof Transforms)) {
                return false;
            }
            Transforms thatTransform = (Transforms) that;
            if (thatTransform.mTranslationX == this.mTranslationX && thatTransform.mTranslationY == this.mTranslationY && thatTransform.mTranslationZ == this.mTranslationZ && thatTransform.mScaleX == this.mScaleX && thatTransform.mScaleY == this.mScaleY && thatTransform.mRotationX == this.mRotationX && thatTransform.mRotationY == this.mRotationY && thatTransform.mRotationZ == this.mRotationZ) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            int i = 0;
            int floatToIntBits = (((((((((((((this.mTranslationX != 0.0f ? Float.floatToIntBits(this.mTranslationX) : 0) * 31) + (this.mTranslationY != 0.0f ? Float.floatToIntBits(this.mTranslationY) : 0)) * 31) + (this.mTranslationZ != 0.0f ? Float.floatToIntBits(this.mTranslationZ) : 0)) * 31) + (this.mScaleX != 0.0f ? Float.floatToIntBits(this.mScaleX) : 0)) * 31) + (this.mScaleY != 0.0f ? Float.floatToIntBits(this.mScaleY) : 0)) * 31) + (this.mRotationX != 0.0f ? Float.floatToIntBits(this.mRotationX) : 0)) * 31) + (this.mRotationY != 0.0f ? Float.floatToIntBits(this.mRotationY) : 0)) * 31;
            if (this.mRotationZ != 0.0f) {
                i = Float.floatToIntBits(this.mRotationZ);
            }
            return floatToIntBits + i;
        }
    }

    private static class GhostListener extends TransitionListenerAdapter {
        private GhostViewImpl mGhostView;
        private View mView;

        GhostListener(View view, GhostViewImpl ghostView) {
            this.mView = view;
            this.mGhostView = ghostView;
        }

        public void onTransitionEnd(@NonNull Transition transition) {
            transition.removeListener(this);
            GhostViewUtils.removeGhost(this.mView);
            this.mView.setTag(C0013R.id.transition_transform, null);
            this.mView.setTag(C0013R.id.parent_matrix, null);
        }

        public void onTransitionPause(@NonNull Transition transition) {
            this.mGhostView.setVisibility(4);
        }

        public void onTransitionResume(@NonNull Transition transition) {
            this.mGhostView.setVisibility(0);
        }
    }

    public ChangeTransform(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.CHANGE_TRANSFORM);
        this.mUseOverlay = TypedArrayUtils.getNamedBoolean(a, (XmlPullParser) attrs, "reparentWithOverlay", 1, true);
        this.mReparent = TypedArrayUtils.getNamedBoolean(a, (XmlPullParser) attrs, "reparent", 0, true);
        a.recycle();
    }

    public boolean getReparentWithOverlay() {
        return this.mUseOverlay;
    }

    public void setReparentWithOverlay(boolean reparentWithOverlay) {
        this.mUseOverlay = reparentWithOverlay;
    }

    public boolean getReparent() {
        return this.mReparent;
    }

    public void setReparent(boolean reparent) {
        this.mReparent = reparent;
    }

    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view.getVisibility() != 8) {
            Matrix parentMatrix;
            ViewGroup parent;
            transitionValues.values.put(PROPNAME_PARENT, view.getParent());
            transitionValues.values.put(PROPNAME_TRANSFORMS, new Transforms(view));
            Matrix matrix = view.getMatrix();
            if (matrix != null) {
                if (!matrix.isIdentity()) {
                    matrix = new Matrix(matrix);
                    transitionValues.values.put(PROPNAME_MATRIX, matrix);
                    if (this.mReparent) {
                        parentMatrix = new Matrix();
                        parent = (ViewGroup) view.getParent();
                        ViewUtils.transformMatrixToGlobal(parent, parentMatrix);
                        parentMatrix.preTranslate((float) (-parent.getScrollX()), (float) (-parent.getScrollY()));
                        transitionValues.values.put(PROPNAME_PARENT_MATRIX, parentMatrix);
                        transitionValues.values.put(PROPNAME_INTERMEDIATE_MATRIX, view.getTag(C0013R.id.transition_transform));
                        transitionValues.values.put(PROPNAME_INTERMEDIATE_PARENT_MATRIX, view.getTag(C0013R.id.parent_matrix));
                    }
                }
            }
            matrix = null;
            transitionValues.values.put(PROPNAME_MATRIX, matrix);
            if (this.mReparent) {
                parentMatrix = new Matrix();
                parent = (ViewGroup) view.getParent();
                ViewUtils.transformMatrixToGlobal(parent, parentMatrix);
                parentMatrix.preTranslate((float) (-parent.getScrollX()), (float) (-parent.getScrollY()));
                transitionValues.values.put(PROPNAME_PARENT_MATRIX, parentMatrix);
                transitionValues.values.put(PROPNAME_INTERMEDIATE_MATRIX, view.getTag(C0013R.id.transition_transform));
                transitionValues.values.put(PROPNAME_INTERMEDIATE_PARENT_MATRIX, view.getTag(C0013R.id.parent_matrix));
            }
        }
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
        if (!SUPPORTS_VIEW_REMOVAL_SUPPRESSION) {
            ((ViewGroup) transitionValues.view.getParent()).startViewTransition(transitionValues.view);
        }
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public Animator createAnimator(@NonNull ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (!(startValues == null || endValues == null || !startValues.values.containsKey(PROPNAME_PARENT))) {
            if (endValues.values.containsKey(PROPNAME_PARENT)) {
                ViewGroup startParent = (ViewGroup) startValues.values.get(PROPNAME_PARENT);
                boolean handleParentChange = this.mReparent && !parentsMatch(startParent, (ViewGroup) endValues.values.get(PROPNAME_PARENT));
                Matrix startMatrix = (Matrix) startValues.values.get(PROPNAME_INTERMEDIATE_MATRIX);
                if (startMatrix != null) {
                    startValues.values.put(PROPNAME_MATRIX, startMatrix);
                }
                Matrix startParentMatrix = (Matrix) startValues.values.get(PROPNAME_INTERMEDIATE_PARENT_MATRIX);
                if (startParentMatrix != null) {
                    startValues.values.put(PROPNAME_PARENT_MATRIX, startParentMatrix);
                }
                if (handleParentChange) {
                    setMatricesForParent(startValues, endValues);
                }
                ObjectAnimator transformAnimator = createTransformAnimator(startValues, endValues, handleParentChange);
                if (handleParentChange && transformAnimator != null && this.mUseOverlay) {
                    createGhostView(sceneRoot, startValues, endValues);
                } else if (!SUPPORTS_VIEW_REMOVAL_SUPPRESSION) {
                    startParent.endViewTransition(startValues.view);
                }
                return transformAnimator;
            }
        }
        return null;
    }

    private ObjectAnimator createTransformAnimator(TransitionValues startValues, TransitionValues endValues, boolean handleParentChange) {
        TransitionValues transitionValues = endValues;
        Matrix startMatrix = (Matrix) startValues.values.get(PROPNAME_MATRIX);
        Matrix endMatrix = (Matrix) transitionValues.values.get(PROPNAME_MATRIX);
        if (startMatrix == null) {
            startMatrix = MatrixUtils.IDENTITY_MATRIX;
        }
        if (endMatrix == null) {
            endMatrix = MatrixUtils.IDENTITY_MATRIX;
        }
        if (startMatrix.equals(endMatrix)) {
            return null;
        }
        Transforms transforms = (Transforms) transitionValues.values.get(PROPNAME_TRANSFORMS);
        View view = transitionValues.view;
        setIdentityTransforms(view);
        startMatrixValues = new float[9];
        startMatrix.getValues(startMatrixValues);
        float[] endMatrixValues = new float[9];
        endMatrix.getValues(endMatrixValues);
        PathAnimatorMatrix pathAnimatorMatrix = new PathAnimatorMatrix(view, startMatrixValues);
        PropertyValuesHolder valuesProperty = PropertyValuesHolder.ofObject(NON_TRANSLATIONS_PROPERTY, new FloatArrayEvaluator(new float[9]), new float[][]{startMatrixValues, endMatrixValues});
        Path path = getPathMotion().getPath(startMatrixValues[2], startMatrixValues[5], endMatrixValues[2], endMatrixValues[5]);
        PropertyValuesHolder translationProperty = PropertyValuesHolderUtils.ofPointF(TRANSLATIONS_PROPERTY, path);
        final Matrix finalEndMatrix = endMatrix;
        final boolean z = handleParentChange;
        final View view2 = view;
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(pathAnimatorMatrix, new PropertyValuesHolder[]{valuesProperty, translationProperty});
        final Transforms transforms2 = transforms;
        final PathAnimatorMatrix pathAnimatorMatrix2 = pathAnimatorMatrix;
        AnimatorListenerAdapter listener = new AnimatorListenerAdapter() {
            private boolean mIsCanceled;
            private Matrix mTempMatrix = new Matrix();

            public void onAnimationCancel(Animator animation) {
                this.mIsCanceled = true;
            }

            public void onAnimationEnd(Animator animation) {
                if (!this.mIsCanceled) {
                    if (z && ChangeTransform.this.mUseOverlay) {
                        setCurrentMatrix(finalEndMatrix);
                    } else {
                        view2.setTag(C0013R.id.transition_transform, null);
                        view2.setTag(C0013R.id.parent_matrix, null);
                    }
                }
                ViewUtils.setAnimationMatrix(view2, null);
                transforms2.restore(view2);
            }

            public void onAnimationPause(Animator animation) {
                setCurrentMatrix(pathAnimatorMatrix2.getMatrix());
            }

            public void onAnimationResume(Animator animation) {
                ChangeTransform.setIdentityTransforms(view2);
            }

            private void setCurrentMatrix(Matrix currentMatrix) {
                this.mTempMatrix.set(currentMatrix);
                view2.setTag(C0013R.id.transition_transform, this.mTempMatrix);
                transforms2.restore(view2);
            }
        };
        animator.addListener(listener);
        AnimatorUtils.addPauseListener(animator, listener);
        return animator;
    }

    private boolean parentsMatch(ViewGroup startParent, ViewGroup endParent) {
        boolean z = false;
        if (isValidTarget(startParent)) {
            if (isValidTarget(endParent)) {
                TransitionValues endValues = getMatchedTransitionValues(startParent, true);
                if (endValues == null) {
                    return false;
                }
                if (endParent == endValues.view) {
                    z = true;
                }
                return z;
            }
        }
        if (startParent == endParent) {
            z = true;
        }
        return z;
    }

    private void createGhostView(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        View view = endValues.view;
        Matrix localEndMatrix = new Matrix((Matrix) endValues.values.get(PROPNAME_PARENT_MATRIX));
        ViewUtils.transformMatrixToLocal(sceneRoot, localEndMatrix);
        GhostViewImpl ghostView = GhostViewUtils.addGhost(view, sceneRoot, localEndMatrix);
        if (ghostView != null) {
            ghostView.reserveEndViewTransition((ViewGroup) startValues.values.get(PROPNAME_PARENT), startValues.view);
            Transition outerTransition = this;
            while (outerTransition.mParent != null) {
                outerTransition = outerTransition.mParent;
            }
            outerTransition.addListener(new GhostListener(view, ghostView));
            if (SUPPORTS_VIEW_REMOVAL_SUPPRESSION) {
                if (startValues.view != endValues.view) {
                    ViewUtils.setTransitionAlpha(startValues.view, 0.0f);
                }
                ViewUtils.setTransitionAlpha(view, 1.0f);
            }
        }
    }

    private void setMatricesForParent(TransitionValues startValues, TransitionValues endValues) {
        Matrix endParentMatrix = (Matrix) endValues.values.get(PROPNAME_PARENT_MATRIX);
        endValues.view.setTag(C0013R.id.parent_matrix, endParentMatrix);
        Matrix toLocal = this.mTempMatrix;
        toLocal.reset();
        endParentMatrix.invert(toLocal);
        Matrix startLocal = (Matrix) startValues.values.get(PROPNAME_MATRIX);
        if (startLocal == null) {
            startLocal = new Matrix();
            startValues.values.put(PROPNAME_MATRIX, startLocal);
        }
        startLocal.postConcat((Matrix) startValues.values.get(PROPNAME_PARENT_MATRIX));
        startLocal.postConcat(toLocal);
    }

    private static void setIdentityTransforms(View view) {
        setTransforms(view, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f);
    }

    private static void setTransforms(View view, float translationX, float translationY, float translationZ, float scaleX, float scaleY, float rotationX, float rotationY, float rotationZ) {
        view.setTranslationX(translationX);
        view.setTranslationY(translationY);
        ViewCompat.setTranslationZ(view, translationZ);
        view.setScaleX(scaleX);
        view.setScaleY(scaleY);
        view.setRotationX(rotationX);
        view.setRotationY(rotationY);
        view.setRotation(rotationZ);
    }
}
