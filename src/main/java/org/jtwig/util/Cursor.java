package org.jtwig.util;

import java.util.Collection;

public class Cursor {
    private int index = 0;
    private final Collection<?> objects;

    public Cursor(Collection<?> objects) {
        this.objects = objects;
    }

    public void step() {
        index++;
    }
}
