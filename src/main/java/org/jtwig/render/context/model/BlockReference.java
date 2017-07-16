package org.jtwig.render.context.model;

public class BlockReference {
    private String identifier;

    public BlockReference(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public BlockReference setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }
}
