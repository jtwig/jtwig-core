package org.jtwig.functions.impl.mixed;

import org.apache.commons.lang3.StringUtils;
import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UrlEncodeFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "url_encode";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);
        Object input = request.getArgument(0, Object.class);
        if (input instanceof Map) {
            Map map = (Map) input;
            List<String> pieces = new ArrayList<>();
            for (Object key : map.keySet()) {
                pieces.add(encodeString(request, key.toString()) + "=" + encodeString(request, map.get(key).toString()));
            }
            return StringUtils.join(pieces, "&");
        }
        return encodeString(request, request.getArgument(0, String.class));
    }

    private String encodeString (JtwigFunctionRequest request, String input) {
        try {
            return URLEncoder.encode(input, Charset.defaultCharset().displayName());
        } catch (UnsupportedEncodingException e) {
            throw request.exception(e.getMessage(), e);
        }
    }
}
