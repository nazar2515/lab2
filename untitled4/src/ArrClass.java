package com.company;

import java.util.Random;

public class ArrClass {
    private final int dim;
    private final int threadNum;
    public final int[] arr;

    public ArrClass(int dim, int threadNum) {
        this.dim = dim;
        this.threadNum = threadNum;
        arr = new int[dim];
        generateArray();
    }

    private void generateArray() {
        Random random = new Random();
        for (int i = 0; i < dim; i++) {
            arr[i] = random.nextInt(10000); // Заповнюємо масив випадковими числами
        }
        arr[random.nextInt(dim)] = -random.nextInt(1000); // Вставляємо від’ємне число у випадкову позицію
    }

    public int getMin() {
        return min;
    }

    public int getMinIndex() {
        return minIndex;
    }

    private int min = Integer.MAX_VALUE;
    private int minIndex = -1;

    synchronized public void collectMin(int minValue, int minIndex) {
        if (minValue < min) {
            min = minValue;
            this.minIndex = minIndex;
        }
    }

    public void findMin() {
        ThreadMin[] threadMins = new ThreadMin[threadNum];
        int chunkSize = (int) Math.ceil((double) dim / threadNum);

        for (int i = 0; i < threadNum; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, dim);
            threadMins[i] = new ThreadMin(start, end, this);
            threadMins[i].start();
        }

        for (ThreadMin thread : threadMins) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}