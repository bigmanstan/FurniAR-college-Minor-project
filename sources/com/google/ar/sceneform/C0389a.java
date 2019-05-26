package com.google.ar.sceneform;

import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;
import android.view.MotionEvent.PointerProperties;
import android.view.View;
import com.google.ar.core.Pose;
import com.google.ar.sceneform.collision.Plane;
import com.google.ar.sceneform.collision.Ray;
import com.google.ar.sceneform.collision.RayHit;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.utilities.Preconditions;

/* renamed from: com.google.ar.sceneform.a */
class C0389a {
    /* renamed from: a */
    private static float m85a(ViewRenderable viewRenderable) {
        int width = viewRenderable.getView().getWidth();
        Vector3 size = viewRenderable.getSizer().getSize(viewRenderable.getView());
        return size.f120x == 0.0f ? 0.0f : ((float) width) / size.f120x;
    }

    /* renamed from: a */
    static Vector3 m86a(Pose pose) {
        return new Vector3(pose.tx(), pose.ty(), pose.tz());
    }

    /* renamed from: a */
    static Vector3 m87a(Node node, Vector3 vector3, ViewRenderable viewRenderable) {
        Preconditions.checkNotNull(node, "Parameter \"node\" was null.");
        Preconditions.checkNotNull(vector3, "Parameter \"worldPos\" was null.");
        Preconditions.checkNotNull(viewRenderable, "Parameter \"viewRenderable\" was null.");
        Vector3 worldToLocalPoint = node.worldToLocalPoint(vector3);
        View view = viewRenderable.getView();
        int width = view.getWidth();
        int height = view.getHeight();
        float a = C0389a.m85a(viewRenderable);
        int i = (int) (worldToLocalPoint.f120x * a);
        int i2 = (int) (worldToLocalPoint.f121y * a);
        int i3 = width / 2;
        int i4 = height / 2;
        switch (viewRenderable.getVerticalAlignment().ordinal()) {
            case 0:
                break;
            case 1:
                i2 += i4;
                break;
            case 2:
                i2 += height;
                break;
            default:
                break;
        }
        i2 = height - i2;
        switch (viewRenderable.getHorizontalAlignment().ordinal()) {
            case 0:
                break;
            case 1:
                i += i3;
                break;
            case 2:
                i += width;
                break;
            default:
                break;
        }
        return new Vector3((float) i, (float) i2, 0.0f);
    }

    /* renamed from: a */
    static boolean m88a(Node node, MotionEvent motionEvent) {
        Node node2 = node;
        MotionEvent motionEvent2 = motionEvent;
        Preconditions.checkNotNull(node2, "Parameter \"node\" was null.");
        Preconditions.checkNotNull(motionEvent2, "Parameter \"motionEvent\" was null.");
        int i = 0;
        if (!(node.getRenderable() instanceof ViewRenderable) || !node.isActive()) {
            return false;
        }
        Scene scene = node.getScene();
        if (scene == null) {
            return false;
        }
        ViewRenderable viewRenderable = (ViewRenderable) node.getRenderable();
        if (viewRenderable == null) {
            return false;
        }
        int pointerCount = motionEvent.getPointerCount();
        PointerProperties[] pointerPropertiesArr = new PointerProperties[pointerCount];
        PointerCoords[] pointerCoordsArr = new PointerCoords[pointerCount];
        Plane plane = new Plane(node.getWorldPosition(), node.getForward());
        RayHit rayHit = new RayHit();
        Plane plane2 = new Plane(node.getWorldPosition(), node.getBack());
        while (i < pointerCount) {
            Vector3 a;
            float f;
            PointerProperties pointerProperties = new PointerProperties();
            PointerCoords pointerCoords = new PointerCoords();
            motionEvent2.getPointerProperties(i, pointerProperties);
            motionEvent2.getPointerCoords(i, pointerCoords);
            Ray screenPointToRay = scene.getCamera().screenPointToRay(pointerCoords.x, pointerCoords.y);
            if (plane.rayIntersection(screenPointToRay, rayHit)) {
                a = C0389a.m87a(node2, rayHit.getPoint(), viewRenderable);
                f = a.f120x;
            } else if (plane2.rayIntersection(screenPointToRay, rayHit)) {
                a = C0389a.m87a(node2, rayHit.getPoint(), viewRenderable);
                f = ((float) viewRenderable.getView().getWidth()) - a.f120x;
            } else {
                pointerCoords.clear();
                pointerProperties.clear();
                pointerPropertiesArr[i] = pointerProperties;
                pointerCoordsArr[i] = pointerCoords;
                i++;
            }
            pointerCoords.x = f;
            pointerCoords.y = a.f121y;
            pointerPropertiesArr[i] = pointerProperties;
            pointerCoordsArr[i] = pointerCoords;
            i++;
        }
        return viewRenderable.getView().dispatchTouchEvent(MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getEventTime(), motionEvent.getAction(), pointerCount, pointerPropertiesArr, pointerCoordsArr, motionEvent.getMetaState(), motionEvent.getButtonState(), motionEvent.getXPrecision(), motionEvent.getYPrecision(), motionEvent.getDeviceId(), motionEvent.getEdgeFlags(), motionEvent.getSource(), motionEvent.getFlags()));
    }

    /* renamed from: b */
    static Quaternion m89b(Pose pose) {
        return new Quaternion(pose.qx(), pose.qy(), pose.qz(), pose.qw());
    }
}
