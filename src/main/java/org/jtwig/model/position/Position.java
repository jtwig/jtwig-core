package org.jtwig.model.position;

import org.jtwig.resource.Resource;

public class Position {
    private final Resource resource;
    private final int line;
    private final int column;

    public Position(Resource resource, int line, int column) {
        this.resource = resource;
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return String.format("%s (Line: %d, Column: %d)", resource, line, column);
    }
}
