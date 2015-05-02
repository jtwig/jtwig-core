package org.jtwig.parser.config;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.Builder;
import org.jtwig.parser.parboiled.node.AddonParser;

import java.util.ArrayList;
import java.util.Collection;

public class ParserConfigurationBuilder implements Builder<ParserConfiguration> {
    public static ParserConfigurationBuilder parserConfiguration () {
        return new ParserConfigurationBuilder();
    }

    private final SyntaxConfigurationBuilder configuration = SyntaxConfigurationBuilder.syntaxConfiguration();
    private final Collection<AddonParser> addonParsers = new ArrayList<AddonParser>();

    public SyntaxConfigurationBuilder syntaxConfiguration() {
        return configuration;
    }

    public ParserConfigurationBuilder withAddonParser (AddonParser addonParser) {
        addonParsers.add(addonParser);
        return this;
    }

    @Override
    public ParserConfiguration build() {
        return new DefaultParserConfiguration(
                configuration.build(),
                ImmutableList.copyOf(addonParsers)
        );
    }

    private static class DefaultParserConfiguration implements ParserConfiguration {
        private final SyntaxConfiguration configuration;
        private final Collection<AddonParser> addonParsers;

        public DefaultParserConfiguration(SyntaxConfiguration configuration, Collection<AddonParser> addonParsers) {
            this.configuration = configuration;
            this.addonParsers = addonParsers;
        }

        @Override
        public SyntaxConfiguration syntaxConfiguration() {
            return configuration;
        }

        @Override
        public Collection<AddonParser> addonParsers() {
            return addonParsers;
        }
    }
}
