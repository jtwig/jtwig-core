package org.jtwig.render.node.renderer;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.tree.IfNode;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.Renderable;
import org.jtwig.value.convert.Converter;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class IfNodeRenderTest {
    private IfNodeRender underTest = new IfNodeRender();

    @Test
    public void render() throws Exception {
        Object conditionValue1 = new Object();
        Object conditionValue2 = new Object();
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        IfNode ifNode = mock(IfNode.class);
        Node node = mock(Node.class);
        Renderable renderable = mock(Renderable.class);

        IfNode.IfConditionNode ifConditionNode1 = mock(IfNode.IfConditionNode.class);
        IfNode.IfConditionNode ifConditionNode2 = mock(IfNode.IfConditionNode.class);
        Expression condition1 = mock(Expression.class);
        Expression condition2 = mock(Expression.class);

        when(ifNode.getConditionNodes()).thenReturn(asList(ifConditionNode1, ifConditionNode2));
        when(ifConditionNode1.getCondition()).thenReturn(condition1);
        when(ifConditionNode2.getCondition()).thenReturn(condition2);
        when(ifConditionNode2.getContent()).thenReturn(node);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, condition1)).thenReturn(conditionValue1);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, condition2)).thenReturn(conditionValue2);
        when(request.getEnvironment().getValueEnvironment().getBooleanConverter().convert(conditionValue1)).thenReturn(Converter.Result.defined(false));
        when(request.getEnvironment().getValueEnvironment().getBooleanConverter().convert(conditionValue2)).thenReturn(Converter.Result.defined(true));
        when(request.getEnvironment().getRenderEnvironment().getRenderNodeService().render(request, node)).thenReturn(renderable);

        Renderable result = underTest.render(request, ifNode);

        assertSame(renderable, result);
    }
}