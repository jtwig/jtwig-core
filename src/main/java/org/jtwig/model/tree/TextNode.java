package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.render.impl.StringRenderable;

public class TextNode extends Node {
    private final String text;
    private final Configuration configuration;

    public TextNode(Position position, String text, Configuration configuration) {
        super(position);
        this.text = text;
        this.configuration = configuration;
    }

    public String getText() {
        return text;
    }

    public TextNode trimRight(boolean trimRight) {
        this.configuration.setTrimRight(trimRight);
        return this;
    }

    private String trimRight(String content) {
        return content.replaceAll("\\s+$", "");
    }

    private String trimLeft(String content) {
        return content.replaceAll("^\\s+", "");
    }

    @Override
    public Renderable render(RenderContext context) {
        String modifiedContent = text;
        modifiedContent = configuration.isTrimLeft() ? trimLeft(modifiedContent) : modifiedContent;
        modifiedContent = configuration.isTrimRight() ? trimRight(modifiedContent) : modifiedContent;
        return new StringRenderable(modifiedContent, context.escapeMode());
    }

    public static class Configuration {
        private boolean trimLeft;
        private boolean trimRight;

        public boolean isTrimLeft() {
            return trimLeft;
        }

        public Configuration setTrimLeft(boolean trimLeft) {
            this.trimLeft = trimLeft;
            return this;
        }

        public boolean isTrimRight() {
            return trimRight;
        }

        public Configuration setTrimRight(boolean trimRight) {
            this.trimRight = trimRight;
            return this;
        }
    }
}
