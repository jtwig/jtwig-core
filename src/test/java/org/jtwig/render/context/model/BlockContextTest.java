package org.jtwig.render.context.model;

import com.google.common.base.Optional;
import org.jtwig.model.tree.BlockNode;
import org.jtwig.model.tree.Node;
import org.jtwig.resource.reference.ResourceReference;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.util.HashMap;
import java.util.LinkedList;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BlockContextTest {
    private static final String IDENTIFIER = "identifier";
    private final HashMap<String, LinkedList<BlockDefinition>> map = new HashMap<>();
    private BlockContext underTest = new BlockContext(map);

    private BlockNode blockNode1;
    private BlockDefinition blockDefinition1;
    private ResourceReference resourceReference1;

    private BlockNode blockNode2;
    private BlockDefinition blockDefinition2;
    private ResourceReference resourceReference2;

    private LinkedList<BlockDefinition> blockDefinitionsWithNode;
    private LinkedList<BlockDefinition> blockDefinitionsWithBothNodes;

    @Before
    public void prepareTestdata() {
        prepareTestnode1();
        prepareTestnode2();
        prepareTeststack();
    }

    private void prepareTestnode1() {
        Node node1 = mock(Node.class, "node1");
        blockNode1 = mock(BlockNode.class, "block1");
        resourceReference1 = mock(ResourceReference.class, "res");
        blockDefinition1 = new BlockDefinition(node1, resourceReference1);

        when(blockNode1.getIdentifier()).thenReturn(IDENTIFIER);
        when(blockNode1.getContent()).thenReturn(node1);
    }

    private void prepareTestnode2() {
        Node node2 = mock(Node.class, "node2");
        blockNode2 = mock(BlockNode.class, "block2");
        resourceReference2 = mock(ResourceReference.class, "res2");
        blockDefinition2 = new BlockDefinition(node2, resourceReference2);

        when(blockNode2.getIdentifier()).thenReturn(IDENTIFIER);
        when(blockNode2.getContent()).thenReturn(node2);
    }

    private void prepareTeststack() {
        blockDefinitionsWithNode = new LinkedList<>();
        blockDefinitionsWithNode.add(blockDefinition1);

        blockDefinitionsWithBothNodes = new LinkedList<>();
        blockDefinitionsWithBothNodes.add(blockDefinition1);
        blockDefinitionsWithBothNodes.add(blockDefinition2);
    }

    @Test
    public void newContext() {
        BlockContext blockContext = BlockContext.newContext();
        assertThat(blockContext, notNullValue());
    }

    @Test
    public void getNotPresent() throws Exception {
        String identifier = "identifier";

        Optional<BlockDefinition> result = underTest.get(identifier);

        assertEquals(false, result.isPresent());
    }

    @Test
    public void getPresent() throws Exception {
        String identifier = "identifier";

        map.put(identifier, blockDefinitionsWithNode);

        Optional<BlockDefinition> result = underTest.get(identifier);

        assertEquals(true, result.isPresent());
        assertEquals(blockDefinition1, result.get());
    }

    @Test
    public void addLast() throws Exception {
        underTest.addLast(blockNode1, resourceReference1);

        assertThat(map.get(IDENTIFIER).peek(), new ReflectionEquals(blockDefinition1));
    }

    @Test
    public void addLastMultiple() throws Exception {
        underTest.addLast(blockNode1, resourceReference1);
        underTest.addLast(blockNode2, resourceReference2);

        assertThat(map.get(IDENTIFIER).get(0), new ReflectionEquals(blockDefinition1));
        assertThat(map.get(IDENTIFIER).get(1), new ReflectionEquals(blockDefinition2));
    }

    @Test
    public void addLastFirst() throws Exception {
        underTest.addFirst(blockNode1, resourceReference1);

        assertThat(map.get(IDENTIFIER).peek(), new ReflectionEquals(blockDefinition1));
    }

    @Test
    public void addFirstMultiple() throws Exception {
        underTest.addFirst(blockNode1, resourceReference1);
        underTest.addFirst(blockNode2, resourceReference2);

        assertThat(map.get(IDENTIFIER).get(1), new ReflectionEquals(blockDefinition1));
        assertThat(map.get(IDENTIFIER).get(0), new ReflectionEquals(blockDefinition2));
    }

    @Test
    public void get() {
        map.put(IDENTIFIER, blockDefinitionsWithNode);

        Optional<BlockDefinition> definitionA = underTest.get(IDENTIFIER);
        assertThat(definitionA.isPresent(), is(true));
        assertThat(definitionA.get(), new ReflectionEquals(blockDefinition1));

        Optional<BlockDefinition> definitionB = underTest.get(IDENTIFIER, 0);
        assertThat(definitionB.isPresent(), is(true));
        assertThat(definitionB.get(), new ReflectionEquals(blockDefinition1));
    }

    @Test
    public void getMultiple() {
        map.put(IDENTIFIER, blockDefinitionsWithBothNodes);

        Optional<BlockDefinition> definition1 = underTest.get(IDENTIFIER, 0);
        assertThat(definition1.isPresent(), is(true));
        assertThat(definition1.get(), new ReflectionEquals(blockDefinition1));

        Optional<BlockDefinition> definition2 = underTest.get(IDENTIFIER, 1);
        assertThat(definition2.isPresent(), is(true));
        assertThat(definition2.get(), new ReflectionEquals(blockDefinition2));
    }

    @Test
    public void unknownIdentifierIsAbsent() {
        assertThat(underTest.get(IDENTIFIER).isPresent(), is(false));
    }

    @Test
    public void outOfBoundsIsAbsent() {
        map.put(IDENTIFIER, blockDefinitionsWithNode);

        assertThat(underTest.get(IDENTIFIER, 45).isPresent(), is(false));
    }

    @Test
    public void pollFirst() {
        map.put(IDENTIFIER, blockDefinitionsWithNode);

        Optional<BlockDefinition> pollFirst = underTest.pollFirst(IDENTIFIER);
        assertThat(pollFirst.isPresent(), is(true));
        assertThat(pollFirst.get(), new ReflectionEquals(blockDefinition1));

        assertThat(map.get(IDENTIFIER).size(), is(0));
    }

    @Test
    public void pollFirstUnknownIdentifierIsAbsent() {
        Optional<BlockDefinition> pollFirst = underTest.pollFirst(IDENTIFIER);
        assertThat(pollFirst.isPresent(), is(false));
    }

    @Test
    public void pollFirstOnEmptyListIsAbsent() {
        map.put(IDENTIFIER, new LinkedList<BlockDefinition>());

        Optional<BlockDefinition> pollFirst = underTest.pollFirst(IDENTIFIER);
        assertThat(pollFirst.isPresent(), is(false));
    }

    @Test
    public void pollFirstMultiple() {
        map.put(IDENTIFIER, blockDefinitionsWithBothNodes);

        assertThat(map.get(IDENTIFIER).size(), is(2));

        Optional<BlockDefinition> definition1 = underTest.pollFirst(IDENTIFIER);
        assertThat(definition1.isPresent(), is(true));
        assertThat(definition1.get(), new ReflectionEquals(blockDefinition1));

        assertThat(map.get(IDENTIFIER).size(), is(1));

        Optional<BlockDefinition> definition2 = underTest.pollFirst(IDENTIFIER);
        assertThat(definition2.isPresent(), is(true));
        assertThat(definition2.get(), new ReflectionEquals(blockDefinition2));

        assertThat(map.get(IDENTIFIER).size(), is(0));
    }
}