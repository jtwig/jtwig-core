package org.jtwig.functions.impl.control;

import com.google.common.base.Optional;
import org.jtwig.escape.EscapeEngine;
import org.jtwig.escape.HtmlEscapeEngine;
import org.jtwig.escape.NoneEscapeEngine;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

public class EscapeFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "escape";
    }

    @Override
    public Object execute(final FunctionRequest request) {
        request.minimumNumberOfArguments(1);
        request.maximumNumberOfArguments(2);

        EscapeEngine escapeEngine = HtmlEscapeEngine.instance();
        if (request.getNumberOfArguments() == 2) {
            if (request.get(1) instanceof Boolean) {
                if (!(boolean) request.get(1)) {
                    escapeEngine = NoneEscapeEngine.instance();
                }
            } else {
                String requestedEscapeMode = request.getEnvironment().getValueEnvironment().getStringConverter().convert(request.get(1));
                Optional<EscapeEngine> optionalEscapeEngine = request.getEnvironment().getEscapeEnvironment().getEscapeEngineSelector().escapeEngineFor(requestedEscapeMode);
                if (optionalEscapeEngine.isPresent()) {
                    escapeEngine = optionalEscapeEngine.get();
                } else {
                    throw request.exception(String.format("Invalid escape engine requested '%s'. Only supporting %s", requestedEscapeMode, request.getEnvironment().getEscapeEnvironment().getEscapeEngineSelector().availableEscapeEngines()));
                }
            }
        }

        request.getRenderContext().getEscapeEngineContext().set(escapeEngine);
        return request.get(0);
    }
}
