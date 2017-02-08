package org.jtwig.property.strategy;

import com.google.common.base.Optional;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.property.resolver.PropertyResolver;
import org.jtwig.property.strategy.method.MethodPropertyResolverFactory;
import org.jtwig.property.strategy.method.finder.PropertyMethodFinder;
import org.jtwig.reflection.model.java.JavaClassManager;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class VariableMethodPropertyResolverStrategyTest {
    private final JavaClassManager classManager = mock(JavaClassManager.class);
    private final PropertyMethodFinder propertyMethodFinder = mock(PropertyMethodFinder.class);
    private final MethodPropertyResolverFactory methodPropertyResolverFactory = mock(MethodPropertyResolverFactory.class);
    private VariableMethodPropertyResolverStrategy underTest = new VariableMethodPropertyResolverStrategy(classManager, propertyMethodFinder, methodPropertyResolverFactory);

    @Test
    public void selectNonVariable() throws Exception {
        PropertyResolverStrategy.Request request = mock(PropertyResolverStrategy.Request.class);
        FunctionExpression functionExpression = mock(FunctionExpression.class);

        given(request.getRightExpression()).willReturn(functionExpression);

        Optional<PropertyResolver> result = underTest.select(request);

        assertThat(result.isPresent(), is(false));
    }
}