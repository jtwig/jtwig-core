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
    public static FileResourceLoader instance () {
        return new FileResourceLoader(new File(""));
    }

    private final File baseDirectory;

    public FileResourceLoader (File baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    @Override
    public Optional<Charset> getCharset(String path) {
        return Optional.absent();
    }

    @Override
    public InputStream load(String path) {
        try {
            return new FileInputStream(file(path));
        } catch (FileNotFoundException e) {
            throw new ResourceNotFoundException(e);
        }
    }

    @Override
    public boolean exists(String path) {
        return file(path).exists();
    }

    @Override
    public Optional<URL> toUrl(String path) {
        try {
            return Optional.of(file(path).toURI().toURL());
        } catch (MalformedURLException e) {
            return Optional.absent();
        }
    }

    private File file(String path) {
        File file = new File(path);
        if (file.isAbsolute()) {
            return file;
        } else {
            return new File(baseDirectory, path);
        }
    }
}
