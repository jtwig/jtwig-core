package org.jtwig.util;

public class EscapeUtils {
    public static String escapeJtwig (String input) {
        return input.replace("\\", "\\\\");
    }
}
