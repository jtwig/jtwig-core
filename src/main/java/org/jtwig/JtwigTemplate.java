package org.jtwig;

import com.google.common.base.Optional;
import org.jtwig.environment.*;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.RenderContextHolder;
import org.jtwig.render.context.StackedContext;
import org.jtwig.render.context.model.BlockContext;
import org.jtwig.render.context.model.MacroAliasesContext;
import org.jtwig.render.context.model.MacroDefinitionContext;
import org.jtwig.render.context.model.RenderContext;
import org.jtwig.render.escape.EscapeEngine;
import org.jtwig.renderable.RenderResult;
import org.jtwig.renderable.StreamRenderResult;
import org.jtwig.renderable.StringBuilderRenderResult;
import org.jtwig.resource.FileResource;
import org.jtwig.resource.Resource;
import org.jtwig.resource.StringResource;
import org.jtwig.resource.resolver.ClasspathResourceResolver;
import org.jtwig.value.context.IsolateParentValueContext;
import org.jtwig.value.context.JtwigModelValueContext;
import org.jtwig.value.context.MapValueContext;
import org.jtwig.value.context.ValueContext;

import java.io.File;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class JtwigTemplate {
    public static final EnvironmentFactory ENVIRONMENT_FACTORY = new EnvironmentFactory();

    public static JtwigTemplate inlineTemplate (String template) {
        return inlineTemplate(template, new DefaultEnvironmentConfiguration());
    }
    public static JtwigTemplate inlineTemplate (String template, EnvironmentConfiguration configuration) {
        return new JtwigTemplate(ENVIRONMENT_FACTORY.create(configuration), new StringResource(template));
    }
    public static JtwigTemplate inlineTemplate (Charset charset, String template, EnvironmentConfiguration configuration) {
        return new JtwigTemplate(ENVIRONMENT_FACTORY.create(configuration), new StringResource(charset, template));
    }

    public static JtwigTemplate classpathTemplate(String location) {
        return classpathTemplate(location, new DefaultEnvironmentConfiguration());
    }

    public static JtwigTemplate classpathTemplate(String location, EnvironmentConfiguration environmentConfiguration) {
        if (!location.startsWith(ClasspathResourceResolver.PREFIX)) {
            location = ClasspathResourceResolver.PREFIX + location;
        }
        Environment environment = ENVIRONMENT_FACTORY.create(environmentConfiguration);
        Optional<Resource> resource = environment.getResourceEnvironment().getResourceResolver().resolve(environment, null, location);
        if (resource.isPresent()) {
            return new JtwigTemplate(environment, resource.get());
        } else {
            throw new IllegalArgumentException(String.format("Classpath resource '%s' not found", location));
        }
    }

    public static JtwigTemplate fileTemplate (File file, EnvironmentConfiguration environmentConfiguration) {
        Environment environment = ENVIRONMENT_FACTORY.create(environmentConfiguration);
        return new JtwigTemplate(environment, new FileResource(environment.getResourceEnvironment().getDefaultInputCharset(), file));
    }


    public static JtwigTemplate fileTemplate (File file) {
        return fileTemplate(file, new DefaultEnvironmentConfiguration());
    }

    public static JtwigTemplate fileTemplate (String path, EnvironmentConfiguration environmentConfiguration) {
        Environment environment = ENVIRONMENT_FACTORY.create(environmentConfiguration);
        return new JtwigTemplate(environment, new FileResource(environment.getResourceEnvironment().getDefaultInputCharset(), new File(path)));
    }

    public static JtwigTemplate fileTemplate (String path) {
        return fileTemplate(path, new DefaultEnvironmentConfiguration());
    }

    private final Resource resource;
    private final Environment environment;

    public JtwigTemplate(Environment environment, Resource resource) {
        this.resource = resource;
        this.environment = environment;
    }

    public String render(JtwigModel model) {
        RenderResult result = new StringBuilderRenderResult();
        render(model, result);
        return result.content();
    }

    public void render(JtwigModel model, OutputStream outputStream) {
        RenderResult result = new StreamRenderResult(outputStream);
        render(model, result);
    }

    private void render(JtwigModel model, RenderResult renderResult) {
        StackedContext<ValueContext> valueContextContext = StackedContext.<ValueContext>context(new IsolateParentValueContext(new JtwigModelValueContext(model), MapValueContext.newContext()));
        StackedContext<EscapeEngine> escapeEngineContext = StackedContext.context(environment.getRenderEnvironment().getInitialEscapeEngine());
        StackedContext<Resource> resourceContext = StackedContext.context(resource);
        StackedContext<BlockContext> blockContext = StackedContext.context(BlockContext.newContext());
        StackedContext<MacroDefinitionContext> macroDefinitionContext = StackedContext.emptyContext();
        StackedContext<MacroAliasesContext> macroContext = StackedContext.emptyContext();

        RenderContext renderContext = new RenderContext(valueContextContext, escapeEngineContext, resourceContext, blockContext, macroDefinitionContext, macroContext);

        EnvironmentHolder.set(environment);
        RenderContextHolder.set(renderContext);

        Node node = environment.getParser().parse(resource);
        environment.getRenderEnvironment().getRenderNodeService()
                .render(new RenderRequest(renderContext, environment), node)
                .appendTo(renderResult);
    }
}
