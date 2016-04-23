package org.jtwig.resource;

import org.jtwig.resource.exceptions.ResourceException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

public class UrlResource implements Resource {
    private static final String FILE = "file";

    public static Resource canonical (Charset charset, URL url) {
        if (FILE.equals(url.getProtocol())) {
            return new FileResource(charset, new File(url.getFile()));
        }
        return new UrlResource(charset, url);
    }

    private final Charset charset;
    private final URL url;

    public UrlResource(Charset charset, URL url) {
        this.charset = charset;
        this.url = url;
    }

    @Override
    public InputStream getContent() {
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new ResourceException("Cannot open the url resource provided", e);
        }
    }

    @Override
    public Charset getCharset() {
        return charset;
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UrlResource that = (UrlResource) o;

        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }
}
