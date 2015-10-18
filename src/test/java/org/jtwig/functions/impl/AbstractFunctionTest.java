package org.jtwig.functions.impl;

import com.google.common.base.Optional;
import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.model.position.Position;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class AbstractFunctionTest {
    protected JtwigFunctionRequest arguments(JtwigValue... values) {
        return new JtwigFunctionRequest(mock(Position.class), "funcName", asList(values));
    }
    protected JtwigFunctionRequest withoutArguments() {
        return new JtwigFunctionRequest(mock(Position.class), "funcName", Collections.<JtwigValue>emptyList());
    }

    protected JtwigValue newValue() {
        JtwigValue jtwigValue = mock(JtwigValue.class);
        when(jtwigValue.as(any(Class.class))).thenReturn(Optional.of(new Value(null)));
        return jtwigValue;
    }
    protected JtwigValue newValue(Object value) {
        JtwigValue jtwigValue = mock(JtwigValue.class);
        when(jtwigValue.as(any(Class.class))).thenReturn(Optional.of(new Value(value)));
        return jtwigValue;
    }
}
