package org.jtwig.resource.loader;

import com.google.common.base.Optional;
import org.jtwig.resource.exceptions.ResourceNotFoundException;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

public class ClasspathResourceLoader implements ResourceLoader {
    public static final String ROOT_PATH = "/";
    private final ClassLoader classLoader;

    public ClasspathResourceLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public Optional<Charset> getCharset(String path) {
        return Optional.absent();
    }

    @Override
    public InputStream load(String path) {
        InputStream result = classLoader.getResourceAsStream(getPath(path));
        if (result == null) throw new ResourceNotFoundException(String.format("Resource '%s' not found", path));
        return result;
    }

    @Override
    public boolean exists(String path) {
        URL result = classLoader.getResource(getPath(path));
        return result != null;
    }

    @Override
    public Optional<URL> toUrl(String path) {
        return Optional.fromNullable(classLoader.getResource(getPath(path)));
    }

    private String getPath(String path) {
        if (path.startsWith(ROOT_PATH)) {
            return path.substring(1);
        }
        return path;
    }
}
