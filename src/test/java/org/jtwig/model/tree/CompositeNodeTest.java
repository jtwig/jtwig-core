package org.jtwig.model.tree;

import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CompositeNodeTest extends AbstractNodeTest {

    private Position position;
    private Collection<Node> nodes;
    private CompositeNode underTest;

    @Before
    public void setUp() throws Exception {
        position = mock(Position.class);
        nodes = new ArrayList<>();

        underTest = new CompositeNode(position, nodes);
    }

    @Test
    public void renderWithoutSubNodes() throws Exception {
        Renderable result = underTest.render(renderContext());

        assertThat(renderResult(result), is(""));
    }


    @Test
    public void renderWithSubNode() throws Exception {
        Node node = mock(Node.class);
        nodes.add(node);
        render(node, "one");

        Renderable result = underTest.render(renderContext());

        assertThat(renderResult(result), is("one"));
    }


    @Test
    public void renderWithMultipleSubNodes() throws Exception {
        Node node1 = mock(Node.class);
        Node node2 = mock(Node.class);
        nodes.add(node1);
        nodes.add(node2);
        render(node1, "Hello");
        render(node2, " World");

        Renderable result = underTest.render(renderContext());

        assertThat(renderResult(result), is("Hello World"));
    }
}