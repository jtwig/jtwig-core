package org.jtwig.resource.loader;

import com.google.common.base.Optional;
import org.jtwig.resource.exceptions.ResourceNotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class FileResourceLoader implements ResourceLoader {
    private static final FileResourceLoader INSTANCE = new FileResourceLoader();

    public static FileResourceLoader instance () {
        return INSTANCE;
    }

    private FileResourceLoader () {}

    @Override
    public Optional<Charset> getCharset(String path) {
        return Optional.absent();
    }

    @Override
    public InputStream load(String path) {
        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new ResourceNotFoundException(e);
        }
    }

    @Override
    public boolean exists(String path) {
        return new File(path).exists();
    }

    @Override
    public Optional<URL> toUrl(String path) {
        try {
            return Optional.of(new File(path).toURI().toURL());
        } catch (MalformedURLException e) {
            return Optional.absent();
        }
    }
}
