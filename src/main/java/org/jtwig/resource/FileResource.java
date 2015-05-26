package org.jtwig.resource;

import org.jtwig.resource.exceptions.ResourceNotFoundException;

import java.io.*;

public class FileResource implements Resource {
    private final File file;

    public FileResource(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public InputStream content() {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new ResourceNotFoundException(e);
        }
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
