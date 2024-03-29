package memory;

import spoon.reflect.code.*;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.CtScanner;
import spoon.processing.AbstractProcessor;
import spoon.support.reflect.code.CtBlockImpl;

import java.util.List;

public class MemoryScanner extends CtScanner {
    public int visited = 0;
    private String indent = "";

//    @Override
//    protected void enter(CtElement e) {
//        System.out.println("----");
//        System.out.println(e);
//        System.out.println(e.getClass());
//    }

    @Override
    public <T> void visitCtLocalVariable(CtLocalVariable<T> localVariable) {
        visited++;
        System.out.println(indent + localVariable);
    }

    @Override
    public <T,A extends T> void visitCtAssignment(CtAssignment<T,A> a) {
        System.out.println(indent + a);
    }

    @Override
    public void visitCtIf(CtIf ifElement) {
        CtStatement thenStatement = ifElement.getThenStatement();
        CtStatement elseStatement = ifElement.getElseStatement();

        if (thenStatement != null) {
//            System.out.println("entering THEN STATEMENTS");
            indent += "    ";
            thenStatement.accept(this);
            indent = indent.substring(0, indent.length()-4);
//            System.out.println("EXITING THEN STATEMENTS");
        }

        if (elseStatement != null) {
//            System.out.println("entering ELSE STATEMENTS");
            indent += "    ";
            elseStatement.accept(this);
            indent = indent.substring(0, indent.length()-4);
//            System.out.println("EXITING ELSE STATEMENTS");
        }
    }

    public void visitCtFor(CtFor forLoop) {
        indent += "    ";
        List<CtStatement> initStatements = forLoop.getForInit();
        for (CtStatement initStatement : initStatements) {
            initStatement.accept(this);
        }

        CtStatement bodyStatements = forLoop.getBody();
        if (bodyStatements != null) {
            bodyStatements.accept(this);
        }
        indent = indent.substring(0, indent.length()-4);
    }

}
