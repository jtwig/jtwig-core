package org.jtwig.render;

public class RenderResult {
    private final StringBuilder stringBuilder;

    public RenderResult() {
        this.stringBuilder = new StringBuilder();
    }

    public RenderResult append(String escape) {
        stringBuilder.append(escape);
        return this;
    }

    public String toString () {
        return stringBuilder.toString();
    }
}
