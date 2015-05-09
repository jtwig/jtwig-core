package org.jtwig.resource.classpath;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class ResourceLoader {
    private final ClassLoader classLoader;

    public ResourceLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public boolean exists (String path) {
        try {
            URL resource = classLoader.getResource(preparePath(path));
            return resource != null && new File(resource.toURI()).exists();
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

    public InputStream load (String location) {
        return classLoader.getResourceAsStream(preparePath(location));
    }
}
