package org.jtwig.render.context.model;

import com.google.common.base.Optional;
import org.jtwig.model.tree.BlockNode;
import org.jtwig.model.tree.Node;
import org.jtwig.resource.reference.ResourceReference;
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
    private final HashMap<String, Stack<BlockDefinition>> map = new HashMap<>();
    private BlockContext underTest = new BlockContext(map);

    @Test
    public void getNotPresent() throws Exception {
        String identifier = "identifier";

        Optional<BlockDefinition> result = underTest.get(identifier);

        assertEquals(false, result.isPresent());
    }

    @Test
    public void getPresent() throws Exception {
        String identifier = "identifier";
        BlockDefinition node = mock(BlockDefinition.class);

        Stack<BlockDefinition> blockDefinitions = new Stack<>();
        blockDefinitions.push(node);
        map.put(identifier, blockDefinitions);

        Optional<BlockDefinition> result = underTest.get(identifier);

        assertEquals(true, result.isPresent());
        assertEquals(node, result.get());
    }

    @Test
    public void add() throws Exception {
        String identifier = "identifier";
        Node node = mock(Node.class);
        BlockNode blockNode = mock(BlockNode.class);
        ResourceReference resourceReference = mock(ResourceReference.class);

        when(blockNode.getIdentifier()).thenReturn(identifier);
        when(blockNode.getContent()).thenReturn(node);

        underTest.add(blockNode, resourceReference);

        assertThat(map.get(identifier).peek(), new ReflectionEquals(new BlockDefinition(node, resourceReference)));
    }

    @Test
    public void addMultiple() throws Exception {
        String identifier = "identifier";
        Node node = mock(Node.class, "node");
        BlockNode blockNode = mock(BlockNode.class, "block");
        ResourceReference resourceReference = mock(ResourceReference.class, "res");

        when(blockNode.getIdentifier()).thenReturn(identifier);
        when(blockNode.getContent()).thenReturn(node);

        Node node2 = mock(Node.class, "node2");
        BlockNode blockNode2 = mock(BlockNode.class, "block2");
        ResourceReference resourceReference2 = mock(ResourceReference.class, "res2");

        when(blockNode2.getIdentifier()).thenReturn(identifier);
        when(blockNode2.getContent()).thenReturn(node2);

        underTest.add(blockNode, resourceReference);
        underTest.add(blockNode2, resourceReference2);

        assertThat(map.get(identifier).get(0), new ReflectionEquals(new BlockDefinition(node, resourceReference)));
        assertThat(underTest.get(identifier).isPresent(), is(true));
        assertThat(underTest.get(identifier).get(), new ReflectionEquals(new BlockDefinition(node, resourceReference)));

        assertThat(map.get(identifier).get(1), new ReflectionEquals(new BlockDefinition(node2, resourceReference2)));
        assertThat(underTest.get(identifier, 1).isPresent(), is(true));
        assertThat(underTest.get(identifier, 1).get(), new ReflectionEquals(new BlockDefinition(node2, resourceReference2)));
    }
}