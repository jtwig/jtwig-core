package org.jtwig.model.tree;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.context.model.MacroContext;
import org.jtwig.environment.Environment;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.resource.Resource;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.DefaultValueConfiguration;
import org.jtwig.value.environment.ValueEnvironment;
import org.jtwig.value.environment.ValueEnvironmentFactory;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class ImportNodeTest extends AbstractNodeTest {
    public static final ValueEnvironment VALUE_ENVIRONMENT = new ValueEnvironmentFactory().crete(new DefaultValueConfiguration());

    public static final String ALIAS = "alias";
    private final RenderContext renderContext = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private final Expression macroLocation = mock(Expression.class);
    private final Position position = mock(Position.class);
    private ImportNode underTest = new ImportNode(position, macroLocation, new VariableExpression(position, ALIAS));

    @Test
    public void render() throws Exception {
        String path = "";
        Resource resource = mock(Resource.class);
        when(macroLocation.calculate(renderContext())).thenReturn(JtwigValueFactory.value(path, VALUE_ENVIRONMENT));
        when(renderContext.environment().resources().getResourceResolver().resolve(any(Environment.class), any(Resource.class), eq(path))).thenReturn(Optional.of(resource));

        Renderable result = underTest.render(renderContext());

        verify(renderContext().valueContext()).add(eq(ALIAS), any(MacroContext.class));
        assertThat(renderResult(result), is(""));
    }
}