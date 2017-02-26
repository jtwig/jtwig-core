package org.jtwig.property.configuration;

import org.jtwig.macro.render.MacroRender;
import org.jtwig.model.expression.Expression;
import org.jtwig.property.resolver.PropertyResolver;
import org.jtwig.property.selection.cache.SelectionPropertyResolverPersistentCache;
import org.jtwig.property.strategy.*;
import org.jtwig.property.strategy.method.ArgumentsConverter;
import org.jtwig.property.strategy.method.FunctionArgumentCalculator;
import org.jtwig.property.strategy.method.MethodArgumentsMatcher;
import org.jtwig.property.strategy.method.MethodPropertyResolverFactory;
import org.jtwig.property.strategy.method.argument.AssignableTypes;
import org.jtwig.property.strategy.method.argument.IsNativeType;
import org.jtwig.property.strategy.method.argument.group.GroupingArgumentsService;
import org.jtwig.property.strategy.method.convert.NativeTypeConverter;
import org.jtwig.property.strategy.method.finder.CompositePropertyMethodFinder;
import org.jtwig.property.strategy.method.finder.ExactMethodNamePropertyMethodFinder;
import org.jtwig.property.strategy.method.finder.PrefixedMethodNamePropertyMethodFinder;
import org.jtwig.reflection.model.java.JavaClassManager;

import java.util.concurrent.ConcurrentHashMap;

import static java.util.Arrays.asList;

public class DefaultPropertyResolverConfiguration extends PropertyResolverConfiguration {
    public DefaultPropertyResolverConfiguration() {
        super(new SelectionPropertyResolverPersistentCache(new ConcurrentHashMap<Expression, PropertyResolver>()), asList(
                new MacroPropertyResolverStrategy(new MacroRender()),
                new MapPropertyResolverStrategy(),
                new ValueContextPropertyResolverStrategy(),
                new FunctionMethodPropertyResolverStrategy(JavaClassManager.classManager(), new CompositePropertyMethodFinder(asList(
                        new ExactMethodNamePropertyMethodFinder(new MethodArgumentsMatcher(new ArgumentsConverter(new NativeTypeConverter(IsNativeType.instance(), new AssignableTypes(IsNativeType.instance())), new GroupingArgumentsService()))),
                        new PrefixedMethodNamePropertyMethodFinder("get"),
                        new PrefixedMethodNamePropertyMethodFinder("is"),
                        new PrefixedMethodNamePropertyMethodFinder("has")
                )), new MethodPropertyResolverFactory(new ArgumentsConverter(new NativeTypeConverter(IsNativeType.instance(), new AssignableTypes(IsNativeType.instance())), new GroupingArgumentsService())), new FunctionArgumentCalculator()),
                new VariableMethodPropertyResolverStrategy(JavaClassManager.classManager(), new CompositePropertyMethodFinder(asList(
                        new ExactMethodNamePropertyMethodFinder(new MethodArgumentsMatcher(new ArgumentsConverter(new NativeTypeConverter(IsNativeType.instance(), new AssignableTypes(IsNativeType.instance())), new GroupingArgumentsService()))),
                        new PrefixedMethodNamePropertyMethodFinder("get"),
                        new PrefixedMethodNamePropertyMethodFinder("is"),
                        new PrefixedMethodNamePropertyMethodFinder("has")
                )), new MethodPropertyResolverFactory(new ArgumentsConverter(new NativeTypeConverter(IsNativeType.instance(), new AssignableTypes(IsNativeType.instance())), new GroupingArgumentsService()))),
                new FieldPropertyResolverStrategy(JavaClassManager.classManager()),
                new GetMethodPropertyResolverStrategy(JavaClassManager.classManager())
        ));
    }
}
