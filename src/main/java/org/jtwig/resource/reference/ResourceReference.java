package org.jtwig.resource.reference;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jtwig.render.context.OnEndTrigger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ResourceReference implements OnEndTrigger {
    public static final String ANY_TYPE = "any";
    public static final String STRING = "string";
    public static final String FILE = "file";
    public static final String MEMORY = "memory";
    public static final String CLASSPATH = "classpath";

    public static ResourceReference inline (String template) {
        return new ResourceReference(STRING, template);
    }

    public static ResourceReference memory (String name) {
        return new ResourceReference(MEMORY, name);
    }

    public static ResourceReference file (String path) {
        return new ResourceReference(FILE, path);
    }

    public static ResourceReference file (File path) {
        return new ResourceReference(FILE, path.getAbsolutePath());
    }

    public static ResourceReference classpath (String path) {
        return new ResourceReference(CLASSPATH, path);
    }

    private final String type;
    private final String path;
    private final List<Runnable> endTasks = new ArrayList<>();

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

    public void onEnd (Runnable runnable) {
        endTasks.add(runnable);
    }

    @Override
    public void end() {
        for (Runnable endTask : endTasks) {
            endTask.run();
        }
    }
}
