package org.jtwig.parser.config;

import org.jtwig.parser.parboiled.node.AddonParser;

import java.util.Collection;

public interface ParserConfiguration {
    SyntaxConfiguration syntaxConfiguration ();
    Collection<AddonParser> addonParsers ();
}
