package org.jtwig.property.strategy.method.finder;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.java.JavaClass;
import org.jtwig.reflection.model.java.JavaMethod;

import java.util.List;

public interface PropertyMethodFinder {
    Optional<JavaMethod> find(JavaClass type, String identifier, List<Object> arguments);
}
