package com.google.android.filament;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.LOCAL_VARIABLE, ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
public @interface EntityInstance {
    public static final int NULL = 0;
}
