package org.jtwig.resource.reference;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ResourceReference {
    public static final String ANY_TYPE = "any";
    public static final String STRING = "string";
    public static final String FILE = "file";
    public static final String MEMORY = "memory";
    public static final String CLASSPATH = "classpath";

    private final String type;
    private final String path;

    public ResourceReference(String type, String path) {
        this.type = type;
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ResourceReference that = (ResourceReference) o;

        return new EqualsBuilder()
                .append(type, that.type)
                .append(path, that.path)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(type)
                .append(path)
                .toHashCode();
    }

    @Override
    public String toString() {
        if (ANY_TYPE.equals(type)) return path;
        else return String.format("%s:%s", type, path);
    }
}
