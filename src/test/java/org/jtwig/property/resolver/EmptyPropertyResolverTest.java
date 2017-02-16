package org.jtwig.property.resolver;

import com.google.common.base.Optional;
import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class EmptyPropertyResolverTest {
    private EmptyPropertyResolver underTest = EmptyPropertyResolver.instance();

    @Test
    public void resolve() throws Exception {
        Optional<Value> result = underTest.resolve(mock(PropertyResolveRequest.class));

        assertEquals(Optional.<Value>absent(), result);
    }
}