package org.jtwig;

import org.jtwig.environment.*;
import org.jtwig.escape.EscapeEngine;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.RenderContext;
import org.jtwig.render.context.RenderContextHolder;
import org.jtwig.render.context.model.BlockContext;
import org.jtwig.renderable.RenderResult;
import org.jtwig.renderable.StreamRenderResult;
import org.jtwig.renderable.StringBuilderRenderResult;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.value.context.IsolateParentValueContext;
import org.jtwig.value.context.JtwigModelValueContext;
import org.jtwig.value.context.MapValueContext;
import org.jtwig.value.context.ValueContext;

import java.io.File;
import java.io.OutputStream;

import static org.jtwig.resource.reference.ResourceReference.*;

public class JtwigTemplate {
    public static final EnvironmentFactory ENVIRONMENT_FACTORY = new EnvironmentFactory();

    public static JtwigTemplate inlineTemplate (String template) {
        return inlineTemplate(template, new DefaultEnvironmentConfiguration());
    }
    public static JtwigTemplate inlineTemplate (String template, EnvironmentConfiguration configuration) {
        return new JtwigTemplate(ENVIRONMENT_FACTORY.create(configuration), new ResourceReference(STRING, template));
    }

    public static JtwigTemplate classpathTemplate(String location) {
        return classpathTemplate(location, new DefaultEnvironmentConfiguration());
    }

    public static JtwigTemplate classpathTemplate(String location, EnvironmentConfiguration environmentConfiguration) {
        ResourceReference resourceReference = new ResourceReference(CLASSPATH, location);
        Environment environment = ENVIRONMENT_FACTORY.create(environmentConfiguration);
        return new JtwigTemplate(environment, resourceReference);
    }

    public static JtwigTemplate fileTemplate (String filename, EnvironmentConfiguration environmentConfiguration) {
        Environment environment = ENVIRONMENT_FACTORY.create(environmentConfiguration);
        ResourceReference resourceReference = new ResourceReference(FILE, filename);
        return new JtwigTemplate(environment, resourceReference);
    }


    public static JtwigTemplate fileTemplate (String filename) {
        return fileTemplate(filename, new DefaultEnvironmentConfiguration());
    }

    public static JtwigTemplate fileTemplate (File path, EnvironmentConfiguration environmentConfiguration) {
        return fileTemplate(path.getAbsolutePath(), environmentConfiguration);
    }

    public static JtwigTemplate fileTemplate (File path) {
        return fileTemplate(path, new DefaultEnvironmentConfiguration());
    }

    private final ResourceReference resource;
    private final Environment environment;

    public JtwigTemplate(Environment environment, ResourceReference resource) {
        this.resource = resource;
        this.environment = environment;
    }

    public String render(JtwigModel model) {
        RenderResult result = new StringBuilderRenderResult();
        render(model, result);
        return result.content();
    }

    public void render(JtwigModel model, OutputStream outputStream) {
        RenderResult result = new StreamRenderResult(outputStream, environment.getRenderEnvironment().getDefaultOutputCharset());
        render(model, result);
    }

    private void render(JtwigModel model, RenderResult renderResult) {
        RenderContext renderContext = RenderContext.create()
                .start(ValueContext.class, new IsolateParentValueContext(new JtwigModelValueContext(model), MapValueContext.newContext()))
                .start(EscapeEngine.class, environment.getEscapeEnvironment().getInitialEscapeEngine())
                .start(ResourceReference.class, resource)
                .start(BlockContext.class, BlockContext.newContext())
                ;

        EnvironmentHolder.set(environment);
        RenderContextHolder.set(renderContext);

        Node node = environment.getParser().parse(environment, resource);
        environment.getRenderEnvironment().getRenderNodeService()
                .render(new RenderRequest(renderContext, environment), node)
                .appendTo(renderResult);
    }
}
