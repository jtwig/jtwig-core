package org.jtwig.context.impl;

import org.jtwig.configuration.Configuration;
import org.jtwig.context.model.NodeContext;
import org.jtwig.context.model.ResourceContext;
import org.jtwig.context.values.ValueContext;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

public class CoreRenderContextTest {
    private final Configuration configuration = mock(Configuration.class, RETURNS_DEEP_STUBS);
    private final Stack<ValueContext> modelStack = new Stack<>();
    private final Stack<ResourceContext> resourceContextStack = new Stack<ResourceContext>();
    private final Stack<NodeContext> nodeContextStack = new Stack<>();
    private CoreRenderContext underTest = new CoreRenderContext(configuration, modelStack, resourceContextStack, nodeContextStack);

    @Before
    public void setUp() throws Exception {
        modelStack.clear();
        resourceContextStack.clear();
        nodeContextStack.clear();
    }

    @Test
    public void configuration() throws Exception {
        Configuration result = underTest.configuration();

        assertThat(result, is(configuration));
    }

    @Test
    public void currentResource() throws Exception {
        ResourceContext context = mock(ResourceContext.class);
        resourceContextStack.push(context);

        ResourceContext result = underTest.currentResource();

        assertThat(result, is(context));
    }

    @Test
    public void model() throws Exception {
        ValueContext model = mock(ValueContext.class);
        modelStack.push(model);

        ValueContext result = underTest.valueContext();

        assertThat(result, is(model));
    }
}