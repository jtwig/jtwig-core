package org.jtwig.render.node.renderer;

import com.google.common.base.Optional;
import org.jtwig.environment.Environment;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.tree.ExtendsNode;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.RenderResourceRequest;
import org.jtwig.renderable.Renderable;
import org.jtwig.resource.Resource;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.value.WrappedCollection;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsString;
import static org.jtwig.support.MatcherUtils.theSame;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class ExtendsNodeRenderTest {
    private final ExtendsNodeRender underTest = new ExtendsNodeRender();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void renderIfNotFound() throws Exception {
        String path = "path";
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        ExtendsNode node = mock(ExtendsNode.class);
        Expression expression = mock(Expression.class);
        Object pathValue = mock(Object.class);
        Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
        Resource parentResource = mock(Resource.class);

        when(request.getEnvironment()).thenReturn(environment);
        when(request.getRenderContext().getResourceContext().getCurrent()).thenReturn(parentResource);
        when(node.getExtendsExpression()).thenReturn(expression);
        when(environment.getRenderEnvironment().getCalculateExpressionService().calculate(request, expression)).thenReturn(pathValue);
        when(environment.getValueEnvironment().getStringConverter().convert(pathValue)).thenReturn(path);
        when(environment.getResourceEnvironment().getResourceResolver().resolve(environment, parentResource, path)).thenReturn(Optional.<Resource>absent());

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage(containsString("Resource 'path' not found"));

        underTest.render(request, node);
    }

    @Test
    public void renderIfFound() throws Exception {
        String path = "path";
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        ExtendsNode node = mock(ExtendsNode.class);
        Expression expression = mock(Expression.class);
        Object pathValue = new Object();
        Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
        Resource parentResource = mock(Resource.class);
        Resource newResource = mock(Resource.class);
        Renderable renderable = mock(Renderable.class);
        Node node1 = mock(Node.class);
        Node node2 = mock(Node.class);

        when(request.getEnvironment()).thenReturn(environment);
        when(request.getRenderContext().getResourceContext().getCurrent()).thenReturn(parentResource);
        when(node.getExtendsExpression()).thenReturn(expression);
        when(node.getNodes()).thenReturn(asList(node1, node2));
        when(environment.getRenderEnvironment().getCalculateExpressionService().calculate(request, expression)).thenReturn(pathValue);
        when(environment.getValueEnvironment().getStringConverter().convert(pathValue)).thenReturn(path);
        when(environment.getResourceEnvironment().getResourceResolver().resolve(environment, parentResource, path)).thenReturn(Optional.of(newResource));
        when(environment.getRenderEnvironment().getRenderResourceService().render(eq(request), argThat(theSame(new RenderResourceRequest(newResource, false, false, WrappedCollection.empty()))))).thenReturn(renderable);


        Renderable result = underTest.render(request, node);

        assertSame(renderable, result);
        verify(environment.getRenderEnvironment().getRenderNodeService()).render(request, node1);
        verify(environment.getRenderEnvironment().getRenderNodeService()).render(request, node2);
    }
}