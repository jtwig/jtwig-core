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
}
