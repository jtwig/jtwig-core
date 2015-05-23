package org.jtwig.model.tree;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.CompatibleModeValueConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IfNodeTest extends AbstractNodeTest {

    public static final Position POSITION = mock(Position.class);
    private Collection<IfNode.IfConditionNode> conditions = new ArrayList<>();
    private IfNode underTest = new IfNode(POSITION, conditions);

    @Before
    public void setUp() throws Exception {
        conditions.clear();
    }

    @Test
    public void renderWhenNoConditions() throws Exception {
        Renderable result = underTest.render(renderContext());

        assertThat(renderResult(result), is(""));
    }

    @Test
    public void renderWhenCondition() throws Exception {
        Node node = mock(Node.class);
        Expression condition = mock(Expression.class);
        IfNode.IfConditionNode ifCondition = new IfNode.IfConditionNode(POSITION, condition, node);
        conditions.add(ifCondition);

        when(condition.calculate(renderContext())).thenReturn(JtwigValueFactory.value(true, new CompatibleModeValueConfiguration()));
        render(node, "test");

        Renderable result = underTest.render(renderContext());

        assertThat(renderResult(result), is("test"));
    }
}