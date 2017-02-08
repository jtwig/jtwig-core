package org.jtwig.property.strategy.method.finder;

import com.google.common.base.Optional;
import org.jtwig.property.strategy.method.MethodArgumentsMatcher;
import org.jtwig.reflection.model.java.JavaClass;
import org.jtwig.reflection.model.java.JavaMethod;
import org.jtwig.reflection.model.java.JavaMethods;

import java.util.List;

public class ExactMethodNamePropertyMethodFinder implements PropertyMethodFinder {
    private final MethodArgumentsMatcher methodArgumentsMatcher;

    public ExactMethodNamePropertyMethodFinder(MethodArgumentsMatcher methodArgumentsMatcher) {
        this.methodArgumentsMatcher = methodArgumentsMatcher;
    }

    @Override
    public Optional<JavaMethod> find(JavaClass type, String identifier, List<Object> arguments) {
        if (arguments.isEmpty()) return type.method(identifier).getMethod();
        else {
            JavaMethods javaMethods = type.method(identifier);
            for (JavaMethod method : javaMethods.getMethods()) {
                if (methodArgumentsMatcher.matches(method, arguments)) {
                    return Optional.of(method);
                }
            }

            return Optional.absent();
        }
    }
}
