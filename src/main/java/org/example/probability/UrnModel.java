package org.example.probability;

import static org.example.combinatorics.CombWithoutRepetitions.getCombinationsCount;

public class UrnModel {
    public static double getProbabilityAllMarked(int n, int m, int k) {
        return ((double) getCombinationsCount(m, k)) / getCombinationsCount(n, k);
    }
    public static double getProbabilityRMarked(int n, int m, int k, int r) {
        return ((double) getCombinationsCount(m, r) * getCombinationsCount(n - m, k - r)) / getCombinationsCount(n, k);
    }
}
