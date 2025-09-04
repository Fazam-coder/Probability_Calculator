package org.example.combinatorics;

import static org.example.combinatorics.Factorial.getFactorial;

public class CombNoRepetitions {

    public static long getPermutationsCount(int n) {
        return getFactorial(n);
    }

    public static long getCombinationsCount(int n, int k) {
        return getFactorial(n) / (getFactorial(k) * getFactorial(n - k)) ;
    }

    public static long getAccommodationsCount(int n, int k) {
        return getFactorial(n) / getFactorial(n - k);
    }
}
