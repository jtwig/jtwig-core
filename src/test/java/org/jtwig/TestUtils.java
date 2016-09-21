package org.jtwig;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

public class TestUtils {
    public static String universalPath (String path) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(StringUtils.join(asList(path.split(Pattern.quote("/"))), File.separator));
            File file = new File(stringBuilder.toString());
            return new File(file.getParentFile(), file.getName()).getCanonicalPath();
        } catch (Exception e) {
            return path;
        }
    }
}
