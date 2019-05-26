package com.google.firebase.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class zze {

    static class zza {
        private final Component<?> zza;
        private final Set<zza> zzb = new HashSet();
        private final Set<zza> zzc = new HashSet();

        zza(Component<?> component) {
            this.zza = component;
        }

        final void zza(zza zza) {
            this.zzb.add(zza);
        }

        final void zzb(zza zza) {
            this.zzc.add(zza);
        }

        final Set<zza> zza() {
            return this.zzb;
        }

        final void zzc(zza zza) {
            this.zzc.remove(zza);
        }

        final Component<?> zzb() {
            return this.zza;
        }

        final boolean zzc() {
            return this.zzc.isEmpty();
        }

        final boolean zzd() {
            return this.zzb.isEmpty();
        }
    }

    static List<Component<?>> zza(List<Component<?>> list) {
        Map hashMap = new HashMap(list.size());
        for (Component component : list) {
            zza zza = new zza(component);
            for (Class put : component.zza()) {
                if (hashMap.put(put, zza) != null) {
                    throw new IllegalArgumentException(String.format("Multiple components provide %s.", new Object[]{(Class) r2.next()}));
                }
            }
        }
        for (zza zza2 : hashMap.values()) {
            for (Dependency dependency : zza2.zzb().zzb()) {
                if (dependency.zzc()) {
                    zza zza3 = (zza) hashMap.get(dependency.zza());
                    if (zza3 != null) {
                        zza2.zza(zza3);
                        zza3.zzb(zza2);
                    }
                }
            }
        }
        Set<zza> hashSet = new HashSet(hashMap.values());
        Set zza4 = zza((Set) hashSet);
        List<Component<?>> arrayList = new ArrayList();
        while (!zza4.isEmpty()) {
            zza = (zza) zza4.iterator().next();
            zza4.remove(zza);
            arrayList.add(zza.zzb());
            for (zza zza5 : zza.zza()) {
                zza5.zzc(zza);
                if (zza5.zzc()) {
                    zza4.add(zza5);
                }
            }
        }
        if (arrayList.size() == list.size()) {
            Collections.reverse(arrayList);
            return arrayList;
        }
        list = new ArrayList();
        for (zza zza6 : hashSet) {
            if (!(zza6.zzc() || zza6.zzd())) {
                list.add(zza6.zzb());
            }
        }
        throw new DependencyCycleException(list);
    }

    private static Set<zza> zza(Set<zza> set) {
        Set<zza> hashSet = new HashSet();
        for (zza zza : set) {
            if (zza.zzc()) {
                hashSet.add(zza);
            }
        }
        return hashSet;
    }
}
