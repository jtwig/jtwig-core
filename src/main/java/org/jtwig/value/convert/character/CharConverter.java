package org.jtwig.value.convert.character;

import org.jtwig.value.convert.Converter;

public class CharConverter implements Converter<Character> {
    @Override
    public Result<Character> convert(Object object) {
        String string = object.toString();

        if (string.length() == 1) {
            return Result.defined(string.charAt(0));
        }

        return Result.undefined();
    }
}
