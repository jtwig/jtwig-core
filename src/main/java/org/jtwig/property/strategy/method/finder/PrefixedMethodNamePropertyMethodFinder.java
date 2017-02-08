package org.jtwig.property.strategy.method.finder;

import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;
import org.jtwig.reflection.model.java.JavaClass;
import org.jtwig.reflection.model.java.JavaMethod;

import java.util.List;

public class PrefixedMethodNamePropertyMethodFinder implements PropertyMethodFinder {
    private final String prefix;

    public PrefixedMethodNamePropertyMethodFinder(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Optional<JavaMethod> find(JavaClass type, String identifier, List<Object> arguments) {
        if (arguments.isEmpty()) return type.method(prefix(identifier)).getMethod();
        return Optional.absent();
    }

    private String prefix(String identifier) {
        return prefix + StringUtils.capitalize(identifier);
    }
}
