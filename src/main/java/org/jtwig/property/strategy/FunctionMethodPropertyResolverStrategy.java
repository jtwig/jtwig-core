package org.jtwig.property.strategy;

import com.google.common.base.Optional;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.property.resolver.PropertyResolver;
import org.jtwig.property.strategy.method.FunctionArgumentCalculator;
import org.jtwig.property.strategy.method.MethodPropertyResolverFactory;
import org.jtwig.property.strategy.method.finder.PropertyMethodFinder;
import org.jtwig.reflection.model.java.JavaClass;
import org.jtwig.reflection.model.java.JavaClassManager;
import org.jtwig.reflection.model.java.JavaMethod;

import java.util.List;

public class FunctionMethodPropertyResolverStrategy implements PropertyResolverStrategy {
    private final JavaClassManager classManager;
    private final PropertyMethodFinder propertyMethodFinder;
    private final MethodPropertyResolverFactory methodPropertyResolverFactory;
    private final FunctionArgumentCalculator functionArgumentCalculator;

    public FunctionMethodPropertyResolverStrategy(JavaClassManager classManager, PropertyMethodFinder propertyMethodFinder, MethodPropertyResolverFactory methodPropertyResolverFactory, FunctionArgumentCalculator functionArgumentCalculator) {
        this.classManager = classManager;
        this.propertyMethodFinder = propertyMethodFinder;
        this.methodPropertyResolverFactory = methodPropertyResolverFactory;
        this.functionArgumentCalculator = functionArgumentCalculator;
    }

    @Override
    public Optional<PropertyResolver> select(Request request) {
        if (request.getRightExpression() instanceof FunctionExpression) {
            FunctionExpression functionExpression = (FunctionExpression) request.getRightExpression();
            String identifier = functionExpression.getFunctionIdentifier();
            List<Expression> arguments = functionExpression.getArguments();
            List<Object> calculatedArguments = functionArgumentCalculator.calculate(request, arguments);
            JavaClass javaClass = classManager.metadata(request.getLeftValue().getClass());
            Optional<JavaMethod> method = propertyMethodFinder.find(javaClass, identifier, calculatedArguments);
            return methodPropertyResolverFactory.create(method);
        }
        return Optional.absent();
    }
}
