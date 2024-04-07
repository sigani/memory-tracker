package memory;

import spoon.reflect.code.*;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.visitor.CtScanner;
import spoon.support.reflect.code.CtLiteralImpl;
import spoon.support.reflect.code.CtNewArrayImpl;
import utils.MemoryAlcValues;
import utils.MemoryKey;

import java.util.*;

public class MemoryScanner extends CtScanner {
    // memorykey is wrapper class for array so that it could be  as a key
        // lets define empty array as global scopeused
        // and lets define each string inside the key as the condition variables
    // and lets define the values to be a list of maps
        // of variable names as keys
        // and amount of bytes they take up as integers
    // both of these classes are defined in utils module
    private final Map<MemoryKey, MemoryAlcValues> memoryUsage = new HashMap<>();

    // when entering an if/else, push onto it the condition variables
    // when leaving, pop the variables, add to memoryUsage map
    // there should always be one element in the stack to signify global scope of class
    // basically just call rememberScope when entering branch
    // and forgetScope when leaving branch
    private final Stack<MemoryKey> scopeStack;
    private final Stack<MemoryAlcValues> allocStack;

    // variables that are influenced by the parameters/inputs
    private final LinkedList<String> userInputs;
    // conditional statements that are influenced by the parameters/inputs
    private final LinkedList<MemoryKey> userConditionals;
    // key = variable name, value = potential values of variable
    private final Map<String, LinkedList<String>> varsToInputs;

    // keep track of what scope we are at
    // calling rememberScope(string[] a)
    // where the param is the string version of what the parameters need to be in order to
    // enter the branch
    // will also update currentScope
    // rememberScope and forgetScope methods takes care of this anyways
    private MemoryKey currentScope;
    // currentAllocs.addValue(Map<String,Integer> a), where string is variable name and integer is amount of bytes it takes
    // rememberScope and forgetScope methods takes care of this anyways
    private MemoryAlcValues currentAllocs;

    MemoryScanner() {
        Stack<MemoryKey> stack = new Stack<>();
        Stack<MemoryAlcValues> stack2 = new Stack<>();
        String[] emptyStringArray = {""};
        MemoryKey scope = new MemoryKey(emptyStringArray);
        MemoryAlcValues allocs = new MemoryAlcValues();

        stack.push(scope);
        currentScope = scope;
        scopeStack = stack;
        allocStack = stack2;
        currentAllocs = allocs;
        userInputs = new LinkedList<>();
        userConditionals = new LinkedList<>();
        varsToInputs = new HashMap<>();
    }

    @Override
    public <T> void visitCtField(CtField<T> f) {
        System.out.println("CtField => " + f.getAssignment());
    }

    @Override
    public <T> void visitCtParameter(CtParameter<T> parameter) {
        userInputs.push(parameter.getSimpleName());
    }

    @Override
    public <T> void visitCtLocalVariable(CtLocalVariable<T> localVariable) {
        int memoryAllocated = calculateMemory(localVariable);
        Map<String, Integer> toAdd = new HashMap<>();
        toAdd.put(localVariable.getSimpleName(), memoryAllocated);
        currentAllocs.addValue(toAdd);

        // check to see if paramater influenced by user input
        if(localVariable.getAssignment() != null) {
            Class<?> classToCheck = localVariable.getAssignment().getClass();
            // literals never it
            if (classToCheck != CtLiteralImpl.class) {
                // split the input string by spaces, commas, and brackets
                // string manipulation goes crazy
                String[] tokens = localVariable.getAssignment().toString().split("[\\s,\\[\\]()]+");
                for(String token : tokens) {
                    for(String inputs : userInputs) {
                        if (token.contains(inputs)) {
                            userInputs.push(localVariable.getSimpleName());
                            if(!varsToInputs.containsKey(userInputs.peek())) {
                                varsToInputs.put(userInputs.peek(), new LinkedList<>());
                            }
                            varsToInputs.get(userInputs.peek()).push(localVariable.getAssignment().toString());
                            break;
                        }
                    }
                }
            }
        }

    }
    @Override
    public <T> void visitCtInvocation(CtInvocation<T> invocation) {
//        System.out.println(invocation);
    }

    @Override
    public <T, A extends T> void visitCtAssignment(CtAssignment<T, A> assignement) {
        System.out.println("CtAssignment => " + assignement);
        int memoryAllocated = calculateMemory(assignement);
        Map<String, Integer> toAdd = new HashMap<>();
        toAdd.put(assignement.getAssigned().toString(), memoryAllocated);
        currentAllocs.addValue(toAdd);
        // check to see if paramater influenced by user input
        if(assignement.getAssignment() != null) {
            Class<?> classToCheck = assignement.getAssignment().getClass();
            // literals never it
            if (classToCheck != CtLiteralImpl.class) {
                // split the input string by spaces, commas, and brackets
                // string manipulation goes crazy
                String[] tokens = assignement.getAssignment().toString().split("[\\s,\\[\\]()]+");
                for(String token : tokens) {
                    for(String inputs : userInputs) {
                        if (token.contains(inputs)) {
                            userInputs.push(assignement.getAssigned().toString());
                            if(!varsToInputs.containsKey(userInputs.peek())) {
                                varsToInputs.put(userInputs.peek(), new LinkedList<>());
                            }
                            varsToInputs.get(userInputs.peek()).push(assignement.getAssignment().toString());
                            break;
                        }
                    }
                }
            }
        }

    }

