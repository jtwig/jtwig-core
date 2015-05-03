package org.jtwig;

import org.jtwig.configuration.Configuration;
import org.jtwig.configuration.InitialEscapeModeParameter;
import org.jtwig.context.RenderContext;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderException;
import org.jtwig.render.RenderResult;
import org.jtwig.resource.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
        RenderResult result = new RenderResult();

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
                .appendTo(result);

        return result.toString();
    }

    public void render(JtwigModel model, OutputStream outputStream) {
        try {
            outputStream.write(render(model).getBytes());
        } catch (IOException e) {
            throw new RenderException(e);
        }
    }

}
