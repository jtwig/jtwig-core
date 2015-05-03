package org.jtwig.util;

import static java.lang.Class.forName;

public class ClasspathFinder {
    private final ClassLoader classLoader;

    public ClasspathFinder(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public boolean exists (String className) {
        try {
            forName(className, false, classLoader);
            return true;
        }
        catch (Throwable ex) {
            // Class or one of its dependencies is not present...
            return false;
        }
    }
}
