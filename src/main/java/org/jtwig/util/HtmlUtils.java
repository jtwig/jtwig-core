package org.jtwig.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static java.util.regex.Pattern.compile;
import static java.util.regex.Pattern.quote;

public final class HtmlUtils {
    private static final String START_COMMENT = "<!--";
    private static final String END_COMMENT = "-->";

    private HtmlUtils () {}

    public static String stripTags (String input, String allowedTags) {
        return removeUnknownTags(removeHtmlComments(input), allowedTags);
    }
    public static String stripTags (String input) {
        return removeUnknownTags(removeHtmlComments(input), "");
    }

    private static String removeUnknownTags(String input, String knownTags) {
        List<String> knownTagList = asList(knownTags.replaceAll("^<", "").replaceAll(">$", "").split("><"));
        return removeTags(input, knownTagList);
    }

    private static String removeTags(String input, List<String> knownTagList) {
        Pattern tag = compile("</?([^\\s>]*)\\s*[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher matches = tag.matcher(input);
        while (matches.find()) {
            if (!knownTagList.contains(matches.group(1))) {
                input = input.replaceAll(quote(matches.group()), "");
            }
        }
        return input;
    }

    private static String removeTags (String input, String startTag, String endTag) {
        while (input.contains(startTag)) {
            int start = input.indexOf(startTag);
            int end = input.substring(start + startTag.length()).indexOf(endTag);

            if (end == -1) input = input.substring(0, start);
            else input = input.substring(0, start) + input.substring(start + startTag.length() + end + endTag.length());
        }

        return input;
    }

    private static String removeHtmlComments (String input) {
        return removeTags(input, START_COMMENT, END_COMMENT);
    }
}
