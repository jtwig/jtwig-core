package org.jtwig.resource.loader;

import com.google.common.base.Optional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

public class StringResourceLoader implements ResourceLoader {
    private static final StringResourceLoader INSTANCE = new StringResourceLoader();
    public static StringResourceLoader instance () {
        return INSTANCE;
    }

    private StringResourceLoader() {}

    @Override
    public Optional<Charset> getCharset(String path) {
        return Optional.absent();
    }

    @Override
    public InputStream load(String path) {
        return new ByteArrayInputStream(path.getBytes());
    }

    @Override
    public boolean exists(String path) {
        return true;
    }

    @Override
    public Optional<URL> toUrl(String path) {
        return Optional.absent();
    }
}
