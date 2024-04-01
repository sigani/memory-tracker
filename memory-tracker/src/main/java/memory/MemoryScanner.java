package memory;

import spoon.reflect.code.*;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.CtScanner;
import utils.MemoryAlcValues;
import utils.MemoryKey;

import java.util.*;

public class MemoryScanner extends CtScanner {
    // memorykey is wrapper class for array so that it could be used as a key
        // lets define empty array as global scope
        // and lets define each string inside the key as the condition variables
    // and lets define the values to be a list of maps
        // of variable names as keys
        // and amount of bytes they take up as integers
    // both of these classes are defined in utils module
    private final Map<MemoryKey, MemoryAlcValues> memoryUsage = new HashMap<>();

    // when entering an if/else, push onto it the condition variables
    // when leaving, pop the variables, add to memoryUsage map
    // there should always be one element in the stack to signify global scope of class
    private final Stack<MemoryKey> scopeStack;

    // keep track of what scope we are at
    // currentScope.push(...) to add another variable to scope
    private MemoryKey currentScope;

    MemoryScanner() {
        Stack<MemoryKey> stack = new Stack<>();
        String[] emptyStringArray = {""};
        MemoryKey scope = new MemoryKey(emptyStringArray);
        stack.push(scope);
        currentScope = scope;
        scopeStack = stack;
    }

    @Override
    public <T> void visitCtLocalVariable(CtLocalVariable<T> localVariable) {
        int memoryAllocated = calculateMemory(localVariable.getType());
        Map<String, Integer> toAdd = new HashMap<>();
        toAdd.put(localVariable.getSimpleName(), memoryAllocated);
        if (memoryUsage.containsKey(currentScope)) {
            memoryUsage.get(currentScope).addValue(toAdd);
        } else {
            MemoryAlcValues vals = new MemoryAlcValues();
            vals.addValue(toAdd);
            memoryUsage.put(currentScope, vals);
        }
    }

    @Override
    public <T, A extends T> void visitCtAssignment(CtAssignment<T, A> assignement) {
        int memoryAllocated = calculateMemory(assignement.getType());
        Map<String, Integer> toAdd = new HashMap<>();
        toAdd.put(assignement.getAssigned().toString(), memoryAllocated);
        if (memoryUsage.containsKey(currentScope)) {
            memoryUsage.get(currentScope).addValue(toAdd);
        } else {
            MemoryAlcValues vals = new MemoryAlcValues();
            vals.addValue(toAdd);
            memoryUsage.put(currentScope, vals);
        }
    }

    @Override
    public void visitCtFor(CtFor forLoop) {

        CtExpression<?> expr = forLoop.getExpression();
        String exprAsString = expr.toString();
        String[] iMessedUp = {exprAsString};

        rememberScope(iMessedUp);

        for(CtStatement vars : forLoop.getForInit()) {
            vars.accept(this);
        }

        CtStatement body = forLoop.getBody();
        if (body != null) body.accept(this);
        forgetScope();

    }

    @Override
    public void visitCtIf(CtIf ifElement) {
        CtExpression<?> expr = ifElement.getCondition();
        String exprAsString = expr.toString();
        String[] iMessedUp = {exprAsString};

        CtBlock<?> statements = ifElement.getThenStatement();
        if (statements != null) {
            // remember the scope we are in.
            rememberScope(iMessedUp);
            // analyze
            statements.accept(this);
            // pop
            forgetScope();
        }

        CtBlock<?> elseStatements = ifElement.getElseStatement();
        if (elseStatements != null) {
            // the opposite of the if statement
            String oppositeValue = "!(" + exprAsString + ")";
            iMessedUp = new String[]{oppositeValue};
            rememberScope(iMessedUp);
            // analyze
            elseStatements.accept(this);
            // pop
            forgetScope();
        }
    }

    private void forgetScope() {
        currentScope = scopeStack.pop();
    }

    private void rememberScope(String[] iMessedUp) {
        // remember the scope we are in.
        MemoryKey scope = new MemoryKey(iMessedUp);
        scope.push(currentScope);
        scopeStack.push(currentScope);
        currentScope = scope;

    }

    private int calculateMemory(CtElement element) {
        String[] primitiveDataTypes = {
                "byte",
                "short",
                "int",
                "long",
                "float",
                "double",
                "char",
                "boolean"
        };
        String str = element.toString();
        if(element.toString().contains("[")){
            str = element.toString().substring(0, element.toString().indexOf("["));
        }
        return switch (str) {
            case "byte", "boolean" -> 1;
            case "short", "char" -> 2;
            case "int", "float" -> 4;
            default -> 8;
        };
    }

    public Map<MemoryKey, MemoryAlcValues> getMemoryUsage() {
        return memoryUsage;
    }

}