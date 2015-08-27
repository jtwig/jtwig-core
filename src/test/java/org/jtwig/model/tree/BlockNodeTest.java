package org.jtwig.model.tree;

import com.google.common.base.Optional;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.context.model.ResourceContext;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.render.impl.StringRenderable;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class BlockNodeTest extends AbstractNodeTest {
    public static final String BLOCK_NAME = "blockName";
    private Node content = mock(Node.class);
    private Position position = mock(Position.class);
    private BlockNode underTest = new BlockNode(position, new VariableExpression(position, BLOCK_NAME), content);

    @Test
    public void renderWhenNoBlockPreviouslyDefined() throws Exception {
        ResourceContext resourceContext = mock(ResourceContext.class);
        when(renderContext().currentResource()).thenReturn(resourceContext);
        when(resourceContext.block(BLOCK_NAME)).thenReturn(Optional.<Renderable>absent());
        when(resourceContext.startBlock(anyString())).thenReturn(resourceContext);
        when(content.render(renderContext())).thenReturn(new StringRenderable("one", EscapeMode.NONE));

        Renderable result = underTest.render(renderContext());

        assertThat(renderResult(result), is("one"));
        verify(renderContext().currentResource()).startBlock(eq(BLOCK_NAME));
    }

    @Test
    public void renderWhenBlockPreviouslyDefined() throws Exception {
        ResourceContext resourceContext = mock(ResourceContext.class);
        when(renderContext().currentResource()).thenReturn(resourceContext);
        when(resourceContext.block(BLOCK_NAME)).thenReturn(Optional.<Renderable>of(new StringRenderable("two", EscapeMode.NONE)));

        Renderable result = underTest.render(renderContext());

        assertThat(renderResult(result), is("two"));
        verify(renderContext().currentResource(), never()).startBlock(eq(BLOCK_NAME));
    }

}