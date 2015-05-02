package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.java.JavaMethod;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.annotations.Parameter;
import org.jtwig.functions.resolver.position.PositionParameterResolver;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PositionInputParameterResolverTest {
    private final PositionParameterResolver positionParameterResolver = mock(PositionParameterResolver.class);
    private PositionInputParameterResolver underTest = new PositionInputParameterResolver(positionParameterResolver);

    @Test
    public void resolve() throws Exception {
        int position = 1;
        Parameter parameter = mock(Parameter.class);
        Parameter parameter2 = mock(Parameter.class);
        FunctionArgument functionArgument = mock(FunctionArgument.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        JavaMethodArgument javaMethodArgument2 = mock(JavaMethodArgument.class);
        JavaMethod javaMethod = mock(JavaMethod.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        JavaMethodArgument staticArgument = mock(JavaMethodArgument.class);
        when(javaMethodArgument.method()).thenReturn(javaMethod);
        when(javaMethodArgument2.method()).thenReturn(javaMethod);
        when(javaMethod.arguments()).thenReturn(asList(staticArgument, javaMethodArgument2, javaMethodArgument));
        when(javaMethodArgument.annotation(Parameter.class)).thenReturn(Optional.of(parameter));
        when(javaMethodArgument2.annotation(Parameter.class)).thenReturn(Optional.of(parameter2));
        when(staticArgument.annotation(Parameter.class)).thenReturn(Optional.<Parameter>absent());
        when(positionParameterResolver.resolve(javaMethodArgument, position, context)).thenReturn(Optional.of(functionArgument));

        Optional<FunctionArgument> result = underTest.resolve(parameter, javaMethodArgument, context);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(functionArgument));
    }
}