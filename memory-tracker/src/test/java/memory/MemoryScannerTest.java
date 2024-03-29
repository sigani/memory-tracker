// https://github.com/SpoonLabs/spoon-examples/blob/master/src/main/java/fr/inria/gforge/spoon/analysis/ReferenceProcessorTest.java

package memory;

import org.junit.Test;
import spoon.Launcher;
import spoon.processing.ProcessingManager;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.factory.Factory;
import spoon.support.QueueProcessingManager;

public class MemoryScannerTest {
    @Test
    public void testSimpleScanner() throws Exception {
        System.out.println("start of test");
        final String[] args = {
//                "-i", "src/test/java/memory/",
//              it looks like this targets which file to "analyze"
                "-i", "src/toAnalyze/",
                "-o", "./target/spooned/",
        };

        final Launcher launcher = new Launcher();
        launcher.setArgs(args);
        launcher.run();

        MemoryScanner scanner = new MemoryScanner();
        final Factory factory = launcher.getFactory();
        CtClass<?> sample = factory.Class().get("Sample");
        sample.accept(scanner);
//        System.out.println(sample);
        System.out.println("end of test");
    }


}
