package org.jtwig.util;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.java.JavaClass;

import static java.lang.Class.forName;

public class ClasspathFinder {
    private final ClassLoader classLoader;

    public ClasspathFinder(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public boolean exists (String className) {
        return load(className).isPresent();
    }

    public Optional<JavaClass> load(String className) {
        try {
            return Optional.of(new JavaClass(forName(className, false, classLoader)));
        } catch (Exception e) {
            return Optional.absent();
        }
    }
}
