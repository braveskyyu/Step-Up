package com.joey.step.project.lock;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class VolatileTest {
    private static volatile int num = 0;

    static CountDownLatch downLatch = new CountDownLatch(10);
    @SneakyThrows
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 2000; j++) {
                     num = num+1;
                }

                downLatch.countDown();
            },"t"+i).start();
        }

        downLatch.await();
        System.out.println("number is "+num);
    }
}
