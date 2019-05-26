package com.google.vr.dynamite.client;

import android.os.IBinder;
import com.google.vr.dynamite.client.IObjectWrapper.C0560a;
import java.lang.reflect.Field;

@UsedByReflection
public final class ObjectWrapper<T> extends C0560a {
    @UsedByReflection
    private final T wrappedObject;

    private ObjectWrapper(T t) {
        this.wrappedObject = t;
    }

    /* renamed from: a */
    public static <T> IObjectWrapper m242a(T t) {
        return new ObjectWrapper(t);
    }

    @UsedByReflection
    public static <T> T unwrap(IObjectWrapper iObjectWrapper, Class<T> cls) {
        if (iObjectWrapper == null) {
            return null;
        }
        if (iObjectWrapper instanceof ObjectWrapper) {
            return ((ObjectWrapper) iObjectWrapper).wrappedObject;
        }
        IBinder asBinder = iObjectWrapper.asBinder();
        Field field = null;
        for (Field field2 : asBinder.getClass().getDeclaredFields()) {
            if (!field2.isSynthetic()) {
                if (field != null) {
                    field = null;
                    break;
                }
                field = field2;
            }
        }
        if (field == null) {
            throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly *one* declared private field for the wrapped object.  Preferably, this is an instance of the ObjectWrapper<T> class.");
        } else if (field.isAccessible()) {
            throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly one declared *private* field for the wrapped object. Preferably, this is an instance of the ObjectWrapper<T> class.");
        } else {
            field.setAccessible(true);
            try {
                Object obj = field.get(asBinder);
                if (obj == null) {
                    return null;
                }
                if (cls.isInstance(obj)) {
                    return cls.cast(obj);
                }
                throw new IllegalArgumentException("remoteBinder is the wrong class.");
            } catch (Throwable e) {
                throw new IllegalArgumentException("Binder object is null.", e);
            } catch (Throwable e2) {
                throw new IllegalArgumentException("remoteBinder is the wrong class.", e2);
            } catch (Throwable e22) {
                throw new IllegalArgumentException("Could not access the field in remoteBinder.", e22);
            }
        }
    }
}
