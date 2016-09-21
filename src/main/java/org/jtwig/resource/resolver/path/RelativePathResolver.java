package org.jtwig.resource.resolver.path;

import org.jtwig.resource.exceptions.ResourceException;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.InvalidPathException;

public class RelativePathResolver implements RelativeReferenceResolver {
    public static final String ROOT_PATH = "/";
    private static final RelativePathResolver INSTANCE = new RelativePathResolver();

    public static RelativePathResolver instance () {
        return INSTANCE;
    }

    private RelativePathResolver () {}

    @Override
    public boolean isRelative(String path) {
        return !path.startsWith(ROOT_PATH);
    }

    @Override
    public String resolve(String parent, String child) {
        try {
            return new URI(String.format("%s/../%s", parent, child)).normalize().toString();
        } catch (InvalidPathException | URISyntaxException e) {
            throw new ResourceException("Invalid path", e);
        }
    }
}
