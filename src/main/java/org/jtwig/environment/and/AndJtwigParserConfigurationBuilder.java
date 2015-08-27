package org.jtwig.environment.and;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.parser.JtwigParserConfiguration;
import org.jtwig.parser.JtwigParserConfigurationBuilder;

public class AndJtwigParserConfigurationBuilder extends JtwigParserConfigurationBuilder<AndJtwigParserConfigurationBuilder> implements AndBuilder<EnvironmentConfigurationBuilder> {
    private final EnvironmentConfigurationBuilder environmentConfigurationBuilder;

    public AndJtwigParserConfigurationBuilder(EnvironmentConfigurationBuilder environmentConfigurationBuilder) {
        super();
        this.environmentConfigurationBuilder = environmentConfigurationBuilder;
    }

    public AndJtwigParserConfigurationBuilder(JtwigParserConfiguration prototype, EnvironmentConfigurationBuilder environmentConfigurationBuilder) {
        super(prototype);
        this.environmentConfigurationBuilder = environmentConfigurationBuilder;
    }

    @Override
    public EnvironmentConfigurationBuilder and () {
        return environmentConfigurationBuilder;
    }
}
