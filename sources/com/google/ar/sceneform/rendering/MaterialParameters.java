package com.google.ar.sceneform.rendering;

import android.support.annotation.Nullable;
import com.google.android.filament.Material;
import com.google.android.filament.MaterialInstance;
import com.google.android.filament.Texture;
import com.google.android.filament.TextureSampler;
import com.google.android.filament.TextureSampler.MagFilter;
import com.google.android.filament.TextureSampler.MinFilter;
import com.google.android.filament.TextureSampler.WrapMode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Texture.Sampler;
import java.util.HashMap;
import java.util.Iterator;

final class MaterialParameters {
    private static final Sampler DEFAULT_SAMPLER = Sampler.builder().build();
    private boolean dirty;
    private final HashMap<String, Parameter> namedParameters = new HashMap();

    static abstract class Parameter implements Cloneable {
        String name;

        abstract boolean applyTo(MaterialInstance materialInstance);

        Parameter() {
        }

        public Parameter clone() {
            try {
                return (Parameter) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    static class Boolean2Parameter extends Parameter {
        /* renamed from: x */
        boolean f172x;
        /* renamed from: y */
        boolean f173y;

        Boolean2Parameter(String name, boolean x, boolean y) {
            this.name = name;
            this.f172x = x;
            this.f173y = y;
        }

        boolean applyTo(MaterialInstance materialInstance) {
            materialInstance.setParameter(this.name, this.f172x, this.f173y);
            return true;
        }
    }

    static class Boolean3Parameter extends Parameter {
        /* renamed from: x */
        boolean f174x;
        /* renamed from: y */
        boolean f175y;
        /* renamed from: z */
        boolean f176z;

        Boolean3Parameter(String name, boolean x, boolean y, boolean z) {
            this.name = name;
            this.f174x = x;
            this.f175y = y;
            this.f176z = z;
        }

        boolean applyTo(MaterialInstance materialInstance) {
            materialInstance.setParameter(this.name, this.f174x, this.f175y, this.f176z);
            return true;
        }
    }

    static class Boolean4Parameter extends Parameter {
        /* renamed from: w */
        boolean f177w;
        /* renamed from: x */
        boolean f178x;
        /* renamed from: y */
        boolean f179y;
        /* renamed from: z */
        boolean f180z;

        Boolean4Parameter(String name, boolean x, boolean y, boolean z, boolean w) {
            this.name = name;
            this.f178x = x;
            this.f179y = y;
            this.f180z = z;
            this.f177w = w;
        }

        boolean applyTo(MaterialInstance materialInstance) {
            materialInstance.setParameter(this.name, this.f178x, this.f179y, this.f180z, this.f177w);
            return true;
        }
    }

    static class BooleanParameter extends Parameter {
        /* renamed from: x */
        boolean f181x;

        BooleanParameter(String name, boolean x) {
            this.name = name;
            this.f181x = x;
        }

        boolean applyTo(MaterialInstance materialInstance) {
            materialInstance.setParameter(this.name, this.f181x);
            return true;
        }
    }

    static class ExternalTextureParameter extends Parameter {
        private final ExternalTexture externalTexture;

        ExternalTextureParameter(String name, ExternalTexture externalTexture) {
            this.name = name;
            this.externalTexture = externalTexture;
        }

        boolean applyTo(MaterialInstance materialInstance) {
            materialInstance.setParameter(this.name, this.externalTexture.getFilamentTexture(), getExternalFilamentSampler());
            return true;
        }

        private TextureSampler getExternalFilamentSampler() {
            TextureSampler filamentSampler = new TextureSampler();
            filamentSampler.setMinFilter(MinFilter.LINEAR);
            filamentSampler.setMagFilter(MagFilter.LINEAR);
            filamentSampler.setWrapModeS(WrapMode.CLAMP_TO_EDGE);
            filamentSampler.setWrapModeT(WrapMode.CLAMP_TO_EDGE);
            filamentSampler.setWrapModeR(WrapMode.CLAMP_TO_EDGE);
            return filamentSampler;
        }

        public Parameter clone() {
            return new ExternalTextureParameter(this.name, this.externalTexture);
        }
    }

    static class Float2Parameter extends Parameter {
        /* renamed from: x */
        float f182x;
        /* renamed from: y */
        float f183y;

        Float2Parameter(String name, float x, float y) {
            this.name = name;
            this.f182x = x;
            this.f183y = y;
        }

        boolean applyTo(MaterialInstance materialInstance) {
            materialInstance.setParameter(this.name, this.f182x, this.f183y);
            return true;
        }
    }

    static class Float3Parameter extends Parameter {
        /* renamed from: x */
        float f184x;
        /* renamed from: y */
        float f185y;
        /* renamed from: z */
        float f186z;

        Float3Parameter(String name, float x, float y, float z) {
            this.name = name;
            this.f184x = x;
            this.f185y = y;
            this.f186z = z;
        }

        boolean applyTo(MaterialInstance materialInstance) {
            materialInstance.setParameter(this.name, this.f184x, this.f185y, this.f186z);
            return true;
        }
    }

    static class Float4Parameter extends Parameter {
        /* renamed from: w */
        float f187w;
        /* renamed from: x */
        float f188x;
        /* renamed from: y */
        float f189y;
        /* renamed from: z */
        float f190z;

        Float4Parameter(String name, float x, float y, float z, float w) {
            this.name = name;
            this.f188x = x;
            this.f189y = y;
            this.f190z = z;
            this.f187w = w;
        }

        boolean applyTo(MaterialInstance materialInstance) {
            materialInstance.setParameter(this.name, this.f188x, this.f189y, this.f190z, this.f187w);
            return true;
        }
    }

    static class FloatParameter extends Parameter {
        /* renamed from: x */
        float f191x;

        FloatParameter(String name, float x) {
            this.name = name;
            this.f191x = x;
        }

        boolean applyTo(MaterialInstance materialInstance) {
            materialInstance.setParameter(this.name, this.f191x);
            return true;
        }
    }

    static class Int2Parameter extends Parameter {
        /* renamed from: x */
        int f192x;
        /* renamed from: y */
        int f193y;

        Int2Parameter(String name, int x, int y) {
            this.name = name;
            this.f192x = x;
            this.f193y = y;
        }

        boolean applyTo(MaterialInstance materialInstance) {
            materialInstance.setParameter(this.name, this.f192x, this.f193y);
            return true;
        }
    }

    static class Int3Parameter extends Parameter {
        /* renamed from: x */
        int f194x;
        /* renamed from: y */
        int f195y;
        /* renamed from: z */
        int f196z;

        Int3Parameter(String name, int x, int y, int z) {
            this.name = name;
            this.f194x = x;
            this.f195y = y;
            this.f196z = z;
        }

        boolean applyTo(MaterialInstance materialInstance) {
            materialInstance.setParameter(this.name, this.f194x, this.f195y, this.f196z);
            return true;
        }
    }

    static class Int4Parameter extends Parameter {
        /* renamed from: w */
        int f197w;
        /* renamed from: x */
        int f198x;
        /* renamed from: y */
        int f199y;
        /* renamed from: z */
        int f200z;

        Int4Parameter(String name, int x, int y, int z, int w) {
            this.name = name;
            this.f198x = x;
            this.f199y = y;
            this.f200z = z;
            this.f197w = w;
        }

        boolean applyTo(MaterialInstance materialInstance) {
            materialInstance.setParameter(this.name, this.f198x, this.f199y, this.f200z, this.f197w);
            return true;
        }
    }

    static class IntParameter extends Parameter {
        /* renamed from: x */
        int f201x;

        IntParameter(String name, int x) {
            this.name = name;
            this.f201x = x;
        }

        boolean applyTo(MaterialInstance materialInstance) {
            materialInstance.setParameter(this.name, this.f201x);
            return true;
        }
    }

    static class TextureParameter extends Parameter {
        @Nullable
        TextureSampler filamentSampler = null;
        @Nullable
        Sampler sampler = null;
        @Nullable
        Texture texture = null;

        TextureParameter(String name, Texture texture, @Nullable Sampler sampler) {
            this.name = name;
            this.texture = texture;
            this.sampler = sampler;
        }

        TextureParameter(String name, Texture texture, @Nullable TextureSampler filamentSampler) {
            this.name = name;
            this.texture = texture;
            this.filamentSampler = filamentSampler;
        }

        boolean applyTo(MaterialInstance materialInstance) {
            Texture texture = this.texture;
            if (texture == null) {
                return false;
            }
            boolean success = true;
            Texture textureData = texture.getData();
            if (textureData == null) {
                textureData = texture.getDefaultTexture().getData();
                if (textureData != null) {
                    success = false;
                } else {
                    throw new AssertionError("Unable to get default texture");
                }
            }
            if (this.filamentSampler != null) {
                materialInstance.setParameter(this.name, textureData, this.filamentSampler);
            } else if (this.sampler != null) {
                materialInstance.setParameter(this.name, textureData, MaterialParameters.convertTextureSampler(this.sampler));
            } else {
                success = false;
            }
            return success;
        }

        public Parameter clone() {
            if (this.texture != null) {
                TextureParameter result = new TextureParameter(this.name, this.texture, this.sampler);
                result.filamentSampler = this.filamentSampler;
                return result;
            }
            throw new AssertionError("Texture Parameter contains null texture.");
        }
    }

    MaterialParameters() {
    }

    void setBoolean(String name, boolean x) {
        this.namedParameters.put(name, new BooleanParameter(name, x));
        this.dirty = true;
    }

    void setBoolean2(String name, boolean x, boolean y) {
        this.namedParameters.put(name, new Boolean2Parameter(name, x, y));
        this.dirty = true;
    }

    void setBoolean3(String name, boolean x, boolean y, boolean z) {
        this.namedParameters.put(name, new Boolean3Parameter(name, x, y, z));
        this.dirty = true;
    }

    void setBoolean4(String name, boolean x, boolean y, boolean z, boolean w) {
        this.namedParameters.put(name, new Boolean4Parameter(name, x, y, z, w));
        this.dirty = true;
    }

    void setFloat(String name, float x) {
        this.namedParameters.put(name, new FloatParameter(name, x));
        this.dirty = true;
    }

    void setFloat2(String name, float x, float y) {
        this.namedParameters.put(name, new Float2Parameter(name, x, y));
        this.dirty = true;
    }

    void setFloat3(String name, float x, float y, float z) {
        this.namedParameters.put(name, new Float3Parameter(name, x, y, z));
        this.dirty = true;
    }

    void setFloat3(String name, Vector3 value) {
        this.namedParameters.put(name, new Float3Parameter(name, value.f120x, value.f121y, value.f122z));
        this.dirty = true;
    }

    void setFloat4(String name, float x, float y, float z, float w) {
        this.namedParameters.put(name, new Float4Parameter(name, x, y, z, w));
        this.dirty = true;
    }

    void setInt(String name, int x) {
        this.namedParameters.put(name, new IntParameter(name, x));
        this.dirty = true;
    }

    void setInt2(String name, int x, int y) {
        this.namedParameters.put(name, new Int2Parameter(name, x, y));
        this.dirty = true;
    }

    void setInt3(String name, int x, int y, int z) {
        this.namedParameters.put(name, new Int3Parameter(name, x, y, z));
        this.dirty = true;
    }

    void setInt4(String name, int x, int y, int z, int w) {
        this.namedParameters.put(name, new Int4Parameter(name, x, y, z, w));
        this.dirty = true;
    }

    void setTexture(String name, Texture texture) {
        setTexture(name, texture, DEFAULT_SAMPLER);
    }

    void setTexture(String name, Texture texture, Sampler sampler) {
        this.namedParameters.put(name, new TextureParameter(name, texture, sampler));
        this.dirty = true;
    }

    void setExternalTexture(String name, ExternalTexture externalTexture) {
        this.namedParameters.put(name, new ExternalTextureParameter(name, externalTexture));
        this.dirty = true;
    }

    void setTextureFilament(String name, Texture texture, TextureSampler filamentSampler) {
        this.namedParameters.put(name, new TextureParameter(name, texture, filamentSampler));
        this.dirty = true;
    }

    boolean isDirty() {
        return this.dirty;
    }

    void forceClearDirty() {
        this.dirty = false;
    }

    void applyTo(MaterialInstance materialInstance) {
        boolean z;
        Material material = materialInstance.getMaterial();
        boolean success = true;
        Iterator it = this.namedParameters.values().iterator();
        while (true) {
            z = false;
            if (!it.hasNext()) {
                break;
            }
            Parameter value = (Parameter) it.next();
            if (material.hasParameter(value.name)) {
                if (value.applyTo(materialInstance) && success) {
                    z = true;
                }
                success = z;
            }
        }
        if (!success) {
            z = true;
        }
        this.dirty = z;
    }

    void copyFrom(MaterialParameters other) {
        this.namedParameters.clear();
        merge(other);
    }

    void merge(MaterialParameters other) {
        for (Parameter value : other.namedParameters.values()) {
            Parameter clonedValue = value.clone();
            this.namedParameters.put(clonedValue.name, clonedValue);
        }
        this.dirty = true;
    }

    void mergeIfAbsent(MaterialParameters other) {
        for (Parameter value : other.namedParameters.values()) {
            if (!this.namedParameters.containsKey(value.name)) {
                Parameter clonedValue = value.clone();
                this.namedParameters.put(clonedValue.name, clonedValue);
            }
        }
        this.dirty = true;
    }

    private static TextureSampler convertTextureSampler(Sampler sampler) {
        TextureSampler convertedSampler = new TextureSampler();
        switch (sampler.getMinFilter()) {
            case NEAREST:
                convertedSampler.setMinFilter(MinFilter.NEAREST);
                break;
            case LINEAR:
                convertedSampler.setMinFilter(MinFilter.LINEAR);
                break;
            case NEAREST_MIPMAP_NEAREST:
                convertedSampler.setMinFilter(MinFilter.NEAREST_MIPMAP_NEAREST);
                break;
            case LINEAR_MIPMAP_NEAREST:
                convertedSampler.setMinFilter(MinFilter.LINEAR_MIPMAP_NEAREST);
                break;
            case NEAREST_MIPMAP_LINEAR:
                convertedSampler.setMinFilter(MinFilter.NEAREST_MIPMAP_LINEAR);
                break;
            case LINEAR_MIPMAP_LINEAR:
                convertedSampler.setMinFilter(MinFilter.LINEAR_MIPMAP_LINEAR);
                break;
            default:
                throw new IllegalArgumentException("Invalid MinFilter");
        }
        switch (sampler.getMagFilter()) {
            case NEAREST:
                convertedSampler.setMagFilter(MagFilter.NEAREST);
                break;
            case LINEAR:
                convertedSampler.setMagFilter(MagFilter.LINEAR);
                break;
            default:
                throw new IllegalArgumentException("Invalid MagFilter");
        }
        convertedSampler.setWrapModeS(convertWrapMode(sampler.getWrapModeS()));
        convertedSampler.setWrapModeT(convertWrapMode(sampler.getWrapModeT()));
        convertedSampler.setWrapModeR(convertWrapMode(sampler.getWrapModeR()));
        return convertedSampler;
    }

    private static WrapMode convertWrapMode(Sampler.WrapMode wrapMode) {
        switch (wrapMode) {
            case CLAMP_TO_EDGE:
                return WrapMode.CLAMP_TO_EDGE;
            case REPEAT:
                return WrapMode.REPEAT;
            case MIRRORED_REPEAT:
                return WrapMode.MIRRORED_REPEAT;
            default:
                throw new IllegalArgumentException("Invalid WrapMode");
        }
    }
}
