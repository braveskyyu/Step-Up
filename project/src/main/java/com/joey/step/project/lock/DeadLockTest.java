package com.joey.step.project.lock;

import java.util.concurrent.TimeUnit;

public class DeadLockTest implements Runnable{
    static {
        System.out.println("start to initialize DeadLockTest");
        try{TimeUnit.SECONDS.sleep(10);}catch(InterruptedException ex){ex.printStackTrace();}
    }

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new DeadLockTest(),""+i).start();
        }
    }

    @Override
    public void run() {
        System.out.println("Thread "+Thread.currentThread().getName()+"\t"+"start!");
    }
}
