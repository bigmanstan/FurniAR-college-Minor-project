package com.google.ar.sceneform.rendering;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import com.google.android.filament.Engine;
import com.google.android.filament.Texture.Format;
import com.google.android.filament.Texture.InternalFormat;
import com.google.android.filament.Texture.PixelBufferDescriptor;
import com.google.android.filament.Texture.Type;
import com.google.ar.sceneform.utilities.AndroidPreconditions;
import com.google.ar.sceneform.utilities.LoadHelper;
import com.google.ar.sceneform.utilities.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

@RequiresApi(api = 24)
public class Texture implements AssetHolder {
    private static final int BUFFER_ALIGNMENT_FOR_32_BITS = 4;
    private static final int DEFAULT_TEXTURE_COLOR = -8947849;
    private static final int DEFAULT_TEXTURE_DEPTH = 1;
    private static final int DEFAULT_TEXTURE_HEIGHT = 4;
    private static final int DEFAULT_TEXTURE_MIP_LEVELS = 1;
    private static final int DEFAULT_TEXTURE_WIDTH = 4;
    private static final int ID_NO_DATA = 0;
    private static final int INT_SIZE_IN_BYTES = 4;
    private static final int MIP_LEVELS_TO_GENERATE = 255;
    private static final String TAG = Texture.class.getSimpleName();
    @Nullable
    private static Texture defaultTexture;
    private int depth;
    @Nullable
    private com.google.android.filament.Texture filamentTexture;
    private int height;
    private int id;
    private int lastUsedId;
    private int mipLevels;
    private boolean ready;
    private Sampler sampler;
    @Nullable
    private ByteBuffer sourceBuffer;
    private Usage usage;
    private int width;