    // todo: override visitCtWhile and analyze as well
    // i forgot to do this, its probably similar to for loop

    // todo: reconsider this function
    // not too sure if this is sufficient for a loop
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
            // remember the scope
            rememberScope(iMessedUp);
            // analyze
            elseStatements.accept(this);
            // pop
            forgetScope();
        }
    }

    private void forgetScope() {
        // add to map/abstract state here
        if (memoryUsage.containsKey(currentScope)) {
            for(Map<String, Integer> val : currentAllocs.getValues())
                memoryUsage.get(currentScope).addValue(val);
        } else {
            MemoryAlcValues vals = new MemoryAlcValues();
            for(Map<String, Integer> val : currentAllocs.getValues())
                vals.addValue(val);
            memoryUsage.put(currentScope, vals);
        }

        // get the scope and allocations from before we entered the branch
        currentScope = scopeStack.pop();
        currentAllocs = allocStack.pop();
    }

    private void rememberScope(String[] iMessedUp) {
        // remember the scope we are in.
        MemoryKey scope = new MemoryKey(iMessedUp);
        MemoryAlcValues scopeAlloc = new MemoryAlcValues();

        // check to see if conditionals are influenced by parameters/user input
        String[] exprTokens = iMessedUp[0].split("[\\s,\\[\\]()]+");
        for(String token : exprTokens) {
            for(String inputs : userInputs) {
                if (token.contains(inputs)) {
                    userConditionals.push(scope);
                    break;
                }
            }
        }

        // push onto stack the current scope we are in and the allocations of that scope
        scopeStack.push(currentScope);
        allocStack.push(currentAllocs);

        // make a copy of the scope and allocations
        scope.push(currentScope);
        for(Map<String, Integer> i : currentAllocs.getValues()) {
            scopeAlloc.addValue(i);
        }

        // save the current scope and allocations
        currentScope = scope;
        currentAllocs = scopeAlloc;

    }

    private int calculateMemory(CtElement element) {
        // this was just for my reference
        // doesn't take into account arrays
        // might have to change signature for this
        String assignment;
        String type;
        if (element instanceof CtLocalVariable) {
            // Cast the element to a type that has the getAssignment method
            type = (((CtLocalVariable) element).getType() != null) ? ((CtLocalVariable) element).getType().toString() : null;
            assignment = (((CtLocalVariable) element).getAssignment() != null) ? ((CtLocalVariable) element).getAssignment().toString() : null;
        } else if (element instanceof CtAssignment) {
            type = (((CtAssignment) element).getType() != null) ? ((CtAssignment) element).getType().toString() : null;
            assignment = (((CtAssignment) element).getAssignment() != null) ? ((CtAssignment) element).getAssignment().toString() : null;
        } else {
            type = null;
            assignment = null;
        }

        if (assignment == null)  {
            System.out.println("Memory size => " + 0);
            return 0; // no new memory allocation
        }

        // TODO: what if assignment is funciton

        System.out.println("CtType => " + type);
        System.out.println("CtExpression => " + assignment);

        int elementSize = getPrimitiveSize(type);

        if (elementSize < 0) {
            if (type.contains("String") && assignment.charAt(0) == '\"') {
                int ret = (assignment.length() - 2) * 2 + 16;
                System.out.println("Memory size => " + ret);
                return (assignment.length() - 2) * 2 + 16; // number of chars * 2 + object overhead
            }

            if (!assignment.contains("new") && !assignment.contains("(")) {
                System.out.println("Memory size => " + 8);
                return 8; // no new memory allocaiton but creating a new pointer (8 bytes for reference variable)
            }

            // only certain built in objects are supported; others are estimated to be 8 bytes
            elementSize = getPrimitiveSize(type.toLowerCase()) >= 0 ? getPrimitiveSize(type.toLowerCase()) : 8;

            elementSize += 16; // adding object overhead
        }

        if(!type.contains("[")){
            System.out.println("Memory size => " + elementSize);
            return elementSize;
        }

        // TODO: consider a case where it is variable inside []
        int arrSize = Integer.parseInt(assignment.substring(assignment.indexOf("[") + 1, assignment.indexOf("]")));

        int ret = elementSize * arrSize + 16;
        System.out.println("Memory size => " + ret);
        return elementSize * arrSize + 16; // adding array object overhead 16
    }

    private int getPrimitiveSize(String type) {
        if (type.contains("byte") || type.contains("boolean")) {
            return 1;
        } else if (type.contains("short") || type.contains("char")) {
            return 2;
        } else if (type.contains("int") || type.contains("float")) {
            return 4;
        } else if (type.contains("double") || type.contains("long")){
            return 8;
        } else {
            return -1;
        }
    }

    public Map<MemoryKey, MemoryAlcValues> getMemoryUsage() {
        // fix this, this was my very lazy way of including the global scope
        // ideally we should add the global scope when we finish analyzing the program
        // this probably involves:
        // overriding some visit function (visitCtBlock or Method or something like that)
        // calling accept(this) on the parameter
        // then put these three lines after the accept call
        if (!memoryUsage.containsKey(currentScope)) {
            memoryUsage.put(currentScope, currentAllocs);
        }
        return memoryUsage;
    }

    public LinkedList<MemoryKey> getUserConditionals() {
        return this.userConditionals;
    }

    public Map<String, LinkedList<String>> getVarsToInputs() {
        return this.varsToInputs;
    }

}