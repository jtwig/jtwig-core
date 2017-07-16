package org.jtwig.functions.impl;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.resource.loader.InMemoryResourceLoader;
import org.jtwig.resource.loader.TypedResourceLoader;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;
import static org.jtwig.resource.reference.ResourceReference.MEMORY;

public class ParentFunctionTest {
    private JtwigModel model;
    private EnvironmentConfiguration configTemplate;

    @Before
    public void setupModel() {
        model = JtwigModel.newModel();
    }

    @Before
    public void setupConfiguration() {
        TypedResourceLoader templateResourceA = new TypedResourceLoader(MEMORY, InMemoryResourceLoader.builder()
                .withResource("a", "x{% block a %}A{% endblock %}x")
                .build());

        TypedResourceLoader templateResourceB = new TypedResourceLoader(MEMORY, InMemoryResourceLoader.builder()
                .withResource("b", "{% extends 'memory:a' %}" +
                        "{% block a %}B{{ parent() }}B{% endblock %}")
                .build());

        TypedResourceLoader templateResourceC = new TypedResourceLoader(MEMORY, InMemoryResourceLoader.builder()
                .withResource("c", "{% extends 'memory:b' %}" +
                        "{% block a %}C{{ parent() }}C{% endblock %}")
                .build());

        configTemplate = configuration().resources().resourceLoaders()
                .add(templateResourceA)
                .add(templateResourceB)
                .add(templateResourceC)
                .and().and().build();
    }

    private void testWith(String template, String expected) {
        String result = JtwigTemplate.inlineTemplate(template, configTemplate)
                .render(JtwigModel.newModel());

        assertThat(result, is(expected));
    }

    @Test
    public void inheritsParentBlock() {
        testWith(
                "{% extends 'memory:a' %}" +
                        "{% block a %}B{{ parent() }}{% endblock %}",
                "xBAx"
        );
    }

    @Test
    public void inheritsParentBlockWithSuffix() {
        testWith(
                "{% extends 'memory:a' %}" +
                        "{% block a %}B{{ parent() }}B{% endblock %}",
                "xBABx"
        );
    }

    @Test(expected = IllegalStateException.class)
    public void errorWhenCallingParentWithoutExtending() {
        testWith(
                "{% block a %}B{{ parent() }}{% endblock %}",
                ""
        );
    }

    @Test
    public void inheritsParentBlockTwiceIfRequested() {
        testWith(
                "{% extends 'memory:a' %}" +
                        "{% block a %}{{ parent() }}B{{ parent() }}{% endblock %}",
                "xABAx"
        );
    }

    @Test
    public void inheritsParentBlockThroughTwoLevels() {
        testWith(
                "{% extends 'memory:b' %}" +
                        "{% block a %}C{{ parent() }}C{% endblock %}",
                "cCBABCx"
        );
    }

    @Test
    public void inheritsParentBlockThroughThreeLevels() {
        testWith(
                "{% extends 'memory:c' %}" +
                        "{% block a %}D{{ parent() }}D{% endblock %}",
                "cDCBABCDx"
        );
    }

    @Test
    public void blockFunctionInterpretsInheritedBlocks() {
        testWith(
                "{% extends 'memory:a' %}" +
                        "{% block a %}{{ parent() }}B{{ parent() }}{% endblock %}" +
                        "{% block z %}Z{{ block('a')}}Z{% endblock %}",
                "xABAxZABAZ"
        );
    }

    @Test
    public void blockFunctionInterpretsInheritedBlocksIndependentOfOrder() {
        testWith(
                "{% extends 'memory:a' %}" +
                        "{% block z %}Z{{ block('a')}}Z{% endblock %}" +
                        "{% block a %}{{ parent() }}B{{ parent() }}{% endblock %}",
                "xABAxZABAZ"
        );
    }

    @Test
    public void blockFunctionInterpretsInheritedBlocksThroughMultipleLevels() {
        testWith(
                "{% extends 'memory:b' %}" +
                        "{% block a %}C{{ parent() }}C{% endblock %}" +
                        "{% block z %}Z{{ block('a')}}Z{% endblock %}",
                "xCBABCxZCBABCZ"
        );
    }
}
