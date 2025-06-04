package org.example;

public class MinCoordinator {
    private final int[] sourceArray;
    private final int threadTotal;
    private final MinTask[] workers;
    private int threadsCompleted;

    public MinCoordinator(int[] array, int threadTotal) {
        this.sourceArray = array;
        this.threadTotal = threadTotal;
        this.workers = new MinTask[threadTotal];
        this.threadsCompleted = 0;
    }

    public void startSearch() {
        int[][] ranges = splitRanges(sourceArray.length, threadTotal);

        for (int i = 0; i < threadTotal; i++) {
            workers[i] = new MinTask(ranges[i][0], ranges[i][1], sourceArray, this);
            workers[i].start();
        }

        synchronized (this) {
            while (threadsCompleted < threadTotal) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        int overallMin = workers[0].getLocalMin();
        int overallIndex = workers[0].getMinIndex();

        for (int i = 1; i < threadTotal; i++) {
            if (workers[i].getLocalMin() < overallMin) {
                overallMin = workers[i].getLocalMin();
                overallIndex = workers[i].getMinIndex();
            }
        }

        System.out.println("Minimum value: " + overallMin);
        System.out.println("Found at index: " + overallIndex);
    }

    public synchronized void notifyThreadDone() {
        threadsCompleted++;
        if (threadsCompleted == threadTotal) {
            notifyAll();
        }
    }

    private int[][] splitRanges(int totalLength, int parts) {
        int[][] result = new int[parts][2];
        int base = totalLength / parts;
        int extra = totalLength % parts;
        int start = 0;

        for (int i = 0; i < parts; i++) {
            int end = start + base + (i < extra ? 1 : 0);
            result[i][0] = start;
            result[i][1] = end;
            start = end;
        }
        return result;
    }
}
