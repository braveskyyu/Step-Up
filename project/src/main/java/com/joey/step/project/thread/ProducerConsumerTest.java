package com.joey.step.project.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerTest {
    private int num = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private void increase(){
        try{
            lock.lock();
            while(num>0){
                System.out.println(Thread.currentThread().getName()+" is blocking" );
                condition.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName()+"\t" +num);
            condition.signalAll();

        }catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            lock.unlock();
        }
    }

    private void decrease(){
        try{
            lock.lock();
            while(num==0){
                System.out.println(Thread.currentThread().getName()+" is blocking" );
                condition.await();
            }
            num--;
            System.out.println(Thread.currentThread().getName()+"\t" +num);
            condition.signalAll();

        }catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ProducerConsumerTest pt = new ProducerConsumerTest();
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                pt.increase();
            },"producer"+i).start();
        }

        try{TimeUnit.SECONDS.sleep(5);}catch(InterruptedException ex){ex.printStackTrace();}

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                pt.decrease();
            },"consumer"+ i).start();
        }
    }
}
