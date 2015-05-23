package org.jtwig;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContextFactory;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.environment.DefaultEnvironmentConfiguration;
import org.jtwig.environment.Environment;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.environment.EnvironmentFactory;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderResult;
import org.jtwig.render.StreamRenderResult;
import org.jtwig.render.StringBuilderRenderResult;
import org.jtwig.resource.FileResource;
import org.jtwig.resource.Resource;
import org.jtwig.resource.StringResource;
import org.jtwig.resource.resolver.ClasspathResourceResolver;
import org.jtwig.util.OptionalUtils;

import java.io.File;
import java.io.OutputStream;

public class JtwigTemplate {
    public static final EnvironmentFactory ENVIRONMENT_FACTORY = new EnvironmentFactory();

    public static JtwigTemplate inlineTemplate (String template) {
        return inlineTemplate(template, new DefaultEnvironmentConfiguration());
    }
    public static JtwigTemplate inlineTemplate (String template, EnvironmentConfiguration configuration) {
        return new JtwigTemplate(new StringResource(template), ENVIRONMENT_FACTORY.create(configuration));
    }

    public static JtwigTemplate classpathTemplate(String location) {
        return classpathTemplate(location, new DefaultEnvironmentConfiguration());
    }

    public static JtwigTemplate classpathTemplate(String location, EnvironmentConfiguration environmentConfiguration) {
        if (!location.startsWith(ClasspathResourceResolver.PREFIX)) {
            location = ClasspathResourceResolver.PREFIX + location;
        }
        Environment environment = ENVIRONMENT_FACTORY.create(environmentConfiguration);
        Optional<Resource> resource = environment.resourceResolver().resolve(null, location);
        return new JtwigTemplate(resource.or(OptionalUtils.<Resource>throwException(String.format("Classpath resource '%s' not found", location))), environment);
    }

    public static JtwigTemplate fileTemplate (File file, EnvironmentConfiguration environment) {
        return new JtwigTemplate(new FileResource(file), ENVIRONMENT_FACTORY.create(environment));
    }


    public static JtwigTemplate fileTemplate (File file) {
        return fileTemplate(file, new DefaultEnvironmentConfiguration());
    }

    public static JtwigTemplate fileTemplate (String path, EnvironmentConfiguration environment) {
        return new JtwigTemplate(new FileResource(new File(path)), ENVIRONMENT_FACTORY.create(environment));
    }

    public static JtwigTemplate fileTemplate (String path) {
        return fileTemplate(path, new DefaultEnvironmentConfiguration());
    }

    private final RenderContextFactory renderContextFactory = new RenderContextFactory();
    private final Resource template;
    private final Environment environment;

    public JtwigTemplate(Resource template, Environment environment) {
        this.template = template;
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

    private void render(JtwigModel model, RenderResult result) {
        Node compositeNode = environment.parser().parse(template);

        RenderContextHolder.set(renderContextFactory.create(model, template, environment))
                .nodeRenderer()
                .render(compositeNode)
                .appendTo(result);
    }
}
