package org.jtwig.context.impl;

import org.jtwig.environment.Environment;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.context.model.EscapeModeContext;
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
    private final Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
    private final Stack<ValueContext> modelStack = new Stack<>();
    private final Stack<ResourceContext> resourceContextStack = new Stack<ResourceContext>();
    private final Stack<NodeContext> nodeContextStack = new Stack<>();
    private final Stack<EscapeMode> escapeContextStack = new Stack<>();
    private CoreRenderContext underTest = new CoreRenderContext(environment, modelStack, resourceContextStack, nodeContextStack, new EscapeModeContext(escapeContextStack));

    @Before
    public void setUp() throws Exception {
        modelStack.clear();
        resourceContextStack.clear();
        nodeContextStack.clear();
    }

    @Test
    public void configuration() throws Exception {
        Environment result = underTest.environment();

        assertThat(result, is(environment));
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