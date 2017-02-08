package org.jtwig.property.resolver;

import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.value.Undefined;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class EmptyPropertyResolverTest {
    private EmptyPropertyResolver underTest = EmptyPropertyResolver.instance();

    @Test
    public void resolve() throws Exception {
        Object result = underTest.resolve(mock(PropertyResolveRequest.class));

        assertEquals(Undefined.UNDEFINED, result);
    }
}