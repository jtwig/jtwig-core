package org.jtwig.model.tree;

import com.google.common.base.Optional;

import org.jtwig.context.model.ResourceRenderResult;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.resource.Resource;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExtendsNodeTest extends AbstractNodeTest {
    private Position position = mock(Position.class);
    private Expression expression = mock(Expression.class);
    private Collection<Node> nodes = new ArrayList<>();
    private ExtendsNode underTest = new ExtendsNode(position, expression, nodes);

    @Before
    public void setUp() throws Exception {
        nodes.clear();
    }

    @Test
    public void render() throws Exception {
        Node node = mock(Node.class);
        Resource resource = mock(Resource.class);
        Renderable renderable = mock(Renderable.class);
        ResourceRenderResult renderResult = mock(ResourceRenderResult.class);

        nodes.add(node);
        when(expression.calculate(renderContext())).thenReturn(JtwigValueFactory.create("path"));
        when(renderContext().configuration().resourceResolver().resolve(any(Resource.class), eq("path"))).thenReturn(Optional.of(resource));
        when(renderContext().resourceRenderer().render(resource)).thenReturn(renderResult);
        when(renderResult.renderable()).thenReturn(renderable);

        Renderable result = underTest.render(renderContext());

        verify(renderContext().nodeRenderer()).render(node);
        assertThat(result, is(renderable));
    }
}