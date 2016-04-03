package org.jtwig.render.node.renderer;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.tree.SetNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class SetNodeRenderTest {
    private SetNodeRender underTest = new SetNodeRender();

    @Test
    public void render() throws Exception {
        Object value = new Object();
        String identifier = "identifier";
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        SetNode setNode = mock(SetNode.class);
        Expression expression = mock(Expression.class);
        VariableExpression variableExpression = mock(VariableExpression.class);

        when(setNode.getExpression()).thenReturn(expression);
        when(setNode.getVariableExpression()).thenReturn(variableExpression);
        when(variableExpression.getIdentifier()).thenReturn(identifier);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, expression)).thenReturn(value);

        Renderable result = underTest.render(request, setNode);

        verify(request.getRenderContext().getValueContext().getCurrent()).with(identifier, value);
        assertSame(EmptyRenderable.instance(), result);
    }
}