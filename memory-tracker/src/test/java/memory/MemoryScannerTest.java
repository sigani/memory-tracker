// https://github.com/SpoonLabs/spoon-examples/blob/master/src/main/java/fr/inria/gforge/spoon/analysis/ReferenceProcessorTest.java

package memory;

import org.junit.Test;
import spoon.Launcher;
import spoon.processing.ProcessingManager;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.factory.Factory;
import spoon.support.QueueProcessingManager;
import utils.MemoryAlcValues;
import utils.MemoryKey;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MemoryScannerTest {


    private static void prettyPrintMap(Map<MemoryKey, MemoryAlcValues> map) {
        for (Map.Entry<MemoryKey, MemoryAlcValues> entry : map.entrySet()) {
            System.out.println("Key: " + Arrays.toString(entry.getKey().getConditions()));
            System.out.println("Values:");
            LinkedList<Map<String,Integer>> linkedList = entry.getValue().getValues();
            for (Map<String, Integer> innerMap : linkedList) {
                System.out.println("\t" + innerMap);
            }
            System.out.println();
        }
    }

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
        prettyPrintMap(scanner.getMemoryUsage());
        System.out.println("end of test");
    }

    @Test
    public void testMemoryReportMAX() throws Exception {
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
        MemoryReport report = new MemoryReport(scanner);

        report.getExtremesReport(true);
    }

    @Test
    public void testMemoryReportMIX() throws Exception {
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
        MemoryReport report = new MemoryReport(scanner);

        report.getExtremesReport(true);
    }

    @Test
    public void testMemoryReportAnalyzeConditionals() throws Exception {
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

        MemoryReport report = new MemoryReport(scanner);

        System.out.println("Testing User Conditionals:");

        for (MemoryKey key : scanner.getUserConditionals()) {
            report.getInputData(key);
        }

        System.out.println("Vars to Inputs:");

        Map<String, LinkedList<String>> varstoInputs = scanner.getVarsToInputs();
        for(Map.Entry<String, LinkedList<String>> entry : varstoInputs.entrySet()) {
            System.out.println(entry.getKey());
            for(String val : varstoInputs.get(entry.getKey())) {
                System.out.println("\t"+val);
            }

        }
    }


}
