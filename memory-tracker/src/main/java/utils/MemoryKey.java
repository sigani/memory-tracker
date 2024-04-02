package utils;

import java.util.*;

public class MemoryKey {
    private String[] array;

    public MemoryKey(String[] array) {
        this.array = array;
    }

    public MemoryKey() {
        String[] empty = new String[1];
        empty[0] = "";
        this.array = empty;
    }


    // Override equals method to compare the contents of the arrays
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MemoryKey other = (MemoryKey) obj;
        return Arrays.equals(this.array, other.array);
    }

    // Override hashCode method to generate hash code based on the contents of the array
    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    // add another key
    public void push(String newItem) {
        String[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[newArray.length - 1] = newItem;
        this.array = newArray;
    }

    public void push(MemoryKey newItem) {
        int old_length = array.length;
        String[] newArray = Arrays.copyOf(array, array.length + newItem.numConditions());
        String[] toCopy = newItem.getConditions();
        for(int i = old_length, j = 0; i < newArray.length; i++, j++) {
            newArray[i] = toCopy[j];
        }
        this.array = newArray;
    }

    public void pop() {
        if (array.length == 0) {
            // No elements to pop
            return;
        }
        this.array = Arrays.copyOf(array, array.length - 1);
    }

    public int numConditions() {
        return this.array.length;
    }

    public String[] getConditions() {
        return this.array;
    }

}