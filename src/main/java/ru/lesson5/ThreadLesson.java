package ru.lesson5;

import java.util.Arrays;

public class ThreadLesson {
    private static int SIZE=10000000;
    private static int HALF = SIZE / 2;

    public static void main(String[] args) throws InterruptedException {

        float[] arr = new float[SIZE];

        Arrays.fill(arr, 1f);

        //single-threading
        long singleThreadStart = System.currentTimeMillis();
        overFillArray(arr);
        System.out.println("Time : " + (System.currentTimeMillis()  - singleThreadStart));

        //multithreading
        long multiThreadStart = System.currentTimeMillis();
        multiThreadRun(arr);
        System.out.println("Time : " + (System.currentTimeMillis()  - multiThreadStart));
    }

    static void multiThreadRun(float[] arr) throws InterruptedException {
        float[] p1 = new float[HALF];
        float[] p2 = new float[HALF];

        //split array
        System.arraycopy(arr,0,p1,0,HALF);
        System.arraycopy(arr,HALF,p2,0,HALF);

        //overfill arrays
        Thread thread1 = new Thread(() -> overFillArray(p1));

        Thread thread2 = new Thread(() ->  overFillArray(p2));

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        //glue arrays
        System.arraycopy(p1, 0, arr, 0, HALF);
        System.arraycopy(p2, 0, arr, HALF, HALF);
    }

    /**
     * overfill array
     */
    static void overFillArray(float[] arr){
        for(int i = 0; i < arr.length; i++)
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
    }
}
