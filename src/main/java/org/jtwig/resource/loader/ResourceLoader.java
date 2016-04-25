package org.jtwig.resource.loader;

import com.google.common.base.Optional;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

public interface ResourceLoader {
    Optional<Charset> getCharset(String path);
    InputStream load (String path);
    boolean exists (String path);
    Optional<URL> toUrl (String path);
}
