package org.jtwig.value.compare;

import org.jtwig.value.JtwigType;

import java.util.Comparator;

public class JtwigTypeComparator implements Comparator<JtwigType> {
    private static final JtwigTypeComparator instance = new JtwigTypeComparator();
    public static JtwigTypeComparator instance() {
        return instance;
    }

    @Override
    public int compare(JtwigType left, JtwigType right) {
        return 0;
    }
}
