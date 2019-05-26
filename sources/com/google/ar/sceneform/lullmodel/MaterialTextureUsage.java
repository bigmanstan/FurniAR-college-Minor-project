package com.google.ar.sceneform.lullmodel;

public final class MaterialTextureUsage {
    public static final int Ambient = 6;
    public static final int BaseColor = 0;
    public static final int BrdfLookupTable = 15;
    public static final int Bump = 3;
    public static final int DiffuseColor = 18;
    public static final int DiffuseEnvironment = 16;
    public static final int Emissive = 7;
    public static final int Height = 4;
    public static final int Light = 8;
    public static final int Metallic = 1;
    public static final int Normal = 2;
    public static final int Occlusion = 13;
    public static final int Opacity = 11;
    public static final int Reflection = 10;
    public static final int Roughness = 12;
    public static final int Shadow = 9;
    public static final int Shininess = 14;
    public static final int Specular = 5;
    public static final int SpecularEnvironment = 17;
    public static final String[] names = new String[]{"BaseColor", "Metallic", "Normal", "Bump", "Height", "Specular", "Ambient", "Emissive", "Light", "Shadow", "Reflection", "Opacity", "Roughness", "Occlusion", "Shininess", "BrdfLookupTable", "DiffuseEnvironment", "SpecularEnvironment", "DiffuseColor"};

    private MaterialTextureUsage() {
    }

    public static String name(int i) {
        return names[i];
    }
}
