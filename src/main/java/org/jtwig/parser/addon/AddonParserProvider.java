package org.jtwig.parser.addon;

import org.jtwig.parser.parboiled.node.AddonParser;

import java.util.Collection;

public interface AddonParserProvider {
    Class<? extends AddonParser> parser ();
    Collection<String> keywords ();
}
