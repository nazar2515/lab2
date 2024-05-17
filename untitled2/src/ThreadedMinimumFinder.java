// ThreadedMinimumFinder.java
import java.util.Arrays;

public class ThreadedMinimumFinder extends Thread {
    private int[] array;
    private int startIndex;
    private int endIndex;
    private static int min = Integer.MAX_VALUE;
    private static int minIndex = -1;
    private static final Object lock = new Object();

    public ThreadedMinimumFinder(int[] array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        int localMin = Integer.MAX_VALUE;
        int localMinIndex = -1;
        for (int i = startIndex; i < endIndex; i++) {
            if (array[i] < localMin) {
                localMin = array[i];
                localMinIndex = i;
            }
        }
        synchronized (lock) {
            if (localMin < min) {
                min = localMin;
                minIndex = localMinIndex;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = generateArray(1000000); // Generating an array of large length
        int numThreads = 4; // Number of threads

        ThreadedMinimumFinder[] threads = new ThreadedMinimumFinder[numThreads];
        int chunkSize = array.length / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? array.length : (i + 1) * chunkSize;
            threads[i] = new ThreadedMinimumFinder(array, start, end);
            threads[i].start();
        }

        try {
            for (ThreadedMinimumFinder thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Minimum value: " + min);
        System.out.println("Index of minimum value: " + minIndex);
    }

    private static int[] generateArray(int length) {
        int[] array = new int[length];
        Arrays.fill(array, Integer.MAX_VALUE); // Filling the array with large values
        array[(int) (Math.random() * length)] = -1; // Replacing a random element with a negative number
        return array;
    }
}