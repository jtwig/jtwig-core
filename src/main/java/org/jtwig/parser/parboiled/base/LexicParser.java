package org.jtwig.parser.parboiled.base;

import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;
import org.parboiled.annotations.Label;

import java.util.Collection;

public class LexicParser extends BasicParser<String> {
    final Collection<String> extraKeywords;
    Rule[] keywordRules = null;

    LexicParser(ParserContext context, Collection<String> extraKeywords) {
        super(LexicParser.class, context);
        this.extraKeywords = extraKeywords;
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

    @Label("Keywork")
    public Rule Keyword(Keyword keyword) {
        return Sequence(
                String(keyword.toString()),
                TestNot(
                        LetterOrDigit()
                )
        );
    }

    @Label("Keywork")
    public Rule Keyword(String keyword) {
        return Sequence(
                String(keyword),
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
        keywordRules = new Rule[keywords.length + extraKeywords.size()];
        int i = 0;
        for (; i < keywords.length; i++) {
            keywordRules[i] = Keyword(keywords[i]);
        }
        for (String extraKeyword : extraKeywords) {
            keywordRules[i++] = Keyword(extraKeyword);
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
