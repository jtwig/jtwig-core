package org.jtwig.parser.config;

public class DefaultSyntaxConfiguration extends SyntaxConfiguration {
    public DefaultSyntaxConfiguration() {
        super("{#", "#}", "{{", "}}", "{%", "%}");
    }
}
