package org.jtwig.integration.addon;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.context.RenderContext;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LimitsParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.node.AddonParser;
import org.jtwig.render.Renderable;
import org.jtwig.render.impl.StringRenderable;
import org.junit.Test;
import org.parboiled.Rule;

import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class AddOnParserTest extends AbstractIntegrationTest {
    @Test
    public void addOn() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% hello %}", configuration()
                .withAddOnParser(new AddonParserProvider() {
                    @Override
                    public Class<? extends AddonParser> parser() {
                        return SimpleAddOnParser.class;
                    }

                    @Override
                    public Collection<String> keywords() {
                        return Collections.emptyList();
                    }
                })
                .build()).render(JtwigModel.newModel());

        assertThat(result, is("Hello World!"));
    }

    public static class SimpleAddOnParser extends AddonParser {
        public SimpleAddOnParser(ParserContext context) {
            super(SimpleAddOnParser.class, context);
        }

        @Override
        public Rule NodeRule() {
            PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
            LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
            SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
            return Sequence(
                    positionTrackerParser.PushPosition(),
                    limitsParser.startCode(),
                    spacingParser.Spacing(),
                    String("hello"),
                    spacingParser.Spacing(),
                    limitsParser.endCode(),
                    push(new SimpleAddOn(positionTrackerParser.pop()))
            );
        }
    }

    public static class SimpleAddOn extends Node {
        protected SimpleAddOn(Position position) {
            super(position);
        }

        @Override
        public Renderable render(RenderContext context) {
            return new StringRenderable("Hello World!", EscapeMode.NONE);
        }
    }
}
