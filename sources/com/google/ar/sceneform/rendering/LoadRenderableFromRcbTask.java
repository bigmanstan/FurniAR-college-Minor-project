package com.google.ar.sceneform.rendering;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.filament.Engine;
import com.google.android.filament.IndexBuffer;
import com.google.android.filament.IndexBuffer.Builder;
import com.google.android.filament.IndexBuffer.Builder.IndexType;
import com.google.android.filament.TextureSampler;
import com.google.android.filament.TextureSampler.MagFilter;
import com.google.android.filament.TextureSampler.MinFilter;
import com.google.android.filament.TextureSampler.WrapMode;
import com.google.android.filament.VertexBuffer;
import com.google.android.filament.VertexBuffer.AttributeType;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Texture.Usage;
import com.google.ar.sceneform.utilities.LoadHelper;
import com.google.ar.sceneform.utilities.Preconditions;
import com.google.ar.sceneform.utilities.RendercoreBufferUtils;
import com.google.ar.schemas.lull.ModelDef;
import com.google.ar.schemas.lull.ModelIndexRange;
import com.google.ar.schemas.lull.ModelInstanceDef;
import com.google.ar.schemas.lull.Vec3;
import com.google.ar.schemas.lull.VertexAttribute;
import com.google.ar.schemas.rendercore.CompiledMaterialDef;
import com.google.ar.schemas.rendercore.RendercoreBundleDef;
import com.google.ar.schemas.rendercore.SamplerDef;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

class LoadRenderableFromRcbTask<T extends Renderable> {
    private static final int BYTES_PER_FLOAT = 4;
    private static final int BYTES_PER_INT = 4;
    private static final int BYTES_PER_SHORT = 2;
    private static final String TAG = LoadRenderableFromRcbTask.class.getSimpleName();
    private final ArrayList<Integer> compiledMaterialIndex = new ArrayList();
    private final ArrayList<Material> compiledMaterials = new ArrayList();
    private final Context context;
    private final LinkedHashMap<String, TextureSampler> filamentSamplers = new LinkedHashMap();
    private ByteBuffer indexBufferData;
    private int indexCount;
    private IndexType indexType;
    private final ArrayList<MaterialParameters> materialParameters = new ArrayList();
    private int meshCount;
    private ModelDef modelDef;
    private ModelInstanceDef modelInstanceDef;
    private final T renderable;
    private final RenderableInternalData renderableData;
    @Nullable
    private final Uri renderableUri;
    private int textureCount;
    private final ArrayList<ModelTexture> textures = new ArrayList();
    private ByteBuffer vertexBufferData;
    private int vertexCount;
    private int vertexStride;

    private static class ModelTexture {
        @Nullable
        Texture data = null;
        String name;

        ModelTexture(String name) {
            this.name = name;
        }
    }

    LoadRenderableFromRcbTask(Context context, T renderable, @Nullable Uri renderableUri) {
        this.context = context;
        this.renderable = renderable;
        this.renderableData = renderable.getRenderableData();
        this.renderableUri = renderableUri;
    }

    public CompletableFuture<T> downloadAndProcessRenderable(Callable<InputStream> inputStreamCreator) {
        CompletableFuture<T> result = CompletableFuture.supplyAsync(new -$$Lambda$LoadRenderableFromRcbTask$qMXQtMDAZ5nF42Dk6jRuCpmhFuI(this, inputStreamCreator), ThreadPools.getThreadPoolExecutor()).thenApplyAsync(new -$$Lambda$LoadRenderableFromRcbTask$nxSmjws024HLog7iE8ClKkyoOQY(), ThreadPools.getMainExecutor());
        result.exceptionally(-$$Lambda$LoadRenderableFromRcbTask$fnM7KFMh4EUFlxlOqVPxZ7qNzqk.INSTANCE);
        return result;
    }

    public static /* synthetic */ RendercoreBundleDef lambda$downloadAndProcessRenderable$0(LoadRenderableFromRcbTask this_, Callable inputStreamCreator) {
        RendercoreBundleDef rcb = this_.byteBufferToRcb(this_.inputStreamToByteBuffer(inputStreamCreator));
        this_.setCollisionShape(rcb);
        this_.loadModel(rcb);
        return rcb;
    }

