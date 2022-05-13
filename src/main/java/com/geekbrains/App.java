package com.geekbrains;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        firstMethod();
        secondMethod();
    }

    private static void secondMethod() {
        int size = 10_000_000;
        int half = size / 2;
        float[] arr = new float[size];
        Arrays.fill(arr, 1.0f);
        long startTime = System.currentTimeMillis();
        float[] leftHalf = new float[half];
        float[] rightHalf = new float[half];
        System.arraycopy(arr, 0, leftHalf, 0, half);
        System.arraycopy(arr, half, rightHalf, 0, half);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < leftHalf.length; i++) {
                leftHalf[i] = (float) (leftHalf[i] * Math.sin(0.2f + i / 5f) * Math.cos(0.2f + i / 5f) * Math.cos(0.4f + i / 2f));
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < rightHalf.length; i++)
                rightHalf[i] = (float) (rightHalf[i] * Math.sin(0.2f + i / 5f) * Math.cos(0.2f + i / 5f) * Math.cos(0.4f + i / 2f));
        });

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        float[] goodArr = new float[size];
        System.arraycopy(leftHalf, 0, goodArr, 0, half);
        System.arraycopy(rightHalf, 0, goodArr, half, half);
        System.out.println("Two thread time: " + (System.currentTimeMillis() - startTime) + " ms.");

    }

    private static void firstMethod() {
        int size = 10_000_000;
        float[] arr = new float[size];
        Arrays.fill(arr, 1.0f);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5f) * Math.cos(0.2f + i / 5f) * Math.cos(0.4f + i / 2f));
        }
        System.out.println("One thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
    }
}
