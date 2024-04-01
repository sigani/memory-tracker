package utils;

import java.util.LinkedList;
import java.util.Map;

public class MemoryAlcValues {
    private LinkedList<Map<String,Integer>> values;

    public MemoryAlcValues() {
        values = new LinkedList<Map<String,Integer>>();
    }

    public void addValue(Map<String,Integer> toAdd) {
        values.push(toAdd);
    }

    public LinkedList<Map<String,Integer>> getValues() {
        return values;
    }


}
