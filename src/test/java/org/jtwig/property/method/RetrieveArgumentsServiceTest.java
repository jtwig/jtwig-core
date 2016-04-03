package org.jtwig.property.method;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RetrieveArgumentsServiceTest {
    private final IsNullableType isNullableType = mock(IsNullableType.class);
    private RetrieveArgumentsService underTest = new RetrieveArgumentsService(isNullableType);

    @Test
    public void retrieveArgumentsTypeMismatch() throws Exception {
        Object argument = "hello";
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);

        List<Object> arguments = asList(argument);
        List<JavaMethodArgument> methodArguments = asList(javaMethodArgument);

        when(javaMethodArgument.type()).thenReturn(Boolean.class);

        Optional<List<Object>> result = underTest.retrieveArguments(arguments, methodArguments);

        assertEquals(false, result.isPresent());
    }

    @Test
    public void retrieveArgumentsNullNotNullable() throws Exception {
        Object argument = null;
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);

        List<Object> arguments = asList(argument);
        List<JavaMethodArgument> methodArguments = asList(javaMethodArgument);

        when(javaMethodArgument.type()).thenReturn(Boolean.class);
        when(isNullableType.isNullable(Boolean.class)).thenReturn(false);

        Optional<List<Object>> result = underTest.retrieveArguments(arguments, methodArguments);

        assertEquals(false, result.isPresent());
    }

    @Test
    public void retrieveArgumentsNullNullable() throws Exception {
        Object argument = null;
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);

        List<Object> arguments = asList(argument);
        List<JavaMethodArgument> methodArguments = asList(javaMethodArgument);

        when(javaMethodArgument.type()).thenReturn(Boolean.class);
        when(isNullableType.isNullable(Boolean.class)).thenReturn(true);

        Optional<List<Object>> result = underTest.retrieveArguments(arguments, methodArguments);

        assertEquals(true, result.isPresent());
        assertEquals(asList(argument), result.get());
    }

    @Test
    public void retrieveArgumentsSameType() throws Exception {
        Object argument = "asdasd";
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);

        List<Object> arguments = asList(argument);
        List<JavaMethodArgument> methodArguments = asList(javaMethodArgument);

        when(javaMethodArgument.type()).thenReturn(String.class);

        Optional<List<Object>> result = underTest.retrieveArguments(arguments, methodArguments);

        assertEquals(true, result.isPresent());
        assertEquals(asList(argument), result.get());
    }
}