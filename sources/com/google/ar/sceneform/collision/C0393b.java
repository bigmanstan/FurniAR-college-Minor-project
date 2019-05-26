package com.google.ar.sceneform.collision;

import com.google.ar.sceneform.math.MathHelper;
import com.google.ar.sceneform.math.Matrix;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.utilities.Preconditions;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.google.ar.sceneform.collision.b */
final class C0393b {
    /* renamed from: a */
    private static Vector3 m90a(Matrix matrix) {
        return new Vector3(matrix.data[0], matrix.data[4], matrix.data[8]);
    }

    /* renamed from: a */
    private static List<Vector3> m91a(Box box) {
        Preconditions.checkNotNull(box, "Parameter \"box\" was null.");
        Vector3 center = box.getCenter();
        Vector3 extents = box.getExtents();
        Matrix rawRotationMatrix = box.getRawRotationMatrix();
        Vector3 a = C0393b.m90a(rawRotationMatrix);
        Vector3 b = C0393b.m94b(rawRotationMatrix);
        Vector3 c = C0393b.m95c(rawRotationMatrix);
        a = a.scaled(extents.f120x);
        b = b.scaled(extents.f121y);
        c = c.scaled(extents.f122z);
        List arrayList = new ArrayList(8);
        arrayList.add(Vector3.add(Vector3.add(Vector3.add(center, a), b), c));
        arrayList.add(Vector3.add(Vector3.add(Vector3.subtract(center, a), b), c));
        arrayList.add(Vector3.add(Vector3.subtract(Vector3.add(center, a), b), c));
        arrayList.add(Vector3.subtract(Vector3.add(Vector3.add(center, a), b), c));
        arrayList.add(Vector3.subtract(Vector3.subtract(Vector3.subtract(center, a), b), c));
        arrayList.add(Vector3.subtract(Vector3.subtract(Vector3.add(center, a), b), c));
        arrayList.add(Vector3.subtract(Vector3.add(Vector3.subtract(center, a), b), c));
        arrayList.add(Vector3.add(Vector3.subtract(Vector3.subtract(center, a), b), c));
        return arrayList;
    }

    /* renamed from: a */
    static boolean m92a(Box box, Box box2) {
        int i;
        Preconditions.checkNotNull(box, "Parameter \"box1\" was null.");
        Preconditions.checkNotNull(box2, "Parameter \"box2\" was null.");
        List a = C0393b.m91a(box);
        List a2 = C0393b.m91a(box2);
        Matrix rawRotationMatrix = box.getRawRotationMatrix();
        Matrix rawRotationMatrix2 = box2.getRawRotationMatrix();
        ArrayList arrayList = new ArrayList(15);
        arrayList.add(C0393b.m90a(rawRotationMatrix));
        arrayList.add(C0393b.m94b(rawRotationMatrix));
        arrayList.add(C0393b.m95c(rawRotationMatrix));
        arrayList.add(C0393b.m90a(rawRotationMatrix2));
        arrayList.add(C0393b.m94b(rawRotationMatrix2));
        arrayList.add(C0393b.m95c(rawRotationMatrix2));
        for (i = 0; i < 3; i++) {
            arrayList.add(Vector3.cross((Vector3) arrayList.get(i), (Vector3) arrayList.get(0)));
            arrayList.add(Vector3.cross((Vector3) arrayList.get(i), (Vector3) arrayList.get(1)));
            arrayList.add(Vector3.cross((Vector3) arrayList.get(i), (Vector3) arrayList.get(2)));
        }
        for (i = 0; i < arrayList.size(); i++) {
            Vector3 vector3 = (Vector3) arrayList.get(i);
            float f = Float.MAX_VALUE;
            float f2 = Float.MIN_VALUE;
            float f3 = Float.MAX_VALUE;
            for (int i2 = 0; i2 < a.size(); i2++) {
                float dot = Vector3.dot(vector3, (Vector3) a.get(i2));
                f3 = Math.min(dot, f3);
                f2 = Math.max(dot, f2);
            }
            float f4 = Float.MIN_VALUE;
            for (int i3 = 0; i3 < a2.size(); i3++) {
                dot = Vector3.dot(vector3, (Vector3) a2.get(i3));
                f = Math.min(dot, f);
                f4 = Math.max(dot, f4);
            }
            boolean z = f <= f2 && f3 <= f4;
            if (!z) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: a */
    static boolean m93a(Sphere sphere, Box box) {
        Preconditions.checkNotNull(sphere, "Parameter \"sphere\" was null.");
        Preconditions.checkNotNull(box, "Parameter \"box\" was null.");
        Vector3 center = sphere.getCenter();
        Vector3 vector3 = new Vector3(box.getCenter());
        center = Vector3.subtract(center, box.getCenter());
        Matrix rawRotationMatrix = box.getRawRotationMatrix();
        Vector3 extents = box.getExtents();
        Vector3 a = C0393b.m90a(rawRotationMatrix);
        float dot = Vector3.dot(center, a);
        if (dot > extents.f120x) {
            dot = extents.f120x;
        } else if (dot < (-extents.f120x)) {
            dot = -extents.f120x;
        }
        vector3 = Vector3.add(vector3, a.scaled(dot));
        a = C0393b.m94b(rawRotationMatrix);
        dot = Vector3.dot(center, a);
        if (dot > extents.f121y) {
            dot = extents.f121y;
        } else if (dot < (-extents.f121y)) {
            dot = -extents.f121y;
        }
        vector3 = Vector3.add(vector3, a.scaled(dot));
        Vector3 c = C0393b.m95c(rawRotationMatrix);
        float dot2 = Vector3.dot(center, c);
        if (dot2 > extents.f122z) {
            dot2 = extents.f122z;
        } else if (dot2 < (-extents.f122z)) {
            dot2 = -extents.f122z;
        }
        center = Vector3.add(vector3, c.scaled(dot2));
        vector3 = Vector3.subtract(center, sphere.getCenter());
        float dot3 = Vector3.dot(vector3, vector3);
        if (dot3 > sphere.getRadius() * sphere.getRadius()) {
            return false;
        }
        if (MathHelper.almostEqualRelativeAndAbs(dot3, 0.0f)) {
            Vector3 subtract = Vector3.subtract(center, box.getCenter());
            if (MathHelper.almostEqualRelativeAndAbs(Vector3.dot(subtract, subtract), 0.0f)) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: b */
    private static Vector3 m94b(Matrix matrix) {
        return new Vector3(matrix.data[1], matrix.data[5], matrix.data[9]);
    }

    /* renamed from: c */
    private static Vector3 m95c(Matrix matrix) {
        return new Vector3(matrix.data[2], matrix.data[6], matrix.data[10]);
    }
}
