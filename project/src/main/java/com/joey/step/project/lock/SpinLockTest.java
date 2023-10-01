package com.joey.step.project.lock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁，实现一个自旋锁的例子
 */
public class SpinLockTest {
    static AtomicReference ref = new AtomicReference();

    public static void lock(){
        Thread currThread = Thread.currentThread();
        System.out.println(currThread.getName()+" lock!");
        while(!ref.compareAndSet(null,currThread)){
            //try {TimeUnit.MILLISECONDS.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}

        }
        System.out.println(currThread.getName()+" out!");
    }

    public static void unlock(){
        Thread currThread = Thread.currentThread();
        if (ref.compareAndSet(currThread, null)){
            System.out.println(currThread.getName()+" unlock!");
        }
    }

    public static void main(String[] args) {
        new Thread(()->{
            lock();
            try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
            unlock();
        }, "A").start();

        new Thread(()->{
            lock();
        }, "B").start();


    }

}
