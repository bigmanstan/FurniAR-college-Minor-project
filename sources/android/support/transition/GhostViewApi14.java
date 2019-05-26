package android.support.transition;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.FrameLayout;

@SuppressLint({"ViewConstructor"})
@RequiresApi(14)
class GhostViewApi14 extends View implements GhostViewImpl {
    Matrix mCurrentMatrix;
    private int mDeltaX;
    private int mDeltaY;
    private final Matrix mMatrix = new Matrix();
    private final OnPreDrawListener mOnPreDrawListener = new C01081();
    int mReferences;
    ViewGroup mStartParent;
    View mStartView;
    final View mView;

    /* renamed from: android.support.transition.GhostViewApi14$1 */
    class C01081 implements OnPreDrawListener {
        C01081() {
        }

        public boolean onPreDraw() {
            GhostViewApi14.this.mCurrentMatrix = GhostViewApi14.this.mView.getMatrix();
            ViewCompat.postInvalidateOnAnimation(GhostViewApi14.this);
            if (!(GhostViewApi14.this.mStartParent == null || GhostViewApi14.this.mStartView == null)) {
                GhostViewApi14.this.mStartParent.endViewTransition(GhostViewApi14.this.mStartView);
                ViewCompat.postInvalidateOnAnimation(GhostViewApi14.this.mStartParent);
                GhostViewApi14.this.mStartParent = null;
                GhostViewApi14.this.mStartView = null;
            }
            return true;
        }
    }

    static class Creator implements android.support.transition.GhostViewImpl.Creator {
        Creator() {
        }

        public GhostViewImpl addGhost(View view, ViewGroup viewGroup, Matrix matrix) {
            GhostViewApi14 ghostView = GhostViewApi14.getGhostView(view);
            if (ghostView == null) {
                FrameLayout frameLayout = findFrameLayout(viewGroup);
                if (frameLayout == null) {
                    return null;
                }
                ghostView = new GhostViewApi14(view);
                frameLayout.addView(ghostView);
            }
            ghostView.mReferences++;
            return ghostView;
        }

        public void removeGhost(View view) {
            GhostViewApi14 ghostView = GhostViewApi14.getGhostView(view);
            if (ghostView != null) {
                ghostView.mReferences--;
                if (ghostView.mReferences <= 0) {
                    ViewParent parent = ghostView.getParent();
                    if (parent instanceof ViewGroup) {
                        ViewGroup group = (ViewGroup) parent;
                        group.endViewTransition(ghostView);
                        group.removeView(ghostView);
                    }
                }
            }
        }

        private static FrameLayout findFrameLayout(ViewGroup viewGroup) {
            while (!(viewGroup instanceof FrameLayout)) {
                ViewParent parent = viewGroup.getParent();
                if (!(parent instanceof ViewGroup)) {
                    return null;
                }
                viewGroup = (ViewGroup) parent;
            }
            return (FrameLayout) viewGroup;
        }
    }

    GhostViewApi14(View view) {
        super(view.getContext());
        this.mView = view;
        setLayerType(2, null);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setGhostView(this.mView, this);
        int[] location = new int[2];
        int[] viewLocation = new int[2];
        getLocationOnScreen(location);
        this.mView.getLocationOnScreen(viewLocation);
        viewLocation[0] = (int) (((float) viewLocation[0]) - this.mView.getTranslationX());
        viewLocation[1] = (int) (((float) viewLocation[1]) - this.mView.getTranslationY());
        this.mDeltaX = viewLocation[0] - location[0];
        this.mDeltaY = viewLocation[1] - location[1];
        this.mView.getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
        this.mView.setVisibility(4);
    }

    protected void onDetachedFromWindow() {
        this.mView.getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
        this.mView.setVisibility(0);
        setGhostView(this.mView, null);
        super.onDetachedFromWindow();
    }

    protected void onDraw(Canvas canvas) {
        this.mMatrix.set(this.mCurrentMatrix);
        this.mMatrix.postTranslate((float) this.mDeltaX, (float) this.mDeltaY);
        canvas.setMatrix(this.mMatrix);
        this.mView.draw(canvas);
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        this.mView.setVisibility(visibility == 0 ? 4 : 0);
    }

    public void reserveEndViewTransition(ViewGroup viewGroup, View view) {
        this.mStartParent = viewGroup;
        this.mStartView = view;
    }

    private static void setGhostView(@NonNull View view, GhostViewApi14 ghostView) {
        view.setTag(C0013R.id.ghost_view, ghostView);
    }

    static GhostViewApi14 getGhostView(@NonNull View view) {
        return (GhostViewApi14) view.getTag(C0013R.id.ghost_view);
    }
}
