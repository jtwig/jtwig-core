package org.jtwig.functions.impl.mixed;

import org.apache.commons.lang3.StringUtils;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.util.UrlEncodingUtils;

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
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);
        Object input = request.get(0);
        if (input instanceof Map) {
            Map map = (Map) input;
            List<String> pieces = new ArrayList<>();
            for (Object key : map.keySet()) {
                pieces.add(encodeString(request, key.toString()) + "=" + encodeString(request, map.get(key).toString()));
            }
            return StringUtils.join(pieces, "&");
        }
        return encodeString(request, getString(request, 0));
    }

    private String getString(FunctionRequest request, int index) {
        return request.getEnvironment().getValueEnvironment().getStringConverter().convert(request.get(index));
    }

    private String encodeString (FunctionRequest request, String input) {
        return UrlEncodingUtils.encode(input, Charset.defaultCharset().displayName(), request.getPosition());
    }
}
