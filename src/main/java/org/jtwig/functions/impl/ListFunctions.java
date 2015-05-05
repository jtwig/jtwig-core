package org.jtwig.functions.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.functions.annotations.Parameter;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.compare.JtwigValueLooseComparator;

import java.util.*;

public class ListFunctions {
    @JtwigFunction("batch")
    public List<List<Object>> batch (@Parameter Object input, @Parameter int groupSize) {
        Iterator<Object> iterator = JtwigValueFactory.create(input).asCollection().iterator();
        List<List<Object>> result = new ArrayList<>();
        while (iterator.hasNext()) {
            List<Object> batch = new ArrayList<>();
            for (int i=0;i<groupSize;i++) {
                if (iterator.hasNext())
                    batch.add(iterator.next());
            }
            result.add(batch);
        }
        return result;
    }

    @JtwigFunction("batch")
    public List<List<Object>> batch (@Parameter Object input, @Parameter int groupSize, @Parameter Object padding) {
        Iterator<Object> iterator = JtwigValueFactory.create(input).asCollection().iterator();
        List<List<Object>> result = new ArrayList<>();
        while (iterator.hasNext()) {
            List<Object> batch = new ArrayList<Object>();
            for (int i=0;i<groupSize;i++) {
                if (iterator.hasNext())
                    batch.add(iterator.next());
                else
                    batch.add(padding);
            }
            result.add(batch);
        }
        return result;
    }

    @JtwigFunction(value = "concat", aliases = { "concatenate" })
    public String concatenate (@Parameter String... pieces) {
        StringBuilder builder = new StringBuilder();
        for (String piece : pieces) {
            if (piece != null)
                builder.append(piece);

        }
        return builder.toString();
    }

    @JtwigFunction("join")
    public String join (@Parameter Object input, @Parameter String separator) {
        List<String> pieces = new ArrayList<>();
        for (Object next : JtwigValueFactory.create(input).asCollection()) {
            if (next == null) pieces.add("");
            else pieces.add(next.toString());
        }
        return StringUtils.join(pieces, separator);
    }

    @JtwigFunction("join")
    public String join (@Parameter Object input) {
        return join(input, "");
    }

    @JtwigFunction("merge")
    public Object merge (@Parameter Object first, @Parameter Object... rest) {
        if (first instanceof Iterable)
            return mergeList(first, rest);
        else if (first instanceof Map)
            return mergeMap(first, rest);
        else // is array (precondition)
            return mergeArray(first, rest);
    }

    @JtwigFunction("length")
    public int length (@Parameter Iterable input) {
        Iterator iterator = input.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    @JtwigFunction("first")
    public Object first (@Parameter List input) {
        if (input.isEmpty()) return null;
        return input.get(0);
    }

    @JtwigFunction("last")
    public Object last (@Parameter List input) {
        if (input.isEmpty()) return null;
        return input.get(input.size() - 1);
    }

    @JtwigFunction("reverse")
    public List reverse (@Parameter Object input) {
        Iterator<Object> iterator = JtwigValueFactory.create(input).asCollection().iterator();
        List<Object> result = new ArrayList<Object>();
        while (iterator.hasNext())
            result.add(iterator.next());

        Collections.reverse(result);
        return result;
    }

    @JtwigFunction("slice")
    public Object slice(@Parameter Object input, @Parameter int begin, @Parameter int length) throws CalculationException {

        if (input instanceof String) {
            String value = (String) input;
            if (value.length() < begin) return "";
            return value.substring(begin, Math.min(value.length(), begin + length));
        }

        Iterator<Object> iterator = JtwigValueFactory.create(input).asCollection().iterator();
        List list = new ArrayList();
        int i = 0;
        while (iterator.hasNext()) {
            if (i >= begin && i < begin + length)
                list.add(iterator.next());
            else
                iterator.next();
            i++;
        }

        if (input instanceof Iterable)
            return list;
        else
            return list.toArray();
    }

    @JtwigFunction("sort")
    public List sort (@Parameter List input) {
        Collections.sort(input);
        return input;
    }

    @JtwigFunction("max")
    public Object max (@Parameter Object ... values) {
        JtwigValue result = JtwigValueFactory.create(values[0]);
        values = ArrayUtils.remove(values, 0);
        for(Object value : values) {
            JtwigValue jtwigValue = JtwigValueFactory.create(value);
            int cmp = Objects.compare(result, jtwigValue, JtwigValueLooseComparator.instance());
            if(cmp < 0) {
                result = jtwigValue;
            }
        }
        return result;
    }

    @JtwigFunction("min")
    public Object min (@Parameter Object ... values) {
        JtwigValue result = JtwigValueFactory.create(values[0]);
        values = ArrayUtils.remove(values, 0);
        for(Object value : values) {
            JtwigValue jtwigValue = JtwigValueFactory.create(value);
            int cmp = Objects.compare(result, jtwigValue, JtwigValueLooseComparator.instance());
            if(cmp > 0) {
                result = jtwigValue;
            }
        }
        return result;
    }

    private Object mergeArray(Object first, Object... arguments) {
        List<Object> result = new ArrayList<>();
        for (Object obj : (Object[]) first)
            result.add(obj);
        for (Object obj : arguments) {
            if (obj == null) continue;
            Object[] list = (Object[]) obj;
            for (Object value : list) {
                result.add(value);
            }
        }
        return result.toArray();
    }

    private Object mergeMap(Object first, Object... arguments) {
        Map<Object, Object> result;
        if (first instanceof TreeMap)
            result = new TreeMap<Object, Object>();
        else
            result = new HashMap<Object, Object>();
        result.putAll((Map) first);
        for (Object obj : arguments) {
            if (obj == null) continue;
            result.putAll((Map) obj);
        }
        return result;
    }

    private Object mergeList(Object first, Object... arguments) {
        List<Object> result = new ArrayList<Object>();
        result.addAll((List) first);
        for (Object obj : arguments) {
            if (obj == null) continue;
            result.addAll((List) obj);
        }
        return result;
    }
}
