package org.jtwig.functions.impl.structural;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.impl.structural.exceptions.ParentFunctionOutsideBlockException;
import org.jtwig.functions.impl.structural.exceptions.ParentFunctionWithoutExtending;
import org.jtwig.render.context.model.BlockContext;
import org.jtwig.render.context.model.BlockDefinition;
import org.jtwig.render.context.model.BlockReference;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ParentFunctionExceptionTest {

    private ParentFunction underTest;
    private FunctionRequest request;

    @Before
    public void prepareTestdata() {
        request = mock(FunctionRequest.class, RETURNS_DEEP_STUBS);
        underTest = new ParentFunction();
    }
    @Test(expected = ParentFunctionOutsideBlockException.class)
    public void getBlockIdentifierThrowsCorrectExceptionWhenOutsideOfBlock() {
        when(request.getRenderContext().getCurrent(BlockReference.class)).thenThrow(IllegalStateException.class);

        underTest.execute(request);
    }

    @Test(expected = ParentFunctionOutsideBlockException.class)
    public void getBlockContextThrowsCorrectExceptionWhenOutsideOfBlock() {
        BlockReference blockReferenceMock = mock(BlockReference.class);
        when(blockReferenceMock.getIdentifier()).thenReturn("ident");

        when(request.getRenderContext().getCurrent(BlockReference.class)).thenReturn(blockReferenceMock);
        when(request.getRenderContext().getCurrent(BlockContext.class)).thenThrow(IllegalStateException.class);

        underTest.execute(request);
    }

    @Test(expected = ParentFunctionWithoutExtending.class)
    public void getParentBlockDefinitionThrowsCorrectExceptionWhenOutsideOfBlock() {
        BlockReference blockReferenceMock = mock(BlockReference.class);
        when(blockReferenceMock.getIdentifier()).thenReturn("ident");

        BlockDefinition blockDefinitionMock = mock(BlockDefinition.class);

        BlockContext blockContextMock = mock(BlockContext.class);
        when(blockContextMock.pollFirst(anyString())).thenReturn(Optional.of(blockDefinitionMock));
        when(blockContextMock.get(anyString())).thenReturn(Optional.<BlockDefinition>absent());

        when(request.getRenderContext().getCurrent(BlockReference.class)).thenReturn(blockReferenceMock);
        when(request.getRenderContext().getCurrent(BlockContext.class)).thenReturn(blockContextMock);

        underTest.execute(request);
    }

    @Test(expected = ParentFunctionOutsideBlockException.class)
    public void pollCurrentBlockDefinitionThrowsCorrectExceptionWhenOutsideOfBlock() {
        BlockReference blockReferenceMock = mock(BlockReference.class);
        when(blockReferenceMock.getIdentifier()).thenReturn("ident");

        BlockContext blockContextMock = mock(BlockContext.class);
        when(blockContextMock.pollFirst(anyString())).thenReturn(Optional.<BlockDefinition>absent());

        when(request.getRenderContext().getCurrent(BlockReference.class)).thenReturn(blockReferenceMock);
        when(request.getRenderContext().getCurrent(BlockContext.class)).thenReturn(blockContextMock);

        underTest.execute(request);
    }
}