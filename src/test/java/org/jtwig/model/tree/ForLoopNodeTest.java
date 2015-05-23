package org.jtwig.model.tree;

import org.jtwig.JtwigModel;
import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.CompatibleModeValueConfiguration;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

public class ForLoopNodeTest {
    private final Position position = mock(Position.class);
    private final VariableExpression keyVariableExpression = mock(VariableExpression.class);
    private final VariableExpression variableExpression = mock(VariableExpression.class);
    private final Expression expression = mock(Expression.class);
    private final Node node = mock(Node.class);
    private final RenderContext renderContext = mock(RenderContext.class, RETURNS_DEEP_STUBS);

    @Test
    public void renderWhenMapIteration() throws Exception {
        JtwigModel jtwigModel = mock(JtwigModel.class);
        LinkedHashMap<Object, Object> map = new LinkedHashMap<>();
        map.put("hello", "one");
        when(expression.calculate(renderContext)).thenReturn(JtwigValueFactory.value(map, new CompatibleModeValueConfiguration()));
        when(renderContext.valueContext()).thenReturn(jtwigModel);
        when(keyVariableExpression.getIdentifier()).thenReturn("key");
        when(variableExpression.getIdentifier()).thenReturn("value");

        new ForLoopNode(position, keyVariableExpression, variableExpression, expression, node)
                .render(renderContext);

        verify(jtwigModel).add("key", "hello");
        verify(jtwigModel).add("value", "one");
        verify(jtwigModel).add(eq("loop"), any());
    }

    @Test
    public void renderWhenMapArrayIteration() throws Exception {
        JtwigModel jtwigModel = mock(JtwigModel.class);
        List<String> list = asList("hello", "test");
        when(expression.calculate(renderContext)).thenReturn(JtwigValueFactory.value(list, new CompatibleModeValueConfiguration()));
        when(renderContext.valueContext()).thenReturn(jtwigModel);
        when(keyVariableExpression.getIdentifier()).thenReturn("key");
        when(variableExpression.getIdentifier()).thenReturn("value");

        new ForLoopNode(position, keyVariableExpression, variableExpression, expression, node)
                .render(renderContext);

        verify(jtwigModel).add("key", 0);
        verify(jtwigModel).add("key", 1);
        verify(jtwigModel).add("value", "hello");
        verify(jtwigModel).add("value", "test");
        verify(jtwigModel, times(2)).add(eq("loop"), any());
    }

    @Test
    public void renderWhenArrayIteration() throws Exception {
        JtwigModel jtwigModel = mock(JtwigModel.class);
        List<String> list = asList("hello", "test");
        when(expression.calculate(renderContext)).thenReturn(JtwigValueFactory.value(list, new CompatibleModeValueConfiguration()));
        when(renderContext.valueContext()).thenReturn(jtwigModel);
        when(variableExpression.getIdentifier()).thenReturn("value");

        new ForLoopNode(position, variableExpression, null, expression, node)
                .render(renderContext);

        verify(jtwigModel).add("value", "hello");
        verify(jtwigModel).add("value", "test");
        verify(jtwigModel, times(2)).add(eq("loop"), any());
    }
}