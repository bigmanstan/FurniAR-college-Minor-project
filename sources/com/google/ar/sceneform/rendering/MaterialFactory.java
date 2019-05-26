package com.google.ar.sceneform.rendering;

import android.content.Context;
import android.support.annotation.RequiresApi;
import java.util.concurrent.CompletableFuture;

@RequiresApi(api = 24)
public final class MaterialFactory {
    private static final float DEFAULT_METALLIC_PROPERTY = 0.0f;
    private static final float DEFAULT_REFLECTANCE_PROPERTY = 0.5f;
    private static final float DEFAULT_ROUGHNESS_PROPERTY = 0.4f;
    public static final String MATERIAL_COLOR = "color";
    public static final String MATERIAL_METALLIC = "metallic";
    public static final String MATERIAL_REFLECTANCE = "reflectance";
    public static final String MATERIAL_ROUGHNESS = "roughness";
    public static final String MATERIAL_TEXTURE = "texture";

    public static CompletableFuture<Material> makeOpaqueWithColor(Context context, Color color) {
        return Material.builder().setSource(context, C0023R.raw.sceneform_opaque_colored_material).build().thenApply(new -$$Lambda$MaterialFactory$99RavRBvl6JufnmZIBwMQ9oC1s0(color));
    }

    static /* synthetic */ Material lambda$makeOpaqueWithColor$0(Color color, Material material) {
        material.setFloat3("color", color);
        applyDefaultPbrParams(material);
        return material;
    }

    public static CompletableFuture<Material> makeTransparentWithColor(Context context, Color color) {
        return Material.builder().setSource(context, C0023R.raw.sceneform_transparent_colored_material).build().thenApply(new -$$Lambda$MaterialFactory$_M5CV9eWxLvduis1So-TR6mJa44(color));
    }

    static /* synthetic */ Material lambda$makeTransparentWithColor$1(Color color, Material material) {
        material.setFloat4("color", color);
        applyDefaultPbrParams(material);
        return material;
    }

    public static CompletableFuture<Material> makeOpaqueWithTexture(Context context, Texture texture) {
        return Material.builder().setSource(context, C0023R.raw.sceneform_opaque_textured_material).build().thenApply(new -$$Lambda$MaterialFactory$tpcGClrps36DtB215p9pCRIImLg(texture));
    }

    static /* synthetic */ Material lambda$makeOpaqueWithTexture$2(Texture texture, Material material) {
        material.setTexture("texture", texture);
        applyDefaultPbrParams(material);
        return material;
    }

    public static CompletableFuture<Material> makeTransparentWithTexture(Context context, Texture texture) {
        return Material.builder().setSource(context, C0023R.raw.sceneform_transparent_textured_material).build().thenApply(new -$$Lambda$MaterialFactory$m-GWZzdzZrGS4ciKS7wD0UsZ83Q(texture));
    }

    static /* synthetic */ Material lambda$makeTransparentWithTexture$3(Texture texture, Material material) {
        material.setTexture("texture", texture);
        applyDefaultPbrParams(material);
        return material;
    }

    private static void applyDefaultPbrParams(Material material) {
        material.setFloat(MATERIAL_METALLIC, 0.0f);
        material.setFloat(MATERIAL_ROUGHNESS, DEFAULT_ROUGHNESS_PROPERTY);
        material.setFloat(MATERIAL_REFLECTANCE, DEFAULT_REFLECTANCE_PROPERTY);
    }
}
