package com.google.ar.sceneform.rendering;

import android.support.annotation.Nullable;
import com.google.ar.sceneform.collision.Box;
import com.google.ar.sceneform.collision.CollisionShape;
import com.google.ar.sceneform.collision.Sphere;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.schemas.rendercore.RendercoreBundleDef;
import com.google.ar.schemas.rendercore.SuggestedCollisionShapeDef;
import java.io.IOException;
import java.nio.ByteBuffer;

public final class RendercoreBundle {
    public static final float RCB_MAJOR_VERSION = 0.52f;
    public static final int RCB_MINOR_VERSION = 1;
    private static final char[] RCB_SIGNATURE = new char[]{'R', 'B', 'U', 'N'};
    private static final int SIGNATURE_OFFSET = 4;
    private static final String TAG = RendercoreBundle.class.getSimpleName();

    static class VersionException extends Exception {
        public VersionException(String message) {
            super(message);
        }
    }

    @Nullable
    public static RendercoreBundleDef tryLoadRendercoreBundle(ByteBuffer buffer) throws VersionException {
        if (!isRendercoreBundle(buffer)) {
            return null;
        }
        buffer.rewind();
        RendercoreBundleDef bundle = RendercoreBundleDef.getRootAsRendercoreBundleDef(buffer);
        float majorVersion = bundle.version().major();
        int minorVersion = bundle.version().minor();
        if (0.52f >= bundle.version().major()) {
            return bundle;
        }
        StringBuilder stringBuilder = new StringBuilder(141);
        stringBuilder.append("Rendercore bundle (.rcb) version not supported, max version supported is 0.52.X. Version requested for loading is ");
        stringBuilder.append(majorVersion);
        stringBuilder.append(".");
        stringBuilder.append(minorVersion);
        throw new VersionException(stringBuilder.toString());
    }

    public static CollisionShape readCollisionGeometry(RendercoreBundleDef rcb) throws IOException {
        SuggestedCollisionShapeDef shape = rcb.suggestedCollisionShape();
        switch (shape.type()) {
            case 0:
                Box box = new Box();
                box.setCenter(new Vector3(shape.center().m202x(), shape.center().m203y(), shape.center().m204z()));
                box.setSize(new Vector3(shape.size().m202x(), shape.size().m203y(), shape.size().m204z()));
                return box;
            case 1:
                Sphere sphere = new Sphere();
                sphere.setCenter(new Vector3(shape.center().m202x(), shape.center().m203y(), shape.center().m204z()));
                sphere.setRadius(shape.size().m202x());
                return sphere;
            default:
                throw new IOException("Invalid collisionCollisionGeometry type.");
        }
    }

    public static boolean isRendercoreBundle(ByteBuffer buffer) {
        for (int i = 0; i < RCB_SIGNATURE.length; i++) {
            if (buffer.get(i + 4) != RCB_SIGNATURE[i]) {
                return false;
            }
        }
        return true;
    }
}
