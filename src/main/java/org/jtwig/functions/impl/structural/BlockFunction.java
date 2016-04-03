package org.jtwig.functions.impl.structural;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.model.tree.Node;
import org.jtwig.renderable.StringBuilderRenderResult;

public class BlockFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "block";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1);
        request.maximumNumberOfArguments(1);
        String name = request.getEnvironment().getValueEnvironment().getStringConverter().convert(request.get(0));

        Optional<Node> nodeOptional = request.getRenderContext().getBlockContext().getCurrent().get(name);
        if (nodeOptional.isPresent()) {
            return request.getEnvironment().getRenderEnvironment().getRenderNodeService().render(request, nodeOptional.get())
                    .appendTo(new StringBuilderRenderResult()).content();
        } else {
            return "";
        }
    }
}
