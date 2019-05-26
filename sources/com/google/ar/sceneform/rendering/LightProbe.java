package com.google.ar.sceneform.rendering;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.filament.Engine;
import com.google.android.filament.IndirectLight;
import com.google.android.filament.Texture;
import com.google.ar.sceneform.utilities.AndroidPreconditions;
import com.google.ar.sceneform.utilities.LoadHelper;
import com.google.ar.sceneform.utilities.Preconditions;
import com.google.ar.sceneform.utilities.RendercoreBufferUtils;
import com.google.ar.schemas.lull.Vec3;
import com.google.ar.schemas.rendercore.LightingDef;
import com.google.ar.schemas.rendercore.RendercoreBundleDef;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class LightProbe {
    private static final int CUBEMAP_FACE_COUNT = 6;
    private static final int CUBEMAP_MIN_WIDTH = 4;
    private static final int[] FACE_TO_FILAMENT_MAPPING = new int[]{3, 0, 4, 1, 5, 2};
    private static final int FLOATS_PER_VECTOR = 3;
    private static final int ID_NO_DATA = 0;
    private static final float LIGHT_ESTIMATE_OFFSET = 0.0f;
    private static final float LIGHT_ESTIMATE_SCALE = 1.8f;
    private static final int RGBM_BYTES_PER_PIXEL = 4;
    private static final int SH_ORDER = 3;
    private static final int SH_VECTORS_FOR_THIRD_ORDER = 9;
    private static final String TAG = LightProbe.class.getSimpleName();
    private IndirectLight ibl;
    private int id;
    private float intensity;
    private FloatBuffer irradiance;
    private int lastUsedId;
    private float lightEstimate;
    @Nullable
    private String name;
    private boolean ready;
    @Nullable
    private Texture reflectCubemap;

    public static final class Builder {
        @Nullable
        private Callable<InputStream> inputStreamCreator;
        private float intensity;
        @Nullable
        private String name;

        private Builder() {
            this.inputStreamCreator = null;
            this.intensity = 220.0f;
            this.name = null;
        }

        public Builder setIntensity(float intensity) {
            this.intensity = intensity;
            return this;
        }

        public Builder setAssetName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSource(Context context, Uri sourceUri) {
            Preconditions.checkNotNull(sourceUri, "Parameter \"sourceUri\" was null.");
            setSource(LoadHelper.fromUri(context, sourceUri));
            return this;
        }

        public Builder setSource(Context context, int resource) {
            setSource(context, LoadHelper.resourceToUri(context, resource));
            return this;
        }

        public Builder setSource(Callable<InputStream> inputStreamCreator) {
            Preconditions.checkNotNull(inputStreamCreator, "Parameter \"sourceInputStreamCallable\" was null.");
            this.inputStreamCreator = inputStreamCreator;
            return this;
        }

        public CompletableFuture<LightProbe> build() {
            if (this.inputStreamCreator != null) {
                LightProbe lightProbe = new LightProbe();
                CompletableFuture<LightProbe> result = lightProbe.loadInBackground(this.inputStreamCreator).thenApplyAsync(new -$$Lambda$LightProbe$Builder$V9SkkJwXGFiRC9o5vCPNpYAtIvw(lightProbe), ThreadPools.getMainExecutor());
                if (result != null) {
                    String access$500 = LightProbe.TAG;
                    String str = this.name;
                    StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 34);
                    stringBuilder.append("Unable to load LightProbe: name='");
                    stringBuilder.append(str);
                    stringBuilder.append("'");
                    return FutureHelper.logOnException(access$500, result, stringBuilder.toString());
                }
                throw new IllegalStateException("CompletableFuture result is null.");
            }
            throw new IllegalStateException("Light Probe source is NULL, this should never happen.");
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
        if (this.ibl != null) {
            this.ibl.setIntensity(this.lightEstimate * intensity);
            return;
        }
        throw new AssertionError("Light Probe is invalid.");
    }

    public float getIntensity() {
        return this.intensity;
    }

    int getId() {
        return this.id;
    }

    public boolean isReady() {
        return this.ready;
    }

    @Nullable
    IndirectLight getData() {
        return this.ibl;
    }

    private LightProbe(Builder builder) {
        this.reflectCubemap = null;
        this.name = null;
        this.id = 0;
        this.lightEstimate = 1.0f;
        this.intensity = builder.intensity;
        this.name = builder.name;
        this.ready = false;
    }

    private void buildFilamentResource(LightingDef lightingDef) {
        dispose();
        this.id = allocateNewId();
        if (lightingDef != null) {
            Texture cubemap = loadReflectCubemapFromLightingDef(lightingDef);
            if (cubemap != null) {
                this.reflectCubemap = cubemap;
                this.irradiance = loadIrradianceFromLightingDef(lightingDef);
                float[] irradianceData = this.irradiance.array();
                if (irradianceData != null) {
                    this.ibl = new com.google.android.filament.IndirectLight.Builder().reflections(cubemap).irradiance(3, irradianceData).intensity(this.intensity).build(EngineInstance.getEngine());
                    if (this.ibl != null) {
                        this.ready = true;
                        return;
                    }
                    throw new AssertionError("Light Probe is invalid.");
                }
                throw new AssertionError("Irradiance array is null.");
            }
            throw new IllegalStateException("Load reflection cubemap failed.");
        }
        throw new IllegalStateException("buildFilamentResource called but no resource is available to load.");
    }

    protected void finalize() throws Throwable {
        try {
            ThreadPools.getMainExecutor().execute(new -$$Lambda$LightProbe$IsI7PJJqonO6oEzXiCi7lY4HFBc());
        } catch (Exception e) {
            Log.e(TAG, "Error while Finalizing Light Probe.", e);
        } catch (Throwable th) {
            super.finalize();
        }
        super.finalize();
    }

    public void dispose() {
        AndroidPreconditions.checkUiThread();
        Engine engine = EngineInstance.getEngine();
        if (!(this.reflectCubemap == null || engine == null || !engine.isValid())) {
            engine.destroyTexture(this.reflectCubemap);
        }
        this.reflectCubemap = null;
        this.id = 0;
        this.ready = false;
    }

    public void setLightEstimate(float estimate) {
        this.lightEstimate = Math.min((LIGHT_ESTIMATE_SCALE * estimate) + 0.0f, 1.0f);
        if (this.ibl != null) {
            this.ibl.setIntensity(this.intensity * this.lightEstimate);
        }
    }

    private CompletableFuture<LightingDef> loadInBackground(Callable<InputStream> inputStreamCreator) {
        return CompletableFuture.supplyAsync(new -$$Lambda$LightProbe$dkPR49UQJGESgjfjsx0q9MnXYt8(this, inputStreamCreator), ThreadPools.getThreadPoolExecutor());
    }

    public static /* synthetic */ LightingDef lambda$loadInBackground$1(LightProbe lightProbe, Callable inputStreamCreator) {
        InputStream inputStream;
        if (inputStreamCreator != null) {
            ByteBuffer assetData = null;
            try {
                inputStream = (InputStream) inputStreamCreator.call();
                assetData = RendercoreBufferUtils.readStream(inputStream);
                if (inputStream != null) {
                    inputStream.close();
                }
                if (assetData != null) {
                    try {
                        RendercoreBundleDef rcb = RendercoreBundle.tryLoadRendercoreBundle(assetData);
                        if (rcb != null) {
                            int lightingDefsLength = rcb.lightingDefsLength();
                            if (lightingDefsLength >= 1) {
                                int lightProbeIndex = -1;
                                if (lightProbe.name != null) {
                                    for (int i = 0; i < lightingDefsLength; i++) {
                                        if (rcb.lightingDefs(i).name().equals(lightProbe.name)) {
                                            lightProbeIndex = i;
                                            break;
                                        }
                                    }
                                    if (lightProbeIndex < 0) {
                                        String str = lightProbe.name;
                                        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 41);
                                        stringBuilder.append("Light Probe asset \"");
                                        stringBuilder.append(str);
                                        stringBuilder.append("\" not found in bundle.");
                                        throw new IllegalArgumentException(stringBuilder.toString());
                                    }
                                } else {
                                    lightProbeIndex = 0;
                                }
                                LightingDef lightingDef = rcb.lightingDefs(lightProbeIndex);
                                if (lightingDef != null) {
                                    return lightingDef;
                                }
                                throw new IllegalStateException("LightingDef is invalid.");
                            }
                            throw new IllegalStateException("Content does not contain any Light Probe data.");
                        }
                        throw new AssertionError("The Rendercore bundle containing the Light Probe could not be loaded.");
                    } catch (VersionException e) {
                        throw new CompletionException(e);
                    }
                }
                throw new AssertionError("The Rendercore bundle containing the Light Probe could not be loaded.");
            } catch (Exception e2) {
                throw new CompletionException(e2);
            } catch (Throwable th) {
                r0.addSuppressed(th);
            }
        }
        throw new IllegalArgumentException("Invalid source.");
    }

    private int allocateNewId() {
        int newId = this.lastUsedId + 1;
        if (newId != 0) {
            this.lastUsedId = newId;
            return newId;
        }
        throw new AssertionError("LightProbe ran out of unique IDs (reloaded too frequently).");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.google.android.filament.Texture loadReflectCubemapFromLightingDef(com.google.ar.schemas.rendercore.LightingDef r30) {
        /*
        r0 = r30;
        r1 = "Parameter \"lightingDef\" was null.";
        com.google.ar.sceneform.utilities.Preconditions.checkNotNull(r0, r1);
        r1 = com.google.ar.sceneform.rendering.EngineInstance.getEngine();
        r2 = r30.cubeLevelsLength();
        r3 = 1;
        if (r2 < r3) goto L_0x0152;
    L_0x0012:
        r4 = 0;
        r5 = r0.cubeLevels(r4);
        r6 = r5.faces(r4);
        r7 = new android.graphics.BitmapFactory$Options;
        r7.<init>();
        r7.inPremultiplied = r4;
        r7.inScaled = r4;
        r7.inJustDecodeBounds = r3;
        r3 = r6.dataAsByteBuffer();
        r8 = r3.array();
        r9 = r3.arrayOffset();
        r10 = r3.position();
        r9 = r9 + r10;
        r10 = r3.limit();
        r11 = r3.position();
        r10 = r10 - r11;
        android.graphics.BitmapFactory.decodeByteArray(r8, r9, r10, r7);
        r11 = r7.outWidth;
        r12 = r7.outHeight;
        r13 = 4;
        if (r11 < r13) goto L_0x0127;
    L_0x004a:
        if (r12 < r13) goto L_0x0127;
    L_0x004c:
        if (r11 != r12) goto L_0x0127;
    L_0x004e:
        r14 = new com.google.android.filament.Texture$Builder;
        r14.<init>();
        r14 = r14.width(r11);
        r14 = r14.height(r12);
        r14 = r14.levels(r2);
        r15 = com.google.android.filament.Texture.InternalFormat.RGBM;
        r14 = r14.format(r15);
        r15 = com.google.android.filament.Texture.Sampler.SAMPLER_CUBEMAP;
        r14 = r14.sampler(r15);
        r14 = r14.build(r1);
        r15 = r11 * r12;
        r15 = r15 * r13;
        r13 = 6;
        r16 = r3;
        r3 = new int[r13];
        r7.inJustDecodeBounds = r4;
        r29 = r11;
        r11 = r4;
        r4 = r12;
        r12 = r29;
    L_0x007f:
        if (r11 >= r2) goto L_0x011e;
    L_0x0081:
        r17 = r15 * 6;
        r13 = java.nio.ByteBuffer.allocateDirect(r17);
        r19 = r2;
        r2 = r0.cubeLevels(r11);
        r17 = 0;
    L_0x008f:
        r20 = r17;
        r21 = r5;
        r0 = r20;
        r5 = 6;
        if (r0 >= r5) goto L_0x00f5;
    L_0x0098:
        r17 = FACE_TO_FILAMENT_MAPPING;
        r5 = r17[r0];
        r17 = r2.faces(r5);
        r20 = r15 * r0;
        r3[r0] = r20;
        r18 = r17.dataAsByteBuffer();
        r22 = r2;
        r2 = r18.array();
        r20 = r18.arrayOffset();
        r23 = r18.position();
        r24 = r5;
        r5 = r20 + r23;
        r20 = r18.limit();
        r23 = r18.position();
        r25 = r6;
        r6 = r20 - r23;
        r26 = r8;
        r8 = android.graphics.BitmapFactory.decodeByteArray(r2, r5, r6, r7);
        r27 = r2;
        r2 = r8.getWidth();
        if (r2 != r12) goto L_0x00eb;
    L_0x00d5:
        r2 = r8.getHeight();
        if (r2 != r4) goto L_0x00eb;
    L_0x00db:
        r8.copyPixelsToBuffer(r13);
        r17 = r0 + 1;
        r5 = r21;
        r2 = r22;
        r6 = r25;
        r8 = r26;
        r0 = r30;
        goto L_0x008f;
    L_0x00eb:
        r2 = new java.lang.AssertionError;
        r28 = r0;
        r0 = "All cube map textures must have the same size";
        r2.<init>(r0);
        throw r2;
    L_0x00f5:
        r22 = r2;
        r25 = r6;
        r26 = r8;
        r13.rewind();
        r0 = new com.google.android.filament.Texture$PixelBufferDescriptor;
        r2 = com.google.android.filament.Texture.Format.RGBM;
        r5 = com.google.android.filament.Texture.Type.UBYTE;
        r0.<init>(r13, r2, r5);
        r14.setImage(r1, r11, r0, r3);
        r12 = r12 >> 1;
        r4 = r4 >> 1;
        r2 = r12 * r4;
        r5 = 4;
        r15 = r2 * 4;
        r11 = r11 + 1;
        r2 = r19;
        r5 = r21;
        r0 = r30;
        r13 = 6;
        goto L_0x007f;
    L_0x011e:
        r19 = r2;
        r21 = r5;
        r25 = r6;
        r26 = r8;
        return r14;
    L_0x0127:
        r19 = r2;
        r16 = r3;
        r21 = r5;
        r25 = r6;
        r26 = r8;
        r0 = new java.lang.IllegalStateException;
        r2 = 66;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r2);
        r2 = "Lighting cubemap has invalid dimensions: ";
        r3.append(r2);
        r3.append(r11);
        r2 = " x ";
        r3.append(r2);
        r3.append(r12);
        r2 = r3.toString();
        r0.<init>(r2);
        throw r0;
    L_0x0152:
        r19 = r2;
        r0 = new java.lang.IllegalStateException;
        r2 = "Lighting cubemap has no image data.";
        r0.<init>(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ar.sceneform.rendering.LightProbe.loadReflectCubemapFromLightingDef(com.google.ar.schemas.rendercore.LightingDef):com.google.android.filament.Texture");
    }

    private static FloatBuffer loadIrradianceFromLightingDef(LightingDef lightingDef) {
        Preconditions.checkNotNull(lightingDef, "Parameter \"lightingDef\" was null.");
        int shVectorCount = lightingDef.shCoefficientsLength();
        if (shVectorCount >= 9) {
            FloatBuffer irradiance = FloatBuffer.allocate(shVectorCount * 3);
            if (irradiance != null) {
                for (int v = 0; v < shVectorCount; v++) {
                    Vec3 shVector = lightingDef.shCoefficients(v);
                    irradiance.put(shVector.m202x());
                    irradiance.put(shVector.m203y());
                    irradiance.put(shVector.m204z());
                }
                irradiance.rewind();
                return irradiance;
            }
            throw new IllegalStateException("Float buffer allocation failed.");
        }
        throw new IllegalStateException("Too few SH vectors for the current Order (3).");
    }
}
