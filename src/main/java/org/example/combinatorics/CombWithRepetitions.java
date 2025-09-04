package org.example.combinatorics;

import static org.example.combinatorics.Factorial.getFactorial;

public class CombWithRepetitions {
    public static long getPermutationsCount(int[] numbers) {
        int n = 0;
        long denominator = 1;
        for (int number : numbers) {
            n += number;
            denominator *= getFactorial(number);
        }
        return getFactorial(n) / denominator;
    }

    public static long getCombinationsCount(int n, int k) {
        return CombNoRepetitions.getCombinationsCount(n + k - 1, k);
    }

    public static long getAccommodationsCount(int n, int k) {
        return (long) Math.pow(n, k);
    }
}
