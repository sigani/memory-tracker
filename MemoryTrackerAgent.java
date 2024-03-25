// referencing
// https://www.baeldung.com/java-instrumentation
// https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/java/lang/instrument/Instrumentation.html
// https://web.archive.org/web/20141014195801/http://dhruba.name/2010/02/07/creation-dynamic-loading-and-instrumentation-with-javaagents/

import java.lang.instrument.Instrumentation;

public class MemoryTrackerAgent {

    // for agents, either one or the other is called but not both
    // pretty sure we want agentmain because it is called after program start
    // aka dynamic program analysis?

    //
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new MemoryTrackerTransformer());
    }

    // this is
    public static void agentmain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new MemoryTrackerTransformer());
    }
}

