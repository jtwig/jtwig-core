package org.jtwig.property.selection;

import org.jtwig.property.resolver.PropertyResolver;
import org.jtwig.property.resolver.request.PropertyResolveRequestFactory;
import org.jtwig.value.Undefined;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class SelectionPropertyResolveServiceTest {
    private final PropertyResolveRequestFactory requestFactory = mock(PropertyResolveRequestFactory.class);
    private SelectionPropertyResolveService underTest = new SelectionPropertyResolveService(requestFactory);

    @Test
    public void nullLeftValue() throws Exception {
        PropertyResolver propertyResolver = mock(PropertyResolver.class);
        SelectionRequest selectionRequest = mock(SelectionRequest.class);

        SelectionResult result = underTest.resolve(propertyResolver, selectionRequest, null);

        assertThat(result.getResolvedValue().isPresent(), is(false));
        assertThat(result.getPropertyResolver().isPresent(), is(false));
    }

    @Test
    public void undefinedLeftValue() throws Exception {
        PropertyResolver propertyResolver = mock(PropertyResolver.class);
        SelectionRequest selectionRequest = mock(SelectionRequest.class);

        SelectionResult result = underTest.resolve(propertyResolver, selectionRequest, Undefined.UNDEFINED);

        assertThat(result.getResolvedValue().isPresent(), is(false));
        assertThat(result.getPropertyResolver().isPresent(), is(false));
    }
}