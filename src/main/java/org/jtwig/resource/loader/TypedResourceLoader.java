package org.jtwig.resource.loader;

public class TypedResourceLoader {
    private final String type;
    private final ResourceLoader resourceLoader;

    public TypedResourceLoader(String type, ResourceLoader resourceLoader) {
        this.type = type;
        this.resourceLoader = resourceLoader;
    }

    public String getType() {
        return type;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
