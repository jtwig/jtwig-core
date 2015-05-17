package org.jtwig.parser;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.Builder;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.config.ParserConfiguration;
import org.jtwig.parser.config.ParserConfigurationBuilder;
import org.jtwig.parser.parboiled.DocumentParser;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.node.AddonParser;
import org.jtwig.parser.util.ParboiledExceptionMessageExtractor;
import org.jtwig.resource.Resource;
import org.parboiled.errors.ParseError;
import org.parboiled.errors.ParserRuntimeException;
import org.parboiled.parserunners.BasicParseRunner;
import org.parboiled.support.ParsingResult;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.parboiled.common.FileUtils.readAllText;

public class JtwigParserBuilder implements Builder<JtwigParser> {
    public static JtwigParserBuilder jtwigParser() {
        return new JtwigParserBuilder();

    }

    private ParserConfiguration configuration;
    private Charset charset = Charset.defaultCharset();
    private Collection<Class<? extends AddonParser>> addOnParsers = new ArrayList<Class<? extends AddonParser>>();

    private JtwigParserBuilder() {
    }


    public JtwigParserBuilder withConfiguration(ParserConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

    public JtwigParserBuilder withAddOnParsers (Collection<Class<? extends AddonParser>> addOnParsers) {
        this.addOnParsers.addAll(addOnParsers);
        return this;
    }

    @Override
    public JtwigParser build() {
        if (configuration == null) {
            return new ParboiledJtwigParser(ParserConfigurationBuilder.parserConfiguration().build(), addOnParsers, charset);
        } else {
            return new ParboiledJtwigParser(configuration, addOnParsers, charset);
        }
    }

    public JtwigParserBuilder withCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    public static class ParboiledJtwigParser implements JtwigParser {
        private final ParboiledExceptionMessageExtractor exceptionMessageExtractor = new ParboiledExceptionMessageExtractor();
        private final ParserConfiguration configuration;
        private final Collection<Class<? extends AddonParser>> addOnParsers;
        private final Charset charset;

        public ParboiledJtwigParser(ParserConfiguration configuration, Collection<Class<? extends AddonParser>> addOnParsers, Charset charset) {
            this.configuration = configuration;
            this.addOnParsers = addOnParsers;
            this.charset = charset;
        }

        @Override
        public Node parse(Resource resource) {
            BasicParseRunner<Node> runner = new BasicParseRunner<Node>(
                    ParserContext.instance(resource, configuration, addOnParsers)
                            .parser(DocumentParser.class)
                            .NodeRule()
            );

            try {
                ParsingResult<Node> result = runner.run(readAllText(resource.content(), charset));

                if (result.hasErrors()) {
                    throw new ParseException(toMessage(result.parseErrors));
                } else if (!result.matched) {
                    throw new ParseException("Invalid template format");
                } else {
                    return result.valueStack.pop();
                }
            } catch (ParserRuntimeException e) {
                if ((e.getCause() != null) && (e.getCause() instanceof ParseException)) {
                    ParseException cause = (ParseException) e.getCause();

                    throw new ParseException(String.format("%s\n%s", cause.getMessage(), exceptionMessageExtractor.extract(e)), cause.getCause());
                } else {
                    throw new ParseException(e);
                }
            }
        }

        private String toMessage(List<ParseError> parseErrors) {
            return StringUtils.join(Collections2.transform(parseErrors, new Function<ParseError, String>() {
                @Override
                public String apply(ParseError input) {
                    return String.format("%d, %d -> %s", input.getStartIndex(), input.getEndIndex(), input.getErrorMessage());
                }
            }), "\n");
        }
    }
}
