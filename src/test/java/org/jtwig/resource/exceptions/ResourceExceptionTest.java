package org.jtwig.resource.exceptions;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ResourceExceptionTest {
    @Test
    public void apiNeededForClients() throws Exception {
        Exception exception = mock(Exception.class);

        ResourceException result = new ResourceException("message", exception);

        assertThat(result.getMessage(), is("message"));
    }
}