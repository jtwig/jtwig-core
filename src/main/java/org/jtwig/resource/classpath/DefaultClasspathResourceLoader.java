package org.jtwig.resource.classpath;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class DefaultClasspathResourceLoader implements ClasspathResourceLoader {
    private final ClassLoader classLoader;

    public DefaultClasspathResourceLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public boolean exists(String path) {
        try {
            URL resource = classLoader.getResource(preparePath(path));
            if (resource == null) {
                return false;
            } else {
                return new File(new URI(resource.toString())).exists();
            }
        } catch (IllegalArgumentException e) {
            return false;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    private String preparePath(String path) {
        if (path.startsWith(File.separator)) {
            return path.substring(File.separator.length());
        } else {
            return path;
        }
    }

    @Override
    public InputStream load(String location) {
        return classLoader.getResourceAsStream(preparePath(location));
    }
}
