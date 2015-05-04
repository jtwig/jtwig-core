package org.jtwig.functions.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.functions.annotations.Parameter;
import org.jtwig.util.HtmlUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.nio.charset.Charset.forName;
import static java.util.Arrays.asList;

public class StringFunctions {
    @JtwigFunction("capitalize")
    public String capitalize (@Parameter String input) {
        if (input.length() > 0)
            return input.substring(0, 1).toUpperCase() + input.substring(1);
        else
            return input;
    }

    @JtwigFunction("convert_encoding")
    public String convertEncoding (@Parameter String input, @Parameter String from, @Parameter String to) {
        return new String(input.getBytes(forName(from)), forName(to));
    }

    @JtwigFunction("format")
    public String format (@Parameter String input, @Parameter Object... arguments) {
        return String.format(input, arguments);
    }

    @JtwigFunction("lower")
    public String lower (@Parameter String input) {
        return input.toLowerCase();
    }

    @JtwigFunction("nl2br")
    public String nl2br (@Parameter String input) {
        return input.replace("\n", "<br />");
    }

    @JtwigFunction("replace")
    public String replace (@Parameter("value") String input, @Parameter("replacements") Map<String, Object> replacements) {
        for (String key : replacements.keySet()) {
            input = input.replace(key, replacements.get(key).toString());
        }
        return input;
    }

    @JtwigFunction("split")
    public List<String> split (@Parameter String input, @Parameter String separator) {
        return asList(input.split(separator));
    }

    @JtwigFunction("striptags")
    public String stripTags (@Parameter String input) {
        return stripTags(input, "");
    }

    @JtwigFunction("striptags")
    public String stripTags (@Parameter String input, @Parameter String allowedTags) {
        return HtmlUtils.stripTags(input, allowedTags);
    }


    @JtwigFunction("title")
    public String title (@Parameter String input) {
        return WordUtils.capitalize(input);
    }

    @JtwigFunction("trim")
    public String trim (@Parameter String input) {
        return (input == null) ? null : input.trim();
    }

    @JtwigFunction("upper")
    public String upper (@Parameter String input) {
        return input.toUpperCase();
    }

    @JtwigFunction("url_encode")
    public String urlEncode (@Parameter String input) throws UnsupportedEncodingException {
        return URLEncoder.encode(input, Charset.defaultCharset().displayName());
    }
    @JtwigFunction("url_encode")
    public String urlEncode (@Parameter Map input) throws UnsupportedEncodingException {
        List<String> pieces = new ArrayList<String>();
        for (Object key : input.keySet()) {
            pieces.add(urlEncode(key.toString()) + "=" + urlEncode(input.get(key).toString()));
        }
        return StringUtils.join(pieces, "&");
    }

    @JtwigFunction("first")
    public Character first (@Parameter String input) {
        if (input.isEmpty()) return null;
        return input.charAt(0);
    }
    @JtwigFunction("last")
    public Character last (@Parameter String input) {
        if (input.isEmpty()) return null;
        return input.charAt(input.length() - 1);
    }
    @JtwigFunction("reverse")
    public String reverse (@Parameter String input) {
        return new StringBuilder(input).reverse().toString();
    }
}
