package org.jtwig.value.context;

import com.google.common.base.Optional;
import org.jtwig.JtwigModel;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.Undefined;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JtwigModelValueContextTest {
    private final JtwigModel jtwigModel = mock(JtwigModel.class);
    private JtwigModelValueContext underTest = new JtwigModelValueContext(jtwigModel);

    @Test
    public void resolveUndefined() throws Exception {
        String key = "key";

        when(jtwigModel.get(key)).thenReturn(Optional.<Value>absent());

        Object result = underTest.resolve(key);

        assertSame(Undefined.UNDEFINED, result);
    }

    @Test
    public void resolveDefined() throws Exception {
        String key = "key";
        Object value = new Object();

        when(jtwigModel.get(key)).thenReturn(Optional.of(new Value(value)));

        Object result = underTest.resolve(key);

        assertSame(value, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void with() throws Exception {
        underTest.with("key", null);
    }
}