    public static /* synthetic */ Renderable lambda$downloadAndProcessRenderable$1(LoadRenderableFromRcbTask this_, RendercoreBundleDef rcb) {
        this_.loadTextures(rcb);
        this_.buildMaterialParameters(rcb);
        return this_.setupFilament(rcb);
    }

    static /* synthetic */ Renderable lambda$downloadAndProcessRenderable$2(Throwable throwable) {
        throw new CompletionException(throwable);
    }

    private ByteBuffer inputStreamToByteBuffer(Callable<InputStream> inputStreamCreator) {
        InputStream inputStream;
        try {
            inputStream = (InputStream) inputStreamCreator.call();
            ByteBuffer result = RendercoreBufferUtils.readStream(inputStream);
            if (inputStream != null) {
                inputStream.close();
            }
            ByteBuffer inputStream2 = result;
            if (inputStream2 != null) {
                return inputStream2;
            }
            throw new AssertionError("Failed reading data from stream");
        } catch (Exception e) {
            throw new CompletionException(e);
        } catch (Throwable th) {
            r1.addSuppressed(th);
        }
    }

    private RendercoreBundleDef byteBufferToRcb(ByteBuffer assetData) {
        try {
            RendercoreBundleDef rcb = RendercoreBundle.tryLoadRendercoreBundle(assetData);
            if (rcb != null) {
                return rcb;
            }
            String valueOf = String.valueOf(this.renderableUri);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 20);
            stringBuilder.append("No RCB file at uri: ");
            stringBuilder.append(valueOf);
            throw new AssertionError(stringBuilder.toString());
        } catch (VersionException e) {
            throw new CompletionException(e);
        }
    }

    private RendercoreBundleDef setCollisionShape(RendercoreBundleDef rcb) {
        try {
            this.renderable.collisionShape = RendercoreBundle.readCollisionGeometry(rcb);
            return rcb;
        } catch (IOException e) {
            throw new CompletionException("Unable to get collision geometry from rcb", e);
        }
    }

    private RendercoreBundleDef loadModel(RendercoreBundleDef rcb) {
        this.modelDef = rcb.model();
        Preconditions.checkNotNull(this.modelDef, "Model error: ModelDef is invalid.");
        this.modelInstanceDef = this.modelDef.lods(0);
        Preconditions.checkNotNull(this.modelInstanceDef, "Lull Model error: ModelInstanceDef is invalid.");
        buildGeometry();
        return rcb;
    }

    private T setupFilament(RendercoreBundleDef rcb) {
        Preconditions.checkNotNull(rcb);
        setupFilamentGeometryBuffers();
        setupFilamentMaterials(rcb);
        setupRenderableData();
        this.renderable.getId().update();
        return this.renderable;
    }

    private void setupFilamentGeometryBuffers() {
        Engine engine = EngineInstance.getEngine();
        IndexBuffer indexBuffer = new Builder().indexCount(this.indexCount).bufferType(this.indexType).build(engine);
        indexBuffer.setBuffer(engine, this.indexBufferData);
        this.renderableData.setIndexBuffer(indexBuffer);
        VertexBuffer.Builder vertexBufferBuilder = new VertexBuffer.Builder().vertexCount(this.vertexCount).bufferCount(1);
        int vertexAttributeCount = this.modelInstanceDef.vertexAttributesLength();
        int byteOffset = 0;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < vertexAttributeCount) {
                VertexAttribute attribute = this.modelInstanceDef.vertexAttributes(i2);
                VertexBuffer.VertexAttribute filamentAttribute = getFilamentVertexAttribute(attribute.usage());
                if (filamentAttribute != null) {
                    vertexBufferBuilder.attribute(filamentAttribute, 0, getFilamentAttributeType(attribute.type()), byteOffset, this.vertexStride);
                    if (isAttributeNormalized(attribute.usage())) {
                        vertexBufferBuilder.normalized(filamentAttribute);
                    }
                }
                byteOffset += getVertexAttributeTypeSizeInBytes(attribute.type());
                i = i2 + 1;
            } else {
                VertexBuffer vertexBuffer = vertexBufferBuilder.build(engine);
                vertexBuffer.setBufferAt(engine, 0, this.vertexBufferData);
                this.renderableData.setVertexBuffer(vertexBuffer);
                return;
            }
        }
    }

    private void setupFilamentMaterials(RendercoreBundleDef rcb) {
        int compiledMaterialLength = rcb.compiledMaterialsLength();
        int i = 0;
        while (i < compiledMaterialLength) {
            CompiledMaterialDef compiledMaterial = rcb.compiledMaterials(i);
            int materialId = compiledMaterial.compiledMaterialAsByteBuffer().hashCode();
            try {
                Material material = (Material) Material.builder().setSource(RendercoreBufferUtils.copyByteBuffer(compiledMaterial.compiledMaterialAsByteBuffer())).setRegistryId(Integer.valueOf(materialId)).build().getNow(null);
                if (material != null) {
                    this.compiledMaterials.add(material);
                    i++;
                } else {
                    throw new AssertionError("Material wasn't loaded.");
                }
            } catch (IOException e) {
                throw new CompletionException("Failed to create material", e);
            }
        }
    }

    private void setupRenderableData() {
        Vec3 modelMinAabb = this.modelDef.boundingBox().min();
        Vector3 minAabb = new Vector3(modelMinAabb.m202x(), modelMinAabb.m203y(), modelMinAabb.m204z());
        Vec3 modelMaxAabb = this.modelDef.boundingBox().max();
        Vector3 extentsAabb = Vector3.subtract(new Vector3(modelMaxAabb.m202x(), modelMaxAabb.m203y(), modelMaxAabb.m204z()), minAabb).scaled(0.5f);
        Vector3 centerAabb = Vector3.add(minAabb, extentsAabb);
        this.renderableData.setExtentsAabb(extentsAabb);
        this.renderableData.setCenterAabb(centerAabb);
        ArrayList<Material> materialBindings = this.renderable.getMaterialBindings();
        materialBindings.clear();
        int m = 0;
        while (m < r0.meshCount) {
            ModelIndexRange range = r0.modelInstanceDef.ranges(m);
            int start = (int) range.start();
            int end = (int) range.end();
            Material material = ((Material) r0.compiledMaterials.get(((Integer) r0.compiledMaterialIndex.get(m)).intValue())).makeCopy();
            material.copyMaterialParameters((MaterialParameters) r0.materialParameters.get(m));
            MeshData meshData = new MeshData();
            materialBindings.add(material);
            meshData.indexStart = start;
            meshData.indexEnd = end;
            Vec3 modelMinAabb2 = modelMinAabb;
            r0.renderableData.getMeshes().add(meshData);
            m++;
            modelMinAabb = modelMinAabb2;
        }
    }

    private void buildGeometry() {
        ByteBuffer vertexData = this.modelInstanceDef.vertexDataAsByteBuffer();
        Preconditions.checkNotNull(vertexData, "Model Instance geometry data is invalid (vertexData is null).");
        int vertexDataCount = this.modelInstanceDef.vertexDataLength();
        this.meshCount = this.modelInstanceDef.rangesLength();
        this.vertexCount = vertexDataCount / LullModel.getByteCountPerVertex(this.modelInstanceDef);
        if (this.modelInstanceDef.indices32Length() > 0) {
            this.indexCount = this.modelInstanceDef.indices32Length();
            this.indexType = IndexType.UINT;
            this.indexBufferData = ByteBuffer.allocateDirect(this.indexCount * 4);
            this.indexBufferData.put(this.modelInstanceDef.indices32AsByteBuffer());
        } else if (this.modelInstanceDef.indices16Length() > 0) {
            this.indexCount = this.modelInstanceDef.indices16Length();
            this.indexType = IndexType.USHORT;
            this.indexBufferData = ByteBuffer.allocateDirect(this.indexCount * 2);
            this.indexBufferData.put(this.modelInstanceDef.indices16AsByteBuffer());
        } else {
            throw new AssertionError("Model Instance geometry data is invalid (model has no index data).");
        }
        this.indexBufferData.flip();
        this.vertexBufferData = ByteBuffer.allocateDirect(vertexData.remaining());
        Preconditions.checkNotNull(this.vertexBufferData, "Failed to allocate geometry for FilamentModel.");
        this.vertexBufferData.put(vertexData);
        this.vertexBufferData.flip();
        int i = 0;
        this.vertexStride = 0;
        int vertexAttributeCount = this.modelInstanceDef.vertexAttributesLength();
        while (i < vertexAttributeCount) {
            this.vertexStride += getVertexAttributeTypeSizeInBytes(this.modelInstanceDef.vertexAttributes(i).type());
            i++;
        }
    }

    private RendercoreBundleDef loadTextures(RendercoreBundleDef rcb) {
        RendercoreBundleDef rendercoreBundleDef = rcb;
        this.textureCount = rcb.samplersLength();
        int t = 0;
        while (t < r0.textureCount) {
            SamplerDef samplerDef = rendercoreBundleDef.samplers(t);
            ModelTexture texture = new ModelTexture(samplerDef.name());
            r0.textures.add(texture);
            int rawUsage = samplerDef.params().usageType();
            Usage[] usageValues = Usage.values();
            if (rawUsage < usageValues.length) {
                CompletableFuture<Texture> textureFuture;
                Usage usage = usageValues[rawUsage];
                String file = samplerDef.file();
                if (samplerDef.dataLength() != 0) {
                    ByteBuffer data = samplerDef.dataAsByteBuffer();
                    ByteArrayInputStream wrappedInputStream = new ByteArrayInputStream(data.array(), data.arrayOffset(), data.capacity());
                    wrappedInputStream.skip((long) data.position());
                    Texture.Builder usage2 = Texture.builder().setUsage(usage);
                    boolean z = true;
                    if (rcb.version().major() <= 0.51f) {
                        if (rcb.version().major() != 0.51f || rcb.version().minor() <= 1) {
                            z = false;
                            textureFuture = usage2.setPremultiplied(z).setSource(new -$$Lambda$LoadRenderableFromRcbTask$7_jtDu88FMNNSTruOc9Clhv8WBs(wrappedInputStream)).build();
                        }
                    }
                    textureFuture = usage2.setPremultiplied(z).setSource(new -$$Lambda$LoadRenderableFromRcbTask$7_jtDu88FMNNSTruOc9Clhv8WBs(wrappedInputStream)).build();
                } else if (TextUtils.isEmpty(file)) {
                    throw new IllegalStateException("Unable to load texture, no sampler definition or file source");
                } else {
                    textureFuture = Texture.builder().setUsage(usage).setSource(r0.context, LoadHelper.resolveUri(Uri.parse(file), r0.renderableUri)).build();
                }
                textureFuture.thenApply(new -$$Lambda$LoadRenderableFromRcbTask$XsVQHhwv3v5waX8SvCq48H7W2y8(texture)).exceptionally(-$$Lambda$LoadRenderableFromRcbTask$jYpsszF3wSqrX9KurFlGtv80JL0.INSTANCE);
                if (textureFuture.isDone()) {
                    TextureSampler filamentSampler = new TextureSampler();
                    filamentSampler.setMinFilter(MinFilter.values()[samplerDef.params().minFilter()]);
                    filamentSampler.setMagFilter(MagFilter.values()[samplerDef.params().magFilter()]);
                    filamentSampler.setWrapModeR(WrapMode.values()[samplerDef.params().wrapR()]);
                    filamentSampler.setWrapModeS(WrapMode.values()[samplerDef.params().wrapS()]);
                    filamentSampler.setWrapModeT(WrapMode.values()[samplerDef.params().wrapT()]);
                    if (!r0.filamentSamplers.containsKey(samplerDef.name())) {
                        r0.filamentSamplers.put(samplerDef.name(), filamentSampler);
                    }
                    t++;
                } else {
                    throw new AssertionError("Texture not built immediately");
                }
            }
            StringBuilder stringBuilder = new StringBuilder(34);
            stringBuilder.append("Invalid Texture Usage: ");
            stringBuilder.append(rawUsage);
            throw new AssertionError(stringBuilder.toString());
        }
        return rendercoreBundleDef;
    }

    static /* synthetic */ Texture lambda$loadTextures$5(Throwable throwable) {
        throw new CompletionException("Texture Load Error", throwable);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.ar.schemas.rendercore.RendercoreBundleDef buildMaterialParameters(com.google.ar.schemas.rendercore.RendercoreBundleDef r43) {
        /*
        r42 = this;
        r0 = r42;
        r1 = r43;
        r2 = r43.materialsLength();
        if (r2 != 0) goto L_0x0012;
    L_0x000a:
        r3 = TAG;
        r4 = "Building materials but the sceneform bundle has no materials";
        android.util.Log.i(r3, r4);
        return r1;
    L_0x0012:
        r4 = 0;
    L_0x0013:
        r5 = r0.meshCount;
        if (r4 >= r5) goto L_0x037c;
    L_0x0017:
        r5 = r4;
        if (r2 > r4) goto L_0x001c;
    L_0x001a:
        r5 = r2 + -1;
    L_0x001c:
        r6 = r1.materials(r5);
        if (r6 != 0) goto L_0x0044;
    L_0x0022:
        r7 = TAG;
        r8 = 29;
        r9 = new java.lang.StringBuilder;
        r9.<init>(r8);
        r8 = "Material ";
        r9.append(r8);
        r9.append(r4);
        r8 = " is null.";
        r9.append(r8);
        r8 = r9.toString();
        android.util.Log.e(r7, r8);
        r31 = r2;
        goto L_0x0374;
    L_0x0044:
        r7 = r0.compiledMaterialIndex;
        r8 = r6.compiledIndex();
        r8 = java.lang.Integer.valueOf(r8);
        r7.add(r8);
        r7 = new com.google.ar.schemas.rendercore.ParameterDef;
        r7.<init>();
        r8 = new com.google.ar.schemas.rendercore.ParameterInitDef;
        r8.<init>();
        r9 = new com.google.ar.schemas.rendercore.ScalarInit;
        r9.<init>();
        r10 = new com.google.ar.schemas.rendercore.Vec2Init;
        r10.<init>();
        r11 = new com.google.ar.schemas.rendercore.Vec3Init;
        r11.<init>();
        r12 = new com.google.ar.schemas.rendercore.Vec4Init;
        r12.<init>();
        r13 = new com.google.ar.schemas.rendercore.BoolInit;
        r13.<init>();
        r14 = new com.google.ar.schemas.rendercore.BoolVec2Init;
        r14.<init>();
        r15 = new com.google.ar.schemas.rendercore.BoolVec3Init;
        r15.<init>();
        r16 = new com.google.ar.schemas.rendercore.BoolVec4Init;
        r16.<init>();
        r17 = r16;
        r16 = new com.google.ar.schemas.rendercore.IntInit;
        r16.<init>();
        r18 = r16;
        r16 = new com.google.ar.schemas.rendercore.IntVec2Init;
        r16.<init>();
        r19 = r16;
        r16 = new com.google.ar.schemas.rendercore.IntVec3Init;
        r16.<init>();
        r20 = r16;
        r16 = new com.google.ar.schemas.rendercore.IntVec4Init;
        r16.<init>();
        r21 = r16;
        r16 = new com.google.ar.schemas.rendercore.SamplerInit;
        r16.<init>();
        r22 = r16;
        r16 = new com.google.ar.sceneform.rendering.MaterialParameters;
        r16.<init>();
        r29 = r16;
        r3 = r6.parametersLength();
        r16 = 0;
    L_0x00b5:
        r30 = r16;
        r31 = r2;
        r2 = r30;
        if (r2 >= r3) goto L_0x0359;
    L_0x00bd:
        r6.parameters(r7, r2);
        r7.initialValue(r8);
        r32 = r3;
        r3 = r7.id();
        r16 = r8.initType();
        switch(r16) {
            case 1: goto L_0x031d;
            case 2: goto L_0x02fe;
            case 3: goto L_0x02d7;
            case 4: goto L_0x02a6;
            case 5: goto L_0x0276;
            case 6: goto L_0x0251;
            case 7: goto L_0x0234;
            case 8: goto L_0x0213;
            case 9: goto L_0x01e7;
            case 10: goto L_0x01b1;
            case 11: goto L_0x018e;
            case 12: goto L_0x0169;
            case 13: goto L_0x013e;
            case 14: goto L_0x010a;
            case 15: goto L_0x00d0;
            case 16: goto L_0x00f8;
            default: goto L_0x00d0;
        };
    L_0x00d0:
        r33 = r5;
        r35 = r6;
        r36 = r7;
        r40 = r17;
        r39 = r18;
        r38 = r19;
        r37 = r20;
        r34 = r21;
        r41 = r22;
        r5 = r29;
        r1 = TAG;
        r6 = "Unknown parameter type: ";
        r7 = java.lang.String.valueOf(r3);
        r17 = r7.length();
        if (r17 == 0) goto L_0x0332;
    L_0x00f2:
        r6 = r6.concat(r7);
        goto L_0x0338;
        r33 = r5;
        r35 = r6;
        r36 = r7;
        r40 = r17;
        r39 = r18;
        r38 = r19;
        r37 = r20;
        r34 = r21;
        goto L_0x0138;
    L_0x010a:
        r33 = r5;
        r5 = r21;
        r8.init(r5);
        r25 = r5.m224x();
        r26 = r5.m225y();
        r27 = r5.m226z();
        r28 = r5.m223w();
        r23 = r29;
        r24 = r3;
        r23.setInt4(r24, r25, r26, r27, r28);
        r34 = r5;
        r35 = r6;
        r36 = r7;
        r40 = r17;
        r39 = r18;
        r38 = r19;
        r37 = r20;
    L_0x0138:
        r41 = r22;
        r5 = r29;
        goto L_0x033b;
    L_0x013e:
        r33 = r5;
        r5 = r21;
        r34 = r5;
        r5 = r20;
        r8.init(r5);
        r35 = r6;
        r6 = r5.m220x();
        r36 = r7;
        r7 = r5.m221y();
        r1 = r5.m222z();
        r37 = r5;
        r5 = r29;
        r5.setInt3(r3, r6, r7, r1);
        r40 = r17;
        r39 = r18;
        r38 = r19;
        goto L_0x0272;
    L_0x0169:
        r33 = r5;
        r35 = r6;
        r36 = r7;
        r37 = r20;
        r34 = r21;
        r5 = r29;
        r1 = r19;
        r8.init(r1);
        r6 = r1.m218x();
        r7 = r1.m219y();
        r5.setInt2(r3, r6, r7);
        r38 = r1;
        r40 = r17;
        r39 = r18;
        goto L_0x0272;
    L_0x018e:
        r33 = r5;
        r35 = r6;
        r36 = r7;
        r1 = r19;
        r37 = r20;
        r34 = r21;
        r5 = r29;
        r6 = r18;
        r8.init(r6);
        r7 = r6.value();
        r5.setInt(r3, r7);
        r38 = r1;
        r39 = r6;
        r40 = r17;
        goto L_0x0272;
    L_0x01b1:
        r33 = r5;
        r35 = r6;
        r36 = r7;
        r6 = r18;
        r1 = r19;
        r37 = r20;
        r34 = r21;
        r5 = r29;
        r7 = r17;
        r8.init(r7);
        r25 = r7.m215x();
        r26 = r7.m216y();
        r27 = r7.m217z();
        r28 = r7.m214w();
        r23 = r5;
        r24 = r3;
        r23.setBoolean4(r24, r25, r26, r27, r28);
        r38 = r1;
        r39 = r6;
        r40 = r7;
        goto L_0x0272;
    L_0x01e7:
        r33 = r5;
        r35 = r6;
        r36 = r7;
        r7 = r17;
        r6 = r18;
        r1 = r19;
        r37 = r20;
        r34 = r21;
        r5 = r29;
        r8.init(r15);
        r38 = r1;
        r1 = r15.m211x();
        r39 = r6;
        r6 = r15.m212y();
        r40 = r7;
        r7 = r15.m213z();
        r5.setBoolean3(r3, r1, r6, r7);
        goto L_0x0272;
    L_0x0213:
        r33 = r5;
        r35 = r6;
        r36 = r7;
        r40 = r17;
        r39 = r18;
        r38 = r19;
        r37 = r20;
        r34 = r21;
        r5 = r29;
        r8.init(r14);
        r1 = r14.m209x();
        r6 = r14.m210y();
        r5.setBoolean2(r3, r1, r6);
        goto L_0x0272;
    L_0x0234:
        r33 = r5;
        r35 = r6;
        r36 = r7;
        r40 = r17;
        r39 = r18;
        r38 = r19;
        r37 = r20;
        r34 = r21;
        r5 = r29;
        r8.init(r13);
        r1 = r13.value();
        r5.setBoolean(r3, r1);
        goto L_0x0272;
    L_0x0251:
        r33 = r5;
        r35 = r6;
        r36 = r7;
        r40 = r17;
        r39 = r18;
        r38 = r19;
        r37 = r20;
        r34 = r21;
        r5 = r29;
        r8.init(r10);
        r1 = r10.m227x();
        r6 = r10.m228y();
        r5.setFloat2(r3, r1, r6);
    L_0x0272:
        r41 = r22;
        goto L_0x033b;
    L_0x0276:
        r33 = r5;
        r35 = r6;
        r36 = r7;
        r40 = r17;
        r39 = r18;
        r38 = r19;
        r37 = r20;
        r34 = r21;
        r5 = r29;
        r1 = r22;
        r8.init(r1);
        r6 = r1.path();
        r7 = r0.getTextureByName(r6);
        if (r7 == 0) goto L_0x02a2;
    L_0x0297:
        r41 = r1;
        r1 = r0.getSamplerByName(r6);
        r5.setTextureFilament(r3, r7, r1);
        goto L_0x033b;
    L_0x02a2:
        r41 = r1;
        goto L_0x033b;
    L_0x02a6:
        r33 = r5;
        r35 = r6;
        r36 = r7;
        r40 = r17;
        r39 = r18;
        r38 = r19;
        r37 = r20;
        r34 = r21;
        r41 = r22;
        r5 = r29;
        r8.init(r12);
        r25 = r12.m233x();
        r26 = r12.m234y();
        r27 = r12.m235z();
        r28 = r12.m232w();
        r23 = r5;
        r24 = r3;
        r23.setFloat4(r24, r25, r26, r27, r28);
        goto L_0x033b;
    L_0x02d7:
        r33 = r5;
        r35 = r6;
        r36 = r7;
        r40 = r17;
        r39 = r18;
        r38 = r19;
        r37 = r20;
        r34 = r21;
        r41 = r22;
        r5 = r29;
        r8.init(r11);
        r1 = r11.m229x();
        r6 = r11.m230y();
        r7 = r11.m231z();
        r5.setFloat3(r3, r1, r6, r7);
        goto L_0x033b;
    L_0x02fe:
        r33 = r5;
        r35 = r6;
        r36 = r7;
        r40 = r17;
        r39 = r18;
        r38 = r19;
        r37 = r20;
        r34 = r21;
        r41 = r22;
        r5 = r29;
        r8.init(r9);
        r1 = r9.value();
        r5.setFloat(r3, r1);
        goto L_0x033b;
    L_0x031d:
        r33 = r5;
        r35 = r6;
        r36 = r7;
        r40 = r17;
        r39 = r18;
        r38 = r19;
        r37 = r20;
        r34 = r21;
        r41 = r22;
        r5 = r29;
        goto L_0x033b;
    L_0x0332:
        r7 = new java.lang.String;
        r7.<init>(r6);
        r6 = r7;
    L_0x0338:
        android.util.Log.e(r1, r6);
    L_0x033b:
        r16 = r2 + 1;
        r29 = r5;
        r2 = r31;
        r3 = r32;
        r5 = r33;
        r21 = r34;
        r6 = r35;
        r7 = r36;
        r20 = r37;
        r19 = r38;
        r18 = r39;
        r17 = r40;
        r22 = r41;
        r1 = r43;
        goto L_0x00b5;
    L_0x0359:
        r32 = r3;
        r33 = r5;
        r35 = r6;
        r36 = r7;
        r40 = r17;
        r39 = r18;
        r38 = r19;
        r37 = r20;
        r34 = r21;
        r41 = r22;
        r5 = r29;
        r1 = r0.materialParameters;
        r1.add(r5);
    L_0x0374:
        r4 = r4 + 1;
        r2 = r31;
        r1 = r43;
        goto L_0x0013;
    L_0x037c:
        r31 = r2;
        r1 = r43;
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ar.sceneform.rendering.LoadRenderableFromRcbTask.buildMaterialParameters(com.google.ar.schemas.rendercore.RendercoreBundleDef):com.google.ar.schemas.rendercore.RendercoreBundleDef");
    }

    @Nullable
    private Texture getTextureByName(String name) {
        for (int t = 0; t < this.textureCount; t++) {
            if (Objects.equals(name, ((ModelTexture) this.textures.get(t)).name)) {
                return ((ModelTexture) this.textures.get(t)).data;
            }
        }
        return null;
    }

    private TextureSampler getSamplerByName(String name) {
        if (this.filamentSamplers.containsKey(name)) {
            return (TextureSampler) this.filamentSamplers.get(name);
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(name).length() + 65);
        stringBuilder.append("Texture sampler (");
        stringBuilder.append(name);
        stringBuilder.append(") not found, falling back to the default sampler");
        Log.e(str, stringBuilder.toString());
        return new TextureSampler(MinFilter.LINEAR_MIPMAP_LINEAR, MagFilter.LINEAR, WrapMode.CLAMP_TO_EDGE);
    }

    private static int getVertexAttributeTypeSizeInBytes(int attributeType) {
        switch (attributeType) {
            case 0:
                return 0;
            case 1:
                return 4;
            case 2:
                return 8;
            case 3:
                return 12;
            case 4:
                return 16;
            case 5:
                return 4;
            case 6:
                return 8;
            case 7:
                return 4;
            default:
                StringBuilder stringBuilder = new StringBuilder(50);
                stringBuilder.append("Unsupported VertexAttributeType value: ");
                stringBuilder.append(attributeType);
                throw new AssertionError(stringBuilder.toString());
        }
    }

    private boolean isAttributeNormalized(int attributeUsage) {
        return attributeUsage == 2;
    }

    @Nullable
    private static VertexBuffer.VertexAttribute getFilamentVertexAttribute(int attributeUsage) {
        VertexBuffer.VertexAttribute filamentAttribute;
        if (attributeUsage != 6) {
            switch (attributeUsage) {
                case 1:
                    filamentAttribute = VertexBuffer.VertexAttribute.POSITION;
                    break;
                case 2:
                    filamentAttribute = VertexBuffer.VertexAttribute.COLOR;
                    break;
                case 3:
                    filamentAttribute = VertexBuffer.VertexAttribute.UV0;
                    break;
                default:
                    filamentAttribute = null;
                    break;
            }
        }
        filamentAttribute = VertexBuffer.VertexAttribute.TANGENTS;
        return filamentAttribute;
    }

    private static AttributeType getFilamentAttributeType(int attributeType) {
        AttributeType filamentAttributeType;
        switch (attributeType) {
            case 1:
                filamentAttributeType = AttributeType.FLOAT;
                break;
            case 2:
                filamentAttributeType = AttributeType.FLOAT2;
                break;
            case 3:
                filamentAttributeType = AttributeType.FLOAT3;
                break;
            case 4:
                filamentAttributeType = AttributeType.FLOAT4;
                break;
            case 5:
                filamentAttributeType = AttributeType.USHORT2;
                break;
            case 6:
                filamentAttributeType = AttributeType.USHORT4;
                break;
            case 7:
                filamentAttributeType = AttributeType.UBYTE4;
                break;
            default:
                StringBuilder stringBuilder = new StringBuilder(50);
                stringBuilder.append("Unsupported VertexAttributeType value: ");
                stringBuilder.append(attributeType);
                throw new AssertionError(stringBuilder.toString());
        }
        return filamentAttributeType;
    }
}
