package org.jtwig.value;

public final class Undefined {
    public static final Undefined UNDEFINED = new Undefined();

    private Undefined() {}

    @Override
    public String toString() {
        return "";
    }
}
