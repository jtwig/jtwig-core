package org.jtwig;

import org.jtwig.configuration.Configuration;
import org.jtwig.configuration.InitialEscapeModeParameter;
import org.jtwig.context.RenderContext;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.model.tree.Node;
import org.jtwig.resource.Resource;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.jtwig.configuration.InitialEscapeModeParameter.escapeMode;
import static org.jtwig.context.RenderContextBuilder.renderContext;

public class JtwigTemplate {

    private final Resource template;
    private final Configuration configuration;

    public JtwigTemplate(Resource template, Configuration configuration) {
        this.template = template;
        this.configuration = configuration;
    }

    public String render(JtwigModel model) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        render(model, outputStream);
        return outputStream.toString();
    }

    public void render(JtwigModel model, OutputStream outputStream) {
        Node compositeNode = configuration.parser().parse(template);

        RenderContext renderContext = renderContext()
                .withConfiguration(configuration)
                .withValueContext(model)
                .withResource(template)
                .withInitialEscapeMode(configuration.parameter(escapeMode()))
                .build();

        RenderContextHolder.set(renderContext);

        renderContext
            .nodeRenderer()
            .render(compositeNode)
            .accept(outputStream);
    }

}
