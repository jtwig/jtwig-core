package org.jtwig.resource.classpath;

import java.io.InputStream;

public interface ClasspathResourceLoader {
    boolean exists(String path);
    InputStream load(String location);
}
