package org.jtwig.model.tree;

import org.jtwig.context.model.EscapeMode;
import org.jtwig.context.model.ResourceContext;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.render.model.StringRenderable;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BlockNodeTest extends AbstractNodeTest {
    public static final String BLOCK_NAME = "blockName";
    private Node content = mock(Node.class);
    private Position position = mock(Position.class);
    private BlockNode underTest = new BlockNode(position, new VariableExpression(position, BLOCK_NAME), content);

    @Test
    public void render() throws Exception {
        ResourceContext resourceContext = mock(ResourceContext.class);
        when(renderContext().currentResource()).thenReturn(resourceContext);
        when(resourceContext.register(eq(BLOCK_NAME), any(Renderable.class))).thenReturn(new StringRenderable("one", EscapeMode.NONE));

        Renderable result = underTest.render(renderContext());

        assertThat(renderResult(result), is("one"));
        verify(renderContext().currentResource()).register(eq(BLOCK_NAME), any(Renderable.class));
    }

}