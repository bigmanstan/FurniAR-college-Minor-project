package com.google.vr.dynamite.client;

/* renamed from: com.google.vr.dynamite.client.c */
public final class C0425c extends Exception {
    /* renamed from: a */
    private final int f146a = 1;

    public C0425c(int i) {
    }

    public final String toString() {
        String str;
        switch (this.f146a) {
            case 1:
                str = "Package not available";
                break;
            case 2:
                str = "Package obsolete";
                break;
            default:
                str = "Unknown error";
                break;
        }
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 17);
        stringBuilder.append("LoaderException{");
        stringBuilder.append(str);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
