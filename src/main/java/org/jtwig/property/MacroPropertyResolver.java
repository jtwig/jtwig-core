package org.jtwig.property;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.context.RenderContextBuilder;
import org.jtwig.context.model.Macro;
import org.jtwig.context.model.MacroContext;
import org.jtwig.context.values.SimpleValueContext;
import org.jtwig.context.values.ValueContext;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.render.RenderResult;
import org.jtwig.render.StringBuilderRenderResult;
import org.jtwig.util.JtwigValue;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;

public class MacroPropertyResolver implements PropertyResolver {
    @Override
    public Optional<JtwigValue> resolve(PropertyResolveRequest request) {
        if (request.getEntity() instanceof MacroContext) {
            MacroContext macroContext = (MacroContext) request.getEntity();
            return macroContext
                    .resolve(request.getPropertyName())
                    .transform(renderMacro(request));
        } else {
            return Optional.absent();
        }
    }

    private Function<? super Macro, JtwigValue> renderMacro(final PropertyResolveRequest request) {
        return new Function<Macro, JtwigValue>() {
            @Override
            public JtwigValue apply(Macro macro) {
                ValueContext valueContext = new SimpleValueContext(new HashMap<String, JtwigValue>());

                Iterator<FunctionArgument> valueIterator = request.getArguments().iterator();
                for (String variableName : macro.getArgumentNames()) {
                    if (valueIterator.hasNext()) {
                        valueContext.add(variableName, valueIterator.next().getValue().asObject());
                    }
                }

                RenderResult renderResult = new StringBuilderRenderResult();

                renderContextBuilder()
                    .withConfiguration(getRenderContext().configuration())
                    .withValueContext(valueContext)
                    .build()
                    .nodeRenderer()
                    .render(macro.getContent())
                    .appendTo(renderResult);

                return new JtwigValue(renderResult.content());
            }
        };
    }

    // Test purposes
    protected RenderContextBuilder renderContextBuilder() {
        return RenderContextBuilder.renderContext();
    }

    // Test purposes
    protected RenderContext getRenderContext() {
        return RenderContextHolder.get();
    }

}
