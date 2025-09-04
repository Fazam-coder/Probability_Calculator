package org.example.combinatorics;

public class Factorial {
    public static long getFactorial(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
