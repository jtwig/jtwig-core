package org.jtwig.parser.parboiled.base;

import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;
import org.parboiled.annotations.Label;

public class LexicParser extends BasicParser<String> {
    Rule[] keywordRules = null;

    LexicParser(ParserContext context) {
        super(LexicParser.class, context);
    }

    // [0-9]+(?:\.[0-9]+)?
    public Rule Number() {
        return Sequence(
                OneOrMore(CharRange('0', '9')),
                Optional(Sequence(
                        Ch('.'),
                        CharRange('0', '9')
                ))
        );
    }

    public Rule Identifier() {
        Rule identifier = Sequence(
                TestNot(Keyword()),
                Letter()
        );
        if (keywordRules.length == 0) {
            identifier = Letter();
        }
        return Sequence(
                identifier,
                ZeroOrMore(LetterOrDigit())
        );
    }

    public Rule String() {
        return FirstOf(
                StringWith('\''),
                StringWith('"')
        );
    }

    Rule StringWith(char start) {
        return Sequence(start,
                ZeroOrMore(FirstOf(
                        Escape(),
                        Sequence(TestNot(AnyOf(new char[]{'\n', '\r', '\\', start})), ANY)
                )),
                push(match()),
                start
        );
    }

    Rule Escape() {
        return Sequence('\\', ANY);
    }

    @Label("Keywork")
    public Rule Keyword(Keyword keyword) {
        return Sequence(
                String(keyword.toString()),
                TestNot(
                        LetterOrDigit()
                )
        );
    }

    Rule Keyword() {
        return FirstOf(
                keywordRules()
        );
    }

    Rule[] keywordRules() {
        Keyword[] keywords = Keyword.values();
        keywordRules = new Rule[keywords.length];
        for (int i = 0; i < keywords.length; i++) {
            keywordRules[i] = Keyword(keywords[i]);
        }
        return keywordRules;
    }

    public Rule Letter() {
        return FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'), '_', '$');
    }

    public Rule LetterOrDigit() {
        return FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'), CharRange('0', '9'), '_', '$');
    }
}
