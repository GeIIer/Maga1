package org.example.vehicle.utils.strategy;

import java.util.HashMap;
import java.util.Map;

public class LinearSearch implements Search {
    @Override
    public Map<Integer, Long> count(int[] array) {
        Map<Integer, Long> result = new HashMap<>();
        for (int key : array) {
            if (!result.containsKey(key)) {
                result.put(key, 1L);
            } else {
                long count = result.get(key);
                result.put(key, ++count);
            }
        }
        return result;
    }
}
