package org.jtwig.property.strategy.method.argument.group;

import com.google.common.base.Optional;
import org.jtwig.property.strategy.method.convert.Converter;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class VarArgumentGroupTest {
    private final ArrayList<Object> arguments = new ArrayList<>();
    private final JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
    private VarArgumentGroup underTest = new VarArgumentGroup(javaMethodArgument, arguments);

    @Before
    public void setUp() throws Exception {
        arguments.clear();
    }

    @Test
    public void test() throws Exception {
        Object object = new Object();
        Converter converter = mock(Converter.class);

        arguments.add(object);
        given(javaMethodArgument.type()).willReturn(String[].class);
        given(converter.convert(object, String.class)).willReturn(Optional.<Value>absent());

        Optional<Value> result = underTest.toArgument(converter);

        assertThat(result.isPresent(), is(false));
    }
}