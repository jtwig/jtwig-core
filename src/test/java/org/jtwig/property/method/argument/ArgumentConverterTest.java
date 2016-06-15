package org.jtwig.property.method.argument;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArgumentConverterTest {
    private final IsNativeType isNativeType = mock(IsNativeType.class);
    private final AssignableTypes assignableTypes = mock(AssignableTypes.class);
    private final ArgumentConverter argumentConverter = new ArgumentConverter(isNativeType, assignableTypes);

    @Test
    public void convertNullNotNative() throws Exception {
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        Class type = Integer.class;

        when(javaMethodArgument.type()).thenReturn(type);
        when(isNativeType.isNative(type)).thenReturn(false);

        Optional<Value> result = argumentConverter.convert(javaMethodArgument, null);

        assertThat(result.get().getValue(), is(nullValue()));
    }

    @Test
    public void convertNullNative() throws Exception {
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        Class type = Integer.TYPE;

        when(javaMethodArgument.type()).thenReturn(type);
        when(isNativeType.isNative(type)).thenReturn(true);

        Optional<Value> result = argumentConverter.convert(javaMethodArgument, null);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void convertNotNullAssignable() throws Exception {
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        Class type = Integer.TYPE;
        Object next = new Object();

        when(javaMethodArgument.type()).thenReturn(type);
        when(assignableTypes.isAssignable(type, next.getClass())).thenReturn(true);

        Optional<Value> result = argumentConverter.convert(javaMethodArgument, next);

        assertThat(result.get().getValue(), is(next));
    }

    @Test
    public void convertNotNullNotAssignable() throws Exception {
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        Class type = Integer.TYPE;
        Object next = new Object();

        when(javaMethodArgument.type()).thenReturn(type);
        when(assignableTypes.isAssignable(type, next.getClass())).thenReturn(false);

        Optional<Value> result = argumentConverter.convert(javaMethodArgument, next);

        assertThat(result.isPresent(), is(false));
    }
}