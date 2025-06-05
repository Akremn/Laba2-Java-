package org.example;

public class MinCoordinator {
    private final int[] dataArray;
    private final int numberOfThreads;
    private final MinTask[] tasks;
    private int completedCount;

    public MinCoordinator(int[] dataArray, int numberOfThreads) {
        this.dataArray = dataArray;
        this.numberOfThreads = numberOfThreads;
        this.tasks = new MinTask[numberOfThreads];
        this.completedCount = 0;
    }

    public void startSearch() {
        int[][] partitions = splitRanges(dataArray.length, numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            tasks[i] = new MinTask(partitions[i][0], partitions[i][1], dataArray, this);
            tasks[i].start();
        }

        synchronized (this) {
            while (completedCount < numberOfThreads) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        int globalMin = tasks[0].getLocalMin();
        int globalMinIndex = tasks[0].getMinIndex();

        for (int i = 1; i < numberOfThreads; i++) {
            if (tasks[i].getLocalMin() < globalMin) {
                globalMin = tasks[i].getLocalMin();
                globalMinIndex = tasks[i].getMinIndex();
            }
        }

        System.out.println("Minimum value: " + globalMin);
        System.out.println("Found at index: " + globalMinIndex);
    }

    public synchronized void notifyThreadDone() {
        completedCount++;
        if (completedCount == numberOfThreads) {
            notifyAll();
        }
    }

    private int[][] splitRanges(int totalLength, int parts) {
        int[][] result = new int[parts][2];
        int base = totalLength / parts;
        int remainder = totalLength % parts;
        int start = 0;

        for (int i = 0; i < parts; i++) {
            int end = start + base + (i < remainder ? 1 : 0);
            result[i][0] = start;
            result[i][1] = end;
            start = end;
        }
        return result;
    }
}
