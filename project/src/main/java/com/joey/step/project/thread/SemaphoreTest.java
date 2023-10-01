package com.joey.step.project.thread;

/**
 * 信号灯测试
 */

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {
    static Semaphore sm = new Semaphore(3);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try{
                    sm.acquire();
                    System.out.println(Thread.currentThread().getName()+"获得slot");
                    try{TimeUnit.SECONDS.sleep(5);}catch(InterruptedException ex){ex.printStackTrace();}
                }catch(Exception ex){}
                finally{
                    System.out.println(Thread.currentThread().getName()+"释放slot");
                    sm.release();
                }
            },""+i).start();
        }
    }
}
