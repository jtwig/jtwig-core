package org.jtwig.util;

import org.jtwig.value.WrappedCollection;
import org.jtwig.value.context.ValueContext;

import java.util.concurrent.atomic.AtomicInteger;

public class LoopCursor {
    private final AtomicInteger index;
    private final ValueContext parentContext;
    private final WrappedCollection collection;

    public LoopCursor(ValueContext parentContext, WrappedCollection collection) {
        this.index = new AtomicInteger(0);
        this.parentContext = parentContext;
        this.collection = collection;
    }

    public int step() {
        return index.getAndIncrement();
    }

    public int getIndex () {
        return index.get() + 1;
    }

    public int getIndex0 () {
        return index.get();
    }

    public int getLength () {
        return collection.size();
    }

    public ValueContext getParent () {
        return parentContext;
    }

    public boolean isFirst () {
        return index.get() == 0;
    }

    public boolean isLast () {
        return index.get() >= getLength() - 1;
    }

    public int getRevindex () {
        return getLength() - getIndex0();
    }

    public int getRevindex0 () {
        return getLength() - getIndex();
    }
}
