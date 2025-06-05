package org.example;

public class MinTask extends Thread {
    private final int startIdx;
    private final int endIdx;
    private final int[] sourceArray;
    private int localMin;
    private int localMinIndex;
    private final MinCoordinator manager;

    public MinTask(int startIdx, int endIdx, int[] sourceArray, MinCoordinator manager) {
        this.startIdx = startIdx;
        this.endIdx = endIdx;
        this.sourceArray = sourceArray;
        this.manager = manager;
    }

    public int getLocalMin() {
        return localMin;
    }

    public int getMinIndex() {
        return localMinIndex;
    }

    @Override
    public void run() {
        localMin = sourceArray[startIdx];
        localMinIndex = startIdx;

        for (int i = startIdx + 1; i < endIdx; i++) {
            if (sourceArray[i] < localMin) {
                localMin = sourceArray[i];
                localMinIndex = i;
            }
        }

        manager.notifyThreadDone();
    }
}
