package org.jtwig.property.strategy;

import com.google.common.base.Optional;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.property.resolver.PropertyResolver;
import org.jtwig.property.strategy.method.MethodPropertyResolverFactory;
import org.jtwig.property.strategy.method.finder.PropertyMethodFinder;
import org.jtwig.reflection.model.java.JavaClass;
import org.jtwig.reflection.model.java.JavaClassManager;
import org.jtwig.reflection.model.java.JavaMethod;

import java.util.Collections;

public class VariableMethodPropertyResolverStrategy implements PropertyResolverStrategy {
    private final JavaClassManager classManager;
    private final PropertyMethodFinder propertyMethodFinder;
    private final MethodPropertyResolverFactory methodPropertyResolverFactory;

    public VariableMethodPropertyResolverStrategy(JavaClassManager classManager, PropertyMethodFinder propertyMethodFinder, MethodPropertyResolverFactory methodPropertyResolverFactory) {
        this.classManager = classManager;
        this.propertyMethodFinder = propertyMethodFinder;
        this.methodPropertyResolverFactory = methodPropertyResolverFactory;
    }

    @Override
    public Optional<PropertyResolver> select(Request request) {
        if (request.getRightExpression() instanceof VariableExpression) {
            String identifier = ((VariableExpression) request.getRightExpression()).getIdentifier();
            JavaClass javaClass = classManager.metadata(request.getLeftValue().getClass());
            Optional<JavaMethod> method = propertyMethodFinder.find(javaClass, identifier, Collections.emptyList());
            return methodPropertyResolverFactory.create(method);
        }
        return Optional.absent();
    }
}
