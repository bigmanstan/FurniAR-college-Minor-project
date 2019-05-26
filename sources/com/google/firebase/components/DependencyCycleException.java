package com.google.firebase.components;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.Arrays;
import java.util.List;

@KeepForSdk
public class DependencyCycleException extends DependencyException {
    private final List<Component<?>> zza;

    @KeepForSdk
    public DependencyCycleException(List<Component<?>> componentsInCycle) {
        StringBuilder stringBuilder = new StringBuilder("Dependency cycle detected: ");
        stringBuilder.append(Arrays.toString(componentsInCycle.toArray()));
        super(stringBuilder.toString());
        this.zza = componentsInCycle;
    }

    @KeepForSdk
    public List<Component<?>> getComponentsInCycle() {
        return this.zza;
    }
}
