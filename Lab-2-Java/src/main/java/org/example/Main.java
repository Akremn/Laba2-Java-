package org.example;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int arraySize = 1_000_000_000;
        int threadsCount = 32;

        System.out.println("Generating data...");
        int[] numbers = generateData(arraySize);

        MinCoordinator coordinator = new MinCoordinator(numbers, threadsCount);

        long start = System.nanoTime();
        coordinator.startSearch();
        long end = System.nanoTime();

        System.out.printf("Threads: %d | Duration: %.2f ms%n", threadsCount, (end - start) / 1_000_000.0);
    }

    private static int[] generateData(int size) {
        int[] array = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(1000);
        }
        int negIndex = rand.nextInt(size);
        array[negIndex] = -1;
        System.out.println("Negative value inserted at index: " + negIndex);
        return array;
    }
}
