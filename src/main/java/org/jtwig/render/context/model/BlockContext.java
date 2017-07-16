package org.jtwig.render.context.model;

import com.google.common.base.Optional;
import org.jtwig.model.tree.BlockNode;
import org.jtwig.resource.reference.ResourceReference;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BlockContext {
    private final Map<String, Stack<BlockDefinition>> blocks;

    // FIXME cleanup heavy mis-use of the stack… analyze actual usage
    public BlockContext(Map<String, Stack<BlockDefinition>> blocks) {
        this.blocks = blocks;
    }

    public static BlockContext newContext() {
        return new BlockContext(new HashMap<String, Stack<BlockDefinition>>());
    }

    public Optional<BlockDefinition> get(String identifier) {
        return get(identifier, 0);
    }

    public Optional<BlockDefinition> get(String identifier, int index) {
        Stack<BlockDefinition> stack = blocks.get(identifier);
        if(stack == null) {
            return Optional.absent();
        }

        try {
            return Optional.of(stack.get(index));
        } catch (ArrayIndexOutOfBoundsException e) {
            return Optional.absent();
        }
    }

    public void add(BlockNode node, ResourceReference source) {
        BlockDefinition blockDefinition = new BlockDefinition(node.getContent(), source);
        String identifier = node.getIdentifier();

        add(identifier, blockDefinition);
    }

    public void add(String identifier, BlockDefinition blockDefinition) {
        if (!blocks.containsKey(identifier)) {
            Stack<BlockDefinition> blockDefinitions = new Stack<>();
            blockDefinitions.push(blockDefinition);
            blocks.put(identifier, blockDefinitions);
        } else {
            blocks.get(identifier).push(blockDefinition);
        }
    }

    public Optional<BlockDefinition> pop(String identifier) {
        Stack<BlockDefinition> stack = blocks.get(identifier);
        if(stack == null) {
            return Optional.absent();
        }


        try {
            BlockDefinition blockDefinition = stack.get(0);
            stack.removeElementAt(0);
            return Optional.of(blockDefinition);
        }
        catch (EmptyStackException e) {
            return Optional.absent();
        }
    }

    public void addTop(String identifier, BlockDefinition blockDefinition) {
        if (!blocks.containsKey(identifier)) {
            Stack<BlockDefinition> blockDefinitions = new Stack<>();
            blockDefinitions.add(0, blockDefinition);
            blocks.put(identifier, blockDefinitions);
        } else {
            blocks.get(identifier).add(0, blockDefinition);
        }
    }
}
