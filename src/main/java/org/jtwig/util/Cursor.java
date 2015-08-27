package org.jtwig.util;

import org.jtwig.context.values.ValueContext;

import java.util.Collection;

public class Cursor {
    private int index;
    private final ValueContext parentValueContext;
    private final Collection<?> objects;

    public Cursor(ValueContext parentValueContext, Collection<?> objects) {
        this.parentValueContext = parentValueContext;
        this.objects = objects;
        this.index = 0;
    }

    public void step() {
        index++;
    }

    public int getIndex () {
        return index + 1;
    }

    public int getIndex0 () {
        return index;
    }

    public int getLength () {
        return objects.size();
    }

    public ValueContext getParent () {
        return parentValueContext;
    }

    public boolean isFirst () {
        return index == 0;
    }

    public boolean isLast () {
        return index == getLength() - 1;
    }

    public int getRevindex () {
        return getLength() - getIndex0();
    }

    public int getRevindex0 () {
        return getLength() - getIndex();
    }
}
