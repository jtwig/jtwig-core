package org.jtwig.resource.metadata;

import com.google.common.base.Optional;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

public interface ResourceMetadata {
    boolean exists ();
    InputStream load();
    Optional<Charset> getCharset ();
    Optional<URL> toUrl ();
}
