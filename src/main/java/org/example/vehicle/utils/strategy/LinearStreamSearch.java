package org.example.vehicle.utils.strategy;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class LinearStreamSearch implements Search {
    @Override
    public Map<Integer, Long> count(int[] array) {
        return Arrays.stream(array)
                .boxed()
                .parallel()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
    }
}
