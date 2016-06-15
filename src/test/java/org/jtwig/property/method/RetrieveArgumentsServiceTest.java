package org.jtwig.property.method;

import com.google.common.base.Optional;
import org.jtwig.property.method.argument.ArgumentConverter;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RetrieveArgumentsServiceTest {
    private final ArgumentConverter argumentConverter = mock(ArgumentConverter.class);
    private RetrieveArgumentsService underTest = new RetrieveArgumentsService(argumentConverter);

    @Test
    public void retrieveArgumentsNotSameType() throws Exception {
        Object argument = null;
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);

        List<Object> arguments = asList(argument);
        List<JavaMethodArgument> methodArguments = asList(javaMethodArgument);

        when(argumentConverter.convert(javaMethodArgument, argument)).thenReturn(Optional.<Value>absent());

        Optional<List<Object>> result = underTest.retrieveArguments(arguments, methodArguments);

        assertEquals(false, result.isPresent());
    }

    @Test
    public void retrieveArgumentsSameType() throws Exception {
        Object argument = "asdasd";
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        Object value = new Object();

        List<Object> arguments = asList(argument);
        List<JavaMethodArgument> methodArguments = asList(javaMethodArgument);

        when(argumentConverter.convert(javaMethodArgument, argument)).thenReturn(Optional.of(new Value(value)));

        Optional<List<Object>> result = underTest.retrieveArguments(arguments, methodArguments);

        assertEquals(true, result.isPresent());
        assertEquals(asList(value), result.get());
    }
}