package memory;

import org.apache.commons.lang3.tuple.Pair;
import utils.MemoryAlcValues;
import utils.MemoryKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemoryReport {

    private final Map<MemoryKey, MemoryAlcValues> state;
    private final Map<String, LinkedList<String>> varsToInputs;


    public MemoryReport(MemoryScanner state) {
        this.state = state.getMemoryUsage();
        this.varsToInputs = state.getVarsToInputs();
    }

    public Pair<MemoryKey, Integer> getExtremesReport(Boolean isMax) {
        MemoryKey biggestKey = null;
        int biggestSize = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Map.Entry<MemoryKey, MemoryAlcValues> entry : state.entrySet()) {
            int currentSize = 0;
            for (Map<String, Integer> innerMap : entry.getValue().getValues()) {
                // System.out.println("\t" + innerMap);
                currentSize += innerMap.values().stream().mapToInt(Integer::intValue).sum();
            }
            // System.out.println("Total size: " + currentSize);
            if (isMax && (currentSize > biggestSize) || !isMax && (currentSize < biggestSize)) {
                biggestSize = currentSize;
                biggestKey = entry.getKey();
            }
        }

        if (biggestKey == null) {
            System.out.println("No keys found");
            return null;
        }

        System.out.println("The input combination that uses the" +  (isMax ? " most" : " least") + " memory is: ");
        for (String condition : biggestKey.getConditions()) {
            System.out.println("\t" + condition);
        }

        System.out.println("The branch uses " + biggestSize + " bytes");

        getInputData(biggestKey);

        return Pair.of(biggestKey, biggestSize);
    }

    public void getComprehensiveReport() {
        for (Map.Entry<MemoryKey, MemoryAlcValues> entry : state.entrySet()) {
            int currentSize = 0;
            for (Map<String, Integer> innerMap : entry.getValue().getValues()) {
                // System.out.println("\t" + innerMap);
                currentSize += innerMap.values().stream().mapToInt(Integer::intValue).sum();
            }
            System.out.println("BRANCH: " + Arrays.toString(entry.getKey().getConditions()));
            System.out.println("Uses " + currentSize + " bytes");
            getInputData(entry.getKey());

        }
    }

    public static String[] getVariablesCondition(String condition) {
        ArrayList<String> variables = new ArrayList<>();
        Pattern pattern = Pattern.compile("[a-zA-Z_$][a-zA-Z_$0-9.]*");
        Matcher matcher = pattern.matcher(condition);

        while (matcher.find()) {
            String variable = matcher.group();
            variables.add(variable);
        }

        return variables.toArray(new String[0]);
    }

    public void getInputData(MemoryKey key) {
        for (String condition : key.getConditions()) {
            if(condition.isEmpty()) {
                continue;
            }
            System.out.println("\tCondition: " + condition);
            String[] variables = getVariablesCondition(condition);

            for (String variable : variables) {
                System.out.println("\t\tVariable: " + variable);
                LinkedList<String> setters = varsToInputs.get(variable);
                if (setters != null) {
                    for (String input : setters) {
                        System.out.println("\t\t\tWas set by: " + input);
                        StringBuilder prefix = new StringBuilder();
                        boolean keep = true;
                        String previous = input;
                        while (keep) {
                            if (varsToInputs.get(input) != null) {
                                prefix.append("\t");
                                input = varsToInputs.get(input).getFirst();
                                System.out.println(prefix + "\t\t\t"+ previous + " was set by: " + input);
                            } else {
                                keep = false;
                            }
                        }
                    }

                } else {
                    System.out.println("\t\t\tWas not set by any input");
                }
            }
        }
    }



}
