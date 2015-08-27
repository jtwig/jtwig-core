package org.jtwig.functions.impl;

import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.functions.annotations.Parameter;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapFunctions {
    @JtwigFunction("keys")
    public Set keys (@Parameter Map map) {
        return map.keySet();
    }

    @JtwigFunction("length")
    public int length (@Parameter Map input) {
        return input.size();
    }


    @JtwigFunction("first")
    public Object first (@Parameter Map input) {
        if (input.isEmpty()) return null;
        return input.get(input.keySet().iterator().next());
    }

    @JtwigFunction("last")
    public Object last (@Parameter Map input) {
        if (input.isEmpty()) return null;
        Iterator iterator = input.keySet().iterator();
        Object key = iterator.next();
        while (iterator.hasNext()) key = iterator.next();
        return input.get(key);
    }
}
