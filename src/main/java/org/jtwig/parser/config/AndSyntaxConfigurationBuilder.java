package org.jtwig.parser.config;

import org.jtwig.environment.and.AndBuilder;

public class AndSyntaxConfigurationBuilder<Parent extends JtwigParserConfigurationBuilder> extends SyntaxConfigurationBuilder<AndSyntaxConfigurationBuilder<Parent>> implements AndBuilder<Parent> {
    private final Parent parent;

    public AndSyntaxConfigurationBuilder(Parent parserConfigurationBuilder) {
        this.parent = parserConfigurationBuilder;
    }

    public AndSyntaxConfigurationBuilder(Parent parent, SyntaxConfiguration prototype) {
        super(prototype);
        this.parent = parent;
    }

    @Override
    public Parent and() {
        return parent;
    }
}
