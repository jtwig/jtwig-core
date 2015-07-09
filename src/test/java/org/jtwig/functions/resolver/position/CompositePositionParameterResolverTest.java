package org.jtwig.functions.resolver.position;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
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

        Optional<Value> result = underTest.resolve(javaMethodArgument, position, context, String.class);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenSomeResolve() throws Exception {
        PositionParameterResolver parameterResolver = mock(PositionParameterResolver.class);
        resolvers.add(parameterResolver);
        int position = 0;
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        Value reference = new Value(null);
        when(parameterResolver.resolve(javaMethodArgument, position, context, String.class)).thenReturn(Optional.of(reference));

        Optional<Value> result = underTest.resolve(javaMethodArgument, position, context, String.class);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(reference));
    }

    @Test
    public void resolveWhenNoneResolve() throws Exception {
        PositionParameterResolver parameterResolver = mock(PositionParameterResolver.class);
        resolvers.add(parameterResolver);
        int position = 0;
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        when(parameterResolver.resolve(javaMethodArgument, position, context, String.class)).thenReturn(Optional.<Value>absent());

        Optional<Value> result = underTest.resolve(javaMethodArgument, position, context, String.class);

        assertThat(result.isPresent(), is(false));
    }
}