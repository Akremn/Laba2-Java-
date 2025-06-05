package org.example;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int dataSize = 1_000_000_000;
        int numberOfThreads = 32;

        System.out.println("Generating data...");
        int[] dataArray = generateData(dataSize);

        MinCoordinator manager = new MinCoordinator(dataArray, numberOfThreads);

        long start = System.nanoTime();
        manager.startSearch();
        long end = System.nanoTime();

        System.out.printf("Threads: %d | Duration: %.2f ms%n", numberOfThreads, (end - start) / 1_000_000.0);
    }

    private static int[] generateData(int size) {
        int[] array = new int[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000);
        }

        int negIndex = random.nextInt(size);
        array[negIndex] = -1;
        System.out.println("Negative value inserted at index: " + negIndex);

        return array;
    }
}
