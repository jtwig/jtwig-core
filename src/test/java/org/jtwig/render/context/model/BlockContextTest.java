package org.jtwig.render.context.model;

import com.google.common.base.Optional;
import org.jtwig.model.tree.BlockNode;
import org.jtwig.model.tree.Node;
import org.jtwig.resource.reference.ResourceReference;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.util.HashMap;
import java.util.Stack;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BlockContextTest {
    private static final String IDENTIFIER = "identifier";
    private final HashMap<String, Stack<BlockDefinition>> map = new HashMap<>();
    private BlockContext underTest = new BlockContext(map);

    private BlockNode blockNode1;
    private BlockDefinition blockDefinition1;
    private ResourceReference resourceReference1;

    private BlockNode blockNode2;
    private BlockDefinition blockDefinition2;
    private ResourceReference resourceReference2;

    private Stack<BlockDefinition> blockDefinitionsWithNode;
    private Stack<BlockDefinition> blockDefinitionsWithBothNodes;

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
        blockDefinitionsWithNode = new Stack<>();
        blockDefinitionsWithNode.push(blockDefinition1);

        blockDefinitionsWithBothNodes = new Stack<>();
        blockDefinitionsWithBothNodes.push(blockDefinition1);
        blockDefinitionsWithBothNodes.push(blockDefinition2);
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
    public void add() throws Exception {
        underTest.add(blockNode1, resourceReference1);

        assertThat(map.get(IDENTIFIER).peek(), new ReflectionEquals(blockDefinition1));
    }

    @Test
    public void addMultiple() throws Exception {
        underTest.add(blockNode1, resourceReference1);
        underTest.add(blockNode2, resourceReference2);

        assertThat(map.get(IDENTIFIER).get(0), new ReflectionEquals(blockDefinition1));
        assertThat(map.get(IDENTIFIER).get(1), new ReflectionEquals(blockDefinition2));
    }

    @Test
    public void get() {
        map.put(IDENTIFIER, blockDefinitionsWithNode);

        assertThat(underTest.get(IDENTIFIER).isPresent(), is(true));
        assertThat(underTest.get(IDENTIFIER).get(), new ReflectionEquals(blockDefinition1));

        assertThat(underTest.get(IDENTIFIER, 0).isPresent(), is(true));
        assertThat(underTest.get(IDENTIFIER, 0).get(), new ReflectionEquals(blockDefinition1));
    }

    @Test
    public void getMultiple() {
        map.put(IDENTIFIER, blockDefinitionsWithBothNodes);

        assertThat(underTest.get(IDENTIFIER, 0).isPresent(), is(true));
        assertThat(underTest.get(IDENTIFIER, 0).get(), new ReflectionEquals(blockDefinition1));

        assertThat(underTest.get(IDENTIFIER, 1).isPresent(), is(true));
        assertThat(underTest.get(IDENTIFIER, 1).get(), new ReflectionEquals(blockDefinition2));
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
}