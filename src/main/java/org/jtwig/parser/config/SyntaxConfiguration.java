package org.jtwig.parser.config;

public interface SyntaxConfiguration {
    String startComment ();
    String endComment ();
    String startOutput();
    String endOutput();
    String startCode();
    String endCode();
}
