package org.jtwig;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JtwigGlobalModelTest {
    
    @Test
    public void putInGlobal() {
        JtwigModel emptyModel = JtwigGlobalModel.newModel();
        Optional<Value> testEmpty = emptyModel.get("test");
        assertFalse("New models should not be filled", testEmpty.isPresent());
        
        JtwigGlobalModel.with("test", "T3ST");

        JtwigModel filledModel = JtwigGlobalModel.newModel();
        Optional<Value> testFilled = filledModel.get("test");
        assertTrue("New models should take from global", testFilled.isPresent());

        JtwigGlobalModel.remove("test");

        emptyModel = JtwigGlobalModel.newModel();
        testEmpty = emptyModel.get("test");
        assertFalse("New models should not be filled when global is empty", testEmpty.isPresent());
    }

    @Test
    public void modelFromGlobal() {
        JtwigGlobalModel.with("test", "D0G3");

        JtwigModel filledModel = JtwigModel.newModel();
        Optional<Value> testFilled = filledModel.get("test");
        assertTrue("New models should take from global", testFilled.isPresent());
        
        JtwigGlobalModel.remove("test");
    }

    @Test
    public void modelNotFromGlobal() {
        JtwigGlobalModel.with("test", "W0W");
        
        JtwigModel emptyModel = JtwigModel.newEmptyModel();
        Optional<Value> testEmpty = emptyModel.get("test");
        assertFalse("New empty models should not take from global", testEmpty.isPresent());

        JtwigGlobalModel.remove("test");
    }
}