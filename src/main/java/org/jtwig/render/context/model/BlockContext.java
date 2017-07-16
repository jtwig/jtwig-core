package org.jtwig.render.context.model;

import com.google.common.base.Optional;
import org.jtwig.model.tree.BlockNode;
import org.jtwig.resource.reference.ResourceReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BlockContext {
    private final Map<String, Stack<BlockDefinition>> blocks;

    public BlockContext(Map<String, Stack<BlockDefinition>> blocks) {
        this.blocks = blocks;
    }

    public static BlockContext newContext() {
        return new BlockContext(new HashMap<String, Stack<BlockDefinition>>());
    }

    public Optional<BlockDefinition> get(String identifier) {
        Stack<BlockDefinition> blockDefinitions = blocks.get(identifier);
        if (blockDefinitions == null) {
            return Optional.absent();
        }

        return Optional.fromNullable(blockDefinitions.get(0));
    }

    public Optional<BlockDefinition> get(String identifier, int index) {
        Stack<BlockDefinition> stack = blocks.get(identifier);
        return Optional.fromNullable(stack.get(index));
    }

    public void add(BlockNode node, ResourceReference source) {
        BlockDefinition blockDefinition = new BlockDefinition(node.getContent(), source);

        if (!blocks.containsKey(node.getIdentifier())) {
            Stack<BlockDefinition> blockDefinitions = new Stack<>();
            blockDefinitions.push(blockDefinition);
            blocks.put(node.getIdentifier(), blockDefinitions);
        } else {
            blocks.get(node.getIdentifier()).push(blockDefinition);
        }
    }

}
