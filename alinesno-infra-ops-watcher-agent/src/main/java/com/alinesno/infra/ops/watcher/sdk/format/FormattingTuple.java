package com.alinesno.infra.ops.watcher.sdk.format;

public class FormattingTuple {
    public static FormattingTuple NULL = new FormattingTuple((String)null);
    private final String message;
    private final Throwable throwable;
    private final Object[] argArray;

    public FormattingTuple(String message) {
        this(message, (Object[])null, (Throwable)null);
    }

    public FormattingTuple(String message, Object[] argArray, Throwable throwable) {
        this.message = message;
        this.throwable = throwable;
        this.argArray = argArray;
    }

    public String getMessage() {
        return this.message;
    }

    public Object[] getArgArray() {
        return this.argArray;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }
}
