package org.jtwig.functions.impl.control;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.Collections2;
import org.apache.commons.lang3.StringUtils;
import org.jtwig.context.RenderContext;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import static java.util.Arrays.asList;

public class EscapeFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "escape";
    }

    @Override
    public Object execute(final JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(1);
        request.maximumNumberOfArguments(2);

        EscapeMode escapeMode = EscapeMode.HTML;
        if (request.getNumberOfArguments() == 2) {
            final String requestedEscapeMode = request.getArgument(1, String.class);
            escapeMode = EscapeMode.fromString(requestedEscapeMode.toUpperCase())
                .or(throwError(request, requestedEscapeMode));
        }

        getRenderContext().currentNode().mode(escapeMode);
        return request.getArgument(0, Object.class);
    }

    private Supplier<EscapeMode> throwError(final JtwigFunctionRequest request, final String requestedEscapeMode) {
        return new Supplier<EscapeMode>() {
            @Override
            public EscapeMode get() {
                String possibleEscapeModes = StringUtils.join(Collections2.transform(asList(EscapeMode.values()), new Function<EscapeMode, String>() {
                    @Override
                    public String apply(EscapeMode input) {
                        return input.toString();
                    }
                }), ", ");
                throw request.exception(String.format("Invalid escape mode requested '%s'. Only supporting [%s]", requestedEscapeMode, possibleEscapeModes));
            }
        };
    }

    protected RenderContext getRenderContext() {
        return RenderContextHolder.get();
    }
}
