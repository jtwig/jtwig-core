package org.jtwig.render.node.renderer;

import com.google.common.base.Optional;
import org.jtwig.environment.Environment;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.tree.ImportNode;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.model.MacroAliasesContext;
import org.jtwig.render.context.model.MacroDefinitionContext;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.jtwig.resource.Resource;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.containsString;
import static org.jtwig.support.MatcherUtils.theSame;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class ImportNodeRenderTest {
    private ImportNodeRender underTest = new ImportNodeRender();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void renderResourceNotFound() throws Exception {
        String identifier = "identifier";
        String path = "path";
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        ImportNode importNode = mock(ImportNode.class, RETURNS_DEEP_STUBS);
        Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
        Expression expression = mock(Expression.class);
        Resource resource = mock(Resource.class);
        String pathValue = "path";

        when(importNode.getAliasIdentifier().getIdentifier()).thenReturn(identifier);
        when(importNode.getImportExpression()).thenReturn(expression);
        when(request.getEnvironment()).thenReturn(environment);
        when(environment.getRenderEnvironment().getCalculateExpressionService().calculate(request, expression))
                .thenReturn(pathValue);
        when(environment.getValueEnvironment().getStringConverter().convert(pathValue)).thenReturn(path);
        when(request.getRenderContext().getResourceContext().getCurrent()).thenReturn(resource);
        when(environment.getResourceEnvironment().getResourceResolver().resolve(environment, resource, path)).thenReturn(Optional.<Resource>absent());

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage(containsString("Resource 'path' not found"));

        underTest.render(request, importNode);
    }

    @Test
    public void renderMacroAliasesContextNotStarted() throws Exception {
        String identifier = "identifier";
        String path = "path";
        String pathValue = "path";
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        ImportNode importNode = mock(ImportNode.class, RETURNS_DEEP_STUBS);
        Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
        MacroAliasesContext macroAliasesContext = mock(MacroAliasesContext.class);
        Expression expression = mock(Expression.class);
        Resource resource = mock(Resource.class);
        Resource newResource = mock(Resource.class);
        Node node = mock(Node.class);

        when(request.getRenderContext().getMacroAliasesContext().hasCurrent()).thenReturn(false);
        when(request.getRenderContext().getMacroAliasesContext().getCurrent()).thenReturn(macroAliasesContext);
        when(importNode.getAliasIdentifier().getIdentifier()).thenReturn(identifier);
        when(importNode.getImportExpression()).thenReturn(expression);
        when(request.getEnvironment()).thenReturn(environment);
        when(environment.getRenderEnvironment().getCalculateExpressionService().calculate(request, expression))
                .thenReturn(pathValue);
        when(environment.getValueEnvironment().getStringConverter().convert(pathValue)).thenReturn(path);
        when(request.getRenderContext().getResourceContext().getCurrent()).thenReturn(resource);
        when(environment.getResourceEnvironment().getResourceResolver().resolve(environment, resource, path)).thenReturn(Optional.of(newResource));
        when(environment.getParser().parse(newResource)).thenReturn(node);

        Renderable result = underTest.render(request, importNode);

        assertSame(EmptyRenderable.instance(), result);
        verify(request.getRenderContext().getMacroAliasesContext()).start(argThat(theSame(MacroAliasesContext.newContext())));
        verify(environment.getRenderEnvironment().getRenderNodeService()).render(request, node);
        verify(request.getRenderContext().getMacroAliasesContext()).end();
        verifyZeroInteractions(macroAliasesContext);
    }

    @Test
    public void renderMacroAliasesContextStarted() throws Exception {
        String identifier = "identifier";
        String path = "path";
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        ImportNode importNode = mock(ImportNode.class, RETURNS_DEEP_STUBS);
        Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
        MacroAliasesContext macroAliasesContext = mock(MacroAliasesContext.class);
        Expression expression = mock(Expression.class);
        Resource resource = mock(Resource.class);
        Resource newResource = mock(Resource.class);
        Node node = mock(Node.class);
        String pathValue = "path";

        when(request.getRenderContext().getMacroAliasesContext().hasCurrent()).thenReturn(true);
        when(request.getRenderContext().getMacroAliasesContext().getCurrent()).thenReturn(macroAliasesContext);
        when(importNode.getAliasIdentifier().getIdentifier()).thenReturn(identifier);
        when(importNode.getImportExpression()).thenReturn(expression);
        when(request.getEnvironment()).thenReturn(environment);
        when(environment.getRenderEnvironment().getCalculateExpressionService().calculate(request, expression))
                .thenReturn(pathValue);
        when(environment.getValueEnvironment().getStringConverter().convert(pathValue)).thenReturn(path);
        when(request.getRenderContext().getResourceContext().getCurrent()).thenReturn(resource);
        when(environment.getResourceEnvironment().getResourceResolver().resolve(environment, resource, path)).thenReturn(Optional.of(newResource));
        when(environment.getParser().parse(newResource)).thenReturn(node);

        Renderable result = underTest.render(request, importNode);

        assertSame(EmptyRenderable.instance(), result);
        verify(request.getRenderContext().getMacroAliasesContext()).start(argThat(theSame(MacroAliasesContext.newContext())));
        verify(request.getRenderContext().getMacroDefinitionContext()).start(argThat(theSame(MacroDefinitionContext.newContext())));
        verify(environment.getRenderEnvironment().getRenderNodeService()).render(request, node);
        verify(request.getRenderContext().getMacroAliasesContext()).end();
        verify(macroAliasesContext).with(eq(identifier), argThat(theSame(MacroDefinitionContext.newContext())));
    }
}