package org.jtwig.render.node.renderer;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.tree.DoNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class DoNodeRenderTest {
    private DoNodeRender underTest = new DoNodeRender();

    @Test
    public void render() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Expression expression = mock(Expression.class);
        DoNode doNode = mock(DoNode.class);

        when(doNode.getExpression()).thenReturn(expression);

        Renderable result = underTest.render(request, doNode);

        verify(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService()).calculate(request, expression);
        assertSame(EmptyRenderable.instance(), result);
    }
}