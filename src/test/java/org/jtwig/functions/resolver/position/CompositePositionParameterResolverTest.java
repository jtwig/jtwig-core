package org.jtwig.functions.resolver.position;

import com.google.common.base.Optional;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositePositionParameterResolverTest {
    private Collection<PositionParameterResolver> resolvers = new ArrayList<>();
    private CompositePositionParameterResolver underTest = new CompositePositionParameterResolver(resolvers);

    @Before
    public void setUp() throws Exception {
        resolvers.clear();
    }

    @Test
    public void resolveWhenNone() throws Exception {
        int position = 0;
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);

        Optional<FunctionArgument> result = underTest.resolve(javaMethodArgument, position, context);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenSomeResolve() throws Exception {
        PositionParameterResolver parameterResolver = mock(PositionParameterResolver.class);
        resolvers.add(parameterResolver);
        int position = 0;
        FunctionArgument functionArgument = mock(FunctionArgument.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        when(parameterResolver.resolve(javaMethodArgument, position, context)).thenReturn(Optional.of(functionArgument));

        Optional<FunctionArgument> result = underTest.resolve(javaMethodArgument, position, context);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(functionArgument));
    }

    @Test
    public void resolveWhenNoneResolve() throws Exception {
        PositionParameterResolver parameterResolver = mock(PositionParameterResolver.class);
        resolvers.add(parameterResolver);
        int position = 0;
        FunctionArgument functionArgument = mock(FunctionArgument.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        when(parameterResolver.resolve(javaMethodArgument, position, context)).thenReturn(Optional.<FunctionArgument>absent());

        Optional<FunctionArgument> result = underTest.resolve(javaMethodArgument, position, context);

        assertThat(result.isPresent(), is(false));
    }
}