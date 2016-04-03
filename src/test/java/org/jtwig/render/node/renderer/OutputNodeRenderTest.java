package org.jtwig.render.node.renderer;

import org.hamcrest.Matcher;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.tree.OutputNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.model.EscapeMode;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.StringRenderable;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.jtwig.support.MatcherUtils.theSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class OutputNodeRenderTest {
    private OutputNodeRender underTest = new OutputNodeRender();

    @Test
    public void render() throws Exception {
        String output = "hehehe";
        EscapeMode escapeMode = EscapeMode.HTML;
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        OutputNode outputNode = mock(OutputNode.class);
        Expression expression = mock(Expression.class);
        Object outputValue = new Object();

        when(outputNode.getExpression()).thenReturn(expression);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, expression)).thenReturn(outputValue);
        when(request.getRenderContext().getEscapeModeContext().getCurrent()).thenReturn(escapeMode);
        when(request.getEnvironment().getValueEnvironment().getStringConverter().convert(outputValue)).thenReturn(output);

        Renderable result = underTest.render(request, outputNode);

        assertThat(result, is((Matcher) theSame(new StringRenderable(output, escapeMode))));
    }
}