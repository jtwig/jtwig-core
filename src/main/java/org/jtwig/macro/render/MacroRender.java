package org.jtwig.macro.render;

import org.jtwig.macro.Macro;
import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.renderable.RenderResult;
import org.jtwig.renderable.StringBuilderRenderResult;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.value.context.MapValueContext;
import org.jtwig.value.context.ValueContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MacroRender {
    public Object render(PropertyResolveRequest request, List<Object> arguments, Macro macro) {
        Map<String, Object> valueMap = new HashMap<>();
        Iterator<Object> valueIterator = arguments.iterator();

        for (String variableName : macro.getArgumentNames()) {
            if (valueIterator.hasNext()) {
                valueMap.put(variableName, valueIterator.next());
            }
        }

        ValueContext valueContext = MapValueContext.newContext(valueMap);
        request.getRenderContext().start(ValueContext.class, valueContext);
        request.getRenderContext().start(ResourceReference.class, macro.getResourceReference());
        RenderResult renderResult = new StringBuilderRenderResult();

        request.getEnvironment().getRenderEnvironment().getRenderNodeService()
                .render(request, macro.getContent())
                .appendTo(renderResult);


        request.getRenderContext().end(ValueContext.class);
        request.getRenderContext().end(ResourceReference.class);

        return renderResult.content();

    }
}
