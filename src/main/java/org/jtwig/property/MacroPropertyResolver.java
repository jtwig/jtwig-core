package org.jtwig.property;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.context.RenderContextFactory;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.context.model.Macro;
import org.jtwig.context.model.MacroContext;
import org.jtwig.context.values.SimpleValueContext;
import org.jtwig.context.values.ValueContext;
import org.jtwig.reflection.model.Value;
import org.jtwig.render.RenderResult;
import org.jtwig.render.StringBuilderRenderResult;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.Undefined;

import java.util.HashMap;
import java.util.Iterator;

public class MacroPropertyResolver implements PropertyResolver {
    @Override
    public Optional<Value> resolve(PropertyResolveRequest request) {
        if (request.getEntity().getValue() instanceof MacroContext) {
            MacroContext macroContext = (MacroContext) request.getEntity().getValue();
            return macroContext
                    .resolve(request.getPropertyName())
                    .transform(renderMacro(request));
        } else {
            return Optional.absent();
        }
    }

    private Function<? super Macro, Value> renderMacro(final PropertyResolveRequest request) {
        return new Function<Macro, Value>() {
            @Override
            public Value apply(Macro macro) {
                ValueContext valueContext = new SimpleValueContext(new HashMap<String, Value>());

                Iterator<JtwigValue> valueIterator = request.getArguments().iterator();
                for (String variableName : macro.getArgumentNames()) {
                    if (valueIterator.hasNext()) {
                        valueContext.add(variableName, valueIterator.next().as(Object.class).or(new Value(Undefined.UNDEFINED)).getValue());
                    }
                }

                RenderResult renderResult = new StringBuilderRenderResult();

                createRenderContext(valueContext)
                        .nodeRenderer()
                        .render(macro.getContent())
                        .appendTo(renderResult);

                return new Value(renderResult.content());
            }
        };
    }

    protected RenderContext createRenderContext(ValueContext valueContext) {
        return new RenderContextFactory().create(valueContext, null, getRenderContext().environment());
    }

    // Test purposes
    protected RenderContext getRenderContext() {
        return RenderContextHolder.get();
    }

}
