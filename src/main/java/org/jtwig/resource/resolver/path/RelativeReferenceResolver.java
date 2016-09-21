package org.jtwig.resource.resolver.path;

public interface RelativeReferenceResolver {
    boolean isRelative(String path);
    String resolve(String parent, String child);
}
