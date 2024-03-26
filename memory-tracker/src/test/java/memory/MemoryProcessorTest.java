// https://github.com/SpoonLabs/spoon-examples/blob/master/src/main/java/fr/inria/gforge/spoon/analysis/ReferenceProcessorTest.java

package memory;

import org.junit.Test;
import spoon.Launcher;
import spoon.processing.ProcessingManager;
import spoon.reflect.factory.Factory;
import spoon.support.QueueProcessingManager;

public class MemoryProcessorTest {
    @Test
    public void testReferenceProcessor() throws Exception {
        final String[] args = {
                "-i", "src/test/java/memory/",
                "-o", "target/spooned/",
        };

        final Launcher launcher = new Launcher();
        launcher.setArgs(args);
        launcher.run();

        final Factory factory = launcher.getFactory();
        final ProcessingManager processingManager = new QueueProcessingManager(factory);
        final MemoryProcessor processor = new MemoryProcessor();
        processingManager.addProcessor(processor);
        processingManager.process(factory.Package().getRootPackage());

    }
}
