package org.example;

public class MinTask extends Thread {
    private final int from;
    private final int to;
    private final int[] data;
    private int minVal;
    private int minIdx;
    private final MinCoordinator coordinator;

    public MinTask(int from, int to, int[] data, MinCoordinator coordinator) {
        this.from = from;
        this.to = to;
        this.data = data;
        this.coordinator = coordinator;
    }

    public int getLocalMin() {
        return minVal;
    }

    public int getMinIndex() {
        return minIdx;
    }

    @Override
    public void run() {
        minVal = data[from];
        minIdx = from;

        for (int i = from + 1; i < to; i++) {
            if (data[i] < minVal) {
                minVal = data[i];
                minIdx = i;
            }
        }

        coordinator.notifyThreadDone();
    }
}
