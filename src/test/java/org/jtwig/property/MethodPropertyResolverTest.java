package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.property.method.MethodPropertyExtractor;
import org.jtwig.reflection.model.Value;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MethodPropertyResolverTest {
    private final MethodPropertyExtractor methodPropertyExtractor = mock(MethodPropertyExtractor.class);
    private MethodPropertyResolver underTest = new MethodPropertyResolver(methodPropertyExtractor);

    @Test
    public void resolveWithNullEntity() throws Exception {
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);

        given(request.getEntity()).willReturn(null);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWithNullValue() throws Exception {
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);
        Value value = new Value(null);

        given(request.getEntity()).willReturn(value);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

}