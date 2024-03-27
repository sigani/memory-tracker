// https://spoon.gforge.inria.fr/processor.html
// https://spoon.gforge.inria.fr/
// https://spoon.gforge.inria.fr/mvnsites/spoon-core/apidocs/spoon/processing/AbstractProcessor.html

package memory;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.CtScanner;
import spoon.reflect.visitor.Filter;

public class MemoryProcessor extends AbstractProcessor<CtClass<?>> {

    @Override
    public void process(CtClass<?> ctClass) {
        CtMethod<?> mainMethod = ctClass.getMethodsByName("main").get(0);
        CtBlock<?> body = mainMethod.getBody();

        CtCodeSnippetStatement snippet = getFactory().Core().createCodeSnippetStatement();
        // insert print statements of memory usage
        final String value = "System.out.println(\"MB: \" + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()))";
        snippet.setValue(value);
        mainMethod.getBody().insertEnd(snippet);
        Filter<? extends CtStatement> f = (Filter<CtStatement>) statement -> statement.toString().contains("= ");

    }
}

