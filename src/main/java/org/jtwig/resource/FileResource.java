package org.jtwig.resource;

import org.jtwig.resource.exceptions.ResourceNotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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

        return file.equals(that.file);

    }

    @Override
    public int hashCode() {
        return file.hashCode();
    }
}
