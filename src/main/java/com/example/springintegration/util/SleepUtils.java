package com.example.springintegration.util;

import java.util.Random;

public class SleepUtils {

    public static void sleep(long second) throws InterruptedException {
        Thread.sleep(second * 1000);
    }

    public static void randomSleep(int n) throws InterruptedException {
        Random r = new Random();
        sleep(r.nextInt(n));
    }
}
