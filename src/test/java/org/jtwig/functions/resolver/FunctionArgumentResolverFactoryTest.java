package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import org.jtwig.reflection.input.InputParameterResolver;
import org.jtwig.reflection.model.java.JavaMethod;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.annotations.Parameter;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FunctionArgumentResolverFactoryTest {
    private FunctionArgumentResolverFactory underTest = new FunctionArgumentResolverFactory();

    @Test
    public void createWhenInputSupportsNamesButArgumentsDoNot() throws Exception {
        Parameter parameter = mock(Parameter.class);
        JavaMethod javaMethod = mock(JavaMethod.class);
        FunctionArgument functionArgument = mock(FunctionArgument.class);
        List<FunctionArgument> functionArguments = asList(functionArgument);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        when(functionArgument.getName()).thenReturn(Optional.<String>absent());
        when(javaMethod.arguments()).thenReturn(asList(javaMethodArgument));
        when(javaMethodArgument.annotation(Parameter.class)).thenReturn(Optional.of(parameter));
        when(parameter.value()).thenReturn("name");

        InputParameterResolver<FunctionArgument> result = underTest.create(javaMethod, functionArguments);

        assertNotNull(result);
    }

    @Test
    public void createWhenInputNotSupportsNamesButArgumentsDo() throws Exception {
        Parameter parameter = mock(Parameter.class);
        JavaMethod javaMethod = mock(JavaMethod.class);
        FunctionArgument functionArgument = mock(FunctionArgument.class);
        List<FunctionArgument> functionArguments = asList(functionArgument);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        when(functionArgument.getName()).thenReturn(Optional.of("name"));
        when(javaMethod.arguments()).thenReturn(asList(javaMethodArgument));
        when(javaMethodArgument.annotation(Parameter.class)).thenReturn(Optional.of(parameter));
        when(parameter.value()).thenReturn("");

        InputParameterResolver<FunctionArgument> result = underTest.create(javaMethod, functionArguments);

        assertNotNull(result);
    }

    @Test
    public void createWhenInputSupportsNamesAndArguments() throws Exception {
        Parameter parameter = mock(Parameter.class);
        JavaMethod javaMethod = mock(JavaMethod.class);
        FunctionArgument functionArgument = mock(FunctionArgument.class);
        List<FunctionArgument> functionArguments = asList(functionArgument);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        when(functionArgument.getName()).thenReturn(Optional.of("name"));
        when(javaMethod.arguments()).thenReturn(asList(javaMethodArgument));
        when(javaMethodArgument.annotation(Parameter.class)).thenReturn(Optional.of(parameter));
        when(parameter.value()).thenReturn("name");

        InputParameterResolver<FunctionArgument> result = underTest.create(javaMethod, functionArguments);

        assertNotNull(result);
    }
}