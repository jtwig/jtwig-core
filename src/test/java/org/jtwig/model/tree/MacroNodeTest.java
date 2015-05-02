package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.context.model.Macro;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.MacroNode;
import org.jtwig.model.tree.Node;
import org.junit.Test;

import java.util.Collection;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class MacroNodeTest {

    public static final String MACRO_NAME = "name";
    private final Position position = mock(Position.class);
    private final Node compositeNode = mock(Node.class);
    private final Collection argumentNames = mock(Collection.class);
    private final RenderContext context = mock(RenderContext.class, RETURNS_DEEP_STUBS);

    private MacroNode underTest = new MacroNode(position, new VariableExpression(position, MACRO_NAME), argumentNames, compositeNode);

    @Test
    public void render() throws Exception {
        underTest.render(context);

        verify(context.currentResource()).register(eq(MACRO_NAME), any(Macro.class));
    }
}