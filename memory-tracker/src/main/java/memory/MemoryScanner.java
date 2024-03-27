package memory;

import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtFieldWrite;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.CtScanner;
import spoon.processing.AbstractProcessor;

public class MemoryScanner extends CtScanner {
    public int visited = 0;
    final String PRINT_MEMORY = "System.out.println(\"MB: \" + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()))";

    @Override
    protected void enter(CtElement e) {
//        System.out.println("----");
//        System.out.println(e);
//        System.out.println(e.getClass());
    }

    @Override
    public <T> void visitCtLocalVariable(CtLocalVariable<T> localVariable) {
        visited++;
        final CtCodeSnippetStatement snippet = localVariable.getFactory().Core().createCodeSnippetStatement();;
        snippet.setValue(PRINT_MEMORY);
        localVariable.insertAfter(snippet);
    }

//    @Override
//    public <T,A extends T> void visitCtAssignment(CtAssignment<T,A> a) {
//        final CtCodeSnippetStatement snippet = a.getFactory().Core().createCodeSnippetStatement();;
//        snippet.setValue(PRINT_MEMORY);
//        a.insertAfter(snippet);
//    }

}
