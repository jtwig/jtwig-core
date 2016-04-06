package org.jtwig.integration.addon;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LimitsParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.node.AddonParser;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.model.EscapeMode;
import org.jtwig.render.node.renderer.NodeRender;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.StringRenderable;
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
                .parser().addonParserProviders().add(new AddonParserProvider() {
                    @Override
                    public Class<? extends AddonParser> parser() {
                        return SimpleAddOnParser.class;
                    }

                    @Override
                    public Collection<String> keywords() {
                        return Collections.emptyList();
                    }
                }).and().and()
                .render().nodeRenders().add(SimpleAddOn.class, new AddOnNodeRender()).and().and()
                .build()).render(JtwigModel.newModel());

        assertThat(result, is("Hello World!"));
    }

    public static class AddOnNodeRender implements NodeRender<SimpleAddOn> {

        @Override
        public Renderable render(RenderRequest renderRequest, SimpleAddOn node) {
            return new StringRenderable("Hello World!", EscapeMode.NONE);
        }
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
    }
}
