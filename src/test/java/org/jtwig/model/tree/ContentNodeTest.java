package org.jtwig.model.tree;

import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ContentNodeTest extends AbstractNodeTest {
    private final Position position = mock(Position.class);
    private final Node content = mock(Node.class);
    private ContentNode underTest = new ContentNode(position, content);


    @Test
    public void render() throws Exception {
        render(content, "one");

        Renderable result = underTest.render(renderContext());

        assertThat(renderResult(result), is("one"));
    }
}