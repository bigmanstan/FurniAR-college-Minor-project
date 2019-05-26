package com.google.ar.sceneform.rendering;

import android.util.Log;
import com.google.android.filament.TextureSampler.MagFilter;
import com.google.android.filament.TextureSampler.MinFilter;
import com.google.android.filament.TextureSampler.WrapMode;
import com.google.ar.schemas.lull.ModelInstanceDef;
import com.google.ar.schemas.lull.TextureDef;
import java.nio.ByteBuffer;

public class LullModel {
    private static final String TAG = LullModel.class.getName();
    public static final WrapMode[] fromLullWrapMode = new WrapMode[]{WrapMode.CLAMP_TO_EDGE, WrapMode.CLAMP_TO_EDGE, WrapMode.MIRRORED_REPEAT, WrapMode.CLAMP_TO_EDGE, WrapMode.REPEAT};

    public static boolean isLullModel(ByteBuffer buffer) {
        return buffer.limit() > 4 && buffer.get(0) < (byte) 32 && buffer.get(1) == (byte) 0 && buffer.get(2) == (byte) 0;
    }

    public static int getByteCountPerVertex(ModelInstanceDef modelInstanceDef) {
        int vertexAttributeCount = modelInstanceDef.vertexAttributesLength();
        int bytesPerVertex = 0;
        for (int i = 0; i < vertexAttributeCount; i++) {
            switch (modelInstanceDef.vertexAttributes(i).type()) {
                case 1:
                case 5:
                case 7:
                    bytesPerVertex += 4;
                    break;
                case 2:
                case 6:
                    bytesPerVertex += 8;
                    break;
                case 3:
                    bytesPerVertex += 12;
                    break;
                case 4:
                    bytesPerVertex += 16;
                    break;
                default:
                    break;
            }
        }
        return bytesPerVertex;
    }

    public static MinFilter fromLullToMinFilter(TextureDef textureDef) {
        switch (textureDef.minFilter()) {
            case 0:
                return MinFilter.NEAREST;
            case 1:
                return MinFilter.LINEAR;
            case 2:
                return MinFilter.NEAREST_MIPMAP_NEAREST;
            case 3:
                return MinFilter.LINEAR_MIPMAP_NEAREST;
            case 4:
                return MinFilter.NEAREST_MIPMAP_LINEAR;
            case 5:
                return MinFilter.LINEAR_MIPMAP_LINEAR;
            default:
                Log.e(TAG, String.valueOf(textureDef.name()).concat(": Sampler has unknown min filter"));
                return MinFilter.NEAREST;
        }
    }

    public static MagFilter fromLullToMagFilter(TextureDef textureDef) {
        switch (textureDef.magFilter()) {
            case 0:
                return MagFilter.NEAREST;
            case 1:
                return MagFilter.LINEAR;
            default:
                Log.e(TAG, String.valueOf(textureDef.name()).concat(": Sampler has unknown mag filter"));
                return MagFilter.NEAREST;
        }
    }
}
