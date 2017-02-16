package org.jtwig.property.resolver;

import com.google.common.base.Optional;
import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ValueContextPropertyResolverTest {
    private ValueContextPropertyResolver underTest = new ValueContextPropertyResolver();

    @Test
    public void resolveNull() throws Exception {
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);

        given(request.getContext()).willReturn(null);

        Optional<Value> result = underTest.resolve(request);

        assertEquals(Optional.<Value>absent(), result);
    }

    @Test
    public void resolveNonValueContext() throws Exception {
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);

        given(request.getContext()).willReturn(new HashMap<>());

        Optional<Value> result = underTest.resolve(request);

        assertEquals(Optional.<Value>absent(), result);
    }

}