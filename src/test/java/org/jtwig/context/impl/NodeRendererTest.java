package org.jtwig.context.impl;

import org.jtwig.context.RenderContext;
import org.jtwig.context.model.NodeContext;
import org.jtwig.context.values.ScopeType;
import org.jtwig.context.values.ValueContext;
import org.jtwig.model.tree.Node;
import org.jtwig.render.Renderable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Stack;

import static junit.framework.Assert.assertSame;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NodeRendererTest {
    private Stack<ValueContext> valueContextStack = new Stack<>();
    private Stack<NodeContext> nodeContextStack = new Stack<>();
    private ValueContext valueContext = mock(ValueContext.class);
    private final RenderContext renderContext = mock(RenderContext.class);
    private NodeRenderer underTest = new NodeRenderer(renderContext, nodeContextStack, valueContextStack);

    @Before
    public void setUp() throws Exception {
        valueContextStack.clear();
        nodeContextStack.clear();

        valueContextStack.push(valueContext);
    }

    @Test
    public void render() throws Exception {
        Node node = mock(Node.class);
        when(node.scopeType()).thenReturn(ScopeType.SHARE);
        final Renderable renderable = mock(Renderable.class);
        when(node.render(renderContext)).thenAnswer(new Answer<Renderable>() {
            @Override
            public Renderable answer(InvocationOnMock invocation) throws Throwable {
                assertThat(nodeContextStack.size(), is(1));
                assertThat(valueContextStack.size(), is(2));
                assertSame(valueContextStack.peek(), valueContext);
                return renderable;
            }
        });

        Renderable render = underTest.render(node);

        assertThat(render, is(renderable));
        assertThat(nodeContextStack.size(), is(0));
        assertThat(valueContextStack.size(), is(1));
    }
}