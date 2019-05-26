package android.support.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.xmlpull.v1.XmlPullParser;

public class Slide extends Visibility {
    private static final String PROPNAME_SCREEN_POSITION = "android:slide:screenPosition";
    private static final TimeInterpolator sAccelerate = new AccelerateInterpolator();
    private static final CalculateSlide sCalculateBottom = new C05716();
    private static final CalculateSlide sCalculateEnd = new C05705();
    private static final CalculateSlide sCalculateLeft = new C05661();
    private static final CalculateSlide sCalculateRight = new C05694();
    private static final CalculateSlide sCalculateStart = new C05672();
    private static final CalculateSlide sCalculateTop = new C05683();
    private static final TimeInterpolator sDecelerate = new DecelerateInterpolator();
    private CalculateSlide mSlideCalculator = sCalculateBottom;
    private int mSlideEdge = 80;

    private interface CalculateSlide {
        float getGoneX(ViewGroup viewGroup, View view);

        float getGoneY(ViewGroup viewGroup, View view);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GravityFlag {
    }

    private static abstract class CalculateSlideHorizontal implements CalculateSlide {
        private CalculateSlideHorizontal() {
        }

        public float getGoneY(ViewGroup sceneRoot, View view) {
            return view.getTranslationY();
        }
    }

    private static abstract class CalculateSlideVertical implements CalculateSlide {
        private CalculateSlideVertical() {
        }

        public float getGoneX(ViewGroup sceneRoot, View view) {
            return view.getTranslationX();
        }
    }

    /* renamed from: android.support.transition.Slide$1 */
    static class C05661 extends CalculateSlideHorizontal {
        C05661() {
            super();
        }

        public float getGoneX(ViewGroup sceneRoot, View view) {
            return view.getTranslationX() - ((float) sceneRoot.getWidth());
        }
    }

    /* renamed from: android.support.transition.Slide$2 */
    static class C05672 extends CalculateSlideHorizontal {
        C05672() {
            super();
        }

        public float getGoneX(ViewGroup sceneRoot, View view) {
            boolean z = true;
            if (ViewCompat.getLayoutDirection(sceneRoot) != 1) {
                z = false;
            }
            if (z) {
                return view.getTranslationX() + ((float) sceneRoot.getWidth());
            }
            return view.getTranslationX() - ((float) sceneRoot.getWidth());
        }
    }

    /* renamed from: android.support.transition.Slide$3 */
    static class C05683 extends CalculateSlideVertical {
        C05683() {
            super();
        }

        public float getGoneY(ViewGroup sceneRoot, View view) {
            return view.getTranslationY() - ((float) sceneRoot.getHeight());
        }
    }

    /* renamed from: android.support.transition.Slide$4 */
    static class C05694 extends CalculateSlideHorizontal {
        C05694() {
            super();
        }

        public float getGoneX(ViewGroup sceneRoot, View view) {
            return view.getTranslationX() + ((float) sceneRoot.getWidth());
        }
    }

    /* renamed from: android.support.transition.Slide$5 */
    static class C05705 extends CalculateSlideHorizontal {
        C05705() {
            super();
        }

        public float getGoneX(ViewGroup sceneRoot, View view) {
            boolean z = true;
            if (ViewCompat.getLayoutDirection(sceneRoot) != 1) {
                z = false;
            }
            if (z) {
                return view.getTranslationX() - ((float) sceneRoot.getWidth());
            }
            return view.getTranslationX() + ((float) sceneRoot.getWidth());
        }
    }

    /* renamed from: android.support.transition.Slide$6 */
    static class C05716 extends CalculateSlideVertical {
        C05716() {
            super();
        }

        public float getGoneY(ViewGroup sceneRoot, View view) {
            return view.getTranslationY() + ((float) sceneRoot.getHeight());
        }
    }

    public Slide() {
        setSlideEdge(80);
    }

    public Slide(int slideEdge) {
        setSlideEdge(slideEdge);
    }

    public Slide(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.SLIDE);
        int edge = TypedArrayUtils.getNamedInt(a, (XmlPullParser) attrs, "slideEdge", 0, 80);
        a.recycle();
        setSlideEdge(edge);
    }

    private void captureValues(TransitionValues transitionValues) {
        int[] position = new int[2];
        transitionValues.view.getLocationOnScreen(position);
        transitionValues.values.put(PROPNAME_SCREEN_POSITION, position);
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        captureValues(transitionValues);
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        captureValues(transitionValues);
    }

    public void setSlideEdge(int slideEdge) {
        if (slideEdge == 3) {
            this.mSlideCalculator = sCalculateLeft;
        } else if (slideEdge == 5) {
            this.mSlideCalculator = sCalculateRight;
        } else if (slideEdge == 48) {
            this.mSlideCalculator = sCalculateTop;
        } else if (slideEdge == 80) {
            this.mSlideCalculator = sCalculateBottom;
        } else if (slideEdge == GravityCompat.START) {
            this.mSlideCalculator = sCalculateStart;
        } else if (slideEdge == GravityCompat.END) {
            this.mSlideCalculator = sCalculateEnd;
        } else {
            throw new IllegalArgumentException("Invalid slide direction");
        }
        this.mSlideEdge = slideEdge;
        SidePropagation propagation = new SidePropagation();
        propagation.setSide(slideEdge);
        setPropagation(propagation);
    }

    public int getSlideEdge() {
        return this.mSlideEdge;
    }

    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        Slide slide = this;
        ViewGroup viewGroup = sceneRoot;
        View view2 = view;
        TransitionValues transitionValues = endValues;
        if (transitionValues == null) {
            return null;
        }
        int[] position = (int[]) transitionValues.values.get(PROPNAME_SCREEN_POSITION);
        float endX = view.getTranslationX();
        float endY = view.getTranslationY();
        float startX = slide.mSlideCalculator.getGoneX(viewGroup, view2);
        return TranslationAnimationCreator.createAnimation(view, endValues, position[0], position[1], startX, slide.mSlideCalculator.getGoneY(viewGroup, view2), endX, endY, sDecelerate);
    }

    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        Slide slide = this;
        ViewGroup viewGroup = sceneRoot;
        View view2 = view;
        TransitionValues transitionValues = startValues;
        if (transitionValues == null) {
            return null;
        }
        int[] position = (int[]) transitionValues.values.get(PROPNAME_SCREEN_POSITION);
        float startX = view.getTranslationX();
        float startY = view.getTranslationY();
        float endX = slide.mSlideCalculator.getGoneX(viewGroup, view2);
        return TranslationAnimationCreator.createAnimation(view, startValues, position[0], position[1], startX, startY, endX, slide.mSlideCalculator.getGoneY(viewGroup, view2), sAccelerate);
    }
}
