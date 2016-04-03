package org.jtwig.functions.impl.control;

import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.render.context.model.EscapeMode;

import java.util.ArrayList;
import java.util.List;

public class EscapeFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "escape";
    }

    @Override
    public Object execute(final FunctionRequest request) {
        request.minimumNumberOfArguments(1);
        request.maximumNumberOfArguments(2);

        EscapeMode escapeMode = EscapeMode.HTML;
        if (request.getNumberOfArguments() == 2) {
            if (request.get(1) instanceof Boolean) {
                if (!(boolean) request.get(1)) {
                    escapeMode = EscapeMode.NONE;
                }
            } else {
                String requestedEscapeMode = request.getEnvironment().getValueEnvironment().getStringConverter().convert(request.get(1));
                Optional<EscapeMode> escapeModeOptional = EscapeMode.fromString(requestedEscapeMode.toUpperCase());
                if (escapeModeOptional.isPresent()) {
                    escapeMode = escapeModeOptional.get();
                } else {
                    String possibleEscapeModes = possibleEscapeModes();
                    throw request.exception(String.format("Invalid escape mode requested '%s'. Only supporting [%s]", requestedEscapeMode, possibleEscapeModes));
                }
            }
        }

        request.getRenderContext().getEscapeModeContext().set(escapeMode);
        return request.get(0);
    }

    private String possibleEscapeModes() {
        List<String> escapeModes = new ArrayList<>();
        for (EscapeMode escapeMode : EscapeMode.values()) {
            escapeModes.add(escapeMode.toString());
        }
        return StringUtils.join(escapeModes, ", ");
    }
}
