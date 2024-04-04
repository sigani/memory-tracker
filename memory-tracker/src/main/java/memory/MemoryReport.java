package memory;

import org.apache.commons.lang3.tuple.Pair;
import utils.MemoryAlcValues;
import utils.MemoryKey;

import java.util.Map;

public class MemoryReport {

    private final Map<MemoryKey, MemoryAlcValues> state;

    public MemoryReport(Map<MemoryKey, MemoryAlcValues> state) {
        this.state = state;
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

        System.out.println("The input combination that uses the most memory is: ");
        for (String condition : biggestKey.getConditions()) {
            System.out.println("\t" + condition);
        }
        System.out.println("With a total size of: " + biggestSize);

        return Pair.of(biggestKey, biggestSize);
    }
}
