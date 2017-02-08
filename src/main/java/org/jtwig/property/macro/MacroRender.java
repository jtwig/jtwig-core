package org.jtwig.property.macro;

import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.model.Macro;
import org.jtwig.render.context.model.MacroDefinitionContext;
import org.jtwig.renderable.RenderResult;
import org.jtwig.renderable.StringBuilderRenderResult;
import org.jtwig.value.context.MapValueContext;
import org.jtwig.value.context.ValueContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MacroRender {
    public String render(RenderRequest request, List<Object> arguments, Macro macro) {
        Map<String, Object> valueMap = new HashMap<>();
        Iterator<Object> valueIterator = arguments.iterator();

        for (String variableName : macro.getArgumentNames()) {
            if (valueIterator.hasNext()) {
                valueMap.put(variableName, valueIterator.next());
            }
        }

        for (Map.Entry<String, MacroDefinitionContext> entry : macro.getMacroAliasesContext()) {
            valueMap.put(entry.getKey(), entry.getValue());
        }

        ValueContext valueContext = MapValueContext.newContext(valueMap);
        request.getRenderContext().getValueContext().start(valueContext);
        RenderResult renderResult = new StringBuilderRenderResult();

        request.getEnvironment().getRenderEnvironment().getRenderNodeService().render(request, macro.getContent())
            .appendTo(renderResult);


        request.getRenderContext().getValueContext().end();

        return renderResult.content();

    }
}
