package org.jtwig.util;

import org.jtwig.model.position.Position;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlEncodingUtils {
    public static String encode (String content, String encoding, Position position) {
        try {
            return URLEncoder.encode(content, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(ErrorMessageFormatter.errorMessage(position, String.format("Invalid encoding '%s'", encoding)), e);
        }
    }
}
