package org.jtwig.model.tree;

import org.jtwig.model.position.Position;

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

    public Configuration getConfiguration() {
        return configuration;
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
