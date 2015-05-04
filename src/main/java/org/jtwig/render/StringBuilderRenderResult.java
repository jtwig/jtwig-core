package org.jtwig.render;

public class StringBuilderRenderResult implements RenderResult {
    private final StringBuilder builder = new StringBuilder();

    @Override
    public RenderResult append(String content) {
        builder.append(content);
        return this;
    }

    @Override
    public RenderResult flush() {
        return this;
    }

    @Override
    public String content() {
        return builder.toString();
    }
}
