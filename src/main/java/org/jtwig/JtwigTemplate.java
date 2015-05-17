package org.jtwig;

import com.google.common.base.Optional;
import org.jtwig.configuration.Configuration;
import org.jtwig.context.RenderContext;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderResult;
import org.jtwig.render.StreamRenderResult;
import org.jtwig.render.StringBuilderRenderResult;
import org.jtwig.resource.ClasspathResource;
import org.jtwig.resource.FileResource;
import org.jtwig.resource.Resource;
import org.jtwig.resource.StringResource;
import org.jtwig.resource.resolver.ClasspathResourceResolver;
import org.jtwig.util.OptionalUtils;

import java.io.File;
import java.io.OutputStream;

import static org.jtwig.configuration.ConfigurationBuilder.configuration;
import static org.jtwig.context.RenderContextBuilder.renderContext;

public class JtwigTemplate {
    public static JtwigTemplate inlineTemplate (String template) {
        return inlineTemplate(template, configuration().build());
    }
    public static JtwigTemplate inlineTemplate (String template, Configuration configuration) {
        return new JtwigTemplate(new StringResource(template), configuration);
    }

    public static JtwigTemplate classpathTemplate(String location) {
        return classpathTemplate(location, configuration().build());
    }

    public static JtwigTemplate classpathTemplate(String location, Configuration configuration) {
        if (!location.startsWith(ClasspathResourceResolver.PREFIX)) {
            location = ClasspathResourceResolver.PREFIX + location;
        }
        Optional<Resource> resource = configuration.resourceResolver().resolve(null, location);
        return new JtwigTemplate(resource.or(OptionalUtils.<Resource>throwException(String.format("Classpath resource '%s' not found", location))), configuration);
    }

    public static JtwigTemplate fileTemplate (File file, Configuration configuration) {
        return new JtwigTemplate(new FileResource(file), configuration);
    }


    public static JtwigTemplate fileTemplate (File file) {
        return new JtwigTemplate(new FileResource(file), configuration().build());
    }

    public static JtwigTemplate fileTemplate (String path, Configuration configuration) {
        return new JtwigTemplate(new FileResource(new File(path)), configuration);
    }

    public static JtwigTemplate fileTemplate (String path) {
        return new JtwigTemplate(new FileResource(new File(path)), configuration().build());
    }

    private final Resource template;
    private final Configuration configuration;

    public JtwigTemplate(Resource template, Configuration configuration) {
        this.template = template;
        this.configuration = configuration;
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
        Node compositeNode = configuration.parser().parse(template);

        RenderContext renderContext = renderContext()
                .withConfiguration(configuration)
                .withValueContext(model)
                .withResource(template)
                .withInitialEscapeMode(configuration.initialEscapeMode())
                .build();

        RenderContextHolder.set(renderContext);

        renderContext
                .nodeRenderer()
                .render(compositeNode)
                .appendTo(result);
    }
}
