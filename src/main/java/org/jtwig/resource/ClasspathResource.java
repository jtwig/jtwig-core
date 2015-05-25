package org.jtwig.resource;

import org.jtwig.resource.classpath.ResourceLoader;

import java.io.InputStream;

public class ClasspathResource implements Resource {
    private final String path;
    private final ResourceLoader resourceLoader;

    public ClasspathResource(String path, ResourceLoader resourceLoader) {
        this.path = path;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public InputStream content() {
        return resourceLoader.load(path);
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
