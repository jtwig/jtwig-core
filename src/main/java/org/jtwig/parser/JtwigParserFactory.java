package org.jtwig.parser;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Collections2;
import org.apache.commons.lang3.StringUtils;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.cache.TemplateCache;
import org.jtwig.parser.parboiled.DocumentParser;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.util.ParboiledExceptionMessageExtractor;
import org.jtwig.resource.Resource;
import org.parboiled.errors.ParseError;
import org.parboiled.errors.ParserRuntimeException;
import org.parboiled.parserunners.BasicParseRunner;
import org.parboiled.support.ParsingResult;

import java.util.List;

import static org.parboiled.common.FileUtils.readAllText;

public class JtwigParserFactory {
    public JtwigParser create (JtwigParserConfiguration configuration) {
        Optional<TemplateCache> templateCache = configuration.getTemplateCache();
        if (templateCache.isPresent()) {
            return new CachedJtwigParser(templateCache.get(), new ParboiledJtwigParser(configuration));
        } else {
            return new ParboiledJtwigParser(configuration);
        }
    }

    public static class ParboiledJtwigParser implements JtwigParser {
        private final ParboiledExceptionMessageExtractor exceptionMessageExtractor = new ParboiledExceptionMessageExtractor();
        private final JtwigParserConfiguration configuration;

        public ParboiledJtwigParser(JtwigParserConfiguration configuration) {
            this.configuration = configuration;
        }

        @Override
        public Node parse(Resource resource) {
            BasicParseRunner<Node> runner = new BasicParseRunner<Node>(
                    ParserContext.instance(resource, configuration.getSyntaxConfiguration(),
                            configuration.getAddonParserProviders(),
                            configuration.getUnaryOperators(),
                            configuration.getBinaryOperators())
                            .parser(DocumentParser.class)
                            .NodeRule()
            );

            try {
                ParsingResult<Node> result = runner.run(readAllText(resource.getContent(), resource.getCharset()));
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
