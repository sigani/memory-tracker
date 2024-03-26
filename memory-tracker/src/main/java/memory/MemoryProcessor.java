// https://spoon.gforge.inria.fr/processor.html
// https://spoon.gforge.inria.fr/
// https://spoon.gforge.inria.fr/mvnsites/spoon-core/apidocs/spoon/processing/AbstractProcessor.html

package memory;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;

public class MemoryProcessor extends AbstractProcessor<CtClass<?>> {
    @Override
    public void process(CtClass<?> ctClass) {
        System.out.println(ctClass.prettyprint());
    }
}
