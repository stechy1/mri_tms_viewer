/*
 * Decompiled with CFR 0_123.
 */
package exceptions;

public class NotYetImplementedException
extends Exception {
    public NotYetImplementedException(String exceptionText) {
        super(exceptionText);
    }

    public NotYetImplementedException() {
        this("Not yet implemented");
    }
}

