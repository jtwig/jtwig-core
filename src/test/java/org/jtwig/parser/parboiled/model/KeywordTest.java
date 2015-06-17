package org.jtwig.parser.parboiled.model;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class KeywordTest {
    @Test
    public void printKeywords() throws Exception {
        Collection<String> list = new ArrayList<>();
        for (Keyword keyword : Keyword.values()) {
            list.add(String.format("%s", keyword.toString()));
        }

        System.out.println(StringUtils.join(list, " "));
    }
}