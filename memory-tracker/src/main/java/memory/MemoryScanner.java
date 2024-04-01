package memory;

import spoon.reflect.code.*;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.CtScanner;
import utils.MemoryAlcValues;
import utils.MemoryKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

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
//        memoryUsage.put(localVariable.getSimpleName(), memoryAllocated);
//        System.out.println("Allocated memory for variable '" + localVariable.getSimpleName() + "': " + memoryAllocated + " bytes");
    }

    @Override
    public <T> void visitCtNewArray(CtNewArray<T> newArray) {
        int memoryAllocated = calculateMemory(newArray.getType()) * newArray.getElements().size();
        Map<String, Integer> toAdd = new HashMap<>();
        toAdd.put(newArray.toString(), memoryAllocated);

        MemoryKey scopeVariable = new MemoryKey();
        scopeVariable.push(currentScope);
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
            int memoryAllocated = calculateMemory(vars);
            Map<String, Integer> toAdd = new HashMap<>();
            toAdd.put(vars.toString(), memoryAllocated);
            if (memoryUsage.containsKey(currentScope)) {
                memoryUsage.get(currentScope).addValue(toAdd);
            } else {
                MemoryAlcValues vals = new MemoryAlcValues();
                vals.addValue(toAdd);
                memoryUsage.put(currentScope, vals);
            }
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
            // remember the scope we are in.
            rememberScope(iMessedUp);
            // analyze
            elseStatements.accept(this);
            // pop
            forgetScope();
        }
    }

    private void forgetScope() {
        scopeStack.pop();
        currentScope = scopeStack.peek();
    }

    private void rememberScope(String[] iMessedUp) {
        // remember the scope we are in.
        MemoryKey scope = new MemoryKey(iMessedUp);
        if (currentScope.numConditions() > 1) {
            for(String conds : currentScope.getConditions()) {
                scope.push(conds);
            }
        }
        scope.push(currentScope);
        scopeStack.push(scope);
        currentScope = scope;
    }

    private int calculateMemory(CtElement element) {
        return 4; // Assuming 4 bytes per element for demonstration purposes
    }

    public Map<MemoryKey, MemoryAlcValues> getMemoryUsage() {
        return memoryUsage;
    }

}

//    @Override
//    protected void enter(CtElement e) {
//        System.out.println("----");
//        System.out.println(e);
//        System.out.println(e.getClass());
//    }

//    @Override
//    public <T> void visitCtLocalVariable(CtLocalVariable<T> localVariable) {
//        visited++;
//        System.out.println(indent + localVariable);
//    }
//
//    @Override
//    public <T,A extends T> void visitCtAssignment(CtAssignment<T,A> a) {
//        System.out.println(indent + a);
//    }
//
//    @Override
//    public void visitCtIf(CtIf ifElement) {
//        CtStatement thenStatement = ifElement.getThenStatement();
//        CtStatement elseStatement = ifElement.getElseStatement();
//
//        if (thenStatement != null) {
////            System.out.println("entering THEN STATEMENTS");
//            indent += "    ";
//            thenStatement.accept(this);
//            indent = indent.substring(0, indent.length()-4);
////            System.out.println("EXITING THEN STATEMENTS");
//        }
//
//        if (elseStatement != null) {
////            System.out.println("entering ELSE STATEMENTS");
//            indent += "    ";
//            elseStatement.accept(this);
//            indent = indent.substring(0, indent.length()-4);
////            System.out.println("EXITING ELSE STATEMENTS");
//        }
//    }
//
//    public void visitCtFor(CtFor forLoop) {
//        indent += "    ";
//        List<CtStatement> initStatements = forLoop.getForInit();
//        for (CtStatement initStatement : initStatements) {
//            initStatement.accept(this);
//        }
//
//        CtStatement bodyStatements = forLoop.getBody();
//        if (bodyStatements != null) {
//            bodyStatements.accept(this);
//        }
//        indent = indent.substring(0, indent.length()-4);
//    }