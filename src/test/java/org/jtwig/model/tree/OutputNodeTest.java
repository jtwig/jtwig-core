package org.jtwig.model.tree;

import com.google.common.base.Optional;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.context.model.NodeContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.util.JtwigValue;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OutputNodeTest extends AbstractNodeTest {
    private final Position position = mock(Position.class);
    private final Expression expression = mock(Expression.class);
    private OutputNode underTest = new OutputNode(position, expression);

    @Test
    public void render() throws Exception {
        NodeContext nodeContext = mock(NodeContext.class);
        when(renderContext().currentNode()).thenReturn(nodeContext);
        when(nodeContext.mode()).thenReturn(Optional.<EscapeMode>absent());
        when(expression.calculate(renderContext())).thenReturn(new JtwigValue("test"));

        Renderable result = underTest.render(renderContext());

        assertThat(renderResult(result), is("test"));
    }
}