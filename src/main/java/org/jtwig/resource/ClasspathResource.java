package org.jtwig.resource;

import org.jtwig.resource.classpath.ClasspathResourceLoader;

import java.io.InputStream;

public class ClasspathResource implements Resource {
    private final String path;
    private final ClasspathResourceLoader defaultClasspathResourceLoader;

    public ClasspathResource(String path, ClasspathResourceLoader defaultClasspathResourceLoader) {
        this.path = path;
        this.defaultClasspathResourceLoader = defaultClasspathResourceLoader;
    }

    @Override
    public InputStream content() {
        return defaultClasspathResourceLoader.load(path);
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return String.format("(Classpath) %s", path);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClasspathResource that = (ClasspathResource) o;

        return path.equals(that.path);

    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }
}
