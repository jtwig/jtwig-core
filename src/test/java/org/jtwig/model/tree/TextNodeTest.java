package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.TextNode;
import org.jtwig.render.Renderable;
import org.junit.Before;
import org.junit.Test;

import java.io.OutputStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TextNodeTest extends AbstractNodeTest {
    public static final String CONTENT = " content ";
    private final Position position = mock(Position.class);
    private final RenderContext renderContext = mock(RenderContext.class);
    private final TextNode.Configuration configuration = mock(TextNode.Configuration.class);
    private TextNode underTest = new TextNode(position, CONTENT, configuration);

    @Test
    public void renderWithoutTrim() throws Exception {
        when(configuration.isTrimLeft()).thenReturn(false);
        when(configuration.isTrimRight()).thenReturn(false);

        Renderable result = underTest.render(renderContext);

        assertThat(renderResult(result), is(CONTENT));
    }

    @Test
    public void renderWithTrimLeft() throws Exception {
        when(configuration.isTrimLeft()).thenReturn(true);
        when(configuration.isTrimRight()).thenReturn(false);

        Renderable result = underTest.render(renderContext);

        assertThat(renderResult(result), is("content "));
    }

    @Test
    public void renderWithTrimRight() throws Exception {
        when(configuration.isTrimLeft()).thenReturn(false);
        when(configuration.isTrimRight()).thenReturn(true);

        Renderable result = underTest.render(renderContext);

        assertThat(renderResult(result), is(" content"));
    }

    @Test
    public void renderWithTrimLeftAndRight() throws Exception {
        when(configuration.isTrimLeft()).thenReturn(true);
        when(configuration.isTrimRight()).thenReturn(true);

        Renderable result = underTest.render(renderContext);

        assertThat(renderResult(result), is("content"));
    }
}