    /* renamed from: com.google.ar.sceneform.rendering.Texture$1 */
    static /* synthetic */ class C04091 {
        static final /* synthetic */ int[] $SwitchMap$com$google$ar$sceneform$rendering$Texture$Usage = new int[Usage.values().length];

        static {
            try {
                $SwitchMap$com$google$ar$sceneform$rendering$Texture$Usage[Usage.COLOR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$ar$sceneform$rendering$Texture$Usage[Usage.NORMAL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$ar$sceneform$rendering$Texture$Usage[Usage.DATA.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public static final class Builder {
        private static final int MAX_BITMAP_SIZE = 4096;
        @Nullable
        private Bitmap bitmap;
        private boolean inPremultiplied;
        @Nullable
        private Callable<InputStream> inputStreamCreator;
        @Nullable
        private Object registryId;
        private Sampler sampler;
        private Usage usage;

        private Builder() {
            this.inputStreamCreator = null;
            this.bitmap = null;
            this.usage = Usage.COLOR;
            this.registryId = null;
            this.inPremultiplied = true;
            this.sampler = Sampler.builder().build();
        }

        public Builder setSource(Context context, Uri sourceUri) {
            Preconditions.checkNotNull(sourceUri, "Parameter \"sourceUri\" was null.");
            this.registryId = sourceUri;
            setSource(LoadHelper.fromUri(context, sourceUri));
            return this;
        }

        public Builder setSource(Callable<InputStream> inputStreamCreator) {
            Preconditions.checkNotNull(inputStreamCreator, "Parameter \"inputStreamCreator\" was null.");
            this.inputStreamCreator = inputStreamCreator;
            this.bitmap = null;
            return this;
        }

        Builder setPremultiplied(boolean inPremultiplied) {
            this.inPremultiplied = inPremultiplied;
            return this;
        }

        public Builder setSource(Context context, int resource) {
            setSource(context, LoadHelper.resourceToUri(context, resource));
            return this;
        }

        public Builder setSource(Bitmap bitmap) {
            Preconditions.checkNotNull(bitmap, "Parameter \"bitmap\" was null.");
            if (bitmap.getConfig() != Config.ARGB_8888) {
                String valueOf = String.valueOf(bitmap.getConfig());
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 69);
                stringBuilder.append("Invalid Bitmap: Bitmap's configuration must be ARGB_8888, but it was ");
                stringBuilder.append(valueOf);
                throw new IllegalArgumentException(stringBuilder.toString());
            } else if (!bitmap.isPremultiplied()) {
                throw new IllegalArgumentException("Invalid Bitmap: Bitmap must be premultiplied.");
            } else if (bitmap.getWidth() > 4096 || bitmap.getHeight() > 4096) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                StringBuilder stringBuilder2 = new StringBuilder(119);
                stringBuilder2.append("Invalid Bitmap: Bitmap width and height must be smaller than 4096. Bitmap was ");
                stringBuilder2.append(width);
                stringBuilder2.append(" width and ");
                stringBuilder2.append(height);
                stringBuilder2.append(" height.");
                throw new IllegalArgumentException(stringBuilder2.toString());
            } else {
                this.bitmap = bitmap;
                this.registryId = bitmap;
                this.inputStreamCreator = null;
                return this;
            }
        }

        public Builder setRegistryId(Object registryId) {
            this.registryId = registryId;
            return this;
        }

        public Builder setUsage(Usage usage) {
            this.usage = usage;
            return this;
        }

        public Builder setSampler(Sampler sampler) {
            this.sampler = sampler;
            return this;
        }

        public CompletableFuture<Texture> build() {
            CompletableFuture<Texture> textureFuture;
            AndroidPreconditions.checkUiThread();
            Object registryId = this.registryId;
            if (registryId != null) {
                textureFuture = ResourceManager.getInstance().getTextureRegistry().get(registryId);
                if (textureFuture != null) {
                    return textureFuture;
                }
            }
            Texture texture = new Texture();
            if (this.inputStreamCreator != null) {
                textureFuture = texture.loadInBackground(this.inputStreamCreator, this.inPremultiplied);
            } else if (this.bitmap != null) {
                texture.bindToBitmap(this.bitmap);
                texture.buildFilamentResource();
                textureFuture = CompletableFuture.completedFuture(texture);
            } else {
                texture.buildDefaultTexture();
                textureFuture = CompletableFuture.completedFuture(texture);
            }
            if (registryId != null) {
                ResourceManager.getInstance().getTextureRegistry().register(registryId, textureFuture);
            }
            String access$900 = Texture.TAG;
            String valueOf = String.valueOf(registryId);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 36);
            stringBuilder.append("Unable to load Texture registryId='");
            stringBuilder.append(valueOf);
            stringBuilder.append("'");
            FutureHelper.logOnException(access$900, textureFuture, stringBuilder.toString());
            return textureFuture;
        }
    }

    public static class Sampler {
        private final MagFilter magFilter;
        private final MinFilter minFilter;
        private final WrapMode wrapModeR;
        private final WrapMode wrapModeS;
        private final WrapMode wrapModeT;

        public static class Builder {
            private MagFilter magFilter;
            private MinFilter minFilter;
            private WrapMode wrapModeR;
            private WrapMode wrapModeS;
            private WrapMode wrapModeT;

            Builder setMinMagFilter(MagFilter minMagFilter) {
                return setMinFilter(MinFilter.values()[minMagFilter.ordinal()]).setMagFilter(minMagFilter);
            }

            public Builder setMinFilter(MinFilter minFilter) {
                this.minFilter = minFilter;
                return this;
            }

            public Builder setMagFilter(MagFilter magFilter) {
                this.magFilter = magFilter;
                return this;
            }

            public Builder setWrapMode(WrapMode wrapMode) {
                return setWrapModeS(wrapMode).setWrapModeT(wrapMode).setWrapModeR(wrapMode);
            }

            public Builder setWrapModeS(WrapMode wrapMode) {
                this.wrapModeS = wrapMode;
                return this;
            }

            public Builder setWrapModeT(WrapMode wrapMode) {
                this.wrapModeT = wrapMode;
                return this;
            }

            public Builder setWrapModeR(WrapMode wrapMode) {
                this.wrapModeR = wrapMode;
                return this;
            }

            public Sampler build() {
                return new Sampler();
            }
        }

        public enum MagFilter {
            NEAREST,
            LINEAR
        }

        public enum MinFilter {
            NEAREST,
            LINEAR,
            NEAREST_MIPMAP_NEAREST,
            LINEAR_MIPMAP_NEAREST,
            NEAREST_MIPMAP_LINEAR,
            LINEAR_MIPMAP_LINEAR
        }

        public enum WrapMode {
            CLAMP_TO_EDGE,
            REPEAT,
            MIRRORED_REPEAT
        }

        public MinFilter getMinFilter() {
            return this.minFilter;
        }

        public MagFilter getMagFilter() {
            return this.magFilter;
        }

        public WrapMode getWrapModeS() {
            return this.wrapModeS;
        }

        public WrapMode getWrapModeT() {
            return this.wrapModeT;
        }

        public WrapMode getWrapModeR() {
            return this.wrapModeR;
        }

        public static Builder builder() {
            return new Builder().setMinFilter(MinFilter.LINEAR_MIPMAP_LINEAR).setMagFilter(MagFilter.LINEAR).setWrapMode(WrapMode.CLAMP_TO_EDGE);
        }

        private Sampler(Builder builder) {
            this.minFilter = builder.minFilter;
            this.magFilter = builder.magFilter;
            this.wrapModeS = builder.wrapModeS;
            this.wrapModeT = builder.wrapModeT;
            this.wrapModeR = builder.wrapModeR;
        }
    }

    public enum Usage {
        COLOR,
        NORMAL,
        DATA
    }

    static class LoadTextureTask implements AssetLoadTask {
        private final boolean inPremultiplied;
        private final Callable<InputStream> inputStreamCreator;
        private final Texture texture;

        LoadTextureTask(Texture texture, Callable<InputStream> inputStreamCreator, boolean inPremultiplied) {
            this.texture = texture;
            this.inputStreamCreator = inputStreamCreator;
            this.inPremultiplied = inPremultiplied;
        }

        public boolean loadData() throws IOException {
            InputStream inputStream;
            Options options = new Options();
            options.inScaled = false;
            options.inPremultiplied = this.inPremultiplied;
            try {
                inputStream = (InputStream) this.inputStreamCreator.call();
                Bitmap textureBitmap = BitmapFactory.decodeStream(inputStream, null, options);
                if (inputStream != null) {
                    inputStream.close();
                }
                Bitmap inputStream2 = textureBitmap;
                if (inputStream2 != null) {
                    this.texture.depth = 1;
                    if (inputStream2.getConfig() == Config.ARGB_8888) {
                        this.texture.bindToBitmap(inputStream2);
                        inputStream2.recycle();
                        return true;
                    }
                    throw new IllegalStateException("Texture must use ARGB8 format.");
                }
                throw new IllegalStateException("Failed to decode the texture bitmap. The InputStream was not a valid bitmap.");
            } catch (Exception e) {
                throw new IllegalStateException(e);
            } catch (Throwable th) {
                r2.addSuppressed(th);
            }
        }

        public void createAsset() {
            this.texture.buildFilamentResource();
        }
    }

    public static Builder builder() {
        AndroidPreconditions.checkMinAndroidApiLevel();
        return new Builder();
    }

    private Texture(Builder builder) {
        this.id = 0;
        this.usage = Usage.COLOR;
        this.sampler = builder.sampler;
        this.usage = builder.usage;
        this.ready = false;
    }

    int getId() {
        return this.id;
    }

    Sampler getSampler() {
        return this.sampler;
    }

    public boolean isReady() {
        return this.ready;
    }

    @Nullable
    public com.google.android.filament.Texture getData() {
        return this.filamentTexture;
    }

    public void onLoadFinished(AssetLoadTask task) {
    }

    public void onLoadCancelled(AssetLoadTask task) {
    }

    Texture getDefaultTexture() {
        if (defaultTexture == null) {
            defaultTexture = createDefaultTexture();
        }
        return defaultTexture;
    }

    private void buildDefaultTexture() {
        this.mipLevels = 1;
        this.width = 4;
        this.height = 4;
        this.depth = 1;
        int numPixels = this.width * this.height;
        this.sourceBuffer = ByteBuffer.allocateDirect(numPixels * 4).order(ByteOrder.nativeOrder());
        IntBuffer intTextureData = this.sourceBuffer.asIntBuffer();
        for (int i = 0; i < numPixels; i++) {
            intTextureData.put(DEFAULT_TEXTURE_COLOR);
        }
        intTextureData.rewind();
        buildFilamentResource();
    }

    private static InternalFormat getInternalFormatForUsage(Usage usage) {
        InternalFormat format;
        if (C04091.$SwitchMap$com$google$ar$sceneform$rendering$Texture$Usage[usage.ordinal()] != 1) {
            format = InternalFormat.RGBA8;
        } else {
            format = InternalFormat.SRGB8_A8;
        }
        return format;
    }

    private void buildFilamentResource() {
        try {
            dispose();
            this.id = allocateNewId();
            Engine engine = EngineInstance.getEngine();
            InternalFormat textureInternalFormat = getInternalFormatForUsage(this.usage);
            Format textureFormat = Format.RGBA;
            com.google.android.filament.Texture.Sampler textureSampler = com.google.android.filament.Texture.Sampler.SAMPLER_2D;
            Type bufferType = Type.UBYTE;
            ByteBuffer sourceBuffer = this.sourceBuffer;
            if (sourceBuffer != null) {
                this.sourceBuffer = null;
                this.filamentTexture = new com.google.android.filament.Texture.Builder().width(this.width).height(this.height).depth(this.depth).levels(this.mipLevels).sampler(textureSampler).format(textureInternalFormat).build(engine);
                this.filamentTexture.setImage(engine, 0, new PixelBufferDescriptor(sourceBuffer, textureFormat, bufferType, 4));
                if (this.mipLevels > 1) {
                    this.filamentTexture.generateMipmaps(engine);
                }
                this.ready = true;
                return;
            }
            throw new AssertionError("Internal Error: Null source for texture");
        } catch (Exception e) {
            throw new AssertionError("Unable to create texture", e);
        }
    }

    protected void finalize() throws Throwable {
        try {
            ThreadPools.getMainExecutor().execute(new -$$Lambda$Texture$xsCwkYxWoBa2wS92XEzDGeLUuhs());
        } catch (Exception e) {
            Log.e(TAG, "Error while Finalizing Texture.", e);
        } catch (Throwable th) {
            super.finalize();
        }
        super.finalize();
    }

    void dispose() {
        AndroidPreconditions.checkUiThread();
        Engine engine = EngineInstance.getEngine();
        com.google.android.filament.Texture filamentTexture = this.filamentTexture;
        this.filamentTexture = null;
        if (!(filamentTexture == null || engine == null || !engine.isValid())) {
            engine.destroyTexture(filamentTexture);
        }
        this.id = 0;
        this.ready = false;
    }

    private CompletableFuture<Texture> loadInBackground(Callable<InputStream> inputStreamCreator, boolean inPremultiplied) {
        ResourceManager.getInstance().getAssetLoader().runLoadTask(this, new LoadTextureTask(this, inputStreamCreator, inPremultiplied));
        return CompletableFuture.completedFuture(this);
    }

    private static Texture createDefaultTexture() {
        CompletableFuture<Texture> defaultTextureFuture = builder().build();
        if (defaultTextureFuture.isDone()) {
            try {
                return (Texture) defaultTextureFuture.get();
            } catch (Exception ex) {
                throw new AssertionError("Unable to create default texture", ex);
            }
        }
        throw new AssertionError("InternalError: defaultTexture is null");
    }

    private int allocateNewId() {
        int newId = this.lastUsedId + 1;
        if (newId != 0) {
            this.lastUsedId = newId;
            return newId;
        }
        throw new AssertionError("Texture ran out of unique IDs (reloaded too frequently).");
    }

    private void bindToBitmap(Bitmap bitmap) {
        this.mipLevels = 255;
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        this.sourceBuffer = ByteBuffer.allocateDirect(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(this.sourceBuffer);
        this.sourceBuffer.rewind();
    }
}
