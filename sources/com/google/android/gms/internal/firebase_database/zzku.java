package com.google.android.gms.internal.firebase_database;

import android.util.Log;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.google.firebase.database.ThrowOnExtraProperties;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

final class zzku<T> {
    private final Class<T> zzuv;
    private final Constructor<T> zzuw;
    private final boolean zzux;
    private final boolean zzuy;
    private final Map<String, String> zzuz = new HashMap();
    private final Map<String, Method> zzva = new HashMap();
    private final Map<String, Method> zzvb = new HashMap();
    private final Map<String, Field> zzvc = new HashMap();

    public zzku(Class<T> cls) {
        Constructor declaredConstructor;
        this.zzuv = cls;
        this.zzux = cls.isAnnotationPresent(ThrowOnExtraProperties.class);
        this.zzuy = cls.isAnnotationPresent(IgnoreExtraProperties.class) ^ true;
        try {
            declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
            declaredConstructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            declaredConstructor = null;
        }
        this.zzuw = declaredConstructor;
        for (Method method : cls.getMethods()) {
            int i;
            String zza;
            if (method.getName().startsWith("get") || method.getName().startsWith("is")) {
                if (!method.getDeclaringClass().equals(Object.class)) {
                    if (Modifier.isPublic(method.getModifiers())) {
                        if (!Modifier.isStatic(method.getModifiers())) {
                            if (!method.getReturnType().equals(Void.TYPE)) {
                                if (method.getParameterTypes().length == 0) {
                                    if (!method.isAnnotationPresent(Exclude.class)) {
                                        i = true;
                                        if (i != 0) {
                                            zza = zza(method);
                                            zzae(zza);
                                            method.setAccessible(true);
                                            if (this.zzva.containsKey(zza)) {
                                                this.zzva.put(zza, method);
                                            } else {
                                                String str = "Found conflicting getters for name: ";
                                                String valueOf = String.valueOf(method.getName());
                                                throw new DatabaseException(valueOf.length() == 0 ? str.concat(valueOf) : new String(str));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            i = 0;
            if (i != 0) {
                zza = zza(method);
                zzae(zza);
                method.setAccessible(true);
                if (this.zzva.containsKey(zza)) {
                    this.zzva.put(zza, method);
                } else {
                    String str2 = "Found conflicting getters for name: ";
                    String valueOf2 = String.valueOf(method.getName());
                    if (valueOf2.length() == 0) {
                    }
                    throw new DatabaseException(valueOf2.length() == 0 ? str2.concat(valueOf2) : new String(str2));
                }
            }
        }
        for (Field field : cls.getFields()) {
            if (!field.getDeclaringClass().equals(Object.class)) {
                if (Modifier.isPublic(field.getModifiers())) {
                    if (!Modifier.isStatic(field.getModifiers())) {
                        if (!Modifier.isTransient(field.getModifiers())) {
                            if (!field.isAnnotationPresent(Exclude.class)) {
                                i = true;
                                if (i != 0) {
                                    zzae(zza(field));
                                }
                            }
                        }
                    }
                }
            }
            i = 0;
            if (i != 0) {
                zzae(zza(field));
            }
        }
        Class cls2 = cls;
        do {
            for (Method method2 : cls2.getDeclaredMethods()) {
                int i2;
                String zza2;
                String str3;
                Method method3;
                boolean z;
                if (method2.getName().startsWith("set")) {
                    if (!method2.getDeclaringClass().equals(Object.class)) {
                        if (!Modifier.isStatic(method2.getModifiers())) {
                            if (method2.getReturnType().equals(Void.TYPE)) {
                                if (method2.getParameterTypes().length == 1) {
                                    if (!method2.isAnnotationPresent(Exclude.class)) {
                                        i2 = true;
                                        if (i2 != 0) {
                                            zza2 = zza(method2);
                                            str3 = (String) this.zzuz.get(zza2.toLowerCase());
                                            if (str3 == null) {
                                                continue;
                                            } else if (str3.equals(zza2)) {
                                                str2 = "Found setter with invalid case-sensitive name: ";
                                                valueOf2 = String.valueOf(method2.getName());
                                                throw new DatabaseException(valueOf2.length() == 0 ? str2.concat(valueOf2) : new String(str2));
                                            } else {
                                                method3 = (Method) this.zzvb.get(zza2);
                                                if (method3 != null) {
                                                    method2.setAccessible(true);
                                                    this.zzvb.put(zza2, method2);
                                                } else {
                                                    zzkq.zza(method2.getDeclaringClass().isAssignableFrom(method3.getDeclaringClass()), "Expected override from a base class");
                                                    zzkq.zza(method2.getReturnType().equals(Void.TYPE), "Expected void return type");
                                                    zzkq.zza(method3.getReturnType().equals(Void.TYPE), "Expected void return type");
                                                    Class[] parameterTypes = method2.getParameterTypes();
                                                    Class[] parameterTypes2 = method3.getParameterTypes();
                                                    zzkq.zza(parameterTypes.length != 1, "Expected exactly one parameter");
                                                    zzkq.zza(parameterTypes2.length != 1, "Expected exactly one parameter");
                                                    z = method2.getName().equals(method3.getName()) && parameterTypes[0].equals(parameterTypes2[0]);
                                                    if (z) {
                                                        str2 = method2.getName();
                                                        valueOf2 = method3.getName();
                                                        String name = method3.getDeclaringClass().getName();
                                                        StringBuilder stringBuilder = new StringBuilder(((String.valueOf(str2).length() + 69) + String.valueOf(valueOf2).length()) + String.valueOf(name).length());
                                                        stringBuilder.append("Found a conflicting setters with name: ");
                                                        stringBuilder.append(str2);
                                                        stringBuilder.append(" (conflicts with ");
                                                        stringBuilder.append(valueOf2);
                                                        stringBuilder.append(" defined on ");
                                                        stringBuilder.append(name);
                                                        stringBuilder.append(")");
                                                        throw new DatabaseException(stringBuilder.toString());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                i2 = 0;
                if (i2 != 0) {
                    zza2 = zza(method2);
                    str3 = (String) this.zzuz.get(zza2.toLowerCase());
                    if (str3 == null) {
                        continue;
                    } else if (str3.equals(zza2)) {
                        method3 = (Method) this.zzvb.get(zza2);
                        if (method3 != null) {
                            zzkq.zza(method2.getDeclaringClass().isAssignableFrom(method3.getDeclaringClass()), "Expected override from a base class");
                            zzkq.zza(method2.getReturnType().equals(Void.TYPE), "Expected void return type");
                            zzkq.zza(method3.getReturnType().equals(Void.TYPE), "Expected void return type");
                            Class[] parameterTypes3 = method2.getParameterTypes();
                            Class[] parameterTypes22 = method3.getParameterTypes();
                            if (parameterTypes3.length != 1) {
                            }
                            zzkq.zza(parameterTypes3.length != 1, "Expected exactly one parameter");
                            if (parameterTypes22.length != 1) {
                            }
                            zzkq.zza(parameterTypes22.length != 1, "Expected exactly one parameter");
                            if (!method2.getName().equals(method3.getName())) {
                            }
                            if (z) {
                                str2 = method2.getName();
                                valueOf2 = method3.getName();
                                String name2 = method3.getDeclaringClass().getName();
                                StringBuilder stringBuilder2 = new StringBuilder(((String.valueOf(str2).length() + 69) + String.valueOf(valueOf2).length()) + String.valueOf(name2).length());
                                stringBuilder2.append("Found a conflicting setters with name: ");
                                stringBuilder2.append(str2);
                                stringBuilder2.append(" (conflicts with ");
                                stringBuilder2.append(valueOf2);
                                stringBuilder2.append(" defined on ");
                                stringBuilder2.append(name2);
                                stringBuilder2.append(")");
                                throw new DatabaseException(stringBuilder2.toString());
                            }
                        } else {
                            method2.setAccessible(true);
                            this.zzvb.put(zza2, method2);
                        }
                    } else {
                        str2 = "Found setter with invalid case-sensitive name: ";
                        valueOf2 = String.valueOf(method2.getName());
                        if (valueOf2.length() == 0) {
                        }
                        throw new DatabaseException(valueOf2.length() == 0 ? str2.concat(valueOf2) : new String(str2));
                    }
                }
            }
            for (Field field2 : cls2.getDeclaredFields()) {
                zza2 = zza(field2);
                if (this.zzuz.containsKey(zza2.toLowerCase()) && !this.zzvc.containsKey(zza2)) {
                    field2.setAccessible(true);
                    this.zzvc.put(zza2, field2);
                }
            }
            cls2 = cls2.getSuperclass();
            if (cls2 == null) {
                break;
            }
        } while (!cls2.equals(Object.class));
        if (this.zzuz.isEmpty()) {
            valueOf2 = "No properties to serialize found on class ";
            String valueOf3 = String.valueOf(cls.getName());
            throw new DatabaseException(valueOf3.length() != 0 ? valueOf2.concat(valueOf3) : new String(valueOf2));
        }
    }

    private static String zza(AccessibleObject accessibleObject) {
        return accessibleObject.isAnnotationPresent(PropertyName.class) ? ((PropertyName) accessibleObject.getAnnotation(PropertyName.class)).value() : null;
    }

    private static String zza(Field field) {
        String zza = zza((AccessibleObject) field);
        return zza != null ? zza : field.getName();
    }

    private static String zza(Method method) {
        String zza = zza((AccessibleObject) method);
        if (zza != null) {
            return zza;
        }
        String name = method.getName();
        String[] strArr = new String[]{"get", "set", "is"};
        int i = 0;
        String str = null;
        for (int i2 = 0; i2 < 3; i2++) {
            String str2 = strArr[i2];
            if (name.startsWith(str2)) {
                str = str2;
            }
        }
        if (str == null) {
            String str3 = "Unknown Bean prefix for method: ";
            name = String.valueOf(name);
            throw new IllegalArgumentException(name.length() != 0 ? str3.concat(name) : new String(str3));
        }
        char[] toCharArray = name.substring(str.length()).toCharArray();
        while (i < toCharArray.length && Character.isUpperCase(toCharArray[i])) {
            toCharArray[i] = Character.toLowerCase(toCharArray[i]);
            i++;
        }
        return new String(toCharArray);
    }

    private static Type zza(Type type, Map<TypeVariable<Class<T>>, Type> map) {
        if (!(type instanceof TypeVariable)) {
            return type;
        }
        Type type2 = (Type) map.get(type);
        if (type2 != null) {
            return type2;
        }
        String valueOf = String.valueOf(type);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 23);
        stringBuilder.append("Could not resolve type ");
        stringBuilder.append(valueOf);
        throw new IllegalStateException(stringBuilder.toString());
    }

    private final void zzae(String str) {
        String str2 = (String) this.zzuz.put(str.toLowerCase(), str);
        if (str2 != null && !str.equals(str2)) {
            String str3 = "Found two getters or fields with conflicting case sensitivity for property: ";
            str = String.valueOf(str.toLowerCase());
            throw new DatabaseException(str.length() != 0 ? str3.concat(str) : new String(str3));
        }
    }

    public final T zza(Map<String, Object> map, Map<TypeVariable<Class<T>>, Type> map2) {
        if (this.zzuw != null) {
            try {
                T newInstance = this.zzuw.newInstance(new Object[0]);
                for (Entry entry : map.entrySet()) {
                    String str = (String) entry.getKey();
                    if (this.zzvb.containsKey(str)) {
                        Method method = (Method) this.zzvb.get(str);
                        Type[] genericParameterTypes = method.getGenericParameterTypes();
                        if (genericParameterTypes.length == 1) {
                            Object zzb = zzkt.zza(entry.getValue(), zza(genericParameterTypes[0], (Map) map2));
                            try {
                                method.invoke(newInstance, new Object[]{zzb});
                            } catch (Throwable e) {
                                throw new RuntimeException(e);
                            } catch (Throwable e2) {
                                throw new RuntimeException(e2);
                            }
                        }
                        throw new IllegalStateException("Setter does not have exactly one parameter");
                    } else if (this.zzvc.containsKey(str)) {
                        Field field = (Field) this.zzvc.get(str);
                        try {
                            field.set(newInstance, zzkt.zza(entry.getValue(), zza(field.getGenericType(), (Map) map2)));
                        } catch (Throwable e22) {
                            throw new RuntimeException(e22);
                        }
                    } else {
                        String name = this.zzuv.getName();
                        StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 36) + String.valueOf(name).length());
                        stringBuilder.append("No setter/field for ");
                        stringBuilder.append(str);
                        stringBuilder.append(" found on class ");
                        stringBuilder.append(name);
                        name = stringBuilder.toString();
                        if (this.zzuz.containsKey(str.toLowerCase())) {
                            name = String.valueOf(name).concat(" (fields/setters are case sensitive!)");
                        }
                        if (this.zzux) {
                            throw new DatabaseException(name);
                        } else if (this.zzuy) {
                            Log.w("ClassMapper", name);
                        }
                    }
                }
                return newInstance;
            } catch (Throwable e222) {
                throw new RuntimeException(e222);
            } catch (Throwable e2222) {
                throw new RuntimeException(e2222);
            } catch (Throwable e22222) {
                throw new RuntimeException(e22222);
            }
        }
        String name2 = this.zzuv.getName();
        StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(name2).length() + 123);
        stringBuilder2.append("Class ");
        stringBuilder2.append(name2);
        stringBuilder2.append(" does not define a no-argument constructor. If you are using ProGuard, make sure these constructors are not stripped.");
        throw new DatabaseException(stringBuilder2.toString());
    }

    public final Map<String, Object> zzm(T t) {
        if (this.zzuv.isAssignableFrom(t.getClass())) {
            Map<String, Object> hashMap = new HashMap();
            for (String str : this.zzuz.values()) {
                Object invoke;
                if (this.zzva.containsKey(str)) {
                    try {
                        invoke = ((Method) this.zzva.get(str)).invoke(t, new Object[0]);
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    } catch (Throwable e2) {
                        throw new RuntimeException(e2);
                    }
                }
                Field field = (Field) this.zzvc.get(str);
                if (field == null) {
                    String str2 = "Bean property without field or getter:";
                    String valueOf = String.valueOf(str);
                    throw new IllegalStateException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                }
                try {
                    invoke = field.get(t);
                } catch (Throwable e22) {
                    throw new RuntimeException(e22);
                }
                hashMap.put(str, zzkt.zzi(invoke));
            }
            return hashMap;
        }
        String valueOf2 = String.valueOf(t.getClass());
        valueOf = String.valueOf(this.zzuv);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf2).length() + 59) + String.valueOf(valueOf).length());
        stringBuilder.append("Can't serialize object of class ");
        stringBuilder.append(valueOf2);
        stringBuilder.append(" with BeanMapper for class ");
        stringBuilder.append(valueOf);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}
