package com.google.ar.core.exceptions;

public class FatalException extends RuntimeException {
    public FatalException(String str) {
        super(str);
    }

    public FatalException(String str, Throwable th) {
        super(str, th);
    }
}
