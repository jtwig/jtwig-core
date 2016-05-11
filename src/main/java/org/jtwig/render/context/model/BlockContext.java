package org.jtwig.render.context.model;

import com.google.common.base.Optional;
import org.jtwig.model.tree.BlockNode;
import org.jtwig.resource.reference.ResourceReference;

import java.util.HashMap;
import java.util.Map;

public class BlockContext {
    public static BlockContext newContext () {
        return new BlockContext(new HashMap<String, BlockDefinition>());
    }

    private final Map<String, BlockDefinition> blocks;

    public BlockContext(Map<String, BlockDefinition> blocks) {
        this.blocks = blocks;
    }

    public Optional<BlockDefinition> get(String identifier) {
        return Optional.fromNullable(blocks.get(identifier));
    }

    public void add(BlockNode node, ResourceReference source) {
        if (!blocks.containsKey(node.getIdentifier())) {
            blocks.put(node.getIdentifier(), new BlockDefinition(node.getContent(), source));
        }
    }

}
