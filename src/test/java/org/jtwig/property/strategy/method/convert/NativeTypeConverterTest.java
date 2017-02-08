package org.jtwig.property.strategy.method.convert;

import com.google.common.base.Optional;
import org.jtwig.property.strategy.method.argument.AssignableTypes;
import org.jtwig.property.strategy.method.argument.IsNativeType;
import org.jtwig.reflection.model.Value;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class NativeTypeConverterTest {
    private final IsNativeType isNativeType = mock(IsNativeType.class);
    private final AssignableTypes assignableTypes = mock(AssignableTypes.class);
    private NativeTypeConverter underTest = new NativeTypeConverter(isNativeType, assignableTypes);

    @Test
    public void convertNullNonNative() throws Exception {
        Class<Integer> type = Integer.class;

        given(isNativeType.isNative(type)).willReturn(false);

        Optional<Value> result = underTest.convert(null, type);

        assertThat(result.get().getValue(), is(nullValue()));
    }

    @Test
    public void convertNullNative() throws Exception {
        Class<Integer> type = Integer.TYPE;

        given(isNativeType.isNative(type)).willReturn(true);

        Optional<Value> result = underTest.convert(null, type);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void convertNonNullNotAssignable() throws Exception {
        String value = "test";
        Class<Integer> type = Integer.TYPE;

        given(assignableTypes.isAssignable(type, value.getClass())).willReturn(false);

        Optional<Value> result = underTest.convert(value, type);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void convertNonNullAssignable() throws Exception {
        Object value = 1;
        Class<Integer> type = Integer.TYPE;

        given(assignableTypes.isAssignable(type, value.getClass())).willReturn(true);

        Optional<Value> result = underTest.convert(value, type);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getValue(), is(value));
    }
}