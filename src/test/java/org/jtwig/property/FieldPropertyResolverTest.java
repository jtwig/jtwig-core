package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.environment.Environment;
import org.jtwig.model.position.Position;
import org.jtwig.reflection.model.Value;
import org.jtwig.render.context.model.RenderContext;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FieldPropertyResolverTest {
    private FieldPropertyResolver underTest = new FieldPropertyResolver(false);

    @Test
    public void resolveWithNullEntity() throws Exception {
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);
        given(request.getArguments()).willReturn(Collections.emptyList());
        given(request.getEntity()).willReturn(null);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWithNullValue() throws Exception {
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);
        given(request.getArguments()).willReturn(Collections.emptyList());
        given(request.getEntity()).willReturn(new Value(null));

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolvePrivateField() throws Exception {
        Optional<Value> result = underTest.resolve(new PropertyResolveRequest(
                mock(RenderContext.class),
                mock(Environment.class),
                mock(Position.class),
                new Value(new TestBean()),
                "test",
                Collections.emptyList()
        ));

        assertEquals(false, result.isPresent());
    }

    @Test
    public void resolveWithArguments() throws Exception {

        Optional<Value> result = underTest.resolve(new PropertyResolveRequest(
                mock(RenderContext.class),
                mock(Environment.class),
                mock(Position.class),
                new Value(new TestBean()),
                "test",
                Collections.singletonList(new Object())
        ));

        assertEquals(false, result.isPresent());

    }

    public static class TestBean {
        String test = "Hello";
    }
}