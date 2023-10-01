package com.joey.step.project.thread;

import com.joey.step.project.collections.CountryEnum;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier 例子
 *
 * 和CountdownLatch 的区别是:
 * CountdownLatch是一个线程等待其他线程全部完成才能继续任务。
 * CyclicBarrier 是所有线程同时到一个栅栏(barrier)被挡住，然后再全部被释放，
 * 先到栅栏的先等待其他的线程
 *
 * Semaphone 是只给有限的线程permit，其他线程阻塞，直到permit被之前的线程释放
 * 才能解除阻塞状态
 */

public class CyclicBarrierTest {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(6, ()->{
        System.out.println("六国聚兵结束，开始进攻!");
    });

    public static void main(String[] args) {
        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"准备完毕");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, CountryEnum.getCountryEnum(i).getCountryName()).start();
        }
    }
}
