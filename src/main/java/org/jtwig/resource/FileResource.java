package org.jtwig.resource;

import org.jtwig.resource.exceptions.ResourceNotFoundException;

import java.io.*;
import java.nio.charset.Charset;

public class FileResource implements Resource {
    private final Charset charset;
    private final File file;

    public FileResource(Charset charset, File file) {
        this.charset = charset;
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public InputStream getContent() {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new ResourceNotFoundException(e);
        }
    }

    @Override
    public Charset getCharset() {
        return charset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileResource that = (FileResource) o;

        try {
            return file.getCanonicalPath().equals(that.file.getCanonicalPath());
        } catch (IOException e) {
            return file.equals(that.file);
        }
    }

    @Override
    public int hashCode() {
        try {
            return file.getCanonicalPath().hashCode();
        } catch (IOException e) {
            return file.hashCode();
        }
    }
}
