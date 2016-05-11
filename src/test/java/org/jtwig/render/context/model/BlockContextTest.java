package org.jtwig.render.context.model;

import com.google.common.base.Optional;
import org.jtwig.model.tree.BlockNode;
import org.jtwig.model.tree.Node;
import org.jtwig.resource.reference.ResourceReference;
import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BlockContextTest {
    private final HashMap<String, BlockDefinition> map = new HashMap<>();
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

        map.put(identifier, node);

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

        assertThat(map.get(identifier), new ReflectionEquals(new BlockDefinition(node, resourceReference)));
    }
}