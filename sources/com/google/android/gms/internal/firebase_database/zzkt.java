package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.GenericTypeIndicator;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class zzkt {
    private static final ConcurrentMap<Class<?>, zzku<?>> zzuu = new ConcurrentHashMap();

    private static <T> zzku<T> zza(Class<T> cls) {
        zzku<T> zzku = (zzku) zzuu.get(cls);
        if (zzku != null) {
            return zzku;
        }
        zzku = new zzku(cls);
        zzuu.put(cls, zzku);
        return zzku;
    }

    public static <T> T zza(Object obj, GenericTypeIndicator<T> genericTypeIndicator) {
        Type genericSuperclass = genericTypeIndicator.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            if (parameterizedType.getRawType().equals(GenericTypeIndicator.class)) {
                return zza(obj, parameterizedType.getActualTypeArguments()[0]);
            }
            String valueOf = String.valueOf(genericSuperclass);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 47);
            stringBuilder.append("Not a direct subclass of GenericTypeIndicator: ");
            stringBuilder.append(valueOf);
            throw new DatabaseException(stringBuilder.toString());
        }
        valueOf = String.valueOf(genericSuperclass);
        stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 47);
        stringBuilder.append("Not a direct subclass of GenericTypeIndicator: ");
        stringBuilder.append(valueOf);
        throw new DatabaseException(stringBuilder.toString());
    }

    public static <T> T zza(Object obj, Class<T> cls) {
        return zzb(obj, (Class) cls);
    }

    private static <T> T zza(Object obj, Type type) {
        if (obj == null) {
            return null;
        }
        StringBuilder stringBuilder;
        String valueOf;
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class cls = (Class) parameterizedType.getRawType();
            int i = 0;
            T arrayList;
            if (List.class.isAssignableFrom(cls)) {
                type = parameterizedType.getActualTypeArguments()[0];
                if (obj instanceof List) {
                    List<Object> list = (List) obj;
                    arrayList = new ArrayList(list.size());
                    for (Object zza : list) {
                        arrayList.add(zza(zza, type));
                    }
                    return arrayList;
                }
                String valueOf2 = String.valueOf(obj.getClass());
                stringBuilder = new StringBuilder(String.valueOf(valueOf2).length() + 47);
                stringBuilder.append("Expected a List while deserializing, but got a ");
                stringBuilder.append(valueOf2);
                throw new DatabaseException(stringBuilder.toString());
            } else if (Map.class.isAssignableFrom(cls)) {
                Object obj2 = parameterizedType.getActualTypeArguments()[0];
                type = parameterizedType.getActualTypeArguments()[1];
                if (obj2.equals(String.class)) {
                    r6 = zzj(obj);
                    arrayList = new HashMap();
                    for (Entry entry : r6.entrySet()) {
                        arrayList.put((String) entry.getKey(), zza(entry.getValue(), type));
                    }
                    return arrayList;
                }
                valueOf = String.valueOf(obj2);
                stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 70);
                stringBuilder.append("Only Maps with string keys are supported, but found Map with key type ");
                stringBuilder.append(valueOf);
                throw new DatabaseException(stringBuilder.toString());
            } else if (Collection.class.isAssignableFrom(cls)) {
                throw new DatabaseException("Collections are not supported, please use Lists instead");
            } else {
                r6 = zzj(obj);
                zzku zza2 = zza(cls);
                Map hashMap = new HashMap();
                TypeVariable[] typeParameters = zza2.zzuv.getTypeParameters();
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments.length == typeParameters.length) {
                    while (i < typeParameters.length) {
                        hashMap.put(typeParameters[i], actualTypeArguments[i]);
                        i++;
                    }
                    return zza2.zza(r6, hashMap);
                }
                throw new IllegalStateException("Mismatched lengths for type variables and actual types");
            }
        } else if (type instanceof Class) {
            return zzb(obj, (Class) type);
        } else {
            if (type instanceof WildcardType) {
                throw new DatabaseException("Generic wildcard types are not supported");
            } else if (type instanceof GenericArrayType) {
                throw new DatabaseException("Generic Arrays are not supported, please use Lists instead");
            } else {
                valueOf = String.valueOf(type);
                stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 26);
                stringBuilder.append("Unknown type encountered: ");
                stringBuilder.append(valueOf);
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
    }

    private static <T> T zzb(Object obj, Class<T> cls) {
        if (obj == null) {
            return null;
        }
        String name;
        StringBuilder stringBuilder;
        String name2;
        if (!(cls.isPrimitive() || Number.class.isAssignableFrom(cls) || Boolean.class.isAssignableFrom(cls))) {
            if (!Character.class.isAssignableFrom(cls)) {
                if (String.class.isAssignableFrom(cls)) {
                    if (obj instanceof String) {
                        return (String) obj;
                    }
                    name = obj.getClass().getName();
                    stringBuilder = new StringBuilder(String.valueOf(name).length() + 42);
                    stringBuilder.append("Failed to convert value of type ");
                    stringBuilder.append(name);
                    stringBuilder.append(" to String");
                    throw new DatabaseException(stringBuilder.toString());
                } else if (cls.isArray()) {
                    throw new DatabaseException("Converting to Arrays is not supported, please use Listsinstead");
                } else if (cls.getTypeParameters().length > 0) {
                    name2 = cls.getName();
                    stringBuilder = new StringBuilder(String.valueOf(name2).length() + 75);
                    stringBuilder.append("Class ");
                    stringBuilder.append(name2);
                    stringBuilder.append(" has generic type parameters, please use GenericTypeIndicator instead");
                    throw new DatabaseException(stringBuilder.toString());
                } else if (cls.equals(Object.class)) {
                    return obj;
                } else {
                    if (cls.isEnum()) {
                        return zzc(obj, cls);
                    }
                    zzku zza = zza(cls);
                    if (obj instanceof Map) {
                        return zza.zza(zzj(obj), Collections.emptyMap());
                    }
                    name = obj.getClass().getName();
                    name2 = cls.getName();
                    StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(name).length() + 38) + String.valueOf(name2).length());
                    stringBuilder2.append("Can't convert object of type ");
                    stringBuilder2.append(name);
                    stringBuilder2.append(" to type ");
                    stringBuilder2.append(name2);
                    throw new DatabaseException(stringBuilder2.toString());
                }
            }
        }
        if (!Integer.class.isAssignableFrom(cls)) {
            if (!Integer.TYPE.isAssignableFrom(cls)) {
                if (!Boolean.class.isAssignableFrom(cls)) {
                    if (!Boolean.TYPE.isAssignableFrom(cls)) {
                        if (!Double.class.isAssignableFrom(cls)) {
                            if (!Double.TYPE.isAssignableFrom(cls)) {
                                if (!Long.class.isAssignableFrom(cls)) {
                                    if (!Long.TYPE.isAssignableFrom(cls)) {
                                        if (!Float.class.isAssignableFrom(cls)) {
                                            if (!Float.TYPE.isAssignableFrom(cls)) {
                                                if (Short.class.isAssignableFrom(cls) || Short.TYPE.isAssignableFrom(cls)) {
                                                    throw new DatabaseException("Deserializing to shorts is not supported");
                                                } else if (Byte.class.isAssignableFrom(cls) || Byte.TYPE.isAssignableFrom(cls)) {
                                                    throw new DatabaseException("Deserializing to bytes is not supported");
                                                } else {
                                                    if (!Character.class.isAssignableFrom(cls)) {
                                                        if (!Character.TYPE.isAssignableFrom(cls)) {
                                                            name2 = String.valueOf(cls);
                                                            stringBuilder = new StringBuilder(String.valueOf(name2).length() + 24);
                                                            stringBuilder.append("Unknown primitive type: ");
                                                            stringBuilder.append(name2);
                                                            throw new IllegalArgumentException(stringBuilder.toString());
                                                        }
                                                    }
                                                    throw new DatabaseException("Deserializing to char is not supported");
                                                }
                                            }
                                        }
                                        return Float.valueOf(zzk(obj).floatValue());
                                    }
                                }
                                if (obj instanceof Integer) {
                                    return Long.valueOf(((Integer) obj).longValue());
                                }
                                if (obj instanceof Long) {
                                    return (Long) obj;
                                }
                                if (obj instanceof Double) {
                                    Double d = (Double) obj;
                                    if (d.doubleValue() >= -9.223372036854776E18d && d.doubleValue() <= 9.223372036854776E18d) {
                                        return Long.valueOf(d.longValue());
                                    }
                                    name = String.valueOf(d);
                                    stringBuilder = new StringBuilder(String.valueOf(name).length() + 89);
                                    stringBuilder.append("Numeric value out of 64-bit long range: ");
                                    stringBuilder.append(name);
                                    stringBuilder.append(". Did you mean to use a double instead of a long?");
                                    throw new DatabaseException(stringBuilder.toString());
                                }
                                name = obj.getClass().getName();
                                stringBuilder = new StringBuilder(String.valueOf(name).length() + 42);
                                stringBuilder.append("Failed to convert a value of type ");
                                stringBuilder.append(name);
                                stringBuilder.append(" to long");
                                throw new DatabaseException(stringBuilder.toString());
                            }
                        }
                        return zzk(obj);
                    }
                }
                if (obj instanceof Boolean) {
                    return (Boolean) obj;
                }
                name = obj.getClass().getName();
                stringBuilder = new StringBuilder(String.valueOf(name).length() + 43);
                stringBuilder.append("Failed to convert value of type ");
                stringBuilder.append(name);
                stringBuilder.append(" to boolean");
                throw new DatabaseException(stringBuilder.toString());
            }
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        if (!(obj instanceof Long)) {
            if (!(obj instanceof Double)) {
                name = obj.getClass().getName();
                stringBuilder = new StringBuilder(String.valueOf(name).length() + 41);
                stringBuilder.append("Failed to convert a value of type ");
                stringBuilder.append(name);
                stringBuilder.append(" to int");
                throw new DatabaseException(stringBuilder.toString());
            }
        }
        Number number = (Number) obj;
        double doubleValue = number.doubleValue();
        if (doubleValue >= -2.147483648E9d && doubleValue <= 2.147483647E9d) {
            return Integer.valueOf(number.intValue());
        }
        stringBuilder2 = new StringBuilder(124);
        stringBuilder2.append("Numeric value out of 32-bit integer range: ");
        stringBuilder2.append(doubleValue);
        stringBuilder2.append(". Did you mean to use a long or double instead of an int?");
        throw new DatabaseException(stringBuilder2.toString());
    }

    private static <T> T zzc(Object obj, Class<T> cls) {
        String str;
        if (obj instanceof String) {
            str = (String) obj;
            try {
                return Enum.valueOf(cls, str);
            } catch (IllegalArgumentException e) {
                String name = cls.getName();
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(name).length() + 42) + String.valueOf(str).length());
                stringBuilder.append("Could not find enum value of ");
                stringBuilder.append(name);
                stringBuilder.append(" for value \"");
                stringBuilder.append(str);
                stringBuilder.append("\"");
                throw new DatabaseException(stringBuilder.toString());
            }
        }
        name = String.valueOf(cls);
        str = String.valueOf(obj.getClass());
        stringBuilder = new StringBuilder((String.valueOf(name).length() + 57) + String.valueOf(str).length());
        stringBuilder.append("Expected a String while deserializing to enum ");
        stringBuilder.append(name);
        stringBuilder.append(" but got a ");
        stringBuilder.append(str);
        throw new DatabaseException(stringBuilder.toString());
    }

    public static Object zzh(Object obj) {
        return zzi(obj);
    }

    private static <T> Object zzi(T t) {
        if (t == null) {
            return null;
        }
        if (t instanceof Number) {
            if (!(t instanceof Float)) {
                if (!(t instanceof Double)) {
                    if (t instanceof Short) {
                        throw new DatabaseException("Shorts are not supported, please use int or long");
                    } else if (!(t instanceof Byte)) {
                        return t;
                    } else {
                        throw new DatabaseException("Bytes are not supported, please use int or long");
                    }
                }
            }
            Number number = (Number) t;
            double doubleValue = number.doubleValue();
            return (doubleValue > 9.223372036854776E18d || doubleValue < -9.223372036854776E18d || Math.floor(doubleValue) != doubleValue) ? Double.valueOf(doubleValue) : Long.valueOf(number.longValue());
        } else if ((t instanceof String) || (t instanceof Boolean)) {
            return t;
        } else {
            if (t instanceof Character) {
                throw new DatabaseException("Characters are not supported, please use Strings");
            } else if (t instanceof Map) {
                Map hashMap = new HashMap();
                for (Entry entry : ((Map) t).entrySet()) {
                    Object key = entry.getKey();
                    if (key instanceof String) {
                        hashMap.put((String) key, zzi(entry.getValue()));
                    } else {
                        throw new DatabaseException("Maps with non-string keys are not supported");
                    }
                }
                return hashMap;
            } else if (t instanceof Collection) {
                if (t instanceof List) {
                    List<Object> list = (List) t;
                    List arrayList = new ArrayList(list.size());
                    for (Object zzi : list) {
                        arrayList.add(zzi(zzi));
                    }
                    return arrayList;
                }
                throw new DatabaseException("Serializing Collections is not supported, please use Lists instead");
            } else if (!t.getClass().isArray()) {
                return t instanceof Enum ? ((Enum) t).name() : zza(t.getClass()).zzm(t);
            } else {
                throw new DatabaseException("Serializing Arrays is not supported, please use Lists instead");
            }
        }
    }

    public static Map<String, Object> zzi(Map<String, Object> map) {
        Object zzi = zzi((Object) map);
        zzkq.zzf(zzi instanceof Map);
        return (Map) zzi;
    }

    private static Map<String, Object> zzj(Object obj) {
        if (obj instanceof Map) {
            return (Map) obj;
        }
        String valueOf = String.valueOf(obj.getClass());
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 46);
        stringBuilder.append("Expected a Map while deserializing, but got a ");
        stringBuilder.append(valueOf);
        throw new DatabaseException(stringBuilder.toString());
    }

    private static Double zzk(Object obj) {
        if (obj instanceof Integer) {
            return Double.valueOf(((Integer) obj).doubleValue());
        }
        String valueOf;
        StringBuilder stringBuilder;
        if (obj instanceof Long) {
            Long l = (Long) obj;
            Double valueOf2 = Double.valueOf(l.doubleValue());
            if (valueOf2.longValue() == l.longValue()) {
                return valueOf2;
            }
            valueOf = String.valueOf(obj);
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 97);
            stringBuilder.append("Loss of precision while converting number to double: ");
            stringBuilder.append(valueOf);
            stringBuilder.append(". Did you mean to use a 64-bit long instead?");
            throw new DatabaseException(stringBuilder.toString());
        } else if (obj instanceof Double) {
            return (Double) obj;
        } else {
            valueOf = obj.getClass().getName();
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 44);
            stringBuilder.append("Failed to convert a value of type ");
            stringBuilder.append(valueOf);
            stringBuilder.append(" to double");
            throw new DatabaseException(stringBuilder.toString());
        }
    }
}
