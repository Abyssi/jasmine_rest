package com.jasmine.jasmine_rest.utils;

import java.util.Random;

public class RandomUtils {
    private static final Random RANDOM = new Random();

    public RandomUtils() {
    }

    public static byte[] nextBytes(int count) {
        byte[] result = new byte[count];
        RANDOM.nextBytes(result);
        return result;
    }

    public static int nextInt(int startInclusive, int endExclusive) {
        return startInclusive == endExclusive ? startInclusive : startInclusive + RANDOM.nextInt(endExclusive - startInclusive);
    }

    public static long nextLong(long startInclusive, long endExclusive) {
        return startInclusive == endExclusive ? startInclusive : (long) nextDouble((double) startInclusive, (double) endExclusive);
    }

    public static double nextDouble(double startInclusive, double endInclusive) {
        return startInclusive == endInclusive ? startInclusive : startInclusive + (endInclusive - startInclusive) * RANDOM.nextDouble();
    }

    public static float nextFloat(float startInclusive, float endInclusive) {
        return startInclusive == endInclusive ? startInclusive : startInclusive + (endInclusive - startInclusive) * RANDOM.nextFloat();
    }
}
