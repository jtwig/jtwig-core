package org.jtwig.functions.impl.mixed;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.context.ValueContext;

import java.util.Map;

public class DumpFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "dump";
    }

    @Override
    public Object execute(FunctionRequest request) {
        StringBuilder builder = new StringBuilder();
        if (request.getNumberOfArguments() == 0) {
            ValueContext valueContext = request.getRenderContext().getCurrent(ValueContext.class);
            builder.append(toString(valueContext));
        }

        for (int i = 0; i < request.getNumberOfArguments(); i++) {
            Object value = request.get(i);
            builder.append(toString(value));
        }

        return builder.toString();
    }

    private String toString (Object value) {
        ReflectionToStringBuilder toStringBuilder = new ReflectionToStringBuilder(value, new RecursiveJsonLikeToStringStyle());
        toStringBuilder.setAppendTransients(true);
        return toStringBuilder.toString();
    }

    private static class RecursiveJsonLikeToStringStyle extends RecursiveToStringStyle  {
        public RecursiveJsonLikeToStringStyle() {
            this.setUseClassName(false);
            this.setUseIdentityHashCode(false);

            this.setContentStart("{");
            this.setContentEnd("}");

            this.setArrayStart("[");
            this.setArrayEnd("]");

            this.setFieldSeparator(",");
            this.setFieldNameValueSeparator(":");

            this.setNullText("null");

            this.setSummaryObjectStartText("\"<");
            this.setSummaryObjectEndText(">\"");

            this.setSizeStartText("\"<size=");
            this.setSizeEndText(">\"");
        }

        @Override
        protected void appendDetail(StringBuffer buffer, String fieldName, Map<?, ?> map) {
            buffer.append(getContentStart());
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                buffer.append(String.valueOf(entry.getKey()));
                buffer.append(getFieldNameValueSeparator());
                buffer.append(ToStringBuilder.reflectionToString(entry.getValue(), this));
            }
            buffer.append(getContentEnd());
        }
    }
}
