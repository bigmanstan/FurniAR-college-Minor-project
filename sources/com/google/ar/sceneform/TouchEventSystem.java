package com.google.ar.sceneform;

import android.view.MotionEvent;
import com.google.ar.sceneform.Scene.OnPeekTouchListener;
import com.google.ar.sceneform.Scene.OnTouchListener;
import com.google.ar.sceneform.utilities.Preconditions;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TouchEventSystem {
    private TouchTarget firstHandlingTouchTarget = null;
    private OnTouchListener handlingTouchListener = null;
    private Method motionEventSplitMethod;
    private final Object[] motionEventSplitParams = new Object[1];
    private final ArrayList<OnPeekTouchListener> onPeekTouchListeners = new ArrayList();
    private OnTouchListener onTouchListener;

    static class TouchTarget {
        /* renamed from: a */
        public Node f103a;
        /* renamed from: b */
        public int f104b;
        /* renamed from: c */
        public TouchTarget f105c;

        private TouchTarget() {
        }
    }

    private TouchTarget addTouchTarget(Node node, int i) {
        TouchTarget touchTarget = new TouchTarget();
        touchTarget.f103a = node;
        touchTarget.f104b = i;
        touchTarget.f105c = this.firstHandlingTouchTarget;
        this.firstHandlingTouchTarget = touchTarget;
        return touchTarget;
    }

    private void clearTouchTargets() {
        this.firstHandlingTouchTarget = null;
    }

    private Node dispatchTouchEvent(MotionEvent motionEvent, HitTestResult hitTestResult, Node node, int i, boolean z) {
        int pointerIdBits = getPointerIdBits(motionEvent);
        i &= pointerIdBits;
        if (i == 0) {
            return null;
        }
        Object obj = null;
        if (i != pointerIdBits) {
            motionEvent = splitMotionEvent(motionEvent, i);
            obj = 1;
        }
        while (node != null && !node.dispatchTouchEvent(hitTestResult, motionEvent)) {
            node = z ? node.getParent() : null;
        }
        if (node == null) {
            tryDispatchToSceneTouchListener(hitTestResult, motionEvent);
        }
        if (obj != null) {
            motionEvent.recycle();
        }
        return node;
    }

    private int getPointerIdBits(MotionEvent motionEvent) {
        int i = 0;
        int i2 = 0;
        while (i < motionEvent.getPointerCount()) {
            i2 |= 1 << motionEvent.getPointerId(i);
            i++;
        }
        return i2;
    }

    private TouchTarget getTouchTargetForNode(Node node) {
        for (TouchTarget touchTarget = this.firstHandlingTouchTarget; touchTarget != null; touchTarget = touchTarget.f105c) {
            if (touchTarget.f103a == node) {
                return touchTarget;
            }
        }
        return null;
    }

    private void removePointersFromTouchTargets(int i) {
        TouchTarget touchTarget = this.firstHandlingTouchTarget;
        TouchTarget touchTarget2 = null;
        while (touchTarget != null) {
            TouchTarget touchTarget3 = touchTarget.f105c;
            if ((touchTarget.f104b & i) != 0) {
                touchTarget.f104b &= ~i;
                if (touchTarget.f104b == 0) {
                    if (touchTarget2 == null) {
                        this.firstHandlingTouchTarget = touchTarget3;
                    } else {
                        touchTarget2.f105c = touchTarget3;
                    }
                    touchTarget = touchTarget3;
                }
            }
            touchTarget2 = touchTarget;
            touchTarget = touchTarget3;
        }
    }

    private MotionEvent splitMotionEvent(MotionEvent motionEvent, int i) {
        if (this.motionEventSplitMethod == null) {
            try {
                this.motionEventSplitMethod = MotionEvent.class.getMethod("split", new Class[]{Integer.TYPE});
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Splitting MotionEvent not supported.");
            }
        }
        try {
            this.motionEventSplitParams[0] = Integer.valueOf(i);
            Object invoke = this.motionEventSplitMethod.invoke(motionEvent, this.motionEventSplitParams);
            return invoke != null ? (MotionEvent) invoke : motionEvent;
        } catch (InvocationTargetException e2) {
            throw new RuntimeException("Unable to split MotionEvent.");
        }
    }

    private boolean tryDispatchToSceneTouchListener(HitTestResult hitTestResult, MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            if (this.onTouchListener != null && this.onTouchListener.onSceneTouch(hitTestResult, motionEvent)) {
                this.handlingTouchListener = this.onTouchListener;
                return true;
            }
        } else if (this.handlingTouchListener != null) {
            this.handlingTouchListener.onSceneTouch(hitTestResult, motionEvent);
            return true;
        }
        return false;
    }

    public void addOnPeekTouchListener(OnPeekTouchListener onPeekTouchListener) {
        if (!this.onPeekTouchListeners.contains(onPeekTouchListener)) {
            this.onPeekTouchListeners.add(onPeekTouchListener);
        }
    }

    public OnTouchListener getOnTouchListener() {
        return this.onTouchListener;
    }

    public void onTouchEvent(HitTestResult hitTestResult, MotionEvent motionEvent) {
        Preconditions.checkNotNull(hitTestResult, "Parameter \"hitTestResult\" was null.");
        Preconditions.checkNotNull(motionEvent, "Parameter \"motionEvent\" was null.");
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.handlingTouchListener = null;
            clearTouchTargets();
        }
        ArrayList arrayList = this.onPeekTouchListeners;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((OnPeekTouchListener) obj).onPeekTouch(hitTestResult, motionEvent);
        }
        Node node = hitTestResult.getNode();
        TouchTarget touchTarget;
        if (node == null || !(actionMasked == 0 || actionMasked == 5)) {
            touchTarget = null;
            i2 = 0;
        } else {
            int pointerId = 1 << motionEvent.getPointerId(motionEvent.getActionIndex());
            removePointersFromTouchTargets(pointerId);
            touchTarget = getTouchTargetForNode(node);
            if (touchTarget != null) {
                touchTarget.f104b |= pointerId;
                i2 = 0;
            } else {
                Node dispatchTouchEvent = dispatchTouchEvent(motionEvent, hitTestResult, node, pointerId, true);
                if (dispatchTouchEvent != null) {
                    touchTarget = addTouchTarget(dispatchTouchEvent, pointerId);
                    i = 1;
                }
                i2 = 1;
            }
            if (touchTarget == null && this.firstHandlingTouchTarget != null) {
                touchTarget = this.firstHandlingTouchTarget;
                while (touchTarget.f105c != null) {
                    touchTarget = touchTarget.f105c;
                }
                touchTarget.f104b = pointerId | touchTarget.f104b;
            }
        }
        if (this.firstHandlingTouchTarget != null) {
            TouchTarget touchTarget2 = this.firstHandlingTouchTarget;
            while (touchTarget2 != null) {
                TouchTarget touchTarget3 = touchTarget2.f105c;
                if (i == 0 || touchTarget2 != r3) {
                    dispatchTouchEvent(motionEvent, hitTestResult, touchTarget2.f103a, touchTarget2.f104b, false);
                }
                touchTarget2 = touchTarget3;
            }
        } else if (i2 == 0) {
            tryDispatchToSceneTouchListener(hitTestResult, motionEvent);
        }
        if (actionMasked != 3) {
            if (actionMasked != 1) {
                if (actionMasked == 6) {
                    removePointersFromTouchTargets(1 << motionEvent.getPointerId(motionEvent.getActionIndex()));
                }
                return;
            }
        }
        clearTouchTargets();
    }

    public void removeOnPeekTouchListener(OnPeekTouchListener onPeekTouchListener) {
        this.onPeekTouchListeners.remove(onPeekTouchListener);
    }

    @Deprecated
    public void setOnPeekTouchListener(OnPeekTouchListener onPeekTouchListener) {
        this.onPeekTouchListeners.clear();
        if (onPeekTouchListener != null) {
            this.onPeekTouchListeners.add(onPeekTouchListener);
        }
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }
}
