package com.company;

public class ThreadMin extends Thread {
    private final int startIndex;
    private final int finishIndex;
    private final ArrClass arrClass;

    public ThreadMin(int startIndex, int finishIndex, ArrClass arrClass) {
        this.startIndex = startIndex;
        this.finishIndex = finishIndex;
        this.arrClass = arrClass;
    }

    @Override
    public void run() {
        int[] arr = arrClass.arr;
        int localMin = Integer.MAX_VALUE;
        int localMinIndex = -1;

        for (int i = startIndex; i < finishIndex; i++) {
            if (arr[i] < localMin) {
                localMin = arr[i];
                localMinIndex = i;
            }
        }

        arrClass.collectMin(localMin, localMinIndex);
    }
}